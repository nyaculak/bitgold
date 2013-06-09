package bitgold.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bitgold.BitGold;

public class TransferCommandExecutor implements CommandExecutor {
	
	private BitGold plugin;
	
	public TransferCommandExecutor(BitGold plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command");
			return false;
		}
		
		if (args.length != 2) {
			sender.sendMessage("Provide an online player and an amount of money to transfer");
			return false;
		}
		
		Player targetPlayer = plugin.getServer().getPlayer(args[0]);
		
		if (targetPlayer == null) {
			sender.sendMessage("Player matching '" + args[0] + "' could not be found");
			return false;
		}
		if (targetPlayer.getName().equals(sender.getName())) {
			sender.sendMessage("Cannot transfer money to yourself");
			return false;
		}
		
		int numNuggets;
		
		try {
			numNuggets = Integer.parseInt(args[1]);
			if (numNuggets < 0) {
				sender.sendMessage("Please provide a positive integer");
				return false;
			}
		} catch (NumberFormatException e) {
			sender.sendMessage("Please provide an integer");
			return false;
		}
		
		int oldNumNuggets = plugin.getMetadataInt((Player)sender, BitGold.BALANCE_KEY);
		if (oldNumNuggets - numNuggets < 0) {
			sender.sendMessage("You do not have enough nuggets to complete the transfer!");
			return false;
		}
		int transferOldNumNuggets = plugin.getMetadataInt(targetPlayer, BitGold.BALANCE_KEY);
		int finalNumNugets = oldNumNuggets - numNuggets;
		int transferFinalNumNuggets = transferOldNumNuggets + numNuggets;
		plugin.setMetadata((Player) sender, BitGold.BALANCE_KEY, finalNumNugets);
		plugin.setMetadata((Player) sender, BitGold.BALANCE_KEY, transferFinalNumNuggets);
		sender.sendMessage(ChatColor.GREEN + "You sent " + ChatColor.GOLD + numNuggets + " Gold Nuggets"
				+ ChatColor.GREEN + " to " + ChatColor.AQUA + targetPlayer.getName());
		sender.sendMessage(ChatColor.GREEN
				+ "Your current balance is now " + ChatColor.GOLD
				+ finalNumNugets + " Gold Nuggets");
		targetPlayer.sendMessage(ChatColor.AQUA + sender.getName() + ChatColor.GREEN + " sent you "
				+ ChatColor.GOLD + numNuggets + " Gold Nuggets");
		targetPlayer.sendMessage(ChatColor.GREEN
				+ "Your current balance is now " + ChatColor.GOLD
				+ transferFinalNumNuggets + " Gold Nuggets");
		
		return true;
	}

}
