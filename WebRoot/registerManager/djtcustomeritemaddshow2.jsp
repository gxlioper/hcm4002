<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<style>
#main {	width: auto;	height: auto;}
#left {	width: 52%;	height: auto;}
#right {	width:47%;	height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
.pop_up_box_lis{
	border:1px solid #ccc;
	background:#fff;
	padding:0 0px 10px;
	position:absolute;
	font-size:12px;
	display:none;
}
.title{
	text-align:center;
	cursor:move;
	height:30px;
	line-height:30px;
	margin-bottom:15px;
	background:#359BCC;
	border-bottom:1px solid #ccc;
	color:#FFFFFF;
	font-size:16px;
}
.al_guanbi{
	width:215px;
	height:170px;
	font-size:16px;
	color:#FFFFFF;
	background-image: url(../images/6.jpg);
	overflow:hidden;
	margin:0 auto;
	background-repeat: no-repeat;
	text-align: center;
}
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/djtcustomeritemaddshow2.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyin_dict_firstletter.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyinUtil.js?randomId=<%=Math.random()%>"></script>
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="custsex" value="<s:property value="model.sex"/>">
<input type="hidden" id="a_customer_id" value="<s:property value="customer_id"/>">
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">
<!--资源  -->
<input type="hidden"  id="web_Resource" value="<s:property value = 'web_Resource'/>" />
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>个人自费</strong>
	</legend>
	<div class="user-query">
		<dl>
		  				<dt>原总额</dt>
			<dd>
				<input  type="text" readonly id="item_amount" value="0"
					style="height: 26px; width: 70px;" /><span>(元)</span>
			</dd>
			<dt id = "ttzkl">折扣率</dt>
			<dd>
<s:if test='teamAmountViewFlag==1'>
				<input  type="text" id="discount"
					value="10"
					style="height: 26px; width: 70px;" />
</s:if>
<s:else>**<input  type="hidden" id="discount"
					value="10"
					style="height: 26px; width: 70px;" />
</s:else>
			</dd>
			<dt id ="ttzkhze">折扣后总额</dt>
			<dd>
<s:if test='teamAmountViewFlag==1'>
				<input type="text" id="amount"
					value="0"
					style="height: 26px; width: 70px;" /><span id = "ttyuan">(元)</span>
</s:if>
<s:else>	**<input type="hidden" id="amount"
					value="0"
					style="height: 26px; width: 70px;" /><span>(元)</span>
</s:else>
			</dd>
			<dt>个人结算总额</dt>
			<dd>
				<input type="text" id="personal_pay" readonly
					value="0"
					style="height: 26px; width: 70px;" /><span>(元)</span>
			</dd>
			<dd>
			 <a href="javascript:djtcustadd();" class="easyui-linkbutton c6" style="width:70px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;" onclick="javascript:window.close();">关闭</a>
	    	<a href="javascript:getcopyitemg();" class="easyui-linkbutton "   style="width:140px;">复制上次体检项目</a>
				 <a href="javascript:getcopyitemg2();" class="easyui-linkbutton " style="width:140px;">选择人复制项目</a>
			&nbsp;&nbsp;&nbsp;&nbsp;<span  id="z_zhi"></span>
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择套餐</strong>
	</legend>

	<div id="main">

			套餐列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="text" class="textinput" id="tclist"
					onclick="selectsetforsexTOG();" value="" style="height: 26px; width: 160px;" />

			已选套餐
			<div id="selectctlist"></div>
	</div>
</fieldset>
 <div id="cc" class="easyui-layout" style="width:90%px;height:85%;">   
    <div data-options="region:'west',title:'科室'" style="width:200px;background:#eee;">
	   <!--  <fieldset style="margin: 5px; padding-right: 0;">
			<legend> -->
			<%-- 	<strong>科室</strong> --%>
			<!-- </legend> -->
		    	<ul id="depd_tree"   ></ul>
		    <!-- </fieldset> -->
    </div>   
     <div data-options="region:'center',title:'选择体检项目',width:500,tools:'#tt',toolPosition:'left'" style="padding:0px;background:#eee;">
     	
     	<!-- <fieldset style="margin: 5px; padding-right: 0;"> -->
				<div id ="itemshow"  ></div>
			<!-- </fieldset> -->
     </div>   
     <div id="tt"  style="width: 400px;" >
     	<div style="float: left;margin-right: 10%;">
	     	<font>名称检索</font>
	     	<input type="text" onkeyup="getItemShow(1)" id ='c_item_name'/>
     	</div>
     	<div style="float: left;margin-right:50%;" ><input type="checkbox"  onclick="item_qihuan_yixuan()" id="btn-yxxm"/><label onclick="item_qihuan_yixuan(1)"><font size="4">已选项目</font></label></div>
     	<div style="width:1050px;"></div> 
	</div>
</div>  

<div id="update_user" class="easyui-dialog"  data-options="width: 400,height: 200,closed: true,cache: false,modal: true,title:'增加介绍人'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:30px;">
			<dl>
				<dt style="width:120px;">介绍人：</dt>
				<dd><input type="text" name="introducer" id="introducer" style="width:150px; height: 28px;"/> <strong class="red">*</strong></dd>
			</dl>
		</div>
		</div>
	<div class="dialog-button-box">
		<input type="text" value="" id="itemIdMsg" style="display: none;"/>
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:updateUser();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#update_user').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
<div style="float: left;"></div>

<div id="cd"></div>
 <div id="dlg-item-fuzhi" class="easyui-dialog"  data-options="width:800,height:600,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-selectsetforsexTOG" class="easyui-dialog" data-options="width: 1200,height: 590,closed: true,cache: false,modal: true"></div>