package com.mageeyang.app.tinker.loader.util;

import android.support.annotation.NonNull;

import com.mageeyang.app.tinker.loader.TinkerRuntimeException;

import java.util.ArrayList;

/**
 * b
 */
public class JarUtil {

    public final String aHq;
    public final String azb;
    public final String cgW;
    public final String nvI;
    public final String nvJ;
    public final String nvK;
    public final boolean nvL;
    public final String path;

    private JarUtil(String nvJ, String aHq, String path, String nvI, String azb, String nvK) {
        this.nvJ = nvJ;
        this.aHq = aHq;
        this.nvI = nvI;
        this.azb = azb;
        this.path = path;
        this.nvK = nvK;
        if (nvK.equals("jar")) {
            this.nvL = true;
            if (FileUtil.isDex(nvJ)) {
                this.cgW = nvJ + ".jar";
            } else {
                this.cgW = nvJ;
            }
        } else if (nvK.equals("raw")) {
            this.nvL = false;
            this.cgW = nvJ;
        } else {
            throw new TinkerRuntimeException("can't recognize dex mode:" + nvK);
        }
    }

    public static void createList(String str, ArrayList<JarUtil> arrayList) {
        if (str != null && str.length() != 0) {
            for (String str2 : str.split("\n")) {
                if (str2 != null && str2.length() > 0) {
                    String[] split = str2.split(",", 6);
                    if (split != null && split.length >= 6) {
                        arrayList.add(new JarUtil(split[0].trim(), split[2].trim(), split[1].trim(), split[3].trim(), split[4].trim(), split[5].trim()));
                    }
                }
            }
        }
    }

    @NonNull
    public final String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.nvJ);
        stringBuffer.append(",");
        stringBuffer.append(this.path);
        stringBuffer.append(",");
        stringBuffer.append(this.aHq);
        stringBuffer.append(",");
        stringBuffer.append(this.nvI);
        stringBuffer.append(",");
        stringBuffer.append(this.azb);
        stringBuffer.append(",");
        stringBuffer.append(this.nvK);
        return stringBuffer.toString();
    }
}
