package com.bongbong.cobl.gameman.velocity.utils;

import com.velocitypowered.api.event.EventManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Registrar {
  private final EventManager eventManager;
  private final Object pluginObject;

  public void registerListener(Object listener) {
    eventManager.register(pluginObject, listener);
  }
}
