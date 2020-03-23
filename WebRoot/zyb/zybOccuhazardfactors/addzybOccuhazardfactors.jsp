<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
//---------------------保存体检类别-----------------------------
$(function(){
	getoccuphyexaclass();
	getcode();
 	$('#hazard_code').validatebox({    
    	required: true,
    	validType:['equals_bm','CHS']
    })  
 	var zfsdf="<s:property value='hazard_code'/>";
	document.getElementById("hazard_code").value=zfsdf;
    $('#hazard_name,#order').validatebox({ 
    	required: true
    })
    $('#order,#hazard_year').validatebox({
		validType:'IsNumber'
	}); 
 	if($('#hazardfactorsID').val()==""){
 		var order2="<s:property value='order2'/>";
 		$('#order').val(order2);
 	} 
})
/////////////////////////////////////////////////////获取职业危害类别////////////////////////////////////////////////
function getoccuphyexaclass(){
	$('#hazardclassID').combobox({
		url:'getOccuhazardfactorsList.action',
		valueField: 'hazardclassID',    
        textField: 'hazardclass_name',
        panelHeight:'auto',
        required: true,
        onLoadSuccess:function(){
        	var hazardclassID_s = "<s:property value='hazardclassID'/>";
        	if(hazardclassID_s!=''){
        		$('#hazardclassID').combobox('setValue',hazardclassID_s);
        	}else{
	        	var oo = $('#hazardclassID').combobox('getData');
	        	$('#hazardclassID').combobox('setValue',oo[0].hazardclassID);
        	}
        }
	})
}
///////////////////////////////////////////////////验证编码唯一//////////////////////////////////////////////////////
var bmvalue="<s:property value='hazard_code'/>"
//验证编码已存在
function getcode(){
	$.extend($.fn.validatebox.defaults.rules, {
	    equals_bm: {
	    	validator: function(value,param){
				var fla = true;
					if(value!="" && bmvalue !=value ){
							$.ajax({
								url:'getHazardFactorsCode.action',
								data:{hazard_code:value},
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
////////////////////////////////////////////////保存职业危害因素///////////////////////////////////////////////
function saveOccuhazardfactors(){
	if($('#hazardclassID').combobox('getText')==''||$('#hazardclassID').combobox('getValue')==''||$('#hazardclassID').combobox('getValue')==undefined){
		$('#hazardclassID').combobox('textbox').focus();
		return;
	}
	if($('#hazard_code').val() == ''){
		$('#hazard_code').focus();
		return;
	}
	var ll="";
	$.ajax({
		url:'getHazardFactorsCode.action',
		data:{hazard_code:$('#hazard_code').val()},
		type:'post',
		async:false,
		dataType:'json',
		success:function(data){
			if(data!=1){
				ll = 1;
			}else{
				ll = 0;
			}
		},
		error:function(){
			$.messager.alert("警告信息","编码唯一性验证失败","error");
		}
	})
	var xgzj='<s:property value='hazard_code'/>';
	if((/[\u0391-\uFFE5]/g.test($('#hazard_code').val()) || $('#hazard_code').val()==''||ll=='0')&&xgzj!=$('#hazard_code').val()){
		$('#hazard_code').focus();
		return;
	} 
	if($('#hazard_name').val()==''){
		$('#hazard_name').focus();
		return;
	}
	if(!(/^[0-9]{1,20}$/.test($(hazard_year).val())) && $(hazard_year).val()!=""){
		$('#hazard_year').focus()
		return;
	}
	if(!(/^[0-9]{1,20}$/.test($(order).val())) || $(order).val()==''){
		$('#order').focus()
		return;
	}
	if($('#hazard_desc').textbox('getValue').length>1200){
		$("#tt").tabs("select",0);
		$('#hazard_desc').textbox('textbox').focus();
		return;
	}
	
	if($('#deffect').textbox('getValue').length>1200){
		$("#tt").tabs("select",1);
		$('#deffect').textbox('textbox').focus();
		return;
	}
	if($('#remark').textbox('getValue').length>100){
		$("#tt").tabs("select",2);
		$('#remark').textbox('textbox').focus();
		return;
	}
	var model = {
			hazardfactorsID:$('#hazardfactorsID').val(),
			hazardclassID:$('#hazardclassID').combobox('getValue'),
			hazard_code:$('#hazard_code').val(),
			hazard_name:$('#hazard_name').val(),
			hazard_year:$('#hazard_year').val(),
			hazard_desc:$('#hazard_desc').textbox('getValue'),
			order:$('#order').val(),
			deffect:$('#deffect').textbox('getValue'),
			remark:$('#remark').textbox('getValue'),
			pycode:$('#pycode').val()
	}
	$.ajax({
		url:'saveHazardFactors.action',
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert('提示信息',data,"info");
			if(!$('#lianxu').is(':checked')){
				$('#dlg-custedit').dialog('close')
			}else{
				$('#hazard_code').val('');
				$('#hazard_name').val('');
				$('#hazard_year').val('1');
				var obj=Number($('#order').val())+1;
				$('#order').val(obj);
				$('#hazard_desc').textbox('setValue','');
				$('#deffect').textbox('setValue','');
				$('#remark').textbox('setValue',''); 
				$('#pycode').val();
				$("#tt").tabs("select",0);
			}
			$('#hazardfactorsShow').datagrid('reload');
		},
		error:function(){
			$.messager.alert('警告信息',"操作失败","error");
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
</script>
<input type="hidden"  value="<s:property value='hazardfactorsID'/>"  name=""   id="hazardfactorsID" /> 
 <fieldset style=" margin:2px 7px 5px 7px;padding: 5px 0px 0px;">
	<legend  ><strong>职业危害因素编辑</strong></legend> 
	<div class="formDiv"   style="padding-left:40px;" >
		<dl>
			<dt  style="width:100px;">职业危害类型</dt>
			<dd>
				<input type="text"      id="hazardclassID"    style="width: 200px;height:26px"  />
			</dd>
		<dt		style="width:100px;"	>职业危害编码</dt>
		<dd>
			<input type="text"   maxlength="20" class="textinput"  value=""      id="hazard_code"      style="width: 200px;height:26px"  />
		</dd>
		</dl>
		<dl>
			<dt		style="width:100px;"  >职业危害名称</dt>
			<dd>
				<input type="text"   maxlength="25" class="textinput"       id="hazard_name"   value="<s:property value='hazard_name'/>"   style="width: 200px;height:26px"  />
			</dd>
			<dt		style="width:100px;" >职业危害年限</dt>
			<dd>
				<s:if test='hazard_year==null'>
					<input type="text"   maxlength="3" class="textinput"    id="hazard_year"   value="1"     style="width: 200px;height:26px"  />
				</s:if>
				<s:else>
						<input type="text"   maxlength="5" class="textinput"    id="hazard_year"   value="<s:property value='hazard_year'/>"     style="width: 200px;height:26px"  />
				</s:else>
			</dd>
		</dl>
		<dl>
			<dt	style="width:100px;"	>显示顺序</dt>
			<dd>
				<input type="text"  	maxlength="4"   class="textinput"     id="order" 	value="<s:property value='order'/>"	   style="width: 200px;height:26px"  />
			</dd>
		</dl>
	</div>
<div id="tt" class="easyui-tabs"  data-options="" style="width:779px;height:293px;background:f7f4f5;margin-top:10px;">   
    <div title="职业危害描述"	data-options="tabWidth:255" style="padding:0px;font-size: 200px;">   
            <input class="easyui-textbox" data-options="multiline:'true',validType:'maxLength[1200]',invalidMessage:'最多可输入1200个字符'"	 value="<s:property value='hazard_desc'/>"	   id='hazard_desc' style="width:100%;height: 100%;"> 
    </div>   
    <div title="对劳动者的影响" data-options="tabWidth:255" style="overflow:auto;padding:0px;font-size: 200px;">
    		<input class="easyui-textbox" data-options="multiline:'true',validType:'maxLength[1200]',invalidMessage:'最多可输入1200个字符'"   value="<s:property value='deffect'/>"	    id='deffect' style="width:100%;height: 100%;">  
    </div>   
	<div title="备注" id="beizhu" data-options="tabWidth:255" style="padding:0px;">
			<input class="easyui-textbox"		 data-options="multiline:'true',validType:'maxLength[100]',invalidMessage:'最多可输入100个字符'"  value="<s:property value='remark'/>"   id='remark' style="width:100%;height: 100%;"> 
	</div> 
</div> 
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
			<s:if  test="hazardfactorsID==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="lianxu"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
		    <a href="javascript:saveOccuhazardfactors();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>