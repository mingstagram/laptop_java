<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<style>
a{
	text-decoration-line: none;
	color:black;
}

a:visited {
	color:black;
}
</style>

<div id=right_box>
	<div id=title_bg>
		<div id=title>
			<img src=/image/circle.jpg
				style="width: 30px; vertical-align: top; margin-top: 1px; margin-right: 4px;">반출
			내역 조회
		</div>
	</div>
	<div id=table3>
		<ul id=top_search>
			<c:if test="${cookie.username.value eq 'admin'}">
				<li><select id=bizDeptCd name=bizDeptCd>
					<option value=''>사업장코드</option>
					<option value='07' <c:if test="${bizDeptCd eq '07' }">selected</c:if>>파주</option>
					<option value='03' <c:if test="${bizDeptCd eq '03' }">selected</c:if>>안산</option>
					<option value='01' <c:if test="${bizDeptCd eq '01' }">selected</c:if>>광주</option>
					<option value='02' <c:if test="${bizDeptCd eq '02' }">selected</c:if>>구미</option>
					<option value='04' <c:if test="${bizDeptCd eq '04' }">selected</c:if>>평택</option>
					<option value='18' <c:if test="${bizDeptCd eq '18' }">selected</c:if>>마곡</option>
			</select></li>
			</c:if>
			<li><input type="date" name=s_datetime id=s_datetime></li>
			<li><input type="date" name=e_datetime id=e_datetime></li>
			<li><select id=result name=result>
					<option value=''>승인/미승인</option>
					<option value='Y' <c:if test="${result eq 'Y' or 'S'}">selected</c:if>>승인</option>
					<option value='N' <c:if test="${result eq 'N' }">selected</c:if>>미승인</option>
			</select></li>
			<li><input id=keyword name=keyword value=""
				placeholder="바코드,사번,사원명"></li>
			<li><span class=btn_search><input onclick="historySearch();"
					class="btn submit" type=button value=검색><img
					class=btn_search_img src=/image/icon_search.png width=15px></span></li> 
			<li><span class="btn_excel btn" onclick="excelDownload('${queryString}');" style="width: 100px;">엑셀
					다운로드</span></li>
		</ul>
	</div>
	<div id=table_contents2>
	<input type="hidden" id="count" value="${count}" />
		<ul class="search1_table table_title">
			<li>순번</li>
			<li>Agent</li>
			<li>사번</li>
			<li>이름</li>
			<li>바코드</li>
			<li>RFID</li>
			<li>사업장</li>
			<li>반출시간</li>
			<li>상태</li>
		</ul> 
		<c:forEach var="history" items="${eventList}">
			<ul class=search1_table>
				<li>${history.id}</li>
				<li>${history.agent}</li>
				<li>${history.userNo}</li>
				<li>${history.username}</li>
				<li>${history.barcode}</li>
				<li title="${history.rfid}">${history.rfid}</li>
				<li>${history.bizDeptText}</li>
				<li>${history.datetime}</li>
				<c:choose>
					<c:when test="${history.result eq 'Y' || history.result eq 'S' }">
						<li>승인</li>
					</c:when>
					<c:otherwise><li>미승인</li></c:otherwise>
				</c:choose> 
			</ul>
		</c:forEach> 
		<c:choose>
			<c:when test="${searchYn}">
			<div class="pageWrap">
			<div class="numList">
				<c:if test="${pageMaker.prev}"><div class='leftBtn'><a href="/eventHistory/search1?page=${pageMaker.startPage-1}${queryString}"> &laquo;</a></div></c:if>  
					<c:forEach var="pageNum" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
						<c:choose>
							<c:when test="${pageNum eq curPageNum}"> 
								<div class="num black"><a style="color:white;" href="/eventHistory/search1?page=${pageNum}${queryString}">${pageNum}</a></div>
							</c:when>
							<c:otherwise>
								<div class="num"><a href="/eventHistory/search1?page=${pageNum}${queryString}">${pageNum}</a></div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				<c:if test="${pageMaker.next && pageMaker.endPage > 0}"><div class='rightBtn'><a href="/eventHistory/search1?page=${pageMaker.endPage + 1}${queryString}">&raquo;</a></div></c:if>
			</div>
		</div>
			</c:when>
			<c:otherwise>
			<div class="pageWrap">
			<div class="numList">
				<c:if test="${pageMaker.prev}"><div class='leftBtn'><a href="/eventHistory/search1?page=${pageMaker.startPage-1}"> &laquo;</a></div></c:if>  
					<c:forEach var="pageNum" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
						<c:choose>
							<c:when test="${pageNum eq curPageNum}"> 
								<div class="num black"><a style="color:white;" href="/eventHistory/search1?page=${pageNum}">${pageNum}</a></div>
							</c:when>
							<c:otherwise>
								<div class="num"><a href="/eventHistory/search1?page=${pageNum}">${pageNum}</a></div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				<c:if test="${pageMaker.next && pageMaker.endPage > 0}"><div class='rightBtn'><a href="/eventHistory/search1?page=${pageMaker.endPage + 1}">&raquo;</a></div></c:if>
			</div>
		</div>
			</c:otherwise>
		</c:choose>
		
		
	</div>
