<script language="javascript">
        var pageno;
        var pagetotal; 
        function initPage(ret,pagefunction){
        	
        	pageno=ret.pageNo;
					pagetotal=ret.pageTotal;
					var pagesize=ret.pageSize;
					var rectotal=ret.recTotal;
        	
        	document.getElementById("pageno").value=pageno;
        	document.getElementById("pagesize").value=pagesize;
        	document.getElementById("pagetotal").innerText=pagetotal;
        	document.getElementById("rectotal").innerText=rectotal;
					
					        
        	if(pageno==1 && pageno==pagetotal){
        		document.getElementById("synjones_page_span1").style.display="block";
        		document.getElementById("synjones_page_span2").style.display="none";
        		document.getElementById("synjones_page_span3").style.display="none";
        		document.getElementById("synjones_page_span4").style.display="none";
        	}else if(pageno==1 && pageno!=pagetotal){
        		document.getElementById("synjones_page_span2").style.display="block";
        		document.getElementById("synjones_page_span1").style.display="none";
        		document.getElementById("synjones_page_span3").style.display="none";
        		document.getElementById("synjones_page_span4").style.display="none";
        	}else if(pageno!=1 && pageno==pagetotal){
        		document.getElementById("synjones_page_span3").style.display="block";
        		document.getElementById("synjones_page_span1").style.display="none";
        		document.getElementById("synjones_page_span2").style.display="none";
        		document.getElementById("synjones_page_span4").style.display="none";
        	}else{
        		document.getElementById("synjones_page_span4").style.display="block";
        		document.getElementById("synjones_page_span1").style.display="none";
        		document.getElementById("synjones_page_span2").style.display="none";
        		document.getElementById("synjones_page_span3").style.display="none";
        	}
        	        	        	
        	document.getElementById("btngopage").onclick=pagefunction;
        	document.getElementById("btnpagesize").onclick=pagefunction;
        
        }
        
        function locatePage(action){
        	if(action==1){
      			pageno=1;
      		}
      		else if(action==2){
      			pageno=pageno-1;
      		}
      		else if(action==3){
      			pageno=pageno+1;
      		}
      		else if(action==4){
      			pageno=pagetotal;
      		}
      		document.forms[0].pageno.value=pageno;
      		
      		document.getElementById("btngopage").click();
				}
				function checkPageSize(){
					var size = document.forms[0].pagesize.value;
				  if(size != ""){
				     if(isNaN(size)){	  
				        alert('**ÿҳ��ʾ����**�������Ϊ����!');
				        document.forms[0].pagesize.focus();
				        return false;                  
				     }
				     else{
				        if(parseInt(size)<=0) {
				          alert('**ÿҳ��ʾ����**�������ֱ������0!');
				          document.forms[0].pagesize.focus();
				          return false;
				        }
				     }
				  }
				  else{
				     alert('ÿҳ��ʾ��������Ϊ��!');
				     document.forms[0].pagesize.focus();
				     return false;
				  }
				}
				
				function checkPageNo(){
					var pageNo = document.forms[0].pageno.value;
				  if(pageNo != ""){
				     if(isNaN(pageNo)){	  
				        alert("**ת��ҳ��**�������Ϊ����!");
				        document.forms[0].pageno.focus();
				        return false;                  
				     }
				     else{
				        if(parseInt(pageNo)<1){
				          alert("**ת��ҳ��**ҳ�Ų���С��1!");
				          document.forms[0].pageno.focus();
				          return false;
				        }
				        if(parseInt(pageNo)>pagetotal){
				          alert("**ת��ҳ��**ҳ�Ų��ܴ���"+pagetotal+"!");
				          document.forms[0].pageno.focus();
				          return false;
				        }  
				     	
				     }
				  }
				  else{
				     alert('**ת��ҳ��**ҳ�Ų���Ϊ��!');
				     document.forms[0].pageno.focus();
				     return false;
				  }
				}
</script>
<table border="0" cellpadding="0" width="100%" class="barbg">
<tr>
<td>
	ÿҳ��ʾ <input name="pagesize" type="text" class="textfield" value="${pagesize?default(10)}" size="3" onblur="return checkPageSize();">�� 
	<input type="button" class="button" name="btnpagesize" value="ȷ��">&nbsp;&nbsp;��<font color="#993333"><span id="pagetotal">${pagetotal?default(1)}</span></font>ҳ<font color="#993333"><span id="rectotal">${rectotal?default(0)}</span>��&nbsp;&nbsp;
</td>

<td valign="center">
		<span id="synjones_page_span1" style="display:none">
			��һҳ&nbsp;&nbsp;&nbsp;��ҳ&nbsp;&nbsp;&nbsp;
		  ��ҳ&nbsp;&nbsp;&nbsp;ĩҳ&nbsp;&nbsp;&nbsp;
	  </span>
	  
	  <span id="synjones_page_span2" style="display:none">
			��һҳ&nbsp;&nbsp;&nbsp;��ҳ&nbsp;&nbsp;&nbsp;
		  <a href="#" onclick="locatePage(3)">��ҳ</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="locatePage(4)">ĩҳ</a>&nbsp;&nbsp;&nbsp;
	  </span>
	  
	  <span id="synjones_page_span3" style="display:none">
			<a href="#" onclick="javascript:locatePage(1)">��һҳ</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="locatePage(2)">��ҳ</a>&nbsp;&nbsp;&nbsp;
	    ��ҳ&nbsp;&nbsp;&nbsp;ĩҳ&nbsp;&nbsp;&nbsp;</font>
	  </span>
	  
	  <span id="synjones_page_span4" style="display:none">
			<a href="#" onclick="javascript:locatePage(1)">��һҳ</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="locatePage(2)">��ҳ</a>&nbsp;&nbsp;&nbsp;
		  <a href="#" onclick="locatePage(3)">��ҳ</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="locatePage(4)">ĩҳ</a>&nbsp;&nbsp;&nbsp;
	  </span>
</td>
	
<td>
	����<input name="pageno" class="textfield" type="text" value="${pageno?default(1)}" size="4" onblur="return checkPageNo();">ҳ
	<input type="button" class="button" name="btngopage" value="��ת"> 
	&nbsp;&nbsp;
</td>
</tr>
</table>	