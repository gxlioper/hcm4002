var teamAmountViewFlag;
$(document).ready(function () {
	//$("[name = chkItem]:checkbox").attr("checked", true);	
	$('#exam_num').textbox('textbox').focus();  
	$('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    }); 
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    });
	
	$('#exam_num').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
           examnumenter();
        }
    });
	
	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if (data.length>0) {
				$('#tjlx').combobox('setValue', data[0].id);
			}
		}
	});
	
	$('#creater').combobox({
		url : 'getFinalDoctorList.action?operation_type=4',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 200,//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if (data.length>0) {
				$('#creater').combobox('setValue', data[0].id);
			}
		}
	});
	
	//数据来源
	$('#data_source').combobox({
		url : 'getDatadis.action?com_Type=SJLY',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'data_code_children',
		textField : 'name'
	});
		
	$('#statusss').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			
		}
	});
	
	$('#exam_type').combobox({
		url : 'getDatadis.action?com_Type=EXAMTYPE',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			
		}
	});
	$('#conn_rylb').combobox({
		url : 'getDatadis.action?com_Type=RYLB',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			
		}
	});
	$("#com_batch").combobox({
		valueField: 'id',
        textField: 'text',
        hasDownArrow:false,
        mode:'remote',
        url:'getComByBatchList.action',
        onSelect:function(record){
        	$("#com_batch_id").val(record.id);
			$.ajax({
				url : 'getbatchcompanyshow.action',
				data : {"company_id":record.id.split('-')[0]},
				async : true,
				type : "post",//数据发送方式   
				success : function(text) {
					var obj = eval(text);
					$("#comnames").val(obj[0].text);
				}
			});
			var checkedItems = $('#groupusershow').datagrid('getChecked');
			var sex = '';
    	    $.each(checkedItems, function(index, item){
				sex = item.sex;
    	    });
			$('#group').combobox({
				url : 'getdjtTgroupInfoDo.action?batch_id='+record.id.split('-')[1]+'&sex='+encodeURI(encodeURI(sex)),
				editable : false, //不可编辑状态
				cache : false,
				panelHeight : 'auto',//自动高度适合
				valueField : 'id',
				textField : 'group_name',
				onLoadSuccess : function(data) {
					
				}
			});
        }
	});
	getZybUserListGrid();	
});


//获取菜单batch
function f_getBatch(id) {
	$('#batch_ids').combobox({
		url : 'getCompanForBatch.action?company_id='+id,
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'batch_name',
		onLoadSuccess : function(data) {
			if(data[0]!=undefined){
				$('#batch_ids').combobox('setValue', data[0].id);				
			}
		}
	});
}

//-------------------------------------------单位信息显示-----------------------------------------------------
/**
 * 模糊查询公司信息
 */
 var hc_com_list,com_Namess;
 function select_com_list(com_Namess){
 	var url='companychangshow.action';
 	var data={"name":com_Namess};
 	load_post(url,data,select_com_list_sess);
 	}

/**
 * 显示树形结构
 * @param data
 * @returns
 */
function select_com_list_sess(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#com_name_list_div").html(mydtree.toString());
			$("#com_name_list_div").css("display","block");
			
		}

/**
 * 点击树设置内容
 * @param id
 * @param name
 * @returns
 */
	function setvalue(id,name){
		$("#company_id").val(id);
		$("#com_Name").textbox('setValue',name);
		$("#com_name_list_div").css("display","none");	
		f_getdept(id);
		f_getBatch(id);
	}

//单位失去焦点
var hc_mous_select1=false;
function select_com_list_over(){
	if(!hc_mous_select1){
	$("#com_name_list_div").css("display","none");
	}
	}

function select_com_list_mover(){
	hc_mous_select1=true;
	}
function select_com_list_amover(){
	hc_mous_select1=false;
	}

