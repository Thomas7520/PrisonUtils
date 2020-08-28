package fr.thomas7520.prisonrp.dictatorship;

import fr.thomas7520.prisonrp.PrisonRP;
import fr.thomas7520.prisonrp.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class DictatorShipHelper {
    private boolean dictator;
    private long cooldown;
    private PrisonUtils utils;

    public DictatorShipHelper() {
        this.utils = PrisonRP.getPrisonUtils();
    }

    public void enabledDictator(Player player) {
        dictator = true;

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&4------------------[&a&lDictature&4]------------------ \n " +
                "\n" +
                "     &cLe directeur commence une &4&lDictature!" +
                "\n" +
                "&4------------------[&a&lDictature&4]------------------"));

        Bukkit.getServer().getWorld("world").getBlockAt(-825, 116, -638).setType(Material.REDSTONE_BLOCK);

        Bukkit.getScheduler().runTaskLater(PrisonRP.getPlugin(PrisonRP.class), () -> {
            utils.getEconomy().withdrawPlayer(player, 450);
            player.sendMessage(utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&e450 §cvous ont été retiré car vous avez lancé une dictature."));
        },20 * 5);

        Bukkit.getScheduler().runTaskLater(PrisonRP.getPlugin(PrisonRP.class), () -> {
            disableDictator(false);
        }, 20 * 60 * 15);

    }


    public void disableDictator(boolean forced) {
        if(!getDictatorStatus()) return;

        setDictator(false);
        Bukkit.getServer().getWorld("world").getBlockAt(-825, 116, -638).setType(Material.AIR);

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4------------------[&a&lDictature&4]------------------ \n " +
                "\n" +
                "                   &2La Dictature prend fin!" +
                "\n" +
                "&4------------------[&a&lDictature&4]------------------"));
        if(!forced) utils.getCurfewHelper().setCooldown(utils.getUnixTime() + TimeUnit.MINUTES.toMillis(15));
    }

    public void setDictator(boolean curefew) {
        this.dictator = curefew;
    }

    public boolean getDictatorStatus() {
        return dictator;
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }
}
