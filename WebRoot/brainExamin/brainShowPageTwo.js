function chaxun(){
	if(!$("#exam_num").val()) {
		return;
	}
	
	queryTitleCont();
	
	$("#xiaLa").empty();
	$("#questionTitle").empty();
	$("#zhengZhaungTitle").empty();
	getcustomerInfo(function() {
		//查询是否付费 答题
		queryIsPayCharing();
	   
	});
	
	xiaLaSelectZhengZhuang('');
	//聚焦展开
	 $('#quest_zhengZhaung').combobox('textbox').bind('focus',function(){  
         $('#quest_zhengZhaung').combobox('showPanel');  
     });  
}

//查询title信息
function queryTitleCont(){
	$.ajax({
		url:'queryTitleCont.action',
		data:{
			quest_sub_code:$("#quest_sub_code").val()
			},
		type:'post',
		success:function(data){
			var obj=eval("("+data+")");
			$("#title_biao").empty();
			$('#title_biao').append('<a href="javaScript:getQuestAllList(0,0)">'+obj+'（全部）</a>');
			$("#titleText").empty();
			$('#titleText').append(obj+'调查表');
			$("#titleText2").empty();
			$('#titleText2').append('<a href="javaScript:getQuestAllList(0,0)">'+obj+'调查表</a>');
		}
	});
	
}

//查询是否付费 答题
function queryIsPayCharing(){
	
	$.ajax({
		url:'queryIsPayCharing.action',
		data:{
			exam_num:$("#exam_num").val(),
			quest_sub_code:$("#quest_sub_code").val()
			},
		type:'post',
		success:function(data){
			var obj=eval("("+data+")");
			if('true' == 'true'){
				//可以体检  不加载左侧树
				//getMeunListFirst();
				//加载所有问题
				getQuestAllList(0,0);
				//初始化 查询 是否已经答题了
				queryMegFromRecordTwo();
				//初始化开始答题
				insertIsdefaultAnswer();
			}else{
				if('查询失败'!=obj){
					//未交费 禁止查询
					$.messager.alert('提示信息','您未交费，请前去交费','error');
				}else{
					$.messager.alert('提示信息','后台查询异常','error');
				}
			}
		}
	});
	
}


//查询人员基本信息
function getcustomerInfo(callback){
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
			callback();
		}
	});
}

//隐藏首页
function hideIndex(){
	document.getElementById('index_text').style.display='none';
}

//下拉列表
function getMeunListFirst(){
	$.ajax({
   		url:'getMeunListFirst.action',
   		type:'post',
   		data: { 
   			Level: "0",
   			quest_sub_code:$("#quest_sub_code").val()
   		},
   		success:function(data){
   			var daMsg=eval("("+data+")"); //先转字符串
   			var divTitle = "<div id='accordionid' class='easyui-accordion' style='width:250px;'>";
   			var divCont = "";
   			var titleId = "";
   			for(var i=0;i<daMsg.length;i++){
   				var str = "";
   				if(daMsg[i].titleName.length>11){
   					str = daMsg[i].titleName.substring(0,10)+"..."
   				}else{str =daMsg[i].titleName}
   				var div= "<div id='title_"+daMsg[i].titleID+"' title='"+str+" <a onClick=\"javaScript:showMenuTitle(&#39;"+daMsg[i].titleID+"&#39;,&#39;"+daMsg[i].titleName+"&#39;,&#39;"+daMsg[i].titleColumn+"&#39;)\" style=\"color:#1C86EE; font-size:12px; float:right;\">（显示本部分）</a> '></div>";
   				divCont += div;
   				titleId += daMsg[i].titleID+"*";
   			}
   			var divThree = "</div>";
   			$("#xiaLa").append(divTitle+divCont+divThree);
   			$.parser.parse($("#xiaLa")); //渲染
   			getMeunListTwo(titleId);
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载菜单失败！','error');
   		}
   	});
}

