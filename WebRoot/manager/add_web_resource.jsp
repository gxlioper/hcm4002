<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	xzxlk();
})
var ss=0;
function  xzxlk(){
	$('#xlk').combobox({    
	    url:'WebResourcePagecombox.action?type='+$('#type').val(),    
	    valueField:'code',    
	    textField:'name',
	    editable:true,
	    panelHeight:'280',
	    onLoadSuccess:function(){
	    	
	    	if($('#add_id').val()>0){
	    		
	    		var obj = $('#xlk').combobox('getData');
	    		$('#xlk').combobox("select",$('#add_code').val());
	    		zyms($('#add_code').val());
	    		
	    		for(var i=0 ; i<obj.length ; i++){
	    			if(obj[i].code == $('#add_code').val()){
	    				if(obj[i].data_type==1){
	    					if(obj[i].code == 'RS012'){
	    						very_z();
	    					}else{
	    						sz();
	    					}
	    				}else{
	    					$('#add_dataValue').unbind();
	    				}
	    			}
	    		}
	    		
	    	}else{
	    		
				var obj = $('#xlk').combobox('getData');
		    	$('#xlk').combobox('select',obj[0].code);
		    	
		    	for(var i=0 ; i<obj.length ; i++){
	    			if(obj[i].code == obj[0].code){
	    				if(obj[i].data_type==1){
	    					if(obj[i].code == 'RS012'){
	    						very_z();
	    					}else{
	    						sz();
	    					}
	    				}else{
	    					$('#add_dataValue').unbind();
	    				}
	    			}
	    		}
		    	
	    	}
	    },
	    onSelect:function(record){
	    	zyms(record.code);
	    	if($('#add_id').val()>0){
	    		if(ss>0){
		    		$('#add_dataValue').val("");
	    		}
	    		ss=ss+1;
	    	}else{
	    		$('#add_dataValue').val("");
	    	}
	    	ss=ss+1;
	    	
	    	var obj = $('#xlk').combobox('getData');
	    	for(var i=0 ; i<obj.length ; i++){
    			if(obj[i].code == record.code){
    				if(obj[i].data_type==1){
    					$('#add_dataValue').unbind();
    					if(obj[i].code == 'RS012'){
    						very_z();
    					}else{
    						sz();
    					}
    				}else{
    					$('#add_dataValue').unbind();
    				}
    			}
    		}
	    	
	    }
	});  
}
/**
 * 获取资源描述--例子
 */
function zyms(code){
	var model = {code:code,type:$('#type').val()}
	$.ajax({
		url:'getWebResourceDTO.action',
		type:'post',
		data:model,
		success:function(data){
			var obj = eval('('+data+')');
			$('#miaoshu').textbox('setValue',obj.notice);
			$('#lizi').textbox('setValue',obj.example+obj.examplenote);
		}
	})
}
/**
 * 限制
 */
function xzsr(){
	$('#add_dataValue').numberbox({    
		required: true,  
		missingMessage:'该项为必输项',
	});  
}
function gbzy(){
	$('#add_resource').dialog('close')
}
/**
 * 保存资源
 */
