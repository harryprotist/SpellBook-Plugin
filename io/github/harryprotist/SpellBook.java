package io.github.harryprotist;

import io.github.harryprotist.*;
import io.github.harryprotist.spellfunction.*;
import io.github.harryprotist.Util;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldSaveEvent;

import java.util.*;
import java.io.*;

public final class SpellBook extends JavaPlugin implements Listener {

	public void onEnable () {
		getLogger().info("Started SpellBook");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
  /*
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
	}
	@EventHandler
	public void onWorldSave(WorldSaveEvent event) {
	}
  */

	public void onDisable () {
		getLogger().info("Stopping SpellBook");
	}

	public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args) {
		String cmdName = cmd.getName();

		if (cmdName.equalsIgnoreCase("setmana") &&
    sender instanceof Player &&
    sender.hasPermission("spellbook.setmana")
    ) {
			
			Integer m = new Integer(0);
      try {
        m = new Integer(args[0]);	
        setMeta((Player)sender, "mana", m);
        sender.sendMessage("Set mana to " + m.toString());
      } catch (Exception e) {
        getLogger().info(e.toString());
        sender.sendMessage("Invalid Format");
        return true;
      }

		} else if (cmdName.equals("cast") &&
    sender instanceof Player &&
    sender.hasPermission("spellbook.cast")
    ) {

      Player player = (Player)sender;
      ItemStack item = player.getItemInHand();
      
      if (item.getAmount() == 1 &&
      (item.getType() == Material.BOOK_AND_QUILL ||
      item.getType() == Material.WRITTEN_BOOK)
      ) {
        try { 
          Book.parse(
            Util.join("", (((BookMeta)item.getItemMeta()).getPages()))
          ).run(
            new SpellContext(this, player)
          );
        } catch (Exception e) {
          player.sendMessage("Error: " + e.getMessage());
        }
      }
    }
    return false;
	}

	public void setMeta(Player p, String k, Object v) {
		p.setMetadata(k, new FixedMetadataValue(this, v) );
	}

	public Object getMeta(Player p, String k) {
	 	List<MetadataValue> vs = p.getMetadata(k);
		for (MetadataValue v : vs) {
			if (v.getOwningPlugin().
      getDescription().
      getName().
      equals(this.getDescription().
      getName())
      ) {
				return v.value();	
			}
		}
		return null;
	}

}
