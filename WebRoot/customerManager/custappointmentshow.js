$(document).ready(function () { 
	var height = window.screen.height-300;  
	stView_layout = $('#cont-page').layout({  
		height: height 
	});
	stView_layout = $('#custappontshollist').layout({  
	height: height 
	});
	stView_layout = $('#custappontshollistwest').layout({  
		height: height 
	});

	 $('#tjrw').bind('click', function() { 
			select_batchcom_list(this.value);
	    }); 
		
		$('#tjrw').bind('keyup', function() {
			select_batchcom_list(this.value);
	    });
		
		$('#tjrw').bind('blur', function() {
			select_batchcom_list_over();
	    });	
	
	
	$('#com_Name').bind('click', function() {  
		select_com_list();
    }); 
	
	$('#com_Name').bind('keyup', function() {
		select_com_list();
    });

	$('#com_Name').bind('blur', function() {  
		select_com_list_over();
    });
	
	gettimeuserGrid();
});



//获取批次任务/////////////////////////////////////////////////////////////////
function select_batchcom_list(batchcom_Namess){
	var url='getComByBatchList.action?remark=Y';
	var data={"comname":batchcom_Namess};
	load_post(url,data,select_batchcom_list_sess);
	}

/**
* 显示树形结构
* @param data
* @returns
*/
function select_batchcom_list_sess(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"方案批次","javascript:void(0)","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:setvaluebatch('"+data[index].id+"','"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvaluebatch('"+data[index].id+"','"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#com_name_list_div").html(mydtree.toString());
			$("#com_name_list_div").css("display","block");
			
		}

/**
* 点击树设置内容
* @param id
* @param name
* @returns
*/
var partnerId;
var companyidss;
	function setvaluebatch(partnerId,name){
		var batcoms=partnerId.split('-');
		$("#company_id").val(0);
		$("#tjrw").val(name);	
		$("#com_Name").val('');
		$("#batch_id").val(batcoms[1]);
		companyidss=batcoms[0];
		batch_idchange(batcoms[1]);
		hc_mousbatch_select1=false;
		select_batchcom_list_over();
	}

//单位失去焦点
var hc_mousbatch_select1=false;
function select_batchcom_list_over(){
	if(!hc_mousbatch_select1){
	$("#com_name_list_div").css("display","none");
	}
	}

function select_batchcom_list_mover(){
	hc_mousbatch_select1=true;
	}
function select_batchcom_list_amover(){
	hc_mousbatch_select1=false;
	}


//获取单位///////////////////////////////////////////////////////////
//-------------------------------------------单位信息显示-----------------------------------------------------
/**
* 模糊查询公司信息
*/
var hc_com_list,com_Namess;
function select_com_list(com_Namess){
	var url='getbatchcompanyshow.action';
	var data={"company_id":companyidss};
	load_post(url,data,select_com_list_sess);
	}

/**
 * 显示树形结构
 * @param data
 * @returns
 */
function select_com_list_sess(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].id == companyidss)){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#com_name_list_div1").html(mydtree.toString());
			$("#com_name_list_div1").css("display","block");
			
		}

/**
 * 点击树设置内容
 * @param id
 * @param name
 * @returns
 */
	function setvalue(id,name){
		$("#company_id").val(id);
		$("#com_Name").val(name);
		$("#com_name_list_div1").css("display","none");
		//f_getdept();
		
	}

//单位失去焦点
var hc_mous_select1=false;
function select_com_list_over(){
	if(!hc_mous_select1){
	$("#com_name_list_div1").css("display","none");
	}
	}

function select_com_list_mover(){
	hc_mous_select1=true;
	}
function select_com_list_amover(){
	hc_mous_select1=false;
	}


//显示部门
function f_getdept() {
	alert($("#company_id").val());
	$('#levelss').combobox({
		url : 'getCompanForDept.action?company_id='+$("#company_id").val(),
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'dep_Name',
		onLoadSuccess : function(data) {
			$('#levelss').combobox('setValue', data[0].id);				
		}
	});
}

//方案获取分组
var barbatchids
function batch_idchange(barbatchids) {
	$('#group_id').combobox({
		url : 'getBatchForGroup.action?batch_id='+barbatchids,
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'group_name',
		onLoadSuccess : function(data) {
			$('#group_id').combobox('setValue', data[0].id);				
		}
	});
}

