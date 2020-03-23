$(document).ready(function () {
	shu("");
	//getgroupuserGrid();
});

/**
 * 鼠标表格悬停，显示隐藏溢出内容
 */
function formatCellTooltip(value){  
    return "<span title='" + value + "'>" + value + "</span>";  
} 
/**
 * 显示体检套餐
 */
 function getgroupuserGrid(){
	    var f = "";
	    var z = "";
	    var node = $("#some_tree").tree('getSelected');
	    if(node.lx=='lx'){//职业危害体检类别
 	    	f = '\''+$('#some_tree').tree("getParent",node.target).id+'\''; 
 	    	z = node.id;
	    } else if(node.ys == 'ys'){//因素
	    	f ='\''+ node.id+'\'';
	    	//z = $("#some_tree").tree("getChildren",node.target);
	    } else {
	    	var zi = $('#some_tree').tree("getChildren",node.target)
	    	f = $('#some_tree').tree('getSelected');
	    	var arra = new Array();
	    	for(var i = 0 ; i < zi.length ; i++){
	    		//console.log(zi[i].text);
	    		if(zi[i].ys=='ys'){
	    			arra.push('\''+zi[i].id+'\'');
	    		}
	    	}
	    	f = arra.toString();
	    	if(f==""){
	    		f='\'\'';
	    	}
	    }
	    
	/* 	var lx = jiedidan.lx;
	 	var ys = jiedidan.ys;
	 	var leixing = "";
	 	var yinsu = "";
	 	if(lx == "lx"){
	 		leixing = jiedidan.id;
	 		 yinsu = $(this).tree("getParent",jiedidan.target).id;
	 		 alert(yinsu);
	 	}
	 	if(ys=='ys'){
	 		yinsu = jiedidan.id;
	 	}*/
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#groupusershow").datagrid({
		 url:'getZybExamSetList.action',
		 queryParams:{
			 set_name:$('#set_name').val(),
			 sex:$("input[name='Jsex']:checked").val(),
			 update_time:$('#update_time').datebox('getValue'),//修改开始时间
			 update_times:$('#update_times').datebox('getValue'),//修改结束时间
			 intss:$('#intss').val(),
			 occuphyexaclassid:z,
			 hazardfactorsid:f
				 
		 },
		 toolbar:toolbar,
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
		        {align:'center',field:'set_num',title:'编码',width:18},
	            {align:'center',field:'set_name',title:'套餐名称',width:28},
			 	{align:'center',field:'sex',title:'适用性别',width:18},
			 	{align:'center',field:'price',title:'金额',width:18},
			 	{align:'center',field:'set_amount',title:'折后金额',width:18},
			 	{align:'center',field:'set_discount',title:'折扣',width:18},
			 	{align:'center',field:'creater_name',title:'创建人',width:15},
			 	{align:'center',field:'create_time',title:'创建时间',width:20},
			 	{align:'center',field:'update_name',title:'修改人',width:15},
			 	{align:'center',field:'update_time',title:'修改时间',width:20},
			 	{align:'center',field:'is_Active',title:'启（停）修改',width:10,"formatter":f_qt},
			   /* {align:'center',field:'ys',title:'因素关系维护',width:30,"formatter":f_ys_gx},*/
			 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
			 	{align:'center',field:'ss',width:10,title:'删除',"formatter":f_sc}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
	    		if(is_show_discount==0){
	    			$("#groupusershow").datagrid("hideColumn", "price"); // 设置隐藏列
	    			$("#groupusershow").datagrid("hideColumn", "set_discount"); // 设置隐藏列
	    		}
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true,
		    onDblClickRow:function(index, row){
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updateSampleDemo(row_id);
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}
//维护因素关系页面调用
function f_ys_gx(val,row) {
 	if($("#some_tree").tree('getSelected').text=='自选套餐'){
		return '<a href=\"javascript:f_ys_gx_open_page('+row.id+')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	}
}
function f_ys_gx_open_page(id) {
	var url='zybExamSetHazardPage.action?id='+id
	newWindow = window.open(url,"危害因素", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newWindow.focus();
}
//-------------------------------------修改体检项目---------------------
 function f_xg(val,row){
	 var node = $("#some_tree").tree('getSelected');
	 if(node.text=='自选套餐'){
		 return '<a href=\"javascript:zxXG(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 } else {
		 return '<a href=\"javascript:updateSampleDemo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
}
function zxXG(id) {
	var url='zybAddExamSetZXPage.action?id='+id
	newWindow = window.open(url,"危害因素", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newWindow.focus();
}
/**
 * 修改体检
 * @param id
 */
 function updateSampleDemo(id){
			$("#dlg-custedit").dialog({
		    //left:20,
			//top:0,
			title:'修改体检套餐',
			//width :1200,
			//height:590,
			href:'updateExamSetPage.action?id='+id
		});
		$("#dlg-custedit").dialog('open');
}
//------------------------------------删除体检套餐----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除体检套餐方法
 * @param id
 */
function f_deluser(id) {
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deletexamSet.action?ids='+id,
	    		type : 'post',
	    		success : function(data) {
	    			$.messager.alert('提示信息',data);
	    			$('#groupusershow').datagrid('reload');
	    		},
	    		error : function() {
	    			$.messager.alert('提示信息', '操作失败！', 'error');
	    		}
	    	});
	    }    
	});
}

