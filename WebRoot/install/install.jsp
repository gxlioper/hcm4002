<%@ page contentType="text/html;charset=utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>版本说明--圈存查询一体机系统安装向导</title>
<link href="img/style.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript">
function formSubmit() {
	if(document.getElementById('license_agree').checked==false){
		alert('请接受已经阅读完版本信息的说明');
		return false;
	}
	document.getElementById('license_form').submit();
}
</script>
</head>

<body>


<div class="regist-header box">
  <div class="rgheader box">
         <div class="brand fl">
            <h1 style="margin:0px;"></h1>
         </div>
     </div>
</div>
<div class="main">
  <table width="980" border="0" align="center" cellpadding="0" cellspacing="0" class="rg-tbg">
    <tr>
      <td height="76" align="center"><h2>1、系统版本说明</h2>
	  <span style="color:#016dd0;">欢迎使用圈存查询一体机系统，<h>强烈建议</h>安装前仔细阅读不同版本的说明：</span></td>
    </tr>
    <tr>
      <td>
	  <table width="600" border="0" align="center" cellpadding="0" cellspacing="0" >
  
  <tr>
    <td height="300">
	<textarea name="textarea" cols="82" rows="23">                            --==圈存查询一体机系统说明==--
  版权所有哈尔滨新中新电子股份有限公司(c)2007-2014。
   感谢您使用圈存查询一体机系统。
  历史版本说明
2.6.1.0
1、支持金融ic卡读取银行卡号功能；
2、支持新中新标准的复旦微电子旧卡；
3、支持m1卡、cpu卡读写卡；
4、支持电信卡、移动、移动cpu1代、移动cpu2代读写卡（需要调试）；
5、支持m1 4k卡读取指定扇区  需要配置applicationContext-cardtype.xml文件；
6、对密码是否正确做了提前验证；
7、对卡片是否正常做了提前验证；
8、cpu卡片支持//复旦微//握奇卡//握奇卡//广州电信手机辫子卡//恒宝//大唐//南方全球通//通宝卡// 中行捷德// 中行恒宝// 中行天喻// 中行天喻；
9、选择刷银行卡或者pboc卡读取银行卡号，需要配置applicationContext-cardtype.xml文件；
10、可设置是否输入银行卡号 需要配置applicationContext-cardtype.xml文件。

2.6.1.1
1、支持复旦微电子的2代卡的读写；
2、支持256台终端机器，原来版本只支持127台；
3、单选框变大，修改了样式；
4、修改建立和插销对应关系时检测银行卡的bug；
5、修改读取pboc银行卡的bug；
6、解决终端编号不能大于127的问题。

2.7.0.0
1、基本功能，兼容圈存QCJ-126 UEC、ATT-203SZ和ATT-204SZ机器；
2、测试了m1卡和上海复旦微电子规范的cpu卡；
3、增加了终端机器的授权功能。

2.7.1.0
1、增加了对老的圈存linux 的机器不判断授权；
2、修改了菜单的显示功能，使其可以按照菜单工具的样式逐级显示；
3、将持卡人的基本信息和流水查询做到菜单里面，由菜单定义是否显示；
4、将当日流水和历史流水合并为一个功能，叫“流水查询”；
5、分开商户、商户管理员登陆页面。统一由菜单工具分配显示位置；
6、兼容QCJ-226圈存终端（只兼容m1卡和上海复旦微电子规范的cpu卡）；
7、实现界面化配置圈存查询一体机文件的配置；
8、去掉终端编号的配置，改用从数据库获取终端信息。所以要求必须在银行转账控制台里面设置真实的ip;
9、规定基本信息索引0、流水查询索引1、辅助信息查询索引2、商户查询索引3、商户操作员查询索引4。

2.8.X.X  深圳大学城版本

2.9.0.0 
1、固化了部分第三方缴费功能（0001 常工电子电控  0002 常工电子水控  0003 新中新电控  0004 新中新水控  0005 锐捷网络缴费  0006 城市热点网络缴费  0007 华为网络缴费  0008 新开普电控  0009 爱丽德电控  0010 三合电控）

2.9.0.1 
1、调整授权检测功能；
2、增加第三方缴费功能   0011 深澜网络缴费
    </textarea></td>
  </tr>
  <tr>
    <td align="right">
	<form id="license_form" action="<%=request.getContextPath()%>/install/install_init.svl" method="post">
	<table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
     <tr>
        <td  height="30" align="center"><input type="checkbox" id="license_agree" name="license_agree"/></td>
        <td  align="left">我已经阅读完版本信息的说明</td>
        </tr>
      <tr>
        <td height="30" colspan="2" align="center"><input type="button" class="regist-submit" onclick="formSubmit();" value=" 下一步 " /></td>
        </tr>
    </table>
	</form>	</td>
  </tr>
</table>	  </td>
    </tr>
  </table>
</div>


</body>
</html>