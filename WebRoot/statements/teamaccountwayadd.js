$(document).ready(function () {
	getteamwayGrid();
});


function getteamwayGrid(){
		var model={
				id:$("#account_id").val(),
			    batchid: $("#batch_idss").val()			    
			};
		$.ajax({
   		  url : 'teamwayList.action',
		  type : "POST",
	      data : model,
	      dataType: 'json',
	      scriptCharset: 'utf-8',
   	      success : function(data){ 			  
   		      //清空内容 
   		      var settotalamt=0;
   		      var strHtml="";
   		      strHtml+="<tr class='datagrid-header-row'><td field='wayname'>付款方式</td><td field='amount'>付款金额</td></tr>";
   		      $.each(data, function (infoIndex, info){
   		    	 if (infoIndex%2==0){
   		    		 strHtml += "<tr bgcolor='#F8F8FF'>";
   		    	 }else{
   		    		 strHtml += "<tr>";
   		    	 }	
   		    	 if(info.data_code_children == '372'){
   		    	 	 strHtml += "<td height: 25px;'><span style='margin-left: 5px;'>"+info.wayname+"</span></td>";   		    	 
	   		    	 strHtml +="<input type='hidden' name='wayid' value='"+ info.wayid + "' />";	        
			         strHtml += "<td style='height: 25px;'><span style='margin-left: 5px;'><span style='font-size: 11px;'>￥</span><input type='text' readonly='readonly' name='inputitem' id='inputitem' value='"+info.amount+"' onchange='dototalamt();' /></span></td>";
	   		    	 strHtml += "</tr>"; 
   		    	 }else{
   		    	 	 strHtml += "<td height: 25px;'><span style='margin-left: 5px;'>"+info.wayname+"</span></td>";   		    	 
	   		    	 strHtml +="<input type='hidden' name='wayid' value='"+ info.wayid + "' />";	        
			         strHtml += "<td style='height: 25px;'><span style='margin-left: 5px;'><span style='font-size: 11px;'>￥</span><input type='text' name='inputitem' id='inputitem' value='"+info.amount+"' onchange='dototalamt();' /></span></td>";
	   		    	 strHtml += "</tr>";
   		    	 }
   		    	settotalamt+=Number(info.amount);
   		      }) 
   		      strHtml += "</table>"; 
   		      $("#titem").empty();
   		      $("#titem").html(strHtml);
   		      $("#syamt").empty();
   		      var syamt = Number($("#accountamt").val())-Number(settotalamt);
   		      $("#syamt").html("剩余金额：￥"+syamt);
   		 },
   		 error : function(){
   			 Info("系统错误");
   		}
   	});

   } 


//验证判断
function isJE(str){
	var reg=/^\d+(\.\d\d)?$/;
	 var reg1=/^\d+(\.\d)?$/;
	if(reg.test(str))
	{
	  return true;
	}else{
	  if (reg1.test(str))
	  {
	     return true;
	  }else
	  {
	    return false;
	  }
	}	
}

function dototalamt(){
	var settotalamt = 0;    
	$("input[id=inputitem]").each(function(a,b){    	
		settotalamt+=Number($(this).val());
	}); 
	$("#syamt").empty();
	var syamt = Number($("#accountamt").val())-Number(settotalamt);
	syamt=syamt.toFixed(2);
	$("#syamt").html("剩余金额：￥"+syamt);
}


/**
 * 保存修改
 */
function teamaccountwayadddo() {
		 var settotalamt = 0;  
		 var ids=[];// 用户
		 var amts=[];
				var matamt = document.getElementsByName("inputitem");
				var matnum = document.getElementsByName("wayid");
				for(g = 0;g<matamt.length; g++){
				if((isJE(matamt[g].value))&&(Number(matamt[g].value)>0)){
					settotalamt+=Number(matamt[g].value);
					ids.push(matnum[g].value);	
					amts.push(matamt[g].value);	
				}	
				}
		settotalamt=settotalamt.toFixed(2);
				
		 if(Number($("#accountamt").val())!=Number(settotalamt)){
		     $.messager.alert("操作提示", "输入金额需要和【"+$("#accountamt").val()+"元】一致","error");
	     }else{
	 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
		    $.ajax({
			url : 'teamaccountwayadddo.action',
			data : {
				        company_id : $("#company_id").val(),
						batchid : $("#batch_idss").val(),
						account_num : $("#account_num").val(),	
						ids:ids.toString(),	
						id: $("#account_id").val(),
						note:amts.toString(),
						charges:$("#charges").val()
					},
					type : "post",// 数据发送方式
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							gettjxmGrid($("#account_num").val());
							gettermaccountlistGrjlid($("#account_num").val());
							$('#dlg-custadd').dialog('close');
							$.messager.alert("操作提示", text);
						} else {
							$.messager.alert("操作提示", text, "error");
						}
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
		}
}
