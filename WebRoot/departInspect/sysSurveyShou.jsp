<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript" src="<%=request.getContextPath()%>/departInspect/sysSurveyShou.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
	$(function(){
		wentiList();
	})
</script>
<!-- 用户体检id -->
<input type="hidden"  value="<s:property value='c_id'/>"   id="s_id"   /> 
<input type="hidden"  id="exam_num_x_x"  value="<s:property value='exam_num_x'/>"  />
<div  style="padding:5px;overflow-y:scroll;height:93%;margin-bottom: 10px;">
	<input type="hidden"  value="" />
	<ul id="wj_neirong"  style="padding-left: 0px;margin-left: 0px;font-size:16px;">
	
	</ul>
</div>  
<div  style="padding-right: 0px;">
    <input type="button"  value="关闭"   class="easyui-linkbutton" style="width:80px;height:26px;margin-right: 0px;float: right;" onclick="javascript:$('#shou_wenjuan').dialog('close');">
</div>
