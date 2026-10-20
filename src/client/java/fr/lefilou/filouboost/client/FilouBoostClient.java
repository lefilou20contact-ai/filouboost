package fr.lefilou.filouboost.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilouBoostClient implements ClientModInitializer {
	public static final String MOD_ID = "filouboost";
	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private boolean throttled = false;
	private int savedFpsLimit = 0;

	@Override
	public void onInitializeClient() {
		FilouBoostConfig.load();
		ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
		// Securite : on remet la limite d'origine avant la fermeture du jeu.
		ClientLifecycleEvents.CLIENT_STOPPING.register(this::restoreFpsLimit);
		LOGGER.info("FilouBoost charge.");
	}

	private void onTick(Minecraft client) {
		if (!FilouBoostConfig.get().unfocusedFpsCap) {
			restoreFpsLimit(client);
			return;
		}

		boolean active = client.isWindowActive();
		if (!active && !throttled) {
			savedFpsLimit = client.options.framerateLimit().get();
			client.options.framerateLimit().set(Math.min(savedFpsLimit, FilouBoostConfig.get().unfocusedFpsLimit));
			throttled = true;
		} else if (active && throttled) {
			restoreFpsLimit(client);
		}
	}

	private void restoreFpsLimit(Minecraft client) {
		if (throttled) {
			client.options.framerateLimit().set(savedFpsLimit);
			throttled = false;
		}
	}
}
