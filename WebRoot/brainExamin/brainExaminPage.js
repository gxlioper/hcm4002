var ji = 0;
$(function(){
	//追加内容
	$("#titleText").append('体检管理');
	$(".titlelist").show();
	//加载左侧列表
	intiLeftMenu();
}); 

//初始化左侧菜单
function intiLeftMenu(){
	$.ajax({
		url:'querySelectType.action',
		type:'post',
		//data: {},
		success:function(data){
			var message=eval("("+data+")");
			var title = "";
			var div1 ="<div class='easyui-accordion' style='width:193px;'>"; 
			for(var i=0;i<message.length;i++){
			    var div2 = 
			    	"<div title='"+message[i].quest_sub_name+"'>" +
			    		"<ul class='ulOne'>" +
			    			"<a href='javascript:void(0)' onclick='showTitleList(&#39;"+message[i].quest_sub_code+"&#39;,&#39;"+message[i].quest_sub_name+"&#39;)'><li>标题</li></a>" +
			    			"<a href='javascript:void(0)' onclick='showProjectList(&#39;"+message[i].quest_sub_code+"&#39;,&#39;"+message[i].quest_sub_name+"&#39;)'><li>项目</li></a>" +
			    		"</ul>"+
			    	"</div>" ;
			    title += div2;
			}
			var div3 = "</div>";
			$("#titleList").append(div1+title+div3);
   			$.parser.parse($("#titleList")); //渲染
		},
		error:function(){
			$.messager.alert('提示信息','初始化问卷类型失败','error');
		}
	});
}

function queryBrainTitleMsg(){
	//根据标识符title_ji判断
	if($("#title_ji").val()==0){
		getBrainrGrid($('#title_type').val());
	}else{
		//获取Id  name
		addBrainTitleChild($("#title_lei").val());
	}
}

function getBrainrGrid(titleType){
	//alert(titleType);
	var titleToolbar=[
	    {
			text:'新增标题',
			iconCls:'icon-add',
		    handler:function(){
		    	$("#dlg-addEdit").dialog({
		    		title:'新增标题',
		    		href:'titleAddEdit.action?titleName=add'
		    	});
		    	$("#dlg-addEdit").dialog("open");
		    }
	    },
	    {
			text:'修改标题',
			iconCls:'icon-edit',
		    handler:function(){
		    	$("#dlg-addEdit").dialog({
		    		title:'修改标题',
		    		href:'titleAddEdit.action?titleName=edit'
		    	});
		    	var rowInfo = $("#titlelist").datagrid('getSelected'); //获得选中行
		    	var rowIndex=$('#titlelist').datagrid('getRowIndex',rowInfo);//行号
		    	if(rowInfo){
		    		$("#dlg-addEdit").dialog("open");
		    	}else{
		    		$.messager.alert('提示信息', "请先选中某一行修改");
		    	};
		    	
		    }
	    },
	    {
			text:'删除标题',
			iconCls:'icon-cancel',
		    handler:function(){
		    	var rowInfo = $("#titlelist").datagrid('getSelected'); //获得选中行
		    	if(rowInfo){
		    		$.messager.confirm('提示信息','是否确定删除该标题吗？',function(r){
			    		if(r){
			    			$.ajax({
				       		url:'deleteBrainTitle.action?titleID='+rowInfo['titleID'],
				       		type:'post',
				       		success:function(data){
				       			var message=eval("("+data+")");
				       			$.messager.alert('提示信息',message);
				       			if($("#title_ji").val()==0){
				          	 		showTitleList($("#title_type").val(),$("#titleText").val());
				          	 	}else{
				          	 		addBrainTitleTwo($("#title_lei").val(),$("#titleText").text(),"refresh",$("#title_ji").val());
				          	 	}
				       		},
				       		error:function(){
				       			$.messager.alert('提示信息','操作失败！','error');
				       		}
				       		});
			    		}
			    	})
		    	}else{
		    		$.messager.alert('提示信息', "请先选中某一行进行删除");
		    	};
		    	
		    }
	    }
	    ];
	
     $("#titlelist").datagrid({
    	 url:'queryBrainTitle.action',
    	 dataType: 'json',
		 queryParams:{
			 titleName:$('#title_name1').val().trim(),
			 quest_sub_code:titleType
		 },
		 toolbar:titleToolbar,
		 rownumbers:false,
		 height:510,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	        {field:'ck',checkbox:true},
	        {align:'center',field:'titleID',title:'序号',width:5},
            {align:'left',field:'titleName',title:'标题名称',width:24},
		 	{align:'center',field:'supID',title:'所属类别',width:20,"formatter":title_sslb},
		 	{align:'center',field:'level',title:'级别',width:14,"formatter":title_ssjb},
		 	{align:'center',field:'seqNo',title:'顺序号',width:8},
		 	{align:'center',field:'titleColumn',title:'列数',width:8 ,hidden:'true'},
		 	{align:'center',field:'isVisable',title:'显示',"formatter":title_sfxs},
		 	{align:'center',field:'title_tjzbt',title:'操作',width:18,"formatter":title_tjzbt}
	 	]],	    	
		   	onLoadSuccess:function(value){
		   		$("#datatotal").val(value.total);
		   		$(".loading_div").remove();
		   	},
		    fitColumns:true,//自适应
		    singleSelect:true,
			pagination:true,//分页控件
		    striped:true,
		   onClickRow: function (rowIndex, rowData) {
                $(this).datagrid('unselectRow', rowIndex);
		   	}
      
     });
}
//所属类别
function title_sslb(val,row){
	if (val == 0){
		return "顶级父标题";
	}else if(val == 1){
		return "子标题";
	}
}

