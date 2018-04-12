$(function () {
    debugger
    var cloudId = sessionStorage.cloudId;
    var instanceId = sessionStorage.instanceId;
    
    $.ajax({
        url: "../instances/" + instanceId,
        data: {
            cloudId: cloudId
        },
        dataType: "json",
        type: "GET",
        success: function (data) {

            if (data.code === 'Success') {

            }
        },
        error: function () {
            
        }
    });
});