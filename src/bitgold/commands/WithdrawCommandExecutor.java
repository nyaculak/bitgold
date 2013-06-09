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

public class WithdrawCommandExecutor implements CommandExecutor {
	
	private BitGold plugin;
	
	public WithdrawCommandExecutor(BitGold plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command");
			return false;
		}
		if (args.length != 1) {
			sender.sendMessage("Must provide one integer argument to withdraw money");
			return false;
		}
					
		try {
			PlayerInventory inventory = ((Player) sender).getInventory();
			int numNuggets = Integer.parseInt(args[0]);
			if(!(numNuggets > 0)) {
				sender.sendMessage("Please provide a positive integer to withdraw");
				return false;
			}
			int maxWithdrawal = 0;
			for(ItemStack item : inventory.getContents()) {
			    if(item == null)
			    	maxWithdrawal += 64;
			    else if (item.getType() == Material.GOLD_NUGGET)
			    	maxWithdrawal += (64 - item.getAmount()); // NOT WORKING!!!!
			}
			if(numNuggets > maxWithdrawal) {
				sender.sendMessage("You do not have room in your inventory to withdraw " +
						numNuggets + " nuggets");
				return false;
			}
			//sender.sendMessage("" + maxWithdrawal);
			int oldNumNuggets = plugin.getMetadataInt((Player) sender, BitGold.BALANCE_KEY);
			int finalNumNuggets = oldNumNuggets - numNuggets;
			if (finalNumNuggets < 0) {
				sender.sendMessage("You do not have enough nuggets in your account to withdraw that many");
				return false;
			}
			inventory.addItem(new ItemStack(Material.GOLD_NUGGET, numNuggets));
			plugin.setMetadata((Player) sender, BitGold.BALANCE_KEY, finalNumNuggets);
			sender.sendMessage(ChatColor.GREEN
					+ "Your current balance is now " + ChatColor.GOLD
					+ finalNumNuggets + " Gold Nuggets");
		} catch (NumberFormatException e) {
			sender.sendMessage("Please provide an integer value to withdraw");
			return false;
		}
		
		return true;
		
	}
}
