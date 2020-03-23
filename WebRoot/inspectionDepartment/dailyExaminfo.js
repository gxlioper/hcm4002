$(document).ready(function () {
	$('#exam_num').textbox('textbox').focus();  
	$('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    }); 
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    });
	
	$('#exam_num').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	chaxun();
        }
    });
		
	$('#statusss').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			
		}
	});
	getgroupuserGrid();	
	getexaminfoGrid();
	getExamitemList(0);
	
	$(".layout-panel-east .layout-button-right").click();
	$('.layout-expand-east .panel-body').html('收费项目明细');
	$('.layout-expand-east .panel-tool').hide();
	$(".layout-expand-east .panel-body").css({"font-weight":"bold","font-size":"16px","text-align":"center"});
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
		
		$('#levelss').combobox('clear');
		//显示部门
		$('#levelss').combobox({
			url : 'getCompanForDept.action?company_id='+id,
			editable : true, //不可编辑状态
			cache : false,
			panelHeight : '300',//自动高度适合
			valueField : 'id',
			textField : 'dep_Name',
			onLoadSuccess : function(data) {
			}
		});
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

function chaxun(){
	var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 var date_type = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datetimebox('getValue');
		 time2 = $("#end_date").datetimebox('getValue');
		 date_type = $('#date_type').combobox('getValue');
	 }
	 var user_name = undefined;
	 if($("#ck_custname")[0].checked){
		 user_name = $("#custname").val();
	 }
	 var searchphone = undefined;
	 if($("#ck_searchphone")[0].checked){
		 searchphone = $("#searchphone").val();
	 }
	 var status = undefined;
	 if($("#ck_status")[0].checked){
		 status = $('#statusss').combobox('getValue');
	 }
	 var examtype = undefined;
	 if($("#ck_examtype")[0].checked){
		 examtype = $('#examtype').combobox('getValue');
	 }
	 var searcsex = undefined;
	 if($("#ck_searcsex")[0].checked){
		 searcsex = $("#searcsex").combobox('getValue');
	 }
	 var arch_num = undefined;
	 if($("#ck_arch_num")[0].checked){
		 arch_num = $("#arch_num").val();
	 }
	 var id_num = undefined;
	 if($("#ck_id_num")[0].checked){
		 id_num = $("#id_num").val()
	 }
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
		 comid = $("#company_id").val();
	 }
	 
	 var levelss = undefined;
	 if($("#ck_levelss")[0].checked){
		 levelss = $("#levelss").combobox('getValue');
	 }
