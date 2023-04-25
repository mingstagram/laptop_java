<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div id=popup_reg>
	<div id=popup_reg_box1>
		<div id=popup_reg_top>Agent PC 등록</div>
		<div id=rfid_close_box>
			<div id=rfid_close onclick="reg_close()">X</div>
		</div>
		<input type="hidden" id=agent_id>
		<ul id=popup_reg_box>
			<li>Agent IP<br> <br> <input id=agent_ip
				placeholder="Agent IP"><br> <br></li>
			<li>Agent Port<br> <br> <input id=agent_port
				placeholder="Port"><br> <br></li>
			<li>회사 코드<br> <br> <input id=bizDeptCd
				placeholder="회사 코드"><br> <br></li>
			<li>비고<br> <br> <input id=agent_num placeholder="비고"><br>
				<br></li>
		</ul>
		<div id=popup_reg_box3>
			<span id=adm_agent_save class="btn btn_new">등록</span> <span
				id=btn_del class="btn" onclick=reg_close()>취소</span>
		</div>
	</div>
</div>
<div id=right_box>
	<div id=title_bg>
		<div id=title>
			<img src=/image/circle.jpg
				style="width: 30px; vertical-align: top; margin-top: 1px; margin-right: 4px;">Agent
			PC 관리
		</div>
	</div>
	<div id=table3>
		<div id=top_box_out>
			<div id=top_box>
				<div id=search_left></div>
				<div id=search_right>
					<span onclick=agent_reg() class="btn btn_w">추가</span> <span
						id=chk_del class="btn btn_w">삭제</span>
				</div>
			</div>
		</div>
		<div id=table_contents3>
			<ul class="admin4_table table_title tables">
				<li><input id=cbx_chkAll type=checkbox></li>
				<li>No</li>
				<li>Agent IP</li>
				<li>Port</li>
				<li>회사코드</li>
				<li>비고</li>
				<li>수정</li>
			</ul>

			<c:forEach var="agent" items="${admAgentList}">
				<ul class="admin4_table tables">
					<li><input value="${agent.id}" class=chk type=checkbox></li>
					<li>${agent.id}</li>
					<li>${agent.agentIp}</li>
					<li>${agent.agentPort}</li>
					<li>${agent.bizDeptCd}</li>
					<li>${agent.agentNum}</li>
					<li
						onclick="agent_mod('${agent.id}', '${agent.agentIp}', '${agent.agentPort }', '${agent.agentNum}', '${agent.bizDeptCd }')"
						class=btn>수정</li>
				</ul>
			</c:forEach>

		</div>
	</div>
</div>
<script>  
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
    $.ajax({
        url: "/api/deleteAdmAgent/"+chk_Val,
        type: 'DELETE',
        //global: false,
        dataType: 'JSON',
        data: {
            chk_Val: chk_Val,
        },
        success: function(data) {
            location.reload();
            server_check();
        }
    }).fail(function() {});
});
$(".select_btn").click(function() {
    $(this).toggleClass("back_red");
});
</script>
<%@ include file="../layout/footer.jsp"%>