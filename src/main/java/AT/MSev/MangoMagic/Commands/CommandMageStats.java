package AT.MSev.MangoMagic.Commands;

import AT.MSev.MangoMagic.Mage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMageStats implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player)commandSender;
        Mage caster = Mage.FromID(p.getUniqueId());
        //
        p.sendMessage(p.getDisplayName() + ChatColor.GOLD + "s magic stats.");
        p.sendMessage(ChatColor.GRAY + "Level: " + ChatColor.YELLOW + caster.GetLevel());
        p.sendMessage(ChatColor.GRAY + "Mana: " + ChatColor.BLUE + caster.GetMana() + " / " + caster.GetMaxMana());
        p.sendMessage(ChatColor.GRAY + "Xp: " + ChatColor.WHITE + caster.GetXP() + " / " + caster.GetMaxXP());
        //
        return true;
    }
}
