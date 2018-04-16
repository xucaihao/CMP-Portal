$(function () {
    $('.dropdown-toggle').dropdown();
    $('.portal-loading').hide();

    // 查询公共镜像列表
    findPublicImages();

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
            field: 'imageId',
            title: 'ID/镜像名',
            formatter: idNameFormatter
        },
        // {
        //     field: 'status',
        //     title: '状态',
        //     align: 'center',
        //     formatter: statusFormatter
        // },
        {
            field: 'imageOwnerAlias',
            title: '类型',
            align: 'center',
            formatter: visibilityFormatter
        },
        {
            field: 'size',
            title: '容量(GB)'
        },
        {
            field: 'oSName',
            title: '操作系统',
            align: 'center',
            formatter: osNameFormatter
        },
        {
            field: 'platform',
            title: '平台'
        },
        {
            field: 'architecture',
            title: '系统位数',
            align: 'center',
            formatter: architectureFormatter
        },
        {
            field: 'creationTime',
            title: '创建时间',
            formatter: creationTimeFormatter
        },
        {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: operateEvents,
            formatter: operateFormatter
        }
    ];
    $("#imagesTable").bootstrapTable({
        showRefresh: true,                  //是否显示刷新按钮
        showPaginationSwitch: true,       //是否显示选择分页数按钮
        columns: columns,
        pagination: true,//是否开启分页（*）
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 10,//每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页的行数（*）
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        search: true,
        toolbar: "#imagesToolbar"
    });

    function findPublicImages() {
        $('.portal-loading').show();
        $.ajax({
            type: "get",
            data: {},
            dataType: 'json',
            url: "../publicImages",
            success: function (data) {
                if ('Success' === data.code) {
                    $("#imagesTable").bootstrapTable('load', data.data.images);
                    $('.portal-loading').hide();
                } else {
                    $('.portal-loading').hide();
                    Ewin.showMsg('error', data.message);
                    $(".modal-backdrop").remove();
                }
            },
            error: function () {
                $('.portal-loading').hide();
                Ewin.showMsg('error', '查询镜像列表失败！');
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

    // 表格中"状态"菜单栏数据格式化
    function stateFormatter(value, row, index) {
        if (row.state == true)
            return {
                disabled: true,//设置是否可用
                checked: true//设置选中
            };
        return value;
    }

    // 表格中"ID/镜像名"菜单栏数据格式化
    function idNameFormatter(value, row, index) {
        sessionStorage.imageId = row.imageId;
        sessionStorage.regionId = row.regionId;
        sessionStorage.cloudId = row.cloudId;
        return '<a id="imageId' + index + '" href="../imageDetailPage" style="margin: 0 auto">' + row.imageId + ' </a>'
            + '<p>' + row.imageName + '</p>';
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

    // 表格中"类型"菜单栏数据格式化
    function visibilityFormatter(value, row, index) {
        var visibility = row.imageOwnerAlias.toLocaleLowerCase();
        var res = "---";
        if (visibility.indexOf("public") >= 0 || visibility.indexOf("system") >= 0)
            res = '<p id="status' + index + '"> 公共镜像</p>';
        if (visibility.indexOf("private") >= 0 || visibility.indexOf("self") >= 0)
            res = '<p id="status' + index + '" class="fa fa-circle" style="color: #8B91A0;"> 已停止</p> ';
        if (visibility.indexOf("shared") >= 0 || visibility.indexOf("others") >= 0)
            res = '<p id="status' + index + '" class="fa fa-spinner" style="color: #0C9C14;"> 启动中</p> ';
        return res;
    }

    // 表格中"操作系统"菜单栏数据格式化
    function osNameFormatter(value, row, index) {
        var osName = row.oSName.toLocaleLowerCase();
        if (osName.startsWith("windows"))
            return '<p> <img title="' + osName + '"' + ' src=../image/windows.jpg style="width: 20px;vertical-align: middle;" </p>';
        else if (osName.startsWith("centos"))
            return '<p> <img title="' + osName + '"' + ' src=../image/centos.jpg style="width: 20px;vertical-align: middle;" </p>';
        else if (osName.startsWith("ubuntu"))
            return '<p> <img title="' + osName + '"' + ' src=../image/ubuntu.jpg style="width: 20px;vertical-align: middle;" </p>';
        else if (osName.startsWith("linux"))
            return '<p> <img title="' + osName + '"' + ' src=../image/linux.jpg style="width: 20px;vertical-align: middle;" </p>';
        else if (osName.indexOf("suse") >= 0)
            return '<p> <img title="' + osName + '"' + ' src=../image/suse.jpg style="width: 20px;vertical-align: middle;" </p>';
        else if (osName.startsWith("gentoo"))
            return '<p> <img title="' + osName + '"' + ' src=../image/gentoo.jpg style="width: 20px;vertical-align: middle;" </p>';
        else if (osName.startsWith("freebsd"))
            return '<p> <img title="' + osName + '"' + ' src=../image/freebsd.jpg style="width: 20px;vertical-align: middle;" </p>';
        else if (osName.startsWith("debian"))
            return '<p> <img title="' + osName + '"' + ' src=../image/debian.jpg style="width: 20px;vertical-align: middle;" </p>';
        else if (osName.startsWith("coreos"))
            return '<p> <img title="' + osName + '"' + ' src=../image/coreos.jpg style="width: 20px;vertical-align: middle;" </p>';
        else if (osName.startsWith("ali"))
            return '<p> <img title="' + osName + '"' + ' src=../image/ali.jpg style="width: 20px;vertical-align: middle;" </p>';
        else
            return '<p>' + row.oSName + '</p>';
    }

    // 表格中"系统位数"菜单栏数据格式化
    function architectureFormatter(value, row, index) {
        var architecture = row.architecture;
        if (architecture.indexOf("64") >= 0)
            return '<p> 64位</p>';
        else
            return '<p> 32位</p>';
    }

    // 表格中"创建时间"菜单栏数据格式化
    function creationTimeFormatter(value, row, index) {
        var creationTime = row.creationTime;
        var res = "---";
        if (creationTime != null || creationTime !== "") {
            return creationTime;
        } else {
            return res;
        }
    }

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