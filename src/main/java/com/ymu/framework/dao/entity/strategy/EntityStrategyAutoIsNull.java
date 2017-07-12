package com.ymu.framework.dao.entity.strategy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 抽象实体类，可作为所有领域驱动实体的基类
 * 
 * @author ZhengJianYan
 * @since JDK1.6
 * @version V1.0, 2012-02-20
 * @see UpdateName:
 * @see UpdateDate:
 * @see Copyright:JuFeng
 * @see QQ:375273058
 * @see <a href="mailto:13822119203@139.com">Email</a>
 */
@MappedSuperclass
public abstract class EntityStrategyAutoIsNull<E> extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7343612702186669784L;

	@Column(name = "addDate", nullable = false)
	private Date addDate;

	/**
	 * 以时间戳作为乐观锁。数据版本控制。
	 */
	@Version
	@Column(name = "updateDate")
	private Date updateDate;

	public EntityStrategyAutoIsNull() {
		if (addDate == null) {
			addDate = new Date();
		}
		if (updateDate == null) {
			updateDate = new Date();
		}
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
