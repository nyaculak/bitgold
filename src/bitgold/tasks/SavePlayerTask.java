package bitgold.tasks;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bitgold.BitGold;

public class SavePlayerTask extends BukkitRunnable {
	
	private final BitGold plugin;
	private final Player player;
	
	public SavePlayerTask(BitGold plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}
	
	@Override
	public void run() {
		while(!plugin.sql.open()) { ; }
		int balance = plugin.getMetadataInt(player, BitGold.BALANCE_KEY);
		try {
			plugin.sql.query("UPDATE Accounts SET nuggets="
					+ balance + " WHERE user_name='"
					+ player.getName() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		while(!plugin.sql.close()) { ; }
	}

}
