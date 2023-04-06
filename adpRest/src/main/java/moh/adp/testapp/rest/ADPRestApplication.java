package moh.adp.testapp.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import moh.adp.testapp.rest.controller.TestRestHandler;

@ApplicationPath("/rs/")
public class ADPRestApplication extends Application {

	public ADPRestApplication() {
		System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
	}	
	
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(TestRestHandler.class);
		return classes;
	}
	
}
