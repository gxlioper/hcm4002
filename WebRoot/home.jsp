<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@taglib prefix="s" uri="/struts-tags"%>
<title>持卡人中心</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.toggle.js"></script>
<script type="text/javascript">
function abc(){
	/*$(".switch").find("li").each(function(i,k){
		$(k).click(function(){
			$(".switch li").removeClass("active");
			$(this).addClass("active");
			if($(this).attr('class')=='active'){
				alert($(this).attr('rel'));
			}
			$(".tabshow").hide();
			$("#"+$(k).attr("rel")).show();
		})
	})*/
	
	$(".switch li").click(function(){
		
		$("li[class='active']").removeAttr('class');
		$(this).addClass('active');
		$(".tabshow").hide();
		var ids=$(this).attr('rel');
		$("#"+ids).show();
		var tt=$("#morenews a").attr('href','more.jsp?lmid='+ids);
		//alert(tt);
		
	});
	
	
	
}
$(function(){

	$.st('#indexSlide > ul > li','#indexSlide >ol',{'mode':'slide','time':3});
	//登陆地址
	f_geturl();
	
	//得到公告列表
	f_getgglist();
	
	function f_geturl(param) {
   			 $.ajax({   
		        url:'homeloginurl.action',  
		        data:{},          
		        type: "post",//数据发送方式   
		          success: function(text){ 
		               var urldiv = $("#loginurldiv");
						var html="";
						var index=text.lastIndexOf("/");
						text=text.substr(0,index);
						html=html+'<input type="image" src="<%=request.getContextPath()%>/themes/default/images/loginbutton.png" onclick="window.location.href=\''+text+'\'"/>';
						urldiv.append(html);
		        }  
		    });    
	} 
	function f_getgglist(param) {
    $.post("homegglist.action","ok",  
        function (data) {
            f_showgg(data);  
         },"json");  
	} 
	
	function f_showgg(obj) { 
  		var gglist = obj;
  		var ggsele = $("#gglb");
		var html="";
		var gglength = 2;
		if(gglist.length<2)
			gglength = gglist.length;
		html=html+'<li class="title">重要公告<span class="arrow"></span></li>';
		for (var i = 0; i < gglength; i++) {
				var xgsjstr = gglist[i].xgsj;
				xgsjstr = xgsjstr.substring(0,4)+"-"+xgsjstr.substring(4,6)+"-"+xgsjstr.substring(6,8);
					html+='<li><font>&gt;</font> <a href="homeonexxfb.action?fbxxid='+gglist[i].fbxxid+'" target="_blank">'+gglist[i].bt+' ['+xgsjstr+']</a></li>';
			
							
		}
		if(gglist.length>2)
			html+='<li class="more"><a href="more.jsp"  target="_blank">更多<font>&gt;&gt;</font></a></li>'
		ggsele.append(html);
	}  
	function f_getXxlmlist(param) { 
    $.post("toaddxxfbeasyui.action","ok",  
        function (data) {
            f_showInfor(data);  
         },"json");  
	} 
	
	function f_showInfor(obj) { 
  		var xxlmlist = obj;
  		
  		var xxlmsele = $("#xxlmlb");
		xxlmsele.empty();//
		var html="";
		var xxlmlength = 4;
		if(xxlmlist.length<4)
			xxlmlength = xxlmlist.length;
		for (var i = 0; i < xxlmlength; i++) {
			//html+="<option value='" + xxlmlist[i].lmid + "'>" + xxlmlist[i].lmmc + "</option>";
			if(i==0){
				html+='<li rel="'+xxlmlist[i].lmid+'" class="active">'+ xxlmlist[i].lmmc +'</li>';
				f_getxxfblist(xxlmlist[i].lmid,xxlmlist[i].lmmc,1);
				
			}else{
				html+='<li rel="'+xxlmlist[i].lmid+'">'+ xxlmlist[i].lmmc +'</li>';
				f_getxxfblist(xxlmlist[i].lmid,xxlmlist[i].lmmc,0);
			}
		}
		xxlmsele.append(html);
		abc();
	}
	
	function f_getxxfblist(param,lmmc,isxs) {
    $.post("homexwlist.action?lmid="+param,"ok",
        function (data) {
        	
            f_showxw(data,param,lmmc,isxs);  
         },"json");  
	} 
	
	function f_showxw(obj,param,lmmc,isxs) { 
  		var xwlist = obj;
  		var xwsele = $("#xwlb");
		var html="";
		var xwlength = 4;
		if(xwlist.length<4)
			xwlength = xwlist.length;
		if(isxs==1)
			html+='<div class="tabshow" id="'+param+'" style="display:block;"><ul class="newslist">';
		else
			html+='<div class="tabshow" id="'+param+'"><ul class="newslist">';
			
			
		for (var i = 0; i < xwlength; i++) {
				var xgsjstr = xwlist[i].xgsj;
				xgsjstr = xgsjstr.substring(0,4)+"-"+xgsjstr.substring(4,6)+"-"+xgsjstr.substring(6,8);
				html+='<li><a href="homeonexxfb.action?fbxxid='+xwlist[i].fbxxid+'" target="_blank" class="title">['+lmmc+'] '+xwlist[i].bt+'</a><span class="date">'+xgsjstr+'</span></li>'
							
		}
		html+='</ul><div id="morenews" class="morenews"><a href="more.jsp?lmid='+param+'" target="_blank">更多内容</a></div></div>'
		xwsele.append(html);
		 
	}  
	
	 
})
</script>
</head>
<body>
<div id="header" class="homehead">
	<div class="box">
		<div class="logo" style="padding:18px 0 0 0;"><a title="查询系统"><img src="<%=request.getContextPath()%>/themes/default/images/logo.png" /></a></div>
		<h1 class="system-title" style="padding-top:22px;"><img src="<%=request.getContextPath()%>/themes/default/images/logo2.png" /></h1>
	</div>
