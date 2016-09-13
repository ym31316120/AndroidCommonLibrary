package com.mageeyang.app.tinker.loader.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mageeyang.kingkong.FileUtils;
import com.mageeyang.smtt.sdk.WebView;

import org.jetbrains.annotations.Contract;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * e
 */
public final class FileUtil {

    private static final String TAG = "FileUtil";

    @Nullable
    /**
     * gp
     */
    public static File getDataDir(Context context) {
        ApplicationInfo appInfo = context.getApplicationInfo();
        if (appInfo == null) {
            return null;
        }
        return new File(appInfo.dataDir, "tinker");
    }

    /**
     * Ly
     * @param str
     * @return
     */
    @Contract("null -> false")
    public static boolean isDex(String str) {
        if (str == null) {
            return false;
        }
        return str.endsWith(".dex");
    }

    @Contract("_ -> !null")
    /**
     * lt
     */
    public static File createPatchInfo(String path) {

        return new File(path + "/patch.info");
    }

    /**
     * lu
     */
    @Contract("_ -> !null")
    public static File createInfoLock(String path) {

        return new File(path + "/info.lock");
    }

    /**
     * Lv
     */
    public static String createPatchName(String str) {
        if (str == null || str.length() != 32) {
            return null;
        }
        return "patch-" + str.substring(0, 8);
    }

    /**
     * Lw
     */
    public static String createApkName(String str) {
        if (str == null || str.length() != 32) {
            return null;
        }
        return createPatchName(str) + ".apk";
    }

    /**
     * Lx
     */
    public static boolean checkPathName(String str) {
        if (str == null || str.length() != 32) {
            return false;
        }
        return true;
    }

    /**
     * D
     */
    public static final boolean deleteFile(File file) {
        boolean z = true;
        if (file != null) {
            Log.v(TAG, "safeDeleteFile, try to delete path: " + file.getPath());
            if (file.exists()) {
                z = file.delete();
                if (!z) {
                    Log.v(TAG, "Failed to delete file, try to delete when exit. path: " + file.getPath());
                    file.deleteOnExit();
                }
            }
        }
        return z;
    }

    /**
     * e
     */
    public static final boolean deleteFileOrDirectory(File file) {
        int i = 0;
        if (file == null || !file.exists()) {
            return false;
        }
        if (file.isFile()) {
            deleteFile(file);
        } else if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                int length = listFiles.length;
                while (i < length) {
                    deleteFileOrDirectory(listFiles[i]);
                    i++;
                }
                deleteFile(file);
            }
        }
        return true;
    }

    /**
     * c
     */
    public static boolean compareFile(File file, String str) throws Throwable {
        if (str == null) {
            return false;
        }
        String E = getStringInfo(file);
        if (E != null) {
            return str.equals(E);
        }
        return false;
    }


    /**
     * d
     * @param file
     * @param str
     * @return
     */
    public static boolean compareFileByDex(File file, String str) throws Throwable {
        if (file == null || str == null) {
            return false;
        }
        Object E;
        if (isDex(file.getName())) {
            E = getStringInfo(file);
        } else {
            try {
                JarFile jarFile = new JarFile(file);
                ZipEntry entry = jarFile.getEntry("classes.dex");
                if (entry == null) {
                    return false;
                }
                E = inputStreamMd5(jarFile.getInputStream(entry));
            } catch (IOException e) {
                return false;
            }
        }
        return str.equals(E);
    }

    /**
     * f
     * @param file
     * @param file2
     */
    public static void copyFileData(File file, File file2) throws Throwable {
        FileChannel inChannel;
        FileChannel outChannel;
        Throwable th;
        Closeable closeable = null;
        File parentFile = file2.getParentFile();
        if (!(parentFile == null || parentFile.exists())) {
            parentFile.mkdirs();
        }
        try {
            inChannel = new FileInputStream(file).getChannel();
            try {
                outChannel = new FileOutputStream(file2).getChannel();
            } catch (Throwable th2) {
                th = th2;
                closeable = inChannel;
                inChannel = null;
                close(closeable);
                close(inChannel);
                throw th;
            }
            try {
                outChannel.transferFrom(inChannel, 0, inChannel.size());
                close(inChannel);
                close(outChannel);
            } catch (Throwable th3) {
                Throwable th4 = th3;
                closeable = inChannel;
                inChannel = outChannel;
                th = th4;
                close(closeable);
                close(inChannel);
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            inChannel = null;
            close(closeable);
            close(inChannel);
            throw th;
        }
    }

    /**
     * a
     *
     * @param jarFile
     * @param jarEntry
     * @return
     */
    public static String readJarFile(JarFile jarFile, JarEntry jarEntry) throws Throwable {
        Throwable th;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedInputStream bufferedInputStream;
        try {
            byte[] bArr = new byte[16384];
            int byteRead = 0;
            bufferedInputStream = new BufferedInputStream(jarFile.getInputStream(jarEntry));
            while ((byteRead = bufferedInputStream.read(bArr)) != -1) {
                try {
                    stringBuilder.append(new String(bArr, 0, byteRead));
                } catch (Throwable th2) {
                    th = th2;
                    close(bufferedInputStream);
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedInputStream = null;
            close(bufferedInputStream);
            throw th;
        }
        return stringBuilder.toString();
    }

    /**
     * p
     * @param inputStream
     * @return
     */
    private static String inputStreamMd5(InputStream inputStream) {
        String str = null;
        if (inputStream != null) {
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                MessageDigest instance = MessageDigest.getInstance("MD5");
                StringBuilder stringBuilder = new StringBuilder(32);
                byte[] bArr = new byte[102400];
                while (true) {
                    int read = bufferedInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    instance.update(bArr, 0, read);
                }
                byte[] digest = instance.digest();
                for (byte b : digest) {
                    stringBuilder.append(Integer.toString((b & WebView.NORMAL_MODE_ALPHA) + FileUtils.S_IRUSR, 16).substring(1));
                }
                str = stringBuilder.toString();
            } catch (Exception e) {
            }
        }
        return str;
    }

    /**
     * E
     *
     * @param file
     * @return
     */
    public static String getStringInfo(File file) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        Throwable th;
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                String p = inputStreamMd5(fileInputStream);
                fileInputStream.close();
                try {
                    fileInputStream.close();
                    return p;
                } catch (IOException e) {
                    return p;
                }
            } catch (Exception e2) {
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException e3) {
                    }
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            fileInputStream2 = null;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
    }

    /**
     * g
     * @param file
     * @param file2
     * @return
     */
    public static String createDexFileName(File file, File file2) {
        String name = file.getName();
        if (!name.endsWith(".dex")) {
            int lastIndexOf = name.lastIndexOf(".");
            if (lastIndexOf < 0) {
                name = name + ".dex";
            } else {
                StringBuilder stringBuilder = new StringBuilder(lastIndexOf + 4);
                stringBuilder.append(name, 0, lastIndexOf);
                stringBuilder.append(".dex");
                name = stringBuilder.toString();
            }
        }
        return new File(file2, name).getPath();
    }

    /**
     * c
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
