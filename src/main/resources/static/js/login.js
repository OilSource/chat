function login() {
    var userName=$("#username").val();
    var passWord=$("#password").val();
    /*获取当前输入的账号密码*/
    localStorage.setItem("userName",userName)
    localStorage.setItem("passWord",passWord)
    /*获取记住密码  自动登录的 checkbox的值*/
    var check1 = $("#check1").prop('checked');
    var check2 = $('#check2').prop('checked');
    localStorage.setItem("check1",check1);
    localStorage.setItem("check2",check2);
    $.ajax({
        type: 'POST',
        url: 'login',
        dataType: 'json',
        data: {
            username: userName,
            password: passWord
        },
        async: false,
        success: function (data) {
            if (data.status == 200) {
                window.location.href = "chatroom";
            } else {
                $("#alertContent").html('<p>' + data.msg + '</p>')
                $("#alert").click();
            }
        }
    });
}