//所属级别
function title_ssjb(val,row){
	if (val == 0){
		return '一级标题';
	}else if(val == 1){
		return '二级标题';
	}else{
		return '三级标题';
	}
}
//是否显示
function title_sfxs(val,row){
	if (val == 0){
		return '否';
	} else {
		return '是';
	}
}

//添加子标题
function title_tjzbt(val, row) {
	return  '<a href=\"javascript:addBrainTitleTwo(\''+row.titleID+'\',\''+row.titleName+'\',\'enter\')\" class="easyui-linkbutton">添加子标题</a>';
}

//添加子标题
function addBrainTitleTwo(id,name,msg,cengJi){
	//菜单级别
	if(msg=="enter"){
		ji++;
		$("#title_ji").val(ji);
	}else if(msg=="refresh"){
		$("#title_ji").val(cengJi);
		ji = cengJi;
	}
	$("#title_lei").val(id); //类别
	//清空text
	var cont = $("#titleText").html();  //获得html内容
	var contText = $("#titleText").text(); //获得text内容
	
	if(!contText.endsWith(name)){
		//点击 是以name结尾  不用拼接
		$("#titleText").empty();
		if(contText.indexOf(name)>=0){
			//包涵  去除name后的内容
			$("#titleText").append(cont.substring(0,cont.lastIndexOf(name)+name.length+4));
		}else{
			$("#titleText").append(cont+'&nbsp;&nbsp; >>&nbsp;&nbsp; <a href="javaScript:addBrainTitleTwo(&#39;'+id+'&#39;,&#39;'+name+'&#39;,&#39;refresh&#39;,&#39;'+ji+'&#39;)">'+name+'</a>');
		}
	}
	addBrainTitleChild(id);
}

function addBrainTitleChild(id){
     $("#titlelist").datagrid({
    	 url:'queryProjectTwoGrid.action',
    	 dataType: 'json',
		 queryParams:{
			 titleID:id,
			 titleName:$('#title_name1').val().trim()
		 },
		 //toolbar:titleToolbarChild,
		 rownumbers:false,
		 height:510,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	        {field:'ck',checkbox:true},
	        {align:'center',field:'titleID',title:'序号',width:5},
            {align:'left',field:'titleName',title:'标题名称',width:24},
		 	{align:'center',field:'supID',title:'所属类别',width:20},
		 	{align:'center',field:'level',title:'级别',width:14,"formatter":title_ssjb},
		 	{align:'center',field:'seqNo',title:'顺序号',width:8},
		 	{align:'center',field:'titleColumn',title:'列数',width:8 ,hidden:'true'},
		 	{align:'center',field:'isVisable',title:'显示',"formatter":title_sfxs},
		 	{align:'center',field:'title_tjzbt',title:'操作',width:18,"formatter":title_tjzbt}
	 	]],	    	
		   	onLoadSuccess:function(value){
		   		$("#datatotal").val(value.total);
		   		$(".loading_div").remove();
		   	},
		    fitColumns:true,//自适应
		    singleSelect:true,
			pagination:true,//分页控件
		    striped:true,
		    /*onDblClickRow:function(index, row){
		    	var row_id = $('#titlelist').datagrid('getRows')[index].id;
		    	//更新
		    	updateSampleDemo(row_id);
	       },*/
		   onClickRow: function (rowIndex, rowData) {
                $(this).datagrid('unselectRow', rowIndex);
		   	}
      
     });
}

