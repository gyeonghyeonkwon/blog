/**
 * 회원가입버튼 클릭하면 실행
 */
$('#submit-btn').on('click', function () {
  const csrfToken = $('meta[name="_csrf"]').attr('content'); //csrf Token
  const csrfHeader = $('meta[name="_csrf_header"]').attr('content'); //csrf Token
  const loginId = $('#login-id').val();
  const realName = $('#real-name').val();
  const email = $('#email').val();
  const verificationCode = $('#verification-code').val();
  const password = $('#password').val();
  const passwordConfirm = $('#password-confirm').val();

  const requestData = {
    loginId : loginId ,
    realName : realName ,
    email : email ,
    verificationCode : verificationCode ,
    password : password ,
    passwordConfirm : passwordConfirm
  };
  if (!$('#form').valid()) { //form 유효성검사
    return;
  }
  //중복체크를 클릭해야만 id-ok 메세지가보인다. id-ok 가 보이지 않으면 중복체크를 하지않았다는것이다.
  if ($('#id-ok').css('display') === 'none') {
    alert('아이디중복체크를진행해주세요.')
    return;
  }
  $.ajax({
    type: 'POST',
    url: '/api/member/signup',
    contentType: 'application/json',
    data: JSON.stringify(requestData), //데이터 요청
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken);
    },
    success: function (data) {
      console.log(data);
      location.href = '/member/login'; //로그인화면
      alert('회원가입에 성공하였습니다. 로그인을 진행해주세요.');
    },
    error: function () {
      alert("회원가입이 실패하였습니다.");
    }
  });
});