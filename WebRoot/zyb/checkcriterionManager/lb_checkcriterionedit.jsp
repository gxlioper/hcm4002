<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
		
	$(function(){
		//var ss="<s:property value='criterion_name'/>";
		
		$('#criterion_name').combobox({
		 	 url:'getLbcriterionManagerComxobox.action',    
		    valueField:'criterionID',    
		    textField:'criterion_name',
		    onLoadSuccess:function(){
		    	var sa = $('#criterion_name').combobox('getData');
		    	for(var i = 0 ; i < sa.length ; i++){
		    		if($('#criterionID').val()==sa[i].criterionID){
		    			$('#criterion_name').combobox('setValue',$('#criterionID').val());
		    			break;
		    		}
		    	}
		    }
		});
		
		
		$('#DISORDER').validatebox({    
		    required: true
		}); 
	})
		
	
	function f_saveche_c(){
		if($("#criterion_name").combobox('getValue')=="" || $("#criterion_name").combobox('getText')==""){
			$.messager.alert("提示信息","请选择检查依据！","erorr");	
			return;
		}
		if($('#DISORDER').val()==""){
			$('#DISORDER').focus();
			return;
		}
		$.ajax({
			url:'saveLbcriterionManager.action', 
			type: "post",
			data:{
				criterionID:$("#criterion_name").combobox('getValue'),
				occuphyexaclassID:$('#occuphyexaclassID').val(),
				hazardfactorsID:$('#hazardfactorsID').val(),
				DISORDER:$('#DISORDER').val(),
				RID:$('#RID').val()
				},          
			success: function(data){
				 $.messager.alert('提示信息', data);
				 if(!$('#add').is(':checked')){
			  		 $("#edit_dlg").dialog("close");
			  	 }else{
			  		$("#criterion_name").val('')
			  	 };
				 getData();
				    },
				 error:function(){
				    	$("#edit_dlg").dialog("close");
				    	$.messager.alert('提示信息', "用户操作失败！",function(){});
				    }
			})
		
	};
	function continuityadd(){
		 if($('#add').is(':checked')){
			$('#add').attr('checked',false)
		}else{
			$('#add').attr('checked',true)
		} 
	} 
</script>
<input type="hidden"  id='occuphyexaclassID'   value="<s:property  value='occuphyexaclassID'/>"  />
<input type="hidden"  id='hazardfactorsID'   value="<s:property  value='hazardfactorsID'/>"  />
<input type="hidden"  id='RID'   value="<s:property  value='RID'/>"  />
<input type="hidden"  id='criterionID'   value="<s:property  value='criterionID'/>"  />
<fieldset style="margin:5px; height:80%;">
		<legend><strong>检查依据信息编辑</strong></legend>
			<div class="formdiv"  style="margin-top:100px;" >
				<dl>
	    	       <dt><input id="criterionID" type="hidden" value="<s:property value='criterionID'/>" />
	    	       	依据内容</dt>
	    	       <dd><input id="criterion_name"  style="width:400px;height: 26px;" name="criterion_name" value="">  </dd>
	    	    </dl>
				<dl>
	    	       <dt>顺序码</dt>
	    	       <dd><input id="DISORDER"  style="width:400px;height: 26px;" name="DISORDER" value = "<s:property value ='DISORDER'/>" value="">  </dd>
	    	    </dl>
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
		<s:if  test="criterionID==null">
				<div style="float:left;padding-left:20px;font-size:16px;cursor:pointer;" onclick="continuityadd();"><input type="checkbox" onclick="continuityadd();" id="add" style="cursor:pointer;" />&nbsp;&nbsp;连续添加</div>
		</s:if>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_saveche_c();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>