</div>
</div>


<script>   
	var totalCount = '';
	
	function excelDownload(query){
		let count = $("#count").val();
		if(confirm(count + "건의 데이터를 다운받으시겠습니까?")){
			location.href="/api/util/excelDownload?sort=history&"+query;
		}
		
	}
	

	$("#excels")
			.click(
					function() {
						if (totalCount > 0) {
							let id = "search";
							let xray_id = '<?php echo $_POST["xray_id"]?>';
							let bizDeptCd = '<?php echo $_POST["bizDeptCd"]?>';
							let keyword = '<?php echo $_POST["keyword"]?>';
							let result = '<?php echo $_POST["result"]?>';
							let s_datetime = '<?php echo $_POST["s_datetime"]?>';
							let e_datetime = '<?php echo $_POST["e_datetime"]?>';

							let qs = "?id=" + id;

							if (xray_id.length > 0)
								qs += "&xray_id=" + xray_id;
							if (bizDeptCd.length > 0)
								qs += "&bizDeptCd=" + bizDeptCd;
							if (keyword.length > 0)
								qs += "&keyword=" + keyword;
							if (result.length > 0)
								qs += "&result=" + result;
							if (s_datetime.length > 0)
								qs += "&s_datetime=" + s_datetime;
							if (e_datetime.length > 0)
								qs += "&e_datetime=" + e_datetime;
							if (bizDeptCd.length > 0 || xray_id.length > 0
									|| keyword.length > 0
									|| result.length > 0
									|| s_datetime.length > 0
									|| e_datetime.length > 0) {
								if (confirm("검색결과 " + totalCount
										+ "건을 다운로드 하시겠습니까?")) {
									location.href = '/assets/db/process_excel.php'
											+ qs;
								}
							} else {
								if (confirm("모든 데이터 " + totalCount
										+ "건을 다운로드 하시겠습니까?")) {
									location.href = '/assets/db/process_excel.php'
											+ qs;
								}
							}
						} else {
							alert("다운로드 받을 데이터가 없습니다.");
						}
					});
	
	function historySearch(){ 
		let data = {
				bizDeptCd: $('#bizDeptCd').val(),
				result: $('#result').val(),
				keyword: $('#keyword').val(),
				sdate: $('#s_datetime').val(),
				edate: $('#e_datetime').val()
		}   
		let qs = "";
		console.log(data);
		if(!isEmpty(data.bizDeptCd)){
			if(data.bizDeptCd.length > 0) qs += "&bizDeptCd="+data.bizDeptCd;
		}
		
		if(data.result.length > 0) qs += "&result="+data.result;
		if(data.keyword.length > 0) qs += "&keyword="+data.keyword;
		if(data.sdate.length > 0) qs += "&sdate="+data.sdate;
		if(data.edate.length > 0) qs += "&edate="+data.edate;
		
		location.href="/eventHistory/search1?page=1"+qs;
		
	}
	
	
	
	$("#search").click(function() {
		
		
	}); 
	
	$('#bizDeptCd').on('change', function() {
		let bizDeptCd = $("#bizDeptCd").val();
		console.log(bizDeptCd);
		$(this).val(bizDeptCd).prop("selected", true);
		var form = document.userinput;
		form.submit();
	});
	$('#result').on('change', function() {
		var form = document.userinput;
		form.submit();
	});
</script>

<%@ include file="../layout/footer.jsp"%>