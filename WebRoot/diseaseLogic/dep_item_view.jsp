<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<style>
	#viewItem dd{
		width: 180px;
		margin-top:5px;
	}
	#viewItem .combo{
		border: 0px;
	}
</style>
<script type="text/javascript">
$(function(){
	var brief_mark = "<s:property value='brief_mark'/>";
	if(brief_mark=='1'){
		$("#briefMark").html("是");
	}else{
		$("#briefMark").html("否");
	}
	
	var brief = "<s:property value='brief'/>";
	if(brief=='1'){
		$("#briefView").html("疾病逻辑");
	}else{
		$("#briefView").html("参考值");
	}
	
	$('#item_type').combobox({
		url:"getDatadisKongGe.action?com_Type="+"JCXMFL",
	    valueField:'id',
	    textField:'name',
	    editable:false,
	    onLoadSuccess : function(data){//下拉框默认选择
	       var val = $(this).combobox('getData');
	       var item_type='<s:property value="model.item_type"/>';
    	   for (var item in val[0]) {  
               if (item == 'id') {
           	   		if(item_type!=''){
           	   			$('#item_type').combobox('setValue',item_type); 
           	   		}else{
           		 		 $(this).combobox('select', val[0][item]);
           	   		}
               }  
           }  
	    }
	});
	
	//$("#item_type").combobox({ disabled: true });
		
	$('#item_name').blur(function(){
		pinying();
	})
	$('#exam_num').blur(function(){
		examnum();
	})
	$('#view_num').blur(function(){
		viewnum();
	})
	
	$('#item_name').validatebox({
		required:'true'
	})
	$('#ref_Mmax,#ref_Mmin,#ref_Fmin,#ref_Fmax,#dang_Fmax,#dang_Fmin,#dang_Mmax,#dang_Mmin,#error_max,#error_min').validatebox({
			validType:'IsNumber_Amt',
			invalidMessage:'请输入数字或者小数'
	})
	/* $('#seq_code,#default_value').validatebox({
		validType:'IsNumber'
	}) */
	
	$('#item_pinyin').blur(function(){
		$('#item_pinyin').validatebox({
			required:'true'
		})
	})
		$($("input[name='leixing']")).click(function(){
			var a =$("input[name='leixing']:checked").val();
			if(a=="短文本型"){
				$("#numType").hide();
				$("#crNum").hide();
				$("#textType").show();
				$("#crText").show();
				
				
			}
			if(a=="数字型"){
				$("#numType").show();
				$("#crNum").show();
				$("#textType").hide();
				$("#crText").hide();
				//$('#referA,#referB,#referC,#referD,#CrisisA,#CrisisB,#CrisisC,#CrisisD').val('');
			}
		})
		$('#item_type').combobox({
			url:"getDatadisKongGe.action?com_Type="+"JCXMFL",
		    valueField:'id',
		    textField:'name',
		    editable:false,
		    onLoadSuccess : function(data){//下拉框默认选择
		       var val = $(this).combobox('getData');
		       var item_type='<s:property value="model.item_type"/>';
	    	   for (var item in val[0]) {  
	               if (item == 'id') {
	           	   		if(item_type!=''){
	           	   			$('#item_type').combobox('setValue',item_type); 
	           	   		}else{
	           		 		 $(this).combobox('select', val[0][item]);
	           	   		}
	               }  
	           }  
		    }
		});
		/* 根据数据库的radio值选择单选框 */
	 	var is_printC='<s:property value="model.is_print"/>';
		if(is_printC=="N"){
			$("#rb").attr("checked",true);
			$("#ra").attr("checked",false);
		} 
		var lx='<s:property value="model.item_category"/>';
		if(lx=="短文本型"){
		  $("#item_categoryb").attr("checked",true);
		  $("#item_categorya").attr("checked",false);
			$("#numType").hide();
			$("#textType").show();
			$("#crNum").hide();
			$("#crText").show();
		}
		if(lx=="数字型"){
			$("#numType").show();
			$("#textType").hide();
			$("#crNum").show();
			$("#crText").hide();
		}
})
//-------------------------------------新增检查项目&修改检查项目----------------------------
/**
 * 关联检验编码验证
 */
