function getLoginId() {
  return $('#login-id').val();
};

function getPassword() {
  return $('#password').val();
};

//로그인 버튼 클릭시
$('#login-submit-btn').on('click', function () {
  loginRequest();
});

//엔터키 누를시
$('#loginForm').on('keypress', function () {
  if (event.key === 'Enter' || event.keyCode === 13) {
    loginRequest();
  }
});

function loginRequest() {
  const loginId = getLoginId();
  const password = getPassword();

  if (!$('#loginForm').valid()) { //form 유효성검사
    return;
  }
  $.ajax({
    url: '/api/members/signIn',
    type: 'POST',
    contentType: 'application/json',

    data: JSON.stringify({
      loginId: loginId,
      password: password
    }),
    success: function (response) {
      // JWT 토큰을 로컬스토리지나 쿠키에 저장
      localStorage.setItem('token', response.data.accessToken);
      console.log(response);
      alert('로그인 성공');
      console.log("토큰:", response.data.accessToken)
      console.log(loginId)
      location.href = '/members/postMain';
    },
    error: function (xhr) {
      alert('아이디 및 패스워드가 일치하지 않습니다.');
    }
  });
}
