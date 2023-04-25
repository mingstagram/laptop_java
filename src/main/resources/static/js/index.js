let upload_btn = document.getElementById("file-upload");
var rslt = document.getElementById("update_result");
let input_file;
let deley_render;

function DropFile(dropAreaId, fileListId) {
  // ("drop-file", "files")
  let dropArea = document.getElementById(dropAreaId);
  let fileList = document.getElementById(fileListId);
  // let excel_file = document.getElementById("file-upload");

  function preventDefaults(e) {
    e.preventDefault();
    e.stopPropagation();
  }

  function highlight(e) {
    preventDefaults(e);
    dropArea.classList.add("highlight");
  }

  function unhighlight(e) {
    preventDefaults(e);
    dropArea.classList.remove("highlight");
  }

  function handleDrop(e) {
    input_file = e.target.cloneNode();
    console.log(e.target);
    unhighlight(e);
    let dt = e.dataTransfer;
    let files = dt.files;

    handleFiles(files);
    const fileList = document.getElementById(fileListId);
    if (fileList) {
      fileList.scrollTo({ top: fileList.scrollHeight });
    }
  }

  function handleFiles(files) {
    console.log(files);
    files = [...files];
    files.forEach(previewFile);
  }

  function previewFile(file) {
    console.log(file);
    // 파일 업로드 확장자 체크
    if (file.name != "") {
      var ext = file.name.split(".").pop().toLowerCase();
      var ext_list = ["xls", "xlsx"];
      rslt.style.display = "none";
      if (!ext_list.includes(ext)) {
        alert("엑셀파일만 등록가능합니다.");
        return;
      } else {
        while (fileList.hasChildNodes()) {
          fileList.removeChild(fileList.firstChild);
        }
        var result = confirm("사원리스트를 업데이트 하시겠습니까?");
        if (result) {
          readExcel(file);
          deley_render = setTimeout(() => {
            fileList.appendChild(renderFile(file));
          }, 500);
        }
      }
    }
  }

  function renderFile(file) {
    let fileDOM = document.createElement("div");
    fileDOM.className = "file";
    fileDOM.innerHTML = `
      <div class="thumbnail">
        <img src="/image/excel_icon.png" alt="파일타입 이미지" class="image">
      </div>
      <div class="details">
        <header class="header">
          <span class="name">${file.name}</span>
          <span class="size">${file.size}</span>
        </header>
        <div class="progress">
          <div class="bar"></div>
        </div>
        <div class="status">
          <span class="percent">100% done</span>
          <span class="speed">900KB/sec</span>
        </div>
      </div>
    `;
    return fileDOM;
  }

  dropArea.addEventListener("dragenter", highlight, false);
  dropArea.addEventListener("dragover", highlight, false);
  dropArea.addEventListener("dragleave", unhighlight, false);
  dropArea.addEventListener("drop", handleDrop, false);
  dropArea.addEventListener("drop", handleDrop, false);
  return {
    handleFiles,
  };
}

const dropFile = new DropFile("drop-file", "files");

function readExcel(file) {
  let reader = new FileReader();
  //console.log(file);
  reader.onload = function () {
    let data = reader.result;
    let workBook = XLSX.read(data, { type: "binary" });
    workBook.SheetNames.forEach(function (sheetName) {
      // console.log('SheetName: ' + sheetName);
      let rows = XLSX.utils.sheet_to_json(workBook.Sheets[sheetName]);
      // console.log('eno: ' + rows[0]['eno']+' team: ' + rows['team']+' name: ' + rows['name']+' position: ' + rows['position']);
      console.log(JSON.stringify(rows));

      insert_data(rows);
    });
  };
  reader.readAsBinaryString(file);
}

function insert_data(rows) {
  console.log(rows);
  if (
    rows[0].hasOwnProperty("사번") &&
    rows[0].hasOwnProperty("성명") &&
    rows[0].hasOwnProperty("BARCODE") &&
    rows[0].hasOwnProperty("소속") &&
    rows[0].hasOwnProperty("RFID")
  ) {
    var list = {
      list: JSON.stringify(rows),
    }; 
	 
    $.ajax({
      type: "POST",
      data: list, 
	  dataType: 'JSON', 
      url: "/api/util/excelUpload",
      success: function (data) {
        console.log(data);
        var tot = document.getElementById("total");
        var upd = document.getElementById("update");
        var ins = document.getElementById("insert");
        setTimeout(() => {
          rslt.style.display = "block";
          tot.innerText = data.total + "건";
          upd.innerText = data.update + "건";
          ins.innerText = data.insert + "건";
          failed.innerText = data.failed + "건";
        }, 500);
      },
      error: function () {
        alert("텅신실패");
      },
    });
  } else {
    clearTimeout(deley_render);
    alert("양식에 맞는 엑셀 데이터를 넣어주세요!");
  }
}
