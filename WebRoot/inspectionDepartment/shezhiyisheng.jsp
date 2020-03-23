<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
	$(function(){
	 	$('#jc').combobox({    
		    url:'getDoctor.action',    
		    valueField:'id',    
		    textField:'chi_name',
		    editable:true
		});
		$('#sh').combobox({    
		    url:'getDoctor.action',    
		    valueField:'id',    
		    textField:'chi_name',
		    editable:true
		}); 
		
	})
</script>
<div style="margin-top: 30px;margin-left:65px;">
	<dl>
		<dt>
			检查医生
		</dt>
		<dd>
			<input id="jc" name="jc" value=""   style="width: 100px;height: 26px;">  
		</dd>
		<dt style="margin-left: 30px;">
			审核医生
		</dt>
		<dd>
			<input id="sh" name="sh" value=""  style="width: 100px;height: 26px;"> 
		</dd>
	</dl>
</div>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
		<div>
		    <a href="javascript:saveExam_result()" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#shezhiyisheng').dialog('close')">关闭</a>
		</div>
	</div>
</div>
