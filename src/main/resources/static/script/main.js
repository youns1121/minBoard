

$(document).keypress(function(e) {
    if (e.keyCode == 13) e.preventDefault();
}
);




function createFn(){
    const titleValueCheck = $('#title').val().trim().length;
    const contentsValueCheck = $('#contents').val().trim().length;

    if(titleValueCheck == 0){
        alert("제목을 입력해 주세요.")
    }
    if(contentsValueCheck < 20 || contentsValueCheck > 100){
        alert("내용은 20자 이상 100자 이하")
    }
    if(titleValueCheck != 0 && (contentsValueCheck > 20 && contentsValueCheck < 100)){
        attachedExtenSionCheck()
    }

}

function deleteFn(){
    let formConrole = $('form').eq(0)
    formConrole.attr("action", "/board/delete");
    formConrole.attr("method","post");
    formConrole.submit();
    alert("삭제 되었습니다")
}

function updateFn(){
    const titleValueCheck = $('#title').val().trim().length;
    const contentsValueCheck = $('#contents').val().trim().length;

    if(titleValueCheck == 0){
        alert("제목을 입력해 주세요.")
    }
    if(contentsValueCheck < 20 || contentsValueCheck > 100){
        alert("내용은 20자 이상 100자 이하")
    }
    if(titleValueCheck != 0 && (contentsValueCheck > 20 && contentsValueCheck < 100)){
        updateAttachedExtenSionCheck()
    }

}

function attachedExtenSionCheck(){
    let status = true;
    let inputFile = $(".file")
    for(let i = 0; i < inputFile.length; i++ ){
        var fileval = inputFile.eq(i).val().split('.').pop().toLowerCase();
        if(inputFile[i].value != "" &&
            $.inArray(fileval, ['gif', 'png', 'jpg','jpeg','doc','docx','xls','xlsx','hwp','pdf', 'pptx', 'txt']) == -1){
            alert(fileval + "는(은) 등록할 수 없는 확장자입니다.")
            inputFile.val("");
            status = false;
        }
    }
    if(status == true){
        uploadNewFile()
    }

}

function updateAttachedExtenSionCheck(){
    let status = true;
    let inputFile = $(".file")
    for(let i = 0; i < inputFile.length; i++ ){
        var fileval = inputFile.eq(i).val().split('.').pop().toLowerCase();
        if(inputFile[i].value != "" &&
            $.inArray(fileval, ['gif', 'png', 'jpg','jpeg','doc','docx','xls','xlsx','hwp','pdf', 'pptx', 'txt']) == -1){
            alert(fileval + "는(은) 등록할 수 없는 확장자입니다.")
            inputFile.val("");
            status = false;
        }
    }
    if(status == true){
        uploadUpdateFile()
    }
}



function uploadNewFile(){
    let formData = new FormData();
    let inputFile = $("input[type='file']")
    let inputFileLength = inputFile.length
    formData.append('title', $('#title').val())
    formData.append('contents', $('#contents').val())
    formData.append('boardAdminId', $(".selectbox option:selected").data('adminid'))
    formData.append('categoryName', $('.selectbox option:selected').text())

    for(var i = 0;  i < inputFileLength; i++){
        if(!(inputFile[i].value == "")){
            formData.append('fileList', inputFile[i].files[0])
        }
    }

    $.ajax({
        url: '/board/new',
        enctype: 'multipart/form-data',
        contentType : false,
        processData: false,
        cache : false,
        type : "POST",
        data : formData,
        success: function(returnData) {
            if (returnData == "false") {
                alert('파일 허용 갯수가 초과 되었습니다.')
                $("input[type='file']").val("")
            }
            if (returnData != "false") {
                alert('등록되었습니다.')
                location.href = '/board/view/' + returnData + '?' + "status=" + true
            }
        },
        error : function (){
            return false;
        }
    });
}

function uploadUpdateFile() {
    let formData = new FormData();
    let inputFile = $("input[type='file']")
    formData.append('boardAdminId', $('#updateButton').data('adminid'))
    formData.append('title', $('#title').val())
    formData.append('contents', $('#contents').val())
    let id = parseInt($('#id').val())
    formData.append('id', id)

    for (var i = 0; i < inputFile.length; i++) {
        if (!(inputFile[i].value == "")) {
            formData.append('fileList', inputFile[i].files[0])
        }
    }
    $.ajax({
        url: '/board/update',
        type: "POST",
        enctype: 'multipart/form-data',
        contentType: false,
        processData: false,
        cache: false,
        data: formData,
        success: function (returnData) {
            if (returnData == "false") {
                alert('파일 허용 갯수가 초과 되었습니다.')
                $("input[type='file']").val("")
            }

            if (returnData != "false") {
                alert('등록되었습니다.')
                location.href = '/board/view/' + returnData + '?' + "status=" + true
            }
        },
        error: function () {
            return false;
        }
    });
}











