$(document).ready(function () {
	dep_shu();
	getgroupuserGrid();
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	getgroupuserGrid();
	     }
	}
	
});

/**
 * 科室树
 */
function dep_shu(zhi){
	
	$("#some_tree").tree({
		 url:'getDepartmentDepList.action',
		 queryParams:{
			 web_Resource:1
		 },
		 type:'post',
		 dataType:'json',
		 loadFilter :function(data,parent){
			 if(zhi!=""&&zhi!=undefined){
				 var obj = data;
				 var newData = new Array();
				 newData.push({id:0,text:"无科室"});
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].id,text:obj[i].dep_name});
				 }
				return newData;
			 }else{
				 var obj = data;
				 var newData = new Array();
				 newData.push({id:0,text:"无科室"});
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].id,text:obj[i].dep_name});
				 }
				 var newDate2 = [{id:-1,text:'所有科室',children:newData}];
				 return newDate2;
			 }
		 },
		 onLoadSuccess:function(node,data){  
	           $("#some_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
	           var n = $("#some_tree").tree("getSelected");  
	           if(n!=null){  
	                $("#some_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
	           }  
        },
        onSelect:function(node){
        	var obj =  $(this).tree('getChildren',node.target);
        	//getgroupuserGrid(node.id);
        	$("#dept_id").val(node.id);
        	getgroupuserGrid();
        }
	});
}
/**
 * 清空查询
 */
function empty(){
	$('#num').textbox('setValue',"");
	$('#demo_name').textbox('setValue',"");
	$('#c_exam_num').textbox('setValue',"");
	$('#c_view_num').textbox('setValue',"");
	$('#groupusershow').datagrid('reload');
}
 //-------------------------------显示检查项目列表------------------------------------
/**
 * 显示检查项目列表
 */
var mccf="";
var itemwblx="";
 function getgroupuserGrid(){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 

		  var chk_value ="";    
 		  $('input[name = chkItem]:checked').each(function(){    
 		   chk_value=chk_value+","+($(this).val());    
 		  }); 
 		  if(chk_value.length>1){
 			  chk_value=chk_value.substring(1,chk_value.length);
 		  }
		 
	     $("#groupusershow").datagrid({
		 url:'queryExaminationItem.action',
		 queryParams:{
			 "exam_num":$('#c_exam_num').val(),
			 "view_num":$('#c_view_num').val(),
			 "item_num":$('#num').val(),
			 "item_name":$('#demo_name').val(),
			 "rt":mccf,//名称重复
			 "item_category":itemwblx,	//文本型
			 "dept_id":$("#dept_id").val(),  //关联科室ID
			 "startStop":chk_value  //是否显示停用/启用
		 },
		 toolbar:'#toolbar',
		 rownumbers:false,
	     //pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
	           	/*{field:'ck',checkbox:true },*/
	            {align:'center',field:'item_num',title:'项目编码',width:15},	
			    {align:'left',field:'item_name',title:'项目名称',width:30,},
			 	/*{align:'center',field:'item_eng_name',title:'项目英文名称',width:15},*/
			 	{align:'center',field:'item_unit',title:'单位',width:15},
			 	/*{align:'center',field:'item_pinyin',title:'项目拼音',width:15},*/
			 	{align:'center',field:'exam_num',title:'关联检验编码',width:20},
			 	{align:'center',field:'view_num',title:'关联影像编码',width:20},
			 	{align:'center',field:'item_category',title:'项目类型',width:20},
			 	/*{align:'center',field:'is_prints',title:'是否打印',width:15},*/
			 	{align:'center',field:'seq_codes',title:'顺序码',width:15},
			 	{align:'left',field:'item_description',title:'项目解释',width:15},
			 	/*{align:'center',field:'default_values',title:'默认值',width:15},*/
			 	{align:'center',field:'remark',title:'备注',width:15},
			 	{align:'center',field:'reference',title:'参考值范围',width:40},
			 	{align:'center',field:'risk',title:'危机值',width:40},
			 	{align:'center',field:'dataName',title:'项目分类',width:15},
			 	{align:'center',field:'sex',title:'适用性别',width:15},
			 	{align:'center',field:'disease_count',title:'阳性逻辑数量',width:40},
                {align:'center',field:'ff',title:'维护逻辑',width:25,"formatter":f_lj},
			 	{align:'center',field:'sss',title:'修改',width:15,"formatter":f_xg},
			 	//{align:'center',field:'ss',title:'删除',width:15,"formatter":f_sc},
			 	{align:"center",field:"is_Active","title":"启(停)修改","width":18,"formatter":f_qt},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		mccf="";
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true,
	        toolbar:toolbar,
	        onDblClickRow:function(index, row){
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updateSampleDemo(row_id);
	        }
	});
}
 