//展示脑标题模块
function showTitleList(type,name){
	//位类型赋值
	$("#title_type").val(type);
	$("#title_ji").val(0);
	ji = 0;
	$("#title_lei").val("")
	//清空text
	$("#titleText").empty();
	$("#titleText").append('<a href="javaScript:showTitleList(&#39;'+type+'&#39;,&#39;'+name+'&#39;)">'+name+'</a>');
	//清空输入框
	$("#title_name1").textbox('setValue','')
	
	$(".titlelist").show();
	$(".projectlist").hide();
	$(".modulelist").hide();
	$(".stylelist").hide();
	
	getBrainrGrid(type);
}

//展示脑项目模块
function showProjectList(type,name){
	$("#title_type").val(type);
	$("#title_ji").val(0);
	ji = 0;
	$("#title_lei").val("")
	//清空text
	$("#titleText").empty();
	$("#titleText").append('<a href="javaScript:showProjectList(&#39;'+type+'&#39;,&#39;'+name+'&#39;)">'+name+'</a>'); 
	//清空输入框
	$("#title_name2").textbox('setValue','')
	
	$(".titlelist").hide();
	$(".projectlist").show();
	$(".modulelist").hide();
	$(".stylelist").hide();
	//加载module表格信息
	getProjectGrid(type);
	document.getElementById('questionlist2').style.display='none'
    document.getElementById('projectlist2').style.display='block';
}

//展示脑体检模块
/*function showModuleList(){
	//清空text
	$("#titleText").empty();
	$("#titleText").append('<a href="javaScript:getModuleGrid()">脑体检模块</a>');
	
	$(".titlelist").hide();
	$(".projectlist").hide();
	$(".modulelist").show();
	$(".stylelist").hide();
	//加载module表格信息
	getModuleGrid();
}*/

//展示样式模块
/*function showStyleList(){
	//清空text
	$("#titleText").empty();
	$("#titleText").append('<a href="javaScript:getStyleGrid()">主题样式</a>');
	
	$(".titlelist").hide();
	$(".projectlist").hide();
	$(".modulelist").hide();
	$(".stylelist").show();
	//加载style表格信息
	getStyleGrid();
}*/

//脑健康 项目查询
function queryProjectGrid(){
	//alert($("#title_ji").val());
	if($("#title_ji").val()==0){
		getProjectGrid($('#title_type').val());
	}else if($("#title_ji").val()==1){
		getProjectTwoGrid($("#sup_item_id").val());
	}else if($("#title_ji").val()==2){
		getQuestionGrid($("#add_title_id").val(),0);
	}else if($("#title_ji").val()>2){
		getQuestionGrid($("#add_title_id").val(),$("#sup_item_id").val());
	}
}



function getProjectGrid(titleType){
     $("#projectlist").datagrid({
    	 url:'queryBrainTitle.action',
    	 dataType: 'json',
		 queryParams:{
			 titleName:$('#title_name2').val().trim(),
			 quest_sub_code:titleType
		 },
		 //toolbar:projectToolbar,
		 rownumbers:false,
		 //height:510,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	        {field:'ck',checkbox:true},
	        {align:'center',field:'titleID',title:'序号',width:8},
            {align:'left',field:'titleName',title:'标题名称',width:22},
		 	{align:'center',field:'supID',title:'所属类别',width:15,"formatter":title_sslb},
		 	{align:'center',field:'project_ss',width:18,title:'操作',"formatter":project_cz}
	 	]],	    	
		   	onLoadSuccess:function(value){
		   		$("#datatotal").val(value.total);
		   		$(".loading_div").remove();
		   	},
		    fitColumns:true,//自适应
		    singleSelect:true,
			pagination:true,//分页控件
		    striped:true,
		   	onClickRow: function (rowIndex, rowData) {
                $(this).datagrid('unselectRow', rowIndex);
		   	}
      
     });
}



//project的操作
function project_cz(val,row){
	return  '&nbsp;&nbsp;<a href=\"javascript:addInProjectTwo(\''+row.titleID+'\',\''+row.titleName+'\',\''+row.itemId+'\',\''+row.itemName+'\',\'enter\')\" class="easyui-linkbutton">进入问卷项目</a>&nbsp;&nbsp;'
}

