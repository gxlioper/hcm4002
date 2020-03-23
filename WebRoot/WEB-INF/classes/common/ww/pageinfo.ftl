<script language="javascript">
function checkPageSize(){
	var oPageSize = document.getElementById("pagesize");
	var size=oPageSize.value;
  if(size != ""){
     if(isNaN(size)){	  
        alert('**每页显示条数**输入必须为数字!');
        oPageSize.focus();
        return false;                  
     }
     else{
        if(parseInt(size)<=0) {
          alert('**每页显示条数**输入数字必须大于0!');
          oPageSize.focus();
          return false;
        }
     }
  }
  else{
     alert('每页显示条数不能为空!');
     oPageSize.focus();
     return false;
  }
}

function checkPageNo(){
	var oPageNo = document.getElementById("pageno");
	var pageNo=oPageNo.value;
	var pagetotal="${pagetotal?default(1)}";
	pagetotal=parseInt(pagetotal.replace(',',''));
  if(pageNo != ""){
     if(isNaN(pageNo)){	  
        alert("**转向页号**输入必须为数字!");
        oPageNo.focus();
        return false;                  
     }
     else{
        if(parseInt(pageNo)<1){
          alert("**转向页号**页号不能小于1!");
          oPageNo.focus();
          return false;
        }
        if(parseInt(pageNo)>pagetotal){
          alert("**转向页号**页号不能大于"+pagetotal+"!");
          oPageNo.focus();
          return false;
        }  
     	
     }
  }
  else{
     alert('**转向页号**页号不能为空!');
     oPageNo.focus();
     return false;
  }
}
function locatePage(action){
        	var iPageNo=${pageno?default(1)};
        	
        	if(action==1){
      			iPageNo=1;
      		}
      		else if(action==2){
      			iPageNo=parseInt(iPageNo)-1;
      		}
      		else if(action==3){
      			iPageNo=parseInt(iPageNo)+1;
      		}
      		else if(action==4){
      			iPageNo=${pagetotal?default(1)};
      		}
      		document.forms[0].pageno.value=iPageNo;
      		document.forms[0].submit();
}
</script>
<table border="0" cellpadding="0" width="100%">
<tr>
<td width="40%">
	每页显示 <input name="pagesize" type="text" class="textfield" value="${pagesize?default(10)}" size="3" onblur="return checkPageSize();">条 
	<input type="submit" class="button" name="btnPageSize" value="确定">&nbsp;&nbsp;共<font color="#993333"><span id="pagetotal">${pagetotal?default(1)}</span></font>页<font color="#993333">${rectotal?default(0)}条&nbsp;&nbsp;
</td>

<td valign="center" width="45%">
	<#if pageno?default(1)!=1>
		<input type="button" class="button" onclick="javascript:locatePage(1)" value="首页" style="cursor:hand"/>&nbsp;&nbsp;&nbsp;
		<input type="button" class="button" onclick="javascript:locatePage(2)" value="上页" style="cursor:hand"/>&nbsp;&nbsp;&nbsp;
	<#else>
	   <input type="button" class="button" value="首页"/>&nbsp;&nbsp;&nbsp;
	   <input type="button" class="button" value="上页"/>&nbsp;&nbsp;&nbsp;
	</#if>
	
	<#if pageno?default(1)!=pagetotal?default(1)>
	   <input type="button" class="button" onclick="javascript:locatePage(3)" value="下页" style="cursor:hand"/>&nbsp;&nbsp;&nbsp;
	   <input type="button" class="button" onclick="javascript:locatePage(4)" value="末页" style="cursor:hand"/>&nbsp;&nbsp;&nbsp;
	<#else>
		 <input type="button" class="button" value="下页"/>&nbsp;&nbsp;&nbsp;
	   <input type="button" class="button" value="末页"/>&nbsp;&nbsp;&nbsp;
	</#if>
</td>
	
<td align="right" width="15%">
	到第<input name="pageno" type="text" class="textfield" value="${pageno?default(1)}" size="2" onchange="return checkPageNo();" onblur="return checkPageNo();">页
	<input type="submit" name="btnGoPage" value="跳转"> 
</td>
</tr>
</table>