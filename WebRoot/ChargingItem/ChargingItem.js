$(document).ready(function () {
	getDepartment_dep();//科室下拉框--搜索框
	getguide_category();//导引单下拉框--搜索框
	getgroupuserGrid();//列表数据
	//var pager=$('#groupusershow').datagrid('options').pageNumber;
	//alert("页码"+pager);
	$('#pinyin').focus();
	//回车事件
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	getgroupuserGrid();
	     }
	}
});
/**
 * 清空查询
 */
function empty(){
	$('#item_code').val("");
	$('#item_name').val("");
	$('#view_num').val("");
	$('#exam_num').val("");
	$('#dep_id').clear();
	getgroupuserGrid();
}
//-------------------------------检索框------------------------------------
/**
 * 科室下拉框
 */
function getDepartment_dep(){
	$('#dep_id').combobox({    
	    url:'getDepartment_dep.action',
	    valueField:'id',    
	    textField:'dep_name'
	});
}
/**
 * 导引单类型
 */
function getguide_category(){
	$('#guide_category').combobox({
		panelHeight:100,
		panelMinWidth:200,
		url:"getDatadisKongGe.action?com_Type="+"DYDLX",
		valueField:'id',
		textField:'name'
	})
}
//-------------------------------显示收费项目------------------------------------
/**
 * 鼠标表格悬停，显示隐藏溢出内容
 */
function formatCellTooltip(value){ 
    return "<span title='" + value + "'>" + value + "</span>";  
} 

function brushpagecharging(){
	 $('#groupusershow').datagrid('reload');  
}
/**
 * 显示收费项目列表
 */
var mccf=""
var cfbg="";
var keshi="";//科室
var exam_item_id="";
 function getgroupuserGrid(){
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#groupusershow").datagrid({
		 url:'queryChargingItem.action',
		 queryParams:{
			 item_pinyin:$('#pinyin').val(),
			 item_code:$('#item_code').val(),	//收费项目编码
			 item_name:$('#item_name').val(),	//收费项目名称
			 view_num:$('#view_num').val(),		//影像系统编码
			 exam_num:$('#exam_num').val(),		//检验系统编码
			 his_num:$("#his_num").val(),		//his系统关联码
			dep_id:$('#dep_id').combobox('getValue'),//所属科室	
			  interface_flag:$('#interface_flag').combobox('getValue'),//接口标识
			guide_category:$('#guide_category').combobox('getValue'),//导引单分类	
			mccf:mccf,
			d_app:$('input[name="c_app_type"]:checked').val(),
			baogaochongfu:cfbg,
			isActive:'Y',
			ks:keshi,
			exam_item_id:exam_item_id
	 	 },
	 	 type:'post',
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize:10,	     
	    // pageNumber:page,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	            {align:'center',field:'item_code',title:'收费项目编码',width:18,"formatter":guanxi},	
	            {align:'left',field:'item_name',title:'收费项目名称',width:28},
			 	{align:'left',field:'d_name',title:'所属科室',width:15},
			 	{align:'left',field:'dname',title:'统计科室',width:15},
			 	{align:'left',field:'s_name',title:'所属样本',width:15},
			 	{align:'left',field:'report_name',title:'所属报告样本',width:20},
			 	{align:'center',field:'amount',title:'金额',width:15},
			 	{align:'left',field:'item_note',title:'项目描述',width:15,"formatter":formatCellTooltip},
			 	{align:'center',field:'his_num',title:'his关联码',width:15},
			 	{align:'center',field:'exam_num',title:'检验关联码',width:15},
			 	{align:'center',field:'view_num',title:'影像关联码',width:15},
			 	{align:'center',field:'f_name',title:'财务类别',width:10},
			 	{align:'center',field:'limit_count_s',title:'项目上限',width:10},
			 	{align:'center',field:'app_type_s',title:'应用类型',width:17},
			 	{align:'center',field:'center_name',title:'体检中心',width:17},
			 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
			 	{align:'center',field:'ss',title:'删除',width:10,"formatter":f_sc}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		mccf="";
	    		cfbg="";
	    		keshi="";
	    		exam_item_id="";
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
	    	singleSelect:false,
		    collapsible:false,
			pagination:true,//分页控件
		    fitColumns:true,//自适应
		    fit:true,
		    striped:true,
	        toolbar:toolbar,
	        singleSelect:false,//只允许选择一行
	        onDblClickRow:function(index, row){
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updateSampleDemo(row_id);
	        }
	});
}
 //---------------样本项目关系--------------
 function guanxi(value,row,index){
		return '<a href=\"javascript:guanxi_s(\''+row.id+'\')\">'+row.item_code+'</a>';
	}
	function guanxi_s(id){
		$("#dlg_s_show").dialog({
			title:'样本项目关系',
			href:'getItemSampleDemoPage.action?id='+id
		});
		$("#dlg_s_show").dialog('open');
	}
