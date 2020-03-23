<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<script type="text/javascript" src="<%=request.getContextPath()%>/chargingItemSchedule/calendar.js?randomId=<%=Math.random()%>"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({ 
			showButtonPanel:true,
//		 	monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
//          changeMonth:true,
//         	changeYear:true,
//         	showMonthAfterYear:true,//是否把月放在年的后面  
//         	dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'], 
//         	minDate:'2000-01-01',//最小日期  
//          maxDate:'2020-12-30',//最大日期  	
         	onSelect: function(dateText, inst) {
				var aa=$("#datepicker tbody td a");//
				aaar(aa,dateText);
         	},
		}); 
	});
	
	$(document).ready(function (){
		$("#datepicker .ui-datepicker-buttonpane button").click();
		$("#datepicker .ui-datepicker-buttonpane button").css("display","none");
		var aa=$("#datepicker tbody td a");
		for(i=0;i<aa.length;i++){
			aa.eq(i).css("color","#000)");
			aa.eq(i).css("background-color","#E6E6E6");
		}
		date_arry.length=0;
		
		if(user_id_arry.length == 1){
			insert_schedu_one_btn(user_id_arry[0],'<s:property value="working_date"/>');
		}
	});

	</script>
<input type="hidden"  id = "p_id" value="<s:property value='ids'/>" />
<div id="scheduling" >
    <div id="datepicker"></div>
    <div>
    	每天数量<input type="text" class="textinput" value='20'  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  style="height: 26px;margin-top: 10px;" id = "plshangliang">
    </div>
    <div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:insert_schedu_date()" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveReportMail();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
	</div>
	</div>
</div>