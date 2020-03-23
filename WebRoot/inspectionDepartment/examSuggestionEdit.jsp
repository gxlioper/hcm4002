<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<style type="text/css">
html{color:#000;background:#fff;}
body,div,ul,li,h1,input,button,textarea{padding:0;margin:0;}
img{border:0;}
li{list-style:none;}
h1{margin:20px auto 0;font-size:25px;width:200px;text-align:center;}
#outer{position:relative;width:800px;margin:auto;margin:20px auto 10px;}
#test1{display:block;width:800px;height:70px;margin-left:0px }
.error{background:#f00;width:800px;height:50px;position:absolute;left:1px;top:10px;opacity:0.6;filter:alpha(opacity=60);}
#test2{display:block;margin-left:60px;width:60px;height:30px;font-size:20px;}
.test3{margin:10px auto;width:800px;}
#test3{border:1px #444 solid;width:800px;min-height:300px;_height:300px;padding-bottom:10px;float:left;}
.test{border-bottom:1px blue dotted;width:783px;padding:10px 5px 5px 10px;float:left;}
.inf{margin-top:15px;float:right;color:#555;}
.con{margin-left:16px;display:inline;width:750px;float:left;word-break:break-all;font-size: 15px;}
.bu{
margin-right: 10px;
display: inline;
width: 50px;
height: 24px;
background-color: #24748f;
float:right;

}
.imgs{width:60px;height:60px;float:left;}
.imgInf{width:120px;background:#f0f;color:#fff;position:absolute;z-index:2;left:-65px;top:62px;opacity:0.5;filter:alpha(opacity=50);}
.finish{background:green;width:300px;height:50px;color:#ff0;font-size:30px;text-align:center;line-height:50px;position:absolute;left:50px;top:10px;opacity:0.6;filter:alpha(opacity=60);}
.imgOut{position:relative;}
.formdiv{
float:left;
margin:10px;
width:800px;
}
#jianyibaocun{
float:right;
margin:10px;

width: 50px;
height: 24px;

margin-right: 10px;
display: inline;

height: 24px;
background-color: #24748f;
}
.xm{
margin-bottom: 16px;
font-size:17px;
width: 790px;
word-break: break-all;
}
</style>
<script type="text/javascript">
var userId=$("#userId").val();
$(document).ready(function () {
	getList();
});
function getList(){
	$.ajax({
        url:'getExamSuggestionList.action',  
        data:{
        	exam_num : $("#exam_num_suggestion").val()
          },          
        type: "post",//数据发送方式   
        success: function(data){
        	if(data != null && data != 'null'){
        		var obj = eval('('+data+')');
        		for(var i = 0; i < obj.length; i++){
        			$('<div class="test"><div class="xm">'+obj[i].chi_name+'&nbsp;&nbsp;&nbsp;&nbsp;'+obj[i].persiondate+'<input type="button" value="删除" id="cs" style="color:#FFFFFF" onclick="javascript:f_delExamSuggestion('+obj[i].id+')" class="bu"/></div><div class="con"></div><div class="inf"></div></div>').prependTo($('#test3'));
      			     $('.con')[0].innerText=obj[i].notices;
      			     if(obj[i].creater!=userId){
      			    	 $("#cs").hide();
      			     }
        		}
        	}
        }
    });
}


function saveExamSuggestionInvoice(){
	if($("#test1").val()==null || $("#test1").val()==""){
		$.messager.alert("提示信息","建议不能为空","");
		return;
	}
/* 	$.messager.confirm('提示信息','是否保存报告预览意见？',function(r){
		if(r){ */
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
		        url:'saveExamSuggestionInfo.action',  
		        data:{
		        	exam_num:$("#exam_num_suggestion").val(),
		        	notices:$("#test1").val(),
		          },          
		        type: "post",//数据发送方式   
		        success: function(data){
		        	$(".loading_div").remove();
		        	//$.messager.alert('提示信息', data,'info');
		        	$('#test3').html("");
		        	getList();
		        	$('#test1').val("");
		        },
				error:function(){
					$(".loading_div").remove();
					$.messager.alert("提示信息","保存失败","error");
				}
		    });
// 		}
//	}); 
}

function removeExamSuggestionInvoice(){
	$("#notices").val("");
	$("#resource").val("");
	$("#apptype").val("");
}

function getinvoiceinfo(){
	$.ajax({
        url:'examSuggestionInfo.action',  
        data:{
        	exam_num : $("#exam_num_suggestion").val()
          },          
        type: "post",//数据发送方式   
        success: function(data){
        	if(data == null || data == 'null'){
        		$("#notices").val('无');
        	}else{
        		var obj = eval('('+data+')');
        		$("#notices").val(obj.notices);
        		$("#resource").val(obj.resource);
        		$("#apptype").val(obj.apptype);
        	}
        }
    });
}

function getExamSuggestionList(){
		var model = {"exam_num" :$("#exam_num_suggestion").val()};
		$("#exam_Suggestionlist").datagrid({
			url : 'getExamSuggestionList.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 5, 10],//可以设置每页记录条数的列表 
			columns : [[ {field:'ck' }, 
			             /* {align : 'center',field : 'edit',title : '修改',width : 10,"formatter":f_edit}, */
			             {align : 'center',field : 'del',title : '删除',	width : 10,"formatter":f_del},
                         {align : 'center',field : 'exam_num',title : '体检编号',	width : 10},
			             {align : 'center',field : 'notices',title : '意见建议',	width : 50},
			             {align : 'center',field : 'creater',title : '操作人',	width :10},
			             {align : 'center',field : 'create_date',title : '操作时间',	width : 12},
			             {align : 'center',field : 'resource',title : '来源',	width : 5},
			             {align : 'center',field : 'apptype',title : '职业体检类型',	width : 10}
			          ]],
			onLoadSuccess : function(value) {
			},
			onDblClickRow : function(index, row) {
				viewExamSuggestion(row);
			},
		    singleSelect : false,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true
		});
}

function viewExamSuggestion(row){
	$("#notices").val(row.notices);
	$("#creater").val(row.creater);
	$("#create_date").val(row.create_date);
}

function show_del(val,row){
	if(row.creater==userId){
		return '<a href=\"javascript:f_delExamSuggestion(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}else{
		return;
	}
}

function f_delExamSuggestion(rowid){
	$.messager.confirm('提示信息','是否确定删除该记录？',function(r){
		if(r){
			$.ajax({
   		url:'delExamSuggestion.action?id='+rowid,
   		type:'post',
   		success:function(data){
   			$('#test3').html("");
   			$.messager.alert('提示信息','删除成功');
   			
   			getList();
   		},
   		error:function(){
   			$.messager.alert('提示信息','操作失败！','error');
   		}
   		});
		}
	});
}
</script>
<input type="hidden" id= "userId" value='<s:property value='model.userId'/>'/>
<form id="add1Form">
	<div class="formdiv" >
		<input type="hidden" id= "old_invoice_class" value='<s:property value='model.invoice_class'/>'/>
		<input type="hidden"  class="textinput" id="exam_num_suggestion" value='<s:property value='model.exam_num'/>' disabled="disabled"/>
<!--  <h1>意见建议</h1> -->
    <div id="outer">
        <textarea  id="test1" maxlength='500'  placeholder="请输入意见建议。"></textarea>
        <input type="button" value="保存" id="jianyibaocun" style="color:#FFFFFF" onclick="javascript:saveExamSuggestionInvoice(0)" class="bu"/>
    </div>
    <div class="test3"><div id="test3"></div></div>
</div>
	
</form>
