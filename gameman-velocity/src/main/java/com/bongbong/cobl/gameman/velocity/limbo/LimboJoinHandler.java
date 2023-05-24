package com.bongbong.cobl.gameman.velocity.limbo;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import lombok.RequiredArgsConstructor;
import net.elytrium.limboapi.api.Limbo;

@RequiredArgsConstructor
public class LimboJoinHandler {
  private final Limbo limboWorld;

  @Subscribe
  public void onJoin(LoginEvent event) {
    limboWorld.spawnPlayer(event.getPlayer(), new LimboSession());
  }
}
