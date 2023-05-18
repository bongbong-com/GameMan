package com.bongbong.cobl.gameman.velocity;

import com.bongbong.cobl.gameman.velocity.nomad.NomadServerManager;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "gameman", name = "GameMan", authors = {"cobl.gg"})
public class GameManPlugin {
  private final ProxyServer proxy;
  private final Logger logger;

  private NomadServerManager nomadServerManager;

  @Inject
  public GameManPlugin(ProxyServer proxy, Logger logger) {
    this.proxy = proxy;
    this.logger = logger;

    logger.info("Setting up GameMan...");
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    this.nomadServerManager = new NomadServerManager(proxy, logger, System.getenv("NOMAD_API_ADDRESS"));
    logger.info("Nomad polling has started...");

    logger.info("GameMan has started successfully!");
  }

  public NomadServerManager getNomadServerManager() {
    return nomadServerManager;
  }

  public ProxyServer getProxy() {
    return proxy;
  }

  public Logger getLogger() {
    return logger;
  }
}
