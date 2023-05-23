package com.bongbong.cobl.gameman.velocity.limbo;

import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import net.elytrium.limboapi.api.Limbo;
import net.elytrium.limboapi.api.LimboFactory;
import net.elytrium.limboapi.api.chunk.Dimension;
import net.elytrium.limboapi.api.chunk.VirtualWorld;
import net.elytrium.limboapi.api.file.BuiltInWorldFileType;
import net.elytrium.limboapi.api.player.GameMode;

import java.io.IOException;

public class LimboManager {
  private final LimboFactory factory;
  private Limbo joinLimbo;
  private VirtualWorld joinWorld;

  public LimboManager(ProxyServer server) {
    this.factory = (LimboFactory) server.getPluginManager().getPlugin("limboapi").flatMap(PluginContainer::getInstance).orElseThrow();

    this.joinWorld = this.factory.createVirtualWorld(Dimension.OVERWORLD, 0, 0, 0, 0, 0);
    try {
      this.factory.openWorldFile(BuiltInWorldFileType.SCHEMATIC, getClass().getResourceAsStream("lobby.schem")).toWorld(factory, joinWorld, 0, 0, 0);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    this.joinLimbo = this.factory.createLimbo(joinWorld).setName("cobl.gg").setGameMode(GameMode.ADVENTURE);
  }

  public LimboFactory getFactory() {
    return factory;
  }

  public Limbo getJoinLimbo() {
    return joinLimbo;
  }
}
