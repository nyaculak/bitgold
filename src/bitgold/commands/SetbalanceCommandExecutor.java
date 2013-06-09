package bitgold.commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bitgold.BitGold;

public class SetbalanceCommandExecutor implements CommandExecutor {

	private BitGold plugin;

	public SetbalanceCommandExecutor(BitGold plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (args.length < 2) {
			sender.sendMessage("Too Few Arguments");
			return false;
		}

		Player player = plugin.getServer().getPlayer(args[0]);
		
		if (player == null) {
			sender.sendMessage("The specified player could not be found online");
			return false;
		}

		int newBalance = 0;

		try {
			newBalance = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			sender.sendMessage("You did not supply an integer");
			return false;
		}
		plugin.setMetadata(player, BitGold.BALANCE_KEY, newBalance);
		sender.sendMessage(ChatColor.GREEN + player.getName()  + 
				"'s balance has been set to " + ChatColor.GOLD + newBalance
				+ " Gold Nuggets");
		player.sendMessage(ChatColor.GREEN + "Your balance has been " +
				"set to " + ChatColor.GOLD + newBalance + " Gold Nuggets");
		return true;
	}

}
