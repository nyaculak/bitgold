package bitgold.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import bitgold.BitGold;

public class DepositCommandExecutor implements CommandExecutor {

	private BitGold plugin;

	public DepositCommandExecutor(BitGold plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command");
			return false;
		}
		if (args.length == 0) {
			sender.sendMessage("Too Few Arguments");
			return false;
		} else if (args.length > 1) {
			sender.sendMessage("Too Many Arguments");
			return false;
		}

		PlayerInventory inventory = ((Player) sender).getInventory();
		
		int numNuggets;

		try {
			numNuggets = Integer.parseInt(args[0]);
			if (!(numNuggets > 0)) {
				sender.sendMessage("Argument must be a positive integer!");
				return false;
			}
			if (inventory.containsAtLeast(new ItemStack(
					Material.GOLD_NUGGET), numNuggets)) {
				inventory.removeItem(new ItemStack(Material.GOLD_NUGGET,
						numNuggets));
				int oldNumNuggets = plugin.getMetadataInt((Player) sender, BitGold.BALANCE_KEY); 
				int finalNumNuggets = oldNumNuggets + numNuggets;
				plugin.setMetadata((Player) sender, BitGold.BALANCE_KEY, finalNumNuggets);
				sender.sendMessage(ChatColor.GREEN
						+ "Your current balance is now " + ChatColor.GOLD
						+ finalNumNuggets + " Gold Nuggets");
				return true;
			} else {
				sender.sendMessage("You don't have " + numNuggets
						+ " nuggets to deposit");
				return false;
			}
		} catch (NumberFormatException e) {
			sender.sendMessage("Not a valid input: Please provide an integer");
			return false;
		}

	}

}