</div>
<div class="bannerbox">
<!-- Slideshow -->
		<div class="slideshow" id="indexSlide">
		  <ul class="alt" >
		  	 <li class="on t01">1</li>
		     <li class="t02">2</li>
		     <li class="t03">3</li>
		   </ul>
		   <ol class="slide">
			    <li style="background-image:url('<%=request.getContextPath()%>/images/banner1.jpg');background-color:#D7E2F7;"></li>
			    <li style="background-image:url('<%=request.getContextPath()%>/images/banner2.jpg');background-color:#60CAB4;"></li>
			    <li style="background-image:url('<%=request.getContextPath()%>/images/banner3.jpg');background-color:#E3B673;"></li>
		    </ol>
		</div>
<!-- Slideshow End -->

<!--
	<div class="innerbox"><img src="images/banner1.jpg"/></div>
-->
</div>

<div class="noticebox">
	<ul id="gglb" name="gglb" class="innernotice">
		<!--li class="title">重要公告<span class="arrow"></span></li>
		<li><font>&gt;</font> <a href="">关于我行短信服务号码更新的公告 [2014-11-25]</a></li>
		<li><font>&gt;</font> <a href="">关于我行短信服务号码更新的公告 [2014-11-25]</a></li>
		<li class="more"><a href="#">更多<font>&gt;&gt;</font></a></li-->
	</ul>
