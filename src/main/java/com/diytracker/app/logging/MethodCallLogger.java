package com.diytracker.app.logging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;

import com.diytracker.app.util.LogConstants;


/**
 * Composant permettant de logger g�n�riquement tout appel de m�thode de service,
 * avec la classe, la m�thode, les param�tres et le temps d'ex�cution
 *
 * @author Fabien Baligand
 */
public class MethodCallLogger {



	private final Logger logger;

	public MethodCallLogger(Logger logger) {
		this.logger = logger;
	}


    /**
     * Logge g�n�riquement un appel de m�thode java : classe/m�thode appel�e, param�tres, temps d'ex�cution.
     * Si une erreur, log en ERROR avec le message d'erreur � la ligne
     * Si le temps d'ex�cution est sup�rieur au seuil "executionTimeWarnThreshold", log en WARN.
     * Sinon, tout va bien, log en INFO.
     */
    public void logRepositoryMethodCall(String className, String methodName, Object[] parameters, long executionTime, long executionTimeWarnThreshold, String errorMessage) {

        StringBuilder logMessage = new StringBuilder();
        logMessage.append(className).append(".").append(methodName);
        logMessage.append(LogConstants.LOG_SEPARATOR);
        logParameters(parameters, logMessage);
        logMessage.append(LogConstants.LOG_SEPARATOR).append(executionTime);

        if (errorMessage == null) {
        	if (executionTimeWarnThreshold != LogConstants.NO_WARN_THRESHOLD && executionTime > executionTimeWarnThreshold) {
                logger.warn(logMessage.toString());
        	}
        	else {
                logger.info(logMessage.toString());
        	}
        }
        else {
            logMessage.append("\n").append(errorMessage);
            logger.error(logMessage.toString(), errorMessage);
        }
    }
    
    /**
     * Logge g�n�riquement un appel de m�thode java : classe/m�thode appel�e, param�tres, temps d'ex�cution.
     * Si une erreur, log en ERROR avec le message d'erreur � la ligne
     * Si le temps d'ex�cution est sup�rieur au seuil "executionTimeWarnThreshold", log en WARN.
     * Sinon, tout va bien, log en INFO.
     */
    public void logOtherMethodCall(String className, String methodName, Object[] parameters, String errorMessage) {

        StringBuilder logMessage = new StringBuilder();
        logMessage.append(className).append(".").append(methodName);
        logMessage.append(LogConstants.LOG_SEPARATOR);
        logParameters(parameters, logMessage);

        if (errorMessage == null) {
                logger.info(logMessage.toString());

        }
        else {
            logMessage.append("\n").append(errorMessage);
            logger.error(logMessage.toString(), errorMessage);
        }
    }

    /**
     * Logge l'ensemble des param�tres dans 'logMessage'
     * @param parameters ensemble des param�tres de la m�thode, � logger
     * @param logMessage message � logger
     */
    void logParameters(Object[] parameters, StringBuilder logMessage) {
    	if (parameters == null || parameters.length == 0) {
    		return;
    	}
    	for (int i = 0; i < parameters.length; i++) {
			String paramAsString = convertToString(parameters[i]);
			logMessage.append(paramAsString);
			if (i < parameters.length - 1) {
				logMessage.append(", ");
			}
		}
	}

	/**
     * Convertit n'importe quel param�tre en String, pour qu'il soit logg�
     * @param parameter param�tre � transformer en String
     * @return le param�tre s�rialis� en String
     */
    String convertToString(Object parameter) {

        String result = null;

        if (parameter == null) {
            result = "null";
        }
        else if (parameter instanceof String) {
            result = (String) parameter;
            result = "'" + result + "'";
        }
        else if (parameter instanceof Number) {
            result = parameter.toString();
        }
        else if (parameter instanceof Date) {
            Date date = (Date) parameter;
            SimpleDateFormat formatter = new SimpleDateFormat(LogConstants.DATE_LOG_FORMAT);
            result = formatter.format(date);
            result = result.replace(" 00:00", "");
        }
        else if (parameter instanceof Calendar) {
        	Calendar calendar = (Calendar) parameter;
        	result = convertToString(calendar.getTime());
        }
        else if (parameter instanceof XMLGregorianCalendar) {
        	XMLGregorianCalendar calendar = (XMLGregorianCalendar) parameter;
        	result = convertToString(calendar.toGregorianCalendar().getTime());
        }
        else if (parameter instanceof Object[] || parameter instanceof Collection) {

        	Iterator<?> collectionIterator;
        	int collectionSize;
        	if (parameter instanceof Object[]) {
        		collectionIterator = new ArrayIterator(parameter);
        		collectionSize = ((Object[])parameter).length;
        	}
        	else {
        		collectionIterator = ((Collection<?>)parameter).iterator();
        		collectionSize = ((Collection<?>)parameter).size();
        	}

        	if (collectionSize <= LogConstants.MAX_COLLECTION_SIZE_TO_LOG_ALL_ITEMS) {
            	StringBuilder collectionResult = new StringBuilder("[");
            	while (collectionIterator.hasNext()) {
            		Object collectionItem = collectionIterator.next();
            		collectionResult.append(convertToString(collectionItem));
            		if (collectionIterator.hasNext()) {
            			collectionResult.append(", ");
            		}
            	}
            	collectionResult.append("]");
            	result = collectionResult.toString();
        	}
        	else {
        		String collectionType = collectionIterator.next().getClass().getSimpleName();
        		result = collectionType + "[" + collectionSize + "]";
        	}
        }
        // tableau de types primitifs
        else if (parameter.getClass().isArray()) {
        	int size = ArrayUtils.getLength(parameter);
        	if (size <= LogConstants.MAX_COLLECTION_SIZE_TO_LOG_ALL_ITEMS) {
            	result = ArrayUtils.toString(parameter);
        	}
        	else {
            	result = parameter.getClass().getComponentType().getSimpleName() + "[" + size + "]";
        	}
        }
        // Cas par d�faut (bean, enum, bool�en, ...)
        else {
        	result = parameter.toString();
        	if (result.startsWith(parameter.getClass().getName())) {
        		result = parameter.getClass().getSimpleName();
        	}
        }

        // Affinage du param�tre � logger (contenu, taille)
        result = result.replace("|", "_");
        if (result.length() > LogConstants.MAX_PARAM_LENGTH) {
        	if (result.startsWith("'")) {
        		result = result.substring(0, LogConstants.MAX_PARAM_LENGTH - 4) + "...'";
        	}
        	// on ne tronque pas les tableaux
        	else if (!result.startsWith("[")) {
        		result = result.substring(0, LogConstants.MAX_PARAM_LENGTH - 3) + "...";
        	}
        }

        return result;
    }
}
