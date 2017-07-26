package com.ymu.framework.dao.entity.strategy;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ymu.framework.utils.OID;

public class EntityStrategyOID extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -345896069130730195L;
	
	/**
	 * 主键
	 */
	@Id
	@NotNull
	@Size(min = 16, max = 16)
	@Column(name = "id", length = 16)
	protected String id;

	public EntityStrategyOID() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!(this.getClass().isAssignableFrom(object.getClass()))) {
			return false;
		}
		EntityStrategyOID other = (EntityStrategyOID) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@PrePersist
	public void prePersist() {
		if (id == null) {
			this.id = OID.get().toBase64UrlString();
		}
		super.prePersist();
	}

	public Object copy() throws CloneNotSupportedException {
		return super.copy();
	}

	@Override
	public String toString() {
		return getClass().getCanonicalName() + "{" + "id='" + id + '\'' + '}';
	}
}
