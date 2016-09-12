package com.mageeyang.app.tinker.loader.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.SystemClock;

import com.mageeyang.app.tinker.loader.TinkerLoader;
import com.mageeyang.app.tinker.loader.TinkerRuntimeException;
import com.mageeyang.app.tinker.loader.util.IntentSerializableUtil;

public class TinkerApplication extends Application {

    private static final String INTENT_PATCH_EXCEPTION = "intent_patch_exception";
    private static final int TINKER_DISABLE = 0;
    private static final String TINKER_LOADER_METHOD = "tryLoad";
    private long applicationStartElapsedTime;
    private long applicationStartMillisTime;
    private AssetManager assetManager = null;
    private ClassLoader classLoader = null;
    private Object delegate = null;
    private Class<?> delegateClass = null;
    private final String delegateClassName;
    private final String loaderClassName;
    private Resources resources = null;
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

    private void delegateConfigurationChanged(Configuration configuration) {
        if ((this.delegate != null) && (this.delegateClass != null)) {
        }
        try {
            this.delegateClass.getMethod("onConfigurationChanged", new Class[]{Configuration.class}).invoke(this.delegate, new Object[]{configuration});
            return;
        } catch (Throwable e) {

        }
    }

    private void delegateMethod(String paramString) {
        if ((this.delegate != null) && (this.delegateClass != null)) {
        }
        try {
            this.delegateClass.getMethod(paramString, new Class[0]).invoke(this.delegate, new Object[0]);
            return;
        } catch (Throwable e) {
        }
    }

    private void delegateTrimMemory(int paramInt) {
        if ((this.delegate != null) && (this.delegateClass != null)) {
        }
        try {
            this.delegateClass.getMethod("onTrimMemory", new Class[]{Integer.TYPE}).invoke(this.delegate, new Object[]{Integer.valueOf(paramInt)});
            return;
        } catch (Throwable e) {
        }
    }

    private void ensureDelegate() {

        if (this.delegate == null) {
            this.delegate = createDelegate();
        }
    }

    private void loadTinker() {
        if (this.tinkerFlags == 0) {
            return;
        }
        this.tinkerResultIntent = new Intent();
        try {
            Class localClass = Class.forName(this.loaderClassName, false, getClassLoader());
            this.tinkerResultIntent = ((Intent) localClass.getMethod("tryLoad", new Class[]{Application.class, Integer.TYPE, Boolean.TYPE})
                    .invoke(localClass.getConstructor(new Class[0]).newInstance(new Object[0]), new Object[]{this, Integer.valueOf(this.tinkerFlags), Boolean.valueOf(this.tinkerLoadVerifyFlag)}));
            return;
        } catch (Throwable e) {
            IntentSerializableUtil.setReturnCode(this.tinkerResultIntent, -19);
            this.tinkerResultIntent.putExtra("intent_patch_exception", e);
        }
    }

    private void onBaseContextAttached(Context context)
    {
        this.applicationStartElapsedTime = SystemClock.elapsedRealtime();
        this.applicationStartMillisTime = System.currentTimeMillis();
        loadTinker();
        ensureDelegate();
        try
        {
            this.delegateClass.getMethod("onBaseContextAttached", new Class[] { Context.class }).invoke(this.delegate, new Object[] { context });
            return;
        }
        catch (Throwable e) {}
    }

    protected final void attachBaseContext(Context context)
    {
        super.attachBaseContext(context);
        onBaseContextAttached(context);
    }

    public AssetManager getAssets()
    {
        if (this.assetManager != null) {
            return this.assetManager;
        }
        return super.getAssets();
    }

    public ClassLoader getClassLoader()
    {
        if (this.classLoader != null) {
            return this.classLoader;
        }
        return super.getClassLoader();
    }

    public Resources getResources()
    {
        if (this.resources != null) {
            return this.resources;
        }
        return super.getResources();
    }

    public void onConfigurationChanged(Configuration paramConfiguration)
    {
        super.onConfigurationChanged(paramConfiguration);
        delegateConfigurationChanged(paramConfiguration);
    }

    public final void onCreate()
    {
        super.onCreate();
        ensureDelegate();
        delegateMethod("onCreate");
    }

    public final void onLowMemory()
    {
        super.onLowMemory();
        delegateMethod("onLowMemory");
    }

    public final void onTerminate()
    {
        super.onTerminate();
        delegateMethod("onTerminate");
    }

    @TargetApi(14)
    public final void onTrimMemory(int paramInt)
    {
        super.onTrimMemory(paramInt);
        delegateTrimMemory(paramInt);
    }



}
