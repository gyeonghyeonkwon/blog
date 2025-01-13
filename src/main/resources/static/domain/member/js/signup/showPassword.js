
//비밀번호 표시
$(`.checkbox`).on("click", function () {
  const target = $(this).data("target");
  const checked = $(this).is(":checked"); //체크박스 체크여부
  const password = $(target);
  if (checked) {
    // 체크박스가 체크되어 있으면 type을 'text'로 변경
    password.attr("type", "text");
  } else {
    // 체크박스가 체크되어 있지 않으면 type을 'password'로 변경
    password.attr("type", "password");
  }
});