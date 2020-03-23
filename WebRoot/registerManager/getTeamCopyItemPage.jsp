<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="s" uri="/struts-tags"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(function(){
	
	teamGridShow();
	
	/* $('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    }); */
})



/* var hc_com_list,com_Namess;
 function select_com_list(com_Namess){
 	var url='companychangshow.action';
 	var data={"name":com_Namess};
 	load_post(url,data,select_com_list_sess);
 }

function select_com_list_sess(data){
	mydtree = new dTree('mydtree','../../images/img/','no','no');
	mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
	for(var index = 0,l = data.length;index<l;index++){
		if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
			mydtree.add(data[index].id,
					0,
					data[index].text,
					"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
					data[index].text
					,"_self",false);
		}else{
			mydtree.add(data[index].id,
					data[index].attributes,
					data[index].text,
					"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
					data[index].text,"_self",false);
		}
	}
	$("#com_name_list_div").html(mydtree.toString());
	$("#com_name_list_div").css("display","block");
	
}

function setvalue(id,name){
	$("#company_id").val(id);
	$("#com_Name").textbox('setValue',name);
	$("#com_name_list_div").css("display","none");		
}

var hc_mous_select1=false;
function select_com_list_over(){
	if(!hc_mous_select1){
	$("#com_name_list_div").css("display","none");
	}
}

function select_com_list_moverT(){
	hc_mous_select1=true;
}

function select_com_list_amoverT(){
	hc_mous_select1=false;
} */
 
function teamGridShow(){
	      var chk_value ="";    
		  $('input[name = chkItem]:checked').each(function(){    
		   chk_value=chk_value+","+($(this).val());    
		  });  
		  if(chk_value.length>1){
			  chk_value=chk_value.substring(1,chk_value.length);
		  } 
			 var model = {
					 exam_num:$("#exam_num_f").val(),
					 chkItem:chk_value,
					 time1:$('#c_start_date_f').datebox('getValue'),
					 time2:$('#c_end_date_f').datebox('getValue'), 
					 custname:$("#custname_f").val(),
					 id_num:$("#id_num_f").val(),
					 arch_num:$('#arch_num_f').val()
					 
			 };
		     $("#showexaminfo").datagrid({
			 url:'getDjtExamInfoUserListCopyItem.action',
			 dataType: 'json',
			 pageNumber:1,
			 queryParams:model,
		     pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,100,300,500,1000],//可以设置每页记录条数的列表 
			 columns:[[
	        /*     {field:'ck',checkbox:true }, */
			    {align:'center',field:'exam_num',title:'体检号',width:28,sortable:true},
			    {align:'center',field:'arch_num',title:'档案号',width:20,sortable:true},
			 	{align:'center',field:'user_name',title:'姓名',width:25,sortable:true},
			 	{align:'center',field:'id_num',title:'身份证号',width:40,sortable:true},
			 	/* {align:'center',field:'employeeID',title:'工号',width:18}, */
			    {align:'center',field:'exam_types',title:'体检类型',width:18},
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
					$('#dlg-item-copy').dialog('close')
					$(".loading_div").remove();
					$.messager.alert('提示信息','操作成功！','info');
					deltiemflags=1;
					delsetflags=1;
					var row = eval('('+data+')');
					var item = row.li;
					var set = row.se;
					//console.log(row);
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
				//insertsettiem(userid.set_num);
			} else {
				// alert("套餐性别冲突111，不能添加。");
			}
		}
	}
</script> 

<fieldset style="margin:5px;padding-right:0;">
<input type="hidden" id="company_id" value="0">
<legend><strong>信息检索</strong></legend>
	<div class="user-query">
				<dl>
				     <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_num"/>体检号</dt>
					 <dd><input class="easyui-textbox"  type="text" id="exam_num_f" value="" style="height:26px;width:100px;"/></dd> 
					 <dt style="height:26px;width:60px;"><input id="chkItem" name="chkItem" type="checkbox" value="custname"/>姓名</dt>
					 <dd><input class="easyui-textbox"  type="text" id="custname_f" value="" style="height:26px;width:135px;"/></dd>
					 <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="register_date"/>体检日期</dt>
			         <dd><input class="easyui-datebox"  id="c_start_date_f"  value="<s:property value="model.time1"/>"  style="width:100px;height:26px;"/>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd><input class="easyui-datebox" id="c_end_date_f"  value="<s:property value="model.time2"/>"  style="width:100px;height:26px;"/></dd> 
				</dl>
				<dl>
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="arch_num"/>档案号</dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num_f" value="" style="height:26px;width:100px;"/></dd>  
					<dt style="height:26px;width:60px;"><input id="chkItem" name="chkItem" type="checkbox" value="id_num"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num_f" value="" style="height:26px;width:135px;"/></dd>
					<dd><a href="javascript:teamGridShow();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
				</dl>
			</div>		
 </fieldset>
	<table id="showexaminfo" >

	</table>
