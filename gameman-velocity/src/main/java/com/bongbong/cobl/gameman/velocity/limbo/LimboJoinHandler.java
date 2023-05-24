package com.bongbong.cobl.gameman.velocity.limbo;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.RequiredArgsConstructor;
import net.elytrium.limboapi.api.Limbo;

@RequiredArgsConstructor
public class LimboJoinHandler {
  private final Limbo limboWorld;
  private final ProxyServer proxy;

  @Subscribe
  public void onJoin(LoginEvent event) {
    limboWorld.spawnPlayer(event.getPlayer(), new LimboSession());
  }

  @Subscribe
  public void onServerFind(PlayerChooseInitialServerEvent event) {
    System.out.println("Test 1");
    proxy.getAllServers().stream().findAny().ifPresent(event::setInitialServer);
  }
}
