package com.diytracker.app.logging;

import org.slf4j.Logger;

import com.diytracker.app.util.LogConstants;

/**
 * Custom logger used to correlate logs of the rest and service layers by using a
 * unique logger for every layer.
 * 
 */
public class CustomLogger {

	
	/**
	 * log info level
	 * 
	 * @param logger
	 * @param message
	 * @param data
	 */
	public static void logInfo(Logger logger, String message, Object... data) {
		
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		String formattedMessage = getCallingClassMethodName(element) + " | ";
		formattedMessage += getFormattedMessage(message, data);
		logger.info(formattedMessage);
	}

	/**
	 * log warn level
	 * 
	 * @param logger
	 * @param message
	 * @param data
	 */
	public static void logWarn(Logger logger, String message, Object... data) {
		
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		String formattedMessage = getCallingClassMethodName(element) + " | ";
		formattedMessage += getFormattedMessage(message, data);
		logger.warn(formattedMessage);
		
	}

	/**
	 * log error level
	 * 
	 * @param logger
	 * @param message
	 * @param data
	 */
	public static void logError(Logger logger, String message, Object... data ) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		String formattedMessage = getCallingClassMethodName(element) + " | ";
		formattedMessage += getFormattedMessage(message, data);
		logger.error(formattedMessage);
	}
	
	/**
	 * Return the class name and method that called the log method
	 * 
	 * @param element
	 * @return
	 */
	private static String getCallingClassMethodName(StackTraceElement element) {
		
		String className = element.getClassName();
		String simpleName = className.substring(className.lastIndexOf('.') + 1);
		String methodName = element.getMethodName();
		
		return simpleName+"."+methodName;
	}
	
	/**
	 * Format log message and data (method params)
	 * 
	 * @param message
	 * @param args
	 * @return Formatted message
	 */
	private static String getFormattedMessage(String message, Object[] args) {
		String formattedMessage = message + LogConstants.LOG_SEPARATOR ;
		
		for (Object arg : args) {
	        formattedMessage += arg.toString() + ";";
	    }
		
		return formattedMessage;
	}
}
