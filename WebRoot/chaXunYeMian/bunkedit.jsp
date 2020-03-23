<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$("#bunk").validatebox({
			required:true,
			validType:'maxLength[30]'
		})
	});

 	function savebunk(){
		if (document.getElementById("bunk").value == ''){
			$("#bunk").focus();
			return;
		}
	$.ajax({
	    url:'savebunk.action', 
	    type: "post",
		data:{
			exam_num:$("#exam_num_s").val(),
			arch_num:$("#arch_num_s").val(),
			bunk:$("#bunk").val(),
		  },          
		success: function(data){  
		  	 $.messager.alert('提示信息', "分配成功！");
		  	 $("#dlg-bunk").dialog("close");
		  	getgroupuserGrid();
		    },
		 error:function(){
		    	 $("#dlg-bunk").dialog("close");
		    	$.messager.alert('提示信息', "用户操作失败！",function(){});
		    }  
	});

 	}
</script>
	
<div class="formdiv" style="paddig-left:140px;padding-top:60px;">
<input type="hidden" name="arch_num" id="arch_num_s" value="<s:property value="arch_num" />" />
	    	    <dl>
	    	       <dt><input type="hidden" name="exam_num" id="exam_num_s" value="<s:property value="exam_num" />" />
	    	       		床位</dt>
	    	       <dd><input id="bunk"  value="<s:property value="bunk"/>"  class="textinput" style="height:30px;width:240px;"></dd>
	    	       <dt  class="" style="position: absolute; height: 25px; width: 100px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    	      </dl>
	    	      
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;"onclick="savebunk()" >保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-bunk').dialog('close')">关闭</a>
	</div>
</div>