//显示部门
function f_getdept(company_id) {
	$('#levelss').combobox({
		url : 'getCompanForDept2.action?company_id='+company_id,
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		multiple:true,
		textField : 'dep_Name',
		onLoadSuccess : function(data) {
			if((data!=undefined)&&(data.length>0)){
			$('#levelss').combobox('setValue', data[0].id);	
			}
		},
		filter: function(q, row){
			var opts = $(this).combobox('options');
			var text = row[opts.textField];//下拉的对应选项的汉字
			var pyjp = pinyinUtil.getFirstLetter(text).toLowerCase();
	 		if(row[opts.textField].indexOf(q) > -1 || pyjp.indexOf(q.toLowerCase()) > -1){
	 			return true;
	 		}	
		}
	});
}

 //---------------------------------------显示人员------------------------------------------------------
 function getZybUserListGrid(){
	 var chk_value ="";    
	  $('input[name = chkItem]:checked').each(function(){    
	   chk_value=chk_value+","+($(this).val());    
	  }); 
	  if(chk_value.length>1){
		  chk_value=chk_value.substring(1,chk_value.length);
	  }
	  
	  //部门
	  var levels = $("#levelss").combobox('getValues');
	  var levelss = new Array();
	  for(i=0;i<levels.length;i++){
		  if(levels[i] != ''){
			  levelss.push("'"+levels[i]+"'"); 
		  }
	  }
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":$("#company_id").val(),
				 "levels":levelss.toString(), //部门
				 "exam_num":$("#exam_num").val(),
				 "time1":$("#start_date").datebox('getValue'),	
				 "time2":$("#end_date").datebox('getValue'),	
				 "time3":$("#time3").datebox('getValue'),	
				 "time4":$("#time4").datebox('getValue'),
				 "yuyue_date1":$("#yuyue_date1").datebox('getValue'),
				 "yuyue_date2":$("#yuyue_date2").datebox('getValue'),
				 "custname":$("#custname").val(),
				 "chkItem":chk_value,
				 "exam_type":document.getElementsByName("exam_type")[0].value,
				 "creater":$("#creater").datebox('getValue'),
				 "status":document.getElementsByName("statusss")[0].value,	
				 "employeeID":'',
				 "arch_num":$("#arch_num").val(),
				 "id_num":$("#id_num").val(),
				 "ren_type":$('#conn_rylb').combobox('getValue'),
				 "djdstatuss":$('#djdstatuss').combobox('getValue'),
				 "data_source":$('#data_source').combobox('getValue'),  //数据来源
				 "batch_id":$('#batch_ids').combobox('getValue'),
				 "customer_type":$('#tjlx').combobox('getValue'), //体检类型
				 "sex":$('#sex').combobox('getValue'),  //性别
		 };
	     $("#groupusershow").datagrid({
		 url:'getZybUserList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 pageNumber:1,
	     //pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100,300,500,1000],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
            {align:'center',field:'readFlags',title:'上传状态',width:80,sortable:true},
            {align:'center',field:'notices',title:'说明',width:40,sortable:true},
            {align:'center',field:'exam_num',title:tjhname,width:100,sortable:true},
            {align:'center',field:'arch_num',title:dahname,width:100,sortable:true},
            {align:'center',field:'name',title:'姓名',width:80,sortable:true,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].userInfo.name;}},
            {align:'center',field:'idcardTypeName',title:'证件类别',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].userInfo.idcardTypeName;}},
            {align:'center',field:'idcardCode',title:'身份证号',width:150,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].userInfo.idcardCode;}},	 	
            {align:'center',field:'sex',title:'性别',width:30,sortable:true,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].userInfo.sex;}},
            {align:'center',field:'birthday',title:'生日',width:80,sortable:true,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].userInfo.birthday;}},
            {align:'center',field:'telPhone',title:'电话',width:80,sortable:true,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].userInfo.telPhone;}},
            {align:'center',field:'workshop',title:'车间',width:40,sortable:true,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].userInfo.workshop;}},
            {align:'center',field:'jobName',title:'工种',width:40,sortable:true,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].userInfo.jobName;}},
