<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script>
$(document).ready(function() {	 
	custdelChangItemListGrid();
	});

/**
 * 显示体检项目套餐信息
 */
 var lastIndex; 
function custdelChangItemListGrid() {
	var model = {"ids" : $("#ids").val()};
	$("#custchangitemdellist").datagrid({
		url : 'custchangitemdellist.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : false,
		//pageSize: 8,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
		columns : [[ {align : 'center',field : 'id',title : '项目ID',width : 20}, 
		             {align : 'center',field : 'item_code',title : '项目编码',width : 20}, 
		             {align : 'center',field : 'item_name',title : '项目名称',width : 25},
		             {align : 'center',field : 'dep_name',title : '科室',width : 25},
		             {align : "center",field : "itemdel",title : "删除",width : 15,"formatter" : f_dellchargitem}
		          ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
		},
		singleSelect : true,
		collapsible : true,
		pagination : true,
		fitColumns : true,
		autowidth : true,
		striped : true,
		pagination : false,
		pageNumber : 1,
		pageSize : 1000		
	});
}

/**
 * 删除收费项目
 * @param val
 * @param row
 * @returns {String}
 */
function f_dellchargitem(val, row) {
	return '<a href=\"javascript:deletechargitemOne(\''
			+ row.id
			+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
}
/**
 * 删除收费项目
 */
 var itemid;
function deletechargitemOne(itemid) {
	$.messager.confirm('提示信息', '确定删除此收费项目吗？', function(r) {
		if (r) {
			var rows = $('#custchangitemdellist').datagrid('getRows');
			if (!rows.length == 0) {
				for (var i = rows.length - 1; i >= 0; i--) {
					if (itemid == rows[i].id) {
						 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
						 $("body").prepend(str);
						$.ajax({
							url : 'deletechargitemOnedo.action',
							data : {
								id : itemid,
								batch_id:$("#batch_id").val(),
								ids :$("#ids").val(),
                                exam_nums:$("#ids").val()
									},
									type : "post",//数据发送方式   
									success : function(text) {
										$(".loading_div").remove();
										if (text.split("-")[0] == 'ok') {
											var index1 = $('#custchangitemdellist').datagrid('getRowIndex', rows[i]);//获取指定索引
											$('#custchangitemdellist').datagrid('deleteRow',index1);//删除指定索引的行
											$.messager.alert("操作提示", text.split("-")[1]);
										} else {
											$.messager.alert("操作提示", text.split("-")[1], "error");
										}
									},
									error : function() {
										$(".loading_div").remove();
										$.messager.alert("操作提示", "操作错误", "error");					
									}
								});	
						break;					
					}
				}//j End             
			}
		}
	})
}

</script>
<input type="hidden" id="ids" value="<s:property value="model.ids"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batch_id"/>">
<fieldset style="margin: 5px; padding-right: 0;">
   <legend>
		<strong>体检人员减项</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dd style="height: 20px; width: 280px;">所选人员为：<s:property value="model.age"/>人</dd>
			<dd style="height: 20px; width: 280px;">人员性别为：<s:property value="model.sex"/>性</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检项目</strong>
	</legend>	
	<div class="user-query">
	    <div id="custchangitemdellist"></div>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">

<div >
	<div class="inner-button-box">
	   	 <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close();">关闭</a>
	</div>
</div>
</fieldset>