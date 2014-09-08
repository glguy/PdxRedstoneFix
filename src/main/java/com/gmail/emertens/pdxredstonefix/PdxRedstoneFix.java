package com.gmail.emertens.pdxredstonefix;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
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

    static final Material TRIGGER_MATERIAL = Material.IRON_BLOCK;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    void onChunkLoad(final ChunkLoadEvent event) {
        final Chunk chunk = event.getChunk();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 1; y < 256; y++) {
                    final Block block = chunk.getBlock(x, y, z);
                    switch (block.getType()) {
                        case DIODE_BLOCK_ON:
                        case DIODE_BLOCK_OFF:
                            final Block under = block.getRelative(BlockFace.DOWN);
                            if (under.getType().equals(TRIGGER_MATERIAL)) {
                                refresh(under);
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
