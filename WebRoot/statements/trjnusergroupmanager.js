$(document).ready(function () {
	
	$('#exam_num').textbox('textbox').css('ime-mode','disabled');
	$('#exam_num').textbox('textbox').focus();
	
	 $('#tjrw').bind('click', function() { 
			select_batchcom_list(this.value);
	    }); 
		
		$('#tjrw').bind('keyup', function() {
			select_batchcom_list(this.value);
	    });
		
		$('#tjrw').bind('blur', function() {
			select_batchcom_list_over();
	    });	
	
	$('#com_Name').bind('click', function() {  
		select_com_list();
    }); 
	
	$('#com_Name').bind('keyup', function() {
		select_com_list();
    });
	
	$('#com_Name').bind('blur', function() {  
		select_com_list_over();
    });	
	
		
	
	
	$('#rylb').combobox({
		url : 'getDatadis.action?com_Type=RYLB',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
					$('#rylb').combobox('setValue', data[0].id);
					break;
			}
		}
	});
	
	getteamExamInfoShowGrid();
});

var groupset_id;
function getSetFrogroupId(groupset_id) {
	$('#set_id').combobox({
		url : 'termSetlistshow.action?group_id=' + groupset_id,
		editable : false, // 不可编辑状态
		cache : false,
		panelHeight : 'auto',// 自动高度适合
		valueField : 'id',
		textField : 'set_name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				$('#set_id').combobox('setValue', data[0].id);
				break;
			}
		}
	});
}

//获取批次任务/////////////////////////////////////////////////////////////////
function select_batchcom_list(batchcom_Namess){
	var url='getComByBatchList.action';
	var data={"comname":batchcom_Namess};
	load_post(url,data,select_batchcom_list_sess);
	}

/**
* 显示树形结构
* @param data
* @returns
*/
function select_batchcom_list_sess(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"方案批次","javascript:void(0)","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:setvaluebatch('"+data[index].id+"','"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvaluebatch('"+data[index].id+"','"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#com_name_list_div1").html(mydtree.toString());
			$("#com_name_list_div1").css("display","block");
			
		}

/**
* 点击树设置内容
* @param id
* @param name
* @returns
*/
var partnerId;
var companyidss;
	function setvaluebatch(partnerId,name){
		var batcoms=partnerId.split('-');
		$("#company_id").val(batcoms[0]);
		$("#tjrw").val(name);	
		$("#com_Name").val('');
		$("#batch_id").val(batcoms[1]);
		
		companyidss=batcoms[0];
		hc_mousbatch_select1=false;
		select_batchcom_list_over();
		
		$('#group_id').combobox({
			url : 'termgrouplistshow.action?batchid='+$("#batch_id").val(),
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'group_name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					$('#group_id').combobox('setValue', data[0].id);
					break;
				}
			},
			onChange:function(n,o){
		         getSetFrogroupId(n);
		     }
		});
	}

//单位失去焦点
var hc_mousbatch_select1=false;
function select_batchcom_list_over(){
	if(!hc_mousbatch_select1){
	$("#com_name_list_div1").css("display","none");
	}
	}

function select_batchcom_list_mover(){
	hc_mousbatch_select1=true;
	}
function select_batchcom_list_amover(){
	hc_mousbatch_select1=false;
	}


