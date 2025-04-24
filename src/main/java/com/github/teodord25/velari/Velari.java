package com.github.teodord25.velari;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Velari.MOD_ID)
public final class Velari {
    public static final String MOD_ID = "velari";

    public Velari(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // initialise networking, config, etc.
    }

    /* ---------------------------------------------------------
       Native loader – extracts lib from JAR and loads via JNI
    --------------------------------------------------------- */
    static {
        String arch = System.getProperty("os.arch").contains("64") ? "x86_64" : "x86";
        String os = switch (System.getProperty("os.name").toLowerCase()) {
            case String s when s.contains("win")  -> "pc-windows-gnu";
            case String s when s.contains("mac")  -> "apple-darwin";
            default -> "unknown-linux-gnu";
        };
        String libName = os.contains("windows") ? "orbit_engine.dll"
                     : os.contains("darwin")   ? "liborbit_engine.dylib"
                     :                           "liborbit_engine.so";
        String path = "/natives/" + arch + "-" + os + "/" + libName;
        try {
            java.nio.file.Path temp = java.nio.file.Files.createTempFile("orbit_engine", libName);
            try (var in = Velari.class.getResourceAsStream(path)) {
                java.nio.file.Files.copy(in, temp, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            System.load(temp.toAbsolutePath().toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load native library " + path, e);
        }
    }
}
