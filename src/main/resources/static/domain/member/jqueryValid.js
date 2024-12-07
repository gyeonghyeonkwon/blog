
  jQuery(function (){
    const form = $('#form');
    form.validate({
      rules: {
        loginId: {
          required: true,
          minlength: 5,
          maxlength: 12,
          pattern: /^[a-z0-9](?=.*[a-z])(?=.*\d)[a-z0-9]{5,12}$/,
        },
        realName: {
          required: true,
          minlength: 2,
          maxlength: 4,
          pattern: /[가-힣]/,
        },
        email: {
          required: true,
          email: true,
        },
        verificationCode: {
          required: true,
          minlength: 6,
          pattern: /^[1-9]*$/,
        },
        password: {
          required : true ,
        },
        passwordConfirm: {
          required : true ,
          equalTo: "#password"
        }
      },
      messages: {
        loginId: {
          required: '아이디를입력하세요.',
          minlength: '최소 5글자이상입력하세요.',
          maxlength: '최대 12글자이상의 아이디를 생성 할 수없습니다.',
          pattern:'숫자로만 입력할 수 없습니다. 소문자 + 숫자 로만 입력가능합니다.',
        },
        realName: {
          required: '본명을 입력하세요.',
          minlength:'최소 두글자이상 작성해주세요..',
          maxlength: '최대 네글자를 초과할 수 없습니다.',
          pattern: '한글만 입력가능합니다.',
        },
        email: {
          required: '이메일을 입력해주세요.',
          email: '올바른 이메일 형식으로 입력하세요.',
        },
        verificationCode: {
          required: '인증번호를 입력해주세요.',
          minlength: '6자리 인증번호를 입력해주세요.',
          pattern: '1 ~ 9 까지의 숫자를 6자리 입력해주세요.'
        },
        password: {
          required: "패스워드를 입력해주세요.",
        },
        passwordConfirm: {
          required : "패스워드 확인을 입력해주세요.",
          equalTo: "입력하신 패스워드와 일치하지 않습니다. 다시 확인해주세요."
        },
      },
      //에러메세지 위치지정
      errorPlacement: function (error, element) {
        if (element.attr("id") === "login-id") { //
          // "loginId" 필드에만 오류 메시지를 특정 위치에 삽입
          error.appendTo("#id-check-msg");
        }
        else if (element.attr("id") === "email") {
          error.appendTo("#email-check-msg");
        }
        else if (element.attr("id") === "verification-code") {
          error.appendTo("#verification-code-msg");
        }
        else {
          // 다른 필드는 기본 동작
          error.insertAfter(element);
        }
      },
    });
  });

