package com.dusterthefirst.storageplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_13_R1.NBTTagCompound;

public class EventListeners implements Listener {
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		Player player = event.getPlayer();
		
		ItemStack item = event.getItem();
		net.minecraft.server.v1_13_R1.ItemStack mnsitem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemcompound = (mnsitem.hasTag()) ? mnsitem.getTag() : new NBTTagCompound();
		
		if (itemcompound.getInt("isPouch") == 1) {
			event.setCancelled(true);
			
			int slots = itemcompound.getInt("slots");
			
			if (slots % 9 == 0) {
		        Inventory inv = Bukkit.createInventory(null, slots, item.getItemMeta().getDisplayName());
		        inv.addItem(item);
		        player.openInventory(inv);
			} else if (slots == 5) {
				Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, item.getItemMeta().getDisplayName());
		        inv.addItem(item);
		        player.openInventory(inv);
			} else {
				Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "uwu whats this?");
				
				ItemStack curseddirt = new ItemStack(Material.DIRT, 64);
				
				ItemMeta curseddirtmeta = curseddirt.getItemMeta();
				curseddirtmeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.MAGIC + "CURSED DIRT");
				curseddirt.setItemMeta(curseddirtmeta);
				
				net.minecraft.server.v1_13_R1.ItemStack mnscurseddirt = CraftItemStack.asNMSCopy(curseddirt);
				NBTTagCompound curseddirtcompound = (mnscurseddirt.hasTag()) ? mnscurseddirt.getTag() : new NBTTagCompound();
				curseddirtcompound.setString("cursed", "very");
				mnscurseddirt.setTag(curseddirtcompound);
				
				curseddirt = CraftItemStack.asBukkitCopy(mnscurseddirt);
				
		        inv.addItem(curseddirt, curseddirt, curseddirt, curseddirt, curseddirt);
		        player.openInventory(inv);
			}
		}
	}
	
}
