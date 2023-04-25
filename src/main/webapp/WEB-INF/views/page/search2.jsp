<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<style>
a {
	text-decoration-line: none;
	color: black;
}

a:visited {
	color: black;
}
</style>
<div id=popup_mod>
	<div id=popup_reg_box1>
		<div id=popup_reg_top>신규</div>
		<div id=rfid_close_box>
			<div id=rfid_close onclick="reg_close()">X</div>
		</div>
		<ul id=popup_reg_box>
			<input id=mod_laptop_id type="hidden">
			<input id=mod_user_id type="hidden">
			<li><span>사번</span><input id=mod_u_num placeholder="사번"></li>
			<li><span>이름</span><input id=mod_u_name placeholder="이름"></li>
			<li><span>부서</span><input id=mod_u_part placeholder="부서"></li>
			<li><span style="font-size: 15px">자산번호</span><input
				id=mod_u_asset placeholder="PC자산번호"></li>
			<li><span>S/N</span><input id=mod_u_serial placeholder="S/N"></li>
			<li><span>바코드</span><input id=mod_u_code placeholder="바코드"></li>
			<li><span></span>
				<div id=b_btn class="btn btn_recog">바코드 인식</div></li>
			<li><span>RFID</span><input id=mod_u_rfid placeholder="RFID"></li>
			<li><span></span>
				<div id=r_btn class="btn btn_recog">RFID 인식</div></li>
		</ul>
		<div id=popup_reg_box3>
			<span id=btn_save class="btn btn_new">등록</span> <span id=btn_del
				class="btn" onclick=reg_close()>취소</span>
		</div>
	</div>
