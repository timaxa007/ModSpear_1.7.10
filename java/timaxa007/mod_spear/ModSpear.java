package timaxa007.mod_spear;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ModSpear.MODID, name = ModSpear.NAME, version = ModSpear.VERSION)
public class ModSpear {

	public static final String
	MODID = "mod_spear",
	NAME = "ModSpear",
	VERSION = "0.001a"
	;

	@Mod.Instance(MODID) public static ModSpear instance;

	public static Item item_spear;

	@Mod.EventHandler
	public void preInit(cpw.mods.fml.common.event.FMLPreInitializationEvent event) {

		item_spear = new ItemThrowable().setDamageVsEntity(10F).setMaxDamage(1024).setUnlocalizedName("spear").setTextureName(MODID + ":item_spear").setCreativeTab(CreativeTabs.tabCombat);
		GameRegistry.registerItem(item_spear, "item_spear");

		EntityRegistry.registerModEntity(EntityItemThrowable.class, "EntityItemThrowable", 0, instance, 20, 1, true);
		RenderingRegistry.registerEntityRenderingHandler(EntityItemThrowable.class, new RenderEntityItemThrowable());

	}

}
