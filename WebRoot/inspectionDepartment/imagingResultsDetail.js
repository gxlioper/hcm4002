$(document).ready(function () {
	var exam_num = $("#exam_num").val();
	getcustomerInfo(exam_num);
	gethyGrid(exam_num);
	gethyItemGrid(0,0);
	//wenjuanbuttoshow();
});
//查询人员基本信息
function getcustomerInfo(exam_num){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+exam_num,
		type:'post',
		async:false,  
		success:function(data){
			var obj=eval("("+data+")");
			$("#arch_num").html(obj.arch_num);
			$("#exam_num_x").html(exam_num);
			$("#exam_id").val(obj.id);
			$("#user_name").html(obj.user_name);
			$("#sex").html(obj.sex);
			$("#age").html(obj.age);
			$("#set_name").html(obj.set_name);
			$("#company").html(obj.company);
			$("#past_medical_history").html(obj.past_medical_history);
			if(obj.picture_path != ''){
				document.getElementById("exampic").src="getdjtexamPhoto.action?others="+obj.picture_path+"&"+new Date().getTime();
			}
		}
	});
}

function gethyGrid(exam_num){
	var model={"exam_num":exam_num};
	$("#viewExamResultList").datagrid({
			 url:'getViewExamCharingItem.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
			 columns:[[
				{align:'center',field:'demo_name',title:'样本名称',width:20},
				{align:'center',field:'item_name',title:'收费项目',width:20},
				{align:'center',field:"exam_doctor","title":"检查医生","width":20},
			    {align:'center',field:'exam_date',title:'检查时间',width:20},
			    //{align:'center',field:'item_name',title:'检查项目',width:20},
			    {align:'center',field:'exam_desc',title:'检查描述',width:30},
			 	{align:'center',field:'exam_result',title:'检查结果',width:20},
			 	{title: '阳性标记', field: 'positive_find', sortable: true,  
                    editor: {  
                        type: 'combobox',  
                        options: {  
                            valueField: 'id',  
                            textField: 'data_name', 
                            panelHeight:'auto',
                            method: 'get',  
                            url: 'getPositiveFindList.action',  
                            required: true,
                            editable:false
                        }  
                    }  
                }
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		MergeCells('viewExamResultList', 'demo_name');
		    	},
		    	
		    	onDblClickRow:function(index, field, row){
		    		if (endEditing()) {  
    	                $('#viewExamResultList').datagrid('selectRow', index)  
    	                        .datagrid('editCell', { index: index, field: "positive_find" });  
    	                editIndex = index;  
    	            } 
		    	},
		    	onAfterEdit:function(index, row, changes){
		        	$.ajax({
	            		url:'savePositivefind.action', 
	            	    data:{
	            	          id:row.id,
	            	          positive_find:changes.positive_find,
	            	          },          
	            	    type: "post",//数据发送方式   
	            	    success: function(data){  
	            	        $.messager.alert('提示信息', "标记成功！",function(){});
	            	        $("#viewExamResultList").datagrid('reload');
	            	         },
	            	     error:function(){
	            	         $.messager.alert('提示信息', "标记失败！",function(){});
	            	         $('#viewExamResultList').datagrid('refreshRow', index);
	            	         }  
	            	    });
		        },
		    	singleSelect:true,
			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false
	});
}






//function gethyItemGrid(pace_id,item_id){}

var editIndex = undefined;
//判断是否编辑结束  
function endEditing() {  
    if (editIndex == undefined) { return true }  
    if ($('#viewExamResultList').datagrid('validateRow', editIndex)) {  
        $('#viewExamResultList').datagrid('endEdit', editIndex);  
        editIndex = undefined;  
        return true;  
    } else {  
        return false;  
    }  
}

$.extend($.fn.datagrid.methods, {  
    editCell: function (jq, param) {  
        return jq.each(function () {  
            var opts = $(this).datagrid('options');  
            var fields = $(this).datagrid('getColumnFields', true).concat($(this).datagrid('getColumnFields'));  
            for (var i = 0; i < fields.length; i++) {  
                var col = $(this).datagrid('getColumnOption', fields[i]);  
                col.editor1 = col.editor;  
                if (fields[i] != param.field) {  
                    col.editor = null;  
                }  
            }  
            $(this).datagrid('beginEdit', param.index);  
            for (var i = 0; i < fields.length; i++) {  
                var col = $(this).datagrid('getColumnOption', fields[i]);  
                col.editor = col.editor1;  
            }  
        });  
    }  
}); 

//function saveExam_result(){}



//FF中需要修改配置window.close方法才能有作用，为了不需要用户去手动修改，所以用一个空白页面显示并且让后退按钮失效
//Opera浏览器旧版本(小于等于12.16版本)内核是Presto，window.close方法有作用，但页面不是关闭只是跳转到空白页面，后退按钮有效，也需要特殊处理
function close_page(){
	var _parentWin =  window.opener ;
	var userAgent = navigator.userAgent;
	  window.opener = null;
	  window.open('', '_self');
	  window.close();
	  _parentWin.getgroupuserGrid();
}

/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param fldList 要合并table的id
4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
5         */
      function MergeCells(tableID, fldList) {
            var Arr = fldList.split(",");
             var dg = $('#' + tableID);
            var fldName;
             var RowCount = dg.datagrid("getRows").length;
            var span;
            var PerValue = "";
            var CurValue = "";
             var length = Arr.length - 1;
             for (i = length; i >= 0; i--) {
               fldName = Arr[i];
                PerValue = "";
                span = 1;
                for (row = 0; row <= RowCount; row++) {
                    if (row == RowCount) {
                        CurValue = "";
                    }
                    else {
                        CurValue = dg.datagrid("getRows")[row][fldName];
                    }
                     if (PerValue == CurValue) {
                        span += 1;
                    }
                     else {
                        var index = row - span;
                         dg.datagrid('mergeCells', {
                            index: index,
                             field: fldName,
                             rowspan: span,
                             colspan: null
                         });
                         span = 1;
                         PerValue = CurValue;
                     }
                 }
             }
         }