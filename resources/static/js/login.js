function findpassword() {
    alert("이메일로 비밀번호 변경 링크를 보냈습니다.");
};

function resetpassword(){
    var pwd = document.getElementById("aPw"). value;
    var pwd_check = document.getElementById("a_pwd_check"). value;
    console.log(pwd);
    console.log(pwd_check);

    if(pwd == ""){
        alert("비밀번호를 입력해주세요");
        return false;
    }

    if(pwd_check == ""){
        alert("비밀번호확인을 입력해주세요");
        return false;
    }

    if(pwd == pwd_check){
        return true;
    }else{
        alert("비밀번호가 다릅니다.");
        return false;
    }
};

/////////////////////////////////////////////////////////////////////////////////////////
function pwd_check_func(){
    var pwd_check = document.getElementById('a_pwd_check').value;
    var pwd = document.getElementById('aPw').value;
    var pwd_check_error = document.getElementById('pwd_check_error');

    if(pwd != pwd_check){
        pwd_check_error.innerText = "비밀번호가 일치하지 않습니다.";
    }
    if(pwd == pwd_check || pwd_check == ""){
        pwd_check_error.innerText = "";
    }
}
function email_check_func(){
    var email_check = document.getElementById('a_email_check').value;
    var email = document.getElementById('aEmail').value;
    var email_check_error = document.getElementById('email_check_error');

    if(email_check == ""){
        email_check_error.innerText = "";
    }else{
        if(email_check.indexOf('@') != -1){
            if(email_check.indexOf('@') + 2 > email_check.indexOf('.')){
                email_check_error.innerText = "옳지 않는 이메일 형식입니다.";
            }else if(email_check.indexOf('@') < email_check.indexOf('.')){
                if(email != email_check){
                    email_check_error.innerText = "이메일이 일치하지 않습니다.";
                }else{
                    email_check_error.innerText = "";
                }
            }
        }else if(email_check.indexOf('@') == -1){
            email_check_error.innerText = "옳지 않는 이메일 형식입니다.";
        }
    }
}
function email_func(){
    var email = document.getElementById('aEmail').value;
    var email_error = document.getElementById("email_error");

    if(email == ""){
        email_error.innerText = "";
    }else{
        if(email.indexOf('@') != -1){
            if(email.indexOf('@') + 2 > email.indexOf('.')){
                email_error.innerText = "옳지 않는 이메일 형식입니다.";
            }else if(email.indexOf('@') < email.indexOf('.')){
                email_error.innerText = "";
            }
        }else if(email.indexOf('@') == -1){
            email_error.innerText = "옳지 않는 이메일 형식입니다.";
        }
    }
}

$(document).ready(function(){
    $("input[name=submit]").click(function(){
        var essential = $("input:checkbox[id='essential']");

        console.log(essential.length);
        console.log(essential.filter(":checked").length);

        if(essential.length == essential.filter(":checked").length){
            return true;
        }
        alert("필수 동의를 선택해주세요.")
        return false;
    })

	$("#check_all").click(function(){
		if(this.checked){
			$("input:checkbox").prop("checked", true);
		}else{
            $("input:checkbox").prop("checked", false);
        }
	});

	$("input[name=mycheck]").click(function(){
        if(this.checked){
            var a = $("input[name=mycheck]");
            console.log(a.length);
            console.log(a.filter(":checked").length);
            if(a.length == a.filter(":checked").length){
                $("#check_all").prop("checked", true);
            }
        }else{
            $("#check_all").prop("checked", false);
        }
	});

});