package bitgold.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import bitgold.BitGold;
import bitgold.tasks.SavePlayerTask;

public class LogoutMetadataListener implements Listener {
	
	private BitGold plugin;
	
	public LogoutMetadataListener(BitGold plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		BukkitTask savePlayer = new SavePlayerTask(plugin, event.getPlayer()).runTask(plugin);
	}

}
