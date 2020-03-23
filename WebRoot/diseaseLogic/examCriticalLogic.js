$(document).ready(function () {
	$('#c_jb').combobox({    
	    url:"getDatadis.action?com_Type=" + "WJZDJ",
	    valueField:'id',    
	    textField:'name',
	    editable:false,
		onLoadSuccess : function() {//下拉框默认选择
			var val = $(this).combobox('getData');
			$(this).combobox('setValue',val[0].id);
		},
		onSelect: function(rec){    
  		  var node = $('#ett').tree("getSelected");
  		  if(node.critical_class_level==0){
  			  list(0,0,rec.id);
  		  } else if(node.critical_class_level==1){
	    	  list(node.id,0,rec.id);
  		  }else {
    		  var father = $('#ett').tree("getParent",node.target);
      		  list(father.id,node.id,rec.id);
  		  }
        },
        loadFilter:function(data){
        	   data.unshift({id:'0',name:'全部等级'});
			   return data;
	    }
	}); 
	tree();
});
var fal = 0;
function tree(){
	$('#ett').tree({
		url:'criticalClasslist.action',
		queryParams:{
			
		},
		loadFilter: function(rows){    
	    	var tree = Array();
			var updaterow = new Object();
			var treeObj = new Object();
			var filterTree = new Array();
	        if (rows){    
	        	for( var i = 0 ; i< rows.length ; i++){
	        		updaterow = new Object();
	        		updaterow.id = rows[i].id;
	        		updaterow.text = rows[i].critical_class_name;
	        		if(rows[i].critical_class_level==1){
	        			updaterow.state = 'closed';
	        		} else {
	        			updaterow.state = 'opend';
	        		}
	        		updaterow.critical_class_level = rows[i].critical_class_level;
	        		tree.push(updaterow);
	        	}
	        } 
	        if(rows[0].critical_class_level==1){
	         	treeObj.id='0';
	        	treeObj.text='所有类';
	        	treeObj.children=tree;
	        	treeObj.critical_class_level = '0';
	        	treeObj.state = 'closed';
	        	filterTree.push(treeObj);
	        	console.log(filterTree);
			} else {
				filterTree = tree;
			}
	        return filterTree;
	    },
	    onBeforeLoad:function(node,param){
	    	if(node){
	    		param.critical_class_level=node.critical_class_level;
	    		param.id=node.id;
	    	}
	    },
		onLoadSuccess:function(node,data){  
			   if(fal==0){
				   $("#ett li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
		           var n = $("#ett").tree("getSelected");  
		           if(n!=null){  
		                $("#ett").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
		                $("#ett").tree("expand",n.target);    //相当于默认展开第一个节点
		           }  
			   }
			   fal++;
        },
	    onSelect:function(node){
	    	if(node.critical_class_level==0){
    		  list(0,0,$('#c_jb').combobox('getValue'));
	    	} else if(node.critical_class_level==1){
	    	  list(node.id,0,$('#c_jb').combobox('getValue'));
	    	} else {
    		  var father = $(this).tree("getParent",node.target);
      		  list(father.id,node.id,$('#c_jb').combobox('getValue'));
	    	}
	    },
	});
}
function cheboxCilke(){
		  var node = $('#ett').tree("getSelected");
		  if(node.critical_class_level==0){
			  list(0,0,$('#c_jb').combobox('getValue'));
		  } else if(node.critical_class_level==1){
			  list(node.id,0,$('#c_jb').combobox('getValue'));
		  }else {
		      var father = $('#ett').tree("getParent",node.target);
		  	  list(father.id,node.id,$('#c_jb').combobox('getValue'));
		  }
}
function list(parent_id,critical_class_id,critical_class_level){
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var isActive = "";
		 if(document.getElementById('Y').checked){
			 isActive = "Y";
		 }
		 if(document.getElementById('N').checked){
			 isActive = "N";
		 }
		 if(document.getElementById('Y').checked && document.getElementById('N').checked){
			 isActive = "";
		 }
	     $("#examCriticalLogicTable").datagrid({
		 url:'queryExamCriticalLogic.action',
		 queryParams:{
		    parent_id:parent_id,
			critical_class_id:critical_class_id,
			critical_class_level:critical_class_level,
			isActive:isActive,
			disease_name:$('#disease_name').val()
			
		 },
		 rownumbers:false,
		 toolbar:toolbar,
	     pageSize:15,//
	     //nowrap:false,
	     height:window.screen.height-370,
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'left',field:'critical_class_d_name',title:'大类',width:15},	
		    {align:'left',field:'critical_class_z_name',title:'子类',width:15,},
		 	{align:'left',field:'critical_class_level_name',title:'级别',width:15},
		 	{align:'center',field:'age_min',title:'适用年龄段',width:15},
			{align:'center',field:'sex',title:'性别',width:15},
		 	{align:'left',field:'disease_name',title:'疾病',width:15},
		 	{align:'left',field:'disease_type_s',title:'阳性发现/疾病',width:15},
		 	{align:'left',field:'info',title:'描述',width:60},
		 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
		    {align:"center",field:"isActive","title":"启(停)修改","width":10,"formatter":f_qt}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar,
	        onDblClickRow:function(index, row){
	        	examCriticalLogicDemoPage(row.id);
	        },
	        rowStyler: function(index,row){
	    		if (row.isActive=='N'){
	    			return 'color:#f00;';    
	    		}
	    	}

	});
}

