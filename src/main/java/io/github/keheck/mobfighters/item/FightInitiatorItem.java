package io.github.keheck.mobfighters.item;

import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.util.network.FightStartPacket;
import io.github.keheck.mobfighters.util.network.MobfightersNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;

public class FightInitiatorItem extends Item
{
    public FightInitiatorItem()
    {
        super(new Properties().group(ItemGroup.MISC).maxStackSize(1));
        setRegistryName(MobFighters.MODID, "fight_initiator");
    }


    @Override
    @SuppressWarnings("NullableProblems")
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if(worldIn.isRemote)
        {
            //MobfightersNetworkHandler.CHANNEL.sendToServer(new FightStartPacket(2, playerIn, playerIn));
            //GUIDisplayHelper.displayGuiScreen(GUIDisplayHelper.ScreenType.FIGHT, worldIn);
        }
        else
        {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity)playerIn;

            // I know, there are convenient methods for sending packets to clients.
            MobfightersNetworkHandler.CHANNEL.sendTo
                    (
                            new FightStartPacket(1, playerIn, playerIn),
                            serverPlayer.connection.getNetworkManager(),
                            NetworkDirection.PLAY_TO_CLIENT
                    );
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
