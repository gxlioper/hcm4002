﻿/**
 * 科室检查
 */
$(document).ready(function(){
	$("#exam_num").val($("#tjh").text());
	
	if($("#picture_path").val() != ''){
		document.getElementById("exampic").src="getdjtexamPhoto.action?others="+$("#picture_path").val()+"&"+new Date().getTime();
	}
	
	getexamIntion();
	compareHistory();
	
	if($('#exam_result_summary').val()!=''){
		$('#jielun').val($('#exam_result_summary').val())
	}else{
		$('#jielun').val($('#moren_exam_result').val());
	}
	$('#zhuanjiaji').val($('#suggestion').val());
	
	if($("#exam_type_code").val() != 'TJLXRZTJ'){
		// weijizhi();
        getExamCriticalDetail();
		yichangzhishu();
		getALL();
		
		$(".layout-panel-north .layout-button-up").click();
		var str = '全科会诊协作平台(单击查看)&nbsp;&nbsp;'
			+'<span style="background-color:#ff9900;">&nbsp;&nbsp;&nbsp;</span><span style="font-size:14px;">异常&nbsp;&nbsp;</span>'
			+'<span style="background-color:#ff0000;">&nbsp;&nbsp;&nbsp;</span><span style="font-size:14px;">危机&nbsp;&nbsp;</span>'
		$('.layout-expand-north .panel-title').html(str);
		$('.layout-expand-north .panel-tool').hide();
	}else{
		$('#div_dep').layout('remove','north');
	}
	
	if($("#is_departinspect_summary_edit").val() == 'N'){
		$('#jielun').attr("readonly",true);
	}
	
	wenjuanbuttoshow();
	
	get_dep_logic();
	shoubaogaopdfbuttion();
	
	if($("#isprint").val() == '0'){
		$("#keshicy").show();
	}
})
//---------------------------------动态获取检查项目------------------------------
var samp_item,is_index,sam_count,dep_logic;
function getexamIntion(){
	$.ajax({
		url:'getDepInspectExamIntion.action',
		type:'post',
		data:{'id':$('#id').val(),'sex':$('#sex').text(),'app_type':$("#app_type").val(),'exam_num':$("#exam_num").val()},
		success:function(data){
			samp_item = eval('('+data+')');
			if(samp_item.length > 0){
				sam_count = 0;
				create_item(samp_item[0].depItemList);
				var charing_str ='';
				var exam_item = 0;
				for(j=0;j<samp_item.length;j++){
					var color = "";
					if(samp_item[j].exam_status == 'N'){
						color = "color:#00a700;";
					}else{
						exam_item ++;
					}
					if(j==0){
						charing_str +='<li onclick="qiehuan_xiangmu(this,'+j+')" class="active" style="border-right:1px solid #dfdfdf;'+color+'">'+samp_item[j].c_name+'</li>';
					}else{
						charing_str +='<li onclick="qiehuan_xiangmu(this,'+j+')" style="border-right:1px solid #dfdfdf;'+color+'">'+samp_item[j].c_name+'</li>';
					}
				}
			
				if(samp_item.length > 1){
					if((samp_item.length-exam_item) > 1){
						is_index = false;
					}else{
						is_index = true;
					}
					$(".gene_head1").html(charing_str);
				}else{
					is_index = true;
					$(".gene_head1").html('');
				} 
			}
		}
	});
}
//获取科室逻辑
function get_dep_logic(){
	$.ajax({
		url:'getMateDepLogic.action',
		data:{'sex':$('#sex').text()},
		type:'post',
		success:function(data){
			if(data != 'null'){
				dep_logic = eval(data);
			}else{
				dep_logic = null;
			}
		}
	});
}

function create_item(obj){
	var row = '';
	var zt='';
	var da='';
	var shuzi = '';
	for(var i=0;i<obj.length;i++){
			if(obj[i].health_level=='Z'){
				zt='#000000';
				da=3;
			}else if(obj[i].health_level=='W'){
				if(obj[i].brief == '1' || obj[i].brief == '2'){
					zt='#ff0000';
					da=5;
				}else{
					zt='#ff0000';
					da=1;
				}
			}else if(obj[i].health_level=='Y'){
				if(obj[i].brief == '1' || obj[i].brief == '2'){
					zt='#ff9900';
					da=4;
				}else{
					zt='#ff9900';
					da=2;
				}
			}else{
				zt='#000000';
				da=3;
			}
			if(obj[i].item_category == "数字型"){
				shuzi = "class='test_box result jre onlynum'";
			}else{
				shuzi = "class='test_box result jre ' onkeypress='return disableEnter(event)'";
			}
			row+="<li><label>"+obj[i].item_name+"</label>";
			row+="<input type='hidden' class='jd'   value='"+obj[i].id+"' data='"+obj[i].item_num+"'/><input type='hidden' class='jdciid' value='"+obj[i].c_id+"' data='"+obj[i].item_code+"'/><input type='hidden' class='lx'   value='"+obj[i].item_category+"'/><input type='hidden' class='brief'   value='"+obj[i].brief+"'/><input type='hidden' id='items_"+obj[i].id+"'/>";
			row+="<textarea "+shuzi+" id='item_"+obj[i].id+"' type='text' onblur='gb(this)'  data='"+da+"'  onkeyup='sr(this)' onchange='sr(this)' ondblclick='sj(this,\""+obj[i].item_num+"\");' onclick='cyc(this,\""+obj[i].item_num+"\","+obj[i].brief+","+obj[i].id+");' style='color:"+zt+";'>"+obj[i].exam_result+"</textarea></li>";
	}
	$("#dt").html(row);
	$("#dt li").each(function(){
		$(this).find(".result").textareaAutoHeight({minHeight: 25, maxHeight: 200});
		$(this).find(".onlynum").onlyNum();
	});
	var inputter = getCookie("inputter");
	if(obj[0].inputter > 0){
		inputter = obj[0].inputter;
	}
	$('#inputter').combobox({
		url : 'getSysLogUserList.action?oper_type=1',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'chi_Name',
		onLoadSuccess : function(data) {
			if(inputter == null){
				$('#inputter').combobox('setValue', $("#userid").val());
			}else{
				$('#inputter').combobox('setValue', inputter);
			}
		}
	});
	if(obj.length > 0 ){
		$("#exam_doctor_name").html(obj[0].exam_doctor_name);
		$("#exam_date").html(obj[0].exam_date);
		$("#item_"+obj[0].id).focus();
		$("#item_"+obj[0].id).select();
	}
	create_conclusion();
}
//生成结论描述
function create_conclusion(){
	var obj = samp_item[sam_count].depItemList;
	for(var k=0;k<obj.length;k++){
		if(obj[k].brief_mark == '1' && obj[k].brief == '2' && obj[k].health_level !='Z'){
			$.ajax({
				url:'getItemResultLibcyc.action',
				type:'post',
				async : false,
//				dataType: "json",
				data:{'item_code':obj[k].item_num,'exam_status':'N'},
				success:function(data){
						var jcxm=eval('('+data+')');
						if(jcxm.length>0){
							var str='';
							for(var i=0;i<jcxm.length;i++){
								if(obj[k].exam_result.indexOf(jcxm[i].exam_result) > -1){
									if(str == ''){
										str = jcxm[i].exam_conclusion;
									}else{
										str += ";"+jcxm[i].exam_conclusion;
									}
									exam_conclusion.push({'item_id':obj[k].id,'conclusion':jcxm[i].exam_conclusion});
								}
							}
							if(estimateweiji(obj[k])){
								$("#item_"+obj[k].id).attr('data','5');
								$("#item_"+obj[k].id).css('color','#ff0000');
								$("#item_"+obj[k].id).attr('data_r',str);
							}else{
								if(str == ''){
									$("#item_"+obj[k].id).attr('data','3');
									$("#item_"+obj[k].id).attr('data_r','');
									$("#item_"+obj[k].id).css('color','#000000');
								}else{
									$("#item_"+obj[k].id).attr('data','4');
									$("#item_"+obj[k].id).css('color','#ff9900');
									$("#item_"+obj[k].id).attr('data_r',str);
								}
							}
						}
					}
			});
		}
	}
}

