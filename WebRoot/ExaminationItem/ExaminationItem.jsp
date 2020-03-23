<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ExaminationItem/ExaminationItem.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","45");
})
</script>
<%-- <style>
.icon
{
    background: url(icon.png) no-repeat; /*url为png图片的路径*/
    /*width和line-height分别对应每个小图标的宽和高*/
    width: 20px;    
    line-height: :20px;
    display: inline-block;
}
.icon-small
{   /*png图片左上角为原点,往右往下均为负值*/
    background-position: -150px 0px;/*对应小图标的坐标*/
}
</style> --%>
<body class="easyui-layout"  style="height: 99%">
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="exeurl" value="<s:property value="model.others"/>">
<input type="hidden" id="dept_id" value="">
<div  data-options="region:'west',title:'科室',split:true" style="width:15%;">
	<div id='some_tree' data-options="animate:true"></div>
</div>   
<div  data-options="region:'center'">
	 <fieldset style="margin:5px;padding-right:0;height: 60px;">
	  <legend><strong>检查项目查询</strong></legend>
				<div class="user-query">
					<dl>
						<dt style="width:60px">项目编码</dt>
						<dd><input class="easyui-textbox"    
						  type="text" id="num"  style="height:26px;width:130px;"/>&nbsp;&nbsp;&nbsp;</dd>
						<dt style="width:60px">项目名称</dt>
						<dd><input class="easyui-textbox"  type="text" id="demo_name"   style="height:26px;width:130px;"/></dd>
						<%-- <dt>项目检测条件</dt>
						<dd>
							<select id="xmjc"  class="easyui-combobox"  data-options="panelHeight:80">
								<option>不检测</option>
								<option>名称重复</option>
								<option>参考值为空</option>
								<option>危机值为空</option>
								<option>参考值-危机值-为空</option>
							</select>
						</dd> --%>
						<dt>关联检验编码</dt>
						<dd><input class="easyui-textbox"  type="text" id="c_exam_num"   style="height:26px;width:130px;"/></dd>
						<dt>关联影像编码</dt>
						<dd><input class="easyui-textbox"  type="text" id="c_view_num"   style="height:26px;width:130px;"/></dd> 
						
						<dt style="height:26px;width:65px;"><input id="chkItem" name="chkItem" type="checkbox" checked="checked" value="Y"/>启用</dt>		
						<dt style="height:26px;width:65px;"><input id="chkItem" name="chkItem" type="checkbox" value="N"/>停用</dt>	
						
						<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
						<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
					</dl>
				</div>
	 </fieldset>
	 <fieldset style="margin:5px;padding-right:0;height:83%">
	<legend><strong>检查项目列表</strong></legend> 
	      <table id="groupusershow">
	      </table>	
	 </fieldset>
	 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
	 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
	 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 </div> 
  </body>