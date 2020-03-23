$(document).ready(function () {
	selectexamflowsholist();	
	$('#toacc').combobox({
		url : 'getDatadis.action?com_Type=USERUSR',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : 200,
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {			
		}
	});	
});

function searchexamflowsholist(){
	var toacc=document.getElementsByName("toacc")[0].value;
	if(toacc==''){
		$.messager.alert("操作提示", "请选择操作员","error");	
	}else if($("#start_date").datebox('getValue')==''){
		$.messager.alert("操作提示", "起止时间无效","error");	
	}else if($("#end_date").datebox('getValue')==''){
		$.messager.alert("操作提示", "起止时间无效","error");	
	}else{
		selectexamflowsholist();
	}
}

function selectexamflowsholist(){
	
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 gettotalcounts();
	 var model={
             "toacc":document.getElementsByName("toacc")[0].value,
			 "time1":$("#start_date").datebox('getValue'),	
			 "time2":$("#end_date").datebox('getValue'),
			 "types":document.getElementsByName("actiontype")[0].value
	 };
		$("#selectexamflowsholist1").datagrid({
		     queryParams:model,
			 url:'examflowListtotalcountall.action',
			 dataType: 'json',
		     pageSize: 20,//每页显示的记录条数，默认为10 
		     pageList:[20,30,45,50,80,100],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'center',field:'datetimes',title:'日期'},
			 	{align:'right',field:'counts',title:'人数'}
			 	]],	    	
		    	onLoadSuccess:function(value){ 
		    		$("#datatotal").val(value.total);
		    		$(".loading_div").remove();
		    	},
				height: window.screen.height-320,
				nowrap:false,
				rownumbers:true,
		    	singleSelect:false,
			    collapsible:true,
				pagination: true,
			    fitColumns:true,
			    striped:true     
		});
  }

function gettotalcounts(){
	$.post("examflowListtotalallall.action", 
			{
		      "toacc":document.getElementsByName("toacc")[0].value,
		      "time1":$("#start_date").datebox('getValue'),	
		      "time2":$("#end_date").datebox('getValue'),
		      "types":document.getElementsByName("actiontype")[0].value
			}, function(jsonPost) {
				//var customertotal = eval(jsonPost);
				document.getElementById("countss").firstChild.nodeValue=jsonPost;				
			}, "json");

}
