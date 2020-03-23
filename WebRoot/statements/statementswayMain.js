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
		 gettjxmGrid('');
		 gettermaccountlistGrjlid('');
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		     lastIndex=undefined;
			 var model={"company_id":$("#company_id").val(),"batch_id":document.getElementsByName("batch_id")[0].value};
		     $("#contractshow").datagrid({
			 url:'contractlistshow.action',
			 dataType: 'json',
			 queryParams:model,
//			 toolbar:'#toolbar',
			 rownumbers:false,
		     pageSize: 10,//每页显示的记录条数，默认为10 
		     pageList:[10,20,45,60,75],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'center',field:'contract_num',title:'合同编号',width:20},
			 	{align:'center',field:'company_name',title:'所属单位',width:25},
			 	{align:'center',field:'batch_name',title:'体检任务',width:25},
			 	{align:'center',field:'linkman',title:'联系人',width:15},
			 	{align:'center',field:'tel',title:'联系电话',width:15},			 	
			 	{align:'center',field:'stypes',title:'状态',width:15},
			 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_show},
				{align:"center",field:"xgddd",title:"新增结帐",width:10,"formatter":f_addjsd}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		$(".loading_div").remove();
		    		getTeamAccountinvoidForBatch();
		    	},
		    	singleSelect:true,
			    collapsible:true,
				pagination: true,
			    fitColumns:true,
			    striped:true
