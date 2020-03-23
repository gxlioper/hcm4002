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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/manager/useradd.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var toolbar = [{
    text:'新增用户',
    iconCls:'icon-add',
    handler:function(){
    	$("#dlg-edit").dialog({
    		title:'新增用户',
    		href:'useredit.action',
    	});
    	$("#dlg-edit").dialog("open");
    }
}];

function loadrolename(){
	$("#role_name").combobox({
		url :'queryAllWebRole.action',
		// editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'rolename'
	});
}

$(document).ready(function () {
	loadrolename();
	$("#dep_name").combobox({
		url :'departmentAll.action',
		// editable : false, //不可编辑状态
		cache : false,
		panelHeight : '400',//自动高度适合
		valueField : 'id',
		textField : 'dep_name'
	});
	
	var mydata;
	 $.post('usermainheader.action?language='+$("#language").val(),"ok",  
	        function (data) {
	            var obj;  
	            mydata = eval(data); 
	            getGrid();
	         },"json");  

	function addrole(com,grid){
		location.href = 'useredit.action?language=<s:property value="language"/>';
	}
	

})

	var userid;
	var adminId="<s:property value="#application.adminUserId"/>";
	var val;
	function f_sqgw(val,row){
	 	return '<a href=\"javascript:f_userrole(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"授权\" /></a>';
	}
	//用户授权岗位
	function f_userrole(userid){
	
					$("#dlg-s").dialog({
					title: '授权角色和科室',
					href:'userrole.action?id='+userid,
					width:850
					});
					$("#dlg-s").dialog('open');
	}

	function f_sqsh(val,row){
	 return '<a href=\"javascript:f_usermerc(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"授权商户\" /></a>';
	}


	function f_qyty(val,row){
	 var html='';
	    if(val=="Y"){
	        if(row.id==adminId){
	        	return '<a style="color:#1CC112;" href=\"javascript:void(\'0\')\">'+html+'</a>';
	        }else{
	           return '<a style="color:#1CC112;" href=\"javascript:f_lockuser(\''+row.id+'\',\'停用\')\">启用</a>';
	        }
	    }else if(val=='N') {
	       return '<a style="color:#f00;" href=\"javascript:f_lockuser(\''+row.id+'\',\'启用\')\">停用</a>';
	     }
	}
	function f_xg(val,row){	
			return '<a href=\"javascript:f_xguser(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
 
	//修改密码
 
	function f_xgpwd(val,row){	
		return '<a href=\"javascript:f_xguserpwd(\''+row.id+'\')\">修改密码</a>';
	 } 
	
	  //修改密码
	function f_xguserpwd(userid){
			$("#editpwd").dialog({
			title:'修改密码',
			href:'updateuserpwd.action?id='+userid,	
		});
		$("#editpwd").dialog('open');
	} 
	  

	  //设置签名
	function f_sign(val,row){	
		return '<a href=\"javascript:f_signshow(\''+row.id+'\')\">签名管理</a>';
	 } 
	
	  //设置签名
	function f_signshow(userid){
		 var url='managersignshow.action?id='+userid;                            //转向网页的地址;
         var name='设置签名';                            //网页名称，可为空;
         var iWidth=1024                          //弹出窗口的宽度;
         var iHeight=420;                         //弹出窗口的高度;
         //获得窗口的垂直位置
         var iTop = (window.screen.availHeight - 30 - iHeight) / 2+100;
         //获得窗口的水平位置
         var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
         newwin = window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
         newwin.focus();
	    // newWindow = window.open(url, "设置签名", "height=400, width=1024, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
	} 
	  
	function f_sc(val,row){
		if(row.id==adminId){
			return;
		}else{
			return '<a href=\"javascript:f_deluser(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
		} 
	}

	function f_xguser(userid){
				$("#editupdate").dialog({
				title:'修改用户',
				//href:'useredit.action?id='+userid
				href:'usereditupdate.action?id='+userid		
			});
			$("#editupdate").dialog('open');
	}

function f_deluser(userid)
{
	$.messager.confirm('提示信息','是否确定删除该用户？',function(r){
		if(r){
			$.ajax({
   		url:'deluser.action?id='+userid,
   		type:'post',
   		success:function(data){
   			var message=eval("("+data+")");
   			$.messager.alert('提示信息',message);
   			getGrid();
   		},
   		error:function(){
   			$.messager.alert('提示信息','操作失败！','error');
   		}
   		});
		}
	})
}

function f_lockuser(userid,html)
{
	$.messager.confirm('提示信息','是否确认'+html+'该用户？',function(r){
		if(r){
			$.ajax({
     	url:'lockuser.action?id='+userid,
     	type:'post',
     	success:function(data){
     		var obj=eval("("+data+")");
     		if(obj=='success'){
     			$.messager.alert('提示信息',html+"该用户成功！");
     			getGrid();
     		}else if(obj=='error'){
     			$.messager.alert('提示信息',html+"该用户失败！",'error');
     		}else{
     				$.messager.alert('提示信息',obj);
     		}
   			
     	},
     	error:function(){
     		$.messager.alert('提示信息','操作失败！','error');
     	}
     	
     });
		}
	})
}


var work_num_ed;
function getGrid(){
	   var lastIndex;
	 //获取启用/停用的值
	 var chk_value ="";    
	  $('input[name = chkItem]:checked').each(function(){    
	   chk_value=chk_value+","+($(this).val());    
	  }); 
	  if(chk_value.length>1){
		  chk_value=chk_value.substring(1,chk_value.length);
	  }
       var model = {"phone_num": $('#phone_num_s').textbox('getValue'),"log_Name": $('#log_Name_s').textbox('getValue'),"chi_Name": $('#chi_Name_s').textbox('getValue'),"dep_name": $('#dep_name').combobox('getValue'),"role_name": $('#role_name').combobox('getValue'),"startStop":chk_value};
	   $("#userlist").datagrid({
		url: '<%=request.getContextPath()%>/usermainshow.action',
		queryParams: model,
		rownumbers:true,
        pageSize: 15,//每页显示的记录条数，默认为10 
        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
        toolbar: '#toolbar',
        sortName: 'cdate',
		sortOrder: 'desc',
        //height:430,
        columns:[[{align:'center',field:"log_Name",title:"登录用户",width:15},
        		  {align:"center",field:"chi_Name","title":"姓名","width":15},
        		  {align:'center',field:"phone_num","title":"电话","width":15},
        		  {align:"center",field:"center_name","title":"体检中心","width":20},
        		  {align:"center",field:"email","title":"Email","width":15},
        		  {align:"center",field:"creaters","title":"创建人","width":10},
        		  {align:"center",field:"work_num","title":"员工编号","width":15,editor:'text'},
        		  {align:"center",field:"updaters","title":"修改人","width":10},
        		  {align:"center",field:"is_active","title":"启(停)修改","width":10,"formatter":f_qyty},
        		  {align:"center",field:"id","title":"授权","width":10,"formatter":f_sqgw},       		  
        		  {align:"center",field:"zysq","title":"资源管理","width":10,"formatter":web_resource_a},
        		  {align:"center",field:"ksdy","title":"科室打印","width":10,"formatter":f_printdep},
        		  {align:"center",field:"xg","title":"修改","width":10,"formatter":f_xg},
        		  /* {align:"center",field:"sc","title":"删除","width":10,"formatter":f_sc}, */
        		  {align:"center",field:"xgpwd","title":"修改密码","width":10,"formatter":f_xgpwd},
        		  // {align:"center",field:"qmgl","title":"设置签名","width":10,"formatter":f_sign},
        		  {align:"center",field:"upload","title":"上传签名","width":10,"formatter":f_upload}
        		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		 pagination: true,
        fitColumns:true,
        striped:true,
        fit:true,
        toolbar:toolbar,
        onDblClickCell:function(index, field, row){
        		$(this).datagrid('beginEdit', index);
        		var ed = $(this).datagrid('getEditor', {index:index,field:"work_num"});
        		//$(ed.target).focus();
    			if(lastIndex != index){
    				$(this).datagrid('endEdit', lastIndex);
    			}
    			lastIndex = index;
        		$(ed.target).change(function(){
        			$('#userlist').datagrid('endEdit', index);
        		});
        },
        onClickCell:function(index, field, row){
        	if(lastIndex != index){
				$(this).datagrid('endEdit', lastIndex);
			}
        },
         onEndEdit:function(index, row, changes){
        	 
        	 $.ajax({
					url:'isUniqueUser.action?work_num='+changes.work_num,
					type:'post',
					success:function(data){
						if(data=='no'){
							
							$.messager.alert('提示信息','该用户编码已存在！','error');
							getGrid();
							$("#userlist").datagrid('beginEdit', index);
			        		var ed = $("#userlist").datagrid('getEditor', {index:index,field:"work_num"});
			        		$(ed.target).focus();
			        		 
							return ;
						}else if(data=='ok'){
							//$("#message1").attr('value','ok');
							//$("#message1").html('');
							$.ajax({
            		url:'usereditdo_w.action?id='+row.id, 
            	    data:{
            	          id:$("#id").val(),
            	          work_num:changes.work_num,
            	          },          
            	    type: "post",//数据发送方式   
            	    success: function(data){  
            	    	var obj=eval("("+data+")");
            	        //$.messager.alert('提示信息', obj,function(){});
            	       
            	        $('#userlist').datagrid('refreshRow', index);
            	         },
            	         error:function(){
            	         	
            	            $.messager.alert('提示信息', "用户操作失败！",function(){});
            	            $('#userlist').datagrid('refreshRow', index);
            	         }  
            	    });
							return ;
						}
					}
			});
        	 
        },  
      
      
	});
	
	
}
//----------------------------------------------资源管理------------------------------------
/**
 * 资源授权  a标签
 */
function web_resource_a(val,row){
	return '<a href=\"javascript:web_resource(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"资源授权\" /></a>';
}
/**
 * 资源管理
 */
 //+'&type=1'
function web_resource(id){
	 var url='getwebResourceZYGL.action?iid='+id+'&type=2'; 
		newwin = window.open(url,"资源管理", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
		newwin.focus();
	/* $('#dlg_resource').dialog({
		title: '资源授权',    
	    width:800,
	    height:500,
	    href: 'getwebResourceZYGL.action?iid='+id+'&type=2'
	});    
	$('#dlg_resource').dialog('open');   */
}
function searchFun(){
	getGrid();
}
function cleanFun(){
	$('#role_name').combobox('setValue',"");
	$('#dep_name').combobox('setValue',"");
	$('#log_Name_s').textbox('setValue',"");
	$('#chi_Name_s').textbox('setValue',"");
	$('#phone_num_s').textbox('setValue',"");
	getGrid();
}



//----------------------------------------------科室打印------------------------------------

function f_printdep(val,row){
	return '<a href=\"javascript:funcprintdep(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"科室打印\" /></a>';
}


function funcprintdep(id){
	/*  var url='getwebResourceZYGL.action?iid='+id+'&type=2'; 
		window.open(url,"资源管理", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes"); */
	 $('#dlg_printdep').dialog({
		title: '打印科室',    
	    /* width:800,
	    height:500, */
	    href: 'printdepshow.action?id='+id
	});    
	$('#dlg_printdep').dialog('open'); 
}
//--------------------------上传用户签名-------------------------------------------
function f_upload(val,row){	
		return '<a href="javascript:void(0)" onclick = "upload_btn('+row.id+')">上传</a>';
	 }
function upload_btn(id) {
 	$("#userId").val(id);
 	$("#dlg-upload").dialog('open');
}
 function wenjianshangchuan() {
	if ($("#customImageInfoImport").val() == '') {
		$.messager.alert("操作提示", "请选择上传的图片", "error");	
		return;
	}
	var srca = $("#customImageInfoImport").val();
	var ext = [ '.gif', '.jpg', '.jpeg', '.png' ];
	var s = srca.toLowerCase();
	var r = false;
	for (i = 0; i < ext.length; i++) {
		if (s.indexOf(ext[i]) > 0) {
			r = true;
			break;
		}
	}
	// return r;
	if (r) {
		var img = null;
		img = document.createElement("img");
		if (!+[ 1, ]) {// ie//alert("这是ie浏览器")
			document.body.insertAdjacentElement("beforeEnd", img); // firefox不行
			img.style.visibility = "hidden";
			img.src = srca;
			var imgwidth = img.offsetWidth;
			var imgheight = img.offsetHeight;
			if (imgwidth != 200 || imgheight != 240) {
				$.messager.alert("操作提示", "图的尺寸应该是" + 200 + "x" + 240, "error");	
				this.value = "";
				return false;
			}
			// return true;
			f_ajaxFileUpload();
		} else {// 非ie alert("这不是ie浏览器");
			f_ajaxFileUpload();
		}

	} else {
		$.messager.alert("操作提示", "请上传正确的图片文件，png、jpg、gif、jpeg。文件尺寸为200*240像素！", "error");	
	}
}
 
function f_ajaxFileUpload() {
	    var id = $("#userId").val();
	    var file = document.getElementById("customImageInfoImport").files[0];
		var formData = new FormData();
            formData.append('id', id);
            formData.append('file', file);
            $.ajax({
                url: "saveUserSign.action",
                type: "post",
                data: formData,
                contentType: false,
                processData: false,
                mimeType: "multipart/form-data",
                dataType : 'json',
                success: function (data) {
                	if(data.state == "Y"){
                		$.messager.alert("提示信息",data.msg,"");
                		$("#dlg-upload").dialog('close');
                	}else{
                		$.messager.alert("提示信息",data.msg,"error");
                	}
                },
                error: function (data) {
                    $.messager.alert("提示信息","操作失败！！","error");
                }
            });

}
    </script>
</head>
<body>
<input type="hidden" id="userId" value=""/>
<div class="easyui-layout" fit="true">
 <div  data-options="region:'north'" style="height:80px;">
	<fieldset style="margin:5px;padding-right:20px;padding-left:60px;">
	<legend><strong>用户信息检索</strong></legend>
	<div id="search" style="padding-right:20px;padding-left:10px;">
		&nbsp;&nbsp;&nbsp;&nbsp;登录名: <input id="log_Name_s" name="log_Name_s" class="easyui-textbox"  data-options="prompt:'登录名'" />
		&nbsp;&nbsp;&nbsp;&nbsp;用户名: <input id="chi_Name_s" name="chi_Name_s" class="easyui-textbox"  data-options="prompt:'用户名'" />
		&nbsp;&nbsp;&nbsp;&nbsp;电话: <input id="phone_num_s" name="phone_num_s" class="easyui-textbox" data-options="prompt:'请输入电话'"/>
		&nbsp;&nbsp;&nbsp;&nbsp;科室:<input id="dep_name" name="dep_name" class="easyui-combobox"  data-options="prompt:'科室'"/>
		&nbsp;&nbsp;&nbsp;&nbsp;角色:<input id="role_name" name="role_name" class="easyui-combobox"  data-options="prompt:'角色'"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="chkItem" name="chkItem" type="checkbox" checked="checked" value="Y"/>启用		
		<input id="chkItem" name="chkItem" type="checkbox" value="N"/>停用	
		<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>&nbsp;&nbsp;&nbsp;&nbsp;   	       
		<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
	</div>
	 </fieldset>
	 </div>
<div data-options="region:'center'" >
 <fieldset style="margin:5px;padding-right:20px;height:95%;">
		<legend><strong>用户管理</strong></legend>
 	<table id="userlist"></table>
 	</fieldset>
</div>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 860,height: 550,closed: true,cache: false,modal: true,top:50"></div>
<div id="editpwd" class="easyui-dialog" data-options="width: 600,height: 300,closed: true,cache: false,modal: true,top:50"></div>
<div id="editupdate" class="easyui-dialog" data-options="width: 860,height: 550,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-s" class="easyui-dialog" data-options="width: 600,height: 500,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg_printdep" class="easyui-dialog" data-options="width: 400,height: 400,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-upload" class="easyui-dialog" title="上传签名" data-options="width: 500,height: 100,closed: true,cache: false,modal: true,top:150">
		<input type="file" id="customImageInfoImport" name="customImageInfoImport" />
  		<input type="submit" onclick="wenjianshangchuan()" value="上传">
</div>
<div id="dlg_resource"></div>	
</body>
</html>