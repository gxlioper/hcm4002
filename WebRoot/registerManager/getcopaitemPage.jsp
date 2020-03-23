<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="s" uri="/struts-tags"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	//dgridshow();
})
function dgridshow(){
	       var chk_value ="";    
		  $('input[name = chkItem]:checked').each(function(){    
		   chk_value=chk_value+","+($(this).val());    
		  });  
		  if(chk_value.length>1){
			  chk_value=chk_value.substring(1,chk_value.length);
		  }
		  var model =  {
				  "exam_num":$("#exam_num_f").val(),
				  "chkItem":chk_value,
				  "custname":$("#custname_f").val(),
				  "id_num":$("#id_num_f").val(),
				  "time1":$("#start_date_f").datebox('getValue'),
			      "time2":$("#end_date_f").datebox('getValue'),
			      "arch_num":$("#arch_num_f").val()
		  };
		     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $(".loading_div").remove();
			
		     $("#showexaminfo").datagrid({
			 url:'getDjtExamInfoUserList.action',
			 dataType: 'json',
			 queryParams:model,
			 pageNumber:1,
		     pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,100,300,500,1000],//可以设置每页记录条数的列表 
			 columns:[[
	        /*     {field:'ck',checkbox:true }, */
			    {align:'center',field:'exam_num',title:tjhname,width:28,sortable:true},
			    {align:'center',field:'arch_num',title:dahname,width:20,sortable:true},
			 	{align:'center',field:'user_name',title:'姓名',width:25,sortable:true},
			 	{align:'center',field:'id_num',title:'身份证号',width:40,sortable:true},
			 	/* {align:'center',field:'employeeID',title:'工号',width:18}, */
			   /*  {align:'center',field:'exam_types',title:'体检类型',width:18},	 */
			 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
			 /* 	{align:'center',field:'is_marriage',title:'婚否',width:10}, */
			 /* 	{align:'center',field:'age',title:'年龄',width:10,sortable:true}, */
			 /* 	{align:'center',field:'phone',title:'电话',width:25,sortable:true}, */
			/*  	{align:'center',field:'company',title:'单位',width:60}, */
			 	/* {align:'center',field:'set_name',title:'套餐',width:60},	 */
			 	{align:'center',field:'join_date',title:'体检日期',width:20,sortable:true},	 	
			 /* 	{align:'center',field:'huishou',title:'回收',width:10,"formatter":f_huishoutatus}, */
			 /* 	{align:'center',field:'freezename',title:'冻结',width:10}, */
			   {align:'center',field:'fuzhi',title:'复制',width:15,"formatter":f_fuzhi}
			 /* 	{align:'center',field:'introducer',title:'介绍人',width:15},
			 	{align:'center',field:'chi_name',title:'创建者',width:15},		 	
			 	{align:'center',field:'final_doctor',title:'总检医生',width:18},
			 	{align:'center',field:'check_doctor',title:'审核医生',width:18} */
			 	]],	    	
		    	onLoadSuccess:function(value){ 
		    		$('#exam_num').textbox("textbox").focus();
		    	},
				rowStyler:function(index,row){    
			        if (row.freeze==1){    
			            return 'color:#f00;';    
			        }    
			    },
		    	onDblClickRow : function(index, row) {	    		
		    		examnumenterDou(row.exam_num);
				},
				height: window.screen.height-410,
				nowrap:false,
				rownumbers:true,
		    	singleSelect:false,
			    collapsible:true,
				pagination: true,
			    fitColumns:true,
			    striped:true
		});
}
function f_fuzhi(val,row){	
	return '<a href=\"javascript:fuzhiitem(\''+row.exam_num+'\')\"><img src=\"themes/al/img/fuzhi.png\" width=\"20\" height=\"20\" alt=\"复制\" /></a>';
}
/**
 * 复制他人项目
 */
 function fuzhiitem(exam_num){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			$.ajax({
				url:'getcopaitem.action',
				data:{
					exam_num:exam_num,
					copy_status:'Z'
					},
				type:'post',
				success:function(data){
					$('#dlg-item-fuzhi').dialog('close')
					$(".loading_div").remove();
					$.messager.alert('提示信息','操作成功！','info');
					deltiemflags=1;
					delsetflags=1;
					var row = eval('('+data+')');
					var item = row.li;
					var set = row.se;
					console.log(row);
					//复制项目
					for(var i = 0 ; i < item.length ; i++){
						tcinsertitem(item[i]);
						copy_item = "N";
					}
					//复制套餐
					for(var i = 0 ; i < set.length ; i++){
						copySetItem(set[i]);
					}
					copy_item = "Y";
				},error:function(){
					$(".loading_div").remove();
					$.messager.alert('提示信息','操作失败','error');
				}
			})
}

 /**
  * 复制套餐
  */
 function copySetItem(userid) {
 	var rowsLength = $('#selectctlist').datagrid('getRows');
 	var flag = true;// 不相等
 	if (!rowsLength.length == 0) {
 		var flag = true;// 不相等
 		for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
 		{
 			if (userid.set_num == rowsLength[j].set_num) {
 				flag = false;// 相等
 				//$.messager.alert("操作提示", "该套餐已添加", "error");
 				break;
 			} else {
 				flag = true;
 			}
 		}// j End
 	}
 	if (flag == true) {
 		var usersex = $("#custsex").val();
 		var sexflag = false;
 		if (usersex == '') {
 			sexflag = true;
 		} else if (userid.sex == '全部') {
 			sexflag = true;
 		} else if (usersex == userid.sex) {
 			sexflag = true;
 		}
 		if (sexflag) {
 			$('#selectctlist').datagrid("appendRow", {
 				set_num : userid.set_num,
 				set_name : userid.set_name,
 				sex : userid.sex,
 				set_discount : userid.set_discount,
 				set_amount : userid.set_amount
 			});
 		} else {
 			// alert("套餐性别冲突111，不能添加。");
 		}
 	}
 }
</script> 

	 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
	<div class="user-query">
				<dl>
				    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_num"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num_f" value="" style="height:26px;width:100px;ime-mode: disabled;"/></dd> 
					<dt style="height:26px;width:60px;"><input id="chkItem" name="chkItem" type="checkbox" value="custname"/>姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname_f" value="" style="height:26px;width:135px;"/></dd>
					 		<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="register_date"/>体检日期</dt>
			         <dd><input class="easyui-datebox" type=text id="start_date_f" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="end_date_f" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd> 
				</dl>
				<dl>
					 <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="arch_num"/><s:text name="dahname"/></dt>					
					<dd><input class="textinput"   id="arch_num_f" value="" style="height:26px;width:100px;"/></dd>  
			
					  <dt style="height:26px;width:60px;"><input id="chkItem" name="chkItem" type="checkbox" value="id_num"/>身份证</dt>					
					 <dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>
										<dd><a href="javascript:dgridshow();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
				</dl>
			</div>		
 </fieldset>
	<table id="showexaminfo" >

	</table>
