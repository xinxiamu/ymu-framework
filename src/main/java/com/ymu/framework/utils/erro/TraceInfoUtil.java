package com.ymu.framework.utils.erro;

/**
 * @author CreateName:ZhengJianYan UpdateName:
 * @see QQ：375273058
 * @see Function:获取当前运行的类名、方法名以及行数
 * @see CreateDate:2012-05-10 UpdateDate:
 * @see Copyright JuFeng
 * @since 1.7
 * @version J1.0.0
 */
public class TraceInfoUtil {

	/**
	 * 获取当前运行的类路径
	 * 
	 * @return String
	 */
	public static String getThisClassRunPath() {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		sb.append("class: ").append(stacks[1].getClassName());
		return sb.toString();
	}

	/**
	 * 获取当前运行类的方法名
	 * 
	 * @return String
	 */
	public static String getThisClassRunMethodName() {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		sb.append("; method: ").append(stacks[1].getMethodName());
		return sb.toString();
	}

	/**
	 * 获取当前运行类的行数
	 * 
	 * @return String
	 */
	public static String getThisClassRunLine() {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		sb.append("; line: ").append(stacks[1].getLineNumber());
		return sb.toString();
	}

	/**
	 * 获取当前运行类的方法名和行数
	 * 
	 * @return String
	 */
	public static String getThisClassRunMethodNameLine() {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		sb.append("; method: ").append(stacks[1].getMethodName())
				.append("; line: ").append(stacks[1].getLineNumber());
		return sb.toString();
	}

	/**
	 * 获取当前运行的类名、方法名以及行数
	 * 
	 * @return String
	 */
	public static String getTraceInfo() {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		sb.append("class: ").append(stacks[1].getClassName())
				.append("; method: ").append(stacks[1].getMethodName())
				.append("; line: ").append(stacks[1].getLineNumber());
		return sb.toString();
	}

	/**
	 * 获取抛出异常信息
	 * 
	 * @param e
	 * @return
	 */
	public static StringBuffer getExceptionTraceInfo(Class<?> clazz, Throwable e) {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = e.getStackTrace();
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i].getClassName().contains(clazz.getName())) {
				sb.append("class: ").append(stacks[i].getClassName())
						.append("; method: ").append(stacks[i].getMethodName())
						.append("; line: ").append(stacks[i].getLineNumber())
						.append(";  Exception: ")
						.append(getExceptionMessage(e));
				break;
			}
		}
		return sb;
	}

	/**
	 * 获取抛出异常的行数
	 * 
	 * @param e
	 * @return
	 */
	public static int getExceptionLine(Class<?> clazz, Throwable e) {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = e.getStackTrace();
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i].getClassName().contains(clazz.getName())) {
				sb.append(stacks[i].getLineNumber());
				break;
			}
		}
		return Integer.valueOf(sb.toString());
	}

	/**
	 * 获取抛出异常的错误类型信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionMessage(Throwable e) {
		String message = e.toString();
		if (message.lastIndexOf(":") != -1) {
			message = message.substring(0, message.lastIndexOf(":"));
		}
		return message;
	}

}