//			    toolbar:toolbar
		});
		}
 


 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'未检人员查询',
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
	 $("#dlg-custedit").dialog({
	 		title:'选择结算单',
	 		href:'statementjsdlist.action?batchid='+document.getElementsByName("batch_id")[0].value
	 	});
	 	$("#dlg-custedit").dialog('open');	
 }
 
 //通过批次任务找发票信息
 function getTeamAccountinvoidForBatch(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	     lastIndex=undefined;
		 var model={"company_id":$("#company_id").val(),"batchid":document.getElementsByName("batch_id")[0].value};
	     $("#statementsinvoidshow").datagrid({
		 url:'getTeamAccountinvoidForBatch.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
		 toolbar:"#toolbar",
	     pageSize: 10,//每页显示的记录条数，默认为10 
	     pageList:[10,20,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
            {align:"center",field:"recorddel",title:"删除",width:10,"formatter":f_recorddel},
		    {align:'center',field:'account_num',title:'结帐单号',width:10},
		 	{align:'center',field:'invoice_num',title:'发票号',width:10},
		 	{align:'center',field:'is_print_recepits',title:'是否已打印发票',width:10},		 	
		 	{align:'center',field:'contract_num',title:'合同号',width:20},
		 	{align:'right',field:'amount1',title:'结帐原金额',width:10},	
		 	{align:'right',field:'amount2',title:'实际结帐金额',width:10},
		 	{align:'right',field:'additional',title:'附加费',width:10},
		 	{align:'center',field:'update_time',title:'结账日期',width:10},
		 	{align:'center',field:'receiv_statuss',title:'付款状态',width:10},
		 	{align:"center",field:"ckdel",title:"退费发票",width:10,"formatter":f_printlndel},
		 	{align:"center",field:"ck",title:"打印发票",width:10,"formatter":f_println},
		 	{align:"center",field:"xg",title:"支付方式",width:10,"formatter":f_xgway},
		 	{align:"center",field:"qrxg",title:"确认付款",width:10,"formatter":f_xgqrfk}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
		    toolbar:toolbar,
		    onDblClickRow : function(index, row) {
				gettjxmGrid(row.account_num);
				gettermaccountlistGrjlid(row.account_num);
			}
	});
	}
 var toolbar = [{
		text:'商户划账',
		width:120,
		iconCls:'icon-edit',
	    handler:function(){
	    	var row = $("#statementsinvoidshow").datagrid('getSelections');
	    	if(row.length == 0){
	    		$.messager.alert('提示信息','请选择所要划账的结账单。。','warning');
	    		return;
	    	}
	    	if(row.length > 1){
	    		$.messager.alert('提示信息','每次只能划账一个结账单,请重新选择。。','warning');
	    		return;
	    	}
	    	gettermaccountlistGrjlid(row[0].account_num);
	    	
	    	$.ajax({
	 			url:'getCompanyAccountAmountByComId.action',
	 			type:'post',
	 			data: {
	 				company_id : $("#company_id").val()
	 			},
	 			success:function(data){
	 				var da  = eval('('+data+')');
	 				if(da.com_type == '1'){
                        $.messager.alert('提示信息','此商户以被冻结，操作不能继续。','info');
                        return;
					}else if(da.com_type == '2'){
                        $.messager.alert('提示信息','此商户以已销户，操作不能继续。','info');
                        return;
					}
	 				if(da.balance > 0){
	 					$("#dlg-huazhang").dialog('open');
		 				$("#sh_balance").val(da.balance);
		 				$("#ykfp_amount").val(da.amount);
		 				$("#jz_amount").val(Number(row[0].amount2+row[0].additional));
		 				$("#hz_amount").numberbox('setValue','');
	 				}else{
	 					$.messager.alert('提示信息','此商户还未充值，请先充值才能划账。。','info');
	 					return;
	 				}
	 				
	 			},
	 			error:function(){
	 				$.messager.alert('提示信息','操作失败！','error');
	 			}
   			});
	    }
}];
 
//确认付款
 function f_xgqrfk(val,row){	
	  return '<a href=\"javascript:recordqrfk('+row.batch_id+',\''+row.id+'\')\">点击确认</a>';
}
 
 var id;
 function recordqrfk(rowbatch_id,id){
  	$.messager.confirm('提示信息','系统将确认此笔付款，确认后将不能再修改，是否确认付款操作？',function(r){
 		if(r){
 			$.ajax({
 			url:'qrfkaccountGroupTT.action?batchid='+rowbatch_id+'&id='+id,
 			type:'post',
 			success:function(text){
 				if(text.split("-")[0]=='ok'){
 	        		  $.messager.alert("操作提示", text.split("-")[1]);
 	        		 getTeamAccountinvoidForBatch();
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
 
 //删除结帐记录
 function f_recorddel(val,row){	
	  return '<a href=\"javascript:recorddelshow(\''+row.account_num+'\')\">删除结帐</a>';
}
 
 var void_nums="";
 var boid_account_num;
 function recorddelshow(boid_account_num){
  	$.messager.confirm('提示信息','系统将此笔结帐删除，是否确定进行操作？',function(r){
 		if(r){
 			$.ajax({
 			url:'delaccountGroupTT.action?batchid='+document.getElementsByName("batch_id")[0].value+'&account_num='+boid_account_num,
 			type:'post',
 			success:function(text){
 				if(text.split("-")[0]=='ok'){
 	        		  $.messager.alert("操作提示", text.split("-")[1]);
 	        		 getTeamAccountinvoidForBatch();
 	        		gettjxmGrid("");
 					gettermaccountlistGrjlid("");
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
 

 function f_xgway(val,row){	
 	return '<a href=\"javascript:f_termaccountxg('+row.id+','+row.batch_id+',\''+row.account_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
 }


 var rowacc_num;
 function f_termaccountxg(rowacc_id,rowbatch_id,rowacc_num){
 	 $("#dlg-custadd").dialog({
 	 		title:'结算方式维护',
 	 		href:'teamaccountwayadd.action?batchid='+rowbatch_id+'&account_num='+rowacc_num+'&id='+rowacc_id
 	 	});
 	 	$("#dlg-custadd").dialog('open');	
 }
 
 //发票作废
 function f_printlndel(val,row){	
	  return '<a href=\"javascript:f_printlndelshow(\''+row.account_num+'\',\''+row.invoice_id+'\',\''+row.invoice_num+'\')\">退费发票</a>';
}


var void_nums="";
function f_printlndelshow(account_num,invoice_id,void_nums){
	if(void_nums.length<=0){
		$.messager.alert('提示信息','发票号无效，操作失败！','error');
	}else{
	$.messager.confirm('提示信息','系统将发票【'+void_nums+'】作废，是否确定进行操作？',function(r){
		if(r){
			$.ajax({
			url:'delSingleInviockTT.action?batchid='+document.getElementsByName("batch_id")[0].value+'&invoice_id='+invoice_id+'&account_num='+account_num,
			type:'post',
			success:function(text){
				if(text.split("-")[0]=='ok'){
	        		  $.messager.alert("操作提示", text.split("-")[1]);
	        		  getTeamAccountinvoidForBatch();
	        		  gettjxmGrid(account_num);
	  				gettermaccountlistGrjlid(account_num);
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


function f_println(val,row){	
	  return '<a href=\"javascript:f_printlnshow(\''+row.account_num+'\')\">打印发票</a>';
}

function f_printlnshow(account_num){
	if(checkchargingway(account_num)=="error"){
		$.messager.alert('提示信息','使用商户划账或会员卡方式收费无法开发票！','error');
		return;
	}
	$("#dlg-custedit").dialog({
		title:'打印发票',
		href:'statemengprintfa.action?batchid='+document.getElementsByName("batch_id")[0].value+'&account_num='+account_num
	});
	$("#dlg-custedit").dialog('open');
	 } 

function checkchargingway(account_num){
	var resChargingway = "error";
	if($("#invoiceRepeatType").val()=="Y"){
		$.ajax({
			url:'checkTteamAccountExamway.action?acc_num='+account_num,
			type:'post',
			success:function(text){
				resChargingway = text;
			},
			error:function(){
				resChargingway = "error";
			}
			});
	}
	return resChargingway;
}
 
  var selectaccount_num;
 function gettjxmGrid(selectaccount_num){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
		 var model={"account_num":selectaccount_num};
	     $("#statementsshow").datagrid({
		 url:'getTeamAccountForBatchfplist.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:true,
	     pageSize: 10,//每页显示的记录条数，默认为10 
	     pageList:[10,20,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'acc_num',title:'结算单号',width:15,"formatter":f_teamaccountone},
		 	{align:'center',field:'contract_num',title:'合同号',width:20},
		 	{align:'center',field:'acc_stautsss',title:'结算状态',width:10,"formatter":f_jszt},
		 	{align:'center',field:'totalexam',title:'人次',width:10},
		 	{align:'right',field:'prices',title:'应收金额 ',width:10},
		 	{align:'right',field:'trueamt',title:'实收金额 ',width:10},
		 	{align:'right',field:'dec_charges',title:'优惠金额',width:10},
		 	{align:'right',field:'additional',title:'附加费用',width:10,"formatter":f_fjfy},
		 	{align:'center',field:'balance_statusss',title:'结账状态',width:10,"formatter":f_jzzt},
		 	{align:'center',field:'acc_date',title:'结算日期',width:15},
		 	{align:'center',field:'balance_date',title:'结账日期',width:15},	
		 	{align:"center",field:"ck",title:"查看",width:10,"formatter":f_ck},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
	}

/**
* 定义工具栏
*/
var toolbarterm=[{
	text:'结账/取消',
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
    		f_updateteamaccountjsdo(ids);
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


//结账
var updateaccnum;
function f_updateteamaccountjsdo(updateaccnum)
{
	$.messager.confirm('提示信息','是否确定修改此结算单结账状态？',function(r){
		if(r){
			$.ajax({
			url:'overTeamAccountForAccNumdo.action?acc_num='+updateaccnum+'&batchid='+document.getElementsByName("batch_id")[0].value,
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
* 查看结算单
* @param val
* @param row
* @returns {String}
*/
function f_ck(val,row){	
	return '<a href=\"javascript:f_termaccount('+row.batchid+','+row.id+')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-search\" alt=\"修改\" /></a>';
}


var rowbatch_id,rowacc_id;
function f_termaccount(rowbatch_id,rowacc_id){
	 var url='termaccountListshowshow.action?company_id='+$("#company_id").val()+'&batchid='+rowbatch_id+'&id='+rowacc_id;
 	newWindow = window.open(url, "团体结算显示细项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
 	newWindow.focus();
}

function f_teamaccountone(val,row){
	return '<a href=\"javascript:f_teamaccountoneshow('+row.acc_num+')\">'+row.acc_num+'</a>';
}

function f_teamaccountoneshow(rowacc_num){
	 var url='termaccountoneshow.action?company_id='+$("#company_id").val()+'&batchid='+document.getElementsByName("batch_id")[0].value+'&acc_num='+rowacc_num;
	newWindow = window.open(url, "团体结算显示细项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newWindow.focus();
}
var checknewWindow;  
var checktimer;   

 
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
    		newWindow = window.open(url, "查看单独合同", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
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
 
 //-------------------------------------------------------
 function gettermaccountlistGrjlid(selectaccount_num) {

		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
				+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);

		var model = {
			"acc_num" : selectaccount_num
		};
		$("#statementswayshow").datagrid({
			url : 'teamAccountExamwayListShow.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : true,
			showFooter:true,
			pageSize : 100000,// 每页显示的记录条数，默认为10
			pageList : [ 10, 20, 45, 60, 75 ],// 可以设置每页记录条数的列表
			columns : [ [ 
			              {align : 'center',field : 'account_num',title : '结算单号',	width : 20}, 
			              {align : 'center',field : 'chargingwayname',title : '支付方式',width : 25},
			              {align : 'center',field : 'amount',title : '支付金额',width : 15}, 
			              {align : 'center',field : 'update_time',title : '更新日期',width : 25}, 
			              {align : 'center',field : 'ck',title : '删除',	width : 10,"formatter" : f_del} 
			              ] ],
			onLoadSuccess : function(value) {
				//$("#datatotal").val(value.total);				
				$(".loading_div").remove();
			},
			singleSelect : false,
			collapsible : true,
			pagination : false,
			fitColumns : true,
			striped : true
		});
	}
	 
	 /**
	  * 显示一条方案
	  * @param val
	  * @param row
	  * @returns {String}
	  */
	  function f_del(val,row){
		  if(row.update_time==undefined){
			  return '';
		  }else{
			  return '<a href=\"javascript:f_teamexamwaydell('+row.id+',\''+row.account_num+'\')\">删除</a>';
		  }
	  }
	  

	//结账
	  var rowacc_num,boid_account_num;
	function f_teamexamwaydell(rowacc_id,boid_account_num)
	{
		$.messager.confirm('提示信息','是否确定此支付方式？',function(r){
			if(r){
				$.ajax({
				url:'teamaccountwaydel.action?id='+rowacc_id+'&account_num='+boid_account_num+'&batchid='+document.getElementsByName("batch_id")[0].value,
				type:'post',
				success:function(text){
					if(text.split("-")[0]=='ok'){
						gettermaccountlistGrjlid(boid_account_num);
		        		  $.messager.alert("操作提示", text);
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
	
	//附加费用
	function f_fjfy(val,row){
		return '<a href="javascript:void(0)" onclick="f_fjfyshow('+row.acc_num+',this)">'+val+'</a>';
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
		$("#results_contrast").css('z-index',99999);
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
	function getElementLeft(element) {
		var actualLeft = element.offsetLeft;
		var current = element.offsetParent;
		while (current !== null) {
			actualLeft += current.offsetLeft;
			current = current.offsetParent;
		}
		return actualLeft;
	}
	
    function save_huazhang(){
    	var  sh_balance = parseFloat($("#sh_balance").val());
    	var  ykfp_amount = $("#ykfp_amount").val();
    	var  jz_amount  =  parseFloat($("#jz_amount").val());
    	var  hz_amount  =  $("#hz_amount").numberbox('getValue'); 
    	var  ys_amount  = 0;
    	var rows = $("#statementswayshow").datagrid('getRows');
    	if(rows.length > 0){
    		for(var i = 0 ; i < rows.length ; i++){
    			ys_amount += rows[i].amount;
    		}
    	}
    	jz_amount = jz_amount - ys_amount;
    	
    	if(hz_amount == '' || hz_amount <= 0){
    		$.messager.alert('提示信息','划价金额不能为空，请重新输入。。','info');
    		return 
    	}
    	if(hz_amount > sh_balance){
    		$.messager.alert('提示信息','划价金额不能大于商户余额，请重新输入。。','info');
    		return ;
    	}
    	if(hz_amount > jz_amount){
    		$.messager.alert('提示信息','划价金额不能大于结账金额，请重新输入。。','info');
    		return ;
    	}
    	var row = $("#statementsinvoidshow").datagrid('getSelections');
    	if(row.length != 1){
    		$.messager.alert('提示信息','结账信息只能为1条，请重新选择。。','info');
    		return ;
    	}
    	var account_num = row[0].account_num;
    	
    	$.ajax({
				url : 'saveDrawAccounts.action',
				data : {
					account_num:account_num,
					hz_amount:hz_amount,
					company_id : $("#company_id").val()
				},
				type : 'post',
				success:function(data){
							var da = data.split("-")
							if(da[0] == 'ok'){
								$("#dlg-huazhang").dialog('close');
								gettermaccountlistGrjlid(account_num);
								$.messager.alert("操作提示", da[1], "");	
							}else{
								$.messager.alert("操作提示", da[1], "error");	
							}
						},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
		 				
		 				
    }
