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
	
	showexam_item_list();
});

//-------------------------------------------单位信息显示-----------------------------------------------------
/**
 * 模糊查询公司信息
 */
 var hc_com_list,com_Namess;
 function select_com_list(com_Namess){
 	var url='mongocompanychangshow.action';
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

function showexam_item_list(){
$("#exam_item_list").datagrid({
url:'researchDataconditions.action',
type: 'post',
queryParams:[],
pageNumber:1,
//pageSize: 15,//每页显示的记录条数，默认为10 
pageList:[1000],//可以设置每页记录条数的列表 
columns:[[
    {align:'center',field:'id',title:'编号',width:50},
	{align:'left',field:'title',title:'标题',width:230},
    {align:'left',field:'create_time',title:'日期',width:200},
    {align:'center',field:'sss',title:'删除',width:50,"formatter":f_expmongoDatadel},
	]],	    	
	onLoadSuccess:function(value){ 
		$("#datatotal").val(value.total);		
	},
	rowStyler:function(index,row){		
   },
	onDblClickRow : function(index, row) {	    		
		showconditionsone(row.id);
	},
	nowrap:false,
	rownumbers:false,
	singleSelect:false,
    collapsible:true,
	pagination: true,
    fitColumns:false,
    striped:true
});
}

function f_expmongoDatadel(val,row){
	return '<a href=\"javascript:f_expmongoDatadeldo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}

var ids;
function showconditionsone(ids) {
	$.ajax({
		url : 'getResearchDataconditionsId.action',
		data : {
			add_i : ids
		},
		type : "post",// 数据发送方式
		success : function(data) {
			if (data.length>3) {
				try {
					var msg = JSON.parse(data);
					showconditionsonedo(msg.conditions);
					showconditionsoneshowdo(msg.conditions1);
				}catch(err){
					$(".loading_div").remove();
				       alert(err);
				    }
			} else {
				$.messager.alert("操作提示", "删除失败", "error");
			}
		},
		error : function() {
			$.messager.alert("操作提示", "操作错误", "error");
		}
	});
}

//显示查询项目
var conditionsText;
function showconditionsonedo(conditionsText) {
	$('#tt').tabs('select', 0);
	 $("#zuhetj").empty();
	$.parser.parse('#zuhetj');
	var conditionsObject=JSON.parse(conditionsText);
	add_i=0;
	for(var i=0;i<conditionsObject.length;i++){
		var objectcon=conditionsObject[i];
		add_i ++;
		var str = '<dl id="dl_'+add_i+'" data="'+add_i+'">'
		+'<dt><select id="dcondition_'+add_i+'" class="easyui-combobox" data-options="height:26,width:50,panelHeight:\'auto\'"></select></dt>'
	    +'<dt><select id="charging_'+add_i+'" class="easyui-combobox"></select><input id=\'dep_category_'+add_i+'\' type=\'hidden\'></dt>'
		+'<dt><select id="item_'+add_i+'" class="easyui-combobox"></select></dt>'
		+'<dt><select id="condition_'+add_i+'" class="easyui-combobox" data-options="height:26,width:50,panelHeight:\'auto\'"></select></dt>'
		+'<dt><input id="result_'+add_i+'" class="easyui-textbox"  type="text" data-options="height:26,width:130"/></dt>'
		+'<dt><input type="button" value="-" onclick="del_value(this);" style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/></dt>'
	    +'</dl>';
	$("#zuhetj").append(str);
	$.parser.parse('#dl_'+add_i);
	
	$("#charging_"+add_i).combobox({
		url:'mongoChargingItemListByq.action?add_i='+add_i,
		valueField:'id',    
		textField:'item_name',
		height:26,
		width:170,
		panelHeight:200,
		hasDownArrow:false,
		mode:'remote',
		onSelect:function (data){
			$("#dep_category_"+data.item_seq).val(data.dep_category);
			$("#item_"+data.item_seq).combobox('clear');
			$("#item_"+data.item_seq).combobox('reload','mongoExaminationItemListByq.action?charging_id='+data.id+'&add_i='+data.item_seq+'&q=');
			$("#condition_"+data.item_seq).combobox('clear');
			$('#dl_'+data.item_seq).children('dt').eq(4).html('<input id="result_'+data.item_seq+'" class="easyui-textbox"  type="text" data-options="height:26,width:130"/>');
			$.parser.parse($('#dl_'+data.item_seq).children('dt').eq(4));
			$("#result_"+data.item_seq).textbox('setValue','');
			
		}
	});
	$("#item_"+add_i).combobox({
		valueField:'id',    
		textField:'item_name',
		height:26,
		width:150,
		panelHeight:200,
		onSelect:function (data){
				$("#condition_"+add_i).combobox({
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
				$('#dl_'+data.seq_code).children('dt').eq(4).html('<input id="result_'+data.seq_code+'" class="easyui-textbox"  type="text" data-options="height:26,width:120"/>');
				$.parser.parse($('#dl_'+data.seq_code).children('dt').eq(4));
		}	
	});

	$("#dcondition_"+add_i).combobox({
		valueField: 'value',
		textField: 'text',
		data: [{
			value: 'and',
			text: 'and'
		},{
			value: 'or',
			text: 'or'
		}],
		onLoadSuccess:function(){
			$('#dcondition_'+add_i).combobox('setValue',objectcon.dcondition);
		}
	});
	
	$('#charging_'+add_i).combobox('setValue',objectcon.charging_id);
	$('#charging_'+add_i).combobox('setText',objectcon.charging_name);
	$('#item_'+add_i).combobox('setValue',objectcon.item_id);
	$('#item_'+add_i).combobox('setText',objectcon.item_name);
	$("#dep_category_"+add_i).val(objectcon.dep_category);
	$('#result_'+add_i).textbox('setValue',objectcon.result);
	$("#condition_"+add_i).combobox({
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
			$('#condition_'+add_i).combobox('setValue',objectcon.condition);
		}
	});
	
	}		
}


/**
 * 删除
 * 
 * @param ids
 * @returns
 */
var ids;
function  f_expmongoDatadeldo(ids){
		$.messager.confirm('提示信息','是否确定删除条件？',function(r){
			 if(r){
				 $.ajax({
						url : 'expmongoDatadel.action',
						data : {add_i:ids},
						type : "post",//数据发送方式   
						success : function(text) {							
							if (text == 'ok') {
								showexam_item_list();
								$.messager.alert("操作提示", "删除成功");
							} else {
								$.messager.alert("操作提示", "删除失败", "error");
							}
						},
						error : function() {
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
			 }
		})
	}

var add_i = 0;
function add_value(){
	if($("#zuhetj").children('dl').length >= 100){
		$.messager.alert("操作提示",'目前最大支持100个组合条件!', "error");
		return;
	}
	
	add_i ++;
	var str = '<dl id="dl_'+add_i+'" data="'+add_i+'">'
	    +'<dt><select id="dcondition_'+add_i+'" class="easyui-combobox" data-options="height:26,width:50,panelHeight:\'auto\'"></select></dt>'
	    +'<dt><select id="charging_'+add_i+'" class="easyui-combobox"></select><input id=\'dep_category_'+add_i+'\' type=\'hidden\'></dt>'
		+'<dt><select id="item_'+add_i+'" class="easyui-combobox" ></select></dt>'
		+'<dt><select id="condition_'+add_i+'" class="easyui-combobox" data-options="height:26,width:50,panelHeight:\'auto\'"></select></dt>'
		+'<dt><input id="result_'+add_i+'" class="easyui-textbox"  type="text" data-options="height:26,width:130"/></dt>'
		+'<dt><input type="button" value="-" onclick="del_value(this);" style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/></dt>'
	    +'</dl>';
	$("#zuhetj").append(str);
	$.parser.parse('#dl_'+add_i);
	
	$("#dcondition_"+add_i).combobox({
		valueField: 'value',
		textField: 'text',
		width:50,
		data: [{
			value: 'and',
			text: 'and'
		},{
			value: 'or',
			text: 'or'
		}],
		onLoadSuccess:function(){
			$("#dcondition_"+add_i).combobox('setValue','and');
		}
	});
	
	$("#charging_"+add_i).combobox({
		url:'mongoChargingItemListByq.action?add_i='+add_i,
		valueField:'id',    
		textField:'item_name',
		height:26,
		width:170,
		panelHeight:200,
		hasDownArrow:false,
		mode:'remote',
		onSelect:function (data){
			$("#dep_category_"+data.item_seq).val(data.dep_category);
			$("#item_"+data.item_seq).combobox('clear');
			$("#item_"+data.item_seq).combobox('reload','mongoExaminationItemListByq.action?charging_id='+data.id+'&add_i='+data.item_seq+'&q=');
			$("#condition_"+data.item_seq).combobox('clear');
			$('#dl_'+data.item_seq).children('dt').eq(4).html('<input id="result_'+data.item_seq+'" class="easyui-textbox"  type="text" data-options="height:26,width:130"/>');
			$.parser.parse($('#dl_'+data.item_seq).children('dt').eq(4));
			$("#result_"+data.item_seq).textbox('setValue','');
		}
	});
	$("#item_"+add_i).combobox({
		valueField:'id',    
		textField:'item_name',
		height:26,
		width:150,
		panelHeight:200,
		onSelect:function (data){
				$("#condition_"+data.seq_code).combobox({
					valueField: 'value',
					textField: 'text',
					width:50,
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
				$('#dl_'+data.seq_code).children('dt').eq(4).html('<input id="result_'+data.seq_code+'" class="easyui-textbox"  type="text" data-options="height:26,width:120"/>');
				$.parser.parse($('#dl_'+data.seq_code).children('dt').eq(4));
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

function getgroupuserGrid(){
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
		 condition.push({
			 dcondition:$("#dcondition_"+id).combobox('getValue'),
			 charging_id:$("#charging_"+id).combobox('getValue'),
			 dep_category:$("#dep_category_"+id).val(),
			 item_id:$("#item_"+id).combobox('getValue'),
			 condition:$("#condition_"+id).combobox('getValue'),
			 result:$("#result_"+id).textbox('getValue')			 
		 });
		 data_num++;
	 }
	 
	 
	//-------------------------------------------------
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
		 condition1.push({
			 charging_id:$("#xcharging_"+id).combobox('getValue'),
			 dep_category:$("#xdep_category_"+id).val(),
			 item_id:$("#xitem_"+id).combobox('getValue')
		 });
		 //colnum.push({align:'center',field:'data'+data_num,title:table_name,width:200,upload:'data'+data_num});
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
				 "pageno":$("#pageno").val(),
		         "pagesize":$("#pagesize").val(),
		         "searchType":$('input:radio[name=searchType]:checked').val(),
		         "searchlimit":$('input:radio[name=searchlimit]:checked').val(),
				 "conditions":JSON.stringify(condition),
				 "conditions1":JSON.stringify(condition1)
		 };		
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
		 $.ajax({
				url:'mongoDataList.action',//加载已排班信息
				data :model,
				type : "post",// 数据发送方式
				//dataType: "json",
				success: function(data){
					$(".loading_div").remove();
					try{ 
						var msg=JSON.parse(data);
						if(msg.searchflag==true){
						creatTab(JSON.parse(msg.str).title,JSON.parse(msg.str).rows,"exam_item_list_table");
						$("#counts").html(msg.pageDTO.pageTotal);
						$("#pagecount").html(msg.pageDTO.pageCount);
						$("#pagenum").html($("#pageno").val());
						if(msg.pageDTO.proPage == true){
							$("#propage").html('<button onclick="mangoOnclick14()">上一页</button>');
						}else{
							$("#propage").html('');
						}
						if(msg.pageDTO.nextPage == true){
							$("#nextpage").html('<button onclick="mangoOnclick15()">下一页</button>');
						}else{
							$("#nextpage").html('');
						}
						}else{
							alert("查无结果");
						}
					}catch(err){
						$(".loading_div").remove();
					       alert(err);
					    }
				 } 
				});
	}

function mangoOnclick14(){
	var pageno = Number($("#pageno").val())-1;
	$("#pageno").val(pageno);
	getgroupuserGrid();
}

function mangoOnclick15(){
	var pageno = Number($("#pageno").val())+1;
	$("#pageno").val(pageno);
	getgroupuserGrid();
}

function creatTab(cols,rows,divname){
	         var div1=document.getElementById(divname)
	         // alert(rows+'\n'+cols)
	         var width=cols.length*80;
	         var tab="<table  width='"+width+"px'>";
	        	 tab+='<tr>'
	        	 tab+="<td align='center' bgcolor='#ECF5FF' >行号</td>" 
	        	 for(var i=0;i<cols.length;i++){
		             tab+="<td align='center' bgcolor='#ECF5FF' >"+cols[i].title+"</td>" 
		         }   
	         tab+='</tr>'
	         for(var i=0;i<rows.length;i++){
	             tab+='<tr>'
	            	 var rowno=i+1;
	            	 tab+="<td align='center'>"+rowno+"</td>" 
	            	 for(var key in rows[i]) {
	            		    var value = rows[i][key];
	            		    tab+="<td>"+value+"</td>" 
	            	 }
	             
	              tab+='</tr>'
	         }    
	         tab+='</table>';
	         div1.innerHTML=tab
	     }

function mangoexport_save(){	 
		 var condition = new Array();		 	 
		 var obj = $("#zuhetj").children('dl');
		 var data_num = 1;
		 for(i=0;i<obj.length;i++){
			 var id = $(obj[i]).attr('data');	
			 condition.push({
				 dcondition:$("#dcondition_"+id).combobox('getValue'),
				 charging_id:$("#charging_"+id).combobox('getValue'),
				 charging_name: $("#charging_"+id).combobox('getText'),
				 dep_category:$("#dep_category_"+id).val(),
				 item_id:$("#item_"+id).combobox('getValue'),
				 item_name:$("#item_"+id).combobox('getText'),
				 condition:$("#condition_"+id).combobox('getValue'),
				 result:$("#result_"+id).textbox('getValue')
			 });
			 data_num++;
		 }
		 //-------------------------------------------------
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
			
			 condition1.push({
				 charging_id:$("#xcharging_"+id).combobox('getValue'),
				 charging_name:$("#xcharging_"+id).combobox('getText'),
				 dep_category:$("#xdep_category_"+id).val(),
				 item_id:$("#xitem_"+id).combobox('getValue'),
				 item_name:$("#xitem_"+id).combobox('getText'),
			 });
			 //colnum.push({align:'center',field:'data'+data_num,title:table_name,width:200,upload:'data'+data_num});
			 data_num++;
		 }
		 
		 if(data_num==1)
		 {
		     $.messager.alert("操作提示", "条件无效，无法保存", "error");	
		 }else{
			 var model={
				 "searchType":$('input:radio[name=searchType]:checked').val(),				 
				 "searchlimit":$('input:radio[name=searchlimit]:checked').val(),
				 "conditions":JSON.stringify(condition),
				 "conditions1":JSON.stringify(condition1)
			 };	
  		   $.ajax({
					url:'expmongoDatasave.action',//加载已排班信息
					data :model,
					type : "post",// 数据发送方式	              
					success: function(data){
						showexam_item_list();
						if("ok"==data){
							$.messager.alert("操作提示", "保存成功", "info");	
						}else{
							$.messager.alert("操作提示", "保存失败", "error");	
						}
					 } 
					});
		 }
		}

function mangoexport_data(){
		 var exam_num = "";
		 if($("#ck_exam_num")[0].checked){
			 exam_num =  $("#exam_num").textbox('getValue');
		 }
		 var time1 = "",time2 = "";
		 if($("#ck_date")[0].checked){
			 time1 = $("#time1").datebox('getValue');
			 time2 = $("#time2").datebox('getValue');
		 }
		 var user_name = "";
		 if($("#ck_custname")[0].checked){
			 user_name = $("#custname").textbox('getValue');
		 }
		 var min_age = -1;max_age = -1;
		 if($("#ck_age")[0].checked){
			 min_age = $("#min_age").numberbox('getValue');
			 max_age = $("#max_age").numberbox('getValue');
		 }
		 var sex = "";
		 if($("#ck_sex")[0].checked){
			 sex = $('#sex').combobox('getValue');
		 }
		 var examtype = "";
		 if($("#ck_examtype")[0].checked){
			 examtype = $('#examtype').combobox('getValue');
		 }
		 var comid = 0;
		 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
			 comid = $("#company_id").val();
		 }
		 var condition = new Array();
		 	 
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
	
			 condition.push({
				 dcondition:$("#dcondition_"+id).combobox('getValue'),
				 charging_id:$("#charging_"+id).combobox('getValue'),
				 dep_category:$("#dep_category_"+id).val(),
				 item_id:$("#item_"+id).combobox('getValue'),
				 condition:$("#condition_"+id).combobox('getValue'),
				 result:$("#result_"+id).textbox('getValue')				 
			 });
			 data_num++;
		 }
		 
		 //-------------------------------------------------
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
			
			 condition1.push({
				 charging_id:$("#xcharging_"+id).combobox('getValue'),
				 dep_category:$("#xdep_category_"+id).val(),
				 item_id:$("#xitem_"+id).combobox('getValue')
			 });
			 //colnum.push({align:'center',field:'data'+data_num,title:table_name,width:200,upload:'data'+data_num});
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
					 "searchType":$('input:radio[name=searchType]:checked').val(),
					 "searchlimit":$('input:radio[name=searchlimit]:checked').val(),
					 "conditions":JSON.stringify(condition),
					 "conditions1":JSON.stringify(condition1)
			 };	
			 var cond = encodeURI(encodeURI(JSON.stringify(condition)));
			 var cond1 = encodeURI(encodeURI(JSON.stringify(condition1)));
			 var searchtype=$('input:radio[name=searchType]:checked').val();
			 var searchlimit=$('input:radio[name=searchlimit]:checked').val();
			 window.location.href='expmongoDataListall.action?searchlimit='+searchlimit+'&searchType='+searchtype+'&company_id='+comid+'&exam_num='+exam_num+'&time1='+time1+'&time2='+time2+'&user_name='+user_name+'&min_age='+min_age+'&max_age='+max_age+'&sex='+sex+'&exam_type='+examtype+'&conditions='+cond+'&conditions1='+cond1;
			 /*$.ajax({
					url:'expmongoDataListall.action',//加载已排班信息
					data :model,
					type : "get",// 数据发送方式	              
		            contentType:'application/vnd.ms-excel;charset=utf-8',
					success: function(data){
						var form=$("<form>");//定义一个form表单  
		                form.attr("style","display:none");  
		                form.attr("target","");  
		                form.attr("method","post");  
		                form.attr("action","expmongoDataListall.action");  
		                var input1=$("<input>");  
		                input1.attr("type","hidden");  
		                input1.attr("name","exportData");  
		                input1.attr("value",(new Date()).getMilliseconds());  
		                $("body").append(form);//将表单放置在web中  
		                form.append(input1);  
		                form.submit();//表单提交   
					 } 
					});*/
		}


var add_i1 = 0;
function add_value1(){
	if(($("#zuhetj").children('dl').length+$("#zuhetj1").children('dl').length) >= 100){
		$.messager.alert("操作提示",'目前最大支持100个组合条件!', "error");
		return;
	}
	
	add_i1 ++;
	var str = '<dl id="xdl_'+add_i1+'" data="'+add_i1+'">'
	    +'<dt><select id="xcharging_'+add_i1+'" class="easyui-combobox" data-options="height:26,width:265,panelHeight:200"></select><input id=\'xdep_category_'+add_i1+'\' type=\'hidden\'></dt>'
		+'<dt><select id="xitem_'+add_i1+'" class="easyui-combobox" data-options="height:26,width:265,panelHeight:200"></select></dt>'
		+'<dt><input type="button" value="-" onclick="del_value(this);" style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/></dt>'
	    +'</dl>';
	$("#zuhetj1").append(str);
	$.parser.parse('#xdl_'+add_i1);
	
	$("#xcharging_"+add_i1).combobox({
		url:'mongoChargingItemListByq.action?add_i='+add_i1,
		valueField:'id',    
		textField:'item_name',		
		hasDownArrow:false,
		mode:'remote',
		onSelect:function (data){
			$("#xdep_category_"+data.item_seq).val(data.dep_category);
			$("#xitem_"+data.item_seq).combobox('clear');
			$("#xitem_"+data.item_seq).combobox('reload','mongoExaminationItemListByq.action?charging_id='+data.id+'&add_i='+data.item_seq+'&q=');
		}
	});
	
	$("#xitem_"+add_i1).combobox({
		valueField:'id',    
		textField:'item_name',
		onSelect:function (data){}	
	});
}

//显示显示项目
function showconditionsoneshowdo(conditionsText) {
	 $("#zuhetj1").empty();
	$.parser.parse('#zuhetj1');
	var conditionsObject=JSON.parse(conditionsText);
	add_i1=0;
	for(var i=0;i<conditionsObject.length;i++){
		var objectcon=conditionsObject[i];
		add_i1 ++;
		var str = '<dl id="xdl_'+add_i1+'" data="'+add_i1+'">'
	    +'<dt><select id="xcharging_'+add_i1+'" data-options="height:26,width:265,panelHeight:200" class="easyui-combobox"></select><input id=\'xdep_category_'+add_i1+'\' type=\'hidden\'></dt>'
		+'<dt><select id="xitem_'+add_i1+'" class="easyui-combobox" data-options="height:26,width:265,panelHeight:200" ></select></dt>'
		+'<dt><input type="button" value="-" onclick="del_value(this);" style="width:20px;text-align:center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/></dt>'
	    +'</dl>';
	$("#zuhetj1").append(str);
	$.parser.parse('#xdl_'+add_i1);
	
	$("#xcharging_"+add_i1).combobox({
		url:'mongoChargingItemListByq.action?add_i='+add_i1,
		valueField:'id',    
		textField:'item_name',		
		hasDownArrow:false,
		mode:'remote',
		onSelect:function (data){
			$("#xdep_category_"+data.item_seq).val(data.dep_category);
			$("#xitem_"+data.item_seq).combobox('clear');
			$("#xitem_"+data.item_seq).combobox('reload','mongoExaminationItemListByq.action?charging_id='+data.id+'&add_i='+data.item_seq+'&q=');
		}
	});
	
	$("#xitem_"+add_i1).combobox({
		valueField:'id',    
		textField:'item_name',
		onSelect:function (data){}	
	});

	$('#xcharging_'+add_i1).combobox('setValue',objectcon.charging_id);
	$('#xcharging_'+add_i1).combobox('setText',objectcon.charging_name);
	$('#xitem_'+add_i1).combobox('setValue',objectcon.item_id);
	$('#xitem_'+add_i1).combobox('setText',objectcon.item_name);
	$("#xdep_category_"+add_i1).val(objectcon.dep_category);
	}		
}