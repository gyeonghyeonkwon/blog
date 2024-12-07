
//이메일 인증번호 전송 시작
$('#send-code-btn').on('click', function () {
  const csrfToken = $('meta[name="_csrf"]').attr('content'); //csrf Token
  const csrfHeader = $('meta[name="_csrf_header"]').attr('content'); //csrf Token
  const email = $('#email').val().trim();
  const isEmailValid = $(`#email`).valid(); //이메일이 맞는지 , 아닌지 확인
  if (!isEmailValid) { //이메일이 valid 조건에 맞지않을경우에 isEmailValid 는 false 이므로 종료
    $('#verify-code-btn').prop('disabled' , true);
    $('#verification-code').prop('disabled' , true);
    return;
  }
  if (isEmailValid) {
    $('#verify-code-btn').prop('disabled' , false);
    $('#verification-code').prop('disabled' , false);
    return;
  }
  $.ajax({
    type: 'POST',
    url: '/api/member/mailSend',
    contentType: 'application/json',
    data: JSON.stringify({email: email}), //데이터 요청
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken); // CSRF 헤더 설정
    },
    success: function (data) {
      console.log(data);
      if (data) {
        alert("인증코드를 전송하였습니다. 이메일을확인해주세요.");
      }
    },
    error: function () {
      alert("이메일 전송이 실패하였습니다.");
    }
  });
});
//끝