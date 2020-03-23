<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common_comboTree_box.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		$("#upsex").val('<s:property value="sex"/>');
		$("#upis_Active").val('<s:property value="is_Active"/>');
	});
	$(function(){
		$("#upuser_name,#upbirthday").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#upid_num").validatebox({
			//required:true,
			validType:'maxLength[18]'
		});
		$("#upnation").combobox({
			url :'getDatadis.action?com_Type=MZLX',
			editable : true, //不可编辑状态
			cache : false,
			panelHeight:200,
			valueField : 'id',
			textField : 'name'
		})
	});
	function f_customsave(){
		if (document.getElementById("upuser_name").value == ''){
			$("#upuser_name").focus();
			return;
		}else if($("#upbirthday").datebox('getValue') == ''){
			$.messager.alert('提示信息', '生日不能为空!');
			$("#upbirthday").focus();
			return;
		}
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	    $("body").prepend(str);
	$.ajax({
	    url:'saveCustom.action', 
	    type: "post",
		data:{
			id:$("#id").val(),
			arch_num:$("#arch_num").val(),
			user_name:$("#upuser_name").val(),
			id_num:$("#upid_num").val(),
			nation:$("#upnation").combobox('getValue'),
			sex:$("#upsex").val(),
			is_Active:$("#upis_Active").val(),
			birthday:$("#upbirthday").datebox('getValue'),
		  },          
		success: function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				$.messager.alert('提示信息', obj[1],'info');
			  	 $("#Custom_edit").dialog("close");
			     getGrid();
			}else{
				$.messager.alert('提示信息', obj[1],'error');
			}
		 },
		 error:function(){
			 $(".loading_div").remove();
		    	$("#Custom_edit").dialog("close");
		    	$.messager.alert('提示信息', "用户操作失败！",function(){});
		    }  
	});

		
	}
	
	//自动填充 生日、年龄、性别
	function selectidnum(){
		var idnum= $("#upid_num").val();//取值 
		if(idnum.length==18){
			var ssidnum=idnum.substring(0,17);
			if (isSZ(ssidnum)) {		
			  $('#upbirthday').datebox('setValue',IdCard(idnum,1));
			  $('#upsex').val(IdCard(idnum,2));
			}
		}
	}

	function isSZ(str){	
		return (/^[0-9]{1,20}$/.test(str));	
	}

	//通过身份证号获取生日、年龄、性别
	function IdCard(UUserCard,num){
		   if(num==1){
		       //获取出生日期
		       birth=UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14);
		    return birth;
		   }
		   if(num==2){
		       //获取性别
		       if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
		           //男
		     return "男";
		       } else {
		           //女
		     return "女";
		       }
		   }
		   if(num==3){
		        //获取年龄
		        var myDate = new Date();
		        var month = myDate.getMonth() + 1;
		        var day = myDate.getDate();
		        var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
		        if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) {
		            age++;
		        }
		  return age;
		 }
		}
</script>
	
	<fieldset style="margin:5px;padding-right:20px;padding-left:30px;font-size:14px;">
	<legend><strong> 档案信息维护</strong></legend>
	<div class="formdiv" style="padding-left:20px;">
		<dl>
	    	<dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>
	    	档案编号：</dt>
	    	<dd><input id="arch_num" name="arch_num" value="<s:property value="arch_num"/>" disabled="true" class="textinput" data-options="prompt:'档案编号'"></dd>
	    	<dt class="" style="position: absolute; height: 25px; width: 100px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    </dl>
	    <dl>
	    	<dt>体检用户姓名：</dt>
	    	<dd><input id="upuser_name" name="upuser_name" value="<s:property value="user_name"/>" class="textinput" ></dd>
	    </dl> 
	    <dl>
	    	<dt>身份证号：</dt>
	    	<dd><input id="upid_num" name="upid_num" value="<s:property value="id_num"/>" class="textinput" maxlength="18" onblur="selectidnum()"></dd>
	    </dl>  
	    <dl>
	    	<dt>性别</dt>
	    	<dd><select style="width:150px;" id="upsex" >
		    	       		<option  value="男">男</option>
		    	       		<option  value="女">女</option>
		    	</select>
	    	</dd>
	    </dl>
	    <dl>
	    	<dt>是否激活</dt>
	    	<dd><select style="width:150px;" id="upis_Active" >
		    	       		<option  value="Y">是</option>
		    	       		<option  value="N">否</option>
		    	</select>
	    	</dd>
	    </dl>
	    <dl>
	    	<dt>生日</dt>
	    	<dd><input id="upbirthday" name="upbirthday" value="<s:property value="birthday"/>"  class="easyui-datebox" ></dd>
	    </dl>
	    <dl>
	    	<dt>民族</dt>
	    	<dd><input id="upnation" name="upnation" value="<s:property value="nation"/>"  class="textinput" ></dd>
	    </dl>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_customsave();">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#Custom_edit').dialog('close')">关闭</a>
		</div>
	</div>
</fieldset>

