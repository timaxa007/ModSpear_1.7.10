package timaxa007.mod_spear;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon {

	public void preInit() {
		super.preInit();

		RenderingRegistry.registerEntityRenderingHandler(EntityItemThrowable.class, new RenderEntityItemThrowable());

	}

}
