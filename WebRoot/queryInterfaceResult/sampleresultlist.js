$(document).ready(function () {
	$('#sample_type').combobox({
		 	url:"getSampleType.action",
		    valueField:"id",    
		    textField:"demo_name",
		    prompt:'请选择标本类型'
	 }
			 )
	 getSampleResultList();
	$('#exam_num').css('ime-mode','disabled');
	$('#exam_num').focus();
});

/**
 * 清空查询
 */
function empty(){
	 $('#user_name').val("");
	 $('#exam_num').val("");
	 $('#sample_datestart').datebox('setValue','');
	 $('#sample_dateend').datebox('setValue','');
	 $('#sample_type').combobox('setValue','');
	 getSampleResultList();
}

function getSampleResultList(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $("#sampleResultShow").datagrid({
		 url:'getSampleResultList.action',
		 queryParams:{
			 exam_num:$('#exam_num').val(),
			 user_name:$('#user_name').val(),
			 sample_datestart:$('#sample_datestart').datebox('getValue'),
			 sample_dateend:$('#sample_dateend').datebox('getValue'),
			 sample_type_id:$('#sample_type').combobox('getValue')
		 },
		 toolbar:toolbar,
//		 dataType: 'json',
		 rownumbers:false,
		 fit:true,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[	
			 {field:'ck',checkbox:true },
			 {align:'center',field:'exam_num',title:tjhname,width:10},
		        {align:'center',field:'user_name',title:'姓名',width:10},
		        {align:'center',field:'item_name',title:'收费项目',width:18},
	            {align:'center',field:'sample_applyid',title:'条形码',width:20},
			 	{align:'center',field:'sample_type',title:'标本类型',width:25},
			 	{align:'center',field:'sample_creater',title:'采集人',width:15},
				{align:'center',field:'sample_createdate',title:'采集时间',width:15},
				{align:'center',field:'sample_reportdate',title:'报告时间',width:15}
		 	]],	   
	    	onLoadSuccess:function(value){
	    		MergeCells('sampleResultShow','exam_num,user_name');
	    		$.ajax({
	    			url:'getSampleResultListShuliang.action',
	    			data:{
	    				 exam_num:$('#exam_num').val(),
	    				 user_name:$('#user_name').val(),
	    				 sample_datestart:$('#sample_datestart').datebox('getValue'),
	    				 sample_dateend:$('#sample_dateend').datebox('getValue'),
	    				 sample_type_id:$('#sample_type').combobox('getValue')
	    			},
	    			type:'post',
	    			success:function(data){
	    				//zrs:'0',yc:'0',xygs:'0',xyrs:'0'}
	    				//alert(data);
	    				var data = eval('('+data+')');
	    				$('#zrs').text(data.zrs);
	    				$('#yc').text(data.yc);
	    				$('#xygs').text(data.xygs);
	    				$('#xyrs').text(data.xyrs);
		    				$("#datatotal").val(value.total);
		    	    		$(".loading_div").remove();
	    			},error:function(){
	    				$("#datatotal").val(value.total);
	    	    		$(".loading_div").remove();
	    				$.messager.alert("提示信息","查询统计数量操作失败","error");
	    			}
	    		})
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
}

/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param tableID 要合并tableID的id
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
