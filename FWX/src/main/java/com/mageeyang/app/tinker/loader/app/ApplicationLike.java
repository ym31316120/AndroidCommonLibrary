package com.mageeyang.app.tinker.loader.app;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;

public abstract class ApplicationLike implements ApplicationLifeCycle {

    private final Application application;
    private final long applicationStartElapsedTime;
    private final long applicationStartMillisTime;
    private AssetManager assetManager;
    private ClassLoader classLoader;
    private Resources resources;
    private final int tinkerFlags;
    private final boolean tinkerLoadVerifyFlag;
    private final Intent tinkerResultIntent;

    public ApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                           long applicationStartElapsedTime, long applicationStartMillisTime,
                           Intent tinkerResultIntent, Resources resources,
                           ClassLoader classLoader, AssetManager assetManager) {
        this.application = application;
        this.tinkerFlags = tinkerFlags;
        this.tinkerLoadVerifyFlag = tinkerLoadVerifyFlag;
        this.applicationStartElapsedTime = applicationStartElapsedTime;
        this.applicationStartMillisTime = applicationStartMillisTime;
        this.tinkerResultIntent = tinkerResultIntent;
        this.resources = resources;
        this.classLoader = classLoader;
        this.assetManager = assetManager;
    }

    public Application getApplication()
    {
        return this.application;
    }
    public long getApplicationStartElapsedTime()
    {
        return this.applicationStartElapsedTime;
    }

    public long getApplicationStartMillisTime()
    {
        return this.applicationStartMillisTime;
    }

    public final int getTinkerFlags()
    {
        return this.tinkerFlags;
    }

    public final boolean getTinkerLoadVerifyFlag()
    {
        return this.tinkerLoadVerifyFlag;
    }

    public final Intent getTinkerResultIntent()
    {
        return this.tinkerResultIntent;
    }

    public void setAssetManager(AssetManager assetManager)
    {
        this.assetManager = assetManager;
    }

    public void setClassLoader(ClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }

    public void setResources(Resources resources)
    {
        this.resources = resources;
    }

    public void onBaseContextAttached(Context context) {}

    public void onConfigurationChanged(Configuration configuration) {}

    public void onCreate() {}

    public void onLowMemory() {}

    public void onTerminate() {}

    public void onTrimMemory(int level) {}
}
