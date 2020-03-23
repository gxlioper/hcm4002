$(document).ready(function () {
	selectexamflowsholist();	
	
});

function searchexamflowsholist(){
	if($("#start_date").datebox('getValue')==''){
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
	 var model={
			 "time1":$("#start_date").datebox('getValue'),	
			 "time2":$("#end_date").datebox('getValue'),
			 "types":document.getElementsByName("actiontype")[0].value
	 };
	 gettotalcounts();
		$("#selectexamflowsholist").datagrid({
		     queryParams:model,
			 url:'examflowListtotal.action',
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
	$.post("examflowListtotalall.action", 
			{
		      "time1":$("#start_date").datebox('getValue'),	
		      "time2":$("#end_date").datebox('getValue'),
		      "types":document.getElementsByName("actiontype")[0].value
			}, function(jsonPost) {
				//var customertotal = eval(jsonPost);
				document.getElementById("countss").firstChild.nodeValue=jsonPost;				
			}, "json");
   }
