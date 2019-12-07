package com.diytracker.app.logging;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.diytracker.app.util.LogConstants;

public class MDCLoggingFilter implements Filter {

    
	private static final Logger NEW_REQ = LoggerFactory.getLogger("com.diytracker.app.NEWREQ");
	private static final Logger REQUEST = LoggerFactory.getLogger("com.diytracker.app.REQUEST");
	
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

    	long startTime = System.currentTimeMillis();
    	
    	HttpServletRequest httpServletRequest = (HttpServletRequest)request;
    	
    	MDC.put(LogConstants.REQUEST_URL_MDC_KEY, httpServletRequest.getServletPath());
    	
    	 String usernameHeader = httpServletRequest.getHeader(LogConstants.USERNAME_MDC_KEY);
    	 
         if (usernameHeader != null ) {
             MDC.put(LogConstants.USERNAME_MDC_KEY, usernameHeader);
         } else {
        	 MDC.put(LogConstants.USERNAME_MDC_KEY, "anonymous");
         }

        String requestIdHeader = httpServletRequest.getHeader("requestId");
        if (requestIdHeader != null ) {
        	MDC.put(LogConstants.REQUEST_ID_MDC_KEY, requestIdHeader);
        } else {
        	MDC.put(LogConstants.REQUEST_ID_MDC_KEY, UUID.randomUUID().toString());
        }
        
        String userAgentHeader = httpServletRequest.getHeader("user-agent");
        
        NEW_REQ.info(userAgentHeader);
        
        
        

        try {
            chain.doFilter(request, response);
        } finally {
        	long duration = System.currentTimeMillis() - startTime;
        	REQUEST.info(" Execution time = " + duration + " ms");
            // remove the key once you are done with it
        	MDC.remove(LogConstants.REQUEST_URL_MDC_KEY);
        	MDC.remove(LogConstants.USERNAME_MDC_KEY);
        	MDC.remove(LogConstants.REQUEST_ID_MDC_KEY);
        }
    }
    
    @Override
    public void destroy() {}

}