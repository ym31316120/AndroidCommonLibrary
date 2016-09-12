package com.mageeyang.app.tinker.loader.util;


import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * a
 */
public final class MessageUtil {
    public String aHq;
    public String azb;
    public String name;
    public String nvI;
    public String path;

    private MessageUtil(String name, String aHq, String path, String nvI, String azb) {
        this.name = name;
        this.aHq = aHq;
        this.nvI = nvI;
        this.azb = azb;
        this.path = path;
    }

    @NonNull
    public final String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.name);
        stringBuffer.append(",");
        stringBuffer.append(this.path);
        stringBuffer.append(",");
        stringBuffer.append(this.aHq);
        stringBuffer.append(",");
        stringBuffer.append(this.nvI);
        stringBuffer.append(",");
        stringBuffer.append(this.azb);
        return stringBuffer.toString();
    }

    public static void CreateList(String str, ArrayList<MessageUtil> arrayList)
    {
        if (str != null && str.length() != 0) {
            for(String message : str.split("\n")){
                if(message!=null && message.length()>0){
                    String[] split = message.split(",", 5);
                    if (split != null && split.length >= 5) {
                        arrayList.add(new MessageUtil(split[0].trim(), split[2].trim(), split[1].trim(), split[3].trim(), split[4].trim()));
                    }
                }
            }
        }
    }

}
