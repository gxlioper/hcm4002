$(document).ready(function (){
	$('#disease_level_z').combobox({
		url :'getDatadis.action?com_Type=JBDJ',
		editable : false, //不可编辑状态
		cache : false,
		height:27,
		width:85,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		loadFilter:function(data){
			data.unshift({'id':'','name':'不选择'});
			return data;
		},
		onLoadSuccess : function(data){
        	$('#disease_level_z').combobox('setValue', data[0].id);
		}
	});
	$("#disease_type_s").combobox({
		data:[{'id':'','text':'不选择'},{'id':'Y','text':'阳性发现'},{'id':'D','text':'疾病'}],
		editable : true, //不可编辑状态
		cache : false,
		height:27,
		width:85,
		panelHeight:'auto',
		valueField : 'id',
		textField : 'text',	
        onLoadSuccess : function(data){
        	$('#disease_type_s').combobox('setValue', data[0].id);
		}
	});
	dep_shu();
	getGrid();
});
/**
 * 科室树
 */
function dep_shu(zhi){
	$("#some_tree").tree({
		 url:'getDepartmentDepList.action?web_Resource=1',
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
        	$("#dept_id").val(node.id);
        	getGrid();
        }
	});
}

//查询按钮
function searchFun(){
	getGrid();
}
//清空按钮
function cleanFun(){
	$("#disease_type_s").combobox('setValue','');
	$("#disease_name_s").textbox('setValue',"");
	$("#disease_level_z").combobox('setValue',"");
	getGrid();
}
	
//工具按钮
var toolbar = [{
		    text:'新增',
		    iconCls:'icon-add',
		    handler:function(){
		    	if($("#dept_id").val() <= 0  ){
                    $.messager.alert('提示信息','请选择科室!','warning');
					return;
				}
		    	$("#dlg-gxwh").dialog({
					title:'疾病信息维护',
					href:'addDKLdge.action?dep_id='+$("#dept_id").val()
				});
				$("#dlg-gxwh").dialog('open');
		    }
		    },{
		    	text:'删除',
		    	width:80,
		    	iconCls:'icon-cancel',
		        handler:function(){
		        	var ids = new Array();
		        	var checkedItems = $('#diseaseKnowloedgeList').datagrid('getChecked');
		    	    $.each(checkedItems, function(index, item){
		    	        ids.push(item.id);
		    	    }); 
		    	    deluserrow(ids.toString());
		        }
		    }];
	 //查看按钮
	function f_ck(val,row){
			return '<a href=\"javascript:lookDKLdge(\''+row.id+'\')\">查看</a>';
	}
	function lookDKLdge(id){
		 $("#dlg-look").dialog({
				title:'查看',
				href:'lookDKLdgelibshow.action?id='+id
			});
		$("#dlg-look").dialog('open');
	}
	
	//修改
	function f_wh(val,row){
		return '<a href=\"javascript:guanxiweihuadd(\''+row.id+'\')\">编辑</a>';
	}
	
	//修改
	function guanxiweihuadd(id){
		$("#dlg-gxwh").dialog({
			title:'疾病信息维护',
			href:'updateDKLdge.action?id='+id
		});
		$("#dlg-gxwh").dialog('open');
	}
	/**
	 * 批量删除
	 * 
	 */
	function deluserrow(ids){
		if(ids==""){
			$.messager.alert('提示信息','请选择要删除的行!')
			return;
		}
		$.messager.confirm('提示信息','是否确定删除选中的行',function(r){
		 	if(r){
//		 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
//				 $("body").prepend(str);
				 $.ajax({
					url : 'deleteDklodeg.action',
					data : {ids:ids},
					type : "post",
					success : function(data) {
							$.messager.alert("操作提示",data);
							getGrid();
					},
					error : function() {
						$.messager.alert('提示信息', '操作失败！', 'error');
					}
				 });
		 	 }
		 });
	}
	

	// 疾病页面数据加载
