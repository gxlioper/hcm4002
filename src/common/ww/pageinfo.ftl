<script language="javascript">
function checkPageSize(){
	var oPageSize = document.getElementById("pagesize");
	var size=oPageSize.value;
  if(size != ""){
     if(isNaN(size)){	  
        alert('**ÿҳ��ʾ����**�������Ϊ����!');
        oPageSize.focus();
        return false;                  
     }
     else{
        if(parseInt(size)<=0) {
          alert('**ÿҳ��ʾ����**�������ֱ������0!');
          oPageSize.focus();
          return false;
        }
     }
  }
  else{
     alert('ÿҳ��ʾ��������Ϊ��!');
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
        alert("**ת��ҳ��**�������Ϊ����!");
        oPageNo.focus();
        return false;                  
     }
     else{
        if(parseInt(pageNo)<1){
          alert("**ת��ҳ��**ҳ�Ų���С��1!");
          oPageNo.focus();
          return false;
        }
        if(parseInt(pageNo)>pagetotal){
          alert("**ת��ҳ��**ҳ�Ų��ܴ���"+pagetotal+"!");
          oPageNo.focus();
          return false;
        }  
     	
     }
  }
  else{
     alert('**ת��ҳ��**ҳ�Ų���Ϊ��!');
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
	ÿҳ��ʾ <input name="pagesize" type="text" class="textfield" value="${pagesize?default(10)}" size="3" onblur="return checkPageSize();">�� 
	<input type="submit" class="button" name="btnPageSize" value="ȷ��">&nbsp;&nbsp;��<font color="#993333"><span id="pagetotal">${pagetotal?default(1)}</span></font>ҳ<font color="#993333">${rectotal?default(0)}��&nbsp;&nbsp;
</td>

<td valign="center" width="45%">
	<#if pageno?default(1)!=1>
		<input type="button" class="button" onclick="javascript:locatePage(1)" value="��ҳ" style="cursor:hand"/>&nbsp;&nbsp;&nbsp;
		<input type="button" class="button" onclick="javascript:locatePage(2)" value="��ҳ" style="cursor:hand"/>&nbsp;&nbsp;&nbsp;
	<#else>
	   <input type="button" class="button" value="��ҳ"/>&nbsp;&nbsp;&nbsp;
	   <input type="button" class="button" value="��ҳ"/>&nbsp;&nbsp;&nbsp;
	</#if>
	
	<#if pageno?default(1)!=pagetotal?default(1)>
	   <input type="button" class="button" onclick="javascript:locatePage(3)" value="��ҳ" style="cursor:hand"/>&nbsp;&nbsp;&nbsp;
	   <input type="button" class="button" onclick="javascript:locatePage(4)" value="ĩҳ" style="cursor:hand"/>&nbsp;&nbsp;&nbsp;
	<#else>
		 <input type="button" class="button" value="��ҳ"/>&nbsp;&nbsp;&nbsp;
	   <input type="button" class="button" value="ĩҳ"/>&nbsp;&nbsp;&nbsp;
	</#if>
</td>
	
<td align="right" width="15%">
	����<input name="pageno" type="text" class="textfield" value="${pageno?default(1)}" size="2" onchange="return checkPageNo();" onblur="return checkPageNo();">ҳ
	<input type="submit" name="btnGoPage" value="��ת"> 
</td>
</tr>
</table>