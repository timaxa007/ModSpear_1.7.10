package timaxa007.mod_spear;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MessageSpearPositionAndRotationClient implements IMessage {

	private static int entityID;
	private static double x, y, z;
	private static float yaw, pitch;

	public MessageSpearPositionAndRotationClient() {}

	public MessageSpearPositionAndRotationClient(Entity entity, double x, double y, double z, float yaw, float pitch) {
		this(entity.getEntityId(), x, y, z, yaw, pitch);
	}

	public MessageSpearPositionAndRotationClient(int entityID, double x, double y, double z, float yaw, float pitch) {
		this.entityID = entityID;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeFloat(yaw);
		buf.writeFloat(pitch);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		yaw = buf.readFloat();
		pitch = buf.readFloat();
	}

	public static class Handler implements IMessageHandler<MessageSpearPositionAndRotationClient, IMessage> {

		@Override
		public IMessage onMessage(MessageSpearPositionAndRotationClient packet, MessageContext message) {
			act(packet.entityID, packet.x, packet.y, packet.z, packet.yaw, packet.pitch);
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void act(int entityID, double x, double y, double z, float yaw, float pitch) {
			System.out.println("MessageSpearPositionAndRotationClient");
			Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(entityID);
			if (entity instanceof EntityItemThrowable) {
				EntityItemThrowable entity_item_throwable = (EntityItemThrowable)entity;
				entity_item_throwable.setPositionAndRotation2(x, y, z, yaw, pitch, 3);
				//entity.setPositionAndRotation(x, y, z, yaw, pitch);
			}

		}

	}

}