//加载一级菜单
function getMeunListTwo(id){
	$.ajax({
   		url:'getMeunListFirst.action',
   		type:'post',
   		data: { 
   			Level: "1",
   			quest_sub_code:$("#quest_sub_code").val()
   		},
   		success:function(data){
   			var daMsg=eval("("+data+")"); //先转字符串
   			var divOne = "<ul class='ulOne'>";
   			var divThr = "</ul>";
   			var list = "";
   			//截取id放数组中
   			var arr = new Array();
   			arr = id.split("*");
   			for(i=0; i < arr.length; i++){
   				if(arr[i]!="" &&arr[i]!=0){
   					for(var j=0;j<daMsg.length;j++){
   	   	   				if(arr[i]==daMsg[j].supID){
	   	   	   			var str = "";
	   	   				if(daMsg[j].titleName.length>16){
	   	   					str = daMsg[j].titleName.substring(0,16)+"...."
	   	   				}else{str =daMsg[j].titleName}
   	   	   					var div = "<a href='javascript:showMenuList(\""+daMsg[j].titleID+"\",\""+daMsg[j].titleName+"\",\""+daMsg[j].titleColumn+"\")'><li><span id='twoMenuList'>"+str+"</span></li></a>";
   	   	   					list += div;
   	   	   				}
   	   	   			}
   				}
   				$("#title_"+arr[i]).append(divOne+list+divThr);
   				list = "";
   			}
   			$.parser.parse($("#xiaLa")); //渲染
   			
   			$("#accordionid").accordion('getSelected').panel('collapse')
   			
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载菜单失败！','error');
   		}
   	});
}
//显示 title全部信息
function showMenuTitle(id,name,column){
	$("#titleText").empty();
	$("#titleText").append(name);
	getQuestAllList(id,column);
	
}

//展示问题信息
function showMenuList(id,name,column){
	$("#titleText").empty();
	$("#titleText").append(name);
	//根据id查询问题
	getQuestionList(id,column);
	
	hideIndex();
}

