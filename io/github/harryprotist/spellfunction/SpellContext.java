package io.github.harryprotist.spellfunction;

import io.github.harryprotist.spellfunction.*;
import io.github.harryprotist.*;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Stack;

public class SpellContext {

  public Player player; 
  public Plugin plugin;
  public Stack<SpellObject> stack;

  public SpellContext(Plugin plugin, Player player) {
    this(plugin, player, null);
  }
  public SpellContext(Plugin plugin, Player player, Stack<SpellObject> stack) {
    this.plugin = plugin;
    this.player = player;
    this.stack = (stack == null)? (new Stack<SpellObject>()):(stack);
  }
}
