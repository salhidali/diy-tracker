package com.diytracker.app.logging;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.diytracker.app.util.LogConstants;

import io.netty.util.internal.StringUtil;



/**
 * Aspect permettant de logger les appels de m�thodes des services de persistance Spring,
 * avec les param�tres et le temps d'ex�cution.
 * Exemple : 
 * PERSISTENCE | ProductRepository.findById | 1 | 13
 *
 * @author Fabien Baligand
 */
@Aspect
@Component
public class CustomCallLogAspect {

	private static final Logger REPOSITORY_LOGGER = LoggerFactory.getLogger("fr.icdc.dei.banque.log.REPOSITORY");
	private static final Logger SERVICE_LOGGER = LoggerFactory.getLogger("fr.icdc.dei.banque.log.SERVICE");
	private static final Logger REST_LOGGER = LoggerFactory.getLogger("fr.icdc.dei.banque.log.REST");
	
	private static final List<String> INTERFACES_TO_IGNORE = Arrays.asList(
		"JpaRepository",
		"PagingAndSortingRepository",
		"CrudRepository",
		"Repository",
		"TransactionalProxy",
		"Advised",
		"DecoratingProxy"
	);
	
	
	/** Param�tre optionnel indiquant une regex des noms de m�thode � ne pas logger */
	private String excludedMethodNamesRegex = null;
	
	/** Param�tre optionnel indiquant le temps d'ex�cution min (en ms) � partir duquel on logge en WARN (au lieu d'info) */
	private long warnThreshold = LogConstants.NO_WARN_THRESHOLD;
	
	MethodCallLogger methodCallLogger = null;
	
	
    @Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..))")
    public void monitor() {}

    @Around("monitor()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
    	methodCallLogger = new MethodCallLogger(REPOSITORY_LOGGER);
    	return logAroundRepositoryMethod(pjp);
    }
	
	
    /**
     * Logge tout appel � une m�thode de service de persistance
     */
    @Around("bean(*Controller) or bean(*Service)")
    public Object logControllerOrServiceCall(ProceedingJoinPoint pjp) throws Throwable {
    	return logAroundOtherMethods(pjp);
    }
    
	private Object logAroundRepositoryMethod(ProceedingJoinPoint pjp) throws Throwable, Exception {
		long executionTime = System.currentTimeMillis();
           String errorMessage = null;

           try {
               Object retVal = pjp.proceed();
               return retVal;
           }
           catch (Exception e) {
               errorMessage = e.toString();
               throw e;
           }
           finally {
               executionTime = System.currentTimeMillis() - executionTime;
               String methodName = pjp.getSignature().getName();

               if ((excludedMethodNamesRegex == null || excludedMethodNamesRegex.equals("")) || !methodName.matches(excludedMethodNamesRegex)) {
                   String className = computeClassName(pjp);
                   Object[] parameters = pjp.getArgs();

               	   methodCallLogger.logRepositoryMethodCall(className, methodName, parameters, executionTime, warnThreshold, errorMessage);
               }
           }
	}
	
	private Object logAroundOtherMethods(ProceedingJoinPoint pjp) throws Throwable, Exception {
           String errorMessage = null;

           try {
        	   String className = computeClassName(pjp);
               String methodName = pjp.getSignature().getName();
               Object[] parameters = pjp.getArgs();
               
        	   if ((excludedMethodNamesRegex == null || excludedMethodNamesRegex.equals("")) || !methodName.matches(excludedMethodNamesRegex)) {
               	   methodCallLogger.logOtherMethodCall(className, methodName, parameters, errorMessage);
               }
        	   
               Object retVal = pjp.proceed();
               return retVal;
           }
           catch (Exception e) {
               errorMessage = e.toString();
               throw e;
           }
 
	}

    /**
     * Calcule et retourne le nom de la classe de persistance � logger.
     * G�re en particulier le cas o� le service spring proxifi� est un Repository Spring Data JPA pour logger le bon nom de composant.
     * @param pjp point de jointure AOP
     * @return nom de la classe de persistance � logger
     */
	private String computeClassName(ProceedingJoinPoint pjp) {
		String className = pjp.getSignature().getDeclaringType().getSimpleName();
		if (INTERFACES_TO_IGNORE.contains(className)) {
			Class<?>[] interfaces = pjp.getTarget().getClass().getInterfaces();
		    for (Class<?> interfaceClass : interfaces) {
		    	String interfaceClassName = interfaceClass.getSimpleName();
		    	if (!INTERFACES_TO_IGNORE.contains(interfaceClassName)) {
		    		className = interfaceClassName;
		            break;
		    	}
		    }
		}
		
		if(className.contains("Controller")) {
			methodCallLogger = new MethodCallLogger(REST_LOGGER);
		} else if(className.contains("Service")) {
			methodCallLogger = new MethodCallLogger(SERVICE_LOGGER);
		} else {
			methodCallLogger = new MethodCallLogger(REPOSITORY_LOGGER);
		}
		
		return className;
	}
	
    
    // SETTERS //

	public void setExcludedMethodNamesRegex(String excludedMethodNamesRegex) {
		this.excludedMethodNamesRegex = excludedMethodNamesRegex;
	}

	public void setWarnThreshold(long warnThreshold) {
		this.warnThreshold = warnThreshold;
	}
}
