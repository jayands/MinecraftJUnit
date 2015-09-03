package com.builtbroken.mc.testing.junit.world;

import com.builtbroken.mc.testing.junit.server.FakeDedicatedServer;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;

import java.io.File;

/**
 * Version of WorldServer for unit testing
 * Created by Dark on 9/3/2015.
 */
public class FakeWorldWithChunksServer extends WorldServer
{
    File rootFolder;

    public FakeWorldWithChunksServer(String name, FakeWorldSaveHandler handler, int dimID, WorldSettings settings)
    {
        super(new FakeDedicatedServer(new File(new File("."), "FakeWorldWithServer/" + name)), handler, name, dimID, settings, new Profiler());
        rootFolder = new File(new File("."), "FakeWorldWithServer/" + name);
    }

    public static FakeWorldWithChunksServer newWorld(String name)
    {
        WorldSettings settings = new WorldSettings(0, WorldSettings.GameType.SURVIVAL, false, false, WorldType.FLAT);
        WorldInfo worldInfo = new WorldInfo(settings, name);
        FakeWorldSaveHandler handler = new FakeWorldSaveHandler(worldInfo);
        if (!DimensionManager.isDimensionRegistered(100))
        {
            DimensionManager.registerProviderType(100, FakeWorldProvider.class, false);
            DimensionManager.registerDimension(100, 100);
        }
        return new FakeWorldWithChunksServer(name, handler, 100, settings);
    }

    @Override
    public File getChunkSaveLocation()
    {
        return new File(rootFolder, "save");
    }
}
