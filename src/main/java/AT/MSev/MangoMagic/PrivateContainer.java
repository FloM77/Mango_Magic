package AT.MSev.MangoMagic;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class PrivateContainer implements ConfigurationSerializable
{
    String p;
    ArrayList<ItemStack> items = new ArrayList<ItemStack>();
    static ArrayList<PrivateContainer> All = new ArrayList<PrivateContainer>();

    public PrivateContainer(Player p)
    {
        this.p = p.getUniqueId().toString();
        All.add(this);
    }

    public void OpenInventory(Player p)
    {
        Inventory i = Bukkit.createInventory(p, 36, p.getDisplayName() + "s Magical Container");

        for (ItemStack is:
             items) {
            if(is!=null)
            i.addItem(is);
        }

        p.openInventory(
                i
        );
    }

    public void ChangeContent(ArrayList<ItemStack> is)
    {
        items = is;
        SafeStates();
    }

    public static PrivateContainer ForPlayer(Player p)
    {
        for (PrivateContainer pc:
             All) {
            if(pc.p.equalsIgnoreCase(p.getUniqueId().toString()))
            {
                return  pc;
            }
        }
        return null;
    }

    public PrivateContainer(OfflinePlayer p, ArrayList<ItemStack> items)
    {
        this.p = p.getUniqueId().toString();
        this.items = items;
    }

    static public Boolean LoadStates()
    {
        if(MangoMagic.Config.contains("privatecontainers")) {
            All = (ArrayList<PrivateContainer>) MangoMagic.Config.get("privatecontainers");
            return true;
        }
        else
        {
            return false;
        }
    }

    static void SafeStates()
    {
        MangoMagic.Config.set("privatecontainers", All);

        try {
            MangoMagic.Config.save(new File(MangoMagic.Folder, "config.yml"));
        } catch (Exception ex)
        {
            Bukkit.getLogger().info("Error saving MangoMagic config");
        }
    }

    public Map<String, Object> serialize() {
        LinkedHashMap map = new LinkedHashMap();
        map.put("uuidstring", p);
        map.put("itemstack", items);
        return map;
    }

    public static PrivateContainer deserialize(Map<String, Object> args) {
        String id = (String)args.get("uuidstring");
        ArrayList<ItemStack> istack = ((ArrayList<ItemStack>)args.get("itemstack"));
        PrivateContainer ret = new PrivateContainer(Bukkit.getOfflinePlayer(UUID.fromString(id)), istack);
        return ret;
    }
}