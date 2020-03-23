<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function(){
        $('#give_notice_type').val($('#h_give_notice_type').val());
        $('#done_flag').val($('#h_done_flag').val());
        //$('#note').text($('#c_note').val());
        getLogGrid();
        $("#note").focus();
    })
    function saveExamCriticalDetail(){
        $.ajax({
            url:'saveCritical.action',
            type:"post",
            data:{
                id:$("#id").val(),
                give_notice_type:$('#give_notice_type').val(),
                note:$("#note").val(),
                done_flag:$('#done_flag').val()
            },
            success : function(data) {
                $.messager.alert("操作提示",data);
                $("#critical_edit").dialog("close");
                getGrid();
            },
            error : function() {
                $.messager.alert('提示信息', '操作失败！', 'error');
            }
        });
    }
    function getLogGrid(){
        var model={"id":$("#id").val(),
            "exam_num":$("#exam_num_s").val()
        };
        $("#criticalLogList").datagrid({
            url: 'criticalLogList.action',
            queryParams: model,
            pageSize:1000,//每页显示的记录条数，默认为10
            pageList:[5,10,15],//可以设置每页记录条数的列表
            height:400,
            columns:[[
                {align:"center",field:"create_time","title":"时间",width:140},
                {align:'center',field:"note","title":'内容',width:400},
                {align:"center",field:"chi_name","title":'操作人',width:80}
            ]],
            nowrap:false,
            rownumbers:true,
            singleSelect:true,
            collapsible:true,
            pagination: false,
            fitColumns:false,
            fit:true,
            striped:true
        });
    }


</script>
<div style="padding: 5px 5px">
	<input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>
	<input type="hidden"  id="h_give_notice_type" value='<s:property value="give_notice_type"/>'  />
	<input type="hidden" name="c_done_flag" id="h_done_flag" value="<s:property value="done_flag"/>" />
	<input type="hidden" name=c_note id="c_note" value="<s:property value="note"/>" />
	<input type="hidden" name = "exam_num_s" id="exam_num_s" value="<s:property value="exam_num"/>" />
	<fieldset style="width:98%;height:50px;float: right;margin: 5px;">
		<legend><strong>基本信息</strong></legend>
		<p style="font-size:17px">
			&nbsp;&nbsp;姓名：<s:property value="user_name"/>&nbsp;&nbsp;
			性别：<s:property value="sex"/>&nbsp;&nbsp;
			年龄：<s:property value="age"/>&nbsp;&nbsp;
			电话：<s:property value="phone"/>
		</p>
	</fieldset>
	<fieldset style="width:680px;height:365px;float: right;">
		<legend><strong>处理记录</strong></legend>
		<div id="criticalLogList"></div>
	</fieldset>
	<fieldset style="float: left;">
		<legend><strong>危急值处理</strong></legend>
		<div class="formdiv" style=" width:460px;height:350px">
			<dl>
				<dt style="width: 80px">通知方式</dt>
				<dd>
					<select id="give_notice_type" name="give_notice_type"  value='<s:property value="give_notice_type"/>'   style="width:310px;" >
						<option value=1 >电话</option>
						<option value=2 >短信</option>
					</select>
				</dd>
			</dl>
			<dl style="height: 190px">
				<dt  style="width: 80px">通知内容</dt>
				<dd>
					<textarea id='note'  rows="8" cols="35"></textarea>
					</textarea>
				</dd>
			</dl>
			<dl>
				<dt  style="width: 80px">处理结果</dt>
				<dd>
					<select id="done_flag"   style="width:310px;"   value='<s:property value="done_flag"/>'    name="done_flag" >
						<option value=0>未通知</option>
						<option value=1>已通知</option>
						<option value=2>未联系上</option>
					</select>
				</dd>
			</dl>
		</div>
	</fieldset>
	<div class="dialog-button-box">
		<div class="inner-button-box" style="text-align:left">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="saveExamCriticalDetail()">处理</a>
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_Crisave(0)">保存</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#critical_edit').dialog('close')">关闭</a>
		</div>
	</div>

</div>
