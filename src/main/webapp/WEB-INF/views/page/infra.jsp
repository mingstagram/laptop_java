<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="./jquery_lastest.min.js"></script>
    <link rel="stylesheet" href="/css/style.css" />
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
    width: 100;
    padding: 10px;
    text-align: center;
}
</style>

<body>
    <div id="root">
        <div class="d-flex">
            <input class="btn_w" type="button" onclick="location.href='/admMember/admin3'" value="뒤로가기" />
        </div>

        <h2 class="title">관리자 등록</h2>
        <hr />
        <div class="contents">
            <form action="/admMember/saveInfra" method="post">
                <div class="upload-box">

                    아이디<input type="text" name="username"><br />
                    비밀번호<input type="text" name="password"><br />
                    <!-- 회사코드<input type="text" name="bizDeptCd">
                    (파주 : 07, 안산 : 03, 광주 : 01, 구미 : 02, 평택 : 04. 마곡 : 18) -->


                    <label class="file-label" for="chooseFile" style="background-color: #bb0841">등록</label>
                    <input class="file" id="chooseFile" type="submit" />
                </div>
            </form>

        </div>
    </div>
</body>
<script></script>
<script src="xlsx.full.js"></script>
<script src="index.js"></script>

</html>
    
    <link rel="stylesheet" type="text/css" href="css/home.css" />
    <script type="text/javascript" src="js/common.js"></script>