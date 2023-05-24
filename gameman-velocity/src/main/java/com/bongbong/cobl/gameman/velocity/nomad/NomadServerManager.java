package com.bongbong.cobl.gameman.velocity.nomad;

import com.bongbong.cobl.gameman.velocity.GameManPlugin;
import com.hashicorp.nomad.apimodel.*;
import com.hashicorp.nomad.javasdk.NomadApiClient;
import com.hashicorp.nomad.javasdk.NomadApiConfiguration;
import com.hashicorp.nomad.javasdk.NomadException;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import lombok.Getter;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class NomadServerManager {
  private static final Set<String> BIND_TO_JOBS = Set.of("test_mc");

  @Getter
  private final NomadApiClient nomad;
  private final Logger logger;
  private final ProxyServer proxy;

  public NomadServerManager(GameManPlugin plugin, ProxyServer proxy, Logger logger, String address) {
    this.logger = logger;
    this.proxy = proxy;
    this.nomad = new NomadApiClient(new NomadApiConfiguration.Builder().setAddress(address).build());
    proxy.getScheduler().buildTask(plugin, this::checkForServers).repeat(1, TimeUnit.SECONDS).schedule();
  }

  public void checkForServers() {
    final List<String> current = proxy.getAllServers().stream().map(x -> x.getServerInfo().getName()).collect(Collectors.toList());
    try {
      for (AllocationListStub alloc : nomad.getAllocationsApi().list().getValue()) {
        if (BIND_TO_JOBS.stream().noneMatch(job -> alloc.getJobId().contains(job))) {
          continue;
        }
        final Allocation info = nomad.getAllocationsApi().info(alloc.getId()).getValue();
        final Node node = nomad.getNodesApi().info(info.getNodeId()).getValue();
        if (!node.getMeta().containsKey("local_ip")) {
          logger.warn("Failed to find local IP of node: " + node.getId());
          continue;
        }
        final Job job;
        try {
          job = nomad.getJobsApi().info(info.getJobId()).getValue();
        } catch (Exception ignored) {
          continue; // nomad info desync, ignore till next GC :/
        }
        final Map<String, String> meta = job.getMeta();
        if (meta == null || meta.isEmpty() || job.getStatus().equalsIgnoreCase("dead")) {
          continue;
        }
        final List<NetworkResource> networks = info.getResources().getNetworks();
        if (networks.isEmpty()) {
          continue;
        }
        final NetworkResource res = networks.get(0);
        if (res == null || res.getIp() == null || res.getDynamicPorts().isEmpty()) {
          continue;
        }

        final String ip = node.getMeta().get("local_ip"); //res.getIp();
        final int port = res.getDynamicPorts().get(0).getValue();
        final String serverId = meta.get("server-id");
        current.remove(serverId);

        if (proxy.getServer(serverId).isPresent()) {
          continue;
        }

        proxy.registerServer(new ServerInfo(serverId, new InetSocketAddress(ip, port)));
        logger.info("Registered a new Nomad server: " + serverId + " (" + ip + ":" + port + ")");
      }

      for (String missed : current) {
        proxy.unregisterServer(proxy.getServer(missed).orElseThrow().getServerInfo());
        logger.info("Removed server: " + missed + " (not found in nomad)...");
      }
    } catch (IOException | NomadException e) {
      logger.error("Failed nomad server check", e);
    }
  }
}
