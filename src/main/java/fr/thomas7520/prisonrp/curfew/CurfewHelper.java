package fr.thomas7520.prisonrp.curfew;

import fr.thomas7520.prisonrp.PrisonRP;
import fr.thomas7520.prisonrp.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class CurfewHelper {

    private boolean curefew;
    private long cooldown;
    private PrisonUtils utils;
    
    public CurfewHelper() {
        this.utils = PrisonRP.getPrisonUtils();
    }

    public void enabledCurfew(Player player) {
        curefew = true;

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&4------------------[&a&lCouvre feu&4]------------------ \n " +
                "\n" +
                "     &cLe directeur vient de lancer un couvre feu!\n" +
                "&cSi les prisonniers ne sont pas dans leurs cellules les \n" +
                " &cgardes et miradors ont l'autorisation de les abbatres! \n" +
                "\n" +
                "&4------------------[&a&lCouvre feu&4]------------------"));

        Bukkit.getServer().getWorld("world").getBlockAt(-825, 116, -638).setType(Material.REDSTONE_BLOCK);
        
        Bukkit.getScheduler().runTaskLater(PrisonRP.getPlugin(PrisonRP.class), () -> {
            utils.getEconomy().withdrawPlayer(player, 150);
            player.sendMessage(utils.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&e150€ §cvous ont été retiré car vous avez lancé un couvre-feu."));
        },20 * 5);

        Bukkit.getScheduler().runTaskLater(PrisonRP.getPlugin(PrisonRP.class), () -> {
           disableCurfew(false);
        }, 20 * 60 * 10);

    }


    public void disableCurfew(boolean forced) {
        if(!getCurfewStatus()) return;

        setCurfew(false);
        Bukkit.getServer().getWorld("world").getBlockAt(-825, 116, -638).setType(Material.AIR);

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4------------------[&a&lCouvre feu&4]------------------ \n " +
                "\n" +
                "                   &2Le couvre feu est terminé!" +
                "\n" +
                "&4------------------[&a&lCouvre feu&4]------------------"));
        if(!forced) utils.getCurfewHelper().setCooldown(utils.getUnixTime() + TimeUnit.MINUTES.toMillis(20));
    }

    public void setCurfew(boolean curefew) {
        this.curefew = curefew;
    }

    public boolean getCurfewStatus() {
        return curefew;
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }
}
