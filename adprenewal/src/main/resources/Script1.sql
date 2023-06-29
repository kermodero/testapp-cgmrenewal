
DELETE FROM APP.EXPECTED_OUTCOME;
DELETE FROM APP.E_CLAIM_SECTION_2;
DELETE FROM APP.E_CLAIM_EQUIPMENT_SPEC;
DELETE FROM APP.E_CLAIM_REC_VARIANCE;      
DELETE FROM APP.CLAIM_REC_VARIANCE;      
DELETE FROM APP.RENEWAL_REC_VARIANCE;
DELETE FROM APP.E_CLAIM_RECORD;
DELETE FROM APP.CLAIM_REC_FIELD;
DELETE FROM APP.RENEWAL_REC_FIELD;
DELETE FROM APP.CLAIM_RECORD;
DELETE FROM APP.INVOICE_RECORD;
DELETE FROM APP.RENEWAL_RECORD;
DELETE FROM APP.RECORD_SET;
DELETE FROM APP.TEST_SET;
DELETE FROM APP.TEST;

DROP TABLE APP.EXPECTED_OUTCOME;
DROP TABLE APP.E_CLAIM_SECTION_2;
DROP TABLE APP.E_CLAIM_EQUIPMENT_SPEC;
DROP TABLE APP.E_CLAIM_REC_VARIANCE;
DROP TABLE APP.CLAIM_REC_VARIANCE;
DROP TABLE APP.RENEWAL_REC_VARIANCE;
DROP TABLE APP.E_CLAIM_RECORD;
DROP TABLE APP.CLAIM_REC_FIELD;
DROP TABLE APP.RENEWAL_REC_FIELD;
DROP TABLE APP.CLAIM_RECORD;
DROP TABLE APP.INVOICE_RECORD;
DROP TABLE APP.RENEWAL_RECORD;
DROP TABLE APP.RECORD_SET;
DROP TABLE APP.TEST_SET;
DROP TABLE APP.TEST;


create table APP.TEST (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	NAME	varchar(20) NOT NULL UNIQUE,
	DESCRIPTION varchar(1000)
);

create table APP.TEST_SET (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	TEST_ID INT,
	NAME	varchar(20),
	DESCRIPTION varchar(1000),
	FOREIGN KEY (TEST_ID) REFERENCES APP.TEST(ID)
);

create table APP.RECORD_SET ( --AKA excel FILE
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	TEST_SET_ID INT,
	FILE_NAME   VARCHAR(20),
	DESCRIPTION VARCHAR(1000),
	FOREIGN KEY (TEST_SET_ID) REFERENCES APP.TEST_SET(ID)
);

create table APP.RENEWAL_RECORD (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	ORIGINAL_CLAIM_NUM VARCHAR(16) NOT NULL,
	RECORD_SET_ID INT NOT NULL,
	HEALTH_NUMBER VARCHAR(24) NOT NULL,
	ADAM_VENDOR_ID BIGINT,
--	ADAM_PHYSICIAN_ID BIGINT,	
	FILE_NAME VARCHAR(64) NOT NULL,
	CLIENT_AGENT_ID BIGINT,
	QUESTION1 CHAR(1),
	QUESTION2 CHAR(1),
	QUESTION3 CHAR(1),
	CERTIFICATION VARCHAR(24),
	SIGNED_BY VARCHAR(128),
	SIGNATURE VARCHAR(128),
	SIGN_DATE DATE,	
	DEVICE_CATEGORY VARCHAR(8),
	FORM_VERSION VARCHAR(8),
	EXPECTED_OUTCOME CHAR(1) NOT NULL,
	ERROR_MESSAGE VARCHAR(1024),		
	RANDOM_RECORDS INTEGER DEFAULT 0 NOT NULL,	
	FOREIGN KEY (RECORD_SET_ID) REFERENCES APP.RECORD_SET(ID)
);

create table APP.RENEWAL_REC_VARIANCE (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	RENEWAL_RECORD_ID INT NOT NULL,
	PROPERTY_NAME VARCHAR(120) NOT NULL,
	STRING_PROPERTY_VALUE VARCHAR(4000),
	FORM_SECTION INT NOT NULL,
	FOREIGN KEY (RENEWAL_RECORD_ID) REFERENCES APP.RENEWAL_RECORD(ID)
);

create table APP.RENEWAL_REC_FIELD (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	RENEWAL_RECORD_ID INT NOT NULL,
	PROPERTY_NAME VARCHAR(120) NOT NULL,
	PROPERTY_VALUE VARCHAR(4000),
	FORM_SECTION INT NOT NULL,	
	FOREIGN KEY (RENEWAL_RECORD_ID) REFERENCES APP.RENEWAL_RECORD(ID)
);

