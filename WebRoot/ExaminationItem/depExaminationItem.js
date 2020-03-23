$(document).ready(function () {
	shu();
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	getgroupuserGrid();
	     }
	}
});
//=============================================state树===========================================================
var charging_item_id = "";
function shu(){
	$("#tt").tree({
	    url : 'getdepExaminationItemList.action',
	    method : "POST",
	    onContextMenu: function(e, node){
			e.preventDefault();
			// 查找节点
			$('#tt').tree('select', node.target);
			var cen = $("#tt").tree('getSelected').cen
			if(cen=='2'){
				// 显示快捷菜单
				$('#yiceng').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			} else if(cen=='3'){
				$('#erceng').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			}
		},
		onBeforeLoad: function (node, param) {
              if (node) {
                  param.id = node.id;
                  param.cen = node.cen;
              }
		},
		onSelect:function(node){
			getgroupuserGridss();
		},
		onLoadSuccess:function(node,data){
			 var n = $('#tt').tree('find', data[0].id);
	          $('#tt').tree('select', n.target);
		}
		
	})
}
function xiugai(){
	$('#addtreedialog').dialog('open');
	var text = $("#tt").tree('getSelected').text;
	var id = $("#tt").tree('getSelected').id;
	$('#lbmc').val(text);
	$('#item_class_id').val(id);
}

function addtreedialog(){
	$('#addtreedialog').dialog('open')
	var id = $("#tt").tree('getSelected').id;
	$('#lbmc').val('');
	$('#item_class_id').val('');
	$('#charging_item_id').val(id);
	
}
function jiansuo(){
	var id = "";
	var da = $('#tt').tree('getRoots');
	for(var i = 0 ; i < da.length; i++){
		if(da[i].text==$('#jiansuo').val().trim()){
			id = da[i].id;
			 var n = $('#tt').tree('find',da[i].id);
	          //调用选中事件
	          $('#tt').tree('select', n.target);
	          break;
		}
	}
}

//--===============================================科室检查项目维护==============================================
function saveDepItem(){
	if($('#lbmc').val()==""){
		$('#lbmc').focus();
		return;
	}
	
	
	var text = $("#tt").tree('getSelected').text
	var id = $("#tt").tree('getSelected').id
	var cen = $('#tt').tree('getSelected').cen;
	var node = $('#tt').tree('getSelected');//找到id为”tt“这个树的节点id为”1“的对象
	
	var model = new Object();
	model.item_class_name = $('#lbmc').val();
	model.item_class_id = $('#item_class_id').val();
	model.charging_item_id = $('#charging_item_id').val();
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	$.ajax({
		url:'savedepExaminationItem.action',
		data:model,
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert("提示信息",data,"info");
			$('#addtreedialog').dialog('close');
			if(cen==2){
				item_class_id = $("#tt").tree('getSelected').id;
				var leibie_fujiedian = $('#tt').tree('find',item_class_id);
				$("#tt").tree('reload',leibie_fujiedian.target);
				$('#tt').tree('select',leibie_fujiedian.target);
			} else if(cen==3){
				item_class_id = $("#tt").tree('getSelected').id;
				var leibie_fujiedian = $('#tt').tree('find',item_class_id);
				var yijijiedian =  $("#tt").tree('getParent',leibie_fujiedian.target);
				$("#tt").tree('reload',yijijiedian.target);
				$('#tt').tree('select',yijijiedian.target);
			}
		},error:function(){
			$(".loading_div").remove();
			$.messager.aerlt("警告信息","操作失败","error");
			
		}
	})
}
function deletelb(){
	var id = $("#tt").tree('getSelected').id
	var node = $('#tt').tree('getSelected');//找到id为”tt“这个树的节点id为”1“的对象
	var cen = $("#tt").tree('getSelected').cen;
	$.messager.confirm('提示信息','确定删除吗？',function(r){
	 	if(r){
	 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 		 $("body").prepend(str);
	 		$.ajax({
	 			url:'deletedepExaminationItem.action?item_class_id='+id,
	 			success:function(){
	 				if(cen==3){
	 					var yijijiedian = $('#tt').tree('getParent',node.target);
	 					
	 					
	 					$("#tt").tree('reload',yijijiedian.target);
	 					$('#tt').tree('select',yijijiedian.target);
	 				} else if(cen==2){
	 					$("#tt").tree('reload',node.target);
	 					$('#tt').tree('select',node.target)
	 				}
	 				$(".loading_div").remove();
	 				$.messager.alert("警告信息","删除成功","info");
	 			},error:function(){
	 				$(".loading_div").remove();
	 				$.messager.alert("警告信息","操作失败","error");
	 			}
	 		})
	 	}
	})
	
}
//===================================================end===================================================
 //-------------------------------显示检查项目列表------------------------------------
/**
 * 显示检查项目列表
 */
