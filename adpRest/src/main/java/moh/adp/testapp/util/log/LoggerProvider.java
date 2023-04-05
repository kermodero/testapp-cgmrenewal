package moh.adp.testapp.util.log;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LoggerProvider {
	
	/**
	 * The idea here is to allow injecting the logger into classes.
	 * 
	 *  All a class needs to do is put:
	 * 
	 *  @Inject
	 * 	public Logger logger;
	 * 
	 * ... The container and this producer then see to it that an appropriate, 
	 * properly initialized logger is injected.
	 * 
	 * For details please see:
	 * https://docs.oracle.com/javaee/7/api/javax/enterprise/inject/spi/InjectionPoint.html
	 * https://docs.oracle.com/javaee/7/api/javax/enterprise/inject/Produces.html
	 */
	@Produces
	public Logger getLogger(final InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
	}
	
}
