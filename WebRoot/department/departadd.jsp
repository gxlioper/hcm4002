<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
  function shifoudayin(){
	    var isPrint_Barcode = '<s:property value="isPrint_Barcode"/>';
	    if(isPrint_Barcode == 0){
	    	$("#isPrint_Barcode").find('option[value=0]').attr("selected",true);
		}else if(isPrint_Barcode == 1){
			$("#isPrint_Barcode").find('option[value=1]').attr("selected",true);
		} 
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
			if($("#dep_num_old").val() && $("#dep_num_old").val()==$("#dep_num").val()) {
				$("#message").attr('value','ok');
				$("#message").html('');
				return false;
			}
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
	});

 	
</script>
<script>
	
</script>	
	<fieldset style="margin:5px;padding-right:20px;">
	<legend><strong> 添加科室</strong></legend>
	<div class="formdiv">

                <s:if test='id == 0' >
                    <dl>
                        <dt>体检中心</dt>
                        <input id="center" class="easyui-combobox" data-options="
                              panelHeight : 'auto',
                                url : 'queryAllExam.action',
                                valueField : 'center_num',
                                textField : 'center_name',
                                editable:false,
                                width:460,
                                height:26,
                                multiple:true,
                                onLoadSuccess:function(){
                                    var val = $(this).combobox('getData');
                                    for (var item in val[0]){
                                        if (item == 'center_num'){
                                             $(this).combobox('select', val[0][item]);
                                        }
                                    }
                                }
                              " />
                 </dl>
                 </s:if>

	    	    <dl>
	    	       <dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/><input type="hidden" id="dep_num_old" value="<s:property value="dep_num"/>"/>
	    	       		科室编码</dt>
	    	       <dd><input id="dep_num" name="dep_num" value="<s:property value="dep_num"/>"  class="textinput" data-options="prompt:'輸入科室编码'"></dd>
	    	       <dt  class="" style="position: absolute; height: 25px; width: 100px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    	       <dt>科室名称</dt>
	    	       <dd><input id="dep_name" name="dep_name" value="<s:property value="dep_name"/>"  class="textinput" data-options="prompt:'輸入科室名称'" ></dd>
	    	    </dl>
	    	     <dl>
	    	       <dt>其他科室编码</dt>
	    	       <dd><input id="dep_inter_num" name="dep_inter_num" value="<s:property value="dep_inter_num"/>" class="textinput" ></dd>
	    	   	    <dt>顺序码</dt>
	    	       <dd><input id="seq_code" name="seq_code" value="<s:property value="seq_code"/>"  class="textinput" ></dd>
	    	    </dl>   
	    	     <dl>
	    	       <dt>科室类型</dt>
	    	       <dd><input id="dep_category" name="dep_category" value="<s:property value="dep_category"/>"  class="easyui-combobox" data-options="prompt:'请选择或输入科室类型'"></dd>
	    	       <dt>适用性别</dt>
	    	       <dd> 
		    	       <select style="width:150px;" id="sex" >
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
	    	       <dt>科室链接</dt>
	    	       <dd><input id="dep_link" name="dep_link" value="<s:property value="dep_link"/>" style="width: 465px" class="textinput" ></dd>
	    	    </dl>
	    	    <dl>
	    	    	 <dt>备注</dt>
	    	       <dd><input id="remark" name="remark" value="<s:property value="remark"/>" style="width: 465px" class="textinput" ></dd>
	    	    </dl>
	    	    </div>
	    	   <!--  </form> -->
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_depsave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dep_edit').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

