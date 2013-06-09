package bitgold.tasks;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bitgold.BitGold;

public class LoadAllTask extends BukkitRunnable {
	
	private final BitGold plugin;
	
	public LoadAllTask(BitGold plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		plugin.getServer().broadcastMessage("Loading BitGold accounts to Metadata");
		while(!plugin.sql.open()) { ; }
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			int balance = 0;
			try {
				balance = plugin.sql.query("SELECT nuggets FROM Accounts WHERE user_name='"
								+ player.getName() + "'").getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			plugin.setMetadata(player, BitGold.BALANCE_KEY, balance);
		}
		while(!plugin.sql.close()) { ; }
	}

}
