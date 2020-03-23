$(document).ready(function () {
	gettermaccountlistGrid();
});

	 /**
	  * 
	  */
function gettermaccountlistGrid() {
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
			+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);

	var model = {
		"acc_num" : $("#acc_num").val()
	};
	$("#teamAccountExamShow").datagrid({
		url : 'teamAccountExamListShow.action',
		dataType : 'json',
		queryParams : model,
		toolbar : '#toolbar',
		rownumbers : true,
		pageSize : 200,// 每页显示的记录条数，默认为10
		pageList : [200,300,500 ],// 可以设置每页记录条数的列表
		columns : [ [ {field:'checkss',checkbox:true },
		              {align : 'center',field : 'acc_num',title : '结算单号',	width : 20}, 
		              {align : 'center',field : 'exam_num',title : '体检编号',width : 25,sortable:true}, 
		              {align : 'center',field : 'id_num',title : '身份证号',width : 40,sortable:true}, 
		              {align : 'center',field : 'user_name',title : '姓名',width : 25,sortable:true}, 
		              {align : 'center',field : 'sex',title : '性别',width : 10,sortable:true}, 
		              {align : 'center',field : 'age',title : '年龄',width : 10,sortable:true}, 
		              {align : 'center',field : 'phone',title : '电话',width : 15,sortable:true},
		              {align : 'center',field : 'dep_name',title : '部门',width : 15,sortable:true}, 
		              {align : 'center',field : 'tjlx',title : '体检类型',width : 15,sortable:true}, 
		              {align : 'center',field : 'status',title : '状态',width : 15,sortable:true},		              
		              {align : 'center',field : 'join_date',title : '体检日期',width : 15,sortable:true}, 		              
		              {align : 'center',field : 'totalamt',title : '结算金额',	width : 15,sortable:true}, 
		              {align : 'center',field : 'isPrePays',title : '是否预结算',	width : 15}, 
		              {align : 'center',field : 'isnotPays',title : '是否含弃检',	width : 15}, 
		              {align : 'center',field : 'ck',title : '查看',	width : 10,"formatter" : f_show} 
		              ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			getteamtotalinfo();
			$(".loading_div").remove();
			if($("#feeresourceflag").val()=='0'){
				 $("div.datagrid-toolbar [id ='6']").eq(0).hide();  
			}
		},
		singleSelect:false,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    remoteSort:true,
	    striped:true,
		toolbar : toolbar
	});
}
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
	    id:1,
		text:'新增人员',
		width:90,
		iconCls:'icon-add',
	    handler:function(){
	       var url='termaccountusershow.action?company_id='+$("#company_id").val()+'&batchid='+$("#batch_id").val()+'&acc_num='+$("#acc_num").val();
	 	   newWindow = window.open(url, "团体结算查询人员", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	 	  newWindow.focus();
    	    
	    }
	},{
		id:2,
		text:'特殊人员和项目',
		width:140,
		iconCls:'icon-add',
	    handler:function(){
	       var url='termaccountusertsshow.action?company_id='+$("#company_id").val()+'&batchid='+$("#batch_id").val()+'&acc_num='+$("#acc_num").val();
	 	   newWindow = window.open(url, "团体结算-特殊人员和项目新增", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	 	  newWindow.focus();
	    }
	},{
		id:3,
		text:'删除人员',
		width:140,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var barids="";
	    	var checkedItems = $('#teamAccountExamShow').datagrid('getChecked');
	    	 $.each(checkedItems, function(index, item){
	    		 barids=barids+","+(item.exam_num);
	    	    }); 
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    delteamuserrow(barids);
	    }
	},{
		id:4,
		text:'打印结算单',
		width:140,
		iconCls:'icon-print',
	    handler:function(){
	    	var ids = $("#acc_num").val();	    	
	    	printreport(ids,2,1);  	    
	    }
	},{
		id:5,
		text:'导出结算单',
		width:140,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = $("#acc_num").val();	    	
	    	printreport(ids,3,1); 
	    }
	},{
		id:6,
		text:'执行减免',
		width:140,
		iconCls:'icon-check',
	    handler:function(){ 	    	
	    	  var url='termdecchargesshow.action?company_id='
	    		  +$("#company_id").val()+'&batchid='+$("#batch_id").val()+'&acc_num='
	    		  +$("#acc_num").val();
		 	   newWindow = window.open(url, "减免操作页面", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
		 	  newWindow.focus();
	    	}
	},{
		id:7,
		text:'导出人员',
		width:100,
		iconCls:'icon-check',
	    handler:function(){
	    	var data = $("#teamAccountExamShow").datagrid('getSelections');
	    	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
	    	if(data.length == 0){
//	    		$.messager.alert("操作提示", "请选择需要导出的人员","error");
//	    		return;
	    		var filed1 =$(".datagrid-sort-asc").parent().eq(0).attr('field');
				var filed2 = $(".datagrid-sort-desc").parent().eq(0).attr('field');
				var sort = undefined;order = undefined;
				if(filed1 != undefined){
					sort = filed1;
					order = 'asc';
				}
				if(filed2 != undefined){
					sort = filed2;
					order = 'desc';
				}
	    		var model = {
	    				"acc_num" : $("#acc_num").val(),
	    				"page":1,
	    				"rows":10000,
	    				"sort":sort,
	    				"order":order
	    			};
	    		$.ajax({
					url : 'teamAccountExamListShow.action',
					data : model,
					type : "post",//数据发送方式
					async : false,
					success : function(text) {
						var temp = eval('('+text+')');
						if(temp.rows.length == 0){
							$(".loading_div").remove();
							$.messager.alert("操作提示", "没有需要导出的人员信息!","error");
							return;
						}
						data = temp.rows;
					}
				});
	    	}
	    	var filelist = new Array();
	    	var obj = new Object();
	    	obj.acc_num = "结算单号";
	    	obj.exam_num = "体检编号";
	    	obj.id_num = "身份证号";
	    	obj.user_name = "姓名";
	    	obj.sex = "性别";
	    	obj.age = "年龄";
	    	obj.phone = "电话";
	    	obj.dep_name="部门";
	    	obj.tjlx="体检类型";
	    	obj.status ="状态"
	    	obj.join_date = "体检日期";
	    	obj.totalamt = "结算金额";
	    	obj.isPrePays = "是否预结算";
	    	obj.isnotPays = "是否含弃检";	    	
	    	filelist.push(obj);
	    	
	    	for(i=0;i<data.length;i++){
	    		obj = new Object();
	    		obj.acc_num = data[i].acc_num;
		    	obj.exam_num = data[i].exam_num;
		    	obj.id_num = data[i].id_num;
		    	obj.user_name = data[i].user_name;
		    	obj.sex = data[i].sex;
		    	obj.age = data[i].age;
		    	obj.phone = data[i].phone;
		    	obj.dep_name=data[i].dep_name;
		    	obj.tjlx=data[i].tjlx;
		    	obj.status =data[i].status;
		    	obj.join_date = data[i].join_date;
		    	obj.totalamt = data[i].totalamt;
		    	obj.isPrePays = data[i].isPrePays;
		    	obj.isnotPays = data[i].isnotPays;
		    	filelist.push(obj);
	    	}
	    	$.ajax({
				url : 'saveDatagridData.action',
				data : {filelist:JSON.stringify(filelist)},
				type : "post",//数据发送方式   
				success : function(text) {
					$(".loading_div").remove();
					window.location.href='datagridExportExcel.action';
						},
				error : function() {
						}
				});
	    }
	}];
 
 /**
  * 批量删除
  */
 var ids;
 function delteamuserrow(ids){
	 if(ids.length<=0){
 		$.messager.alert("操作提示", "请选择体检编号","error");
 	}else if($("#acc_num").val()==""){
 		$.messager.alert("操作提示", "结算单号无效","error");
 	}else{	 
	    $.messager.confirm('提示信息','是否确定删除选中体检人员？',function(r){
		 	if(r){
		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	     $.ajax({
			url : 'teamdelexamnumdo.action',
			data : {
				    batchid:$("#batch_id").val(),
				    acc_num : $("#acc_num").val(),
		            ids:ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						getteamnumdo();
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							gettermaccountlistGrid();
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
 }
 
 function windowcloses(){
	 var _parentWin =  window.opener ; 
		_parentWin.getTeamAccountForBatch();
		window.close();
 }
 
 function getteamnumdo(){
	 $.ajax({
			url : 'setTeamAccountcountdo.action',
			data : {
				acc_num : $("#acc_num").val(),
				batchid: $("#batch_id").val()
			},
			type : "post",// 数据发送方式
			success : function(text) {
				
			},
			error : function() {				
			}
		});
	
 }
 
 /**
  * 显示一条方案
  * @param val
  * @param row
  * @returns {String}
  */
  function f_show(val,row){	
	  return '<a href=\"javascript:f_teamexaminfo(\''+row.acc_num+'\',\''+row.exam_num+'\')\">查看</a>';
  }
  
  var examaccnum,examnumss;
  function f_teamexaminfo(examaccnum,examnumss){
	  var url='termexamitemlistshow.action?exam_num='+examnumss+'&acc_num='+examaccnum;
	  newWindow = window.open(url, "体检人员结算明细", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	  newWindow.focus();
  }
  
  /**
   * 显示一条方案
   * @param userid
   */
  function f_batchshow(){
 	 	$("#dlg-show").dialog({
 	 		title:'单独查询体检任务',
 	 		href:'batchoneshow.action?id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()
 	 	});
 	 	$("#dlg-show").dialog('open');
 	 }
  

 /**
 * 增加结算单
 * @param val
 * @param row
 * @returns {String}
 */
 function f_xg(val,row){	
 	return '<a href=\"javascript:f_shht(\''+row.batch_id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"新增结算\" /></a>';
 }
 
 function getteamtotalinfo() {
		$.post(
			"teamItemCount.action",
			{
				"acc_num" : $("#acc_num").val()
			},
			function(jsonPost) {
			    var customertotal = eval(jsonPost);
			    //document.getElementById("zrs").firstChild.nodeValue = customertotal.totalcustume;
				document.getElementById("zrc").firstChild.nodeValue = customertotal.totalexam;
			    document.getElementById("ysje").firstChild.nodeValue = customertotal.totalAmt;
				document.getElementById("ssje").firstChild.nodeValue = customertotal.termAmt;
				document.getElementById("yhje").firstChild.nodeValue = customertotal.personAmt;
			}, "json");
	}
 
 
 var totaltypeid,totalcon;
 function printreport(rowacc_num,totalcon,totaltypeid){		
		var exeurl="teamaccountServices://&"+rowacc_num+"&"+totalcon+"&"+totaltypeid;
		 RunReportExe(exeurl);
	 }
 
 function RunReportExe(strPath) {
	 location.href=strPath;
 }
 