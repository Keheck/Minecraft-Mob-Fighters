package io.github.keheck.mobfighters.registry.entries;

import com.google.gson.*;
import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.fight.moves.Move;
import io.github.keheck.mobfighters.registry.Registry;
import io.github.keheck.mobfighters.util.holders.LearnMoveHolder;
import io.github.keheck.mobfighters.util.holders.StartMoveHolder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResource;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static io.github.keheck.mobfighters.MobFighters.*;

/**
 * <p>
 *     Q: What is this class? <br/>
 *     A: Every instance of this class holds information on what an instance
 *     of the {@link Fighter} class should be able to do, e.g. what
 *     {@linkplain Move moves} can be added to said instance or what
 *     moves the instance holds to begin with.
 * </p>
 * <p/>
 * <p>
 *     Q: Why is {@link #setRegistryName(ResourceLocation)} deprecated? <br/>
 *     A: The registry name of every instance will always be the registry name
 *     of the {@link #entityType} field, so there can be quick access to
 *     the {@code FighterEntry} instance when instantiating a fighter.
 * </p>
 * <p/>
 * <p>
 *     Q: Where can I set the moves and traits for my fighter? <br/>
 *     A: You set those in a file at the location "data/[modID]/fighters/[fighterID].json".
 *        See <a href="example.com">[PUT LINK HERE!!]</a> to see how to set up your fighter.
 * </p>
 * <p/>
 * <p>
 *     Q: Why have you made it so complicated?<br/>
 *     A: Because it would have been even more complicated for you to add all the
 *        moves you want your fighter to have to the entry by code. Putting it in a file makes it
 *        easier for you and me in the long run :)
 * </p>
 */
@Mod.EventBusSubscriber
public final class FighterEntry implements IForgeRegistryEntry<FighterEntry>
{
    /** This is used to get a model for the fighter */
    private EntityType<? extends LivingEntity> entityType;
    /** This is a pool of the moves the Fighter can have at the beginning. They also count as learnables. */
    private StartMoveHolder[] movePool;
    /** This is a pool of all learnable moves. */
    private LearnMoveHolder[] learnPool;
    /** This is an array of all Traits. */
    private TraitEntry[] traits;

    /** These are the base stats a fighter has on Level 5 (starting level)
     *  standard values:
     *  hp = 20,
     *  atk = 2,
     *  def = 2,
     *  spd = 1
     */
    private int hp, atk, def, spd;

    private final HashMap<TraitEntry, Object[]> traitDataMap = new HashMap<>();

    private static final Gson GSON = new GsonBuilder().create();

    private static int warns = 0;
    private static int errors = 0;

    public FighterEntry(EntityType<? extends LivingEntity> entityType)
    {
        this.entityType = entityType;

        movePool = new StartMoveHolder[0];
        learnPool = new LearnMoveHolder[0];
        traits = new TraitEntry[]{};
    }

    /** Listen for a server start and apply fighter data. */
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void serverStarting(FMLServerStartingEvent event)
    {
        IReloadableResourceManager manager = event.getServer().getResourceManager();
        Registry.getFighterRegistry().getEntries().forEach((entry) -> entry.getValue().gatherData(manager));
        LOGGER.info("Loaded all fighters, warnings found: {}", warns);
        warns = 0;
    }

    /**
     * @return A {@link ResourceLocation} representing a file path used by {@link #gatherData(IReloadableResourceManager)}.
     *         Has usually the form "[modID]:fighters/[fighterID].json
     */
    @SuppressWarnings("ConstantConditions")
    private ResourceLocation getDataLocation()
    {
        ResourceLocation location = this.getRegistryName();
        String domain = location.getNamespace().equals(MobFighters.MC) ? MODID : location.getNamespace();
        return new ResourceLocation(domain, "fighters/" + location.getPath() + ".json");
    }

    /**
     * @return {@code this}
     * @deprecated This class doesn't use this method, since the
     *             {@link #getRegistryName()} method returns the
     *             registry name of the {@link #entityType} field.
     */
    @Override
    @Deprecated
    public FighterEntry setRegistryName(ResourceLocation name) { return this; }

    /**
     * This method returns an instance of {@link Fighter} or one of it's subclasses.
     *
     * @return A new {@link Fighter} instance.
     */
    public Fighter buildInstance()
    {
        MoveEntry[] startMoves = new MoveEntry[4];
        int index = 0;

        for(StartMoveHolder holder : movePool)
        {
            if(index >= 3)
                break;

            if(new Random().nextFloat() <= holder.getProbability())
            {
                startMoves[index] = holder.getEntry();
                index++;
            }
        }

        return new Fighter(this, startMoves, traits, hp, atk, def, spd);
    }

