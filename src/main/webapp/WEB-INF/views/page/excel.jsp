<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
 
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" /> 
    <script src="/js/jquery.min.js"></script>

    <link rel="stylesheet" type="text/css" href="/css/style.css" />
    <title>Document</title>
</head>
<style> 
.btn_w {
    cursor: pointer;
    font-size: 14px;
    padding: 5px 15px 5px 15px;
    margin: 5px;
    display: inline-block;
    border: 1px solid #ccc;
    background: white;
    border-radius: 20px; 
    padding: 10px;
    text-align: center;
}
</style>

<body>
    <div id="root">
        <div class="d-flex">
            <input class="btn_w" type="button" onclick="location.href='/laptopInfo/search2'" value="뒤로가기" />
            <a href="/sample/excel_sample.xlsx"><input class="btn_w" type="button" value="양식 다운로드" /></a>
        </div>

        <h2 class="title">엑셀 업로드</h2>
        <hr />
        <div style="text-align: center">
            <span style="color: red"><b>※ 양식에 맞춰 데이터 입력 후 업로드 하시기바랍니다.</b></br></span>
        </div>
        <div class="contents">
            <div class="upload-box">
                <div id="drop-file" class="drag-file">
                    <!-- <img src="https://img.icons8.com/pastel-glyph/2x/image-file.png" alt="파일 아이콘" class="image"> -->
                    <img src="/image/excel_icon.png" alt="파일 아이콘" class="image" />
                    <p class="message">Drag files to upload</p>
                </div>
                <label class="file-label" for="chooseFile" style="background-color: #bb0841">Choose File</label>
                <input class="file" id="chooseFile" type="file" onchange="dropFile.handleFiles(this.files)" />
            </div>
            <div id="files" class="files"></div>
            <button class="file-upload" id="file-upload" onclick="readExcel(this)" style="display: none">
                Update
            </button>
            <div id="update_result">
                <ul>
                    <li>전체사원수</li>
                    <li id="total">9명</li>
                </ul>
                <ul>
                    <li>업데이트 수</li>
                    <li id="update">4명</li>
                </ul>
                <ul>
                    <li>신규추가</li>
                    <li id="insert">5명</li>
                </ul>
                <ul>
                    <li>실패</li>
                    <li id="failed">1명</li>
                </ul>
            </div>
        </div>
    </div>
</body>
<script></script> 
    <script src="/js/index.js"></script>
    <script src="/js/xlsx.full.js"></script>
</html>