package com.bongbong.cobl.gameman.velocity.limbo;

import com.velocitypowered.api.event.Subscribe;
import net.elytrium.limboapi.api.event.LoginLimboRegisterEvent;

public class LimboJoinHandler {
  private final LimboManager limboManager;

  public LimboJoinHandler(LimboManager limboManager) {
    this.limboManager = limboManager;
  }

  @Subscribe
  public void onJoin(LoginLimboRegisterEvent event) {
    event.addOnJoinCallback(() -> limboManager.getJoinLimbo().spawnPlayer(event.getPlayer(), new LimboSession()));
  }
}