//            {align:'center',field:'radiationType',title:'放射工种',width:40,sortable:true,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].userInfo.radiationType;}},
            {align:'center',field:'maritalStatusName',title:'婚否',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].userInfo.maritalStatusName;}},
            {align:'center',field:'creditCode',title:'单位统一信用代码',width:150,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].empInfo.creditCode;}},
            {align:'center',field:'employerName',title:'单位名称',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].empInfo.employerName;}},
            {align:'center',field:'employerPhone',title:'单位联系电话',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].empInfo.employerPhone;}},
            {align:'center',field:'jobNumber',title:'工号',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].empInfo.jobNumber;}},
            {align:'center',field:'orgCode',title:'体检机构编码',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].orgInfo.orgCode;}},
            {align:'center',field:'orgName',title:'体检机构名称',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].orgInfo.orgName;}},
            {align:'center',field:'code',title:'个案卡编号',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.code;}},
            {align:'center',field:'typeName',title:'个案卡类别',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.typeName;}},
            {align:'center',field:'seniorityYear',title:'总工龄年',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.seniorityYear+'年';}},
            {align:'center',field:'seniorityMonth',title:'总工龄月',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.seniorityMonth+'月';}},
            {align:'center',field:'exposureYear',title:'接触工龄年',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.exposureYear+'年';}},
            {align:'center',field:'exposureMonth',title:'接触工龄月',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.exposureMonth+'月';}},
            {align:'center',field:'checkTypeName',title:'检查类型',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.checkTypeName;}},
            {align:'center',field:'bodyCheckTypeName',title:'体检类型',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.bodyCheckTypeName;}},
            {align:'center',field:'previousCardId',title:'复检对应上次的个案卡编号',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.previousCardId;}},
            {align:'center',field:'checkTime',title:'体检时间',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.checkTime;}},
            {align:'center',field:'reportTime',title:'体检报告时间',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.reportTime;}},
            {align:'center',field:'checkResultName',title:'体检结果',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.checkResultName;}},
            {align:'center',field:'suggest',title:'主检建议',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.suggest;}},
            {align:'center',field:'checkDoctor',title:'主检医生',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].cardInfo.checkDoctor;}},
            {align:'center',field:'hazardFactorList',title:'危害因素',width:300,formatter:function(val, row, index){
            	var str = '';
            	var hazardFactors = row.body.reportCardList.reportCard[0].hazardFactorList.hazardFactor;
            	for(var i in hazardFactors) {
            		str += (hazardFactors[i].hazardName+'('+hazardFactors[i].hazardYear+'年'+hazardFactors[i].hazardMonth+'月)<br>');
            	}
            	return str;
            }},
            {align:'center',field:'itemList',title:'检查项目',width:1500,formatter:function(val, row, index){
            	var str = '<PRE>';
            	var items = row.body.reportCardList.reportCard[0].itemList.item;
            	for(var i in items) {
            		str += ('项目：'+items[i].itemName+'&#9科室：'+items[i].department+'&#9结果：'+items[i].result+'&#9类别：'+items[i].type+'&#9计量单位：'+items[i].unit+'&#9最大：'+items[i].max+'&#9最小：'+items[i].min+'&#9结论：'+items[i].checkResult+'&#9合格标记：'+items[i].mark+'&#9检查日期：'+items[i].checkDate+'&#9医生：'+items[i].checkDoctor+'<br>');
            	}
            	str += '</PRE>';
            	return str;
            }},
            {align:'center',field:'conclusion',title:'诊断结论',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].diagnosisList.diagnosis[0].conclusion;}},
            {align:'center',field:'repeatItemNames',title:'复查项目',width:80,formatter:function(val, row, index){
            	var str = '';
            	var repeatItemNames = row.body.reportCardList.reportCard[0].diagnosisList.diagnosis[0].repeatItemList.repeatItemName;
            	for(var i in repeatItemNames) {
            		str += (repeatItemNames[i]+'<br>');
            	}
            	return str;
            }},
            {align:'center',field:'cdts',title:'职业禁忌证',width:180,formatter:function(val, row, index){
            	var str = '';
            	var cdts = row.body.reportCardList.reportCard[0].diagnosisList.diagnosis[0].cdtList.cdt;
            	for(var i in cdts) {
            		str += (cdts[i].cdtHazardName+'-'+cdts[i].cdtName+'<br>');
            	}
            	return str;
            }},
            {align:'center',field:'cdts',title:'疑似职业病',width:180,formatter:function(val, row, index){
            	var str = '';
            	var spts = row.body.reportCardList.reportCard[0].diagnosisList.diagnosis[0].sptList.spt;
            	for(var i in spts) {
            		str += (spts[i].sptHazardName+'-'+spts[i].sptName+'<br>');
            	}
            	return str;
            }},
            {align:'center',field:'otherDiseases',title:'其他疾病',width:80,formatter:function(val, row, index){
            	var str = '';
            	var otherDiseases = row.body.reportCardList.reportCard[0].diagnosisList.diagnosis[0].otherList.otherDisease;
            	for(var i in otherDiseases) {
            		str += (otherDiseases[i]+'<br>');
            	}
            	return str;
            }},
