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
            field: 'userName',
            title: '用户名'
        },
        {
            field: 'roleName',
            title: '角色'
        },
        {
            field: 'phone',
            title: '联系电话'
        },
        {
            field: 'email',
            title: '邮箱'
        },
        {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: operateEvents,
            formatter: operateFormatter
        }
    ];
    $("#userTable").bootstrapTable({
        showRefresh: true,                  //是否显示刷新按钮
        showPaginationSwitch: true,       //是否显示选择分页数按钮
        columns: columns,
        pagination: true,//是否开启分页（*）
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 5,//每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页的行数（*）
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        search: true,
        toolbar: "#userToolbar"
    });


    $('#btn_addUser').click(function () {
        debugger
        $("#addUserModal").modal('hide');
        $('.portal-loading').show();
        $.ajax({
            type: "post",
            async: true,
            data: {
                "roleName": $('#type option:selected').val(),
                "userName": $("#name").val(),
                "password": "000000",
                "phone": $("#phone").val(),
                "email": $("#email").val()
            },
            url: "../users/register",
            success: function (data) {
                debugger
                if (data.code == "Success") {
                    Ewin.showMsg('success', '新增用户成功，初始密码: 000000！');
                    findUserList();
                }
                $('.portal-loading').hide();
            },
            error: function () {
                Ewin.showMsg('error', '新增用户失败！');
                $('.portal-loading').hide();
            }
        });
    });

    findUserList();

    function operateFormatter(value, row, index) {
        return [
            '<a class="RoleOfDelete fa fa-trash-o"></a>'
        ].join('');
    }

    //批量删除
    $('#btn_deleteUser').click(function () {
        debugger
        var rows = $("#userTable").bootstrapTable("getSelections");
        if (rows.length === 0) {
            Ewin.showMsg('warning', '请选中要删除的用户');
            return;
        }

        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
        }

        Ewin.confirm({message: "确认要删除选中的用户吗？数量：" + rows.length}).on(function (flag) {
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
                            findUserList();
                        } else {
                            Ewin.showMsg('error', '删除用户失败！');
                        }
                        $('.portal-loading').hide();
                    },
                    error: function () {
                        $('.portal-loading').hide();
                        Ewin.showMsg('error', '删除用户失败！');
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
        Ewin.confirm({message: "确认要删除该用户吗？"}).on(function (flag) {
            if (flag === true) {
                //操作提示
                $('.portal-loading').show();
                var ids = [];
                ids.push(row.id);
                $.ajax({
                    type: "get",
                    async: true,
                    data: {ids: ids},
                    url: "../user/deleteUser",
                    success: function (data, status) {
                        if (status == "success") {
                            Ewin.showMsg('success', '删除成功！');
                            findUserList();
                        } else {
                            Ewin.showMsg('error', '删除用户失败！');
                        }
                        $('.portal-loading').hide();
                    },
                    error: function () {
                        $('.portal-loading').hide();
                        Ewin.showMsg('error', '删除用户失败！');
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

function findUserList() {
    $('.portal-loading').show();
    $.ajax({
        type: "get",
        data: {},
        dataType: 'json',
        url: "../users",
        success: function (data) {
            if ('Success' == data.code) {
                debugger
                data.data.users.forEach(function (value, index, array) {
                    debugger
                    if (value.roleName === 'MANAGER') {
                        value.roleName = '管理员';
                    }
                    if (value.roleName === 'USER') {
                        value.roleName = '普通用户'
                    }

                    if (value.phone === undefined) {
                        value.phone = '-----';
                    }

                    if (value.email === undefined) {
                        value.email = '-----';
                    }
                });
                $("#userTable").bootstrapTable('load', data.data.users);
            }

            $('.portal-loading').hide();
        },
        error: function () {
            $('.portal-loading').hide();
            Ewin.showMsg('error', '查找用户服务商列表失败！');
            $(".modal-backdrop").remove();
        }
    });
}