
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
          pattern: /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/,
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
          pattern: '유효하지 않은 이메일 주소입니다.'
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
        switch (element.attr("id")) {
          case "login-id": //input id값
            error.appendTo("#id-check-msg"); //에러메세지 p 태그 id값
            return;

          case "real-name":
            error.appendTo("#real-name-check-msg");
            return;

          case "email":
            error.appendTo("#email-check-msg");
            return;

          case "verification-code":
            error.appendTo("#verification-code-msg");
            return;

          case "password":
            error.appendTo("#password-check1-msg");
            return;

          case "password-confirm":
            error.appendTo("#password-check2-msg");
            return;

          default :
            error.insertAfter(element);
        }
      },
      highlight: function (element) {
        // 에러 발생 시 인풋에 클래스 추가
        $(element).addClass("input-invalid");
      },
      unhighlight: function (element) {
        // 에러가 해결되면 클래스 제거
        $(element).removeClass("input-invalid");
      }
    });
  });

