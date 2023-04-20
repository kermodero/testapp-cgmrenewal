package moh.adp.xml;

public interface RandomQueries {

	String RANDOM_HEALTH_NUM = "SELECT HEALTH_NUMBER " +  
			"FROM client sample(1)                 " + 
			"WHERE DECEASED_DATE IS NULL 	       " +
			"AND CLIENT_REVIEW_REASON_CD IS NULL   " +
			"AND BIO_INFO_REVIEW_REQUIRED_IND = 'N'" +
			"AND REVIEW_REQUESTER_ROLE_CD IS NULL  " +
			"AND rownum = 1                        ";

	String RANDOM_HEALTH_NUM_WITH_GM_CLAIM = "";
	
	String RANDOM_CLIENT_AGENT_ID = 
			"SELECT client_agent_id      " +
			"FROM client_agent sample(1) " + 
			"WHERE rownum = 1            ";
	
	String RANDOM_VENDOR_ID = 
			"SELECT vendor_id   " +
			"FROM vendor sample(1)   " +
			"WHERE VENDOR_REVIEW_REASON_CD IS NULL  " +
			"AND rownum = 1  ";
	
	String RANDOM_GM_VENDOR_ID = 
			"SELECT vendor_id   " +
			"FROM vendor sample(1)   " +
			"WHERE VENDOR_REVIEW_REASON_CD IS NULL  " +
			"AND rownum = 1  ";
	
}
