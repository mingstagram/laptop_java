<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/css/main.css" />
<script src="/js/jquery-3.6.0.js"></script>
<script src="/js/all.js"></script>
<script src="/js/jquery.min.js"></script>
</head>
<body>
	<form method=get name=userinput id=userinput>
		<div class=shadow></div>
		<div id=main_box>
			<div id=sub_box>
				<div id="left_box">
					<div>
						<div class=align_center id=menu_top>
							<img src=/image/menu_top2.jpg width=100px>
						</div>
						<div class="align_center" id=title2>
							<b>노트북 반출</b> 시스템
						</div>
						<div class="align_center" style="font-size: 15px;">
							( 로그인 아이디 : <b><c:out value="${cookie.username.value}" /></b> )
						</div>
						<ul id=menu>
							<c:choose>
								<c:when test="${cookie.username.value eq 'admin'}">
									<li onclick="location.href='/eventHistory/main'"><span><img
											src=/image/menu1.png width=24px></span><span class=menu_title>실시간
											모니터링</span></li>
									<li onclick="location.href='/eventHistory/search1'"><span><img
											src=/image/menu2.png width=24px></span><span class=menu_title>반출
											내역 조회</span></li>
									<li onclick="location.href='/laptopInfo/search2'"><span><img
											src=/image/menu3.png width=24px></span><span class=menu_title>등록
											노트북 조회</span></li>
									<li><span><img src=/image/menu4.png width=24px></span><span
										class=menu_title>설정</span>
										<ul>
											<li onclick="location.href='/admAgent/admin4'">Agent PC
												관리</li>
											<li onclick="location.href='/admSet/admin1'">장비 설정</li>
											<li onclick="location.href='/admMember/admin3'">관리자 관리</li>
											<li onclick="location.href='/logCon/equip_test'">장비 로그</li>
											<!-- <li onclick=link("error_view.php")>실시간 로그 확인</li> -->
										</ul></li>
									<li onclick="location.href='/admMember/logout'"><span><img
											src=/image/logout.png></span><span class=menu_title>로그아웃</span>
									</li>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${cookie.agent_id.value eq '0'}">
											<li onclick="location.href='/laptopInfo/search2'"><span><img
													src=/image/menu3.png width=24px></span><span
												class=menu_title>등록 노트북 조회</span></li>
											<li onclick="location.href='/admMember/logout'"><span><img
													src=/image/logout.png></span><span class=menu_title>로그아웃</span>
										</c:when>
										<c:otherwise>
											<li onclick="location.href='/eventHistory/main'"><span><img
													src=/image/menu1.png width=24px></span><span
												class=menu_title>실시간 모니터링</span></li>
											<li onclick="location.href='/eventHistory/search1'"><span><img
													src=/image/menu2.png width=24px></span><span
												class=menu_title>반출 내역 조회</span></li>
											<li onclick="location.href='/laptopInfo/search2'"><span><img
													src=/image/menu3.png width=24px></span><span
												class=menu_title>등록 노트북 조회</span></li>
											<li><span><img src=/image/menu4.png width=24px></span><span
												class=menu_title>설정</span>
												<ul>
													<li onclick="location.href='/systemInfo/admin2'">시스템
														설정</li>
													<li onclick="location.href='/admSet/admin6'">장비 확인</li>
													<li onclick="location.href='/logCon/equip_test'">장비 로그</li>
													<!-- <li onclick=link("error_view.php")>실시간 로그 확인</li> -->
												</ul></li>
											<li onclick="location.href='/admMember/logout'"><span><img
													src=/image/logout.png></span><span class=menu_title>로그아웃</span>
											</li>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>

						</ul>
						<!--<div id=message></div>-->
						<div id=bottom_logo class=align_center>
							<img src=/image/logo_bottom.jpg width=150px>
						</div>
					</div>
				</div>