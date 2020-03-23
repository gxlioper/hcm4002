<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyin_dict_firstletter.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyinUtil.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" >
$(document).ready(function () {
	$('#disease_name,#disease_pinyin').validatebox({
		required : true,
		validType:'maxLength[30]'
	})
	
	var nationtype = '<s:property value="model.disease_level"/>';
	$('#disease_level').combobox({
		url :'getDatadis.action?com_Type=JBDJ',
		editable : false, //不可编辑状态
		cache : false,
		height:27,
		width:185,
		panelHeight : 200,//自动高度适合
		valueField : 'id',
		textField : 'name',
		loadFilter:function(data){
			data.unshift({'id':'','name':'不选择'});
			return data;
		},
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == nationtype) {
					$('#disease_level').combobox('setValue', data[i].id);
					break;
				}
			}	
		}
	});
	
	var classification = '<s:property value="model.disease_classification"/>';
	$('#disease_classification').combobox({
		url :'getDatadis.action?com_Type=JBFL',
		editable : false, //不可编辑状态
		cache : false,
		height:27,
		width:185,
		panelHeight : 200,//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == classification) {
					$('#disease_classification').combobox('setValue', data[i].id);
					break;
				} else {
					$('#disease_classification').combobox('setValue', data[0].id);
				}
			}	
		}
	});
	
	$('#disease_name').blur(function(){
		pinyin();
	});
		
	$("#dep_name").combobox({
		url:'huoqudepname.action',
		editable : true, //不可编辑状态
		cache : false,
		height:27,
		width:185,
		panelHeight:200,
		valueField : 'dep_id',
		textField : 'dep_name',
		loadFilter:function(data){
			//data.unshift({'dep_id':'','dep_name':'不选择'});
			return data;
		},
        onLoadSuccess : function(data){
        	 var dep_idedit= '<s:property value="dep_id"/>';
			 if(dep_idedit >0){				
				 $('#dep_name').combobox('setValue', dep_idedit);	
			 }
		}
	});
	
	$("#disease_type").combobox({
		data:[{'id':'Y','text':'阳性发现'},{'id':'D','text':'疾病'}],
		editable : true, //不可编辑状态
		cache : false,
		height:27,
		width:185,
		panelHeight:'auto',
		valueField : 'id',
		textField : 'text',	
        onLoadSuccess : function(data){
        	 var disease_type= '<s:property value="disease_type"/>';
			 if(disease_type != ''){				
				 $('#disease_type').combobox('setValue', disease_type);	
			 }else{
				 $('#disease_type').combobox('setValue', data[0].id);
			 }
		}
	});
	var disease_system = '<s:property value="model.disease_system"/>';
	$('#disease_system').combobox({
		url :'getDatadis.action?com_Type=JBXTFL',
		editable : false, //不可编辑状态
		cache : false,
		height:27,
		width:185,
		panelHeight : 200,//自动高度适合
		valueField : 'id',
		textField : 'name',
		loadFilter:function(data){
			data.unshift({'id':'','name':'不选择'});
			return data;
		},
		onLoadSuccess : function(data) {
			if(disease_system != ''){
				$('#disease_system').combobox('setValue', disease_system);
			}
		}
	});
	var disease_statistics = '<s:property value="model.disease_statistics"/>';
	$('#disease_statistics').combobox({
		url :'getDatadis.action?com_Type=JBTJFL',
		editable : false, //不可编辑状态
		cache : false,
		height:27,
		width:185,
		panelHeight : 200,//自动高度适合
		valueField : 'id',
		textField : 'name',
		loadFilter:function(data){
			data.unshift({'id':'','name':'不选择'});
			return data;
		},
		onLoadSuccess : function(data) {
			if(disease_statistics != ''){
				$('#disease_statistics').combobox('setValue', disease_statistics);
			}
		}
	});
	
	$("#disease_report").combobox({
		data:[{'id':'0','text':'上报'},{'id':'1','text':'不上报'}],
		editable : true, //不可编辑状态
		cache : false,
		height:27,
		width:185,
		panelHeight:'auto',
		valueField : 'id',
		textField : 'text',	
        onLoadSuccess : function(data){
        	 var disease_report= '<s:property value="disease_report"/>';
        	 $('#disease_report').combobox('setValue', disease_report);
		}
	});
	
	$("#disease_team_show").combobox({
		data:[{'id':'0','text':'显示'},{'id':'1','text':'不显示'}],
		editable : true, //不可编辑状态
		cache : false,
		height:27,
		width:185,
		panelHeight:'auto',
		valueField : 'id',
		textField : 'text',	
        onLoadSuccess : function(data){
        	 var disease_team_show= '<s:property value="disease_team_show"/>';
        	 $('#disease_team_show').combobox('setValue', disease_team_show);
		}
	});
});


	/**
	 * 拼音
	 */
	function pinyin(){
		$.ajax({
			url:'pinying.action',
			type:'post',
			data:{pinying:$('#disease_name').val()},
			success:function(data){
				$('#disease_pinyin').val(data);
			}
		})
	}
	
 function f_jbksgxsave(){
		if ($("#disease_name").val() == ''){
			$("#disease_name").focus();
			return;
		}else if($("#disease_pinyin").val() == ''){
			$("#disease_pinyin").focus();
			return;
		}
		$.ajax({
			url:'saveDiseaseKLg.action',  
			data:{
				id:$("#id").val(),
				dep_id:$("#dep_name").combobox('getValue'),
				disease_type:$("#disease_type").combobox('getValue'),
				disease_name:$("#disease_name").val(),
				disease_pinyin:$("#disease_pinyin").val(),
				disease_level:$("#disease_level").combobox('getValue'),
				disease_classification:$("#disease_classification").combobox('getValue'),
				disease_num:$("#disease_num").val(),
				icd_10:$("#icd_10").val(),
				disease_system:$("#disease_system").combobox('getValue'),
				disease_statistics:$("#disease_statistics").combobox('getValue'),
				disease_report:$("#disease_report").combobox('getValue'),
				disease_team_show:$("#disease_team_show").combobox('getValue'),
				disease_description:$("#disease_description").val(),
				disease_evendice:$("#disease_evendice").val(),
				disease_suggestion:$("#disease_suggestion").val()
			  },          
			type: "post",  
			success: function(data){
			  	 $.messager.alert('提示信息', data);
			  	 $("#dlg-gxwh").dialog("close");
			     getGrid();
			     getsuggestionGrid($("#id").val());
			},
			error:function(){
			    $("#dlg-gxwh").dialog("close");
			    $.messager.alert('提示信息', "用户操作失败！",function(){});
			}  
		});
	}
