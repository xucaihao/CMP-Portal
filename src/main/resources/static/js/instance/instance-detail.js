$(function () {
    $('.portal-loading').hide();
    findInstanceAttribute()

    function findInstanceAttribute() {
        $('.portal-loading').show();
        var cloudId = sessionStorage.cloudId;
        var regionId = sessionStorage.regionId;
        var instanceId = sessionStorage.instanceId;
        $.ajax({
            url: "../instances/" + instanceId,
            data: {
                cloudId: cloudId,
                regionId: regionId
            },
            dataType: "json",
            type: "GET",
            success: function (data) {
                if (data.code === 'Success') {
                    $('.portal-loading').hide();

                } else {
                    $('.portal-loading').hide();
                    Ewin.showMsg('error', '查找云主机失败！');
                    $(".modal-backdrop").remove();
                }
            },
            error: function () {
                $('.portal-loading').hide();
                Ewin.showMsg('error', '查找云主机失败！');
                $(".modal-backdrop").remove();
            }
        });
    }

});