<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	getcharging_summary_list();
});
function getcharging_summary_list(){
	var model = {"exam_num":<s:property value="exam_num"/>};
	$("#charging_summary_list").datagrid({
		url: 'getChargingSummaryList.action',
//		title:'收费方式明细',
		queryParams: model,
		rownumbers:true,
		columns:[[
				  {align:"center",field:"req_num","title":"结算号","width":15},
				  {align:"center",field:"charging_status_y","title":"收费类型","width":10},
				  {align:'center',field:"amount","title":"收费金额(元)","width":15},
                  {align:"center",field:"cashier","title":"收费员","width":15},
                  {align:"center",field:"cash_date","title":"收费日期","width":25},
                  {align:"center",field:"way","title":"收费方式","width":25,"formatter":f_way},
                  {align:"center",field:"card","title":"卡信息","width":35,"formatter":f_card},
                  {align:"center",field:"item_num","title":"项目个数","width":15},
                  {align:"center",field:"ckmx","title":"项目明细","width":15,"formatter":f_ckxmmx}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		rowStyler:function(index,row){    
	        if (row.charging_status != 'Y'){    
	            return 'color:red;';    
	        }    
	    }
	});
}

var xvxian = '<DIV style="BORDER-TOP: #00686b 1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV>'
function f_way(value,row,index){
	var way = '';
	if(row.chargingWay.length > 0){
		for(var i=0;i<row.chargingWay.length;i++){
			if(i == row.chargingWay.length -1){
				way += row.chargingWay[i].charging_way + '(金额：'+row.chargingWay[i].amount+'元)';
			}else{
				way += row.chargingWay[i].charging_way + '(金额：'+row.chargingWay[i].amount+'元)</br>'+xvxian;
			}
		}
	}
	return way;
}
function f_card(value,row,index){
	var way = '';
	if(row.card_deal.length > 0){
		for(var i=0;i<row.card_deal.length;i++){
			if(i == row.card_deal.length -1){
				way += row.card_deal[i].card_num + '(金额：'+row.card_deal[i].amount+'元)';
			}else{
				way += row.card_deal[i].card_num + '(金额：'+row.card_deal[i].amount+'元)</br>'+xvxian;
			}
		}
	}
	return way;
}
function f_ckxmmx(value,row,index){
	return '<a href=\"javascript:f_xmmx(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function f_xmmx(id){
	var model = {"summary_id":id};
	$("#charging_detail_single").datagrid({
		url: 'getChargingDetailList.action',
		queryParams: model,
		rownumbers:true,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',
		columns:[[
		          {align:"center",field:"item_code","title":"项目编码","width":10},
		          {align:'center',field:"item_name","title":"项目名称","width":15},
		          {align:"center",field:"dep_name","title":"科室","width":15},
		          {align:"center",field:"item_amount","title":"项目金额(元)","width":15},
		          {align:"center",field:"amount","title":"收费金额(元)","width":15},
		          {align:"center",field:"discount","title":"折扣","width":10},
		          {align:"center",field:"creater","title":"收费人","width":15},
                  {align:"center",field:"create_time","title":"收费日期","width":15}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
	});
	$("#dlg-show_xmmx").dialog('open');
}

</script>

<div class="easyui-layout" fit="true">
<div data-options="region:'center'">
<table id="charging_summary_list"> 
</table>
</div>
</div>
<div id="dlg-show_xmmx" class="easyui-dialog"  data-options="width: 1000,height: 420,closed: true,cache: false,modal: true,top:50,title:'收退费项目明细'">
	<div class="easyui-layout" fit="true">
	<div data-options="region:'center'">
	<table id=charging_detail_single> 
	</table>
	</div>
</div>
</div>