</div>
<div id=right_box>
	<div id=title_bg>
		<div id=title>
			<img src=/image/circle.jpg
				style="width: 30px; vertical-align: top; margin-top: 1px; margin-right: 4px;">등록
			노트북 조회
		</div>
	</div>
	<div id=table4>
		<div id=top_box_out>
			<div id=top_box>
				<div id=search_left>
					<input type="hidden" id="search_excel" value="${search}" />
					<span class="btn btn_w" id=chk_del>삭제</span> <span id=btn_new_reg
						class="btn btn_w">신규</span> <span class="btn btn_w" id=btn_upload >업로드</span>
					<span class="btn btn_w"
						onclick="excelDownload()"
						style="width: 100px;">엑셀 다운로드</span>
				</div>
				<div id=search_right>
					<span><input name=keyword style="width: 250px" id="keyword"
						placeholder="바코드,사번,사원명,RFID" value="${search}"></span> <span class=btn_search><img
						class=btn_search_img src=/image/icon_search.png width=15px><input
						class="btn submit" type=button value=검색 onclick="laptopSearch();"></span>
				</div>
			</div>
		</div>
		<div id=table_contents3 style="justify-content: center;">
		<input type="hidden" id="count" value="${count}" />
			<ul class="search2_table table_title">
				<li><input id=cbx_chkAll type=checkbox></li>
				<li>사번</li>
				<li>부서명</li>
				<li>이름</li>
				<li>자산번호</li>
				<li>S/N</li>
				<li>바코드</li>
				<li>RFID</li>
				<li>수정</li>
			</ul>
			<c:choose>
				<c:when test="${empty search}">
					<c:forEach var="laptop" items="${laptopList}">
						<ul class=search2_table>
							<li><input value="${laptop.id}" class=chk type=checkbox></li>
							<li>${laptop.user.userNo}</li>
							<li>${laptop.user.userPart}</li>
							<li>${laptop.user.username}</li>
							<li>${laptop.asset}</li>
							<li>${laptop.serial}</li>
							<li>${laptop.barcode}</li>
							<li title="${laptop.rfid}">${laptop.rfid}</li>
							<li class="btn_mod btn" laptop_id="${laptop.id}"
								u_num="${laptop.user.userNo}" user_id="${laptop.user.id}"
								u_asset="${laptop.asset}" u_serial="${laptop.serial}"
								u_name="${laptop.user.username}" u_code="${laptop.barcode}"
								u_rfid="${laptop.rfid}" u_part="${laptop.user.userPart}">수정</li>
						</ul>
					</c:forEach>
					<div class="pageWrap">
						<div class="numList">
							<c:if test="${pageMaker.prev}">
								<div class='leftBtn'>
									<a href="/laptopInfo/search2?page=${pageMaker.startPage-1}">
										&laquo;</a>
								</div>
							</c:if>
							<c:forEach var="pageNum" begin="${pageMaker.startPage}"
								end="${pageMaker.endPage}">
								<c:choose>
									<c:when test="${pageNum eq curPageNum}">
										<div class="num black">
											<a style="color: white;"
												href="/laptopInfo/search2?page=${pageNum}">${pageNum}</a>
										</div>
									</c:when>
									<c:otherwise>
										<div class="num">
											<a href="/laptopInfo/search2?page=${pageNum}">${pageNum}</a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
								<div class='rightBtn'>
									<a href="/laptopInfo/search2?page=${pageMaker.endPage + 1}">&raquo;</a>
								</div>
							</c:if>
						</div>
					</div>
				</c:when>

				<c:otherwise>
					<c:forEach var="laptop" items="${laptopList}">
						<ul class=search2_table>
							<li><input value="${laptop.id}" class=chk type=checkbox></li>
							<li>${laptop.userNo}</li>
							<li>${laptop.userPart}</li>
							<li>${laptop.username}</li>
							<li>${laptop.asset}</li>
							<li>${laptop.serial}</li>
							<li>${laptop.barcode}</li>
							<li title="${laptop.rfid}">${laptop.rfid}</li>
							<li class="btn_mod btn" laptop_id="${laptop.id}"
								u_num="${laptop.userNo}" user_id="${laptop.userId}"
								u_asset="${laptop.asset}" u_serial="${laptop.serial}"
								u_name="${laptop.username}" u_code="${laptop.barcode}"
								u_rfid="${laptop.rfid}" u_part="${laptop.userPart}">수정</li>
						</ul>
					</c:forEach>
					<div class="pageWrap">
						<div class="numList">
							<c:if test="${pageMaker.prev}">
								<div class='leftBtn'>
									<a href="/laptopInfo/search2?page=${pageMaker.startPage-1}&keyword=${search}">
										&laquo;</a>
								</div>
							</c:if>
							<c:forEach var="pageNum" begin="${pageMaker.startPage}"
								end="${pageMaker.endPage}">
								<c:choose>
									<c:when test="${pageNum eq curPageNum}">
										<div class="num black">
											<a style="color: white;"
												href="/laptopInfo/search2?page=${pageNum}&keyword=${search}">${pageNum}</a>
										</div>
									</c:when>
									<c:otherwise>
										<div class="num">
											<a href="/laptopInfo/search2?page=${pageNum}&keyword=${search}">${pageNum}</a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
								<div class='rightBtn'>
									<a href="/laptopInfo/search2?page=${pageMaker.endPage + 1}&keyword=${search}">&raquo;</a>
								</div>
							</c:if>
						</div>
					</div> 
				</c:otherwise>
			</c:choose>

		</div>
	</div>
	</form>
</div>


<script>

function excelDownload(){
	let count = $("#count").val(); 
	let search = $("#search_excel").val();
	console.log(search);
	if(search === undefined) search = "";
	else search = "&keyword="+search;
	if(confirm(count + "건의 데이터를 다운받으시겠습니까?")){
		location.href="/api/util/excelDownload?sort=laptop"+search;
	}
	
}

function laptopSearch(){
	let keyword = $('#keyword').val(); 
	location.href="/laptopInfo/search2?page=1&keyword="+keyword; 
}