    /**
     * This method is called every time a fighter of this {@code FighterEntry} type
     * has leveled up. The array {@link #learnPool} is copied into a list to be
     * filtered for moves that cannot be learned (either because it is already or
     * because the level of the fighter is not high enough).
     *
     * @param caller The fighter that received the level up.
     * @param level The level the Fighter is now on.
     * @return Either a {@link Move} instance if one has been
     *         selected or {@code null} if no move has been selected.
     */
    @Nullable
    public MoveEntry learnMove(Fighter caller, int level)
    {
        List<LearnMoveHolder> filterSafe = Arrays.asList(learnPool);
        filterSafe.removeIf((e) -> e.getLevel() > level);
        filterSafe.removeIf((e) -> caller.hasMove(e.getEntry()));
        filterSafe.sort(LearnMoveHolder::sort);

        for(LearnMoveHolder holder : filterSafe)
            if (new Random().nextFloat() <= holder.getProbability())
                return holder.getEntry();

        return null;
    }

    /**
     * @return The registry name of {@link #entityType} by calling it's
     *         {@linkplain EntityType#getRegistryName() getRegistryName()} method
     */
    @Nullable
    @Override
    public ResourceLocation getRegistryName() { return entityType.getRegistryName(); }

    @Override
    public Class<FighterEntry> getRegistryType() { return FighterEntry.class; }

    /** @return The {@link EntityType} this instance is representing*/
    public EntityType<? extends LivingEntity> getEntityType() { return entityType; }

    /**
     * This method is called by {@link TraitEntry#onFighterBuild(Fighter)}. It looks
     * through the {@link #traitDataMap} and returns the result
     *
     * @param forTrait The {@link TraitEntry} requesting tha data.
     * @return An {@link Object} array containing objects.
     */
    public Object[] requestData(TraitEntry forTrait) { return traitDataMap.get(forTrait); }

