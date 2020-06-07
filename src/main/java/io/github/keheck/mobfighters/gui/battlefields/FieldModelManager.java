package io.github.keheck.mobfighters.gui.battlefields;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;

import static io.github.keheck.mobfighters.MobFighters.LOGGER;

public class FieldModelManager extends JsonReloadListener
{
    private static final Gson GSON = new GsonBuilder().create();

    // If reading the default block in the .json file failed.
    // Let's assume air is the most desirable
    private static final Block DEF_DEF_BLOCK = Blocks.AIR;
    private ImmutableMap<ResourceLocation, FieldModel> models = ImmutableMap.of();

    private static final String OPTIMIZE = "optimize";
    private static final String DEFAULT = "default";

    public FieldModelManager() { super(GSON, "fields"); }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void apply(Map<ResourceLocation, JsonObject> splashList, IResourceManager resourceManagerIn, IProfiler profilerIn)
    {
        Map<ResourceLocation, FieldModel> modelMap = new HashMap<>();
        IForgeRegistry<Block> blocks = ForgeRegistries.BLOCKS;

        splashList.forEach((location, jsonRoot) ->
        {
            FieldModel.ModelBuilder builder;
            Block defBlock = DEF_DEF_BLOCK;
            Map<Character, Block> blockMap = new HashMap<>();
            boolean optimize = true;

            int width, length, height;

            if(jsonRoot.has(DEFAULT))
                defBlock = blocks.getValue(new ResourceLocation(jsonRoot.get(DEFAULT).getAsString()));

            if(jsonRoot.has(OPTIMIZE))
                optimize = jsonRoot.get(OPTIMIZE).getAsBoolean();

            try
            {
                JsonObject size = jsonRoot.getAsJsonObject("size");
                width = size.get("width").getAsInt();
                length = size.get("length").getAsInt();
                height = size.get("height").getAsInt();

                builder = FieldModel.ModelBuilder.initBuilder(width, length, height);
            }
            catch(Exception e)
            {
                LOGGER.error("{}: Either 'size' was not a json object, " +
                        "'size' was not found or width, length or height was not or incorrectly defined", location);
                throw e;
            }

            try
            {
                JsonObject keys = jsonRoot.get("keys").getAsJsonObject();

                for(Map.Entry<String, JsonElement> entry : keys.entrySet())
                {
                    String val = entry.getValue().getAsString();
                    char key = entry.getKey().charAt(0);

                    blockMap.put(key, blocks.getValue(new ResourceLocation(val)));
                }
            }
            catch(Exception e)
            {
                LOGGER.error("{}: 'keys' was not found or not a json object.", location);
                throw e;
            }

            try
            {
                JsonObject layers = jsonRoot.getAsJsonObject("layers");

                for(int i = 0; i < height; i++)
                {
                    JsonArray layer = layers.getAsJsonArray("layer" + i);

                    // Read the file like the blocks are arranged in FieldModel#blocks
                    for(int j = 0; j < width; j++)
                    {
                        String line = layer.get(j).getAsString();

                        for(char c : line.toCharArray())
                        {
                            builder.addBlock(blockMap.getOrDefault(c, defBlock));
                        }
                    }

                    if(builder.isDone())
                        break;
                }
            }
            catch(Exception e)
            {
                LOGGER.error("{}: 'layers' was not a json object, it's members weren't json arrays or their values weren't strings.", location);
                throw e;
            }

            try
            {
                JsonObject middle = jsonRoot.getAsJsonObject("middle");

                float x = middle.get("x").getAsFloat();
                float y = middle.get("y").getAsFloat();
                float z = middle.get("z").getAsFloat();

                builder.setMiddle(new Vec3d(x, y, z));
            }
            catch(Exception ignore) { }

            if(optimize)
                builder.optimize();

            modelMap.put(location, builder.build());
        });

        models = ImmutableMap.copyOf(modelMap);
        LOGGER.info("Loaded {} battle fields", models.size());
    }

    FieldModel getFieldModel(ResourceLocation location) { return models.get(location); }
}
