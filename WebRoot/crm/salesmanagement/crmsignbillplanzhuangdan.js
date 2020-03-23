$(document).ready(function () {
	getCompany();
	getCrmSignBillPlan();
	getSignBillPlanZhuangDanList();
});
/**
 * 清空查询
 */
function empty(){
	 $('#sign_name').val('');
	 getSignBillPlanZhuangDanList();
	 
}
function getCompany(){
	 $.ajax({
		 url : 'getCompanyInfoFromZhuangdan.action',
		data : {company_id:$('#company_ids').val()},
		type : "post",
		success : function(data) {
			if(data!=null){
				var datastr=eval('('+data+')');
				$("#com_names").html(datastr[0].com_Name);
				$("#com_types").html(datastr[0].com_Type);
				$("#economiccodes").html(datastr[0].economiccode);
				$("#comsizecodes").html(datastr[0].comsizecode);
				$("#addresss").html(datastr[0].address);
				$("#industrycodes").html(datastr[0].industrycode);
				$("#areacodes").html(datastr[0].areacode);
			}
		}
	 });
}
function getCrmSignBillPlan(){
	$.ajax({
		 url : 'getCrmSignBillPlanFromZhuangdan.action',
		data : {ids:$('#ids').val()},
		type : "post",
		success : function(data) {
			if(data!=null){
				var datastr=eval('('+data+')');
				$("#ck_sign_names").html(datastr[0].sign_name);
				$("#ck_sign_pingyings").html(datastr[0].sign_pingying);
				$("#ck_sign_types").html(datastr[0].sign_type);
				$("#ck_sign_nums").html(datastr[0].sign_num);
				$("#ck_sign_counts").html(datastr[0].sign_count);
				$("#ck_sign_years").html(datastr[0].sign_year);
				$("#ck_old_new_types").html(datastr[0].old_new_type);
				$("#ck_customer_types").html(datastr[0].customer_type);
				$("#ck_protect_dates").html(datastr[0].protect_date);
				$("#ck_sign_persions").html(datastr[0].sign_persion);
				$("#ck_sign_amounts").html(datastr[0].sign_amount);
				$("#ck_sign_dates").html(datastr[0].sign_date);
				$("#ck_end_dates").html(datastr[0].end_date);
				$("#ck_abort_dates").html(datastr[0].abort_date);
				$("#ck_sign_statusss").html(datastr[0].sign_statuss);
				$("#ck_track_progressss").html(datastr[0].track_progresss);
				$("#ck_track_times").html(datastr[0].track_time);
				$("#ck_creaters").html(datastr[0].creater);
				$("#ck_create_times").html(datastr[0].create_time);
				getcompanyCons_ck(datastr[0].company_id);
			}				
		},
	 });
}
function updateSignBillPlanZhuangDan(){
	$.messager.confirm('提示信息','确定将该签单计划设置为已撞单',function(r){
	 	if(r){
	 		$('#dlg-edit').dialog({    
	    	    title: '填写原因', 
	    	    href: 'saveBatchPlanLogPage.action?project_type=2&id='+$('#ids').val(),
	    	    width: 500,    
	    	    height: 250, 
	    	    closed: true,
	    	    cache: false,
	    	    modal:true
	    	});
	    	$('#dlg-edit').dialog('open');
	 	 }
	 });
}

function updateSignBillPlanWeiZhuangDan(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $.ajax({
		 url : 'updateSignBillPlanZhuangDan.action',
		data : {ids:$('#ids').val(),updatestatus:'3'},
		type : "post",
		success : function(data) {
			$(".loading_div").remove();
			var _parentWin =  window.opener ; 
			_parentWin.getSignBillPlanList(); // 主窗口getgroupGrid();刷新
			setTimeout("window.close()", 10000);
				$.messager.confirm('提示信息',data+',10秒钟后该页面将自动关闭,是否立即关闭',function(r){
					if(r){
						window.close()
					}
				})
			
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
			$(".loading_div").remove();
		}
	 });
}
function compareDate(DateOne, DateTwo) {
	  var OneMonth = DateOne.substring(5, DateOne.lastIndexOf("-"));
	  var OneDay = DateOne.substring(DateOne.length, DateOne.lastIndexOf("-") + 1);
	  var OneYear = DateOne.substring(0, DateOne.indexOf("-"));
	  var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf("-"));
	  var TwoDay = DateTwo.substring(DateTwo.length, DateTwo.lastIndexOf("-") + 1);
	  var TwoYear = DateTwo.substring(0, DateTwo.indexOf("-"));
	  if (Date.parse(OneMonth + "/" + OneDay + "/" + OneYear) > Date.parse(TwoMonth + "/" + TwoDay + "/" + TwoYear)) {
	    return true;
	  } else {
	    return false;
	  }
	}
