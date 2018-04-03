$(function () {
    $('.portal-loading').hide();
    //模拟数据填充bootstrap-table表
    var data = {
        "data": [
            {
                "id": "1",
                "address": "127.0.0.1",
                "state": "未绑定",
                "resource": "-----",
                "user": "admin"
            },
            {
                "id": "2",
                "address": "127.0.0.2",
                "state": "已绑定",
                "resource": "ali_instance",
                "user": "admin"
            },
            {
                "id": "3",
                "address": "127.0.0.3",
                "state": "未绑定",
                "resource": "-----",
                "user": "admin"
            },
            {
                "id": "4",
                "address": "127.0.0.4",
                "state": "未绑定",
                "resource": "-----",
                "user": "admin"
            },
            {
                "id": "5",
                "address": "127.0.0.5",
                "state": "未绑定",
                "resource": "-----",
                "user": "admin"
            },
            {
                "id": "6",
                "address": "127.0.0.5",
                "state": "未绑定",
                "resource": "-----",
                "user": "admin"
            }
        ]
    };
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
            field: 'address',
            title: 'IP'
        },
        {
            field: 'state',
            title: '状态'
        },
        {
            field: 'resource',
            title: '绑定资源'
        },
        {
            field: 'user',
            title: '所有者'
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: operateEvents,
            formatter: operateFormatter
        }
    ];
    debugger
    $.ajax({
        type: "get",
        data: {},
        dataType: 'json',
        url: "../floatingIp/findFloatingIpList",
        success: function (data) {
            debugger
            $("#floatingIpTable").bootstrapTable({
            showRefresh: true,                  //是否显示刷新按钮
            showPaginationSwitch: true,       //是否显示选择分页数按钮
            columns: columns,
            data: data.data,
            pagination: true,//是否开启分页（*）
            pageNumber: 1,//初始化加载第一页，默认第一页
            pageSize: 5,//每页的记录行数（*）
            pageList: [5, 10, 15],//可供选择的每页的行数（*）
            sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
            search: true,
            toolbar: "#floatingIpToolbar",
        });
        },
        error: function () {
            debugger
            for (var i = 0; i < 2000000000; i++) ;
            $('.portal-loading').hide();
            Ewin.showMsg('error', '申请失败！');
            $(".modal-backdrop").remove();
        }
    });


    $('btn_applyFloatingIp').click(function () {

    })

    $('#bandwidth').slider({
        formatter: function (value) {
            return value;
        }
    }).on('slide', function (slideEvt) {
    }).on('change', function (e) {
        console.info(e.value.oldValue + '--' + e.value.newValue);
    });


    $('#btn_applyFloatingIp').click(function () {
        $('.portal-loading').show();
        $('#applyFloatingIpModal').hide();

        debugger
        $.ajax({
            type: "get",
            url: "http://www.baidu.com",
            success: function (data, status) {
                if (status == "success") {
                    $("#tb_departments").bootstrapTable('refresh');
                }
                $(".modal-backdrop").remove();
            },
            error: function () {
                for (var i = 0; i < 2000000000; i++) ;
                $('.portal-loading').hide();
                Ewin.showMsg('error', '申请失败！');
                $(".modal-backdrop").remove();
            },
            complete: function () {

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
    })
    $("#btn_applyFloatingIp").bind('hide.bs.modal', function () {
        $(".modal-backdrop").remove();
    })
    //通过ajax请求数据
    // $.ajax({
    //     type: "GET",
    //     url: "/floatingIp/findFloatingIpList",
    //     dataType: "json",
    //     success: function (data) {
    //         $("#floatingIpTable").bootstrapTable('load', data);
    //     },
    //     error: function () {
    //         alert("错误");
    //     }
    // });
    function operateFormatter(value, row, index) {
        if (row !== undefined) {
            return [
                row.resource === '' || row.resource === '-----' || row.resource === '-' ?
                    ('<a class="RoleOfBand fa fa-link " ></a>&nbsp;&nbsp;&nbsp;')
                    : '<a class="RoleOfBand fa fa-unlink " ></a>&nbsp;&nbsp;&nbsp;',
                '<a class="RoleOfEdit  fa fa-edit"></a>&nbsp;&nbsp;&nbsp;',
                '<a class="RoleOfDelete fa fa-recycle" ></a>'
            ].join('');
        } else {
            return [
                '<a class="RoleOfBand fa fa-unlink " ></a>&nbsp;',
                '<a class="RoleOfEdit  fa fa-edit"></a>&nbsp;',
                '<a class="RoleOfDelete fa fa-recycle" ></a>'
            ].join('');
        }

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
    'click .RoleOfDelete': function (e, value, row, index) {
        debugger
        Ewin.confirm({message: "确认要回收选中的弹性公网IP吗？"}).on(function (flag) {
            if (flag === true) {
                //操作提示
                $('.portal-loading').show();
                debugger
                $.ajax({
                    type: "get",
                    url: "http://www.baidu.com",
                    success: function (data, status) {
                        if (status == "success") {
                            $("#tb_departments").bootstrapTable('refresh');
                        }
                    },
                    error: function () {
                        for (var i = 0; i < 2000000000; i++) ;
                        $('.portal-loading').hide();
                        Ewin.showMsg('error', '弹性公网IP回收失败！');
                    },
                    complete: function () {

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
    },
    'click .RoleOfBand': function (e, value, row, index) {
        $("#updateFloatingIpModal").modal('show');
    },
    'click .RoleOfEdit': function (e, value, row, index) {
        $("#updateFloatingIpModal").modal('show');
        $('#bandwidth1').slider({
            formatter: function (value) {
                return value;
            }
        }).on('slide', function (slideEvt) {
        }).on('change', function (e) {
            console.info(e.value.oldValue + '--' + e.value.newValue);
        });
    }
};