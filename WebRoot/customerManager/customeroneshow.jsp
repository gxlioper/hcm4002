<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script>
$(document).ready(function() {	
	gettotalinfo();
	custChangItemListGrid();
	});

/**
 * 显示体检项目套餐信息
 */
 var lastIndex; 
function custChangItemListGrid() {
	
	var model = {"exam_num" : $("#exam_num").val()};
	$("#custchangitemlist").datagrid({
		url : 'djtcustchangitemlist.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : false,
		toolbar:'#toolbar',
		//pageSize: 8,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
		columns : [[ {field:'ck',checkbox:true }, 
                     {align : 'left',field : 'item_code',title : '项目编码',	width : 15},
		             {align : 'left',field : 'item_name',title : '项目名称',	width : 35},
		             {align : 'center',field : 'item_amount',title : '原金额',	width : 10},
		             {align : 'center',field : 'discount',title : '折扣率',	width : 10},
		             {align : 'center',field : 'is_new_added',title : '增加次数',	width : 1},
		             {align : 'center',field : 'amount',title : '应收额',	width : 10},
		             {align : 'center',field : 'pay_statuss',title : '结算状态',	width : 15}, 
		             {align : 'center',field : 'exam_indicators',title : '付费方式',	width : 15}, 
		             {align : 'center',field : 'exam_statuss',title : '检查状态',	width : 15}, 
		             {align : 'center',field : 'is_applications',title : '接口标识',	width : 15} 
		          ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			$("#custchangitemlist").datagrid("hideColumn", "is_new_added"); // 设置隐藏列   
			$("#custchangitemlist").datagrid("hideColumn", "item_code"); // 设置隐藏列  
			var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
			if(is_show_discount==0){
				$("#custchangitemlist").datagrid("hideColumn", "discount"); // 设置隐藏列
				$("#custchangitemlist").datagrid("hideColumn", "amount"); // 设置隐藏列
			}
		},
		rowStyler:function(index,row){    
	        if (row.is_new_added>0){    
	            return 'font-weight:bold;';    
	        }    
	    },
	    singleSelect : false,
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

function gettotalinfo(){
	$.post("djtGItemCount.action", 
			{
				"exam_num":$("#exam_num").val(),
				"exam_type":'T'
			}, function(jsonPost) {
				var customertotal = eval(jsonPost);
				document.getElementById("countss").firstChild.nodeValue=customertotal.counts;
				document.getElementById("tyjje").firstChild.nodeValue=customertotal.termAmt;
				document.getElementById("gyjje").firstChild.nodeValue=customertotal.personAmt;
				document.getElementById("gsjje").firstChild.nodeValue=customertotal.personYfAmt;
				document.getElementById("gwjje").firstChild.nodeValue=customertotal.qfAmt;
			}, "json");

}

</script>
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检任务信息</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dd style="height: 20px; width: 140px;">单位名称</dd>
			<dd style="height: 20px; width: 280px;">
				<s:property value="model.comname" />
			</dd>
			<dd style="height: 20px; width: 140px;">体检任务名称：</dd>
			<dd style="height: 20px; width: 280px;"><s:property value="model.batch_name" /></dd>
		</dl>
</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员信息</strong>
	</legend>	
	<div class="user-query">
		<dl>
			<dt>姓名</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.custname" /></dd>
			<dt>身份证号</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.id_num" /></dd>
			<dt>年龄</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.age" /></dd>
		</dl>
		<dl>
			<dt>性别</dt>
			<dd  style="height: 26px; width: 140px;"><s:property value="model.sex" /></dd>
			<dt>婚否</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.is_marriage" /></dd>
			<dt>出生日期</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.birthday"/></dd>			
		</dl>
		<dl>
		    <dt>体检类别</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.customer_type"/></dd>
			<dt>民族</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.nation"/></dd>
			<dt>人员类别</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.time1"/></dd>			
		</dl>
		<dl>
		    <dt>费别</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.chargingType"/></dd>
			<dt>联系电话</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.tel" /></dd>
			<dt>邮箱</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.email" /></dd>
		</dl>
		<dl>
		    <dt>部门</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model._level"/></dd>
			<dt>职务</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.position" /></dd>
			<dt>工号</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.employeeID" /></dd>			
		</dl>
		<dl>		    
			<dt>通讯住址</dt>
			<dd style="height: 26px; width: 640px;"><s:property value="model.address" /></dd>				
		</dl>
		<dl>		    
			<dt>备注</dt>
			<dd style="height: 26px; width: 400px;"><s:property value="model.remark" /></dd>
			<dt>其他</dt>
			<dd style="height: 26px; width: 140px;"><s:property value="model.others" /></dd>			
		</dl>
		<dl>		    
			<dt>套餐名称</dt>
			<dd style="height: 26px; width: 640px;"><s:property value="model.hansidjdflag" /></dd>					
		</dl>
	</div>
</fieldset>

 <fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检项目</strong>
	</legend>

			&nbsp;&nbsp;&nbsp;&nbsp;收费项目数：<font id="countss" style="font-weight:bold;font-style:italic;">0</font>项
			&nbsp;&nbsp;团体应缴金额：<font id="tyjje" style="font-weight:bold;font-style:italic;">0</font>元
			&nbsp;&nbsp;个人应缴金额：<font id="gyjje" style="font-weight:bold;font-style:italic;">0</font>元
			&nbsp;&nbsp;个人实缴金额：<font id="gsjje" style="color:blue;font-weight:bold;font-style:italic;">0</font>元
			&nbsp;&nbsp;个人未缴金额：<font id="gwjje" style="color:red;font-weight:bold;font-style:italic;">0</font>元
	
			<div id="custchangitemlist"></div>
	</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
<div align="right">
	<div class="inner-button-box">
	   	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close();">关闭</a>
	</div>
</div>
</fieldset>
