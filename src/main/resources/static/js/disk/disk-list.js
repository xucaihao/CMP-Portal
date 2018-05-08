$(function () {
    $('.dropdown-toggle').dropdown();
    $('.portal-loading').hide();

    // 查询快照列表
    findDisks();

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
            field: 'diskId',
            title: 'ID/硬盘名',
            events: operateEvents,
            formatter: idNameFormatter
        },
        {
            field: 'status',
            title: '状态',
            align: 'center',
            formatter: statusFormatter
        },
        {
            field: 'type',
            title: '属性',
            align: 'center',
            formatter: typeFormatter
        },
        {
            field: 'category',
            title: '配置',
            formatter: categoryFormatter
        },
        {
            field: 'instanceId',
            title: '关联云主机',
            formatter: instanceIdFormatter
        },
        {
            field: 'diskChargeType',
            title: '计费方式',
            align: 'center',
            formatter: diskChargeTypeFormatter
        },
        {
            field: 'portable',
            title: '可卸载',
            align: 'center',
            formatter: portableFormatter
        },
        {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: operateEvents,
            formatter: operateFormatter
        }
    ];
    $("#disksTable").bootstrapTable({
        showRefresh: false,                  //是否显示刷新按钮
        showPaginationSwitch: true,       //是否显示选择分页数按钮
        columns: columns,
        pagination: true,//是否开启分页（*）
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 10,//每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页的行数（*）
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        search: true,
        toolbar: "#disksToolbar",
        onCheck: function (row) {
            sessionStorage.diskId = row.diskId;
            sessionStorage.regionId = row.regionId;
            sessionStorage.cloudId = row.cloudId;
            sessionStorage.status = row.status.toLowerCase();
        },
        onCheckAll: function (rows) {
            var selectedDisks = $("#disksTable").bootstrapTable("getSelections");
        },
        onUncheck: function () {
            var selectedDisks = $("#disksTable").bootstrapTable("getSelections");
        }
    });

    function findDisks() {
        $('.portal-loading').show();
        $.ajax({
            type: "get",
            data: {},
            dataType: 'json',
            url: "../disks",
            success: function (data) {
                if ('Success' === data.code) {
                    $("#disksTable").bootstrapTable('load', data.data.disks);
                    $('.portal-loading').hide();
                } else {
                    $('.portal-loading').hide();
                    if (data.msg == null || data.msg === "")
                        Ewin.showMsg('error', '查询硬盘列表失败！');
                    else
                        Ewin.showMsg('error', data.msg);
                    $(".modal-backdrop").remove();
                }
            },
            error: function () {
                $('.portal-loading').hide();
                Ewin.showMsg('error', '查询硬盘列表失败！');
                $(".modal-backdrop").remove();
            }
        });
    }

    //刷新硬盘列表
    $('#btn_refreshDisks').click(function () {
        findDisks();
    });

    //确认修改硬盘名称
    $('#btn_modifyDiskName').click(function () {
        $("#modifyDiskNameDialog").modal('hide');
        $('.portal-loading').show();
        var data = {
            cloudId: sessionStorage.cloudId,
            regionId: sessionStorage.regionId,
            diskId: sessionStorage.diskId,
            diskName: $('#modifyDiskName').val()
        };
        $.ajax({
            type: "get",
            async: true,
            data: data,
            url: "../disks/modifyName",
            success: function (data, status) {
                debugger
                if (data.code === 'Success') {
                    Ewin.showMsg('success', '修改成功！');
                    findDisks();
                } else {
                    Ewin.showMsg('error', data.msg);
                }
                $('.portal-loading').hide();
            },
            error: function () {
                Ewin.showMsg('error', '修改失败！');
                $('.portal-loading').hide();
            }
        });
    });

    //确认创建快照
    $('#doCreSnapshot').click(function () {
        $("#createSnapshotInDiskDialog").modal('hide');
        $('.portal-loading').show();
        var data = {
            cloudId: sessionStorage.cloudId,
            regionId: sessionStorage.regionId,
            diskId: sessionStorage.diskId,
            snapshotName: $('#createSnapshot-snapshotName').val()
        };
        $.ajax({
            type: "get",
            async: true,
            data: data,
            url: "../snapshots/create",
            success: function (data, status) {
                debugger
                if (data.code === 'Success') {
                    Ewin.showMsg('success', '快照创建成功，请稍后到快照界面查看！');
                    findDisks();
                } else {
                    Ewin.showMsg('error', data.msg);
                }
                $('.portal-loading').hide();
            },
            error: function () {
                Ewin.showMsg('error', '快照创建失败！');
                $('.portal-loading').hide();
            }
        });
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

    // 表格中"ID/快照名"菜单栏数据格式化
    function idNameFormatter(value, row, index) {
        sessionStorage.diskId = row.diskId;
        sessionStorage.regionId = row.regionId;
        sessionStorage.cloudId = row.cloudId;
        return '<a id="diskId' + index + '" href="../diskDetailPage">' + row.diskId + ' </a><br/>'
            + row.diskName + '&nbsp;&nbsp;'
            + '<a class="modifyDiskName fa fa-edit" style="color: #2A2E36"></a>';
    }

    // 表格中"status"菜单栏数据格式化
    function statusFormatter(value, row, index) {
        var status = row.status.toLocaleLowerCase();
        var instanceId = row.instanceId;
        var response = status;
        if (instanceId != null && instanceId !== "")
            response = '<span id="status' + index + '" class="fa fa-circle" style="color: #0C9C14;"></span>&nbsp;&nbsp;'
                + '<span style="color: #0C9C14;">使用中</span>';
        else if (status === "available" || status === "normal")
            response = '<span id="status' + index + '" class="fa fa-circle" style="color: #ff6600;"></span>&nbsp;&nbsp;'
                + '<span style="color: #ff6600;">待挂载</span>';
        else if (status === "attaching")
            response = '<span id="status' + index + '" class="fa fa-spinner fa-spin" style="color: #0C9C14;"></span>&nbsp;&nbsp;'
                + '<span style="color: #0C9C14;">挂载中</span>';
        else if (status === "detaching")
            response = '<span id="status' + index + '" class="fa fa-spinner fa-spin" style="color: #ff6600;"></span>&nbsp;&nbsp;'
                + '<span style="color: #ff6600;">卸载中</span>';
        return response;
    }

    // 表格中"属性"菜单栏数据格式化
    function typeFormatter(value, row, index) {
        var type = row.type.toLocaleLowerCase();
        var response = "---";
        if (type === "system" || type === "root")
            response = "系统盘";
        if (type === "data")
            response = "数据盘";
        return response;
    }

    // 表格中"配置"菜单栏数据格式化
    function categoryFormatter(value, row, index) {
        var category = row.category.toLowerCase();
        var size = row.size;
        var res = '<p id="category' + index + '" >' + size + '(GB)</p> ';
        if (category === "cloud" || category === "cloudbasic")
            res = '<p id="category' + index + '"> 普通云盘 </p>' +
                '<p id="category' + index + '" >' + size + '(GB)</p> ';
        if (category === "cloud_efficiency" || category === "cloudpremium")
            res = '<p id="category' + index + '"> 高效云盘 </p>' +
                '<p id="category' + index + '" >' + size + '(GB)</p> ';
        if (category === "cloud_ssd" || category === "cloudssd")
            res = '<p id="category' + index + '"> SSD云盘 </p>' +
                '<p id="category' + index + '" >' + size + '(GB)</p> ';
        return res;
    }

    // 表格中"关联云主机"菜单栏数据格式化
    function instanceIdFormatter(value, row, index) {
        sessionStorage.instanceId = row.instanceId;
        sessionStorage.regionId = row.regionId;
        sessionStorage.cloudId = row.cloudId;
        return '<a id="instanceId' + index + '" href="../instanceDetailPage">' + row.instanceId + ' </a>';
    }

    // 表格中"计费方式"菜单栏数据格式化
    function diskChargeTypeFormatter(value, row, index) {
        var diskChargeType = row.diskChargeType.toLowerCase();
        var response = "---";
        if (diskChargeType === "prepaid" || diskChargeType === "prepay") {
            response = '<p id="diskChargeType' + index + '" >包年包月</p> ';
        }
        if (diskChargeType === "postpay" || diskChargeType === "postpaid") {
            response = '<p id="diskChargeType' + index + '" >按量付费</p> ';
        }
        return response;
    }

    // 表格中"可卸载"菜单栏数据格式化
    function portableFormatter(value, row, index) {
        var portable = row.portable;
        if (portable != null && portable === true) {
            return "支持";
        } else {
            return "不支持";
        }
    }

    function operateFormatter(value, row, index) {
        return '<div>' +
            '<a class="CreateSnapshotInDisk fa fa-camera" title="创建快照" style="color: #0e9aef;"></a>&nbsp;&nbsp;&nbsp;' +
            '<a class="dropdown fa fa-list" data-toggle="dropdown" href="#" title="更多操作" style="color: #0e9aef"></a>' +
            '<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">' +
            '<li><a href = "#">挂载</a></li>' +
            '<li><a href = "#">卸载</a></li>' +
            '<li><a href = "#">扩容</a></li>' +
            '<li><a href = "#">销毁</a></li>' +
            '</ul></div>';
    }

});
window.operateEvents = {
    //修改硬盘名称
    'click .modifyDiskName': function (e, value, row, index) {
        sessionStorage.cloudId = row.cloudId;
        sessionStorage.regionId = row.regionId;
        sessionStorage.diskId = row.diskId;
        $('#modifyDiskName').val(row.diskName);
        $("#modifyDiskNameDialog").modal('show');
    },
    //创建快照
    'click .CreateSnapshotInDisk': function (e, value, row, index) {
        $('#createSnapshot-diskId').html(row.diskId);
        $('#createSnapshot-diskName').html(row.diskName);
        $('#createSnapshot-diskSize').html(row.size + "GB");
        debugger
        var category = row.category.toLowerCase();
        if (category === "cloud" || category === "cloudbasic")
            $('#createSnapshot-category').html("普通云盘");
        if (category === "cloud_efficiency" || category === "cloudpremium")
            $('#createSnapshot-category').html("高效云盘");
        if (category === "cloud_ssd" || category === "cloudssd")
            $('#createSnapshot-category').html("SSD云盘");
        $("#createSnapshotInDiskDialog").modal('show');
    }
};