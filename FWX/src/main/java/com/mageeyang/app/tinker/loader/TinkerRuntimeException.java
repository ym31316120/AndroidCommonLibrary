package com.mageeyang.app.tinker.loader;

/**
 * Created by Administrator on 2016/9/9.
 */
public class TinkerRuntimeException extends RuntimeException {

    public TinkerRuntimeException(String paramString)
    {
        super("Tinker Exception:" + paramString);
    }

    public TinkerRuntimeException(String paramString, Throwable paramThrowable)
    {
        super("Tinker Exception:" + paramString, paramThrowable);
    }
}