function addWebResource(){
	var reg = /^[0-9]*$/;
	var r= /^[+-]?[1-9]?[0-9]*\.[0-9]*$/;
	
	if(!reg.test($('#add_dataValue').val()) &&  !r.test($('#add_dataValue').val()) ){
		$.messager.alert("警告","资源值必须为数字","error");
		return;
	}
	
	if($('#add_id').val()<=0||$('#add_code').val()!=$('#xlk').combobox('getValue')){
		if(resourceyz()=="1"){
			$.messager.alert("警告信息","资源已存在","error");
			return;
		}
	} 
	
	if($('#add_dataValue').val()==""||$('#add_dataValue').val()=="undefined"){
		$.messager.alert("警告信息","请输入资源值","error");
		return;
	}
	
	var model = {
				id:$('#add_id').val(),
				res_code:$('#xlk').combobox('getValue'),
				res_type:$('#type').val(),
				datavalue:$('#add_dataValue').val(),
				iid:$('#iid').val()
			}
	$.ajax({
		url:'addWebResource.action',
		data:model,
		type:'post',
		success:function(data){
			$('#resourceShow').datagrid("reload")
			gbzy();
			$.messager.alert("提示信息",data,"info");
			
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	})
}
function sz(){
	$('#add_dataValue').bind('keyup', function (event) {
	 	if($('#add_dataValue').val()>10){
    		$('#add_dataValue').val('10');
	    }
	 	
	    var $amountInput = $(this);
	    //响应鼠标事件，允许左右方向键移动 
	    event = window.event || event;
	    if (event.keyCode == 37 | event.keyCode == 39) {
	        return;
	    }
	    //先把非数字的都替换掉，除了数字和. 
	    $amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
	        //只允许一个小数点              
	        replace(/^\./g, "").replace(/\.{2,}/g, ".").
	        //只能输入小数点后两位
	        replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
	    });
		$('#add_dataValue').bind('blur',function(){
			var $amountInput = $(this);
		    //最后一位是小数点的话，移除
		    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
			
			/* ;
			if($(this).val()<1){
				$(this).val("1");
			} */
		})
}

function very_z(){
	$('#add_dataValue').bind('keyup', function (event) {
	    var $amountInput = $(this);
	    //响应鼠标事件，允许左右方向键移动 
	    event = window.event || event;
	    if (event.keyCode == 37 | event.keyCode == 39) {
	        return;
	    }
	    //先把非数字的都替换掉，除了数字和. 
	    $amountInput.val($amountInput.val().replace(/[^\d]/g, ""));
	    });
		$('#add_dataValue').bind('blur',function(){
			var $amountInput = $(this);
		    //最后一位是小数点的话，移除
		    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
		})
}
/**
 * 资源验证唯一
 */
function resourceyz(){
	
	var g = $('#xlk').combobox('getValue');	// 获取数据表格对象
	var obj = $('#xlk').combobox('getData');
	var data_type="";
	for(var i=0 ; i<obj.length ; i++){
		if(obj[i].code == g){
			data_type=obj[i].data_type
		}
	}
	
	
	var b="0";
	
	var model = {
			id:$('#add_id').val(),
			res_code:g,
			res_type:$('#type').val(),
			data_type:data_type,
			datavalue:$('#add_dataValue').val(),
			iid:$('#iid').val()
		}
	
	$.ajax({
		url:'webResourcePD.action',
		data:model,
		type:'post',
		async:false,
		success:function(data){
			if(data=="no"){
				b = "1";
			}
		}
	})
	return b;
}
</script>
<input type="hidden"  id="add_id"   value="<s:property value='id'/>"/>
<input type="hidden"  id="add_code"   value="<s:property value='res_code'/>"/>
<div class="formDiv"  style="padding-top: 20px;padding-left:5px;margin-left: 0px;margin-top:20px">
	<dl>
		<dt  style="width:105px">资源</dt>
		<dd>
			<input type="text" id="xlk"   style="width:300px;height: 26px" />
		</dd>
	</dl>
	<dl  style="height: 105px">
		<dt style="width:105px">资源描述</dt>
		<dd>
			<input id="miaoshu" class = "easyui-textbox"  data-options="multiline:true,readonly:true,disabled:true"  style="height:100px;width:300px"/>
		</dd>
	</dl>
	<dl style="height:70px">
		<dt style="width:105px">资源例子</dt>
		<dd>
			<input id="lizi"  class="easyui-textbox"   data-options="multiline:true,readonly:true,disabled:true"  style="height:60px;width:300px;"/>
		</dd>
	</dl>
	<dl>
		<dt  style="width:105px">资源值</dt>
		<dd>
			<input  class="textinput"   id="add_dataValue"   value="<s:property value='datavalue'/>"  style="width:300px;height: 26px" />
		</dd>
	</dl>
</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addWebResource();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:gbzy()" class="easyui-linkbutton" style="width:80px;" onclick="javascript:gbzy()">关闭</a>
	</div>
</div>