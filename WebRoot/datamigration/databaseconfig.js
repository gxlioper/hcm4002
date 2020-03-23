
$(document).ready(function () {
	getGrid();
});

function getGrid(){
	   $("#configList").datagrid({
		url: 'getDataBaseConfigList.action',
//		queryParams: model,
		rownumbers:true,
     	pageSize: 15,//每页显示的记录条数，默认为10 
     	pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
     	sortName: 'cdate',
		sortOrder: 'desc',
     //height:390,
     columns:[[{align:"center",field:"types","title":"配置类型","width":15},
     		  {align:'center',field:"database_url","title":"IP地址","width":20},
     		  {align:"center",field:"database_port","title":"端口号","width":15},
     		  {align:"center",field:"database_uame","title":"数据库名称","width":15},
     		  {align:"center",field:"username","title":"登录名称","width":15},
     		  {align:"center",field:"password","title":"登录密码","width":15,"formatter":f_yc},
     		  {align:"center",field:"xg","title":"修改","width":15,"formatter":f_xg}
     		 ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
     	fitColumns:true,
     	striped:true,
     	fit:true,
     	rowStyler: function(index,row){
	    	if(row.is_default == '1'){
				return 'color:#ff0000;';
			}
		}
	});
}
function f_xg(val,row){	
	return '<a href=\"javascript:f_xgViewWords(\''+row.type+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function f_xgViewWords(id){
		$("#dlg-edit").dialog({
			title:'修改配置',
			href:'getDataBaseConfigEditPage.action?type='+id
		});
		$("#dlg-edit").dialog('open');
}

function f_yc(val,row){
	return '******';
}