load_set();
load_set2();
var socket_connect=1;
let socket = new WebSocket("ws://localhost:8081");
function socket_start(){
    setTimeout(function() { 
        socket_connect=socket.readyState;
        //console.log(socket_connect);
        if(socket_connect==0){
            $("#message").html("Socket connect fail. Wait for reconnection.")
            reload();
        }else{
            $("#message").html("Connected")
        }
    }, 100);
}




function reload(){
    setTimeout(function() { 
        location.reload();
    }, 10000);
}

var set1;
var set2;
var set3;
var set4;
function login(){
    location.href="main.php";
}
function link(link){
    location.href=link;
}
function rfid_reg(){
    $("#popup_rfid").css("display","block");
    $(".shadow").css("display","block");
}
function rfid_close(){
    $("#popup_rfid").css("display","none");
    $(".shadow").css("display","none");
}
function popup_reg(){
    $("#u_id").val('');
    $("#u_pass").val('');
    $("#popup_reg_top").html("신규 관리자 등록");
    $("#popup_save").html("등록");
    $(".select_btn").css("background","gray");
    $("#popup_reg").css("display","block");
    $(".shadow").css("display","block");
    $('#u_id').attr('readonly', false); 
}
function reg_close(){
    $("#popup_reg").css("display","none");
    $("#popup_mod").css("display","none");
    $(".shadow").css("display","none");
    $(".select_btn").css("background","gray");
}
function popup_mod(u_id,u_pass,xray){
    $("#popup_reg").css("display","block");
    $("#popup_reg_top").html("수정");
    $("#popup_save").html("수정");
    $("#u_id").val(u_id);
    $("#u_pass").val(u_pass);
    $('#u_id').attr('readonly', true); 
    //var xray=$("#xray1").val(xray);
    var xray_temp=xray.split(",");
    for(var i=1;i<(xray_temp.length+1);i++)
        $("#xray"+xray_temp[i-1]).addClass("back_red");
    for(var i=0;i<xray_temp.length;i++){
        $("#a_xray"+(i+1)).val("")
        console.log(i);
        $("#a_xray"+(i+1)).val(xray_temp[i]);
    }
}

function agent_mod(agent_ip,agent_port,agent_num){
    $("#popup_reg").css("display","block");
    $("#popup_reg_top").html("수정");
    $("#adm_agent_save").html("수정");
    $("#agent_ip").val(agent_ip);
    $("#agent_port").val(agent_port);
    $("#agent_num").val(agent_num);
    $('#agent_ip').attr('readonly', true); 
}



function load_set(){
    $.ajax({ 
        url: "load_set.php", 
        type: 'POST', 
        //global: false,
        dataType: 'JSON', 
        data: { 
        }, success: function (data) {
            set1=data.set1;
            set2=data.set2;
            set3=data.set3*1000;
            set4=data.set4;
        } 
    }).fail(function() {
    }); 
};


var xray=[];
var alert_red=[];
var alert_red_sound=[];
var alert_blue=[];
var alert_blue_sound=[];
function load_set2(){
    $.ajax({ 
        url: "load_set2.php", 
        type: 'POST', 
        //global: false,
        dataType: 'JSON', 
        data: { 
        }, success: function (data) {
            for(var i=0;i<data.length;i++){
                xray[i]=data[i].xray;
                alert_red[i]=data[i].alert_red;
                alert_red_sound[i]=data[i].alert_red_sound;
                alert_blue[i]=data[i].alert_blue;
                alert_blue_sound[i]=data[i].alert_blue_sound;
            }
        } 
    }).fail(function() {
    }); 
};



//장비설정 수정
$(document).on("click", ".popup_mod_admin1", function () {
    $("#popup_rfid").css("display","block");
    $("#popup_rfid_top").html("수정");
    $("#btn_new").html("수정");
    var count=$(this).parent().children().length;
    var adm_set_id=$(this).parent().attr("adm_set_id");
    var col_name=[];
    var col_val=[];
    for(var i=0;i<count;i++){
        col_name[i]=$(this).parent().find("li:eq("+i+")").attr("name");
        col_val[i]=$(this).parent().find("li:eq("+i+")").attr("val");
        $("#"+col_name[i]).val(col_val[i]);
    }
    $("#adm_set_id").val(adm_set_id);
});
    