//启停修改
 function f_qt(val,row){
  var html='';
     if(val=="Y"){
       return '<a style="color:#1CC112;" href=\"javascript:f_startorblock(\''+row.id+'\',\'停用\')\">停用</a>';
     }else if(val=='N') {
        return '<a style="color:#f00;" href=\"javascript:f_startorblock(\''+row.id+'\',\'启用\')\">启用</a>';
      }
 }


 //启（停）修改
 function f_startorblock(id,html){
 	$.messager.confirm('提示信息','是否确认'+html+'该项目？',function(r){
 		if(r){
 		$.ajax({
      	url:'updateExaminationStopOrStart.action?ids='+id,
      	type:'post',
      	success:function(data){
      		if(data.split("-")[0] == 'ok'){
				$.messager.alert("提示信息",html+"该项目成功!");
				$('#groupusershow').datagrid('reload');
			} else {
				$.messager.alert("提示信息",data.split("-")[1],"error");
			}
      	},
      	error:function(){
      		$.messager.alert('提示信息','操作失败！','error');
      			}
 			});
 		}
 	})
 }
 
//-------------------------------------修改检验样本页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updateSampleDemo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function f_lj(val,row){
	return '<a href=\"javascript:editDisease(\''+row.id+'\',\''+row.item_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function editDisease(id,item_num) {
    var ul='itemDiseaseLogicSinglePage.action?id='+id+'&item_num='+item_num
    newWindow = window.open(ul,"单项阳性逻辑维护", "toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
}
/**
 * 修改检查项目页面
 * 
 */
 function updateSampleDemo(id){
			$("#dlg-custedit").dialog({
			title:'修改检查项目',
			width : 1000,
			height: 515,
			href:'updateExaminationItemPage.action?id='+id
		});
		$("#dlg-custedit").dialog('open');
}

/**
 * 检查项目名称获取拼音
 */
function pinying(){
	$.ajax({
		url:'pinying.action',
		type:'post',
		data:{pinying:$('#item_name').val()},
		success : function(data) {
			$('#item_pinyin').val(data);
		},
	})
}

  
//------------------------------------删除检查项目----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除检查项目提示信息
 * 
 */
function f_deluser(id) {
	//验证是否已收费
		$.ajax({
				url : 'getItemId.action?id='+id,
				type : 'post',
				success : function(data) {
					if(data=='no'){
						$.messager.alert('提示信息','检查项目已收费不可删除！');
						return;
					}else{
						$.messager.confirm('提示信息', '是否确定删除检查项目？', function(r) {
							//执行删除
							if (r) {
								$.ajax({
									url : 'deleteExaminationItem.action?ids='+id,
									type : 'post',
									success : function(data) {
										$.messager.alert('提示信息', data);
										$('#groupusershow').datagrid('reload')
									},
									error : function() {
										$.messager.alert('提示信息', '操作失败！', 'error');
									}
								});
							}
					})
					}
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			})
	
}
/**
 * 批量删除检查项目
 * 
 */
/*function deluserrow(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择要删除的检查项目!')
		return;
	}
	$.messager.confirm('提示信息','是否确定删除选中检查项目',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'deleteExaminationItem.action',
				data : {ids:ids},
				type : "post",
				success : function(data) {
						$.messager.alert("操作提示",data);
						getgroupuserGrid();
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			 });
	 	 }
	 });
}*/
//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text : '新增检查项目',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		if($("#dept_id").val() <= 0){ // 没有选中科室树
            $.messager.alert('提示信息', '请先选择科室！', 'warning');
            return;
		}

		$("#dlg-custedit").dialog({
			title : '新增检查项目',
			align : 'center',
			width : 1000,
			height: 515,
			href : 'addExaminationItemPage.action?dept_id='+$("#dept_id").val()
		});
		$("#dlg-custedit").dialog('open');
	}
}, {
	text:'项目名称重复',
	width:120,
	iconCls:'icon-cards',
    handler:function(){
    	mccf="名称重复"
    	getgroupuserGrid();
    }
}];
