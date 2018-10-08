package com.ymu.framework.base;

import com.ymu.framework.dao.persist.jdbc.knife.JdbcBaseServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

/**
 * 服务共用类。
 * @param <T> 实体对象
 */
@Transactional(readOnly = true)
public class BaseService<T extends BaseEntity> extends JdbcBaseServiceImpl<T> {

    @Value("${app.tmp-dir}")
    protected String appTmpDir;

    @Override
    public Map<String, Object> genMapCondition(T obj) {
        return null;
    }
}
