$(document).ready(function () { 
	 	 
	 $('#batchcom_Name').bind('click', function() { 
			select_batchcom_list(this.value);
	    }); 
		
		$('#batchcom_Name').bind('keyup', function() {
			select_batchcom_list(this.value);
	    });
		
		$('#batchcom_Name').bind('blur', function() {
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
	
	getgroupGrid();
	setcolorDatagrid();
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
 		$("#companybatch_id").val(partnerId);
 		$("#company_id").val(batcoms[0]);
 		$("#batchcom_Name").val(name);	
 		$("#com_Name").val('');
 		$("#batch_id").val(batcoms[1]);
 		companyidss=batcoms[0];
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
var hc_batchcom_list,batchcom_Namess;
function select_com_list(){
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
							"javascript:setvalue('"+data[index].id+"','"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvalue('"+data[index].id+"','"+data[index].text+"')",
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
		id = id.split('-')[0];
		$("#company_id").val(id);
		$("#com_Name").val(name);
		$("#com_name_list_div1").css("display","none");		
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

function setcolorDatagrid(){	
	$('#impusershow').datagrid({   
	    rowStyler:function(index,row){ 
	        if (row.flags==1){
	            return 'color:red;';   
	        }   
	    }   
	});
}
 //---------------------------------------显示方案------------------------------------------------------
 /**
  * 
  */
var lastIndex;
 function getgroupGrid(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	     lastIndex=undefined;
		 var model={"company_id":$("#company_id").val(),"batch_id":$("#batch_id").val()};
	     $("#impusershow").datagrid({
		 url:'impusershowlist.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 250,//每页显示的记录条数，默认为10 
	     pageList:[250,500,750,1000,2000],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
            {align:'center',field:'notices',title:'导入错误原因',width:15},
            {align:'center',field:'id',title:'编号',width:10},
            {align:'center',field:'arch_num',title:dahname,width:20,editor:{type:'text'}},		    
		 	{align:'center',field:'id_num',title:'证件号',width:25,editor:{type:'text'}},
		 	{align:'center',field:'employeeID',title:'工号',width:25,editor:{type:'text'}},
		 	{align:'center',field:'tel',title:'电话',width:25,editor:{type:'text'}},
		 	{align:'center',field:'email',title:'电子邮箱',width:25,editor:{type:'text'}},
		 	{align:'center',field:'custname',title:'姓名',width:15,editor:{type:'text'}},
		 	{align:'center',field:'sex',title:'性别',width:15,editor:{type:'text'}},
		 	{align:'center',field:'age',title:'年龄',width:15,editor:{type:'text'}},
		 	{align:'center',field:'birthday',title:'出生日期',width:15,editor:{type:'text'}},		 	
		 	{align:'center',field:'is_marriage',title:'婚否',width:15,editor:{type:'text'}},
		 	{align:'center',field:'exam_type',title:'体检类型',width:15,editor:{type:'text'}},
		 	{align:'center',field:'customer_type',title:'人员类型',width:15,editor:{type:'text'}},
		 	{align:'center',field:'_level',title:'部门',width:15,editor:{type:'text'}},
		 	{align:'center',field:'position',title:'职务',width:15,editor:{type:'text'}},		 	
		 	{align:'center',field:'remark',title:'备注',width:15,editor:{type:'text'}},
		 	{align:'center',field:'others',title:'其他',width:15,editor:{type:'text'}},
		 	{align:'center',field:'visit_no',title:'就诊卡',width:15,editor:{type:'text'}},
		 	{align:'center',field:'introducer',title:'介绍人',width:15,editor:{type:'text'}},
		 	{align:'center',field:'idtypename',title:'证件类型',width:25,editor:{type:'text'}},
		 	{align:'center',field:'sflags',title:'状态',width:15},
		 	{align:'center',field:'search',title:'姓名定位',width:10,"formatter":f_show}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar,
	        onDblClickRow  : function (rowIndex) {
	            if (lastIndex != rowIndex) {
	            	$("#impusershow").datagrid('endEdit', lastIndex);  
	            	$("#impusershow").datagrid('beginEdit', rowIndex); 
	            }  
	            lastIndex = rowIndex;  
        }
	});
	}
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'上传文件',
		width:100,
		iconCls:'icon-add',
	    handler:function(){
	    	if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
	    		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	    	}else{
	    		$("#dlg-show").dialog({
		 		title:'上传文件',
		 		href:'impuserfile.action?batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()
		 	});
		 	$("#dlg-show").dialog('open');
	    	}
	    }
	},{
		text:'保存行修改',
		width:100,
		iconCls:'icon-save',
	    handler:function(){
	    	var row = $('#impusershow').datagrid('getData').rows[lastIndex];
	    	if(row!=null){
	    		$("#impusershow").datagrid('endEdit', lastIndex);  
	    		savrow(row);
	    	}
	    }
	},{
		text:'删除选择项',
		width:120,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var checkedItems = $('#impusershow').datagrid('getChecked');
	    	    var ids = "";
	    	    $("#impusershow").datagrid('endEdit', lastIndex); 
	    	    $.each(checkedItems, function(index, item){
	    	        ids=ids+","+(item.id);
	    	    }); 	    	    
	    	    delrow(ids);
	    }
	},{
		text:'选择项数据入正式库',
		width:180,
		iconCls:'icon-save',
	    handler:function(){
	    	var checkedItems = $('#impusershow').datagrid('getChecked');
	    	var ids = "";
    	    $("#impusershow").datagrid('endEdit', lastIndex); 
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    }); 	    	    
    	    impuser(ids);
	    }
	},{
		text:'整体数据入正式库',
		width:160,
		iconCls:'icon-check',
	    handler:function(){
	    	var checkedItems = $('#impusershow').datagrid('getChecked');
	    	impalluser();
	    }
	},{
		text:'错误数据导出',
		width:140,
		iconCls:'icon-save',
	    handler:function(){
	    	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
	    	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	    	 		return;
	    	 	}else{
	    	 		window.location.href='saveimpData.action?batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
	    	 	}	    	
	    }
	},{
		text:'删除错误记录',
		width:140,
		iconCls:'icon-cancel',
	    handler:function(){
	    	deleteerrorinfo();	    	
	    }
	},{
		text:'<a href=\"../../customerManager/template.xlsx\">下载模板</a>',
		iconCls:'icon-check',
		handler:function(){
	    }
	}];
 
 /**
  * 
  */
 function deleteerrorinfo(){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	 	}else{	 
		 $.messager.confirm('提示信息','是否确定删除当前任务批次下的错误记录？',function(r){
			 	if(r){
			 		// alert($("#companybatch_id").val());
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		 $.ajax({
			  
				url : 'deleteimpuserToExaminfodo.action',
				data : {
					    company_id : $("#company_id").val(),
					    batch_id:$("#batch_id").val()
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getgroupGrid();
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
  * 选择行整体导入数据库
  */
 function impuser(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else{	 
	 $.messager.confirm('提示信息','是否确定将选择行导入人员库？',function(r){
		 	if(r){
		 		// alert($("#companybatch_id").val());
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	 $.ajax({
		  
			url : 'impuserToExaminfodo.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id:$("#batch_id").val(),
		            ids:ids,
		            companybatch_id:$("#companybatch_id").val()
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getgroupGrid();
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
  * 整体导入数据库
  */
 function impalluser(){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else{	 
	 $.messager.confirm('提示信息','是否确定执行整体数据导入人员库？',function(r){
		 	if(r){
		 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 		 $("body").prepend(str);
	 $.ajax({
			url : 'impuserAllToExaminfodo.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id:$("#batch_id").val(),
		            companybatch_id:$("#companybatch_id").val()
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {							
							getgroupGrid();
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
 
 
 document.onkeydown=function(event){
	    var e = event || window.event || arguments.callee.caller.arguments[0]; 
			if(e && e.keyCode==27){ // 按 Esc                 
			//要做的事情           
			}           
			if(e && e.keyCode==113){ // 按 F2                 
			  //要做的事情               
			}                         
			if(e && e.keyCode==13){ // enter 键                
			  //要做的事情
				
				  var row = $('#impusershow').datagrid('getData').rows[lastIndex];
				  if(row!='undefined'){
		    	  if(row!=null){
		    		  $("#impusershow").datagrid('endEdit', lastIndex);  
		    		  savrow(row);
		    	  }
			   }
			}        
	};
 
 /**
  * 编辑行，并保存
  * @param row
  */
 function savrow(row){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $.ajax({
			url : 'saveCustomerTmplist.action',
			data : {
						id : row.id,
						arch_num:row.arch_num,
						id_num:row.id_num,
						sex:row.sex,
						birthday:row.birthday,
						custname:row.custname,
						age:row.age,
						is_marriage:row.is_marriage,
						position:row.position,
						_level:row._level,
						tel:row.tel,
						exam_type:row.exam_type,
						remark:row.remark,
						customer_type:row.customer_type,
						others:row.others,
						employeeID:row.employeeID,
						notices:row.noticesrow,
						visit_no:row.visit_no,
						introducer:row.introducer,
						idtypename:row.idtypename
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getgroupGrid();
							//$.messager.alert("操作提示", text);							
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
 
 
 /**
  * 编辑行，并保存
  * @param row
  */
 function delrow(ids){
	 $.messager.confirm('提示信息','是否确定删除选中行数据？',function(r){
		 	if(r){
		 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	 $.ajax({
			url : 'delCustomerTmplist.action',
			data : {
						ids : ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getgroupGrid();
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
  
 
 /**
  * 姓名定位
  * @param val
  * @param row
  * @returns {String}
  */
  function f_show(val,row){	
	  return '<a href=\"javascript:f_dingweishow(\''+row.id+'\',\''+row.custname+'\')\">定位</a>';
  }

 var scustname,sid;
 function f_dingweishow(sid,scustname){ 
	 scustname = encodeURI(encodeURI(scustname));
	 if(lastIndex!=undefined){
	     $("#impusershow").datagrid('endEdit', lastIndex); 
	 }
	 var strurl='impuserusernamedw.action?id='+sid+'&custname='+scustname+'&company_id='+$("#company_id").val()+'&batch_id='+$("#batch_id").val();
	 $("#dlg-show").dialog({
	 		title:'姓名定位',
	 		href:strurl
	 	});
	 	$("#dlg-show").dialog('open');
 }
