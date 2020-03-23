function chaxun(){
	if(!$("#exam_num").val()) {
		return;
	}
	$("#titleText").empty();
	$("#titleText").append("问卷类型");
	$("#xiaLa").empty();
	$("#questionTitle").empty();
	getcustomerInfo();
	getFirstMenu_questshow();
}

//查询人员基本信息
function getcustomerInfo(){
	if(!$("#exam_num").val()) {
		return;
	}
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#exam_num").val(),
		type:'post',
		success:function(data){
			if(data == 'null'){
				$("#exam_id").val('');
				$("#arch_num").val('');
				$("#exam_num").val('');
				$("#user_name").html('');
				$("#sex").html('');
				$("#age").html('');
				$("#company").html('');
				$("#customer_type").html('');
				$("#set_name").html('');
				return;
			}
			var obj=eval("("+data+")");
			if(obj.flag && obj.flag == 'error') {
				$.messager.alert('提示信息',obj.info,'error');
				return;
			}
			$("#exam_id").val(obj.id);
			$("#exam_num").val(obj.exam_num);
			$("#arch_num").html(obj.arch_num);
			$("#user_name").html(obj.user_name);
			$("#sex").html(obj.sex);
			$("#age").html(obj.age);
			$("#company").html(obj.company);
			$("#customer_type").html(obj.customer_type);
			$("#set_name").html(obj.set_name);
		}
	});
}

function showRoot(){
	if(!$("#exam_num").val()) {
		$.messager.alert('提示信息','请输入体检编号！');
		return;
	}
	$("#titleText").empty();
	$("#titleText").append("问卷类型");
	$("#questionTitle").empty();
	$.ajax({
   		url:'getFirstMenu_questshow.action',
   		type:'post',
   		data: { quest_code: $("#quest_code").val(), exam_num:$("#exam_num").val()},
   		success:function(data){
   			var daMsg=eval("("+data+")"); //先转字符串
   			for(var i=0;i<daMsg.length;i++){
   				if(daMsg[i].isScale == "1") {
   					var iframe='<iframe scrolling="auto" width="100%" align="center" onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="scale_show.action?scale_code='+daMsg[i].quest_sub_code+'&exam_num='+$("#exam_num").val()+'"></iframe>';
   					$("#questionTitle").append(iframe);
   					$("#questionTitle").append("<br><br>");
   				} else {
   					getQuestAllList(0, daMsg[i].quest_sub_code);
   				}
   			}
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载菜单失败！','error');
   		}
   	});
}

//下拉列表
function getFirstMenu_questshow(){
	if(!$("#exam_num").val()) {
		return;
	}
	$.ajax({
   		url:'getFirstMenu_questshow.action',
   		type:'post',
   		data: { quest_code:$("#quest_code").val(), exam_num:$("#exam_num").val()},
   		success:function(data){
   			var daMsg=eval("("+data+")"); //先转字符串
   			var divTitle = "<div id='accordionid' class='easyui-accordion' style='width:155px;'>";
   			var divCont = "";
   			for(var i=0;i<daMsg.length;i++){
   				var str = "";
   				if(daMsg[i].quest_sub_name.length>11){
   					str = daMsg[i].quest_sub_name.substring(0,10)+"..."
   				}else{str =daMsg[i].quest_sub_name}
   				var div= "<div id='title_"+daMsg[i].quest_sub_code+"' title='<a onClick=\"javaScript:showAllResult(&#39;"+daMsg[i].quest_sub_code+"&#39;,&#39;"+daMsg[i].quest_sub_name+"&#39;,&#39;"+daMsg[i].isScale+"&#39;)\" style=\"color:#000; font-size:15px;\">"+str+"</a> '></div>";
   				divCont += div;
   	   			getSecondMenu_questshow(daMsg[i].quest_sub_code);
   			}
   			var divThree = "</div>";
   			$("#xiaLa").append(divTitle+divCont+divThree);
   			$.parser.parse($("#xiaLa")); //渲染
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载菜单失败！','error');
   		}
   	});
}

//加载子菜单
function getSecondMenu_questshow(quest_sub_code) {
	$.ajax({
   		url:'getMeunListFirst.action',
   		type:'post',
   		data: {Level: 0, quest_sub_code:quest_sub_code},
   		success:function(data){
   			var daMsg=eval("("+data+")"); //先转字符串
   			var divOne = "<ul class='ulOne'>";
   			var divThr = "</ul>";
   			var list = "";
			for(var j=0;j<daMsg.length;j++){
   	   			var str = "";
   				if(daMsg[j].titleName.length>16){
   					str = daMsg[j].titleName.substring(0,16)+"....";
   				}else{
   					str =daMsg[j].titleName;
   					var div = "<a href='javascript:showMenuTitle(\""+daMsg[j].titleID+"\",\""+daMsg[j].titleName+"\",\""+quest_sub_code+"\")'><li><span id='twoMenuList'>"+str+"</span></li></a>";
   					list += div;
   				}
   			}
			$("#title_"+quest_sub_code).append(divOne+list+divThr);
			list = "";
   			$.parser.parse($("#xiaLa")); //渲染
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载菜单失败！','error');
   		}
   	});
}

//显示 title全部信息
function showMenuTitle(id,name, quest_sub_code){
	$("#titleText").empty();
	$("#titleText").append(name);
	getQuestAllList(id, quest_sub_code);
}

