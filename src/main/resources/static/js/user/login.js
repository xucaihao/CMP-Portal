$(function () {
    $("#btn_login").click(function () {
        debugger
        var account = $("#userName").val();
        var password = $("#password").val();
        $.ajax({
            url: "../login",
            data: {
                password: password,
                account: account,
            },
            type: "get",
            success: function (data, status) {
                debugger
                if (status === "success") {
                    window.location.href = "../index"
                }
            },
            error: function () {
                debugger
            }
        })
        ;
    })
});