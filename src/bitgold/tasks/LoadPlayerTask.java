package bitgold.tasks;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bitgold.BitGold;

public class LoadPlayerTask extends BukkitRunnable {
	
	private final BitGold plugin;
	private final Player player;
	
	public LoadPlayerTask(BitGold plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}
	
	@Override
	public void run() {
		while(!plugin.sql.open()) { ; }
		int balance = 0;
		try {
			balance = plugin.sql
					.query("SELECT nuggets FROM Accounts WHERE user_name='"
							+ player.getName() + "'").getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		plugin.setMetadata(player, BitGold.BALANCE_KEY, balance);
		while(!plugin.sql.close()) { ; }
	}

}
