package dev.cubemedia.setowner;

import dev.cubemedia.setowner.command.SetPlotOwnerCommand;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SetOwnerPlugin extends JavaPlugin {

    @Getter private static SetOwnerPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("setplotowner").setExecutor(new SetPlotOwnerCommand());
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }
}
