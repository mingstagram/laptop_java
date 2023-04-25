<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
 
<div id="right_box">
	<div id=title_bg>
		<div id=title><img src=/image/circle.jpg
                style=width:30px;vertical-align:top;margin-top:1px;margin-right:4px;>시스템 설정</div>
	</div>
	<div id=admin_input>
	<input type="hidden" id=system_id value="${system.id}">
	<ul>
		<li><span>감지횟수</span><input id=set1 value="${system.set1}"></li>
		<li><span>RFID 초기화 시간</span><input id=set2 value="${system.set2}"></li>
		<li><span>경광등 알림시간</span><input id=set3 value="${system.set3}"></li>
		<li><span>재조회 가능 시간</span><input id=set4 value="${system.set4}"></li>
		</ul>
	</div>
	<div id=reg class=btn>등록</div>
</div> 
<script>
$("#reg").click(function() {
	let id = $("#system_id").val();
	let data = {
		set1: $("#set1").val(),
		set2: $("#set2").val(),
		set3: $("#set3").val(),
		set4: $("#set4").val()
	}; 
    // var xray_id = $("#xray_id").val();
    $.ajax({
        url: "/api/updateSystem/"+id,
        type: 'PUT',
        //global: false,
        dataType: 'JSON',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(data),
        success: function(data) { 
            reset_devide(data.data);
            location.href="/systemInfo/admin2";
        }
    }).fail(function() {});
});
</script>

<%@ include file="../layout/footer.jsp"%>