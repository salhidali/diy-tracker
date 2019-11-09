package com.diytracker.app.logging;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.diytracker.app.logging.MDCLoggingFilter;

@RunWith(MockitoJUnitRunner.class)
public class MDCLoggingFilterTest {
	
	private MDCLoggingFilter mdcFilter = new MDCLoggingFilter();
	
	private Logger NEWREQ_LOGGER = Logger.getLogger("com.airbus.usecasetracker.NEWREQ");
    private Logger REQUEST_LOGGER = Logger.getLogger("com.airbus.usecasetracker.REQUEST");
    
    @Mock
    private Appender newreqSpyAppender;
    @Mock
    private Appender requestSpyAppender;
    @Captor
    private ArgumentCaptor<LoggingEvent> newreqLoggingEventCaptor;
    @Captor
    private ArgumentCaptor<LoggingEvent> requestLoggingEventCaptor;
    
    @Mock(answer=Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest httpRequest;
    @Mock
    HttpServletResponse httpResponse;
    @Mock
    FilterChain filterChain;
    @Mock
    FilterConfig filterConfig;

    
	@Before
	public void setup() throws Exception {
		NEWREQ_LOGGER.addAppender(newreqSpyAppender);
		REQUEST_LOGGER.addAppender(requestSpyAppender);
	}

	@After
	public void teardown() throws Exception {
		NEWREQ_LOGGER.removeAppender(newreqSpyAppender);
		REQUEST_LOGGER.removeAppender(requestSpyAppender);
	}

	/** Cas nominal */
	@Test
	public void testDoFilter() throws Exception {
		
		// given
		Mockito.when(httpRequest.getServerName()).thenReturn("localhost");
		Mockito.when(httpRequest.getServletPath()).thenReturn("/test/test");
		Mockito.when(httpRequest.getHeader("user-agent")).thenReturn("MyBrowser");
		Mockito.when(httpRequest.getHeader("requestId")).thenReturn("MyRequestId");
		Mockito.when(httpRequest.getHeader("username")).thenReturn("MyLogin");
		Mockito.when(httpRequest.getRequestURI()).thenReturn("/MyURI");
		
		Mockito.doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				newreqLoggingEventCaptor.getValue().getMDCCopy();
				return null;
			}
		})
		.when(newreqSpyAppender).doAppend(newreqLoggingEventCaptor.capture());
		
		Mockito.doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				requestLoggingEventCaptor.getValue().getMDCCopy();
				return null;
			}
		})
		.when(requestSpyAppender).doAppend(requestLoggingEventCaptor.capture());
		
		// when
		mdcFilter.doFilter(httpRequest, httpResponse, filterChain);
		
		// then
		Mockito.verify(newreqSpyAppender).doAppend(newreqLoggingEventCaptor.capture());
		LoggingEvent newreqLoggingEvent = newreqLoggingEventCaptor.getValue();
		Assert.assertEquals(Level.INFO, newreqLoggingEvent.getLevel());
		Assert.assertEquals("MyLogin", newreqLoggingEvent.getMDC("username"));
		Assert.assertEquals("MyRequestId", newreqLoggingEvent.getMDC("requestId"));
		Assert.assertEquals("MyBrowser", newreqLoggingEvent.getRenderedMessage());

		Mockito.verify(requestSpyAppender).doAppend(requestLoggingEventCaptor.capture());
		LoggingEvent requestLoggingEvent = requestLoggingEventCaptor.getValue();
		Assert.assertEquals(Level.INFO, requestLoggingEvent.getLevel());
		Assert.assertEquals("MyLogin", requestLoggingEvent.getMDC("username"));
		Assert.assertEquals("MyRequestId", requestLoggingEvent.getMDC("requestId"));
		Assert.assertTrue(requestLoggingEvent.getRenderedMessage().startsWith("/MyURI | MyRequestInfo | "));
		Assert.assertTrue(MDC.getContext().isEmpty());
	}
	
	@Test
	public void testExceptionThrown() throws Exception {
		
		// given
		Mockito.when(httpRequest.getServerName()).thenReturn("localhost");
		Mockito.when(httpRequest.getServletPath()).thenReturn("/test/test");
		Mockito.when(httpRequest.getRequestURI()).thenReturn("/MyURI");
		Mockito.doThrow(new IllegalStateException("MyError")).when(filterChain).doFilter(httpRequest, httpResponse);
		
		// when
		try {
			mdcFilter.doFilter(httpRequest, httpResponse, filterChain);
			Assert.fail("should throw IllegalStateException");
		}
		// then
		catch (IllegalStateException e) {
			Mockito.verify(requestSpyAppender).doAppend(requestLoggingEventCaptor.capture());
			LoggingEvent requestLoggingEvent = requestLoggingEventCaptor.getValue();
			Assert.assertEquals("MyError", e.getMessage());
			Assert.assertEquals(Level.ERROR, requestLoggingEvent.getLevel());
			Assert.assertTrue(requestLoggingEvent.getRenderedMessage().startsWith("/MyURI | MyRequestInfo | "));
		}
	}
	
	

	@Test
	public void testLogOnlyPostRequests() throws Exception {
		
		// given
		Mockito.when(filterConfig.getInitParameter("logOnlyPostRequests")).thenReturn("true");
		Mockito.when(httpRequest.getMethod()).thenReturn("GET");
		
		// when
		mdcFilter.init(filterConfig);
		mdcFilter.doFilter(httpRequest, httpResponse, filterChain);
		
		// then
		Mockito.verifyZeroInteractions(newreqSpyAppender);
		Mockito.verifyZeroInteractions(requestSpyAppender);
	}

	
}
