package com.github.teodord25.velari.world.gen;

/**
 *  ▸ Skips chunks whose chunk‑X **or** chunk‑Z is odd → those chunks stay all‑air.
 *  ▸ Keeps even/even chunks and fills blocks from world min‑Y up to y = 64 with stone.
 *  ▸ Implements every abstract method required by the 1.21.1 `ChunkGenerator` API
 *    (`codec`, `getGenDepth`, `fillFromNoise`, `applyCarvers`, `buildSurface`,
 *     `spawnOriginalMobs`, `getBaseColumn`, `getBaseHeight`, `getMinY`,
 *     `getSeaLevel`, `addDebugScreenInfo`).
 *
 * Just testing stuff for now
 */

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlanetChunkGenerator extends ChunkGenerator {

    /* --------------------------------------------------------------------- */
    /*  Data‑driven wiring (MapCodec)                                        */
    /* --------------------------------------------------------------------- */

    public static final MapCodec<PlanetChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(g -> g.biomeSource)
            ).apply(i, PlanetChunkGenerator::new)
    );

    private final BiomeSource biomeSource;

    /* --------------------------------------------------------------------- */
    /*  Construction                                                         */
    /* --------------------------------------------------------------------- */

    public PlanetChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource);
        this.biomeSource = biomeSource;
    }

    /* --------------------------------------------------------------------- */
    /*  Codec / biome source                                                 */
    /* --------------------------------------------------------------------- */

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public BiomeSource getBiomeSource() {
        return biomeSource;
    }

    /* --------------------------------------------------------------------- */
    /*  Core terrain pass – FILL step                                        */
    /* --------------------------------------------------------------------- */

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender,
                                                        RandomState random,
                                                        StructureManager structureManager,
                                                        ChunkAccess chunk) {
        ChunkPos cPos = chunk.getPos();

        // 1️⃣  decide whether this chunk should be void or solid
        if (!shouldGenerate(cPos)) {
            return CompletableFuture.completedFuture(chunk); // leave empty (air)
        }

        // 2️⃣  crude flat stone layer for demo purposes
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        int minY = chunk.getMinBuildHeight();
        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                for (int y = minY; y <= 64; y++) {
                    cursor.set(cPos.getBlockX(dx), y, cPos.getBlockZ(dz));
                    chunk.setBlockState(cursor, Blocks.STONE.defaultBlockState(), false);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    /* --------------------------------------------------------------------- */
    /*  Required API surface stubs                                           */
    /* --------------------------------------------------------------------- */

    @Override
    public int getGenDepth() {
        // Minimal implementation – no noise blending pyramid depth needed yet.
        return 0;
    }

    @Override
    public void applyCarvers(WorldGenRegion region, long seed, RandomState random, BiomeManager biomeManager,
                             StructureManager structureManager, ChunkAccess chunk, GenerationStep.Carving step) {
        // No caves/ravines – intentionally left blank.
    }

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structureManager, RandomState random, ChunkAccess chunk) {
        // Surface already built in fillFromNoise – noop.
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion region) {
        // Void world – skip mob spawning.
    }

    /* --------------------------------------------------------------------- */
    /*  Height & ambience                                                    */
    /* --------------------------------------------------------------------- */

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor heightAccessor, RandomState random) {
        // Treat every column as empty for structure placement.
        return new NoiseColumn(heightAccessor.getMinBuildHeight(), new net.minecraft.world.level.block.state.BlockState[0]);
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor heightAccessor, RandomState random) {
        // Flat – every column height is the same (min build height).
        return heightAccessor.getMinBuildHeight();
    }

    @Override
    public int getMinY() {
        return -64;
    }

    @Override
    public int getSeaLevel() {
        return 63;
    }

    @Override
    public void addDebugScreenInfo(List<String> lines, RandomState random, BlockPos pos) {
        lines.add("PlanetGen: " + (shouldGenerate(new ChunkPos(pos)) ? "SOLID" : "VOID"));
    }

    /* --------------------------------------------------------------------- */
    /*  Helper – toggle which chunks generate                                */
    /* --------------------------------------------------------------------- */

    private boolean shouldGenerate(ChunkPos pos) {
        // Only generate when both chunk coordinates are even → checkerboard pattern.
        return (pos.x & 1) == 0 && (pos.z & 1) == 0;
    }
}
