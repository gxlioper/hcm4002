<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function () {
		getExam_type();
		getOccuphyexaclass();
		$("#exam_type").validatebox({
			required:true,
		});
		$("#occuphyexaclass").validatebox({
			required:true,
		});
		$("#template").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
	});
	
	function getExam_type(){
		$("#exam_type").combobox({
			url :'getDatadis.action?com_Type=TJLX',
			editable : false, //不可编辑状态
			panelHeight : 'auto',//自动高度适合 
			valueField : 'id',
			textField : 'name'
		});
		var exam_type = "<s:property value='exam_type'/>";
		if(exam_type != '0') {
			$("#exam_type").combobox('select',exam_type);
			$("#exam_type").combobox('setText',"<s:property value='exam_type_name'/>");
		}
	}
	function getOccuphyexaclass(){
		$("#occuphyexaclass").combobox({
			url :'getOccuphyclass.action',
			editable : false, //不可编辑状态
			panelHeight : 'auto',//自动高度适合 
			valueField : 'occuphyexaclassID',
			textField : 'occuphyexaclass_name'
		})
		$("#occuphyexaclass").combobox('select',"<s:property value='occuphyexaclassid'/>");
		$("#occuphyexaclass").combobox('setText',"<s:property value='occuphyexaclass_name'/>");
	}
	function validateAndSave(){
		if ($("#exam_type").combobox('getValue').trim()==''){
			 $('#exam_type').combobox().next('span').find('input').focus();
				return;
		}else if ($("#occuphyexaclass").combobox('getValue').trim()==''){
			 $('#occuphyexaclass').combobox().next('span').find('input').focus();
				return;
		}else if (document.getElementById("template").value ==""){
			$("#template").focus();
			return;
		}
		$.ajax({
			url:'validateOcctemplate.action',
			type:'post',
			async: false,
			data:{
				exam_type:$("#exam_type").combobox('getValue'),
				occuphyexaclassid:$("#occuphyexaclass").combobox('getValue'),
				id:$("#id").val(),
			},
			success:function(data){
				if(data=='no'){
					$.messager.alert('错误信息', "\"" + $("#exam_type").combobox('getText')+" - "+$("#occuphyexaclass").combobox('getText')+"\""+" 已经设置过模板！");
					return;
				} else {
					saveData();
				}
			},
			error:function() {
				return;
			}
		});
	};
	
	function saveData() {
		$.ajax({
			url:'saveOcctemplate.action', 
		    type: "post",
			data:{
				id:$("#id").val(),
				exam_type:$("#exam_type").combobox('getValue').trim(),
				occuphyexaclassid:$("#occuphyexaclass").combobox('getValue').trim(),
				template:$("#template").val(),
				remark:$("#remark").val(),
			  },          
			success: function(data){
			  	 $.messager.alert('提示信息', data);
			  	 if(!$('#add').is(':checked')){
			  		 $("#edit").dialog("close");
			  	 }else{
			  		$("#template").val(''),
					$("#remark").val(''),
					$("#exam_type").combobox('setValue','')
					$("#occuphyexaclass").combobox('setValue','')
			  	 }
			  	 getData();
			    },
			 error:function(){
			    	$("#edit").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }
		});
	}
	 function continuityadd(){
		 
		 if($('#add').is(':checked')){
			$('#add').attr('checked',false)
		}else{
			$('#add').attr('checked',true)
		} 
	} 
</script>
<fieldset style="margin:5px; height:80%;padding-top:20px;">
		<legend><strong>职业病团报模板编辑</strong></legend>
			<div class="formdiv">
				<dl>
	    	       <input type="hidden"  id="id" value="<s:property value='id'/>"   />
	    	       <dt>职业体检大类别</dt>
	    	       <dd><input id="exam_type" value="" required='true'  ></dd>
	    	    </dl>
				<dl>
	    	       <dt>职业体检类别</dt>
	    	       <dd><input id="occuphyexaclass" required='true'  ></dd>
	    	    </dl>
				<dl>
	    	       <dt>模板名称</dt>
	    	       <dd><input id="template"  class="textinput" value="<s:property value='template'/>" ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>备注</dt>
	    	       <dd><input id="remark"  class="textinput" value='<s:property value='remark'/>' ></dd>
	    	    </dl>
	    	    </div>

<div class="dialog-button-box">
	<div class="inner-button-box">
		<s:if  test="id==null">
				<div style="float:left;padding-left:20px;font-size:16px;cursor:pointer;" onclick="continuityadd();"><input type="checkbox" onclick="continuityadd();" id="add" style="cursor:pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="validateAndSave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit').dialog('close')">关闭</a>
	</div>
</div>
</fieldset> 