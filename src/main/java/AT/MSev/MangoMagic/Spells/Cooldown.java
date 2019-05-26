package AT.MSev.MangoMagic.Spells;

import AT.MSev.Mango_Core.Items.Interactable.ItemInteractable;
import AT.MSev.Mango_Core.Utils.Security;
import AT.MSev.Mango_Core.Utils.Time;
import org.bukkit.entity.Player;
import org.joda.time.Period;


public class Cooldown {
    String CooldownKey;

    public Cooldown(SpellBase spell, Player player, String tag)
    {
        CooldownKey = Security.SHAString(player.getUniqueId().toString() + spell.GetName() + tag);
    }

    void StartCooldownMillis(int milliDelay)
    {
        Time.Entry(CooldownKey, milliDelay);
    }

    void StartCooldownMinutes(int minuteDelay)
    {
        Time.EntryMinutes(CooldownKey, minuteDelay);
    }

    public Period GetRemaining()
    {
        return Time.UntilCompletion(CooldownKey);
    }

    public Time.Status GetStatus()
    {
        return Time.GetStatus(CooldownKey);
    }

    public void Reset() { Time.Reset(CooldownKey); }
}
