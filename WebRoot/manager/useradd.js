
$(document).ready(function(){
   $(this).keypress( function(e) {
   var key = window.event ? e.keyCode : e.which;
   if(key.toString() == "13"){
      return false;
     }
   });   
  
}) 
//控制密码长度
  function check(obj){
    	var value = $(obj).val();
    	var length = value.length;
    	if(length>10){
        		//截取前10个字符
        		value = value.substring(0,10);
        		alert('密码长度不能超过十位');
        		$(obj).attr("value",value);
    	}
}   
     
function f_userSave(){
			if (document.getElementById("username").value == ''){
		  		//alert("用户名称不能为空！");
				//document.getElementById("username").focus();
				$("#username").focus();
				return;
			}else if((/[\u4e00-\u9fa5]/g).test(document.getElementById("username").value)){
				//alert("用户名称不能为汉字！");
				//document.getElementById("username").focus();
				$("#username").focus();
				return;
			}else if($("#message").val()=='no'){
				$("#username").focus();
				return;
			}else if (document.getElementById("passwd").value == ''){
		  		//alert('密码不能为空！');
				//document.getElementById("passwd").focus();
				$("#passwd").focus();
				return;
			}else if (document.getElementById("passwd").value !=  document.getElementById("passwd1").value){
		  		//alert('两次输入的密码不一致！');
				//document.getElementById("passwd").focus();
				$("#passwd1").focus();
				return;
			}else if (document.getElementById("name").value == ''){
		  		//alert('姓名不能为空！');
				//document.getElementById("name").focus();
				$("#name").focus();
				return;
			}/*else if (document.getElementById("work_num").value != ''){
				if($("#message1").attr('value') == 'no'){
					$("#work_num").focus();
					return;
				}
				return;
			}*/
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
	$.ajax({
        url:'usereditdo.action?language='+$("#language").val(),  
        data:{
          id:$("#id").val(),
          username:$("#username").val(),
          passwd:$("#passwd").val(),
          name:$("#name").val(),
          tel1:$("#tel1").val(),
          tel2:$("#tel2").val(),
          email:$("#email").val(),
          //work_num:$("#work_num").val(),
          user_notices:$("#user_notices").val(),
          user_pic_path:$("#user_pic_path").val(),
          usertype:$("#usertype").val()
          },          
        type: "post",//数据发送方式   
          success: function(data){
        	  $(".loading_div").remove();
          		var obj=eval("("+data+")");
          		//alert(obj);
          		$.messager.alert('提示信息', obj,function(){});
          		location.reload();
          	 $("#dlg-edit").dialog("close");
              	getGrid();
          		
            },
            error:function(){
            	$(".loading_div").remove();
            	 $("#dlg-edit").dialog("close");
            	//alert("用户操作失败！");
            	$.messager.alert('提示信息', "用户操作失败！",function(){});
            }  
    });            
}