function examnum() {
	//检验编码
	if($('#id').val()>0){
		/* alert("id"+$('#id').val()); */
		var examnums="<s:property value='exam_num'/>";
		var  fs=$('#exam_num').val();
		/* alert("原检验编码"+examnums+"输入框检验编码"+fs); */
		if(examnums==fs){
				return;
		}
	}
	var f=0;
	$.ajax({
		url:'getexamnum.action',
		type : 'post',
		async:false,
		data:{exam_num:$('#exam_num').val()},
		success : function(data) {
			if (data=='ok') {
				f=1;
			} else if (data=='no') {
				$.messager.alert('警告信息','关联检验编码已存在','error');
			}
		}
	});
	return f;
}
/**
 * 关联影像编码验证
 */
function viewnum() {
	if($('#id').val()>0){
		var view_nums="<s:property value='view_num'/>";
		var ss = $('#view_num').val();
		/* alert("影像编码原来："+view_nums+"影像编码输入框"+ss); */
		if(view_nums==ss){
				return;
		}
	}
	var a=0;
	$.ajax({
		url:'getviewnum.action',
		type : 'post',
	    async:false,
		data:{view_num:$('#view_num').val()},
		success : function(data) {
			if (data=='ok') {
				a=1;
			} else if (data=='no') {
				$.messager.alert('警告信息','关联影像编码已存在','error');
			}
		}
	});
	return a;
}
/**
 * 添加检查项目
 * 
 */
