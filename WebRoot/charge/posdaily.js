$(document).ready(function () {
	
	getWbeSocketPosCode();
	poshuizongjine();
	getposDailyDetailList();
	getendposdailySummary();
//	getcashierdetail();
	getendposdailydetail(0);
	$('#tt').tabs({    
	    onSelect:function(title,index){    
//	    	getendposdailydetail();  
	    	poshuizongjine();
	    }    
	});
});

var pos_code = '';
var trans_code = '';
var  pay_way = '';
var sf_amount= 0;
var tf_amount= 0;



//pos汇总
function poshuizongjine(){
	sf_amount=0;
	tf_amount=0;
	var model = {
		"pos_code":pos_code
	};
	$("#poshuizongjine").datagrid({
		url: 'getPosDailySummaryCharge.action', 
		queryParams: model,
		rownumbers:true,
		columns:[[
		          {field:'ck',checkbox:true },
		          {align:"center",field:"pos_code","title":"pos终端号","width":8}, 
		          {align:"center",field:"trans_code","title":"类型","width":10,"formatter":f_type},
		          {align:"center",field:"data_name","title":"收费方式","width":15},
		          {align:"center",field:"pay_way","title":"收费方式","width":15,"hidden":true},
		          {align:"center",field:"pos_charge_amount","title":"pos交易金额","width":15}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('poshuizongjine','ck,pos_code',0);
	    	$("#sf_amount").html(sf_amount);
	    	$("#tf_amount").html(tf_amount);
	    	$("#rj_amount").html(decimal(Number(sf_amount-tf_amount),2));
	    	
	    },
	    onDblClickRow : function(index, row) {	    		
	    		pos_code = row.pos_code;
	    		trans_code = row.trans_code;
	    		pay_way = row.pay_way;
	    	getposDailyDetailList();
			},
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false
	
	});
}

//pos明细
function getposDailyDetailList(){
	var model = {
		"pos_code":pos_code,
		"trans_code":trans_code,
		"pay_way":pay_way
	};
	$("#getposDailyDetailList").datagrid({
		url: 'getposDailyDetailListCharge.action', 
		queryParams: model,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		rownumbers:true,
		columns:[[
//		          {field:'ck',checkbox:true },
		          {align:"center",field:"peis_trade_code","title":"交易流水号","width":8},
		          {align:"center",field:"trans_code","title":"类型","width":10,"formatter":f_types},
		          {align:"center",field:"pos_charge_amount","title":"交易金额","width":15},
		          {align:"center",field:"creater","title":"操作员","width":15},
		          {align:"center",field:"create_time","title":"交易时间","width":15},
		          {align:"center",field:"pay_class_s","title":"交易类型","width":15},
		          {align:"center",field:"trade_no","title":"原参考号","width":15},
		          {align:"center",field:"voucher_no","title":"凭证号","width":15},
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false
	});
}

function  f_type(val,row){
	if(val == '00'){
		sf_amount += decimal(Number(row.pos_charge_amount),2);
		return "收费";
	}else if(val == '01' || val == '02'){
		tf_amount += decimal(Number(row.pos_charge_amount),2);
		return  '<font color="red">退费</font>';
	}
}
function  f_types(val,row){
	if(val == '00'){
		return "收费";
	}else if(val == '01' || val == '02'){
		return  '<font color="red">退费</font>';
	}
}


// 收费员日结
function posrijie(){
   var rows = $("#poshuizongjine").datagrid('getRows');
   if(rows.length <= 0){
	   	$.messager.alert("操作提示", "暂无日结信息，请重新加载信息。。", "error");
		return;
   }
   if(pos_code == '' || pos_code == null){
   		$.messager.alert("操作提示", "本机终端号获取失败，请刷新重试。。", "info");
		return;
   }
	$.messager.confirm('是否日结',' 确定日结吗？',function(r){
		if(r){
			var mes = {
				inter_class:"01",   //接口类型(01:银联POS通;02:市医保;03:省医保)
			    trade_class:'00',   //交易类型(00:银行卡;01:POS通)
			    trade_op_code:"06",   //POS交易操作类型(05:签到)
			    pay_class:"01",  //支付种类(01:个检缴费;02:卡充值;03:团体预付费;04:团体缴费)
			    peis_trade_code:'', //体检交易流水号
			    operator_id:$("#userid").val(),  //操作员ID
  				operator_code:"",   //操作员工号
                amount: '1',  //金额
  				trade_date:"",//原交易日期(yyyymmdd格式，退货时用，其他交易空格)
			    trade_no:'',  //原交易参考号
			    voucher_no:'',  //原凭证号
			    qrcode:''  //POS通串码(微信、支付宝码)
			};
			
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >打印申请中，请稍后。。。</font></div></div>';
			$("body").prepend(str);	
			
			insurance_send('dbgj','01','06',JSON.stringify(mes),function(objvalue) {//发送请求
			    if(objvalue.error_flag == '1'){//发送成功
			    	$(".loading_div").remove();
			    	var  da = eval('('+objvalue.jsondata+')');
			    	if(da.erro_code == '0'){//成功  
			    		var mes1 = {
									inter_class:"01",   //接口类型(01:银联POS通;02:市医保;03:省医保)
								    trade_class:'01',   //交易类型(00:银行卡;01:POS通)
								    trade_op_code:"06",   //POS交易操作类型(05:签到)
								    pay_class:"01",  //支付种类(01:个检缴费;02:卡充值;03:团体预付费;04:团体缴费)
								    peis_trade_code:'', //体检交易流水号
								    operator_id:$("#userid").val(),  //操作员ID
					  				operator_code:"",   //操作员工号
					                amount: '1',  //金额
					  				trade_date:"",//原交易日期(yyyymmdd格式，退货时用，其他交易空格)
								    trade_no:'',  //原交易参考号
								    voucher_no:'',  //原凭证号
								    qrcode:''  //POS通串码(微信、支付宝码)
								};
				    	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >结算中，请稍后。。。</font></div></div>';
						$("body").prepend(str);		
				    	insurance_send('dbgj','01','06',JSON.stringify(mes1),function(obj) {//发送请求
					    	 if(objvalue.error_flag == '1'){//发送成功
					    	 	var  dat = eval('('+obj.jsondata+')');
					    	 	if(dat.erro_code == '0'){//成功  
					    	 		savePosDailyInfo();
					    	 	}else{
					    	 		$(".loading_div").remove();
					    	 		$.messager.alert('提示信息', dat.error_msg+' 操作失败。。','error');
					    			return;
					    	 	}
					    	 }else{
					    	 	$(".loading_div").remove();
						    	$.messager.alert('提示信息', obj.error_msg+'操作失败。。','error');
						        return;
					    	 }
				    	});
			    	}else{
			    		$.messager.alert('提示信息', da.error_msg+' 操作失败。。','error');
					    return;
			    	}
			    }else{
			    	$(".loading_div").remove();
			    	$.messager.alert('提示信息', objvalue.error_msg+'操作失败。。','error');
			        return;
			    }
			});
        }
	});
	
}

//从新发websocket申请  打pos日结小票
function cdfp(){//重打发票
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >打印申请中，请稍后。。。</font></div></div>';
	$("body").prepend(str);	
	var mes = {
				"inter_class":"01",   //接口类型(01:银联POS通;02:市医保;03:省医保)
			    "trade_class":'01',   //交易类型(00:银行卡;01:POS通)
			    "trade_op_code":"07",   //POS交易操作类型(05:签到)
			    "pay_class":"01",  //支付种类(01:个检缴费;02:卡充值;03:团体预付费;04:团体缴费)
			    "peis_trade_code":'', //体检交易流水号
			    "operator_id":$("#userid").val(),  //操作员ID
  				"operator_code":"",   //操作员工号
                "amount": "1",  //金额
  				"trade_date":"",//原交易日期(yyyymmdd格式，退货时用，其他交易空格)
			    "trade_no":'',  //原交易参考号
			    "voucher_no":'',  //原凭证号
			    "qrcode":''  //POS通串码(微信、支付宝码)
			};
	
	insurance_send('dbgj','01','02',JSON.stringify(mes),function(objvalue) {//发送请求
		$(".loading_div").remove();
	    if(objvalue.error_flag == '1'){//发送成功
	    	var  dat = eval('('+obj.jsondata+')');
	    	if(dat.erro_code == '0'){//成功
	    		$.messager.alert('提示信息', ' 操作成功。。','');
	    	}else{
	    		$.messager.alert('提示信息', dat.error_msg+' 操作失败。。','error');
				return;
	    	}
	    }else{
	    	$.messager.alert('提示信息', objvalue.error_msg+'操作失败。。','error');
			return;
	    }
	});
}




/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param fldList 要合并table的id
4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
5         */
function MergeCells(tableID, fldList,zhuIndex) {
	var Arr = fldList.split(",");
	var dg = $('#' + tableID);
	var fldName;
	var RowCount = dg.datagrid("getRows").length;
	var span;
	var PerValue = "";
	var CurValue = "";
	var length = Arr.length - 1;
	for (i = length; i >= 1; i--) {
		z_fldName = Arr[zhuIndex];
		fldName = Arr[i];
		PerValue = "";
		span = 1;
		for (row = 0; row <= RowCount; row++) {
			if (row == RowCount) {
				CurValue = "";
			}else {
				CurValue = dg.datagrid("getRows")[row][z_fldName];
			}
			if (PerValue == CurValue) {
				span += 1;
			}else {
				var index = row - span;
				dg.datagrid('mergeCells', {
					index: index,
					field: fldName,
					rowspan: span,
					colspan: null
				});
				span = 1;
				PerValue = CurValue;
			}
		}
	}
}
/**
 * 定义工具栏
 */
var toolbar1=[{
		text:'打印日结单',
		width:150,
		height:30,
		iconCls:'icon-check',
	    handler:function(){
	    	var obj = $("#dailyacclist").datagrid('getSelected');
	    	if(obj == null || obj == undefined){
	    		$.messager.alert("操作提示", '请选择需要打印的日结单!',"error");
	    		return;
	    	}
	    	var exeurl="invoiceService://"+$("#userid").val()+"$2$"+obj.daily_acc_num;
	    	location.href=exeurl;
	    }
	}];
var toolbar=[{
		text:'导出Excel',
		width:150,
		height:30,
		iconCls:'icon-check',
	    handler:function(){
	    	var obj = $("#dailyacclist").datagrid('getSelected');
	    	if(obj == null || obj == undefined){
	    		$.messager.alert("操作提示", '请选择需要打印的日结单!',"error");
	    		return;
	    	}
	    	var exeurl="invoiceService://"+$("#userid").val()+"$2$"+obj.daily_acc_num;
	    	location.href=exeurl;
	    }
	}];



//获取日结收费明细列表
function getcashierdetail(num){
	var model = {'daily_acc_num':num};
	$("#dailyaccdetaillist").datagrid({
		url: 'getCashierDailyAccDetailCharge.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
		          {align:"center",field:"exam_num","title":"体检号","width":20},
		          {align:"center",field:"user_name","title":"姓名","width":15},
		          {align:"center",field:"com_name","title":"单位","width":25},
		          {align:"center",field:"batch_name","title":"批次任务","width":15},
		          {align:"center",field:"exam_types","title":"结算类型","width":15},
		          {align:"center",field:"amount2","title":"金额(元)","width":15},
		          {align:"center",field:"charging_statuss","title":"结算状态","width":15},
		          {align:"center",field:"cash_date","title":"结算时间","width":25}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false,
		toolbar:toolbar
	});
}

//已日结记录
function getendposdailySummary(){
	var model = {
			"daily_acc_date1":$("#daily_acc_date1").datebox('getValue'),
			"daily_acc_date2":$("#daily_acc_date2").datebox('getValue')
	}
	$("#yjmx_posdaily").datagrid({
		url: 'getendposdailySummaryCharge.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
				  {field:'ck',checkbox:true },
		          {align:"center",field:"creater","title":"操作员","width":10},
		          {align:"center",field:"create_time","title":"日结时间","width":18},
		          {align:"center",field:"amount","title":"日结金额","width":15},
		          {align:"center",field:"daily_count","title":"总笔数","width":15}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    onDblClickRow : function(index, row) {	 
	    		getendposdailydetail(row.id);
		},
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false,
		toolbar:toolbar1
	});
}


//已日结记录
function getendposdailydetail(id){
	var model = {
			"id":id
	}
	$("#yj_posdaily").datagrid({
		url: 'getEndPosDailyDetailListCharge.action',
		queryParams: model,
		pageSize: 30,//每页显示的记录条数，默认为10 
		pageList:[30,45,60,75],//可以设置每页记录条数的列表 
		rownumbers:true,
		columns:[[
		          {align:"center",field:"peis_trade_code","title":"交易流水号","width":12},
		          {align:"center",field:"trans_code","title":"类型","width":8,"formatter":f_types},
		          {align:"center",field:"data_name","title":"收费方式","width":12},
		          {align:"center",field:"pay_class_s","title":"交易类型","width":15},
		          {align:"center",field:"trade_amount","title":"交易金额","width":15},
		          {align:"center",field:"create_time","title":"交易时间","width":15},
		          {align:"center",field:"creater","title":"操作员","width":15},
		          {align:"center",field:"trade_no","title":"原参考号","width":15},
		          {align:"center",field:"voucher_no","title":"凭证号","width":15},
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: true,
		fitColumns:true,
		fit:true,
		striped:true,
		border:false
	});
}

function getWbeSocketPosCode(){   //获取本机终端号
	var mes={
			"inter_class":"01",   //接口类型(01:银联POS通;02:市医保;03:省医保)
		    "trade_class":'02',   //交易类型(00:银行卡;01:POS通，02获取本机终端号)
		    "trade_op_code":"",   //POS交易操作类型(05:签到)
		    "pay_class":"",  //支付种类(01:个检缴费;02:卡充值;03:团体预付费;04:团体缴费)
		    "peis_trade_code":"01", //体检交易流水号
		    "operator_id":"",  //操作员ID
			"operator_code":"",   //操作员工号
            "amount": "1",  //金额
			"trade_date":"",//原交易日期(yyyymmdd格式，退货时用，其他交易空格)
		    "trade_no":"",  //原交易参考号
		    "voucher_no":'',  //原凭证号
		    "qrcode":""  //POS通串码(微信、支付宝码)
		}
	
	insurance_send('dbgj','01','02',JSON.stringify(mes),function(objvalue) {//发送请求
	    if(objvalue.error_flag == '1'){//发送成功
	    	var  da = eval('('+objvalue.jsondata+')');
	    	if(da.erro_code == '0' && da.peis_trade_code != ''){
	    		pos_code = da.peis_trade_code;
	    		poshuizongjine();
				getposDailyDetailList();
				getendposdailySummary();
	    	}else{
	    		$.messager.alert('提示信息', da.error_msg+'操作失败。。','error');
			         return;
	    	}
	    }else{
	    	$.messager.alert('提示信息', objvalue.error_msg+'操作失败。。','error');
	        return;
	    }
	});
	
}
function shuaxin(num){
	poshuizongjine();
	getposDailyDetailList();
	if(num == '1'){
		posrijie();
	}
}


function savePosDailyInfo(){ //保存结算信息
	$.ajax({
        url:'savePosDailyInfoCharge.action',  
        data:{ 
        	"pos_code":pos_code
        },
        type: "post",//数据发送方式   
        success: function(data){
        		var state = eval("("+data+")");
        		if(state.flag == 'error'){
        			$(".loading_div").remove();
        			$.messager.alert('提示信息', state.info,'error');
        			return;
        		}
        		if(state.flag == 'ok'){
        			$(".loading_div").remove();
        			$.messager.alert('提示信息', state.info,'');
        			return;
        		}
        		
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",'error');
            }  
    });
	
}
