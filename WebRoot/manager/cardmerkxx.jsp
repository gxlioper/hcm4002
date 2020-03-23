<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var toolbar1 = [{
    text:'新制卡',
    iconCls:'icon-add',
    handler:function(){
    	$("#card-edit").dialog({
    		title:'新制卡',
    		height:420,
    		href:'cardInfoadd.action?member_id=<s:property value="id"/>'
    	});
    	$("#card-edit").dialog("open");
    }
},{
    text:'绑定卡',
    iconCls:'icon-add',
    handler:function(){
	    $("#card-edit").dialog({
	       title:'绑定卡',
	       height:420,
	       href:'getboundCard.action?member_id=<s:property value="id"/>'
	    });
	    $("#card-edit").dialog("open");
	}
}];
$(document).ready(function () {
	getGridCard();
});
function getGridCard(){
	var model = {};
	   $("#cardinfolist").datagrid({
		url: '<%=request.getContextPath()%>/getCardInfoByMerId.action?id=<s:property value="id"/>',
		queryParams: model,
		rownumbers:true,
  pageSize: 15,//每页显示的记录条数，默认为10 
  pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
  toolbar: '#toolbar',
  sortName: 'cdate',
		sortOrder: 'desc',
  height:320,
  columns:[[{align:"center",field:"card_num","title":"卡号","width":25},
  		  {align:'center',field:"status_y","title":"状态","width":10},
  		  {align:"center",field:"card_type_y","title":"类型","width":10},
  		  {align:"center",field:"amount","title":"金额(元)","width":15},
  		  {align:"center",field:"card_level","title":"等级","width":10},
  		  {align:"center",field:"limit_card_count","title":"限制次数","width":15},
  		  {align:"center",field:"card_count","title":"消费次数","width":15},
  		  {align:"center",field:"deadline","title":"有效日期","width":15},
  		  {align:"center",field:"remark","title":"备注","width":15},
  		  {align:"center",field:"jc","title":"解除绑定","width":15,"formatter":f_jb}//"formatter":f_xg
  		 ]
  		  ],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		 pagination: false,
  fitColumns:true,
  striped:true,
  toolbar:toolbar1
	});
}

function f_jb(val,row){
	return '<a href=\"javascript:f_jbcardinfo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"解绑\" /></a>';
}
function f_jbcardinfo(id){
	$.messager.confirm('提示信息','确定解除绑定卡信息？',function(r){
		if(r){
			$.ajax({
		        url:'removeBinding.action?card_id='+id,  
		        data:{},          
		        type: "post",//数据发送方式   
		          success: function(data){
		        		$.messager.alert('提示信息', data,function(){});
		            	getGridCard();
		            },
		            error:function(){
		            	$.messager.alert('提示信息', "操作失败！",function(){});
		            }  
		    });
		}
	});
}
</script>
<table id="cardinfolist"> 
</table>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-s').dialog('close')">关闭</a>
	</div>
</div>