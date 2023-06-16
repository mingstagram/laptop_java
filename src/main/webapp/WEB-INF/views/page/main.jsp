<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%> 
<style>
.select {
	width: 150px;
	height: 35px;
	background:
		url("https://freepikpsd.com/media/2019/10/down-arrow-icon-png-7-Transparent-Images.png")
		calc(100% - 5px) center no-repeat;
	background-size: 20px;
	border-radius: 4px;
	margin-left: 0px;
	outline: 0 none;
	border: 1px solid gray;
}

.btn-select {
	height: 35px;
	background-color: gray;
	color: white;
	background-size: 20px;
	border-radius: 4px;
	margin-left: 0px;
	outline: 0 none;
	border: 1px solid gray;
}

.select option {
	padding: 3px 0;
}

.current {
	background: #e0e0e0;
	color: #222;
}

.select {
	width: 150px;
	height: 35px;
	background-size: 20px;
	border-radius: 4px;
	margin-left: 0px;
	outline: 0 none;
	border: 1px solid gray;
	text-align: center;
}

.select option {
	padding: 3px 0;
}
</style>
<div id=right_box>
	<div id=title_bg>
		<div id=title style="padding-bottom: 0px;">
			<img src=/image/circle.jpg
				style="width: 30px; vertical-align: top; margin-top: 1px; margin-right: 4px;">실시간
			모니터링
		</div>
	</div>
	<input type="hidden" id="username" value="${cookie.username.value}"/>
	<div id=search_right style="float: right; margin-right: 5%;">
		<select class="select">
			<c:choose>
				<c:when test="${cookie.prop.value eq '1'}">
					<option class="zoom" id="zoom" value="1" selected>아주작게</option>
				</c:when>
				<c:otherwise>
					<option class="zoom" id="zoom" value="1">아주작게</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${cookie.prop.value eq '2'}">
					<option class="zoom" id="zoom" value="2" selected>작게</option>
				</c:when>
				<c:otherwise>
					<option class="zoom" id="zoom" value="2">작게</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${cookie.prop.value eq '3'}">
					<option class="zoom" id="zoom" value="3" selected>기본</option>
				</c:when>
				<c:otherwise>
					<option class="zoom" id="zoom" value="3">기본</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${cookie.prop.value eq '4'}">
					<option class="zoom" id="zoom" value="4" selected>크게</option>
				</c:when>
				<c:otherwise>
					<option class="zoom" id="zoom" value="4">크게</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${cookie.prop.value eq '5'}">
					<option class="zoom" id="zoom" value="5" selected>아주크게</option>
				</c:when>
				<c:otherwise>
					<option class="zoom" id="zoom" value="5">아주크게</option>
				</c:otherwise>
			</c:choose>
		</select>
		<button class="btn-select">설정</button>
	</div>


	<div id=top_table_box class="zoom_box">
		<%-- <ul id=top_table>
		
			<div class="xray_box" >
				<span class="xray_box_title" >Xray</span>
				<div id=xray_contents>
				<c:forEach var="topHistory" items="${topHistoryList}">
					<c:if test="${topHistory.result eq 'Y'}">
						<li class="border_green radius10">
							<div id=top_top_box>
								<div id=gate_name>gate1</div>
							</div>
							<div id=top_bottom_box>
								<span class=name_big>${topHistory.username}</span><br> <br> 
								<span>🟢 </span><span>승인</span>
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
		</ul> --%>
	</div>
	<div id=main_bottom>
		<div id=main_bottom_in class=zoom_box>
			<ul class="main_table table_title" style="background:#474747">
				<li>사번</li>
				<li>성명</li>
				<li>바코드</li>
				<!--<li>RFID</li>-->
				<li>구역</li>
				<li>반출시간</li>
				<li>상태</li>
			</ul>
			<div id=table_contents> 
				<%-- <c:forEach var="mainHistory" items="${mainHistoryList}"> 
					<ul class="main_table"> 
						<li>${mainHistory.userNo}</li>
						<li>${mainHistory.username}</li> 
						<li>${mainHistory.barcode}</li>
						<li>Xray ${mainHistory.xray}</li>
						<li><fmt:formatDate value="${mainHistory.datetime }" pattern="yyyy-MM-dd HH:mm:ss"/></li>
						<c:choose>
							<c:when
								test="${mainHistory.result eq 'Y' || mainHistory.result eq 'S'}">
								<li><span class="">승인</span></li>
							</c:when>
							<c:otherwise>
								<li><span class="" style="color:red;">미승인</span></li>
							</c:otherwise>
						</c:choose>
					</ul> 
				</c:forEach>
			</div> --%>
		</div>
	</div>
</div>