    private void gatherData(IReloadableResourceManager manager)
    {
        ResourceLocation myLocation = this.getDataLocation();
        LOGGER.debug("Loading fighter-data for {}. File path: {}", this.getRegistryName(), myLocation);

        try(IResource resource = manager.getResource(myLocation);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream())))
        {
            LOGGER.debug("Found resource for {}! Reading data now...", this.getRegistryName());
            JsonObject root = JSONUtils.fromJson(GSON, reader, JsonObject.class);
            Objects.requireNonNull(root);
            JsonElement tElem = root.get("traits");
            JsonElement smElem = root.get("startMoves");
            JsonElement lmElem = root.get("learnMoves");

            /* ---------- Read base stats begin ---------- */
            try { hp  = root.get("hp") .getAsInt(); } catch(Exception ignored) { hp = 20; }
            try { atk = root.get("atk").getAsInt(); } catch(Exception ignored) { atk = 2; }
            try { def = root.get("def").getAsInt(); } catch(Exception ignored) { def = 2; }
            try { spd = root.get("spd").getAsInt(); } catch(Exception ignored) { spd = 1; }
            /* ---------- Read base stats end ---------- */
            /* ---------- Read traits begin ---------- */
            if(tElem != null)
            {
                try
                {
                    ArrayList<TraitEntry> extraRead = new ArrayList<>();
                    JsonArray traits = tElem.getAsJsonArray();
                    this.traits = new TraitEntry[traits.size()];

                    for(int i = 0; i < traits.size(); i++)
                    {
                        JsonElement element = traits.get(i);

                        try
                        {
                            ResourceLocation regName = new ResourceLocation(element.getAsString());
                            if(regName.getNamespace().equals(MobFighters.MC))
                                regName = new ResourceLocation(MODID, regName.getPath());

                            // Won't likely happen, but just to be sure...
                            TraitEntry entry = Objects.requireNonNull(Registry.getTraitRegistry().getValue(regName));

                            if(entry.needsExtra())
                                extraRead.add(entry);
                        }
                        catch(IllegalStateException | ClassCastException e)
                        {
                            LOGGER.error("Trait element in file {} at index {} is not a string!", myLocation, i);
                            throw e;
                        }
                    }

                    for(TraitEntry entry : extraRead)
                    {
                        ArrayList<Object> attributes = new ArrayList<>();

                        try
                        {
                            JsonObject traitObj;
                            Map<String, Class<?>> extra;

                            String withDomain = Objects.requireNonNull(entry.getRegistryName()).toString();
                            String nameOnly = withDomain;

                            if(withDomain.startsWith("mobfighters"))
                                nameOnly = entry.getRegistryName().getPath();

                            traitObj = root.getAsJsonObject(withDomain);

                            if(traitObj == null)
                                traitObj = root.getAsJsonObject(nameOnly);

                            try { Objects.requireNonNull(traitObj); }
                            catch (NullPointerException e)
                            {
                                LOGGER.error("Extra info for trait {} not provided in file {}!", entry.getRegistryName(), myLocation);
                                throw e;
                            }

                            extra = Objects.requireNonNull(entry.getExtra()).getExtraInfo();

                            for(String attribute : extra.keySet())
                            {
                                Class<?> clazz = extra.get(attribute);
                                Object value = GSON.fromJson(traitObj.getAsJsonPrimitive(attribute), clazz);
                                attributes.add(value);
                            }

                            traitDataMap.put(entry, attributes.toArray(new Object[0]));
                        }
                        catch(NullPointerException e)
                        {
                            LOGGER.error("Error in file {}: Either trait {} requires info, but doesn't provide information on what," +
                                    "or file {} didn't provide extra info", myLocation, entry.getRegistryName(), myLocation);
                            throw e;
                        }
                    }

                    extraRead.clear();
                }
                catch(IllegalStateException e)
                {
                    LOGGER.warn("Found error in file {}: JSON element \"traits\" must be an array. Application of traits skipped.", myLocation);
                    warns++;
                }

            }
            /* ---------- Read Traits end ---------- */
            /* ---------- Read start moves begin ---------- */
            if(smElem != null)
            {
                try
                {
                    JsonArray startMoves = smElem.getAsJsonArray();
                    movePool = new StartMoveHolder[startMoves.size()];

                    for(int i = 0; i < startMoves.size(); i++)
                    {
                        JsonObject elem = startMoves.get(i).getAsJsonObject();
                        ResourceLocation moveId = new ResourceLocation(elem.get("moveID").getAsString());
                        float probability = 1;

                        // If probability is not available, don't care! It's optional
                        try { probability = elem.get("probability").getAsFloat(); }
                        catch(Exception ignore) {}

                        if(moveId.getNamespace().equals(MobFighters.MC))
                            moveId = new ResourceLocation(MODID, moveId.getPath());

                        MoveEntry entry = Registry.getMoveRegistry().getValue(moveId);
                        StartMoveHolder holder = new StartMoveHolder(entry, probability);
                        movePool[i] = holder;
                    }

                    Arrays.sort(movePool, StartMoveHolder::sort);
                }
                catch(IllegalStateException e)
                {
                    LOGGER.error("Found error in file {}: JSON element \"startMoves\" was not an array " +
                            "OR array elements were not JSON-Objects. Using standard move set.", myLocation);
                }

            }
            /* ---------- Read start moves end ---------- */
            /* ---------- Read learnable moves begin ---------- */
            if(lmElem != null)
            {

                try
                {
                    JsonArray learnMoves = lmElem.getAsJsonArray();
                    learnPool = new LearnMoveHolder[learnMoves.size()];

                    for(int i = 0; i < learnMoves.size(); i++)
                    {
                        JsonObject elem = learnMoves.get(i).getAsJsonObject();
                        ResourceLocation moveId = new ResourceLocation(elem.get("moveID").getAsString());
                        float probability = .1f;
                        int level = 20;

                        // If probability is not available, don't care! It's optional
                        try { probability = elem.get("probability").getAsFloat(); }
                        catch(Exception ignore) {}

                        try { level = elem.get("minLvl").getAsInt(); }
                        catch(Exception ignore) {}

                        if(moveId.getNamespace().equals(MobFighters.MC))
                            moveId = new ResourceLocation(MODID, moveId.getPath());

                        MoveEntry entry = Registry.getMoveRegistry().getValue(moveId);
                        LearnMoveHolder holder = new LearnMoveHolder(entry, probability, level);
                        learnPool[i] = holder;
                    }

                    Arrays.sort(learnPool, LearnMoveHolder::sort);
                }
                catch(IllegalStateException e)
                {
                    LOGGER.warn("Found error in file {}: JSON element \"learnMoves\" was not an array " +
                            "OR array elements were not JSON-Objects. Using standard move set.", myLocation);
                    warns++;
                }
            }
            /* ---------- Read learnable moves end ---------- */
        }
        catch (IOException e)
        {
            LOGGER.warn("Failed to load resource for {}...", this.getRegistryName());
            e.printStackTrace();
            warns++;
            return;
        }

        LOGGER.debug("Successfully loaded resources for {}!", this.getRegistryName());
    }
}
