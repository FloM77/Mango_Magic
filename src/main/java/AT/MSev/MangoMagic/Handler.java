package AT.MSev.MangoMagic;

import AT.MSev.MangoMagic.Items.ItemManaCrystal;
import AT.MSev.MangoMagic.Items.ItemStaff;
import AT.MSev.Mango_Core.Utils.MangoUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Handler implements Listener {

    @EventHandler
    void OnPlayerJoin(PlayerJoinEvent e) {

        if (e.getPlayer().getDisplayName().equalsIgnoreCase("flomich")) {
            e.getPlayer().getInventory().addItem(
                    ItemManaCrystal.FromAmount(20, 20)
            );
        }
    }

    @EventHandler
    void EntityDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            ItemStack is = ItemManaCrystal.FromAmount(e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), 1);
            MangoUtils.ItemRename(is, e.getEntity().getName() + " Soul");
            e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), is);
        }
    }

    @EventHandler
    void OnInventoryClose(InventoryCloseEvent e)
    {
        Player p = (Player)e.getPlayer();
        if(e.getInventory().getTitle().equalsIgnoreCase(p.getDisplayName() + "s Magical Container"))
        {
            PrivateContainer pc = PrivateContainer.ForPlayer(p);
            pc.ChangeContent(new ArrayList<ItemStack>(Arrays.asList(e.getInventory().getContents())));
        }
    }
}
