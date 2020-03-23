<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
//----------------------------------------添加题目----------------------------------------
var xxbm = "";
var zi="";
$(document).ready(function(){
	
	//性别
	var  sex_x = "<s:property value='sex'/>"
	if(sex_x=='F'){
		$('#nv').attr('checked',true);
	}else if(sex_x=='M'){
		$('#nan').attr('checked',true);
	}else{
		$('#quanbu').attr('checked',true);
	}
	
	
	var  age='<s:property value="age"/>'; 
	if(Number(age)>0){
		$('#s_age').val(age);
	}
	var  ageto='<s:property value="age_to"/>'; 
	if( Number(ageto)>0 ){
		$('#s_to_age').val(ageto);
	}
	
	
	//婚否
	var hunfou = "<s:property value='marriageState'/>";
	if(hunfou=='1'){
		$('#h_yihun').attr('checked',true);
	}else if(hunfou=='2'){
		$('#h_weihun').attr('checked',true);
	}else{
		$('#h_quanbu').attr('checked',true);
	}
	
	//题目类型
	xlqust_type_id();
	
	//题目类型//多选//问答//单选
	var dtlx = "<s:property value='answer_type'/>";
	if(dtlx=='2'){
		$('#t_duoxuan').attr('checked',true);
	}else if(dtlx=='3'){
		$('#t_wenda').attr('checked',true);
		$('#yiji_shou').css('display','none');
	}else{
		$('#t_danxun').attr('checked',true);
	}
	var dep_homepage_show_s="<s:property value='dep_homepage_show'/>";
	if(dep_homepage_show_s=='1'){
		$('#dep_homepage_show1').attr("checked",true);
	}else{
		$('#dep_homepage_show0').attr("checked",true);
	} 
	
	if( $('#x_id').val()>0){
		getupdatexuanxiangmlist();
		getSysQuestionItemsDTOList();
	} 
	
})
function yingcangwenda(){
	$('#yiji_shou').css('display','none');
	$('#neirong').html('');
	$('#xiangmu_list').html('');
}
function xlqust_type_id(){
	$('#qust_type_id').combobox({
		  url:'getDatadis.action?com_Type=WJTMLX',  
		  editable:false,
		  panelHeight:'auto',
		  valueField:'id',    
		  textField:'name',
		  onLoadSuccess:function(){
			 var  zhi = $('#qust_type_id').combobox('getData');
			 var hou = "<s:property value='qust_type_id'/>";
			 if( hou!="" ){
				 for( var i=0; i<zhi.length ; i++ ){
					 if( Number(hou)== zhi[i].id ){
						 $('#qust_type_id').combobox('setValue',zhi[i].id);
						 break;
					 }
				 }
			 }else{
				 $('#qust_type_id').combobox('setValue',zhi[0].id);
			 }
		  }
	})
}
/**
 * table添加行-----//---&nbsp;&nbsp;<a   onclick='zitimuPage(this);'  >添加子题目</a>
 */
function addtr(){
	var str= "<tr style='background: #d9d9d9'>"
				+"<td style='width: 98px;height: 23px;text-align: center;'  ><a  onclick='deletetr(this);'>删除<a>&nbsp;&nbsp;<a   onclick='addcharitem(this);' >推荐项目</a></td>"
				+"<td><input type='text' style='width: 98px;height: 23px'     onblur='xx_updatexm(this);'     onclick='xx_dj(this);' /></td>"
				+"<td><input type='text' style='width: 200px;height: 23px'   /></td>"
				+"<td><input type='text' style='width: 300px;height: 23px'   /></td>"
			+" </tr>";
	$('#neirong').append(str);
}
/*
 * 选项删除行
 */
function deletetr(obj){
	var tr = $(obj).parent().parent();
	$(tr).remove();
	
    var	code = $(tr).children().eq(1).children().eq(0).val();
    var xm_code="";
    $('#xiangmu_list tr').each(function(){
    	xm_code = $(this).children().eq(1).text();
    	if(code==xm_code){
    		$(this).remove();
    	}
    })
}
/**
 * 选项改编码联动项目编码
 */
var dj_code="";
function xx_updatexm(obj){
    var xx_xm_code="";
	$('#xiangmu_list tr').each(function(){
		xx_xm_code = $(this).children().eq(1).text();
    	if(dj_code==xx_xm_code){
    		$(this).children().eq(1).text($(obj).val());
    	}
	}) 
}
/**
 * 选项编码点击事件
 */
