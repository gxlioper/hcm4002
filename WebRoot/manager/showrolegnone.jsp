<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %> 
<%@ page contentType="text/html;charset=utf-8" %>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>授权功能</title>

<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>

<script type="text/javascript">

/*function f_rolegn()
{
 if (confirm('<s:text name="qrconfirm"/>？'))
  {
     $.ajax({
        url:'rolegnsave.action?language='+$("#language").val()+'&id='+$("#roleid").val()+'&rolegnid='+$("#dv_system_menus").getTSVs()+'&roleshid='+$("#dv_mercacc").getTSVs()+'&rolezyid='+$("#dv_resources").getTSVs(),  
        type: "post",//数据发送方式   
          success: function(text){  
             // document.getElementById("mes").innerHTML='<s:text name="opermsg"/>:\n'+text;
              alert('<s:text name="opermsg"/>:\n'+text);
            }     
    });    
  }
}*/
</script>
</head>

<body>

<div class="shanghu_dlg_top">
	<input type="hidden" name="roleid" id="roleid" value='<s:property value="id"/>'/>
				<div class="shanghu_dlg_top_1">角色名称：<s:property value="name"/></div>
			
				<div class="shanghu_dlg_top_2">分配授权</div>
			</div>
			<div class="easyui-tabs" style="height:340px;">
				<div class="active" id="system_menus"  title="功能菜单"><ul class="easyui-tree" id="dv_system_menus"  data-options="checkbox:true,onnodeclick:false,url:'rolegnshow.action?id='+$('#roleid').val()" ></ul></div>
				<!--div  id="mercacc"  title="授权商户(部门)"><ul class="easyui-tree" id="dv_mercacc"  data-options="checkbox:true,onnodeclick:false,url:'getMerccdept.action?roleid='+$('#roleid').val()"></ul></div>
				<div id="resources"  title="授权资源"><ul class="easyui-tree" id="dv_resources"  data-options="checkbox:true,onnodeclick:false,url:'getZyzbtree.action?roleid='+$('#roleid').val()"></ul></div-->
			</div>

<!--  <div class="dialog-button-box">
	<div class="inner-button-box">
	  
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-query').dialog('close')">关闭</a>
	</div> -->
</div>
</body>
</html>




