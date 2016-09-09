package com.mageeyang.app.tinker.loader.app;

import android.app.Application;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.mageeyang.app.tinker.loader.TinkerLoader;
import com.mageeyang.app.tinker.loader.TinkerRuntimeException;

public class TinkerApplication extends Application {

    private static final String INTENT_PATCH_EXCEPTION = "intent_patch_exception";
    private static final int TINKER_DISABLE = 0;
    private static final String TINKER_LOADER_METHOD = "tryLoad";
    private long applicationStartElapsedTime;
    private long applicationStartMillisTime;
    private AssetManager[] assetManager = new AssetManager[1];
    private ClassLoader[] classLoader = new ClassLoader[1];
    private Object delegate = null;
    private Class<?> delegateClass = null;
    private final String delegateClassName;
    private final String loaderClassName;
    private Resources[] resources = new Resources[1];
    private final int tinkerFlags;
    private final boolean tinkerLoadVerifyFlag;
    private Intent tinkerResultIntent;

    protected TinkerApplication(int tinkerFlags) {
        this(tinkerFlags, "com.tencent.tinker.loader.app.DefaultApplicationLifeCycle", TinkerLoader.class.getName(), false);
    }

    protected TinkerApplication(int tinkerFlags, String delegateClassName) {
        this(tinkerFlags, delegateClassName, TinkerLoader.class.getName(), false);
    }

    public TinkerApplication(int tinkerFlags, String delegateClassName, String loaderClassName, boolean tinkerLoadVerifyFlag) {
        this.tinkerFlags = tinkerFlags;
        this.delegateClassName = delegateClassName;
        this.loaderClassName = loaderClassName;
        this.tinkerLoadVerifyFlag = tinkerLoadVerifyFlag;
    }

    private Object createDelegate() {
        try {
            this.delegateClass = Class.forName(this.delegateClassName, false, getClassLoader());
            Object localObject = this.delegateClass.getConstructor(new Class[]{Application.class, Integer.TYPE, Boolean.TYPE, Long.TYPE, Long.TYPE, Intent.class, android.content.res.Resources.class, java.lang.ClassLoader.class,
                    android.content.res.AssetManager.class}).
                    newInstance(new Object[]{this, Integer.valueOf(this.tinkerFlags), Boolean.valueOf(this.tinkerLoadVerifyFlag), Long.valueOf(this.applicationStartElapsedTime), Long.valueOf(this.applicationStartMillisTime), this.tinkerResultIntent, this.resources, this.classLoader, this.assetManager});
            return localObject;
        } catch (Exception localException) {
            throw new TinkerRuntimeException("createDelegate failed", localException);
        }
    }
}
