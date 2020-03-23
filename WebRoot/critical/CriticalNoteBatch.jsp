<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function(){
        $('#give_notice_type').val($('#h_give_notice_type').val());
        $('#done_flag').val($('#h_done_flag').val());
        //$('#note').text($('#c_note').val());
    })
    function saveExamCriticalDetail(){
        $.ajax({
            url:'saveCritical.action',
            type:"post",
            data:{
                ids:$("#ids").val(),
                give_notice_type:$('#give_notice_type').val(),
                note:$("#note1").val(),
                done_flag:$('#done_flag').val()
            },
            success : function(data) {
                $.messager.alert("操作提示",data);
                $("#batch_critical_edit").dialog("close");
                getGrid();
            },
            error : function() {
                $.messager.alert('提示信息', '操作失败！', 'error');
            }
        });
    }
</script>
<div style="padding: 5px 5px">
	<input type="hidden" name="ids" id="ids" value="<s:property value="ids"/>"/>
	<input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>
	<input type="hidden"  id="h_give_notice_type" value='<s:property value="give_notice_type"/>'  />
	<input type="hidden" name="c_done_flag" id="h_done_flag" value="<s:property value="done_flag"/>" />
	<input type="hidden" name=c_note id="c_note" value="<s:property value="note"/>" />
	<fieldset style="width:96%;height:50px;margin:2px;">
		<legend><strong>基本信息</strong></legend>
		<p style="font-size:17px">
			&nbsp;&nbsp;姓名：<s:property value="user_name"/>&nbsp;&nbsp;
			性别：<s:property value="sex"/>&nbsp;&nbsp;
			年龄：<s:property value="age"/>&nbsp;&nbsp;
			电话：<s:property value="phone"/>
		</p>
	</fieldset>
	<fieldset style="width:96%;height:350px;text-align: center;">
		<legend><strong>危急值处理</strong></legend>
		<div class="formdiv" style="text-align: center;padding-left:60px" >
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
			    		<textarea rows="3" id="note1"   style="width:310px;height:180px;padding: 0 0">
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
		<div class="inner-button-box" >
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="saveExamCriticalDetail()">处理</a>
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_Crisave(0)">保存</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#batch_critical_edit').dialog('close')">关闭</a>
		</div>
	</div>

</div>
