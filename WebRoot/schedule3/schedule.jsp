<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%  
  application.setAttribute("name","application_James");  
   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<link href='<%=request.getContextPath()%>/schedule3/packages/bootstrap/all.css' rel='stylesheet' />
<link href='<%=request.getContextPath()%>/schedule3/packages/core/main.css' rel='stylesheet' />
<link href='<%=request.getContextPath()%>/schedule3/packages/daygrid/main.css' rel='stylesheet' />
<link href='<%=request.getContextPath()%>/schedule3/packages/bootstrap/main.css' rel='stylesheet' />
<link href="<%=request.getContextPath()%>/schedule2/jquery-ui.css" rel="stylesheet" type="text/css" />

<script src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/schedule2/jquery-ui.min1.9.1.js" type="text/javascript"></script>
<script src='<%=request.getContextPath()%>/schedule3/packages/core/main.js?randomId=<%=Math.random()%>'></script>
<script src='<%=request.getContextPath()%>/schedule3/packages/interaction/main.js'></script>
<script src='<%=request.getContextPath()%>/schedule3/packages/daygrid/main.js'></script>
<script src='<%=request.getContextPath()%>/schedule3/packages/bootstrap/main.js'></script>
<script src='<%=request.getContextPath()%>/schedule3/schedule.js?randomId=<%=Math.random()%>'></script>

<body>
<input id = "schedule_eite" type="hidden" value="<s:property value='significance'/>"/>
    <div class='left' style="display:none;">
      <div id='theme-system-selector' class='selector'>
        Theme System:
        <select>
          <option value='bootstrap' selected>Bootstrap 4</option>
          <option value='standard'>unthemed</option>
        </select>
      </div>
      <div data-theme-system="bootstrap" class='selector' style='display:none'>
        Theme Name:
        <select>
          <option value='' >Default</option>
          <option value='cerulean' selected>Cerulean</option>
        </select>
      </div>
      <span id='loading' style='display:none'>loading theme...</span>
    </div>
    <div id="wrap"  style="width: 98%">
        <div id='calendar'  >
        </div>
        <div id="reserveinfo" title="Details">
            <div id="revtitle">
            </div>
            <div id="revdesc">
            </div>
        </div>
        <div style="display: none" id="reservebox" title="Reserve meeting room">
            <form id="reserveformID" method="post">
            <div class="rowElem"  style="display:none">
                <label>
                    标题:</label>
                <input id="id" type="hidden"/>
                <input id="title"  type="hidden"   maxlength="50" name="start"><span    id="biaoti"  style="color:#FF0000"    >&nbsp;*</span>
            </div>
            <div class="rowElem"  style="display: none;">
                <label>
                    重要程度:</label>
                <input id="chengdu" name="start">
            </div>
            <div class="rowElem"  style="display:none">
                <label>
                    开始时间:</label>
                <span  type="hidden"  id="shijian"></span><input   type="hidden"  readonly="readonly"  style="width:145px;" id="start" name="start"><span   id="kaishi"  style="color:#FF0000"    >&nbsp;*</span>
            </div>
            <div class="rowElem"  style="display:none">
                <label>
                    结束时间:</label>
               <input id="end"   type="hidden"  readonly="readonly" name="end"><span    type="hidden" id="jieshu"  style="color:#FF0000"    >&nbsp;*</span>
            </div>
            <div class="rowElem">
                <label>
                    内容:</label>
                <textarea id="details" style="width: 770px;height: 400px;resize: none;font-size: 15px;" name="details"></textarea>
            </div>
            </form>
        </div>
       
    </div>
</body>
</html>