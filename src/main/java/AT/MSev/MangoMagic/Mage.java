package AT.MSev.MangoMagic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class Mage implements ConfigurationSerializable {
    UUID ID;
    double Mana = 100;
    double MaxMana = 100;
    int XP = 0;
    int XPNextLevel = 100;
    int MageLevel = 1;

    static HashMap<UUID, Mage> Mages = new HashMap<UUID, Mage>();

    public Mage(UUID id, double mana, double maxmana, int xp, int xpnextlevel, int magelevel)
    {
        ID = id;
        Mana = mana;
        MaxMana = maxmana;
        XP = xp;
        XPNextLevel = xpnextlevel;
        MageLevel = magelevel;
        Mages.put(id, this);
    }

    public static Mage FromID(UUID id)
    {
        if(Mages.containsKey(id))
        {
            return Mages.get(id);
        }
        else
        {
            return new Mage(id, 100, 100, 0, 100, 1);
        }
    }

    void SaveState()
    {
        ArrayList<Mage> curmages = new ArrayList<Mage>(Mages.values());
        if(MangoMagic.Config.get("mages") != null){
            curmages = new ArrayList<Mage>((ArrayList<Mage>) MangoMagic.Config.get("mages"));
            ArrayList<Mage> removemages = new ArrayList<Mage>();
            for (Mage m:
                 curmages) {
                if(m.ID == ID)
                    removemages.add(m);
            }
            curmages.removeAll(removemages);
            curmages.add(this);
        }
        MangoMagic.Config.set("mages", curmages);
        try {
            MangoMagic.Config.save(new File(MangoMagic.Folder, "config.yml"));
        } catch (Exception ex)
        {
            Bukkit.getLogger().info("Error saving MangoMagic config");
        }
    }

    static public void LoadStates()
    {
        if(MangoMagic.Config.get("mages") != null) {
            ArrayList<Mage> mages = (ArrayList<Mage>) MangoMagic.Config.get("mages");
        }
    }

    public void GainXP(int xp)
    {
        SetXP(XP + xp);
        if(XP >= XPNextLevel)
        {
            MageLevel++;
            Bukkit.getPlayer(ID).sendMessage(ChatColor.GOLD + "You are now a level " + MageLevel + " mage!");
            SetXP(XP - XPNextLevel);
            XPNextLevel = ((int)Math.pow((double)XPNextLevel, 1.015)) +  (MageLevel * 10);
        }
        SaveState();
    }

    void SetXP(int xp)
    {
        XP = xp;
    }

    public void SetMana(double mana)
    {
        Mana = mana;
    }

    public double GetMana()
    {
        return Mana;
    }

    public void SetMaxMana(double mana)
    {
        MaxMana = mana;
        SaveState();
    }

    public double GetMaxMana()
    {
        return MaxMana;
    }

    public UUID GetID()
    {
        return ID;
    }

    public int GetLevel()
    {
        return MageLevel;
    }

    public int GetXP()
    {
        return XP;
    }

    public int GetMaxXP()
    {
        return XPNextLevel;
    }

    //UUID id, double mana, double maxmana, int xp, int xpnextlevel, int magelevel
    public Map<String, Object> serialize() {
        LinkedHashMap result = new LinkedHashMap();
        result.put("idstring", ID.toString());
        result.put("mana", Mana);
        result.put("maxmana", MaxMana);
        result.put("xp", XP);
        result.put("xpnextlevel", XPNextLevel);
        result.put("magelevel", MageLevel);
        return result;
    }

    public static Mage deserialize(Map<String, Object> args) {
        UUID id = UUID.fromString ((String)args.get("idstring"));
        double mana = ((Double)args.get("mana"));
        double maxmana = ((Double)args.get("maxmana"));
        int xp = ((Integer)args.get("xp"));
        int xpnext = ((Integer)args.get("xpnextlevel"));
        int level = ((Integer)args.get("magelevel"));
        Mage ret = new Mage(id, mana, maxmana, xp, xpnext, level);
        return ret;
    }
}
