package bitgold.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import bitgold.BitGold;

public class NewAccountListener implements Listener {

	private BitGold plugin;

	public NewAccountListener(BitGold plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (plugin.sql.open()) {
			String user_name = event.getPlayer().getName();
			try {
				event.getPlayer().sendMessage(event.getPlayer().getName());
				ResultSet results = plugin.sql
						.query("SELECT user_name FROM Accounts WHERE user_name='"
								+ user_name + "'");
				if (!results.next()) {
					plugin.sql.query("INSERT INTO Accounts VALUES ('"
							+ user_name + "', 0)");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			plugin.sql.close();
		}
	}

}
