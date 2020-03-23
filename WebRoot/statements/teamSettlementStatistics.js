$(document).ready(function () {
	getgroupuserGrid();
	
	$('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    }); 
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    });
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

function getgroupuserGrid(){
	 var time1 = $("#start_date").datebox('getValue');
	 var time2 = $("#end_date").datebox('getValue');
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != ''){
		 comid = $("#company_id").val();
	 }
		var model={
				 "company_id":comid,
				 "s_join_date":time1,	
				 "e_join_date":time2	
		 };
	     $("#groupusershow").datagrid({
		 url:'getTeamSettlementStatisticsList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'com_name',title:'单位名称',width:25},
		 	{align:'center',field:'batch_name',title:'批次名称',width:15},
		    {align:'center',field:'tjrs',title:'总人数',width:15},	
		 	{align:'center',field:'jsrs',title:'已结算人数',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		getExamitemList(0);
	    	},
	    	onDblClickRow : function(index, row) {	
	    		getExamitemList(row.batch_id);
	    		batch_ids = row.batch_id;
	    		pay_status = '';
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
var batch_ids = 0;var pay_status;
 function getExamitemList(batch_id){
	 var model={"batch_id":batch_id,"pay_status":pay_status};
	 $("#examinfoshow").datagrid({
		 url:'getTeamSettlementExamList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'exam_num',title:'体检编号',width:20,sortable:true},
		 	{align:'center',field:'join_date',title:'体检日期',width:20,sortable:true},
		    {align:'center',field:'user_name',title:'姓名',width:15,sortable:true},	
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:15,sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',width:10,sortable:true},
		 	{align:'center',field:'pay_status',title:'结算标志',width:15,sortable:true},
		 	{align:'center',field:'totalamt',title:'结算金额',width:15,sortable:true},
		 	{align:'center',field:'isPrePay',title:'是否预结算',width:15},
		 	{align:'center',field:'isnotPay',title:'是否含弃检',width:15},
		 	{align:'center',field:'statuss',title:'体检状态',width:15},
		 	{align:'center',field:'set_name',title:'体检套餐',width:20}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
			rownumbers:true,
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true,
		    toolbar:toolbar
	});
 }
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
	 		text:'未结算',
      		width:90,
    		iconCls:'icon-check',
    		handler:function(){
    			pay_status = 'N';
    			getExamitemList(batch_ids);
    		}
    },{
 		text:'已结算',
  		width:90,
		iconCls:'icon-check',
		handler:function(){
			pay_status = 'Y';
			getExamitemList(batch_ids);
		}
    },{
		text:'数据导出',
		width:90,
		iconCls:'icon-check',
		handler:function(){
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
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			var model={
				"batch_id":batch_ids,
				"pay_status":pay_status,
				"page":1,
				"rows":10000,
				"sort":sort,
				"order":order
			};
			$.ajax({
				url : 'getTeamSettlementExamList.action',
				data : model,
				type : "post",//数据发送方式   
				success : function(data) {
					var temp = eval('('+data+')');
					if(temp.rows.length == 0){
						$(".loading_div").remove();
						$.messager.alert("操作提示", "没有需要导出的人员信息!","error");
						return;
					}
			    	var filelist = new Array();
			    	var obj = new Object();
			    	obj.exam_num = "体检号";
			    	obj.join_date = "体检日期";
			    	obj.user_name = "姓名";
			    	obj.sex = "性别";
			    	obj.age = "年龄";
			    	obj.phone = "电话";
			    	obj.is_marriage = "婚否";
			    	obj.pay_status = "结算标志"
			    	obj.totalamt = "结算金额";
			    	obj.isPrePay = "是否预结算";
			    	obj.isnotPay = "是否含弃检";
			    	obj.statuss = "体检状态";
			    	obj.set_name = "体检套餐";
			    	filelist.push(obj);
			    	
			    	for(i=0;i<temp.rows.length;i++){
			    		obj = new Object();
			    		obj.exam_num = temp.rows[i].exam_num;
				    	obj.join_date = temp.rows[i].join_date;
				    	obj.user_name = temp.rows[i].user_name;
				    	obj.sex = temp.rows[i].sex;
				    	obj.age = temp.rows[i].age;
				    	obj.phone = temp.rows[i].phone;
				    	obj.is_marriage = temp.rows[i].is_marriage;
				    	obj.pay_status = temp.rows[i].pay_status;
				    	obj.totalamt = temp.rows[i].totalamt;
				    	obj.isPrePay = temp.rows[i].isPrePay;
				    	obj.isnotPay = temp.rows[i].isnotPay;
				    	obj.statuss = temp.rows[i].statuss;
				    	obj.set_name = temp.rows[i].set_name;
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
							$.messager.alert("操作提示", "导出excel出错","error");
						}
					});
				},
				error : function() {
					$.messager.alert("操作提示", "导出excel出错","error");
				}
			});
	    }
	}];
