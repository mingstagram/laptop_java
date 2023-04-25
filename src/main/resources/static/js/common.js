$("#btn_login").on("click", function() {
	let data = {
		username: $("#u_id").val(),
		password: $("#u_pw").val()
	}
	// console.log(data);

	$.ajax({
		url: "/api/login_check.do",
		type: "POST",
		//global: false,
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(data),
		success: function(res) {
			if (res.data == 1) {
				location.href = "/eventHistory/main";
			} else if (res.data == 2) {
				location.href = "/laptopInfo/search2";
			} else {
				alert("아이디와 암호를 확인 해 주세요");
			}

			console.log(res.data);
		},
	}).fail(function(error) {
		alert("실패");
	});

});

function reload() {
	setTimeout(function() {
		location.reload();
	}, 10000);
}
/**
 * 문자열이 빈 문자열인지 체크하여 결과값을 리턴한다. 
 * @param str		: 체크할 문자열
 */
function isEmpty(str) {
	if (typeof str == "undefined" || str == null || str == "")
		return true;
	else
		return false;
}

/**
 * 문자열이 빈 문자열인지 체크하여 기본 문자열로 리턴한다. 
 * @param str			: 체크할 문자열
 * @param defaultStr	: 문자열이 비어있을경우 리턴할 기본 문자열
 */	
function nvl(str, defaultStr){
	
	if(typeof str == "undefined" || str == null || str == "")
		str = defaultStr ;
	
	return str ;
}

function link(link) {
	location.href = link;
}
function rfid_reg() {
	$("#popup_rfid").css("display", "block");
	$(".shadow").css("display", "block");
	$("#popup_rfid_top").html("장비추가");
	$("#btn_new").html("등록");
	$("#adm_set_id").val("");
	$("#agent").val("");
	$("#xray").val("");
	$("#antena_ip").val("");
	$("#antena_port").val("");
	$("#alert_ip").val("");
	$("#alert_port").val("");
	$("#alert_blue").val("");
	$("#alert_blue_sound").val("");
	$("#alert_red").val("");
	$("#alert_red_sound").val("");
	$("#frm")[0].reset();
}
function rfid_close() {
	$("#popup_rfid").css("display", "none");
	$(".shadow").css("display", "none");
	$("#adm_set_id").val("");
}
function popup_reg() {
	$("#u_id").val("");
	$("#u_pass").val("");
	$("#popup_reg_top").html("신규 관리자 등록");
	$("#popup_update").html("등록");
	$("#popup_update").html("등록");
	$("#agent").val("").prop("selected", true);
	$("#a_xray1").val("").prop("selected", true);
	$("#a_xray2").val("").prop("selected", true);
	$(".select_btn").css("background", "gray");
	$("#popup_reg").css("display", "block");
	$(".shadow").css("display", "block");
	$("#u_id").attr("readonly", false);
	$("#agent_ip").val("");
	$("#agent_port").val("");
	$("#agent_num").val("");
}
function agent_reg() {
	$("#u_id").val("");
	$("#u_pass").val("");
	$("#popup_reg_top").html("신규 관리자 등록");
	$("#adm_agent_save").html("등록");
	$(".select_btn").css("background", "gray");
	$("#popup_reg").css("display", "block");
	$(".shadow").css("display", "block");
	$("#agent_ip").attr("readonly", false);
	$("#agent_ip").val("");
	$("#agent_port").val("");
	$("#bizDeptCd").val("");
	$("#agent_num").val("");
}
function reg_close() {
	$("#popup_reg").css("display", "none");
	$("#popup_mod").css("display", "none");
	$(".shadow").css("display", "none");
	$(".select_btn").css("background", "gray");
}
function popup_mod(id, u_id, u_pass, agent, bizDeptCd) {
	$("#popup_reg").css("display", "block");
	$("#popup_reg_top").html("수정");
	$("#popup_save").html("수정");
	$("#u_idx").val(id);
	$("#u_id").val(u_id);
	$("#u_pass").val(u_pass);
	$("#bizDeptCd").val(bizDeptCd);
	$("#agent").val(agent).attr("selected", "selected");
	$("#u_id").attr("readonly", true);


	//var xray=$("#xray1").val(xray);
}
function xray_chg(xray1, xray2) {
	$("#a_xray1").val(xray1).prop("selected", true);
	$("#a_xray2").val(xray2).prop("selected", true);
}

