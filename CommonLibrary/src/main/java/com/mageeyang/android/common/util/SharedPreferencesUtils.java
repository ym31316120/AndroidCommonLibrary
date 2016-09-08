
package com.mageeyang.android.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.ArrayMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * SharePreferences Tool
 * 加密的过程暂时没加
 *
 * @author Magee Yang
 * @datetime 2016/09/07
 */
public class SharedPreferencesUtils {


    /**
     * 向SharedPreferences里放key-value的值
     * 默认不加密，处理默认的xml文件名
     * @param context       上下文对象
     * @param key           key值
     * @param value         value值
     * @return              返回存储的状态boolean类型
     */
    public static boolean putValue(Context context, String key, Object value) {
        return putValue(context, null, key, value);
    }

    /**
     * 向SharedPreferences里放key-value的值
     * @param context       上下文对象
     * @param key           key值
     * @param value         value值
     * @param isKey         是否对key和value加密处理
     * @return              返回存储的状态boolean类型
     */
    public static boolean putValue(Context context, String key, Object value, boolean isKey) {
        return putValue(context, null, key, value,isKey);
    }

    /**
     * 向SharedPreferences里放key-value的值
     * @param context       上下文对象
     * @param key           key值
     * @param value         value值
     * @param keyValueEncrypt   控制加密的状态数组，[false,true],第一个控制key的加密，第二个控制value的加密
     * @return              返回存储的状态boolean类型
     */
    public static boolean putValue(Context context, String key, Object value, boolean[] keyValueEncrypt) {
        return putValue(context, null, key, value, keyValueEncrypt);
    }

    /**
     * 向SharedPreferences里放key-value的值
     * 默认不加密
     * @param context       上下文对象
     * @param name          指定要修改的xml文件名
     * @param key           key值
     * @param value         value值
     * @return              返回存储的状态boolean类型
     */

    public static boolean putValue(Context context, String name, String key, Object value) {
        return putValue(context, name, key, value, false);
    }

    /**
     * 向SharedPreferences里放key-value的值
     * @param context       上下文对象
     * @param name          指定要修改的xml文件名
     * @param key           key值
     * @param value         value值
     * @param isKey         是否对key和value加密处理
     * @return              返回存储的状态boolean类型
     */

    public static boolean putValue(Context context, String name, String key, Object value, boolean isKey) {
        return putValue(context, name, key, value, new boolean[]{isKey, isKey});
    }

    public static boolean putValue(Context context, String name, String key, Object value, boolean[] keyValueEncrypt) {
        return putValue(context, name, key, value, Context.MODE_PRIVATE, keyValueEncrypt);
    }

    public static boolean putValue(Context context, String name, String key, Object value, int mode, boolean[] keyValueEncrypt) {
        ArrayMap<String, Object> map = new ArrayMap<String, Object>();
        map.put(key, value);
        return putValue(context, name, map,mode, keyValueEncrypt);
    }

    public static boolean putValue(Context context, String name, Map<String, Object> map, boolean[] keyValueEncrypt) {
        return putValue(context, name, map, Context.MODE_PRIVATE, keyValueEncrypt);
    }

    /**
     * 向SharedPreferences里放key-value的值
     *
     * @param context           上下文对象
     * @param name              xml文件的名字，如果为空则获取默认名字
     * @param map               要存放key-value的map对象
     * @param mode              xml文件的读取方式
     * @param keyValueEncrypt   控制加密的状态数组，[false,true],第一个控制key的加密，第二个控制value的加密
     * @return                  返回存储的状态boolean类型
     */
    public static boolean putValue(Context context, String name, Map<String, Object> map, int mode, boolean[] keyValueEncrypt) {
        SharedPreferences sp = null;
        if (TextUtils.isEmpty(name)) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            sp = context.getSharedPreferences(name, mode);
        }

        Editor editor = sp.edit();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            String key = keyValueEncrypt[0] ? entry.getKey() : entry.getKey();

