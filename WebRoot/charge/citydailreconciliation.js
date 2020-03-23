$(document).ready(function () {
    getdailyacclist();
});

function chaxun(){
    getdailyacclist();
    getRefundlist();
    getAmountAll();
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
    text:'生成日结单',
    width:150,
    height:30,
    iconCls:'icon-save',
    handler:function(){
        $.messager.confirm('确认','您确认要生成日结单吗？',function(r){
            if (r){
                saveCashierDaily();
            }
        });
    }
},{
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


var  list = new Array();

function getdailyacclist(){
    var model = {
        "daily_acc_date1":$("#daily_acc_date1").datebox('getValue'),
        "daily_acc_date2":$("#daily_acc_date2").datebox('getValue'),
        "pay_way":$("#type").combobox('getValue')
    }
    $("#dailyacclist").datagrid({
        url:'getPersionCityReconciliationCharge.action',
        dataType: 'json',
        queryParams:model,
        pageSize: 15,//每页显示的记录条数，默认为10
        pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
        columns:[[
            {align:'center',field:'req_num',title:'申请号',width:50},
            {align:'center',field:'charging_status',title:'类型',width:50,"formatter":f_charging_status},
            {align:'center',field:'amount',title:'交易金额',width:50},
            {align:'center',field:'exam_num',title:'体检号',width:50},
            {align:'center',field:'user_name',title:'姓名',width:50},
            {align:'center',field:'create_time',title:'时间',width:50},
            {align:'center',field:'ck',title:'查看',width:20,"formatter":f_permingxi},
            {align:'center',field:'mxdz',title:'明细对账',width:20,"formatter":f_detail}
        ]],
        onLoadSuccess:function(value){
            $("#datatotal").val(value.total);
        },
        onDblClickRow : function(index, row) {

        },
        rownumbers:true,
        singleSelect:true,
        collapsible:true,
        pagination: true,
        fit:true,
        fitColumns:true,
        striped:true
//		    toolbar:toolbar1
    });
}
function  f_charging_status(val,row){
    if(val == 'Y'){
        return '收费';
    }
    if(val == 'M'){
        return '<font color="red">退费</font>';
    }
}

function f_mingxi(val,row){
    return '<a href=\"javascript:f_cardmx(\''+row.sale_trade_num+'\')\">销售明细</a>';
}
function f_cardmx(sale_trade_num){
    getcardlistdetail(sale_trade_num);
    $("#dlg-card").dialog('open');
    $("#dlg-card").dialog('center');
}

function f_permingxi(val,row){
    return '<a href=\"javascript:permingxi(\''+row.id+'\')\">明细</a>';
}
function f_detail(val,row){
    return '<a href=\"javascript:getmedicalInsuranceDetail(\''+row.req_num+'\')\">单笔对账</a>';
}
function permingxi(id){
    getdailyacclistdetail(id);
    $("#dlg-psersion").dialog('open');
    $("#dlg-psersion").dialog('center');
}



function getdailyacclistdetail(id){
    var model = {
        "id":id
    }
    $("#persioncitycharginglist").datagrid({
        url:'getPersionCityReconciliationDetailListCharge.action',
        dataType: 'json',
        queryParams:model,
        pageSize: 15,//每页显示的记录条数，默认为10
        pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
        columns:[[
            {align:'center',field:'item_name',title:'项目',width:50},
            {align:'center',field:'item_amount',title:'原金额',width:50},
            {align:'center',field:'discount',title:'折扣',width:50},
            {align:'center',field:'amount',title:'折后金额',width:50},
            {align:'center',field:'user_name',title:'操作员',width:50},
            {align:'center',field:'create_time',title:'操作时间',width:50}
        ]],
        onLoadSuccess:function(value){
            $("#datatotal").val(value.total);
        },
        onDblClickRow : function(index, row) {

        },
        rownumbers:true,
        singleSelect:true,
        collapsible:true,
        pagination: false,
        fit:true,
        fitColumns:true,
        striped:true
//		    toolbar:toolbar1
    });
}

function getcardlistdetail(sale_trade_num){
    var model = {
        "sale_trade_num":sale_trade_num
    }
    $("#cardcitycharginglist").datagrid({
        url:'getCardCityReconciliationDetailListCharge.action',
        dataType: 'json',
        queryParams:model,
        pageSize: 15,//每页显示的记录条数，默认为10
        pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
        columns:[[
            {align:'center',field:'sale_trade_num',title:'流水号',width:50},
            {align:'center',field:'card_num',title:'卡号',width:50},
            {align:'center',field:'amount',title:'面值',width:50},
            {align:'center',field:'sale_amount',title:'售卡金额',width:50},
            {align:'center',field:'user_name',title:'操作员',width:50},
            {align:'center',field:'create_time',title:'操作时间',width:50}
        ]],
        onLoadSuccess:function(value){
            $("#datatotal").val(value.total);
        },
        onDblClickRow : function(index, row) {

        },
        rownumbers:true,
        singleSelect:true,
        collapsible:true,
        pagination: false,
        fit:true,
        fitColumns:true,
        striped:true
//		    toolbar:toolbar1
    });
}


var  check_amount = 0;//对账总金额
function  getSummaryAmount(){
    $.ajax({
        url : 'getCityReconciliationSummaryAmountCharge.action',
        data : {
            "daily_acc_date1":$("#daily_acc_date1").datebox('getValue'),
            "daily_acc_date2":$("#daily_acc_date2").datebox('getValue'),
            "pay_way":$("#type").combobox('getValue')
        },
        type : "post",//数据发送方式
        success : function(data) {
            var da = 	eval('('+data+')');
            $("#persionamount").html(da.amount);
            $("#persion_count").html(da.persion_count);
            $("#cardamount").html(da.sale_amount);
            $("#card_count").html(da.card_count);
            $("#summaryamount").html(decimal(da.amount+da.sale_amount,2)+' 元');

            $("#detailamount").html();
            $("#settlement_amount").html(da.settlement_amount);
            $("#settlement_count").html(da.settlement_count);
            $("#revoke_amount").html(da.revoke_amount);
            $("#revoke_count").html(da.revoke_count);
            $("#correct_amount").html(da.correct_amount);
            $("#correct_count").html(da.correct_count);

            check_amount = decimal(da.amount+da.sale_amount,2);
        },
        error : function() {
            $.messager.alert("操作提示", "操作错误", "error");
        }
    });
}

//-------------------------------------获取对账金额---------------------------------
function getAmountAll(){
    clearYbRZ();
    if($("#type").combobox('getValue') == '' || $("#type").combobox('getValue') == null){
        $.messager.alert("操作提示", "请选择收费方式。", "warning");
        return;
    }
    $.ajax({    // 获取所有的收费 总额 以及 明细
        url: 'getCityReconciliationQueryCharge.action',
        data: {
            "daily_acc_date1": $("#daily_acc_date1").datebox('getValue'),
            "daily_acc_date2": $("#daily_acc_date2").datebox('getValue'),
            "pay_way": $("#type").combobox('getValue')
        },
        type: "post",//数据发送方式
        success: function (data) {
            $(".loading_div").remove();
            var objstar = eval("(" + data + ")");
            var jymxlist = objstar.jymxlist;
            var sf_je = 0;
            var tf_je = 0;
            var sf_bs = 0;
            var tf_bs = 0;
            if (jymxlist.length > 0) {  //  对账数据
                for (var i = 0; i < jymxlist.length; i++) {
                    if (jymxlist[i].akc227 >= 0) {//收费
                        sf_je += Number(jymxlist[i].akc227);
                        sf_bs++;
                    } else if (jymxlist[i].akc227 < 0) {//退费
                        tf_je += Number(jymxlist[i].akc227);
                        tf_bs++;
                    }
                }
            }
			 
            $("#persionamount").html(sf_je);
            $("#persion_count").html(sf_bs);
            $("#revoke_amount").html(tf_je);
            $("#revoke_count").html(tf_bs);
            $("#summaryamount").html(objstar.akb065);
        }
    });
}


//------------------医保对账申请------------------
function ybdzsq(){
    clearYbRZ();
    if($("#type").combobox('getValue') == '' || $("#type").combobox('getValue') == null){
        $.messager.alert("操作提示", "请选择收费方式。", "warning");
        return;
    }
	var date1=$("#daily_acc_date1").datebox('getValue');
	var date2=$("#daily_acc_date2").datebox('getValue');
	var  startDate = Date.parse(date1);
   var  endDate = Date.parse(date2);
   var days=(endDate - startDate)/(1*24*60*60*1000);
   if(date1 == '' || date2 == ''){
	    $.messager.alert("操作提示", "请选择时间。", "warning");
	   return;
   }
    
   if(days > 0){
	    $.messager.alert("操作提示", "日对账只能选择当天日期。。。", "warning");
	   return;
   }
	
	
    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    $("body").prepend(str);
    $.ajax({    // 获取所有的收费 总额 以及 明细
        url : 'getCityReconciliationQueryCharge.action',

        data : {
            "daily_acc_date1":date1,
            "daily_acc_date2":date2,
            "pay_way":$("#type").combobox('getValue')
        },
        type : "post",//数据发送方式
        success : function(data) {
            $(".loading_div").remove();
            var objstar = eval("("+data+")");
            var jymxlist = objstar.jymxlist;
            var sf_je = 0;
            var tf_je = 0;
            var sf_bs = 0;
            var tf_bs = 0;
            if(jymxlist.length > 0){  //  对账数据
                for(var i = 0; i< jymxlist.length;i++){
                    if(jymxlist[i].akc227 >= 0){//收费
                        sf_je += Number(jymxlist[i].akc227);
                        sf_bs++;
                    }else if(jymxlist[i].akc227 < 0){//退费
                        tf_je += Number(jymxlist[i].akc227);
                        tf_bs++;
                    }
                }
            }
			
            $("#persionamount").html(sf_je);
            $("#persion_count").html(sf_bs);
            $("#revoke_amount").html(tf_je);
            $("#revoke_count").html(tf_bs);
            $("#summaryamount").html(objstar.akb065);
            var inputValueStr = JSON.stringify({
                "data": objstar,
                "usr": "350603010835060300202",
                "pwd": "123",
                "funid": "yb04.07.03.07"
            });

            insurance_send("02","yb04.07.03.07",inputValueStr,function(val){

                var oj= translation(val)
                addYbRZ(inputValueStr,JSON.stringify(oj));
                if(oj.flag == '1'){
                    $.messager.alert("操作提示", "日对账成功，对账失败 "+oj.data.bkeb31+" 笔，对账成功 "+oj.data.bkeb30+"笔。", "");
                    return;

                }else{
                    $.messager.alert("操作提示", "日对账失败，失败原因："+oj.cause+" 对账失败 "+oj.data.bkeb31+" 笔，对账成功 "+oj.data.bkeb30+"笔。详情："+JSON.stringify(oj.data.jyfhmxlist), "");
                    return;
                }
            });

        },
        error : function() {
            $(".loading_div").remove();
            $.messager.alert("操作提示", "操作错误", "error");
        }
    });
}

//-------------医保总额对账-------------------------------
function yb_sum_dzsq(){
    clearYbRZ();
    if ($("#type").combobox('getValue') == '' || $("#type").combobox('getValue') == null) {
        $.messager.alert("操作提示", "请选择收费方式。", "warning");
        return;
    }
    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    $("body").prepend(str);
    $.ajax({    // 获取所有的收费 总额 以及 明细
        url: 'getCityReconciliationSummaryAmountCharge.action',
        data: {
            "daily_acc_date1": $("#daily_acc_date1").datebox('getValue'),
            "daily_acc_date2": $("#daily_acc_date2").datebox('getValue'),
            "pay_way": $("#type").combobox('getValue')
        },
        type: "post",//数据发送方式
        success: function (data) {
            $(".loading_div").remove();
            var obj =  eval('('+data+')');
            var  date1 = $("#daily_acc_date1").datebox('getValue').replace(/-/g,"");
            var  date2 = $("#daily_acc_date2").datebox('getValue').replace(/-/g,"");
            var inputValueStr = JSON.stringify({
                "data": {
                    "aae030": date1,
                    "aae031": date2,
                    "bka555": "01",
                    "bka556": "31",
                    "aka078": "10",
                    "akb065": obj.amount_medical,
                    "akb068": obj.fund,
                    "akb067": obj.personal_cash,
                    "akb066": obj.individual_account,
                    "bkeb83": obj.whole_fund,
                    "bke213": obj.commercial_fund,
                    "bke352": obj.civil_servants,
                    "bke353": obj.bailout_fund,
                    "bkd923": obj.poverty_alleviation,
                    "bkd924": obj.other_fund,
                    "bkd927": obj.enterprise_complement
                },
                "funid": "yb04.07.03.01",
                "usr": "350603010835060300202",
                "pwd": "123"
            });
            insurance_send("02","yb04.07.03.01",inputValueStr,function(val){
                var oj= translation(val);
                addYbRZ(inputValueStr,JSON.stringify(oj));
                if(oj.flag == '1'){
                    $.messager.alert("操作提示", "对账成功, 正向交易笔数: "+oj.data.bke931+" 反向交易笔数："+oj.data.bke932+" 医疗费用总额："+oj.data.akb065, "");
                    return;
                }else{
                    $.messager.alert("操作提示", "日对账失败，失败原因："+oj.cause, "error");
                    return;
                }
            });

        },
        error: function () {
            $(".loading_div").remove();
            $.messager.alert("操作提示", "操作错误", "error");
        }

    });
}


// ---------  医保明细  -------
function getmedicalInsuranceDetail(req_num){
    clearYbRZ();
    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    $("body").prepend(str);
    $.ajax({
        url : 'getCityReconciliationQueryDetailCharge.action',
        data : {
             "req_num":req_num
        },
        type : "post",//数据发送方式
        success : function(data) {
            $(".loading_div").remove();
            if(data != null && data != ""){   // 发送申请
                var obj =  eval('('+data+')');
				 var inputValueStr = JSON.stringify({
                "data": {
                     "aka078": "10",
					 "akc190":obj.medical_req_num,
                     "aae072":obj.medical_charge_req_num,
                     "bke298":obj.his_req_num
                },
                "funid": "yb04.07.03.05",
                "usr": "350603010835060300202",
                "pwd": "123"
            });
				
               /* var inputValueStr = JSON.stringify({
                    "data": {
                        "aka078": "10",
                        "bke354": "Y",
                        "bkeb32": "1",
                        "jymxlist": [{
                            "akc190":obj.medical_req_num,
                            "aae072":obj.medical_charge_req_num,
							"bke298":obj.req_num,
							"akc227":obj.amount_medical,
							"bkc40":obj.personal_cash,
							"bkc41":obj.individual_account,
							"bkc041":obj.individual_account,
							"bkc102":obj.fund,
							"bkc045":obj.whole_fund,
							"bkc052":obj.commercial_fund,
							"bkc059":obj.civil_servants,
							"bkc062":obj.poverty_alleviation,
							"bkc060":obj.bailout_fund,
							"ake173":obj.other_fund,
							"ake026":obj.enterprise_complement,
							"bkc014":
							
		
                            "akc227	":obj.amount_medical,//医疗费用总额
                            "bkc040	":obj.personal_cash,//个人现金支付金额
                            "bkc041	":obj.individual_account,//个人账户支付金额
                            "bkc102	":obj.fund,//基金支付总额
                            "bkc045	":obj.whole_fund,//其中：统筹基金支付
                            "bkc052	":obj.commercial_fund,//其中：商保基金支付（大额补充）
                            "bkc059	":obj.civil_servants,//其中：公务员医疗补助
                            "bkc062	":obj.poverty_alleviation,//其中：精准扶贫医疗叠加
                            "bkc060	":obj.bailout_fund,//其中：医疗救助基金
                            "ake173	":obj.other_fund,//其中：其他基金支付
                            "ake026	":obj.enterprise_complement,//其中：企业补充
                            "bkc014	":obj.actual_visit_date,//实际就诊日期
                            "ake007	":obj.settlement_date//费用发生日期
                            }]
                    },
                    "funid": "yb04.07.03.01",
                    "usr": "350603010835060300202",
                    "pwd": "123"
                });*/
				
					 
                insurance_send("02","yb04.07.03.05",inputValueStr,function(val){
                    var oj= translation(val);
                    addYbRZ(inputValueStr,JSON.stringify(oj));
                    if(oj.flag == '1'){
						$.messager.alert("操作提示", "对账成功", "");
                        return;
                    }else{
                        $.messager.alert("操作提示", "对账失败，失败原因："+oj.cause, "error");
                        return;
                    }
                });

            }else{
                $.messager.alert("操作提示", "收费记录异常，请重新选择。", "warning");
            }
        },
        error : function() {
            $(".loading_div").remove();
            $.messager.alert("操作提示", "操作错误", "error");
        }
    });
}

//对账明细列表
function  insureVerifyAccountDetailList(){
    var model = {
        "pay_way":$("#type").combobox('getValue')
    }
    $("#getInsureVerifyAccountDetailList").datagrid({
        url:'getInsureVerifyAccountDetailListCharge.action',
        dataType: 'json',
        queryParams:model,
        pageSize: 15,//每页显示的记录条数，默认为10
        pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
        columns:[[
            {align:'center',field:'peis_req_code',title:'体检流水号',width:50},
            {align:'center',field:'total_amount',title:'交易金额',width:50},
            {align:'center',field:'center_cycle_code',title:'业务周期号',width:50},
            {align:'center',field:'peis_trade_code',title:'原交易流水号',width:50},
            {align:'center',field:'PatNo',title:'医保编号',width:50},
            {align:'center',field:'account_pay',title:'本次账户支付',width:50},
            {align:'center',field:'acc_date',title:'结算日期',width:50},
            {align:'center',field:'czjy',title:'冲正',width:50,formatter:f_cz}
        ]],
        onLoadSuccess:function(value){
            $("#datatotal").val(value.total);
        },
        onDblClickRow : function(index, row) {

        },
        rownumbers:true,
        singleSelect:true,
        collapsible:true,
        pagination: true,
        fit:true,
        fitColumns:true,
        striped:true
//		    toolbar:toolbar1
    });
}

function f_cz(val,row,index){
    if(row.type == '1'){
        return '<a href=\"javascript:checkCorrect(\''+row.peis_trade_code+'\')\">冲正交易</a>';
    }
}

//--------------冲正交易--------
function checkCorrect(req){
    if(req == null || req == ''){
        $.messager.alert("操作提示", "体检申请号错误！", "info");
        return ;
    }
    var inter_class = '';
    var type = $("#type").combobox('getValue');
    if(type == '103'){  //市医保
        inter_class = '02';
    }else if(type == '104'){//省医保
        inter_class ='03';
    }
    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    $("body").prepend(str);
    $.ajax({
        url : 'checkCorrectTransactionCharge.action', //校验交易是否能冲正
        data : {
            "req_num":req
        },
        type : "post",//数据发送方式
        success : function(data) {
            if("ok"==data.split("-")[0]){  // 可以冲正操作
                var obj = {
                    "inter_class":inter_class,   //接口类型(01:银联POS通;02:市医保;03:省医保)
                    "trade_code":"2421",      //业务编码(2421:冲正交易)
                    "operator_id":$("#userid").val(),  //操作员ID
                    "operator_code":$("#work_num").val(),   //操作员工号
                    "operator_name":$("#userName").val(),  //操作员姓名
                    "peis_trade_code":req, //被冲正体检交易流水号
                    "reversal_trade_code":"2410",//冲正业务编码(仅支持2210（入院登记），2310（处方明细上传），2410（费用结算）交易的冲正)
                    "reversal_reason":"02" //冲正原因(二级编码 冲正原因： 01 没有得到中心端响应  02 HIS系统处理失败 03 对账发现中心多数据 09 动态库自动冲正)
                }
                //----------医保申请----------
                insurance_send('dbgj',inter_class,2421,JSON.stringify(obj),function(objvalue) {//申请
                    $(".loading_div").remove();
                    if(objvalue.error_flag == '1'){//调用成功
                        var val= eval('('+objvalue.jsondata+')');//接口返回结果
                        if(val.erro_code == '0'){//成功
                            $.messager.alert("操作提示", "冲正成功，请重新对账。。", "");
//						 			getmedicalInsuranceDetail();//重新加载数据
                            chaxun();
                        }else{//失败
                            $.messager.alert("操作提示", val.error_msg, "error");
                            return;
                        }
                    }else{//失败
                        $.messager.alert("操作提示", objvalue.error_msg, "error");
                        return;
                    }
                });
            }else{
                $(".loading_div").remove();
                $.messager.alert("操作提示", data.split("-")[1], "error");
                return;
            }
        },
        error : function() {
            $(".loading_div").remove();
            $.messager.alert("操作提示", "操作错误", "error");
            return;
        }
    });

}


//系统内对账    对照 insure_account表
function checkInsureAccount(){
    var model = {
        "daily_acc_date1":$("#daily_acc_date1").datebox('getValue'),
        "daily_acc_date2":$("#daily_acc_date2").datebox('getValue'),
        "pay_way":$("#type").combobox('getValue')
    }

    $("#getInsureVerifyAccountDetailList").datagrid({
        url:'getInsureAccountListCharge.action',
        dataType: 'json',
        queryParams:model,
        pageSize: 15,//每页显示的记录条数，默认为10
        pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
        columns:[[
//		    {align:'center',field:'type',title:'type',width:10},
            {align:'center',field:'peis_req_code',title:'体检流水号',width:50},
            {align:'center',field:'total_amount',title:'交易金额',width:50},
            {align:'center',field:'center_cycle_code',title:'业务周期号',width:50},
            {align:'center',field:'peis_trade_code',title:'原交易流水号',width:50},
            {align:'center',field:'PatNo',title:'医保编号',width:50},
            {align:'center',field:'account_pay',title:'本次账户支付',width:50},
            {align:'center',field:'acc_date',title:'结算日期',width:50},
            {align:'center',field:'czjy',title:'冲正',width:50,formatter:f_cz}
        ]],
        onLoadSuccess:function(value){
            $("#datatotal").val(value.total);

        },
        onDblClickRow : function(index, row) {

        },
        rowStyler:function(index,row){
            if (row.type=='1'){
                return 'color:#f00;';
            }
        },
        rownumbers:true,
        singleSelect:true,
        collapsible:true,
        pagination: true,
        fit:true,
        fitColumns:true,
        striped:true
    });
}


//读卡

function readCard(){
    var inter_class = '02';
    var type=  $("#type").combobox('getValue');
    if(type == '104'){
        inter_class = '03';
    }
    insurance_send('dbgj',inter_class,'2100','{"inter_class":"'+inter_class+'","trade_code":"2100","operator_id":"'+$("#userid").val()+'","operator_code":"'+$("#work_num").val()+'","operator_name":"'+$("#userName").val()+'"}',function(objvalue) {
    });
}

// 个人退费列表
function getRefundlist(){
    var model = {
        "daily_acc_date1":$("#daily_acc_date1").datebox('getValue'),
        "daily_acc_date2":$("#daily_acc_date2").datebox('getValue'),
        "pay_way":$("#type").combobox('getValue')
    }
    $("#refundlist").datagrid({
        url:'getPersionRefundlistCharge.action',
        dataType: 'json',
        queryParams:model,
        pageSize: 15,//每页显示的记录条数，默认为10
        pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
        columns:[[
            {align:'center',field:'req_num',title:'申请号',width:50},
            {align:'center',field:'charging_status',title:'类型',width:50,"formatter":f_charging_status},
            {align:'center',field:'amount',title:'交易金额',width:50},
            {align:'center',field:'exam_num',title:'体检号',width:50},
            {align:'center',field:'user_name',title:'姓名',width:50},
            {align:'center',field:'create_time',title:'时间',width:50},
            {align:'center',field:'ck',title:'查看',width:20,"formatter":f_permingxi}
        ]],
        onLoadSuccess:function(value){
            $("#datatotal").val(value.total);

        },
        onDblClickRow : function(index, row) {

        },
        rownumbers:true,
        singleSelect:true,
        collapsible:true,
        pagination: true,
        fit:true,
        fitColumns:true,
        striped:true
    });
}

// 退费的信息列表
function getRefundDetailList(){
    var model = {
        "daily_acc_date1":$("#daily_acc_date1").datebox('getValue'),
        "daily_acc_date2":$("#daily_acc_date2").datebox('getValue'),
        "pay_way":$("#type").combobox('getValue'),
        "type":'T'
    }

    $("#getRefundInsureVerifyAccountDetailList").datagrid({
        url:'getInsureAccountListCharge.action',
        dataType: 'json',
        queryParams:model,
        pageSize: 15,//每页显示的记录条数，默认为10
        pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
        columns:[[
//		    {align:'center',field:'type',title:'type',width:10},
            {align:'center',field:'peis_req_code',title:'体检流水号',width:50},
            {align:'center',field:'total_amount',title:'交易金额',width:50},
            {align:'center',field:'center_cycle_code',title:'业务周期号',width:50},
            {align:'center',field:'peis_trade_code',title:'原交易流水号',width:50},
            {align:'center',field:'PatNo',title:'医保编号',width:50},
            {align:'center',field:'account_pay',title:'本次账户支付',width:50},
            {align:'center',field:'create_time',title:'结算日期',width:50}
        ]],
        onLoadSuccess:function(value){
            $("#datatotal").val(value.total);

        },
        onDblClickRow : function(index, row) {

        },
        rownumbers:true,
        singleSelect:true,
        collapsible:true,
        pagination: true,
        fit:true,
        fitColumns:true,
        striped:true
    });
}

//冲正交易列表
function getCorrectDetailList(){
    var model = {
        "daily_acc_date1":$("#daily_acc_date1").datebox('getValue'),
        "daily_acc_date2":$("#daily_acc_date2").datebox('getValue'),
        "pay_way":$("#type").combobox('getValue'),
        "type":'C'
    }

    $("#getCorrectInsureVerifyAccountDetailList").datagrid({
        url:'getInsureAccountListCharge.action',
        dataType: 'json',
        queryParams:model,
        pageSize: 15,//每页显示的记录条数，默认为10
        pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
        columns:[[
//		    {align:'center',field:'type',title:'type',width:10},
            {align:'center',field:'peis_req_code',title:'体检流水号',width:50},
            {align:'center',field:'total_amount',title:'交易金额',width:50},
            {align:'center',field:'center_cycle_code',title:'业务周期号',width:50},
            {align:'center',field:'peis_trade_code',title:'原交易流水号',width:50},
            {align:'center',field:'PatNo',title:'医保编号',width:50},
            {align:'center',field:'account_pay',title:'本次账户支付',width:50},
            {align:'center',field:'create_time',title:'结算日期',width:50}
        ]],
        onLoadSuccess:function(value){
            $("#datatotal").val(value.total);

        },
        onDblClickRow : function(index, row) {

        },
        rownumbers:true,
        singleSelect:true,
        collapsible:true,
        pagination: true,
        fit:true,
        fitColumns:true,
        striped:true
    });
}


//帆软打印医保明细
function print_insure_detail(){
    var beginDate = $("#daily_acc_date1").datebox('getValue');
    var endDate = $("#daily_acc_date2").datebox('getValue');
    var user_id = $("#userid").val();
//	window.open("ReportServer?reportlet=insure_charge_detail.cpt&user_id="+user_id+"&beginDate="+beginDate+"&endDate="+endDate);

    var reportlets = new Array();
    var sa = {
        "reportlet" : 'insure_charge_detail.cpt',
        "user_id" : user_id,
        "endDate":endDate,
        "beginDate":beginDate
    };
    reportlets.push(sa);
    var printurl = "../../ReportServer";
    var config = {
        url : printurl,
        isPopUp : true,
        data : {
            reportlets : reportlets
        }
    }
    FR.doURLPDFPrint(config);
}


function print_jzd(){
    var beginDate = $("#daily_acc_date1").datebox('getValue');
    var endDate = $("#daily_acc_date2").datebox('getValue');
    var user_id = $("#userid").val();
//	window.open("ReportServer?reportlet=insure_charge_detail.cpt&user_id="+user_id+"&beginDate="+beginDate+"&endDate="+endDate);

    var reportlets = new Array();
    var sa = {
        "reportlet" : 'sfy_jkd.cpt',
        "user_id" : user_id,
        "endDate":endDate,
        "beginDate":beginDate
    };
    reportlets.push(sa);
    var printurl = "../../ReportServer";
    var config = {
        url : printurl,
        isPopUp : true,
        data : {
            reportlets : reportlets
        }
    }
    FR.doURLPDFPrint(config);
}


function qiantui(inter_class){
    insurance_send('dbgj',inter_class,'9110','{"inter_class":"'+inter_class+'","trade_code":"9110","operator_id":"'+$("#userid").val()+'","operator_code":"'+$("#work_num").val()+'","operator_name":"'+$("#userName").val()+'"}',function(objvalue) {
        if(objvalue.error_flag=="1"){
            var obj=eval("("+objvalue.jsondata+")");
//					if(obj.erro_code=="0"){
            $.messager.alert("操作提示", obj.error_msg, "");
//					}
        }else{
            $.messager.alert("操作提示", objvalue.error_msg, "");
        }

    });
}

function addYbRZ(req,res){
    var transitionJson = formatJson(req, function (error) {
    });
    var transitionJson1 = formatJson(res, function (error) {
    });
    $("#yb_req").val(transitionJson);
    $("#yu_reqs").val(transitionJson1);
}
function clearYbRZ(){
    $("#yb_req").val("");
    $("#yu_reqs").val("");
}

// 格式方法
// 公共方法
function transitionJsonToString (jsonObj, callback) {
    // 转换后的jsonObj受体对象
    var _jsonObj = null;
    // 判断传入的jsonObj对象是不是字符串，如果是字符串需要先转换为对象，再转换为字符串，这样做是为了保证转换后的字符串为双引号
    if (Object.prototype.toString.call(jsonObj) !== "[object String]") {
        try {
            _jsonObj = JSON.stringify(jsonObj);
        } catch (error) {
            // 转换失败错误信息
            console.error('您传递的json数据格式有误，请核对...');
            console.error(error);
            callback(error);
        }
    } else {
        try {
            jsonObj = jsonObj.replace(/(\')/g, '\"');
            _jsonObj = JSON.stringify(JSON.parse(jsonObj));
        } catch (error) {
            // 转换失败错误信息
            console.error('您传递的json数据格式有误，请核对...');
            console.error(error);
            callback(error);
        }
    }
    return _jsonObj;
}
// callback为数据格式化错误的时候处理函数
function formatJson (jsonObj, callback) {
    // 正则表达式匹配规则变量
    var reg = null;
    // 转换后的字符串变量
    var formatted = '';
    // 换行缩进位数
    var pad = 0;
    // 一个tab对应空格位数
    var PADDING = '    ';
    // json对象转换为字符串变量
    var jsonString = transitionJsonToString(jsonObj, callback);
    if (!jsonString) {
        return jsonString;
    }
    // 存储需要特殊处理的字符串段
    var _index = [];
    // 存储需要特殊处理的“再数组中的开始位置变量索引
    var _indexStart = null;
    // 存储需要特殊处理的“再数组中的结束位置变量索引
    var _indexEnd = null;
    // 将jsonString字符串内容通过\r\n符分割成数组
    var jsonArray = [];
    // 正则匹配到{,}符号则在两边添加回车换行
    jsonString = jsonString.replace(/([\{\}])/g, '\r\n$1\r\n');
    // 正则匹配到[,]符号则在两边添加回车换行
    jsonString = jsonString.replace(/([\[\]])/g, '\r\n$1\r\n');
    // 正则匹配到,符号则在两边添加回车换行
    jsonString = jsonString.replace(/(\,)/g, '$1\r\n');
    // 正则匹配到要超过一行的换行需要改为一行
    jsonString = jsonString.replace(/(\r\n\r\n)/g, '\r\n');
    // 正则匹配到单独处于一行的,符号时需要去掉换行，将,置于同行
    jsonString = jsonString.replace(/\r\n\,/g, ',');
    // 特殊处理双引号中的内容
    jsonArray = jsonString.split('\r\n');
    jsonArray.forEach(function (node, index) {
        // 获取当前字符串段中"的数量
        var num = node.match(/\"/g) ? node.match(/\"/g).length : 0;
        // 判断num是否为奇数来确定是否需要特殊处理
        if (num % 2 && !_indexStart) {
            _indexStart = index
        }
        if (num % 2 && _indexStart && _indexStart != index) {
            _indexEnd = index
        }
        // 将需要特殊处理的字符串段的其实位置和结束位置信息存入，并对应重置开始时和结束变量
        if (_indexStart && _indexEnd) {
            _index.push({
                start: _indexStart,
                end: _indexEnd
            })
            _indexStart = null
            _indexEnd = null
        }
    })
    // 开始处理双引号中的内容，将多余的"去除
    _index.reverse().forEach(function (item, index) {
        var newArray = jsonArray.slice(item.start, item.end + 1)
        jsonArray.splice(item.start, item.end + 1 - item.start, newArray.join(''))
    })
    // 奖处理后的数组通过\r\n连接符重组为字符串
    jsonString = jsonArray.join('\r\n');
    // 将匹配到:后为回车换行加大括号替换为冒号加大括号
    jsonString = jsonString.replace(/\:\r\n\{/g, ':{');
    // 将匹配到:后为回车换行加中括号替换为冒号加中括号
    jsonString = jsonString.replace(/\:\r\n\[/g, ':[');
    // 将上述转换后的字符串再次以\r\n分割成数组
    jsonArray = jsonString.split('\r\n');
    // 将转换完成的字符串根据PADDING值来组合成最终的形态
    jsonArray.forEach(function (item, index) {
        console.log(item)
        var i = 0;
        // 表示缩进的位数，以tab作为计数单位
        var indent = 0;
        // 表示缩进的位数，以空格作为计数单位
        var padding = '';
        if (item.match(/\{$/) || item.match(/\[$/)) {
            // 匹配到以{和[结尾的时候indent加1
            indent += 1
        } else if (item.match(/\}$/) || item.match(/\]$/) || item.match(/\},$/) || item.match(/\],$/)) {
            // 匹配到以}和]结尾的时候indent减1
            if (pad !== 0) {
                pad -= 1
            }
        } else {
            indent = 0
        }
        for (i = 0; i < pad; i++) {
            padding += PADDING
        }
        formatted += padding + item + '\r\n'
        pad += indent
    })
    // 返回的数据需要去除两边的空格
    return formatted.trim();
}