//判断危机
function estimateweiji(obj){
	var result = false;
	if(obj.item_category == "数字型"){
		if($('#sex').text() == '男'){
			if($("#item_"+obj.id).val() != '' ){
				if((Number($("#item_"+obj.id).val()) > Number(obj.dang_Mmax) && obj.dang_Mmax != '') || (Number($("#item_"+obj.id).val()) < Number(obj.dang_Mmin) && obj.dang_Mmin != '')){
					result = true;
				}
			}
		}else if($('#sex').text() == '女'){
			if($("#item_"+obj.id).val() != ''){
				if((Number($("#item_"+obj.id).val()) > Number(obj.dang_Fmax) && obj.dang_Fmax != '') || (Number($("#item_"+obj.id).val()) < Number(obj.dang_Fmin) && obj.dang_Fmin != '')){
					result = true;
				}
			}
		}
	}else{
		if($("#item_"+obj.id).val() != ''){
			for(var j=0;j<obj.itemRefDang.length;j++){
				if(obj.itemRefDang[j].is_ReforDang == 'D' && $("#item_"+obj.id).val().indexOf(obj.itemRefDang[j].val_info) > -1){
					result = true;
				}
			}
		}
	}
	return result;
}
//判断参考值
function estimatecankao(obj){
	var result = false;
	if(obj.item_category == "数字型"){
		if($('#sex').text() == '男'){
			if($("#item_"+obj.id).val() != '' ){
				if(Number($("#item_"+obj.id).val()) >= Number(obj.ref_Mmin) && obj.ref_Mmin != '' && Number($("#item_"+obj.id).val()) < Number(obj.ref_Mmax) && obj.ref_Mmax != ''){
					result = true;
				}
			}
		}else if($('#sex').text() == '女'){
			if($("#item_"+obj.id).val() != ''){
				if(Number($("#item_"+obj.id).val()) >= Number(obj.ref_Fmin) && obj.ref_Fmin != '' && Number($("#item_"+obj.id).val()) < Number(obj.ref_Fmax) && obj.ref_Fmax != ''){
					result = true;
				}
			}
		}
	}else{
		if($("#item_"+obj.id).val() != ''){
			for(var j=0;j<obj.itemRefDang.length;j++){
				if(obj.itemRefDang[j].is_ReforDang == 'R' && $("#item_"+obj.id).val().indexOf(obj.itemRefDang[j].val_info) > -1){
					result = true;
				}
			}
		}
	}
	return result;
}

//切换检查项目
function qiehuan_xiangmu(dom,x){
	sam_count = x;
	var obj = samp_item[x].depItemList;
	create_item(obj);
	var obj1 = $(dom).parent().children("li");
	for(var i=0;i<obj1.length;i++){
		obj1.eq(i).removeClass('active');
	}
	$(dom).addClass('active');
	jiel();
}

var pd=1;
//鼠标离开到下拉div
function select_com_list_amover(){
	pd=1;
}
//鼠标移动到下拉div
function select_com_list_mover(){
	pd=2;
}
//失去焦点
function gb(fd){
	//移动到div不执行焦点事件
	if(pd!=2){
		$(".xscyc").hide();
	}
	//sr(fd);
}
var shuangji="";
var sjid="";
//输入框双击事件
function sj(df,item_code_c){
	sjid=item_code_c;
	shuangji=df;
	$(".xscyc").hide();
	var name=$(df).parent().children("label").eq(0).text();
	$("#resultVocabulary").dialog({
		width :900,
		height:600,
		title:"检查项目:"+name,
		href:'getItemResultLibPage.action',
		type:'post',
		queryParams:{
			item_num:item_code_c
		}
	});
	$("#fit").dialog('open');
	$("#fit").dialog('center');
}
//获取常用词--单击事件
function cyc(ths,item_code_c,brief,id){
	$.ajax({
		url:'getItemResultLibcyc.action',
		type:'post',
//		dataType: "json",
		data:{'item_code':item_code_c,'exam_status':'N'},
		success:function(data){
				var jcxm=eval('('+data+')');
				if(jcxm.length>0){
					var str = '';
					for(var i=0;i<jcxm.length;i++){
						if(brief=="2"){
//							str+="<li onclick='zhi(this,"+id+")' id='jcxm_"+i+"'>"+jcxm[i].exam_conclusion.replace(/[\r\n]/g,"")+"("+jcxm[i].exam_result.replace(/[\r\n]/g,"")+")</li>";
//							str+="<li onclick='zhi(this,"+id+")' id='jcxm_"+i+"'>结果："+jcxm[i].exam_result.replace(/[\r\n]/g,"")+"<div style='display:none;'>-"+jcxm[i].exam_conclusion.replace(/[\r\n]/g,"")+"</div></li>结论："+jcxm[i].exam_conclusion.replace(/[\r\n]/g,"");
							str+="<li onclick='zhi(this,\""+id+"\","+brief+")' id='jcxm_"+i+"'>"+jcxm[i].exam_conclusion.replace(/[\r\n]/g,"")+"("+jcxm[i].exam_result.replace(/[\r\n]/g,"")+")</li>";	
						}else{
							str+="<li onclick='zhi(this,\""+id+"\","+brief+")' id='jcxm_"+i+"'>"+jcxm[i].exam_result.replace(/[\r\n]/g,"")+"</li>";
						}
					}
					var left = $(ths).offset().left - document.body.clientWidth*0.25-2;
					var height = $(ths).height();
					var top = $(ths).offset().top+height-25;
					var width = $(ths).width()+6;
					
					$(".xscyc").css("width",width+'px');
					$(".xscyc").css("left",left+'px');
					$(".xscyc").css("top",top+'px');
					
					$(".xscyc ul").html(str+"");
					for(var i=0;i<jcxm.length;i++){
						$("#jcxm_"+i).attr('data',jcxm[i].exam_conclusion);
					}
					$(".xscyc").show();
				}
			}
	});
	
	lishiCommonResult(id);
}
//li标签点击事件
var exam_conclusion = new Array();
function zhi(va,id,brief){
	var arr=$(va).text();
	if(brief=="2"){
		arr = $(va).text().split('(')[1].split(')')[0];
		var dh=",";
		if($(va).text().split('(')[0]==""||$("#items_"+id).val()==""){
			dh="";
		}
		$('#items_'+id).val($("#items_"+id).val()+dh+$(va).text().split('(')[0]);
	}
	var item_list = samp_item[sam_count].depItemList;
	for(i=0;i<item_list.length;i++){
		if(item_list[i].id == id){
			if(item_list[i].default_value == $("#item_"+id).val() || item_list[i].default_value == $(va).text() || $("#item_"+id).val() == '' || $("#item_"+id).val() == '婉拒检查'){
				$("#item_"+id).val(arr);
				for(var j =0;j<exam_conclusion.length;j++){
					if(exam_conclusion[j].item_id == id){
						exam_conclusion.splice(j,1);
						j--;
					}
				}
				exam_conclusion.push({item_id:id,conclusion:$(va).attr('data')});
			}else{
				$("#item_"+id).val($("#item_"+id).val() +','+arr);
				exam_conclusion.push({item_id:id,conclusion:$(va).attr('data')});
			}
			$("#item_"+id).trigger('blur');
		}
	}
	sr($("#item_"+id));
	if(arr=="未见异常"){
		$('#items_'+id).val("");
		$('#item_'+id).val("未见异常");
	}
	$(".xscyc").hide();
}

