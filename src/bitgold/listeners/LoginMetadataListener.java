package bitgold.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitTask;

import bitgold.BitGold;
import bitgold.tasks.LoadPlayerTask;

public class LoginMetadataListener implements Listener {
	
	private BitGold plugin;
	
	public LoginMetadataListener(BitGold plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		BukkitTask loadPlayer = new LoadPlayerTask(plugin, event.getPlayer()).runTask(plugin);
	}

}
