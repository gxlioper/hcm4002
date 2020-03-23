<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
$(document).ready(function () { 
	reopenshow();
});

//加载单位信息
function reopenshow(){
	var url = 'companychangshow.action';
	var data = {"name":$("#com_name").val()};
	//var data = {};
	load_post(url,data,function(data){	
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"单位","javascript:selectvalue(0,'根目录')","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:selectvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:selectvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#dv_system_menus").html(mydtree.toString());
			//select_dengji();
			
	});
}

var datedtoid,datedtoname;
function selectvalue(datedtoid,datedtoname){
	document.getElementById("selectff").firstChild.nodeValue=datedtoid+"-"+datedtoname;
    document.getElementById("parent_com_id").value=datedtoid;
}

function checkinput(){
	if (document.getElementById("parent_com_id").value == ''){
  		alert('目标单位为空！请选择需要移动到哪个单位下面。');
		return false;
	}else if (document.getElementsByName("id").value==''){
  		alert('原单位为空！请选择需要移动的单位。');
		return false;
	}else{
	    return true;
	}
}

function removedcom(){
	if(checkinput())	
	{
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	    $("body").prepend(str);
	 $.ajax({
        url:'companychangedo.action',  
        data:{
          id:$("#id").val(),
          parent_com_id:$("#parent_com_id").val(),
          type: "post"//数据发送方式   
          },
          success: function(text){
        	  $(".loading_div").remove();
        	  if(text.split("-")[0]=='ok'){
        		  $.messager.alert("操作提示", text);
        		  reopenshow();
        		  window.parent.reopen();
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
</head>
<body>
<input type="hidden" id="company_Id" value='<s:property value="model.company_Id"/>'/>
<input type="hidden" id="father_con_num" value='<s:property value="model.father_con_num"/>'/>

<input type="hidden" id="id" value='<s:property value="model.id"/>'/>

<input type="hidden" id="com_Level" value='<s:property value="model.com_Level"/>'/>
<input type="hidden" id="com_Num" value='<s:property value="model.com_Num"/>'/>
<input type="hidden" id="com_Name" value='<s:property value="model.com_Name"/>'/>

<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>选中树形单位信息</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="height:26px;width:140px;">上级单位编号</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.company_Id"/></dd>
					<dt style="height:26px;width:140px;">上级单位名称</dt>
					<dd style="height:26px;width:560px;"><s:property value="model.dep_Name"/></dd>
				</dl>
				<dl>
				    <dt style="height:26px;width:140px;">当前选中单位编号</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.id"/></dd>
					<dt style="height:26px;width:140px;">当前选中单位名称</dt>
					<dd style="height:26px;width:560px;font-weight:bold;"><s:property value="model.com_Name"/></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>部门关系移动</strong></legend>
			<div class="easyui-layout" style="height:400px;overflow:auto;">
				<div class="active" id="system_menus" > 
				 <div id="dv_system_menus"></div>
				</div>
			
			</div>
 </fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"><input type="hidden" id="parent_com_id" value=""/>
	    将当前单位移动到<font id="selectff" style="color:red;font-weight:bold;font-style:italic;">未知</font>，确定请点击“确认”按钮
	    <a href="javascript:removedcom()" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-search').dialog('close')">确认</a>
	</div>
</div>
</body>
</html>