//添加子项目
function addInProjectTwo(id,name,itemId,itemName,msg,cengJi){
	//msg 为信息标识符号 如果msg==enter 代表进入子项目 msg==refresh 代表刷新该项目
	document.getElementById('questionlist2').style.display='none';
	if(msg=="enter"){
		ji++;
		$("#title_ji").val(ji);
	}else if(msg=="refresh"){
		$("#title_ji").val(cengJi);
		ji = cengJi;
	}
	$("#add_title_id").val(id);
	
	var cont = $("#titleText").html();  //获得html内容
	var contText = $("#titleText").text(); //获得text内容
	if(name!=null && name!=''){
		if(!contText.endsWith(name)){
			//点击 是以name结尾  不用拼接
			$("#titleText").empty();
			if(contText.indexOf(name)>=0){
				//包涵  去除name后的内容
				$("#titleText").append(cont.substring(0,cont.lastIndexOf(name)+name.length+4));
			}else{
				var stra="";//原始字符串
				if(name.length>10){
					stra=name.substring(0,8)+"...";
				}else{
					stra=name
				}
				$("#titleText").append(cont+'&nbsp;&nbsp; >>&nbsp;&nbsp; <a href="javaScript:addInProjectTwo(&#39;'+id+'&#39;,&#39;'+name+'&#39;,&#39;'+itemId+'&#39;,&#39;'+itemName+'&#39;,&#39;refresh&#39;,&#39;'+ji+'&#39;)">'+stra+'</a>');
			}
		}
	}else{
		if(!contText.endsWith(itemName)){
			//点击 是以name结尾  不用拼接
			$("#titleText").empty();
			if(contText.indexOf(itemName)>=0){
				//包涵  去除name后的内容
				$("#titleText").append(cont.substring(0,cont.lastIndexOf(itemName)+itemName.length+4));
			}else{
				var strb="";//原始字符串
				if(itemName.length>10){
					strb=itemName.substring(0,8)+"...";
				}else{
					strb=itemName
				}
				$("#titleText").append(cont+'&nbsp;&nbsp; >>&nbsp;&nbsp; <a href="javaScript:addInProjectTwo(&#39;'+id+'&#39;,&#39;'+name+'&#39;,&#39;'+itemId+'&#39;,&#39;'+itemName+'&#39;,&#39;refresh&#39;,&#39;'+ji+'&#39;)">'+strb+'</a>');
			}
		}
	}
	
	//加载问卷信息
	getQuestionGrid(id,itemId);
	//加载title表信息二阶段信息  
	getProjectTwoGrid(id);
	
}

//二级项目
function getProjectTwoGrid(id){
     $("#projectlist").datagrid({
    	 url:'queryProjectTwoGrid.action',
    	 dataType: 'json',
		 queryParams:{
			 titleID:id.toString(),  //父类ID
			 titleName:$('#title_name2').val().trim()
		 },
		 //toolbar:projectToolbar,
		 rownumbers:false,
		 //height:510,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	        {field:'ck',checkbox:true},
	        {align:'center',field:'titleID',title:'序号',width:5},
            {align:'left',field:'titleName',title:'标题名称',width:24},
		 	{align:'center',field:'supID',title:'所属类别',width:20},
		 	{align:'center',field:'level',title:'级别',width:20,"formatter":title_sslb},
		 	{align:'center',field:'seqNo',title:'顺序号',width:10},
		 	{align:'center',field:'project_ss',title:'操作',"formatter":project_cz}
	 	]],	    	
		   	onLoadSuccess:function(value){
		   		$("#datatotal").val(value.total);
		   		$(".loading_div").remove();
		   	},
		    fitColumns:true,//自适应
		    singleSelect:true,
			pagination:true,//分页控件
		    striped:true,
	       onClickRow: function (rowIndex, rowData) {
               $(this).datagrid('unselectRow', rowIndex);
		   	}
      
     });
     
}

