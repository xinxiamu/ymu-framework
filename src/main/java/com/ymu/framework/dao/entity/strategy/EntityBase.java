package com.ymu.framework.dao.entity.strategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class EntityBase implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4964423360686914428L;

	/**
	 * 是否无效（默认是有效）
	 */
	@Column(columnDefinition = "decimal(1,0)")
	protected Boolean disabled;

	/**
	 * 最后更新时间
	 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastUpdated;

	/**
	 * 创建时间
	 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateCreated;

	/**
	 * 数据库版本号（用于乐观锁）
	 */
	@Column(name = "version")
	@Version
	protected Integer version;

	public EntityBase() {

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

	public Object copy() throws CloneNotSupportedException {
		return super.clone();
	}

}
