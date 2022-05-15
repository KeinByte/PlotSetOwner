package dev.cubemedia.setowner.command;

import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import dev.cubemedia.setowner.SetOwnerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetPlotOwnerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;
        Configuration configuration = SetOwnerPlugin.getInstance().getConfig();
        PlotPlayer<Player> plotPlayer = PlotPlayer.from(player);
        Plot plot = plotPlayer.getCurrentPlot();

        if (!player.hasPermission(configuration.getString("permission.use"))){
            player.sendMessage(configuration.getString("message.noRights"));
            return true;
        }

        if (strings.length != 1){
            player.sendMessage(configuration.getString("message.usage"));
            return true;
        }

        if (plot == null){
            player.sendMessage(configuration.getString("message.noPlot"));
            return true;
        }

        Player target = Bukkit.getPlayer(strings[0]);

        if (plot != null && plot.isOwner(player.getUniqueId()) || player.hasPermission("permission.admin")){
            if (target != null){
                plot.setOwner(target.getUniqueId());
                player.sendMessage(configuration.getString("message.success.player").replace("{0}", target.getName()));
                target.sendMessage(configuration.getString("message.success.target").replace("{0}", player.getName()).replace("{1}", plot.getId().toString()));
            }else{
                player.sendMessage(configuration.getString("message.targetOffline").replace("{0}", target.getName()));
            }
        }else{
            player.sendMessage(configuration.getString("message.error"));
        }

        return false;
    }
}
