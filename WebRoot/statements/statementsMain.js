$(document).ready(function () {
	$('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    }); 
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    });	
	$('#results_contrast').mouseleave(function(){
		 $('#results_contrast').css('display', 'none');
	});
	getcontractGrid();
});

 //-------------------------------------------单位信息显示-----------------------------------------------------
/**
 * 模糊查询公司信息
 */
 var hc_com_list,com_Namess;
 function select_com_list(com_Namess){
 	var url='companychangshow.action';
 	var data={"name":com_Namess};
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
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
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
			$("#com_name_list_div").html(mydtree.toString());
			$("#com_name_list_div").css("display","block");
			
		}
 
 /**
  * 点击树设置内容
  * @param id
  * @param name
  * @returns
  */
	function setvalue(id,name){
		$("#company_id").val(id);
		$("#com_Name").textbox('setValue',name);
		$("#com_name_list_div").css("display","none");
		f_getDatadic();	
	}
 
//单位失去焦点
 var hc_mous_select1=false;
 function select_com_list_over(){
 	if(!hc_mous_select1){
 	$("#com_name_list_div").css("display","none");
 	}
 	}

 function select_com_list_mover(){
 	hc_mous_select1=true;
 	}
 function select_com_list_amover(){
 	hc_mous_select1=false;
 	}


	//获取菜单
	function f_getDatadic() {
		$('#batch_id').combobox({
			url : 'getCompanForBatch.action?company_id='+$("#company_id").val(),
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				$('#batch_id').combobox('setValue', data[0].id);				
			}
		});
	}
 
	//---------------------------------------显示方案------------------------------------------------------
	 /**
	  * 
	  */
	 function getcontractGrid(){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		     lastIndex=undefined;
			 var model={"company_id":$("#company_id").val(),"batch_id":document.getElementsByName("batch_id")[0].value};
		     $("#contractshow").datagrid({
			 url:'contractlistshow.action',
			 dataType: 'json',
			 queryParams:model,
			 toolbar:'#toolbar',
			 rownumbers:false,
		     pageSize: 10,//每页显示的记录条数，默认为10 
		     pageList:[10,20,45,60,75],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'center',field:'contract_num',title:'合同编号',width:20},
			 	{align:'center',field:'company_name',title:'所属单位',width:25},
			 	{align:'center',field:'batch_name',title:'体检任务',width:25},
			 	{align:'center',field:'linkman',title:'联系人',width:15},
			 	{align:'center',field:'tel',title:'联系电话',width:15},			 	
			 	{align:'center',field:'stypes',title:'审核状态',width:15},
			 	{align:'center',field:'overflags',title:'封帐状态',width:15},
			 	// {align:'center',field:'accountflag',title:'封帐状态',width:15,"formatter":f_account_flag},
			 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_show},
			 	{align:"center",field:"xgddd",title:"新增结算",width:10,"formatter":f_addjsd}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		$(".loading_div").remove();
					if($("#isovertype").val()=='0'){
						$("div.datagrid-toolbar [id ='1']").eq(0).hide();
					}
					if($("#isunovertype").val()=='0'){
						$("div.datagrid-toolbar [id ='2']").eq(0).hide();
					}
		    		getTeamAccountForBatch();
		    	},
		    	singleSelect:true,
			    collapsible:true,
				pagination: true,
			    fitColumns:true,
			    striped:true,
			    toolbar:toolbar
		});
		}
	 function f_account_flag(val,row){
		 if(val==1){return "已锁定";}else{return "未锁定";}
	 }

	 /**
	 * 增加结算单
	 * @param val
	 * @param row
	 * @returns {String}
	 */
	 function f_addjsd(val,row){	
	 	return '<a href=\"javascript:f_addtermaccount(\''+row.batch_id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"新增结算\" /></a>';
	 }

	 var rowbatch_id;
	 function f_addtermaccount(rowbatch_id){
		 $.ajax({
				url:'termaccountadd.action?company_id='+$("#company_id").val()+'&batchid='+rowbatch_id,
				type:'post',
				success:function(text){
					if(text.split("-")[0]=='ok'){
		        		  $.messager.alert("操作提示", text);
		        		  getTeamAccountForBatch();
		        	  }else if(text.split("-")[0]=='error'){
		        		  $.messager.alert("操作提示", text,"error");
		        	  }
				},
				error:function(){
					$.messager.alert('提示信息','操作失败！','error');
				}
				});
	 }
	 
 /**
  * 定义工具栏
  */
 var toolbar=[{
 		id:1,
		text:'封帐',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
	    	if(($("#company_id").val()=='')||(document.getElementsByName("batch_id")[0].value=='')){
	    		$.messager.alert("操作提示", "请选择单位和批次任务","error");
	    	}else{
	    		$.messager.confirm('提示信息','是否确定进行封帐操作？',function(r){
	    			if(r){
	    				$.ajax({
	    				url:'overTeamAccountForbatch.action?company_id='+$("#company_id").val()+'&batchid='+document.getElementsByName("batch_id")[0].value,
	    				type:'post',
	    				success:function(text){
	    					if(text.split("-")[0]=='ok'){
	    		        		  $.messager.alert("操作提示", text.split("-")[1]);
	    		        		  getcontractGrid();
	    		        	  }else if(text.split("-")[0]=='error'){
	    		        		  $.messager.alert("操作提示", text.split("-")[1],"error");
	    		        	  }
	    				},
	    				error:function(){
	    					$.messager.alert('提示信息','操作失败！','error');
	    				}
	    				});
	    			}
	    		});
	      }
	    }
	},{
 		id:2,
 		text:'解除封帐',
		width:100,
		iconCls:'icon-check',
	    handler:function(){
	    	if(($("#company_id").val()=='')||(document.getElementsByName("batch_id")[0].value=='')){
	    		$.messager.alert("操作提示", "请选择单位和批次任务","error");
	    	}else{
	    		$.messager.confirm('提示信息','是否确定进行解除封帐操作？',function(r){
	    			if(r){
	    				$.ajax({
	    					url:'unoverTeamAccountForbatch.action?company_id='+$("#company_id").val()+'&batchid='+document.getElementsByName("batch_id")[0].value,
		    				type:'post',
	    				success:function(text){
	    					if(text.split("-")[0]=='ok'){
	    		        		  $.messager.alert("操作提示", text.split("-")[1]);
	    		        		  getcontractGrid();
	    		        	  }else if(text.split("-")[0]=='error'){
	    		        		  $.messager.alert("操作提示", text.split("-")[1],"error");
	    		        	  }
	    				},
	    				error:function(){
	    					$.messager.alert('提示信息','操作失败！','error');
	    				}
	    				});
	    			}
	    		});
	      }
	    }
	},{
		text:'未检信息查询',
		width:140,
		iconCls:'icon-check',
	    handler:function(){
	    	if(($("#company_id").val()=='')||(document.getElementsByName("batch_id")[0].value=='')){
	    		$.messager.alert("操作提示", "请选择单位和批次任务","error");
	    	}else{
	    	 var url='teamnoexamshow.action?company_id='+$("#company_id").val()+'&batchid='+document.getElementsByName("batch_id")[0].value;
	    	newWindow = window.open(url, "未检信息列表", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    	newWindow.focus();
	    	}
	    }
	}];
 
 var lastIndex;
 function getTeamAccountForBatch(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	     lastIndex=undefined;
		 var model={"company_id":$("#company_id").val(),"batchid":document.getElementsByName("batch_id")[0].value};
	     $("#statementsshow").datagrid({
		 url:'getTeamAccountForBatch.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 10,//每页显示的记录条数，默认为10 
	     pageList:[10,20,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'checkss',checkbox:true },
            {align:'center',field:'delnum',title:'删除',width:10,"formatter":f_del},
		    {align:'center',field:'accsnum',title:'结算单号',width:15,"formatter":f_teamaccountone},
		    {align:'center',field:'acc_name',title:'结算说明',width:35,editor:{type:'text'}},
		 	{align:'center',field:'contract_num',title:'合同号',width:20},
		 	{align:'center',field:'acc_stautsss',title:'结算状态',width:10,"formatter":f_jszt},
		 	{align:'center',field:'linker',title:'联系人',width:10},
		 	{align:'center',field:'phone',title:'联系电话',width:15},	
		 	{align:'right',field:'prices',title:'应收金额 ',width:10},
		 	{align:'center',field:'totalexam',title:'人次',width:10},
		 	{align:'right',field:'trueamt',title:'实收金额 ',width:10},
		 	{align:'right',field:'dec_charges',title:'优惠金额',width:10},
		 	{align:'center',field:'additional',title:'附加费用',width:10,"formatter":f_fjfy},
		 	{align:'center',field:'balance_statusss',title:'结账状态',width:10,"formatter":f_jzzt},
		 	{align:'center',field:'invoice_no',title:'发票号',width:15},
		 	{align:'center',field:'acc_date',title:'结算日期',width:20},
		 	{align:'center',field:'balance_date',title:'结账日期',width:20},
		 	{align:"center",field:"xg",title:"结算/附加费用维护",width:15,"formatter":f_xg}
		 	]],
		 	onBeforeLoad:function(data){
		 		if($("#is_show_team_settlement_toolbar").val() =='N'){
		 			$("div.datagrid-toolbar [id ='jsd']").eq(0).hide();
					$("div.datagrid-toolbar [id ='atchz']").eq(0).hide();
					$("div.datagrid-toolbar [id ='afzhz']").eq(0).hide();
					$("div.datagrid-toolbar [id ='axmhz']").eq(0).hide();
					$("div.datagrid-toolbar [id ='atcmx']").eq(0).hide();
					$("div.datagrid-toolbar [id ='axfzmx']").eq(0).hide();
					$("div.datagrid-toolbar [id ='axmmx']").eq(0).hide();
		 		}
 				
		 	},
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	onDblClickRow  : function (rowIndex) {
		            	$("#statementsshow").datagrid('beginEdit', rowIndex); 
		            	calcnamekeydown(rowIndex);
	        },
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns : true,
			autowidth : true,
			striped : true,
		    toolbar:toolbarterm
	});
	}
 
 var rowIndex,desindex;
 function calcnamekeydown(rowIndex){
 	var objGrid = $("#statementsshow");        // 表格对象
 	var nameEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'acc_name'}); 
  // 折扣率  绑定离开事件 
     $(nameEdt.target).bind("blur",function(){
         calcname(rowIndex);        // 根据 折扣率变更后 计算 折扣额
     });
     
     $(nameEdt.target).keydown(function (e) {
         if (e.keyCode == 13) {
         	calcname(rowIndex);
         }
     });
 }
 
 function calcname(desindex){
     var objGrid = $("#statementsshow");   
     var rows = objGrid.datagrid('getRows');
     var discountEdt = objGrid.datagrid('getEditor', {index:desindex,field:'acc_name'}); 
     var discountValue = $(discountEdt.target).val();  
     var teamid=rows[desindex].id;
     var accnum=rows[desindex].acc_num;
     var endiscountValue = encodeURI(encodeURI(discountValue));
     $.ajax({
 		url:'updateTeamAccountForIddo.action?company_id='+$("#company_id").val()+'&batchid='+document.getElementsByName("batch_id")[0].value+'&id='+teamid+'&acc_name='+endiscountValue+'&acc_num='+accnum,
 		type:'post',
 		success:function(text){
 			if(text.split("-")[0]=='ok'){
         		 rows[desindex].acc_name=discountValue;    // 折扣额  赋值
         	  }else if(text.split("-")[0]=='error'){
         		  $.messager.alert("操作提示", text,"error");
         	  }
 			 $('#statementsshow').datagrid('refreshRow', desindex);
 		     $("#statementsshow").datagrid('endEdit', desindex); 
 		},
 		error:function(){
 			$.messager.alert('提示信息','操作失败！','error');
 	 	    $("#statementsshow").datagrid('endEdit', desindex); 
 		} 		 
 		});       
 }

