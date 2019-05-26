package AT.MSev.MangoMagic.Spells;

import AT.MSev.MangoMagic.Mage;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpellDash extends SpellBase {
    public SpellDash(String name, double cost, int cooldown, int minlevel, int xpgain, String description, Object... param) {
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
        p.setVelocity(p.getVelocity().clone().multiply(0.5).add(p.getLocation().getDirection().clone().multiply(1.2)));
        p.setFallDistance(0);
    }
}
