<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
 <div id=popup_reg>
    <div id=popup_reg_box1>
        <div id=popup_reg_top>신규 관리자 등록</div>
        <div id=rfid_close_box>
            <div id=rfid_close onclick="reg_close()">X</div>
        </div>
        <input type="hidden" id=u_idx>
        <ul id=popup_reg_box> 
            <li><input id=u_id placeholder="아이디 입력"></li>
            <li><input id=u_pass placeholder="비밀번호 입력"></li> 
            <!--<li><span class=btn_plus_minus>+</span><span class=btn_plus_minus>-</span></li>
                    <li><input id=xray1 placeholder="Xray 추가"></li>
                    <li><input id=xray2 placeholder="Xray 추가"></li>-->
            <li class=xray_text>PC 선택</li>
            <li> 
                <select id=agent>
                    <option value="">선택</option>
                   	<c:forEach var="admAgent" items="${admAgentList}">
						<option value="${admAgent.id}">${admAgent.agentIp} ${admAgent.agentNum}</option>
					</c:forEach>
			</select>
            </li>
            <!--<li class=xray_text>Xray 선택</li>-->
            <li id=xray_refresh>
            </li>
        </ul>
        <div id=popup_reg_box3>
            <span id=popup_save class="btn btn_new">등록</span>
            <span id=btn_del class="btn" onclick="reg_close()">취소</span>
        </div>
    </div>
</div> 
<div id=right_box>
    <div id=title_bg>
        <div id=title><img src=/image/circle.jpg
                style=width:30px;vertical-align:top;margin-top:1px;margin-right:4px;>관리자 관리</div>
    </div>
    <div id=table3>
        <div id=top_box_out>
            <div id=top_box>
                <div id=search_left>
                </div>
                <div id=search_right>
                    <span onclick="location.href='/admMember/infra'" class="btn btn_w1">인프라 관리자 추가</span>
                    <span onclick=popup_reg() class="btn btn_w">추가</span>
                    <span id=chk_del class="btn btn_w">삭제</span>
                </div>
            </div>
        </div>
        <div id=table_contents3>
            <ul class="admin3_table table_title tables">
                <li><input id=cbx_chkAll type=checkbox></li>
                <li>아이디</li>
                <li>Agent PC</li>
                <li>패스워드</li>
                <li>등록 Xray</li>
                <li>회사코드</li>
                <li>수정</li>
            </ul>
            <c:forEach var="admMember" items="${admMemberList}">
				<ul class="admin3_table tables">
					<li><input value="${admMember.id}" class=chk type=checkbox></li>
					<li>${admMember.username}</li>
					<li>${admMember.admAgent.agentNum}<br>${admMember.admAgent.agentIp}</li>
					<li>****</li>
					<li>${admMember.xrayNum}</li>
					<li>${admMember.admAgent.bizDeptCd}</li> 
					<li onclick="popup_mod('${admMember.id}','${admMember.username}','${admMember.password}','${admMember.admAgent.id}','${admMember.admAgent.bizDeptCd}')"
        class=btn>수정</li>
				</ul>
			</c:forEach>
            </div>
        </div>
    </div>
</div>
<script>
function server_check() {
    $.ajax({
        url: "load.php",
        type: 'POST',
        //global: false,
        dataType: 'JSON',
        data: {
            //dir : dir
        },
        success: function(data) {
            $("#admin3_content").load("admin3_table.php");
        }
    }).fail(function() {});
}
$(document).ready(function() {
    server_check();
    //setInterval(server_check,1000);
});
$("#cbx_chkAll").click(function() {
    if ($("#cbx_chkAll").is(":checked"))
        $(".chk").prop("checked", true);
    else
        $(".chk").prop("checked", false);
});
$("#chk_del").click(function() {
    let chk_Val = [];
    $('.chk[type="checkbox"]:checked').each(function(index) {
        this_val = $(this).val();
        //alert(this_val)
        chk_Val.push(this_val);
    });
    alert(chk_Val);
    $.ajax({
        url: "/api/deleteAdmMember/"+chk_Val,
        type: 'DELETE',
        //global: false,
        dataType: 'JSON',
        success: function(data) { 
            location.reload();
        }
    }).fail(function() {});
});

$(".select_btn").click(function() {
    $(this).toggleClass("back_red");
});
$("#agent").change(function() {
    agent_chg();
})
$("body").on("change", "#a_xray1", function(event) {
    var a_xray1 = $("#a_xray1").val();
    var a_xray2 = $("#a_xray2").val();
    if (a_xray1 == a_xray2)
        alert("xray 값이 중복됩니다");
})
$("body").on("change", "#a_xray2", function(event) {
    var a_xray1 = $("#a_xray1").val();
    var a_xray2 = $("#a_xray2").val();
    if (a_xray1 == a_xray2)
        alert("xray 값이 중복됩니다");
})

function agent_chg(agent) {
    if (agent == null)
        var agent = $("#agent").val();
    $.ajax({
        url: "admin3_xray_db2.php",
        type: 'POST',
        //global: false,
        dataType: 'JSON',
        data: {
            agent: agent
        },
        success: function(data) {
            if (data == null)
                $("#xray_refresh").html("");
            var html = "<select id=a_xray1>";
            html += "<option value=''>선택</option>";
            if (data.length == 1) {
                html += "<option value=" + data[0].xray + ">Xray " + data[0].xray + "</option>";
            } else if (data.length > 1) {
                data.reverse();
                for (var i = 0; i < data.length; i++) {
                    html += "<option value=" + data[i].xray + ">Xray " + data[i].xray + "</option>";
                }
            }
            html += "</select>";
            $("#xray_refresh").html(html);

            html += "<select id=a_xray2>";
            html += "<option value=''>선택</option>";
            if (data.length == 1) {
                html += "<option value=" + data[0].xray + ">Xray " + data[0].xray + "</option>";
            } else if (data.length > 1) {
                for (var i = 0; i < data.length; i++) {
                    html += "<option value=" + data[i].xray + ">Xray " + data[i].xray + "</option>";
                }
            }
            html += "</select>";
            $("#xray_refresh").html(html);
            //$("#xray_refresh").load("admin3_xray_db2.php", {agent:agent});
        }
    }).fail(function() {});
}
</script>

<%@ include file="../layout/footer.jsp"%>