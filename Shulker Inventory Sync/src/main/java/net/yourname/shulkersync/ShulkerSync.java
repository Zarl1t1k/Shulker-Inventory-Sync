package net.yourname.shulkersync;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShulkerSync implements ClientModInitializer {
    public static final String MOD_ID = "shulkersync";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("ShulkerSync готов помогать со стройкой!");
    }
}