//加载二级标题的下的项目
function getQuestionGrid(id,itemId){
	
	if(itemId==0){$("#sup_item_id").val(id);
	}else{$("#sup_item_id").val(itemId);}
	
	if($("#title_ji").val() > 1){
		document.getElementById('questionlist2').style.display='block'; //问题
		document.getElementById('projectlist2').style.display='none';  //标题
	} else if($("#title_ji").val()==1){
		document.getElementById('projectlist2').style.display='block';  //标题
	}
	
	var questionToolbar=[
  	    {
  			text:'添加项目',
  			iconCls:'icon-add',
  		    handler:function(){
  		    	$("#pro-addEdit").dialog({
  		    		title:'添加项目',
  		    		href:'projectAddEdit.action?titleName=add'
  		    	});
  		    	$("#pro-addEdit").dialog("open");
  		    }
  	    },
  	    {
  			text:'修改项目',
  			iconCls:'icon-edit',
  		    handler:function(){
  		    	$("#pro-addEdit").dialog({
  		    		title:'修改项目',
  		    		href:'projectAddEdit.action?titleName=edit'
  		    	});
  		    	var rowInfo = $("#questionlist").datagrid('getSelected'); //获得选中行
  		    	var rowIndex=$('#questionlist').datagrid('getRowIndex',rowInfo);//行号
  		    	if(rowInfo){
  		    		$("#pro-addEdit").dialog("open");
  		    	}else{
  		    		$.messager.alert('提示信息', "请先选中某一行修改");
  		    	};
  		    	
  		    }
  	    },
  	    {
  			text:'删除项目',
  			iconCls:'icon-cancel',
  		    handler:function(){
  		    	var rowInfo = $("#questionlist").datagrid('getSelected'); //获得选中行
  		    	if(rowInfo){
  		    		$.messager.confirm('提示信息','是否确定删除该项目吗？',function(r){
  			    		if(r){
  			    			$.ajax({
  				       		url:'deleProjectQuest.action?itemId='+rowInfo['itemId'],
  				       		type:'post',
  				       		success:function(data){
  				       			var message=eval("("+data+")");
  				       			$.messager.alert('提示信息',message);
	  				       		if($("#title_ji").val() < 3){
	  			          	 		addInProjectTwo($("#add_title_id").val(),$("#titleText").text(),"",$("#titleText").text(),"refresh",$("#title_ji").val());
	  			          	 	}else{
	  			          	 		addInProjectTwo($("#add_title_id").val(),$("#titleText").text(),$("#sup_item_id").val(),$("#titleText").text(),"refresh",$("#title_ji").val());
	  			          	 	}
  				       		},
  				       		error:function(){
  				       			$.messager.alert('提示信息','操作失败！','error');
  				       		}
  				       		});
  			    		}
  			    	})
  		    	}else{
  		    		$.messager.alert('提示信息', "请先选中某一行进行删除");
  		    	};
  		    	
  		    }
  	    }
  	 ];
	
    $("#questionlist").datagrid({
   	 url:'getQuestionGrid.action',
   	 dataType: 'json',
		 queryParams:{
			 titleID:id.toString(),  //父类ID
			 Itemlevel:$("#title_ji").val()-2,
			 supItemId:itemId,
			 titleName:$('#title_name2').val().trim()
		 },
		 toolbar:questionToolbar,
		 rownumbers:false,
		 //height:510,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	        {field:'ck',checkbox:true},
	        {align:'center',field:'itemId',title:'序号',width:5},
            {align:'left',field:'itemName',title:'问卷项目名称',width:24},
		 	{align:'center',field:'supItemId',title:'所属类别',width:20,"formatter":question_sslb},
		 	{align:'center',field:'itemlevel',title:'级别',width:12,"formatter":question_jb},
		 	{align:'center',field:'isDefault',title:'是否有默认值',width:13,"formatter":question_isDefault},
		 	{align:'center',field:'defaultResult',title:'默认值',width:10,hidden:'true'},
		 	{align:'center',field:'seqNo',title:'顺序号',width:10},
		 	{align:'center',field:'itemCode',title:'项目编码',width:10},
		 	{align:'center',field:'isVisable',title:'是否显示',width:10,"formatter":title_sfxs},
		 	{align:'center',field:'isMulSel',title:'项目类别',width:10,"formatter":question_xmlb},
		 	{align:'center',field:'linkNo',title:'关联代码',width:10},
		 	{align:'center',field:'project_ss',title:'操作',"formatter":project_cz}
	 	]],	    	
		   	onLoadSuccess:function(value){
		   		$("#datatotal").val(value.total);
		   		$(".loading_div").remove();
		   	},
		    fitColumns:true,//自适应
		    singleSelect:true,
			pagination:true,//分页控件
		    striped:true,
	       onClickRow: function (rowIndex, rowData) {
              $(this).datagrid('unselectRow', rowIndex);
		   	}
     
    });
}
//所属类别
function question_sslb(val,row){
	if(val==0) {
		return "顶级父项目 ";
	}else{
		return val;
	};
}

