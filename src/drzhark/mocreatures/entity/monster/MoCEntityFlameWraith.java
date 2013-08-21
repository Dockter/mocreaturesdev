package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityFlameWraith extends MoCEntityWraith implements IMob {

    protected int burningTime;
    private float moveSpeed;

    public MoCEntityFlameWraith(World world)
    {
        super(world);
        texture = "flamewraith.png";
        setSize(1.5F, 1.5F);
        isImmuneToFire = true;
        burningTime = 30;
        
    }

   

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.5D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);

            if (MoCreatures.isServer() && !worldObj.provider.isHellWorld)
            {
                ((EntityLivingBase) entity).setFire(burningTime);
            }
        }
    }

    @Override
    protected int getDropItemId()
    {
        return Item.redstone.itemID;
    }

    @Override
    public void onLivingUpdate()
    {
        if (!worldObj.isRemote)
        {
            if (rand.nextInt(40) == 0)
            {
                this.setFire(2);
            }
            if (worldObj.isDaytime())
            {
                float f = getBrightness(1.0F);
                if ((f > 0.5F) && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && ((rand.nextFloat() * 30F) < ((f - 0.4F) * 2.0F)))
                {
                    this.setEntityHealth(func_110143_aJ() - 2);
                }
            }
        }
        super.onLivingUpdate();
    }

    @Override
    public float getMoveSpeed()
    {
        return 1.1F;
    }
    
    @Override
    public float getMaxHealth()
    {
        return 15;
    }

   
}