/**
* 定义工具栏
*/
var toolbarterm=[{
text:'结算审核/取消',
	width:120,
	iconCls:'icon-check',
    handler:function(){
    	var ids = "";
    	var checkedItems = $('#statementsshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids=ids+","+(item.acc_num);
	    });
	    if(ids.split(',').length<=1){
    		$.messager.alert("操作提示", "请选择要修改的结算单","error");
    	}else if(ids.split(',').length>2){
    		$.messager.alert("操作提示", "只能同时修改一个结算单","error");
    	}else{
    		if(ids.length>1) ids=ids.substring(1,ids.length);
    		f_checktermaccount(ids);
    	}
    }
},{
    id:'jsd',
	text:'结算单',
	width:90,
	iconCls:'icon-check',
    handler:function(){
    	var ids = "";
    	var checkedItems = $('#statementsshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids=ids+","+(item.acc_num);
	    });
	    if(ids.split(',').length<=1){
    		$.messager.alert("操作提示", "请选择要修改的结算单","error");
    	}else if(ids.split(',').length>2){
    		$.messager.alert("操作提示", "只能同时修改一个结算单","error");
    	}else{
    		if(ids.length>1) ids=ids.substring(1,ids.length);
    		printreport(ids,1);
    	}    
    }
},{
	id:'atchz',
	text:'按套餐汇总',
	width:140,
	iconCls:'icon-check',
    handler:function(){
    	var ids = "";
    	var checkedItems = $('#statementsshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids=ids+","+(item.acc_num);
	    });
	    if(ids.split(',').length<=1){
    		$.messager.alert("操作提示", "请选择要修改的结算单","error");
    	}else if(ids.split(',').length>2){
    		$.messager.alert("操作提示", "只能同时修改一个结算单","error");
    	}else{
    		if(ids.length>1) ids=ids.substring(1,ids.length);
    		printreport(ids,2);
    	}    
    }
},{
	id:'afzhz',
	text:'按分组汇总',
	width:140,
	iconCls:'icon-check',
    handler:function(){
    	var ids = "";
    	var checkedItems = $('#statementsshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids=ids+","+(item.acc_num);
	    });
	    if(ids.split(',').length<=1){
    		$.messager.alert("操作提示", "请选择要修改的结算单","error");
    	}else if(ids.split(',').length>2){
    		$.messager.alert("操作提示", "只能同时修改一个结算单","error");
    	}else{
    		if(ids.length>1) ids=ids.substring(1,ids.length);
    		printreport(ids,3);
    	}    
    }
},{
	id:'axmhz',
	text:'按项目汇总',
	width:140,
	iconCls:'icon-check',
    handler:function(){
    	var ids = "";
    	var checkedItems = $('#statementsshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids=ids+","+(item.acc_num);
	    });
	    if(ids.split(',').length<=1){
    		$.messager.alert("操作提示", "请选择要修改的结算单","error");
    	}else if(ids.split(',').length>2){
    		$.messager.alert("操作提示", "只能同时修改一个结算单","error");
    	}else{
    		if(ids.length>1) ids=ids.substring(1,ids.length);
    		printreport(ids,4);
    	}    
    }
},{
	id:'atcmx',
	text:'按套餐明细',
	width:140,
	iconCls:'icon-check',
    handler:function(){
    	var ids = "";
    	var checkedItems = $('#statementsshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids=ids+","+(item.acc_num);
	    });
	    if(ids.split(',').length<=1){
    		$.messager.alert("操作提示", "请选择要修改的结算单","error");
    	}else if(ids.split(',').length>2){
    		$.messager.alert("操作提示", "只能同时修改一个结算单","error");
    	}else{
    		if(ids.length>1) ids=ids.substring(1,ids.length);
    		printreport(ids,5);
    	}    
    }
},{
	id:'axfzmx',
	text:'按分组明细',
	width:140,
	iconCls:'icon-check',
    handler:function(){
    	var ids = "";
    	var checkedItems = $('#statementsshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids=ids+","+(item.acc_num);
	    });
	    if(ids.split(',').length<=1){
    		$.messager.alert("操作提示", "请选择要修改的结算单","error");
    	}else if(ids.split(',').length>2){
    		$.messager.alert("操作提示", "只能同时修改一个结算单","error");
    	}else{
    		if(ids.length>1) ids=ids.substring(1,ids.length);
    		printreport(ids,6);
    	}    
    }
},{
	id:'axmmx',
	text:'按项目明细',
	width:140,
	iconCls:'icon-check',
    handler:function(){
    	var ids = "";
    	var checkedItems = $('#statementsshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids=ids+","+(item.acc_num);
	    });
	    if(ids.split(',').length<=1){
    		$.messager.alert("操作提示", "请选择要修改的结算单","error");
    	}else if(ids.split(',').length>2){
    		$.messager.alert("操作提示", "只能同时修改一个结算单","error");
    	}else{
    		if(ids.length>1) ids=ids.substring(1,ids.length);
    		printreport(ids,7);
    	}    
    }
}];