//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text : '新增体检套餐',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		var node = $("#some_tree").tree('getSelected');
		var lx = node.lx;
		var whlb = node.whlb;
		if(lx=="lx"){
			$('#lb_name').val(node.text);
			var f_lx = $("#some_tree").tree("getParent",node.target);
			$('#ys_name').val(f_lx.text);
				$("#dlg-custedit").dialog({
					title : '新增体检套餐',
					//left:200,
					//top:0,
					//width :1200,
					//height:590,
					href :'addExamSet.action?occuphyexaclassid='+node.id+'&hazardfactorsid='+f_lx.id
			});
			$("#dlg-custedit").dialog('open');
		} else if(whlb=='whlb') {
			if(node.text=='自选套餐'){
				$('#lb_name').val('');
				$('#ys_name').val('');
				// $("#dlg-custedit").dialog({
				// 	title : '自选套餐',
				// 	//left:200,
				// 	//top:0,
				// 	//width :1200,
				// 	//height:590,
				// 	href :'zybAddExamSetZXPage.action'
				// });
				// $("#dlg-custedit").dialog('open');

				var url='zybAddExamSetZXPage.action'
				newWindow = window.open(url,"危害因素", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
				newWindow.focus();
			} else{
				$('#lb_name').val('');
				$('#ys_name').val('');
				$("#dlg-custedit").dialog({
					title : '新增体检套餐',
					//left:200,
					//top:0,
					//width :1200,
					//height:590,
					href :'addExamSet.action'
				});
				$("#dlg-custedit").dialog('open');
			}
		} else {
			$.messager.alert("提示信息","请选择危害因素和类别","error");
			return;
		}
	}
}];

var set_ids;
function setweixinadddo(set_ids){
	 $.messager.confirm('提示信息','执行此操作，将对所选套餐同步到微信端，是否执行此操作？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'setweixinadddo.action',
				data : {
					    ids:set_ids
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								$.messager.alert("操作提示", text);															
							} else {
								$.messager.alert("操作提示", text, "error");
							}							
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	   }
		 });
}



//启停修改
function f_qt(val,row){
var html='';
  if(val=="Y"){
    return '<a style="color:#1CC112;" href=\"javascript:f_startorblock(\''+row.id+'\',\'停用\')\">停用</a>';
  }else if(val=='T') {
     return '<a style="color:#f00;" href=\"javascript:f_startorblock(\''+row.id+'\',\'启用\')\">启用</a>';
   }
}


//启（停）修改
function f_startorblock(id,html){
	$.messager.confirm('提示信息','是否确认'+html+'该套餐？',function(r){
		if(r){
		$.ajax({
   	url:'getstartorblock.action?id='+id,
   	type:'post',
   	success:function(data){
   		var obj=eval("("+data+")");
   		if(obj=='success'){
   			$.messager.alert('提示信息',html+"该套餐成功！");
   			$('#groupusershow').datagrid('reload');
   		}else if(obj=='error'){
   			$.messager.alert('提示信息',html+"该套餐失败！",'error');
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
/**
--------------------------------------------------树-----------------------------

**/
/**
 * 树
 */
function shu(zhi){
	/* var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);*/
	$("#some_tree").tree({
		 url:'getZybExamSetTree.action',
//		 dataType:'json',
		 queryParams:{
			 name:zhi
		 },
		 /*loadFilter :function(data,parent){
			 if(zhi!=""&&zhi!=undefined){
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].diseaseclassID,text:obj[i].diseaseclass_name});
				 }
				return newData;
			 }else{
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].diseaseclassID,text:obj[i].diseaseclass_name});
				 }
				 var newDate2 = [{id:'',text:'所有职业病类别',children:newData}];
				 return newDate2;
			 }
		 },*/
		 onClick:function(node){
//			 if(node.lx=='lx'){
//				 var father = $(this).tree("getParent",node.target);
//				 alert(father.text);
//				 alert("子节点");
//			 } else if(node.ys=='ys'){
//				 alert("二级节点");
//			 } else {
//				 alert(node.text);
//				 var no = $(this).tree("getChildren",node.target);
//				 console.info(no);
//			 }
//			 $('#groupusershow').datagrid('reload');
		 },
		 onLoadSuccess:function(node,data){  
		 	   $(".loading_div").remove();
	           $("#some_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
	           var n = $("#some_tree").tree("getSelected");  
	           if(n!=null){  
	                $("#some_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
	           }  
        },
        onSelect:function(node){
        	getgroupuserGrid();
        }
	});
}