package io.github.harryprotist.spellfunction;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SpellContext {

  public Player player; 
  public Plugin plugin;

  public SpellContext(Plugin plugin, Player player) {
    this.plugin = plugin;
    this.player = player;
  }
}