function showAllResult(quest_sub_code,quest_sub_name,isScale){
	if(isScale == "1") {
		if(!$("#exam_num").val()) {
			$.messager.alert('提示信息','请输入体检编号！');
		}
		$("#titleText").empty();
		$("#titleText").append(quest_sub_name);
		
		$("#questionTitle").empty();
		var iframe='<iframe scrolling="auto" width="100%" align="center" onload="Javascript:SetWinHeightinn(this)" frameborder="0"  src="scale_show.action?scale_code='+quest_sub_code+'&exam_num='+$("#exam_num").val()+'"></iframe>';
		$("#questionTitle").append(iframe);
		$("#questionTitle").append("<br><br>");
	} else {
		showMenuTitle(0,quest_sub_name,quest_sub_code);
	}
}

//查询此部分所有信息
function getQuestAllList(id, quest_sub_code){
	$("#questionTitle").empty();
	$.ajax({
   		url:'getQuestAllList.action',
   		type:'post',
   		data: {
   			titleID: id,
   			itemlevel:0,
   			quest_sub_code:quest_sub_code
   		},
   		async: false,//false为同步方式,true为异步方式
   		success:function(data){
   			var daMsg = eval("("+data+")"); //先转字符串
   			var divTitle = "";
   			//var arrHiddenId = new Array();
   			for(i=0; i < daMsg.length; i++){
				var div = "<div id='quest_"+daMsg[i].itemId+"' class='easyui-panel' title='"+daMsg[i].itemCode+"、"+daMsg[i].itemName+"' style='width:97%;padding:10px;'></div>";
				divTitle += div+"<br/>";
				/*if(daMsg[i].isVisable==0){
					arrHiddenId.push(daMsg[i].itemId);
				}*/
   			}
   			$("#questionTitle").append(divTitle);
   			
			$.parser.parse($("#questionTitle")); //渲染
			//拼接答案
			getQuestAllAnswer(id, quest_sub_code);
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
}

//查询此部分所有答案
function getQuestAllAnswer(id, quest_sub_code){
	$.ajax({
   		url:'getQuestAllList_status.action',
   		type:'post',
   		data: {
   			titleID: id,
   			itemlevel:1,
   			peId:$("#exam_num").val(),
   			quest_sub_code:quest_sub_code,
   		},
   		success:function(data){
   			var daMsg = eval("("+data+")"); //先转字符串
			for(var j=0;j<daMsg.length;j++){
				var divAnser = "";
				if(daMsg[j].isMulSel==0){//单选
	   				var lableSpan = "";
	   				var value = daMsg[j].itemtextUnit;
	   				var ansInp = daMsg[j].itemName.replace(/_____/g, "<input disabled style='color:black;width:100px; background:none;border:none;border-bottom:1px solid #ddd; font-size:18px; text-align:center;' type='text' value='"+value+"'/>");
	   				var radio;
	   				if(daMsg[j].selected ==1) {
	   					radio = "<input type='radio' checked/> &nbsp;"
	   				if(daMsg[j].linkNo =='无'){
	   						//不连接
	   						lableSpan =ansInp;
	   					}else{
	   						//连接
	   						lableSpan =ansInp+
	   								" &nbsp;&nbsp;------------------------" +
	   								"<span style='font-size:12px;'>连接跳转</span>----------------->>  " +
	   								"<a href='javascript:linkToQuestion(&#39;"+daMsg[j].linksubItem+"&#39;)' style='color:red;'>&nbsp;&nbsp;"+daMsg[j].linkNo+"&nbsp;&nbsp;</a>";
	   					}
	   				} else {
	   					radio = "<input type='radio' disabled/> &nbsp;"
	   					lableSpan ='<span style="color:gray">' + ansInp + '</span>';
	   				}
	   				divAnser = "<p style='font-size:16px;margin:15px 15px 15px 35px;'>" + radio + lableSpan + "</p>";
				}else if(daMsg[j].isMulSel==1){ //多选
					
				}else if(daMsg[j].isMulSel==2){ //文本
					var value = daMsg[j].itemtextUnit;
					var ansInp = daMsg[j].itemName.replace(/_____/g, "<input disabled style='color:black;width:100px; background:none;border:none;border-bottom:1px solid #ddd; font-size:18px; text-align:center;' type='text' value='"+value+"'/>");
					divAnser = "<p style='font-size:16px;margin:15px 15px 15px 35px;'>" +ansInp + "</p>";
				} else if(daMsg[j].isMulSel==3){
					var value = daMsg[j].itemtextUnit;
					var ansInp = daMsg[j].itemName.replace(/_____/g,"<textarea disabled id='text_"+daMsg[j].itemId+"' style='color:black;height: 100px; width: 98%; border-radius:5px;'>"+value+"</textarea>");
					divAnser = "<p>" +ansInp+"</p>";
				}
				$("#quest_"+daMsg[j].supItemId).append(divAnser);
   			}
   			$.parser.parse($("#questionTitle")); //渲染
   			
   			/*for(var index in arrHiddenId){
   				//alert(arrHiddenId[index]);
   				$("#quest_"+arrHiddenId[index]).parent().css("display","none");
   			}*/
   			
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
}

//链接跳转下一题
function linkToQuestion(id){
	var mao = $("#quest_"+id); //获得锚点   
	if (mao.length > 0) {//判断对象是否存在   
	     var pos = mao.offset().top;  
	     var poshigh = mao.height();  
	     $('#center').animate({ scrollTop: pos-poshigh-30 }, 800);  
	 } 
	
}

function SetWinHeightinn(iframe) {
	if (iframe) {
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		if (iframeWin.document.body) {
			iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
		}
	}
} 