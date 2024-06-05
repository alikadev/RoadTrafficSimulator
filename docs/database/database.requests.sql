use rts_main;

-- ======== DUMMIES ======== --
-- Dummy select every account
select * from Account;

-- Dummy select every account's setting
select * from AccountSetting;

-- Dummy select every setting
select * from Setting;

-- ======== NON-DUMMIES ======== --
-- Select password from account's id
SELECT password LIKE "123" AS result FROM account WHERE id = "abc";

-- Insert an account
INSERT INTO Account VALUES ("abc", "123");

-- Select all sets for account
SELECT DISTINCT setname FROM accountsetting WHERE account="abc";

-- Select settings and values for account and set
SELECT
	st.id AS setting,
    CASE WHEN acst.value IS NULL THEN st.default ELSE acst.value END AS value
FROM AccountSetting AS acst
RIGHT JOIN Setting AS st 
	ON acst.setting = st.id 
	AND acst.account = "abc" 
	AND acst.setname = "S3";

-- Check if a setting set exists for an account
SELECT 1 FROM dual 
WHERE EXISTS(
    SELECT 1 FROM accountsetting 
    WHERE account LIKE 'a' 
    AND setname LIKE 'test' );

-- Insert/update setting set for an account et set
-- Warn: value need to be set in values AND at the end
INSERT INTO AccountSetting VALUES ("abc", "E2", 5.5, "S3") ON DUPLICATE KEY UPDATE value=5.5; 

