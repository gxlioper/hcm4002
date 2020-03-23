$(document).ready(function () {
	
	 $('#sendflag').combobox({
		 	data:[{
		 		id:'0',value:'不限'
		 	},{
		 		id:'1',value:'成功'
		 	},{
		 		id:'2',value:'失败'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    panelHeight:'auto',
		    prompt:'请选择发送状态'
	 });
	 $('#com_Name').textbox('textbox').bind('click', function() {  
			select_com_list(this.value);
	    }); 
		
		$('#com_Name').textbox('textbox').bind('keyup', function() {
			select_com_list(this.value);
	    });
		
		$('#com_Name').textbox('textbox').bind('blur', function() {  
			select_com_list_over();
	    });
		$('#exam_num').css('ime-mode','disabled');
		$('#exam_num').focus();
	 getInterfaceList();
});
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
		$("#company_id").val(id);
		$("#com_Name").textbox('setValue',name);
		$("#com_name_list_div").css("display","none");		
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

/**
 * 清空查询
 */
function empty(){
	 $('#user_name').val("");
	 $('#exam_num').val("");
	 $('#join_datestart').datebox('setValue','');
	 $('#join_datezend').datebox('setValue','');
	 $('#examflags').combobox('setValue','');
	 getInterfaceList();
}

function getInterfaceList(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $("#interfaceshow").datagrid({
		 url:'getInterResultList.action',
		 queryParams:{
			 exam_num:$('#exam_num').val(),
			 user_name:$('#user_name').val(),
			 join_datestart:$('#join_datestart').datebox('getValue'),
			 join_dateend:$('#join_dateend').datebox('getValue'),
			 sendflag:$('#sendflag').combobox('getValue'),
			 company_id:$('#company_id').val()
		 },
		 toolbar:toolbar,
//		 dataType: 'json',
		 rownumbers:false,
		 fit:true,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[	
			 {field:'ck',checkbox:true },
			 {align:'center',field:'exam_num',title:'体检号',width:20},
		        {align:'center',field:'user_name',title:'姓名',width:18},
		        {align:'center',field:'dep_name',title:'科室',width:10},
	            {align:'center',field:'exam_types',title:'体检类型',width:10},
			 	{align:'center',field:'company_name',title:'工作单位',width:25},
			 	{align:'center',field:'item_name',title:'项目',width:15},
				{align:'center',field:'send_flags',title:'发送状态',width:15}
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
}
function impuser(list){
	 $.messager.confirm('提示信息','是否确定将选择行上传？',function(r){
		 	if(r){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
		 $.ajax({
			  
				url : 'againApply.action',
				data : {
					list:list
						},
						type : "post",//数据发送方式   
						success : function(data) {
							$(".loading_div").remove();
								$.messager.alert("操作提示", data);	
								 $("#interfaceshow").datagrid("reload");
						},
						error : function(data) {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", data);					
						}
					});

}
	 });
	
	
}

function impalluser(){
	
	 $.messager.confirm('提示信息','是否确定将所有信息上传？',function(r){
		 	if(r){
		 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 		 $("body").prepend(str);
	 $.ajax({
			url : 'againAll.action',
			data : {
				 exam_num:$('#exam_num').val(),
				 chi_name:$('#user_name').val(),
				 join_datestart:$('#join_datestart').datebox('getValue'),
				 join_dateend:$('#join_dateend').datebox('getValue'),
				 sendflag:$('#sendflag').combobox('getValue')
				},
					type : "post",//数据发送方式   
					success : function(data) {
						$(".loading_div").remove();
							$.messager.alert("操作提示", data);							
							$("#interfaceshow").datagrid("reload");
					},
					error : function(data) {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", data);					
					}
				});
}
	 });
	
} 
//----------------------------------定义工具栏---------------------------
var toolbar = [{
	text:'选择数据重发',
	width:180,
	iconCls:'icon-save',
    handler:function(){
    	var checkedItems = $('#interfaceshow').datagrid('getChecked');
    	var strs=[];
    	var list="";
    	if(checkedItems.length==0){
    		$.messager.alert("操作提示", "请选择需要重发信息");		
    	}else{
    		 $.each(checkedItems, function(index, item){
    	    	strs.push(item.exam_num+"!!"+item.charge_item_id);
    	    }); 
    		 list=strs.join(',');
    		 impuser(list);
    	}
    }
},{
	text:'全部重发',
	width:160,
	iconCls:'icon-check',
    handler:function(){
    	var checkedItems = $('#interfaceshow').datagrid('getChecked');
    	impalluser();
    }
}
];
