$(document).ready(function () {
	chaxun();
});
/**
 * 早餐科室
 */

function chaxun(){
	getGrid();
	getcustomerInfo();
}
//查询人员基本信息
function getcustomerInfo(){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#ser_num").val(),
		type:'post',
		success:function(data){
			if(data == 'null'){
				$("#arch_num").html('');
				$("#exam_id").val('');
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
			$("#arch_num").html(obj.arch_num);
			$("#exam_id").val(obj.id);
			$("#user_name").html(obj.user_name);
			$("#sex").html(obj.sex);
			$("#age").html(obj.age);
			$("#company").html(obj.company);
			$("#customer_type").html(obj.customer_type);
			$("#set_name").html(obj.set_name);
		}
	});
}
//检查项目列表
function getGrid(){
	var model = {"exam_num":$("#ser_num").val()};
	$("#zcitemList").datagrid({
		url: 'breakfastList.action',
		queryParams: model,
		rownumbers:true,
		//pageSize: 15,//每页显示的记录条数，默认为10 
		//pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		columns:[[
		          {field:'ck',checkbox:true },
		          {align:"center",field:"id","title":"ID","hidden":true},
		          {align:"center",field:"item_code","title":"项目编码","width":20},
		          {align:'center',field:"item_name","title":"项目名称","width":20},
		          {align:'center',field:"exam_status","title":"检查状态","width":20},
		          {align:'center',field:"exam_date","title":"检查日期","width":20},
		          {align:'center',field:"exam_doctor_name","title":"检查医生","width":20},
		          ]],
		          onLoadSuccess:function(){
                      $('#zcitemList').datagrid('selectAll');
                  },        
                  toolbar: [{ 
				 	  text:'保存',
			          iconCls:'icon-save',    
			          handler:function(){
			        	var ids = new Array();
			  	    	var Items = $('#zcitemList').datagrid('getChecked');
			  		    $.each(Items, function(index, item){
			  		        ids.push(item.id);
			  		    }); 
			  		  updatebatch(ids.toString());
			          }    
		          },{ 
				 	  text:'关闭',
			          iconCls:'icon-close',    
			          handler:function(){
			        	  close_page();
			          }    
		          }],          
	    singleSelect:false,
	   // collapsible:true,
		fitColumns:true,
		fit:true,
		border:false
	});
}
/**
 * 批量更新
 */
function updatebatch(ids){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'updaterBreakStatus.action',
		data:{ids:ids},
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert("操作提示",data, "info",close_page);
		},
		error:function(){
			$(".loading_div").remove();
			$.messager.alert("警告","操作失败","error");
		}
	})
}

//FF中需要修改配置window.close方法才能有作用，为了不需要用户去手动修改，所以用一个空白页面显示并且让后退按钮失效
//Opera浏览器旧版本(小于等于12.16版本)内核是Presto，window.close方法有作用，但页面不是关闭只是跳转到空白页面，后退按钮有效，也需要特殊处理
function close_page(){
	var _parentWin =  window.opener ;
	var userAgent = navigator.userAgent;
	  window.opener = null;
	  window.open('', '_self');
	  window.close();
	  _parentWin.shuaxing();
}


