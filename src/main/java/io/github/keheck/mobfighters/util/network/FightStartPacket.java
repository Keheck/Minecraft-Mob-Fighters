package io.github.keheck.mobfighters.util.network;

import io.github.keheck.mobfighters.MobFighters;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Direction: Server -> Client
 */
public class FightStartPacket
{
    private int fightID;
    private boolean enemyWild;
    private UUID playerUUID;
    private UUID enemyUUID;

    private static int id = 0;

    public FightStartPacket(int id, PlayerEntity player, LivingEntity enemy)
    {
        this.fightID = id;
        this.playerUUID = player.getUniqueID();
        enemyWild = !(enemy instanceof PlayerEntity);

        if(enemyWild)
            this.enemyUUID = new UUID(0, enemy.getEntityId());
        else
            this.enemyUUID = enemy.getUniqueID();
    }

    private FightStartPacket() { }

    public static void encode(FightStartPacket packet, PacketBuffer buffer)
    {
        for(int i = 0; i < 4; i++)
        {
            // Put the least significant bits in the buffer
            boolean putLeast = i%2 == 0;

            // Put the player in the buffer
            if(i < 2)
            {
                if(putLeast)
                    buffer.setLong(i*Long.BYTES, packet.playerUUID.getLeastSignificantBits());
                else
                    buffer.setLong(i*Long.BYTES, packet.playerUUID.getMostSignificantBits());
            }
            // Put the enemy in the buffer
            else
            {
                if(putLeast)
                    buffer.setLong(i*Long.BYTES, packet.enemyUUID.getLeastSignificantBits());
                else
                    buffer.setLong(i*Long.BYTES, packet.enemyUUID.getMostSignificantBits());
            }
        }

        buffer.setInt(Long.BYTES*4, packet.fightID);
        buffer.setBoolean(Long.BYTES*4 + Integer.BYTES, packet.enemyWild);
    }

    public static FightStartPacket decode(PacketBuffer buffer)
    {
        FightStartPacket packet = new FightStartPacket();

        long playerLeast = buffer.getLong(0);
        long playerMost  = buffer.getLong(Long.BYTES);
        long enemyLeast  = buffer.getLong(Long.BYTES*2);
        long enemyMost   = buffer.getLong(Long.BYTES*3);

        packet.playerUUID = new UUID(playerMost, playerLeast);
        packet.enemyUUID = new UUID(enemyMost, enemyLeast);
        packet.fightID = buffer.getInt(Long.BYTES*4);
        packet.enemyWild = buffer.getBoolean(Long.BYTES*4 + Integer.BYTES);

        return packet;
    }

    public static void handle(FightStartPacket packet, Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context ctx = supplier.get();

        if(ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT)
        {
            MobFighters.LOGGER.debug("RECIEVED CLIENT-SIDE PACKET!");
        }

        ctx.setPacketHandled(true);
    }
}
