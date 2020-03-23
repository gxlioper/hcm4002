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
				        alert('**每页显示条数**输入必须为数字!');
				        document.forms[0].pagesize.focus();
				        return false;                  
				     }
				     else{
				        if(parseInt(size)<=0) {
				          alert('**每页显示条数**输入数字必须大于0!');
				          document.forms[0].pagesize.focus();
				          return false;
				        }
				     }
				  }
				  else{
				     alert('每页显示条数不能为空!');
				     document.forms[0].pagesize.focus();
				     return false;
				  }
				}
				
				function checkPageNo(){
					var pageNo = document.forms[0].pageno.value;
				  if(pageNo != ""){
				     if(isNaN(pageNo)){	  
				        alert("**转向页号**输入必须为数字!");
				        document.forms[0].pageno.focus();
				        return false;                  
				     }
				     else{
				        if(parseInt(pageNo)<1){
				          alert("**转向页号**页号不能小于1!");
				          document.forms[0].pageno.focus();
				          return false;
				        }
				        if(parseInt(pageNo)>pagetotal){
				          alert("**转向页号**页号不能大于"+pagetotal+"!");
				          document.forms[0].pageno.focus();
				          return false;
				        }  
				     	
				     }
				  }
				  else{
				     alert('**转向页号**页号不能为空!');
				     document.forms[0].pageno.focus();
				     return false;
				  }
				}
</script>
<table border="0" cellpadding="0" width="100%" class="barbg">
<tr>
<td>
	每页显示 <input name="pagesize" type="text" class="textfield" value="${pagesize?default(10)}" size="3" onblur="return checkPageSize();">条 
	<input type="button" class="button" name="btnpagesize" value="确定">&nbsp;&nbsp;共<font color="#993333"><span id="pagetotal">${pagetotal?default(1)}</span></font>页<font color="#993333"><span id="rectotal">${rectotal?default(0)}</span>条&nbsp;&nbsp;
</td>

<td valign="center">
		<span id="synjones_page_span1" style="display:none">
			第一页&nbsp;&nbsp;&nbsp;上页&nbsp;&nbsp;&nbsp;
		  下页&nbsp;&nbsp;&nbsp;末页&nbsp;&nbsp;&nbsp;
	  </span>
	  
	  <span id="synjones_page_span2" style="display:none">
			第一页&nbsp;&nbsp;&nbsp;上页&nbsp;&nbsp;&nbsp;
		  <a href="#" onclick="locatePage(3)">下页</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="locatePage(4)">末页</a>&nbsp;&nbsp;&nbsp;
	  </span>
	  
	  <span id="synjones_page_span3" style="display:none">
			<a href="#" onclick="javascript:locatePage(1)">第一页</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="locatePage(2)">上页</a>&nbsp;&nbsp;&nbsp;
	    下页&nbsp;&nbsp;&nbsp;末页&nbsp;&nbsp;&nbsp;</font>
	  </span>
	  
	  <span id="synjones_page_span4" style="display:none">
			<a href="#" onclick="javascript:locatePage(1)">第一页</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="locatePage(2)">上页</a>&nbsp;&nbsp;&nbsp;
		  <a href="#" onclick="locatePage(3)">下页</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="locatePage(4)">末页</a>&nbsp;&nbsp;&nbsp;
	  </span>
</td>
	
<td>
	到第<input name="pageno" class="textfield" type="text" value="${pageno?default(1)}" size="4" onblur="return checkPageNo();">页
	<input type="button" class="button" name="btngopage" value="跳转"> 
	&nbsp;&nbsp;
</td>
</tr>
</table>	