package com.ymu.framework.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class BaseService {

    @Value("${app.tmp-dir}")
    protected String appTmpDir;
}
