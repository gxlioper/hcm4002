<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script >
$(document).ready(function () {
	f_getDatadic();
	f_getDatadicparent();
})

function f_getDatadic() {
	var reporttype = '<s:property value="model.report_type"/>';
	$('#report_type').combobox({
		url : 'getDatadis.action?com_Type=REPORTTYPE',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == reporttype) {
					$('#report_type').combobox('setValue', data[i].id);
					break;
				} else {
					$('#report_type').combobox('setValue', data[0].id);
				}
			}
		}
	});
}

function f_getDatadicparent() {
	var parent_idtype = '<s:property value="model.parent_id"/>';
	$('#parent_id').combobox({
		url : 'getDatadis.action?com_Type=REPORTPARENT',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == parent_idtype) {
					$('#parent_id').combobox('setValue', data[i].id);
					break;
				} else {
					$('#parent_id').combobox('setValue', data[0].id);
				}
			}
		}
	});
}

function checkinput() {
	var parent_id = document.getElementsByName("parent_id")[0].value;
	var types = document.getElementsByName("report_type")[0].value;
	if($("#report_name").val()==''){
		$.messager.alert("操作提示", "报表模板名称无效！", "error");
		return false;
	}else if((Number(parent_id)>0)&&($("#report_address").val()=='')){
		$.messager.alert("操作提示", "报表模板地址无效！", "error");
		return false;
	}else if(($("#report_address").val()!='')&&(types=='')){
		$.messager.alert("操作提示", "报表模板类型无效！", "error");
		return false;
	}else if (($("#seq_code").val().length!= '')&&(!isSZ($("#seq_code").val()))) {
		$.messager.alert("操作提示", "索引格式错误！", "error");
		return false;
	} 
	return true;
}

/**
 * 保存修改
 */
function addreportdo() {
	if (checkinput()) {
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		$.ajax({
			url : 'reportmanageradddo.action',
			data : {
				report_name:$("#report_name").val(),							
				parent_id : document.getElementsByName("parent_id")[0].value,
				report_type : document.getElementsByName("report_type")[0].value,
				report_address : $("#report_address").val(),
				seq_code:$("#seq_code").val()
					},
					type : "post",//数据发送方式   
					success : function(text) {
			    		$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							$.messager.alert("操作提示", text);
							getgroupuserGrid();
							$('#dlg-reportedit').dialog('close');
						} else {
							$.messager.alert("操作提示", text, "error");
						}
					},
					error:function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");
					}
				});
	}
}


//文件上传按钮
function reprotimport() {
	if ($("#reportImport").val() == '') {
		$.messager.alert("操作提示", "请选择上传的报表模板", "error");	
		return;
	}
	var srca = $("#reportImport").val();
	var ext = [ '.CPT', '.FRM', '.CPT&VIEW', '.cpt', '.frm', '.cpt&view' ];
	var s = srca.toLowerCase();
	var r = false;
	for (i = 0; i < ext.length; i++) {
		if (s.indexOf(ext[i]) > 0) {
			r = true;
			break;
		}
	}
	// return r;
	if (r) {
	   ajaxCPTFileUpload();
	} else {
		$.messager.alert("操作提示", "请上传正确的报表模板文件，CPT、FRM、CPT&VIEW、cpt、frm、cpt&view。", "error");	
	}
}

//文件上传
function ajaxCPTFileUpload(a, b) {
	$.ajaxFileUpload({
		url : 'reportimportdo.action',
		fileElementId : 'reportImport',
		dataType : 'json',
		success : function(data) {
			if (data.state == 'Y') {
				$('#report_address').textbox('setValue', data.msg);
			}else{
				$.messager.alert("操作提示", data.msg, "error");	
				$('#report_address').textbox('setValue', '');
			}
			// select_person_list();
		}
	})
}
</script>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>新增报表模板</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>
				模板名称
			</dt>
			<dd><input class="easyui-textbox" type=text id="report_name" style="width:100px;height:26px;"></input></dd>		
		</dl>
		<dl>
			<dt>
				所属父级
			</dt>
			<dd><select class="easyui-combobox" id="parent_id" name="parent_id"					
					data-options="height:26,width:120,panelHeight:'auto'"></select></dd>	
		</dl>
		<dl>
			<dt>
				模板地址
			</dt>
			<dd><input class="easyui-textbox" id="report_address" name="report_address"					
					data-options="height:26,width:120,panelHeight:'auto'"></dd>	
		</dl>
		<dl>
			<dt>
				上传模板
			</dt>
			<dd><input type="file" id="reportImport" name="reportImport" />
                <input type="submit" onclick="reprotimport()" value="上传"></dd>
		</dl>
		<dl>
			<dt>
				报表类型
			</dt>
			<dd><select class="easyui-combobox" id="report_type" name="report_type"					
					data-options="height:26,width:120,panelHeight:'auto'"></select></dd>	
		</dl>
		<dl>
			<dt>
				索引
			</dt>
			<dd><input  class="easyui-textbox" type="text" id="seq_code" style="width:60px;height:26px;"></input></dd>				
		</dl>
		
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addreportdo();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-reportedit').dialog('close')">关闭</a>
	</div>
</div>