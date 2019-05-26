package AT.MSev.MangoMagic.Spells;

import AT.MSev.MangoMagic.Mage;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

import java.util.Random;

public class SpellSummonSheep extends SpellBase {
    public SpellSummonSheep(String name, double cost, int cooldown, int minlevel, int xpgain, String description, Object... param) {
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
    Random random = new Random();
    void SpellCast(Player p, Mage m)
    {
        Sheep s = (Sheep)p.getWorld().spawnEntity(p.getLocation(), EntityType.SHEEP);
        s.setColor(DyeColor.values()[random.nextInt(DyeColor.values().length)]);
    }
}
