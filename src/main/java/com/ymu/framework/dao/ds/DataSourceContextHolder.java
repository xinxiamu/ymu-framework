package com.ymu.framework.dao.ds;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataSourceContextHolder {

    private static final Logger LOGGER = LogManager.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源名。
     * @param dbType
     */
    public static void setDS(String dbType) {
        if (dbType == null) {
            throw new NullPointerException("数据源不能null");
        }
        LOGGER.info("切换到{}数据源", dbType);
        contextHolder.set(dbType);
    }

    /**
     * 获取数据源名。
     * @return
     */
    public static String getDS() {
        return (contextHolder.get());
    }

    /**
     * 清除数据源名。
     */
    public static void clearDS() {
        contextHolder.remove();
    }
}