//查询分布问题标题
function getQuestionList(id,column){
	$("#questionTitle").empty();
	$.ajax({
   		url:'getQuestionList.action',
   		type:'post',
   		data: {
   			titleID: id,
   			itemlevel:0,sex:$("#sex").html(),
   			quest_sub_code:$("#quest_sub_code").val()
   			},
   		success:function(data){
   			
   			var daMsg = eval("("+data+")"); //先转字符串
   			var divTitle = "";
   			var title = "";
   			var arrHiddenId = new Array();
   			for(i=0; i < daMsg.length; i++){
   				title = 
   						"<p><span style=' letter-spacing:2px; color:#1676A7; margin-left:10px; font-size: 20px;line-height:40px'>"+daMsg[i].titleName+"</span></p>";
   				var div = "<div id='quest_"+daMsg[i].itemId+"' class='easyui-panel' title='"+daMsg[i].itemCode+"、"+daMsg[i].itemName+"' style='width:"+97/column+"%;padding:10px;'></div>";
				divTitle += div;
				if(daMsg[i].isVisable==0){
					arrHiddenId.push(daMsg[i].itemId);
				}
   			}
   			$("#questionTitle").append(divTitle);
   			//拼接答案
			$.parser.parse($("#questionTitle")); //渲染
			
			getQuestionAnswer(id,arrHiddenId);
			
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
}

//分布问题答案  
function getQuestionAnswer(id,arrHiddenId){
	$.ajax({
   		url:'getQuestionList.action',
   		type:'post',
   		data: {
   			titleID: id,
   			itemlevel:1,
   			quest_sub_code:$("#quest_sub_code").val()
   		},
   		success:function(data){
   			var daMsg = eval("("+data+")"); //先转字符串
   			var arr = new Array();
   			var divAnser = "";
   					
			for(var j=0;j<daMsg.length;j++){
   					if(daMsg[j].isMulSel==0){ //单选
   						var ansInp = daMsg[j].itemName.replace(/_____/g, "<input id='text_"+daMsg[j].itemId+"' value='"+daMsg[j].defaultResult+"' onblur='mouseBlur(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' style='width:100px; background:none;border:none;border-bottom:1px solid #ddd; font-size:18px; text-align:center;' type='text' value=''/>");
   						var lableSpan = "";
   	   					if(daMsg[j].linkNo =='无' || daMsg[j].linkNo =="" || daMsg[j].linkNo ==null){
   	   						//不连接
   	   						lableSpan ="<label for='rd_ans_"+daMsg[j].itemId+"'>"+ansInp+"</label>";
   	   					}else{
   	   						//连接
   	   						lableSpan ="<label for='rd_ans_"+daMsg[j].itemId+"'>"+ansInp+"</label>  "+
   	   								" &nbsp;&nbsp;------------------------" +
   	   								"<span style='font-size:12px;'>连接跳转</span>----------------->>  " +
   	   								"<a style='color:red;'>&nbsp;&nbsp;"+daMsg[j].linkNo+"&nbsp;&nbsp;</a>";
   	   					}
   	   					if(daMsg[j].isDefault==1){
   	   						//默认值
	   	   					var div1 = 
								"<p style='font-size:16px;margin:15px 15px 15px 16px;'>" +
								"<input checked onClick='clickSaveData(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' id='rd_ans_"+daMsg[j].itemId+"' type='radio' name='radio_"+daMsg[j].supItemId+"' value='1'/> &nbsp;" +
								lableSpan+
								"</p>";
   	   					}else{
	   	   					var div1 = 
								"<p style='font-size:16px;margin:15px 15px 15px 16px;'>" +
								"<input onClick='clickSaveData(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' id='rd_ans_"+daMsg[j].itemId+"' type='radio' name='radio_"+daMsg[j].supItemId+"' value='1'/> &nbsp;" +
								lableSpan+
								"</p>";
   	   					}
   	   	   				
   	   	   				divAnser += div1;
   					}else if(daMsg[j].isMulSel==1){ //多选
   						
   						var lableSpan = "";
	   	   					if(daMsg[j].linkNo =='无' || daMsg[j].linkNo =="" || daMsg[j].linkNo ==null){
	   						//不连接
   	   						lableSpan ="<label for='rd_ans_"+daMsg[j].itemId+"'>"+ansInp2+"</label>";
   	   					}else{
   	   						//连接
   	   						lableSpan ="<label for='rd_ans_"+daMsg[j].itemId+"'>"+ansInp2+"</label>  " +
   	   								" &nbsp;&nbsp;------------------------" +
   	   								"<span style='font-size:12px;'>连接跳转</span>----------------->>  " +
   	   								"<a href='javascript:linkToQuestion(&#39;"+daMsg[j].linksubItem+"&#39;)' style='color:red;'>&nbsp;&nbsp;"+daMsg[j].linkNo+"&nbsp;&nbsp;</a>";
   	   					}
   	   					
   	   				if(daMsg[j].isDefault==1){
	   						//默认值
	   	   				var div1 = 
							"<p style='font-size:14px;margin:5px;'>" +
							"<input checked onClick='clickSaveData(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' id='rd_ans_"+daMsg[j].itemId+"' type='checkbox' name='checkbox_"+daMsg[j].itemId+"' value='1'/> &nbsp;" +
							lableSpan +
							"</p>";
	   					}else{
   	   					var div1 = 
							"<p style='font-size:14px;margin:5px;'>" +
							"<input onClick='clickSaveData(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' id='rd_ans_"+daMsg[j].itemId+"' type='checkbox' name='checkbox_"+daMsg[j].itemId+"' value='1'/> &nbsp;" +
							lableSpan +
							"</p>";
	   					}
	   	   				
	   	   				divAnser += div1;
   						
   					}else if(daMsg[j].isMulSel==2){ //文本单位
   						var ansInp = daMsg[j].itemName.replace(/_____/g, "<input id='text_"+daMsg[j].itemId+"' value='"+daMsg[j].defaultResult+"' onblur='mouseBlur(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' style='width:100px; background:none;border:none;border-bottom:1px solid #ddd; font-size:18px; text-align:center;' type='text' value=''/>");
   						var div3 = 
							"<p style='font-size:16px;margin:15px 15px 15px 16px;'>" +
								 ""+ansInp+""+daMsg[j].textUnit+
							"</p>";
   						divAnser += div3;
   					}else if(daMsg[j].isMulSel==3){
   						var ansInp = daMsg[j].itemName.replace(/_____/g,"<textarea id='text_"+daMsg[j].itemId+"' onblur='mouseBlur(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' style='height: 100px; width: 98%; border-radius:5px;'></textarea>");
   						var div4 =  
   							"<p>" +ansInp+"</p>";
   						divAnser += div4;
   					}
					
   					$("#quest_"+daMsg[j].supItemId).append(divAnser);
   					divAnser = "";
   			}
   			
   			$.parser.parse($("#questionTitle")); //渲染
   			
   			for(var index in arrHiddenId){
   				//alert(arrHiddenId[index]);
   				$("#quest_"+arrHiddenId[index]).parent().css("display","none");
   			}
   			
   			if($("#start_quest").val()!=""){
   				QueryResultMsg();
   			}
   			
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
}

//查询此部分所有信息
function getQuestAllList(id,column){
	if(id==0){
		$("#titleText").empty();
		$("#titleText").append("全部问卷");
	}
	hideIndex();
	$("#questionTitle").empty();
	$.ajax({
   		url:'getQuestAllList.action',
   		type:'post',
   		data: {
   			titleID: id,
   			itemlevel:0,
   			sex:$("#sex").html(),
   			quest_sub_code:$("#quest_sub_code").val()
   		},
   		success:function(data){
   			var daMsg = eval("("+data+")"); //先转字符串
   			var divTitle = "";
   			var title = "";
   			var isBr = "";
   			var arrHiddenId = new Array();
   			for(i=0; i < daMsg.length; i++){
   				if(daMsg[i].titleColumn!=0){
   					column = daMsg[i].titleColumn;
   				}
   				if($('#set_num').val()!=0 && $('#set_num').val()==daMsg[i].titleID){
   					//不换行
   					isBr = "";
   				}else{
   					//换行
   					isBr = "<div class='easyui-panel'  style='width:97%;border:0px;' ></div>";
   				}
   				$('#set_num').val(daMsg[i].titleID);
   				title = 
						"<p><span style='color:#1676A7; margin-left:10px; font-size: 16px;line-height:20px;'>"+daMsg[i].titleName+"</span></p>";
				
				var div = "<div id='quest_"+daMsg[i].itemId+"' class='easyui-panel' title='"+daMsg[i].itemCode+"、"+daMsg[i].itemName+"' style='width:"+97/column+"%; font-size:16px;'></div>";
				divTitle += isBr+div;
				//alert(divTitle);
				if(daMsg[i].isVisable==0){
					arrHiddenId.push(daMsg[i].itemId);
				}
				//问题标题拼接
				
   			}
   			$("#questionTitle").append(divTitle);
   			//拼接答案
			$.parser.parse($("#questionTitle")); //渲染
			
			getQuestAllAnswer(id,arrHiddenId);
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
}

//查询此部分所有答案
function getQuestAllAnswer(id,arrHiddenId){
	$.ajax({
   		url:'getQuestAllList.action',
   		type:'post',
   		data: {
   			titleID: id,
   			itemlevel:1 ,
   			quest_sub_code:$("#quest_sub_code").val()
   		},
   		success:function(data){
   			var daMsg = eval("("+data+")"); //先转字符串
   			var arr = new Array();
   			var divAnser = "";
			for(var j=0;j<daMsg.length;j++){
					var result = daMsg[j].itemName.match(new RegExp("_", 'g'));
  	   				var count = !result ? 0 : result.length;
  	   				var px = 100+(count-5)*50;
	  	   			var ansInp = daMsg[j].itemName.replace(/_____/g, "<input id='text_"+daMsg[j].itemId+"' value='"+daMsg[j].defaultResult+"' onblur='mouseBlur(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' style='width:"+px+"px; background:none;border:none;border-bottom:1px solid #ddd; font-size:14px; text-align:center;' type='text' value=''/>");
	  	   			var ansInp2 = "";
	  	   			if(count>5){
						var a = ansInp.split("/>")[0];
						var b=ansInp.split("/>")[1].replace(/_/g,'');
						ansInp2 =  a+b;
					}else{
						ansInp2 = ansInp;
					}
   					if(daMsg[j].isMulSel==0){ //单选
   	   	   					var lableSpan = "";
   	   	   					if(daMsg[j].linkNo =='无' || daMsg[j].linkNo =="" || daMsg[j].linkNo ==null){
   	   						//不连接
	   	   						lableSpan ="<label for='rd_ans_"+daMsg[j].itemId+"'>"+ansInp2+"</label>";
	   	   					}else{
	   	   						//连接
	   	   						lableSpan ="<label for='rd_ans_"+daMsg[j].itemId+"'>"+ansInp2+"</label>  " +
	   	   								" &nbsp;&nbsp;------------------------" +
	   	   								"<span style='font-size:12px;'>连接跳转</span>----------------->>  " +
	   	   								"<a href='javascript:linkToQuestion(&#39;"+daMsg[j].linksubItem+"&#39;)' style='color:red;'>&nbsp;&nbsp;"+daMsg[j].linkNo+"&nbsp;&nbsp;</a>";
	   	   					}
	   	   					
	   	   				if(daMsg[j].isDefault==1){
   	   						//默认值
		   	   				var div1 = 
								"<p style='font-size:14px;margin:5px;'>" +
								"<input checked onClick='clickSaveData(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' id='rd_ans_"+daMsg[j].itemId+"' type='radio' name='radio_"+daMsg[j].supItemId+"' value='1'/> &nbsp;" +
								lableSpan +
								"</p>";
   	   					}else{
	   	   					var div1 = 
								"<p style='font-size:14px;margin:5px;'>" +
								"<input onClick='clickSaveData(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' id='rd_ans_"+daMsg[j].itemId+"' type='radio' name='radio_"+daMsg[j].supItemId+"' value='1'/> &nbsp;" +
								lableSpan +
								"</p>";
   	   					}
   	   	   				
   	   	   				divAnser += div1;
   					}else if(daMsg[j].isMulSel==1){ //多选
   						
   						var lableSpan = "";
	   	   					if(daMsg[j].linkNo =='无' || daMsg[j].linkNo =="" || daMsg[j].linkNo ==null){
	   						//不连接
   	   						lableSpan ="<label for='rd_ans_"+daMsg[j].itemId+"'>"+ansInp2+"</label>";
   	   					}else{
   	   						//连接
   	   						lableSpan ="<label for='rd_ans_"+daMsg[j].itemId+"'>"+ansInp2+"</label>  " +
   	   								" &nbsp;&nbsp;------------------------" +
   	   								"<span style='font-size:12px;'>连接跳转</span>----------------->>  " +
   	   								"<a href='javascript:linkToQuestion(&#39;"+daMsg[j].linksubItem+"&#39;)' style='color:red;'>&nbsp;&nbsp;"+daMsg[j].linkNo+"&nbsp;&nbsp;</a>";
   	   					}
   	   					
   	   				if(daMsg[j].isDefault==1){
	   						//默认值
	   	   				var div1 = 
							"<p style='font-size:14px;margin:5px;'>" +
							"<input checked onClick='clickSaveData(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' id='rd_ans_"+daMsg[j].itemId+"' type='checkbox' name='checkbox_"+daMsg[j].itemId+"' value='1'/> &nbsp;" +
							lableSpan +
							"</p>";
	   					}else{
   	   					var div1 = 
							"<p style='font-size:14px;margin:5px;'>" +
							"<input onClick='clickSaveData(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' id='rd_ans_"+daMsg[j].itemId+"' type='checkbox' name='checkbox_"+daMsg[j].itemId+"' value='1'/> &nbsp;" +
							lableSpan +
							"</p>";
	   					}
	   	   				
	   	   				divAnser += div1;
   						
   					}else if(daMsg[j].isMulSel==2){ //文本
   						var div3 = 
						"<p style='font-size:14px;margin:5px;'>" +
							 ""+ansInp2+""+daMsg[j].textUnit+
						"</p>";
   						divAnser += div3;
   						
   					}else if(daMsg[j].isMulSel==3){
   						var ansInpText = daMsg[j].itemName.replace(/_____/g,"<textarea id='text_"+daMsg[j].itemId+"' onblur='mouseBlur(&#39;"+daMsg[j].itemId+"&#39;,&#39;"+daMsg[j].supItemId+"&#39;)' style='height: 100px; width: 98%; border-radius:5px;'></textarea>");
   						var div4 =  
   							"<p>" +ansInp+"</p>";
   						divAnser += div4;
   					}
					
   				$("#quest_"+daMsg[j].supItemId).append(divAnser);
   				divAnser = "";
   			}
   			
   			$.parser.parse($("#questionTitle")); //渲染
   			
   			for(var index in arrHiddenId){
   				//alert(arrHiddenId[index]);
   				$("#quest_"+arrHiddenId[index]).parent().css("display","none");
   			}
   			
   			//加载 答案
   			if($("#start_quest").val()!=""){
   				QueryResultMsg();
   				
   			}
   			//回显示症状
			resultWenZhengMsg();
				
   			$('#end_msg').show();
   			$('#bs_zhengZhuang').show();
   			
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
}


//开始答题在record表中插入记录
function startRecordQuest(id,supId){
	$.ajax({
		url:'saveRecordMsg.action',
		type:'post',
		data: {
			peId:$("#exam_num").val(),
			quest_sub_code:$("#quest_sub_code").val()
		},
		success:function(data){
			var message=eval("("+data+")");
			$("#start_quest").val(message);
			clickSaveData(id,supId);
			//$.messager.alert('提示信息',"您已经开始答题！");
			//执行插入默认值信息
		},
		error:function(){
			$.messager.alert('提示信息','加载问题失败！','error');
		}
	});
}

//执行插入默认值信息 有默认答案
function insertIsdefaultAnswer(){
	$.ajax({
   		url:'insertIsdefaultAnswer.action',
   		type:'post',
   		data: {
   			recId:$("#start_quest").val(),
   			peId:$("#exam_num").val(),
   			quest_sub_code:$("#quest_sub_code").val()
   		},
   		success:function(data){
   			var message=eval("("+data+")");
   			$("#start_quest").val(message);
   			//$.messager.alert('提示信息',message);
   			QueryResultMsg();
   		},
   		error:function(){
   			$.messager.alert('提示信息','初始化信息失败','error');
   		}
   	});
}

//选中 保存信息
function clickSaveData(id,supId){
	//alert($("#rd_ans_"+id).prop("checked"));
	if($("#start_quest").val()!="" ){
		//根据id判断是否有信息
		var testIn = $("#text_"+id).val();
		$.ajax({
	   		url:'clickSaveData.action',
	   		type:'post',
	   		data: {
	   			itemId: id,
	   			recId:$("#start_quest").val(),
	   			peId:$("#exam_num").val(),
	   			textUnit:testIn,
	   			quest_sub_code:$("#quest_sub_code").val(),
	   			isMuslCheck:$("#rd_ans_"+id).prop("checked")
	   		},
	   		success:function(data){
	   			var message=eval("("+data+")");
	   			
	   			var arr = new Array();
     			arr = message.substr(2, message.length).split(",");
     			//alert(message.substr(0, 1));
	     		if(message.substr(0, 1)==1){
	     			//显示隐藏内容
	     			for(var inde in arr){
	     				$("#quest_"+arr[inde]).parent().css("display","block");
	     			}
	     		}else if(message.substr(0, 1)==0){
	     			//隐藏
	     			/*for(var inde in arr){
	     				$("#quest_"+arr[inde]).parent().css("display","none");
	     			}*/
	     		}
	   		},
	   		error:function(){
	   			$.messager.alert('提示信息','加载问题失败！','error');
	   		}
	   	});
		
	}else{
		//为空时候 先插入record表
		startRecordQuest(id,supId);
	}
	
}


//回显示信息
function QueryResultMsg(){
	$.ajax({
   		url:'QueryResultMsg.action',
   		type:'post',
   		data: {
   			recordId: $("#start_quest").val(),
   			peId:$("#exam_num").val()
   		},
   		success:function(data){
   			var reData=eval("("+data+")"); //先转字符串
   			//$.messager.alert('提示信息',"检测您已答题");
   			//回显数据
   			for(var i=0;i<reData.length;i++){
   				//alert(reData[i].questResult_itemID);
   				var isId = reData[i].questResult_itemID;
   				if(isId!="" || isId!=null){
   					//alert(isId);
   					var isChek = document.getElementById('rd_ans_'+isId);
   					if(isChek!=null) isChek.checked = true;
   					$("#quest_"+reData[i].resultId_itemID).parent().css("display","block");
   				}
   				var inputText = reData[i].questResult;
   				if(inputText!="" || inputText!=null){
   					$("#text_"+reData[i].questResult_itemID).val(inputText);
   					$("#quest_"+reData[i].resultId_itemID).parent().css("display","block");
   				}
   				
   				
   			}
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
}


//链接跳转下一题
function linkToQuestion(id){
	$("#quest_"+id).parent().css("color","red");
	if($("#red_id").val()!=0 && $("#red_id").val()!=""){
		$("#quest_"+$("#red_id").val()).parent().css("color","#000");
	}
	
	setTimeout("$('#red_id').val("+id+");",2000);
	var maps = $("#end_msg").offset().top;
	var mao = $("#quest_"+id); //获得锚点   
	//alert(maps);
	if (mao.length > 0) {//判断对象是否存在   
	     var pos = mao.offset().top;  
	     var poshigh = mao.height();  
	     $('#first_maoHeight').val(pos);
	     if(maps<689){
	    	 //标题不在顶部
	    	 $('#center').animate({ scrollTop: 0 }, 800);
	    	 setTimeout("linkToQuestion("+id+")",1000); 
	     }else{
	    	//标题在顶部
	    	 $('#center').animate({ scrollTop:  pos-poshigh-150 }, 1000);
	     }
	    
	 } 
	//window.location.hash = "#quest_"+id;
	
}


function queryMegFromRecordTwo(){
	$.ajax({
		url:'queryMegFromRecord.action',
		type:'post',
		data: {
			peId:$("#exam_num").val(),
			quest_sub_code:$("#quest_sub_code").val()
		},
		success:function(data){
			var message=eval("("+data+")");
			if(message!="" && message!=null){
				//检测已经答题了
				$("#start_quest").val(message); //开始答题标志
			}else{
				//检测未开始答题
				$("#start_quest").val(""); //开始答题标志为空
			}
		},
		error:function(){
			$.messager.alert('提示信息','加载问题失败！','error');
		}
	});
}

//鼠标失去焦点  存入数据库
function mouseBlur(id,supId){
	//保存数据
	clickSaveData(id,supId);
	
}

//答题结束修改
function saveEndRecord(){
	//进入ajax提交
	$.ajax({
		url:'saveEndRecord.action',
		type:'post',
		data: {
			dep_id:$("#dep_id").val(),
			exam_num:$("#exam_num").val(),
			recordId: $("#start_quest").val(),
			quest_sub_code:$("#quest_sub_code").val()
		},
		success:function(data){
			var message=eval("("+data+")");
			//$.messager.alert('提示信息',message);
			close_page(); //关闭页面
			
		},
		error:function(){
			$.messager.alert('提示信息','加载问题失败！','error');
		}
	});
	
}
//查询定位
function queryQuestNameFixed(){
	if($("#quest_name").val().trim()==''){
		$.messager.alert('提示信息','输入为空无法查询定位');
		return;
	}
	$.ajax({
		url:'queryQuestNameFixed.action',
		type:'post',
		data: {
			itemName: $("#quest_name").val().trim(),
			quest_sub_code:$("#quest_sub_code").val()
		},
		success:function(data){
			var message=eval("("+data+")");
			if('false'==message){
				$.messager.alert('提示信息','查无此标题','error');
			}else{
				linkToQuestion(message);
			}
		},
		error:function(){
			$.messager.alert('提示信息','查询问题失败！','error');
		}
	});
	
}
function queryQuestNameEmpty(){
	$("#quest_name").textbox('setValue','')
}

//下拉查询
function xiaLaSelectZhengZhuang(par){
	$('#quest_zhengZhaung').combobox({
		url:'queryLikeTitleName.action?itemlevel=0&sex='+encodeURI($("#sex").html())+'&itemName='+encodeURI(par)+'&quest_sub_code='+$("#quest_sub_code").val(),
	    valueField:'itemId',    
	    textField:'itemName',
	    //value:par,
		filter: function(q, row){
			var opts = $(this).combobox('options');
			var text = row[opts.textField];//下拉的对应选项的汉字
			var pyjp = pinyinUtil.getFirstLetter(text).toLowerCase();
	 		if(row[opts.textField].indexOf(q) > -1 || pyjp.indexOf(q.toLowerCase()) > -1){
	 			return true;
	 		}	
		},
	    onSelect:function(){
	    	if($('#quest_zhengZhaung').combobox('getValue')!=""){
	    		//$("#item_id").val($('#quest_zhengZhaung').combobox('getValue'))
	    		addZhengZhuang($('#quest_zhengZhaung').combobox('getValue'));
	    	}
	    }
	
	  }) 
}

//添加值
function addZhengZhuang(itemId){
	if(itemId!=0 && itemId!=""){
		if($('#addZheng_'+itemId).val()==""){
			$.messager.alert('提示信息','您已添加该项目','error');
			//清空输入框
			$('#quest_zhengZhaung').combobox('setValue', '');
			return;
		}
	}
	var a1 =  $('#quest_zhengZhaung').combobox('getValue');
	var a2 = $('#quest_zhengZhaung').combobox('getText');
	if(undefined!=a1 && a2!=''){ 
		$.ajax({
			url:'addZhengZhuangById.action',
			type:'post',
			data: {
				itemId: itemId,
			},
			success:function(data){
				var daMsg=eval("("+data+")");
				var divTitle = "";
	   			var title = "";
	   			for(i=0; i < daMsg.length; i++){
	   				if(daMsg[i].itemlevel==0){
	   					var div = 
	   							"<div id='addZheng_"+daMsg[i].itemId+"' class='easyui-panel' title='"+daMsg[i].itemCode+"、"+daMsg[i].itemName+"<a style=&#39;float:right;&#39; href=&#39;javaScript:deleteAddZheng("+daMsg[i].itemId+")&#39;>删除</a>'  style='width:"+97/daMsg[i].titleColumn+"%;'>" +
	   							"</div>";
	   					$("#zhengZhaungTitle").append(div);
	   				}
	   				//修改数据库内容
	   				addZhengZhaungUpdate(daMsg[i].itemId);
	   			} //for循环
	   			//拼接答案
				$.parser.parse($("#zhengZhaungTitle")); //渲染
				//清空输入框
				$('#quest_zhengZhaung').combobox('setValue', '');
			},
			error:function(){
				$.messager.alert('提示信息','加载问题失败！','error');
			}
		});
	}else{
		$.messager.alert('提示信息','请选择项目','error') 
		return;
	}
}

function deleteAddZheng(id){
	//删除  移除页面   修改数据库
	$.ajax({
   		url:'addZhengZhaungUpdate.action',
   		type:'post',
   		data: {
			supItemId: id,
   			textUnit:"-",
   			peId:$("#exam_num").val()
   		},
   		success:function(data){
   			//移除界面内容
   			var daMsg=eval("("+data+")");
   			if(daMsg =="true"){
   				$("#addZheng_"+id).parent().remove();
   			}
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
}


//选中 保存信息
function addZhengZhaungUpdate(id){
	$.ajax({
   		url:'addZhengZhaungUpdate.action',
   		type:'post',
   		data: {
   			supItemId: id,
   			textUnit:"+",
   			peId:$("#exam_num").val()
   		},
   		success:function(data){
   			var daMsg=eval("("+data+")");
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
	
}

//关闭页面
function close_page(){
	var _parentWin =  window.opener;
	var userAgent = navigator.userAgent;
	window.opener = null;
	window.open('', '_self');
	window.close();
	_parentWin.brushpagecharging();
}

//问诊所有信息回显
function resultWenZhengMsg(){
	$.ajax({
   		url:'resultWenZhengMsg.action',
   		type:'post',
   		data: {
   			peId:$("#exam_num").val()
   		},
   		success:function(data){
   			var daMsg=eval("("+data+")");
			var divTitle = "";
   			for(i=0; i < daMsg.length; i++){
   				if(daMsg[i].itemlevel==0){
   					var div = 
   							"<div id='addZheng_"+daMsg[i].itemId+"' class='easyui-panel' title='"+daMsg[i].itemCode+"、"+daMsg[i].itemName+"<a style=&#39;float:right;&#39; href=&#39;javaScript:deleteAddZheng("+daMsg[i].itemId+")&#39;>删除</a>'  style='width:"+97/daMsg[i].titleColumn+"%;'>" +
   							"</div>";
   					divTitle += div;
   				}
   			} //for循环
   			$("#zhengZhaungTitle").append(divTitle);
			$.parser.parse($("#zhengZhaungTitle")); //渲染
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
}