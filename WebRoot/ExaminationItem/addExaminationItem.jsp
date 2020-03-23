<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyin_dict_firstletter.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyinUtil.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	if($('#id').val()>0){
		var brief_mark = "<s:property value='brief_mark'/>";
		
		if(brief_mark=='1'){
			$('#shi').attr('selected',true);
			$('#fou').attr('selected',false);
		}else{
			$('#fou').attr('selected',true);
			$('#shi').attr('selected',false);
		}
		
		var brief = "<s:property value='brief'/>";
		if(brief=='1'){
			$('#jblj').attr('selected',true);
			$('#ckz').attr('selected',false);
			$('#jlmslx').attr('selected',false);
		}else if(brief=='2'){
			$('#jblj').attr('selected',false);
			$('#ckz').attr('selected',false);
			$('#jlmslx').attr('selected',true);
		}else{
			$('#jblj').attr('selected',false);
			$('#ckz').attr('selected',true);
			$('#jlmslx').attr('selected',false);
		}
		
		var sex = "<s:property value='sex'/>";
		if(sex == '男'){
			$('#nanb').attr('selected',true);
		}else if(sex == '女'){
			$('#nvnb').attr('selected',true);
		}else{
			$('#quanb').attr('selected',true);
		}
		
	}
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
		
		//科室
		var dept_id='<s:property value="model.dept_id"/>';
		//alert('<s:property value="model.dept_id"/>');
		$('#dept_item_select').combobox({
			url:"getDepartmentDepList.action?web_Resource=1",
		    valueField:'id',    
		    textField:'dep_name',
		    onLoadSuccess : function(data){//下拉框默认选择
		    	if(dept_id!=0){
		    	   var val = $(this).combobox('getData');
			       for(var i=0;i<val.length;i++){
			    		if(dept_id==val[i].id){
			    			$('#dept_item_select').combobox('setValue',val[i].dep_name);
			    			break;
			    		}
			    	} 
		    	}else{
		    		$('#dept_item_select').combobox('setValue','无科室');
		    	}
		       
		    },
		    filter: function(q, row){
				var opts = $(this).combobox('options');
				var text = row[opts.textField];//下拉的对应选项的汉字
				var pyjp = pinyinUtil.getFirstLetter(text).toLowerCase();
		 		if(row[opts.textField].indexOf(q) > -1 || pyjp.indexOf(q.toLowerCase()) > -1){
		 			return true;
		 		}	
			}
		});
	
	$("#item_result_type").combobox({
		width:80,
		height:26,
		panelHeight:'auto',
	    valueField:'value',    
	    textField:'label',
	    data:[{
				label: '结果型',
				value: '0'
			},{
				label: '结论描述型',
				value: '1'
			}],
		onLoadSuccess:function(data){
			var item_result_type = "<s:property value='item_result_type'/>";
			$("#item_result_type").combobox('setValue',item_result_type);
		}
	});
		 
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
	    var deptId = 0;
		if(isNaN($('#dept_item_select').combobox('getValue'))){
			deptId = $("#dept_item_hidden").val();
		}else{
			deptId = $('#dept_item_select').combobox('getValue');
		}
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
			sex:$('#sex').combobox('getValue'),
			dept_id:deptId,
			item_result_type:$('#item_result_type').combobox('getValue')
		},
		success : function(data) {
			$.messager.alert('提示信息',data,'info');
			$('#dlg-custedit').dialog('close')
			$('#groupusershow').datagrid('reload');
			//getExaminationItem();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	})
  }else if($("input[name='leixing']:checked").val()=="短文本型"){
	  var itemNum='<s:property value="model.item_num"/>';
	    var deptId = 0;
		if(isNaN($('#dept_item_select').combobox('getValue'))){
			deptId = $("#dept_item_hidden").val();
		}else{
			deptId = $('#dept_item_select').combobox('getValue');
		}
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
				sex:$('#sex').combobox('getValue'),
				dept_id:deptId,
				item_result_type:$('#item_result_type').combobox('getValue')
			},
			success : function(data) {
				$.messager.alert('提示信息',data,'info');
				$('#dlg-custedit').dialog('close');
				$('#groupusershow').datagrid('reload');
				//getExaminationItem();
			},
			error : function() {
				$.messager.alert('提示信息', '操作失败！', 'error');
			}
		}) 
  }
}else if($('#id').val()>0){//修改方法
	//alert('<s:property value="model.dept_id"/>');
	var deptId = 0;
	if(isNaN($('#dept_item_select').combobox('getValue'))){
		deptId = $("#dept_item_hidden").val();
	}else{
		deptId = $('#dept_item_select').combobox('getValue');
	}
	
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
			sex:$('#sex').combobox('getValue') ,
			dept_id:deptId,
			item_result_type:$('#item_result_type').combobox('getValue')
		},
		success : function(data) {
			//$.messager.alert('修改成功',data,'info');
			$('#dlg-custedit').dialog('close')
			$('#groupusershow').datagrid('reload');
			//getExaminationItem();
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
				sex:$('#sex').combobox('getValue') ,
				dept_id:deptId,
				item_result_type:$('#item_result_type').combobox('getValue')
			},
			success : function(data) {
				//$.messager.alert('修改成功',data,'info');
				$('#dlg-custedit').dialog('close')
				$('#groupusershow').datagrid('reload');
				//getExaminationItem();
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
 <fieldset style=" margin: 5px;height:400px; width: 650px; float:left">
	<legend><strong>检查项目编辑</strong></legend> 
	<div class="formDiv"  style="margin-top:15px; float: left; width: 650px" >
		<dl >
			<dt style="width: 80px">
				项目编码
			</dt>
			<dd>
				<input type="hidden" value='<s:property value="id"/>' id="id"/>
				<input type="text" class="textinput" id="item_num"
				disabled="true"	value="<s:property value="item_num"/> "	maxlength="45"  style="height: 26px; width:200px;"  class="textinput"/> 
				<font color="ff0000">
					<span>*</span>
				</font>
			</dd>
			<dt  style="width:80px">
				项目名称 
			</dt>
			<dd>
				<input   type="text" id="item_name"   maxlength="45" 
				value="<s:property value="item_name"/>"
				maxlength="45"   style="height: 26px; width:200px;"  class="textinput"/>
				<font color="ff0000"><span>*</span></font>
			</dd>
		</dl>
		<dl >
			<dt style="width:80px;">所属科室</dt>
			<dd>
				<input type="hidden" id="dept_item_hidden" value="<s:property value="model.dept_id"/>"/>
				<input type="text" id="dept_item_select" style="height: 26px;  width:200px;"/>
			</dd>
			<dt  style="width:80px">单位:</dt>
			<dd>
				<input maxlength="45"   type="text" maxlength="45" id="item_unit"
					value="<s:property value="item_unit"/>"
				 class="textinput"	style="height: 26px; width:200px;"/>
			</dd>	
		</dl>
		<dl >		
			<dt style="width: 80px">检查项目拼音</dt>
			<dd>
				<input type="text" maxlength="200" class="textinput" id="item_pinyin" 
				value="<s:property value="item_pinyin"/>"
				maxlength="45" 	style="height: 26px; width:200px;"/>
				<font color="ff0000"><span>*</span></font>
			</dd>
			<dt  style="width:80px">关联检验编码</dt>
			<dd>
				<input type="text" maxlength="45" class="textinput" id="exam_num"    ondblclick="lisitem()"
				value="<s:property value="exam_num"/>"
				 maxlength="4"  style="height: 26px; width: 200px;"/>
			</dd>
		</dl>
		<dl>
			<dt style="width: 80px">关联影像编码</dt>
			<dd>
				<input type="text" maxlength="45" class="textinput" id="view_num" 
				value="<s:property value="view_num"/>"
			    maxlength="4" style="height: 26px;  width:200px;"/>
			    &nbsp;&nbsp;
			</dd>
			<dt style="width: 80px">项目解释</dt>
			<dd>
				<input type="text" class="textinput" id="item_description" 
				value="<s:property  value='item_description'/>"
				maxlength="500"   style="height: 26px;  width:200px;"/>
				&nbsp;&nbsp;
			</dd>
		</dl>
		<dl >
			<dt style="width: 80px">项目分类</dt>
			<dd>
				<input type="text" class="textinput" id="item_type" 
					value="<s:property value="item_type"/>"
				 style="height: 26px;  width:200px;"/>
				<font color="ff0000"><span>*</span></font>
			</dd>
			<dt style="width: 80px">顺序码</dt>
			<dd>
				<input type="text" maxlength="4" class="textinput" id="seq_code" 
				value="<s:property  value='seq_code'/>"
				 style="height: 26px;  width:40px;"/>
			</dd>
			<dt style="width: 60px">结果类型</dt>
			<dd>
				<select id="item_result_type"></select>
			</dd>
		</dl>
		<dl>
			<dt style="width: 80px">小结标示</dt>
			<dd >
				<select   class="easyui-combobox"  id="brief_mark"  data-options="panelHeight:50" style="height: 26px;  width:200px"> 
					<option  value='1'  id='shi'>是</option>   
				    <option  value='0'  id='fou'>否</option>   
				</select>
			</dd>
			<dt style="width:88px"   >记入小结</dt>
			<dd>
				<select id="brief"  class="easyui-combobox"   data-options="panelHeight:75"  style="height: 26px;  width:200px">   
				    <option  value='1' id='jblj'>疾病逻辑</option>   
				    <option  value='0' id='ckz'>参考值</option>   
				    <option  value='2' id='jlmslx'>结论描述类型</option>
				</select>  
			</dd>
		</dl>
		<dl>
			<dt style="width: 80px">纠错上限</dt>
			<dd>
				<input type="text" maxlength="45" class="textinput" id="error_max" 
				value="<s:property value="error_max"/>"
			    maxlength="4" style="height: 26px;  width:200px;"/>
			    &nbsp;&nbsp;
			</dd>
			<dt style="width: 80px">纠错下限</dt>
			<dd>
				<input type="text" class="textinput" id="error_min" 
				value="<s:property  value="error_min"/>"
				maxlength="500"   style="height: 26px;  width:200px;"/>
				&nbsp;&nbsp;
			</dd>
		</dl>
		<dl>
			<dt style="width: 80px">是否打印</dt>
			<dd>
				<input type="radio"  name="opt"  id="ra"  value="Y" checked="checked"/>
				是
				<input type="radio"  name="opt"  id="rb" value="N" />
				否
			</dd>
			<dt style="width:40px;">性别</dt>
			<dd><select id="sex" class="easyui-combobox" data-options="panelHeight:'auto'"  style="height:26px;width:75px">
				<option value="全部" id="quab">全部</option>
				<option value="男" id="nanb">男</option>
				<option value="女" id="nvnb">女</option>
			</select></dd>
			<dt style="width:100px;">项目类型</dt>
			<dd>
				<input type="radio"  name="leixing" id="item_categorya" value="数字型" checked ='checked' />
				数字型&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio"  name="leixing" id="item_categoryb" value="短文本型" />
				短文本型&nbsp;&nbsp;&nbsp;&nbsp;
			</dd>
		</dl>
		<dl>
			<dt style="width: 80px">英文名称</dt>
			<dd >
				<input maxlength="45"   id="item_eng_name"
					value="<s:property value="item_eng_name"/>"
				 class="textinput" style="height: 26px; width:200px;" />
			</dd>
			<dt  style="width: 70px">备注</dt>
			<dd>
				<input type="text" class="textinput" id="remark" 
				value="<s:property  value='remark'/>"
				maxlength="500"	 style="height: 26px;  width:230px;"/>
			</dd>
			
		</dl>
	</div>
</fieldset>
 <fieldset style=" margin: 15px; width: 285px">
 	<!-- 数字型 -->
	<legend><strong>参考值范围</strong></legend> 
	<div id="numType">
		<dl  style="height:40px">
			<dt>男性:最大值</dt>
			<dd>
				<input type="text" class="textinput" id="ref_Mmax" 
					value="<s:property  value='ref_Mmax'/>"
				maxlength="8"	 style="height: 26px;  width: 210px;"/>
			</dd>
		</dl>
		<dl style="height:40px">
			<dt>男性:最小值</dt>
			<dd>
				<input type="text" class="textinput" 
					value="<s:property  value='ref_Mmin'/>"
				maxlength="8"		id="ref_Mmin" style="height: 26px;  width: 210px;"/>
			</dd>
		</dl>
		<dl style="height:40px">
			<dt>女性:最大值</dt>
			<dd>
				<input type="text" class="textinput" id="ref_Fmax" 
					value="<s:property  value='ref_Fmax'/>"
				maxlength="8"		style="height: 26px;  width: 210px; "/>
			</dd>
		</dl>
		<dl style="height:40px">
			<dt>女性:最小值</dt>
			<dd>
				<input type="text" class="textinput" id="ref_Fmin" 
				value="<s:property  value='ref_Fmin'/>"
				maxlength="8"		style="height: 26px;  width: 210px;"/>
			</dd>
		</dl>
	</div>
	<!--短文本型  -->
	<div id="textType"  style="display:none">
		<dl style="height:40px">
			<dt>参考值1</dt>
			<dd>
				<input maxlength="45"	 type="text" class="textinput" id="referA" 
				value="<s:property  value='referA'/>"
				style="height: 26px;  width: 210px; "/>
			</dd>
		</dl>
		<dl style="height:40px">
			<dt>参考值2</dt>
			<dd>
				<input maxlength="45" type="text" class="textinput" id="referB" 
				value="<s:property  value='referB'/>"
				 style="height: 26px;  width: 210px; "/>
			</dd>
		</dl>
		<dl style="height:40px">
			<dt>参考值3</dt>
			<dd>
				<input maxlength="45" type="text" class="textinput" id="referC" 
				value="<s:property  value='referC'/>"
				 style="height: 26px;  width: 210px; "/>
			</dd>
		</dl>
		<dl style="height:40px">
			<dt>参考值4</dt>
			<dd>
				<input maxlength="45" type="text" class="textinput" id="referD" 
				value="<s:property  value='referD'/>"
				 style="height: 26px;  width: 210px; "/>
			</dd>
		</dl>
	</div>
</fieldset>

<fieldset style=" margin: 15px; width: 285px">
	<!--数字型-->
 	<legend><strong>危机值范围</strong></legend> 
 		<div id="crNum">
 			<dl style="height:28px">
 				<dt>男性:危险值大于值</dt>
 				<dd><input maxlength="8" type="text" id="dang_Mmax" class="textinput"
 				value="<s:property  value='dang_Mmax'/>"
 				 style=" width:170px; height: 26px"/></dd>
 			</dl>
 			<br/>
 			<dl style="height:28px">
 				<dt>男性:危险值小于值</dt>
 				<dd><input maxlength="8"	 type="text" id="dang_Mmin"
 				value="<s:property  value='dang_Mmin'/>"
 				 class="textinput" style=" width: 170px; height: 26px"/></dd>
 			</dl>
 			<br/>
 			<dl style="height:28px">
 				<dt>女性:危险值大于值</dt>
 				<dd><input maxlength="8"	 type="text" id="dang_Fmax"
 				value="<s:property  value='dang_Fmax'/>"
 				 class="textinput" style=" width: 170px; height: 26px"/></dd>
 			</dl>
 			<br/>
 			<dl style="height:28px">
 				<dt>女性:危险值小于值</dt>
 				<dd><input maxlength="8"	 type="text" id="dang_Fmin"
 				value="<s:property  value='dang_Fmin'/>"
 				 class="textinput" style=" width: 170px; height: 26px"/></dd>
 			</dl>
 		</div>
 		<!--短文本型 -->
 		<div id="crText"  style="display:none">
 			<dl style="height:28px">
 				<dt>危机值1</dt>
 				<dd><input type="text" maxlength="45"	 class="textinput" id="CrisisA"
 				value="<s:property  value='CrisisA'/>"
 				 style=" width: 210px; height: 26px"/></dd>
 			</dl>
 			<br/>
 			<dl style="height:28px">
 				<dt>危机值2</dt>
 				<dd><input type="text"  maxlength="45"	 class="textinput" id="CrisisB" 
 				value="<s:property  value='CrisisB'/>"
 				style=" width: 210px; height: 26px"/></dd>
 			</dl>
 			<br/>
 			<dl style="height:28px">
 				<dt>危机值3</dt>
 				<dd><input type="text"  maxlength="45"	 class="textinput" id="CrisisC"  
 				value="<s:property  value='CrisisC'/>" style=" width: 210px; height: 26px"/></dd>
 			</dl>
 			<br/>
 			<dl style="height:28px">
 				<dt>危机值4</dt>
 				<dd><input type="text"  maxlength="45"	  class="textinput" id="CrisisD" 
 					value="<s:property  value='CrisisD'/>"
 				style=" width: 210px; height: 26px"/></dd>
 			</dl>
 		</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addExaminationItem()" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>
<div id="lis_open" class="easyui-dialog"  data-options="width: 1200,height: 590,closed: true,cache: false,modal: true"></div>