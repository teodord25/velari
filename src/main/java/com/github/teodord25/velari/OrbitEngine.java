package com.github.teodord25.velari;

import java.io.File;

public class OrbitEngine {
    static {
        try {
            String path = new File("build/libs/liborbit_engine.so").getAbsolutePath();
            System.load(path);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Failed to load native lib: " + e.getMessage());
            throw e;
        }
    }

    // NOTE: only used for checking if the JNI bridge is working
    public static native String checkBridge();

    public static native Double computeDistance(double x, double y, double z);
}
