<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
	#lookmsg input{
		border: 0px;
	}
	#lookmsg .textinput{
		border: 0px;
	}
</style>
<script type="text/javascript">
	$(document).ready(function (){
		var nationtype = '<s:property value="model.disease_level"/>';
		$('#disease_level_l').combobox({
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
						$('#disease_level_l').combobox('setValue', data[i].id);
						break;
					}
				}	
			}
		});
		
		var classification = '<s:property value="model.disease_classification"/>';
		$('#disease_classification_l').combobox({
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
						$('#disease_classification_l').combobox('setValue', data[i].id);
						break;
					} else {
						$('#disease_classification_l').combobox('setValue', data[0].id);
					}
				}	
			}
		});
		
		$("#dep_name_l").combobox({
			url:'huoqudepname.action',
			editable : true, //不可编辑状态
			cache : false,
			height:27,
			width:185,
			panelHeight:200,
			valueField : 'dep_id',
			textField : 'dep_name',
			loadFilter:function(data){
				data.unshift({'dep_id':'','dep_name':'不选择'});
				return data;
			},
	        onLoadSuccess : function(data){
	        	 var dep_idedit= '<s:property value="dep_id"/>';
				 if(dep_idedit >0){				
					 $('#dep_name_l').combobox('setValue', dep_idedit);	
				 }
			}
		});
		
		$("#disease_type_l").combobox({
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
					 $('#disease_type_l').combobox('setValue', disease_type);	
				 }else{
					 $('#disease_type_l').combobox('setValue', data[0].id);
				 }
			}
		});
		var disease_system = '<s:property value="model.disease_system"/>';
		$('#disease_system_l').combobox({
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
					$('#disease_system_l').combobox('setValue', disease_system);
				}
			}
		});
		var disease_statistics = '<s:property value="model.disease_statistics"/>';
		$('#disease_statistics_l').combobox({
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
					$('#disease_statistics_l').combobox('setValue', disease_statistics);
				}
			}
		});
		
		$("#disease_report_l").combobox({
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
	        	 $('#disease_report_l').combobox('setValue', disease_report);
			}
		});
		
		$("#disease_team_show_l").combobox({
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
	        	 $('#disease_team_show_l').combobox('setValue', disease_team_show);
			}
		});
		
		
		$.ajax({
			url : 'lookDKLdgelib.action?id='+'<s:property value="id" />',
			type:'post',
			success:function(data){
				var obj = eval('('+data+')');
				var str = '';
				for(i=0;i<obj.length;i++){
					str += '<dl>'
	    	   		+'<dt>适用性别：</dt><dd><input style="widht:40px;" readonly="readonly" value="'+obj[i].sex+'" class="textinput"/></dd>'
	    	   		+'<dd>年龄：<input style="width: 25px;" readonly="readonly" value="'+obj[i].minAge+'" class="textinput"/>'
	    	   		+'至：<input style="width: 25px;" readonly="readonly" value="'+obj[i].maxAge+'" class="textinput"/></dd>'
	    	   		+'<dt>默认值：</dt><dd><input style="width: 25px; readonly="readonly" value="'+obj[i].default_value+'" class="textinput"/></dd> </dl>'
	    	    	+'<dl style="height: 150px;"> <dt>健康建议：</dt>'
	    	       +'<dd>'
	    	       	+'<textarea readonly="readonly" style="width:490px;resize:none;" cols="66" rows="4">'+obj[i].health_suggestion+'</textarea>'
	    	       +'</dd>'
	    	    +'</dl>';
				}
				$(".fomr3").append(str);
			}
		});
	});
</script>
<form id="add1Form">
<div class="formdiv">
	<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
				<dt>疾病编码</dt>
	    	    <dd><input readonly="readonly" id="disease_num_l" value="<s:property value="disease_num"/>"  class="textinput" style="width:180px;" disabled="disabled"><strong class="red">*</strong></dd>
				<dt style="width:103px;">所属科室</dt>
	    	    <dd><select readonly="readonly" id="dep_name_l"></select></dd>
			</dl>
	    	    <dl>
	    	       <dt>疾病名称</dt>
	    	       <dd><input readonly="readonly" id="disease_name_l" type="text" value="<s:property value="disease_name" />" class="textinput" style="width:180px;"><strong class="red">*</strong></dd>
	    	       <dt style="width:103px;">疾病拼音</dt>
	    	       <dd><input readonly="readonly" id="disease_pinyin_l" type="text" value="<s:property value="disease_pinyin"/>"  class="textinput" style="width:180px;"></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>疾病类型</dt>
	    	       <dd ><select readonly="readonly" id="disease_type_l"></select><strong class="red">*</strong></dd> 
	    	       <dt style="width:103px;">疾病级别</dt>
	    	       <dd ><select readonly="readonly" id="disease_level_l"></select></dd >
	    	    </dl>
	    	     <dl>
	    	       <dt>ICD-10编码</dt>
	    	       <dd><input readonly="readonly" id="icd_10_l" value="<s:property value="icd_10"/>" class="textinput" style="width:180px;"></dd>
	    	       <dt style="width:108px;">疾病分类</dt>
	    	       <dd ><select readonly="readonly" id="disease_classification_l"></select></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>系统分类</dt>
	    	       <dd ><select readonly="readonly" id="disease_system_l"></select></dd>
	    	       <dt style="width:108px;">统计分类</dt>
	    	       <dd ><select readonly="readonly" id="disease_statistics_l"></select></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>是否上报</dt>
	    	       <dd ><select readonly="readonly" id="disease_report_l"></select></dd>
	    	       <dt style="width:108px;">团报显示</dt>
	    	       <dd ><select readonly="readonly" id="disease_team_show_l"></select></dd>
	    	    </dl>
	    	    <dl style="height:100px;">
	    	    	<dt>病例解释</dt>
	    	       <dd>
	    	       	<textarea readonly="readonly" style="width:490px;resize:none;" cols="66" rows="4" id="disease_description_l" ><s:property value="disease_description"/></textarea>
	    	       </dd>
	    	    </dl>
	    	     <dl style="height:100px;">
	    	    	<dt>诊断依据</dt>
	    	       <dd>
	    	       	<textarea readonly="readonly" style="width:490px;resize:none;" cols="66" rows="4" id="disease_evendice_l" ><s:property value="disease_evendice"/></textarea>
	    	       </dd>
	    	    </dl>
	    	    <dl style="height:130px;">
	    	    	<dt>治疗建议</dt>
	    	       <dd>
	    	       	<textarea readonly="readonly" style="width:490px;resize:none;" cols="66" rows="5" id="disease_suggestion_l" ><s:property value="disease_suggestion"/></textarea>
	    	       </dd>
	    	    </dl>
	    </div>
</div>
</form>

