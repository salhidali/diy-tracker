package com.diytracker.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log correlation constants
 * 
 * @author salhidali
 */
public class LogConstants {
	
	// HEADERS //

	/** Header HTTP identifiant la requ�te en cours (d�marre au niveau de l'Apache Portail/WebService EDI/...) */
	public static final String UNIQUE_ID_HEADER = "X-RequestID";

	/** Header HTTP indiquant le login de l'abonn� connect� */
	public static final String LOGIN_HEADER = "X-Login";

	/** Header HTTP indiquant l'IP de l'abonn� connect� */
	public static final String X_FORWARDED_FOR_HEADER ="X-Forwarded-For";
	
	/** Header HTTP indiquant le nom de l'application cliente ou le navigateur client */
	public static final String USER_AGENT_HEADER ="User-Agent";
	
	// MDC KEYS //

	/** MDC key for the unique request id */
	public static final String REQUEST_ID_MDC_KEY = "requestId";

	/** MDC key for the username performing the request */
	public static final String USERNAME_MDC_KEY = "username";
	
	/** MDC key for the request url */
	public static final String REQUEST_URL_MDC_KEY = "requestUrl";

	/** cl� MDC contenant l'extra-information de la requ�te en cours que l'on veut logger en fin de requ�te */
	public static final String REQUEST_INFO_MDC_KEY = "requestInfo";

	/** cl� MDC contenant le header HTTP 'X-Forwarded-For' de la requ�te HTTP en cours */
	public static final String X_FORWARDED_FOR_MDC_KEY ="xForwardedFor";
	
	// OTHER //
	
	public static final Logger SERVICE_LOGGER = LoggerFactory.getLogger("com.diytracker.app.logging.SERVICE");
	public static final Logger REST_LOGGER = LoggerFactory.getLogger("com.diytracker.app.logging.REST");
	
	public static final String DATE_LOG_FORMAT = "''dd/MM/yyyy HH:mm''";
	public static final int MAX_COLLECTION_SIZE_TO_LOG_ALL_ITEMS = 10;
	public static final int MAX_PARAM_LENGTH = 300;
	public static final long NO_WARN_THRESHOLD = -1L;
	public static final String LOG_SEPARATOR = " | ";
    
}
