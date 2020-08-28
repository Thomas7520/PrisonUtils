package fr.thomas7520.prisonrp.curfew;

import fr.thomas7520.prisonrp.utils.DateUtil;
import fr.thomas7520.prisonrp.PrisonRP;
import fr.thomas7520.prisonrp.utils.PrisonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class CMDCurfew implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(PrisonRP.getPrisonUtils().getPrefix() + "Commande réservée uniquement aux joueurs.");
            return true;
        }

        PrisonUtils utils = PrisonRP.getPrisonUtils();

        Player player = (Player) sender;

        if(args.length > 0 && args[0].equalsIgnoreCase("stop") && player.hasPermission("admin.*")) {
            if(!utils.getCurfewHelper().getCurfewStatus()) {
                player.sendMessage(utils.getPrefix() + RED + "Il n'y a pas de couvre-feu actif.");
                return true;
            }
            player.sendMessage(utils.getPrefix() + RED + "Vous venez de retirer le couvre-feu !");
            utils.getCurfewHelper().disableCurfew(true);
            return true;
        }

        if(utils.getCurfewHelper().getCurfewStatus()) {
            player.sendMessage(utils.getPrefix() + RED + "Un couvre-feu est déjà lancé, attendez la fin de celui-ci avant d'en lancer un nouveau !");
            return true;
        }

        if(utils.getUnixTime() < utils.getCurfewHelper().getCooldown()) {
            player.sendMessage(utils.getPrefix() + String.format(RED + "Vous devez attendre %s avant de pouvoir lancer à nouveau un couvre-feu.", DateUtil.formatDateDiff(utils.getCurfewHelper().getCooldown())));
            return true;
        }

        if(utils.getEconomy().getBalance(player) < 150) {
            player.sendMessage(utils.getPrefix() + RED + "Vous devez avoir 150€ afin de pouvoir lancer un couvre-feu !");
            return true;
        }

        utils.getCurfewHelper().enabledCurfew(player);



        return true;
    }
}
