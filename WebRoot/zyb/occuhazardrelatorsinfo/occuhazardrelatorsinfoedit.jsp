<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function () { 
		 
		 $("#hazard_name").validatebox({
			required:true,
		});
		
		 $("#hazardclass_name").validatebox({
			required:true,
				});
		 $("#occuphyexaclass_name").validatebox({
				required:true,
			}); 
		 loadcombox();
	});
		
	function loadcombox(){
		 $("#hazardclass_name").combobox({
				url :'getOccuhazardclass.action',
				editable : true, //可编辑状态
				panelHeight : 'auto',//自动高度适合
				valueField : 'hazardclassID',
				textField : 'hazardclass_name',
				onLoadSuccess:function(){
					var sa = $('#hazardclass_name').combobox('getData');
					var o_id = "<s:property value='hazardclassID'/>"
			    	for(var i = 0 ; i < sa.length ; i++){
			    		if(o_id==sa[i].hazardclassID){
			    			$('#hazardclass_name').combobox('setValue',o_id);
			    			break;
			    		} else {
			    			$('#hazardclass_name').combobox('setValue',sa[0].hazardclassID);
			    		}
			    	}
				}
			}); 
		 
		 $("#hazard_name").combobox({
			url :'getgetOccuhazardfactor.action',
			editable : true, //可编辑状态
			panelHeight : 'auto',//自动高度适合 
			valueField : 'hazardfactorsID',
			textField : 'hazard_name',
			onLoadSuccess:function(){
				var sa = $('#hazard_name').combobox('getData');
				var o_id = "<s:property value='hazardfactorsID'/>"
		    	for(var i = 0 ; i < sa.length ; i++){
		    		if(o_id==sa[i].hazardfactorsID){
		    			$('#hazard_name').combobox('setValue',o_id);
		    			
		    			break;
		    		} else {
		    			$('#hazard_name').combobox('setValue',sa[0].hazardfactorsID);
		    		}
		    	}
			}
		}); 
 	 
	 $("#occuphyexaclass_name").combobox({
		url :'getOccuphyclass.action',
		editable : true, //可编辑状态
		panelHeight : 'auto',//自动高度适合
		valueField : 'occuphyexaclassID',
		textField : 'occuphyexaclass_name',
		onLoadSuccess:function(){
			var sa = $('#occuphyexaclass_name').combobox('getData');
			var o_id = "<s:property value='occuphyexaclassID'/>"
	    	for(var i = 0 ; i < sa.length ; i++){
	    		if(o_id==sa[i].occuphyexaclassID){
	    			$('#occuphyexaclass_name').combobox('setValue',o_id);
	    			break;
	    		} else {
	    			$('#occuphyexaclass_name').combobox('setValue',sa[0].occuphyexaclassID);
	    		}
	    	}
		}
	});  
	}
		
	function f_save(){
		 if($("#hazardclass_name").combobox('getValue').trim()==''){
				//$("#hazardclass_name_i").focus();
				$('#hazardclass_name').combobox().next('span').find('input').focus();
				return;
		}else if($("#hazard_name").combobox('getValue').trim()==''){
			//$("#hazard_name_i").focus();
			$('#hazard_name').combobox().next('span').find('input').focus();
			return;
		}else if($("#occuphyexaclass_name").combobox('getValue').trim()==''){
			//$("#occuphyexaclass_name_i").focus();
			$('#occuphyexaclass_name').combobox().next('span').find('input').focus();
			return;
		}
		$.ajax({
			url:'saveOccuhazardrelatorsinfo.action', 
			type: "post",
			data:{
				id:$("#id").val(),
				hazardclassID:$("#hazardclass_name").combobox('getValue').trim(),
				hazardfactorsID:$("#hazard_name").combobox('getValue').trim(),
				occuphyexaclassID:$("#occuphyexaclass_name").combobox('getValue').trim(),
				diseaseandtaboo:$("#diseaseandtaboo").val(),
				checkcontent:$("#checkcontent").val(),
				checkcriterion:$("#checkcriterion").val(),
				followdisease:$("#followdisease").val(),
				examperiod:$("#examperiod").val(),
				remark:$("#remark").val(),
				},          
			success: function(data){
				 $.messager.alert('提示信息', data);
				 $("#edit_dlg").dialog("close");
				 getData();
				    },
				 error:function(){
				    	$("#edit_dlg").dialog("close");
				    	$.messager.alert('提示信息', "用户操作失败！",function(){});
				    }
			})
		
	}
</script>
<%-- <fieldset style="margin:5px; height:90%;">
		<legend><strong>职业危害相关信息编辑</strong></legend> --%>
<div class="formdiv"  style="margin-bottom:350px;margin-top: 20px;">
			<input type="hidden" name="id" id="id" value="<s:property value='id'/>"   />
				<dl>
	    	       <dt>职业危害类别</dt>
	    	       <dd><input style="width:300px;" id="hazardclass_name"   required='true'  ></dd>
	    	    </dl>
				<dl>
	    	       <dt>职业危害名称</dt>
	    	       <dd><input style="width:300px;" id="hazard_name"   required='true' ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>
	    	       		职业体检类别</dt>
	    	       <dd><input style="width:300px;" id="occuphyexaclass_name"   required='true' ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>目标疾病与职业禁忌证</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="3" name="diseaseandtaboo" id="diseaseandtaboo" ><s:property value="diseaseandtaboo"/></textarea></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>检查内容</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="3" name="checkcontent" id="checkcontent" ><s:property value="checkcontent"/></textarea></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>检查依据</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="3" name="checkcriterion" id="checkcriterion" ><s:property value="checkcriterion"/></textarea></dd>
	    	    </dl>
	    	    <dl>
	    	    	<dt>检查周期</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="3" name="examperiod" id="examperiod" ><s:property value="examperiod"/></textarea></dd>
	    	    </dl>
	    	    <dl>
	    	    	<dt>疾病跟踪</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="3" name="followdisease" id="followdisease" ><s:property value="followdisease"/></textarea></dd>
	    	    </dl>
	    	    <dl>
	    	    	<dt>备注</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="4" name="remark" id="remark" ><s:property value="remark"/></textarea></dd>
	    	    </dl>
 </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_save();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
	</div>
</div>
	<!--  </fieldset> -->