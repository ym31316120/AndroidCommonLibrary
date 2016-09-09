package com.mageeyang.app.tinker.loader.app;


import android.content.Context;
import android.content.res.Configuration;

public  interface ApplicationLifeCycle {

    void onBaseContextAttached(Context context);

    /**
     * 配置改变时触发这个方法
     * @param configuration
     */
    void onConfigurationChanged(Configuration configuration);

    /**
     * 这个函数是当我们的应用开始之时就被调用了，比应用中的其他对象创建的早，
     * 这个实现尽可能的快一点，因为这个时间直接影响到我们第一个activity/service
     */
    void onCreate();

    /**
     * 低内存的时候执行
     */
    void onLowMemory();

    /**
     * 程序终止的时候执行
     */
    void onTerminate();

    /**
     * 程序在内存清理的时候执行
     * @param level
     */
    void onTrimMemory(int level);
}
