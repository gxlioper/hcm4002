<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
//---------------------------------添加子题目--------------------------------
$(document).ready(function(){
	getgroupuserGriss();
})
/*
 * 子标题收费项目列表
 */
function getgroupuserGriss(){
    $("#aj").datagrid({
	 url:'getSysSurveyChargItem.action',
	 queryParams:{
		 ss_code:$('#a_code').val(),	//收费项目编码
		 ss_name:$('#a_name').val()//收费项目名称
	 },
	 type:'post',
	 rownumbers:false,
    pageSize:10,	
   // pageNumber:page,
    pageList:[10,20,30,40,50],//可以设置每页记录条数的列表 
	 columns:[[
           {align:'center',field:'item_code',title:'收费项目编码',width:18},	
           {align:'center',field:'item_name',title:'收费项目名称',width:28},
		 	{align:'center',field:'sss',title:'添加',width:10,formatter:add_zitimu_tr},
		 	{align:'center',field:'ss',title:'删除',width:10}
	 	]],	    	
   	onLoadSuccess:function(value){
   		$("#datatotal").val(value.total);
   		$(".loading_div").remove();
   	},
   	//singleSelect:true,
   	//checkOnSelect:false,
   	//selectOnCheck:false,
   	singleSelect:false,
	    collapsible:false,
		pagination:true,//分页控件
	    fitColumns:true,//自适应
	    striped:true,
       singleSelect:true//只允许选择一行
	});
}
/**
 * 二级答案添加行
 */
function add_zitimu_tr(){
	var str= "<tr style='background: #d9d9d9'>"
				+"<td style='width: 120px;height: 26px;text-align: center;'  ><a  onclick='deletetr(this);'>删除<a/>&nbsp;&nbsp;<a   onclick='add_zi_charg_item(this);' >推荐项目</a></td>"
				+"<td style='width: 200px;' ><input type='text' style='width:200px;height: 26px'  /></td>"
				+"<td style='width: 200px;' ><input type='text' style='width: 200px;height: 26px'   /></td>"
				+"<td style='width: 200px;' ><input type='text' style='width: 200px;height: 26px'   /></td>"
			+" </tr>";
	$('#zixiangmu_list').append(str);
}
/**
 * 子标题推荐项目
 */
function add_zi_charg_item(obj){
	var bianma = $(obj).parent().parent().children().eq(1).children(0).val();
	if( bianma == ""){
		$.messager.alert('警告','请输入选项编码！','error');    
		return;
	}
	xxbm = bianma;
	$("#zi_xiangmu_item").dialog({
		title : '选择收费项目',
		width :700,
		height:500,
		href :'getzi_chagr_item.action'
	});
	$("#zi_xiangmu_item").dialog('open');
	$("#zi_xiangmu_item").dialog('center');
}
//------------------------------------保存二级项目---------------------------------------------------
function baocunerjixiangmu(){
	if( $('#a_code').val()== ""){
		 $.messager.alert("提示信息","项目编码不能为空！","error");
		return;
	}
	
	var bm="";
	//选项拼接List数据
	var er_bianma="";
	var er_neirong="";
	var er_xiatibianma="";
	var er_wenti="";
	$("#zixiangmu_list  tr").each(function(){
		er_bianma =  $(this).children().eq(1).children().val();//选项编码
		if(er_bianma==""){
			bm='2';
		}
		er_neirong =  $(this).children().eq(2).children().val();//内容
		er_xiatibianma =  $(this).children().eq(3).children().val();//下一题编码
		er_wenti+="{code:'"+er_bianma+"',content:'"+er_neirong+"',next_quest_code:'"+er_xiatibianma+"'},";
	})
	
	//判断编码不能为空
	if(bm=="2"){
		$.messager.alert("提示信息","选项编码不能为空","error");
		return;
	}
	
	var er_wentilist="";
	if( er_wenti!=""){
		 er_wentilist = "["+er_wenti.substring(0,er_wenti.length-2)+"]";
	}
	
	//判断单项问答项目
	
	if($('input[name=a_t]:checked').val()!="问答" && er_wenti == ""){
		$.messager.alert("提示信息","请添加问题选项！","error");
		return;
	}
	
	baocunerjishuju(er_wentilist);
}
/**
 * 保存二级问题数据
 */
function baocunerjishuju(wentilist){
	var model={
			wenti:wentilist,
			code:code,
			content:content,
			next_quest_code:next_quest_code,
			question_level:'2'
	};
	
	$.ajax({
		url:'',
		data:model,
		type:'post',
		dataType:'json',
		success:function(data){
			$.messager.alert("提示信息","保存成功！","info");
		},
		error:function(){
			$.messager.alert("提示信息","操作失败","error");
		}
	})
}
</script>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>题目编辑</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width:70px;">项目编码</dt>
					<dd><input class="textinput"  type="text" id="a_code"  style="height:23px;width:140px;"
					class="easyui-validatebox" /></dd>
					<dt style="width: 70px">项目名称</dt>
					<dd><input class="textinput"  type="text" id="a_name"   style="height:23px;width:140px;"/></dd>
					<dt  style="width: 70px">题目类型</dt>
					<dd>
						<input type="radio"  name="a_t"	  value="单选"   checked="checked"     onclick="javascript:$('#er_ji').css('display','block');"    />单选
						<input type="radio"  name="a_t"   value="多选"     onclick="javascript:$('#er_ji').css('display','block');"   />多选
						<input type="radio"  name="a_t"   value="问答"    onclick="javascript:$('#er_ji').css('display','none');"   />问答
					</dd>
				</dl>
			</div>
 </fieldset>
<div id="er_ji">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong><input type="button"  value = "添加二级答案"  onclick="add_zitimu_tr();"  /></strong></legend> 
 <table >   
    <thead  style="background: #a4c2f4">   
        <tr>   
            <th  style="width:120px">操作</th>   
            <th style="width: 200px">选项编码</th>   
            <th style="width: 200px">内容</th>   
            <th style="width:200px">下一题编码</th>   
        </tr>   
    </thead>   
    <tbody  id="zixiangmu_list">   
    </tbody>   
</table>  
 </fieldset>
<fieldset style="margin:5px;padding-right:0;margin-bottom: 50px;">
<legend><strong>推荐项目</strong></legend> 
<table  >   
    <thead  style="background: #a4c2f4">   
        <tr>   
            <th  style="width:120px">操作</th>   
            <th style="width: 200px">选项编码</th>   
            <th style="width: 200px">收费项目编码</th>   
            <th style="width: 200px">收费项目名称</th>   
        </tr>   
    </thead>   
    <tbody  id="zi_xiangmu_list">   
    </tbody>   
</table>  
</fieldset>
</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
		<div>
			<span   id="zhis" ></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <a href="javascript:baocunerjixiangmu();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#zitimu').dialog('close')">关闭</a>
		</div>
	</div>
</div>
<div id="zi_xiangmu_item" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:10"></div>