            if (keyValueEncrypt[1] && !(value instanceof Set)) {

            } else {
                if (value instanceof Boolean) {
                    editor.putBoolean(key, Boolean.parseBoolean(String.valueOf(value)));
                } else if (value instanceof Float) {
                    editor.putFloat(key, Float.parseFloat(String.valueOf(value)));
                } else if (value instanceof Integer) {
                    editor.putInt(key, Integer.parseInt(String.valueOf(value)));
                } else if (value instanceof Long) {
                    editor.putLong(key, Long.parseLong(String.valueOf(value)));
                } else if (value instanceof String) {
                    editor.putString(key, String.valueOf(value));
                } else if (value instanceof Set) {
                    if (keyValueEncrypt[1]) {
                        Set<String> sets = (Set<String>) value;
                        Set<String> tempSets = new HashSet<String>();
                        for (String s : sets) {
//                          tempSets.add(SecurityUtils.DESencrypt(String.valueOf(s), SECURITY_KEY));
                            tempSets.add(s);
                        }
                        editor.putStringSet(key, tempSets);
                    } else {
                        editor.putStringSet(key, (Set<String>) value);
                    }
                } else {
                    throw new IllegalArgumentException("Value type is not support(不支持传递的Value值类型)");
                }
            }
        }
        return editor.commit();
    }

    /**
     * 从默认的SharePreferences中移除key-value的值
     *
     * @param context   上下文
     * @param key       要移除的key值
     * @return          返回是否移除成功状态
     */
    public static boolean removeKey(Context context, String key) {
        return removeKey(context, null, key);
    }

    /**
     * 从SharePreferences中移除key-value的值
     * @param context       上下文
     * @param name          指定的xml文件名
     * @param key           要移除的key值
     * @return              返回是否移除成功状态
     */
    public static boolean removeKey(Context context, String name, String key) {
        return removeKey(context, name, key, false);
    }

    public static boolean removeKey(Context context, String name, String key, boolean isKeyEncrypt) {
        return removeKey(context, name, key, Context.MODE_PRIVATE, isKeyEncrypt);
    }

    /**
     * 从SharePreferences中移除key-value的值
     * @param context       上下文
     * @param name          指定要移除内容的xml文件名
     * @param key           要移除的key值
     * @param mode          获取xml的模式
     * @param isKeyEncrypt  key值是否加密
     * @return  返回处理状态boolean类型
     */
    public static boolean removeKey(Context context, String name, String key, int mode, boolean isKeyEncrypt) {
        SharedPreferences preferences = null;
        if (TextUtils.isEmpty(name)) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            preferences = context.getSharedPreferences(name, mode);
        }

        Editor editor = preferences.edit();
        editor.remove(isKeyEncrypt ? key: key);
        return editor.commit();
    }

    /**
     * 从默认的SharePreferences中获取String类型的值
     *
     * @param context       上下文
     * @param key           key值
     * @param defValue      没有失败的默认值
     * @return              返回key对应的value值
     */
    public static String getString(Context context, String key, String defValue) {
        return getString(context, null, key, defValue);
    }

    /**
     * 从SharePreferences中获取String类型的值
     * 默认不加密
     * @param context       上下文
     * @param name          指定xml文件名
     * @param key           key值
     * @param defValue      获取失败的默认值
     * @return              返回key对应的value值
     */
    public static String getString(Context context, String name, String key, String defValue) {
        return getString(context, name, key, defValue, false);
    }

    public static String getString(Context context, String name, String key, String defValue, boolean isKey) {
        return getString(context, name, key, defValue, new boolean[]{isKey, isKey});
    }

    public static String getString(Context context, String name, String key, String defValue, boolean[] keyValueEncrypt) {
        return getString(context, name, key, defValue, Context.MODE_PRIVATE, keyValueEncrypt);
    }

    public static String getString(Context context, String name, String key, String defValue, int mode, boolean[] keyValueEncrypt) {
        SharedPreferences preferences = null;
        if (TextUtils.isEmpty(name)) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            preferences = context.getSharedPreferences(name, mode);
        }

        String value = preferences.getString(keyValueEncrypt[0] ? key : key, defValue);
        if (value.equals(defValue)) {
            return value;
        } else {
            return keyValueEncrypt[1] ? value : value;
        }
    }

    /**
     * 从SharePreferences中获取Int类型的值
     *
     * @param context       上下文
     * @param key           key值
     * @param defValue      没有失败的默认值
     * @return              返回key对应的value值
     */
    public static int getInt(Context context, String key, int defValue) {
        return getInt(context, null, key, defValue);
    }

    public static int getInt(Context context, String name, String key, int defValue) {
        return getInt(context, name, key, defValue, false);
    }

    public static int getInt(Context context, String name, String key, int defValue,boolean isKey) {
        return getInt(context, name, key, defValue, new boolean[]{isKey, isKey});
    }

    public static int getInt(Context context, String name, String key, int defValue, boolean[] keyValueEncrypt) {
        return getInt(context, name, key, defValue, Context.MODE_PRIVATE, keyValueEncrypt);
    }

    public static int getInt(Context context, String name, String key, int defValue, int mode, boolean[] keyValueEncrypt) {
        SharedPreferences preferences = null;
        if (TextUtils.isEmpty(name)) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            preferences = context.getSharedPreferences(name, mode);
        }

        if (keyValueEncrypt[1]) {
            String value = getString(context, name, key, String.valueOf(defValue), mode, keyValueEncrypt);
            try {
                return Integer.valueOf(value);
            } catch (Exception e) {
                return defValue;
            }
        } else {
            return preferences.getInt(keyValueEncrypt[0] ? key : key, defValue);
        }
    }

    /**
     * 从SharePreferences中获取Long类型的值
     *
     * @param context       上下文
     * @param key           key值
     * @param defValue      没有失败的默认值
     * @return              返回key对应的value值
     */
    public static long getLong(Context context, String key, long defValue) {
        return getLong(context, null, key, defValue);
    }

    public static long getLong(Context context, String name, String key, long defValue) {
        return getLong(context, name, key, defValue, false);
    }

    public static long getLong(Context context, String name, String key, long defValue,boolean isKey) {
        return getLong(context, name, key, defValue, new boolean[]{isKey, isKey});
    }

    public static long getLong(Context context, String name, String key, long defValue, boolean[] keyValueEncrypt) {
        return getLong(context, name, key, defValue, Context.MODE_PRIVATE, keyValueEncrypt);
    }

    public static long getLong(Context context, String name, String key, long defValue, int mode, boolean[] keyValueEncrypt) {
        SharedPreferences preferences = null;
        if (TextUtils.isEmpty(name)) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            preferences = context.getSharedPreferences(name, mode);
        }

        if (keyValueEncrypt[1]) {
            String value = getString(context, name, key, String.valueOf(defValue), mode, keyValueEncrypt);

            try {
                return Long.valueOf(value);
            } catch (Exception e) {
                return defValue;
            }
        } else {
            return preferences.getLong(keyValueEncrypt[0] ? key : key, defValue);
        }
    }

    /**
     * 从SharePreferences中获取Float类型的值
     *
     * @param context       上下文
     * @param key           key值
     * @param defValue      没有失败的默认值
     * @return              返回key对应的value值
     */
    public static float getFloat(Context context, String key, float defValue) {
        return getFloat(context, null, key, defValue);
    }

    public static float getFloat(Context context, String name, String key, float defValue) {
        return getFloat(context, name, key, defValue, false);
    }

    public static float getFloat(Context context, String name, String key, float defValue,boolean isKey) {
        return getFloat(context, name, key, defValue, new boolean[]{isKey, isKey});
    }

    public static float getFloat(Context context, String name, String key, float defValue, boolean[] keyValueEncrypt) {
        return getFloat(context, name, key, defValue, Context.MODE_PRIVATE, keyValueEncrypt);
    }

    public static float getFloat(Context context, String name, String key, float defValue, int mode, boolean[] keyValueEncrypt) {
        SharedPreferences preferences = null;
        if (TextUtils.isEmpty(name)) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            preferences = context.getSharedPreferences(name, mode);
        }

        if (keyValueEncrypt[1]) {
            String value = getString(context, name, key, String.valueOf(defValue), mode, keyValueEncrypt);
            try {
                return Float.valueOf(value);
            } catch (Exception e) {
                return defValue;
            }
        } else {
            return preferences.getFloat(keyValueEncrypt[0] ? key : key, defValue);
        }

    }

    /**
     * 从SharePreferences中获取boolean类型的值
     *
     * @param context       上下文
     * @param key           key值
     * @param defValue      没有失败的默认值
     * @return              返回key对应的value值
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getBoolean(context, null, key, defValue);
    }

    public static boolean getBoolean(Context context, String name, String key, boolean defValue) {
        return getBoolean(context, name, key, defValue, false);
    }

    public static boolean getBoolean(Context context, String name, String key, boolean defValue,boolean isKey) {
        return getBoolean(context, name, key, defValue, new boolean[]{isKey, isKey});
    }

    public static boolean getBoolean(Context context, String name, String key, boolean defValue, boolean[] keyValueEncrypt) {
        return getBoolean(context, name, key, defValue, Context.MODE_PRIVATE, keyValueEncrypt);
    }

    public static boolean getBoolean(Context context, String name, String key, boolean defValue, int mode, boolean[] keyValueEncrypt) {
        SharedPreferences preferences = null;
        if (TextUtils.isEmpty(name)) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            preferences = context.getSharedPreferences(name, mode);
        }

        if (keyValueEncrypt[1]) {
            String valueString = getString(context, name, key, String.valueOf(defValue), mode, keyValueEncrypt);
            try {
                return Boolean.valueOf(valueString);
            } catch (Exception e) {
                return defValue;
            }
        } else {
            return preferences.getBoolean(keyValueEncrypt[0] ? key : key, defValue);
        }
    }

    /**
     * 从SharePreferences中获取StringSet类型的值
     *
     * @param context       上下文
     * @param key           key值
     * @param defValues     没有失败的默认值
     * @return              返回key对应的value值
     */
    public static Set<String> getStringSet(Context context, String key, Set<String> defValues) {
        return getStringSet(context, null, key, defValues);
    }

    public static Set<String> getStringSet(Context context, String name, String key, Set<String> defValues) {
        return getStringSet(context, name, key, defValues, false);
    }

    public static Set<String> getStringSet(Context context, String name, String key, Set<String> defValues,boolean isKey) {
        return getStringSet(context, name, key, defValues, new boolean[]{isKey, isKey});
    }

    public static Set<String> getStringSet(Context context, String name, String key, Set<String> defValues, boolean[] keyValueEncrypt) {
        return getStringSet(context, name, key, defValues, Context.MODE_PRIVATE, keyValueEncrypt);
    }

    /**
     * 从SharePreferences中获取StringSet类型的值
     * @param context       上下文
     * @param name          指定xml文件名
     * @param key           key值
     * @param defValues     默认值
     * @param mode          文件读取模式
     * @return              返回key对应的value值
     */
    public static Set<String> getStringSet(Context context, String name, String key, Set<String> defValues, int mode, boolean[] keyValueEncrypt) {
        SharedPreferences preferences = null;
        if (TextUtils.isEmpty(name)) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            preferences = context.getSharedPreferences(name, mode);
        }
        Set<String> valueSet = preferences.getStringSet(keyValueEncrypt[0] ? key : key, defValues);
        Set<String> tempValueSet = new HashSet<String>();
        for (String s : valueSet) {
            tempValueSet.add(s);
        }
        return tempValueSet;
    }

    private SharedPreferencesUtils(){/*Do not new me*/};
}
