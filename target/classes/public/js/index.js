layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
    
    //提交表单
    form.on('submit(login)', function(data){
       // data.field.username;
        //data.field.password;
        var fieldData=data.field;
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        //验证
        if(fieldData.username=='undefinded'||fieldData.username.trim()==""){
            layer.msg("用户名不能为空");
            return false;
        }

        if(fieldData.password=='undefinded'||fieldData.password.trim()==""){
            layer.msg("用户密码不能为空");
            return false;
        }
        //ajax提交，登录
        $.ajax({
            type:"post",
            url:ctx+"/user/login",
            data:{
                "username":fieldData.username,
                "password":fieldData.password
            },
            dataType:"json",
            success:function(data){
                //判断
                if(data.code==200){

                    if( $(":checkbox[id='rememberMe']").is(":checked")){
                        //将数据存储到Cookie
                        $.cookie("userIdStr",data.result.userIdStr,{expires:7});
                        $.cookie("userName",data.result.userName,{expires:7});
                        $.cookie("trueName",data.result.trueName,{expires:7});
                    }else{
                        //cookie  maxAge=7
                        //将数据存储到Cookie
                        $.cookie("userIdStr",data.result.userIdStr);
                        $.cookie("userName",data.result.userName);
                        $.cookie("trueName",data.result.trueName);
                    }

                    //跳转页面
                    window.location.href=ctx+"/main";


                }else{
                    layer.msg("登录失败了");
                }
            }
        })
        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });

});