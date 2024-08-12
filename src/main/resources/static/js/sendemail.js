$(document).ready(function () {

    $('#checkEmailandName').on('click', function(){
        checkEmailandName();
    });

    function checkEmailandName(){
        const email = $('#email').val();
        const name = $('#name').val();

        if(!email || email.trim() === ""){
            alert("이메일을 입력하세요.");
        } else if(!name || name.trim()===""){
            alert("이름을 입력하세요.");
        }else {
            $.ajax({
                type: 'GET',
                url: '/member/checkEmailandName',
                data: {
                    'email': email,
                    'name':name
                },
                dataType: "text",
            }).done(function(result){
                console.log("result :" + result);
                if (result == "true") {
                    sendEmail();
                    alert('임시비밀번호를 전송했습니다.');
                    window.close();
                } else if (result == "false") {
                    alert('이메일과 이름을 확인해주세요.');
                }
            }).fail(function(error){
                alert(JSON.stringify(error));
                alert('오류가 발생했습니다.');
            })
        }
    }
    function sendEmail(){
        const email = $('#email').val();

        $.ajax({
            type: 'POST',
            url: '/member/sendPwd',
            data: {
                'email' : email
            }
        })
    }

});
