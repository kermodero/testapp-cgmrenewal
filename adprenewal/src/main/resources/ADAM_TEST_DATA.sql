
--DELETE FROM APP.EXPECTED_OUTCOME;
--DELETE FROM APP.E_CLAIM_SECTION_2;
--DELETE FROM APP.E_CLAIM_EQUIPMENT_SPEC;
--DELETE FROM APP.E_CLAIM_REC_VARIANCE;      
--DELETE FROM APP.CLAIM_REC_VARIANCE;
DELETE FROM APP.RENEWAL_ERROR_OUTCOME;
DELETE FROM APP.RENEWAL_POSITIVE_OUTCOME;      
DELETE FROM APP.RENEWAL_REC_VARIANCE;
--DELETE FROM APP.E_CLAIM_RECORD;
--DELETE FROM APP.CLAIM_REC_FIELD;
DELETE FROM APP.RENEWAL_REC_FIELD;
--DELETE FROM APP.CLAIM_RECORD;
--DELETE FROM APP.INVOICE_RECORD;
DELETE FROM APP.RENEWAL_RECORD;
DELETE FROM APP.RECORD_SET;
DELETE FROM APP.TEST_SET;
DELETE FROM APP.TEST;

INSERT INTO APP.TEST (NAME, DESCRIPTION) values ('GENERIC_RENEWAL', 'Test generation of GM renewals');
INSERT INTO APP.TEST (NAME, DESCRIPTION) values ('GENERIC_INVOICE', 'Test generation of invoices');
INSERT INTO APP.TEST_SET ( TEST_ID, NAME, DESCRIPTION) VALUES ((SELECT ID FROM APP.TEST WHERE NAME='GENERIC_RENEWAL'), 'GM e-renewals', 'GM e-renewals initial test');
INSERT INTO APP.RECORD_SET (TEST_SET_ID, FILE_NAME, DESCRIPTION) VALUES ((SELECT ID FROM APP.TEST_SET WHERE NAME='GM e-renewals'), 'GM Renewals all', 'GM e-renewal records, positive and negative tests');
--INSERT INTO APP.RENEWAL_RECORD (ORIGINAL_CLAIM_NUM, RECORD_SET_ID, HEALTH_NUMBER, ADAM_VENDOR_ID, FILE_NAME, CLIENT_AGENT_ID, DEVICE_CATEGORY, FORM_VERSION) 
--VALUES ('', (SELECT ID FROM APP.RECORD_SET WHERE FILE_NAME='GM Renewals'), 
--		'4267999615', 
--		120049147, 
--		'RWKGMRENEWAL-VRX-12345-4267999615-7654321.xml',
--		14395, 
--		'GM', 
--		'202304');
		
INSERT INTO APP.RENEWAL_RECORD (
	ORIGINAL_CLAIM_NUM, 
	RECORD_SET_ID, 
	HEALTH_NUMBER, 
	ADAM_VENDOR_ID, 
	FILE_NAME, 
	CLIENT_AGENT_ID, 
	QUESTION1, 
	QUESTION2, 
	QUESTION3, 
	CERTIFICATION, 
	SIGNED_BY, 
	SIGNATURE, 
	SIGN_DATE, 
	DEVICE_CATEGORY, 
	FORM_VERSION, 
	EXPECTED_OUTCOME, 
	ERROR_MESSAGE, 
	RANDOM_RECORDS)
VALUES('', (SELECT ID FROM APP.RECORD_SET WHERE FILE_NAME='GMERenewals'),
		'4267999615', 120049147, 'RWKGMRENEWAL-VRX-12345-4267999615-7654333.xml', 
		2074916,
		'T',
		'T',
		'F',
		'valid',
		'Homer Simpson',
		'Simpson',
		DATE('2023-03-28'),
		'GM',
		'202304',
		'F',
		'Invalid last name',
		8);

INSERT INTO APP.RENEWAL_ERROR_OUTCOME (
	RENEWAL_RECORD_ID, 
	ERROR_MESSAGE_ID, 
	ERROR_PARM1, 
	ERROR_PARM2, 
	ERROR_PARM3)
