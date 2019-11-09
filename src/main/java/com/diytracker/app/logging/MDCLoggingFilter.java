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

public class MDCLoggingFilter implements Filter {

    private static final String REQUEST_ID = "requestId";
	private static final String USERNAME_MDC = "username";
	private static final String REQUEST_URL = "requestUrl";
	private static final String USERNAME_CONST = USERNAME_MDC;
    
	private static final Logger NEW_REQ = LoggerFactory.getLogger("com.diytracker.app.NEWREQ");
	private static final Logger REQUEST = LoggerFactory.getLogger("com.diytracker.app.REQUEST");
	
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

    	long startTime = System.currentTimeMillis();
    	
    	HttpServletRequest httpServletRequest = (HttpServletRequest)request;
    	
    	MDC.put(REQUEST_URL, httpServletRequest.getServletPath());
    	
    	 String usernameHeader = httpServletRequest.getHeader(USERNAME_MDC);
    	 
         if (usernameHeader != null ) {
             MDC.put(USERNAME_MDC, usernameHeader);
         } else {
        	 MDC.put(USERNAME_MDC, "anonymous");
         }

        String requestIdHeader = httpServletRequest.getHeader("requestId");
        if (requestIdHeader != null ) {
        	MDC.put(REQUEST_ID, requestIdHeader);
        } else {
        	MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        }
        
        String userAgentHeader = httpServletRequest.getHeader("user-agent");
        
        NEW_REQ.info(userAgentHeader);
        
        
        

        try {
            chain.doFilter(request, response);
        } finally {
        	long duration = System.currentTimeMillis() - startTime;
        	REQUEST.info(" Execution time = " + duration + " ms");
            // remove the key once you are done with it
        	MDC.remove(REQUEST_URL);
        	MDC.remove(USERNAME_MDC);
        	MDC.remove(REQUEST_ID);
        }
    }
    
    @Override
    public void destroy() {}

}