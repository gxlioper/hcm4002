<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
    $(document).ready(function(){
	   zywhlb();	
	   zytjlb();
	});	
	
//////////////////////////////////////////////////////////////////

function zytjlb() {
	$('#zytjlb').combobox({
		url : 'getzytjlbshow.action',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'text',
		onLoadSuccess : function(data) {
			$('#zytjlb').combobox('setValue', data[0].id);
		}
	});
}
	//////////////////////////职业危害类别/////////////////////////////////////
	function zywhlb() {
		$('#zywhlb').combobox({
			url : 'getzywhlbshow.action',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'text',
			onLoadSuccess : function(data) {
				$('#zywhlb').combobox('setValue', data[0].id);
				zywhyslb(data[0].id);
			},
			onChange: function (n,o) {
				zywhyslb(n);
			}
		});
	}
	
	var zywhlb
	function zywhyslb(zywhlb) {
		$('#zywhyslb').combobox({
			url : 'getzywhyslbshow.action?zywhlb='+zywhlb,
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'text',
			onLoadSuccess : function(data) {
				$('#zywhyslb').combobox('setValue', data[0].id);				
			}
		});
	}


	function checkinputoccwhys() {
		 if (document.getElementsByName("zywhlb")[0].value=='') {
			alert('职业危害类别不能为空！');
			return false;
		}else if (document.getElementsByName("zywhyslb")[0].value=='') {
			alert('职业危害因素类别不能为空！');
			return false;
		}else if (document.getElementsByName("zytjlb")[0].value=='') {
			alert('职业体检类别不能为空！');
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 保存修改
	 */
	function addocchis() {
		if (checkinputoccwhys()) {
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
			$.ajax({
				url : 'zyboccwhyslbadddo.action',
				data : {
					id_num : document.getElementById("addid_num").value,
					exam_num : document.getElementById("addexam_num").value,
					arch_num : document.getElementById("addarch_num").value,						
					zywhlb : document.getElementsByName("zywhlb")[0].value,
					zywhyslb : document.getElementsByName("zywhyslb")[0].value,
					zytjlb:document.getElementsByName("zytjlb")[0].value
					},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								alert_autoClose("操作提示", "操作成功！","");
								getoccuhazardfactorsGrid();
								zybsetselectListGrid();
								zybcustChangItemListGrid();
								$('#zybGselectsetlist').datagrid('reload');
								$('#zybGselectItemlist').datagrid('reload');
								$('#djtGselectsetlist').datagrid('reload');
								$('#djtGselectItemlist').datagrid('reload');
								gettotalinfo();
								$('#dlg-zybocchisedit').dialog('close');
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
		}
	}

	$(function(){
		$('input').attr("maxlength","20");
	})

</script>
<input type="hidden" id="addexam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="addarch_num" value="<s:property value="model.arch_num"/>">
<input type="hidden" id="addid_num" value="<s:property value="model.id_num"/>">
<input type="hidden" id="zywhlxsex" value="<s:property value="model.sex"/>">
<input type="hidden" id="zywhlb_id" >
<input type="hidden" id="examset_id" >
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>职业危害因素与套餐维护</strong>
	</legend>
	<div class="user-query">
		<div class="user-query">
				<dl>
					<dt>职业危害类别<strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="zywhlb" name="zywhlb" panelMaxHeight="200px" data-options="height:26,width:280,panelHeight:'auto'"></select>
					</dd>
				</dl>
				<dl>
					<dt>职业危害因素<strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="zywhyslb" name="zywhyslb" panelMaxHeight="200px"  data-options="height:26,width:280,panelHeight:'auto'"></select>
					</dd>					
				</dl>
				<dl>
					<dt>职业体检类别<strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="zytjlb" name="zytjlb" panelMaxHeight="200px"  data-options="height:26,width:280,panelHeight:'auto'"></select>
					</dd>					
				</dl>
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addocchis();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-zybocchisedit').dialog('close')">关闭</a>
	</div>
</div>