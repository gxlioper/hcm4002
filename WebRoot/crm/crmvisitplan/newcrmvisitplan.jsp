 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmvisitplan/newcrmvisitplan.js?randomId=<%=Math.random()%>"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>
</head>
<body>
<input id="plan_visit_datedata" value='<s:property value="plan_visit_date"/>' hidden="true">
 <div class="easyui-layout" fit="true" style="margin:5px">
	<div data-options="region:'east',border:false"  style="width:24%;">
		<div class="easyui-panel" style="height:50%">
		<div  id="genzongrili1" class="easyui-calendar" style="width:100%;height:100%;"></div>
		</div>
		<div class="easyui-panel" style="height:50%">
			<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
			<dt style="width:120px;">
				任务总数 &nbsp;&nbsp;<span id="rewuzongshu"><s:property value="rewuzongshu"/></span>条
			</dt>
			<dt style="width:120px;">
				客户总数 &nbsp;&nbsp;<span id="kehuzongshu"><s:property value="kehuzongshu"/></span>位
			</dt>
		</dl>
		<dl>
			<dt style="width:120px;">
				普通任务 &nbsp;&nbsp;<span id="putongrenwuzongshu"><s:property value="putongrenwuzongshu"/></span>条
			</dt>
			<dt style="width:120px;">
				已计划 &nbsp;&nbsp;<span id="yidafukehuzongshu"><s:property value="yidafukehuzongshu"/></span> 位
			</dt>
		</dl>
		<dl>
			<dt style="width:120px;">
				一般任务 &nbsp;&nbsp;<span id="yibanrenwuzongshu"><s:property value="yibanrenwuzongshu"/></span> 条
			</dt>
			<dt style="width:120px;">
				未计划 &nbsp;&nbsp;<span id="weidafukehuzongshu"><s:property value="weidafukehuzongshu"/></span>  位
			</dt>
		</dl>
		<dl>
			<dt style="width:120px;">
				重要任务 &nbsp;&nbsp;<span id="zhongyaorenwuzongshu"><s:property value="zhongyaorenwuzongshu"/></span>条
			</dt>
			<dt style="width:120px;">
				生日人数 &nbsp;&nbsp;<span id="shengrikehushu"><s:property value="shengrikehushu"/></span>位
			</dt>
		</dl>
		</div>
		</div>
	</div>
	
		<div data-options="region:'west',border:false"  style="width:38%;">
		<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'west',border:false"  style="width:50%;" >
		<div class="easyui-panel" style="height:50%">
			<div style="text-align:center;font-size:60px;margin-top:50px">
				<span id="jinrirenwucount"><s:property value="jinrirenwucount"/></span>
				<font style="font-size:12px">条</font>
			</div>
			<div style="text-align:center;margin:10px">
				今日任务
			</div>
		<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
			<dt style="width:80px;">
				普通&nbsp;&nbsp;<span id="putongrenwucount"><s:property value="putongrenwucount"/></span>条
			</dt>
			<dt style="width:80px;">
				一般&nbsp;&nbsp;<span id="yibanrenwucount"><s:property value="yibanrenwucount"/></span>条
			</dt>
			<dt style="width:80px;">
				重要&nbsp;&nbsp;<span id="zhongyaorenwucount"><s:property value="zhongyaorenwucount"/></span>条
			</dt>
		</dl>
		<dl>
			<dt style="width:100px;">
				<a href="javascript:f_historyrenwu()">》》进入历史任务</a>
			</dt>
		</dl>
		</div>
		</div>
		<div class="easyui-panel" style="height:50%">
			<div style="text-align:center;font-size:60px;margin-top:50px">
				<span id="huifanggenzong"><s:property value="huifanggenzong"/></span>
				<font style="font-size:12px">条</font>
			</div>
			<div style="text-align:center;margin:10px">
				今日回访跟踪
			</div>
		<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
		<dt style="width:80px;">
				
			</dt>
			<dt style="width:80px;">
				
			</dt>
			</dl>
			<dl>
			<dt style="width:120px;">
				<a href="javascript:f_historygenzong()">》》进入历史回访跟踪</a>
			</dt>
		</dl>
		</div>
		</div>
		</div>
		
		<div data-options="region:'center',border:false"  style="width:25%;">
			<div class="easyui-panel" style="height:50%">
			<div style="text-align:center;font-size:60px;margin-top:50px">
				<span id="dafuzixun"><s:property value="dafuzixun"/></span>
				<font style="font-size:12px">条</font>
			</div>
			<div style="text-align:center;margin:10px">
				咨询答复
			</div>
			<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
			<dt style="width:80px;">
				
			</dt>
			<dt style="width:80px;">
				
			</dt>
			</dl>
		<dl>
			<dt style="width:100px;">
			<a href="javascript:f_historyzixun()">》》进入咨询答复</a>
			</dt>
		</dl>
		</div>
		</div>
		<div class="easyui-panel" style="height:50%">
			<div style="text-align:center;font-size:60px;margin-top:50px">
				<span id="kehucount"><s:property value="kehucount"/></span>
				<font style="font-size:12px">位</font>
			</div>
			<div style="text-align:center;margin:10px">
				当前客户数
			</div>
		<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
			<dt style="width:100px;padding-left:40px">
				<!--已计划&nbsp;&nbsp;<span id="yidafukehucount"><s:property value="yidafukehucount"/></span>位-->
			</dt>
			<dt style="width:100px;">
				<!--未计划&nbsp;&nbsp;<span id="weidafukehucount"><s:property value="weidafukehucount"/></span>位-->
			</dt>
		</dl>
		<dl>
			<dt style="width:100px;">
				<a href="javascript:f_historykehu()">》》进入我的客户</a>
			</dt>
		</dl>
		</div>
		</div>
			</div>
			
		</div>
		</div>
		<div data-options="region:'center',border:false" style="width:38%;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',border:false"  style="width:50%;" >
			<div class="easyui-panel" style="height:50%">
				<div style="text-align:center;font-size:60px;margin-top:50px">
				<span id="fujianflag"><s:property value="fujianflag"/></span>
				<font style="font-size:12px">条</font>
			</div>
			<div style="text-align:center;margin:10px">
				需复检提示
			</div>
			<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
		<dt style="width:80px;">
			</dt>
			<dt style="width:80px;">
			</dt>
			</dl>
			<dl>
			<dt style="width:100px;">
			<a href="javascript:f_historyfujian()">》》进入复检提示</a>
			</dt>
		</dl>
		</div>
		</div>
		<div class="easyui-panel" style="height:50%">
			<div style="text-align:center;font-size:60px;margin-top:50px">
				<span id="shifangjilu"><s:property value="shifangjilu"/></span>
				<font style="font-size:12px">条</font>
			</div>
			<div style="text-align:center;margin:10px">
				今日失访记录
			</div>
		<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
		<dt style="width:80px;">
				
			</dt>
			<dt style="width:80px;">
				
			</dt>
			</dl>
			<dl>
			<dt style="width:120px;">
			<a href="javascript:f_historyshifang()">》》进入历史失访记录</a>
			</dt>
		</dl>
		</div>
		</div>
			</div>
			
			<div data-options="region:'east',border:false"  style="width:50%;" >
			<div class="easyui-panel" style="height:50%">
				<div style="text-align:center;font-size:60px;margin-top:50px">
				<span id="shengrikehu"><s:property value="shengrikehushu"/></span>
				<font style="font-size:12px">位</font>
			</div>
			<div style="text-align:center;margin:10px">
				生日客户
			</div>
			<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
		<dt style="width:80px;">
			</dt>
			<dt style="width:80px;">
			</dt>
			</dl>
			<dl>
			<dt style="width:100px;">
			<a href="javascript:f_shengrikehu()">》》进入生日客户</a>
			</dt>
		</dl>
		</div>
		</div>
			</div>
			
			</div>
		</div>
</div>
</body>
</html>