$("#cbx_chkAll").click(function() {
    if ($("#cbx_chkAll").is(":checked"))
        $(".chk").prop("checked", true);
    else
        $(".chk").prop("checked", false);
});

$("#b_btn").click(function() {
    $('#mod_u_code').val("");
    $('#mod_u_code').focus();
    $("#mod_u_code").addClass("ready_input");
});
$("#r_btn").click(function() {
    $('#mod_u_rfid').val("");
    $('#mod_u_rfid').focus();
    $("#mod_u_rfid").addClass("ready_input");
});
$("#mod_u_code").keyup(function() {
    $("#mod_u_code").removeClass("ready_input");
});
$("#mod_u_rfid").keyup(function() {
    $("#mod_u_rfid").removeClass("ready_input");
});
$("#mod_u_num").keyup(function() {
    var mod_u_num = $("#mod_u_num").val();
    console.log(mod_u_num);
    $.ajax({
        url: "/api/loadUserName",
        type: 'POST',
        //global: false,
        dataType: 'JSON',
        data: {
            u_num: mod_u_num
        },
        success: function(data) { 
            $("#mod_u_name").val(data.data);
        }
    }).fail(function() {});
});

// $('#mod_u_num').keyup(function() { 

//     var antena_ip = $('#antena_ip').val();
//     $.ajax({
//         url: "verification.php",
//         type: 'POST',
//         //global: false,
//         dataType: 'JSON',
//         data: {
//             antena_ip: antena_ip
//         },
//         success: function(data) {
//             console.log(data);
//             if (data == "y")
//                 $("#alert_message").html("안테나 ip 중복");
//             else
//                 $("#alert_message").html("");
//         }
//     }).fail(function() {});
// });