//-------------------------------------修改收费项目页面---------------------
 function f_xg(val,row){
	 
	return '<a href=\"javascript:updateSampleDemo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	/* str='<a href="updateChargingItemPage.action?id='+id">修改</a>';
	 return str;*/
}
/**
 * 修改收费项目
 * @param id
 */
 function updateSampleDemo(id){
	 var url='updateChargingItemPage.action?id='+id; 
	newWindow = window.open(url,"收费项目编辑", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newWindow.focus();
}
 
//------------------------------------删除收费项目----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 单个删除收费项目
 * @param id
 */
function f_deluser(id) {
	$.messager.confirm("提示信息","是否删除收费项目？",function(r){
		if(r){
			$.ajax({
				url : 'updateChargingItemDel.action?ids='+id,
				type : 'post',
				success : function(data) {
					$.messager.alert('提示信息', data);
					$('#groupusershow').datagrid('reload');
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}		
//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text : '新增收费项目',
	iconCls : 'icon-add',
	width : 120,
	closed: true,cache: false,modal: true,
	handler : function() {
		 //var url='updateChargingItemPage.action?id='+id; 
		 //window.open(url,"收费项目编辑",'updateChargingItemPage.action?id='+id);
		 var ul='addChargingItePage.action'; 
		 newWindow = window.open(ul,"收费项目编辑", "toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		 newWindow.focus();
	}
},{
		text : '项目名称重复',
		iconCls : 'icon-cards',
		width : 120,
		closed: true,cache: false,modal: true,
		handler : function() {
			mccf="项目名称重复";
			getgroupuserGrid();
		}
},{
	text : '项目报告重复',
	iconCls : 'icon-cards',
	width : 120,
	closed: true,cache: false,modal: true,
	handler : function() {
		cfbg="项目报告重复";
		getgroupuserGrid();
	}
},{
	text : '检验没有检验样本',
	iconCls : 'icon-cards',
	width : 160,
	closed: true,cache: false,modal: true,
	handler : function() {
		keshi="131";
		getgroupuserGrid();
	}
},{
	text : '影像没有影像样本',
	iconCls : 'icon-cards',
	width : 160,
	closed: true,cache: false,modal: true,
	handler : function() {
		keshi="21";
		getgroupuserGrid();
	}
},{
		text : '影像没有影像检查项目',
		iconCls : 'icon-cards',
		width : 170,
		closed: true,cache: false,modal: true,
		handler : function() {
			exam_item_id="1";
			getgroupuserGrid();
		}
},{
		text : '同步his价表价格',
		iconCls : 'icon-cards',
		width : 150,
		closed: true,cache: false,modal: true,
		handler : function() {
			tongbuhisjiage();
		}
},{
	text : '同步his数据字典',
	iconCls : 'icon-cards',
	width : 150,
	closed: true,cache: false,modal: true,
	handler : function() {
		tongbuhisshujv();
	}
}];
/**
 * 自动同步his价格表
 */
function tongbuhisjiage(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'updateHIsPriceSynchro.action',
		success:function(data){
			$(".loading_div").remove();
			if(data=="同步成功"){
				$.messager.alert("提示信息","同步成功");
			} else {
				$.messager.alert("提示信息","操作失败","error");
			}
			$('#groupusershow').datagrid('reload');
		},
		error:function(){
			$(".loading_div").remove();
			$.messager.alert("提示信息","操作失败","error");
			$('#groupusershow').datagrid('reload');
		}
	});
}

function tongbuhisshujv(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'hisDataSynchronizing.action',
		success:function(data){
			$(".loading_div").remove();
			if(data.split("-")[0] == 'ok'){
				$.messager.alert("提示信息",data.split("-")[1]);
			} else {
				$.messager.alert("提示信息",data.split("-")[1],"error");
			}
			$('#groupusershow').datagrid('reload');
		},
		error:function(){
			$(".loading_div").remove();
			$.messager.alert("提示信息","操作失败","error");
			$('#groupusershow').datagrid('reload');
		}
	});
}
