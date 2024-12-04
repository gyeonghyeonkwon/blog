
//아이디 알림텍스트초기화
$('#loginId').on('input', function () {
  const  loginId = $('#loginId').val().trim();
  $('#duplicateCheck').prop("disabled" , false);
  if (loginId === '' || loginId.length < 5 || loginId.length < 12 || loginId.length > 5 || loginId.length > 12){
    $('#idCheck').text('');
    $('#id-ok').css("display" , "none");
    $('#id-already').css("display" , "none");
  }
  if (loginId.length < 5 || loginId.length > 12) {
    $('#duplicateCheck').prop("disabled" , true);
  }
});
//끝

// 아이디중복확인 시작
$('#duplicateCheck').on('click' , function (){
  const csrfToken = $('meta[name="_csrf"]').attr('content'); // CSRF 토큰 값
  const csrfHeader = $('meta[name="_csrf_header"]').attr('content'); // CSRF 헤더 이름
  const loginId = $('#loginId').val().trim();
  if (loginId === '') {
    $("#idCheck").text('아이디를 입력하세요!');
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
      if (response.availability) {
        $('#id-already').css("display" , "inline-block");
        $('#id-ok').css("display" , "none");
        submitButton.disabled = true;
        $('#duplicateCheck').prop("disabled" , false); //중복검사버튼 활성화
      }
      else {
        $('#id-ok').css("display" , "inline-block")
        $('#id-already').css("display" , "none");
        submitButton.disabled = false;
        $('#duplicateCheck').prop("disabled" , true); //중복검사버튼 비활성화 , 사용가능한아이디이기떄문에 중복검사를 할 필요가 없음.
      }
    },
    error: function () {
      alert("API 요청실패");
    }
  });
});
// 아이디중복확인 끝