function f_xg(val,row){	
	return '<a href=\"javascript:examCriticalLogicDemoPage(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}

function examCriticalLogicDemoPage(id){
			$("#dlg-edit").dialog({
			title:'修改危急值逻辑',
			width :1250,
			height:565,
			href:'examCriticalLogicadd.action?ecl_id='+id
		});
		$("#dlg-edit").dialog('open');
}
//启停修改
function f_qt(val,row){
    if(val=="N"){
      return '<a style="" href=\"javascript:f_startorblock(\''+row.id+'\',\'启用\')\">启用</a>';
    }else if(val=='Y') {
       return '<a style="" href=\"javascript:f_startorblock(\''+row.id+'\',\'停用\')\">停用</a>';
     }
}


//启（停）修改
function f_startorblock(id){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	$.ajax({
     	url:'updateExamCriticalLogicIsAcive.action?ecl_id='+id,
     	type:'post',
     	success:function(data){
     		$('#examCriticalLogicTable').datagrid('reload');
     		$(".loading_div").remove();
			//$.messager.alert("操作提示",data);
     	},
     	error:function(){
     		$.messager.alert('提示信息','操作失败！','error');
		}
	});
}
var toolbar = [ {
	text : '新增',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		 var critical_class_parent_id = 0;
		 var critical_class_id = 0;
		 var node = $('#ett').tree("getSelected");
 		  if(node.critical_class_level==0){

 		  } else if(node.critical_class_level==1){
	    	  critical_class_parent_id = node.id;
 		  }else {
 			  var father = $('#ett').tree("getParent",node.target);
     		  critical_class_parent_id = father.id;
    		  critical_class_id = node.id;;
 		  }
		
		$("#dlg-edit").dialog({
			title : '危急值逻辑维护',
			width :1250,
			height:565,
			href : 'examCriticalLogicadd.action?critical_class_parent_id='+critical_class_parent_id+'&critical_class_id='+critical_class_id
		});
		$("#dlg-edit").dialog('open');
	}
}, {
	text:'删除',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#examCriticalLogicTable').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push("'"+item.id+"'");
	    }); 
	    remove(ids.toString());
    }
} ];

/**
 * 删除
 * 
 */
function remove(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择要数据',"error")
		return;
	}
	$.messager.confirm('提示信息','是否删除',function(r){
	 	if(r){
			 $.ajax({
				 url : 'removeExamCriticalLogic.action',
				data : {ids:ids},
				type : "post",
				success : function(data) {
					$('#examCriticalLogicTable').datagrid('reload');
					$.messager.alert("操作提示",data);
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			 });
	 	 }
	 });
}
