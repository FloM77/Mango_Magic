package AT.MSev.MangoMagic.Spells;

import org.bukkit.entity.Player;

public interface ISpell {
    double GetCost();
    String GetName();
    String GetDescription();
    int GetCooldown();
    int GetMinLevel();
    int GetXPGain();
    Boolean OnCast(Player p);
}
