$(function () {
    $('.dropdown-toggle').dropdown();
    $('.portal-loading').hide();

    // 查询快照列表
    findSnapshots();

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
            field: 'snapshotId',
            title: 'ID/快照名',
            formatter: idNameFormatter
        },
        {
            field: 'sourceDiskId',
            title: '磁盘ID',
            formatter: sourceDiskIdFormatter
        },
        {
            field: 'sourceDiskSize',
            title: '磁盘容量(GB)',
            align: 'center'
        },
        {
            field: 'sourceDiskType',
            title: '磁盘属性',
            align: 'center',
            formatter: sourceDiskTypeFormatter
        },
        {
            field: 'creationTime',
            title: '创建时间',
            align: 'center',
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
    $("#snapshotsTable").bootstrapTable({
        showRefresh: true,                  //是否显示刷新按钮
        showPaginationSwitch: true,       //是否显示选择分页数按钮
        columns: columns,
        pagination: true,//是否开启分页（*）
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 10,//每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页的行数（*）
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        search: true,
        toolbar: "#sapshotsToolbar"
    });

    function findSnapshots() {
        $('.portal-loading').show();
        $.ajax({
            type: "get",
            data: {},
            dataType: 'json',
            url: "../snapshots",
            success: function (data) {
                if ('Success' === data.code) {
                    $("#snapshotsTable").bootstrapTable('load', data.data.snapshots);
                    $('.portal-loading').hide();
                } else {
                    $('.portal-loading').hide();
                    if (data.message == null || data.message === "")
                        Ewin.showMsg('error', '查询快照列表失败！');
                    else
                        Ewin.showMsg('error', data.message);
                    $(".modal-backdrop").remove();
                }
            },
            error: function () {
                $('.portal-loading').hide();
                Ewin.showMsg('error', '查询快照列表失败！');
                $(".modal-backdrop").remove();
            }
        });
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

    // 表格中"ID/快照名"菜单栏数据格式化
    function idNameFormatter(value, row, index) {
        return '<p>' + row.snapshotId + ' </p>'
            + '<p>' + row.snapshotName + '</p>';
    }

    // 表格中"磁盘ID"菜单栏数据格式化
    function sourceDiskIdFormatter(value, row, index) {
        sessionStorage.sourceDiskId = row.sourceDiskId;
        sessionStorage.regionId = row.regionId;
        sessionStorage.cloudId = row.cloudId;
        return '<a id="sourceDiskId' + index + '" href="../volumeDetailPage" style="margin: 0 auto">' + row.sourceDiskId + ' </a>';
    }

    // 表格中"磁盘ID"菜单栏数据格式化
    function sourceDiskTypeFormatter(value, row, index) {
        var sourceDiskType = row.sourceDiskType.toLowerCase();
        var res = "---";
        if (sourceDiskType === "system" || sourceDiskType === "root")
            res = '<a id="sourceDiskType' + index + '"> 系统盘 </a>';
        if (sourceDiskType === "data")
            res = '<a id="sourceDiskType' + index + '"> 数据盘 </a>';
        return res;
    }

    // 表格中"创建时间"菜单栏数据格式化
    function creationTimeFormatter(value, row, index) {
        debugger
        var creationTime = row.creationTime;
        var res = "---";
        if (creationTime != null || creationTime !== "") {
            return creationTime;
        } else {
            return res;
        }
    }

    function operateFormatter(value, row, index) {
        // var visibility = row.imageOwnerAlias.toLocaleLowerCase();
        // if (visibility.indexOf("public") >= 0 || visibility.indexOf("system") >= 0) {
        //     return '<a class="InstanceLogInBlack fa fa-plus-square-o" title="创建云主机" style="color: #0e9aef;"></a>';
        // } else {
        //     return [
        //         ('<a class="InstanceLogInBlack fa fa-plus-square-o" title="创建云主机" style="color: #0e9aef;"></a>&nbsp;&nbsp;&nbsp;'),
        //         '<a class="InstanceFee  fa fa-share-alt" title="共享" style="color: #0e9aef;"></a>&nbsp;&nbsp;&nbsp;',
        //         '<a class="InstanceMore fa fa-list" title="更多操作" style="color: #0e9aef;"></a>'
        //     ].join('');
        // }
    }

});
window.operateEvents = {
    // 'click .InstanceLogInBule': function (e, value, row, index) {
    //     sessionStorage.cloudType = row.cloudType;
    //     sessionStorage.regionId = row.regionId;
    //     sessionStorage.instanceId = row.instanceId;
    //     $("#instanceLogIn").modal('show');
    // }
};