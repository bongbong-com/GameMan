package com.bongbong.cobl.gameman.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TempListener {
    private final ProxyServer proxy;

    @Subscribe
    public void onServerFind(PlayerChooseInitialServerEvent event) {
        System.out.println("Test 1");
        proxy.getAllServers().stream().findAny().ifPresent(event::setInitialServer);
    }
}