function addExaminationItem(){
  if($('#item_name').val()==''){
	$('#item_name').focus();
	return;
  }
  if($('#item_pinyin').val()==''){
	  $('#item_pinyin').focus();
		return;
  }
  /* if(!/^[0-9]{1,20}$/.test(document.getElementById('default_value').value)
		  &&document.getElementById('default_value').value!=''){
	  $('#default_value').focus();
	  return;
  } */
  if(!/^[0-9]{1,20}$/.test(document.getElementById('seq_code').value)
		  &&document.getElementById('seq_code').value!=''){
	  $('#seq_code').focus();
	  return;
  }
  if($("input[name='leixing']:checked").val()=='数字型'){
	  if(!isJE((document.getElementById('ref_Mmax').value))
			  &&document.getElementById('ref_Mmax').value!=''){
		  $('#ref_Mmax').focus();
			return;
	  }
	  if(!isJE((document.getElementById('ref_Mmin').value))
			  &&document.getElementById('ref_Mmin').value!=''){
		  $('#ref_Mmin').focus();
		  return;
	  }
	  if(!isJE((document.getElementById('ref_Fmin').value))
			  &&document.getElementById('ref_Fmin').value!=''){
		  $('#ref_Fmin').focus();
		  return;
	  }
	  if(!isJE((document.getElementById('ref_Fmax').value))
			  &&document.getElementById('ref_Fmax').value!=''){
		  $('#ref_Fmax').focus();
		  return;
	  }
	  if(!isJE((document.getElementById('dang_Fmax').value))
			  &&document.getElementById('dang_Fmax').value!=''){
		  $('#dang_Fmax').focus();
		  return;
	  }
	  if(!isJE((document.getElementById('dang_Fmin').value))
			  &&document.getElementById('dang_Fmin').value!=''){
		  $('#dang_Fmin').focus();
		  return;
	  }
	  if(!isJE((document.getElementById('dang_Mmax').value))
			  &&document.getElementById('dang_Mmax').value!=''){
		  $('#dang_Mmax').focus();
		  return;
	  }
	  if(!isJE((document.getElementById('dang_Mmin').value))
			  &&document.getElementById('dang_Mmin').value!=''){
		  $('#dang_Mmin').focus();
		  return;
	  }
  	}
  if(!isJE((document.getElementById('error_max').value))
		  &&document.getElementById('error_max').value!=''){
	  $('#error_max').focus();
	  return;
  }
  if(!isJE((document.getElementById('error_min').value))
		  &&document.getElementById('error_min').value!=''){
	  $('#error_min').focus();
	  return;
  }
  var id=$('#id').val();
  if(examnum()==0||viewnum()==0){
	  return;
  }
  //添加方法
if(id==null||id==''||id=='0'){
  if($("input[name='leixing']:checked").val()=="数字型"){
	  var itemNum='<s:property value="model.item_num"/>';
	$.ajax({
		url:'addExaminationItem.action',
		type:'post',
		data:{
			id:$('#id').val(),
			item_num:itemNum,
			item_name:$('#item_name').val(),
			item_pinyin:$('#item_pinyin').val(),
			item_eng_name:$('#item_eng_name').val(),
			item_unit:$('#item_unit').val(),
			exam_num:$('#exam_num').val(),
			view_num:$('#view_num').val(),
			item_category:$("input[name='leixing']:checked").val(),
			is_print:$("input[name='opt']:checked").val(),
			seq_code:$('#seq_code').val(),
			item_description:$('#item_description').val(),
			remark:$('#remark').val(),
			ref_Mmax:$('#ref_Mmax').val(),
			ref_Mmin:$('#ref_Mmin').val(),
			ref_Fmin:$('#ref_Fmin').val(),
			ref_Fmax:$('#ref_Fmax').val(),
			dang_Fmax:$('#dang_Fmax').val(),
			dang_Fmin:$('#dang_Fmin').val(),
			dang_Mmax:$('#dang_Mmax').val(),
			dang_Mmin:$('#dang_Mmin').val(),
			is_Active:$('#is_Active').val(),
			//default_value:$('#default_value').val(),
			item_type:$('#item_type').combobox('getValue'),
			brief_mark:$('#brief_mark').combobox('getValue'),//小结标示
			brief:$('#brief').combobox('getValue'),//小结记入方法
			error_max:$('#error_max').val(),
			error_min:$('#error_min').val(),
			sex:$('#sex').combobox('getValue')
		},
		success : function(data) {
			$.messager.alert('提示信息',data,'info');
			$('#dlg-custedit').dialog('close')
			$('#groupusershow').datagrid('reload')
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	})
  }else if($("input[name='leixing']:checked").val()=="短文本型"){
	  var itemNum='<s:property value="model.item_num"/>';
		$.ajax({
			url:'addExaminationItem.action',
			type:'post',
			data:{
				id:$('#id').val(),
				item_num:itemNum,
				item_name:$('#item_name').val(),
				item_pinyin:$('#item_pinyin').val(),
				item_eng_name:$('#item_eng_name').val(),
				item_unit:$('#item_unit').val(),
				exam_num:$('#exam_num').val(),
				view_num:$('#view_num').val(),
				item_category:$("input[name='leixing']:checked").val(),
				is_print:$("input[name='opt']:checked").val(),
				seq_code:$('#seq_code').val(),
				item_description:$('#item_description').val(),
				remark:$('#remark').val(),
				referA:$('#referA').val(),
				referB:$('#referB').val(),
				referC:$('#referC').val(),
				referD:$('#referD').val(),
				CrisisA:$('#CrisisA').val(),
				CrisisB:$('#CrisisB').val(),
				CrisisC:$('#CrisisC').val(),
				CrisisD:$('#CrisisD').val(),
				is_Active:$('#is_Active').val(),
				//default_value:$('#default_value').val(),
				item_type:$('#item_type').combobox('getValue'),
				brief_mark:$('#brief_mark').combobox('getValue'),//小结标示
				brief:$('#brief').combobox('getValue'),//小结记入方法
				error_max:$('#error_max').val(),
				error_min:$('#error_min').val(),
				sex:$('#sex').combobox('getValue')
			},
			success : function(data) {
				$.messager.alert('提示信息',data,'info');
				$('#dlg-custedit').dialog('close')
				$('#groupusershow').datagrid('reload')
			},
			error : function() {
				$.messager.alert('提示信息', '操作失败！', 'error');
			}
		}) 
  }
}else if($('#id').val()>0){//修改方法
	if(examnum()==0||viewnum()==0){
		  return;
	  }
  if($("input[name='leixing']:checked").val()=="数字型"){
	var itemNum='<s:property value="model.item_num"/>';
	$.ajax({
		url:'updateExaminationItem.action',
		type:'post',
		data:{
			id:$('#id').val(),
			item_num:itemNum,
			item_name:$('#item_name').val(),
			item_pinyin:$('#item_pinyin').val(),
			item_eng_name:$('#item_eng_name').val(),
			item_unit:$('#item_unit').val(),
			exam_num:$('#exam_num').val(),
			view_num:$('#view_num').val(),
			item_category:$("input[name='leixing']:checked").val(),
			is_print:$("input[name='opt']:checked").val(),
			seq_code:$('#seq_code').val(),
			item_description:$('#item_description').val(),
			remark:$('#remark').val(),
			ref_Mmax:$('#ref_Mmax').val(),
			ref_Mmin:$('#ref_Mmin').val(),
			ref_Fmin:$('#ref_Fmin').val(),
			ref_Fmax:$('#ref_Fmax').val(),
			dang_Fmax:$('#dang_Fmax').val(),
			dang_Fmin:$('#dang_Fmin').val(),
			dang_Mmax:$('#dang_Mmax').val(),
			dang_Mmin:$('#dang_Mmin').val(),
			is_Active:$('#is_Active').val(),
			//default_value:$('#default_value').val(),
			item_type:$('#item_type').combobox('getValue'),
			brief_mark:$('#brief_mark').combobox('getValue'),//小结标示
			brief:$('#brief').combobox('getValue'),//小结记入方法
			error_max:$('#error_max').val(),
			error_min:$('#error_min').val(),
			sex:$('#sex').combobox('getValue')
		},
		success : function(data) {
			$.messager.alert('修改成功',data,'info');
			$('#dlg-custedit').dialog('close')
			$('#groupusershow').datagrid('reload')
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	})
  }else if($("input[name='leixing']:checked").val()=="短文本型"){
	  var itemNum='<s:property value="model.item_num"/>';
	  $.ajax({
			url:'updateExaminationItem.action',
			type:'post',
			data:{
				id:$('#id').val(),
				item_num:itemNum,
				item_name:$('#item_name').val(),
				item_pinyin:$('#item_pinyin').val(),
				item_eng_name:$('#item_eng_name').val(),
				item_unit:$('#item_unit').val(),
				exam_num:$('#exam_num').val(),
				view_num:$('#view_num').val(),
				item_category:$("input[name='leixing']:checked").val(),
				is_print:$("input[name='opt']:checked").val(),
				seq_code:$('#seq_code').val(),
				item_description:$('#item_description').val(),
				remark:$('#remark').val(),
				referA:$('#referA').val(),
				referB:$('#referB').val(),
				referC:$('#referC').val(),
				referD:$('#referD').val(),
				CrisisA:$('#CrisisA').val(),
				CrisisB:$('#CrisisB').val(),
				CrisisC:$('#CrisisC').val(),
				CrisisD:$('#CrisisD').val(),
				is_Active:$('#is_Active').val(),
				//default_value:$('#default_value').val(),
				item_type:$('#item_type').combobox('getValue'),
				brief_mark:$('#brief_mark').combobox('getValue'),//小结标示
				brief:$('#brief').combobox('getValue'),//小结记入方法
				error_max:$('#error_max').val(),
				error_min:$('#error_min').val(),
				sex:$('#sex').combobox('getValue')
			},
			success : function(data) {
				$.messager.alert('修改成功',data,'info');
				$('#dlg-custedit').dialog('close')
					$('#groupusershow').datagrid('reload');
			},
			error : function() {
				$.messager.alert('提示信息', '操作失败！', 'error');
			}
		})  
  }
}
}
function lisitem(){
	var e = $('#exam_num').val();
	e = e.replace(/\+/g, "','");
	$('#lis_open').dialog({    
	    title: 'LIS检验项目明细',    
	    center:'center',
	    href:'getThridLisItemPage.action?exam_num='+e
	});    
	$('#lis_open').dialog('open');  
}	
</script>
 <fieldset id="viewItem" style=" margin: 5px;height:400px; width: 650px; float:left">
	<legend></legend> 
	<div class="formDiv"  style="margin-top:15px; float: left; width: 650px" >
		<dl >
			<dt style="width: 100px">
				项目编码：
			</dt>
			<dd>
				<span class="spanClas"><s:property value="item_num"/> </span>
			</dd>
			<dt  style="width:100px">
				项目名称 ：
			</dt>
			<dd>
				<span class="spanClas"><s:property value="item_name"/></span>
			</dd>
		</dl>
		<dl >
			<dt style="width: 100px">项目英文名称：</dt>
			<dd >
				<span class="spanClas"><s:property value="item_eng_name"/></span>
			</dd>
			<dt  style="width:100px">单位：</dt>
			<dd>
				<span class="spanClas"><s:property value="item_unit"/></span>
			</dd>	
		</dl>
		<dl >		
			<dt style="width: 100px">检查项目拼音：</dt>
			<dd>
				<span class="spanClas"><s:property value="item_pinyin"/></span>
			</dd>
			<dt  style="width:100px">关联检验编码：</dt>
			<dd>
				<span class="spanClas"><s:property value="exam_num"/></span>
			</dd>
		</dl>
		<dl>
			<dt style="width: 100px">关联影像编码：</dt>
			<dd>
				<span class="spanClas"><s:property value="view_num"/></span>
			</dd>
			<dt style="width: 100px">项目解释：</dt>
			<dd>
				<span class="spanClas"><s:property value="item_description"/></span>
			</dd>
		</dl>
		<dl >
			<dt style="width: 100px">项目分类：</dt>
			<dd>
				<input type="text" class="textinput" id="item_type"  readonly="readonly" 
					value="<s:property value="item_type"/>"
				 style="height: 26px;  width:200px; border:0px;"/>
				<%-- <span class="spanClas" id="itemType"></span> --%>
			</dd>
			<dt style="width: 100px">顺序码：</dt>
			<dd>
				<span class="spanClas"><s:property value="seq_code"/></span>
			</dd>
		</dl>
		<dl>
			<dt style="width: 100px">小结标示：</dt>
			<dd >
				<span class="spanClas" id="briefMark"></span>
			</dd>
			<dt style="width:100px">记入小结:</dt>
			<dd>
				<span class="spanClas" id="briefView"></span>
			</dd>
		</dl>
		<dl>
			<dt style="width: 100px">纠错上限：</dt>
			<dd>
				<span class="spanClas"><s:property value="error_max"/></span>
			</dd>
			<dt style="width: 100px">纠错下限：</dt>
			<dd>
				<span class="spanClas"><s:property value="error_min"/></span>
			</dd>
		</dl>
		<dl>
			<%-- <dt style="width: 100px">是否打印：</dt>
			<dd>
				<span class="spanClas"><s:property value="error_min"/></span>
				<input type="radio"  name="opt"  id="ra"  value="Y" checked="checked"/>
				是
				<input type="radio"  name="opt"  id="rb" value="N" />
				否
			</dd> --%>
			<dt style="width:100px;">性别：</dt>
			<dd>
				<span class="spanClas"><s:property value='sex'/></span>
			</dd>
			
		</dl>
		<dl>
			<%-- <dt style="width:100px;">项目类型：</dt>
			<dd>
				<span class="spanClas"><s:property value="error_min"/></span>
				<input type="radio"  name="leixing" id="item_categorya" value="数字型" checked ='checked' />
				数字型&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio"  name="leixing" id="item_categoryb" value="短文本型" />
				短文本型&nbsp;&nbsp;&nbsp;&nbsp;
			</dd> --%>
			<dt  style="width: 100px">备注：</dt>
			<dd>
				<span class="spanClas"><s:property value="remark"/></span>
			</dd>
		</dl>
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-itemView').dialog('close')">关闭</a>
	</div>
</div>
<div id="lis_open" class="easyui-dialog"  data-options="width: 1200,height: 590,closed: true,cache: false,modal: true"></div>