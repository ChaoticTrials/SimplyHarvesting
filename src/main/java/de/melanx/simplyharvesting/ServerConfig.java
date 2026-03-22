package de.melanx.simplyharvesting;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {

    public static final ModConfigSpec CONFIG;
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static {
        init(BUILDER);
        CONFIG = BUILDER.build();
    }

    private static ModConfigSpec.BooleanValue disableOnSneaking;

    public static void init(ModConfigSpec.Builder builder) {
        disableOnSneaking = builder.comment(
                        "If you encounter plants that you grow that are harvested completely instead of the default behavior as of Berry Bushes, set this to true and tell me about that plant. " +
                                "That way, I can add it to the block tag #c:berry_bushes to ignore them internally."
                )
                .define("disableOnSneaking", false);
    }

    public static boolean disableOnSneaking() {
        return disableOnSneaking.get();
    }
}
