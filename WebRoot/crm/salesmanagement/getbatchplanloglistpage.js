
$(document).ready(function () {
	getbatchplan_ck();
})
function getbatchplan_ck(){
	var model = {"id":$("#ids").val(),type:$("#type").val()};
	$("#batchplanloglist").datagrid({
		url: 'getBatchPlanLogDTOList.action',
		queryParams: model,
		rownumbers:false,
		columns:[[{align:"center",field:"project_name","title":"名称","width":10},
		      {align:"center",field:"project_id","title":"编码","width":10},
		      {align:"center",field:"project_status","title":"状态","width":12},
              {align:"center",field:"project_reson","title":"原因","width":15},
     		  {align:'center',field:"creater","title":"建立者","width":15},
     		  {align:"center",field:"creater_time","title":"建立时间","width":15}
     		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		striped:true
	});
}