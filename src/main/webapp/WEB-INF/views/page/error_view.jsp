<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<style>
.select {
    width: 150px;
    height: 35px;
    background: url("https://freepikpsd.com/media/2019/10/down-arrow-icon-png-7-Transparent-Images.png") calc(100% - 5px) center no-repeat;
    background-size: 20px;
    border-radius: 4px;
    margin-left: 0px;
    margin-right: 0px;
    outline: 0 none;
    border: 1px solid gray;
}

.select option {
    padding: 3px 0;
}

.reload {
    width: 50px;
    height: 35px;
    border-radius: 4px;
    outline: 0 none;
    border: 1px solid gray;
}

.download {
    height: 35px;
    border-radius: 4px;
    outline: 0 none;
    border: 1px solid gray;
}

#table_contents4 {
    width: 100%;
    display: inline-block;
    overflow-y: auto;
    font-size: 15px;
    height: 600px;
    border: 1px solid gray;
    text-align: left;
    border-radius: 4px;
}

ul.tabs {
    margin: 0px;
    padding: 0px;
    list-style: none;
}

ul.tabs li {
    display: inline-block;
    background: #898989;
    color: white;
    padding: 11px 15px;
    cursor: pointer;
    border-top: 1px solid gray;
    border-left: 1px solid gray;
    border-right: 1px solid gray;
}

ul.tabs li.current {
    background: #e0e0e0;
    color: #222;
}
</style>

<div id=right_box>
	<div id=title_bg>
		<div id=title>
			<img src=/image/circle.jpg
				style="width: 30px; vertical-align: top; margin-top: 1px; margin-right: 4px;">실시간
			로그확인
		</div>
	</div>
	<div id=table3>
		<div id=top_box_out>
			<div id=top_box style="margin-bottom:0px;">
			
			</div>
		</div>
		<div id=table_contents4 class="target">
			${errorLog}
		</div>
		<div id=top_box style="margin-top:5px">
            <div id=search_left class=*title>
            </div>
            <div id=search_left class=*title>
                <input class="download" type="button" value="로그 다운로드" onclick="log_download();">
            </div>
        </div>
	</div>
</div>

<script>
document.getElementById('table_contents4').scrollTop = document.getElementById('table_contents4').scrollHeight;

function log_download() { 
    location.href = "/api/util/logDownload";
}

function myFunction() {
    var input = document.getElementById("Search");
    var filter = input.value.toLowerCase();
    var nodes = document.getElementsByClassName('target');

    for (i = 0; i < nodes.length; i++) {
        if (nodes[i].innerText.toLowerCase().includes(filter)) {
            nodes[i].style.display = "block";
        } else {
            nodes[i].style.display = "none";
        }
    }
}
</script>