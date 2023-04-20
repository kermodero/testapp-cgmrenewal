package moh.adp.xml;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import moh.adp.db.common.TestDBException;


public class RandomDataService implements RandomQueries {
	private static RandomDataService rds;
	private static String ADP_DATA_SOURCE = "jdbc/adp/ds";	
	private static DataSource adpDS = null;
	
	static {
		rds = new RandomDataService();
	}
	
	public static RandomDataService instance(){
		return rds;
	}

	private RandomDataService() {
		
	}

	public String getRandomHealthNum() {
		return getOneValue(RANDOM_HEALTH_NUM, String.class);
	}

	public Long getRandomClientAgent() {
		return getOneValue(RANDOM_CLIENT_AGENT_ID, BigDecimal.class).longValue();
	}	

	public Long getRandomVendor() {
		return getOneValue(RANDOM_VENDOR_ID, BigDecimal.class).longValue();
	}
	
	private <T> T getOneValue(String sql, Class<T> classT) {
		try {
			PreparedStatement ps = getStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return classT.cast(rs.getObject(1));
		} catch (SQLException e) {
			throw new TestDBException("Failed to run query: " + sql, e);
		}
	}

	private PreparedStatement getStatement(String sql) throws SQLException {
		return getAdpDataSource().getConnection().prepareStatement(sql);
	}
	
	private synchronized DataSource getAdpDataSource(){
		if(adpDS == null){
			try{
				System.out.println("Getting initialContext...");
				InitialContext ctx = new InitialContext();
				adpDS = (DataSource) ctx.lookup(ADP_DATA_SOURCE);
				System.out.println("Got adpApp datasource");
			}catch(NamingException e){
				throw new TestDBException("Failed to get datasource: ", e);
			}
		}
		return adpDS;
	}

}