function xx_dj(obj){
	dj_code=$(obj).val();
}

/**
 * input边框变色
 */
function inputbianse(obj){
	$(obj).css("border-color","#a4c2f4")
}
/**
 * 问卷推荐项目页面
 */
 function addcharitem(obj){
		var bianma = $(obj).parent().parent().children().eq(1).children(0).val();
		if( bianma == ""){
			$.messager.alert('警告','请输入选项编码！','error');    
			return;
		}
		xxbm = bianma;
		$("#chargItem").dialog({
			title : '选择收费项目',
			width :700,
			height:500,
			href :'addSysSurveyChargItem.action'
		});
		$("#chargItem").dialog('open');
		$("#chargItem").dialog('center'); 
}
/**
 * 添加子题目
 */
 function zitimuPage(obj){
		 var bianma = $(obj).parent().parent().children().eq(1).children(0).val();
		if( bianma == ""){
			$.messager.alert('警告','请输入选项编码！','error');    
			return;
		}
		$("#zitimu").dialog({
			title : '编辑子题目',
			width :800,
			height:510,
			href :'getziTiMuPage.action'
		});
		$("#zitimu").dialog('open'); 
		$("#zitimu").dialog('center'); 
}
//--------------------------------------------------保存一级问题--------------------------------------------
function saveyijiwenti(){
	var qid = $('#qust_type_id').combobox('getValue');
	if($('#qust_type_id').combobox('getValue')<1){
		$.messager.alert("提示信息","请选择题目类型");
		return;
	}
	if( $('#code').textbox('getValue')==""){
		$('#code').textbox('textbox').focus();
		return;
	}
	if( $('#name').textbox( 'getValue' ) == ""){
		$('#name').textbox( 'textbox' ).focus();
		return;
	}
	if( $('#s_age').val() == ""){
		$('#s_age').focus();
		return;
	}
	if( $('#s_to_age').val() == ""){
		$('#s_to_age').focus();
		return;
	}
	
    var   wenti="";
	var   xuanxiangbianma="";//选项编码
	var   bm="1";
	var	  content="";//内容
	var   next_quest_code="";//下一题编号
	
	//选型拼接json数据
	$('#neirong  tr').each(function(){
	   xuanxiangbianma = $(this).children().eq(1).children().val();//选项编码
	   if( xuanxiangbianma=="" ){
		   bm='2';
	   }
	   content = $(this).children().eq(2).children().val();//内容
	   next_quest_code = $(this).children().eq(3).children().val();//下一题编码
	   wenti+="{code:'"+xuanxiangbianma+"',content:'"+content+"',next_quest_code:'"+next_quest_code+"'},";
	})
	
	//判断编码不能为空
	if(bm=="2"){
		$.messager.alert("提示信息","选项编码不能为空","error");
		return;
	}
	
	var wentilist="";
	if( wenti!=""){
		 wentilist = "["+wenti.substring(0,wenti.length-1)+"]";
	}
	
	//判断单选/问答
	if($("input[name='answer_type']:checked").val()!="3" && wenti == ""){
				$.messager.alert("提示信息","请添加问题选项！","error");
			return;
	}
	//保存数据
	baocunwenti(wentilist);
}
/**
 * 保存问题
 */
function baocunwenti(wentilist){
	
	//推荐项目Json
	var xxbm=""
		var sfxmbm="";
		var sfxmmc="";
		var xm="";
		$('#xiangmu_list tr').each(function(){
			xxbm = $(this).children().eq(1).text();//推荐项目选项编码
			xmid = $(this).children().eq(2).children().val();
			xm+="{charge_items_id:'"+xmid+"',quest_option_id:'"+xxbm+"'},";
		})
		var  shoufeixm="";
		if( xm!=""){
			 shoufeixm = "["+xm.substring(0,xm.length-1)+"]";
		}
	//保存问题
	var model={
			wenti:wentilist,
			code:$('#code').val(),
			name:$('#name').val(),
			qust_type_id:$('#qust_type_id').combobox('getValue'),
			sex:$('input[name=sex]:checked').val(),
			age:$('#s_age').val(),
			age_to:$('#s_to_age').val(),
			marriageState:$("input[name='hf']:checked").val(),
			answer_type:$('input[name=answer_type]:checked').val(),
			question_level:'1',
			shoufeixm:shoufeixm,
			objectId:$('#x_id').val(),
			dep_homepage_show:$("input[name='dep_homepage_show']:checked").val()
	};
	$.ajax({
		url:'addSysSurvey.action',
		data:model,
	  	type:'post',
        success:function(data){
        	$('#dlg-custedit').dialog('close')
        	$("#groupusershow").datagrid("reload");
        	$.messager.alert("提示信息",data,"info");
        },
        error:function(){
        	$.messager.alert("提示信息","操作失败！","error");
        }
	})
}
/**
 * 修改时获取选项数据
 */
