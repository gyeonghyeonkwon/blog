const loginIdPattern = /^[a-z0-9](?=.*[a-z])(?=.*\d)[a-z0-9]{5,12}$/; //로그인아이디 패턴 , jqueryValid.js 에서도 사용
// const verificationPattern = /^[0-9]*$/;

  jQuery(function (){
    const form = $('#form');
    form.validate({
      rules: {
        loginId: {
          // required: true,  //id중복확인 ajax 코드랑 충돌되어 주석처리
          minlength: 5,
          maxlength: 12,
          pattern: loginIdPattern,
        },
        email: {
          required: true,
          email: true,
        },
        verificationCode: {
          required: true,
          minlength: 6,
        }
      },
      messages: {
        loginId: {
          // required: '아이디를입력하세요.',
          minlength: '최소 5글자이상입력하세요.',
          maxlength: '최대 12글자이상의 아이디를 생성 할 수없습니다.',
          pattern:'숫자로만 입력할 수 없습니다. 소문자 + 숫자 로만 입력가능합니다.',
        },
        email: {
          required: '이메일을 입력해주세요.',
          email: '올바른 이메일 형식으로 입력하세요.',
        },
        verificationCode: {
          required: '인증번호를 입력해주세요.',
          minlength: '1 ~ 9까지의 숫자 6자리를 입력해주세요.',
        },
      },
      //에러메세지 위치지정
      errorPlacement: function (error, element) {
        if (element.attr("id") === "login-id") { //
          // "loginId" 필드에만 오류 메시지를 특정 위치에 삽입
          error.appendTo("#id-check-msg");
        }
        else if (element.attr("id") === "email") {
          error.appendTo("#emailCheck");
        }
        else if (element.attr("id") === "verificationCode") {
          error.appendTo("#authCodeCheck");
        }
        else {
          // 다른 필드는 기본 동작
          error.insertAfter(element);
        }
      },
    });
  });

