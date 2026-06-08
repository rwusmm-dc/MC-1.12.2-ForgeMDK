package com.example.examplemod;

import net.minecraftforge.fml.common.eventbus.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

@SideOnly(Side.CLIENT)
public class AFKBotTickHandler
{
    private static int movementTicks = 0;
    private static int actionTicks = 0;
    private static final int MOVEMENT_INTERVAL = 100; // 5 seconds (100 ticks at 20 TPS)
    private static final int ACTION_INTERVAL = 6000; // 5 minutes (6000 ticks at 20 TPS)
    private static final int MOVEMENT_DURATION = 50; // 2.5 seconds forward, 2.5 seconds back

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END)
        {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if (player == null || !AFKBotService.getInstance().isActive())
        {
            return;
        }

        // Handle movement
        movementTicks++;
        if (movementTicks >= MOVEMENT_INTERVAL)
        {
            movementTicks = 0;
        }

        // Move forward for first 2.5 seconds, backward for next 2.5 seconds
        if (movementTicks < MOVEMENT_DURATION)
        {
            // Move forward by simulating movement input
            player.movementInput.moveForward = 1.0f;
        }
        else if (movementTicks < MOVEMENT_DURATION * 2)
        {
            // Move backward
            player.movementInput.moveForward = -1.0f;
        }

        // Handle actions (jump and punch)
        actionTicks++;
        if (actionTicks >= ACTION_INTERVAL)
        {
            actionTicks = 0;

            // Jump
            if (player.onGround)
            {
                player.jump();
            }

            // Punch (swing arm)
            player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}
