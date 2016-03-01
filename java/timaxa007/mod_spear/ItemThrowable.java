package timaxa007.mod_spear;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemThrowable extends Item {

	private float damage_vs_entity = 0F;

	public ItemThrowable() {
		super();
	}

	public ItemThrowable setDamageVsEntity(float damage) {
		damage_vs_entity = damage;
		return this;
	}

	public float getDamageVsEntity() {
		return damage_vs_entity;
	}

	@Override
	public boolean hitEntity(ItemStack is, EntityLivingBase entity1, EntityLivingBase entity2) {
		DamageSource dmgSrc = null;
		if (entity2 instanceof EntityPlayer)
			dmgSrc = DamageSource.causePlayerDamage((EntityPlayer)entity2);
		else
			dmgSrc = DamageSource.causeMobDamage(entity2);
		if (dmgSrc != null) entity1.attackEntityFrom(dmgSrc, getDamageVsEntity());
		is.damageItem(1, entity2);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack is, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
		if (block.getBlockHardness(world, x, y, z) != 0.0F) {
			is.damageItem(2, entity);
		}
		return true;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int tick) {
		int j = this.getMaxItemUseDuration(is) - tick;

		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, is) > 0;

		if (flag || player.inventory.hasItem(this)) {
			float f = (float)j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;
			if ((double)f < 0.1D) return;
			if (f > 1.0F) f = 1.0F;

			EntityItemThrowable entity_spear = new EntityItemThrowable(world, player, f * 2.0F);
			entity_spear.setDamage(getDamageVsEntity());

			if (f == 1.0F) {
				entity_spear.setIsCritical(true);
			}

			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, is);

			if (k > 0) {
				entity_spear.setDamage(entity_spear.getDamage() + (double)k * 0.5D + 0.5D);
			}

			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, is);

			if (l > 0) {
				entity_spear.setKnockbackStrength(l);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, is) > 0) {
				entity_spear.setFire(100);
			}

			if (flag) {
				entity_spear.canBePickedUp = 2;
			} else {
				//player.inventory.consumeInventoryItem(this);
			}

			ItemStack new_item_spear = is.copy();
			new_item_spear.stackSize = 1;
			new_item_spear.damageItem(1, player);
			entity_spear.setItemStack(new_item_spear);

			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			if (!world.isRemote) world.spawnEntityInWorld(entity_spear);

			if (!flag) {
				--is.stackSize;
				if (is.stackSize <= 0) {
					if (player.getCurrentEquippedItem() == is) {
						player.setCurrentItemOrArmor(0, null);
					} else is = null;
				}
			}

		}
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player) {
		return is;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
		if (player.capabilities.isCreativeMode || player.inventory.hasItem(this)) {
			player.setItemInUse(is, this.getMaxItemUseDuration(is));
		}
		return is;
	}

	@Override
	public int getItemEnchantability() {
		return 1;
	}

}
