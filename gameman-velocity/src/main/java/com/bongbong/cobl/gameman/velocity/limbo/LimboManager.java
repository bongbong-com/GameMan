package com.bongbong.cobl.gameman.velocity.limbo;

import com.bongbong.cobl.gameman.velocity.utils.JARFiles;
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
    final VirtualWorld joinWorld = limboFactory.createVirtualWorld(Dimension.OVERWORLD, 2, 91, 1, 180, 0);

    try {
      limboFactory.openWorldFile(BuiltInWorldFileType.STRUCTURE, JARFiles.getResource("world.nbt")).toWorld(limboFactory, joinWorld, 0, 90, 0, 15);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    final Limbo joinLimbo = limboFactory.createLimbo(joinWorld).setName("cobl.gg").setWorldTime(1000L).setGameMode(GameMode.ADVENTURE);

    registrar.registerListener(new LimboJoinHandler(joinLimbo));
  }
}
