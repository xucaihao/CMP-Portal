$(function () {
    $('.dropdown-toggle').dropdown();
    $('.portal-loading').hide();
    getClouds();
    getUsers();
    var columns = [
        {
            field: 'checked',
            checkbox: true,
            formatter: stateFormatter
        },
        {
            field: 'cmpUserName',
            title: '用户名'
        },
        {
            field: 'accessKey',
            title: 'ak'
        },
        {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: operateEvents,
            formatter: operateFormatter
        }
    ];
    $("#userMappingTable").bootstrapTable({
        showRefresh: true,                  //是否显示刷新按钮
        showPaginationSwitch: true,       //是否显示选择分页数按钮
        columns: columns,
        pagination: true,//是否开启分页（*）
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 5,//每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页的行数（*）
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        search: true,
        toolbar: "#userMappingToolbar"
    });


    $('#btn_addUserMapping').click(function () {
        debugger
        $("#addUserMappingModal").modal('hide');
        $('.portal-loading').show();
        $.ajax({
            type: "post",
            async: true,
            data: {
                "cmpUserId": $('#user option:selected').val(),
                "cloudId":  $('#cloud option:selected').val(),
                "accessKey": $("#ak").val(),
                "secret": $("#sk").val()
            },
            url: "../userMappings/add",
            success: function (data) {
                debugger
                if (data.code == "Success") {
                    Ewin.showMsg('success', '新增用户映射成功');
                    findUserMappingList();
                } else {
                    Ewin.showMsg('error', data.des);
                }
                $('.portal-loading').hide();
            },
            error: function () {
                Ewin.showMsg('error', '新增用户映射失败！');
                $('.portal-loading').hide();
            }
        });
    });

    $('#addUserMappingModal').on('show.bs.modal', function (event) {
        debugger
        getUsers();
        getClouds();
    })

    findUserMappingList();

    function operateFormatter(value, row, index) {
        return [
            '<a class="RoleOfDelete fa fa-trash-o"></a>'
        ].join('');
    }

    //批量删除
    $('#btn_deleteUserMapping').click(function () {
        debugger
        var rows = $("#userMappingTable").bootstrapTable("getSelections");
        if (rows.length === 0) {
            Ewin.showMsg('warning', '请选中要删除的用户映射');
            return;
        }

        var userMappingIds = [];
        for (var i = 0; i < rows.length; i++) {
            userMappingIds.push(rows[i].userMappingId);
        }

        Ewin.confirm({message: "确认要删除选中的用户映射吗？数量：" + rows.length}).on(function (flag) {
            if (flag === true) {
                $('.portal-loading').show();
                $.ajax({
                    type: "get",
                    async: true,
                    traditional: false,
                    data: {userMappingIds: userMappingIds},
                    url: "../userMappings/delete",
                    success: function (data, status) {
                        debugger
                        if (status == "success") {
                            Ewin.showMsg('success', '删除用户映射成功！');
                            findUserMappingList();
                        } else {
                            Ewin.showMsg('error', '删除用户映射失败！');
                        }
                        $('.portal-loading').hide();
                    },
                    error: function () {
                        $('.portal-loading').hide();
                        Ewin.showMsg('error', '删除用户映射失败！');
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

    $("#addUserMappingModal").on("hidden.bs.modal", function() {
        debugger

    });
});
window.operateEvents = {
    'click .RoleOfDelete': function (e, value, row, index) {
        debugger
        Ewin.confirm({message: "确认要删除该用户映射吗？"}).on(function (flag) {
            if (flag === true) {
                //操作提示
                $('.portal-loading').show();

                var userMappingIds = [];
                userMappingIds.push(row.userMappingId);
                $.ajax({
                    type: "get",
                    async: true,
                    data: {userMappingIds: userMappingIds},
                    url: "../userMappings/delete",
                    success: function (data, status) {
                        if (status == "success") {
                            Ewin.showMsg('success', '删除用户映射成功！');
                            findUserMappingList();
                        } else {
                            Ewin.showMsg('error', '删除用户映射失败！');
                        }
                        $('.portal-loading').hide();
                    },
                    error: function () {
                        $('.portal-loading').hide();
                        Ewin.showMsg('error', '删除用户映射失败！');
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

function findUserMappingList() {
    $('.portal-loading').show();
    $.ajax({
        type: "get",
        data: {},
        dataType: 'json',
        url: "../userMappings",
        success: function (data) {
            if ('Success' == data.code) {
                debugger
                data.data.userMappings.forEach(function (value, index, array) {
                    debugger
                    if (value.roleName === 'MANAGER') {
                        value.roleName = '管理员';
                    }
                    if (value.roleName === 'USER') {
                        value.roleName = '普通用户'
                    }

                    if (value.phone === undefined || value.phone === '') {
                        value.phone = '-----';
                    }

                    if (value.email === undefined || value.email === '') {
                        value.email = '-----';
                    }
                });
                $("#userMappingTable").bootstrapTable('load', data.data.userMappings);
            }

            $('.portal-loading').hide();
        },
        error: function () {
            $('.portal-loading').hide();
            Ewin.showMsg('error', '查找用户映射列表失败！');
            $(".modal-backdrop").remove();
        }
    });
}

function getUsers() {
    debugger
    $.ajax({
        type: "get",
        data: {},
        dataType: 'json',
        url: "../users",
        success: function (data) {
            if (data.code == "Success") {
                var tempAjax = "";
                $.each(data.data.users, function (i, n) {
                    tempAjax += "<option value='" + n.userId + "'>" + n.userName + "</option>";
                });
                $("#user").empty();
                $("#user").append(tempAjax);
            } else {
                Ewin.showMsg('error', '查询用户列表失败！');
            }
            $('.portal-loading').hide();

        },
        error: function () {
            $('.portal-loading').hide();
            Ewin.showMsg('error', '查找用户列表失败！');
            $(".modal-backdrop").remove();
        }
    });
}


function getClouds() {
    debugger
    // if (type === 'PUBLIC') {
    //     visibility = 'PUBLIC';
    // } else {
    //     $('#ipDiv').show();
    //     $('#portDiv').show();
    //     visibility = 'PRIVATE';
    // }

    $.ajax({
        type: "get",
        async: false,
        url: "../clouds",
        success: function (data, status) {
            debugger

            if (data.code == "Success") {
                var tempAjax = "";
                $.each(data.data.clouds, function (i, n) {
                    tempAjax += "<option value='" + n.cloudId + "'>" + n.cloudName + "</option>";
                });
                $("#cloud").empty();
                $("#cloud").append(tempAjax);
            } else {
                Ewin.showMsg('error', '查询云列表失败！');
            }
            $('.portal-loading').hide();
        },
        error: function () {
            $('.portal-loading').hide();
            Ewin.showMsg('error', '查询云列表失败！');
        }
    });
}