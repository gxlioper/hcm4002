<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	f_getDatadic();	
});

//获取菜单
function f_getDatadic() {
	var comtype='<s:property value="model.pay_way"/>';
	$('#pay_way').combobox({
        url:'getDatadis.action?com_Type=JSLX', 
        editable:false, //不可编辑状态
        cache: false,
        panelHeight: 'auto',//自动高度适合
        valueField:'id',   
        textField:'name',
        onLoadSuccess: function (data){
           for(var i=0;i<data.length;i++)
            {
               if(data[i].id==comtype){
            	$('#pay_way').combobox('setValue',data[i].id);
            	break;
               }else{
            	$('#pay_way').combobox('setValue',data[0].id);
               }
            }
        }
	});
	
	var charge_type='<s:property value="model.charge_type"/>';
	$('#charge_type').combobox({
		    url:'getDatadis.action?com_Type=SFTYPE', 
	        editable:false, //不可编辑状态
	        cache: false,
	        panelHeight: 'auto',//自动高度适合
	        valueField:'id',   
	        textField:'name',
           onLoadSuccess: function (data){
           for(var i=0;i<data.length;i++)
            {
               if(data[i].id==charge_type){
            	$('#charge_type').combobox('setValue',data[i].id);
            	break;
               }else{
            	$('#charge_type').combobox('setValue',data[0].id);
               }
            }
        }
	});	
}

function checkinput(){
	 if (document.getElementsByName("pay_way")[0].value==''){
  		alert('结算方式不能为空！');
		document.getElementsByName("pay_way").focus();
		return false;
	}else if (document.getElementsByName("charge_type")[0].value == ''){
  		alert('收费类型不能为空！');
		document.getElementsByName("charge_type")[0].focus();
		return false;
	}else if (document.getElementById("batch_name").value == ''){
  		alert('体检任务名称不能为空！');
		document.getElementById("batch_name").focus();
		return false;
	}else if (document.getElementById("phone").value != ''){
		if(!(isSZ(document.getElementById("phone").value))){	
		alert('联系人电话格式错误！');
		document.getElementById("phone").focus();
		return false;
		}
	}else if (document.getElementById("exam_number").value == ''){
  		alert('计划体检人数不能为空！');
		document.getElementById("exam_number").focus();
		return false;
	}else if(!(isSZ(document.getElementById("exam_number").value))){
		alert('计划体检人数格式错误！');
		document.getElementById("exam_number").focus();
		return false;
	}else if (document.getElementById("exam_fee").value == ''){
  		alert('计划体检费用不能为空！');
		document.getElementById("exam_fee").focus();
		return false;
	}else if(!(isJE(document.getElementById("exam_fee").value))){
		alert('计划体检费用格式错误！');
		document.getElementById("exam_fee").focus();
		return false;
	}else if (document.getElementById("remark").value != ''){
	    if (document.getElementById("remark").value.length>200)
	    {
  		alert('备注字数超限！');
		document.getElementById("remark").focus();
		return false;
		}
	}
	return true;
}

/**
 * 保存修改
 */
function fanganadd(){
	if(checkinput())	
	{
		$("#batch_num").val($("#batch_name").val());
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	    $("body").prepend(str);
	 $.ajax({
        url:'zybbatcheditdo.action',  
        data:{
          id:$("#id").val(),
          company_id:$("#company_id").val(),
          batch_num:$("#batch_num").val(),
          batch_name:$("#batch_name").val(),
          contact_name:$("#contact_name").val(),
          phone:$("#phone").val(),
          pay_way:document.getElementsByName("pay_way")[0].value,
          charge_type:document.getElementsByName("charge_type")[0].value,
          exam_number:$("#exam_number").val(),
          exam_fee:$("#exam_fee").val(),
          sales_name:$("#sales_name").val(),
          introducer_name:$("#introducer_name").val(),
          exam_item:$("#exam_item").val(),
          accommodation:$("#accommodation").val(),          
          dine:$("#dine").val(),
          invoice_title:$("#invoice_title").val(),
          batch_address:$("#batch_address").val(),
          exam_date:$("#exam_date").datebox('getValue'),          
          remark:$("#remark").val()
          },          
          type: "post",//数据发送方式   
          success: function(text){
        	  $(".loading_div").remove();
        	  if(text.split("-")[0]=='ok'){
        		  $.messager.alert("操作提示", text);
        		   getbatchGrid();
        		   $('#dlg-edit').dialog('close');
        	  }else if(text.split("-")[0]=='error'){
        		  $.messager.alert("操作提示", text,"error");
        	  }
            },
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}    
    }); 
	}
}
</script>
<input type="hidden" id="company_id" value='<s:property value="model.company_id"/>'>
<input type="hidden" id="id" value='<s:property value="model.id"/>'>
<input type="hidden" id="batch_num" value='<s:property value="model.batch_num"/>'>

<fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检任务基本信息</strong></legend>
			<div class="user-query">
				<dl>
				    <dt>单位名称 </dt>
					<dd><input class="easyui-textbox"  maxlength="20" readonly type="text" id="comname" value="<s:property value="model.comname"/>" style="height:26px;width:390px;"/></dd>
					<dt>体检任务名称 <strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox" maxlength="20" type="text" id="batch_name"  value="<s:property value="model.batch_name"/>" style="height:26px;width:140px;"/></dd>
				</dl>
				<dl>
					<dt>联系人</dt>
					<dd><input class="easyui-textbox" type="text" id="contact_name" maxlength="20" value="<s:property value="model.contact_name"/>" style="width:140px;height:26px;"></input></dd>
					<dt>联系电话</dt>
					<dd><input class="easyui-textbox" type="text" id="phone" maxlength="20" value="<s:property value="model.phone"/>" style="width:140px;height:26px;"></input></dd>
					<dt>结算方式 <strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="pay_way" name="pay_way" value="<s:property value="model.pay_way"/>" style="height:26px;width:140px;"></select></dd>
				</dl>
				<dl>
					<dt>计划人数<strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox" type="text"  id="exam_number" value="<s:property value="model.exam_number"/>" style="height:26px;width:140px;"/></dd>
					<dt>计划费用 <strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox" type="text" id="exam_fee" maxlength="20"  value="<s:property value="model.exam_fee"/>" style="height:26px;width:140px;"/></dd>
					<dt>收费类型 <strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="charge_type" name="charge_type" value="<s:property value="model.charge_type"/>" style="height:26px;width:140px;"/></select></dd>
				</dl>
				
				<dl>
				    <dt>销售人员</dt>
					<dd><input class="easyui-textbox" type="text" id="sales_name" maxlength="20" value="<s:property value="model.sales_name"/>" style="width:140px;height:26px;"></input></dd>
					<dt>介绍人</dt>
					<dd><input class="easyui-textbox" type="text" id="introducer_name" maxlength="20" value="<s:property value="model.introducer_name"/>" style="width:140px;height:26px;"></input></dd>
                    <dt>体检项目</dt>
					<dd><input class="easyui-textbox"   type=text id="exam_item" maxlength="100" value="<s:property value="model.exam_item"/>" style="width:140px;height:26px;"></input></dd>
				</dl>
				<dl>
				    <dt>住宿安排</dt>
					<dd><input class="easyui-textbox" type="text" maxlength="100" id="accommodation" value="<s:property value="model.accommodation"/>" style="width:140px;height:26px;"></input></dd>
				    <dt>用餐安排</dt>
					<dd><input class="easyui-textbox"  maxlength="100"  type=text id="dine" value="<s:property value="model.dine"/>" style="width:140px;height:26px;"></input></dd>
					<dt>体检日期</dt>
					<dd><input  class="easyui-datebox"   type=text id="exam_date" value="<s:property value="model.exam_date"/>" style="width:140px;height:26px;"></input></dd>
				</dl>
				<dl>
					<dt>发票抬头</dt>
					<dd><input  type=textarea id="invoice_title" maxlength="100" value="<s:property value="model.invoice_title"/>" style="width:630px;height:26px;"></input></dd>
				</dl>
				<dl>
					<dt>邮寄地址</dt>
					<dd><input  type=textarea id="batch_address" maxlength="100" value="<s:property value="model.batch_address"/>" style="width:630px;height:26px;"></input></dd>
				</dl>
				<dl>
					<dt>备注</dt>
					<dd><input  type=textarea id="remark" maxlength="200" value="<s:property value="model.remark"/>" style="width:630px;height:26px;"></input></dd>
				</dl>
				</div>
 </fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:fanganadd();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>

