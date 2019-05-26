package AT.MSev.MangoMagic.Spells;

import AT.MSev.MangoMagic.Items.ItemStaff;
import AT.MSev.MangoMagic.*;
import org.bukkit.DyeColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SpellContainer extends SpellBase {
    public SpellContainer(String name, double cost, int cooldown, int minlevel, int xpgain, String description, Object... param) {
        super(name, cost, cooldown, minlevel, xpgain, description, param);
        Spells.add(this);
    }

    @Override
    public Boolean OnCast(Player p) {
        Boolean result = super.OnCast(p);
        Mage caster = Mage.FromID(p.getUniqueId());
        if(result)
        {
            if(CheckLevel(caster) && CorrectMana(caster)) {
                SpellCast(p, caster);
                ProvideXP(caster);
            } else { ResetCooldown(p); }
        }
        return result;
    }

    void SpellCast(Player p, Mage m)
    {
        PrivateContainer pc = PrivateContainer.ForPlayer(p);
        if(pc == null)
        {
            pc = new PrivateContainer(p);
        }
        pc.OpenInventory(p);
    }
}

