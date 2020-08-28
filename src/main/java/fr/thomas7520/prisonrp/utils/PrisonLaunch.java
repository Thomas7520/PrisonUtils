package fr.thomas7520.prisonrp.utils;

import fr.thomas7520.prisonrp.PrisonRP;
import fr.thomas7520.prisonrp.assault.AssaultHelper;
import fr.thomas7520.prisonrp.assault.CMDAssault;
import fr.thomas7520.prisonrp.curfew.CMDCurfew;
import fr.thomas7520.prisonrp.curfew.CurfewHelper;
import fr.thomas7520.prisonrp.dictatorship.CMDDictatorShip;
import fr.thomas7520.prisonrp.dictatorship.DictatorShipHelper;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.InputStreamReader;

public class PrisonLaunch {

    private PrisonRP main;
    private PrisonUtils utils;
    
    public PrisonLaunch(PrisonRP prisonRP) {
        this.main = prisonRP;
        this.utils = PrisonRP.getPrisonUtils();
    }

    public void setupPlugin() {
        utils.setPrefix("&f[&ePrisonRP&f] ");
        this.setupCommands();
        this.setupEconomy();
        this.setupPermissions();
        this.setupPrefixCommandYaml();
        this.setupClassHelper();
    }

    private void setupCommands() {
        main.getCommand("couvrefeu").setExecutor(new CMDCurfew());
        main.getCommand("dictature").setExecutor(new CMDDictatorShip());
        main.getCommand("assautrebel").setExecutor(new CMDAssault());
    }


    private boolean setupPermissions()

    {
        RegisteredServiceProvider<Permission> permissionProvider = main.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            utils.setPermission(permissionProvider.getProvider());
        }
        return (utils.getPermission() != null);
    }


    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = main.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            utils.setEconomy(economyProvider.getProvider());
        }

        return (utils.getEconomy() != null);
    }

    public void setupPrefixCommandYaml() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(main.getResource("plugin.yml")));
        config.getConfigurationSection("commands").getKeys(false).forEach(String -> main.getCommand(String).setPermissionMessage(utils.getPrefix() + ChatColor.RED + "Vous n'avez pas la permission !"));
        config.getConfigurationSection("commands").getKeys(false).forEach(String -> main.getCommand(String).setUsage(utils.getPrefix() + ChatColor.RED + "Review your arguments counts ! (" + main.getCommand(String).getUsage() + ")"));
    }

    private void setupClassHelper() {
        utils.setCurfewHelper(new CurfewHelper());
        utils.setDictatorHelper(new DictatorShipHelper());
        utils.setAssaultHelper(new AssaultHelper());
    }
}
