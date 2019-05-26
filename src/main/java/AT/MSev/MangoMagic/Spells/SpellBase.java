package AT.MSev.MangoMagic.Spells;

import AT.MSev.MangoMagic.Mage;
import AT.MSev.Mango_Core.Items.Interactable.ItemInteractableCooldown;
import AT.MSev.Mango_Core.Utils.Time;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SpellBase implements ISpell {
    protected String name;
    protected double cost;
    protected int cooldown;
    protected int minlevel;
    protected String description;
    protected int xpgain;
    protected Object[] param;
    public static ArrayList<ISpell> Spells = new ArrayList<ISpell>();

    public SpellBase(String name, double cost, int cooldown, int minlevel, int xpgain, String description, Object[] param) {
        this.name = name;
        this.cost = cost;
        this.cooldown = cooldown;
        this.xpgain = xpgain;
        this.param = param;
        this.minlevel = minlevel;
        this.description = description;
    }

    public double GetCost() {
        return cost;
    }

    public String GetName() {
        return name;
    }

    public String GetDescription() {
        return description;
    }

    public int GetCooldown() {
        return cooldown;
    }

    public int GetMinLevel() { return minlevel; }

    public int GetXPGain() { return xpgain; }

    public Boolean OnCast(Player p) {
        Cooldown cd = new Cooldown(this, p, "cast");
        org.joda.time.Period remaining;

        if (cd.GetStatus().equals(Time.Status.UNMAPPED) | cd.GetStatus().equals(Time.Status.COMPLETED)) {
            cd.StartCooldownMillis(cooldown);
            return true;
        } else if (cd.GetStatus().equals(Time.Status.RUNNING)) {
            if ((remaining = cd.GetRemaining()) != null) {
                p.sendMessage(ChatColor.RED + remaining.toString() + " until " + name  + " is off cooldown");
                return false;
            }
        }
        return true;
    }

    protected void ProvideXP(Mage mage)
    {
        mage.GainXP(xpgain);
    }

    protected Boolean CheckLevel(Mage mage)
    {
        if(mage.GetLevel() >= minlevel)
        {
            return true;
        }
        else
        {
            Bukkit.getPlayer(mage.GetID()).sendMessage(ChatColor.RED + "You need to be level " + minlevel + " to use this magic");
            return false;
        }
    }

    protected Boolean CorrectMana(Mage mage)
    {
        if(mage.GetMana() >= cost) {
            mage.SetMana(mage.GetMana() - cost);
            Bukkit.getPlayer(mage.GetID()).sendMessage(ChatColor.GRAY + "Mana: " + ChatColor.BLUE + mage.GetMana() + " / " + mage.GetMaxMana());
            return true;
        }
        else
        {
            Bukkit.getPlayer(mage.GetID()).sendMessage(ChatColor.RED + "You need " + (cost - mage.GetMana()) + " more mana");
            return false;
        }
    }

    protected void ResetCooldown(Player p)
    {
        Cooldown cd = new Cooldown(this, p, "cast");
        cd.Reset();
    }

    public static ISpell FromName(String name)
    {
        for (ISpell spell:
             Spells) {
            if(spell.GetName().equalsIgnoreCase(name)) return spell;
        }
        return null;
    }
}
