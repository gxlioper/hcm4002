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

</head>

<body>
<input type="hidden" name="roleid" id="roleid" value='<s:property value="id"/>'/>
<div class="shanghu_dlg_top">
	
				<div class="shanghu_dlg_top_1">角色名称：<s:property value="name"/></div>
			</div>
			<div class="easyui-tabs" style="height:360px;">				
				<div class="active" id="system_menus"  title="功能菜单">
				 <div  id="mask1" class="datagrid-mask" style="display:block;"></div><div class="datagrid-mask-msg"  id="mask-msg1" style="display:block;left:50%;margin-left:-35px;">正在加载,请稍候...</div> 
					<ul class="easyui-tree" id="dv_system_menus"  data-options="checkbox:true,onnodeclick:false,url:'rolegnshow.action?id='+$('#roleid').val(),onLoadSuccess:function(){
							$('#mask1').removeAttr('style');
							$('#mask-msg1').removeAttr('style');
					
					}" ></ul>
					<ul style="height:40px;"></ul>
				</div>
				
				<div  id="mercacc"  title="授权报表">
				<div id="mask2" class="datagrid-mask" style="display:block;"></div><div class="datagrid-mask-msg"  id="mask-msg2" style="display:block;left:50%;margin-left:-35px;">正在加载,请稍候...</div>
				<ul class="easyui-tree" id="dv_mercacc"  data-options="checkbox:true,onnodeclick:false,url:'reportshow.action?id='+$('#roleid').val(),onLoadSuccess:function(){
							$('#mask2').removeAttr('style');
							$('#mask-msg2').removeAttr('style');
				}"></ul>
				</div>
				<!--<div id="resources"  title="授权资源">
				 <div  id="mask3" class="datagrid-mask" style="display:block;"></div><div class="datagrid-mask-msg"  id="mask-msg3" style="display:block;left:50%;margin-left:-35px;">正在加载,请稍候...</div> 
				<ul class="easyui-tree" id="dv_resources"  data-options="checkbox:true,onnodeclick:false,url:'getZyzbtree.action?roleid='+$('#roleid').val(),onLoadSuccess:function(){
							$('#mask3').removeAttr('style');
							$('#mask-msg3').removeAttr('style');
				}"></ul>
				</div>
				-->
			</div>

<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saverolegn()">确认</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-shanghu').dialog('close')">取消</a>
	</div>
</div>
<script type="text/javascript">
function f_saverolegn()
{		//授权菜单
	 		var menu=[];
	 		var mec=$("#dv_system_menus").tree("getChecked");
	 		for(var i=0;i<mec.length;i++){
	 			if(($("#dv_system_menus").tree('isLeaf',mec[i].target))){
	 				menu.push(mec[i].id);
	 			}
	 		}
	 		if(menu.length==0){
			//alert('用户没有选择任何岗位!');
			$.messager.alert("提示","用户没有选择菜单！", "warning");
			return ;
		    }
	 		
	 		//授权报表	 		
	 		var shids=[];
			var nodes=$("#dv_mercacc").tree("getChecked");			
			for(var i=0;i<nodes.length;i++){				
				if($("#dv_mercacc").tree('isLeaf',nodes[i].target)){					
						shids.push(nodes[i].id);				
				}
			}	
	 		
$.messager.confirm('提示信息','确认对该角色进行授权吗？',function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);	
	 		$.ajax({
		       // url:'rolegnsave.action?id='+$("#roleid").val()+'&rolegnid='+menu+'&roleshid='+shids+'&rolezyid='+resource,  
		        url:'rolegnsave.action?id='+$("#roleid").val()+'&rolegnid='+menu+'&reportids='+shids,  
		        type: "post",//数据发送方式   
		          success: function(data){  
		        	  $(".loading_div").remove();
		            	var message=eval("("+data+")");
							//alert(message);
							$.messager.alert('提示信息', message,function(){});
							$("#dlg-shanghu").dialog('close');
							getGrid();
		            } ,
		           error:function(){
		        	   $(".loading_div").remove();
		           		//alert('用户操作失败！');
		           		$.messager.alert('提示信息', '用户操作失败！',function(){});
		           }  
		    });    
		}
	})

}

</script>
</body>
</html>




