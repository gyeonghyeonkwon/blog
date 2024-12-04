jQuery(function (){
  const form = $('#form');
  form.validate({
    rules: {
      loginId: {
        // required: true,  //id중복확인 ajax 코드랑 충돌되어 주석처리
        minlength: 5,
        maxlength: 12,
      }
    },
    messages: {
      loginId: {
        // required: '아이디를입력하세요.',
        minlength: '최소 5글자이상입력하세요.',
        maxlength: '최대 12글자이상입력하세요.',
      }
    },
    //에러메세지 위치지정
    errorPlacement: function (error, element) {
      if (element.attr("id") === "loginId") { //
        // "loginId" 필드에만 오류 메시지를 특정 위치에 삽입
        error.appendTo("#idCheck");
      }
      else if (element.attr("id") === "email") {
        error.appendTo("#emailCheck");
      }
      else if (element.attr("id") === "code") {
        error.appendTo("#authCodeCheck");
      }
      else {
        // 다른 필드는 기본 동작
        error.insertAfter(element);
      }
    },
  });
});