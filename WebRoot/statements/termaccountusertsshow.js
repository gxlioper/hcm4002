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
	
	$('#rylb').combobox({
		url : 'getDatadis.action?com_Type=RYLB',
		editable : false, //不可编辑状态
		cache : false,
		multiple:true,//是否允许多选
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
	
	$('#itemname').bind('keyup', function() {
		setChangItemListGrid();
	});
	
	setfzchareitemListGrid();
	setChangItemListGrid();	
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
		multiple:true,//是否允许多选
		valueField : 'id',
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
		
		var setchargrows = $('#selectchangitemlist').datagrid('getRows');
		var chargitems="";
		for (i = 0; i < setchargrows.length; i++) {
			chargitems = chargitems + JSON.stringify(setchargrows[i]);
		}
		

		$.ajax({
			url : 'setTeamAccounttsList.action',
			data : {
				batchid : $("#batch_id").val(),
				setentities : setentities,
				chargentities:chargitems,
				acc_num : $("#acc_num").val()
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
		"tjlxs":tjlxss.toString(),
		"customer_type_id":$("#rylb").combobox('getValues').join(','),
		"exam_status":$("#exam_status").combobox('getValues').join(','),
		"levels":levelss.toString(),
		"chkItem":chk_value
	};
	$("#teamExamInfoShow").datagrid({
		url:'termExamInfoUserListts.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : true,
		remoteSort:false,
		pageSize : 100000,// 每页显示的记录条数，默认为10
		pageList : [ 10, 20, 45, 60, 75 ],// 可以设置每页记录条数的列表
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
		              {align : 'center',field : 'isnotpayss',title : '含弃检',width : 15,"formatter" : f_isnotPay,sortable:true},
		              {align : 'center',field : 'ck',title : '删除',	width : 10,	"formatter" : f_dellete} 
		              ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			$(".loading_div").remove();
		},
		singleSelect : true,
		collapsible : true,
		pagination : false,
		fitColumns : true,
		striped : true
	});
}

function f_showexam(val,row){
	  return '<a href=\"javascript:f_showchargingitemshow('+row.id+')\">'+row.exam_num+'</a>';
} 

var termexamid;
function f_showchargingitemshow(termexamid){
	 var url='termexamchargingitem.action?exam_id='+termexamid;
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
 
 
 //------------------------------------------------------------------------------------------------------
 

 /**
	 * 增加分组项目
	 */
	function insertitem(row) {
		var rowsLength = $('#selectchangitemlist').datagrid('getRows');
		var flag = true;//不相等
		var selectitemcode="";
		var itemtypeflag=true;
		if (!rowsLength.length == 0) {
			for (var j = 0; j <= rowsLength.length - 1; j++)//已选择
			{
				if (row.item_code == rowsLength[j].item_code) {
					flag = false;//相等
				}
				if((row.item_type!='')&&(row.item_type==rowsLength[j].item_type)){
					itemtypeflag=false;
				}
				selectitemcode=selectitemcode+",'"+rowsLength[j].item_code+"'";
			}//j End             
		}
		if (flag == true) {	

			var usersex = document.getElementsByName("sex")[0].value;
			var sexflag=false;
			//alert(usersex+"---"+row.sex);
			if(usersex==''){
				sexflag=true;
			}else if(row.sex=='全部'){
				sexflag=true;
			}else if(usersex==row.sex){
				sexflag=true;
			}
			if(sexflag){			
				if(itemtypeflag){
					$('#selectchangitemlist').datagrid("appendRow", {
						id : row.id,
						item_code : row.item_code,
						item_name : row.item_name,
						dep_name : row.dep_name,
						item_amount : row.item_amount,
						sex : row.sex,
						discount : row.discount,
						set_num : row.set_num,
						item_type:row.item_type,
						amount : row.amount
					});
					countamt();
				}else{
					 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
				     if(r){
				    	 $('#selectchangitemlist').datagrid("appendRow", {
								item_code : row.item_code,
								item_name : row.item_name,
								dep_name : row.dep_name,
								item_amount : row.item_amount,
								sex : row.sex,
								discount : row.discount,
								set_num : row.set_num,
								item_type:row.item_type,
								amount : row.amount
							});
							countamt();
						 }
					 });
				}	
			}else{
				alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
			}

		}else{
			alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
		}
	}
//----------------------------------------显示收费项目-------------------------------------------------
	/**
	 * 显示体检项目套餐信息
	 */
	function setChangItemListGrid() {
		var model = {
			"setname" : $("#itemname").val(),
			"sex":document.getElementsByName("sex")[0].value
		};
		$("#changitemlist").datagrid(
				{
					url : 'changitemlist.action',
					dataType : 'json',
					queryParams : model,
					rownumbers : false,
					pageSize: 5,//每页显示的记录条数，默认为10 
					pageList : [ 5 ],//可以设置每页记录条数的列表 
					columns : [[ {align : 'left',	field : 'id',title : '项目ID',width : 10},
					             {align : 'left',	field : 'item_code',title : '项目编码',width : 10},
					         {align : 'left',field : 'item_name',title : '项目名称',	width : 40}, 
					         {align : 'left',field : 'dep_name',title : '所属科室',width : 25}, 
					         {align : 'center',field : 'sex',title : '性别',width : 10}, 
					         {align : 'center',field : 'item_amount',title : '金额',width : 10}
					         ]],
					onLoadSuccess : function(value) {
						$("#datatotal").val(value.total);
					},
					singleSelect : true,
					collapsible : true,
					pagination : true,
					fitColumns : true,
					striped : true,
					onDblClickRow : function(index, row) {							
							insertitem(row);						
					}
				});
		//document.getElementById("itemname").value = '';
		//document.getElementById("itemname").focus();
	}

	//----------------------------------------显示分组收费项目-------------------------------------------------
	/**
	 * 显示体检项目套餐信息
	 */
	 var lastIndex; 
	function setfzchareitemListGrid() {
		var model = {};
		$("#selectchangitemlist").datagrid({
			url : 'custchangitemlist.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ 
			   {align : "center",field : "fxfzddd",title : "删除",width : 10,	"formatter" : f_dellchargitem},
			   {align : 'left',field : 'id',title : '项目ID',width : 15},
			   {align : 'left',field : 'item_code',title : '项目编码',width : 15}, 
			   {align : 'center',field : 'item_type',title : '项目类型',width : 20}, 
			   {align : 'left',field : 'item_name',title : '项目名称',width :40},
			   {align : 'left',field : 'dep_name',title : '科室',width : 25}, 
			   {align : 'center',field : 'item_amount',title : '原金额',width : 10}, 
			   {align : 'center',field : 'discount',title : '折扣率',width : 10,editor : {type : 'text'}}, 
			   {align : 'center',field : 'amount',title : '金额',	width : 10,	editor : {type : 'text'}} 
			   ]],
			onLoadSuccess : function(value) {
				$("#selectchangitemlist").datagrid("hideColumn", "item_type"); // 设置隐藏列  
				$("#datatotal").val(value.total);
				var data=$('#selectchangitemlist').datagrid('getData');
			},
			singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000
		});
	}
	
	/**
	 * 删除收费项目
	 * @param val
	 * @param row
	 * @returns {String}
	 */
	function f_dellchargitem(val, row) {		
		
		   return '<a href=\"javascript:deletechargitemOne(\''
				+ row.item_code
				+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';

	}
	/**
	 * 删除收费项目
	 */
	function deletechargitemOne(set_numsss) {
		$.messager.confirm('提示信息', '确定删除此收费项目吗？', function(r) {
			if (r) {
				var rows = $('#selectchangitemlist').datagrid('getRows');
				if (!rows.length == 0) {
					for (var i = rows.length - 1; i >= 0; i--) {
						if (set_numsss == rows[i].item_code) {
							var index1 = $('#selectchangitemlist').datagrid(
									'getRowIndex', rows[i]);//获取指定索引
							$('#selectchangitemlist').datagrid('deleteRow',
									index1);//删除指定索引的行
							break;
						}
					}//j End             
				}
			}
		})
	}