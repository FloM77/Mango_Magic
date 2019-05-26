package AT.MSev.MangoMagic;

import AT.MSev.MangoMagic.Commands.CommandImbueStaff;
import AT.MSev.MangoMagic.Commands.CommandMageStats;
import AT.MSev.MangoMagic.Commands.CommandPrintSpells;
import AT.MSev.MangoMagic.Items.ItemManaCrystal;
import AT.MSev.MangoMagic.Items.ItemStaff;
import AT.MSev.MangoMagic.Spells.*;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class MangoMagic extends JavaPlugin {
    public static FileConfiguration Config;
    public static File Folder;
    public static NamespacedKey key;

    static {
        ConfigurationSerialization.registerClass(Mage.class, "Mage");
        ConfigurationSerialization.registerClass(PrivateContainer.class, "PrivateContainer");
    }

    @Override
    public void onEnable() {
        key = new NamespacedKey(this, this.getDescription().getName());
        Config = getConfig();
        Folder = getDataFolder();
        Mage.LoadStates();
        PrivateContainer.LoadStates();

        this.getCommand("spells").setExecutor(new CommandPrintSpells());
        this.getCommand("imbue").setExecutor(new CommandImbueStaff());
        this.getCommand("mage").setExecutor(new CommandMageStats());

        //Items
        new ItemStaff();
        new ItemManaCrystal();
        //Spells
        new SpellFireball("Fireball", 10, 5000, 5, 10, "Throws a fireball");
        new SpellSmelt("MinorSmelt", 2, 0, 1, 2, "Smelts ore in your offhand", 1);
        new SpellSmelt("Smelt", 5, 0, 10, 5, "Smelts ore in your offhand. Gain 2 Ingots per ore", 2);
        new SpellDash("Dash", 5, 0, 3, 1, "Dash a short distance");
        new SpellGrowth("Growth", 30, 10000, 7, 40, "Use the power of nature to grow food on grass around you");
        new SpellSummonSheep("SummonSheep", 50, 3000, 6, 50, "Summon a sheep from the sheep dimension");
        new SpellContainer("Container", 10, 0, 4, 1, "Use a portal to always have accesss to a chest");

        getServer().getPluginManager().registerEvents(new Handler(), this);
    }
    @Override
    public void onDisable() {

    }
}
