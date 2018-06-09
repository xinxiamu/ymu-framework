package com.ymu.framework.utils.erro;

public final class ExceptionUtils {

    /**
     * 获取最原始的异常。
     * @param e
     * @return
     */
    public static Throwable getOriginException(Throwable e){
        e = e.getCause();
        while(e.getCause() != null){
            e = e.getCause();
        }
        return e;
    }
}
