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
	getbatchcheckGrid();
	getgroupcheckGrid(0);
	gettccheckGrid(0);
	gettjxmcheckGrid(0);
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
		$("#companyname").html(id+"-"+name);
		$("#company_id").val(id);
		$("#com_name_list_div").css("display","none");
		getbatchcheckGrid();
		getgroupcheckGrid(0);
		gettccheckGrid(0);
		gettjxmcheckGrid(0);
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
 function getbatchcheckGrid(){
		 var model={"company_id":$("#company_id").val()};
	     $("#fanganchecklist").datagrid({
		 url:'batchlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'batch_num',title:'体检任务编码',width:20},
		 	{align:'center',field:'batch_name',title:'体检任务名称',width:25},
		 	{align:'center',field:'data_name',title:'结算方式',width:15},
		 	{align:'center',field:'exam_number',title:'预计人数',width:15},
		 	{align:'center',field:'exam_fee',title:'预算金额',width:15},
		 	{align:'center',field:'contact_name',title:'联系人',width:15},
		 	{align:'center',field:'phone',title:'联系电话',width:15},
		 	{align:'center',field:'update_times',title:'修改时间',width:15},
		 	{align:'center',field:'checktypes',title:'状态',width:15},
		 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_showcheck},
		 	{align:'center',field:'checkfa',title:'审核',width:10,"formatter":f_fangancheck}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000,
			onDblClickRow : function(index, row) {
				getgroupcheckGrid(row.id);
			}
	});
	}
  
 
//---------------------------------------显示方案------------------------------------------------------
 /**
  * 
  */
 var batch_id;
 function getgroupcheckGrid(batch_id){
		 var model={"batch_id":batch_id};
	     $("#groupchecklist").datagrid({
		 url:'grouplistshow.action',
		 dataType: 'json',
		 queryParams:model,
	//	 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'group_num',title:'分组编码',width:20},
		 	{align:'center',field:'group_name',title:'分组名称',width:25},
		 	{align:'center',field:'exam_indicators',title:'结算方式',width:15},
		 	{align:'center',field:'sex',title:'性别',width:15},
		 	{align:'center',field:'min_age',title:'最小年龄',width:15},
		 	{align:'center',field:'max_age',title:'最大年龄',width:15},
		 	{align:'center',field:'is_Marriage',title:'婚否',width:15},
		 	{align:'center',field:'posttion',title:'职位',width:15},
		 	{align:'center',field:'amount',title:'金额',width:15},
		 	{align:'center',field:'discount',title:'折扣率',width:15},		 	
		 	{align:'center',field:'group_index',title:'标志',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#groupchecklist").datagrid("hideColumn", "discount"); // 设置隐藏列
					$("#groupchecklist").datagrid("hideColumn", "amount"); // 设置隐藏列
				}
	    	},
	    	singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000,
	      //  toolbar:toolbar,
			onDblClickRow : function(index, row) {
				gettccheckGrid(row.id);
				gettjxmcheckGrid(row.id);
			}
	});
	}
 
 
//----------------------------------------显示套餐-------------------------------------------------
 /**
  * 显示分组套餐信息
  */
 function gettccheckGrid(userid){
		 var model={"group_id":userid};
	     $("#examsetchecklist").datagrid({
		 url:'groupsetlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'set_num',title:'套餐编码',width:20},
		 	{align:'center',field:'set_name',title:'套餐名称',width:25},
		 	{align:'center',field:'sex',title:'适用性别',width:15},
		 	{align:'center',field:'set_discount',title:'套餐折扣率',width:15},
		 	{align:'center',field:'set_amount',title:'套餐金额',width:15}
		 	]],	
		 	onLoadSuccess:function(value){
	    		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#examsetchecklist").datagrid("hideColumn", "set_discount"); // 设置隐藏列
					$("#examsetchecklist").datagrid("hideColumn", "set_amount"); // 设置隐藏列
				}
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
//----------------------------------------显示套餐-------------------------------------------------
 /**
  * 显示体检项目套餐信息
  */
 function gettjxmcheckGrid(userid){
		 var model={"group_id":userid};
	     $("#chargitemchecklist").datagrid({
		 url:'groupchargitemlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	     columns : [ [ 
	       {align : 'center',field : 'item_code',title : '项目编码',width : 20},
	       {align : 'center',field : 'item_name',title : '项目名称',width : 25}, 
	       {align : 'center',field : 'dep_name',title : '科室',width : 25	}, 
	       {align : 'center',field : 'item_amount',title : '原金额',width : 20},
	       {align : 'center',field : 'discount',title : '折扣率',	width : 20},
	       {align : 'center',field : 'amount',title : '套餐金额',width : 20}
	     ] ],
	     onLoadSuccess:function(value){
	    		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#chargitemchecklist").datagrid("hideColumn", "discount"); // 设置隐藏列
					$("#chargitemchecklist").datagrid("hideColumn", "amount"); // 设置隐藏列
				}
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
 
 
 function f_fangancheck(val,row){
	 return '<a href=\"javascript:f_fangancheckcheck(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"审核\" /></a>';
 }
 
 
 
/**
 * 体检任务审核
 * @param userid
 */
 function f_fangancheckcheck(userid){	
		 	$("#dlg-show").dialog({
		 		title:'审核体检任务',
		 		href:'batcheckedit.action?id='+userid+'&company_id='+$("#company_id").val()
		 	});
		 	$("#dlg-show").dialog('open');
 }
 
 /**
  * 显示一条
  * @param val
  * @param row
  * @returns {String}
  */
  function f_showcheck(val,row){	
	  return '<a href=\"javascript:f_batchcheckshow(\''+row.id+'\')\">查看</a>';
  }
  
  
  var checknewWindow;  
  var checktimer;   
 function f_batchcheckshow(userid){
	 	var url='/batchonecheckshow.action?id='+userid+'&company_id='+$("#company_id").val();
    	if(userid>0){
    		newWindow = window.open(url, "查看单独体检任务", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes")
    		newWindow.focus();
    	}else{
    	  $.messager.alert('提示信息',"请先选择体检任务","error");
    	}
	 }
 
 /**
  * 设置每隔1秒钟刷新父节点的表格
  */
 function updateAfterClose() {  
     //父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口  
     if(checknewWindow.closed == true) {  
       clearInterval(checktimer);  
       getbatchcheckGrid(); // 主窗口getgroupGrid();刷新  
       return;  
     }  
}  
