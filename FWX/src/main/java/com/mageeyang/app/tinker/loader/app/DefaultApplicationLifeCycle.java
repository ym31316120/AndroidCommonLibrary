package com.mageeyang.app.tinker.loader.app;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;

public class DefaultApplicationLifeCycle extends ApplicationLike {

    private static final String TAG = "DefaultLifeCycle";

    public DefaultApplicationLifeCycle(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                                       long applicationStartElapsedTime, long applicationStartMillisTime,
                                       Intent tinkerResultIntent, Resources resources,
                                       ClassLoader classLoader, AssetManager assetManager) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent, resources, classLoader, assetManager);
    }

    public void onBaseContextAttached(Context context) {}

    public void onConfigurationChanged(Configuration configuration)
    {
        new StringBuilder("onConfigurationChanged:").append(configuration.toString());
    }

    public void onCreate() {}

    public void onLowMemory() {}

    public void onTerminate() {}

    public void onTrimMemory(int level) {}
}
