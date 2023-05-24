package com.bongbong.cobl.gameman.velocity.limbo;

import lombok.Getter;
import net.elytrium.limboapi.api.Limbo;
import net.elytrium.limboapi.api.LimboFactory;
import net.elytrium.limboapi.api.chunk.Dimension;
import net.elytrium.limboapi.api.chunk.VirtualWorld;
import net.elytrium.limboapi.api.file.BuiltInWorldFileType;
import net.elytrium.limboapi.api.player.GameMode;

import java.io.IOException;

@Getter
public class LimboManager {
  private final LimboFactory factory;
  private final Limbo joinLimbo;

  public LimboManager(Object limboPlugin) {
    this.factory = (LimboFactory) limboPlugin;

    final VirtualWorld joinWorld = this.factory.createVirtualWorld(Dimension.OVERWORLD, 0, 0, 0, 0, 0);
    try {
      this.factory.openWorldFile(BuiltInWorldFileType.SCHEMATIC, getClass().getResourceAsStream("lobby.schem")).toWorld(factory, joinWorld, 0, 0, 0);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    this.joinLimbo = this.factory.createLimbo(joinWorld).setName("cobl.gg").setGameMode(GameMode.ADVENTURE);
  }
}
