package de.melanx.simplyharvesting;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(SimplyHarvesting.MODID)
public class SimplyHarvesting {

    //    public static final Logger LOGGER = LoggerFactory.getLogger(SimplyHarvesting.class);
    public static final String MODID = "simplyharvesting";

    public SimplyHarvesting(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.CONFIG);
    }
}
