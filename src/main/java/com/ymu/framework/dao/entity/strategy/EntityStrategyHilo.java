package com.ymu.framework.dao.entity.strategy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;


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
@SuppressWarnings({ "unchecked" })
@MappedSuperclass
public abstract class EntityStrategyHilo<E> extends EntityBase {

	private static final long serialVersionUID = -7358052664222681948L;
	
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "hilo")
	@Column(name = "id")
	private Long id;

	
	@Column(name = "addDate", nullable = false)
	private Date addDate;

	/**
	 * 以时间戳作为乐观锁
	 */
	@Version
	@Column(name = "updateDate")
	private Date updateDate;
	
	/**
	 * 数据终端来源
	 */
	@Column(nullable = false, length = 15)
	private String fromSystem;
	
//	@Column(name = "CALENDAR")
//	private Timestamp calendar = new Timestamp(System.currentTimeMillis());

	@Transient
	@XmlTransient
	private Class<E> entityClass;

	public EntityStrategyHilo(Long id) {
		this.id = id;
	}
	
	public EntityStrategyHilo(Long id, Date addDate) {
		this.id = id;
		this.addDate = addDate;
	}

	public EntityStrategyHilo() {
		try {
			Class<?> c = super.getClass();
			Type type = c.getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
				this.entityClass = ((Class<E>) parameterizedType[0]);
			}
			if(addDate == null){
				addDate = new Date();
			}
			if(updateDate == null){
				updateDate = new Date();
			}
		} catch (Exception e) {
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<E> entityClass) {
		this.entityClass = entityClass;
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

//	public Timestamp getCalendar() {
//		return calendar;
//	}
//
//	public void setCalendar(Timestamp calendar) {
//		this.calendar = calendar;
//	}
	
	public String getFromSystem() {
		return fromSystem;
	}

	public void setFromSystem(String fromSystem) {
		this.fromSystem = fromSystem;
	}

	@Override
	public String toString() {
		return "{" + ToStringBuilder.reflectionToString(this) + "id=" + id
				+ ", addDate=" + addDate + ", updateDate=" + updateDate
				+ ", entityClass=" + entityClass + "}";
	}

}
