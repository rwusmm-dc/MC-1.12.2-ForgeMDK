package com.example.examplemod;

public class AFKBotService
{
    private static AFKBotService instance;
    private boolean isActive;

    private AFKBotService()
    {
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
        isActive = true;
    }

    public void stop()
    {
        isActive = false;
    }

    public boolean isActive()
    {
        return isActive;
    }
}
