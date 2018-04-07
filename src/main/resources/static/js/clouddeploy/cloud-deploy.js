$(function () {
    $('.dropdown-toggle').dropdown();
    $('.portal-loading').hide();
    //模拟数据填充bootstrap-table表
    var data = {
        "data": [
            {
                "name": "阿里云-1",
                "type": "ali",
                "state": "可用"
            }
        ]
    };


    findCloudDeployList();


    // $("#cloudDeployTable").bootstrapTable({
    //     showRefresh: true,                  //是否显示刷新按钮
    //     showPaginationSwitch: true,       //是否显示选择分页数按钮
    //     columns: columns,
    //     data: data.data,
    //     pagination: true,//是否开启分页（*）
    //     pageNumber: 1,//初始化加载第一页，默认第一页
    //     pageSize: 5,//每页的记录行数（*）
    //     pageList: [5, 10, 15],//可供选择的每页的行数（*）
    //     sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
    //     search: true,
    //     toolbar: "#floatingIpToolbar"
    // });


    $('#btn_addCloudDeploy').click(function () {
        debugger
        $("#addCloudDeployModal").modal('hide');
        $('.portal-loading').show();
        $.ajax({
            type: "post",
            async: true,
            data: {
                "type": $('#type option:selected').val(),
                "name": $("#name").val(),
                "ak": $("#ak").val(),
                "sk": $("#sk").val()
            },
            url: "../cloudDeploy/addCloudDeploy",
            success: function (data, status) {
                debugger
                if (status == "success") {
                    $("#cloudDeployTable").bootstrapTable('refresh', {url: "../cloudDeploy/findCloudDeployList"});
                }
                $('.portal-loading').hide();
            },
            error: function () {
                Ewin.showMsg('error', '申请失败！');
                $('.portal-loading').hide();
            }
        });
    })

    var columns = [
        {
            field: 'checked',
            checkbox: true,
            formatter: stateFormatter
        },
        {
            field: 'name',
            title: '云运营商'
        },
        {
            field: 'type',
            title: '云类型'
        },

        {
            field: 'state',
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
    $("#cloudDeployTable").bootstrapTable({
        showRefresh: true,                  //是否显示刷新按钮
        showPaginationSwitch: true,       //是否显示选择分页数按钮
        columns: columns,
        pagination: true,//是否开启分页（*）
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 5,//每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页的行数（*）
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        search: true,
        toolbar: "#cloudDeployToolbar"
    });

    function findCloudDeployList() {
        $('.portal-loading').show();
        $.ajax({
            type: "get",
            data: {},
            dataType: 'json',
            url: "../cloudDeploy/findCloudDeployList",
            success: function (data) {
                debugger
                $("#cloudDeployTable").bootstrapTable('load', data.data);
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
            '<a class="RoleOfDelete fa fa-trash-o" onclick="deleteCloudDeploy(row.id)"></a>'
        ].join('');
    }

    //批量删除
    $('#btn_deleteCloudDeploy').click(function () {
        debugger
        var rows = $("#cloudDeployTable").bootstrapTable("getSelections");
        if (rows.length === 0) {
            Ewin.showMsg('warning', '请选中要删除的云');
            return;
        }

        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
        }

        Ewin.confirm({message: "确认要删除选中的云吗？数量：" + rows.length}).on(function (flag) {
            if (flag === true) {
                $('.portal-loading').show();
                $.ajax({
                    type: "get",
                    async: true,
                    traditional: false,
                    data: {ids: ids},
                    url: "fd oy",
                    success: function (data, status) {
                        debugger
                        if (status == "success") {
                            Ewin.showMsg('success', '删除成功！');
                            $("#cloudDeployTable").bootstrapTable('refresh', {url: "../cloudDeploy/findCloudDeployList"});
                        } else {
                            Ewin.showMsg('error', '删除云失败！');
                        }
                        $('.portal-loading').hide();
                    },
                    error: function () {
                        for (var x = 0; x < 4000000000; x++);
                        $('.portal-loading').hide();
                        Ewin.showMsg('error', '删除云失败！');
                    }
                });
            }
        });
    });

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
        Ewin.confirm({message: "确认要删除该云吗？"}).on(function (flag) {
            if (flag === true) {
                //操作提示
                $('.portal-loading').show();
                var ids = [];
                ids.push(row.id);
                $.ajax({
                    type: "get",
                    async: true,
                    data: {ids: ids},
                    url: "../cloudDeploy/deleteCloudDeploy",
                    success: function (data, status) {
                        if (status == "success") {
                            Ewin.showMsg('success', '删除成功！');
                            $("#cloudDeployTable").bootstrapTable('refresh', {url: "../cloudDeploy/findCloudDeployList"});
                        } else {
                            Ewin.showMsg('error', '删除云失败！');
                        }
                        $('.portal-loading').hide();
                    },
                    error: function () {
                        $('.portal-loading').hide();
                        Ewin.showMsg('error', '删除云失败！');
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