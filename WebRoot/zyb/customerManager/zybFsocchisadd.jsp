<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script>
$(document).ready(function() { 
		
	});

	function checkinput() {
		if((document.getElementById("addid_num").value=='')&&(document.getElementById("addexam_num").value=='')&&(document.getElementById("addarch_num").value==''))
		{
			alert('无效体检信息！');
			return false;
		}else if (document.getElementById("Fs_addcompany").value== '') {
			alert('单位名称无效！');
			return false;
		}else if (document.getElementById("Fs_addcompany").value.length>=100) {
			alert('单位名称超过最大100的长度！');
			return false;
		}else if (document.getElementById("Fs_addworkshop").value == '') {
			alert('车门部门不能为空！');
			return false;
		}else if (document.getElementById("Fs_addworkshop").value.length>= 100) {
			alert('车门部门超过最大100的长度！');
			return false;
		}else if (document.getElementById("Fs_addworktype").value == '') {
			alert('工种不能为空！');
			return false;
		}else if (document.getElementById("Fs_addworktype").value.length>= 100) {
			alert('工种超过最大100的长度！');
			return false;
		}else if ($("#Fs_addstartdate").datebox('getValue')=='') {
			alert('进厂日期不能为空！');
			return false;
		}else if ($("#Fs_addenddate").datebox('getValue')=='') {
			alert('离厂日期不能为空！');
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 保存修改
	 */
	function addocchis() {
		if (checkinput()) {
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
			$.ajax({
				url : 'zybocchisadddo.action',
				data : {
					zyb_id:$('#zyb_id').val(),
					batch_id : $("#addbatch_id").val(),
					id_num : document.getElementById("addid_num").value,
					exam_num : document.getElementById("addexam_num").value,
					arch_num : document.getElementById("addarch_num").value,							
					comname : $("#Fs_addcompany").val(),
					_level : $("#Fs_addworkshop").val(),							
					occutypeofwork : $("#Fs_addworktype").val(),							
					isradiation :'1',
					time1:$("#Fs_addstartdate").datebox('getValue'),
					time2:$("#Fs_addenddate").datebox('getValue'),
					radiation:$('#Fs_radiation').val(),
					man_haur:$('#Fs_man_haur').val(),
					cumulative_exposure:$('#Fs_cumulative_exposure').val(),
					history_excessive:$('#Fs_history_excessive').val(),
					remark:$('#Fs_Remark').val(),
					history_excessive:$('#Fs_History_excessive').val()
					},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								alert_autoClose("操作提示", "操作成功！","");
								getFscusthisGrid();
								$('#dlg-fangsheshi').dialog('close');
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
<input type="hidden" id="addbatch_id" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="zyb_id" value="<s:property value="zyb_id"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>放射史维护</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>
				单位名称<strong class="quest-color">*</strong>
			</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="Fs_addcompany"    value="<s:property value="zybOccuHisDTO.company"/>"
					style="height: 26px; width: 280px;" />
			</dd>
		</dl>
		<dl>
			<dt>
				 车间部门<strong class="quest-color">*</strong>
			</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="Fs_addworkshop"   value="<s:property value="zybOccuHisDTO.workshop"/>"
					style="height: 26px; width: 280px;""/>
			</dd>
		</dl>
		<dl>
			<dt>工种<strong class="quest-color">*</strong></dt>
			<dd>
				<input class="easyui-textbox" type="text" id="Fs_addworktype"  value="<s:property value="zybOccuHisDTO.worktype"/>"
					style="height: 26px; width: 280px;" />
			</dd>
		</dl>
		<dl>
			<dt>进厂日期<strong class="quest-color">*</strong></dt>
			<dd><input class="easyui-datebox" data-options="validType:'Length[10]'" type=text id="Fs_addstartdate"  value="<s:property value="zybOccuHisDTO.startdate"/>"
			 style="width:100px;height:26px;"></input>
			<dt>放射线种类</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="Fs_radiation"    value="<s:property value="zybOccuHisDTO.radiation"/>"
					style="height: 26px; width: 240px;" />
			</dd>
			
		</dl>
		<dl>
		<dt>离厂日期<strong class="quest-color">*</strong></dt>
			<dd><input class="easyui-datebox"  data-options="validType:'Length[10]'" type=text id="Fs_addenddate"  value="<s:property value="zybOccuHisDTO.enddate"/>"
			 style="width:100px;height:26px;"></input>
			<dt>每日工作量</dt>
			<dd><input class="easyui-textbox" type="text" id="Fs_man_haur"   value="<s:property value="zybOccuHisDTO.man_haur"/>"
					style="height: 26px; width: 240px;" />
			</dd>
		</dl>
		<dl>
		    <dt>累积受照射剂量</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="Fs_cumulative_exposure"   value="<s:property value="zybOccuHisDTO.cumulative_exposure"/>"
					style="height: 26px; width: 450px;" />
			</dd>
		</dl>
		<dl>
		    <dt>过量照射史</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="Fs_History_excessive"    value="<s:property value="zybOccuHisDTO.history_excessive"/>"
					style="height: 26px; width: 450px;" />
			</dd>
		</dl>
		<dl>
			<dt>备注</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="Fs_Remark"   value="<s:property value="zybOccuHisDTO.remark"/>"
					style="height: 26px; width: 450px;" />
			</dd>	
		</dl>		
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addocchis();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-fangsheshi').dialog('close')">关闭</a>
	</div>
</div>