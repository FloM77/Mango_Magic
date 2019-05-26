package AT.MSev.MangoMagic.Spells;

import AT.MSev.MangoMagic.Mage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class SpellGrowth extends SpellBase {
    public SpellGrowth(String name, double cost, int cooldown, int minlevel, int xpgain, String description, Object... param) {
        super(name, cost, cooldown, minlevel, xpgain, description, param);
        Spells.add(this);
        Spawnables.add(new Spawnable(0.20, Material.HAY_BLOCK));
        Spawnables.add(new Spawnable(0.20, Material.MELON_BLOCK));
        Spawnables.add(new Spawnable(0.05, Material.PUMPKIN));
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
        for(int i=-4;i<5;i++)
            for(int ii=-4;ii<5;ii++)
                for(int iii=-4;iii<5;iii++)
                {
                    Block b = null;
                    if((b = p.getWorld().getBlockAt(
                            p.getLocation().clone().add(i,ii,iii)
                    )).getType().equals(Material.GRASS) && b.getRelative(0,1,0).getType()
                            .equals(Material.AIR))
                    {
                        int j = random.nextInt(Spawnables.size());
                        int br = 0;
                        while (!Spawnables.get(j).Selected())
                        {
                            j++;
                            br++;
                            if(j>=Spawnables.size()) j = 0;
                            if(br>(Spawnables.size()*0.5))
                            {
                                break;
                            }
                        }
                        if(!(br>(Spawnables.size()*0.5))){
                            b.getRelative(0, 1, 0).setType(Spawnables.get(j).block);
                        }
                    }
                }
    }


    static ArrayList<Spawnable> Spawnables = new ArrayList<Spawnable>();
    class Spawnable{
        double chance;
        public Material block;
        Random random = new Random();
        public Spawnable(double chance, Material block)
        {
            this.chance = chance;
            this.block = block;
        }

        public Boolean Selected()
        {
            if(random.nextDouble()<chance)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