function sr(sa){
	var item_ids=$(sa).parent().find('.jd').val(); 
	var leixing=$(sa).parent().find('.lx').val(); 
	var obj = samp_item[sam_count].depItemList;
	for(i=0;i<obj.length;i++){
		var result = 'zc';
		if(obj[i].id == item_ids && obj[i].brief_mark == '1' && obj[i].brief == '0'){
			if(obj[i].item_category == "数字型"){
				if($('#sex').text() == '男'){
					if($(sa).val() != ''){
						if(Number($(sa).val()) >= Number(obj[i].ref_Mmin) && obj[i].ref_Mmin != '' && Number($(sa).val()) < Number(obj[i].ref_Mmax) && obj[i].ref_Mmax != ''){
							result = 'zc'
						}else if((Number($(sa).val()) > Number(obj[i].dang_Mmax) && obj[i].dang_Mmax != '') || (Number($(sa).val()) < Number(obj[i].dang_Mmin) && obj[i].dang_Mmin != '')){
							result = 'wj'
						}else{
							result = 'yc'
						}
					}
				}else if($('#sex').text() == '女'){
					if($(sa).val() != ''){
						if(Number($(sa).val()) >= Number(obj[i].ref_Fmin) && obj[i].ref_Fmin != '' && Number($(sa).val()) < Number(obj[i].ref_Fmax) && obj[i].ref_Fmax != ''){
							result = 'zc'
						}else if((Number($(sa).val()) > Number(obj[i].dang_Fmax) && obj[i].dang_Fmax != '') || (Number($(sa).val()) < Number(obj[i].dang_Fmin) && obj[i].dang_Fmin != '')){
							result = 'wj'
						}else{
							result = 'yc'
						}
					}
				}
			}else{
				result = 'yc'
				if($(sa).val() != ''){
					for(var j=0;j<obj[i].itemRefDang.length;j++){
						if(obj[i].itemRefDang[j].is_ReforDang == 'R' && $(sa).val().indexOf(obj[i].itemRefDang[j].val_info) > -1){
							result = 'zc'
						}else if(obj[i].itemRefDang[j].is_ReforDang == 'D' && $(sa).val().indexOf(obj[i].itemRefDang[j].val_info) > -1){
							result = 'wj'
						}
					}
				}
			}
			
			if(result=="zc"){//正常
				$(sa).css('color','#000000');
				$(sa).attr('data',3);
			}else if(result=="wj"){//危机
				$(sa).css('color','#ff0000');
				$(sa).attr('data',1);
			}else{//异常
				$(sa).css('color','#ff9900');
				$(sa).attr('data',2);
			}
		}else if(obj[i].id == item_ids && obj[i].brief_mark == '1' && obj[i].brief == '2'){
			var dep_disease_result = '';
			for(z=0;z<exam_conclusion.length;z++){
				if(exam_conclusion[z].item_id == item_ids){
					if(exam_conclusion[z].conclusion != ''){
						
						
						if(dep_disease_result == ''){
							dep_disease_result = exam_conclusion[z].conclusion;
						}else{
							dep_disease_result += ','+exam_conclusion[z].conclusion;
						}
					}
				}
			}
			if(estimateweiji(obj[i])){
				$(sa).attr('data','5');
				$(sa).css('color','#ff0000');
				$(sa).attr('data_r',dep_disease_result);
			}else{
				if(dep_disease_result == ''){
					$(sa).attr('data','3');
					$(sa).attr('data_r','');
					$(sa).css('color','#000000');
				}else{
					$(sa).attr('data','4');
					$(sa).css('color','#ff9900');
					$(sa).attr('data_r',dep_disease_result);
				}
			}
		}
	}
	jiel();
//	$(sa).focus();
}
function creatLogic(){
	var obj = samp_item[sam_count].depItemList;
	for(var k=0;k<obj.length;k++){
		if(obj[k].brief_mark == '1' && obj[k].brief == '1'){
			$("#item_"+obj[k].id).attr('data','3');
			$("#item_"+obj[k].id).attr('data_r','');
			$("#item_"+obj[k].id).css('color','#000000');
		}
	}
	if(dep_logic != null){
		for(i=0;i<dep_logic.length;i++){
			var count = 0;
			var andflag = false;
			for(j=0;j<dep_logic[i].logic_item.length;j++){
				if("and" == dep_logic[i].logic_item[j].andOrNo && !andflag){
					andflag = true;
				}
				if($("#item_"+dep_logic[i].logic_item[j].exam_item_id).val() == '' || $("#item_"+dep_logic[i].logic_item[j].exam_item_id).val() == undefined){
					if(andflag){
						break;
					}else{
						continue;
					}
				}
//				var flag = true;
//				for(var k=0;k<obj.length;k++){
//					if(obj[k].id == dep_logic[i].logic_item[j].exam_item_id && obj[k].brief_mark == '1' && obj[k].brief == '1'){
//						flag = false;
//					}
//				}
//				if(flag){
//					if(andflag){
//						break;
//					}else{
//						continue;
//					}
//				}
				if(convertLogic($("#item_"+dep_logic[i].logic_item[j].exam_item_id).val(),dep_logic[i].logic_item[j].condition,dep_logic[i].logic_item[j].condition_value)){
					count ++;
					if(!andflag){
						for(var k=0;k<obj.length;k++){
							if(obj[k].id == dep_logic[i].logic_item[j].exam_item_id && obj[k].brief_mark == '1' && obj[k].brief == '1'){
								$("#item_"+dep_logic[i].logic_item[j].exam_item_id).attr('data','4');
								var dep_disease_result = $("#item_"+dep_logic[i].logic_item[j].exam_item_id).attr('data_r');
								if(dep_disease_result == '' || dep_disease_result == undefined){
									$("#item_"+dep_logic[i].logic_item[j].exam_item_id).attr('data_r',dep_logic[i].conclusion_word);
								}else if(dep_logic[i].conclusion_word.indexOf(dep_disease_result) == -1){
									$("#item_"+dep_logic[i].logic_item[j].exam_item_id).attr('data_r',dep_disease_result +','+dep_logic[i].conclusion_word);
								}
								$("#item_"+dep_logic[i].logic_item[j].exam_item_id).css('color','#ff9900');
							}
						}
					}
				}else{
					if(andflag){
						break;
					}else{
						continue;
					}
				}
			}
			if(andflag){
				if (dep_logic[i].logic_item.length != 0 && dep_logic[i].logic_item.length == count) {
					var item_id = 0;
					for(j=0;j<dep_logic[i].logic_item.length;j++){
						if(item_id != dep_logic[i].logic_item[j].exam_item_id){
							item_id = dep_logic[i].logic_item[j].exam_item_id;
							for(var k=0;k<obj.length;k++){
								if(obj[k].id == dep_logic[i].logic_item[j].exam_item_id && obj[k].brief_mark == '1' && obj[k].brief == '1'){
									$("#item_"+dep_logic[i].logic_item[j].exam_item_id).attr('data','4');
									var dep_disease_result = $("#item_"+dep_logic[i].logic_item[j].exam_item_id).attr('data_r');
									if(dep_disease_result == '' || dep_disease_result == undefined){
										$("#item_"+dep_logic[i].logic_item[j].exam_item_id).attr('data_r',dep_logic[i].conclusion_word);
									}else if(dep_logic[i].conclusion_word.indexOf(dep_disease_result) == -1){
										$("#item_"+dep_logic[i].logic_item[j].exam_item_id).attr('data_r',dep_disease_result +','+dep_logic[i].conclusion_word);
									}
									$("#item_"+dep_logic[i].logic_item[j].exam_item_id).css('color','#ff9900');
								}
							}
						}
					}
				}
			}
		}
	}
	for(var k=0;k<obj.length;k++){
		if(estimateweiji(obj[k])){
			$("#item_"+obj[k].id).attr('data','5');
			$("#item_"+obj[k].id).css('color','#ff0000');
			$("#item_"+obj[k].id).attr('data_r','');
		}
		if(estimatecankao(obj[k])){
			$("#item_"+obj[k].id).attr('data','3');
			$("#item_"+obj[k].id).attr('data_r','');
			$("#item_"+obj[k].id).css('color','#000000');
		}
	}
}

function convertLogic(result,con,value){
	if(con == '='){
		if(result == value){
			return true;
		}
	}else if(con == '>'){
		if(Number(result) > Number(value)){
			return true;
		}
	}else if(con == '<'){
		if(Number(result) < Number(value)){
			return true;
		}
	}else if(con == '>='){
		if(Number(result) >= Number(value)){
			return true;
		}
	}else if(con == '<='){
		if(Number(result) <= Number(value)){
			return true;
		}
	}else if(con == 'in'){
		if(result.indexOf(value) != -1){
			return true;
		}
	}else if(con == 'not in'){
		if(result.indexOf(value) == -1){
			return true;
		}
	}else if(con == 'like'){
		if(result.indexOf(value) != -1){
			return true;
		}
	}
	return false;
}

