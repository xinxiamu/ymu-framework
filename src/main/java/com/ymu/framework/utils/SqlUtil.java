package com.ymu.framework.utils;

import java.util.Date;
import java.util.List;

import com.ymu.framework.utils.time.DateUtil;

public final class SqlUtil {

	private SqlUtil() {
	}

	/*public static void main(String[] args) {
		Object[] rowValues = new Object[] { "张", 3, 4F, 5D, new Date(), true, false };

		System.out.println(generateInsertValue(rowValues));

		List<Object[]> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(rowValues);
		}
		System.out.println(generateInsertValueBatch(list));

		String[] fields = new String[] { "username", "age", "cars", "house", "bornDate", "man", "woman" };
		System.out.println(generateInsertSql("user", fields, list));
	}*/

	
	private static final String generateInsertValue(final Object[] rowValues) {
		AssertUtil.notNull(rowValues);
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < rowValues.length; i++) {
			Object value = rowValues[i];
			if (value instanceof String) {
				sb.append("'").append(value).append("'");
			} else if (value instanceof Integer) {
				sb.append(((Integer) value).intValue());
			} else if (value instanceof Boolean) {
				boolean v = ((Boolean) value).booleanValue();
				sb.append(v ? "'1'" : "'0'");
			} else if (value instanceof Float) {
				sb.append(((Float) value).floatValue());
			} else if (value instanceof Date) {
				Date d = (Date) value;
				sb.append("'").append(DateUtil.dateToString(d)).append("'");
			} else if (value instanceof Double) {
				sb.append(((Double) value).doubleValue());
			}
			if (i != rowValues.length - 1) {
				sb.append(",");
			}
		}
		sb.append(")");

		return sb.toString();
	}

	private static final String generateInsertValueBatch(final List<Object[]> rowBatch) {
		AssertUtil.notNull(rowBatch);
		StringBuilder sb = new StringBuilder(); 
		for (int i = 0; i < rowBatch.size(); i++) {
			Object[] row = rowBatch.get(i);
			String rowValues = generateInsertValue(row);
			sb.append(rowValues);
			if (i != rowBatch.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 生成INSERT插入sql语句。
	 * 
	 * @param tableName
	 *            表名字,实体类名
	 * @param fields
	 *            表字段。实体字段名
	 * @param rowBatch
	 *            要插入的多行数据
	 * @return 返回组装成功的插入sql语句
	 */
	public static String generateInsertSql(String tableName, final String[] fields, final List<Object[]> rowBatch) {
		AssertUtil.nullOrEmptyException(tableName);
		AssertUtil.notNull(fields);
		AssertUtil.notNull(rowBatch);

		StringBuilder sb = new StringBuilder("INSERT INTO ").append(StringUtils.javaFieldName2SqlFieldName(tableName))
				.append("(");
		for (int i = 0; i < fields.length; i++) {
			String field = StringUtils.javaFieldName2SqlFieldName(fields[i]);
			sb.append(field);
			if (i != fields.length - 1) {
				sb.append(",");
			}
		}
		sb.append(")").append(" VALUES");

		return sb.append(generateInsertValueBatch(rowBatch)).toString();
	}
}