function countExamInfotimeGrid(){	
	 var model={
	   "batch_id":$("#batch_id").val(),
	   "company_id":$("#company_id").val()
	 };
    $("#custtimelist").datagrid({
	 url:'countExamInfotime.action',
	 dataType: 'json',
	 queryParams:model,
	 rownumbers:false,
	 columns:[[
	    {align:'center',field:'datetime',title:'日期',width:25},	
	    {align:'center',field:'times',title:'时间',width:30},
	    {align:'center',field:'nums',title:'人数',width:10},
	 	{align:'center',field:'states',title:'说明',width:30},
	 	{align : "center",field : "timesearch",title : "查询",width : 15,"formatter" : f_timesearch}
	 	]],	    	
	 	singleSelect : true,
		collapsible : true,
		pagination : true,
		fitColumns : true,
		autowidth : true,
		striped : true,
		pagination : false,
		pageNumber : 1,
		pageSize : 1000,    
});
}

function f_timesearch(val, row) {
	return '<a href=\"javascript:timesearch(\''+ row.datetime+ '\')\">查询</a>';
}

var seartime;
function timesearch(seartime){
	$("#register_date").datebox('setValue',seartime);
	gettimeuserGrid();
}
 //---------------------------------------显示人员------------------------------------------------------
 /**
  * 
  */
 function gettimeuserGrid(){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":$("#company_id").val(),
				 "batch_id":$("#batch_id").val(),
				 "group_id":document.getElementsByName("group_id")[0].value,			 
				 "arch_num":$("#arch_num").val(),
				 "id_num":"",
				 "birthday":$("#register_date").datebox('getValue'),	
				 "tel":$("#tel").val(),
				 "custname":$("#custname").val()
		 };
	     $("#groupusershow").datagrid({
		 url:'getExamInfoUserList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 75,//每页显示的记录条数，默认为10 
	     pageList:[75,150,300,500,1000],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'arch_num',title:dahname,width:20},	
		    {align:'center',field:'exam_num',title:tjhname,width:20},
		 	{align:'center',field:'id_num',title:'身份证号',width:40},
		 	{align:'center',field:'user_name',title:'姓名',width:20},
		 	{align:'center',field:'sex',title:'性别',width:10},		 	
		 	{align:'center',field:'register_date',title:'体检日期',width:15},			 	
		 	{align:'center',field:'exam_times',title:'体检时间段',width:20},	
		 	{align:'center',field:'remark1',title:'导检单/条码/报到',width:25}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		countExamInfotimeGrid();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar	       
	});
	}
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'设置体检时间',
		iconCls:'icon-save',
	    handler:function(){
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    var ids = "";
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    });
	    	setcusttime(ids);
	    }
	},{
		text:'删除体检时间',
		iconCls:'icon-add',
	    handler:function(){
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    var ids = "";
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    });
	    	 if(ids.length>1){
	    	       ids=ids.substring(1,ids.length);	
	    	    }
	    	 deluserrow(ids);
	    }
	},{
		text:'批量申请',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    });
	    	doSendApplyChargeItem(ids);
	    }
	},{
		text:'批量签到',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    }); 	    	 
	    	batchSign(ids);
	    }
	},{
		text:'打印导检单条码',
		iconCls:'icon-print',
		width:130,
	    handler:function(){
	    	var ids = "";
	    	var barids="";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	        barids=barids+"$"+(item.exam_num);
    	    });
    	    if(ids.length>1) ids=ids.substring(1,ids.length);
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    var falgs='1';
    	    daoyindan_point(ids.split(","),ids,barids,falgs);
	    }
	},{
		text:'打印导检',
		width:90,
		iconCls:'icon-print',
	    handler:function(){
	    	var ids = "";
	    	var barids="";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	        barids=barids+"$"+(item.exam_num);
    	    });
    	    if(ids.length>1) ids=ids.substring(1,ids.length);
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    var falgs='0';
    	    daoyindan_point(ids.split(","),ids,barids,falgs);
    	    
	    }
	},{
		text:'打印条码',
		width:90,
		iconCls:'icon-print',
	    handler:function(){
	    	var barids="";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        barids=barids+"$"+(item.exam_num);
    	    });
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    printBar(barids);
	    }
	},{
		text:'<input name=\"isprintdah\" type=\"checkbox\" value=\"1\"/>打印档案号',
		width:100,
		iconCls:'',
		handler:function(){
	    }
	}];

 
 /**
  * 批量删除
  */
 function deluserrow(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else{	 
	 $.messager.confirm('提示信息','是否确定删除选中人员的体检时间？',function(r){
		 	if(r){
		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	 $.ajax({
			url : 'delcusttimedo.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id:$("#batch_id").val(),
		            ids:ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							gettimeuserGrid();
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
 /**
  * 批量设置体检日期
  */
 function setcusttime(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else if(ids.length<=0){
 		$.messager.alert("操作提示", "请选择体检人员","error");	
 	}else{
 			$("#dlg-custedit").dialog({
		 		title:'设置体检时间段',
		 		href:'setcusttime.action?batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()+'&ids='+ids
		 	});
		 	$("#dlg-custedit").dialog('open'); 			
 		}
 }

 
 
 
 function printBar(barids){
	 if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
		 var exeurl =$("#barexeurl").val() +" barcode "+barids+" * *"; //打印档案号
	 }else{
		 var exeurl =$("#barexeurl").val() +" barcode "+barids+" *"; 
	 }
	 $.ajax({
			url : 'updateSampleExamEetail.action',
			data : {
				ids : barids
			    },
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						RunExe(exeurl);
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});	
 }
 
 //打印导检单和条码
 function daoyindan_point(idessss,idss,barids,falgs) {
	 if(idss.length<=1){
	 		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
	 	}else{
	 if($("#hansidjdflag").val()==1){
		if(idss.split(",").length>10){
			$.messager.alert("操作提示", "每次打印不能超过10个.","error");
		}else{
			doURLPDFPrint_ireport(idss);		
		}		 
	 }else{ 
		 printDJD_exe(barids);//打印条码
	 }
	 if(falgs=='1'){
	 printBar(barids);//打印条码
	 }
	 	}
 }
 
//新版本打印导检单
 function printDJD_exe(barids){
	 if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
		 var exeurl =$("#djdexeurl").val() + "  guid "+barids+" *"; //打印档案号
	 }else{
		 var exeurl =$("#djdexeurl").val() + "  guid "+barids; 
	 }
	 RunExe(exeurl);
 }
 
 function doURLPDFPrint_ireport(idss){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
		$.ajax({
			url:'getDjDpdf.action',
			data:{
				ids:idss,
				frandom:Math.random()
				},
			type:"POST",
			complete:function(res,status){
				if(status=="success"){
					var resText=res.responseText;
					if(resText=="timeout"){
						alert("连接超时");
						}else{

							var filesurl=resText.split("-")[1];

							 var pdf = document.getElementById("createPDF");
							    if (pdf != undefined && pdf != null) {//判断pdf对象是否存在，如果存在就删除该对象
							        var parentNode = pdf.parentNode;
							        parentNode.removeChild(pdf);
							    }
							    var p = document.createElement("object");
							    p.id = "createPDF";
							    p.classid = "CLSID:CA8A9780-280D-11CF-A24D-444553540000";
							    p.width = 1;
							    p._Version=327680;
							    p._ExtentX=19315;
							    p._ExtentY=16034;
							    p._StockProps=0;
							    p.height = 1;
							    var objects=new Object();
							    objects.src=filesurl;
							    p.object=objects;  
							    document.body.appendChild(p);
							    
							    var pdfReader = document.getElementById("createPDF");  
							     pdfReader.setShowToolbar(false);  
							     pdfReader.setShowScrollbars(false);  
							     pdfReader.src=filesurl;  
							     setTimeout(function () { 
							    	 doprintln();
							     }, 5000);	
						}
				}
			}
		})
	}
 
 function doprintln(){
	 var pdfReader = document.getElementById("createPDF");   
	 pdfReader.gotoFirstPage();
     pdfReader.print();
     $(".loading_div").remove();
 }
 
 
 /**
  * 执行签到
  */
 function batchSign(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	 	}else{	 
		 $.messager.confirm('提示信息','是否进行批量签到？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'batchSigndo.action',
				data : {
					    company_id : $("#company_id").val(),
					    batch_id:$("#batch_id").val(),
					    ids:ids
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
} 
 
 /**
  * 执行批量申请
  */
 function doSendApplyChargeItem(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	 	}else{	 
		 $.messager.confirm('提示信息','执行此操作，将对所选体检人员进行批量申请,请确定先进行人员分组，此操作比较慢，是否执行此操作？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'doSendApplyChargeItem.action',
				data : {
					    company_id : $("#company_id").val(),
					    batch_id:$("#batch_id").val(),
					    ids:ids
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
 }
 
 

 function RunExe(strPath) {
		try {
			var objShell = new ActiveXObject('wscript.shell');
			objShell.Run(strPath);
			objShell = null;

		} catch (e) {
			alert(e);
			// alert_all('找不到文件"'+strPath+'"(或它的组件之一)。请确定路径和文件名是否正确.');
		}
	}
 
 