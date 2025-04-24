package com.github.teodord25.velari.math;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.*;

class FaceTest {

    private static final double EPS = 1e-5;

    @RepeatedTest(1_000)
    void roundTrip() {
        var r = ThreadLocalRandom.current();
        Face f = Face.values()[r.nextInt(6)];
        double u = r.nextDouble(0, Face.L);
        double v = r.nextDouble(0, Face.L);
        double h = r.nextDouble(-10, 10);

        Vec3 p = Face.faceToVec(f, u, v, h);
        Face.FaceCoord fc = Face.vecToFace(p);

        assertEquals(f, fc.face());
        assertEquals(u, fc.u(), EPS);
        assertEquals(v, fc.v(), EPS);
        assertEquals(h, fc.h(), EPS);
    }

    @Test
    void edgeContinuityPX_PZ() {
        double v = 123.456;
        Vec3 a = Face.faceToVec(Face.POS_X, Face.L, v, 0);
        Vec3 b = Face.faceToVec(Face.POS_Z,       0, v, 0);
        assertTrue(a.distanceToSqr(b) < EPS*EPS);
    }
}
