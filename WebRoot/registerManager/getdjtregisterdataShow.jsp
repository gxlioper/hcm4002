<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script>
function checkinputtimes() {
	var startdate=$("#datetime").datebox('getValue');
	var time1=document.getElementById("time1").value;
	var time2=document.getElementById("time2").value;
	if(startdate==''){
		alert('体检日期不能为空！');
		return false;
	}else if(!isDate(startdate)){
		alert('体检日期格式错误！');
		return false;
	}else if((time1=='')||(time1.length!=5)){
		alert('体检开始时间不能为空或格式错误！');
	    return false;
    }else if(!isTime(time1)){
		alert('体检开始时间格式错误！');
        return false;
    }else if((time2=='')||(time2.length!=5)){
		alert('体检结束时间不能为空或格式错误！');
        return false;
    }else if(!isTime(time2)){
		alert('体检结束时间格式错误！');
        return false;
    } else if(!zyDate(startdate)){
    	alert('不能选择过去日期');
    	$('#datetime').textbox('textbox').focus();
    	return false;
    }
	return true;
}

/**
 * 保存修改
 */
function addcustomertimedo() {
	if (checkinputtimes()) {		
		var startdate=$("#datetime").datebox('getValue');
		var time1=document.getElementById("time1").value;
		var time2=document.getElementById("time2").value;
		var timess = time1+"-"+time2;
		timess=timess.replace(/:/g,"");
		$("#register_date").val(startdate);
		$("#setimes").val(timess);
		addcustomerdo();
		$('#dlg-edit').dialog('close');		
	}
}
$.extend($.fn.datebox.defaults.rules,{  
    checkDate:{  
         validator:function(value, param){      
            return zyDate(value);  
         },  
         message:"不能选择过去日期"  
    }     
});

var d = "";
function yz(d){
	$('#datetime').textbox('textbox').focus();
}
var value ="";
function zyDate(value){
	 var xuanzeDate = new Date(value);
     var newDate = new Date();  
     var fal = false;
     var connt = "";
     

     if(xuanzeDate.getFullYear()>newDate.getFullYear()){
    	 fal = true;
     }
     if(xuanzeDate.getFullYear()==newDate.getFullYear()){
    	 if(xuanzeDate.getMonth()>newDate.getMonth()){
    			 fal = true;
    	 } 
     } 
     if(xuanzeDate.getFullYear()==newDate.getFullYear()){
    	 if(xuanzeDate.getMonth()==newDate.getMonth()){
    		 if(xuanzeDate.getDate()>=newDate.getDate()){
    			 fal = true;
    		 }
    	 } 
     } 
     console.log(fal);
     return fal;
}
</script>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员基本信息</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>
				体检日期
			</dt>
			<dd><input class="easyui-datebox" type=text id="datetime"  data-options="required:true,validType:'checkDate',onSelect: function(date){yz(date)}" style="width:100px;height:26px;"></input></dd>		
		</dl>
		<dl>
			<dt>
				开始时间
			</dt>
			<dd><input class="easyui-textbox" type="text" id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input></dd>	
			<dt>
				结束时间
			</dt>
			<dd><input  class="easyui-textbox" type="text" id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>			
		</dl>
		<dl>
			<dt style="width:400px;height:26px;">
				注意：1、开始时间和结束时间输入格式如 10:00表示10点00分。
			</dt>
					
		</dl>
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addcustomertimedo();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>