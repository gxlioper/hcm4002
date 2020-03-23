$(document).ready(function () {
	$('#exam_num').textbox('textbox').css('ime-mode','disabled');
	$('#exam_num').textbox('textbox').focus();
	
	
	$('#com_Name').bind('click', function() {  
		select_com_list();
    }); 
	
	$('#com_Name').bind('keyup', function() {
		select_com_list();
    });
	
	$('#com_Name').bind('blur', function() {  
		select_com_list_over();
    });	
	
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
	
	$('#exam_status').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS3',
		editable : false, //不可编辑状态
		cache : false,
		multiple:true,//是否允许多选
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
        onLoadSuccess:function(){ 	
        	
		}
	});
	
	$('#rylb').combobox({
		url : 'getDatadis.action?com_Type=RYLB',
		editable : false, //不可编辑状态
		cache : false,
		multiple:true,//是否允许多选
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
		}
	});

	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLX',
		editable : false, //不可编辑状态
		cache : false,
		multiple:true,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
		}
	});

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
		$("#batchcompany_id").val(id);
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
		url : 'getCompanForDept2.action?company_id='+$("#batchcompany_id").val(),
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		multiple:true,
		textField : 'dep_Name',
		onLoadSuccess : function(data) {
			$('#levelss').combobox('setValue', data[0].id);				
		}
	});
}
//-----------------------------------------------------------------------------------------------
/**
 * 保存修改
 */
