<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<form id=frm enctype="multipart/form-data" method="post">
	<div id=popup_rfid>
		<div class=box_center>
			<div id=popup_rfid_top>장비 추가</div>
			<div id=rfid_close_box>
				<div id=rfid_close onclick="rfid_close()">X</div>
			</div>
			<div id=popup_rfid_box>
				<input type="hidden" id="set_id">
				<span id=popup_rfid_text_left>
					<li>Agent</li>
					<li>Xray</li>
					<li>Antena IP</li>
					<li>Antena Port</li>
					<li>경광등 IP</li>
					<li>경광등 Port</li>
					<li>청색등</li>
					<li>청색등 사운드</li>
					<li>적색등</li>
					<li>적색등 사운드</li>
				</span> <span id=popup_rfid_text_right>
					<li><select id=agent>
							<option value=>선택</option>
							<c:forEach var="admAgent" items="${admAgentList}">
								<option value="${admAgent.id}">${admAgent.agentIp}
									${admAgent.agentNum}</option>
							</c:forEach>
					</select></li>
					<li><input id=xray><span id=alert_message2></span></li>
					<li><input id=antena_ip><span id=alert_message></span></li>
					<li><input id=antena_port></li>
					<li><input id=alert_ip></li>
					<li><input id=alert_port></li>
					<li><select id=alert_blue>
							<option value=>선택</option>
							<option value=green>On</option>
							<option value=gb>Blink</option>
					</select></li>
					<li><select id=alert_blue_sound>
							<option value=0>사운드 없음</option>
							<option value=sound>사운드 있음</option>
					</select></li>
					<li><select id=alert_red>
							<option value=>선택</option>
							<option value=red>On</option>
							<option value=rb>Blink</option>
					</select></li>
					<li><select id=alert_red_sound>
							<option value=0>사운드 없음</option>
							<option value=sound>사운드 있음</option>
					</select></li>
				</span>
			</div>
			<div id=popup_rfid_box3>
				<span id=btn_new class=btn>등록</span>
			</div>
		</div>
	</div>
	<div id=right_box>
		<div id=title_bg>
			<div id=title>
				<img src=/image/circle.jpg
					style="width: 30px; vertical-align: top; margin-top: 1px; margin-right: 4px;">장비
				설정
			</div>
		</div>
		<div id=table3> 
			<div id=top_box_out>
				<div id=top_box>
					<div id=search_left class=title>Xray 리스트(장비 IP / PORT 세팅)</div>
					<div id=search_right>
						<span onclick=rfid_reg() class="btn btn_w">추가</span>
					</div>
				</div>
			</div>
			<div id=table_contents3>
				<ul class="admin1_table table_title tables">
					<li>Agent</li>
					<li>Xray</li>
					<li>Antena IP</li>
					<li>Port</li>
					<li>경광등 IP</li>
					<li>Port</li>
					<li>청색등</li>
					<li>사운드</li>
					<li>적색등</li>
					<li>사운드</li>
					<li>수정</li>
					<li>삭제</li>
				</ul>
				<c:forEach var="set" items="${admSetList}">
					<ul class="admin1_table tables" adm_set_id="${set.id}">
						<li name="agent" value='${set.admAgent.id}'>${set.admAgent.id}</li>
						<li name="xray" value='${set.xray}'>${set.xray}</li>
						<li name="antena_ip" value='${set.antenaIp}'>${set.antenaIp}</li>
						<li name="antena_port" value='${set.antenaPort}'>${set.antenaPort}</li>
						<li name="alert_ip" value='${set.alertIp}'>${set.alertIp}</li>
						<li name="alert_port" value='${set.alertPort}'>${set.alertPort}</li>
						<c:choose>
							<c:when test="${set.alertBlue == 'green'}">
								<li name="alert_blue" value='${set.alertBlue}'>On</li>
							</c:when>
							<c:otherwise>
								<li name="alert_blue" value='${set.alertBlue}'>Blink</li>
							</c:otherwise>
						</c:choose>
						<li name="alert_blue_sound" value='${set.alertBlueSound}'>${set.alertBlueSound}</li>
						<c:choose>
							<c:when test="${set.alertRed == 'red'}">
								<li name="alert_red" value='${set.alertRed}'>On</li>
							</c:when>
							<c:otherwise>
								<li name="alert_red" value='${set.alertRed}'>Blink</li>
							</c:otherwise>
						</c:choose>
						<li name="alert_red_sound" value='${set.alertRedSound}'>${set.alertRedSound}</li>
						<li class="popup_mod_admin1 btn" onclick="popup_mod_admin1('${set.id}','${set.admAgent.id}','${set.xray}','${set.antenaIp}',
						'${set.antenaPort}','${set.alertIp}','${set.alertPort}','${set.alertBlue}','${set.alertBlueSound}','${set.alertRed}','${set.alertRedSound}')">수정</li>
						<li><span class="del btn" onclick="del_data('${set.id}')">-</span></li>
					</ul>
				</c:forEach>
			</div>
		</div>
	</div>
	</div>
</form>
<script> 
function del_data(id){
    var result = confirm('삭제하시겠습니까?');
    if(result) {
        $.ajax({ 
            url: "/api/deleteAdmSet/"+id, 
            type: 'DELETE', 
            //global: false,
            dataType: 'JSON', 
            success: function (data) {
                alert("삭제완료");
                location.href="/admSet/admin1";
            } 
        }).fail(function() {
            alert("실패");
        });
    }
}
$('#antena_ip').keyup(function() {
    var antena_ip=$('#antena_ip').val();
    $.ajax({
        url: "verification.php", 
        type: 'POST', 
        //global: false,
        dataType: 'JSON', 
        data: { 
            antena_ip:antena_ip
        }, success: function (data) {
            console.log(data);
            if(data=="y")
                $("#alert_message").html("안테나 ip 중복");
            else
                $("#alert_message").html("");
        }
    }).fail(function() {
    });
});
$('#xray').keyup(function() {
    var antena_ip=$('#antena_ip').val();
    var xray=$('#xray').val();
    $.ajax({
        url: "verification2.php", 
        type: 'POST', 
        //global: false,
        dataType: 'JSON', 
        data: { 
            antena_ip:antena_ip,
            xray:xray
        }, success: function (data) {
            console.log(data);
            if(data=="y")
                $("#alert_message2").html("xray 중복");
            else
                $("#alert_message2").html("");
        }
    }).fail(function() {
    });
});
</script>

<%@ include file="../layout/footer.jsp"%>