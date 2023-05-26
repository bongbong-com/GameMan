package com.bongbong.cobl.gameman.velocity.limbo;

import com.velocitypowered.api.event.Subscribe;
import lombok.RequiredArgsConstructor;
import net.elytrium.limboapi.api.Limbo;
import net.elytrium.limboapi.api.event.LoginLimboRegisterEvent;

@RequiredArgsConstructor
public class LimboJoinHandler {
  private final Limbo limboWorld;

  @Subscribe
  public void onJoin(LoginLimboRegisterEvent event) {
    limboWorld.spawnPlayer(event.getPlayer(), new LimboSession());
  }
}