// 体检类别  健康  职业
    var apptype = undefined;
    if($("#ck_tjlb")[0].checked){
        apptype = $("#tjlb").combobox('getValue');
    }
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "s_join_date":time1,	
				 "e_join_date":time2,	
				 "user_name":user_name,
				 "status":status,	
				 "sex":searcsex,
				 "arch_num":arch_num,
				 "id_num":id_num,
				 "phone":searchphone,
				 "exam_type":examtype,
				 "level":levelss,
				 "dep_category":date_type,
			     "apptype":apptype
		 };
	$("#groupusershow").treegrid('load',model);
	getexaminfoGrid();
	getExamitemList(0);
}

 function getgroupuserGrid(){
	 var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 var date_type = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datetimebox('getValue');
		 time2 = $("#end_date").datetimebox('getValue');
		 date_type = $('#date_type').combobox('getValue');
	 }
	 var user_name = undefined;
	 if($("#ck_custname")[0].checked){
		 user_name = $("#custname").val();
	 }
	 var searchphone = undefined;
	 if($("#ck_searchphone")[0].checked){
		 searchphone = $("#searchphone").val();
	 }
	 var status = undefined;
	 if($("#ck_status")[0].checked){
		 status = $('#statusss').combobox('getValue');
	 }
	 var examtype = undefined;
	 if($("#ck_examtype")[0].checked){
		 examtype = $('#examtype').combobox('getValue');
	 }
	 var searcsex = undefined;
	 if($("#ck_searcsex")[0].checked){
		 searcsex = $("#searcsex").combobox('getValue');
	 }
	 var arch_num = undefined;
	 if($("#ck_arch_num")[0].checked){
		 arch_num = $("#arch_num").val();
	 }
	 var id_num = undefined;
	 if($("#ck_id_num")[0].checked){
		 id_num = $("#id_num").val()
	 }
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
		 comid = $("#company_id").val();
	 }
	 var levelss = undefined;
	 if($("#ck_levelss")[0].checked){
		 levelss = $("#levelss").combobox('getValue');
	 }

		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "s_join_date":time1,	
				 "e_join_date":time2,	
				 "user_name":user_name,
				 "status":status,	
				 "sex":searcsex,
				 "arch_num":arch_num,
				 "id_num":id_num,
				 "phone":searchphone,
				 "exam_type":examtype,
				 "level":levelss,
				 "dep_category":date_type
		 };
	 
	 $("#groupusershow").treegrid({
			 url:'getDilyExamInfoList.action',
			 dataType: 'json',
			 queryParams:model,
			 idField:'id',
			 treeField:'name',
			 frozenColumns: [[    
	                             {title: '体检团体', field: 'name', width: 300},
	                             {align:'center', title: '总人数', field: 'person_count', width: 80}
	                         ]],
			 columns:[[
			         {align:'center',"title":"缴费金额(元)","colspan":4},  
			         {align:'center',"title":"性别","colspan":2},
			         {align:'center',"title":"导引单","colspan":2},
			         {align:'center',"title":"总检","colspan":2},
			         {align:'center',"title":"打印报告","colspan":2}],
			         [{align:'center',field:'amount',title:'总金额合计',width:30},
			          {align:'center',field:'amount_pj',title:'平均金额',width:30,"formatter":fn_amount_pj},
			          {align:'center',field:'personal_pay',title:'个人金额合计',width:30},
			          {align:'center',field:'team_pay',title:'团体金额合计',width:30},
			          {align:'center',field:'man_count',title:'男性',width:20},
			          {align:'center',field:'woman_count',title:'女性',width:20},
			          {align:'center',field:'w_huishou',title:'未回收',width:20},
			          {align:'center',field:'y_huishou',title:'已回收',width:20},
			          {align:'center',field:'w_zongjian',title:'未总检',width:20},
			          {align:'center',field:'y_zongjian',title:'已总检',width:20},
			          {align:'center',field:'w_report',title:'未打印',width:20},
			          {align:'center',field:'y_report',title:'已打印',width:20}
			]],
	    	singleSelect:false,
		    fitColumns:true,
		    rownumbers:true,
		    onDblClickRow : function(row) {
		    	var exam_num = undefined;
			   	 if($("#ck_exam_num")[0].checked){
			   		 exam_num =  $("#exam_num").val();
			   	 }
			   	 var time1 = undefined,time2 = undefined;
			   	 var date_type = undefined;
			   	 if($("#ck_date")[0].checked){
			   		 time1 = $("#start_date").datebox('getValue');
			   		 time2 = $("#end_date").datebox('getValue');
			   		 date_type = $('#date_type').combobox('getValue');
			   	 }
			   	 var user_name = undefined;
			   	 if($("#ck_custname")[0].checked){
			   		 user_name = $("#custname").val();
			   	 }
			   	 var searchphone = undefined;
			   	 if($("#ck_searchphone")[0].checked){
			   		 searchphone = $("#searchphone").val();
			   	 }
			   	 var status = undefined;
			   	 if($("#ck_status")[0].checked){
			   		 status = $('#statusss').combobox('getValue');
			   	 }
			   	 var examtype = undefined;
			   	 if($("#ck_examtype")[0].checked){
			   		 examtype = $('#examtype').combobox('getValue');
			   	 }
			   	 var searcsex = undefined;
			   	 if($("#ck_searcsex")[0].checked){
			   		 searcsex = $("#searcsex").combobox('getValue');
			   	 }
			   	 var arch_num = undefined;
			   	 if($("#ck_arch_num")[0].checked){
			   		 arch_num = $("#arch_num").val();
			   	 }
			   	 var id_num = undefined;
			   	 if($("#ck_id_num")[0].checked){
			   		 id_num = $("#id_num").val()
			   	 }
			   	 var comid = 0;
			   	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
			   		 comid = $("#company_id").val();
			   	 }
			   	 var levelss = undefined;
			   	 if($("#ck_levelss")[0].checked){
			   		 levelss = $("#levelss").combobox('getValue');
			   	 }
			   	 var customer_type_id = 0;
			   	 if(row.id == -3){ //全部体检者
			   		 
			   	 }else if(row.id == -2){//全部个人
			   		examtype = 'G';
			   	 }else if(row.id == -1){//全部单位
			   		examtype = 'T';
			   	 }else if(row.id == 1){//VIP 
			   		examtype = 'G';
			   		customer_type_id = 2;
			   	 }else if(row.id == 2){//非VIP
			   		examtype = 'G';
			   		customer_type_id = 3;
			   	 }else{//一个单位
			   		comid = row.id;
			   	 }
		   		 var model={
		   				 "company_id":comid,
		   				 "exam_num":exam_num,
		   				 "s_join_date":time1,	
		   				 "e_join_date":time2,	
		   				 "user_name":user_name,
		   				 "status":status,	
		   				 "sex":searcsex,
		   				 "arch_num":arch_num,
		   				 "id_num":id_num,
		   				 "phone":searchphone,
		   				 "exam_type":examtype,
		   				 "level":levelss,
		   				 "dep_category":date_type,
		   				 "customer_type_id":customer_type_id
		   		 };
		    	
		    	$("#examinfolisthow").datagrid('load',model);
		    	$('#tjgc_lb').accordion('select',1);
			},
		    fit:true
	     });
	}
 function fn_amount_pj(val,row){
	 var amount = 0;
	 if(row.person_count != 0){
		 amount = decimal(Number(row.amount)/Number(row.person_count),2);
		 row.batch_id = amount;
	 }
	 return amount;
 }
 function getexaminfoGrid(){
	 var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 var date_type = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datetimebox('getValue');
		 time2 = $("#end_date").datetimebox('getValue');
		 date_type = $('#date_type').combobox('getValue');
	 }
	 var user_name = undefined;
	 if($("#ck_custname")[0].checked){
		 user_name = $("#custname").val();
	 }
	 var searchphone = undefined;
	 if($("#ck_searchphone")[0].checked){
		 searchphone = $("#searchphone").val();
	 }
	 var status = undefined;
	 if($("#ck_status")[0].checked){
		 status = $('#statusss').combobox('getValue');
	 }
	 var examtype = undefined;
	 if($("#ck_examtype")[0].checked){
		 examtype = $('#examtype').combobox('getValue');
	 }
	 var searcsex = undefined;
	 if($("#ck_searcsex")[0].checked){
		 searcsex = $("#searcsex").combobox('getValue');
	 }
	 var arch_num = undefined;
	 if($("#ck_arch_num")[0].checked){
		 arch_num = $("#arch_num").val();
	 }
	 var id_num = undefined;
	 if($("#ck_id_num")[0].checked){
		 id_num = $("#id_num").val()
	 }
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
		 comid = $("#company_id").val();
	 }
	 var levelss = undefined;
	 if($("#ck_levelss")[0].checked){
		 levelss = $("#levelss").combobox('getValue');
	 }

     var apptype = undefined;
     if($("#ck_tjlb")[0].checked){
         apptype = $("#tjlb").combobox('getValue');
     }
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "s_join_date":time1,	
				 "e_join_date":time2,	
				 "user_name":user_name,
				 "status":status,	
				 "sex":searcsex,
				 "arch_num":arch_num,
				 "id_num":id_num,
				 "phone":searchphone,
				 "exam_type":examtype,
				 "level":levelss,
				 "dep_category":date_type,
			     "apptype":apptype
		 };
	     $("#examinfolisthow").datagrid({
		 url:'getDirectorExamInfoList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'exam_num',title:tjhname,width:25,sortable:true},
		  //{align:'center',field:'arch_num',title:'档案号',width:18},
		 	{align:'center',field:'id_num',title:'身份证号',width:25},
		    {align:'center',field:'exam_types',title:'体检类型',width:15},	
		 	{align:'center',field:'user_name',title:'姓名',width:20,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:20},		 
		 	{align:'center',field:'address',title:'地址',width:25},
		 	{align:'center',field:'company',title:'单位',width:20,sortable:true},
		 	{align:'center',field:'set_name',title:'套餐',width:20},
		 	{align:'center',field:'register_date',title:'登记日期',width:20},
		 	{align:'center',field:'join_date',title:'体检日期',width:20},
		 	{align:'center',field:'personal_pay',title:'个人金额合计',width:20},
	        {align:'center',field:'team_pay',title:'团体金额合计',width:20},
	        {align:'center',field:'pay_amount',title:'已收费',width:15},
	        {align:'center',field:'no_pay_amount',title:'未收费',width:15},
//	        {align:'center',field:'is_guide_backs',title:'导引单回收',width:20},
//	        {align:'center',field:'is_need_guides',title:'导引单打印',width:20},
//	        {align:'center',field:'counter_checks',title:'复检',width:10}
	        {align:'center',field:'introducer',title:'介绍人',width:10,sortable:true}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
	    	onDblClickRow : function(index, row) {	
	    		getExamitemList(row.exam_num);
	    		$('.layout-expand-east .panel-body').click();
			},
			onClickRow :function(index, row){
				
			},
			rownumbers:true,
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true
	});
	}

 function getExamitemList(examinfo_id){
	 var model={"exam_num":examinfo_id
	 };
     $("#exam_item_list").datagrid({
	 url:'getDirectorItemStatus.action',
	 dataType: 'json',
	 queryParams:model,
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
	 columns:[[
	    {align:'center',field:'dep_name',title:'科室',width:10},
	 	{align:'center',field:'item_name',title:'项目名称',width:25},
	    {align:'center',field:'exam_indicators',title:'计费类型',width:15},
	    {align:'center',field:'personal_pay',title:'个人金额',width:15},
        {align:'center',field:'team_pay',title:'团体金额',width:15},
	 	{align:'center',field:'pay_statuss',title:'付费状态',width:15},
	 	{align:'center',field:'exam_statuss',title:'检查状态',width:20},
	 	{align:'center',field:'exam_date',title:'检查日期',width:20},
	 	{align:'center',field:'exam_doctor_name',title:'检查医生',width:20}	 	
	 	]],	    	
    	onLoadSuccess:function(value){ 
    		$("#datatotal").val(value.total);
    	},
    	onDblClickRow : function(index, row) {
    		if(row.exam_status == 'Y'){
	    		$("#dlg-edit").dialog({//width: 1200,height: 515,closed: true,cache: false,modal: true
	    			width: 700,
	    			height: 515,
	    			closed: true,
	    			cache: false,
	    			modal: true,
	    			title:row.item_name+'检查结果',
	    			href:'getDirectorItemResult.action?charging_item_ids='+row.item_code+'&exam_num='+examinfo_id+'&dep_category='+row.dep_category
	    		});
	    		$("#dlg-edit").dialog("open");
    		}
		},
		rownumbers:true,
    	singleSelect:true,
	    collapsible:true,
		pagination: false,
	    fitColumns:true,
	    fit:true,
	    striped:true
     });
 }
 //概览数据导出
 function daochu(){
	var temp = $("#groupusershow").treegrid('getData');
	var filelist = new Array();
 	var obj = new Object();
 	obj.name = "体检团体";
 	obj.person_count = "总人数";
 	obj.amount = "缴费金额(元)";
 	obj.amount_pj = "";
 	obj.personal_pay = "";
 	obj.team_pay = "";
 	obj.man_count = "性别";
 	obj.woman_count = "";
 	obj.w_huishou = "导检单";
 	obj.y_huishou = "";
 	obj.w_zongjian="总检"
 	obj.y_zongjian = ""
 	obj.w_report = "打印报告";
 	obj.y_report = "";
 	filelist.push(obj);
 	
 	obj = new Object();
 	obj.name = "";
 	obj.person_count = "";
 	obj.amount = "总金额合计";
 	obj.amount_pj = "平均金额";
 	obj.personal_pay = "个人金额合计";
 	obj.team_pay = "团体金额合计";
 	obj.man_count = "男性";
 	obj.woman_count = "女性";
 	obj.w_huishou = "未回收";
 	obj.y_huishou = "已回收";
 	obj.w_zongjian="未总检"
 	obj.y_zongjian = "已总检"
 	obj.w_report = "未打印";
 	obj.y_report = "已打印";
 	filelist.push(obj);
    for(a=0;a<temp.length;a++){
    	obj = new Object();
    	obj.name = temp[a].name;
        obj.person_count = temp[a].person_count;
        obj.amount = temp[a].amount.toString();
        obj.amount_pj = temp[a].batch_id;
        obj.personal_pay = temp[a].personal_pay.toString();
        obj.team_pay = temp[a].team_pay.toString();
        obj.man_count = temp[a].man_count;
        obj.woman_count = temp[a].woman_count;
        obj.w_huishou = temp[a].w_huishou;
        obj.y_huishou = temp[a].y_huishou;
        obj.w_zongjian = temp[a].w_zongjian;
        obj.y_zongjian = temp[a].y_zongjian;
        obj.w_report = temp[a].w_report;
        obj.y_report = temp[a].y_report;
        filelist.push(obj);
	    if(temp[a].children != undefined){
	    	for(i=0;i<temp[a].children.length;i++){
	    		var children1 = temp[a].children[i];
	    		obj = new Object();
	     		obj.name = children1.name;
	    	    obj.person_count = children1.person_count;
	    	    obj.amount = children1.amount.toString();
	    	    obj.amount_pj = children1.batch_id;
	    	    obj.personal_pay = children1.personal_pay.toString();
	    	    obj.team_pay = children1.team_pay.toString();
	    	    obj.man_count = children1.man_count;
	    	    obj.woman_count = children1.woman_count;
	    	    obj.w_huishou = children1.w_huishou;
	    	    obj.y_huishou = children1.y_huishou;
	    	    obj.w_zongjian = children1.w_zongjian;
	    	    obj.y_zongjian = children1.y_zongjian;
	    	    obj.w_report = children1.w_report;
	    	    obj.y_report = children1.y_report;
	    	    filelist.push(obj);
	    	    
	    	    if(children1.children != undefined){
	    	    	for(j=0;j<children1.children.length;j++){
	    	    		var children2 = children1.children[j];
	    	    		obj = new Object();
	    	     		obj.name = children2.name;
	    	    	    obj.person_count = children2.person_count;
	    	    	    obj.amount = children2.amount.toString();
	    	    	    obj.amount_pj = children2.batch_id;
	    	    	    obj.personal_pay = children2.personal_pay.toString();
	    	    	    obj.team_pay = children2.team_pay.toString();
	    	    	    obj.man_count = children2.man_count;
	    	    	    obj.woman_count = children2.woman_count;
	    	    	    obj.w_huishou = children2.w_huishou;
	    	    	    obj.y_huishou = children2.y_huishou;
	    	    	    obj.w_zongjian = children2.w_zongjian;
	    	    	    obj.y_zongjian = children2.y_zongjian;
	    	    	    obj.w_report = children2.w_report;
	    	    	    obj.y_report = children2.y_report;
	    	    	    filelist.push(obj);
	    	    	}
	    	    }
	    	}
	    }
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
				$.messager.alert("操作提示", "导出excel出错","error");
			}
		});
 }
 
 function daochuxiangxi(){
	var options = $("#examinfolisthow").datagrid('options');
	var queryParams = options.queryParams;
	var sortName = options.sortName;
	var order = options.sortOrder;
	if(sortName != null){
		queryParams.sort = sortName;
	}
	if(order != null){
		queryParams.order = order;
	}
	if(queryParams.arch_num == undefined){
		queryParams.arch_num = '';
	}
	if(queryParams.s_join_date == undefined){
		queryParams.s_join_date = '';
	}
	if(queryParams.e_join_date == undefined){
		queryParams.e_join_date = '';
	}
	if(queryParams.user_name == undefined){
		queryParams.user_name = '';
	}
	if(queryParams.status == undefined){
		queryParams.status = '';
	}
	if(queryParams.sex == undefined){
		queryParams.sex = '';
	}
	if(queryParams.id_num == undefined){
		queryParams.id_num = '';
	}
	if(queryParams.exam_type == undefined){
		queryParams.exam_type = '';
	}
	if(queryParams.level == undefined){
		queryParams.level = '';
	}
	if(queryParams.dep_category == undefined){
		queryParams.dep_category = '';
	}
	if(queryParams.customer_type_id == undefined){
		queryParams.customer_type_id = '';
	}
	if(queryParams.phone == undefined){
		queryParams.phone = '';
	}

	window.location.href='dailyExamInfoExportExcel.action?company_id='+queryParams.company_id+'&s_join_date='+queryParams.s_join_date+'&e_join_date='+queryParams.e_join_date
	+'&user_name='+queryParams.user_name+'&status='+queryParams.status+'&sex='+queryParams.sex+'&arch_num='+queryParams.arch_num+'&id_num='+queryParams.id_num+'&phone='+queryParams.phone
	+'&exam_type='+queryParams.exam_type+'&level='+queryParams.level+'&dep_category='+queryParams.dep_category+'&customer_type_id='+queryParams.customer_type_id;
 }