function editabort_dates(str){
	var time1 =$('#ck_protect_dates').html();
	if(str=='1'){
		var zhuangdan=$('#ck_sign_statusss').html();
		if(zhuangdan=='撞单'){
			$.messager.alert('提示信息', '该签单计划已撞单，无法修改截止日期');
		}else{
			var time =$('#ck_abort_dates').html();
			$('#ck_abort_dates').css("display","none");
			$('#showdates').datebox({    
			    value: time,    
			    required: true,    
			    showSeconds: true,
			    width:160
			});
			$('#showdatesdt').css("display","block");
			$('#xiugai').val('完成');
		}
	}else if(str=='0'){
		var v = $('#showdates').datebox('getValue');
		if(compareDate(time1,v)){
			$.messager.alert('提示信息', '保护截止日期小于保护开始日期了！', 'error');
		}else{
			$.ajax({
				 url : 'updateAbortDate.action',
				data : {ids:$('#ids').val(),abort_date:v},
				type : "post",
				success : function(data) {
					$('#showdatesdt').css("display","none");
					$('#ck_abort_dates').html(v.substring(0,10));
					$('#ck_abort_dates').css("display","block");
					$('#xiugai').val('修改截止日期');
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			 });
		}
	}
}
/**
 * 获取单位联系人
 * @param id
 * @returns
 */
function getcompanyCons_ck(id){
	var model = {"company_id":id};
	$("#ck_contacts_lists").datagrid({
		url: 'getCompanyContactsList.action',
		queryParams: model,
		rownumbers:false,
		columns:[[{align:"center",field:"contacts_name","title":"姓名","width":10},
		      {align:"center",field:"positions","title":"职务","width":10},
		      {align:"center",field:"important_levels","title":"重要级别","width":12},
              {align:"center",field:"phone","title":"办公电话","width":15},
     		  {align:'center',field:"telephone","title":"手机","width":15},
     		  {align:"center",field:"email","title":"电子邮件","width":15},
     		  {align:"center",field:"id_num","title":"身份证号","width":15},
     		  {align:"center",field:"personal_interests","title":"个人爱好","width":35},
     		  {align:"center",field:"remarke","title":"备注","width":20}
     		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		striped:true
	});
}
/**
 * 获取撞单List
 * @returns
 */
function getSignBillPlanZhuangDanList(){
	$("#crmsignbillplanzhuangdan").datagrid({
		url: 'getSignBillPlanZhuangDanList.action',
		queryParams: {ids:$('#ids').val(),
			sign_names:$('#sign_name').val(),
			company_id:$('#company_ids').val()},
		rownumbers:false,
		pageSize: 4,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		columns:[[
			  {align:"center",field:"id","title":"id","width":15,hidden:true},
		      {align:"center",field:"track_progresss","title":"跟踪进度","width":15},
		      {align:"center",field:"protect_date","title":"保护开始日期","width":20},
		      {align:"center",field:"abort_date","title":"保护截止日期","width":20},
		      {align:"center",field:"sign_statuss","title":"状态","width":10},
              {align:"center",field:"sign_num","title":"编号","width":15},
     		  {align:'center',field:"sign_name","title":"名称","width":10},
     		  {align:"center",field:"com_name","title":"单位","width":25},
     		  {align:"center",field:"sign_year","title":"年度","width":10},
     		  {align:"center",field:"sign_type","title":"签单类型","width":12},
       		 {align:"center",field:"sign_persion","title":"估算人数","width":12},
       		 {align:"center",field:"sign_amount","title":"估算金额","width":12},
       		 {align:"center",field:"creater","title":"负责人","width":12},
     		  {align:"center",field:"f_ck","title":"查看","width":10,"formatter":f_ck}
     		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		fitColumns:true,
		striped:true,
		pagination: true
	});
}

function f_ck(value,row,index){
	var str = '<a href=\"javascript:f_ck_sign('+index+','+row.company_id+',\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-export\" alt=\"查看\" /></a>';
	return str;
}

var row_index = 0;
function f_ck_sign(index,company_id,id){
	row_index = index;
	$('#dlg-edit').dialog({    
	    title: '查看签单计划', 
	    href: 'getSignBillPlanLookPage.action?company_id='+company_id+'&flag=1&id='+id,
	    width: 1000,    
	    height: 500, 
	    closed: true,
	    cache: false,
	    modal:true
	});
	$('#dlg-edit').dialog('open');
}