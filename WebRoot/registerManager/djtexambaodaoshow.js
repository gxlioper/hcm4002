$(document).ready(function () {
	getbaodaoGrid();	
});

 //---------------------------------------显示人员------------------------------------------------------
 /**
  * 
  */
 function getbaodaoGrid(){
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "id_num":$("#id_num").val()
		 };
	     $("#djtexambaodaolistshow").datagrid({
		 url:'djtexambaodaolistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
	     pageSize: 75,//每页显示的记录条数，默认为10 
	     pageList:[75,150,300,500,1000],//可以设置每页记录条数的列表 
		 columns:[[
            {align:'center',field:'exam_num',title:tjhname,width:18},
		    {align:'center',field:'arch_num',title:dahname,width:18},
		    {align:'center',field:'exam_types',title:'体检类型',width:18},	
		 	{align:'center',field:'id_num',title:'身份证号',width:35},
		 	{align:'center',field:'user_name',title:'姓名',width:20},
		 	{align:'center',field:'sex',title:'性别',width:10},
		 	{align:'center',field:'is_marriage',title:'婚否',width:10},
		 	{align:'center',field:'age',title:'年龄',width:10},	 	
		 	{align:'center',field:'register_date',title:'登记日期',width:15},
		 	{align:'center',field:'statuss',title:'报到状态',width:20},
		    {align:'center',field:'exambd',title:'报到',width:10,"formatter":f_showexambaodao}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
			 rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true      
	});
	}

 /**
  * 显示一条
  * @param val
  * @param row
  * @returns {String}
  */
  function f_showexambaodao(val,row){
	  return '<a href=\"javascript:f_exambaodaoshow(\''+row.exam_num+'\')\">报到</a>';
  } 
  
  var exam_num;
  function f_exambaodaoshow(exam_num){
  $.messager.confirm('提示信息','是否确定报到？',function(r){
  	if(r){
  		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		$.ajax({
			url : 'djtexamInfoStatusdo.action',
			data : {
				    exam_num:exam_num
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							$('#dlg-custedit').dialog('close');
							getgroupuserGrid();
							if(($('[name=isprintdjd]:checked').val()!=undefined)&&($('[name=isprintdjd]:checked').val()=='1')){
								 printDJD_exe(exam_num,'\''+exam_num+'\'');
							 }
						} else {							
							$.messager.alert("操作提示", text.split("-")[1], "error");
						}
						
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
  	}
  });
  }
  