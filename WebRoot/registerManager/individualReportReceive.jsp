<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
$(document).ready(function() {
	getreportreceve()
});

function closeTab(){
	// window.parent.tabsClose();  
	
}
//--------------------------------获取报告领取方式数据-------------------------------------
function getreportreceve(){
	$.post("getReportReceive.action", 
			{
				"e_num" : $('#s_num').val()
			}, function(jsonPost) {
				var selectways="1";
				var data = eval(jsonPost);
					  $('#r_address').val(data.reportAddress.replace(/\s/g,''));
					  $('#r_ziqu_time').val(data.ziqu_report_time);	
				    if(data.getReportWay!=''){		    	
				    	if(data.getReportWay=='2'){
				    		selectways="2";
				    		dianji('2');
				    	}else{
				    		selectways="1";
				    		dianji('1');
				    	}
				    	//$('#lingqufangshi').attr('value',data2.getReportWay);
				    }else{
				    	dianji('2');
				    }				    
				    $("#lingqufangshi").val(selectways);
			}, "json");
	
}
/**
 * 保存领取方式
 */
function savereportreceve(){
 	
/* 	var sel=$('#lingqufangshi').val();
	if(sel=="1"){
			if($('#receive_address').val()==''){
				$('#receive_address').focus();
				return;
			}
	} */
		var model ={
			   ziqu_report_time:document.getElementsByName("r_ziqu_time")[0].value,
		       receive_type:$('#lingqufangshi').val(),
		       receive_address:$('#r_address').val(),
		       exam_num:$('#s_num').val()
			} 
 	$.ajax({
		url:'saveReportReceive.action',
		data:model,
		type:'post',
		success:function(data){
			alert_autoClose("操作提示", data,"");
			window.parent.ztabsselect();
		},
		error:function(){
			$.messager.alert('提示信息','操作失败','error');
		}
	}) 
} 
//--------------下拉框点击事件-----------
function dianji(obj){
	if(obj=='2'){
		$("#ziqu").css('display','none');
		$("#ziqu_time").css('display','block');
		$("#r_address").val("");
		  $('#r_ziqu_time').datebox({
		        required:true
		    });
		   
		  $("#r_ziqu_time").datebox("setValue", dayTime());
		  $('#lingqufangshi').combobox('setValue', '2');
	}else{
		$("#ziqu").css('display','block');
		$("#ziqu_time").css('display','none');
	}
	
}

function dayTime(){
	   var curr_time = new Date();
	   var str = curr_time.getFullYear()+"-";
	   str += curr_time.getMonth()+1+"-";
	   str += curr_time.getDate()+"-";
	   str += curr_time.getHours()+":";
	   str += curr_time.getMinutes()+":";
	   str += curr_time.getSeconds();
	   return str;
	}
</script>
<body>
	<input type="hidden"   id="s_id"   value="<s:property value = 'id'/>" />
	<input type="hidden"   id="s_num"   value="<s:property value = 'exam_num'/>" />
	 	<div class="formDiv" style="width: 500px;margin: 50px auto;">
			<dl>
				<dt>领取方式</dt>
				<dd>
					<select  name="lingqufangshi"   class="textinput"  id="lingqufangshi" style="width:280px;height: 35px;"   >   
					    <option  value='1'  onclick="dianji('1');" >邮寄报告</option>   
					    <option  value='2'  onclick="dianji('2');" selected="selected">自取报告</option>   
					</select>  
					
				</dd>
			</dl>
			<dl id="ziqu">
				<dt>邮寄地址</dt>
				<dd>
					<input type="text"  value=""  id="r_address" name="" class="textinput" style="width:280px;height: 35px;" />
				</dd>
			</dl>
			 <dl id="ziqu_time">
				<dt>自取时间</dt>
				<dd><input maxlength="10" data-options="validType:'Length[10]'"  type=text id="r_ziqu_time" name="r_ziqu_time" style="width:280px;height: 35px;"></input>
			</dd>
			</dl>
			<dl >
				<dt></dt>
				<dd>
					<input type="button"    onclick="savereportreceve();"  style="width:280px;height:35px;background: #EDEDED;"   value="保存"/>
				</dd>
			</dl>
			</div> 
		<!-- </div> -->
</body>