function getupdatexuanxiangmlist(){
	var model = {
			objectId:$('#x_id').val()
	};
	$.ajax({
		url:'getSysQuestionOptions.action',
		data:model,
		type:'post',
		success:function(data){
			var obj=eval('('+data+')');
			var str="";
			if(obj==''){
				return;
			}
			//xg_getitem(JSON.stringify(obj));
			var s_id="";
			for( var i=0; i<obj.length; i++){
			 s_id+=obj[i].objectId+",";
			 str+= "<tr style='background: #d9d9d9'>"
					+"<td style='width: 98px;height: 23px;text-align: center;'  ><a  onclick='deletetr(this);'>删除<a>&nbsp;&nbsp;<a   onclick='addcharitem(this);' >推荐项目</a></td>"
					+"<td><input type='text' style='width: 98px;height: 23px'   data='"+obj[i].objectId+"'  value='"+obj[i].code+"'    onblur='xx_updatexm(this);'   onchange='xx_updatexm(this);'   onclick='xx_dj(this);'  /></td>"
					+"<td><input type='text' style='width: 200px;height: 23px' value='"+obj[i].content+"'   /></td>"
					+"<td><input type='text' style='width: 300px;height: 23px' value='"+obj[i].next_quest_code+"'   /></td>"
				+" </tr>";
			}
			$('#neirong').append(str);
			
			var s_id = s_id.substring(0,s_id.length-1);
			xg_getitem(s_id);
			
		}
	})
}
/**
 * 修改页面获取项目
 */
function xg_getitem(sid){
	$.ajax({
		url:'getSysQuestionItemsDTOList.action',
		data:{ids:sid},
		type:'post',
		dataType:'json',
		success:function(data){
			var  we="";
			for(var i=0; i<data.length; i++){
			   we+= " <tr style='background: #d9d9d9'> "
					+"<td  style='width:220px;text-align: center;' ><a  onclick='tujjian_tr(this);' >删除</a></td>"
					+"<td  style='width:100px'>"+data[i].code+"</td>"
					+"<td  style='width:200px'>"+data[i].item_code+"<input type='hidden'  value='"+data[i].so_id+"'/></td>"
					+"<td  style='width:200px'>"+data[i].item_name+"</td>"
				 +" </tr> ";
			}
			$('#xiangmu_list').append(we);
		},
		error:function(){
			
		}
	})
}
/**
 * 修改时删除
 */
function tujjian_tr(obj){
	var tr = $(obj).parent().parent();
	$(tr).remove();
}
/*
 * 修改时获取项目数据
 */
