package AT.MSev.MangoMagic.Spells;

import AT.MSev.MangoMagic.Mage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SpellSmelt extends SpellBase {
    public SpellSmelt(String name, double cost, int cooldown, int minlevel, int xpgain, String description, Object... param)
    {
        super(name, cost, cooldown, minlevel, xpgain, description, param);
        Spells.add(this);
        Smeltables.add(new Smeltable(Material.IRON_ORE, Material.IRON_INGOT));
        Smeltables.add(new Smeltable(Material.GOLD_ORE, Material.GOLD_INGOT));
    }

    @Override
    public Boolean OnCast(Player p) {
        Boolean result = super.OnCast(p);
        Mage caster = Mage.FromID(p.getUniqueId());
        if(result)
        {
            Smeltable smelt = null;
            if(CheckLevel(caster) && (smelt = OffhandSmeltable(p)) != null && CorrectMana(caster)) {
                SpellCast(p, caster, smelt);
                ProvideXP(caster);
            } else { ResetCooldown(p); }
        }
        return result;
    }

    Smeltable OffhandSmeltable(Player p)
    {
        for (Smeltable smelt:
             Smeltables) {
            if(p.getInventory().getItemInOffHand().getType().equals(smelt.Ore))
            {return smelt;}
        }
        p.sendMessage(ChatColor.RED + "You need to have either iron or gold ore in your offhand to use this magic");
        return null;
    }

    void SpellCast(Player p, Mage m, Smeltable smelt)
    {
        p.getInventory().getItemInOffHand().setAmount(p.getInventory().getItemInOffHand().getAmount()-1);
        p.getInventory().addItem(new ItemStack(smelt.Out, (Integer) param[0]));
    }
    static ArrayList<Smeltable> Smeltables = new ArrayList<Smeltable>();
    class Smeltable
    {
        Material Ore;
        Material Out;

        public Smeltable(Material ore, Material out)
        {
            Ore = ore;
            Out = out;
        }
    }
}
