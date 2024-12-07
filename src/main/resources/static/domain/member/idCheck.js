
//아이디 알림텍스트초기화
$('#login-id').on('input' , function (){
    if ($('#id-check-msg') !== '') {
      $('#id-ok').hide();
      $('#id-already').hide();
    }
});
//끝

// 아이디중복확인 시작
$('#check-duplicate-btn').on('click' , function (){
  const csrfToken = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰 값
  const csrfHeader = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더 이름
  const loginId = $('#login-id').val().trim();
  const isLoginIdValid = $(`#login-id`).valid();
  if (!isLoginIdValid) { //만약 로그인아이디가 공백이나 valid 조건에 맞지않은상태로 요청되었다고하면  isisLoginIdValid 가 false 이므로 false 면 종료된다.
    return;
  }
  $.ajax({
    type: 'POST',
    url: "/api/member/loginIdCheck", //Controller 주소
    contentType: 'application/json', // JSON 데이터로 전송
    data: JSON.stringify({loginId: loginId}),
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken); // CSRF 헤더 설정
    },
    success: function (response) {
      console.log(response);
      if (response.data.availability) { //아이디가 중복이라면
        $('#id-already').show(); //아이디중복입니다 텍스트 출력
        $('#id-ok').hide(); //사용가능한아이디입니다 텍스트 숨김
        $('#submit-btn').prop("disabled" , true); //submit 비활성화
      }
      else { //아이디가 중복이 아니라면
        $('#id-ok').show() //사용가능한아이디입니다 텍스트 출력
        $('#id-already').hide(); //아이디중복입니다 텍스트 숨김
        $('#submit-btn').prop("disabled" , false); //submit 활성화
      }
    },
    error: function () {
      alert("API 요청실패");
    }
  });
});
// 아이디중복확인 끝