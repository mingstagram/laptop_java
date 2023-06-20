# laptop_java

노트북 자산반출 V1.0 java version

## - 개선사항

## - 미반영 

#### \* 20230417 git upload 

## - 반영 완료

## crontab


```
임의의 경로(/var/crontab)에 test.sh 생성

vi test.sh

#! /bin/bash
curl -X POST http://127.0.0.1/eventHistory/limitDel


sudo crontab -l : 리스트확인
systemctl status crond : cron 상태 확인

crontab -e 

59 23 * * * /var/crontab/test.sh

crontab 설정했으면 cron 재시작
systemctl restart crond

매일 23시 59분에 test.sh(EventHistory 테이블 10만건이 넘어가면 10만건을 제외한 나머지데이터 삭제) 실행

```

## 테이블 변경 쿼리 (데이터 이동 (기존->신규))
 
```

-- 외래키 삭제 가능 쿼리
set FOREIGN_KEY_CHECKS = 0;

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

-- br_match -> LaptopInfoblog
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

-- 외래키 삭제 불가 쿼리
set FOREIGN_KEY_CHECKS = 1;
```


## 테이블 변경 쿼리 (데이터 이동 (신규->기존))
 
```

-- 외래키 삭제 가능 쿼리
set FOREIGN_KEY_CHECKS = 0;

-- AdmAgent -> adm_agent
TRUNCATE TABLE adm_agent;
INSERT INTO adm_agent(id, agent_ip, agent_port, agent_num, bizDeptCd, datetime)
SELECT id, agentIp, agentPort, agentNum, bizDeptCd, DATETIME FROM AdmAgent;

-- AdmMember -> adm_member
TRUNCATE TABLE adm_member;
INSERT INTO adm_member(id, DATETIME, u_pass, prop, u_id, xray_num, agent, bizDeptCd)
SELECT 
id, datetime, password, prop, username, xrayNum, ifnull(agentId, 0),
IFNULL((SELECT bizDeptCd FROM AdmAgent WHERE id = (AdmMember.agentId)), '00')
FROM AdmMember;

-- AdmSet -> adm_set
TRUNCATE TABLE adm_set;
INSERT INTO adm_set(id, alert_blue, alert_blue_sound, alert_ip, alert_port, alert_red, 
alert_red_sound, antena_ip, antena_port, datetime, xray, agent)
SELECT id, alertBlue, alertBlueSound, alertIp, alertPort, alertRed, 
alertRedSound, antenaIp, antenaPort, datetime, xray, agentId FROM AdmSet;

-- SystemInfo -> adm_set2
TRUNCATE TABLE adm_set2;
INSERT INTO adm_set2 (id, datetime, set1, set2, set3, set4, agent)
SELECT id, datetime, set1, set2, set3, set4, agentId FROM SystemInfo;

-- LaptopInfo -> br_match
TRUNCATE TABLE br_match;
INSERT INTO br_match (id, u_num, u_code, u_asset, u_serial, u_rfid, datetime)
SELECT
l.id,
u.userNo,
l.barcode,
l.asset,
l.serial,
l.rfid,
l.datetime
FROM laptopinfo l
LEFT JOIN user u
ON l.userId = u.id;

-- User -> users
TRUNCATE TABLE users;
INSERT INTO users (id, datetime, u_num, u_part, u_pos, u_name, u_code)
SELECT 
id, datetime, userNo, userPart, userPos, username,
(SELECT barcode FROM laptopinfo WHERE userId = user.id)
FROM User;

-- LogCon -> log_con
TRUNCATE TABLE log_con;
INSERT INTO log_con (id, agent_id, agent_ip, agent_port, datetime, etc)
SELECT 
id, agent, agentIp, agentPort, datetime, etc
FROM LogCon;

-- EventHistory -> event_history
TRUNCATE TABLE event_history;
INSERT INTO event_history (id, agent, rfid, result, xray_id, antena_num, u_confirm, DATETIME, err_code)
SELECT id, agent, rfid, result, xray, antenaNo, '', DATETIME, errorCode
FROM EventHistory;

-- 외래키 삭제 불가 쿼리
set FOREIGN_KEY_CHECKS = 1;
```
