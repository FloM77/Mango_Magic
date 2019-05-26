package AT.MSev.MangoMagic.Items;

import AT.MSev.MangoMagic.Mage;
import AT.MSev.MangoMagic.Spells.ISpell;
import AT.MSev.MangoMagic.Spells.SpellBase;
import AT.MSev.Mango_Core.Items.Interactable.ItemInteractable;
import AT.MSev.Mango_Core.Items.ItemBase;
import AT.MSev.Mango_Core.Utils.MangoUtils;
import AT.MSev.Mango_Core.Utils.NBTManager;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemManaCrystal extends ItemInteractable {
    public ItemManaCrystal() {
        super("ManaCrystal", Material.PRISMARINE_SHARD);
    }

    @Override
    protected Boolean OnRightClick(PlayerInteractEvent e) {
        Boolean result = super.OnRightClick(e);
        if(result)
        {
            double amount = 0;
            if((amount = ((NBTTagDouble) NBTManager.GetTag(e.getPlayer().getInventory().getItemInMainHand(), "mana")).asDouble()) != 0)
            {
                Mage caster = Mage.FromID(e.getPlayer().getUniqueId());
                if(caster.GetMana() == caster.GetMaxMana())
                {
                    e.getPlayer().sendMessage(ChatColor.RED + "Already at maximum mana");
                    return true;
                }
                if((caster.GetMana() + amount) >= caster.GetMaxMana())
                {
                    caster.SetMana(caster.GetMaxMana());
                }
                else
                {
                    caster.SetMana(caster.GetMana() + amount);
                }
                e.getPlayer().sendMessage(ChatColor.GREEN + "Restored " + amount + " Mana");
                e.getItem().setAmount(e.getItem().getAmount()-1);

                e.getPlayer().sendMessage(ChatColor.GRAY + "Mana: " + ChatColor.BLUE + caster.GetMana() + " / " + caster.GetMaxMana());
            }
        }
        return result;
    }

    static public ItemStack FromAmount(final double amount, int stacksize)
    {
        ItemStack ret = NBTManager.AddItemNBT(ItemBase.Get("ManaCrystal").Physical, "mana", new NBTTagDouble(amount) );
        MangoUtils.ItemRename(ret, ChatColor.GOLD + "Mana crystal");
        MangoUtils.ItemRelore(ret, new ArrayList<String>() {{ add("Restores " + amount + " mana"); }});
        ret.setAmount(stacksize);
        return ret;
    }
}
