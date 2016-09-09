package com.mageeyang.app.tinker.loader;


import android.app.Application;
import android.content.Intent;

public abstract class AbstractTinkerLoader {
    public abstract Intent tryLoad(Application paramApplication, int paramInt, boolean paramBoolean);
}
