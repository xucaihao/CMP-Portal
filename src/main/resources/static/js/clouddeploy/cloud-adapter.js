var visibility = 'PUBLIC';
$(function () {
    $('.dropdown-toggle').dropdown();
    $('.portal-loading').hide();
    $('#ipDiv').hide();
    $('#portDiv').hide();


    var columns = [
        {
            field: 'checked',
            checkbox: true,
            formatter: stateFormatter
        },
        {
            field: 'adapterName',
            title: '适配器名'
        },
        {
            field: 'adapterProtocol',
            title: '协议'
        },
        {
            field: 'adapterIp',
            title: '服务器地址'
        },
        {
            field: 'adapterPort',
            title: '服务器端口'
        },
        {
            field: 'cloudType',
            title: '云类型'
        },
        {
            field: 'description',
            title: '描述'
        },
        {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: operateEvents,
            formatter: operateFormatter
        }
    ];
    $("#cloudAdapterTable").bootstrapTable({
        showRefresh: true,                  //是否显示刷新按钮
        showPaginationSwitch: true,       //是否显示选择分页数按钮
        columns: columns,
        pagination: true,//是否开启分页（*）
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 5,//每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页的行数（*）
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        search: true,
        toolbar: "#cloudAdapterToolbar",
    });

    findCloudAdapterList();

    $('#btn_modifyCloudAdapter').click(function () {
        debugger;
        $("#modifyCloudAdapterModal").modal('hide');
        $('.portal-loading').show();
        var data = {
            "cloudType": $("#modifyCloudType").val(),
            "adapterIp": $("#modifyAdapterIp").val(),
            "adapterPort": $('#modifyAdapterPort').val()
        };

        $.ajax({
            type: "post",
            async: true,
            data: data,
            url: "../cloudAdapters/update",
            success: function (data, status) {
                debugger
                if (data.code === 'Success') {
                    Ewin.showMsg('success', '修改成功！');
                    findCloudAdapterList();
                } else {
                    Ewin.showMsg('error', data.des);
                }
                $('.portal-loading').hide();
            },
            error: function () {
                Ewin.showMsg('error', '修改失败！');
                $('.portal-loading').hide();
            }
        });


    });
});

window.operateEvents = {
    'click .RoleOfEdit': function (e, value, row, index) {
        $("#modifyCloudType").val(row.cloudType);
        $('#modifyAdapterName').val(row.adapterName);
        $('#modifyAdapterIp').val(row.adapterIp);
        $('#modifyAdapterPort').val(row.adapterPort);
        $("#modifyCloudAdapterModal").modal();

    }
};

function findCloudAdapterList() {
    $('.portal-loading').show();
    $.ajax({
        type: "get",
        data: {},
        dataType: 'json',
        url: "../cloudAdapters",
        success: function (data) {
            if ('Success' == data.code) {
                data.data.cloudAdapters.forEach(function (value, index, array) {
                    debugger;
                    if (value.adapterProtocol === 'default') {
                        value.adapterProtocol = '-----'
                    }

                    if (value.adapterIp === 'default') {
                        value.adapterIp = '-----';
                    }


                    if (value.adapterPort === 'default') {
                        value.adapterPort = '-----';
                    }
                });
                $("#cloudAdapterTable").bootstrapTable('load', data.data.cloudAdapters);
            }

            $('.portal-loading').hide();
        },
        error: function () {
            $('.portal-loading').hide();
            Ewin.showMsg('error', '查找云适配器服务商列表失败！');
            $(".modal-backdrop").remove();
        }
    });
}

function stateFormatter(value, row, index) {
    if (row.state == true)
        return {
            disabled: true,//设置是否可用
            checked: true//设置选中
        };
    return value;
}

function operateFormatter(value, row, index) {
    return [
        '<a class="RoleOfEdit fa fa-edit" style="text-decoration: none" title="修改"></a>'
    ].join('');
}
