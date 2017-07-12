package com.ymu.framework.utils.erro;

import com.ymu.framework.utils.logger.LoggerUtil;


/**
 * 处理异常和日志
 * @author Administrator
 *
 */
public class BaseException extends Exception{

	private static final long serialVersionUID = 6885698426846999041L;
	
	/**
	 * 
	 * @类描述：
	 * 
	 * 注意：在实际项目中，用一个类来实现它作为统一的实现类处理异常。
	 *
	 */
	public interface BaseExceptionCallback {
		
		/**
		 * 保存异常信息到数据库。
		 */
		void saveExceptionInfo(String classStr, String addExceptionNoteMessage,
				String exceptionType, int exceptionLine);
	}
	
	public BaseException() {
	}

	public BaseException(String smg) {
		super(smg);
	}
	
	@Override
	public void printStackTrace() {
		printStackTrace(System.err);
	}
	
	public void printStackTrace(Class<?> clazz, Exception e) {
		throwsException(clazz, e);
	}
	
	public void printStackTrace(Class<?> clazz, Exception e, BaseExceptionCallback callBack) {
		throwsException(clazz, e, callBack);
	}
	
	public static void throwsException(Class<?> clazz, Throwable throwable) {
		throwsException(clazz, throwable.getMessage(), throwable, null);
	}

	public static void throwsException(Class<?> clazz, Throwable throwable, BaseExceptionCallback callBack) {
		throwsException(clazz, throwable.getMessage(), throwable, callBack);
	}
	
	public static void throwsException(Class<?> clazz, String addExceptionNoteMessage, Throwable throwable, BaseExceptionCallback callBack) {
		try {
			int exceptionLine = TraceInfoUtil.getExceptionLine(clazz, throwable);
			String exceptionType = TraceInfoUtil.getExceptionMessage(throwable);
			LoggerUtil.error(clazz, throwable);
			if(null != callBack){
				//回调对异常数据做持久化处理。
				callBack.saveExceptionInfo(clazz.toString(), addExceptionNoteMessage, exceptionType, exceptionLine);
			}
		} catch (Exception e) {
			LoggerUtil.error(BaseException.class, e);
		}
	}

	
}
