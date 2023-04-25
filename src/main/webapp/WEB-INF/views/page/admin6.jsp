<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<form id=frm>
	<div id=right_box>
		<div id=title_bg>
			<div id=title>
				<img src=/image/circle.jpg
					style="width: 30px; vertical-align: top; margin-top: 1px; margin-right: 4px;">장비
				확인
			</div>
		</div>
		<div id=table3>
			<input type=hidden id=adm_set_id>
			<div id=top_box_out>
				<div id=top_box>
					<div id=search_left class=title>Xray 리스트(장비 IP / PORT 세팅)</div>
					<!--<div id=search_right>
                            <span onclick=load() class="btn btn_w">새로고침</span>
                        </div>-->
				</div>
			</div>
			<div id=table_contents3>
				<ul class="admin6_table table_title tables">
					<li>Agent</li>
					<li>Xray</li>
					<li>Antena IP</li>
					<li>Antena status</li>
					<li>Port</li>
					<li>경광등 IP</li>
					<li>경광등 status</li>
					<li>Port</li>
					<li>경광등 테스트 Red</li>
					<li>경광등 테스트 Green</li>
				</ul>
				<c:if test="${!empty admSet}"> 
				<input type="hidden" id="agent" value="${admSet.admAgent.id}" />
					<ul class="admin6_table tables" adm_set_id="${admSet.id}">
						<li name="agent" value='${admSet.admAgent.id}'>${admSet.admAgent.id}</li>
						<li name="xray" value='${admSet.xray}'>${admSet.xray}</li>
						<li name="antena_ip" value='${admSet.antenaIp}'>${admSet.antenaIp}</li>
						<li id="antena_${admSet.admAgent.id}" name="antena_status"></li>
						<li name="antena_port" value='${admSet.antenaPort}'>${admSet.antenaPort}</li>
						<li name="alert_ip" value='${admSet.alertIp}'>${admSet.alertIp}</li>
						<li id="alert_${admSet.admAgent.id}" name="alert_status"></li>
						<li name="alert_port" value='${admSet.alertPort}'>${admSet.alertPort}</li>
						<li><span class="btn_alert btn" onclick="alert_test('${admSet.admAgent.id}', '1','1','2','1','0')">Red Test</span></li>
						<li><span class="btn_alert btn" onclick="alert_test('${admSet.admAgent.id}', '1','2','2','1','0')">Green Test</span></li>
					</ul>
				</c:if>

			</div>
		</div>
	</div> 
</form>
<script>
	var field_list = new Array('agent', 'xray', 'antena_ip', 'device_status',
			'antena_port', 'alert_ip', 'alert_port');
	load();

	function load() {
		$.ajax({
			url : "load.php",
			type : 'POST',
			//global: false,
			dataType : 'JSON',
			async : true,
			data : {
				field_list : field_list,
			},
			success : function(data) {
				//alert(field_list);
				$("#admin1_content").load("admin6_table.php");
			}
		}).fail(function() {
		});
	}

	function alert_test(agent, xray_id, lamp_type, warning_time, warning_type, sound_onoff) {
		let data = {
				agent: agent,
				xray_id : xray_id,
				lamp_type : lamp_type,
				warning_time : warning_time,
				warning_type : warning_type,
				sound_onoff : sound_onoff
		}
		$.ajax({
			//url : "/api/control_lamp",
			url : "/rest/control_lamp.php",
			type : 'POST',
			//global: false,
			contentType: 'application/json',
			dataType: 'JSON', 
			data : JSON.stringify(data),
			success : function(data) {
				//alert("신호 전송");
				console.log(data);
			}
		}).fail(function() {
		});
	}

	$(document).ready(function() {
		var agent = $("#agent").val();
		/* var agent_temp = agent.split("/");
		if (agent_temp.length == 1)
			setTimeout(agent_check, 1000, agent_temp);
		else {
			for (var i = 0; i < agent_temp.length - 1; i++)
				setTimeout(agent_check, 1000, agent_temp[i]);
		} */
		console.log("1111");
		agent_check(agent);
	});

	function agent_check(agent) {
		console.log("1111");
		$.ajax({
			//url : "../api/get_device_state",
			url : "../rest/get_device_state.php",
			type : 'POST',
			//global: false,
			dataType : 'JSON',
			data : JSON.stringify({
				agent : agent,
			}),
			contentType: "application/json; charset=utf-8", 
			success : function(data) {
				
				data['antena_result'] === 'connected' ? $("#antena_" + agent).css("color", "green") : $(
	                "#antena_" + agent).css("color", "red");
	            data['alert_result'] === 'connected' ? $("#alert_" + agent).css("color", "green") : $(
	                "#alert_" + agent).css("color", "red");
	            $("#antena_" + agent).html(" " + data['antena_result']).css("font-weight", "bold");
	            $("#alert_" + agent).html(" " + data['alert_result']).css("font-weight", "bold");
				console.log(data);
				//console.log(data);
				//$("#"+id).html(data+"1")
			}
		}).fail(function() {
			$("#agent_" + agent).html("fail");
		});
	}

	function agent_input(agent, data) {
	}
</script>

<%@ include file="../layout/footer.jsp"%>