var toolbar = [{
    text:'新增敏感词',
    iconCls:'icon-add',
    handler:function(){
    	$("#dlg-edit").dialog({
    		title:'新增敏感词',
    		href:'getEditSensitiveWordsLibPage.action'
    	});
    	$("#dlg-edit").dialog("open");
    }
}];
$(document).ready(function () {
	getGrid();
});

function getGrid(){
	var model = {"sensitive_type":$('#ser_type').combobox('getValue')};
	   $("#sensitive_list").datagrid({
		url: 'getSensitiveWordsLibList.action',
		queryParams: model,
		rownumbers:true,
     	pageSize: 15,//每页显示的记录条数，默认为10 
     	pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
     	sortName: 'cdate',
		sortOrder: 'desc',
     //height:390,
     columns:[[{align:"center",field:"sensitive_types","title":"敏感词类型","width":15},
     		  {align:'center',field:"sensitive_sex","title":"适用性别","width":10},
     		  {align:"center",field:"sensitive_content","title":"敏感词内容","width":35},
     		  {align:"center",field:"updater","title":"修改人","width":10},
     		  {align:"center",field:"update_time","title":"修改时间","width":15},
     		  {align:"center",field:"xg","title":"修改","width":10,"formatter":f_xg},
     		 {align:"center",field:"sc","title":"删除","width":10,"formatter":f_sc}
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
     	toolbar:toolbar
	});
}
function f_xg(val,row){	
	return '<a href=\"javascript:f_xgViewWords(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function f_xgViewWords(id){
		$("#dlg-edit").dialog({
			title:'修改敏感词',
			href:'getEditSensitiveWordsLibPage.action?id='+id
		});
		$("#dlg-edit").dialog('open');
}
function f_sc(val,row){	
	return '<a href=\"javascript:f_delViewWords(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
function f_delViewWords(id){
	$.messager.confirm("提示信息","是否删除敏感词？",function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
				url : 'delSensitiveWordsLib.action?id='+id,
				type : 'post',
				success:function(data){
					$(".loading_div").remove();
					var obj = data.split("-");
					if(obj[0] == 'ok'){
						$.messager.alert('提示信息', obj[1],'info');
						getGrid();
					}else{
						$.messager.alert('提示信息', obj[1],'error');
					}
				},
				error:function(data){
					$(".loading_div").remove();
					$.messager.alert('提示信息', data,'error');
				}
			});
		}
	})
}
//回车查询
function serch_cls(dom){
	$(dom).unbind("keydown").keydown(function (e) {
	    if (e.which == 13) {
	    	getGrid();
	    }
    });
}