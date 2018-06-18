package com.ymu.framework.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.File;

public abstract class BaseSpringbootApplication implements ApplicationRunner {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Value("${app.tmp-dir}")
    private String appTmpDir;

    @Override
    public void run(ApplicationArguments args) {
        //创建应用程序文件持久化路径
        File file = new File(appTmpDir);
        if (!file.exists()) {
            file.mkdirs();
            logger.info(String.format("成功创建应用程序文件持久化路径：%s",appTmpDir));
        } else {
            logger.info(String.format("应用程序文件持久化路径已存在：%s",appTmpDir));
        }

        init(args);
    }

    /**
     * 程序加载完成后。在这里做一些初始化的准备工作。
     * @param args 命令行参数
     */
    public abstract void init(ApplicationArguments args);
}
