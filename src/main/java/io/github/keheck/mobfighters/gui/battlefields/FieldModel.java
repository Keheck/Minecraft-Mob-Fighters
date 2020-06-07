package io.github.keheck.mobfighters.gui.battlefields;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class FieldModel
{
    /**
     * Order if "seen" from above:
     * <ol>
     *     <li>left to right</li>
     *     <li>back to front</li>
     *     <li>bottom to top</li>
     * </ol>
     */
    ImmutableMap<BlockPos, Block> blocks;
    Vec3d middle;

    static class ModelBuilder
    {
        private static final ModelBuilder BUILDER = new ModelBuilder();

        private int width, length, height;
        private BlockPos.MutableBlockPos currentPos;
        private Map<BlockPos, Block> blockBuffer;
        private Vec3d middle;
        private boolean isDone;

        private ModelBuilder() { }

        static ModelBuilder initBuilder(int width, int length, int height)
        {
            BUILDER.width = width;
            BUILDER.length = length;
            BUILDER.height = height;
            BUILDER.currentPos = new BlockPos.MutableBlockPos();
            BUILDER.blockBuffer = new HashMap<>();
            BUILDER.isDone = false;

            return BUILDER;
        }

        // TODO: Put a system in place to manually shift to the next layer instead of automatically.
        ModelBuilder addBlock(Block block)
        {
            blockBuffer.put(currentPos.toImmutable(), block);
            currentPos.move(1, 0, 0);

            // Reached end of line, go one line up
            if(currentPos.getX() >= width)
                currentPos.move(-currentPos.getX(), 0, 1);

            // Reached end of layer, go one layer up
            if(currentPos.getZ() >= length)
                currentPos.move(0, 1, -currentPos.getZ());

            // Reached maximum height, mark for finish
            if(currentPos.getY() >= height)
                isDone = true;

            return this;
        }

        boolean isDone() { return isDone; }

        /**
         * <p>This method loops through all values of {@link Direction} to see if the adjacent block
         * in that direction is in some way transparent. A block is considered transparent if it's
         * {@linkplain Block#getRenderLayer() getRenderLayer} returns anything but
         * {@link BlockRenderLayer#SOLID}.</p>
         * <p>The Direction {@link Direction#DOWN DOWN} is ignored
         * for the bottommost layer because the bottom side of the model will never be seen under
         * normal circumstances.</p>
         * <p>All blocks that would be positioned at the outer layer of a cuboid
         * with the same length, height and width of this model will be ignored completely, since they are
         * considered to be always visible at some point</p>
         * <p>If break/continue statements are hit, it means the following:
         * <ul>
         *     <li>{@code continue}: Ignore the direction/position, as it will cause the block to not be visible</li>
         *     <li>{@code break}: Ignore the position, as it will cause the block to always be visible at some point.</li>
         * </ul></p><br/>
         *
         * <p>TL;DR: Apply a simple "culling" algorithm: remove any block from the block buffer that would never be seen by the player
         * under normal circumstances.</p>
         *
         * @return The {@link #BUILDER} singleton.
         */
        // TODO: Try to make it better, like looking further than just one block
        ModelBuilder optimize()
        {
            // A list of all positions that are obscured from all sides
            final ArrayList<BlockPos> invisiblePoss = new ArrayList<>();

            blockBuffer.forEach((pos, block) ->
            {
                if(block == Blocks.AIR)
                    return;

                for(Direction dir : Direction.values())
                {
                    if(pos.getY() <= 0 && dir == Direction.DOWN)
                        continue;

                    if(pos.getX() == 0 || pos.getX() == width-1 || pos.getZ() == 0 || pos.getZ() == length-1)
                        break;

                    BlockPos adjacentPos = pos.offset(dir);
                    Block adjacentBlock = blockBuffer.getOrDefault(adjacentPos, Blocks.AIR);

                    if(adjacentBlock == Blocks.AIR || adjacentBlock.getRenderLayer() != BlockRenderLayer.SOLID)
                        break;

                    // The last direction to be tested according to EnumType#values()
                    if(dir == Direction.EAST)
                        invisiblePoss.add(pos);
                }
            });

            // Replace every block at the invisible positions with Blocks.AIR
            // Blocks.AIR is ignored over in the BattleField#render() method
            for(BlockPos pos : invisiblePoss)
                blockBuffer.replace(pos, Blocks.STONE);

            return this;
        }

        public void setMiddle(Vec3d middle) { this.middle = middle.mul(-1, -1, -1); }

        FieldModel build()
        {
            FieldModel model = new FieldModel();
            model.blocks = ImmutableMap.copyOf(blockBuffer);
            model.middle = middle == null ? new Vec3d(-width/2d, -height/2d, -length/2d) : middle;

            return model;
        }
    }
}
