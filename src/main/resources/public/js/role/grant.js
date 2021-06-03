var zTreeObj;
$(function () {
    loadModuleInfo();
})

//加载 tree
function loadModuleInfo() {
    $.ajax({
        type: "post",
        url: ctx + "/module/queryAllModules",
        data:{
          roleId:$("#roleId").val()
        },
        datatype: "json",
        success: function (resultData) {
            //zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                view: {
                    showLine: false
                    //showIcon:false
                },
                check: {
                    enable: true,
                    chkboxType: {"Y": "ps", "N": "ps"}
                },
                //添加ztree 复选框点击回调onCheck事件。
                callback: {
                    onCheck: zTreeOnCheck
                }
            }
            var zNodes = resultData;
            //console.log(zNodes)
            zTreeObj = $.fn.zTree.init($("#test1"), setting, zNodes);
        }
    })
}

var roleId;
var mids;

//复选框点击回调onCheck事件。
function zTreeOnCheck(event, treeId, treeNode) {
    var nodes = zTreeObj.getCheckedNodes(true);
    roleId = $("#roleId").val();
    mids = "mids=";

    /*console.log(nodes);
    console.log(event);
    console.log(treeId);
    console.log(treeNode);*/

    for (var i = 0; i < nodes.length; i++) {
        if (i < nodes.length - 1) {
            mids = mids + nodes[i].id + "&mids=";
        } else {
            mids = mids + nodes[i].id;
        }
    }
    //console.log(mids);
    /* $.ajax({
         type:"post",
         url:ctx + "/role/addGrant",
         datatype: "json",
         data:mids + "&roleId=" +roleId,
         success:function(data){
             console.log(data)
         }
     })*/
}

layui.use(['layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer;
    $("#insertTrue").click(function () {
        if (!mids||!roleId) {
            layer.msg('请选择角色授权!!!', {icon: 5});
            return;
        }
        var index = top.layer.msg('数据提交中,请稍候...', {icon: 16, time: false, shade: 0.8});
        /*console.log(mids);
        console.log(roleId);*/
        $.ajax({
            type: "post",
            url: ctx + "/role/addGrant",
            datatype: "json",
            data: mids + "&roleId=" + roleId,
            success: function (data) {
                if (data.code == 200) {
                    setTimeout(function () {
                        layer.close(index);
                        layer.msg("操作成功!!!")
                        //layer.closeAll("iframe")
                        //刷新父页面
                        parent.location.reload();
                    }, 500)
                } else {
                    layer.msg(result.msg, {
                        icon: 5
                    })
                }

            }
        })
    })
    /**
     * 关闭弹出层
     */
    $("#insertFalse").click(function(){
        var index = parent.layer.getFrameIndex(window.name);
        // 再执行关闭
        parent.layer.close(index);
    })

})
