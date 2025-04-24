package com.github.teodord25.velari.math;

import net.minecraft.world.phys.Vec3;

/**
 * 6 cardinal faces of the cube-planet and coordinate transforms.<br>
 * Local axes are chosen so that neighbouring faces meet seamlessly.
 *
 *  — u grows “to the right” looking straight at the face  
 *  — v grows “up” (toward the +Y direction on the rendered map)  
 *  — h is the offset away from the cube surface, +outwards
 */
public enum Face {
    /* ===========  concrete faces  =========== */

    POS_X  {                                      //  u→ +Z , v→ +Y
        Vec3 toWorld(double u,double v,double h,double L){
            return new Vec3( L/2 + h ,  v-L/2 ,  u-L/2 );
        }
        UVH  toLocal(Vec3 p,double L){
            return new UVH(p.z+L/2 , p.y+L/2 , p.x-L/2);
        }
        public Face uPos() { return POS_Z; }
        public Face uNeg() { return NEG_Z; }
        public Face vPos() { return POS_Y; }
        public Face vNeg() { return NEG_Y; }
    },

    NEG_X  {                                      //  u→ –Z , v→ +Y
        Vec3 toWorld(double u,double v,double h,double L){
            return new Vec3(-L/2 - h ,  v-L/2 ,  L/2-u );
        }
        UVH  toLocal(Vec3 p,double L){
            return new UVH(L/2-p.z , p.y+L/2 , -L/2-p.x);
        }
        public Face uPos() { return NEG_Z; }
        public Face uNeg() { return POS_Z; }
        public Face vPos() { return POS_Y; }
        public Face vNeg() { return NEG_Y; }
    },

    POS_Y  {                                      //  u→ +X , v→ –Z
        Vec3 toWorld(double u,double v,double h,double L){
            return new Vec3( u-L/2 ,  L/2 + h ,  L/2-v );
        }
        UVH  toLocal(Vec3 p,double L){
            return new UVH(p.x+L/2 , L/2-p.z , p.y-L/2);
        }
        public Face uPos() { return POS_X; }
        public Face uNeg() { return NEG_X; }
        public Face vPos() { return POS_Z; }
        public Face vNeg() { return NEG_Z; }
    },

    NEG_Y  {                                      //  u→ +X , v→ +Z
        Vec3 toWorld(double u,double v,double h,double L){
            return new Vec3( u-L/2 , -L/2 - h ,  v-L/2 );
        }
        UVH  toLocal(Vec3 p,double L){
            return new UVH(p.x+L/2 , p.z+L/2 , -L/2-p.y);
        }
        public Face uPos() { return POS_X; }
        public Face uNeg() { return NEG_X; }
        public Face vPos() { return NEG_Z; }
        public Face vNeg() { return POS_Z; }
    },

    POS_Z  {                                      //  u→ –X , v→ +Y
        Vec3 toWorld(double u,double v,double h,double L){
            return new Vec3( L/2-u ,  v-L/2 ,  L/2 + h );
        }
        UVH  toLocal(Vec3 p,double L){
            return new UVH(L/2-p.x , p.y+L/2 , p.z-L/2);
        }
        public Face uPos() { return NEG_X; }
        public Face uNeg() { return POS_X; }
        public Face vPos() { return POS_Y; }
        public Face vNeg() { return NEG_Y; }
    },

    NEG_Z  {                                      //  u→ +X , v→ +Y
        Vec3 toWorld(double u,double v,double h,double L){
            return new Vec3( u-L/2 ,  v-L/2 , -L/2 - h );
        }
        UVH  toLocal(Vec3 p,double L){
            return new UVH(p.x+L/2 , p.y+L/2 , -L/2-p.z);
        }
        public Face uPos() { return POS_X; }
        public Face uNeg() { return NEG_X; }
        public Face vPos() { return POS_Y; }
        public Face vNeg() { return NEG_Y; }
    };

    /* ===========  shared data / helpers  =========== */

    /** cube face size (blocks) */
    public static final double L = 512.0;

    /** tuple of (u,v,h) in local coordinates */
    public record UVH(double u,double v,double h){}

    /** result of {@link #vecToFace(Vec3)} or {@link #wrapUV(Face,double,double)} */
    public record FaceCoord(Face face,double u,double v,double h){}

    /* ---- public API ---- */

    /** Convert local cube-face coords ⇒ world space */
    public static Vec3 faceToVec(Face f,double u,double v,double h){
        return f.toWorld(u,v,h,L);
    }

    /** Infer which face a point lies on and return its local (u,v,h). */
    public static FaceCoord vecToFace(Vec3 p){
        double ax=Math.abs(p.x), ay=Math.abs(p.y), az=Math.abs(p.z);
        Face f;            // pick the dominant component
        if(ax>=ay && ax>=az) f = (p.x>=0? POS_X:NEG_X);
        else if(ay>=ax && ay>=az) f = (p.y>=0? POS_Y:NEG_Y);
        else                      f = (p.z>=0? POS_Z:NEG_Z);

        UVH uvh = f.toLocal(p,L);
        return new FaceCoord(f, uvh.u(), uvh.v(), uvh.h());
    }

    /**
     * Normalise out-of-range u/v by crossing edges until 0 ≤ u,v &lt; L.
     * h stays zero (surface) by design.
     */
    public static FaceCoord wrapUV(Face f,double u,double v){
        Vec3 p = faceToVec(f,u,v,0.0);     // fire through world space once
        return vecToFace(p);               // gives correct face / wrapped (u,v)
    }

    /* ---- edge navigation helpers ---- */

    /** neighbour reached when <b>u == L</b> (positive-u edge) */
    public abstract Face uPos();
    /** neighbour reached when <b>u == 0</b> (negative-u edge) */
    public abstract Face uNeg();
    /** neighbour reached when <b>v == L</b> (positive-v edge / top) */
    public abstract Face vPos();
    /** neighbour reached when <b>v == 0</b> (negative-v edge / bottom) */
    public abstract Face vNeg();

    /* ---- implementation hooks (package-private!) ---- */

    abstract Vec3 toWorld(double u,double v,double h,double L);
    abstract UVH  toLocal(Vec3 p,double L);
}
