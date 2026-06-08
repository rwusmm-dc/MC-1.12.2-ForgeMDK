package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.Vec3d;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AFKBotService
{
    private static AFKBotService instance;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> movementTask;
    private ScheduledFuture<?> actionTask;
    private AtomicInteger fiveMinuteCounter;
    private boolean isActive;

    private static final float FORWARD_SPEED = 1.0f;
    private static final float ZERO_SPEED = 0.0f;
    private static final int MOVEMENT_INTERVAL = 5; // seconds
    private static final int ACTION_INTERVAL = 5 * 60; // 5 minutes in seconds

    private AFKBotService()
    {
        scheduler = Executors.newScheduledThreadPool(2);
        fiveMinuteCounter = new AtomicInteger(0);
        isActive = false;
    }

    public static AFKBotService getInstance()
    {
        if (instance == null)
        {
            instance = new AFKBotService();
        }
        return instance;
    }

    public void start()
    {
        if (isActive)
        {
            return;
        }

        isActive = true;
        fiveMinuteCounter.set(0);

        // Start movement task (every 5 seconds)
        movementTask = scheduler.scheduleAtFixedRate(this::performMovement, 0, MOVEMENT_INTERVAL, TimeUnit.SECONDS);

        // Start action task (every 5 minutes)
        actionTask = scheduler.scheduleAtFixedRate(this::performAction, ACTION_INTERVAL, ACTION_INTERVAL, TimeUnit.SECONDS);
    }

    public void stop()
    {
        if (!isActive)
        {
            return;
        }

        isActive = false;

        if (movementTask != null)
        {
            movementTask.cancel(false);
            movementTask = null;
        }

        if (actionTask != null)
        {
            actionTask.cancel(false);
            actionTask = null;
        }

        fiveMinuteCounter.set(0);
    }

    private void performMovement()
    {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null)
        {
            return;
        }

        // Move forward 2 blocks
        player.motionX += Math.cos(Math.toRadians(player.rotationYaw + 90)) * 0.1;
        player.motionZ += Math.sin(Math.toRadians(player.rotationYaw + 90)) * 0.1;

        // Schedule backward movement after 2.5 seconds
        scheduler.schedule(() -> {
            EntityPlayerSP p = Minecraft.getMinecraft().player;
            if (p != null)
            {
                // Move backward 2 blocks (opposite direction)
                p.motionX -= Math.cos(Math.toRadians(p.rotationYaw + 90)) * 0.1;
                p.motionZ -= Math.sin(Math.toRadians(p.rotationYaw + 90)) * 0.1;
            }
        }, 2500, TimeUnit.MILLISECONDS);
    }

    private void performAction()
    {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null)
        {
            return;
        }

        // Perform jump
        player.jump();

        // Perform punch (left click)
        Minecraft mc = Minecraft.getMinecraft();
        mc.playerController.attackEntity(player, player);
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void shutdown()
    {
        stop();
        scheduler.shutdownNow();
    }
}