function agent_mod(agent_id, agent_ip, agent_port, agent_num, bizDeptCd) {
	$("#popup_reg").css("display", "block");
	$("#popup_reg_top").html("수정");
	$("#adm_agent_save").html("수정");
	$("#agent_id").val(agent_id);
	$("#agent_ip").val(agent_ip);
	$("#agent_port").val(agent_port);
	$("#bizDeptCd").val(bizDeptCd);
	$("#agent_num").val(agent_num);
	$("#agent_ip").attr("readonly", true);
}

//장비설정 수정
function popup_mod_admin1(set_id, agent_id, xray, antena_ip, antena_port, alert_ip, alert_port, alert_blue, alert_blue_sound, alert_red, alert_red_sound) {
	$("#popup_rfid").css("display", "block");
	$("#popup_rfid_top").html("수정");
	$("#btn_new").html("수정");
	$("#set_id").val(set_id);
	$("#agent").val(agent_id);
	$("#xray").val(xray);
	$("#antena_ip").val(antena_ip);
	$("#antena_port").val(antena_port);
	$("#alert_ip").val(alert_ip);
	$("#alert_port").val(alert_port);
	$("#alert_blue").val(alert_blue);
	$("#alert_blue_sound").val(alert_blue_sound);
	$("#alert_red").val(alert_red);
	$("#alert_red_sound").val(alert_red_sound);
}

var xray_num = [];
$("#popup_save").click(function() {
	let u_idx = $("#u_idx").val();
	let data = {
		username: $("#u_id").val(),
		password: $("#u_pass").val(),
		agentId: $("#agent").val()
	}

	if (data.username == "" || data.password == "" || data.agentId == "") {
		alert("공란을 입력 해 주세요.");
	} else if (u_idx == '' || u_idx == null) {
		// 추가
		var xray = $("#a_xray1").val();
		var xray2 = $("#a_xray2").val();
		if (xray2 != "") {
			xray = xray + ",";
			xray = xray + xray2;
		}
		$.ajax({
			url: "/api/insertAdmMember",
			type: "POST",
			//global: false,
			dataType: "JSON",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			success: function(data) {
				reset_devide(data.data);
				location.href = "/admMember/admin3";
				//server_check();

			},
		}).fail(function() { });
		$("#popup_reg").css("display", "none");
		$(".shadow").css("display", "none");
		xray_num = [];
	} else {
		// 수정
		$.ajax({
			url: "/api/updateAdmMember/" + u_idx,
			type: "PUT",
			//global: false,
			dataType: "JSON",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			success: function(data) {
				reset_devide(data.data);
				location.href = "/admMember/admin3";
				//server_check();

			},
		}).fail(function() { });
		$("#popup_reg").css("display", "none");
		$(".shadow").css("display", "none");
		xray_num = [];
	}
});

$("#adm_agent_save").click(function() {
	let data = {
		agentIp: $("#agent_ip").val(),
		agentPort: $("#agent_port").val(),
		bizDeptCd: $("#bizDeptCd").val(),
		agentNum: $("#agent_num").val()
	};
	var agent_id = $("#agent_id").val();
	if (data.agentIp == "" || data.agentPort == "" || data.agentNum == "" || data.bizDeptCd == "") {
		alert("공란을 입력 해 주세요.");
	} else if (agent_id == '' || agent_id == null) {
		// 추가 
		$.ajax({
			url: "/api/insertAdmAgent",
			type: "POST",
			//global: false,
			dataType: "JSON",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			success: function(data) {
				console.log(data);
				reset_devide(data.data);
				location.href="/admAgent/admin4";
				//server_check();

			},
		}).fail(function() { });
		$("#popup_reg").css("display", "none");
		$(".shadow").css("display", "none");
		xray_num = [];
	} else {
		// 수정
		$.ajax({
			url: "/api/updateAdmAgent/" + agent_id,
			type: "PUT",
			//global: false,
			dataType: "JSON",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			success: function(data) {
				console.log(data);
				reset_devide(data.data);
				location.href="/admAgent/admin4";
				//server_check();

			},
		}).fail(function() { });
		$("#popup_reg").css("display", "none");
		$(".shadow").css("display", "none");
		xray_num = [];
	}
});

