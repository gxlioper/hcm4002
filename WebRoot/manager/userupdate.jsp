
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/sxtCutPic.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jpgcam/webcam.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/sxtCutPic.js"></script>
<script>
	$(function(){
		//修改页面加载时，显示原图片
		document.getElementById("exampic").src="getdjtexamPhoto.action?others="+$("#user_pic_path").val()+"&"+new Date().getTime();
		$('#username,#name').validatebox({   
   		 	required: true    	
		})
		
		$("#username").validatebox({
			required:true,
			validType:'CHS'
		});
		$("#username").change(function(){
			$("#message").html();
		});
		$("#username").blur(function(){
			var flag=$("#username").validatebox('isValid');
			if((/[\u0391-\uFFE5]/g.test($("#username").val()))){
				$("#message").attr('value','no');
				$("#message").html('不能输入中文');
				return true;
			}
			if(flag){
				var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		   	    $("body").prepend(str);
					$.ajax({
						url:'checkUsername.action?username='+$("#username").val(),
						type:'post',
						success:function(data){
							$(".loading_div").remove();
							var obj=eval(data);
							if(obj=='no'){
								//$("#username").addClass('validatebox-invalid');
								$("#message").attr('value','no');
								$("#message").html('用户名称已存在');
								return true;
							}else if(obj=='ok'){
								$("#message").attr('value','ok');
								//$("#username").removeClass('validatebox-invalid');
								$("#message").html('用户名称可用');
								return false;
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
				}
			
		});
		
		$("#email").validatebox({
			required:false,
			validType:'email'
		});
		$("#work_num").validatebox({
			required:false,
			validType:'maxLength[30]'
		});
		
	});
	$(function(){
		$('input').attr("maxlength","20");
	})
	
	//数据保存
	function f_userSave_up(){
			if (document.getElementById("username").value == ''){
				$("#username").focus();
				return;
			}else if((/[\u4e00-\u9fa5]/g).test(document.getElementById("username").value)){
				$("#username").focus();
				return;
			}else if($("#message").val()=='no'){
				$("#username").focus();
				return;
			}else if (document.getElementById("name").value == ''){
				$("#name").focus();
				return;
			}
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
	$.ajax({
        url:'usereditdo.action?language='+$("#language").val(),  
        data:{
          id:$("#id").val(),
          username:$("#username").val(),
          name:$("#name").val(),
          tel1:$("#tel1").val(),
          tel2:$("#tel2").val(),
          email:$("#email").val(),
          user_notices:$("#user_notices").val(),
          user_pic_path_edit:$("#user_pic_path").val(),
          usertype:$("#usertype").val(),
          passwd:$("#passwd").val()
          },          
        type: "post",//数据发送方式   
          success: function(data){
        	  $(".loading_div").remove();
          		var obj=eval("("+data+")");
          		$.messager.alert('提示信息', obj,function(){});
          	 $("#editupdate").dialog("close");
              	getGrid();
          		
            },
            error:function(){
            	$(".loading_div").remove();
            	 $("#editupdate").dialog("close");
            	$.messager.alert('提示信息', "用户操作失败！",function(){});
            }  
    });            
}
</script>

<!--拍照-->
<div id="upload_poto_div" style="z-index: 89999;visibility:hidden ">
  <div style="float:right; top:2; margin-right:5px;"> <a style="font-size:20px; font-weight:bold; color:#FFF;" href="javascript:void(0)" onclick="Closeupload_poto_div()" title="关闭"> 关  闭 </a></div>
  <div id="title"><span>在线拍照</span></div>
  <div id="my_camera" style="width:150px;height:300px;float:left;"></div>
  <div id="img_upload" style="width:40%;float:right;"><img id="temppicshow" src="<%=request.getContextPath()%>/themes/default/images/user.png" width="200px" height="300px" /></div>
  <div style=" width:240px; margin:0 auto; margin-top:10px; overflow:hidden; text-align:center;">
      <a  href="javascript:void(0)" onClick="Webcam.unfreeze();"><img src="<%=request.getContextPath()%>/images/sxt.png" /><br>重拍</a>
      <a  href="javascript:void(0)" onClick="do_photo();"><img src="<%=request.getContextPath()%>/images/paizhao.png" /><br>拍照</a>
      <a  href="javascript:void(0)" onClick="do_upload_user()"><img src="<%=request.getContextPath()%>/images/shangchuan.png" /><br>确定</a>
      </div>
</div>
<input id="myphotodata" type="hidden" name="myphotodata" value=""/>
<!-- 拍照结束 -->

<!--数据上传-->
<div id="upload_photofile_div" style="left:550;top:100;z-index: 89999;visibility:hidden ">
  <div style="float:right; top:2; margin-right:5px;"><a style="font-size:20px; font-weight:bold; color:#FFF;" href="javascript:void(0)" onclick="closeuploadshow()" title="关闭"> 关  闭 </a> </div>
  <div id="title"><span>上传文件</span></div>
  <input type="file" id="customImageInfoImport" name="customImageInfoImport" />
  <input type="submit" onclick="wenjianshangchuan_user()" value="上传">
</div>
<!--数据上传结束-->
<input type="hidden" id="user_pic_path" value="<s:property value="user_pic_path"/>">
<form id="addForm">
<div class="formdiv" style="display:inline-block;float: left;">
	<br/>
			<dl>
				<dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>登录名称：</dt>
				<s:if test="username=='admin'">
				<dd>
					
						<input type="hidden" class="textinput"  name="username" id="username" value="<s:property value="username"/>" />
						<s:property value="username"/> 
				</dd>
				</s:if>
					<s:else>
						<dd>
						<input type="text" class="textinput"  name="username" id="username" maxlength=15  value="<s:property value="username"/>" style="width:244px;"/> <strong class="red">*</strong>
				</dd>
				<dt  class="autoWidth"><span  id="message" class="red"></span></dt>
				</s:else>
				
			</dl>
			
			<dl>
				<dt>姓名：</dt>
				<dd><input type="text" class="textinput" style="width:244px;" value="<s:property value="name"/>" name="name" id="name" /> <strong class="red">*</strong></dd>
			</dl>
			
			<dl>
				<dt>密码：</dt>
				<dd><input type="password" class="textinput" style="width:244px;"  name="passwd" id="passwd" /></dd>
			</dl>
			
			<dl>
				<dt>联系电话：</dt>
				<dd><input type="text" class="textinput" style="width:244px;" value="<s:property value="tel1"/>" name="tel1" id="tel1" /></dd>
			</dl>
			
			<dl>
				<dt>Email：</dt>
				<dd><input type="text" class="textinput" style="width:244px;" value="<s:property value="email"/>" name="email" id="email" /></dd>
			</dl>
		    <dl>
				<dt>用户简介：</dt>
				<dd><textarea style="width:320px;resize:none;" cols="66" rows="4" id="user_notices" name="user_notices" ><s:property value="user_notices"/></textarea></dd>
			</dl>
			<input type="hidden" name="usertype" id="usertype" value="<s:property value="usertype"/>"/>
	</div>
		<div style="display:inline-block;padding-top: 16px;margin-left: 180px;">
		
			<img id="exampic" style="height:120px;width:100px;" src="<%=request.getContextPath()%>/themes/default/images/user.png" /><br/>&nbsp;&nbsp;&nbsp;
			<a  href="javascript:void(0)" onClick="doload_photo_load()"><img style="height:25px;width:25px;" title="摄像头拍照" src="<%=request.getContextPath()%>/themes/default/images/sxt.png" /></a>&nbsp;&nbsp;&nbsp;
		    <a href="javascript:void(0)" onClick="upload_excel_btn()"><img style="height:25px;width:25px;" title="照片文件上传" src="<%=request.getContextPath()%>/themes/default/images/btndi.png" /></a>
			
	</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:f_userSave_up()" class="easyui-linkbutton c6" style="width:80px;" >保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#editupdate').dialog('close')">关闭</a>
	</div>
	
</div>
</form>
