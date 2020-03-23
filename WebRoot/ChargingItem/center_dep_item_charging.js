$(document).ready(function () {
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
	
	$('#addDemo_type').combobox({
		//panelHeight:80,
		url:"getDatadis.action?com_Type="+"YBFL",
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function() {//下拉框默认选择
		},
		onSelect:function(node){
        	$("#demo_type").val(node.id);
        },
        loadFilter:function(data){
            data.unshift({id:'0',name:'无'});
            return data;
        }
	    
	})
});

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

/**
 * 显示收费项目列表
 */
var mccf=""
var cfbg="";
var keshi="";//科室
var exam_item_id="";
 function getgroupuserGrid(nodeId){
	     //获取启用/停用的值
		 var chk_value ="";    
		  $('input[name = chkItem]:checked').each(function(){    
		   chk_value=chk_value+","+($(this).val());    
		  }); 
		  if(chk_value.length>1){
			  chk_value=chk_value.substring(1,chk_value.length);
		  }
		 if(nodeId!=undefined){
			 $("#leftSelectDeptId").val(nodeId);
		 }
		 //alert($("#leftSelectDeptId").val());
		 //alert($("#demo_type").val());
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
			 dep_id:$("#leftSelectDeptId").val(),//所属科室	
			interface_flag:$('#interface_flag').combobox('getValue'),//接口标识
			guide_category:$('#guide_category').combobox('getValue'),//导引单分类	
			mccf:mccf,
			baogaochongfu:cfbg,
			ks:keshi,
			exam_item_id:exam_item_id,
			startStop:chk_value,  //是否显示停用/启用
			demo_type:$("#demo_type").val()    //样本分类
	 	 },
	 	 type:'post',
	     pageNumber:1,
	     //pageSize:10,	     
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	         	{field:'ck',checkbox:true },
	         	
	         	{align:'center',field:'item_code',title:'收费项目编码',width:18},	
	            {align:'left',field:'item_name',title:'收费项目名称',width:28},
			 	{align:'left',field:'d_name',title:'所属科室',width:15},
			 	{align:'left',field:'dname',title:'统计科室',width:15},
			 	{align:'left',field:'s_name',title:'所属样本',width:15},
			 	{align:'left',field:'report_name',title:'所属报告样本',width:20},
			 	{align:'right',field:'amount',title:'金额',width:10},
			 	{align:"center",field:"calculation_rate","title":"利润率","width":10,"formatter":xg_rate},	
			 	{align:"center",field:"item_discount","title":"理论折扣","width":12},
			 	{align:'left',field:'item_note',title:'项目描述',width:15,"formatter":formatCellTooltip},
//			 	{align:'center',field:'his_num',title:'his关联码',width:15},
//			 	{align:'center',field:'exam_num',title:'检验关联码',width:15},
//			 	{align:'center',field:'view_num',title:'影像关联码',width:15},
			 	{align:'center',field:'f_name',title:'财务类别',width:10},
			 	{align:'center',field:'limit_count_s',title:'项目上限',width:10},
			 	{align:'center',field:'xz',title:'添加到本体检中心',width:28,"formatter":f_xz}
//			 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
//			 	{align:"center",field:"isActive","title":"启(停)修改","width":18,"formatter":f_qt}
			 	//{align:'center',field:'ss',title:'删除',width:10,"formatter":f_sc}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		mccf="";
	    		cfbg="";
	    		keshi="";
	    		exam_item_id="";
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination:true,//分页控件
		    fitColumns:true,//自适应
		    fit:true,
		    toolbar:toolbar,
		    striped:true,
		    nowrap:false,
	        singleSelect:false,//只允许选择一行
	        onDblClickRow:function(index, row){
	        	var row = $('#groupusershow').datagrid('getRows')[index];
	        	//alert(row.id+"===="+row.item_category);
	        	parent.selectTab2(row);
	        	//parent.selectTab2(row.id,row.item_category,row.limit_count,row.dep_id);
	        }
	});
}///hjw_contract_manager4.0.0.2/WebRoot/themes/al/img/yiyuan.png
function f_xz(val,row){
	  return '<a href=\"javascript:f_xz_save(\''+row.item_code+'\',\'启用\')\"><img style="height:20px;width:20px" src=\"themes/al/img/tianjia.png\"  alt=\"添加到体检中心\" /></a>';
} 
function f_xz_save(item_code,html){
	$.ajax({
      	url:'centerSeleteItem.action?item_code_s='+item_code,
      	type:'post',
      	success:function(data){
			if(data == 'ok'){
				$.messager.alert("提示信息","添加成功","info");
			}
      	},
      	error:function(){
      		$.messager.alert("提示信息","操作失败","error");
 		}
	})
}
//启停修改
 function f_qt(val,row){
  var html='';
     if(val=="N"){
       return '<a style="color:#f00;" href=\"javascript:f_startorblock(\''+row.id+'\',\'启用\')\">启用</a>';
     }else if(val=='Y') {
        return '<a style="color:#1CC112;" href=\"javascript:f_startorblock(\''+row.id+'\',\'停用\')\">停用</a>';
      }
 }


 //启（停）修改
 function f_startorblock(id,html){
 	$.messager.confirm('提示信息','是否确认'+html+'该收费项目？',function(r){
 		if(r){
 		$.ajax({
      	url:'updateChargingItemStopOrStart.action?ids='+id,
      	type:'post',
      	success:function(data){
			if(data.split("-")[0] == 'ok'){
				$.messager.alert("提示信息",html+"该收费项目成功!");
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
 
 
//---------------样本项目关系--------------
 /*function guanxi(value,row,index){
		return '<a href=\"javascript:guanxi_s(\''+row.id+'\')\">'+row.item_code+'</a>';
	}
	function guanxi_s(id){
		$("#dlg_s_show").dialog({
			title:'样本项目关系',
			href:'getItemSampleDemoPage.action?id='+id
		});
		$("#dlg_s_show").dialog('open');
	}*/
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

function xg_rate(val,row){
	 var str = row.calculation_rate+'%';
	 return str;
}

/**
 * 鼠标表格悬停，显示隐藏溢出内容
 */
function formatCellTooltip(value){ 
    return "<span title='" + value + "'>" + value + "</span>";  
} 

function brushpagecharging(){
	 $('#groupusershow').datagrid('reload');  
}

//----------------------------------定义工具栏---------------------------
var toolbar = [{
	text : '批量添加',
	iconCls : 'icon-add',
	width : 120,
	closed: true,cache: false,modal: true,
	handler : function() {
		var item_chechked = $('#groupusershow').datagrid('getChecked');    // 重新载入当前页面数据 
		console.log(item_chechked);
		if(item_chechked.length==0){
			$.messager.alert("提示信息","未选择项目","error");
			return;
		}
		var item_code_s = new Array();
		for(var i = 0 ; i < item_chechked.length; i++){
			item_code_s.push(item_chechked[i].item_code);
		}
		//console.log(item_code_s.toString());
		$.ajax({
	      	url:'centerSeleteItem.action?item_code_s='+item_code_s.toString(),
	      	type:'post',
	      	success:function(data){
				if(data == 'ok'){
					$.messager.alert("提示信息","添加成功","info");
				}
	      	},
	      	error:function(){
	      		$.messager.alert("提示信息","操作失败","error");
	 		}
		})
	}
}];
