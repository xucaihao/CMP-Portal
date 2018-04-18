$(function () {
    $("#btn_login").click(function () {
        debugger
        var account = $("#userName").val();
        var password = $("#password").val();
        $.ajax({
            url: "../login",
            data: {
                password: password,
                account: account
            },
            type: "get",
            success: function (data) {
                if ('Success' === data.code) {
                    window.location.href = "../index"
                } else {
                    // Ewin.showMsg('error', '用户名密码错误！');
                }
            },
            error: function () {
                // Ewin.showMsg('error', '用户名密码错误！');
            }
        })
        ;
    })
});