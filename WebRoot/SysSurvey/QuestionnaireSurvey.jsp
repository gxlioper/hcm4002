<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>
<script type="text/javascript">
/**
 * 验证体检号是否有效
 */
var s_num=""
function chazhaotijianhao(){
	$.ajax({
		url:'getQuestionnaireSurveyPageShow.action?s_num='+$('#num').val(),
		type:'post',
	    success:function(data){
	    	if(data=="youxiao"){
	    		var url="getQuestionnaireSurveyPageYM.action?s_num="+$("#num").val();
	    	 	newWindow = window.open (url,'fullscreen = yes , height=100%, width=100%, top=5, left=5,toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no','_self') 
	    	 	newWindow.focus();
	    	}else{
		    	$.messager.alert("警告信息","体检号不存在","error");
	    	}
	    },
	    error:function(){
	    	$.messager.alert("警告信息","操作失败","error");
	    }
		
	})
}
function qk(){
	$('#num').textbox('setValue',"")
}
</script>
<body   >
	<div style="width: 400px;text-align:center; margin:200 auto; ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="text"  class="easyui-textbox"  id="num"   data-options="prompt:'输入体检编号'"       style="width: 200px;height: 26px" />
		<input type="button" style="width: 80px;height: 28px"  value="确定"  onclick="chazhaotijianhao();" />
	</div>
</body>
</html>