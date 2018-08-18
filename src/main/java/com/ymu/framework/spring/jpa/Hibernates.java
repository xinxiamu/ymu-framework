package com.ymu.framework.spring.jpa;

import com.ymu.framework.utils.Lang;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Hibernate工具类
 *
 * @author lujijiang
 *
 */
public class Hibernates {

	static Logger logger = LoggerFactory.getLogger(Hibernates.class);

	@SuppressWarnings("rawtypes")
	private static Class hibernateSessionClass = null;
	static {
		try {
			hibernateSessionClass = Class.forName("org.hibernate.Session");
		} catch (ClassNotFoundException e) {
		}
	}

	/**
	 * 判断是否是Hibernate实现
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isHibernate(EntityManager em) {
		if (em.getDelegate() != null && hibernateSessionClass != null) {
			if (hibernateSessionClass.isAssignableFrom(em.getDelegate().getClass())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 写入注释（仅支持Oracle, H2, MySql, PostgreSql, SQLServer，如需更多数据库类型，请仿照此类自行编写。）
	 * 数据源需要设置如下属性支持
	 * ：remarksReporting＝true（当是oracle的时候）或者useInformationSchema＝true
	 * （当是mysql的时候）
	 *
	 * @param sessionFactory
	 */
	public static void writeJpaComments(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			for (String name : sessionFactory.getAllClassMetadata().keySet()) {
				AbstractEntityPersister classMetadata = (AbstractEntityPersister) sessionFactory.getAllClassMetadata()
						.get(name);
				Class<?> entityType = classMetadata.getMappedClass();
				final String tableName = classMetadata.getTableName();
				String tableColumnComment = session.doReturningWork((Connection connection) -> {
					ResultSet rs = connection.getMetaData().getTables(null, getScheme(connection), tableName, null);
					try {
						while (rs.next()) {
							String remark = rs.getString("REMARKS");
							if (StringUtils.isNotBlank(remark)) {
								return remark;
							}
						}
					} finally {
						rs.close();
					}
					return null;
				});
				if (StringUtils.isNotBlank(tableColumnComment)) {
					continue;
				}
				{
					JpaComment jpaComment = entityType.getAnnotation(JpaComment.class);
					if (jpaComment != null) {
						writeTableComment(session, tableName, jpaComment);
					}
				}

				try {
					// 解析实体类中的属性
					PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(entityType)
							.getPropertyDescriptors();
					for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
						Method method = propertyDescriptor.getReadMethod();
						if (method != null) {
							JpaComment jpaComment = method.getAnnotation(JpaComment.class);
							String propertyName = propertyDescriptor.getName();
							writeInternalColumnComment(session, classMetadata, tableName, jpaComment, propertyName);
						}
					}
					// 解析实体类中的字段
					while (entityType != null) {
						Field[] fields = entityType.getDeclaredFields();
						entityType = entityType.getSuperclass();
						for (Field field : fields) {
							JpaComment jpaComment = field.getAnnotation(JpaComment.class);
							String fieldName = field.getName();
							writeInternalColumnComment(session, classMetadata, tableName, jpaComment, fieldName);
						}
					}
				} catch (Exception e) {
					throw Lang.unchecked(e);
				}

			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw Lang.unchecked(e);
		} finally {
			session.close();
		}
	}

	private static void writeInternalColumnComment(Session session, AbstractEntityPersister classMetadata,
                                                   String tableName, JpaComment jpaComment, String propertyName) {
		try {
			String[] columnNames = classMetadata.getPropertyColumnNames(propertyName);
			if (jpaComment != null && columnNames != null) {
				for (String columnName : columnNames) {
					writeColumnComment(session, tableName, columnName, jpaComment);
				}
			}
		} catch (MappingException e) {
		}
	}

	/**
	 * 生成修改列注释的SQL
	 *
	 * @param connection
	 * @param tableName
	 * @param columnName
	 * @param jpaComment
	 * @return
	 * @throws SQLException
	 */
	private static String generateAlertTableCommentSql(Connection connection, final String tableName,
			final String columnName, final JpaComment jpaComment) throws SQLException {
		String jdbcUrl = connection.getMetaData().getURL();
		String schemeName = getScheme(connection);
		if (StringUtils.contains(jdbcUrl, ":mysql:")) {
			String columnMeta = null;
			Statement statement = connection.createStatement();
			try {
				String sql = "SELECT CONCAT_WS(' ',COLUMNS.COLUMN_TYPE,IF(COLUMNS.IS_NULLABLE = 'NO','NOT NULL',''),COLUMNS.COLUMN_DEFAULT) FROM information_schema.COLUMNS "
						+ "where COLUMNS.TABLE_SCHEMA like '%s' " + "and COLUMNS.TABLE_NAME = '%s' "
						+ "and COLUMNS.COLUMN_NAME='%s'";

				schemeName = schemeName == null ? "%" : schemeName;
				sql = String.format(sql, schemeName, tableName, columnName);
				ResultSet resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					columnMeta = resultSet.getString(1);
				}
				resultSet.close();
			} finally {
				statement.close();
			}

			if (columnMeta == null) {
				throw new IllegalStateException(
						String.format("无法查询到%s.%s列的元数据，请检查information_schema.columns表", tableName, columnName));
			}

			String sql = "ALTER TABLE %s.%s MODIFY COLUMN %s %s COMMENT '%s'";
			sql = String.format(sql, schemeName, tableName, columnName, columnMeta, columnCommentToString(jpaComment));
			return sql;
		} else {
			String sql = "COMMENT ON COLUMN %s.%s IS '%s'";
			sql = String.format(sql, tableName, columnName, columnCommentToString(jpaComment));
			return sql;
		}

	}

	private static String columnCommentToString(final JpaComment jpaComment) {
		String ColumnCommentString = jpaComment.value();
		if (StringUtils.isNotBlank(jpaComment.description())) {
			ColumnCommentString = ColumnCommentString.concat(",").concat(jpaComment.description());
		}
		return escapeSql(ColumnCommentString);
	}

	private static String escapeSql(final String sql) {
		return sql.replace("'", "''");
	}

	/**
	 * 写如列注释
	 *
	 * @param tableName
	 * @param columnName
	 * @param jpaComment
	 */
	private static void writeColumnComment(Session session, final String tableName, final String columnName,
                                           final JpaComment jpaComment) {
		session.doWork((Connection connection) -> {
			Statement statement = connection.createStatement();
			try {
				String sql = generateAlertTableCommentSql(connection, tableName, columnName, jpaComment);
				if (sql != null) {
					statement.execute(sql);
				}
			} catch (SQLException e) {
				logger.error("writeColumnComment", e);
			} finally {
				statement.close();
			}
		});
	}

	/**
	 * 生成修改表注释的SQL
	 *
	 * @param connection
	 * @param tableName
	 * @param jpaComment
	 * @return
	 * @throws SQLException
	 */
	private static String generateAlertTableCommentSql(Connection connection, final String tableName,
			final JpaComment jpaComment) throws SQLException {
		String jdbcUrl = connection.getMetaData().getURL();
		String schemeName = getScheme(connection);
		if (StringUtils.contains(jdbcUrl, ":mysql:")) {
			String sql = "ALTER TABLE %s.%s COMMENT '%s'";
			sql = String.format(sql, schemeName, tableName, columnCommentToString(jpaComment));
			return sql;
		} else {
			String sql = "COMMENT ON TABLE %s IS '%s'";
			sql = String.format(sql, tableName, columnCommentToString(jpaComment));
			return sql;
		}

	}

	private static String getScheme(final Connection connection) throws SQLException {
		return connection.getCatalog();
	}

	/**
	 * 写如表注释
	 *
	 * @param tableName
	 * @param jpaComment
	 */
	private static void writeTableComment(Session session, final String tableName, final JpaComment jpaComment) {
		session.doWork((Connection connection) -> {
			Statement statement = connection.createStatement();
			try {
				String sql = generateAlertTableCommentSql(connection, tableName, jpaComment);
				if (sql != null) {
					statement.execute(sql);
				}
			} catch (SQLException e) {
				logger.error("write Table Comment error", e);
			} finally {
				statement.close();
			}
		});
	}
}