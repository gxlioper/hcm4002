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
	getbatchGrid();
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
		getbatchGrid();	
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
 function getbatchGrid(){
		 var model={"company_id":$("#company_id").val()};
	     $("#fanganlist").datagrid({
		 url:'batchlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
            {align:"center",field:"sc",title:"删除",width:15,"formatter":f_sc},
		 	{align:'center',field:'batch_name',title:'体检任务名称',width:25},
		 	{align:'center',field:'data_name',title:'结算方式',width:15},
		 	{align:'center',field:'exam_number',title:'预计人数',width:15},
		 	{align:'center',field:'exam_fee',title:'预算金额',width:15},
		 	{align:'center',field:'contact_name',title:'联系人',width:15},
		 	{align:'center',field:'phone',title:'联系电话',width:15},
		 	{align:'center',field:'update_times',title:'修改时间',width:15},
		 	{align:'center',field:'checktypes',title:'审核状态',width:15},	
		 	{align:'center',field:'checkdate',title:'审核时间',width:25},	
		 	{align:'center',field:'checknotice',title:'审核说明',width:15},	
		 	{align:"center",field:"xgddd",title:"修改",width:15,"formatter":f_xg},
		 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_show},
		 	{align:"center",field:"fxfz",title:"进行分组",width:15,"formatter":f_fz},
		 	{align:"center",field:"ryjh",title:"人员计划",width:15,"formatter":f_ryjh}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    singleSelect:true,
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
		text:'新增体检任务',
		iconCls:'icon-add',
	    handler:function(){
	    	$("#dlg-edit").dialog({
	    		title:'新增体检任务',
	    		href:'batchedit.action?id=0&company_id='+$("#company_id").val()
	    	});
	    	if($("#company_id").val()>0){
	    	   $("#dlg-edit").dialog("open");
	    	}else{
	    	  $.messager.alert('提示信息',"请选择单位名称","error");
	    	}
	    }
	}];
 
/**
 * 修改
 * @param val
 * @param row
 * @returns {String}
 */
 function f_xg(val,row){	
 	return '<a href=\"javascript:f_xguser(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
 }
 
 
 /**
  * 显示一条
  * @param val
  * @param row
  * @returns {String}
  */
  function f_show(val,row){	
	  return '<a href=\"javascript:f_batchshow(\''+row.id+'\')\">查看</a>';
  }
  
  
  /**
   * 进行分组
   * @param val
   * @param row
   * @returns {String}
   */
   function f_fz(val,row){	
 	  return '<a href=\"javascript:f_fzctro(\''+row.id+'\')\">体检任务分组</a>';
   }

   
   function f_ryjh(val,row){	
	 	  return '<a href=\"javascript:f_ryjhctro(\''+row.id+'\')\">人员计划</a>';
	   }
 /**
  * 
  * 删除
  * @param val
  * @param row
  * @returns {String}
  */
 function f_sc(val,row){
 	return '<a href=\"javascript:f_deluser(\''+row.id+'\')\">删除</a>';
 }
 
 
 function f_batchshow(userid){
	 	$("#dlg-show").dialog({
	 		title:'单独查询体检任务',
	 		href:'batchoneshow.action?id='+userid+'&company_id='+$("#company_id").val()
	 	});
	 	$("#dlg-show").dialog('open');
	 }
 
function f_fzctro(userid){
	var reqUrl2 = "groupmanager.action?company_id="+$("#company_id").val()+"&batch_id="+userid;
	window.parent.showOnetab(2,reqUrl2);
}


function f_ryjhctro(userid){
	var reqUrl3 = "batchproShow.action?company_id="+$("#company_id").val()+"&batch_id="+userid;
	window.parent.showOnetab(3,reqUrl3);
}

 function f_xguser(userid){
 	$("#dlg-edit").dialog({
 		title:'修改体检任务',
 		href:'batchedit.action?id='+userid+'&company_id='+$("#company_id").val()
 	});
 	$("#dlg-edit").dialog('open');
 }

 function f_deluser(userid)
 {
 $.messager.confirm('提示信息','是否确定删除此体检任务？',function(r){
 	if(r){
 		$.ajax({
 		url:'batchdelete.action?id='+userid,
 		type:'post',
 		success:function(text){
 			if(text.split("-")[0]=='ok'){
         		  $.messager.alert("操作提示", text);
         		  $("#dlg-edit").dialog("close");
         		  getbatchGrid();
         	  }else if(text.split("-")[0]=='error'){
         		  $.messager.alert("操作提示", text,"error");
         	  }
 		},
 		error:function(){
 			$.messager.alert('提示信息','操作失败！','error');
 		}
 		});
 	}
 });
 }