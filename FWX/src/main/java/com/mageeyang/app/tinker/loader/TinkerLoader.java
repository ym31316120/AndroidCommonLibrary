package com.mageeyang.app.tinker.loader;


import android.app.Application;
import android.content.Intent;
import android.os.SystemClock;

public class TinkerLoader extends AbstractTinkerLoader {

    private static final String TAG = "TinkerLoader";

    @Override
    public Intent tryLoad(Application paramApplication, int paramInt, boolean paramBoolean) {
        Intent localIntent = new Intent();
        long l = SystemClock.elapsedRealtime();
//        tryLoadPatchFilesInternal(paramApplication, paramInt, paramBoolean, localIntent);
        localIntent.putExtra("intent_patch_cost_time", SystemClock.elapsedRealtime() - l);
        return localIntent;
    }
}