</script>
<form id="add1Form">
<div class="formdiv">
	<div class="formdiv fomr3" style="padding-top:20px;">
		<input type="hidden" id="id" value="<s:property value="id" />"/>
			<dl>
				<dt>疾病编码</dt>
	    	    <dd><input id="disease_num" value="<s:property value="disease_num"/>"  class="textinput" style="width:180px;" disabled="disabled"><strong class="red">*</strong></dd>
				<dt style="width:103px;">所属科室</dt>
	    	    <dd><select id="dep_name"></select></dd>
			</dl>
	    	    <dl>
	    	       <dt>疾病名称</dt>
	    	       <dd><input id="disease_name" type="text" value="<s:property value="disease_name" />" class="textinput" style="width:180px;"><strong class="red">*</strong></dd>
	    	       <dt style="width:103px;">疾病拼音</dt>
	    	       <dd><input id="disease_pinyin" type="text" value="<s:property value="disease_pinyin"/>"  class="textinput" style="width:180px;"></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>疾病类型</dt>
	    	       <dd ><select id="disease_type"></select><strong class="red">*</strong></dd> 
	    	       <dt style="width:103px;">疾病级别</dt>
	    	       <dd ><select id="disease_level"></select></dd >
	    	    </dl>
	    	     <dl>
	    	       <dt>ICD-10编码</dt>
	    	       <dd><input id="icd_10" value="<s:property value="icd_10"/>" class="textinput" style="width:180px;"></dd>
	    	       <dt style="width:108px;">疾病分类</dt>
	    	       <dd ><select id="disease_classification"></select></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>系统分类</dt>
	    	       <dd ><select id="disease_system"></select></dd>
	    	       <dt style="width:108px;">统计分类</dt>
	    	       <dd ><select id="disease_statistics"></select></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>是否上报</dt>
	    	       <dd ><select id="disease_report"></select></dd>
	    	       <dt style="width:108px;">团报显示</dt>
	    	       <dd ><select id="disease_team_show"></select></dd>
	    	    </dl>
	    	    <dl style="height:100px;">
	    	    	<dt>病例解释</dt>
	    	       <dd>
	    	       	<textarea style="width:490px;resize:none;" cols="66" rows="4" id="disease_description" ><s:property value="disease_description"/></textarea>
	    	       </dd>
	    	    </dl>
	    	     <dl style="height:100px;">
	    	    	<dt>诊断依据</dt>
	    	       <dd>
	    	       	<textarea style="width:490px;resize:none;" cols="66" rows="4" id="disease_evendice" ><s:property value="disease_evendice"/></textarea>
	    	       </dd>
	    	    </dl>
	    	    <dl style="height:180px;">
	    	    	<dt>治疗建议</dt>
	    	       <dd>
	    	       	<textarea style="width:490px;resize:none;" cols="66" rows="5" id="disease_suggestion" ><s:property value="disease_suggestion"/></textarea>
	    	       </dd>
	    	    </dl>
	    	</div>
	    	</div>

<div class="dialog-button-box">
	<div class="inner-button-box" >
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="f_jbksgxsave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-gxwh').dialog('close')">关闭</a>
	</div>
</div>
</form>

