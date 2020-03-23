﻿/**
 * 科室检查
 */
$(document).ready(function(){
	getexamIntion_bone();
})
//---------------------------------动态获取检查项目------------------------------
function getexamIntion_bone(){
	$.ajax({
		url:'getDepInspectExamIntion_BSR.action',
		type:'post',
		data:{'id':$('#id').val(),'exam_num':$("#exam_num").val()},
		success:function(data){
			samp_item = eval('('+data+')');
			if(samp_item.length > 0){
				sam_count = 0;
				create_item_bone(samp_item[0].depItemList);
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

function create_item_bone(obj){
	var row = '';
	var zt='';
	var da='';
	for(var i=0;i<obj.length;i++){
		var hasCorrection = true;
		if(obj[i].item_num == 'E0005506') {//右耳500
			right500_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005507') {//右耳1000
			right1000_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005508') {//右耳2000
			right2000_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005509') {//右耳3000
			right3000_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005510') {//右耳4000
			right4000_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005511') {//右耳6000
			right6000_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005512') {//左耳500
			left500_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005513') {//左耳1000
			left1000_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005514') {//左耳2000
			left2000_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005515') {//左耳3000
			left3000_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005516') {//左耳4000
			left4000_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005517') {//左耳6000
			left6000_bone = obj[i].exam_result;
		} else if(obj[i].item_num == 'E0005518') {//右耳语平均听阈
			isWeight = false;
			right_avg_bone = obj[i].exam_result;
			hasCorrection = false;
		} else if(obj[i].item_num == 'E0005519') {//左耳语平均听阈
			isWeight = false;
			left_avg_bone = obj[i].exam_result;
			hasCorrection = false;
		} else if(obj[i].item_num == 'E0005520') {//双耳高频平均听阈
			double_avg_bone = obj[i].exam_result;
			hasCorrection = false;
		} else if(obj[i].item_num == 'E0005529') {//左耳平均听阈加权值
			isWeight = true;
			left_weight_bone = obj[i].exam_result;
			hasCorrection = false;
		} else if(obj[i].item_num == 'E0005530') {//右耳平均听阈加权值
			isWeight = true;
			right_weight_bone = obj[i].exam_result;
			hasCorrection = false;
		}
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
		row+="<li><label>"+obj[i].item_name+"</label>";
		row+="<input type='hidden' class='jd' value='"+obj[i].id+"' data='"+obj[i].item_num+"'/><input type='hidden' class='jdciid' value='"+obj[i].c_id+"' data='"+obj[i].item_code+"'/><input type='hidden' class='lx'   value='"+obj[i].item_category+"'/><input type='hidden' class='brief'   value='"+obj[i].brief+"'/><input type='hidden' id='items_"+obj[i].id+"'/>";
		if(hasCorrection) {
			row+="<textarea class='test_box result jre onlynum' id='item_"+obj[i].id+"' type='text' onblur='gb(this)'  data='"+da+"'  onkeyup='sr_bone(this)' onchange='sr_bone(this)' ondblclick='sj(this,\""+obj[i].item_num+"\");' onclick='cyc(this,\""+obj[i].item_num+"\","+obj[i].brief+","+obj[i].id+");' style='color:"+zt+";width:20%'></textarea>&nbsp;&nbsp;&nbsp;&nbsp;矫正后：<textarea style='width:20%' class='test_box result_correctionValue jre onlynum' disabled ></textarea></li>";
		} else {
			row+="<textarea id='item_"+obj[i].id+"' type='text'  data='"+da+"' style='color:"+zt+";width:60%' class='test_box result result_correctionValue jre onlynum' disabled ></textarea></li>";
		}
	}
	$("#dt_bone").html(row);
	$("#dt_bone li").each(function(){
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
}

function change_right_avg_bone() {
	if(right500_bone && right1000_bone && right2000_bone) {
		right_avg_bone = ( parseInt(right500_bone)+parseInt(right1000_bone)+parseInt(right2000_bone) )/3;
		right_avg_bone = Math.round(right_avg_bone);
		$("#dt_bone li").each(function(){
			var item_num=$(this).find(".jd").attr('data');
			if(item_num == 'E0005518') {//右耳语平均听阈
				$(this).find(".result").val(right_avg_bone);
			}
		});
	}
}
function change_left_avg_bone() {
	if(left500_bone && left1000_bone && left2000_bone) {
		left_avg_bone = ( parseInt(left500_bone)+parseInt(left1000_bone)+parseInt(left2000_bone) )/3;
		left_avg_bone = Math.round(left_avg_bone);
		$("#dt_bone li").each(function(){
			var item_num=$(this).find(".jd").attr('data');
			if(item_num == 'E0005519') {//左耳语平均听阈
				$(this).find(".result").val(left_avg_bone);
			}
		});
	}
}
function change_double_avg_bone() {
	if(right3000_bone && right4000_bone && right6000_bone && left3000_bone && left4000_bone && left6000_bone ) {
		double_avg_bone = ( parseInt(right3000_bone)+parseInt(right4000_bone)+parseInt(right6000_bone)
					+ parseInt(left3000_bone)+parseInt(left4000_bone)+parseInt(left6000_bone) )/6;
		double_avg_bone = Math.round(double_avg_bone);
		$("#dt_bone li").each(function(){
			var item_num=$(this).find(".jd").attr('data');
			if(item_num == 'E0005520') {//双耳高频平均听阈
				$(this).find(".result").val(double_avg_bone);
			}
		});
	}
}
function change_right_weight_bone() {
	if(right500_bone && right1000_bone && right2000_bone && right4000_bone) {
		right_weight_bone = ( parseInt(right500_bone)+parseInt(right1000_bone)+parseInt(right2000_bone) ) * 0.9 / 3 + right4000_bone * 0.1;
		right_weight_bone = Math.round(right_weight_bone);
		$("#dt_bone li").each(function(){
			var item_num=$(this).find(".jd").attr('data');
			if(item_num == 'E0005530') {//右耳平均听阈加权值
				$(this).find(".result").val(right_weight_bone);
			}
		});
	}
}
function change_left_weight_bone() {
	if(left500_bone && left1000_bone && left2000_bone && left4000_bone) {
		left_weight_bone = ( parseInt(left500_bone)+parseInt(left1000_bone)+parseInt(left2000_bone) ) * 0.9 / 3 + left4000_bone * 0.1;
		left_weight_bone = Math.round(left_weight_bone);
		$("#dt_bone li").each(function(){
			var item_num=$(this).find(".jd").attr('data');
			if(item_num == 'E0005529') {//左耳平均听阈加权值
				$(this).find(".result").val(left_weight_bone);
			}
		});
	}
}

function create_jielun_bone() {
	if(!right500_bone || !right1000_bone || !right2000_bone || !left500_bone || !left1000_bone || !left2000_bone
			|| !right3000_bone || !right4000_bone || !right6000_bone || !left3000_bone || !left4000_bone || !left6000_bone ) {
		return;
	}
	var ydp = '';
	if(parseInt(right500_bone) > 25) {
		ydp = ydp + "500Hz" + right500_bone + "dB,";
	}
	if(parseInt(right1000_bone) > 25) {
		ydp = ydp + "1000Hz" + right1000_bone + "dB,";
	}
	if(parseInt(right2000_bone) > 25) {
		ydp = ydp + "2000Hz" + right2000_bone + "dB,";
	}
	if(ydp != '') {
		ydp = "右耳语频有改变：" +ydp.substring(0, ydp.length-1)+ ";";
	}

	var ygp = '';
	if(parseInt(right3000_bone) > 25) {
		ygp = ygp + "3000Hz" + right3000_bone + "dB,";
	}
	if(parseInt(right4000_bone) > 25) {
		ygp = ygp + "4000Hz" + right4000_bone + "dB,";
	}
	if(parseInt(right6000_bone) > 25) {
		ygp = ygp + "6000Hz" + right6000_bone + "dB,";
	}
	if(ygp != '') {
		ygp = "右耳高频有改变：" +ygp.substring(0, ygp.length-1)+ ";";
	}

	var zdp = '';
	if(parseInt(left500_bone) > 25) {
		zdp = zdp + "500Hz" + left500_bone + "dB,";
	}
	if(parseInt(left1000_bone) > 25) {
		zdp = zdp + "1000Hz" + left1000_bone + "dB,";
	}
	if(parseInt(left2000_bone) > 25) {
		zdp = zdp + "2000Hz" + left2000_bone + "dB,";
	}
	if(zdp != '') {
		zdp = "左耳语频有改变：" +zdp.substring(0, zdp.length-1)+ ";";
	}
	
	var zgp = '';
	if(parseInt(left3000_bone) > 25) {
		zgp = zgp + "3000Hz" + left3000_bone + "dB,";
	}
	if(parseInt(left4000_bone) > 25) {
		zgp = zgp + "4000Hz" + left4000_bone + "dB,";
	}
	if(parseInt(left6000_bone) > 25) {
		zgp = zgp + "6000Hz" + left6000_bone + "dB,";
	}
	if(zgp != '') {
		zgp = "左耳高频有改变：" +zgp.substring(0, zgp.length-1)+ ";";
	}
	
	var jl = ydp + ygp + zdp + zgp;
	if(jl != '') {
		jl = '骨导：'+'\r\n'+jl;
		jl = jl + "双耳高频平均听阈：" + double_avg_bone + "dB;";
		if(isWeight) {
			jl = jl + "左耳平均听阈加权值：" + left_weight_bone + "dB;" +
			"右耳平均听阈加权值：" + right_weight_bone + "dB。";
		} else {
			jl = jl + "左耳语频平均听阈：" + left_avg_bone + "dB;" +
			"右耳语频平均听阈：" + right_avg_bone + "dB。";
		}
	} else {
		jl = '骨导：'+'\r\n'+jl;
		jl += "未见异常";
	}
	$('#jielun_bone').val(jl);
}

function dealWithChange_bone() {
	if(isWeight) {
		change_right_weight_bone();
		change_left_weight_bone();
	} else {
		change_right_avg_bone();
		change_left_avg_bone();
	}
	change_double_avg_bone();
	create_jielun_bone();
}

function sr_bone(sa){
	var item_num=item_ids=$(sa).parent().find('.jd').attr('data');
	var item_ids=$(sa).parent().find('.jd').val();
	var exam_result=$('#dt_bone li #item_'+item_ids).val();
	if(exam_result == '') {
		return;
	}
	if(item_num == 'E0005506') {//右耳500
		right500_bone = exam_result-frequency500;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency500);
	} else if(item_num == 'E0005507') {//右耳1000
		right1000_bone = exam_result-frequency1000;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency1000);
	} else if(item_num == 'E0005508') {//右耳2000
		right2000_bone = exam_result-frequency2000;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency2000);
	} else if(item_num == 'E0005509') {//右耳3000
		right3000_bone = exam_result-frequency3000;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency3000);
	} else if(item_num == 'E0005510') {//右耳4000
		right4000_bone = exam_result-frequency4000;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency4000);
	} else if(item_num == 'E0005511') {//右耳6000
		right6000_bone = exam_result-frequency6000;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency6000);
	} else if(item_num == 'E0005512') {//左耳500
		left500_bone = exam_result-frequency500;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency500);
	} else if(item_num == 'E0005513') {//左耳1000
		left1000_bone = exam_result-frequency1000;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency1000);
	} else if(item_num == 'E0005514') {//左耳2000
		left2000_bone = exam_result-frequency2000;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency2000);
	} else if(item_num == 'E0005515') {//左耳3000
		left3000_bone = exam_result-frequency3000;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency3000);
	} else if(item_num == 'E0005516') {//左耳4000
		left4000_bone = exam_result-frequency4000;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency4000);
	} else if(item_num == 'E0005517') {//左耳6000
		left6000_bone = exam_result-frequency6000;
		$(sa).parent().find('.result_correctionValue').val(exam_result-frequency6000);
	}
	dealWithChange_bone();
	
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
//	jiel();
//	$(sa).focus();
}
