<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
//---------------------保存体检类别-----------------------------
$(document).ready(function (){
	weihaibm();
	//重写验证框编码已存在
 	$('#hazardclass_code').validatebox({
		 required: true,  
		validType:'equals_bm'
	})  
	//给体检编码赋值
	var zfsdf="<s:property value='hazardclass_code'/>";
	document.getElementById("hazardclass_code").value=zfsdf;
	$('#hazardclass_code').focus();
	$('#hazardclass_name').validatebox({    
	    required: true
	});  
	
	$('#order').validatebox({
		validType:'IsNumber'
	});
	

})
var bmvalue="<s:property value='hazardclass_code'/>";
function saveoccuhazardclass(){
	if($('#hazardclass_code').val()==""||$('#hazardclass_code').val()==null||$('#hazardclass_code').val().length<1){
		$('#hazardclass_code').focus();
		return;
	}
	if(!yzbm($('#hazardclass_code').val()) && $('#hazardclass_code').val()!=bmvalue){
		$('#hazardclass_code').focus();
		return;
	}
	if($('#hazardclass_name').val()==""||$('#hazardclass_name').val()==null){
		$('#hazardclass_name').focus();
		return;
	}
	if(!/^[0-9]{1,20}$/.test($('#order').val()) && $('#order').val()!=""){
		$('#order').focus();
		return;
	}
 	var model={
 		'hazardclassID':$('#hazardclassID').val(),
		'hazardclass_code':$('#hazardclass_code').val(),
		'hazardclass_name':$("#hazardclass_name").val(),     
		'order':$("#order").val(),     
		'remark':$("#remark").val()
 	} 
 	
	$.ajax({
		url:"saveZYB_OccuhazardClass.action",
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert("提示信息",data,"info");
			if(!$('#lianxu').is(':checked')){
				$('#dlg-custedit').dialog('close')
			}else{
				$('#hazardclass_code').val('');
				$("#hazardclass_name").val('');    
				$("#order").val('');  
				$("#remark").val('');
			}
			$('#OccuhazardClassshow').datagrid('reload');
			
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	}) 
	
}
function dianji(){
	if($('#lianxu').is(':checked')){
		$('#lianxu').attr('checked',false)
	}else{
		$('#lianxu').attr('checked',true)
	}
}

//验证编码已存在
function weihaibm(){
	$.extend($.fn.validatebox.defaults.rules, {
	    equals_bm: {
	    	validator: function(value,param){
				var fla = true;
					
					if(value!=""&&value!=bmvalue){
							$.ajax({
								url:'getVerificationHazardclassCode.action',
								data:{hazardclass_code:value},
								type:'post',
								async:false,
								dataType:'json',
								success:function(data){
									
									if(data!=1){
										fla = true;
									}else{
										fla = false;
									}
								},
								error:function(){
									$.messager.alert("警告信息","编码唯一性验证失败","error");
								}
							}) 
					} 
					
				return fla;
			},
			message: '编码已存在'
	    }
	});
};

function yzbm(value){
	var fla=false;
	$.ajax({
		url:'getVerificationHazardclassCode.action',
		data:{hazardclass_code:value},
		type:'post',
		async:false,
		success:function( data ){
			if(data!='1'){
				fla = true;
			}
		},
		error:function(){
			$.messager.alert("警告信息","编码唯一性验证失败","error");
		}
	})
	return fla; 
} 
</script>
<input type="hidden"  value="<s:property value='hazardclassID'/>"  name=""   id="hazardclassID" />

 <fieldset style=" margin:5px;margin-bottom:3px;margin-top:3px;padding-top:40px;padding-bottom: 40px;">
	<legend><strong>职业危害类别编辑</strong></legend> 
	<div class=formDiv   >
		<dl>
			<dt>危害类别编码</dt>
			<dd>
				<input type="text"   maxlength="10"   class="textinput"    value=""      id="hazardclass_code"    style="width: 260px;height:26px"  />
			</dd>
		</dl>
		<dl>
			<dt>危害类别名称</dt>
			<dd>
				<input type="text"   maxlength="25" class="textinput" value="<s:property value='hazardclass_name'/>"     id="hazardclass_name"    style="width: 260px;height:26px"  />
			</dd>
		</dl>
		<dl>
			<dt>显示顺序</dt>
			<dd><input type="text" maxlength="4"	 class="textinput"	 value="<s:property  value='order'/>"   id="order"   style="width: 260px;height: 26px"  /> </dd>
		</dl>
		<dl>
			<dt>备注</dt>
			<dd><input type="text"	 maxlength="100"  class="textinput" value="<s:property  value='remark'/>"   id="remark"   style="width: 260px;height:26px"  /> </dd>
		</dl>
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
			<s:if  test="hazardclassID==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="lianxu"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
		    <a href="javascript:saveoccuhazardclass();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>