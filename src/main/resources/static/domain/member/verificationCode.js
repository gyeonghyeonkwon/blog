
//이메일이 바뀌었을경우 동작, 이메일이 바뀌면 인증코드도 바뀌어야하기때문에 인증코드를 초기화한다.
$('#email').on('change', function (){
      $('#verification-code').val(''); //인증코드입력란 초기화
      $('#verification-success-msg').hide();
      $('#verification-fail-msg').hide();
});

$('#verification-code').on('input' , function () {
  const csrfToken = $('meta[name="_csrf"]').attr('content');
  const csrfHeader = $('meta[name="_csrf_header"]').attr('content');
  const email = $('#email').val().trim(); //이메일 요청데이터
  const verificationCode = $('#verification-code').val().trim(); //인증번호 요청데이터
  const isEmailValid = $(`#email`).valid();
  const isVerificationCode = $(`#verification-code`).valid();
  const requestData = { //요청데이터
    email : email,
    verificationCode : verificationCode,
  }
  if ($('#verification-code-msg') !== '') { //제이쿼리 벨리테이션이 메세지가 출력된다면
    $('#verification-success-msg').hide(); // 인증에 성공하였습니다 메세지를 숨김
    $('#verification-fail-msg').hide(); // 인증에 실패하였습니다 메세지를 숨김
  }
  if (!isEmailValid || !isVerificationCode) { //만약 이메일입력란이 공백이거나 이메일형식이 아닌 텍스트(test.test.com)로 요청되었다고하면  isEmailValid 가 false 이므로 false 면 종료된다.
    return;
  }
  $.ajax({
    type: 'POST',
    url: '/api/member/verificationCode',
    contentType: 'application/json',
    data: JSON.stringify(requestData), //데이터 요청
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken); // CSRF 헤더 설정
    },
    success: function (response) {
      console.log(response);
      console.log(response.data);
      if (response.data) { // 인증성공
        $('#verification-success-msg').show(); //인증에 성공하였습니다. 출력
        $('#verification-fail-msg').hide(); //인증에 실패하였습니다. 숨김
        $('#submit-btn').prop('disabled' , false); //submit 버튼을 활성화
      }
      else { //인증 실패
        $('#verification-fail-msg').show(); //인증에 실패하였습니다. 출력
        $('#verification-success-msg').hide(); //인증에 성공하였습니다. 숨김
        $('#submit-btn').prop('disabled' , true); //submit 버튼을 비활성화
      }
    },
    error: function () {
      alert("API 요청실패 , 서버확인필요");
    }
  });
});