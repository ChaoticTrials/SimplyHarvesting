package de.melanx.simplyharvesting;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(SimplyHarvesting.MODID)
public class SimplyHarvesting {

    //    public static final Logger LOGGER = LoggerFactory.getLogger(SimplyHarvesting.class);
    public static final String MODID = "simplyharvesting";

    public SimplyHarvesting() {
        MinecraftForge.EVENT_BUS.register(new EventListener());
    }
}
