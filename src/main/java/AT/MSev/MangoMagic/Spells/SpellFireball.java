package AT.MSev.MangoMagic.Spells;

import AT.MSev.MangoMagic.Mage;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpellFireball extends SpellBase {
    public SpellFireball(String name, double cost, int cooldown, int minlevel, int xpgain, String description, Object... param) {
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
        p.getWorld().spawnEntity(p.getLocation().clone().add(p.getLocation().getDirection().clone().multiply(2)), EntityType.FIREBALL);
    }
}