//结算状态
function f_jszt(val,row){
	  if(row.acc_stauts=='N'){
		  return '<font color="red">'+row.acc_stautss+'</font>';
	  }else{
		  return row.acc_stautss;
	  }
}

//结账状态
function f_jzzt(val,row){
	  if(row.balance_status=='N'){
		  return '<font color="red">'+row.balance_statuss+'</font>';
	  }else{
		  return row.balance_statuss;
	  }
}
//附加费用
function f_fjfy(val,row){
	return '<a href="javascript:void(0)" onclick="f_fjfyshow(\''+row.acc_num+'\',this)">'+val+'</a>';
}

function f_fjfyshow(acc_num,ths){
	var height = window.screen.height-210 - $(ths).offset().top;
	if(height > 238){
		$("#results_contrast").css("top",$(ths).offset().top);
	}else{
		$("#results_contrast").css("top",$(ths).offset().top-(238-height));
	}
	var left = getElementLeft(ths);
	$("#results_contrast").css("left",left);
	$('#results_contrast').css('display','block');
	$("#fjfy_list").datagrid({
		url:'getteamaccountaddlist.action',
		dataType: 'json',
		queryParams:{'acc_num':acc_num},
		rownumbers:false,
		width:300,
		height:150,
		columns:[[
			 {align:'center',field:'item_name',title:'费用名称',width:60},
			 {align:'center',field:'amount',title:'金额',width:40}
		]],
		singleSelect:true,
		collapsible:true,
		pagination: false,
		striped:true,
		nowrap:false,
		fitColumns:true
	});
}

