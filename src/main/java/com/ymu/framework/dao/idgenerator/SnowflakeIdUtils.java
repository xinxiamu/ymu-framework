package com.ymu.framework.dao.idgenerator;

public final class SnowflakeIdUtils {

    /**
     * 生成ID
     * @param dataCenterId 数据中心id
     * @param workerId 机器id(节点)
     * @return id
     */
    public  final static long genId(final long dataCenterId, final long workerId){
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(workerId,dataCenterId);
        return snowflakeIdWorker.nextId();
    }

}
