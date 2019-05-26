package AT.MSev.MangoMagic.Commands;

import AT.MSev.MangoMagic.Items.ItemStaff;
import AT.MSev.MangoMagic.Spells.ISpell;
import AT.MSev.MangoMagic.Spells.SpellBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandImbueStaff implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player)commandSender;

        if(strings.length != 1)
        {
            return false;
        }

        if(!p.getInventory().getItemInMainHand().getType().equals(Material.STICK))
        {
            p.sendMessage(ChatColor.RED + "You must hold a stick to use this magic!");
            return true;
        }

        ISpell spell = SpellBase.FromName(strings[0]);
        if(spell == null)
        {
            p.sendMessage(ChatColor.RED + "Spell not found!");
            return true;
        }
        ItemStack addToP = ItemStaff.FromSpellName(spell.GetName());
        p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
        p.getInventory().addItem(addToP);
        return true;
    }
}
