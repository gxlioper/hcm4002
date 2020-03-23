function wentiList(){
	var model={
			customer_id:$('#c_id').val(),
			dep_homepage_show:'1',
			exam_num_x:$('#exam_num_x_x').val()
	}
	$.ajax({
		url:'getQuestionnaireSurveyPageYMShou.action',
		data:model,
		success:function(data){
			var obj =  eval('(' + data + ')');
			var wenti = obj.sysSurveyQuestionList//问题
			var xx = obj.sysQuestionOptionsList;//选项
			var yh = obj.surverList;//已选择选项
			//问题
			var w_str="";
			var x_str="";
			var y_str="";
			var pd = "1";
			var s_str="1";
			w_str="";
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
						
						w_str+= "<li  style='display:block;height:80px;line-height:40px;margin-left:0px;'><input type='hidden'  value='"+wenti[i].objectId+"'  data-xx='单选'    data-name='danxuan"+i+"' /><h4>"+wenti[i].code+":"+wenti[i].name+"</h4>"
						if( xx!="" && xx!=undefined ){
							w_str+="<p  style='margin-left: 20px;'>";
							for(var j=0; j<xx.length; j++){
								//问题关联选项
								if(wenti[i].objectId==xx[j].quest_id){
									
									//---已选选项//单选-----
									if( y_str!=""&&y_str==xx[j].code){
										s_str="2";
										w_str+="<span style='margin-right: 50px;' >"+xx[j].content+"</span>";
									}else{
										//w_str+="<span style='margin-right: 50px;' ><input name='danxuan"+i+"'  data-zhi='"+xx[j].code+"'   value='"+xx[j].objectId+"' type='radio' value=''/>"+xx[j].content+"</span>";
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
						w_str+= "<li  style='display:block;height:80px;line-height:40px;margin-left:0px;'><input type='hidden'  value='"+wenti[i].objectId+"'  data-xx='多选'	 data-name='duoxuan"+i+"'	 /><h4>"+wenti[i].code+":"+wenti[i].name+"</h4>"
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
													  w_str+="<span style='margin-right: 50px;'  >"+xx[j].content+"</span>";
													  s_str="2";
													  x_pdl="2";
													  break;
												  }
											}
									}
									if(x_pdl=="1"){
										//w_str+="<span style='margin-right: 50px;'  ><input name='duoxuan"+i+"'     data-zhi='"+xx[j].code+"' type='checkbox'  value='"+xx[j].objectId+"'			 />"+xx[j].content+"</span>";
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
							w_str+= "<li  style='display:block;line-height:40px;margin-left:0px;'><input type='hidden'  value='"+wenti[i].objectId+"'  data-xx='问答'			 /><h4>"+wenti[i].code+":"+wenti[i].name+"</h4>"
							if( xx!="" && xx!=undefined ){
								s_str="2";
								w_str+="<p   style='margin-left: 20px;'>"+y_str+"</p>";
							}
						}else{
							w_str+= "<li  style='display:block;height:80px;line-height:40px;margin-left:10px;'><input type='hidden'  value='"+wenti[i].objectId+"'  data-xx='问答'			 /><h4>"+wenti[i].code+":"+wenti[i].name+"</h4>"
							if( xx!="" && xx!=undefined ){
								w_str+="<p   style='margin-left: 20px;'><textarea id='wenda"+i+"'   style='width:500px;height:50px;resize: none;'></textarea></p>";
							}
						}
					}
					w_str+="</li>";
					if(s_str=="2"){
						$('#wj_neirong').append(w_str);
						w_str="";
						s_str="1";
					}else{
						w_str="";
					}
				}
				var r=""
				var su="";
				$('#wj_neirong').find('li').each(function(){
					r++;
					su="";
					su=r%2;
					if(su==0){
							$(this).css("background","#f7f7f7");
					}else{
						$(this).css("background","#eaeaea");
					}
				})
			}
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	})
}