


INSERT INTO APP.TEST (NAME, DESCRIPTION) values ('GENERIC_CLAIM', 'Test generation of claims');
INSERT INTO APP.TEST (NAME, DESCRIPTION) values ('GENERIC_INVOICE', 'Test generation of invoices');

INSERT INTO APP.TEST_SET ( TEST_ID, NAME, DESCRIPTION) VALUES (0, 'HD claims', '');

INSERT INTO APP.RECORD_SET (TEST_SET_ID, FILE_NAME, DESCRIPTION) VALUES (0, 'HD_FILES', 'HD test records');

INSERT INTO APP.CLAIM_RECORD (RECORD_SET_ID, ADAM_CLIENT_ID, ADAM_VENDOR1_ID, ADAM_PHYSICIAN_ID)
       VALUES (0, 319082, 83095, 62368);

INSERT INTO APP.CLAIM_REC_VARIANCE (CLAIM_RECORD_ID,PROPERTY_NAME,STRING_PROPERTY_VALUE,NUMERIC_PROPERTY_VALUE, DATE_PROPERTY_VALUE)
       VALUES (0, 'firstName', 'Fred', null, null);

INSERT INTO APP.CLAIM_REC_VARIANCE (CLAIM_RECORD_ID,PROPERTY_NAME,STRING_PROPERTY_VALUE,NUMERIC_PROPERTY_VALUE, DATE_PROPERTY_VALUE)
       VALUES (0, 'lastName', 'Flintstone', null, null);

INSERT INTO APP.CLAIM_REC_VARIANCE (CLAIM_RECORD_ID,PROPERTY_NAME,STRING_PROPERTY_VALUE,NUMERIC_PROPERTY_VALUE, DATE_PROPERTY_VALUE)
       VALUES (0, 'deviceCategory', 'HD', null, null);

-- INSERT INTO APP.INVOICE_RECORD (RECORD_SET_ID,ADAM_CLAIM_ID) VALUES ( );



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
		
		