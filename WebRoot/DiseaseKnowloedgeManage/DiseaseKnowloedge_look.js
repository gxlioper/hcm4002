/**
 * 
 */
//查询按钮
	function searchFun(){
		getGrid();
		getsuggestionGrid(0);
	}
	//清空按钮
	function cleanFun(){
		$("#disease_type_s").val('');
		$("#disease_name_s").textbox('setValue',"");
		$("#disease_level_z").combobox('setValue',"");
		getGrid();
		getsuggestionGrid(0);
	}
	
	/*//工具按钮
	var toolbar = [{
		    text:'新增',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#dlg-edit").dialog({
		    		title:'新增疾病信息',
		    		href:'addDKLdge.action'
		    	});
		    	$("#dlg-edit").dialog("open");
		    }
		    },{
		    	text:'删除',
		    	width:120,
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
	*/
	 //编辑按钮
	function f_bj(val,row){
			return '<a href=\"javascript:updateDKLdge(\''+row.id+'\')\">编辑</a>';
	}
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
	/*//编辑
	function updateDKLdge(id){
				$("#dlg-edit").dialog({
				title:'编辑',
				href:'updateDKLdge.action?id='+id
				});
			   $("#dlg-edit").dialog('open');
	}*/
	/**
	 * 批量删除
	 * 
	 */
/*	function deluserrow(ids){
		if(ids==""){
			$.messager.alert('提示信息','请选择要删除的行!')
			return;
		}
		$.messager.confirm('提示信息','是否确定删除选中的行',function(r){
		 	if(r){
		 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
				 $.ajax({
					url : 'deleteDklodeg.action',
					data : {ids:ids},
					type : "post",
					success : function(data) {
							$.messager.alert("操作提示",data);
							getGrid();
							getsuggestionGrid(0);
					},
					error : function() {
						$.messager.alert('提示信息', '操作失败！', 'error');
					}
				 });
		 	 }
		 });
	}*/
	
	//疾病页面数据加载
	function getGrid(){
	       var model = {"disease_type": $('#disease_type_s').val(),"disease_name":$('#disease_name_s').textbox('getValue'),"disease_level":$('#disease_level_z').combobox('getValue')};
	        $("#diseaseKnowloedgeList").datagrid({
			url: 'diseaseList.action',
			queryParams: model,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	        //toolbar: '#toolbar',
	        columns:[[
	        		  {align:"center",field:"icd_10",title:"ICD编码","width":15},
	        		  {align:'center',field:"disease_type_d",title:"疾病类型","width":20},
	        		  {align:'center',field:"disease_name",title:"疾病名称","width":22},
	        		  {align:'center',field:"disease_level",title:"疾病级别","width":20},
	        		  {align:'center',field:"disease_classification_d",title:"疾病分类","width":20},
	        		  {align:"center",field:"updater",title:"更新者","width":15},
	        		  //{align:"center",field:"update_time",title:"更新时间","width":30},
	        		  {align:"center",field:"chakan",title:"查看","width":12,"formatter":f_ck},
	        		  //{align:"center",field:"bianji",title:"编辑","width":12,"formatter":f_bj}
	        		  ]],
	        		  onLoadSuccess:function(value){
	      		    	$("#datatotal").val(value.total);
	      		    },
	      		  singleSelect:true,
			      pagination:true,
			      fitColumns:true,
			      fit:true,
			      //toolbar:toolbar,
			      onClickRow:function(index,row){
	        	 getsuggestionGrid(row.id);
	        }
		});
		
		
	}
	//数据保存
/*	function f_dklgsave(){
		if (document.getElementById("disease_name").value == ''){
			$("#disease_name").focus();
			return;
		}else if(document.getElementById("disease_pinyin").value == ''){
			$("#disease_pinyin").focus();
			return;
		}else if (document.getElementById("disease_num").value == ''){
			$("#disease_num").focus();
			return;
		}else if($("#message").attr('value') == 'no'){
			$("#disease_num").focus();
			return;
		}
	$.ajax({
		
	url:'saveDiseaseKLg.action',  
	data:{
		id:$("#id").val(),
		disease_type:$("#disease_type").val(),
		disease_name:$("#disease_name").val(),
		disease_pinyin:$("#disease_pinyin").val(),
		disease_level:$("#disease_level").combobox('getValue'),
		disease_classification:$("#disease_classification").combobox('getValue'),
		disease_num:$("#disease_num").val(),
		icd_10:$("#icd_10").val(),
		isActive:$("#isActive").val(),
		disease_description:$("#disease_description").val(),
		disease_evendice:$("#disease_evendice").val(),
		disease_suggestion:$("#disease_suggestion").val(),
		//health_suggestion:$("#health_suggestion").val(),
	  },          
	type: "post",  
	  success: function(data){  
	  	 $.messager.alert('提示信息', data);
	  	 $("#dlg-edit").dialog("close");
	      	getGrid();
	      	getsuggestionGrid($("#id").val());
	  		
	    },
	    error:function(){
	    	 $("#dlg-edit").dialog("close");
	    	$.messager.alert('提示信息', "用户操作失败！",function(){});
	    }  
	});
}*/
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
	        		  {align:'center',field:"disease_suggestion",title:"健康建议","width":51},
	        		  {align:'center',field:"default_value",title:"默认值","width":10},
	        		  //{align:"center",field:"xg","title":"修改","width":12,"formatter":xg},
	        		  //{align:"center",field:"ch","title":"删除","width":12,"formatter":ch},
	        		  //{align:'center',field:"is_active",title:"启(用)修改","width":14,"formatter":f_off_on},
	        		]],
	    	pagination:true,
	    	fitColumns:true,
	    	fit:true,
	        //toolbar:toolbar1
		});
	}
	
	/*var toolbar1 = [{
	    text:'新增',
	    iconCls:'icon-add',
	    handler:function(){
	    	
	    	if(disid == 0){
	    		$.messager.alert('提示信息','请先选择疾病信息！','error');
	    		return;
	    	}
	    	
	    	$("#dlg-add").dialog({
	    		title:'新增疾病信息',
	    		href:'addSuggestion.action?disease_id='+disid
	    	});
	    	$("#dlg-add").dialog("open");
	    }
	    }];*/
	/*function xg(val,row){
		 var str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:updatesug(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>'
		 return str;
	 }
	function ch(val,row){
		 var str = '&nbsp;&nbsp;&nbsp;&nbsp;' + '<a href=\"javascript:deletesug(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>'
		 return str;
	 }
	
	function updatesug(id){
				$("#dlg-add").dialog({
				title:'修改建议',
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
    
    function f_qt(id,html)
    {
    	$.messager.confirm('提示信息','是否确认'+html+'该条记录？',function(r){
    		if(r){
    			$.ajax({
	         	url:'update_off_on.action?sug_id='+id,
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
    }*/
	