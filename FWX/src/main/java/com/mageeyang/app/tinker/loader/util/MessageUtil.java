package com.mageeyang.app.tinker.loader.util;


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

    public final String toString()
    {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(this.name);
        localStringBuffer.append(",");
        localStringBuffer.append(this.path);
        localStringBuffer.append(",");
        localStringBuffer.append(this.aHq);
        localStringBuffer.append(",");
        localStringBuffer.append(this.nvI);
        localStringBuffer.append(",");
        localStringBuffer.append(this.azb);
        return localStringBuffer.toString();
    }

    public static void CreateList(String paramString, ArrayList<MessageUtil> paramArrayList)
    {
        if ((paramString == null) || (paramString.length() == 0)) {
            return ;
        }else{
            String [] params = paramString.split("\n");
            for(int i=0;i<params.length;i++){
                if((params[i]!=null)&&(params[i].length()>0)){
                    String [] arrayOfString = params[i].split(",",5);
                    if((arrayOfString!=null)&&(arrayOfString.length>=5)){
                        String str1 = arrayOfString[0].trim();
                        String str2 = arrayOfString[1].trim();
                        paramArrayList.add(new MessageUtil(str1, arrayOfString[2].trim(), str2, arrayOfString[3].trim(), arrayOfString[4].trim()));

                    }
                }
            }
        }
    }

}