function getSysQuestionItemsDTOList(){
	var model = {
			objectId:$('#x_id').val()
	};
	$.ajax({
		url:'getSysQuestionItemsDTOList.action',
		data:model,
		type:'post',
		success:function(data){
			var obj = eval('('+data+')')
			if(obj==""){
				return;
			}
		/* 	var sr="";
			for(var i=0; i<obj.length; i++){
				sr+=" <tr style='background: #d9d9d9'> "
					+"<td  style='width:220px;text-align: center;' ><a  onclick='tujjian_tr(this);' >删除</a></td>"
					+"<td  style='width:100px'>"+obj[i].+"</td>"
					+"<td  style='width:200px'>"+obj[i].item_code+"<input type='hidden'  value='"+rows[index].id+"'/></td>"
					+"<td  style='width:200px'>"+obj[i].item_name+"</td>"
				 +" </tr> ";
			
			$(xiangmu_list).append(sr);
			} */
		}
	})
}
</script>
<input type="hidden"  id="x_id" value="<s:property value='objectId'/>" />
 <fieldset style=" margin:10px;margin-bottom:0px;margin-top:5px;">
	<legend><strong>问题编辑</strong></legend> 
	<div class="formDiv">
		<dl  style="height: 24px;">
			<dt style="width:80px"  >题目编码</dt>
			<dd>
				<input type="text"    id="code" class = "easyui-textbox"    data-options="required:true,prompt:'请输入编码',missingMessage:'此项为必输项！'"
			 	style="width: 150px;height: 23px"   value="<s:property value='code'/>"   />
				<span id='code_cunzai'></span>
			</dd>
			<dt style="width:80px">题目名称</dt>
			<dd>
				<input type="text" id="name" class = "easyui-textbox" style="width: 150px;height: 23px"    value='<s:property value="name"/>'  data-options="required:true,prompt:'请输入题目名称',missingMessage:'此项为必输项！'"/>
			</dd>
			<dt  style="width:80px">题目类型</dt>
			<dd>
				<input  id = "qust_type_id" type="text" class = "easyui-combobox" style="width: 150px;height: 23px"  data-options="prompt:'请选择题目类型'"/>
			</dd>			
		</dl>
		<dl style="height: 24px;">
			<dt  style="width:80px">性别：</dt>
			<dd style="width: 150px">
				<input type="radio"  id="quanbu"  name="sex"  value="A"   checked="true" />全部
				<input type="radio"  id="nan" name="sex"  value="M"    />男
				<input type="radio"  id="nv"  name="sex"  value="F"    />女
			</dd>
			<dt style="width:80px" >年龄：</dt>
			<dd>
				
				<input type="text"  class="textinput easyui-validatebox"  id="s_age"	 data-options="required:true,missingMessage:'此项为必填项！'"     value='1'     style="width:67px;height: 23px;"  />
				~
				<input type="text"  class="textinput easyui-validatebox"  data-options="required:true,missingMessage:'此项为必填项！'"    id="s_to_age"  value='100'     style="width:67px;height: 23px;"  />
			</dd>
			<dt   style="width:80px" >婚否：</dt>
			<dd>
				<input type="radio"  name="hf"  id="h_quanbu" value="0"   checked="checked" />全部
				<input type="radio"  name="hf"  id="h_weihun" value="2"     />未婚
				<input type="radio"  name="hf"  id="h_yihun" value="1"   />已婚
			</dd>
			
		</dl>
		<dl style="height: 24px;">
			<dt style="width:80px" >
				题目类型
			</dt>
			<dd>
				<input type="radio"  name="answer_type" id="t_danxuan"   value="1"  checked="checked"  onclick="javascript:$('#yiji_shou').css('display','block');"  />单选
				<input type="radio"  name="answer_type"  id="t_duoxuan"  value="2"  onclick="javascript:$('#yiji_shou').css('display','block');"     />多选
				<input type="radio"  name="answer_type"  id="t_wenda"    value="3"  onclick="yingcangwenda();" />问答
			</dd>
			<dt>科室首页显示</dt>
			<dd>
				<input type="radio"  name="dep_homepage_show"    id="dep_homepage_show1"  value="1"     />是
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio"  name="dep_homepage_show"  id="dep_homepage_show0"    value="0"  checked="checked" />否
			</dd>
		</dl>
	</div>
</fieldset>
<div  id="yiji_shou">
<fieldset style=" margin:10px;margin-bottom:0px;margin-top:5px;">
<legend><strong><input type="button"  value="添加选项"   onclick="addtr();"  /></strong></legend> 
<table >   
    <thead  style="background: #a4c2f4">   
        <tr>   
            <th  style="width:223px">操作</th>   
            <th style="width: 100px">选项编码</th>   
            <th style="width: 200px">内容</th>   
            <th style="width: 300px">下一题编码</th>   
        </tr>   
    </thead>   
    <tbody  id="neirong">   
    </tbody>   
</table>  
</fieldset>
<fieldset style=" margin:10px;margin-bottom:0px;margin-top:5px;margin-bottom: 100px">
<legend><strong>推荐项目</strong></legend> 
<table  >   
    <thead  style="background: #a4c2f4">   
        <tr>   
            <th  style="width:223px">操作</th>   
            <th style="width: 100px">选项编码</th>   
            <th style="width: 200px">收费项目编码</th>   
            <th style="width: 300px">收费项目名称</th>   
        </tr>   
    </thead>   
    <tbody  id="xiangmu_list">   
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
		    <a href="javascript:saveyijiwenti();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>
 <div id="chargItem" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:100"></div>
 <div id="zitimu" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:100"></div>