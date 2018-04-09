package com.ymu.framework.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Cloneable {

    /**
     * 主键。手动调用远程接口生成分布式id，再set插入
     */
    @Id
    @Column(nullable = false, unique = true)
    protected Long id;

    /**
     * 是否无效（默认是有效）
     */
    @Column(nullable = false, columnDefinition = "decimal(1,0)")
    protected Boolean disabled;

    /**
     * 最后更新时间
     */
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastUpdated;

    /**
     * 创建时间
     */
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateCreated;

    /**
     * 数据库版本号（用于乐观锁）
     */
    @Column(nullable = false, name = "version")
    @Version
    protected Integer version;

    /**
     * 客户端设备类型。
     */
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    protected ClientDeviceType clientDeviceType;

    public enum ClientDeviceType {

        ANDROID("android"),
        IOS("ios"),
        BROWSER("web");

        private final String value;

        ClientDeviceType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public ClientDeviceType getClientDeviceType() {
        return clientDeviceType;
    }

    public void setClientDeviceType(ClientDeviceType clientDeviceType) {
        this.clientDeviceType = clientDeviceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @PrePersist
    public void prePersist() {
        if (dateCreated == null)
            this.dateCreated = new Date();
        if (disabled == null)
            this.disabled = false;
        if (lastUpdated == null)
            this.lastUpdated = this.dateCreated;
        if (version == null) {
            version = 0;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = new Date();
        if (disabled == null) {
            this.disabled = false;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEntity that = (AbstractEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