var editIndex1 = undefined;
var ind = "";
 function getgroupuserGridss(){	
 		var item_class_id ="";
 		var charging_item_id = "";
	 	var tr = $('#tt').tree('getSelected');
	 	if(tr!=null && tr!=""){
	 		var cen = $("#tt").tree('getSelected').cen
	 		//alert("层级"+cen);
	 			if(cen=='3'){
		 			item_class_id = $("#tt").tree('getSelected').id;
			    	var leibie_fujiedian = $('#tt').tree('find',item_class_id);
			    	var cjiedian =  $("#tt").tree('getParent',leibie_fujiedian.target);
			    	//alert("cjiedian==="+cjiedian.id+"==item_class_id==="+item_class_id);
			    	charging_item_id = cjiedian.id;
			    } else if(cen=='2'){
			    	charging_item_id = $("#tt").tree('getSelected').id;
			    } else {
			    	charging_item_id = "0";
			    }
	 		$('#charging_item_id').val(charging_item_id);
	 		$('#item_class_id').val(item_class_id);
	 	} 
	    //alert("item_class_id=="+item_class_id+"====charging_item_id=="+charging_item_id);
	 	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#groupusershow").datagrid({
		url:'queryExaminationItem.action',
		 queryParams:{
			 "item_num":$('#item_num').val(),
			 "item_name":document.getElementById("cl_demo_name").value,
			 "item_class_id":item_class_id,
			/* "exam_num":$('#c_exam_num').val(),
			 "view_num":$('#c_view_num').val(),*/
			 "charging_item_id":charging_item_id,
			 "item_class_id":item_class_id,
			 "Remark":"XJCXM"
		 },
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
	           	{field:'ck',checkbox:true },
	           	{align:'center',field:'charging_item_id',title:'收费项目编码',width:30},	
	            {align:'center',field:'item_num',title:'项目编码',width:30},	
			    {align:'left',field:'item_name',title:'项目名称',width:20},
			    {align:'center',field:'item_class_id',title:'类别id',width:25},
			    {align:'center',field:'item_class_name',title:'类别',width:25,
			    	editor:{
						type:'combobox',
						options:{
							valueField:'id',
							textField:'text',
							method:'get',
							url:'getdepExaminationItemList.action?cen=2&id='+charging_item_id,
						}
					}
	  		    },
			 /*	{align:'center',field:'item_unit',title:'单位',width:15},*/
			 /*	{align:'center',field:'item_category',title:'项目类型',width:20},*/
			 	{align:'center',field:'seq_codes',title:'顺序码',width:15},
			 	{align:'center',field:'remark',title:'备注',width:15},
			 /*	{align:'center',field:'reference',title:'参考值范围',width:40},
			 	{align:'center',field:'risk',title:'危机值',width:40},*/
			 /*	{align:'center',field:'dataName',title:'项目分类',width:15},*/
			 	{align:'center',field:'sex',title:'适用性别',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){
		 		$("#groupusershow").datagrid('hideColumn','item_class_id');
		 		$("#groupusershow").datagrid('hideColumn','charging_item_id');
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    rownumbers:true,
		    fit:true,
		    cache:true,
		    striped:true,
	        toolbar:toolbar,
	        onDblClickRow:function(index, row){
	        	$('#groupusershow').datagrid('selectRow', index).datagrid('beginEdit', index);
	        	ind = index;
	        	if (editIndex1 != index){
					if (endEditing1()){
						$('#groupusershow').datagrid('selectRow', index).datagrid('beginEdit', index);
						editIndex1 = index;
					} else {
						$('#groupusershow').datagrid('selectRow', editIndex1);
					}
				}
	        }
	});
	 
	    
}
 function zuihoubaocun1(){
//		$('#groupusershow').datagrid('getRows')[ind]['item_class_name'] = text;
//		$('#groupusershow').datagrid('getRows')[ind]['item_class_id'] = item_class_id;
		var row = $('#groupusershow').datagrid('getRows');
		for(var i = 0 ; i < row.length ; i++){
			var ed = $('#groupusershow').datagrid('getEditor', {index:i,field:'item_class_name'});
			if(ed==null || ed==""){
				continue;
			}
			var text = $(ed.target).combobox('getText');
			var item_class_id = $(ed.target).combobox('getValue');
			if(text==item_class_id){
			} else {
				$('#groupusershow').datagrid('getRows')[i]['item_class_name'] = text;
				$('#groupusershow').datagrid('getRows')[i]['item_class_id'] = item_class_id;
			}
			
			$('#groupusershow').datagrid('refreshRow',i);
		    $("#groupusershow").datagrid('endEdit',i);
		}
	}
	function endEditing1(){
		if (editIndex1 == undefined){return true}
		if ($('#groupusershow').datagrid('validateRow', editIndex1)){
			var ed = $('#groupusershow').datagrid('getEditor', {index:editIndex1,field:'item_class_name'});
			if(ed==null || ed==""){
				return;
			}
			var text = $(ed.target).combobox('getText');
			var item_class_id = $(ed.target).combobox('getValue');
//			$('#groupusershow').datagrid('getRows')[editIndex1]['item_class_name'] = text;
//			$('#groupusershow').datagrid('getRows')[editIndex1]['item_class_id'] = item_class_id;
//			$('#groupusershow').datagrid('refreshRow',editIndex1);
//		    $("#groupusershow").datagrid('endEdit',editIndex1); //结束行编辑
			editIndex1 = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	function accept1(){
		if (endEditing1()){
			$('#groupusershow').datagrid('acceptChanges');
		}
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

  
//------------------------------------删除类别和项目关系----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 批量删除检查项目
 * 
 */
function deluserrow(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择记录!')
		return;
	}
	$.messager.confirm('提示信息','是否删除',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'deleteItemclass.action',
				data : {ids:ids},
				type : "post",
				success : function(data) {
					$(".loading_div").remove();
						$.messager.alert("操作提示",data,"info");
						getgroupuserGridss();
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			 });
	 	 }
	 });
}
//----------------------------------定义工具栏---------------------------
var  item_class_name = "";
var toolbar = [ {
	text : '编辑关系',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
			
			var item_class_id = "";
		    var node = $("#tt").tree('getSelected');
		    if(node==null || node==""){
		    	$.messager.alert("警告信息","请选择类别或者项目","error");
		    	return;
		    }
		    if(node.cen=='3'){//第一层
		    	item_class_id = $("#tt").tree('getSelected').id;
		    	var leibie_fujiedian = $('#tt').tree('find',item_class_id);
		    	var cjiedian =  $("#tt").tree('getParent',leibie_fujiedian.target);
		    	item_class_name = $("#tt").tree('getSelected').text;
		    	charging_item_id = cjiedian.id;
		    } else if(node.cen=='2'){
		    	charging_item_id = $("#tt").tree('getSelected').id;
		    } else {
		    	$.messager.alert("警告信息","请选择类别或者项目","error");
		    	return;
		    }
		$("#dlg-custedit").dialog({
			title : '关系',
			align : 'center',
			width : 1000,
			height: 515,
			method:'post',
			href : 'addExaminationItemPage.action?charging_item_id='+charging_item_id+"&item_class_id="+item_class_id
		});
		$("#dlg-custedit").dialog('open');
	}
},{
	text : '删除',
	iconCls : 'icon-remove',
	width : 80,
	handler : function() {
		var da = $('#groupusershow').datagrid('getChecked');
		var ids = new Array();
		for(var i = 0 ; i < da.length ; i++){
			ids.push(da[i].id);
		}
		deluserrow(ids.toString())
	}
},{
			text : '保存编辑行',
			iconCls : 'icon-save',
			width :120,
			handler : function() {
				zuihoubaocun1();
				//$('#groupusershow').datagrid('acceptChanges');
				var item="";
				var row = $("#groupusershow").datagrid('getRows');
				console.log(row);
				for(var i = 0 ; i < row.length ; i++){
					item+='{"charging_item_id":'+row[i].charging_item_id+',"exam_item_id":'+row[i].exam_itemid+',"item_class_id":'+row[i].item_class_id+'},';
				}
				var item_s = item.substring(0,item.length-1);
				console.log(item_s);
				$.ajax({
					url:'saveItemClass.action',
					data:{
						item_id:'['+item_s+']',
						charging_item_id:$('#charging_item_id').val(),
						item_class_id:$('#item_class_id').val()
					},
					type:'post',
					success:function(data){
						$.messager.alert("提示信息",data,"info");
						$('#dlg-custedit').dialog('close');
						$("#groupusershow").datagrid('reload');
					},error:function(){
						$.messager.alert("警告信息","操作失败","error");
					}
				})
			}
},{
	text : '设置类别',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		var cen = $("#tt").tree('getSelected').cen
		var item_class_id = "";
		if(cen=='3'){
 			var item_id = $("#tt").tree('getSelected').id;
	    	var leibie = $('#tt').tree('find',item_id);
	    	item_class_id =  $("#tt").tree('getParent',leibie.target).id;
	    } else if(cen=='2'){
	    	item_class_id = $("#tt").tree('getSelected').id;
	    } else {
	    	$.messager.alert("提示信息","请选择类别或者项目！","error");
	    	return;
	    }
		
		
		var da = $('#groupusershow').datagrid('getChecked');
		if(da.length<=0){
			$.messager.alert("警告信息","请选择记录！","error");
			return;
		}
		var ids = new Array();
		for(var i = 0 ; i < da.length ; i++){
			ids.push(da[i].cteid);
		}
		$("#dlg-custedit").dialog({
			title : '设置类别',
			align : 'center',
			width : 700,
			height: 400,
			method:'post',
			href : 'shezhileibiePage.action?ids='+ids.toString()+"&charging_item_id="+item_class_id
		});
		$("#dlg-custedit").dialog('open');
	}
}];