//获取单位///////////////////////////////////////////////////////////
// -----------------------------------------------------------------------------------------------
//获取单位///////////////////////////////////////////////////////////
var hc_batchcom_list,batchcom_Namess;
function select_com_list(){
	var url='getCompersonByIdList.action';
	var data={"company_id":$("#company_id").val()};
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
							"javascript:setvalue('"+data[index].id+"','"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvalue('"+data[index].id+"','"+data[index].text+"')",
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
		$("#com_Name").val(name);
		$("#com_name_list_div").css("display","none");	
		f_getdept();
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

function setcolorDatagrid(){	
	$('#impusershow').datagrid({   
	    rowStyler:function(index,row){ 
	        if (row.flags==1){
	            return 'color:red;';   
	        }   
	    }   
	});
}

//显示部门
function f_getdept() {
	$('#levelss').combobox({
		url : 'getCompanForDept.action?company_id='+$("#batchcompany_id").val(),
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'dep_Name',
		onLoadSuccess : function(data) {
			$('#levelss').combobox('setValue', data[0].id);				
		}
	});
}
//-----------------------------------------------------------------------------------------------

	 /**
	  * 
	  */
function getteamExamInfoShowGrid() {
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
			+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var chk_value ="";    
	  $('input[name = chkItem]:checked').each(function(){    
	   chk_value=chk_value+","+($(this).val());    
	  }); 
	  if(chk_value.length>1){
		  chk_value=chk_value.substring(1,chk_value.length);
	  }
	var model = {
		"company_id":$("#company_id").val(),
		"batchid":$("#batch_id").val(),
		"sex":document.getElementsByName("sex")[0].value,
		"group_id":document.getElementsByName("group_id")[0].value,
		"set_id":document.getElementsByName("set_id")[0].value,
		"exam_num":$("#exam_num").val(),
		"custname":$("#custname").val(),
		"customer_type_id":document.getElementsByName("rylb")[0].value,
		"exam_status":document.getElementsByName("exam_status")[0].value,
		"levels":document.getElementsByName("levelss")[0].value,
		"time3":$("#time3").datebox('getValue'),	
		"time4":$("#time4").datebox('getValue'),
		"chkItem":chk_value
	};
	$("#teamExamInfoShow").datagrid({
		url:'gettrjnExamInfoUserList.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : true,
		pageSize : 10,// 每页显示的记录条数，默认为10
		pageList : [ 10, 20, 45, 60, 75 ],// 可以设置每页记录条数的列表
		columns : [[ 
		             {field:'ck',checkbox:true },
		             {align : 'center',field : 'exam_num',title : '体检编号',width : 18}, 
		              {align : 'center',field : 'id_num',title : '身份证号',width : 25}, 
		              {align : 'center',field : 'user_name',title : '姓名',width : 15}, 
		              {align : 'center',field : 'sex',title : '性别',width : 10}, 
		              {align : 'center',field : 'age',title : '年龄',width : 10}, 
		              {align : 'center',field : 'phone',title : '电话',width : 15}, 
		              {align : 'center',field : 'statussss',title : '体检状态',width :15},
		              {align : 'center',field : 'group_name',title : '分组',width : 20}, 
		              {align : 'center',field : 'set_name',title : '套餐',width : 47}, 
		              {align : 'center',field : 'join_date',title : '报道日期',width : 15} 
		              ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			$(".loading_div").remove();
		},
		singleSelect:false,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    striped:true,
        toolbar:toolbar
	});
}

/**
 * 定义工具栏
 */
var toolbar=[{
		text:'执行日结',
		iconCls:'icon-save',
		width:90,
	    handler:function(){
	    	var checkedItems = $('#teamExamInfoShow').datagrid('getChecked');
   	    var ids = "";
   	    $.each(checkedItems, function(index, item){
   	        ids=ids+","+(item.exam_num);
   	    });
   	    termtrjndo(ids);
	    }
	}];

/**
 * 执行日结
 */
var ids;
function termtrjndo(ids){
		 if(ids.length<=0){
	 		$.messager.alert("操作提示", "请选择体检人员","error");
	 	}else{
	 	 ids=ids.substring(1,ids.length);
		 $.messager.confirm('提示信息','是否确定对所选人员执行结算操作？',function(r){
			 	if(r){
			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
		 $.ajax({
				url : 'termtrjndo.action',
				data : {ids:ids},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getteamExamInfoShowGrid();
								alert_autoClose("操作提示",text.split("-")[1],"");
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	   }
		 });
	 	}
	 }