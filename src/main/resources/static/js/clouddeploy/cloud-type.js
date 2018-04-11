$(function () {
    $('.dropdown-toggle').dropdown();
    $('.portal-loading').hide();

    var columns = [
        {
            field: 'checked',
            checkbox: true,
            formatter: stateFormatter
        },
        {
            field: 'id',
            title: 'ID'
        },
        {
            field: 'typeName',
            title: '云服务商类型'
        },

        {
            field: 'disable',
            title: '状态'
        },
        {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: operateEvents,
            formatter: operateFormatter
        }
    ];

    $("#cloudTypeTable").bootstrapTable({
        showRefresh: true,                  //是否显示刷新按钮
        showPaginationSwitch: true,       //是否显示选择分页数按钮
        columns: columns,
        pagination: true,//是否开启分页（*）
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 5,//每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页的行数（*）
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        search: true,
        toolbar: "#cloudTypeToolbar"
    });
    findCloudTypeList();


    function findCloudTypeList() {
        $('.portal-loading').show();
        $.ajax({
            type: "get",
            data: {},
            dataType: 'json',
            url: "../cloudTypes",
            success: function (data) {
                debugger
                if ('Success' === data.code)
                $("#cloudTypeTable").bootstrapTable('load', data.data.cloudTypes);
                $('.portal-loading').hide();
            },
            error: function () {
                $('.portal-loading').hide();
                Ewin.showMsg('error', '申请失败！');
                $(".modal-backdrop").remove();
            }
        });
    }

    function operateFormatter(value, row, index) {
        return [
            '<a class="RoleOfDisable fa fa-trash-o"></a>'
        ].join('');
    }


    function stateFormatter(value, row, index) {
        if (row.state == true)
            return {
                disabled: true,//设置是否可用
                checked: true//设置选中
            };
        return value;
    }
});
window.operateEvents = {
    'click .RoleOfDisable': function (e, value, row, index) {
        debugger
        var msg = '启用';
        var val = true;
        if ('1' === row.disable) {
            msg = '禁用';
            val = false;
        }
        Ewin.confirm({message: "确认要" + msg + "该云服务商类型吗？"}).on(function (flag) {
            if (flag === true) {
                //操作提示
                $('.portal-loading').show();
                $.ajax({
                    type: "get",
                    async: true,
                    data: {id: row.id, disable: val},
                    url: "../cloudTypes/update",
                    success: function (data, status) {
                        if (status == "success") {
                            Ewin.showMsg('success', msg + '该云服务商类型成功！');
                            $("#cloudTypeTable").bootstrapTable('refresh', {url: "../cloudTypes"});
                        } else {
                            Ewin.showMsg('error', msg + '该云服务商类型失败！');
                        }
                        $('.portal-loading').hide();
                    },
                    error: function () {
                        $('.portal-loading').hide();
                        Ewin.showMsg('error', msg + '该云服务商类型失败！');
                    }
                });
            }


            // if (!e) {
            //     return;
            // }
            // $.ajax({
            //     type: "post",
            //     url: "/api/DepartmentApi/Delete",
            //     data: { "": JSON.stringify(arrselections) },
            //     success: function (data, status) {
            //         if (status == "success") {
            //             toastr.success('提交数据成功');
            //             $("#tb_departments").bootstrapTable('refresh');
            //         }
            //     },
            //     error: function () {
            //         toastr.error('Error');
            //     },
            //     complete: function () {
            //
            //     }
            //
            // });
        });


        //警告消息提示，默认背景为橘黄色
        // toastr.warning("你有新消息了!");
        // //错误消息提示，默认背景为浅红色
        // toastr.error("你有新消息了!");
        //带标题的消息框
        // toastr.success("你有新消息了!","消息提示");
        // $.growl({ title: "消息标题", message: "消息内容!" });
        // $.growl.error({    title: "错误标题", message: "错误消息内容!" });
        // $.growl.notice({title: "提醒标题", message: "提醒消息内容!" });
        // $.growl.warning({title: "警告标题", message: "警告消息内容!" });
    }
};