<script>
	var server_now;
	$(document).ready(function() { 
		let prop = $('.select').val();
		//server_check();
		console.log(prop);
		zoomLevel(prop);
		//server_now = setInterval(server_check, 1000);   
		setInterval(() => {server_check()}, 1000);
	});  

	function server_check() {
		let prop = $('.select').val();
		
		var topHtml = '';
		var bottomHtml = '';
		$.ajax({
			url : "/eventHistory/load",
			type : 'POST',
			//global: false,
			dataType : 'JSON',
			data : {
			//dir : dir
			},
			success : function(data) { 
				//$('#xray_contents').load(' #xray_contents').trigger("create");
				//$('#table_contents').load(' #table_contents').trigger("create"); 
				$('#top_table_box').load('/eventHistory/xrayContents');
				$('#table_contents').load('/eventHistory/tableContents'); 
			}
		}).fail(function() {
		});
	}

	function zoomLevel(level) {
		if (level === "1") {
			$(".zoom_box").css({
				"zoom" : 0.6
			}); 
			/* $(".xray_box_title").css({
				"zoom" : 1.4
			});  */
			$(".table_title").css({
				"zoom" : 1.4
			}); 
			$("#table_contents").css({
				"height" : "810px"
			}); 
		} else if (level === "2") {
			$(".zoom_box").css({
				"zoom" : 0.7
			}); 
			/* $(".xray_box_title").css({
				"zoom" : 1.2
			});  */
			$(".table_title").css({
				"zoom" : 1.2
			});
			$("#table_contents").css({
				"height" : "640px"
			}); 
		} else if (level === "3") {
			$(".zoom_box").css({
				"zoom" : 0.8
			});  
			/* $(".xray_box_title").css({
				"zoom" : 1
			});  */
			$(".table_title").css({
				"zoom" : 1
			});
			$("#table_contents").css({
				"height" : "510px"
			}); 
		} else if (level === "4") {
			$(".zoom_box").css({
				"zoom" : 0.9
			}); 
			/* $(".xray_box_title").css({
				"zoom" : 0.9
			});  */
			$(".table_title").css({
				"zoom" : 0.9
			});
			$("#table_contents").css({
				"height" : "410px"
			}); 
		} else if (level === "5") {
			$(".zoom_box").css({
				"zoom" : 1.0
			}); 
			/* $(".xray_box_title").css({
				"zoom" : 0.8
			});  */
			$(".table_title").css({
				"zoom" : 0.8
			});
			$("#table_contents").css({
				"height" : "330px"
			}); 
		}
	}

	$(".btn-select").click(function() {
		var username = $("#username").val();
		var prop = $('.select').val();

		if (confirm("선택하신 해상도로 적용하시겠습니까?")) {
			$.ajax({
				url : "/api/updateProp",
				type : 'POST',
				//global: false,
				dataType : 'JSON',
				data : {
					username : username,
					prop : prop
				},
				success : function(data) {
					alert("저장 완료");
					location.reload(true);
				}
			}).fail(function() {
				alert("텅신실패");
			});
		}
	});

	$(".select").change(function() { 
		var zoom = $(this).val();
		if (zoom === "1") {
			$(".zoom_box").css({
				"zoom" : 0.6
			}); 
			$(".xray_box_title").css({
				"zoom" : 1.4
			}); 
			$(".table_title").css({
				"zoom" : 1.4
			});
			$("#table_contents").css({
				"height" : "810px"
			}); 
		} else if (zoom === "2") {
			$(".zoom_box").css({
				"zoom" : 0.7
			}); 
			$(".xray_box_title").css({
				"zoom" : 1.2
			});
			$(".table_title").css({
				"zoom" : 1.2
			});
			$("#table_contents").css({
				"height" : "640px"
			}); 
		} else if (zoom === "3") {
			$(".zoom_box").css({
				"zoom" : 0.8
			}); 
			$(".xray_box_title").css({
				"zoom" : 1
			}); 
			$(".table_title").css({
				"zoom" : 1
			});
			$("#table_contents").css({
				"height" : "510px"
			}); 
		} else if (zoom === "4") {
			$(".zoom_box").css({
				"zoom" : 0.9
			}); 
			$(".xray_box_title").css({
				"zoom" : 0.9
			}); 
			$(".table_title").css({
				"zoom" : 0.9
			});
			$("#table_contents").css({
				"height" : "410px"
			}); 
		} else if (zoom === "5") {
			$(".zoom_box").css({
				"zoom" : 1.0
			}); 
			$(".xray_box_title").css({
				"zoom" : 0.8
			});
			$(".table_title").css({
				"zoom" : 0.8
			});
			$("#table_contents").css({
				"height" : "330px"
			}); 
		}
	});
</script>
<%@ include file="../layout/footer.jsp"%>