package com.ymu.framework.dao.entity.strategy;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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
@MappedSuperclass
public abstract class EntityStrategyUUID<E> extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7695230587155918888L;

	@Id
	@Column(name = "ID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid") // uuid.hex
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
