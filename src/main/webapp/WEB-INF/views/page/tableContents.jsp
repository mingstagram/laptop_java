<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:forEach var="mainHistory" items="${mainHistoryList}">

	<ul class="main_table">
		<li>${mainHistory.userNo}</li>
		<li>${mainHistory.username}</li>
		<li>${mainHistory.barcode}</li>
		<li>Xray ${mainHistory.xray}</li>
		<li><fmt:formatDate value="${mainHistory.datetime }"
				pattern="yyyy-MM-dd HH:mm:ss" /></li>
		<c:choose>
			<c:when
				test="${mainHistory.result eq 'Y' || mainHistory.result eq 'S'}">
				<li><b><span class="">승인</span></b></li>
			</c:when>
			<c:otherwise>
				<li><b><span class="" style="color: red;">미승인</span></b></li>
			</c:otherwise>
		</c:choose>
	</ul>

</c:forEach>