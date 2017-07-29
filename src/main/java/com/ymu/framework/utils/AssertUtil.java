package com.ymu.framework.utils;

public final class AssertUtil {

	private AssertUtil() {
	}

	public final static void nullOrEmptyException(String str) {
		if (null == str || "".equals(str)) {
			throw new NullPointerException("符串不能为null或者空");
		}
	}

	public static final void notNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException("obj不能null");
		}
	}
	
}
