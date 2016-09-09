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

    private FileCloseableUtil(File paramFile) throws FileNotFoundException {

        this.fos = new FileOutputStream(paramFile);
        FileLock fileLock = null;
        try {
            fileLock = this.fos.getChannel().lock();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fl = fileLock;
    }

    public static FileCloseableUtil getInstance(File paramFile) throws FileNotFoundException {
        return new FileCloseableUtil(paramFile);
    }


    @Override
    public void close() throws IOException {
        try {
            if(this.fl!=null){
                this.fl.release();
            }
            return;
        }finally {
            if(this.fos!=null){
                this.fos.close();
            }
        }
    }
}
