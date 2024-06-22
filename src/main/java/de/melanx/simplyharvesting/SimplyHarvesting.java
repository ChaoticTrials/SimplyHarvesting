package de.melanx.simplyharvesting;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(SimplyHarvesting.MODID)
public class SimplyHarvesting {

    //    public static final Logger LOGGER = LoggerFactory.getLogger(SimplyHarvesting.class);
    public static final String MODID = "simplyharvesting";

    public SimplyHarvesting() {
        NeoForge.EVENT_BUS.register(new EventListener());
    }
}
