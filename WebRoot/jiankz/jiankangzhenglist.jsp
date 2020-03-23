<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jiankz/jiankangzhenglist.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<style type="text/css">
.scolor{color:#fff;}
</style>
<body>
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:100%;">
<input type="hidden" id="company_id" value="0">
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
<div class="user-query">
				<dl>
				    <dt style="height:26px;width:80px;"><s:text name="tjhname"/></dt>
					<dd>
					<input  type="text" class="textinput" id="exam_numjkz"    style="height:26px;width:100px;ime-mode: disabled;" />
					</dd> 
					<dt style="height:26px;width:60px;">姓名</dt>
					<dd>
					<input  type="text" class="textinput" id="user_namejkz"    style="height:26px;width:135px;" />
					</dd>
					<dt style="height:26px;width:80px;">体检日期</dt>
			         <dd><input  type="text" class="easyui-datebox" id="join_datejkzstart"    style="height:26px;width:100px;" /></input>
                     <dt style="height:26px;width:60px;">至</dt>
			         <dd><input  type="text" class="easyui-datebox" id="join_datejkzend"    style="height:26px;width:135px;" />
			         </dd> 
			         <dt style="width:100px;">选择是否上传</dt>
		    <dd>
		       <select class="easyui-combobox" id="examflags" style="width:120px;height:26px;"></select>
		    </dd>
			
				</dl>
				<dl>
				<dt style="height:26px;width:80px;">选择单位</dt>					
					<dd><input class="easyui-textbox"  type="text" id="com_Namejkz" value="" style="height:26px;width:305px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:1px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
				   <dt style="height:26px;width:80px;"><s:text name="dahname"/></dt>					
					<dd><input  type="text" class="textinput" id="arch_numjkz"    style="height:26px;width:100px;" />
					</dd>   
                    <dt style="height:26px;width:60px;">身份证</dt>					
					<dd><input  type="text" class="textinput" id="id_numjkz"    style="height:26px;width:135px;" /></dd>
					 
					 <dt style="width:100px;">打印状态</dt>
				    <dd>
				       <select class="easyui-combobox" id="sfdy" style="width:120px;height:26px;"></select>
				    </dd>
                    <dd><a href="javascript:getJKZList(1);" class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:80px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="height:26px;width:80px;">清空</a></dd>
                </dl>
			</div>	
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;height:77%;">
<legend><strong>健康证列表信息</strong></legend>
      <table id="jkzshow" class="easyui-datagrid" >
      </table>	
 </fieldset>
  </div>
  </div>
 <div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  </body>