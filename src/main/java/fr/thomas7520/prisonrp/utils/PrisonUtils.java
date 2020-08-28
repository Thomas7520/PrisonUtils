package fr.thomas7520.prisonrp.utils;

import fr.thomas7520.prisonrp.assault.AssaultHelper;
import fr.thomas7520.prisonrp.curfew.CurfewHelper;
import fr.thomas7520.prisonrp.dictatorship.DictatorShipHelper;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;

import java.util.Date;

public class PrisonUtils {

    private String prefix;
    private Economy economy;
    private Permission permission;
    private CurfewHelper curfewHelper;
    private DictatorShipHelper dictatorHelper;
    private AssaultHelper assaultHelper;

    public Economy getEconomy() {
        return economy;
    }

    public void setEconomy(Economy economy) {
        this.economy = economy;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', prefix);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public CurfewHelper getCurfewHelper() {
        return curfewHelper;
    }

    public void setCurfewHelper(CurfewHelper curfewHelper) {
        this.curfewHelper = curfewHelper;
    }

    public DictatorShipHelper getDictatorHelper() {
        return dictatorHelper;
    }

    public void setDictatorHelper(DictatorShipHelper dictatorHelper) {
        this.dictatorHelper = dictatorHelper;
    }

    public long getUnixTime() {
        return new Date().getTime();
    }

    public AssaultHelper getAssaultHelper() {
        return assaultHelper;
    }

    public void setAssaultHelper(AssaultHelper assaultHelper) {
        this.assaultHelper = assaultHelper;
    }
}
