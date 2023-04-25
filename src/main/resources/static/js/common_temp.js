var set3;
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

//장비설정 수정
//$('.popup_mod_admin1').on('click',function() {
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
    
    /*for(var i=0;i<xray_temp.length;i++){
        $("#a_xray"+(i+1)).val("")
        $("#a_xray"+(i+1)).val(xray_temp[i]);
    }*/
});
    
var xray_num=[];
$("#popup_save").click(function() {
    var u_id=$("#u_id").val();
    var u_pass=$("#u_pass").val();
    if(u_id=='' || u_pass==''){
        alert("아이디 또는 비번을 입력 해 주세요.");
    }else{
        /*$(".back_red").each(function(index){
            var xray_temp=$(this).attr("id");
            //alert(this_val)
            xray_temp = xray_temp.slice(4, 5);
            xray_num.push(xray_temp);
        });
        var xray=xray_num.toString();
        */
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
function del_user(){
    //선택된 체크박스의 아이디 가져오기
    //삭제 명령
    //해당 라인 삭제
    alert("삭제");
}

$('#search').on('click',function() {
    var form = document.userinput;
    form.submit();
});









var alert_red;
function warning_light(signal){
    if(signal=="y"){
        socket.send('green');
        //$("#alert_green").css("background","green");
        audio('beep_green.mp3');
    }else if(signal=="n"){
        socket.send('rb');
        //$("#alert_red").css("background","red");
        audio('beep_red.wav');
        //for(var i=0;i<5;i++)
        //setTimeout("audio('red')",1000);
    }
    //setTimeout("light_stop()",3000);
}
function audio(file){
    var audio = new Audio('/assets/audio/'+file);
    audio.play();
}
function light_stop(xray_id){
    socket.send('off,'+xray_id);
    server_now=setInterval(server_check,1000);
}
function light_start(data){
    var xray_id=data.xray_id;
    console.log(xray_id);
    if(data.length != 0){
        if(data.u_confirm=="1"){
            socket.send("off,"+xray_id);
        }else if(data.result=="1" && data.u_confirm=="0"){
            socket.send("green,"+xray_id);
        }else if((data.result=="0" && data.u_confirm=="0") || data.result=="2"){
            socket.send("rb,1");
            //start_sound();
        }
    }
    clearTimeout(server_now);
    setTimeout("light_stop('"+xray_id+"')",set3);
}
function load_set(){
    $.ajax({ 
        url: "load_set.php", 
        type: 'POST', 
        //global: false,
        dataType: 'JSON', 
        data: { 
        }, success: function (data) {
            set3=data.set3*1000;
        } 
    }).fail(function() {
    }); 
};
function load_set2(){
    $.ajax({ 
        url: "load_set2.php", 
        type: 'POST', 
        //global: false,
        dataType: 'JSON', 
        data: { 
        }, success: function (data) {
            var xray=[];
            var alert_red=[];
            var xralert_red_sounday=[];
            var alert_blue=[];
            var alert_blue_sound=[];
            for(var i=0;i<data.length;i++){
                xray[i]=data[i].xray;
                alert_red[i]=data[i].alert_red;
                xralert_red_sounday[i]=data[i].alert_red_sound;
                alert_blue[i]=data[i].alert_blue;
                alert_blue_sound[i]=data[i].alert_blue_sound;
            }
        } 
    }).fail(function() {
    }); 
};
function start_sound(){
    socket.send('sound'); 
};
let socket = new WebSocket("ws://localhost:8081");


