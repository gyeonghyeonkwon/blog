//이메일 중복체크
let timer;
$('#email').on('input', function () {
  const email = getEmail();
  const csrfHeader = getCsrfHeader();
  const isEmailValid = isEmailValidation();
  if ($('#email-check-msg') !== '') {
    $('#email-ok').hide();
    $('#email-already').hide();
  }
  if (!isEmailValid) {
    return;
  }
  if (timer) {
    clearTimeout(timer);
  }
  //디바운싱 설정
  timer = setTimeout(function () {
    $.ajax({
      type: 'POST',
      url: '/api/members/check-email',
      contentType: 'application/json',
      data: JSON.stringify({email: email}), //데이터 요청
      beforeSend: function (xhr) {
        xhr.setRequestHeader(csrfHeader.csrfHeader, csrfHeader.csrfToken); // CSRF 헤더 설정
      },
      success: function (response) {
        console.log(response);
        if (response.data.isEmailAvailable) {
          $('#email-already').hide();
          $('#email-ok').show();
          $('#send-code-btn').prop('disabled', false);
          $('#verification-code').prop('disabled', false);
        } else {
          $('#email-already').show();
          $('#email-ok').hide();
          $('#send-code-btn').prop('disabled', true);
          $('#verification-code').prop('disabled', true)
        }
      },
      error: function () {
        alert("API 요청실패");
      }
    });
  }, 300);
});