<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link type="text/css"  rel="stylesheet" href="<%=request.getContextPath()%>/scripts/layui-v2.5.5/layui/css/layui.css">
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>
    <title>Title</title>
    <style type="text/css">
        .layui-form-select{
            width: 200px;
        }
        .layui-form-label{
            padding-left: 0px;
            text-align:left;
        }
        .layui-input-inline{
            margin-left: 200px;
        }
    </style>
</head>

<body style="background: #f2f0f0">

<script type="text/javascript"  src="<%=request.getContextPath()%>/scripts/layui-v2.5.5/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script  type="text/javascript" src="<%=request.getContextPath()%>/scripts/layui-v2.5.5/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<input type="hidden" id="zyb_set_id" value="<s:property value="id"/>">
<div class="layui-row" style="margin-right: 5px;margin-left: 5px" >
    <div class="layui-card layui-col-md12" style="margin: 0px 0px;height: 100%">
        <div class="layui-card-header">操作</div>
        <div class="layui-card-body">
            <button type="button" onclick="hazaard_save()"  class="layui-btn layui-btn-primary">保存</button>
            <button type="button" onclick="javascript:window.close()"  class="layui-btn layui-btn-primary">关闭</button>
        </div>
    </div>
</div>
<div class="layui-row" >
    <div class="layui-card" style="margin: 5px 5px;">
        <div class="layui-card-header">体检类别</div>
        <div id="tjlb_type" class="layui-card-body">

        </div>
    </div>
</div>
<div class="layui-row" style="height:618px;margin-right: 5px;margin-left: 5px" >
    <div class="layui-card layui-col-md2" style="margin: 0px 0px;height: 100%">
        <div class="layui-card-header">危害类别</div>
        <div class="layui-card-body">
            <ul id="tt"></ul>
        </div>
    </div>
    <div class="layui-card layui-col-md10" style="margin: 0px 0px;height:100%">
        <div class="layui-card-header">
            <form class="layui-form" action="">
                <div class="layui-inline layui-col-md3">
                    <label class="layui-form-label" style="width: 80px">危害因素</label>
                    <div class="layui-input-block" >
                        <input type="text" name="title" id="hazard_name" ondblclick="zywhys_html"  value=""   placeholder="请输入查询内容" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline layui-col-md2">
                    <div class=""  style="margin-left: 10px">
                        <button type="button" onclick="zywhys_html()"  class="layui-btn layui-btn-primary">查询</button>
                    </div>
                </div>
                <div class="layui-inline layui-col-md2">
                    <div class="layui-input-block">
                        <input type="checkbox" id="yxxm" title="已选因素"  lay-filter="yxxm">
                    </div>
                </div>
            </form>
        </div>
        <div id="zywhys_data"  class="layui-card-body" style="height:90%;overflow:auto">
        </div>
    </div>
</div>
<script type="text/javascript" >
    //禁用Enter键表单自动提交
    document.onkeydown = function(event) {
        var target, code, tag;
        if (!event) {
            event = window.event; //针对ie浏览器
            target = event.srcElement;
            code = event.keyCode;
            if (code == 13) {
                tag = target.tagName;
                if (tag == "TEXTAREA") { return true; }
                else { return false; }
            }
        }
        else {
            target = event.target; //针对遵循w3c标准的浏览器，如Firefox
            code = event.keyCode;
            if (code == 13) {
                tag = target.tagName;
                if (tag == "INPUT") { return false; }
                else { return true; }
            }
        }
    };

</script>
<script  type="text/javascript" src="<%=request.getContextPath()%>/examSet/examSet_hazard.js"></script>

</body>
</html>
