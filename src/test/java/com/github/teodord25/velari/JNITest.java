package com.github.teodord25.velari;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JNITest {
    @Test
    void testRustInterop() {
        String msg = OrbitEngine.checkBridge();
        System.out.println("Rust says: " + msg);
        assertEquals("Hello from Rust!", msg);
    }
}
