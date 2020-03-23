<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/examSet/examsettype.js?randomId=<%=Math.random()%>"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>


<body >
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>类别查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:70px;"><input type="checkbox"  checked="checked"  id="ck_exam_set_name" name = "ck_exam_set_name"  value=""/>类别名称</dt>
					<dd>
						<input  type="text" class="textinput" id="exam_set_name"  style="height:25px;width:140px;" />
					</dd>
					<dt style="width:70px;"><input type="checkbox"  id = "ck_set_class"  name = "ck_set_class"  value=""/>类别类型</dt>
					<dd><select id="set_class" class="easyui-combobox" name="set_class"  data-options="panelHeight:'auto',editable:false" style="width:140px;height:27px">   
					    <option value='1'>常规体检</option>   
					    <option value='2'>微信</option>   
					</select>  </dd>
					<dd><a href="javascript:dgridType();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>类别列表</strong></legend> 
      <table id="typeShow" style="height:525px"  >
      </table>	
 </fieldset>
 <div id="dlg-exam_set_type" class="easyui-dialog"  data-options="width:500,height: 300,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>