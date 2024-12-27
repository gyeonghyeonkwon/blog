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
    loginId: loginId,
    realName: realName,
    email: email,
    verificationCode: verificationCode,
    password: password,
    passwordConfirm: passwordConfirm
  };
  if (!$('#form').valid()) { //form 유효성검사
    return;
  }
  /**
   * 사용가능한 아이디입니다 ,
   * 다른아이디를사용해주세요 메세지가 보이지않고
   * 아이디중복검사를 실시하세요 메세지만보인다면
   * 실행된다.
   */
  if ($('#id-check-msg').css('display') !== 'none' &&
      $('#id-already').css('display') === 'none'
      && $('#id-ok').css('display') === 'none') {
    alert('아이디 중복검사를 진행해주세요.');
    return;
  }
  // 다른아이디를사용해주세요 메세지만 보인다면 실행된다.
  if ($('#id-already').css('display') !== 'none') {
    alert('아이디를 다시 확인해주세요.');
    return;
  }
  // 입력하신 이메일을 사용할 수 없습니다 메세지만 보인다면 실행된다.
  if ($('#email-already').css('display') !== 'none') {
    alert('이메일을 다시 확인해주세요.');
    return;
  }
  //인증에 실패하였습니다 메세지만 보이면 실행된다.
  if ($('#verification-fail-msg').css('display') !== 'none') {
    alert('인증번호를 다시 확인해주세요.');
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
      alert("회원가입을 진행할 수 없습니다.");
    }
  });
});