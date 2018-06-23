package com.ymu.framework.base;

import org.springframework.beans.factory.annotation.Value;

public class BaseService {

    @Value("${app.tmp-dir}")
    protected String appTmpDir;
}
