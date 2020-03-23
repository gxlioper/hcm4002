<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>

.uboder1{margin:0px;padding:0px;border:none}
.uboder{
	margin:0px;padding:0px;
	border:1px solid #a0b3d6;
	width:80.5%;
	margin-left:100px;
} 

.jre{}
.test_box {
    width: 80%; 
    height: 25px; 
    line-height: 1.5;
    border: 1px solid #a0b3d6; 
    margin-left:10px;
    resize:none;
}

.views{ overflow:hidden; width:100%;}
.views li{ margin:5px 0; line-height:20px; float:left; width:49%;}
.views li label{ width:150px; display:block; float:left; text-align:right;}

.xscyc{min-height:50px;overflow-y:auto;max-height:330px;border:solid 1px #3399cc;position:absolute;z-index:10000;background:#ffffff;display:none}
.xscyc ul{ width:auto !important; float:none;}

.xscyc ul li{ margin:0 !important; display:block; line-height:22px;width:100%}
.xscyc ul li:hover{background:#3399cc;color:#ffff;}

</style>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/departAndFinaly.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
$(document).ready(function () {
	document.getElementById("ub").className = 'uboder1';
	var shuzi="class='test_box result jre ' onkeypress='return disableEnter(event)'";
	var row='';
	row+="<span style='float:left;'>"+"驳回原因："+"</span>";
	row+="<textarea "+shuzi+" style='float:left;' id='bohui_text' type='text' onblur='gb()' onkeyup='bhyj(this)'  onclick='bhyj(this);' >"+"</textarea>";
	$("#dt").html(row);
	$("#bohui_text").textareaAutoHeight({minHeight: 120, maxHeight: 200});
});
//点击带出下拉列表
function bhyj(ths){
	$.ajax({
		url:'getRejectionlistshow.action',
		type:'post',
		success:function(data){
				var jcxm=eval('('+data+')');
				if(jcxm.length>0){
					var str = '';
					for(var i=0;i<jcxm.length;i++){
						
						str+="<li style='width:360px;margin:0px;padding:0px;' onclick='zhi(this,"+i+")' id='jcxm_"+i+"'>"+jcxm[i].reject_context+"</li>";
					}
					 var left = $(ths).offset().left - document.body.clientWidth*0.25-2;
					var height = $(ths).height();
					var top = $(ths).offset().top+height-25;
					var width = $(ths).width()+6; 
					
					$(".xscyc").css("width",width+'px');
					$(".xscyc").css("left",left+'px');
					$(".xscyc").css("top",top+'px');
					document.getElementById("ub").className = 'uboder';
					$("#str").html(str);
					$("#str").show();
				}
			}
	});
}
var yjnum=1;
//li标签点击事件
var reject_context = new Array();
function zhi(va,id){
	if($("#bohui_text").val()==""){
		$("#bohui_text").val($(va).text());
	}else{
		$("#bohui_text").val($("#bohui_text").val() +','+$(va).text());
	}
	
/* 	 if($("#bhjj").val()==""){
		$("#bhjj").val("("+yjnum+")、"+$(va).text());
	}else{
		yjnum++;
		$("#bhjj").val($("#bhjj").val() +'\n'+'('+yjnum+')、'+$(va).text());
	}  */
	$("#str").hide();
	document.getElementById("ub").className = 'uboder1';
}


var pd=1;
//鼠标离开到下拉div
function select_com_list_amover(){
	pd=1;
}
//鼠标移动到下拉div
function select_com_list_mover(){
	pd=2;
}
//失去焦点
function gb(){
	//移动到div不执行焦点事件
	if(pd!=2){
		$("#str").hide();
		document.getElementById("ub").className = 'uboder1';
	}
}

function f_save_bohui(){
	if($("#bohui_text").val() == ''){
		$.messager.alert("警告信息","请输入驳回原因!","error");
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveExamSummaryRehectInfo.action',
		type:'post',
		data:{'exam_info_id':$("#exam_id").val(),'final_exam_result':$("#bohui_text").val(),'exam_num':$("#exam_num").val()},
		success:function(data){
			$(".loading_div").remove();	
			if(data.split("-")[0] == 'ok'){
				$.messager.alert("操作提示",data.split("-")[1], "info",close_page);
				$('#dlg-bohui').dialog('close');
			}else{
				$.messager.alert("警告信息",data.split("-")[1],"error");
			}
		},
		error:function(){
			$(".loading_div").remove();
			$.messager.alert("警告信息","操作失败","error");
		}
	});
}

</script>


	<div class="formdiv fomr3" style="padding-top:20px;">
		<div>
			<ul id='dt' class="views" onkeydown="keyDown(event)"></ul>
		</div>
		<div id = "ub" style="height:150px;margin-left: 79px;overflow:auto;" onmouseover="select_com_list_mover()" onmouseout="select_com_list_amover()">
				<ul id="str" class="uboder1" style="margin-left: 10%;width:15%; margin-bottom: 20px;"></ul>
		</div>
		 <!-- <div>
			<textarea style="width:355px;margin-left:67px;resize:none;height:133px;" id="bhjj" ></textarea>
		</div>  -->
				
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_save_bohui();">确定驳回</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-bohui').dialog('close')">关闭</a>
	</div>
</div>



