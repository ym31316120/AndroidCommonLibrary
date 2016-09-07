
package com.mageeyang.android.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Map;

/**
 * SharePreferences Tool
 * @author Magee Yang
 * @datetime 2016/09/07
 */
public class SharedPreferencesUtils {


    public static boolean putValue(Context context, String name, Map<String,Object> map,int mode,boolean [] keyValueEncrypt){
        SharedPreferences sp = null;
        if(TextUtils.isEmpty(name)){
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        }else{
            sp = context.getSharedPreferences(name,mode);
        }

        Editor editor = sp.edit();
        for(Map.Entry<String,Object> entry : map.entrySet()){
            Object value = entry.getValue();
            String key = keyValueEncrypt[0]?entry.getKey():entry.getKey();
        }

        return true;
    }
}
