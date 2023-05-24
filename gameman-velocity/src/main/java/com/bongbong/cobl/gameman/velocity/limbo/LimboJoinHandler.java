package com.bongbong.cobl.gameman.velocity.limbo;

import com.velocitypowered.api.event.Subscribe;
import lombok.RequiredArgsConstructor;
import net.elytrium.limboapi.api.event.LoginLimboRegisterEvent;

@RequiredArgsConstructor
public class LimboJoinHandler {
  private final LimboManager limboManager;

  @Subscribe
  public void onJoin(LoginLimboRegisterEvent event) {
    event.addOnJoinCallback(() -> limboManager.getJoinLimbo().spawnPlayer(event.getPlayer(), new LimboSession()));
  }
}
