<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/sfzCard.js?randomId=<%=Math.random()%>"></script>
<script>
function regjzkread_cardno(){
	  var mc_no=readCardJZK_DH();
	  if(mc_no>0){
		  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
           $.ajax({
		         url : 'djtjzkinfodoreg.action',
		         data : {
		        	 mc_no:mc_no,
		        	 exam_id:$("#exam_id").val(),
		        	 exam_num:$('#exam_num').val()
		         },
				 type : "post",//数据发送方式   
				 success : function(text) {
					$(".loading_div").remove();
					if(text.indexOf("error")==0){
						$.messager.alert("操作提示", text.split("-")[1], "error");
					}else if(text.indexOf("errorexam")==0){
						$("#showmsg").html('医保卡对应体检编号： '+text.split("-")[1]+' 存在未交付项，请先撤销此体检编号的缴费项以后再进行此操作！');
					}else{
						var cumtomersddata=JSON.parse(text);
						setjzkCustomer_selectreg(cumtomersddata);
						$('#dlg-edit').dialog('close');
					}					
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			 });
	  }else{
		  $.messager.alert("操作提示","读取就诊卡错误！", "error");  
	  }
}

//身份证获取挂号信息
function regsfzread_cardno(){
	  var sfz_div_code = $("#sfz_div_code").val();
	  var data=readCardSFZ(sfz_div_code);    	
    if(data.error_flag=="0"){
    	var certno=data.certno;
    	if(certno.length==18){
		  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
             $.ajax({
		         url : 'djtjzkinfodoreg.action',
		         data : {
		        	 mc_no:certno,
		        	 exam_id:$("#exam_id").val(),
		        	 exam_num:$('#exam_num').val()
		         },
				 type : "post",//数据发送方式   
				 success : function(text) {
					$(".loading_div").remove();
					if(text.indexOf("error")==0){
						$.messager.alert("操作提示", text.split("-")[1], "error");
					}else if(text.indexOf("errorexam")==0){
						$("#showmsg").html('医保卡对应体检编号： '+text.split("-")[1]+' 存在未交付项，请先撤销此体检编号的缴费项以后再进行此操作！');
					}else{
						var cumtomersddata=JSON.parse(text);
						setjzkCustomer_selectreg(cumtomersddata);
						window.close();;
					}			
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			 });
    	}else{
//    		$.messager.alert("操作提示","读取身份证号错误！", "error");  
    		$.messager.alert("操作提示", data.error_msg, "error");
    	}
    }
	  
}
</script>
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
<!-- 定义身份证设备 -->
<OBJECT ID='GT2ICROCX' width='0' height='0' <s:property value="sfz_div_ocx"/>></OBJECT>
<input type="hidden" id="sfz_div_code" value="<s:property value="sfz_div_code"/>">
<!-- 定义身份证设备结束 -->
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>读取就诊卡或者身份证获取挂号信息</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>
				读取就诊卡挂号
			</dt>
			<dd><a  href="javascript:void(0)" onClick="regjzkread_cardno();"><img style="height:25px;width:25px;"  title="就诊卡获取人员信息" src="<%=request.getContextPath()%>/themes/default/images/bendi.jpg" /></a></dd>
			<dt>
				读取身份证挂号
			</dt>
			<dd><a href="javascript:void(0)" onClick="regsfzread_cardno();"><img  title="身份证获取挂号信息" style="height:25px;width:25px;" src="<%=request.getContextPath()%>/themes/default/images/pic.jpg" /></a></dd>		
					
		</dl>
		
		<dl>
			<dt id="showmsg" style="width:400px;height:26px;">
				
			</dt>
					
		</dl>
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	   
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close();" >关闭</a>
	</div>
</div>