var xray_num=[];
$("#popup_save").click(function() {
    var u_id=$("#u_id").val();
    var u_pass=$("#u_pass").val();
    if(u_id=='' || u_pass==''){
        alert("아이디 또는 비번을 입력 해 주세요.");
    }else{
        var xray=$("#a_xray1").val();
        var xray2=$("#a_xray2").val();
        if(xray2)
            xray=xray+",";
        xray=xray+xray2;
        $.ajax({ 
            url: "admin3_xray_db.php", 
            type: 'POST', 
            //global: false,
            dataType: 'JSON', 
            data: { 
                u_id:u_id,
                u_pass:u_pass,
                xray:xray,
            }, success: function (data) {
                reg_close();
                server_check();
            } 
        }).fail(function() {
        }); 
        $("#popup_reg").css("display","none");
        $(".shadow").css("display","none");
        xray_num=[];
    }
});


$("#adm_agent_save").click(function() {
    var agent_ip=$("#agent_ip").val();
    var agent_port=$("#agent_port").val();
    var agent_num=$("#agent_num").val();
    if(agent_ip=='' || agent_port=='' || agent_num==''){
        alert("공란을 입력 해 주세요.");
    }else{
        $.ajax({ 
            url: "admin4_db.php", 
            type: 'POST', 
            //global: false,
            dataType: 'JSON', 
            data: { 
                agent_ip:agent_ip,
                agent_port:agent_port,
                agent_num:agent_num,
            }, success: function (data) {
                reg_close();
                server_check();
            } 
        }).fail(function() {
        }); 
        $("#popup_reg").css("display","none");
        $(".shadow").css("display","none");
        xray_num=[];
    }
});

$('#search').on('click',function() {
    var form = document.userinput;
    form.submit();
});



function main_alert(){
    $.ajax({ 
        url: "main_alert.php", 
        type: 'POST', 
        //global: false,
        dataType: 'JSON', 
        data: { 
        }, success: function (data) {
            //light_start(data);
            u_confirm();
        } 
    }).fail(function() {
    }); 
}

function light_start(data){
    if(data!=null && socket_connect==1){
        var xray_id=data.xray_id-1;
        if(data.u_confirm=="1"){
            light_stop(xray_id);
        }else{
            if(data.result=="1" && data.u_confirm=="0"){
                socket.send("green,"+xray_id);
                if(alert_red_sound[xray_id]==1)
                    start_sound(xray_id);
            }else if((data.result=="0" && data.u_confirm=="0") || data.result=="2"){
                socket.send("rb,"+xray_id);
                console.log(alert_red_sound[xray_id]);
                if(alert_red_sound[xray_id]==1)
                    start_sound(xray_id);
            }
        }
        clearTimeout(server_now);
        setTimeout("light_stop('"+xray_id+"')",set3);
    }
}
function light_stop(xray_id){
    socket.send('off,'+xray_id);
    //socket.send('off,0');
    //socket.send('off,1');
    server_now=setInterval(server_check,1000);
}

function start_sound(xray_id){
    socket.send('sound,'+xray_id); 
};





function u_confirm(){
    $.ajax({ 
        url: "u_confirm_db.php", 
        type: 'POST', 
        //global: false,
        dataType: 'JSON', 
        data: { 
        }, success: function (data) {
        } 
    }).fail(function() {
    }); 
}




function update_event(){
    $.ajax({ 
        url: "insert_event_update.php", 
        type: 'POST', 
        dataType: 'JSON',
        data: { 
        }, success: function (data) {
            //alert("전체 출입승인 됨");
        } 
    }).fail(function() {
        alert("실패");
    }); 
}
function del_db(){
    $.ajax({ 
        url: "insert_event_del.php", 
        type: 'POST', 
        //global: false,
        dataType: 'JSON',
        data: { 
            //result : 'y'
        }, success: function (data) {
            //alert("전체 삭제완료");
            server_check();
        } 
    }).fail(function() {
        alert("실패");
    }); 
}