$(document).ready(function () {
    let emailChecked = false;
    let checkedEmail = "";

    $("#email-check-btn").click(function () {
        const email = $("#email-input").val();
        // if (email === checkedEmail) {
        //     return; // 이미 중복 체크한 이메일이면 다시 체크하지 않음
        // }

        $.ajax({
            type: "GET",
            url: "/member/check-email",
            data: { email: email },
            success: function (response) {
                if (response) {
                    $("#email-check-result").text("이미 사용 중인 이메일입니다.").css("color", "red");
                    emailChecked = false;
                } else {
                    $("#email-check-result").text("사용 가능한 이메일입니다.").css("color", "green");
                    emailChecked = true;
                    checkedEmail = email; // 중복 체크된 이메일 저장
                }
            }
        });
    });

    $("#email-input").on("input", function () {
        emailChecked = false;
        $("#email-check-result").text("");
    });// 이메일 입력 변경 시 결과 초기화


    $("#signup-form").submit(function (event) {
        if (!emailChecked) {
            $("#email-check-result").text("이메일 중복 확인을 해주세요.");
            event.preventDefault(); // 폼 제출 중단
        }
    }); //중복확인을 안했을때


    $("#password, #passwordCheck").on("input", function () {
        const password = $("#password").val();
        const passwordCheck = $("#passwordCheck").val();
        if (password === passwordCheck) {
            $("#password-check-result").text("비밀번호가 일치합니다.").css("color", "green");
        } else {
            $("#password-check-result").text("비밀번호가 일치하지 않습니다.").css("color", "red");
        }
    });


    $("#password").on("input", function () {
        $("#password-check-result").text("");
    });// 비밀번호 입력 변경 시 결과 초기화
});
