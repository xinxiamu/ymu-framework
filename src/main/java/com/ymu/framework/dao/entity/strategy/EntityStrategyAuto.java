package com.ymu.framework.dao.entity.strategy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * 抽象实体类，可作为所有领域驱动实体的基类
 * 
 */
@SuppressWarnings({ "unchecked" })
@MappedSuperclass
public abstract class EntityStrategyAuto<E> extends EntityBase {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	// 定时间格式
	@Column(name = "addDate", nullable = false)
	private Date addDate = new Date();

	/**
	 * 乐观锁
	 */
	@Version
	@Column(name = "version")
	private Integer version = 1;

	/**
	 * 数据终端来源。关联到表
	 * {@link com.logistics.model.lg.dictionary.FromClientTypeDic}.id
	 */
	@Column(nullable = false, length = 3)
	private Long fromClientType_id;

	@Transient
	@XmlTransient
	private Class<E> entityClass;

	public EntityStrategyAuto() {
		try {
			Class<?> c = super.getClass();
			Type type = c.getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				Type[] parameterizedType = ((ParameterizedType) type)
						.getActualTypeArguments();
				this.entityClass = ((Class<E>) parameterizedType[0]);
			}
			if (addDate == null) {
				addDate = new Date();
			}
			if (version == null) {
				version = 1;
			}
		} catch (Exception e) {
		}
	}

	@Override
	public String toString() {
		return "{" + ToStringBuilder.reflectionToString(this) + ", id=" + id
				+ ", addDate=" + addDate + ", updateDate=" + version
				+ ", entityClass=" + entityClass + "}";
	}

	public Long getId() {
		return id;
	}

	// {serialVersionUID=-5799022233822037101,exceptionClass=co.jufeng.model.entity.ExceptionslTest,exceptionType=抛出异常类型,noteMessage=抛出的异常错误信息,exceptionLine=50id=1,
	// addDate=2014-09-17 11:01:30.0, updateDate=2014-09-17 11:01:30.0,
	// entityClass=class co.jufeng.model.entity.Exceptions}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFromClientType_id() {
		return fromClientType_id;
	}

	public void setFromClientType_id(Long fromClientType_id) {
		this.fromClientType_id = fromClientType_id;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

}
