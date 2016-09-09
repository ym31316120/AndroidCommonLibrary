package com.mageeyang.app.tinker.loader.util;

import android.content.Intent;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 获取Intent中Serializable对象
 * d
 * @author MageeYang
 * @date 2016/09/08
 */
public class IntentSerializableUtil {

    @Nullable
    public static Serializable getSerializableExtra(Intent paramIntent, String paramString) {
        if (paramIntent == null) {
            return null;
        }
        try {
            Serializable value = null;
            value = paramIntent.getSerializableExtra(paramString);
            return value;
        } catch (Exception e) {
            new StringBuilder("getSerializableExtra exception:").append(e.getMessage());
        }
        return null;
    }

    @Nullable
    public static String getStringExtra(Intent paramIntent, String paramString) {
        if (paramIntent == null) {
            return null;
        }
        try {
            String value = null;
            value = paramIntent.getStringExtra(paramString);
            return value;
        } catch (Exception e) {
            new StringBuilder("getStringExtra exception:").append(e.getMessage());
        }
        return null;
    }

    @Nullable
    public static HashMap<String, String> getLibsPath(Intent paramIntent) {
        Serializable value = null;
        value = getSerializableExtra(paramIntent, "intent_patch_libs_path");
        if (value != null) {
            return (HashMap) value;
        }
        return null;
    }

    @Nullable
    public static HashMap<String, String> getPackageConfig(Intent paramIntent) {
        Serializable value = null;
        value = getSerializableExtra(paramIntent, "intent_patch_package_config");
        if (value != null) {
            return (HashMap) value;
        }
        return null;
    }

    public static void setReturnCode(Intent paramIntent, int paramInt) {
        paramIntent.putExtra("intent_return_code", paramInt);
    }

    public static int getIntExtra(Intent paramIntent, String paramString)
    {
        if (paramIntent == null) {
            return -10000;
        }
        try
        {
            int i = paramIntent.getIntExtra(paramString, -10000);
            return i;
        }
        catch (Exception e)
        {
            new StringBuilder("getIntExtra exception:").append(e.getMessage());
        }
        return -10000;
    }

    public static boolean getBooleanExtra(Intent paramIntent, String paramString)
    {
        if (paramIntent == null) {
            return false;
        }
        try
        {
            boolean bool = paramIntent.getBooleanExtra(paramString, false);
            return bool;
        }
        catch (Exception e)
        {
            new StringBuilder("getBooleanExtra exception:").append(e.getMessage());
        }
        return false;
    }

}
