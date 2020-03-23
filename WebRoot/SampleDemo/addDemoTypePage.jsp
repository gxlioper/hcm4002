<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyin_dict_firstletter.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyinUtil.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" >
	$(function(){
		$('#demo_type1').combobox({
			url:"getDatadis.action?com_Type="+"YBFL",
			valueField : 'id',
			textField : 'name',
			editable:false,
			onLoadSuccess : function() {//下拉框默认选择
			}
	   })
	})
	
	function save(){
		$.ajax({
			url:'saveDemoTypePage.action',
			data:{"ids":$("#ids").val(),"demo_type":$('#demo_type1').combobox('getValue')},
			type:'post',
			success:function(data){
				$.messager.alert("提示信息",data,"info");
				$('#dlg_demo_type').dialog('close');
				getgroupuserGrid();
			},
			error:function(){
				$.messager.alert("警告信息","操作失败！","error");
			}
		})
		
	}
</script>
<input type="hidden" id="ids" value="<s:property value='ids'/>"  />
		<div class="formdiv"  style="margin-top: 50px;">
  			<dl>
  				<dt>所属类别：</dt>
  				<dd><input type="text" id="demo_type1" style="width: 290px;height: 26px;" /></input>
  			</dl>
	   	</div>
<div class="dialog-button-box">
	<div class="inner-button-box" >
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="save();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg_demo_type').dialog('close')">关闭</a>
	</div>
</div>

