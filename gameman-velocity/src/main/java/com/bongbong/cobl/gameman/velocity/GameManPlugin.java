package com.bongbong.cobl.gameman.velocity;

import com.bongbong.cobl.gameman.velocity.limbo.LimboJoinHandler;
import com.bongbong.cobl.gameman.velocity.limbo.LimboManager;
import com.bongbong.cobl.gameman.velocity.nomad.NomadServerManager;
import com.bongbong.cobl.gameman.velocity.utils.Registrar;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;

@Plugin(id = "gameman", name = "GameMan", authors = {"cobl.gg"}, version = "1.0.0")
@Getter
public class GameManPlugin {
  private final ProxyServer proxy;
  private final Logger logger;
  private final Registrar registrar;
  private LimboManager limboManager;

  private NomadServerManager nomadServerManager;

  @Inject
  public GameManPlugin(ProxyServer proxy, Logger logger) {
    this.proxy = proxy;
    this.logger = logger;
    this.registrar = new Registrar(proxy.getEventManager(), this);

    logger.info("Setting up GameMan...");
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    this.nomadServerManager = new NomadServerManager(this, proxy, logger, System.getenv("NOMAD_API_ADDRESS"));
    logger.info("Nomad polling has started...");

    limboManager = new LimboManager(proxy.getPluginManager().getPlugin("limboapi").flatMap(PluginContainer::getInstance).orElseThrow());
    registrar.registerListener(new LimboJoinHandler(limboManager));
    logger.info("Limbo handlers have been setup...");

    logger.info("GameMan has started successfully!");
  }
}
