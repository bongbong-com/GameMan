package com.bongbong.cobl.gameman.velocity.limbo;

import com.bongbong.cobl.gameman.velocity.utils.Registrar;
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

  public LimboManager(LimboFactory limboFactory, Registrar registrar) {
    VirtualWorld joinWorld = limboFactory.createVirtualWorld(Dimension.OVERWORLD, 0, 0, 0, 0, 0);

    try {
      limboFactory
              .openWorldFile(BuiltInWorldFileType.SCHEMATIC, getClass().getResourceAsStream("lobby.schem"))
              .toWorld(limboFactory, joinWorld, 0, 0, 0);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Limbo joinLimbo = limboFactory.createLimbo(joinWorld).setName("cobl.gg").setGameMode(GameMode.ADVENTURE);

    registrar.registerListener(new LimboJoinHandler(joinLimbo));
  }
}
