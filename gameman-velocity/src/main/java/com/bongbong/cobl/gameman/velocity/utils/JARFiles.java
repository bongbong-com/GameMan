package com.bongbong.cobl.gameman.velocity.utils;

import com.bongbong.cobl.gameman.velocity.GameManPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class JARFiles {
  public static InputStream getResource(@NotNull String filename) {
    try {
      URL url = GameManPlugin.class.getClassLoader().getResource(filename);

      if (url == null) {
        return null;
      }

      URLConnection connection = url.openConnection();
      connection.setUseCaches(false);
      return connection.getInputStream();
    } catch (IOException ex) {
      return null;
    }
  }
}
