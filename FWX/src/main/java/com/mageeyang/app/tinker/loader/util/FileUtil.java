package com.mageeyang.app.tinker.loader.util;

import java.io.File;

/**
 * e
 */
public final class FileUtil {

    public static final boolean deleteFile(File paramFile){
        boolean flag = true;
        if(paramFile != null){
            if(paramFile.exists()){
                paramFile.delete();
                paramFile.deleteOnExit();
            }
        }

        return flag;
    }
}
