<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div id=right_box>
    <div id=title_bg>
        <div id=title><img src=/image/circle.jpg
                style=width:30px;vertical-align:top;margin-top:1px;margin-right:4px;>장비 로그</div>
    </div>
    <div id=table3>
        <div id=table_contents3>
            <ul class="log_table table_title">
                <li>ID</li>
                <li>Agent ID</li>
                <li>Agent IP</li>
                <li>Agent Port</li>
                <li>Etc.</li>
                <li>DATETIME</li>
            </ul>
			<c:forEach var="logCon" items="${logConList}">
				<ul class=log_table>
					<li>${logCon.id}</li>
					<li>${logCon.agent}</li>
					<li>${logCon.agentIp}</li>
					<li>${logCon.agentPort}</li>
					<li>${logCon.etc}</li>
					<li>${logCon.datetime}</li>
				</ul>
			</c:forEach>

		</div>
    </div>
</div>

<%@ include file="../layout/footer.jsp"%>