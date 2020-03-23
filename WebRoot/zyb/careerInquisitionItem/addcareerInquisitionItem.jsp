<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
//---------------------保存体检类别-----------------------------
$(document).ready(function (){
	weihaibm();
	//重写验证框编码已存在
 	$('#item_code').validatebox({
		 required: true,  
		 validType: 'equals_bm'
 	})
	//给体检编码赋值
	var zfsdf="<s:property value='item_code'/>";
	document.getElementById("item_code").value=zfsdf;
	$('#item_code').focus();
	$('#item_name').validatebox({    
	    required: true
	});  
	
	$('#order').validatebox({
		validType:'IsNumber'
	});
	var  sex1="<s:property value='sex'/>"; 
	$('#sex').val(sex1);
	var isshow1="<s:property value='isshow'/>";
	if(isshow1=='0'){
		$('#isshow').attr("checked",true );
	}

})
var bmvalue="<s:property value='item_code'/>";
function saveoccuhazardclass(){
	if($('#item_code').val()==""||$('#item_code').val()==null||$('#item_code').val().length<1){
		$('#item_code').focus();
		return;
	}
	if(!yzbm($('#item_code').val()) && $('#item_code').val()!=bmvalue){
		$('#hazardclass_code').focus();
		return;
	}
	if($('#item_name').val()==""||$('#item_name').val()==null){
		$('#item_name').focus();
		return;
	}
	if(!/^[0-9]{1,20}$/.test($('#order').val()) && $('#order').val()!=""){
		$('#order').focus();
		return;
	}
	var bss="1";
	var af=$('#add_isshow').is(':checked');
		if(af){
			bss="0";
		}
 	var model={
 		'item_id':$('#item_id').val(),
		'item_code':$('#item_code').val(),
		'item_name':$("#item_name").val(),     
		'order':$("#order").val(),     
		'sex':$("#sex").val(),
		'isshow':bss
 	} 
 	
	$.ajax({
		url:"saveZybCareerInquisitionItemCode.action",
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert("提示信息",data,"info");
			if(!$('#lianxu').is(':checked')){
				$('#dlg-custedit').dialog('close')
			}else{
				$('#item_id').val('');
				$('#item_code').val('');
				$("#item_name").val('');    
				$('#isshow').attr('checked',false);
				var zhi = Number($('#order').val())+1;
				$('#order').val(zhi);
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
								url:'getZybCareerInquisitionItemCode.action',
								data:{item_code:value},
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
<input type="hidden"  value="<s:property value='item_id'/>"  name=""   id="item_id" />

 <fieldset style=" margin:5px;margin-bottom:3px;margin-top:3px;padding-top:40px;padding-bottom: 40px;">
	<legend><strong>职业体检问诊项目编辑</strong></legend> 
	<div class=formDiv   >
		<dl>
			<dt>职业问诊项目编码</dt>
			<dd>
				<input type="text"   maxlength="20"   class="textinput"    value=""      id="item_code"    style="width: 260px;height:26px"  />
			</dd>
		</dl>
		<dl>
			<dt>职业问诊项目名称</dt>
			<dd>
				<input type="text"   maxlength="50" class="textinput" value="<s:property value='item_name'/>"     id="item_name"    style="width: 260px;height:26px"  />
			</dd>
		</dl>
		<dl>
			<dt>
				适用性别
			</dt>
			<dd>
				<input type="hidden"    value="<s:property value='sex'/>"     id="sex1"    style="width: 260px;height:26px"  />
				<select id="sex"   style="width:260px;">
					<option  value='0'>通用</option>
					<option  value='1'>男</option>
					<option	 value='2'>女</option>
				</select>
			</dd>
		</dl>
		<dl>
			<dt>显示顺序</dt>
			<dd><input type="text" maxlength="4"	 class="textinput"	 value="<s:property  value='order'/>"   id="order"   style="width: 260px;height: 26px"  /> </dd>
		</dl>
		<dl>
			<dt></dt>
			<dd>
				<s:if test="isshow==0">
					<input type="checkbox"     id="add_isshow"   checked="checked"   style="cursor: pointer;"   />
				</s:if>
				<s:else>
					<input type="checkbox"     id="add_isshow"    style="cursor: pointer;"   />
				</s:else>
				 是否显示在体检表
			</dd>
		</dl>
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
			<s:if  test="item_id==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="lianxu"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
		    <a href="javascript:saveoccuhazardclass();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>