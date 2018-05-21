package com.ymu.framework.utils;

public final class SystemUtils {

    /**
     * 获取系统类型。
     * @return
     */
    public static OsType showSysType() {
        if(System.getProperty("os.name").indexOf("Windows") == -1) {
           return OsType.LINUX;
        }else {
            return OsType.WIN;
        }
    }

    /**
     * 系统类型.
     */
    public enum  OsType {
        LINUX,
        WIN
    }
}
