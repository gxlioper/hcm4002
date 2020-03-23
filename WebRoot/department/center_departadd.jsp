<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
  function shifoudayin(){
	    var isPrint_Barcode = '<s:property value="IsPrint_Barcode_c"/>';
	    $("#isPrint_Barcode").val(isPrint_Barcode);
	 /*    if(isPrint_Barcode == 0){
	    	$("#isPrint_Barcode").find('option[value=0]').attr("selected",true);
		}else if(isPrint_Barcode == 1){
			$("#isPrint_Barcode").find('option[value=1]').attr("selected",true);
		}  */
  }
  function xingbie(){
		var sex='<s:property value="sex"/>';
	    if(sex=="全部"){
			$("#sex").find('option[value="全部"]').attr("selected",true);
		}else if(sex=="男"){
			$("#sex").find('option[value="男"]').attr("selected",true);
		}else if(sex=="女"){
			$("#sex").find('option[value="女"]').attr("selected",true);
			}
	}   
	$(function(){
		
		$("#dep_num").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#dep_num").change(function(){
			$("#message").html('');
		});
		$("#dep_num").blur(function(){
			var flag=$("#dep_num").validatebox('isValid');
			if(flag){
				$.ajax({
					url:'isUnique.action?dep_num='+$("#dep_num").val(),
					type:'post',
					success:function(data){
						if(data=='no'){
							$("#message").attr('value','no');
							$("#message").html('该科室编码已存在');
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
		
		$("#dep_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#dep_category").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#sex").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#seq_code").validatebox({
			//required:true,
			validType:'IsNumber'
		});
		$("#dep_category").combobox({
			url :'getDatadis.action?com_Type=KSLX',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name'
		})
		shifoudayin();
		xingbie();
		var view_result_type = '<s:property value="view_result_type"/>';
		$('#view_result_type').val(view_result_type);
	});
	function center_dep_save(){
		if (document.getElementById("dep_num").value == ''){
			$("#dep_num").focus();
			return;
		}else if($("#message").attr('value') == 'no'){
			$("#dep_num").focus();
			return;
		}else if(document.getElementById("dep_name").value == ''){
			$("#dep_name").focus();
			return;
		}else if($("#dep_category").combobox('getValue')==''){
			//alert('请选择科室类型');
			$.messager.alert('提示信息', '请选择科室类型!');
			return;
		}else if (document.getElementById("sex").value == ''){
			$("#sex").focus();
			return;
		}else if (document.getElementById("seq_code").value == ''){
			$("#seq_code").focus();
			return;
		}
	$.ajax({
		url:'updateCenterDep.action',  
		data:{
			id:$("#id").val(),
			dep_num:$("#dep_num").val(),
			dep_name:$("#dep_name").val(),
			dep_category:$("#dep_category").combobox('getValue'),
			sex:$("#sex").val(),
			seq_code:$("#seq_code").val(),
			remark1:$("#remark1").val(),
			dep_link:$("#dep_link").val(),
			isPrint_Barcode:$("#isPrint_Barcode").val(),
			dep_inter_num:$("#dep_inter_num").val(),
			calculation_rate:$('#calculation_rate').val(),
			dep_address:$('#dep_address').val(),
			/* synchro:$("input[name='synchro']:checked").val(), */
			view_result_type:$('#view_result_type').val()
		  },          
		  type: "post",  
		  success: function(text){
			    getGrid();	 
				$.messager.alert("操作提示",text);
			    $("#dep_edit").dialog("close");
		   },
		   error:function(){
		    	$.messager.alert('提示信息', "用户操作失败！","error");
		   }  
	});
}
 	
</script>
<script>
	
</script>	
	<fieldset style="margin:5px;padding-right:20px;">
	<legend><strong></strong></legend>
	<div class="formdiv">
	    	    <dl>
	    	       <dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>
	    	       		科室编码</dt>
	    	       <dd><input id="dep_num" name="dep_num" value="<s:property value="dep_num"/>"  disabled="disabled" class="textinput" data-options="prompt:'輸入科室编码'"></dd>
	    	       <dt  class="" style="position: absolute; height: 25px; width: 100px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    	       <dt>科室名称</dt>
	    	       <dd><input id="dep_name" name="dep_name" value="<s:property value="dep_name"/>" disabled="disabled"  class="textinput" data-options="prompt:'輸入科室名称'" ></dd>
	    	    </dl>
	    	     <dl>
	    	       <dt>科室类型</dt>
	    	       <dd><input id="dep_category" name="dep_category" disabled="disabled" value="<s:property value="dep_category"/>"  class="easyui-combobox" data-options="prompt:'请选择或输入科室类型'"></dd>
	    	   	    <dt>顺序码</dt>
	    	       <dd><input id="seq_code" name="seq_code" disabled="disabled" value="<s:property value="seq_code"/>"  class="textinput" ></dd>
	    	    </dl>   
	    	     <dl>
	    	      <dt>其他科室编码</dt>
	    	       <dd><input id="dep_inter_num" name="dep_inter_num" value="<s:property value="dep_inter_num"/>" class="textinput" ></dd>
	    	       <dt>适用性别</dt>
	    	       <dd> 
		    	       <select style="width:150px;background: #d9d9d9"  disabled="disabled" id="sex" >
		    	       		<option  value="全部">全部</option>
		    	       		<option  value="男">男</option>
		    	       		<option  value="女">女</option>
		    	       </select>
	    	       </dd>
	    	    </dl>
	    	    <dl>
	    	    	<dt>是否打印条码</dt>
	    	       <dd>
	    	       	 <select style="width:150px;" id="isPrint_Barcode">
		    	       		<option  value="0">是</option>
		    	       		<option  value="1">否</option>
		    	       </select>
	    	       </dd>
	    	       <dt>利润率(%)</dt>
	    	       <dd><input id="calculation_rate" name="calculation_rate" value="<s:property value="calculation_rate"/>" class="textinput" ></dd>
	    	    </dl>
    	        <dl>
    	    	   <dt>影像科保存结果类型</dt>
	    	       <dd>
	    	        	<select style="width:150px;" id="view_result_type" value="<s:property value="view_result_type"/>" name="view_result_type" >
		    	       		<option  value="1">样本</option>
		    	       		<option  value="2">科室编码</option>
		    	       </select>
	    	       </dd>
	    	        <!-- <dt>上传微信</dt>
	    	       <dd>
	    	        	<input type="radio" checked="checked" value="1" name="synchro" />是&nbsp;&nbsp;&nbsp;&nbsp;
	    	        	<input type="radio" value="2" name="synchro" />否
	    	       </dd> -->
	    	    </dl>
	    	     <dl>
	    	    	 <dt>科室地址</dt>
	    	       <dd><input id="dep_address" name="dep_address" value="<s:property value="dep_address"/>" style="width: 465px" class="textinput" ></dd>
	    	    </dl>
	    	    <dl>
	    	    	 <dt>备注</dt>
	    	       <dd><input id="remark1" name="remark1" value="<s:property value="remark1"/>" style="width: 465px" class="textinput" ></dd>
	    	    </dl>
	    	    </div>
	    	   <!--  </form> -->
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="center_dep_save();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dep_edit').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

