package timaxa007.mod_spear;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderEntityItemThrowable extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
		render((EntityItemThrowable)entity, x, y, z, yaw, partialTickTime);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

	private void render(EntityItemThrowable entity, double x, double y, double z, float yaw, float partialTickTime) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTickTime - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTickTime, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-45F, 0.0F, 0.0F, 1.0F);

		ItemStack itemstack = entity.getItemStack();
		if (itemstack != null) {
			EntityItem entityitem = new EntityItem(entity.worldObj, 0.0D, 0.0D, 0.0D, itemstack);
			entityitem.hoverStart = 0.0F;
			RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		}

		GL11.glPopMatrix();
	}

}
