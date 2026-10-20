package fr.lefilou.filouboost.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/** Configuration simple, stockee dans config/filouboost.json. */
public final class FilouBoostConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger("filouboost");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("filouboost.json");

	private static FilouBoostConfig instance = new FilouBoostConfig();

	/** Limite les FPS quand la fenetre du jeu n'est pas active. */
	public boolean unfocusedFpsCap = true;

	/** Limite de FPS appliquee quand la fenetre n'est pas active (5 a 60). */
	public int unfocusedFpsLimit = 30;

	public static FilouBoostConfig get() {
		return instance;
	}

	public static void load() {
		if (Files.exists(PATH)) {
			try (Reader reader = Files.newBufferedReader(PATH)) {
				FilouBoostConfig loaded = GSON.fromJson(reader, FilouBoostConfig.class);
				if (loaded != null) {
					instance = loaded;
				}
			} catch (Exception e) {
				LOGGER.warn("Config illisible, valeurs par defaut utilisees", e);
			}
		}
		instance.unfocusedFpsLimit = Math.max(5, Math.min(60, instance.unfocusedFpsLimit));
		save();
	}

	public static void save() {
		try (Writer writer = Files.newBufferedWriter(PATH)) {
			GSON.toJson(instance, writer);
		} catch (IOException e) {
			LOGGER.warn("Impossible d'ecrire la config", e);
		}
	}
}
