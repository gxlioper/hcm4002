$(document).ready(function(){
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

var add_i = 0;
function add_value(){
	if($("#zuhetj").children('dl').length >= 20){
		$.messager.alert("操作提示",'目前最大支持20个组合条件!', "error");
		return;
	}
	
	add_i ++;
	var str = '<dl id="dl_'+add_i+'" data="'+add_i+'">'
	    +'<dt><select id="charging_'+add_i+'" class="easyui-combobox" data-options="height:26,width:180,panelHeight:200,valueField: \'id\',textField: \'item_name\',hasDownArrow:false,mode:\'remote\',url:\'getChargingItemListByq.action?add_i='+add_i+'\'"></select></dt>'
		+'<dt><select id="item_'+add_i+'" class="easyui-combobox" data-options="height:26,width:180,panelHeight:200,valueField: \'id\',textField: \'item_name\',url:\'getExaminationItemListByq.action\'"></select></dt>'
		+'<dt><select id="condition_'+add_i+'" class="easyui-combobox" data-options="height:26,width:60,panelHeight:\'auto\'"></select></dt>'
		+'<dt><input id="result_'+add_i+'" class="easyui-textbox"  type="text" data-options="height:26,width:120"/></dt>'
		+'<dt><input type="button" value="-" onclick="del_value(this);" style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/></dt>'
	    +'</dl>';
	$("#zuhetj").append(str);
	$.parser.parse('#dl_'+add_i);
	
	$("#charging_"+add_i).combobox({
		onSelect:function (data){
			$("#item_"+data.item_seq).combobox('clear');
			$("#item_"+data.item_seq).combobox('reload','getExaminationItemListByq.action?charging_id='+data.id+'&add_i='+data.item_seq+'&q=');
			$("#condition_"+data.item_seq).combobox('clear');
			$('#dl_'+data.item_seq).children('dt').eq(3).html('<input id="result_'+data.item_seq+'" class="easyui-textbox"  type="text" data-options="height:26,width:120"/>');
			$.parser.parse($('#dl_'+data.item_seq).children('dt').eq(3));
			$("#result_"+data.item_seq).textbox('setValue','');
		}
	});
	$("#item_"+add_i).combobox({
		onSelect:function (data){
			if(data.item_category == '数字型'){
				$("#condition_"+data.seq_code).combobox({
					valueField: 'value',
					textField: 'text',
					data: [{
						value: '>',
						text: '大于'
					},{
						value: '>=',
						text: '大于等于'
					},{
						value: '<',
						text: '小于'
					},{
						value: '<=',
						text: '小于等于'
					},{
						value: '=',
						text: '等于'
					}],
					onLoadSuccess:function(){
						$("#condition_"+data.seq_code).combobox('setValue','>');
					}
				});
				$('#dl_'+data.seq_code).children('dt').eq(3).html('<input id="result_'+data.seq_code+'" class="easyui-numberbox"  type="text" data-options="height:26,width:120"/>');
				$.parser.parse($('#dl_'+data.seq_code).children('dt').eq(3));
			}else{
				$("#condition_"+data.seq_code).combobox({
					valueField: 'value',
					textField: 'text',
					data: [{
						value: 'in',
						text: '包含'
					},{
						value: 'not in',
						text: '不包含'
					}],
					onLoadSuccess:function(){
						$("#condition_"+data.seq_code).combobox('setValue','in');
					}
				});
				$('#dl_'+data.item_seq).children('dt').eq(3).html('<input id="result_'+data.item_seq+'" class="easyui-textbox"  type="text" data-options="height:26,width:120"/>');
				$.parser.parse($('#dl_'+data.item_seq).children('dt').eq(3));
			}
		}
	});
//	$("#condition_"+add_i).combobox({
//		valueField: 'value',
//		textField: 'text',
//		data: [{
//			value: '>',
//			text: '大于'
//		},{
//			value: '>=',
//			text: '大于等于'
//		},{
//			value: '<',
//			text: '小于'
//		},{
//			value: '<=',
//			text: '小于等于'
//		},{
//			value: '=',
//			text: '等于'
//		},{
//			value: 'in',
//			text: '包含'
//		},{
//			value: 'not in',
//			text: '不包含'
//		}]
//	});
}

function del_value(obj){
	$(obj).parent().parent().remove();
}

var add_i1 = 0;
function add_value1(){
	if(($("#zuhetj").children('dl').length+$("#zuhetj1").children('dl').length) >= 20){
		$.messager.alert("操作提示",'目前最大支持20个组合条件!', "error");
		return;
	}
	
	add_i1 ++;
	var str = '<dl id="xdl_'+add_i1+'" data="'+add_i1+'">'
	    +'<dt><select id="xcharging_'+add_i1+'" class="easyui-combobox" data-options="height:26,width:265,panelHeight:200,valueField: \'id\',textField: \'item_name\',hasDownArrow:false,mode:\'remote\',url:\'getChargingItemListByq.action?add_i='+add_i1+'\'"></select></dt>'
		+'<dt><select id="xitem_'+add_i1+'" class="easyui-combobox" data-options="height:26,width:265,panelHeight:200,valueField: \'id\',textField: \'item_name\',url:\'getExaminationItemListByq.action\'"></select></dt>'
		+'<dt><input type="button" value="-" onclick="del_value(this);" style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/></dt>'
	    +'</dl>';
	$("#zuhetj1").append(str);
	$.parser.parse('#xdl_'+add_i1);
	
	$("#xcharging_"+add_i1).combobox({
		onSelect:function (data){
			$("#xitem_"+data.item_seq).combobox('clear');
			$("#xitem_"+data.item_seq).combobox('reload','getExaminationItemListByq.action?charging_id='+data.id+'&add_i='+data.item_seq+'&q=');
		}
	});
}

function getgroupuserGrid(){
	if(($("#zuhetj").children('dl').length+$("#zuhetj1").children('dl').length) <= 0){
		$.messager.alert("操作提示",'请选择组合条件!', "error");
		return;
	}
	 var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").textbox('getValue');
	 }
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#time1").datebox('getValue');
		 time2 = $("#time2").datebox('getValue');
	 }
	 var user_name = undefined;
	 if($("#ck_custname")[0].checked){
		 user_name = $("#custname").textbox('getValue');
	 }
	 var min_age = -1;max_age = -1;
	 if($("#ck_age")[0].checked){
		 min_age = $("#min_age").numberbox('getValue');
		 max_age = $("#max_age").numberbox('getValue');
	 }
	 var sex = undefined;
	 if($("#ck_sex")[0].checked){
		 sex = $('#sex').combobox('getValue');
	 }
	 var examtype = undefined;
	 if($("#ck_examtype")[0].checked){
		 examtype = $('#examtype').combobox('getValue');
	 }
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
		 comid = $("#company_id").val();
	 }
	 var condition = new Array();
	 
	 var colnum = new Array();
	 colnum.push({align:'center',field:'exam_num',title:'体检号',width:100,sortable:true,upload:'exam_num'});
	 colnum.push({align:'center',field:'id_num',title:'身份证号',width:120,upload:'id_num'});
	 colnum.push({align:'center',field:'exam_types',title:'体检类型',width:80,upload:'exam_types'});
	 colnum.push({align:'center',field:'user_name',title:'姓名',width:100,sortable:true,upload:'user_name'});
	 colnum.push({align:'center',field:'sex',title:'性别',width:80,sortable:true,upload:'sex'});
	 colnum.push({align:'center',field:'age',title:'年龄',width:80,sortable:true,upload:'age'});
	 colnum.push({align:'center',field:'phone',title:'电话',width:120,upload:'phone'});
	 colnum.push({align:'center',field:'company',title:'单位',width:150,upload:'company'});
	 colnum.push({align:'center',field:'join_date',title:'体检日期',width:150,upload:'join_date'});
	 
	 var obj = $("#zuhetj").children('dl');
	 var data_num = 1;
	 for(i=0;i<obj.length;i++){
		 var id = $(obj[i]).attr('data');
		 if($("#charging_"+id).combobox('getValue') == ''){
			 $.messager.alert("操作提示",'请选择查询结果符合条件的项目第'+(i+1)+'行组合条件的收费项目！', "error");
			 return;
		 }else if($("#item_"+id).combobox('getValue') == ''){
			 $.messager.alert("操作提示",'请选择查询结果符合条件的项目第'+(i+1)+'行组合条件的检查项目！', "error");
			 return;
		 }else if($("#result_"+id).textbox('getValue') == ''){
			 $.messager.alert("操作提示",'请输入查询结果符合条件的项目第'+(i+1)+'行组合条件的条件值！', "error");
			 return;
		 }
		 var charging_item = $("#charging_"+id).combobox('getData');
		 var dep_category = null;sample_id = 0;table_name = null;
		 for(j=0;j<charging_item.length;j++){
			 if(charging_item[j].id == $("#charging_"+id).combobox('getValue')){
				 dep_category = charging_item[j].dep_category;
				 sample_id = charging_item[j].sam_demo_id;
				 if(dep_category == '21'){
					 table_name = $("#charging_"+id).combobox('getText');
				 }else{
					 table_name = $("#item_"+id).combobox('getText');
				 }
			 }
		 }
		 condition.push({
			 charging_id:$("#charging_"+id).combobox('getValue'),
			 dep_category:dep_category,
			 sample_id : sample_id,
			 item_id:$("#item_"+id).combobox('getValue'),
			 condition:$("#condition_"+id).combobox('getValue'),
			 result:$("#result_"+id).textbox('getValue'),
			 table_name:table_name,
			 table_colnum:'data'+data_num
		 });
		 colnum.push({align:'center',field:'data'+data_num,title:table_name,width:200,upload:'data'+data_num});
		 data_num++;
	 }
	 
	 var condition1 = new Array();
	 var obj = $("#zuhetj1").children('dl');
	 for(i=0;i<obj.length;i++){
		 var id = $(obj[i]).attr('data');
		 if($("#xcharging_"+id).combobox('getValue') == ''){
			 $.messager.alert("操作提示",'请选择需查询结果的项目第'+(i+1)+'行组合条件的收费项目！', "error");
			 return;
		 }else if($("#xitem_"+id).combobox('getValue') == ''){
			 $.messager.alert("操作提示",'请选择需查询结果的项目第'+(i+1)+'行组合条件的检查项目！', "error");
			 return;
		 }
		 var charging_item = $("#xcharging_"+id).combobox('getData');
		 var dep_category = null;sample_id = 0;table_name = null;
		 for(j=0;j<charging_item.length;j++){
			 if(charging_item[j].id == $("#xcharging_"+id).combobox('getValue')){
				 dep_category = charging_item[j].dep_category;
				 sample_id = charging_item[j].sam_demo_id;
				 if(dep_category == '21'){
					 table_name = $("#xcharging_"+id).combobox('getText');
				 }else{
					 table_name = $("#xitem_"+id).combobox('getText');
				 }
			 }
		 }
		 condition1.push({
			 charging_id:$("#xcharging_"+id).combobox('getValue'),
			 dep_category:dep_category,
			 sample_id : sample_id,
			 item_id:$("#xitem_"+id).combobox('getValue'),
			 table_name:table_name,
			 table_colnum:'data'+data_num
		 });
		 colnum.push({align:'center',field:'data'+data_num,title:table_name,width:200,upload:'data'+data_num});
		 data_num++;
	 }
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "time1":time1,	
				 "time2":time2,	
				 "user_name":user_name,
				 "min_age":min_age,	
				 "max_age":max_age,
				 "sex":sex,
				 "exam_type":examtype,
				 "conditions":JSON.stringify(condition),
				 "conditions1":JSON.stringify(condition1)
		 };
	     $("#exam_item_list").datagrid({
		 url:'getResearchDataList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[colnum],	    	
	     onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
			//height: window.screen.height-400,
			rownumbers:true,
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:false,
		    fit:true,
		    striped:true
	});
}

function export_data(){
	var rows = $("#exam_item_list").datagrid('getRows');
	if(rows.length <= 0){
		$.messager.alert("操作提示",'没有数据，不能导出!', "error");
		return;
	}
	datagridExportExcel('exam_item_list',true);
}