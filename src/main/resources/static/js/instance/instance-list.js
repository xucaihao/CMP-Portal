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
            field: 'instanceId',
            title: 'ID/云主机名',
            formatter: idNameFormatter
        },
        {
            field: 'status',
            title: '状态',
            formatter: statusFormatter
        },
        {
            field: 'zoneId',
            title: '可用区'
        },
        {
            field: 'instanceType',
            title: '主机类型',
            formatter: instanceTypeFormatter
        },
        {
            field: 'memory',
            title: '配置',
            formatter: memoryFormatter
        }
        // {
        //     field: 'cloudPort',
        //     title: 'IP地址'
        // },
        // {
        //     field: 'status',
        //     title: '主机计费模式'
        // },
        // {
        //     field: 'status',
        //     title: '网络计费模式'
        // },
        // {
        //     field: 'operate',
        //     title: '操作',
        //     align: 'center',
        //     events: operateEvents,
        //     formatter: operateFormatter
        // }
    ];
    $("#instancesTable").bootstrapTable({
        showRefresh: true,                  //是否显示刷新按钮
        showPaginationSwitch: true,       //是否显示选择分页数按钮
        columns: columns,
        pagination: true,//是否开启分页（*）
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 5,//每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页的行数（*）
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        search: true,
        toolbar: "#instancesToolbar"
    });

    // 查询主机列表
    findInstances();

    function findInstances() {
        debugger
        var instances = [{
            instanceId: "111",
            cloudId: "cloudId",
            instanceName: "2222",
            status: "RUNNING",
            instanceType: "instanceType",
            osName: "osName"
        },
            {
                instanceId: "111",
                cloudId: "cloudId",
                instanceName: "2222",
                status: "rebooting",
                instanceType: "instanceType",
                osName: "osName"
            },
            {
                instanceId: "111",
                cloudId: "cloudId",
                instanceName: "2222",
                status: "Starting",
                instanceType: "instanceType",
                osName: "osName"
            },
            {
                instanceId: "111",
                cloudId: "cloudId",
                instanceName: "2222",
                status: "pending",
                instanceType: "instanceType",
                osName: "osName"
            },
            {
                instanceId: "111",
                cloudId: "cloudId",
                instanceName: "2222",
                status: "stopping",
                instanceType: "instanceType",
                osName: "osName"
            }
        ];
        $("#instancesTable").bootstrapTable('load', instances);
        // $('.portal-loading').show();
        // $.ajax({
        //     type: "get",
        //     data: {},
        //     dataType: 'json',
        //     url: "../instances",
        //     success: function (data) {
        //         debugger
        //         if ('Success' == data.code) {
        //             $("#instancesTable").bootstrapTable('load', data.data.instances);
        //         }
        //
        //         $('.portal-loading').hide();
        //     },
        //     error: function () {
        //         $('.portal-loading').hide();
        //         Ewin.showMsg('error', '查找云主机列表失败！');
        //         $(".modal-backdrop").remove();
        //     }
        // });
        for (var i = 0; i < instances.length; i++) {
            var status = "#status" + i;
            $(status).css({
                "position": "relative",
                "left": ($(status).parent().width() - $(status).width()) / 2 + "px"
            });
        }

        // $("#aaa0").css({
        //     "position": "relative",
        //     "left": "200px"
        // });
        // console.log($("#aaa0").parent().width());
    }

    function findInstanceAttribute() {
        debugger
        alert('');
        // alert(row);
    }

    // 表格中"状态"菜单栏数据格式化
    function stateFormatter(value, row, index) {
        // if (row.state == true)
        //     return {
        //         disabled: true,//设置是否可用
        //         checked: true//设置选中
        //     };
        return value;
    }

    // 表格中"ID/云主机名"菜单栏数据格式化
    function idNameFormatter(value, row, index) {
        sessionStorage.instanceId = row.instanceId;
        sessionStorage.cloudId = row.cloudId;
        return '<a id="instanceId' + index + '" href="../instanceDetailPage" style="margin: 0 auto">' + row.instanceId + ' </a>'
            + '<p>' + row.instanceName + '</p>';
    }

    // 表格中"status"菜单栏数据格式化
    function statusFormatter(value, row, index) {
        var status = row.status.toLocaleLowerCase();
        if (status == "running")
            return '<p id="status' + index + '" class="fa fa-circle" style="color: #0C9C14;"> 运行中</p>';
        if (status == "stopped")
            return '<p id="status' + index + '" class="fa fa-circle" style="color: #8B91A0;"> 已停止</p> ';
        if (status == "starting" || status == "rebooting")
            return '<p id="status' + index + '" class="fa fa-spinner" style="color: #0C9C14;"> 启动中</p> ';
        if (status == "pending")
            return '<p id="status' + index + '" class="fa fa-spinner" style="color: #ff6600;"> 准备中</p> ';
        if (status == "stopping")
            return '<p id="status' + index + '" class="fa fa-spinner" style="color: #8B91A0;"> 停止中</p> ';
    }

    // 表格中"instanceType"菜单栏数据格式化
    function instanceTypeFormatter(value, row, index) {
        var instanceType = row.instanceType;
        var osName = row.osName;
        if (null != osName)
            return '<p">' + row.instanceType + '<img src=../image/windows.jpg style="width: 20px;vertical-align: middle;"> </p>';
        else
            return '<p">' + row.instanceType + '</p>';
    }

    // 表格中"memory"菜单栏数据格式化
    function memoryFormatter(value, row, index) {
        var memory = row.memory;
        var cpu = row.cpu;
        return '<p id="memory' + index + '" > 运行中</p>'+ memory + ;
        var osName = row.osName;
        if (null != osName)
            return '<p">' + row.instanceType + '<img src=../image/windows.jpg style="width: 20px;vertical-align: middle;"> </p>';
        else
            return '<p">' + row.instanceType + '</p>';
    }

    function operateFormatter(value, row, index) {
        return [
            '<a class="RoleOfDelete fa fa-trash-o"></a>'
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
                        $('.portal-loading').hide();
                        Ewin.showMsg('error', '删除云失败！');
                    }
                });
            }
        });
    });
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