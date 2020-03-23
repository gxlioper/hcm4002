$(document).ready(function () {
	wentiList();
});
function wentiList(){
	$.ajax({
		url:'getQuestionnaireSurveyPageYMShou.action',
		data:{customer_id:$('#s_id').val(),sex:$('#s_sex').text()},
		success:function(data){
			//var obj = data.parseJSON()
			var obj =  eval('(' + data + ')');
			var wenti = obj.sysSurveyQuestionList//问题
			var xx = obj.sysQuestionOptionsList;//选项
			var yh = obj.surverList;//已选择选项
			console.log(yh);
			//问题
			var w_str="";
			var x_str="";
			var y_str="";
			var pd = "1";
			if( wenti!="" && wenti!=undefined ){
				for(var i=0; i<wenti.length; i++){
					y_str="";
				
					
					//单选
					if(wenti[i].answer_type=="1"){
						
						//--------------已选选项//单选//多选//问答----------
						for(var k = 0 ; k<yh.length; k++){
							if( yh[k].question_id==wenti[i].objectId){
								y_str = yh[k].code;
								break;
							}
						}
						
						w_str+= "<li  style='display:block;height:80px;line-height:40px;margin-left:10px;'><input type='hidden'  value='"+wenti[i].objectId+"'  data-xx='单选'    data-name='danxuan"+i+"' /><h4>"+wenti[i].code+":"+wenti[i].name+"</h4>"
						if( xx!="" && xx!=undefined ){
							w_str+="<p  style='margin-left: 20px;'>";
							for(var j=0; j<xx.length; j++){
								//问题关联选项
								if(wenti[i].objectId==xx[j].quest_id){
									
									//---已选选项//单选-----
									if(y_str==xx[j].code){
										w_str+="<span style='margin-right: 50px;' ><input name='danxuan"+i+"'  data-zhi='"+xx[j].code+"'   value='"+xx[j].objectId+"' type='radio'    checked='checked'    />"+xx[j].content+"</span>";
									}else{
										w_str+="<span style='margin-right: 50px;' ><input name='danxuan"+i+"'  data-zhi='"+xx[j].code+"'   value='"+xx[j].objectId+"' type='radio' />"+xx[j].content+"</span>";
									}
								}
							}
							w_str+="</p>";
						}
						
						
						
					//多选
					}else if(wenti[i].answer_type=="2"){
						
						//--------------已选选项//单选//多选//问答----------
					   for(var k = 0 ; k<yh.length; k++){
							if( yh[k].question_id==wenti[i].objectId){
								y_str = yh[k].code;
							}
						}
						w_str+= "<li  style='display:block;height:80px;line-height:40px;margin-left:10px;'><input type='hidden'  value='"+wenti[i].objectId+"'  data-xx='多选'	 data-name='duoxuan"+i+"'	 /><h4>"+wenti[i].code+":"+wenti[i].name+"</h4>"
						if( xx!="" && xx!=undefined ){
							w_str+="<p  style='margin-left: 20px;'>";
							for(var j=0; j<xx.length; j++){
								//问题关联选项
								
								if(wenti[i].objectId==xx[j].quest_id){
									
									var x_pdl="1";
									//----已选项//多选----------
									if( y_str!=""){
											var result=y_str.split(",");
											for(var c=0;c<result.length;c++){
												  if(result[c]==xx[j].code){
													  w_str+="<span style='margin-right: 50px;'  ><input name='duoxuan"+i+"'     data-zhi='"+xx[j].code+"' type='checkbox'  value='"+xx[j].objectId+"'	checked='checked'	 />"+xx[j].content+"</span>";
													  x_pdl="2";
													  break;
												  }
											}
									}
									if(x_pdl=="1"){
										w_str+="<span style='margin-right: 50px;'  ><input name='duoxuan"+i+"'     data-zhi='"+xx[j].code+"' type='checkbox'  value='"+xx[j].objectId+"'			 />"+xx[j].content+"</span>";
									}
								}
								
							}
							w_str+="</p>";
						}
						
						
						
					//问答
					}else{
						for(var k = 0 ; k<yh.length; k++){
							if( yh[k].question_id==wenti[i].objectId){
								y_str = yh[k].code;
								break;
							}
						}
						if(y_str!=""){
							w_str+= "<li  style='display:block;height:80px;line-height:40px;margin-left:10px;'><input type='hidden'  value='"+wenti[i].objectId+"'  data-xx='问答'			 /><h4>"+wenti[i].code+":"+wenti[i].name+"</h4>"
							if( xx!="" && xx!=undefined ){
								w_str+="<p   style='margin-left: 20px;'><textarea id='wenda"+i+"'   style='width:500px;height:50px;resize: none;'>"+y_str+"</textarea></p>";
							}
						}else{
							w_str+= "<li  style='display:block;height:80px;line-height:40px;margin-left:10px;'><input type='hidden'  value='"+wenti[i].objectId+"'  data-xx='问答'			 /><h4>"+wenti[i].code+":"+wenti[i].name+"</h4>"
							if( xx!="" && xx!=undefined ){
								w_str+="<p   style='margin-left: 20px;'><textarea id='wenda"+i+"'   style='width:500px;height:50px;resize: none;'></textarea></p>";
							}
						}
					}
					w_str+="</li>";
				}
				
				
				w_str="<ul  id='wenti_shou' style='font-size:16px;'>"+w_str+"</ul>";
				$('#wj_neirong').append(w_str);
			}
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	})
}
//-----------------------------------------------------------提交问卷----------------------------------------------------------
function savewenjuan(){
	var  wenti_id="";
	var  ddw="";
	var  w_name="";
	
	var question_id="";//保存问题id
	var code="";//选项code编码
	var  question_list=new Array();//存储问卷问题
	
	$('#wj_neirong  li').each(function(){
		
		  wenti_id = $(this).children().eq(0).val();//问题id
		  ddw = $(this).children().eq(0).data('xx');//单选/多选/问答
		  w_name = $(this).children().eq(0).data('name');//单选/多选/问答
		  
		  
		if(ddw=="单选"){
			if($("input[name='"+w_name+"']:checked").val()!=null){
				var dxk = $("input[name='"+w_name+"']:checked").data('zhi');//单选值
				question_list.push("{'question_id':'"+wenti_id+"','code':'"+dxk+"'}");   
			}
		}else if(ddw=="多选"){
			
			var fxk = document.getElementsByName(w_name);
			var d_xz = new Array();//复选值
			
		       for(var i = 0; i < fxk.length; i++){
			         if(fxk[i].checked){
			        	 d_xz.push($(fxk[i]).data('zhi'));
			         }
		        } 
		       var sz = d_xz.toString();
		   question_list.push("{'question_id':'"+wenti_id+"','code':'"+sz+"'}");   
		}else{
			var  ss = $(this).children().eq(2).children(0).val();//问答值
			question_list.push("{'question_id':'"+wenti_id+"','code':'"+ss+"'}");
		}
	})
	question_list="["+question_list.toString()+"]";
	var model={
			shujulist:question_list,
			user_id:$('#s_id').val(),
			person_name:$('#s_name').text().trim(),
			sex:$('#s_sex').text().trim(),
			age:$('#s_age').text().trim()
			};
	$.ajax({
		url:"saveQuestionnaireSurvey.action",
		data:model,
		type:'post',
		success:function(data){
			var _parentWin =  window.opener ; 
			_parentWin.qk();
			window.alert("提交成功！");
			window.close();
		},
		error:function(){
			$.messager.alert("警告信息","操作失败6","error");
		}
	})
}