function termcustadd() {
	var itemrows = $('#teamExamInfoShow').datagrid('getRows');
	if (itemrows.length > 0) {
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
				+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		var setrows = $('#teamExamInfoShow').datagrid('getRows');
        
		var setentities = "";
		for (i = 0; i < setrows.length; i++) {
			var tmp = {
					exam_num:setrows[i].exam_num,
					isprepay:setrows[i].isprepay,
					isnotpay:setrows[i].isnotpay,
			};
			setentities = setentities + JSON.stringify(tmp);
		}
		
		var chk_value ="";    
		  $('input[name = chkItem]:checked').each(function(){    
		   chk_value=chk_value+","+($(this).val());    
		  }); 
		  if(chk_value.length>1){
			  chk_value=chk_value.substring(1,chk_value.length);
		  }

		$.ajax({
			url : 'setTeamAccountList.action',
			data : {
				batchid : $("#batch_id").val(),
				setentities : setentities,
				acc_num : $("#acc_num").val(),
				chkItem:chk_value
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.messager.alert("操作提示", text.split("-")[1]);
					var _parentWin =  window.opener ; 
					_parentWin.gettermaccountlistGrid();
					window.close();
				} else {
					$.messager.alert("操作提示", text.split("-")[1], "error");
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
	} else {
		$.messager.alert("操作提示", "无效体检人员", "error");
	}
}

/**
 * 保存修改
 */
function termcustaddall() {
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
	  	  
	  var levels = $("#levelss").combobox('getValues');
		 var levelss = new Array();
		 for(i=0;i<levels.length;i++){
			 if(levels[i] != ''){
				 levelss.push("'"+levels[i]+"'"); 
			 }
		 }
		 
		 var tjlxs = $("#tjlx").combobox('getValues');
		 var tjlxss = new Array();
		 for(i=0;i<tjlxs.length;i++){
			 if(tjlxs[i] != ''){
				 tjlxss.push("'"+tjlxs[i]+"'"); 
			 }
		 }
		 
		$.ajax({
			url : 'setTeamAccountListall.action',
			data : {
				company_id:$("#batchcompany_id").val(),
				batchid:$("#batch_id").val(),
				sex:document.getElementsByName("sex")[0].value,
				group_id:document.getElementsByName("group_id")[0].value,
				set_id:document.getElementsByName("set_id")[0].value,
				exam_num:$("#exam_num").val(),
				custname:$("#custname").val(),
				time1:$("#start_date").datebox('getValue'),	
				time2:$("#end_date").datebox('getValue'),
				customer_type_id:$('#rylb').combobox('getValues').join(','),
				exam_status:$('#exam_status').combobox('getValues').join(','),
				levels:levelss.toString(),
				tjlxs:tjlxss.toString(),
				chkItem:chk_value,
				acc_num:$("#acc_num").val()
			},
			type : "post",// 数据发送方式
			success : function(text) {
				
				getteamnumdo();
				
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.messager.alert("操作提示", text.split("-")[1]);
					var _parentWin =  window.opener ; 
					_parentWin.gettermaccountlistGrid();
					window.close();
				} else {
					$.messager.alert("操作提示", text.split("-")[1], "error");
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
	
}

function getteamnumdo(){
	 $.ajax({
			url : 'setTeamAccountcountdo.action',
			data : {
				acc_num :$("#acc_num").val(),
				batchid: $("#batch_id").val()
			},
			type : "post",// 数据发送方式
			success : function(text) {
				
			},
			error : function() {
					
			}
		});
	
}
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
	  
	  var levels = $("#levelss").combobox('getValues');
		 var levelss = new Array();
		 for(i=0;i<levels.length;i++){
			 if(levels[i] != ''){
				 levelss.push("'"+levels[i]+"'"); 
			 }
		 }
		 
     var tjlxs = $("#tjlx").combobox('getValues');
	 var tjlxss = new Array();
	 for(i=0;i<tjlxs.length;i++){
		if(tjlxs[i] != ''){
		   tjlxss.push("'"+tjlxs[i]+"'"); 
	    }
	  }
		 
	var model = {
		"company_id":$("#batchcompany_id").val(),
		"batchid":$("#batch_id").val(),
		"sex":document.getElementsByName("sex")[0].value,
		"group_id":document.getElementsByName("group_id")[0].value,
		"set_id":document.getElementsByName("set_id")[0].value,
		"exam_num":$("#exam_num").val(),
		"custname":$("#custname").val(),
		"time1":$("#start_date").datebox('getValue'),	
		"time2":$("#end_date").datebox('getValue'),
		"customer_type_id":$('#rylb').combobox('getValues').join(','),
		"exam_status":$('#exam_status').combobox('getValues').join(','),
		"levels":levelss.toString(),
		"tjlxs":tjlxss.toString(),
		"chkItem":chk_value
	};
	$("#teamExamInfoShow").datagrid({
		url:'termExamInfoUserList.action',
		dataType : 'json',
		queryParams : model,
		remoteSort:false,
		rownumbers : true,
		pageSize : 200,// 每页显示的记录条数，默认为10
		pageList : [ 200,300,500 ],// 可以设置每页记录条数的列表
		columns : [[ {align : 'center',field : 'exam_num',title : '体检编号',width : 18,"formatter":f_showexam,sortable:true}, 
		              {align : 'center',field : 'id_num',title : '身份证号',width : 25,sortable:true}, 
		              {align : 'center',field : 'user_name',title : '姓名',width : 15,sortable:true}, 
		              {align : 'center',field : 'sex',title : '性别',width : 10,sortable:true}, 
		              {align : 'center',field : 'age',title : '年龄',width : 10,sortable:true}, 
		              {align : 'center',field : 'phone',title : '电话',width : 15,sortable:true}, 	
		              {align : 'center',field : 'deptname',title : '单位部门',width : 15,sortable:true}, 
		              {align : 'center',field : 'tjlx',title : '体检类型',width :15,sortable:true},		
		              {align : 'center',field : 'statussss',title : '体检状态',width :15,"formatter":f_showstatus,sortable:true},		                            
		              {align : 'center',field : 'group_name',title : '分组',width : 20,sortable:true}, 
		              {align : 'center',field : 'set_name',title : '套餐',width : 47,sortable:true}, 
		              {align : 'center',field : 'isprepayss',title : '是否预结算',	width : 15,"formatter" : f_isPrePay,sortable:true}, 
		              {align : 'center',field : 'isnotpayss',title : '含弃检',width : 15,"formatter" : f_isnotPay,sortable:true},
		              {align : 'center',field : 'ck',title : '删除',	width : 10,	"formatter" : f_dellete} 
		              ]],
		onLoadSuccess : function(value) {
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

function f_showexam(val,row){
	  return '<a href=\"javascript:f_showchargingitemshow('+row.exam_num+')\">'+row.exam_num+'</a>';
} 

var termexamid;
function f_showchargingitemshow(termexamid){
	 var url='termexamchargingitem.action?exam_num='+termexamid;
	   newWindow = window.open(url, "体检人员缴费项目明细", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	   newWindow.focus();
}

function f_showstatus(val,row){
	  if(row.status=='Y'){
		  return '<font color="red">'+row.statuss+'</font>';
	  }else if(row.status=='D'){
		  return '<font color="blue">'+row.statuss+'</font>';
	  }else if(row.status=='J'){
		  return '<font color="green">'+row.statuss+'</font>';
	  }else{
		  return row.statuss;
	  }
}
	 /**
	 * 是否含弃检
	 * @param val
	 * @param row
	 * @returns {String}
	 */
	 function f_isnotPay(val,row){
		 if(row.isnotpay=='1'){
			 return '<input id=\"isnotpay'+row.id+'\" name=\"isnotpay\" onchange=\"f_isnotpaydo('+row.id+')\" checked type=\"checkbox\" value=\"'+row.exam_num+'\"/>';
		 }else{
			 return '<input id=\"isnotpay'+row.id+'\" name=\"isnotpay\" onchange=\"f_isnotpaydo('+row.id+')\" type=\"checkbox\" value=\"'+row.exam_num+'\"/>'; 
		 }	 	
	 }
	 
	 var termexamid;
	function f_isnotpaydo(termexamid) {
	    var isnotpayflag = $("#isnotpay" + termexamid).is(':checked');
	    var rows = $('#teamExamInfoShow').datagrid('getRows');
	    if (!rows.length == 0) {
		    for (var j = 0; j <= rows.length - 1; j++)// 已选择
		    {
			   var row = rows[j];
			   if (row.id == termexamid) {
				  
				   if (isnotpayflag) {					   
					   row.isprepay = 1;
					   $('#teamExamInfoShow').datagrid('updateRow',{index:$('#teamExamInfoShow').datagrid('getRowIndex',j),row:{isprepay:1}});
				   } else {
					   row.isprepay = 0;
					   $('#teamExamInfoShow').datagrid('updateRow',{index:$('#teamExamInfoShow').datagrid('getRowIndex',j),row:{isprepay:0}});
				   }				   
				   break;
			   }
		    }
	    }
   }
	 
	 /**
		 * 是否预结算
		 * 
		 * @param val
		 * @param row
		 * @returns {String}
		 */
		 function f_isPrePay(val,row){
			 if(row.isprepay=='1'){
				 return '<input id=\"isprepay'+row.id+'\" name=\"isprepay\" onchange=\"f_isprepaydo('+row.id+')\" checked type=\"checkbox\" value=\"'+row.exam_num+'\"/>';
			 }else{
				 return '<input id=\"isprepay'+row.id+'\" name=\"isprepay\" onchange=\"f_isprepaydo('+row.id+')\" type=\"checkbox\" value=\"'+row.exam_num+'\"/>'; 
			 }	 	
		 }
	
			function f_isprepaydo(termexamid) {
			    var isprepayflag = $("#isprepay" + termexamid).is(':checked');
			    var rows = $('#teamExamInfoShow').datagrid('getRows');
			    if (!rows.length == 0) {
				    for (var j = 0; j <= rows.length - 1; j++)// 已选择
				    {
					   var row = rows[j];
					   if (row.id == termexamid) {
						   if (isprepayflag) {
						       row.isnotpay = 1;
						   } else {
							   row.isnotpay = 0;
						   }
						   $('#teamExamInfoShow').datagrid('refreshRow', j);
						   break;
					   }
				    }
			    }
		   }
 /**
  * 显示一条方案
  * @param val
  * @param row
  * @returns {String}
  */
  function f_dellete(val,row){	
	  return '<a href=\"javascript:f_delexamuser(\''+ row.exam_num+ '\')\">删除</a>';
  }
  
  var delexam_num;
  function f_delexamuser(delexam_num){
	  var rowsLength = $('#teamExamInfoShow').datagrid('getRows');
		if (!rowsLength.length == 0) {
			var flag = true;// 不相等
			for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
			{
				if (delexam_num == rowsLength[j].exam_num) {
					var index = $('#teamExamInfoShow').datagrid('getRowIndex',rowsLength[j]);// 获取指定索引
					$('#teamExamInfoShow').datagrid('deleteRow', index);// 删除指定索引的行
					break;
				}
			}// j End
		}
  }
  
  /**
   * 显示一条方案
   * @param userid
   */
  function f_batchshow(){
 	 	$("#dlg-show").dialog({
 	 		title:'单独查询体检任务',
 	 		href:'batchoneshow.action?id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()
 	 	});
 	 	$("#dlg-show").dialog('open');
 	 }
  

 /**
 * 增加结算单
 * @param val
 * @param row
 * @returns {String}
 */
 function f_xg(val,row){	
 	return '<a href=\"javascript:f_shht(\''+row.batch_id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"新增结算\" /></a>';
 }
 