function judgment_ref(sa,item_ids){
	var obj = samp_item[sam_count].depItemList;
	for(i=0;i<obj.length;i++){
		var result = 'zc';
		if(obj[i].id == item_ids && obj[i].brief_mark == '1' && obj[i].brief == '0'){ 
			if(obj[i].item_category == "数字型"){
				if($('#sex').text() == '男'){
					if($(sa).val() != ''){
						if(Number($(sa).val()) >= Number(obj[i].ref_Mmin) && obj[i].ref_Mmin != '' && Number($(sa).val()) < Number(obj[i].ref_Mmax) && obj[i].ref_Mmax != ''){
							result = 'zc'
						}else if((Number($(sa).val()) > Number(obj[i].dang_Mmax) && obj[i].dang_Mmax != '') || (Number($(sa).val()) < Number(obj[i].dang_Mmin) && obj[i].dang_Mmin != '')){
							result = 'wj'	
						}else{
							result = 'yc'
						}
					}
				}else if($('#sex').text() == '女'){
					if($(sa).val() != ''){
						if(Number($(sa).val()) >= Number(obj[i].ref_Fmin) && obj[i].ref_Fmin != '' && Number($(sa).val()) < Number(obj[i].ref_Fmax) && obj[i].ref_Fmax != ''){
							result = 'zc'
						}else if((Number($(sa).val()) > Number(obj[i].dang_Fmax) && obj[i].dang_Fmax != '') || (Number($(sa).val()) < Number(obj[i].dang_Fmin) && obj[i].dang_Fmin != '')){
							result = 'wj'
						}else{
							result = 'yc'
						}
					}
				}
			}else{
				result = 'yc'
				if($(sa).val() != ''){
					for(var j=0;j<obj[i].itemRefDang.length;j++){
						if(obj[i].itemRefDang[j].is_ReforDang == 'R' && $(sa).val() == obj[i].itemRefDang[j].val_info){
							result = 'zc'
						}else if(obj[i].itemRefDang[j].is_ReforDang == 'D' && $(sa).val() == obj[i].itemRefDang[j].val_info){
							result = 'wj'
						}
					}
				}
			}
			
			if(result=="zc"){//正常
				$(sa).css('color','#000000');
				$(sa).attr('data',3);
			}else if(result=="wj"){//危机
				$(sa).css('color','#ff0000');
				$(sa).attr('data',1);
			}else{//异常
				$(sa).css('color','#ff9900');
				$(sa).attr('data',2);
			}
		}
	}
} 
function jslb(){
	var obj = samp_item[sam_count].depItemList;
	
	var zongfei1 = 0,zongfei2 = 0,zongfei3 = 0,zongfei4 = 0,zongobj1,zongobj2,zongobj3,zongobj4;
	for(var j=0;j<obj.length;j++){
		if(obj[j].item_num == 'ttnj12' || obj[j].item_num == 'ttnj13'
			|| obj[j].item_num == 'ttnj14' || obj[j].item_num == 'ttnj15'
				||obj[j].item_num == 'ttnj16' || obj[j].item_num == 'ttnj17'
					|| obj[j].item_num == 'ttnj18' || obj[j].item_num == 'ttnj19'){
			if($("#item_"+obj[j].id).val() != ''){
				zongfei1 += parseFloat($("#item_"+obj[j].id).val().replace(/[^0-9]/ig,""));
			}
		}else if(obj[j].item_num == 'ttnj21' || obj[j].item_num == 'ttnj22'
					|| obj[j].item_num == 'ttnj23' || obj[j].item_num == 'ttnj24'
						||obj[j].item_num == 'ttnj25' || obj[j].item_num == 'ttnj26'
							|| obj[j].item_num == 'ttnj27' ||obj[j].item_num == 'ttnj28' || obj[j].item_num == 'ttnj29'){
			if($("#item_"+obj[j].id).val() != ''){
				zongfei2 += parseFloat($("#item_"+obj[j].id).val().replace(/[^0-9]/ig,""));
			}
		}else if(obj[j].item_num == 'ttnj31' || obj[j].item_num == 'ttnj32'
					|| obj[j].item_num == 'ttnj33' || obj[j].item_num == 'ttnj34'
						||obj[j].item_num == 'ttnj35' || obj[j].item_num == 'ttnj36'
							|| obj[j].item_num == 'ttnj37'){
			if($("#item_"+obj[j].id).val() != ''){
				zongfei3 += parseFloat($("#item_"+obj[j].id).val().replace(/[^0-9]/ig,""));
			}
		}else if(obj[j].item_num == 'ttnj39' || obj[j].item_num == 'ttnj40'
					|| obj[j].item_num == 'ttnj41' || obj[j].item_num == 'ttnj42'
						||obj[j].item_num == 'ttnj43' || obj[j].item_num == 'ttnj44'
							|| obj[j].item_num == 'ttnj45' || obj[j].item_num == 'ttnj46'){
			if($("#item_"+obj[j].id).val() != ''){
				zongfei4 += parseFloat($("#item_"+obj[j].id).val().replace(/[^0-9]/ig,""));
			}
		}else if(obj[j].item_num == 'ttnj20'){
			zongobj1 = obj[j].id;
		}else if(obj[j].item_num == 'ttnj30'){
			zongobj2 = obj[j].id;
		}else if(obj[j].item_num == 'ttnj38'){
			zongobj3 = obj[j].id;
		}else if(obj[j].item_num == 'ttnj47'){
			zongobj4 = obj[j].id;
		}
	}
	if(zongobj1 != null || zongobj1 != undefined){
		$("#item_"+zongobj1).val(zongfei1);
		judgment_ref("#item_"+zongobj1,zongobj1);
	}
	if(zongobj2 != null || zongobj2 != undefined){
		$("#item_"+zongobj2).val(zongfei2);
		judgment_ref("#item_"+zongobj2,zongobj2);
	}
	if(zongobj3 != null || zongobj3 != undefined){
		$("#item_"+zongobj3).val(zongfei3);
		judgment_ref("#item_"+zongobj3,zongobj3);
	}
	if(zongobj4 != null || zongobj4 != undefined){
		$("#item_"+zongobj4).val(zongfei4);
		judgment_ref("#item_"+zongobj4,zongobj4);
	}
}

function jiel(){
	jslb();
	var obj = samp_item[sam_count].depItemList;
	var shen_gao = '';
	var ti_zhong = '';
	var ti_zhi = '';
	
	var sum_count = 0;
	for(var j=0;j<obj.length;j++){
		if(obj[j].item_num == 'WL001'){
			shen_gao = $("#item_"+obj[j].id).val();
		}
		if(obj[j].item_num == 'WL002'){
			ti_zhong = $("#item_"+obj[j].id).val();
		}
		if(obj[j].item_num == 'WL003'){
			ti_zhi = obj[j].id;
		}
		if(obj[j].brief_mark == 0){
			sum_count = sum_count + 1;
		}
	}
		
	if(shen_gao != '' && ti_zhong != ''){
		var zhong = accDiv(parseFloat(shen_gao),100);
		var temp = accMul(zhong,zhong);
		var zhi_shu = accDiv(parseFloat(ti_zhong),temp);
		$("#item_"+ti_zhi).val(zhi_shu.toFixed(1));
		judgment_ref($("#item_"+ti_zhi),ti_zhi);
	}else if(shen_gao != null || ti_zhong != null){
		$("#item_"+ti_zhi).val('');
		$("#item_"+ti_zhi).css('color','#000000');
		$("#item_"+ti_zhi).attr('data',3);
	}
	creatLogic();
	var val=new Array();
	var i=1;
	
	var old_words = '';
	for(j=0;j < samp_item.length;j++){
		if(j == sam_count){
			$(".test_box").each(function(){
				if($(this).attr('data')==1||$(this).attr('data')==2){
					if($(this).val()!=""){
						var fssa=$(this).parent().children("label").eq(0).text();
						val+="("+i+")"+fssa+":"+$(this).val().trim()+"\n"; //.replace(/\s*/g,"") 去掉去吧空格
						i=i+1;
					}
				}else if($(this).attr('data')==4 || $(this).attr('data')==5){
					if(old_words != $(this).attr('data_r') && $(this).attr('data_r') != '' && $(this).attr('data_r') !=undefined ){
						val+="("+i+")"+CtoH($(this).attr('data_r').trim())+"\n";
						i=i+1;
						old_words = $(this).attr('data_r');
					}
				} 
			});
		}else{
			var itemList = samp_item[j].depItemList;
			for(x=0;x<itemList.length;x++){
				if(itemList[x].health_level == 'Y' || itemList[x].health_level == 'W'){
					if(itemList[x].brief == '1' || itemList[x].brief == '2'){
						if(old_words != itemList[x].conclusion_word){
							val+="("+i+")"+itemList[x].conclusion_word+"\n";
							i=i+1;
							old_words = itemList[x].conclusion_word;
						}
					}else if(itemList[x].brief == '0'){
						val+="("+i+")"+itemList[x].item_name+":"+itemList[x].exam_result+"\n";
						i=i+1;
					}
				}
			}
		}
	}
	  
	if(val!=''){
		$('#jielun').val(val);
	}else{
		if(sum_count != obj.length){
			$('#jielun').val($('#moren_exam_result').val());
		}
	}
	
	//zhuankejianyi();
}
function zhuankejianyi(){
	$.ajax({
		url:'getDepSuggestionList.action',
		data:{"exam_result_summary":$('#jielun').val()},
		type:'post',
		success:function(data){
			var obj = eval(data);
			var str = '';
			for(i=0;i<obj.length;i++){
				str += obj[i].sugg_content+'\n';
			}
			$("#zhuanjiaji").val(str);
		}
	});
}

