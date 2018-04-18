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
        pageSize: 10,//每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页的行数（*）
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        search: true,
        toolbar: "#instancesToolbar",
        onCheck: function (row) {
            var selectedInstances = $("#instancesTable").bootstrapTable("getSelections");
            //循环处理判断是否存
            if (selectedInstances.length == 1 && row.status.toLowerCase() === "running") {
                sessionStorage.instanceId = row.instanceId;
                sessionStorage.regionId = row.regionId;
                sessionStorage.cloudId = row.cloudId;
                $("#btn_open_close_Instance_span").text('关机');
                $('#btn_open_close_Instance').attr("disabled", false);
            }
            if (selectedInstances.length == 1 && row.status.toLowerCase() === "stopped") {
                sessionStorage.instanceId = row.instanceId;
                sessionStorage.regionId = row.regionId;
                sessionStorage.cloudId = row.cloudId;
                $("#btn_open_close_Instance_span").text('开机');
                $("#btn_open_close_Instance").attr("disabled", false);
            }
        },
        onCheckAll: function (rows) {
            var selectedInstances = $("#instancesTable").bootstrapTable("getSelections");
        },
        onUncheck: function () {
            var selectedInstances = $("#instancesTable").bootstrapTable("getSelections");
            if (selectedInstances.length == 0) {
                $('#btn_open_close_Instance').attr("disabled", true);
            }
        }
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
                if ('Success' === data.code) {
                    $('.portal-loading').hide();
                    $("#instancesTable").bootstrapTable('load', data.data.instances);
                    for (var i = 0; i < data.data.instances.length; i++) {
                        var status = "#status" + i;
                        $(status).css({
                            "position": "relative",
                            "left": ($(status).parent().width() - $(status).width()) / 2 + "px"
                        });
                    }
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

    //登录主机实例
    $('#openInstanceConsole').click(function () {
        var cloudType = sessionStorage.cloudType;
        var regionId = sessionStorage.regionId;
        var instanceId = sessionStorage.instanceId;
        if (cloudType === "tencent-cloud") {
            window.open("https://iaas.qcloud.com/vnc?regionId=" + regionId + "&instanceId=" + instanceId);
        }
        if (cloudType === "ali-cloud") {
            window.open("https://ecs.console.aliyun.com/vnc/index.htm?spm=5176.2020520101.107.d515.65834df558kRdd&instanceId="
                + instanceId + "&regionId=" + regionId);
        }
    });

    //登录云创建主机实例
    $('#btn_addInstance').click(function () {
        window.location.href = '../instancesCreatePage';
    });

    //开启、关闭主机
    $('#btn_open_close_Instance').click(function () {
        var op = $("#btn_open_close_Instance_span").text();
        if (op === "关机") {
            $("#closeInstanceDialog").modal('show');
        }
        if (op === "开机") {
            $.ajax({
                type: "get",
                data: {
                    cloudId: sessionStorage.cloudId,
                    instanceId: sessionStorage.instanceId,
                    regionId: sessionStorage.regionId,
                },
                dataType: 'json',
                url: "../instances/start",
                success: function (data) {
                    if ('Success' === data.code) {
                        setInterval(findInstances(), 1000);
                    } else {
                        $('.portal-loading').hide();
                        if (data.message == null || data.message === "")
                            Ewin.showMsg('error', '启动云主机失败！');
                        else
                            Ewin.showMsg('error', data.message);
                        $(".modal-backdrop").remove();
                    }
                },
                error: function () {
                    $('.portal-loading').hide();
                    Ewin.showMsg('error', '启动云主机失败！');
                    $(".modal-backdrop").remove();
                }
            });
        }
    });


    //确认关闭主机
    $('#doCloseInstance').click(function () {
        $("#closeInstanceDialog").hide();
        $.ajax({
            type: "get",
            data: {
                cloudId: sessionStorage.cloudId,
                instanceId: sessionStorage.instanceId,
                regionId: sessionStorage.regionId,
                forceStop: sessionStorage.forceStop
            },
            dataType: 'json',
            url: "../instances/close",
            success: function (data) {
                if ('Success' === data.code) {
                    setInterval(findInstances(), 1000);
                    $(".modal-backdrop").remove();
                } else {
                    $('.portal-loading').hide();
                    if (data.message == null || data.message === "")
                        Ewin.showMsg('error', '关闭云主机失败！');
                    else
                        Ewin.showMsg('error', data.message);
                    $(".modal-backdrop").remove();
                }
            },
            error: function () {
                $('.portal-loading').hide();
                Ewin.showMsg('error', '关闭云主机失败！');
                $(".modal-backdrop").remove();
            }
        });
    });


    // 表格中"状态"菜单栏数据格式化
    function stateFormatter(value, row, index) {
        if (row.state === true) {
            if (row.status.toLowerCase() === "running") {
                ('#btn_closeInstance').css(hidden, 'false');
                ('#btn_openInstance').css(hidden, 'true');
            }
            if (row.status.toLowerCase() === "stopped") {
                ('#btn_closeInstance').css(hidden, 'true');
                ('#btn_openInstance').css(hidden, 'false');
            }
            return {
                disabled: true,//设置是否可用
                checked: true//设置选中
            };
        }
        return value;
    }

    // 表格中"ID/云主机名"菜单栏数据格式化
    function idNameFormatter(value, row, index) {
        sessionStorage.instanceId = row.instanceId;
        sessionStorage.regionId = row.regionId;
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
        var osname = row.osname;
        var osName = row.osname.toLocaleLowerCase();
        if (osName.startsWith("windows"))
            return '<p>' + row.instanceType + '  <img title="' + osName + '"' + ' src=../image/windows.jpg style="width: 20px;vertical-align: middle;"> </p>';
        else if (osName.startsWith("centos"))
            return '<p>' + row.instanceType + '  <img title="' + osName + '"' + ' src=../image/centos.jpg style="width: 20px;vertical-align: middle;"> </p>';
        else if (osName.startsWith("ubuntu"))
            return '<p>' + row.instanceType + '  <img title="' + osName + '"' + ' src=../image/ubuntu.jpg style="width: 20px;vertical-align: middle;"> </p>';
        else if (osName.startsWith("linux"))
            return '<p>' + row.instanceType + '  <img title="' + osName + '"' + ' src=../image/linux.jpg style="width: 20px;vertical-align: middle;"> </p>';
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
            row.status.toLowerCase() !== "running" ?
                ('<a class="InstanceLogInBlack fa fa-tv" title="请确认云主机处于运行状态" style="color: #5E5E5E"></a>&nbsp;&nbsp;&nbsp;')
                : ('<a class="InstanceLogInBule fa fa-tv" title="登录" style="color: #0e9aef;"></a>&nbsp;&nbsp;&nbsp;'),
            '<a class="InstanceFee  fa fa-paypal" title="续费" style="color: #0e9aef;"></a>&nbsp;&nbsp;&nbsp;',
            '<a class="InstanceMore fa fa-list" title="更多操作" style="color: #0e9aef;"></a>'
        ].join('');
    }


});
window.operateEvents = {
    'click .InstanceLogInBule': function (e, value, row, index) {
        sessionStorage.cloudType = row.cloudType;
        sessionStorage.regionId = row.regionId;
        sessionStorage.instanceId = row.instanceId;
        $("#instanceLogIn").modal('show');
    }
};

function stopInstance(type) {
    debugger
    if (type === 'false') {
        sessionStorage.forceStop = false;
    } else {
        sessionStorage.forceStop = true;
    }
}
