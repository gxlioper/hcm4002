<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ExaminationItem/depExaminationItem.js?randomId=<%=Math.random()%>"></script>
<body class="easyui-layout">
<input  id="item_class_id"  value="" type="hidden" />
<input  id="charging_item_id"  value="" type="hidden" />
<input  id="item_class_name" value=""  value="" type="hidden" />
<!-- // 右键菜单定义如下： -->
<div id="yiceng" class="easyui-menu" style="width:120px;">
	<div onclick="addtreedialog()" data-options="iconCls:'icon-add'" style="height: 30px;text-align: center;">添加类别</div>
</div>
<div id="erceng" class="easyui-menu" style="width:120px;">
	<div onclick="xiugai()" data-options="iconCls:'icon-edit'" style="height: 30px;text-align: center;">修改</div>
	<div onclick="deletelb()" data-options="iconCls:'icon-remove'" style="height: 30px;text-align: center;">删除</div>
</div>
<div id="addtreedialog" class="easyui-dialog" title="编辑类别"   data-options="width:500,height:150,closed: true,cache: false,modal: true,top:50,left:20">
	<form action="" id="lbfrom"  style="margin-left: 20px;margin-top:20px;">
		<label>名称</label>
		<input  id="lbmc" type="text"   class="textinput easyui-validatebox" data-options="required:true" value=""   style="width: 200px;height: 23px;">
	</form>
	<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:saveDepItem()" class="easyui-linkbutton" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#addtreedialog').dialog('close')">关闭</a>
	</div>
</div>
</div>

<div data-options="region:'west',split:true" style="width:250px;">
	<input type="text" id="jiansuo" style="height:25px;width: 130px;margin-top:10px;"    /> <a href="javascript:saveDepItem()" class="easyui-linkbutton"  onclick="jiansuo()" style="width:60px;height: 25px;">查询</a>
	<ul id="tt"  ></ul>  
</div>   
<div data-options="region:'center'" style="">   
 <fieldset style="margin:5px;padding-right:0;height: 60px;">
<legend><strong>检查项目查询</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width:60px">项目编码</dt>
					<dd><input class="easyui-textbox"    
					  type="text" id="item_num"  style="height:26px;width:140px;"/>&nbsp;&nbsp;&nbsp;</dd>
					<dt style="width:60px">项目名称</dt>
					<dd><input class="textinput"  type="text" id="cl_demo_name"   style="height:26px;width:140px;"/></dd>
					<!-- <dt>关联检验编码</dt>
					<dd><input class="textinput"  type="text" id="c_exam_num"   style="height:26px;width:140px;"/></dd>
					<dt>关联影像编码</dt>
					<dd><input class="textinput"  type="text" id="c_view_num"   style="height:26px;width:140px;"/></dd>  -->
					<dd><a href="javascript:getgroupuserGridss();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;height:83%">
<legend><strong>检查项目列表</strong></legend> 
      <table id="groupusershow">
      </table>	
 </fieldset>
 </div>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height:500,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-lb" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  </body>