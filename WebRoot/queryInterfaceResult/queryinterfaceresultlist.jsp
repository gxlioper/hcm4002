<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />


<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/queryInterfaceResult/queryinterfaceresultlist.js?randomId=<%=Math.random()%>"></script>
<style type="text/css">
.scolor{color:#fff;}
</style>
<body>
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:100%;">
<input type="hidden" id="company_id" value="0">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
<div class="user-query">
				<dl>
				    <dt style="height:26px;width:60px;">体检号</dt>
					<dd>
					<input  type="text" class="textinput" id="exam_num"    style="height:26px;width:100px;ime-mode: disabled;" />
					</dd> 
					<dt style="height:26px;width:60px;">姓名</dt>
					<dd>
					<input  type="text" class="textinput" id="user_name"    style="height:26px;width:100px;" />
					</dd>
					<dt style="height:26px;width:60px;">体检日期</dt>
			         <dd><input  type="text" class="easyui-datebox" id="join_datestart"    style="height:26px;width:100px;" value="<s:property value="join_datestart"/>"/></input>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd><input  type="text" class="easyui-datebox" id="join_dateend"    style="height:26px;width:100px;" value="<s:property value="join_datestart"/>"/>
			         </dd> 
			         <dt style="width:60px;">发送状态</dt>
		    <dd>
		       <select class="easyui-combobox" id="sendflag" style="width:120px;height:26px;"></select>
		    </dd>
		    <dt style="height:26px;width:80px;">选择单位</dt>					
					<dd><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:200px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:850px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
		      <dd><a href="javascript:getInterfaceList();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:80px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="height:26px;width:80px;">清空</a></dd>
             
				</dl>
				
			</div>	
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;height:77%;">
<legend><strong>接口列表信息</strong></legend>
      <table id="interfaceshow" >
      </table>	
 </fieldset>
  </div>
  </div>
 <div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  </body>