//            {align:'center',field:'questionnaireId',title:'问卷表编号',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.id;}},
//            {align:'center',field:'surveyDate',title:'填表日期',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.surveyDate;}},
//            {align:'center',field:'smkSituation',title:'吸烟情况',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.smkSituation;}},
//            {align:'center',field:'smkNum',title:'吸烟数',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.smkNum;}},
//            {align:'center',field:'smkYear',title:'烟龄（年）',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.smkYear;}},
//            {align:'center',field:'drkSituation',title:'饮酒情况',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.drkSituation;}},
//            {align:'center',field:'drkNum',title:'饮酒量ml/日',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.drkNum;}},
//            {align:'center',field:'drkYear',title:'酒龄（年）',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.drkYear;}},
//            {align:'center',field:'childrenNum',title:'现有子女数',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.childrenNum;}},
//            {align:'center',field:'abortionNum',title:'流产数',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.abortionNum;}},
//            {align:'center',field:'stillbirthNum',title:'死产数',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.stillbirthNum;}},
//            {align:'center',field:'prematureBirthNum',title:'早产数',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.prematureBirthNum;}},
//            {align:'center',field:'abnormalFetalNum',title:'异常胎数',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.abnormalFetalNum;}},
//            {align:'center',field:'childrenCondition',title:'子女健康状况',width:80,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.childrenCondition;}},
//            {align:'center',field:'menarcheAge',title:'初潮年龄',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.menarcheAge;}},
//            {align:'center',field:'period',title:'经期（天）',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.period;}},
//            {align:'center',field:'cycle',title:'周期（天）',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.cycle;}},
//            {align:'center',field:'menopauseAge',title:'绝经年龄',width:40,formatter:function(val, row, index){return row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.menopauseAge;}},
//            {align:'center',field:'occupationHostorys',title:'职业史',width:400,formatter:function(val, row, index){
//            	var str = '';
//            	var occupationHostorys = row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.occupationHistoryList.occupationHostory;
//            	for(var i in occupationHostorys) {
//            		str += ('开始日期：'+occupationHostorys[i].startDate+'&#9结束日期：'+occupationHostorys[i].endDate+'&#9部门车间：'+occupationHostorys[i].depWorkShop+'&#9工种：'+occupationHostorys[i].jobCode+'&#9有害因素：'+occupationHostorys[i].harmful+'&#9防护措施：'+occupationHostorys[i].protectiveMeasures+'&#9每日工作时数：'+occupationHostorys[i].dayNum
////            				+'&#9累积受照剂量：'+occupationHostorys[i].totalNum+'&#9过量照射史：'+occupationHostorys[i].overdose+'&#9职业照射种类：'+occupationHostorys[i].occuExposure+'&#9放射线种类：'+occupationHostorys[i].radiationType
//            				+'&#9防护措施：'+occupationHostorys[i].radProtectiveMeasures
//            				+'<br>');
//            	}
//            	return str;
//            }},
//            {align:'center',field:'diseaseHistorys',title:'既往病史',width:400,formatter:function(val, row, index){
//            	var str = '';
//            	var diseaseHistorys = row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.diseaseHistoryList.diseaseHistory;
//            	for(var i in diseaseHistorys) {
//            		str += ('疾病名称：'+diseaseHistorys[i].diseaseName+'&#9诊断日期：'+diseaseHistorys[i].diagnosisDate+'&#9诊断机构：'+diseaseHistorys[i].diagnosisOrg+'&#9治疗经过：'+diseaseHistorys[i].treatmentProcess+'&#9转归：'+diseaseHistorys[i].dieaseOutcome+'<br>');
//            	}
//            	return str;
//            }},
//            {align:'center',field:'occupationDieaseHistorys',title:'职业病史',width:400,formatter:function(val, row, index){
//            	var str = '';
//            	var occupationDieaseHistorys = row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.occupationDieaseHistoryList.occupationDieaseHistory;
//            	for(var i in occupationDieaseHistorys) {
//            		str += ('疾病名称：'+occupationDieaseHistorys[i].diseaseName+'&#9诊断日期：'+occupationDieaseHistorys[i].diagnosisDate+'&#9诊断机构：'+occupationDieaseHistorys[i].diagnosisOrg+'&#9是否痊愈：'+occupationDieaseHistorys[i].isRecovery+'<br>');
//            	}
//            	return str;
//            }},
//            {align:'center',field:'marriageHistorys',title:'婚姻史',width:400,formatter:function(val, row, index){
//            	var str = '';
//            	var marriageHistorys = row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.marriageHistoryList.marriageHistory;
//            	for(var i in marriageHistorys) {
//            		str += ('结婚日期：'+marriageHistorys[i].marriageDate
////            				+'&#9配偶接触放射线情况：'+marriageHistorys[i].spouseRadiation
//            				+'&#9配偶职业及健康情况：'+marriageHistorys[i].spouseOcpt+'<br>');
//            	}
//            	return str;
//            }},
//            {align:'center',field:'symptoms',title:'症状',width:400,formatter:function(val, row, index){
//            	var str = '';
//            	var symptoms = row.body.reportCardList.reportCard[0].healthSurvey.questionnaire.symptomList.symptom;
//            	for(var i in symptoms) {
//            		str += ('症状名称：'+symptoms[i].symptomName+'&#9其他症状描述：'+symptoms[i].otherDesc+'&#9检查医生：'+symptoms[i].checkDoctor+'&#9检查日期：'+symptoms[i].checkDate+'<br>');
//            	}
//            	return str;
//            }},
            
            
		    {align:'center',field:'exam_types',title:'体检类型',width:70},	
		 	{align:'center',field:'age',title:'年龄',width:40,sortable:true},
		 	{align:'center',field:'join_date',title:'体检日期',width:100,sortable:true},	 	
		 	{align:'center',field:'huishou',title:'回收',width:40},
		 	{align:'center',field:'freezename',title:'冻结',width:40},
		 	{align:'center',field:'status',title:'体检状态',width:70,sortable:true},
		 	{align:'center',field:'swuxuzongjian',title:'需要总检',width:80},
		 	{align:'center',field:'chargeType',title:'付费类型',width:80},
		 	{align:'center',field:'company',title:'单位',width:200},
		 	{align:'center',field:'group_name',title:'分组',width:200},
		 	{align:'center',field:'dep_name',title:'部门',width:150},
		 	{align:'center',field:'introducer',title:'介绍人',width:80},
		 	{align:'center',field:'chi_name',title:'创建者',width:80},
		 	{align:'center',field:'join_operatorName',title:'报到人',width:80},
		 	{align:'center',field:'final_doctor',title:'总检医生',width:80},
		 	{align:'center',field:'final_date',title:'总检时间',width:100,sortable:true},
		 	{align:'center',field:'check_doctor',title:'审核医生',width:80},
		 	{align:'center',field:'check_time',title:'审核时间',width:140},
		 	{align:'center',field:'exam_times',title:'完成时间',width:140},
		 	{align:'center',field:'set_name',title:'套餐',width:200},	
		 	{align:'center',field:'customer_type_name',title:'体检类别',width:80},
		 	]],	    	
	    	onLoadSuccess:function(value){ 
//	    		 $("#groupusershow").datagrid("hideColumn", "set_name"); // 设置隐藏列    
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	onDblClickRow : function(index, row) {	    		
	    		//examnumenterDou(row.exam_num);
			},
			height: window.screen.height-420,
		    nowrap:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
			fitColumns:false,
			fit:false,
		    striped:true,
	        toolbar:toolbar	       
	});
	}
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'确认上传',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, row){
    	        ids=ids+","+(row.exam_num);
    	    }); 	 
    	    if(ids=="" || ids==null){
    	    	$.messager.alert("操作提示", "请先选择某一行", "error");
    	    }else{
    	    	ids = ids.substring(1);
    	    	uploadZybData(ids);
    	    }
	    }
	}];
function  uploadZybData(ids){
		$.messager.confirm('提示信息','是否进行批量同步？',function(r){
	 if(r){
		 $.ajax({
				url : 'uploadZybData.action',
				data : {ids:ids},
				type : "post",//数据发送方式   
				success : function(text) {
					if (text.split("-")[0] == 'ok') {
						getZybUserListGrid();
						$.messager.alert("操作提示", text);
					} else {
						$.messager.alert("操作提示", text, "error");
					}
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
		 }
	});
}