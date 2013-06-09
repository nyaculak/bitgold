package bitgold;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import bitgold.commands.DepositCommandExecutor;
import bitgold.commands.MoneyCommandExecutor;
import bitgold.commands.SetbalanceCommandExecutor;
import bitgold.commands.TransferCommandExecutor;
import bitgold.commands.WithdrawCommandExecutor;
import bitgold.listeners.LoginMetadataListener;
import bitgold.listeners.LogoutMetadataListener;
import bitgold.listeners.NewAccountListener;
import bitgold.tasks.LoadAllTask;
import bitgold.tasks.SaveAllTask;

public class BitGold extends JavaPlugin {

	public static final String BALANCE_KEY = "BitGoldBalance";
	
	public SQLite sql;

	@Override
	public void onEnable() {

		/* Check if the data folder exists, create it if it does not */
		File dataFolder = getDataFolder();
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}

		sql = new SQLite(getLogger(), "[BitGold] ", this.getDataFolder()
				.getAbsolutePath(), "BitGold", ".sqlite");
		try {
			if (sql.open()) {
				sql.query("CREATE TABLE IF NOT EXISTS Accounts (user_name varchar(16), nuggets int)");
				sql.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		getCommand("money").setExecutor(new MoneyCommandExecutor(this));
		getCommand("deposit").setExecutor(new DepositCommandExecutor(this));
		getCommand("withdraw").setExecutor(new WithdrawCommandExecutor(this));
		getCommand("transfer").setExecutor(new TransferCommandExecutor(this));
		getCommand("setbalance").setExecutor(
				new SetbalanceCommandExecutor(this));

		getServer().getPluginManager().registerEvents(
				new NewAccountListener(this), this);
		getServer().getPluginManager().registerEvents(
				new LoginMetadataListener(this), this);
		getServer().getPluginManager().registerEvents(
				new LogoutMetadataListener(this), this);

		BukkitTask loadAll = new LoadAllTask(this).runTask(this);
		//BukkitTask saveAll = new SaveAllTask(this).runTaskTimer(this, 0,
		//		secondsToTicks(5));
	}
	
	@Override
	public void onDisable() {
		new SaveAllTask(this).run();
	}

	public long secondsToTicks(double seconds) {
		return (long) seconds * 20;
	}

	public void setMetadata(Player player, String key, Object value) {
		player.setMetadata(key, new FixedMetadataValue(this, value));
	}

	public int getMetadataInt(Player player, String key) {
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			//if (value.getOwningPlugin().getDescription().getName()
			//		.equals(this.getDescription().getName())) {
				return value.asInt();
			//}
		}
		return 0;
	}


}