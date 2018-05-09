$(function(){
    //菜单点击
    //J_iframe
    $(".J_menuItem").on('click',function(){
        var url = $(this).attr('href');
        $("#J_iframe").attr('src',url);
        return false;
    });

    findUserName();

    function findUserName() {
        $.ajax({
            type: "get",
            data: {},
            dataType: 'json',
            url: "../disks",
            success: function (data) {
                if ('Success' === data.code) {
                    $("#disksTable").bootstrapTable('load', data.data.disks);
                    $('.portal-loading').hide();
                } else {
                    $('.portal-loading').hide();
                    if (data.msg == null || data.msg === "")
                        Ewin.showMsg('error', '查询硬盘列表失败！');
                    else
                        Ewin.showMsg('error', data.msg);
                    $(".modal-backdrop").remove();
                }
            },
            error: function () {
                $('.portal-loading').hide();
                Ewin.showMsg('error', '查询硬盘列表失败！');
                $(".modal-backdrop").remove();
            }
        });
    }
});