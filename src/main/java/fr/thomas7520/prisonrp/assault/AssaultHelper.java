package fr.thomas7520.prisonrp.assault;

import fr.thomas7520.prisonrp.PrisonRP;
import fr.thomas7520.prisonrp.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class AssaultHelper {

    private boolean assault;
    private long cooldown;
    private PrisonUtils utils;
    public AssaultHelper() {
        this.utils = PrisonRP.getPrisonUtils();
    }

    public void enabledAssault(Player player) {
        assault = true;

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&4------------------[&a&lLa B.R.I&4]------------------ \n " +
                "\n" +
                "&cLe directeur vient de lancer un assault camp rebelle\n" +
                "&cLes miradors, infiltré, et haut gradés allez assault le camp rebel\n" +
                "&cL'assault dure 6 minutes ( + de temps = peines de ban )\n" +
                "\n" +
                "&4------------------[&a&lLa B.R.I&4]------------------"));

        Bukkit.getServer().getWorld("world").getBlockAt(-825, 116, -638).setType(Material.REDSTONE_BLOCK);

        Bukkit.getScheduler().runTaskLater(PrisonRP.getPlugin(PrisonRP.class), () -> {
            utils.getEconomy().withdrawPlayer(player, 500);
            player.sendMessage(utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&e500€ §cvous ont été retiré car vous avez lancé l'assaut."));
        },20 * 5);

        Bukkit.getScheduler().runTaskLater(PrisonRP.getPlugin(PrisonRP.class), () -> {
            disableAssault(false);
        }, 20 * 60 * 6);

    }


    public void disableAssault(boolean forced) {
        if(!getAssaultStatus()) return;

        setAssault(false);
        Bukkit.getServer().getWorld("world").getBlockAt(-825, 116, -638).setType(Material.AIR);

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4------------------[&a&lLa B.R.I&4]------------------ \n " +
                "\n" +
                "                   &2Assault camp rebel finis !" +
                "\n" +
                "&4------------------[&a&lLa B.R.I&4]------------------"));
        if(!forced) utils.getAssaultHelper().setCooldown(utils.getUnixTime() + TimeUnit.MINUTES.toMillis(6));
    }

    public void setAssault(boolean assault) {
        this.assault = assault;
    }

    public boolean getAssaultStatus() {
        return assault;
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }
}