function getGrid() {
	var is_active = "";
	if($("#qiyong").get(0).checked && !$("#tingyong").get(0).checked){
		is_active = "Y";
	}else if(!$("#qiyong").get(0).checked && $("#tingyong").get(0).checked){
		is_active = "N";
	}
	var model = {
		"disease_type" : $('#disease_type_s').combobox('getValue'),
		"disease_name" : $('#disease_name_s').textbox('getValue'),
		"disease_level" : $('#disease_level_z').combobox('getValue'),
		'dep_id' : $("#dept_id").val(),
		'is_active':is_active
	};
	$("#diseaseKnowloedgeList").datagrid({
		url : 'diseaseList.action',
		queryParams : model,
		pageSize : 15,// 每页显示的记录条数，默认为10
		pageList : [ 15, 30, 45, 60, 75 ],// 可以设置每页记录条数的列表
		columns : [ [ {field : 'ck',checkbox : true}, 
		              {align : 'center',field : "disease_type_d",title : "疾病类型","width" : 18}, 
		              {align : 'center',field : "disease_name",title : "疾病名称","width" : 40}, 
		              {align : 'center',field : "disease_level_d",title : "级别","width" : 10}, 
		              {align : 'center',field : "disease_classification_d",title : "疾病分类","width" : 15}, 
		              {align : "center",field : "updater",title : "更新者","width" : 12}, 
		              {align : "center",field : "update_time",title : "更新时间","width" : 20}, 
		              {align : "center",field : "dep_name",title : "科室名称","width" : 18}, 
		              {align : "center",field : "chakan",title : "查看","width" : 12,"formatter" : f_ck}, 
		              {align : "center",field : "bj",title : "编辑","width" : 18,"formatter" : f_wh},
		              {align : "center",field : "qt",title : "启停","width" : 18,"formatter" : f_qiting} 
		          ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			getsuggestionGrid(0);
		},
		singleSelect : false,
		pagination : true,
		fitColumns : true,
		fit : true,
		toolbar : toolbar,
		onClickRow : function(index, row) {
			getsuggestionGrid(row.id);
		}
	});
}
function f_qiting(val,row){
	   if(row.isActive=="Y"){
	        return '<a style="color:#f00;" href=\"javascript:f_qt_disease(\''+row.id+'\',\'N\')\">停用</a>';
	   }else if(row.isActive=='N') {
	       return '<a style="color:#1CC112;" href=\"javascript:f_qt_disease(\''+row.id+'\',\'Y\')\">启用</a>';
	   }
}
function f_qt_disease(id,is_active){
	var html = "启用";
	if(is_active == 'N'){
		html = "停用";
	}
	$.messager.confirm('提示信息','是否确认'+html+'该条记录？',function(r){
		if(r){
			$.ajax({
         	url:'diseaseLibOffOn.action?id='+id+'&is_active='+is_active,
         	type:'post',
         	success:function(data){
         		$.messager.alert('提示信息',data);
         		getGrid();
         	},
         	error:function(){
         		$.messager.alert('提示信息','操作失败！','error');
         	}
			});
		}
	})
}
	//建议列表加载
	var dataGrid;
	var disid = 0;
	function getsuggestionGrid(id){
			disid = id;
	       var model = {"disease_id":id};
	       dataGrid= $("#suggestionList").datagrid({
			url: 'getsuggestionList.action',
			queryParams: model,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			height:400,
	        columns:[[
	        		  {align:"center",field:"sex",title:"性别","width":10},
	        		  {align:'center',field:"minAge",title:"最小年龄","width":10},
	        		  {align:'center',field:"maxAge",title:"最大年龄","width":10},
	        		  {align:'center',field:"disease_suggestion",title:"健康建议","width":55},
	        		  {align:'center',field:"default_value",title:"默认值","width":10},
	        		  {align:"center",field:"xg","title":"修改","width":10,"formatter":xg},
	        		  {align:"center",field:"ch","title":"删除","width":10,"formatter":ch},
	        		  {align:'center',field:"is_active",title:"启(用)修改","width":14,"formatter":f_off_on},
	        		]],
	    	pagination:true,
	    	fitColumns:true,
	    	fit:true,
	        toolbar:toolbar1
		});
	}
	
	var toolbar1 = [{
	    text:'新增',
	    iconCls:'icon-add',
	    handler:function(){
	    	if(disid == 0){
	    		$.messager.alert('提示信息','请先选择疾病信息！','error');
	    		return;
	    	}
	    	$("#dlg-add").dialog({
	    		title:'新增健康建议信息',
	    		href:'addSuggestion.action?disease_id='+disid
	    	});
	    	$("#dlg-add").dialog("open");
	    }
	    }];
	function xg(val,row){
		 var str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:updatesug(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>'
		 return str;
	 }
	function ch(val,row){
		 var str = '&nbsp;&nbsp;&nbsp;&nbsp;' + '<a href=\"javascript:deletesug(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>'
		 return str;
	 }
	
	function updatesug(id){
				$("#dlg-add").dialog({
				title:'修改健康建议信息',
				href:'updateSuggestion.action?sug_id='+id
				});
				$("#dlg-add").dialog('open');
	}
    function deletesug(id){
		$.messager.confirm('提示信息','是否确定删除该条建议？',function(r){
			if(r){
		    $.ajax({
	   		url:'deleteSuggestion.action?sug_id='+id,
	   		type:'post',
	   		success:function(data){
	   			$.messager.alert('提示信息',data);
	   			getsuggestionGrid(disid);
	   		},
	   		error:function(){
	   			$.messager.alert('提示信息','操作失败！','error');
	   		}
	   		});
			}
		})
	}
    //启（停）修改
    function f_off_on(val,row){
    	 var html='';
    	    if(val=="Y"){
    	        return '<a style="color:#f00;" href=\"javascript:f_qt(\''+row.id+'\',\'停用\')\">停用</a>';
    	    }else if(val=='N') {
    	       return '<a style="color:#1CC112;" href=\"javascript:f_qt(\''+row.id+'\',\'启用\')\">启用</a>';
    	    }
    	}
    
    function f_qt(id,html){
    	$.messager.confirm('提示信息','是否确认'+html+'该条记录？',function(r){
    		if(r){
    			$.ajax({
	         	url:'diseaseSuggestionOffOn.action?sug_id='+id,
	         	type:'post',
	         	success:function(data){
	         		var obj=eval("("+data+")");
	         		if(obj=='success'){
	         			$.messager.alert('提示信息',html+"该条记录成功！");
	         			getsuggestionGrid(disid);
	         		}else if(obj=='error'){
	         			$.messager.alert('提示信息',html+"该条记录失败！",'error');
	         		}else{
	         				$.messager.alert('提示信息',obj);
	         		}
	         	},
	         	error:function(){
	         		$.messager.alert('提示信息','操作失败！','error');
	         	}
    			});
    		}
    	})
    }
	