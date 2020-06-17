package com.dusterthefirst.storageplus;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_13_R1.NBTTagCompound;
import net.minecraft.server.v1_13_R1.NBTTagInt;
import net.minecraft.server.v1_13_R1.NBTTagString;

public class Plugin extends JavaPlugin {
	ConsoleCommandSender console = getServer().getConsoleSender();
	DatabaseHandler db;

	@Override
	public void onEnable() {
		 getServer().getPluginManager().registerEvents(new EventListeners(), this);
		
		sendMessage(ChatColor.YELLOW + "Connecting to MongoDB");
		try {
			this.db = new DatabaseHandler();
			sendMessage(ChatColor.GREEN + "Connected to MongoDB");
		} catch (Exception e) {
			sendMessage(ChatColor.RED + "Cannot connect to MongoDB on localhost:27017");
			this.getPluginLoader().disablePlugin(this);
		}
		
		getServer().addRecipe(
				new ShapedRecipe(new NamespacedKey(this, "pouch-1"), createPouch(-1))
					.shape(	"DDD",
							"DCD",
							"DDD")
					.setIngredient('C', Material.CHEST)
					.setIngredient('D', Material.DIRT));
		
		getServer().addRecipe(
				new ShapedRecipe(new NamespacedKey(this, "pouch0"), createPouch(0))
					.shape(	"WCW",
							"CBC",
							"WCW")
					.setIngredient('C', Material.CHEST)
					.setIngredient('B', Material.BUCKET)
					.setIngredient('W', Material.OAK_PLANKS));
		
		getServer().addRecipe(
				new ShapedRecipe(new NamespacedKey(this, "pouch1"), createPouch(1))
					.shape(	"ICI",
							"CBC",
							"ICI")
					.setIngredient('C', Material.CHEST)
					.setIngredient('B', Material.BUCKET)
					.setIngredient('I', Material.IRON_INGOT));
		
		getServer().addRecipe(
				new ShapedRecipe(new NamespacedKey(this, "pouch2"), createPouch(2))
					.shape(	"GCG",
							"CBC",
							"GCG")
					.setIngredient('C', Material.CHEST)
					.setIngredient('B', Material.BUCKET)
					.setIngredient('G', Material.GOLD_INGOT));
		
		getServer().addRecipe(
				new ShapedRecipe(new NamespacedKey(this, "pouch3"), createPouch(3))
					.shape(	"DCD",
							"CBC",
							"DCD")
					.setIngredient('C', Material.CHEST)
					.setIngredient('B', Material.BUCKET)
					.setIngredient('D', Material.DIAMOND));
		
		getServer().addRecipe(
				new ShapedRecipe(new NamespacedKey(this, "pouch4"), createPouch(4))
					.shape(	"DGD",
							"GCG",
							"DGD")
					.setIngredient('C', Material.CHEST)
					.setIngredient('G', Material.GOLD_INGOT)
					.setIngredient('D', Material.DIAMOND));
		
		getServer().addRecipe(
				new ShapedRecipe(new NamespacedKey(this, "pouch5"), createPouch(5))
					.shape(	"ODO",
							"DED",
							"ODO")
					.setIngredient('E', Material.ENDER_PEARL)
					.setIngredient('O', Material.OBSIDIAN)
					.setIngredient('D', Material.DIAMOND));
		
		getServer().addRecipe(
				new ShapedRecipe(new NamespacedKey(this, "pouch6"), createPouch(6))
					.shape(	"ODO",
							"DED",
							"ODO")
					.setIngredient('E', Material.ENDER_EYE)
					.setIngredient('O', Material.OBSIDIAN)
					.setIngredient('D', Material.DIAMOND));
		
		getServer().addRecipe(
				new ShapedRecipe(new NamespacedKey(this, "pouch12"), createPouch(12))
					.shape(	"EDE",
							"DED",
							"EDE")
					.setIngredient('E', Material.ENDER_CHEST)
					.setIngredient('D', Material.DIAMOND));
	}
	
	private ItemStack createPouch(int level) {
		int slots;
		if (level == 0) {
			slots = 5;
		} else if (level == -1) {
			slots = 1;
		} else {
			slots = level * 9;
		}
		
		ItemStack chest = new ItemStack(level >= 5 ? Material.ENDER_CHEST : Material.CHEST);
		ItemMeta chestMeta = chest.getItemMeta();
		
		chestMeta.setDisplayName(ChatColor.DARK_AQUA + (level == -1 ? ChatColor.MAGIC + "" : "") + ChatColor.ITALIC + "" + ChatColor.BOLD + "Level " + level + " Pouch");
		chestMeta.setLore(Arrays.asList(ChatColor.RESET + "" + (level == -1 ? ChatColor.MAGIC + "" : "") + slots + " Slots"));
		chest.setItemMeta(chestMeta);
		
		net.minecraft.server.v1_13_R1.ItemStack mnschest = CraftItemStack.asNMSCopy(chest);
		NBTTagCompound chestcompound = (mnschest.hasTag()) ? mnschest.getTag() : new NBTTagCompound();
		chestcompound.set("slots", new NBTTagInt(slots));
		chestcompound.set("level", new NBTTagInt(level));
		chestcompound.set("isPouch", new NBTTagInt(1));
		chestcompound.set("UUID", new NBTTagString(UUID.randomUUID().toString().replaceAll("-", "")));
		mnschest.setTag(chestcompound);
		chest = CraftItemStack.asBukkitCopy(mnschest);
		
		return chest;
	}
	public void sendMessage(String message) {
		console.sendMessage("[StoragePlus] " + message);
	}
}
