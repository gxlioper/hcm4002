<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>体检任务信息</title>
<style type="text/css">
<!--
.STYLE4 {font-size: 16px; font-weight: bold; }
-->
    @media print{
    .print{display:block;}
    .nprint{display:none;}
    }
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
</head>

 <script language="javascript">
        function winprintln()
        {
            /* bdhtml=window.document.body.innerHTML;
            sprnstr="<!--startprint-->";
            eprnstr="<!--endprint-->";
            prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
            prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
            window.document.body.innerHTML=prnhtml; */
            window.print();
        }
    </script>

<body>
<!--startprint-->
<p class="print" >&nbsp;</p>
<p class="print" align="center"  class="STYLE4"><s:property value="model.comname"/>-<s:property value="model.batch_name"/>明细</p>
<p class="print">&nbsp;</p>
<table class="print" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td align="center" valign="middle"> 
<table width="950" height="389" border="1" align="center" cellspacing="0">
  <tr>
    <td colspan="8"><div align="left" class="STYLE4">单位名称：<s:property value="model.comname"/></div></td>
  </tr>
  <tr>
    <td width="79"><div align="right" class="STYLE4">联系人</div></td>
    <td width="128">&nbsp;<s:property value="model.contact_name"/></td>
    <td width="94"><div align="right" class="STYLE4">联系电话</div></td>
    <td width="120">&nbsp;<s:property value="model.phone"/></td>
    <td width="85"><div align="right" class="STYLE4">结算方式</div></td>
    <td width="198">&nbsp;<s:property value="model.pay_way"/></td>
    <td width="75"><div align="right" class="STYLE4">收费类型</div></td>
    <td width="137">&nbsp;<s:property value="model.charge_type"/></td>
  </tr>
  <tr>
    <td><div align="right" class="STYLE4">计划人数</div></td>
    <td>&nbsp;<s:property value="model.exam_number"/></td>
    <td><div align="right" class="STYLE4">计划费用</div></td>
    <td>&nbsp;<s:property value="model.exam_fee"/></td>
    <td><div align="right" class="STYLE4">住宿安排</div></td>
    <td>&nbsp;<s:property value="model.accommodation"/></td>
    <td><div align="right" class="STYLE4">用餐安排</div></td>
    <td>&nbsp;<s:property value="model.dine"/></td>
  </tr>
  <tr>
    <td><div align="right" class="STYLE4">发票抬头</div></td>
    <td colspan="3">&nbsp;<s:property value="model.invoice_title"/></td>
    <td><div align="right" class="STYLE4">创建人</div></td>
    <td>&nbsp;<s:property value="model.creaters"/></td>
    <td class="STYLE4"><div align="right">创建日期</div></td>
    <td>&nbsp;<s:property value="model.create_time"/></td>
  </tr>
  <tr>
    <td><div align="right"><span class="STYLE4">邮寄地址</span></div></td>
    <td colspan="3">&nbsp;<s:property value="model.batch_address"/></td>
    <td><div align="right" class="STYLE4">修改人</div></td>
    <td>&nbsp;<s:property value="model.updaters"/></td>
    <td class="STYLE4"><div align="right">修改日期</div></td>
    <td>&nbsp;<s:property value="model.update_time"/></td>
  </tr>

  <tr>
    <td colspan="2" bgcolor="#F2F9F5"><div align="center" class="STYLE4">分组名称</div></td>
    <td colspan="6" bgcolor="#F2F9F5"><div align="center" class="STYLE4">套餐说明</div></td>
  </tr>
  <s:iterator value="model.groupSetList" status="index">
  <tr>
    <td colspan="2"><div align="center"><strong>&nbsp;<s:property value="set_name" /></strong></div></td>
    <td colspan="6">&nbsp;<s:property value="disease_name" /></td>
  </tr>
  </s:iterator>
 
  <tr>
    <td colspan="2" bgcolor="#F2F9F5"><div align="center" class="STYLE4">分组名称</div></td>
    <td bgcolor="#F2F9F5"><div align="center" class="STYLE4">实际金额/人</div></td>
    <td bgcolor="#F2F9F5"><div align="center" class="STYLE4">折扣率</div></td>
    <td colspan="4" bgcolor="#F2F9F5"><div align="center" class="STYLE4">分组选项</div></td>
  </tr>
  <s:iterator value="model.groupItemList" status="index">
  <tr>
    <td colspan="2"><div align="center"><strong>&nbsp;<s:property value="group_name" /></strong></div></td>
    <td><div align="right">&nbsp;<s:property value="amount" /></div></td>
    <td><div align="right">&nbsp;<s:property value="discount" /></div></td>
    <td colspan="4">&nbsp;<s:property value="itemnames" /></td>
  </tr>
   </s:iterator>
  
  <tr>
    <td colspan="2" bgcolor="#F2F9F5"><div align="center" class="STYLE4">体检项目</div></td>
    <td colspan="6" bgcolor="#F2F9F5"><div align="center" class="STYLE4">体检项目说明</div></td>
  </tr>
  <s:iterator value="model.itemList" status="index">
  <tr>
    <td colspan="2"><strong>&nbsp;<s:property value="item_name" /></strong></td>
    <td colspan="6">&nbsp;<s:property value="exam_num" /></td>
  </tr>
   </s:iterator>
   <tr>
    <td colspan="8" bgcolor="#F2F9F5"><div align="center" class="STYLE4">体检人员计划</div></td>
  </tr>
  <s:iterator value="model.bppList" status="index">
  <tr>
    <td colspan="2"><div align="center"><strong>&nbsp;<s:property value="plandate"/></strong></div></td>
    <td colspan="6">&nbsp;<s:property value="per_num"/>（人）</td>
  </tr>
  </s:iterator>
</table>
</td>
</tr>
</table>
<!--endprint-->
<p class="nprint" align="center">&nbsp;</p>
<p class="nprint" align="center">
  <input type="button" name="Submit2" onclick="javascript:winprintln();" value="打印体检任务" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <input type="button" name="Submit2" onclick="javascript:window.close();" value="关闭窗口" />
</p>
</body>
</html>
