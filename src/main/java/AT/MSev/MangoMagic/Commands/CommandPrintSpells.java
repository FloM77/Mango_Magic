package AT.MSev.MangoMagic.Commands;

import AT.MSev.MangoMagic.Spells.ISpell;
import AT.MSev.MangoMagic.Spells.SpellBase;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPrintSpells implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player)commandSender;
        int page = 0;
        int spellsPerPage = 7;
        if(strings.length>0)
        {
            try{
                page = Integer.parseInt(strings[0]) - 1;
            } catch (Exception ex) { p.sendMessage("Couldn't read page number"); }
        }
        if(page<0) return true;
        p.sendMessage(ChatColor.GOLD + "List of Mango Spells. Hover for spell info");
        for (int i = page * spellsPerPage; i < (page * spellsPerPage) + spellsPerPage; i++)
        {
            if(i>=SpellBase.Spells.size()) break;
            ISpell ispell = (ISpell) SpellBase.Spells.toArray()[i];
            if(ispell!=null)
            {
                TextComponent tc = new TextComponent(i + " : " + ChatColor.LIGHT_PURPLE + ispell.GetName());
                tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(ChatColor.GOLD + ispell.GetName() + ChatColor.GRAY +
                                "\nCooldown: " + ((double)ispell.GetCooldown()/1000) +
                                "\nMana cost: " +  ispell.GetCost() +
                                "\nXp gain: " + ispell.GetXPGain() +
                                "\nLevel requirement: " + ispell.GetMinLevel() +
                                "\n" + ispell.GetDescription()).create()));
                p.spigot().sendMessage(tc);
            }
        }
        p.sendMessage(ChatColor.GOLD + "Page" + (page + 1) + " out of " + (((SpellBase.Spells.size()-1) / spellsPerPage) + 1));
        return true;
    }
}