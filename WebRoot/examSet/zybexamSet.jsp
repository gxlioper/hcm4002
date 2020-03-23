<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/examSet/zybexamSet.js?randomId=<%=Math.random()%>"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>


<body class="easyui-layout" style="height: 99%">
    <div data-options="region:'west',title:'危害因素',split:true" style="width:250px;">
    		<div  data-options="region:'north'"  style="height:5%;padding-left: 20px;padding-top: 10px;margin-bottom:10px;">
	 				<input type="text"  id="cx"    style="width:140px;height: 23px;"  value=""  />
	 				<input type="button"  value="查询"  onclick="shu($('#cx').val());"  style="width:50px;height: 25px"/>
	 			</div>
	 		<div data-options="region:'south'"	>
    			<ul id="some_tree"></ul>
    		</div>
    </div>   
    <div data-options="region:'center',title:'套餐'" style="padding:5px;">
<input type="hidden"   id="intss"  value="<s:property value='intss'/>"/>
<input type="hidden"   id="ys_name"  value="" name="ys_name"/>
<input type="hidden"   id="lb_name"  value="" name="lb_name"/>
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检套餐查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:70px;">套餐名称</dt>
					<dd>
						<input  type="text" class="textinput" id="set_name"  style="height:23px;width:140px;" />
					</dd>
					<dt style="text-align: right;">
						适用性别：
					</dt>
					<dd>
						<input type="radio"   value=''  onclick="getgroupuserGrid();"  id='sexN' name='Jsex' checked="checked"  />&nbsp;全部&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio"   value='男'    onclick="getgroupuserGrid();"  id='sexN' name='Jsex'  />&nbsp;&nbsp;男&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio"  value='女'     onclick="getgroupuserGrid();" id='sexNv' name='Jsex'  />&nbsp;&nbsp;女
					</dd>
					<dt style="width:90px;text-align: right">修改时间</dt>
					<dd>
						&nbsp;&nbsp;<input class="easyui-datebox"   type="text" id="update_time"  style="height:23px;width:140px;"/>
					</dd>
					<dd>至<input class="easyui-datebox"  type="text" id="update_times"  style="height:23px;width:140px;"/></dd>
					<dd><a href="javascript:getgroupuserGrid();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;" >
<legend><strong>体检套餐列表</strong></legend> 
      <table id="groupusershow"   >
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 1200,height: 590,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 </div> 
</body>
</html>