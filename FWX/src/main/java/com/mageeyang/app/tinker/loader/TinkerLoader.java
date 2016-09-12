package com.mageeyang.app.tinker.loader;


import android.app.Application;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.mageeyang.app.tinker.loader.util.FileUtil;
import com.mageeyang.app.tinker.loader.util.IntentSerializableUtil;
import com.mageeyang.app.tinker.loader.util.ParamUtil;

import java.io.File;

public class TinkerLoader extends AbstractTinkerLoader {

    private static final String TAG = "TinkerLoader";

    private f patchInfo;

    private void tryLoadPatchFilesInternal(Application application, int type, boolean paramBoolean, Intent intent)
    {
        if (!ParamUtil.isNotZero(type))
        {
            IntentSerializableUtil.setReturnCode(intent, -1);
            return;
        }
        File dataDir = FileUtil.getDataDir(application);
        if (dataDir == null)
        {
            IntentSerializableUtil.setReturnCode(intent, -2);
            return;
        }
        String dataDirPath = dataDir.getAbsolutePath();
        if (!dataDir.exists())
        {
            IntentSerializableUtil.setReturnCode(intent, -2);
            return;
        }
        File patchInfo = FileUtil.createPatchInfo(dataDirPath);
        if (!patchInfo.exists())
        {
            Log.v(TAG,"tryLoadPatchFiles:patch info not exist:"+patchInfo.getAbsolutePath());
            IntentSerializableUtil.setReturnCode(intent, -3);
            return;
        }
        File infoLock = FileUtil.createInfoLock(dataDirPath);
//        this.patchInfo = f.h(localFile1, localFile2);
//        if (this.patchInfo == null)
//        {
//            d.a(paramIntent, -4);
//            return;
//        }
//        localObject1 = this.patchInfo.nvO;
//        String str = this.patchInfo.nvP;
//        if ((localObject1 == null) || (str == null))
//        {
//            d.a(paramIntent, -4);
//            return;
//        }
        intent.putExtra("intent_patch_old_version", (String)localObject1);
        intent.putExtra("intent_patch_new_version", str);
        boolean bool1 = h.gs(paramApplication);
        int i;
        if (!((String)localObject1).equals(str))
        {
            i = 1;
            label205:
            if ((i == 0) || (!bool1)) {
                break label500;
            }
            localObject1 = str;
        }
        label500:
        for (;;)
        {
            if (h.ky((String)localObject1))
            {
                d.a(paramIntent, -5);
                return;
                i = 0;
                break label205;
            }
            str = e.Lv((String)localObject1);
            str = (String)localObject2 + "/" + str;
            localObject2 = new File(str);
            if (!((File)localObject2).exists())
            {
                d.a(paramIntent, -6);
                return;
            }
            localObject2 = new File(((File)localObject2).getAbsolutePath(), e.Lw((String)localObject1));
            if (!((File)localObject2).exists())
            {
                d.a(paramIntent, -7);
                return;
            }
            g localg = new g(paramApplication);
            int j = h.a(paramApplication, (File)localObject2, localg);
            if (j != 0)
            {
                paramIntent.putExtra("intent_patch_package_patch_check", j);
                d.a(paramIntent, -9);
                return;
            }
            paramIntent.putExtra("intent_patch_package_config", localg.bxc());
            boolean bool2 = h.wc(paramInt);
            if (((bool2) && (!TinkerDexLoader.a(str, localg, paramIntent))) || ((h.wd(paramInt)) && (!TinkerSoLoader.a(str, localg, paramIntent)))) {
                break;
            }
            if ((bool1) && (i != 0))
            {
                this.patchInfo.nvO = ((String)localObject1);
                if (!f.a(localFile1, this.patchInfo, localFile2))
                {
                    d.a(paramIntent, -18);
                    return;
                }
            }
            if ((bool2) && (!TinkerDexLoader.a(paramApplication, paramBoolean, str, paramIntent))) {
                break;
            }
            d.a(paramIntent, 0);
            return;
        }
    }

    @Override
    public Intent tryLoad(Application paramApplication, int paramInt, boolean paramBoolean) {
        Intent localIntent = new Intent();
        long l = SystemClock.elapsedRealtime();
        tryLoadPatchFilesInternal(paramApplication, paramInt, paramBoolean, localIntent);
        localIntent.putExtra("intent_patch_cost_time", SystemClock.elapsedRealtime() - l);
        return localIntent;
    }
}
