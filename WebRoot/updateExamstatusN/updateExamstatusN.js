$(document).ready(function () {
	getcyitemListGrid();
	$("#ser_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			chaxun();
		} 
	});
	
	$("#ser_num").focus();
	
});

function chaxun(){
	getcyitemListGrid();
	getcustomerInfo();
}

//查询人员基本信息
function getcustomerInfo(){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#ser_num").val(),
		type:'post',
		success:function(data){
			if(data == 'null'){
				$("#exam_id").val('');
				$("#status").val('');
				$("#user_name").html('');
				$("#sex").html('');
				$("#age").html('');
				$("#company").html('');
				$("#customer_type").html('');
				$("#set_name").html('');
				$("#ser_num").focus();
				return;
			}
			var obj=eval("("+data+")");
			$("#exam_id").val(obj.id);
			$("#status").val(obj.status);
			$("#user_name").html(obj.user_name);
			$("#sex").html(obj.sex);
			$("#age").html(obj.age);
			$("#company").html(obj.company);
			$("#customer_type").html(obj.customer_type);
			$("#set_name").html(obj.set_name);
		}
	});
}

function getcyitemListGrid(){//已开发票项目列表
	var model = {"exam_num":$("#ser_num").val()};
	$("#cyitemList").datagrid({
		url: 'getExamInfoItemList.action',
		queryParams: model,
		rownumbers:true,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',
		columns:[[{align:'center',field:'ck',checkbox:true},
		          {align:"center",field:"item_code","title":"项目编码","width":15},
		          {align:"center",field:"item_name","title":"项目名称","width":15},
		          {align:'center',field:"item_category","title":"项目类型","width":20},
		          {align:'center',field:"exam_status","title":"检查状态","width":20},
		          {align:"center",field:"sex","title":"适用性别","width":10},
		          {align:"center",field:"amount","title":"金额","width":15}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('cyitemList', 'dep_name');
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		toolbar:"#toolbar",
		border:false
	});
}
function save(){
	var checkedItems = $('#cyitemList').datagrid('getChecked');
	if(checkedItems==""){
		$.messager.alert("警告信息","请选择项目","error");
		return;
	}
	var ids = new Array();
    $.each(checkedItems, function(index, item){
        ids.push(item.eid);
    }); 
    $.ajax({
    	url:'updateStatusN.action',
    	data:{
    		'ids':ids.toString()
    	},
    	type:'post',
    	success:function(data){
    		$.messager.alert("提示信息",data,"info");
    		getcyitemListGrid();
    	},
    	error:function(){
    		$.messager.alert("警告信息","操作失败","error");
    	}
    })
}
function saveRemoveResult(){
	var data = $('#cyitemList').datagrid('getSelections');
	if(data.length == 0){
		$.messager.alert('提示信息', '请选择需要清除结果的项目!','error');
		return;
	}
	if($("#status").val() == 'Z'){
		$.messager.alert('提示信息', '该体检人已终检，不能清除结果!','error');
		return;
	}
	$.messager.confirm('提示信息','确定要清除这些项目的结果吗？',function(r){
		if(r){
			
			var chargingitem = new Array();
			
			for(i=0;i<data.length;i++){
				chargingitem.push({
					id:data[i].id,
					charge_item_id:data[i].charge_item_id,
					item_code:data[i].item_code,
					dep_id:data[i].dep_id,
					dep_category:data[i].dep_category
				});
			}
			
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({  
		        url:'saveRemoveResult.action',  
		        data:{exam_num:$("#ser_num").val(),
		        	  id:$("#exam_id").val(),
		        	  itemLists:JSON.stringify(chargingitem)},          
		        type: "post",//数据发送方式   
		        success: function(data){
		        	$(".loading_div").remove();
		        	$.messager.alert('提示信息', data,'info');
		        	$("#cyitemList").datagrid('reload');
		        },
		        error:function(data){
		        	$(".loading_div").remove();
		        	$.messager.alert('提示信息', data,function(){});
		        }
			});
		}
	});
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

//弹出框自动关闭
function alert_autoClose(title,msg,icon){  
	 var interval;  
	 var time=500;  
	 var x=1;    //设置时间2s
	$.messager.alert(title,msg,icon,function(){});  
	 interval=setInterval(fun,time);  
	        function fun(){  
	      --x;  
	      if(x==0){  
	          clearInterval(interval);  
	  $(".messager-body").window('close');    
	       }  
	}; 
	}