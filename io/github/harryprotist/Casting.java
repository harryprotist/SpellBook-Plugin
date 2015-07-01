package io.github.harryprotist;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import io.github.harryprotist.*;
import io.github.harryprotist.spellfunction.*;

import java.util.*;
import java.lang.reflect.*;

public class Casting implements Listener {

  private SpellBook plugin;

  public Casting(SpellBook plugin) {
    this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  public void activateSpell(Player player) {

    ItemStack item = player.getItemInHand();
    
    if (item.getAmount() == 1 &&
    (item.getType() == Material.BOOK_AND_QUILL ||
    item.getType() == Material.WRITTEN_BOOK)
    ) {

      String pages = Util.join("",((BookMeta)item.getItemMeta()).getPages());
      String code = pages.replaceAll("^\\[Spell Book\\]", "");
      if (pages.equals(code)) return;

      try {
        int remaining = Book.parse(code).run(new SpellContext(plugin, player)).con.mana;
        plugin.setMeta(player, "mana",
          new Integer(remaining)
        );
      } catch (Exception e) {
        player.sendMessage("Parse Error: " + e.getMessage());
        //e.printStackTrace(System.out);
      }
    }
  }

  @EventHandler
  public void onPlayerInteractEvent(PlayerInteractEvent event) {
    Action a = event.getAction();
    if (a == Action.LEFT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR) {
      activateSpell(event.getPlayer()); 
    }
  }  
}
