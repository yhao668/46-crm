layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    //提交表单
    form.on('submit(saveBtn)', function(data){
        // data.field.username;
        //data.field.password;
        var fieldData=data.field;

        // fieldData.old_password;
        // fieldData.new_password;
        // fieldData.again_password;

        $.ajax({
            type:"post",
            url:ctx+"/user/toPasswordUpdate",
            data:{
                oldPwd:fieldData.old_password,
                newPwd:fieldData.new_password,
                confirmPwd:fieldData.again_password
              },
            dataType:"json",
            success:function(data){
                //判断
                if(data.code==200){
                    layer.msg("修改成功了");
                    layer.msg("修改成功了，三秒后退出系统",function(){
                        // 退出系统后，删除对应的cookie
                        $.removeCookie("userIdStr", {domain:"localhost",path:"/crm"});
                        $.removeCookie("userName", {domain:"localhost",path:"/crm"});
                        $.removeCookie("trueName", {domain:"localhost",path:"/crm"});
                    });
                    //修改成功
                    window.parent.location.href=ctx+"/index";
                }else{
                    //修改失败
                    layer.msg("修改失败了");
                }
            }
        });
        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });

});