VALUES((SELECT MAX(ID) FROM APP.RENEWAL_RECORD),
	2060, 'Last Name', null, null);


INSERT INTO APP.RENEWAL_RANDOM
(RECORD_SET_ID, PROPERTY_NAME, STRING_PROPERTY_VALUE, FORM_SECTION, EXPECTED_OUTCOME, ERROR_MESSAGE, DEVICE_CATEGORY, FORM_VERSION)
VALUES((SELECT ID FROM APP.RECORD_SET WHERE FILE_NAME='GM Renewals all'), 
'QUESTION1', 'Z', 2, 'F', 'Question 1 invalid selection', 'GM', '202305');

INSERT INTO APP.RENEWAL_RANDOM
(RECORD_SET_ID, PROPERTY_NAME, STRING_PROPERTY_VALUE, FORM_SECTION, EXPECTED_OUTCOME, ERROR_MESSAGE, DEVICE_CATEGORY, FORM_VERSION)
VALUES((SELECT ID FROM APP.RECORD_SET WHERE FILE_NAME='GM Renewals  all'), 
'LAST_NAME', '234FF', 1, 'F', 'invalid client last name', 'GM', '202305');

INSERT INTO APP.RENEWAL_REC_FIELD
(RENEWAL_RECORD_ID, PROPERTY_NAME, PROPERTY_VALUE, FORM_SECTION)
VALUES(0, 'lastName', '3FLINTSTONE', 1);

INSERT INTO APP.RENEWAL_REC_FIELD
(RENEWAL_RECORD_ID, PROPERTY_NAME, PROPERTY_VALUE, FORM_SECTION)
VALUES(0, 'firstName', 'FR2ED', 1);

INSERT INTO APP.RENEWAL_REC_FIELD
(RENEWAL_RECORD_ID, PROPERTY_NAME, PROPERTY_VALUE, FORM_SECTION)
VALUES(0, 'question1', 'Y', 2);
		



SELECT ID FROM APP.RECORD_SET WHERE file_NAME='GM Renewals all'		
select * from APP.RENEWAL_RECORD
		
select * from APP.RECORD_SET		
		
-- CGM vendor ids --120049147, 64218

--INSERT INTO APP.CLAIM_RECORD (RECORD_SET_ID, ADAM_CLIENT_ID, ADAM_VENDOR1_ID, ADAM_PHYSICIAN_ID)
--       VALUES (0, 319082, 83095, 62368);
       

--SELECT MAX(ID) FROM APP.RECORD_SET
--
--INSERT INTO APP.CLAIM_REC_VARIANCE (CLAIM_RECORD_ID,PROPERTY_NAME,STRING_PROPERTY_VALUE,NUMERIC_PROPERTY_VALUE, DATE_PROPERTY_VALUE)
--       VALUES (0, 'firstName', 'Fred', null, null);
--
--INSERT INTO APP.CLAIM_REC_VARIANCE (CLAIM_RECORD_ID,PROPERTY_NAME,STRING_PROPERTY_VALUE,NUMERIC_PROPERTY_VALUE, DATE_PROPERTY_VALUE)
--       VALUES (0, 'lastName', 'Flintstone', null, null);
--
--INSERT INTO APP.CLAIM_REC_VARIANCE (CLAIM_RECORD_ID,PROPERTY_NAME,STRING_PROPERTY_VALUE,NUMERIC_PROPERTY_VALUE, DATE_PROPERTY_VALUE)
--       VALUES (0, 'deviceCategory', 'HD', null, null);
--




-- SELECT * FROM CLIENT SAMPLE(0.001) FETCH FIRST ROW only
select * 
from app.test t
	inner join app.test_set ts
		on t.id = ts.test_id
	inner join app.record_set rs
		on rs.test_set_id = ts.id
	inner join app.claim_record cr
		on cr.record_set_id = rs.id		
	inner join app.claim_rec_variance crv
		on crv.claim_record_id = cr.id	
		
		