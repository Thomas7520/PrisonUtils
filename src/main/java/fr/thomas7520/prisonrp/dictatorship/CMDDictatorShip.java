package fr.thomas7520.prisonrp.dictatorship;

import fr.thomas7520.prisonrp.utils.DateUtil;
import fr.thomas7520.prisonrp.PrisonRP;
import fr.thomas7520.prisonrp.utils.PrisonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.RED;

public class CMDDictatorShip implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(PrisonRP.getPrisonUtils().getPrefix() + "Commande réservée uniquement aux joueurs.");
            return true;
        }

        PrisonUtils utils = PrisonRP.getPrisonUtils();

        Player player = (Player) sender;

        if(args.length > 0 && args[0].equalsIgnoreCase("stop") && player.hasPermission("admin.*")) {
            if(!utils.getDictatorHelper().getDictatorStatus()) {
                player.sendMessage(utils.getPrefix() + RED + "Il n'y a pas de dictature active.");
                return true;
            }
            player.sendMessage(utils.getPrefix() + RED + "Vous venez de retirer la dictature !");
            utils.getDictatorHelper().disableDictator(true);
            return true;
        }

        if(utils.getDictatorHelper().getDictatorStatus()) {
            player.sendMessage(utils.getPrefix() + RED + "Une dictature est déjà lancée, attendez la fin de celle-ci avant d'en lancer une nouvelle !");
            return true;
        }

        if(utils.getUnixTime() < utils.getDictatorHelper().getCooldown()) {
            player.sendMessage(utils.getPrefix() + String.format(RED + "Vous devez attendre %s avant de pouvoir lancer à nouveau une dictature.", DateUtil.formatDateDiff(utils.getDictatorHelper().getCooldown())));
            return true;
        }

        if(utils.getEconomy().getBalance(player) < 450) {
            player.sendMessage(utils.getPrefix() + RED + "Vous devez avoir 450€ afin de pouvoir lancer un couvre-feu !");
            return true;
        }

        utils.getDictatorHelper().enabledDictator(player);



        return true;
    }
}
