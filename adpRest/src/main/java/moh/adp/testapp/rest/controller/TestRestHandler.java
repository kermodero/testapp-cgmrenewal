package moh.adp.testapp.rest.controller;


import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;

import moh.adp.testapp.adam.ADAMServer;
import moh.adp.testapp.rest.common.Result;
import moh.adp.testapp.rest.common.TestScope;
import moh.adp.testapp.util.DummyDataService;

@Path("adam")
@Produces("application/json")
public class TestRestHandler {
	@Context
	public HttpServletRequest request;
	@Context
	public SecurityContext secContext;
	@Inject
	public ADAMServer adamServer;	
	@Inject
	public Logger logger;	
	@Inject //TODO Remove this
	public DummyDataService dummyData;
	
	//private String username = "adpadministrator1";
	private String username = "adpnopayappv";
	private String password = "Password2010$";

	@GET
	@Path("/regression/{scope}/{testId}")
	public Result runTests(@PathParam("scope") String scope, @PathParam("testId") String testId) throws Exception {
		logger.debug("regression, scope: " + scope);
		login();
		TestScope ts = TestScope.get(scope);
		return adamServer.runNightlyBatchDirectly(ts, testId);
	}

	@GET
	@Path("/renew/{testId}")
	public Result runRenewal(@PathParam("testId") String testId) throws Exception {
		logger.debug("regression, scope: " + testId);
		login();
		Result r = new Result();
		adamServer.runRenewal(testId);
		return r;
	}

	@GET
	@Path("/claim/create/{testName}")
	public Result createClaims(@PathParam("testName") String testName) throws Exception {
		logger.debug("create claims test id: " + testName);
		login();
		Result r = new Result();
		adamServer.createClaims(r, testName);
		return r;
	}	
	
	@GET
	@Path("/data/{level}/{parentId}")
	public Result allData(@PathParam("level") String level, @PathParam("parentId") String parentId) throws Exception {
		logger.debug("all data: " + level);
		login();
		Result r = new Result();
		r.setContent(dummyData.someTests());
		return r;
	}		

	@GET
	@Path("/datum/{level}/{id}")
	public Result oneDatum(@PathParam("level") String level, @PathParam("id") String id) throws Exception {
		logger.debug("edit: " + id);
		login();
		Result r = new Result();
		r.setContent(dummyData.aTest(Integer.parseInt(id)));
		return r;
	}		
	
	@PUT
	@Path("/datum/{level}/{id}")
	public Result updateObject(@PathParam("json") String json) throws Exception {
		logger.debug("create claims test id: " + json);
		login();
		Result r = new Result();
		System.out.println("updating " + json);
		return r;
	}	
	
	@POST
	@Path("/datum/{level}/{id}")
	public Result createObject(@PathParam("json") String json) throws Exception {
		logger.debug("create claims test id: " + json);
		login();
		Result r = new Result();
		System.out.println("creating " + json);
		return r;
	}		

	@GET
	@Path("/etl/{directory}/{testName}")
	public Result runETL(@PathParam("directory") String directory, @PathParam("testName") String testName) throws Exception {
		logger.debug("running etl: " + directory + "  " + testName);
		Result r = new Result();
		adamServer.runETL(r, directory, testName);
		return r;
	}		
	
	//Have to turn off application security in WAS Console -> Security -> Global security
	private void login() throws ServletException {
		try {
			request.logout();
			request.login(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

}
