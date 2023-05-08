# laptop_java

노트북 자산반출 V1.0 java version

## - 개선사항

## - 미반영 

#### \* 20230417 git upload 

## - 반영 완료


## 테이블 변경 쿼리 (데이터 이동)
 
```

-- adm_agent -> AdmAgent
TRUNCATE TABLE AdmAgent;
INSERT INTO AdmAgent(id, agentIp, agentNum, agentPort, bizDeptCd, datetime)
SELECT id, agent_ip, agent_num, agent_port, bizDeptCd, DATETIME FROM adm_agent;

-- adm_member -> AdmMember
TRUNCATE TABLE AdmMember;
INSERT INTO AdmMember(id, datetime, password, prop, username, xrayNum, agentId)
SELECT 
id, DATETIME, u_pass, prop, u_id, xray_num, 
CASE
	WHEN agent IN (SELECT id FROM adm_agent)
	THEN agent
	ELSE null
END AS agent 
FROM adm_member;

-- adm_set -> AdmSet
TRUNCATE TABLE AdmSet;
INSERT INTO AdmSet(id, alertBlue, alertBlueSound, alertIp, alertPort, alertRed, alertRedSound, antenaIp, antenaPort, datetime, xray, agentId)
SELECT id, alert_blue, alert_blue_sound, alert_ip, alert_port, alert_red, alert_red_sound, antena_ip, antena_port, datetime, xray, agent FROM adm_set;

-- adm_set2 -> SystemInfo
TRUNCATE TABLE SystemInfo;
INSERT INTO SystemInfo (id, datetime, set1, set2, set3, set4, agentId)
SELECT id, datetime, set1, set2, set3, set4, agent FROM adm_set2;

-- br_match -> LaptopInfo
TRUNCATE TABLE LaptopInfo;
INSERT INTO LaptopInfo (id, asset, barcode, datetime, rfid, serial, userId)
SELECT 
b.id, b.u_asset, b.u_code, b.datetime, b.u_rfid, b.u_serial, (SELECT id FROM users u WHERE u.u_num = b.u_num LIMIT 1) AS userId
FROM br_match b;

-- users -> User
TRUNCATE TABLE User;
INSERT INTO User (id, datetime, userNo, userPart, userPos, username)
SELECT 
id, datetime, u_num, u_part, u_pos, u_name
FROM users;

-- log_con -> LogCon
TRUNCATE TABLE LogCon;
INSERT INTO LogCon (id, agent, agentIp, agentPort, datetime, etc)
SELECT 
id, agent_id, agent_ip, agent_port, datetime, etc
FROM log_con;

-- event_history -> EventHistory
TRUNCATE TABLE EventHistory;
INSERT INTO EventHistory (id, agent, antenaNo, barcode, bizDeptCd, bizDeptText, datetime, errorCode, result, rfid, userNo, username, xray)
SELECT 
e.id, e.agent, e.antena_num, b.u_code, a.bizDeptCd
,CASE
	WHEN a.bizDeptCd = '07'
	THEN '파주'
	WHEN a.bizDeptCd = '03'
	THEN '안산'
	WHEN a.bizDeptCd = '01'
	THEN '광주'
	WHEN a.bizDeptCd = '02'
	THEN '구미'
	WHEN a.bizDeptCd = '04'
	THEN '평택'
	WHEN a.bizDeptCd = '18'
	THEN '마곡'
	ELSE '미등록'
END AS bizDeptText
, e.datetime, err_code, result, e.rfid, u.u_num, u.u_name, e.xray_id
FROM event_history e
LEFT JOIN br_match b
ON e.rfid = b.u_rfid
LEFT JOIN users u
ON b.u_num = u.u_num
LEFT JOIN adm_agent a
ON e.agent = a.id;

```
