
function getCsrfHeader() {
  const csrfToken = $('meta[name="_csrf"]').attr('content');
  const csrfHeader = $('meta[name="_csrf_header"]').attr('content');
  return {csrfToken , csrfHeader};
};

function getEmail() {
  return  $('#email').val();
};

function isEmailValidation() {
  return $(`#email`).valid();
};
//이메일 인증번호 전송 시작
$('#send-code-btn').on('click', function () {
  const csrfHeader = getCsrfHeader(); //csrf Token
  const email = getEmail();
  const isEmailValid = isEmailValidation();

  if (!isEmailValid) {
    return;
  }

  $.ajax({
    type: 'POST',
    url: '/api/member/mail-send',
    contentType: 'application/json',
    data: JSON.stringify({email: email}), //데이터 요청
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeader.csrfHeader, csrfHeader.csrfToken); // CSRF 헤더 설정
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