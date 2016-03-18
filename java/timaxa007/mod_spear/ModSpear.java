package timaxa007.mod_spear;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = ModSpear.MODID, name = ModSpear.NAME, version = ModSpear.VERSION)
public class ModSpear {

	public static final String
	MODID = "mod_spear",
	NAME = "Spears Mod",
	VERSION = "0.002a"
	;

	@Mod.Instance(value = ModSpear.MODID)
	public static ModSpear instance;

	@SidedProxy(modId = ModSpear.MODID, serverSide = "timaxa007.mod_spear.ProxyCommon", clientSide = "timaxa007.mod_spear.ProxyClient")
	public static ProxyCommon proxy;

	public static Item item_spear;

	public static SimpleNetworkWrapper network;

	@Mod.EventHandler
	public void preInit(cpw.mods.fml.common.event.FMLPreInitializationEvent event) {

		item_spear = new ItemThrowable().setDamageVsEntity(6F).setMaxDamage(375).setUnlocalizedName("spear").setTextureName(MODID + ":item_spear").setCreativeTab(CreativeTabs.tabCombat);
		GameRegistry.registerItem(item_spear, "item_spear");

		EntityRegistry.registerModEntity(EntityItemThrowable.class, "EntityItemThrowable", 0, instance, 20, 1, true);

		network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		network.registerMessage(MessageSpearPositionAndRotationClient.Handler.class, MessageSpearPositionAndRotationClient.class, 0, Side.CLIENT);

		proxy.preInit();

	}

}
