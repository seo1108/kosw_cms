INSERT INTO c_customer (cust_code, cust_name, post_name, post_email, post_phone, cust_remarks, cust_reg_time)
SELECT '10000', '포토인테리어', '담당자', 'photo@test.com', '000000000', '슈퍼고객사',DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') FROM DUAL
WHERE NOT EXISTS (
	SELECT cust_code FROM c_customer WHERE cust_code = '10000'
);


INSERT INTO a_version (
	admin_seq, 
	app_type,
	app_version,
	force_kill,
	update_desc,
	ver_reg_time ) 
SELECT 
	'1',
	'I',
	'1.0.0',
	'N',
	'앱이 업데이트 되었습니다.',
	DATE_FORMAT(NOW(), '%Y%m%d%H%i%s')
FROM DUAL WHERE NOT EXISTS (
	SELECT * FROM a_version WHERE app_type = 'I'
);

INSERT INTO a_version (
	admin_seq, 
	app_type,
	app_version,
	force_kill,
	update_desc,
	ver_reg_time ) 
SELECT 
	'1',
	'A',
	'1.0.0',
	'N',
	'앱이 업데이트 되었습니다.',
	DATE_FORMAT(NOW(), '%Y%m%d%H%i%s')
FROM DUAL WHERE NOT EXISTS (
	SELECT * FROM a_version WHERE app_type = 'A'
);

