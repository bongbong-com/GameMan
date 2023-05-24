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
import net.elytrium.limboapi.api.LimboFactory;
import org.slf4j.Logger;

@Plugin(id = "gameman", name = "GameMan", authors = {"cobl.gg"}, version = "1.0.0")
public class GameManPlugin {
  private final ProxyServer proxy;
  private final Logger logger;

  @Inject
  public GameManPlugin(ProxyServer proxy, Logger logger) {
    this.proxy = proxy;
    this.logger = logger;

    logger.info("Setting up GameMan...");
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    NomadServerManager nomad = new NomadServerManager(this, proxy, logger, System.getenv("NOMAD_API_ADDRESS"));
    logger.info("Nomad polling has started...");

    Registrar registrar = new Registrar(proxy.getEventManager(), this);

    LimboFactory limboFactory = (LimboFactory) proxy.getPluginManager().getPlugin("limboapi").flatMap(PluginContainer::getInstance).orElseThrow();
    new LimboManager(limboFactory, registrar);

    logger.info("Limbo handlers have been setup...");

    logger.info("GameMan has started successfully!");
  }
}
