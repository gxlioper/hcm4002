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
	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name'
	});
	getExamInfoGrid();
	
	$(document).keydown(function(e){
		if(e.keyCode == 13){
			getExamInfoGrid();
		}
	});
	
	$("#exam_num").focus();
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


	//获取菜单
	function f_getDatadic() {
		$('#batch_id').combobox({
			url : 'getCompanForBatch2.action?company_id='+$("#company_id").val(),
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				if(data[0].batch_name == '请选择体检任务'){
					data[0].batch_name = '不限';
					$('#batch_id').combobox('loadData',data);
				}
			},
			onChange:function(newValue, oldValue){
				if(newValue == ''){
					$('#group_id').combobox('clear');
					$('#group_id').combobox('loadData',[{id:'',group_name:'不限'}]);
				}else{
					$('#group_id').combobox({
						url : 'getBatchForGroup.action?batch_id='+newValue,
						editable : false, //不可编辑状态
						cache : false,
						panelHeight : 'auto',//自动高度适合
						valueField : 'id',
						textField : 'group_name',
						onLoadSuccess : function(data) {
							if(data[0].group_name == '可选分组'){
								data[0].group_name = '不限';
								$('#group_id').combobox('loadData',data);
							}
						}
					});
				}
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

 //---------------------------------------显示方案------------------------------------------------------
 /**
  * 
  */
var examinfo_id = 0;
function getExamInfoGrid(){
	var exam_num = undefined;
	if ($("#ck_exam_num")[0].checked) {
		exam_num = $("#exam_num").val();
	}
	var arch_num = undefined;
	if ($("#ck_arch_num")[0].checked) {
		arch_num = $("#arch_num").val();
	}
	var user_name = undefined;
	if ($("#ck_user_name")[0].checked) {
		user_name = $("#user_name").val();
	}
	var sex = undefined;
	if ($("#ck_sex")[0].checked) {
		sex = $("#sex").combobox('getValue');
	}
	var phone = undefined;
	if ($("#ck_phone")[0].checked) {
		phone = $("#phone").val();
	}
	var exam_type = undefined;
	if ($("#ck_exam_type")[0].checked) {
		exam_type = $("#exam_type").combobox('getValue');
	}
	var s_join_date = undefined,e_join_date = undefined;
	if ($("#ck_join_date")[0].checked) {
		s_join_date = $("#s_join_date").datebox('getValue');
		e_join_date = $("#e_join_date").datebox('getValue');
	}
	var comid = undefined;
	if ($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked) {
		comid = $("#company_id").val();
	}
	var customer_type = undefined;
	if($("#ck_tjlx")[0].checked){
		customer_type = $('#tjlx').combobox('getValue');
	}
	var model = {
		"exam_num" : exam_num,
		"arch_num" : arch_num,
		"user_name" : user_name,
		"sex" : sex,
		"phone" : phone,
		"exam_type" : exam_type,
		"s_join_date" : s_join_date,
		"e_join_date" : e_join_date,
		"com_id" : comid,
		"batch_id" : $("#batch_id").combobox('getValue'),
		"group_id" : $("#group_id").combobox('getValue'),
		"customer_type":customer_type
	};
	
	$("#contractlist").datagrid({
		url:'getExamRosterList.action',
		dataType: 'json',
		queryParams:model,
		rownumbers:false,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		columns:[[
		          {align:'center',field:'fx',checkbox:true},
		          {align:'center',field:'exam_num',title:tjhname,width:15,sortable:true},
		          {align:'center',field:'arch_num',title:dahname,width:10,sortable:true},
		          {align:'center',field:'user_name',title:'姓名',width:10,sortable:true},
		          {align:'center',field:'id_num',title:'身份证',width:15,sortable:true},
		          {align:'center',field:'sex',title:'性别',width:8,sortable:true},
		          {align:'center',field:'age',title:'年龄',width:8,sortable:true},
		          {align:'center',field:'phone',title:'电话',width:10},
		          {align:'center',field:'join_date',title:'体检日期',width:15,sortable:true},
		          {align:'center',field:'exam_type_y',title:'体检类型',width:15},
		          {align:'center',field:'set_name',title:'体检套餐',width:15},
		          {align:'center',field:'company',title:'单位',width:20},
		          {align:'center',field:'statuss',title:'体检状态',width:10},
		          {align:'center',field:'final_doctor',title:'总检医生',width:10},
		          {align:'center',field:'final_date',title:'总检日期',width:15,sortable:true},
		          {align:'center',field:'app_doctor',title:'审核医生',width:10},
		          {align:'center',field:'old_arch_num',title:'旧档案号',width:10},
		          {align:'center',field:'up_arch_num_time',title:'合并时间',width:15}
		]],	    	
		onLoadSuccess:function(value){
			$("#datatotal").val(value.total);
		},
		height: window.screen.height-400,
		singleSelect:false,
		rownumbers:false,
		collapsible:true,
		pagination: true,
		fitColumns:true,
		striped:true,
		onDblClickRow:function(index,row){
			examinfo_id = row.exam_num;
			$("#dlg-edit").dialog({//width: 1200,height: 515,closed: true,cache: false,modal: true
					width: 1200,
					height: 515,
			    	closed: true,
			    	cache: false,
			    	modal: true,
			    	title:'个人体检信息',
			    	href:'getExamItemResultByExamNum.action?exam_num ='+row.exam_num
			    });
			$("#dlg-edit").dialog("open");
		},
		toolbar:[{
			iconCls: 'icon-edit',
			text:"自动合并档案",
			handler: function(){
				var stid=new Array();
				var str_arch=new Array();
				var rows= $("#contractlist").datagrid('getChecked');
		    	if(rows.length<=1){
		    		$.messager.alert("提示信息","请选择两条以上的体检信息","error");
		    		return;
		    	}else{
		    		$.messager.confirm('提示信息','确认自动合并档案？',function(r){
						if(r){
							for(var i=0;i<rows.length;i++){
				    			stid.push(rows[i].exam_num);
				    			str_arch.push(rows[i].arch_num);
				    		};
				    		$.ajax({
					    		url:'autocombinearch.action?arch_com_ids='+stid.toString()+"&str_arch_num="+str_arch.toString(),
					    		success:function(){
					    			$.messager.alert("提示信息","自动合并成功");
					    			$("#contractlist").datagrid('reload');
					    		},
					    		error:function(){
					    			$.messager.alert("提示信息","自动合并失败","error");
					    		}
					    	});
						}
					})
		    		
		    	}
			}
		},{
			iconCls: 'icon-edit',
			text:"选择合并档案",
			handler: function(){
				var stid=new Array();
				var rows= $("#contractlist").datagrid('getSelections');
		    	if(rows.length<=1){
		    		$.messager.alert("提示信息","请选择两条以上的体检信息","error");
		    		return;
		    	}else{
		    		for(var i=0;i<rows.length;i++){
		    			stid.push(rows[i].exam_num);
		    		};
		    		$("#combine-edit").dialog({
						title:"选择合并档案",
						href:'combineArch.action?arch_com_ids='+stid.toString(),
					});
					$("#combine-edit").dialog('open');
		    	}
				
			}
		}]
	});
}

