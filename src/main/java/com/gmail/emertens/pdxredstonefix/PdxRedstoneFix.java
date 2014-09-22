package com.gmail.emertens.pdxredstonefix;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by emertens on 9/6/14.
 */
public class PdxRedstoneFix extends JavaPlugin implements Listener {

    static final Material TRIGGER_MATERIAL = Material.QUARTZ_BLOCK;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    void onChunkLoad(final ChunkLoadEvent event) {
        final Chunk chunk = event.getChunk();
        final World world = chunk.getWorld();
        final int x0 = 16*chunk.getX();
        final int z0 = 16*chunk.getZ();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

                final int ytop = world.getHighestBlockYAt(x0+x, z0+z);

                // stops one short of top because we're searching blocks
                // underneath as triggers
                for (int y = 0; y < ytop; y++) {
                    final Block block = chunk.getBlock(x, y, z);
                    if (block.getType().equals(TRIGGER_MATERIAL)) {
                        switch (block.getRelative(BlockFace.UP).getType()) {
                        case DIODE_BLOCK_ON:
                        case DIODE_BLOCK_OFF:
                            refresh(block);
                        }
                    }
                }

            }
        }
    }

    private void refresh(Block block) {
        byte data = block.getData();
        int typeid = block.getTypeId();
        block.setType(Material.STONE);
        block.setTypeIdAndData(typeid, data, true);
    }
}