create table APP.CLAIM_RECORD (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	RECORD_SET_ID INT NOT NULL,
	HEALTH_NUMBER VARCHAR(24) NOT NULL,
	ADAM_VENDOR1_ID BIGINT,
	ADAM_PHYSICIAN_ID BIGINT,
	DEVICE_CATEGORY VARCHAR(8),
	FOREIGN KEY (RECORD_SET_ID) REFERENCES APP.RECORD_SET(ID)
);

create table APP.INVOICE_RECORD (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	RECORD_SET_ID INT NOT NULL,
	ADAM_CLAIM_NUM VARCHAR(32) NOT NULL,
	FOREIGN KEY (RECORD_SET_ID) REFERENCES APP.RECORD_SET(ID)
);

create table APP.CLAIM_REC_VARIANCE (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	CLAIM_RECORD_ID INT NOT NULL,
	PROPERTY_NAME VARCHAR(120) NOT NULL,
	STRING_PROPERTY_VALUE VARCHAR(4000),
	NUMERIC_PROPERTY_VALUE DECIMAL,
	DATE_PROPERTY_VALUE DATE,	
	FOREIGN KEY (CLAIM_RECORD_ID) REFERENCES APP.CLAIM_RECORD(ID)
);

create table APP.CLAIM_REC_FIELD (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	CLAIM_RECORD_ID INT NOT NULL,
	PROPERTY_NAME VARCHAR(120) NOT NULL,
	PROPERTY_VALUE VARCHAR(4000),
	FOREIGN KEY (CLAIM_RECORD_ID) REFERENCES APP.CLAIM_RECORD(ID)
);

create table APP.E_CLAIM_RECORD (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	RECORD_SET_ID INT NOT NULL,
	DEVICE_CATEGORY VARCHAR(12),
	GATEKEEPER_DATE DATE,
	HEALTH_NUMBER VARCHAR(24),
	CONF_BENEFIT_Q1YN    VARCHAR(8),
    CONF_BENEFIT_Q1IFYES VARCHAR(8),
    CONF_BENEFIT_Q2YN    VARCHAR(8),
    CONF_BENEFIT_Q3YN    VARCHAR(8),
    CONTACT_ID BIGINT,
    RELATIONSHIP VARCHAR(32),
    SIGNATURE VARCHAR(1024),
    PERSON VARCHAR(64),
    SIGNED_ON DATE,
    PHYSICIAN_BILLING VARCHAR(32),
    AUDIOLOGIST_ID BIGINT,
    AUTHORIZER_ID BIGINT,
	VENDOR_ID BIGINT,
	DELIVERY_PROOF_SIG VARCHAR(1024),
	DELIVERY_PROOF_DATE DATE,
	FOREIGN KEY (RECORD_SET_ID) REFERENCES APP.RECORD_SET(ID)
);

create table APP.E_CLAIM_SECTION_2 (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	E_CLAIM_RECORD_ID INT NOT NULL,
	PROPERTY_NAME VARCHAR(1024) NOT NULL,
	PROPERTY_VALUE VARCHAR(4000),
	FOREIGN KEY (E_CLAIM_RECORD_ID) REFERENCES APP.E_CLAIM_RECORD(ID)
);

create table APP.E_CLAIM_REC_VARIANCE (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	E_CLAIM_RECORD_ID INT NOT NULL,
	PROPERTY_NAME VARCHAR(120) NOT NULL,
	STRING_PROPERTY_VALUE VARCHAR(4000),
	NUMERIC_PROPERTY_VALUE DECIMAL,
	DATE_PROPERTY_VALUE DATE,	
	FOREIGN KEY (E_CLAIM_RECORD_ID) REFERENCES APP.E_CLAIM_RECORD(ID)
);

create table APP.E_CLAIM_EQUIPMENT_SPEC (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	E_CLAIM_RECORD_ID INT NOT NULL,
	VALUES_AS_JASON VARCHAR(32000),	
	FOREIGN KEY (E_CLAIM_RECORD_ID) REFERENCES APP.E_CLAIM_RECORD(ID)
);

CREATE TABLE APP.RENEWAL_ERROR_OUTCOME (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	RENEWAL_RECORD_ID INT NOT NULL,
	ERROR_MESSAGE_ID BIGINT,
	ERROR_PARM1 VARCHAR(200),
	ERROR_PARM2 VARCHAR(200),
	ERROR_PARM3 VARCHAR(200),
	FOREIGN KEY (RENEWAL_RECORD_ID) REFERENCES APP.RENEWAL_RECORD(ID)
);

CREATE TABLE APP.RENEWAL_POSITIVE_OUTCOME (
	ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
	RENEWAL_RECORD_ID INT NOT NULL,
	SQL_UUID VARCHAR(128) NOT NULL,
	PARAM1 VARCHAR(1000),
	PARAM2 VARCHAR(1000),
	PARAM3 VARCHAR(1000),
	PARAM4 VARCHAR(1000),				
	EXPECTED_OUTCOME VARCHAR(1000),	
	FOREIGN KEY (RENEWAL_RECORD_ID) REFERENCES APP.RENEWAL_RECORD(ID)
);