$("#btn_new_reg").click(function() {
    $("#popup_mod").css("display", "block");
    $(".shadow").css("display", "block");
    $("#mod_u_num").css("background-color", "white");
    $("#mod_laptop_id").val("");
    $("#mod_user_id").val("");
    $("#mod_u_num").removeAttr("readonly");
    $("#mod_u_name").removeAttr("readonly");
    $("#mod_u_num").val("");
    $("#mod_u_name").val("");
    $("#mod_u_part").val("");
    $("#mod_u_asset").val("");
    $("#mod_u_serial").val("");
    $("#mod_u_code").val("");
    $("#mod_u_rfid").val("");
});
$(".btn_mod").click(function() {
    $("#popup_mod").css("display", "block");
    $(".shadow").css("display", "block");
    $("#mod_u_num").attr("readonly", true);
    $("#mod_u_num").css("background-color", "#BDBDBD");
    var mod_u_num = $(this).attr("u_num");
    var mod_laptop_id = $(this).attr("laptop_id");
    var mod_user_id = $(this).attr("l_id");
    var mod_u_asset = $(this).attr("u_asset");
    var mod_u_serial = $(this).attr("u_serial");
    var mod_u_name = $(this).attr("u_name");
    var mod_u_part = $(this).attr("u_part");
    var mod_u_code = $(this).attr("u_code");
    var mod_u_rfid = $(this).attr("u_rfid");
    $("#mod_laptop_id").val(mod_laptop_id);
    $("#mod_user_id").val(mod_user_id);
    $("#mod_u_num").val(mod_u_num);
    $("#mod_u_asset").val(mod_u_asset);
    $("#mod_u_serial").val(mod_u_serial);
    $("#mod_u_name").val(mod_u_name);
    $("#mod_u_part").val(mod_u_part);
    $("#mod_u_code").val(mod_u_code);
    $("#mod_u_rfid").val(mod_u_rfid);
});
$("#chk_del").click(function() {
    let chk_Val = [];
    $('.chk[type="checkbox"]:checked').each(function(index) {
        this_val = $(this).val();
        //alert(this_val)
        chk_Val.push(this_val);
    });
    $.ajax({
        url: "/api/deleteLaptop/"+chk_Val,
        type: 'DELETE',
        //global: false,
        dataType: 'JSON', 
        success: function(data) { 
            location.href="/laptopInfo/search2";
        }
    }).fail(function() {});
});
$("#btn_save").click(function() {
    var mod_laptop_id = $("#mod_laptop_id").val();
    var mod_user_id = $("#mod_user_id").val();
    var mod_u_num = $("#mod_u_num").val();
    var mod_u_name = $("#mod_u_name").val();
    var mod_u_part = $("#mod_u_part").val();
    var mod_u_asset = $("#mod_u_asset").val();
    var mod_u_serial = $("#mod_u_serial").val();
    var mod_u_code = $("#mod_u_code").val();
    var mod_u_rfid = $("#mod_u_rfid").val();
    console.log(mod_laptop_id);
    console.log(mod_user_id);
    if (mod_u_code == '' || mod_u_rfid == '') {
        alert("code 또는 rfid를 입력 해 주세요.");
    } else {
    	let data = {
    			laptopId: $("#mod_laptop_id").val(),
    			userId: $("#mod_user_id").val(),
    			userNo: $("#mod_u_num").val(),
    			username: $("#mod_u_name").val(),
    			userPart: $("#mod_u_part").val(),
    			asset: $("#mod_u_asset").val(),
    			serial: $("#mod_u_serial").val(),
    			barcode: $("#mod_u_code").val(),
    			rfid: $("#mod_u_rfid").val()
    	};
    	
    	if(data.laptopId == ''){
    		// 신규
    		$.ajax({
                url: "/api/insertLaptop",
                type: 'POST',
                //global: false,
                dataType: 'JSON',
                contentType: "application/json; charset=utf-8",
        		data: JSON.stringify(data),
                success: function(data) { 
                	if(data.data == 0){
                		alert("중복되는 사번입니다.");
                		return;
                	} else if(data.data == 1) {
                		alert("추가완료");
                		location.href="/laptopInfo/search2";
                	} else {
                		alert("신규 추가 실패. 관리자에게 문의해주세요.");
                	}
                }
            }).fail(function() {});
    	} else {
    		// 수정
    		$.ajax({
                url: "/api/updateLaptop/"+data.laptopId,
                type: 'PUT',
                //global: false,
                dataType: 'JSON',
                contentType: "application/json; charset=utf-8",
        		data: JSON.stringify(data),
                success: function(data) {  
                	if(data.data == 1){
                		alert("수정완료");
                		location.href="/laptopInfo/search2"; 
                	} else {
                		alert("수정 실패. 관리자에게 문의해주세요.");
                	}
                	
                }
            }).fail(function() {});
    	}
        
        $("#popup_reg").css("display", "none");
        $(".shadow").css("display", "none");
        xray_num = [];
    }
});

$.fn.numCheck = function(u_num) {
    alert(u_num);
    var checkYn = "";
    $.ajax({
        url: "/pages/numCheck.php",
        type: 'POST',
        //global: false,
        dataType: 'JSON',
        data: {
            u_num: u_num,
        },
        success: function(data) {
            if (data == "y") checkYn = data;
            else checkYn = "n";
        }
    }).fail(function() {});

    return checkYn;
}

$("#btn_upload").click(function() {
    link("/util/excel");
});

function btn_download(keyword, count) {
    // console.log(keyword);
    let id = "search2";
    if (keyword.length > 0) {
        if (confirm("검색결과 " + count + "건을 다운로드 하시겠습니까?")) {
            location.href = "/assets/db/process_excel.php?id=" + id + "&keyword=" + keyword;
        }
    } else {
        if (confirm("모든 데이터 " + count + "건을 다운로드 하시겠습니까?")) {
            location.href = "/assets/db/process_excel.php?id=" + id;
        }
    }
    // location.href = "/assets/db/process_excel.php?id=" + id + "&keyword=" + keyword;

}
</script>
<%@ include file="../layout/footer.jsp"%>