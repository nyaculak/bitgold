package bitgold.tasks;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bitgold.BitGold;

public class SaveAllTask extends BukkitRunnable {
	
	private final BitGold plugin;
	
	public SaveAllTask(BitGold plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		//plugin.getServer().broadcastMessage("Saving BitGold accounts");
		while(!plugin.sql.open()) { ; }
		if (plugin.getServer().getOnlinePlayers().length == 0)
			return;
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			int balance = plugin.getMetadataInt(player, BitGold.BALANCE_KEY);
			try {
				plugin.sql.query("UPDATE Accounts SET nuggets="
						+ String.valueOf(balance) + " WHERE user_name='"
						+ player.getName() + "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		while(!plugin.sql.close()) { ; }
		//plugin.getServer().broadcastMessage("Done saving BitGold accounts");
	}

}
