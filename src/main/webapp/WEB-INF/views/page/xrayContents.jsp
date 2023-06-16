<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<ul id=top_table> 
			<div class="xray_box" > 
				<c:if test = "${cookie.prop.value eq '1'}">
					<span class="xray_box_title" style="zoom:1.4;">Xray</span>
				</c:if>
				<c:if test = "${cookie.prop.value eq '2'}">
					<span class="xray_box_title" style="zoom:1.2;">Xray</span>
				</c:if>
				<c:if test = "${cookie.prop.value eq '3'}">
					<span class="xray_box_title" style="zoom:1;">Xray</span>
				</c:if>
				<c:if test = "${cookie.prop.value eq '4'}">
					<span class="xray_box_title" style="zoom:0.9;">Xray</span>
				</c:if>
				<c:if test = "${cookie.prop.value eq '5'}">
					<span class="xray_box_title" style="zoom:0.8;">Xray</span>
				</c:if>  
				<div id=xray_contents>
				<c:forEach var="topHistory" items="${topHistoryList}">
					<c:if test="${topHistory.result eq 'Y'}">
						<li class="border_green radius10">
							<div id=top_top_box>
								<div id=gate_name>gate1</div>
							</div>
							<div id=top_bottom_box>
								<span class=name_big>${topHistory.username}</span><br> <br> 
								<span>🟢 </span><span>승인</span> ${prop}
							</div>
						</li>
					</c:if>
					<c:if test="${topHistory.result eq 'S'}">
						<li class="border_yellow radius10">
							<div id=top_top_box>
								<div id=gate_name>gate1</div>
							</div>
							<div id=top_bottom_box>
								<span class=name_big>${topHistory.username}</span><br> <br> 
								<span>🟡 </span><span>승인</span>
							</div>
						</li>
					</c:if>
					<c:if test="${topHistory.result eq 'N'}">
						<li class="border_red radius10">
							<div id=top_top_box>
								<div id=gate_name>gate1</div>
							</div>
							<div id=top_bottom_box>
								<span class=name_big>${topHistory.username}</span><br> <br> 
								<span>❌ 미승인</span>
							</div>
						</li>
					</c:if>
					<c:if test="${topHistory.result eq ''}">
						<li class="border_red radius10">
							<div id=top_top_box>
								<div id=gate_name>gate1</div>
							</div>
							<div id=top_bottom_box>
								<span class=name_big>${topHistory.username}</span><br> <br>
								<img src=/image/icon_x.jpg class=icon_x><span>인식불가</span>
							</div>
						</li>
					</c:if>

				</c:forEach>
				</div>
			</div>
		</ul> 
 
