$(document).ready(function () {
	getgroupuserGrid();		
});

 //---------------------------------------显示人员------------------------------------------------------
 /**
  * 
  */
 function getgroupuserGrid(){
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str); 
	     $("#cptmanagershow").treegrid({
		    url:'cptmanagerList.action',
		    toolbar:'#toolbar',
		    animate: true, 
		    collapsible: true,
			fit:"true",  
			dataType: 'json',
            idField:'id',  
            parent: 'parent_id',  // the node has a 'id' value that defined through 'idField' property
            treeField:'report_name',  
            pageSize:10000,
		 columns:[[
		    {align:'left',field:'report_name',title:'报表名称',width:40},
		    {align:'left',field:'report_address',title:'报表地址',width:48},
		 	{align:'center',field:'seq_code',title:'索引',width:10},
		    {align:'center',field:'report_type',title:'类型',width:25},
		    {align:'center',field:'report_edit',title:'修改',width:10,"formatter":f_showedit},
	        {align:'center',field:'report_del',title:'删除',width:10,"formatter":f_showdel}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$(".loading_div").remove();
	    	},
	    	onLoadError: function (m) {
	    		$(".loading_div").remove();
	    		alert_autoClose("操作提示", "查询失败", "error");
	    	},
	    	onBeforeExpand:function(row){  
	            //动态设置展开查询的url  
	            var url = "cptmanagerList.action?parent_id="+row.id;  
	            $("#cptmanagershow").treegrid("options").url = url;  
	            return true;      
	        },
			height: window.screen.height-300,
            pagination:false,  
	        toolbar:toolbar	       
	});
	}

 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'新增',
		width:90,
		iconCls:'icon-add',
	    handler:function(){
	    	$("#dlg-reportedit").dialog({
		 		title:'新增报表模板',
		 		href:'reportmanageradd.action'
		 	});
		 	$("#dlg-reportedit").dialog('open');
	    }
	}];

 
 /**
  * 修改
  * @param val
  * @param row
  * @returns {String}
  */
  function f_showedit(val,row){
	  return '<a href=\"javascript:f_reportoneeditshow('+row.id+')\">修改</a>';
  } 
  
  var reportid;
  function f_reportoneeditshow(reportid){
	  $("#dlg-reportedit").dialog({
	 		title:'修改报表模板',
	 		href:'reportmanageredit.action?id='+reportid
	 	});
	 	$("#dlg-reportedit").dialog('open');
  }
  
  /**
   * 删除
   * @param val
   * @param row
   * @returns {String}
   */
   function f_showdel(val,row){
 	  return '<a href=\"javascript:f_reportonedelshow('+row.id+')\">删除</a>';
   } 
   
   function f_reportonedelshow(reportid){
	 $.messager.confirm('提示信息','是否删除模板？',function(r){
	 if(r){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $.ajax({
			url : 'reportmanagerdel.action',
			data : {
				    id:reportid
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getgroupuserGrid();
							$.messager.alert("操作提示", text);
														
						} else {
							$.messager.alert("操作提示", text, "error");
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
 
   