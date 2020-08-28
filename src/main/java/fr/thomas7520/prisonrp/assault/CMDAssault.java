package fr.thomas7520.prisonrp.assault;

import fr.thomas7520.prisonrp.utils.DateUtil;
import fr.thomas7520.prisonrp.PrisonRP;
import fr.thomas7520.prisonrp.utils.PrisonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.RED;

public class CMDAssault implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(PrisonRP.getPrisonUtils().getPrefix() + "Commande réservée uniquement aux joueurs.");
            return true;
        }

        PrisonUtils utils = PrisonRP.getPrisonUtils();

        Player player = (Player) sender;

        if(args.length > 0 && args[0].equalsIgnoreCase("stop") && player.hasPermission("admin.*")) {
            if(!utils.getAssaultHelper().getAssaultStatus()) {
                player.sendMessage(utils.getPrefix() + RED + "Il n'y a pas d'assaut actif.");
                return true;
            }
            player.sendMessage(utils.getPrefix() + RED + "Vous venez d'annuler l'assaut !");
            utils.getAssaultHelper().disableAssault(true);
            return true;
        }

        if(utils.getAssaultHelper().getAssaultStatus()) {
            player.sendMessage(utils.getPrefix() + RED + "Un assaut est déjà en cours, attendez la fin de celui-ci avant d'en lancer un nouveau !");
            return true;
        }

        if(utils.getUnixTime() < utils.getAssaultHelper().getCooldown()) {
            player.sendMessage(utils.getPrefix() + String.format(RED + "Vous devez attendre %s avant de pouvoir lancer à nouveau un assaut.", DateUtil.formatDateDiff(utils.getAssaultHelper().getCooldown())));
            return true;
        }

        if(utils.getEconomy().getBalance(player) < 500) {
            player.sendMessage(utils.getPrefix() + RED + "Vous devez avoir 500€ afin de pouvoir lancer un assaut !");
            return true;
        }

        utils.getAssaultHelper().enabledAssault(player);



        return true;
    }
}
