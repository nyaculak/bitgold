package bitgold.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bitgold.BitGold;

public class MoneyCommandExecutor implements CommandExecutor {

	private BitGold plugin;

	public MoneyCommandExecutor(BitGold plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command");
			return false;
		}
		
		int balance = plugin.getMetadataInt((Player) sender, BitGold.BALANCE_KEY);
		
		sender.sendMessage(ChatColor.GREEN
				+ "Your current balance is " + ChatColor.GOLD
				+ balance + " Gold Nuggets");
		
		return true;

	}

}