function f_del(val,row){	
	return '<a href=\"javascript:f_deltermaccount(\''+row.acc_num+'\')\">删除</a>';
}

function f_teamaccountone(val,row){
	return '<a href=\"javascript:f_teamaccountoneshow(\''+row.acc_num+'\')\">'+row.acc_num+'</a>';
}

var rowacc_num;
function f_teamaccountoneshow(rowacc_num){
	 var url='termaccountoneshow.action?company_id='+$("#company_id").val()+'&batchid='+document.getElementsByName("batch_id")[0].value+'&acc_num='+rowacc_num;
	newWindow = window.open(url, "团体结算显示细项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newWindow.focus();
}


function f_deltermaccount(rowacc_num)
{
$.messager.confirm('提示信息','是否确定删除此结算单？',function(r){
	if(r){
		$.ajax({
		url:'delTeamAccountForAccNumdo.action?acc_num='+rowacc_num+'&batchid='+document.getElementsByName("batch_id")[0].value,
		type:'post',
		success:function(text){
			if(text.split("-")[0]=='ok'){
        		  $.messager.alert("操作提示", text);
        		  getTeamAccountForBatch();
        	  }else if(text.split("-")[0]=='error'){
        		  $.messager.alert("操作提示", text,"error");
        	  }
		},
		error:function(){
			$.messager.alert('提示信息','操作失败！','error');
		}
		});
	}
});
}

//结算审核与取消
function f_checktermaccount(rowacc_num)
{
$.messager.confirm('提示信息','是否确定修改此结算单审核状态？',function(r){
	if(r){
		$.ajax({
		url:'checkTeamAccountForAccNumdo.action?acc_num='+rowacc_num+'&batchid='+document.getElementsByName("batch_id")[0].value,
		type:'post',
		success:function(text){
			if(text.split("-")[0]=='ok'){
        		  $.messager.alert("操作提示", text);
        		  getTeamAccountForBatch();
        	  }else if(text.split("-")[0]=='error'){
        		  $.messager.alert("操作提示", text,"error");
        	  }
		},
		error:function(){
			$.messager.alert('提示信息','操作失败！','error');
		}
		});
	}
});
}

/**
* 修改
* @param val
* @param row
* @returns {String}
*/
function f_xg(val,row){	
	return '<a href=\"javascript:f_termaccount('+row.batchid+','+row.id+')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"结算维护\" /></a> /  '+
	'<a href=\"javascript:f_fjfyedit(\''+row.acc_num+'\',\''+row.acc_stauts+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-add\" alt=\"附加费用维护\" /></a>';
}

function f_fjfyedit(acc_num,acc_stauts){
	if(acc_stauts == 'Y'){
		$.messager.alert('提示信息',"该结算单已结算审核，不能维护附加费用","error");
		return;
	}else{
		$("#dlg-fjfy").dialog({
			title:'维护附加费用',
			href:'getteamaccountaddpage.action?acc_num='+acc_num,
		});
		$('#dlg-fjfy').dialog('open');
	}
}

var rowbatch_id,rowacc_id;
function f_termaccount(rowbatch_id,rowacc_id){
	 var url='termaccountListshow.action?company_id='+$("#company_id").val()+'&batchid='+rowbatch_id+'&id='+rowacc_id;
 	newWindow = window.open(url, "团体结算显示细项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
 	newWindow.focus();
}
 
 /**
  * 显示一条方案
  * @param val
  * @param row
  * @returns {String}
  */
  function f_show(val,row){	
	  return '<a href=\"javascript:f_contractshow(\''+row.contract_num+'\')\">查看</a>';
  }
  
  var checknewWindow;  
  var checktimer;   
 function f_contractshow(userid){
 	 	var url='/contractonecheckshow.action?contract_num='+userid;
    	if(userid!=''){
    		newWindow = window.open(url, "查看单独合同", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes")
    		newWindow.focus();
    	}else{
    	  $.messager.alert('提示信息',"请先选择合同","error");
    	}
 	 } 
 var totaltypeid;
 function printreport(rowacc_num,totaltypeid){		
		var exeurl="teamaccountServices://&"+rowacc_num+"&1&"+totaltypeid;
		 RunReportExe(exeurl);
	 }
 
 function RunReportExe(strPath) {
	 location.href=strPath;
 }
 
 
function getElementLeft(element) {
	var actualLeft = element.offsetLeft;
	var current = element.offsetParent;
	while (current !== null) {
		actualLeft += current.offsetLeft;
		current = current.offsetParent;
	}
	return actualLeft;
}