function zhuankejianyi_db(){
	$('#zkjylr').val($("#zhuanjiaji").val());
	$("#zhuankejianyi_list").datagrid({
 		url: 'getDepSuggestionList.action',
//		queryParams: model,
		rownumbers:false,
		columns:[[
		          {align:"",field:"sugg_content","title":"建议内容","width":20},
		          {align:"center",field:"sug_add","title":"添加","width":5,"formatter":f_add_depsug}
		          ]],
	    onLoadSuccess:function(value){
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		nowrap:false
	});
	
	$("#dlg-zkjy").dialog('open');
	$("#dlg-zkjy").dialog('center');
}
function f_add_depsug(val,row){
	return '<a href=\"javascript:f_edit(\''+row.sugg_content+'\')\">添加</a>';
}
function f_edit(sugg_content){
	$('#zkjylr').val($('#zkjylr').val()+sugg_content+'\n');
}

function f_save_depsug(){
	$('#zhuanjiaji').val($("#zkjylr").val());
	$('#dlg-zkjy').dialog('close');
}
//----------------------------------------------科室检查结论保存----------------------------
var save_status = 0;
function addexamDepResult(){
	if(save_status > 0){
		return;
	}
	save_status ++;
	var charing_ids = new Array();
	for(i=0;i<samp_item[sam_count].depItemList.length;i++){
		charing_ids.push("'"+samp_item[sam_count].depItemList[i].item_code+"'");
	}
	if(getStatus()=="2"){
		save_status --;
		return;
	}
	var ms=new Array();
	var zt="";
	
	var sensitive_content = '';
	$("#dt li").each(function(){
		if($(this).find(".result").attr("data")==1 || $(this).find(".result").attr("data")==5 || $(this).find(".result").attr("data")==7){
			zt="W";
		}else if($(this).find(".result").attr("data")==2 || $(this).find(".result").attr("data")==4 || $(this).find(".result").attr("data")==6){
			zt="Y";
		}else{
			zt="Z";
		}
		var exam_result_back="";
		var value=$("#item_"+$(this).find(".jd").val()).val();
		if($(this).find(".brief").val()=="2"){
			exam_result_back=$('#items_'+$(this).find(".jd").val()).val();
		}else{
			exam_result_back=$('#item_'+$(this).find(".jd").val()).val();
		}
		ms.push({
			"exam_item_id":$(this).find(".jd").val(),
			"item_num":$(this).find(".jd").attr("data"),
			"charging_item_id":$(this).find(".jdciid").val(),
			"item_code":$(this).find(".jdciid").attr("data"),
			"exam_result":$(this).find(".result").val().trim().replace(/\'/g,"''"),
			"health_level":zt,
			"exam_result_back":exam_result_back,
			"exam_date":$('#join_date').val()
		});
		
		sensitive_content += $(this).find(".result").val();
	});
	
	sensitive_content += $('#jielun').val() + $('#zhuanjiaji').val();
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	if($("#isCheckSensvitiveWord").val() == 'Y'){
		$.ajax({
			url:'checkSensitiveWordsSex.action',
			type:'post',
			data:{sensitive_content:sensitive_content,sensitive_sex:$("#sex").html()},
			success:function(data){
				var obj = data.split('-');
				if(obj[0] == 'ok'){
					$.ajax({
						url:'checkSensitiveWord.action',
						type:'post',
						data:{sensitive_content:sensitive_content},
						success:function(data){
							var obj1 = data.split('-');
							if(obj1[0] == 'ok'){
								savedepresult(charing_ids,ms);
							}else{
								$(".loading_div").remove();
								$.messager.confirm("提示信息","结果出现特殊重要词语：（"+obj1[1]+"）,确认保存吗？",function(r){
									if(r){
										var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
										$("body").prepend(str);
										savedepresult(charing_ids,ms);
									}else{
										save_status --;
									}
								});
							}
						},
						error:function(){
							$(".loading_div").remove();
							$.messager.alert("操作提示",'操作失败！', "error");
						}
					});
				}else{
					$(".loading_div").remove();
					$.messager.alert("操作提示",'结果出现与性别不一致词语：（'+obj[1]+'）,请检查!', "error");
				}
			},
			error:function(){
				$(".loading_div").remove();
				$.messager.alert("操作提示",'操作失败！', "error");
			}
		});
	}else{
		savedepresult(charing_ids,ms);
	}
}

function savedepresult(charing_ids,ms){
	$.ajax({
		url:'addexamDepResult.action',
		type:'post',
		data:{
			dep_id:$("#dep_id").val(),
			app_type:$("#app_type").val(),
			dep_num:$("#dep_num").val(),
			exam_info_id:$('#id').val(),
			exam_num:$("#tjh").html(),
			exam_result_summary:$('#jielun').val().replace(/\'/g,"''"),
			suggestion:$('#zhuanjiaji').val(),
			li:JSON.stringify(ms),
			charing_ids:charing_ids.toString(),
			inputter : $('#inputter').combobox('getValue')
		},
		success:function(data){
			save_status -- ;
			$(".loading_div").remove();
			setCookie('inputter',$('#inputter').combobox('getValue'));
			if(data.indexOf('失败')>0){
				$.messager.alert("操作提示",data, "error");
			}else{
				if(is_index){
					close_page();
				}else{
					refsh_page();
				}
			}
		},
		error:function(){
			$(".loading_div").remove();
			$.messager.alert("操作提示",'操作失败！', "error");
		}
	});
}
//拒检
function jvjian(){
	$.messager.confirm('确认','您确认要拒检吗？',function(r){
		if (r){
			$("#dt li").each(function(){
				$(this).find(".result").val('婉拒检查');
				$(this).find(".onlynum").val('');
			});
			$('#jielun').val('婉拒检查');
		}
	});
}
//清除结果
function qinchujieguo(){
	if(getStatus()=="2"){
		return;
	}
	$.messager.confirm('确认','您确要清除结果吗？',function(r){    
	    if (r){    
	        var exam=new Array()
	    	$("#dt dd").each(function(){
	    		exam.push($(this).find(".jd").val())
	    	})
	    	var model={"exam_item_id":exam.toString(),
	    			   "exam_info_id":$('#id').val()
	    			 }
	    	$.ajax({
	    		url:'deleteResult.action',
	    		type:'post',
	    		data:model,
	    		success:function(data){
	    			window.alert(data);
	    			$('#jielun').val('未见异常');
	    			$('#zhuanjiaj').val('');
	    			getexamIntion();
	    			compareHistory();
	    			// weijizhi();
                    getExamCriticalDetail();
	    			yichangzhishu();
	    			getALL();
	    		},
	    		error:function(){
	    			
	    		}
	    	})
	    }    
	});  
	
}
//-----------------------------------危机值-----------------------------------
function weijizhitanchuan(){
	$('#weijizhil').dialog({    
	    title: '危机值设置',    
	    width: 700,    
	    height:600,    
	    closed: false,    
	    cache: false,    
	    href: 'getriskPage.action',    
	    modal: true   
	});    
	$('#weijizhil').dialog('open');
	$('#weijizhil').dialog('center');
}
//判断操作医生和是否总检
function getStatus(){
	var j = "0";
	$.ajax({
		url:'getStatus.action?exam_num='+$("#exam_num").val(),
		async: false,
		success:function(data){
			if(data=='Z'){
				$.messager.alert("提示信息","已总检不能修改");
				j="2";
			}else{
				var charing_ids = new Array();
				
				for(i=0;i<samp_item[sam_count].depItemList.length;i++){
					charing_ids.push(samp_item[sam_count].depItemList[i].c_id);
				}
				$.ajax({
					url:'getExamDepStatus.action',
					data:{exam_num:$("#exam_num").val(),charing_ids:charing_ids.toString()},
					type:'post',
					async: false,
					success:function(data){
						if(data == "OK"){
							j='1';
						}else{
							j='2';
							$.messager.alert("提示信息","不是该项目的检查医生不能修改此项目！","error");
						}
					}
				})
			}
		}
	})
	return j;
} 
//-----------------------------------健康档案对比--------------------------------
function compareHistory(){
	   $.ajax({
			url : 'getExamSumarry.action?id='+$('#id').val(),
			type:'post',
			success:function(data){
				var obj = eval('('+data+')');
				var str = '';
				if(obj.length>0){
					for(i=0;i<obj.length;i++){
						str +='<dl><dt style="width:60%;color:#FF0000;font-size:15px;">上次检查结果</dt></dl>';
						str +='<dl><dt style="width:25%">小结：</dt><dd style="width:50%"><input id="old_exam_id" type="hidden" value="'+obj[i].exam_info_id+'">'+obj[i].exam_result_summary+'</dd></dl>';
						str +='<dl><dt style="width:25%">建议：</dt><dd style="width:50%">'+obj[i].suggestion+'</dd></dl>';
						str +='<dl><dt style="width:25%">医生：</dt><dd style="width:50%">'+obj[i].exam_doctor+'</dd></dl>';
						str +='<dl id="item_old_result"></dl>';
					}
					$("#result").html(str);
				}else{
					    str+='<dl><dt style="width:60%;color:#FF0000;font-size:15px;">上次检查结果</dt></dl>';
					    str+='<dl><dt style="width:75%">小结：无<input id="old_exam_id" type="hidden" value=""></dt></dl>';
						str+='<dl><dt style="width:75%">建议：无</dt></dl>';
						str+='<dl><dt style="width:75%">医生：无</dt></dl>';
						str +='<dl id="item_old_result"></dl>';
					$("#result").html(str);
				}
				
			}
		});
}
function lishiCommonResult(id){
	var exam_id = $("#old_exam_id").val();
	if(exam_id == ''){
		return;
	}
	$.ajax({
		url : 'getOldCommonDetailResult.action?exam_info_id='+exam_id+'&id='+id,
		type:'post',
		success:function(data){
			var obj = eval('('+data+')');
			$("#item_old_result").html('<dd>'+obj.item_name+'：</dd><dd>'+obj.exam_result+'</dd>');
		}
	});
}

//历史结果对比
	function lishijieguoduibi(){
		$("#history").dialog({
			title:'历史结果对比',
		});
	    $.ajax({
		   url : 'getHistoryResult.action?exam_num='+$("#exam_num").val(),
		   type:'post',
		   success:function(data){
			   var obj = eval('('+data+')');
			   var str = '';
			   if(obj.length>0){
				   for(i=0;i<obj.length;i++){
					   str += '<tr>'           
							str +='<td style="200px;padding-left:100px;padding-top:30px;">时间：</td><td style="width:200px;algin:center">'+obj[i].join_date.split("-")[0]+'年</td><td>结论：</td><td style="width:200px">'+obj[i].exam_result_summary+'</td>'
					   str +='</tr>';
				   }
			   }else{
				   str+='<tr><td style="width:130px;color:#FF0000;padding-left:100px;padding-top:30px; ">无历史记录</td></tr>'
				   str += '<tr>'
						str +='<td style="padding-left:100px;padding-top:30px;">时间：</td><td style="width:80px"></td><td>结论：</td><td style="width:100px"></td>'
				   str +='</tr>';
			   }
			$("#history").html(str);
				}
			})
		$("#history").dialog('open');
	    $("#history").dialog('center');
		
	}
//-----------------------------------------全科会诊---------------------------------------------------------------
	
	function weijizhi(){
			 var model={"exam_num":$('#exam_num').val()};
		     $("#weijizhi_div").datagrid({
			 		url: 'getWeijizhi.action',
					queryParams: model,
					rownumbers:false,
					columns:[[
					          {align:"center",field:"dep_name","title":"收费项目","width":20},
					          {align:'center',field:"exam_doctor","title":"检查医生","width":10},
					          {align:'center',field:"exam_date","title":"检查时间","width":15},
					          {align:'',field:"item_name","title":"检查项目","width":15,"styler":f_clolor},
					          {align:"",field:"exam_result","title":"检查结果","width":45,"styler":f_clolor,"formatter":f_flowor}
					          ]],
				    onLoadSuccess:function(value){
				    	$("#datatotal").val(value.total);
				    	MergeCells('weijizhi_div', 'dep_name,exam_doctor,exam_date');
				    },
				    singleSelect:true,
				    collapsible:true,
					pagination: false,
					fitColumns:true,
					fit:true,
					border:false,
					nowrap:false
				});
	}

	//新危急值显示
function getExamCriticalDetail(){
    var model={"exam_num":$('#exam_num').val()};
    $("#weijizhi_new").datagrid({
        url:'getExamCriticalList.action',
        dataType: 'json',
        queryParams:model,
        rownumbers:true,
        columns:[[{field:'ck',checkbox:true },
            {align:"center",field:"id",title:"id",hidden:true},
            {align:"center",field:"creater",title:"creater",hidden:true},
            {align:"center",field:"data_source",title:"data_source",hidden:true},
            {align:'',field:'critical_class_parent_name',title:'大类',width:40},
            {align:'',field:'critical_class_name',title:'子类 ',width:20},
            {align:'center',field:'exam_result',title:'危急值结果',width:70},
            //{align:'center',field:'check_date',title:'体检日期',width:15},//
            {align:'center',field:'critical_class_level',title:'等级',width:15},
            {align:'center',field:'done_flag_s',title:'处理状态',width:15}
        ]],
        onLoadSuccess:function(value){
        },
        singleSelect:false,
        pagination: false,
        fitColumns:true,
        fit:true,
        striped:true,
        nowrap:false,
        toolbar:"#toolbar2",
        checkOnSelect:false
    });
}



function yichangzhishu(){
	var model={ "exam_num":$('#exam_num').val()};
	 	$("#yichang").datagrid({
	 		url: 'getyichang.action',
			queryParams: model,
			rownumbers:false,
			columns:[[
			          {align:"center",field:"dep_name","title":"收费项目","width":20},
			          {align:'center',field:"exam_doctor","title":"检查医生","width":10},
			          {align:'center',field:"exam_date","title":"检查时间","width":15},
			          {align:'',field:"item_name","title":"检查项目","width":25,"styler":f_clolor},
			          {align:"",field:"exam_result","title":"检查结果","width":45,"styler":f_clolor,"formatter":f_flowor}
//			          {align:"",field:"exam_result","title":"检查结果","width":20,"styler":f_clolor,"formatter":f_yiChangResult},
//			          {align:"",field:"fanWei","title":"参考范围","width":15,"formatter":f_yiChangFanWei},
//			          {align:"",field:"danWei","title":"单位","width":15,"formatter":f_yiChangDanWei}
			          ]],
		    onLoadSuccess:function(value){
		    	$("#datatotal").val(value.total);
		    	MergeCells('yichang', 'dep_name,exam_doctor,exam_date');
		    },
		    singleSelect:true,
		    collapsible:true,
			pagination: false,
			fitColumns:true,
			fit:true,
			border:false,
			nowrap:false
		});
}

function getALL(){//
	var model={ "exam_num":$("#tjh").html()};
	$("#all").datagrid({
		url: 'getaLL.action',
		queryParams: model,
		rownumbers:false,
		columns:[[
		          {align:"center",field:"dep_name","title":"收费项目","width":20},
		          {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		          {align:'center',field:"exam_date","title":"检查时间","width":15},
		          {align:'',field:"item_name","title":"检查项目","width":25,"styler":f_clolor},
		          {align:"",field:"exam_result","title":"检查结果","width":45,"styler":f_clolor,"formatter":f_flowor}
//		          {align:"",field:"exam_result","title":"检查结果","width":20,"styler":f_clolor,"formatter":f_yiChangResult},
//		          {align:"",field:"fanWei","title":"参考范围","width":15,"formatter":f_yiChangFanWei},
//		          {align:"",field:"danWei","title":"单位","width":15,"formatter":f_getAllDanWei}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('all', 'dep_name,exam_doctor,exam_date');
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		border:false,
		nowrap:false
	});
}

//异常结果处理
function f_yiChangResult(val,row){
	row.fanWei = val;
	row.danWei = val;
	if(row.dep_category == '21'){
		if(row.exam_result == 'image_path'){
			return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.req_id+'\')">查看图片</a>';
		}else{
			return row.exam_result.replace(/\n/g,'</br>');
		}
	}else if(row.dep_category == '131'){
		if(row.health_level == 1){
			return subResultBefore(row.exam_result)+' ↑';
		}else if(row.health_level == 2){
			return subResultBefore(row.exam_result)+' ↓';
		}else if(row.health_level == 5){
			return subResultBefore(row.exam_result)+' ↑↑';
		}else if(row.health_level == 6){
			return subResultBefore(row.exam_result)+' ↓↓';
		}else{
			return subResultBefore(row.exam_result);
		}
	}else{
		return row.exam_result.replace(/\n/g,'</br>');
	}
}
//处理result括号前
function subResultBefore(result){
	var arr = result.split("(");
	return arr[0];
}


function f_yiChangFanWei(val,row){
	if(val.indexOf("(") != -1 ){ //包含
		var regex=".*?\\((.*?)\\).*?";
		var arr1 = val.match(regex);
		var arr2 = arr1[1].split(" ");
		if(arr2[0].length>2){
			return arr2[0].replace(/\n/g,'</br>');
		}else{
			return "";
		}
	}else{
		return "";
	}
}

function f_yiChangDanWei(val,row){
	if(val.indexOf("(") != -1 ){ //包含
		var regex=".*?\\((.*?)\\).*?";
		var arr1 = val.match(regex);
		var arr2 = arr1[1].split(" ");
		return arr2[2].replace(/\n/g,'</br>');
	}else{
		return "";
	}
}

function f_getAllDanWei(val,row){
	if(val.indexOf("(") != -1 ){ //包含
		var regex=".*?\\((.*?)\\).*?";
		var arr1 = val.match(regex);
		var arr2 = arr1[1].split(" ");
		if(arr2.length>1){
			return arr2[2].replace(/\n/g,'</br>');
		}else{
			return "";
		}
	}else{
		return "";
	}
}


function f_clolor(value,row,index){
	if(row.dep_category == '17'){
		if(row.health_level == 'Y'){
			return 'color:#F00;';
		}else if(row.health_level == 'W'){
			return 'color:#FF9B00;';
		}
		if(row.item_id == '0'){
			return 'background:#FEEAA8;color:#ff5100;';
		}
	}else if(row.dep_category == '131'){
		if(row.health_level == 1 || row.health_level == 2 || row.health_level == 3|| row.health_level == 5|| row.health_level == 6){
			return 'color:#F00;';
		}else if(row.health_level == 4){
			return 'color:#FF9B00;';
		}
	}
}

function f_flowor(val,row){
	if(row.dep_category == '21'){
		if(row.exam_result == 'image_path'){
			return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.req_id+'\')">查看图片</a>';
		}else{
			return row.exam_result.replace(/\n/g,'</br>');
		}
	}else if(row.dep_category == '131'){
		if(row.health_level == 1){
			return row.exam_result+' ↑';
		}else if(row.health_level == 2){
			return row.exam_result+' ↓';
		}else if(row.health_level == 5){
			return row.exam_result+' ↑↑';
		}else if(row.health_level == 6){
			return row.exam_result+' ↓↓';
		}else{
			return row.exam_result;
		}
	}else{
		return row.exam_result.replace(/\n/g,'</br>');
	}
}
function show_picture(id){
	var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_num").val();
	newWindow = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newWindow.focus();
}
/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param fldList 要合并table的id
4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
5         */
      function MergeCells(tableID, fldList) {
            var Arr = fldList.split(",");
             var dg = $('#' + tableID);
            var fldName;
             var RowCount = dg.datagrid("getRows").length;
            var span;
            var PerValue = "";
            var CurValue = "";
             var length = Arr.length - 1;
             for (i = length; i >= 0; i--) {
               fldName = Arr[i];
                PerValue = "";
                span = 1;
                for (row = 0; row <= RowCount; row++) {
                    if (row == RowCount) {
                        CurValue = "";
                    }
                    else {
                        CurValue = dg.datagrid("getRows")[row][fldName];
                    }
                     if (PerValue == CurValue) {
                        span += 1;
                    }
                     else {
                        var index = row - span;
                         dg.datagrid('mergeCells', {
                            index: index,
                             field: fldName,
                             rowspan: span,
                             colspan: null
                         });
                         span = 1;
                         PerValue = CurValue;
                     }
                 }
             }
         }
	
	//FF中需要修改配置window.close方法才能有作用，为了不需要用户去手动修改，所以用一个空白页面显示并且让后退按钮失效
	//Opera浏览器旧版本(小于等于12.16版本)内核是Presto，window.close方法有作用，但页面不是关闭只是跳转到空白页面，后退按钮有效，也需要特殊处理
	function close_page(){
		var _parentWin =  window.opener ;
		var userAgent = navigator.userAgent;
		  window.opener = null;
		  window.open('', '_self');
		  window.close();
		  _parentWin.brushpagecharging();
	}
	
	function refsh_page(){
		window.location.reload();
	}

	
    function disableEnter(e) {
        var keynum = window.event ? e.keyCode : e.which;
        if(keynum == 13){
        	return false
        }else{
        	return true;
        }
    }

    function keyDown(event){ 
    	var inputs=$(".jre");
        var focus=document.activeElement;
        var event=window.event||event;
        var key=event.keyCode; 
        for(var i=0; i<inputs.length; i++) { 
            if(inputs[i]===focus) break; 
        } 
        switch(key) { 
            case 37: //左
                if(i>0){ 
                	inputs[i-1].focus(); 
                	inputs[i-1].select();
                	break; 
                }
            case 38: //上
                if(i>0){
                	inputs[i-1].focus();
                	inputs[i-1].select();
                	break;
                }
            case 39: //右
                if(i<inputs.length-1){ 
                	inputs[i+1].focus(); 
                	inputs[i+1].select();
                	break; 
                }
            case 40: //下
                if(i<inputs.length-1){ 
                	inputs[i+1].focus();
                	inputs[i+1].select();
                	break;
                }
    		case 13://回车 
    			if(i<inputs.length-1){ 
    				inputs[i+1].focus();
    				inputs[i+1].select();
    				break;
    			}
        }
    } 
    
    
  //除法函数，用来得到精确的除法结果
  //说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
  //调用：accDiv(arg1,arg2)
  //返回值：arg1除以arg2的精确结果
  function accDiv(arg1,arg2){
  var t1=0,t2=0,r1,r2;
  try{t1=arg1.toString().split(".")[1].length}catch(e){}
  try{t2=arg2.toString().split(".")[1].length}catch(e){}
  with(Math){
  r1=Number(arg1.toString().replace(".",""))
  r2=Number(arg2.toString().replace(".",""))
  return (r1/r2)*pow(10,t2-t1);
  }
  }
  //乘法函数，用来得到精确的乘法结果
  //说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
  //调用：accMul(arg1,arg2)
  //返回值：arg1乘以 arg2的精确结果
  function accMul(arg1,arg2)
  {
  var m=0,s1=arg1.toString(),s2=arg2.toString();
  try{m+=s1.split(".")[1].length}catch(e){}
  try{m+=s2.split(".")[1].length}catch(e){}
  return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
  }
  ///////////////////////////////显示问卷/////////////////////////////////////
  function shouSurver(){
	  if($("#is_dep_edit_questionnaire").val() == 'Y'){
		  var url="getQuestionnaireSurveyPageYM.action?s_num="+$("#tjh").html();
  	 		newWindow = window.open (url,'fullscreen = yes , height=100%, width=100%, top=5, left=5,toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no','_self') 
  	 		newWindow.focus();
	  }else{
		  $.ajax({
				url:'getQuestionnaireSurveyPageYMShou.action',
				data:{customer_id:$('#c_id').val()},
				success:function(data){
					var obj =  eval('(' + data + ')');
					var yh = obj.surverList;//已选择选项
					if(yh.length<1){
						$.messager.alert("提示信息","此用户未答问卷","info");
						return false;
					}else{
						  $('#shou_wenjuan').dialog({
						  		title: '问卷',    
							    width: 800,    
							    height:700,    
							    href: "getSurverPage.action?s_num="+$('#c_id').val(),    
						  })
						   $('#shou_wenjuan').dialog('open');
						   $('#shou_wenjuan').dialog('center');
					}
				},
		  });
	  }
  }
//空函数，有用 问卷
  function qk(){}
  function wenjuanbuttoshow(){
		 var model={
				 exam_num_x:$('#tjh').text()	 
		 }
		  $.ajax({
				url:'getQuestionnaireSurveyPageYMShou.action',
				data:model,//通过体检号
				type:'post',
				 async:false,  
				success:function(data){
					var obj =  eval('(' + data + ')');
					var yh = obj.surverList;//已选择选项
					if(yh.length>0 || $("#is_dep_edit_questionnaire").val() == 'Y'){
						 $("#wenjuan").show();
					}
				}
		  })
	}
  function shoubaogaopdfbuttion(){
		$.ajax({
			url:'getDepShwoPinCeBaoGaoButtion.action?exam_num='+$('#tjh').text(),
			success:function(data){
				if(data!=""){
				        $("#pdfbuttion").show();
				}
			}
		})
	}
	//+++++++++++++++++++++++调用问卷获取报告评测PDF++++++++++++++++
	function wenjuanpdf(){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		$.ajax({
			url:'getYxCardPdf.action?exam_num='+$('#tjh').text(),
			success:function(data){
				$(".loading_div").remove();	
				var re = eval('('+data+')');
				
				if(re.status=="error"){
					$.messager.alert("警告信息",re.result,"error");
				} else {
					newWindow = window.open('getYxCardPdfPage.action','健康评测报告','fullscreen','top=0,left=0,directories=no,toolbar=no,menubar=no,alwaysRaised=yes,scrollbars=no, resizable=no,location=no, status=no');
					newWindow.focus();
				}
			},
			error:function(){
				$(".loading_div").remove();
				$.messager.alert("警告信息","操作失败","error");
			}
		})
	}
	
	
/*************科室采样****************************/
	//科室采样
	function keshicaiyang(){
		getcyitemListGrid();
		$('#dlg-item').dialog('open');
		$('#dlg-item').dialog('center');
	}

	function getcyitemListGrid(){
		var model = {"exam_num":$("#tjh").html()};
		$("#exam_item_list").datagrid({
			url: 'getDepPrintSanpleItem.action',
			queryParams: model,
			rownumbers:true,
			columns:[[{field:'ck',checkbox:true },
			          {align:"center",field:"canl","title":"操作","width":10,"formatter":f_canl},
			          {align:"center",field:"dep_name","title":"科室","width":10},
//			          {align:"center",field:"item_code","title":"项目编码","width":10},
			          {align:'center',field:"item_name","title":"项目名称","width":20},
			          {align:"center",field:"barcode","title":"条码号","width":30,"formatter":f_barcode},
			          {align:'center',field:"demo_name","title":"样本名称","width":20},
			          {align:"center",field:"status_y","title":"样本状态","width":10},
			          {align:"center",field:"check_doctor","title":"采样人","width":10},
			          {align:"center",field:"check_date","title":"采样日期","width":20}
			          ]],
		    onLoadSuccess:function(value){
		    	$("#datatotal").val(value.total);
		    	MergeCells3('exam_item_list', 'id,ck,canl,barcode,demo_name,dep_name,status_y,check_doctor,check_date',0);
		    },
		    singleSelect:false,
		    collapsible:true,
			pagination: false,
			fitColumns:true,
			fit:true,
			striped:true,
			border:false,
			onSelect:function(){
				$("#exam_item_list").datagrid('unselectAll');
			}
		});
	}

	function f_barcode(val,row,index){
		if(row.status == 'W' || row.status == 'N'){
			//return '<input class="jre textinput" type="text" id="code_'+index+'" value="'+row.barcode_back+'" />';
			return row.sample_barcode;
		}else{
			//return row.barcode_back;
			return row.sample_barcode;
		}
	}

	function f_color(value,row,index){
		return '<div style="height:15px;width:20px;margin-right:auto;margin-left:auto;background:#'+row.demo_color.substring(1,row.demo_color.length)+';"></div>'
	}

	function f_canl(val,row){
		if(row.status == 'Y'){
			return '<a href=\"javascript:f_canlSamplExamDetail(\''+row.id+'\',\''+row.dep_type+'\')\">取消采样</a>';
		}
		return '';
	}
	function save_sampling_barcode(){
		//var data = $('#exam_item_list').datagrid('getRows');
		var data = $('#exam_item_list').datagrid('getChecked');
		var sampl = new Array();
		var item_ids = new Array();
		var count = 0;
		var close_flag = false;
		for(i=0;i<data.length;i++){
			if(data[i].status == 'N' || data[i].status == 'W'){
				if($('#code_'+i).val() != ''){
					sampl.push({'id':data[i].id,'dep_type':data[i].dep_type,"barcode_back":$('#code_'+i).val()});
					item_ids.push(data[i].item_id);
					count ++;
				}
			}else{
				count ++;
			}
		}
		if(data.length == count){
			close_flag = true;
		}
		if(sampl.length == 0){
			$.messager.alert('提示信息', '没有需要采样的样本!','error');
			return;
		}
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		$.ajax({  
			url:'saveDepSampleItem.action',  
			data:{exam_num:$("#tjh").html(),
				charingids:item_ids.toString(),
			    sampleExamDetails:JSON.stringify(sampl)},          
			type: "post",//数据发送方式   
			success: function(data){
				$(".loading_div").remove();
				if(close_flag){
					$('#dlg-item').dialog('close');
				}
				alert_autoClosep('提示信息', data,'info');
			    $("#exam_item_list").datagrid('reload');
			},
			error:function(data){
			    $(".loading_div").remove();
			    alert_autoClosep('提示信息', data,'error');
			}
		});
	}

	function f_canlSamplExamDetail(id,type){
		$.messager.confirm('提示信息','是否取消采样？',function(r){
			if(r){
				var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				$("body").prepend(str);
				$.ajax({
			        url:'canlSampleLisPacsStatus.action',  
			        data:{id:id,barcode_print_type:type},          
			        type: "post",//数据发送方式   
			        success: function(data){
			        	$(".loading_div").remove();
			        	alert_autoClosep('提示信息', data,'info');
			        	$("#exam_item_list").datagrid('reload');
			        },
			        error:function(data){
			        	$(".loading_div").remove();
			        	alert_autoClosep('提示信息', data,'error');
			        }
				});
			}
		});
	}
function CtoH(str) {
	var result = "";
	for (var i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) == 12288) {
			result += String.fromCharCode(str.charCodeAt(i) - 12256);
			continue;
		}
		if (str.charCodeAt(i) > 65280 && str.charCodeAt(i) < 65375)
			result += String.fromCharCode(str.charCodeAt(i) - 65248);
		else
			result += String.fromCharCode(str.charCodeAt(i));
	}
	return result;
}

	//跳转至危急值列表页
	function tocriticalpage(){
		$("#dlg-list").dialog({
			title:'危急值列表',
			href:'depCriticalPage.action?exam_num='+$("#exam_num").val()
		});
		$("#dlg-list").dialog("open");
	}
