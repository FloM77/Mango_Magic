package AT.MSev.MangoMagic.Items;

import AT.MSev.MangoMagic.Spells.ISpell;
import AT.MSev.MangoMagic.Spells.SpellBase;
import AT.MSev.Mango_Core.Items.Interactable.ItemInteractable;
import AT.MSev.Mango_Core.Items.ItemBase;
import AT.MSev.Mango_Core.Utils.MangoUtils;
import AT.MSev.Mango_Core.Utils.NBTManager;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemStaff extends ItemInteractable {
    public ItemStaff() {
        super("ItemStaff", Material.STICK);
    }

    @Override
    protected Boolean OnRightClick(PlayerInteractEvent e) {
        Boolean result = super.OnRightClick(e);
        if(result)
        {
            String spell = null;
            if((spell = NBTManager.FromNBTString((NBTTagString) NBTManager.GetTag(e.getPlayer().getInventory().getItemInMainHand(), "spell"))) != null)
            {
                SpellBase.FromName(spell).OnCast(e.getPlayer());
            }
        }
        return result;
    }

    static public ItemStack FromSpellName(String name)
    {
        ISpell ispell = null;
        for (ISpell spell:
             SpellBase.Spells) {
            if(spell.GetName().equalsIgnoreCase(name)) ispell = spell;
        }

        if(ispell == null) return null;

        ItemStack ret = NBTManager.AddItemNBT(ItemBase.Get("ItemStaff").Physical, "spell", new NBTTagString(ispell.GetName()) );
        MangoUtils.ItemRename(ret, ChatColor.GOLD + ispell.GetName() + " Staff");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Cooldown: " + ((double)ispell.GetCooldown()/1000));
        lore.add("Mana cost: " + ispell.GetCost());
        lore.add("Xp gain: " + ispell.GetXPGain());
        lore.add("Level requirement: " + ispell.GetMinLevel());
        lore.add(ChatColor.GRAY + ispell.GetDescription());
        MangoUtils.ItemRelore(ret, lore);
        return ret;
    }
}
