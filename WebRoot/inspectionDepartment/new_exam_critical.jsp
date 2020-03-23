<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" >
$(document).ready(function () {
	
	//获取大类
	$("#critical_class_parent_name").combobox({
		url:'getCriticalClassD.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight:200,
		valueField : 'id',
		textField : 'critical_class_name',	
        onLoadSuccess : function(data){
        	 var critical_id= '<s:property value="critical_class_parent_name"/>';
			 if(critical_id >0){				
				 $('#critical_class_parent_name').combobox('setValue', critical_id);
			 }else{
				 $('#critical_class_parent_name').combobox('setValue', data[0].id);
			 }
		}
	});
	
	$("#critical_class_parent_name").combobox({
		onChange: function (n) {
		getCriticalClassId(n);
		}
	});
	
	function getCriticalClassId(parentId){
		//获取子类
		$("#critical_class_name").combobox({
			url:'getCriticalClass.action?parentId='+parentId,
			editable : true, //不可编辑状态
			cache : false,
			panelHeight:200,
			valueField : 'id',
			textField : 'critical_class_name',	
	        onLoadSuccess : function(data){
	        	 var critical_id= '<s:property value="critical_class_name"/>';
				 if(critical_id >0){				
					 $('#critical_class_name').combobox('setValue', critical_id);	
				 }else{
					 $('#critical_class_name').combobox('setValue', data[0].id);
				 }
			}
		});
	}
	

	
	//获取疾病
	$("#disease_id").combobox({
		url:'getDisease.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight:200,
		valueField : 'id',
		textField : 'disease_name',	
        onLoadSuccess : function(data){
        	 var disease_id= '<s:property value="disease_id"/>';
			 if(disease_id >0){				
				 $('#disease_id').combobox('setValue', disease_id);	
			 }else{
				 //$('#disease_id').combobox('setValue', data[0].id);
			 }
		}
	});
	
	//获取危急值等级
	$("#critical_class_level").combobox({
		url:'getCriticalLevel.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight:200,
		valueField : 'id',
		textField : 'critical_class_level',	
        onLoadSuccess : function(data){
        	 var critical_level_id= '<s:property value="critical_class_level"/>';
			 if(critical_level_id >0){				
				 $('#critical_class_level').combobox('setValue', critical_level_id);	
			 }else{
				 $('#critical_class_level').combobox('setValue', data[0].id);
			 }
		}
	});
});
	

	//手动登记危急值保存
 function f_criticalSave(){
	if (document.getElementsByName("critical_class_parent_name")[0].value == 0){
		$("#critical_class_parent_name").focus();
		return;
	}else if(document.getElementsByName("critical_class_name")[0].value == 0){
		$("#critical_class_name").focus();
		return;
	}else if (document.getElementsByName("critical_class_level")[0].value == 0){
		$("#critical_class_level").focus();
		return;
	}else if (document.getElementById("exam_result").value == ''){
		$("#exam_result").focus();
		return;
	}
	$.ajax({
		url:'saveCriticalDetail.action',  
		data:{
			critical_id1:$("#critical_id1").val(),
			exam_num:$("#exam_num").val(),
			critical_class_parent_name:document.getElementsByName("critical_class_parent_name")[0].value,
			critical_class_name:document.getElementsByName("critical_class_name")[0].value,
			critical_class_level:document.getElementsByName("critical_class_level")[0].value,
			disease_id:document.getElementsByName("disease_id")[0].value,
			exam_result:document.getElementById("exam_result").value,
			critical_suggestion:document.getElementById("critical_suggestion").value,
			//disease_num:document.getElementById("disease_num").value,
		  },          
		type: "post",  
		success: function(data){
		  	 $.messager.alert('提示信息', data);
		  	 $("#dlg-add").dialog("close");
		  	 getExamCriticalDetail($("#exam_num").val());
		    },
		    error:function(){
		    	 $("#dlg-add").dialog("close");
		    	 $.messager.alert('提示信息', "用户操作失败！",function(){});
		    }  
		});

	}
	
 function selectResult(){
		$("#dlg-xz").dialog({
			title:'选择检查结果',
			width : 800,
			height :550,
			href : 'toSelectResult.action?exam_num='+$("#exam_num").val()
		});
		$("#dlg-xz").dialog("open");
	}
</script>
	<fieldset style="margin:5px;padding-right:20px;padding-top:20px; ">
	<legend><strong>手工登记危急值</strong></legend>
  		<div class="formdiv">
  		<input type="hidden" id="critical_id1" value="<s:property value="critical_id1" />"/>
	   	    <dl>
	   	       <dt>大类</dt>
	   	       <dd><select class="easyui-combobox" id="critical_class_parent_name" name="critical_class_parent_name"					
				data-options="height:30,width:200,panelHeight:'auto'"></select>
	   	       </dd>
	   	    </dl>   
	   	    <dl>
	   	       <dt>子类</dt>
	   	       <dd><select class="easyui-combobox" id="critical_class_name" name="critical_class_name"					
				data-options="height:30,width:200,panelHeight:'auto'"></select>
	   	       </dd>
	   	    </dl>
	   	    <dl>
	   	       <dt>等级</dt>
	   	       <dd><select class="easyui-combobox" id="critical_class_level" name="critical_class_level"					
				data-options="height:30,width:200,panelHeight:'auto'"></select>
	   	       </dd>
	   	    </dl>
	   	    <dl>
	   	       <dt>疾病</dt>
	   	       <dd><select class="easyui-combobox" id="disease_id" name="disease_id"					
				data-options="height:30,width:200,panelHeight:'auto'"></select>
	   	       </dd>
	   	    </dl>
	   	         
	   	    <dl>
	   	       <dt>危急值结果</dt>
	   	       <dd > 
	   	       	   <textarea style="width:350px;resize:none;height:100px;" id="exam_result"><s:property value="exam_result"/></textarea>
	    	       <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="selectResult();">选择</a>
	   	       </dd >
	   	    </dl>
	   	
	   	    <dl>
	   	       <dt>危急值建议</dt>
	   	       <dd > 
	   	       	   <textarea style="width:350px;resize:none;height:100px;" id="critical_suggestion"><s:property value="critical_suggestion"/></textarea>
	   	       </dd >
	   	    </dl>
	    </div>
	 </fieldset>
	<div id="dlg-xz" class="easyui-dialog"  data-options="width: 800,height: 600,closed: true,cache: false,modal: true"></div>
<div class="dialog-button-box">
	<div class="inner-button-box" >
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_criticalSave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-add').dialog('close')">关闭</a>
	</div>
</div>

