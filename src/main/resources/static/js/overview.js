$(function () {

    // 查询主机列表
    findInstances();

    function findInstances() {
        $.ajax({
            type: "get",
            data: {},
            dataType: 'json',
            url: "../instances",
            success: function (data) {
                if ('Success' === data.code) {
                    var instanceNum = data.data.instances.length;
                    $("#instanceNum").text(instanceNum);
                } else {
                    $('.portal-loading').hide();
                    if (data.message == null || data.message === "")
                        Ewin.showMsg('error', '查询主机列表失败！');
                    else
                        Ewin.showMsg('error', data.message);
                    $(".modal-backdrop").remove();
                }
            },
            error: function () {
                $('.portal-loading').hide();
                Ewin.showMsg('error', '查找云主机列表失败！');
                $(".modal-backdrop").remove();
            }
        });
    }


});
