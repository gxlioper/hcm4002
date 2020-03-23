<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>系统日志管理</title>	
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/scripts/plugins/upload/uploadify.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/plugins/tree.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body style="height:600px;">
		<div id="cont-page" class="include-page">
			<div class="location">
				<div class="repeat textbox-div"><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" id="father_iconurl" class="icon-24 <s:property value="#session.menufather_iconurl"/>" alt="" /><s:property value="#session.menufather_name"/> <font face="simsun">&gt;</font> <s:property value="#session.menu_now"/></div>
				<s class="sprite"></s><i class="sprite"></i>
			</div>
			<div style="width:100%;overflow:hidden;">
					<ul class="searchbar" style="width:200000px;">
						<li>操作时间:<input id="starttime" name="starttime"   type="text" class="easyui-datebox" style="height:26px"/> <strong class="red">*</strong>
									到
									 <input id="endtime" name="endtime"   type="text" class="easyui-datebox" style="height:26px"/> <strong class="red">*</strong>

						</li>
						<li>
						<button class="easyui-linkbutton c6 l-btn l-btn-small" iconcls="icon-search-light"  style="width:80px;height: 30px;" onclick="f_seacher();">查询</button>
						</li>
					</ul>
			</div>
			<table id="infolist"></table>
		</div>
<script>
	$(function(){
		//设置初始日期
		var regDate=CurentTime();
		$("#starttime").datebox('setValue', regDate);
		$("#endtime").datebox('setValue', regDate);
		getGrid();
	});
	function getGrid()
	{
			var starttime=$("#starttime").datebox('getValue');
			var endtime=$("#endtime").datebox('getValue');
			if(starttime=="")
			{
				$.messager.alert("提示","起始日期不能为空！", "warning");
				return ;
			}
			if(endtime=="")
			{
				$.messager.alert("提示","结束日期不能为空！", "warning");
				return ;
			}
			var start = new Date(starttime.replace("-", "/").replace("-", "/"));
			var end = new Date(endtime.replace("-", "/").replace("-", "/"));
			if (end < start) 
			{
			    $.messager.alert("提示","结束日期不能小于起始日期！", "warning");
				return false;
			}
			var timeflag=CheckForm(starttime,endtime);
			if(!timeflag)
			{
				return;
			}
	        var model = {};
			$("#infolist").datagrid({
			url: '<%=request.getContextPath()%>/sysLogmainshow.action?starttime='+starttime+'&endtime='+endtime,
			queryParams: model,
			rownumbers:true,
	       // toolbar: '#toolbar',
	        //sortName: 'opdate',
			//sortOrder: 'desc',
			remoteSort:true,
	   		collapsible:true,
	   		rownumbers:true,
	        height: 325,
	        columns:[[{align:"center",field:"ry",title:"人员",width:20},
	                  {align:"center",field:"dateTime",title:"操作时间",width:30},
	                  {align:"center",field:"explain",title:"说明",width:30},
	                  {align:"center",field:"bizType",title:"业务类型",width:30,formatter:ywlx},
	        		  {align:"center",field:"operType",title:"操作类型",width:30,formatter:czlx}
	        		  ]],
     		onLoadSuccess:function(value){
   		    	$("#datatotal").val(value.total);
   		    	$("#rowTotal").val(value.total);
   		    },
   		    singleSelect:true,
   		    collapsible:true,
   			pagination: true,
   	        fitColumns:true,
   	        striped:true
   	       // toolbar:toolbar
		});
	}
	function f_seacher()
	{
		getGrid();
	}
	function CurentTime()
    { 
        var now = new Date();
       
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
       
        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
       
        var clock = year + "-";
       
        if(month < 10)
            clock += "0";
       
        clock += month + "-";
       
        if(day < 10)
            clock += "0";
           
        clock += day + " ";
        return(clock); 
    } 
	function czlx(val,row)
	{
		//操作类型，1为增加、2为删除、3为修改、4为查询
	    if(val==1)
	    {
	        return '增加';
	    }
	    else if(val==2) 
	    {
	       return '删除';
	    }
	    else if(val==3) 
	    {
	       return '修改';
	    }
	    else if(val==4) 
	    {
	       return '查询';
	    }
	    else
	    {
	       return '未识别';
	    }
		
	}
	function ywlx(val,row)
	{
		//操作类型，1为系统业务、2访问报表
	    if(val==1)
	    {
	        return '系统业务';
	    }
	    else if(val==2) 
	    {
	       return '访问报表';
	    }
	    else
	    {
	       return '未识别';
	    }
		
	}
	//查询在31天之内
	function CheckForm(first,second)
	{
	    var data1 = Date.parse(first.replace(/-/g,   "/"));  
	    var data2 = Date.parse(second.replace(/-/g,   "/"));  
	    var datadiff = data2-data1;  
	    var time = 31*24*60*60*1000; 
	    //alert("datadiff:"+datadiff);
	    if(first.length>0 && second.length>0)
	    {  
              if(datadiff<0||datadiff>time)
              {  
                 $.messager.alert("提示","起始时间与结束时间 间隔应小于31天，请检查!", "warning");
                 return false;  
              }  
	    }  
		return true;
	 }
</script>
</body>

</html>