</div>
<div id="content">
	<div class="content-home">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top">
					<div class="head-line"><strong>校园卡服务<span class="arrow"></span></strong></div>
					<ul class="custom-item item-type-2 item-type-4">
					<li>
						<dl>
							<dt><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon-96 ico-1-96" alt="" /></dt>
							<dd>
								<h2>消费查询</h2>
							</dd>
						</dl>
					</li>
					
					<li>
						<dl>
							<dt><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon-96 ico-2-96" alt="" /></dt>
							<dd>
								<h2>校园卡充值</h2>
							</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon-96 ico-3-96" alt="" /></dt>
							<dd>
								<h2>校园卡挂失</h2>
							</dd>
						</dl>
					</li>
					
					<li>
						<dl>
							<dt><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon-96 ico-4-96" alt="" /></dt>
							<dd>
								<h2>电子转账</h2>
							</dd>
						</dl>
					</li>
					
				</ul>
					<ul id="xxlmlb" name="xxlmlb" class="switch">
					
						<!--li rel="t_1" class="active">校内新闻</li>
						<li rel="t_2">私人通知</li>
						<li rel="t_3">公共资讯</li>
						<li rel="t_4">缴费通知</li-->
					</ul>
					<div id="xwlb" name="xwlb" class="tab-content">
						<!--div class="tabshow" id="t_1" style="display:block;">
							<ul class="newslist">
								<li><a href="" class="title">[校内新闻] 关于我行短信服务号码...</a><span class="date">2014-11-25</span></li>
								<li><a href="" class="title">[校内新闻] 关于2014年11月6日...</a><span class="date">2014-11-14</span></li>
								<li><a href="" class="title">[校内新闻] 交通银行第二代支付交通银行第二代支付交通银行第二代支付...</a><span class="date">2014-10-29</span></li>
								<li><a href="" class="title">[校内新闻] 关于航运及大宗商品...</a><span class="date">2014-10-13</span></li>
								<li><a href="" class="title">[校内新闻] 关于北京市分行新增...</a><span class="date">2014-09-26</span></li>
							</ul>
							<div class="morenews"><a href="#">更年更多内容</a></div>
						</div>
						<div class="tabshow" id="t_2">
							<ul class="newslist">
								<li><a href="" class="title">[私人通知] 关于我行短信服务号码...</a><span class="date">2014-11-25</span></li>
								<li><a href="" class="title">[私人通知] 关于2014年11月6日...</a><span class="date">2014-11-14</span></li>
								<li><a href="" class="title">[私人通知] 交通银行第二代支付交通银行第二代支付交通银行第二代支付...</a><span class="date">2014-10-29</span></li>
								<li><a href="" class="title">[私人通知] 关于航运及大宗商品...</a><span class="date">2014-10-13</span></li>
								<li><a href="" class="title">[私人通知] 关于北京市分行新增...</a><span class="date">2014-09-26</span></li>
							</ul>
							<div class="morenews"><a href="#">更年更多内容</a></div>
						</div>
						<div class="tabshow" id="t_3">
							<ul class="newslist">
								<li><a href="" class="title">[公共资讯] 关于我行短信服务号码...</a><span class="date">2014-11-25</span></li>
								<li><a href="" class="title">[公共资讯] 关于2014年11月6日...</a><span class="date">2014-11-14</span></li>
								<li><a href="" class="title">[公共资讯] 交通银行第二代支付交通银行第二代支付交通银行第二代支付...</a><span class="date">2014-10-29</span></li>
								<li><a href="" class="title">[公共资讯] 关于航运及大宗商品...</a><span class="date">2014-10-13</span></li>
								<li><a href="" class="title">[公共资讯] 关于北京市分行新增...</a><span class="date">2014-09-26</span></li>
							</ul>
							<div class="morenews"><a href="#">更年更多内容</a></div>
						</div>
						<div class="tabshow" id="t_4">
							<ul class="newslist">
								<li><a href="" class="title">[缴费通知] 关于我行短信服务号码...</a><span class="date">2014-11-25</span></li>
								<li><a href="" class="title">[缴费通知] 关于2014年11月6日...</a><span class="date">2014-11-14</span></li>
							</ul>
							<div class="morenews"><a href="#">更年更多内容</a></div>
						</div-->
					</div>
				</td>
				<td valign="top" width="320" style="padding-left:15px;padding-top:30px;">
					<div id="loginurldiv"></div>
					<!--  <ul class="custom-item item-type-2  item-type-3">
					<li>
						<a class="item-a" href="找回用户名.html"></a>
						<dl>
							<dt><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon-64 ico-user-64" alt="" /></dt>
							<dd>
								<h2>找回用户名</h2>
							</dd>
						</dl>
					</li>
					
					<li>
						<a class="item-a" href="找回密码.html"></a>
						<dl>
							<dt><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon-64 ico-pass-64" alt="" /></dt>
							<dd>
								<h2>找回密码</h2>
							</dd>
						</dl>
					</li>
					<li>
						<a class="item-a" href="操作演示.html"></a>
						<dl>
							<dt><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon-64 ico-help-64" alt="" /></dt>
							<dd>
								<h2>操作演示</h2>
							</dd>
						</dl>
					</li>
					
					
				</ul>-->
					<div class="widget">
						<h3><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon ico-24-more" alt="" />卡片招领</h3>
						<div class="box" id="card-lost">
								<!--li><span class="cardname"><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon ico-card" alt="" /> [32837] 蒙丽多</span><span class="phone"><img src="themes/default/images/blank.gif" class="icon ico-phone" alt="" />13621239329</span></li>
								<li><span class="cardname"><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon ico-card" alt="" /> [32837] Ksj lei</span><span class="phone"><img src="themes/default/images/blank.gif" class="icon ico-phone" alt="" />14778483824</span></li>
								<li><span class="cardname"><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon ico-card" alt="" /> [32837] 李中百</span><span class="phone"><img src="themes/default/images/blank.gif" class="icon ico-phone" alt="" />15982832732</span></li>
								<li><span class="cardname"><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon ico-card" alt="" /> [32837] 蒙丽多</span><span class="phone"><img src="themes/default/images/blank.gif" class="icon ico-phone" alt="" />13621239329</span></li>
								<li><span class="cardname"><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon ico-card" alt="" /> [32837] kka Wang</span><span class="phone"><img src="themes/default/images/blank.gif" class="icon ico-phone" alt="" />13621239329</span></li>
								<li><span class="cardname"><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon ico-card" alt="" /> [32837] 黄地</span><span class="phone"><img src="themes/default/images/blank.gif" class="icon ico-phone" alt="" />8566094</span></li>
								<li><span class="cardname"><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon ico-card" alt="" /> [32837] kka Wang</span><span class="phone"><img src="themes/default/images/blank.gif" class="icon ico-phone" alt="" />13621239329</span></li>
								<li><span class="cardname"><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" class="icon ico-card" alt="" /> [32837] 黄地</span><span class="phone"><img src="themes/default/images/blank.gif" class="icon ico-phone" alt="" />8566094</span></li-->
							
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>

<div id="footer">
	<div class="box">
		<div class="copyright">版权所有 &copy; 2012 哈尔滨新中新电子股份有限公司</div>
	</div>
</div>
</body>
</html>