$("#btn_new").click(function() {
	let set_id = $("#set_id").val();
	let data = {
		agentId: $("#agent").val(),
		xray: $("#xray").val(),
		antenaIp: $("#antena_ip").val(),
		antenaPort: $("#antena_port").val(),
		alertIp: $("#alert_ip").val(),
		alertPort: $("#alert_port").val(),
		alertBlue: $("#alert_blue").val(),
		alertBlueSound: $("#alert_blue_sound").val(),
		alertRed: $("#alert_red").val(),
		alertRedSound: $("#alert_red_sound").val()
	}
	console.log(set_id);

	if (set_id == '' || set_id == null) {
		// 추가 
		$.ajax({
			url: "/api/insertAdmSet",
			type: "POST",
			//global: false,
			dataType: "JSON",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			success: function(data) {
				console.log(data);
				reset_devide(data.data);
				location.href = "/admSet/admin1";
				//server_check();

			},
		}).fail(function() { });
		$("#popup_reg").css("display", "none");
		$(".shadow").css("display", "none");
		xray_num = [];
	} else {
		// 수정
		$.ajax({
			url: "/api/updateAdmSet/" + set_id,
			type: "PUT",
			//global: false,
			dataType: "JSON",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(data),
			success: function(data) {
				reset_devide(data.data);
				location.href = "/admSet/admin1";
				//server_check();

			},
		}).fail(function() { alert("x") });
		$("#popup_reg").css("display", "none");
		$(".shadow").css("display", "none");
		xray_num = [];
	}
});


$("#search").on("click", function() {
	let bizDeptCd = $("#bizDeptCd").val();
	let sdate = $("#s_datetime").val();
	let edate = $("#e_datetime").val();
	let result_temp = $("#result_temp").val();
	let keyword = $("#keyword").val();

	let searchQs = '';
	searchQs += bizDeptCd == '?bizDeptCd=' ? '' : '?bizDeptCd=' + bizDeptCd;
	searchQs += sdate == '' ? '' : '&sdate=' + sdate;
	searchQs += edate == '' ? '' : '&edate=' + edate;
	searchQs += result_temp == '' ? '' : '&result_temp=' + result_temp;
	searchQs += keyword == '' ? '' : '&keyword=' + keyword;

	location.href = "/eventHistory/search1" + searchQs;

	//var form = document.userinput;
	//form.submit();
});

function update_event() {
	$.ajax({
		url: "insert_event_update.php",
		type: "POST",
		dataType: "JSON",
		data: {},
		success: function(data) {
			//alert("전체 출입승인 됨");
		},
	}).fail(function() {
		alert("실패");
	});
}
function del_db() {
	$.ajax({
		url: "insert_event_del.php",
		type: "POST",
		//global: false,
		dataType: "JSON",
		data: {
			//result : 'y'
		},
		success: function(data) {
			//alert("전체 삭제완료");
			server_check();
		},
	}).fail(function() {
		alert("실패");
	});
}

function reset_devide(agent_id) {
	console.log("reset_devicde id " + agent_id);
	$.ajax({
		url: "/rest/send_reset_device_info.php",
		type: "POST",
		//global: false,
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({
			xray_id: agent_id,
		}),
		success: function(data) {
			//alert(data);
		},
	}).fail(function() { });
}
