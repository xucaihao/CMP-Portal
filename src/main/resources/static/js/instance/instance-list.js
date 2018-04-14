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
            field: 'cloudName',
            title: '云服务商'
        },
        {
            field: 'instanceId',
            title: 'ID/云主机名',
            formatter: idNameFormatter
        },
        {
            field: 'status',
            title: '状态',
            align: 'center',
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
        },
        {
            field: 'publicIpAddress',
            title: 'IP地址',
            formatter: ipFormatter
        },
        {
            field: 'instanceChargeType',
            title: '主机计费模式',
            formatter: instanceChargeTypeFormatter
        },
        // {
        //     field: 'internetChargeType',
        //     title: '网络计费模式',
        //     align: 'center',
        //     formatter: internetChargeTypeFormatter
        // },
        {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: operateEvents,
            formatter: operateFormatter
        }
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
        // var instances = [{
        //     instanceId: "111",
        //     cloudId: "cloudId",
        //     instanceName: "2222",
        //     status: "RUNNING",
        //     instanceType: "instanceType",
        //     osName: "osName",
        //     memory: 2048,
        //     cpu: 1
        // },
        //     {
        //         instanceId: "111",
        //         cloudId: "cloudId",
        //         instanceName: "2222",
        //         status: "rebooting",
        //         instanceType: "instanceType",
        //         osName: "osName",
        //         memory: 2048,
        //         cpu: 1
        //     },
        //     {
        //         instanceId: "111",
        //         cloudId: "cloudId",
        //         instanceName: "2222",
        //         status: "Starting",
        //         instanceType: "instanceType",
        //         osName: "osName",
        //         memory: 2048,
        //         cpu: 1
        //     },
        //     {
        //         instanceId: "111",
        //         cloudId: "cloudId",
        //         instanceName: "2222",
        //         status: "pending",
        //         instanceType: "instanceType",
        //         osName: "osName",
        //         memory: 2048,
        //         cpu: 1
        //     },
        //     {
        //         instanceId: "111",
        //         cloudId: "cloudId",
        //         instanceName: "2222",
        //         status: "stopping",
        //         instanceType: "instanceType",
        //         osName: "osName",
        //         memory: 2048,
        //         cpu: 1
        //     }
        // ];
        // $("#instancesTable").bootstrapTable('load', instances);
        $('.portal-loading').show();
        $.ajax({
            type: "get",
            data: {},
            dataType: 'json',
            url: "../instances",
            success: function (data) {
                if ('Success' == data.code) {
                    $("#instancesTable").bootstrapTable('load', data.data.instances);
                    for (var i = 0; i < data.data.instances.length; i++) {
                        var status = "#status" + i;
                        $(status).css({
                            "position": "relative",
                            "left": ($(status).parent().width() - $(status).width()) / 2 + "px"
                        });
                    }
                }
                $('.portal-loading').hide();
            },
            error: function () {
                $('.portal-loading').hide();
                Ewin.showMsg('error', '查找云主机列表失败！');
                $(".modal-backdrop").remove();
            }
        });
    }

    function findInstanceAttribute() {
        debugger
        alert('');
        // alert(row);
    }

    // 表格中"状态"菜单栏数据格式化
    function stateFormatter(value, row, index) {
        if (row.state == true)
            return {
                disabled: true,//设置是否可用
                checked: true//设置选中
            };
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
        var osName = row.osname.toLowerCase();
        if (osName.startsWith("windows"))
            return '<p>' + row.instanceType + '  <img src=../image/windows.jpg style="width: 20px;vertical-align: middle;"> </p>';
        else if (osName.startsWith("centos"))
            return '<p>' + row.instanceType + '  <img src=../image/centos.jpg style="width: 20px;vertical-align: middle;"> </p>';
        else if (osName.startsWith("ubuntu"))
            return '<p>' + row.instanceType + '  <img src=../image/ubuntu.jpg style="width: 20px;vertical-align: middle;"> </p>';
        else if (osName.startsWith("linux"))
            return '<p>' + row.instanceType + '  <img src=../image/linux.jpg style="width: 20px;vertical-align: middle;"> </p>';
        else
            return '<p>' + row.instanceType + '</p>';
    }

    // 表格中"memory"菜单栏数据格式化
    function memoryFormatter(value, row, index) {
        var memory = row.memory;
        var cpu = row.cpu;
        return '<p id="memory' + index + '" >' + cpu + '核' + memory + 'MB </p> ';
    }

    // 表格中"ip"菜单栏数据格式化
    function ipFormatter(value, row, index) {
        var publicIpAddress = row.publicIpAddress;
        var innerIpAddress = row.innerIpAddress;
        var pIp = "---(公)";
        var iIp = "---(私)";
        if (publicIpAddress != null) {
            pIp = '<p id="ip' + index + '" >' + publicIpAddress + '(公)</p> ';
        }
        if (innerIpAddress != null) {
            iIp = '<p id="ip' + index + '" >' + innerIpAddress + '(私) </p> ';
        }
        return pIp + iIp;
    }

    // 表格中"instanceChargeType"菜单栏数据格式化
    function instanceChargeTypeFormatter(value, row, index) {
        var instanceChargeType = row.instanceChargeType.toLowerCase();
        var expiredTime = row.expiredTime;
        var chargeType = "---";
        if (instanceChargeType == "prepaid") {
            chargeType = '<p id="instanceChargeType' + index + '" >包年包月</p> ';
        }
        if (instanceChargeType == "postpaid_by_hour" || instanceChargeType === "postpaid") {
            chargeType = '<p id="instanceChargeType' + index + '" >按量付费</p> ';
        }
        return chargeType
            + '<p id="instanceChargeType' + index + '" >' + expiredTime + '到期 </p> ';
    }

    // 表格中"internetChargeType"菜单栏数据格式化
    // function internetChargeTypeFormatter(value, row, index) {
    //     var internetChargeType = row.internetChargeType.toLowerCase();
    //     var chargeType = "---";
    //     if (internetChargeType.indexOf("bandwidth") >= 0) {
    //         chargeType = '<p id="internetChargeType' + index + '" >按带宽付费</p> ';
    //     }
    //     if (internetChargeType.indexOf("traffic") >= 0) {
    //         chargeType = '<p id="internetChargeType' + index + '" >按流量付费</p> ';
    //     }
    //     return chargeType;
    // }

    function operateFormatter(value, row, index) {
        return [
            '<button id="instanceLogIn" type="button" class="btn btn-primary" data-toggle="modal" data-target="#instanceLogIn">'
            + '<span class="fa fa-tv" aria-hidden="true"></span>登录 </button>',
            '<a class="RoleOfDelete fa fa-paypal"></a>',
            '<a class="RoleOfDelete fa fa-list"></a>'
        ].join('');
    }



});
window.operateEvents = {
    'click .instanceLogIn': function (e, value, row, index) {
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
    }
};