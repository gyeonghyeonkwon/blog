
//아이디 알림텍스트초기화
$('#login-id').on('input', function () {
  const  loginId = $('#login-id').val().trim(); //아이디 입력란
  const regexId = loginIdPattern; //로그인 아이디 패턴
  $('#check-duplicate-btn').prop("disabled" , false)
  if (loginId === '' || loginId.length < 5 || loginId.length < 12 || loginId.length > 5 || loginId.length > 12){
    $('#id-check-msg').text('');
    $('#id-ok').css("display" , "none");
    $('#id-already').css("display" , "none");
  }
  if (loginId.length < 5 || loginId.length > 12) {
    $('#check-duplicate-btn').prop("disabled" , true);
  }
  if (!regexId.exec(loginId)) {
    $('#check-duplicate-btn').prop("disabled" , true)
  }
});
//끝

// 아이디중복확인 시작
$('#check-duplicate-btn').on('click' , function (){
  const csrfToken = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰 값
  const csrfHeader = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더 이름
  const loginId = $('#login-id').val().trim();
  if (loginId === '') {
    $("#id-check-msg").text('아이디를 입력하세요!');
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
        $('#id-already').css("display" , "inline-block");
        $('#id-ok').css("display" , "none");
        $('#submitButton').prop("disabled" , true);
      }
      else { //아이디가 중복이 아니라면
        $('#id-ok').css("display" , "inline-block")
        $('#id-already').css("display" , "none");
        $('#submitButton').prop("disabled" , false);
      }
    },
    error: function () {
      alert("API 요청실패");
    }
  });
});
// 아이디중복확인 끝