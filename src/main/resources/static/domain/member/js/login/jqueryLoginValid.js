jQuery(function () {
  const form = $('#loginForm');
  form.validate({
    rules: {
      loginId: {
        required: true,
      },
      password: {
        required: true,
      }
    },
    messages: {
      loginId: {
        required: '아이디를 입력해주세요.',
      },
      password: {
        required: '패스워드를 입력해주세요.'
      }
    },
    errorPlacement: function (error, element) {
      error.insertAfter(element);
    },
    highlight: function (element) {
      $(element).addClass('input-invalid');
    },
    unhighlight: function (element) {
      $(element).removeClass('input-invalid');
    }
  });
});



