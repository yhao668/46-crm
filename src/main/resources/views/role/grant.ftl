<html>
<head>
    <#include "../common.ftl">
    <link rel="stylesheet" href="${ctx}/js/zTree_v3-3.5.32/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/lib/jquery-3.4.1/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/zTree_v3-3.5.32/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${ctx}/js/zTree_v3-3.5.32/js/jquery.ztree.excheck.js"></script>
</head>
<body>
<div id="test1" class="ztree"></div>
<div class="layui-form-item layui-row layui-col-xs12">
    <div class="layui-input-block">
        <button class="layui-btn layui-btn-lg" id="insertTrue">确认</button>
        <button class="layui-btn layui-btn-lg layui-btn-normal" id="insertFalse">取消</button>
    </div>
</div>
<input id="roleId" value="${roleId!}" type="hidden">
<script type="text/javascript">
    var ctx="${ctx}";
</script>
<script type="text/javascript" src="${ctx}/js/role/grant.js"></script>
</body>
</html>