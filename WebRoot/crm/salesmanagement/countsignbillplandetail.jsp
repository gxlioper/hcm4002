<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	 $('#genzongzhuangtai').combobox({
		 	data:[{
		 		id:'1',value:'未排除撞单'
		 	},{
		 		id:'2',value:'开始跟踪'
		 	},{
		 		id:'3',value:'制作方案'
		 	},{
		 		id:'4',value:'生成合同'
		 	},{
		 		id:'5',value:'已备单'
		 	},{
		 		id:'6',value:'体检并回款'
		 	},{
		 		id:'7',value:'已排除撞单'
		 	},{
		 		id:'8',value:'合同审核通过'
		 	}],
		    valueField:'id',    
		    textField:'value',
			width:80,
		    prompt:'请选择跟踪状态'
	 });
	 $('#qiandanzhuangtai').combobox({
		 	data:[{
		 		id:'1',value:'草稿'
		 	},{
		 		id:'2',value:'撞单'
		 	},{
		 		id:'3',value:'正式稿'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    width:80,
		    prompt:'请选择签单状态'
	 });
	 $('#detailstarttime').datebox({    
		 prompt:'请选择开始查询日期'
		}); 
	 $('#detailendtime').datebox({    
		 prompt:'请选择结束查询日期' 
		}); 
		 getCountSignBillPlanDetailList();
});

function getCountSignBillPlanDetailList(){
	$("#sign_bill_detail_count_list").datagrid({
		url: 'countSignBillPLanDetailList.action',
		queryParams: {
			signstartTime:$('#detailstarttime').datebox('getValue'),
			signendTime:$('#detailendtime').datebox('getValue'),
				 creater:$('#creater_detail').val(),
				 sign_name:$("#ser_detail_sign_name").val(),
				sign_year:$("#ser_detail_sign_year").val(),
				 track_progress:$("#genzongzhuangtai").combobox("getValue"),
				 sign_status:$("#qiandanzhuangtai").combobox("getValue")
		 },
		rownumbers:false,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		columns:[[
			{align:"center",field:"id","title":"id",hidden:true},
		      {align:"center",field:"track_progresss","title":"跟踪进度","width":15},
		      {align:"center",field:"track_time","title":"变化时间","width":25},
		      {align:"center",field:"sign_statuss","title":"状态","width":10},
             {align:"center",field:"sign_num","title":"编号","width":15},
    		  {align:'center',field:"sign_name","title":"名称","width":20},
    		  {align:"center",field:"com_name","title":"单位","width":25},
    		 {align:"center",field:"company_id","title":"单位",hidden:true},
    		  {align:"center",field:"sign_year","title":"年度","width":10},
    		  {align:"center",field:"sign_type","title":"签单类型","width":12},
    		 {align:"center",field:"sign_persion","title":"估算人数","width":12},
    		{align:"center",field:"sign_amount","title":"估算金额","width":12},
    		{align:"center",field:"creater","title":"负责人","width":10}
    		  ]],
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
		fitColumns:true,
		striped:true,
		fit:true
	});
}
  
</script>
<input id="creater_detail" value="<s:property value='creater'/>" hidden="true"/>
<input id="tubiao" value='<s:property value="tubiao"/>' hidden="true"/>
<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:80px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd>名称:<input type="text" id="ser_detail_sign_name" class="textinput" style="height:13px;width:80px"/></dd>
					<dd>跟踪状态：<select id="genzongzhuangtai">
					</select></dd>
					<dd>年度：<input type="text" id="ser_detail_sign_year" value="<s:property value="sign_year"/>" class="textinput" style="height:13px;width:80px"/></dd>
					<dd>签单状态：<select id="qiandanzhuangtai">
					</select></dd>
					<dd style="width:50px;">开始日期</dd>
					<dd>
						<input  type="text"  id="detailstarttime"    style="height:23px;width:80px;" />
					</dd>
					<dd style="width:50px;">结束日期</dd>
					<dd>
						<input type="text"  id="detailendtime"    style="height:23px;width:80px;"/>
					</dd>
					
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:getCountSignBillPlanDetailList();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
</div>
 <div  data-options="region:'center'">
<fieldset id="biao" style="margin:5px;padding-right:0;height:94%">
<legend><strong>签单计划列表</strong></legend>
<table id="sign_bill_detail_count_list"> 
</table>
</fieldset>
</div>
</div>
