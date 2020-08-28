package fr.thomas7520.prisonrp;

import fr.thomas7520.prisonrp.utils.PrisonLaunch;
import fr.thomas7520.prisonrp.utils.PrisonUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class PrisonRP extends JavaPlugin {


    private static PrisonUtils prisonUtils;


    @Override
    public void onEnable() {
        prisonUtils = new PrisonUtils();
        new PrisonLaunch(this).setupPlugin();
        super.onEnable();
    }


    @Override
    public void onDisable() {
        super.onDisable();
    }


    public static PrisonUtils getPrisonUtils() {
        return prisonUtils;
    }

}