//是否默认
function question_isDefault(val,row){
	if(val==0) {
		return "否 ";
	}else{
		return "是";
	};
}


//级别
function question_jb(val,row){
	if(val==0) return "顶级父项目 ";
	if(val==1) return "父项目 ";
	if(val==1) return "子项目 ";
}

//项目类别
function question_xmlb(val,row){
	if(val==0) return "单选 ";
	if(val==1) return "复选 ";
	if(val==2) return "文本单位 ";
	if(val==3) return "文本输入框";
}

//加载module表格信息
/*function getModuleGrid(){
     $("#modulelist").datagrid({
    	 url:'queryModuleList.action',
    	 dataType: 'json',
		 queryParams:{
			 titleName:$('#title_name3').val().trim()
		 },
		 //toolbar:toolbar,
		 rownumbers:false,
		 height:510,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	        {field:'ck',checkbox:true},
	        {align:'center',field:'menuId',title:'序号',width:5},
            {align:'center',field:'menuTitle',title:'问卷标题名称',width:24},
		 	{align:'center',field:'menuTitleFather',title:'所属类别级别',width:20},
		 	{align:'center',field:'seqNo',title:'顺序号',width:10},
		 	{align:'center',field:'tablebodercolor',title:'页码样式',width:14},
		 	{align:'center',field:'menuPagequestNum',title:'分页组合',width:12},
		 	{align:'center',field:'meunquestNum',title:'共有多少小题',width:12},
		 	{align:'center',field:'modulEdit',title:'操作',"formatter":modul_cz}
	 	]],	    	
		   	onLoadSuccess:function(value){
		   		$("#datatotal").val(value.total);
		   		$(".loading_div").remove();
		   	},
		    fitColumns:true,//自适应
		    singleSelect:true,
			pagination:true,//分页控件
		    striped:true,
		    onDblClickRow:function(index, row){
		    	var row_id = $('#titlelist').datagrid('getRows')[index].id;
		    	//更新
		    	updateSampleDemo(row_id);
	       },
	       onClickRow: function (rowIndex, rowData) {
               $(this).datagrid('unselectRow', rowIndex);
		   	}
      
     });
}*/

//操作
function modul_cz(){
	return  '&nbsp;&nbsp;<a href="#" class="easyui-linkbutton">设置菜单</a>&nbsp;&nbsp;'
}


//加载Style表格信息
/*function getStyleGrid(){
     $("#stylelist").datagrid({
    	 url:'queryStyleList.action',
    	 dataType: 'json',
		 queryParams:{
			  titleName:$('#title_name4').val().trim()
		 },
		 //toolbar:toolbar,
		 rownumbers:false,
		 height:510,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	        {field:'ck',checkbox:true},
	        {align:'center',field:'partStyleId',title:'序号',width:5},
            {align:'center',field:'partStyleTitle',title:'主题名称',width:24},
		 	{align:'center',field:'tCss',title:'主题样式',width:15},
		 	{align:'center',field:'tlogo',title:'主题图标',width:20,"formatter":style_logo},
		 	{align:'center',field:'modulEdit',title:'操作',"formatter":style_cz}
	 	]],	    	
		   	onLoadSuccess:function(value){
		   		$("#datatotal").val(value.total);
		   		$(".loading_div").remove();
		   	},
		    fitColumns:true,//自适应
		    singleSelect:true,
			pagination:true,//分页控件
		    striped:true,
		    onDblClickRow:function(index, row){
		    	var row_id = $('#titlelist').datagrid('getRows')[index].id;
		    	//更新
		    	updateSampleDemo(row_id);
	       },
	       onClickRow: function (rowIndex, rowData) {
               $(this).datagrid('unselectRow', rowIndex);
		   	}
      
     });
}*/

//操作
function style_cz(){
	return  '&nbsp;<a href="#" class="easyui-linkbutton">修改</a>&nbsp;&nbsp;&nbsp;'+
			'&nbsp;<a href="#" class="easyui-linkbutton">删除</a>&nbsp;';
}

//style 图标
function style_logo(val,row){
	return '<img src="'+val+'"  width="50px"/>';
}

//清空查询内容
function queryEmpty(){
	$("#title_name1").textbox('setValue','')
	$("#title_name2").textbox('setValue','')
	$("#title_name3").textbox('setValue','')
	$("#title_name4").textbox('setValue','')
}
