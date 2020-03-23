$(document).ready(function () {
	selectexamflowsholist();	
	$('#exam_num').textbox('textbox').focus();
	$('#exam_num').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	selectexamflowsholist();
        }
    });
	$('#exam_num').textbox('textbox').css('ime-mode','disabled');
	$('#exam_num').textbox('textbox').focus();
});

function selectexamflowsholist(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 var time1 = '',time2 = '';
	 if($("#ck_date")[0].checked){
		 time1 = $("#time1").datebox('getValue');
		 time2 = $("#time2").datebox('getValue');
	 }
	 var model={
			 "exam_num":$("#exam_num").val(),
			 "username":$("#username").val(),
			 "time1":time1,	
			 "time2":time2,
			 "types":document.getElementsByName("types")[0].value
	 };
		$("#selectexamflowsholist").datagrid({
		     queryParams:model,
			 url:'examflowListshow.action',
			 dataType: 'json',
			 pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'center',field:'exam_num',title:'体检号',width:15},
			 	{align:'center',field:'user_name',title:'姓名',width:15},
			 	{align:'center',field:'sex',title:'性别',width:10},
			 	{align:'center',field:'phone',title:'电话',width:20},		 	
			 	{align:'center',field:'join_date',title:'体检日期',width:15},
			 	{align:'center',field:'statusall',title:'状态',width:10,"formatter":f_showstatus},
			 	{align:'center',field:'batch_name',title:'分发人',width:10},
			 	{align:'center',field:'counter_check',title:'分发日期',width:25},
			 	{align:'center',field:'picture_Path',title:'签收人',width:10},
			 	{align:'center',field:'guide_nurse',title:'签收日期',width:25},
			 	{align:'center',field:'actiontypes',title:'类型',width:10,"formatter":f_showtypes},
			 	{align:'center',field:'remarke',title:'备注',width:85}
			 	]],	    	
		    	onLoadSuccess:function(value){ 
		    		$(".loading_div").remove();
		    		$("#datatotal").val(value.total);
		    	},
		    	height: window.screen.height-400,
				nowrap:false,
				rownumbers:false,
		    	singleSelect:false,
			    collapsible:true,
				pagination: true,
			    fitColumns:true,
			    striped:true       
		});		
  }


function f_showtypes(val,row){
	  if(row.actiontype=='0'){
		  return '<font color="red">'+row.actiontypes+'</font>';
	  }else if(row.actiontype=='1'){
		  return '<font color="blue">'+row.actiontypes+'</font>';
	  }else{
		  return row.actiontypes;
	  }
 }

function f_showstatus(val,row){
	  if(row.status=='Y'){
		  return '<font color="red">'+row.statuss+'</font>';
	  }else if(row.status=='D'){
		  return '<font color="blue">'+row.statuss+'</font>';
	  }else if(row.status=='J'){
		  return '<font color="green">'+row.statuss+'</font>';
	  }else{
		  return row.statuss;
	  }
}
