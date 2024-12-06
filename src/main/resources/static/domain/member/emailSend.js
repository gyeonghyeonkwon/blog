// emailCheck 가 텍스트가 적혀있으면 "공백으로는 이메일을 전송할수없습니다." 라는 텍스트를 숨긴다
$('#email').on('input' , function (){
  if ($('#emailCheck') !== '') {
    $('#emailBlankMassage').hide();
  }
});
//이메일 인증번호 전송 시작
$('#emailSend').on('click', function () {
  const csrfToken = $('meta[name="_csrf"]').attr('content'); //csrf Token
  const csrfHeader = $('meta[name="_csrf_header"]').attr('content'); //csrf Token
  const email = $('#email').val().trim();
  if (email === '') {
    $('#emailBlankMassage').text('공백으로는 이메일을 전송할수없습니다.').show();
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
        alert("인증코드를 전송하였습니다. 이메일을확인해주세요.")
      }
    },
    error: function () {
      alert("이메일 전송이실패하였습니다.")
    }
  });
});
//끝