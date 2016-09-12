package com.mageeyang.app.tinker.loader.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

/**
 * c
 */
public final class FileCloseableUtil implements Closeable {

    private final FileOutputStream fos;
    private final FileLock fl;

    private FileCloseableUtil(File file) throws IOException {

        this.fos = new FileOutputStream(file);
        Throwable th = null;
        FileLock fileLock = null;
        int i = 0;
        while (i < 3) {
            try {
                fileLock = this.fos.getChannel().lock();
                if (fileLock != null) {
                    break;
                }
                Thread.sleep(10);
            } catch (Throwable e) {
                th = e;
            }
            i++;
        }
        if (fileLock == null) {
            throw new IOException("Tinker Exception:FileLockHelper lock file failed: " + file.getAbsolutePath(), th);
        }
        this.fl = fileLock;
    }

    public static FileCloseableUtil getInstance(File paramFile) throws IOException {
        return new FileCloseableUtil(paramFile);
    }


    @Override
    public void close() throws IOException {
        try {
            if(this.fl!=null){
                this.fl.release();
            }
        }finally {
            if(this.fos!=null){
                this.fos.close();
            }
        }
    }
}
