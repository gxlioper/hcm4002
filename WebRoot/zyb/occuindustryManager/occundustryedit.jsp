<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function (){		
		$("#industry_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#industry_code").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#industry_code").blur(function(){
			var flag=$("#industry_code").validatebox('isValid');
			if(flag){
				$.ajax({
					url:'isvalidateOcuindustry.action?industry_code='+$("#industry_code").val(),
					type:'post',
					success:function(data){
						if(data=='no'){
							$("#message").attr('value','no');
							$("#message").html('该编码已存在');
							return true;
						}else if(data=='ok'){
							$("#message").attr('value','ok');
							$("#message").html('');
							return false;
						}
					}
			});
			}	
		});
		$("#set_name").combobox({
			url :'geTExam_set.action',
			editable : false, //不可编辑状态
			//cache : false,
			panelHeight : '300',//自动高度适合
			valueField : 's_id',
			textField : 'set_name',
				onLoadSuccess : function(data) {
					for (var i = 0; i < data.length; i++) {
						if (Number(data[i].s_id) == Number($("#packageID").val())) {
							$('#set_name').combobox('setValue', data[i].s_id);
							break;
						} else {
							$('#set_name').combobox('setValue', data[0].s_id);
						}
					}
				}
		});		
	});	
		
	function savecyhy(){
	if (document.getElementById("industry_name").value == "") {
			$("#industry_name").focus();
			return;
		} else if (document.getElementById("industry_code").value == "") {
			$("#industry_code").focus();
			return;
		} else if($("#message").attr('value')=='no'){
			$("#industry_code").focus();
			return;
		}

		$.ajax({
			url:'saveOccuindustry.action',
			data:{
				industryID:$("#industryID").val(),
				packageID:$("#set_name").combobox('getValue'),
				industry_name:$("#industry_name").val(),
				industry_code:$("#industry_code").val(),
				scriptID:$("#scriptID").val(),
				trainperiod:$("#trainperiod").val(),
				phyexeperiod:$("#phyexeperiod").val()
			},
			type : "post",//数据发送方式   
			success : function(text) {
				if (text.split("-")[0] == 'ok') {
					$.messager.alert('提示信息', "操作成功！");
					$("#edit_dlg").dialog("close");
					getcyhylist();
				}else{
					$.messager.alert("操作提示", text, "error");
				}
				
			},
			error:function() {
				$.messager.alert("操作提示", "操作错误", "error");
			}
		});
	}
</script>
<fieldset style="margin:5px; height:90%;">
		<legend><strong>从业行业信息编辑</strong></legend>
			<div class="formdiv">
				<dl>
	    	       <dt>行业名称</dt>
	    	       <dd><input id="industry_name"  value="<s:property value='industry_name'/>"  class="textinput"  ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt><input type="hidden"  id="industryID" value="<s:property value='industryID'/>"   />
	    	       		行业编码</dt>
	    	       <dd><input id="industry_code"  value="<s:property value='industry_code'/>"  class="textinput" ></dd>
	    	       <dt style="position: absolute; height: 25px; width: 150px; margin-left:240px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
	    	    <dl>
	    	       <dt>套餐名称</dt><input type="hidden"  id="packageID" value="<s:property value='packageID'/>" />
	    	       <dd><input id="set_name" class="easyui-combobox" ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>报表文件</dt>
	    	       <dd><input id="scriptID" value="<s:property value='scriptID'/>" ></dd>
	    	       <dt style="position: absolute; height: 25px; width: 150px; margin-left:240px;"><!-- span  id="message" class="red"></span --></dt>
	    	    </dl>
	    	    <dl>
	    	       <dt>培训有效期</dt>
	    	       <dd><input id="trainperiod"  value="<s:property value='trainperiod'/>"  type="number" ></dd>
	    	    </dl>
	    	    <dl>
	    	    	<dt>体检有效期</dt>
	    	       <dd><input id="phyexeperiod" name="phyexeperiod" value="<s:property value='phyexeperiod'/>"  type="number" ></dd>
	    	    </dl>
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="savecyhy();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>