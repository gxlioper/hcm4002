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
            {align:'center',field:'notices',title:'说明',width:180,sortable:true},
            {align:'center',field:'readFlags1',title:'单位上传状态',width:80,sortable:true},
            {align:'center',field:'notices1',title:'单位上传说明',width:100,sortable:true},
            {align:'center',field:'arch_num',title:dahname,width:100,sortable:true},
            {align:'center',field:'separation',title:'',width:10,formatter:function(val, row, index){return '<b>|</b><br><b>|</b><br><b>|</b><br><b>|</b>';}},
            //{align:'center',field:'RID',title:'业务系统主键',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.RID;}},
            {align:'center',field:'BHKORGAN_CODE',title:'体检机构编号',width:100,formatter:function(val, row, index){return row.person.TD_TJ_BHK.BHKORGAN_CODE;}},
            {align:'center',field:'BHK_CODE',title:'体检编号',width:100,formatter:function(val, row, index){return row.person.TD_TJ_BHK.BHK_CODE;}},
            {align:'center',field:'INSTITUTION_CODE',title:'社会信用代码',width:180,formatter:function(val, row, index){return row.person.TD_TJ_BHK.INSTITUTION_CODE;}},
            {align:'center',field:'CRPT_NAME',title:'企业名称',width:180,formatter:function(val, row, index){return row.person.TD_TJ_BHK.CRPT_NAME;}},
            {align:'center',field:'CRPT_ADDR',title:'企业注册地址',width:180,formatter:function(val, row, index){return row.person.TD_TJ_BHK.CRPT_ADDR;}},
            {align:'center',field:'PERSON_NAME',title:'人员姓名',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.PERSON_NAME;}},
            {align:'center',field:'sexName',title:'性别',width:40,formatter:function(val, row, index){return row.person.TD_TJ_BHK.sexName;}},
            {align:'center',field:'IDC',title:'身份证号',width:160,formatter:function(val, row, index){return row.person.TD_TJ_BHK.IDC;}},
            {align:'center',field:'BRTH',title:'出生日期',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.BRTH;}},
            {align:'center',field:'AGE',title:'年龄',width:40,formatter:function(val, row, index){return row.person.TD_TJ_BHK.AGE;}},
            {align:'center',field:'ISXMRDName',title:'婚否',width:40,formatter:function(val, row, index){return row.person.TD_TJ_BHK.ISXMRDName;}},
            {align:'center',field:'LNKTEL',title:'人员联系电话',width:100,formatter:function(val, row, index){return row.person.TD_TJ_BHK.LNKTEL;}},
            {align:'center',field:'DPT',title:'体检人员工作部门',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.DPT;}},
            {align:'center',field:'WRKNUM',title:'人员工号',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.WRKNUM;}},
            {align:'center',field:'WRKLNT',title:'总工龄年数',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.WRKLNT;}},
            {align:'center',field:'WRKLNTMONTH',title:'总工龄月数',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.WRKLNTMONTH;}},
            {align:'center',field:'TCHBADRSNTIM',title:'接害工龄年数',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.TCHBADRSNTIM;}},
            {align:'center',field:'TCHBADRSNMONTH',title:'接害工龄月数',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.TCHBADRSNMONTH;}},
            {align:'center',field:'WORK_NAME',title:'工种名称',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.WORK_NAME;}},
            {align:'center',field:'ONGUARD_STATEName',title:'在岗状态',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.ONGUARD_STATEName;}},
            {align:'center',field:'BHK_DATE',title:'体检日期',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.BHK_DATE;}},
            {align:'center',field:'BHKRST',title:'体检结果',width:180,formatter:function(val, row, index){return row.person.TD_TJ_BHK.BHKRST;}},
            {align:'center',field:'MHKADV',title:'主检建议',width:180,formatter:function(val, row, index){return row.person.TD_TJ_BHK.MHKADV;}},
            {align:'center',field:'VERDICT',title:'体检结论',width:180,formatter:function(val, row, index){return row.person.TD_TJ_BHK.VERDICT;}},
            {align:'center',field:'MHKDCT',title:'主检医师',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.MHKDCT;}},
            {align:'center',field:'BHK_TYPEName',title:'体检类型',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.BHK_TYPEName;}},
            {align:'center',field:'JDGDAT',title:'主检判定日期',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.JDGDAT;}},
            {align:'center',field:'BADRSN',title:'接害因素',width:220,formatter:function(val, row, index){return row.person.TD_TJ_BHK.BADRSN.replace(/，/g,'<br>');}},
            {align:'center',field:'IF_RHKName',title:'是否为复检',width:50,formatter:function(val, row, index){return row.person.TD_TJ_BHK.IF_RHKName;}},
            {align:'center',field:'LAST_BHK_CODE',title:'上次体检号',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.LAST_BHK_CODE;}},
            {align:'center',field:'PSN_TYPEName',title:'人员类型',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.PSN_TYPEName;}},
            {align:'center',field:'RPT_PRINT_DATE',title:'报告打印日期',width:80,formatter:function(val, row, index){return row.person.TD_TJ_BHK.RPT_PRINT_DATE;}},
            /*{align:'center',field:'TD_TJ_BADRSNS',title:'接触危害因素',width:80,formatter:function(val, row, index){
            	var str = '';
            	var TD_TJ_BADRSNS = row.person.TD_TJ_BADRSNSES.TD_TJ_BADRSNS;
            	for(var i in TD_TJ_BADRSNS) {
            		str += (//TD_TJ_BADRSNS[i].RID+'-'+
            				TD_TJ_BADRSNS[i].BADRSN_CODE+'<br>');
            	}
            	return str;
            }},*/
            {align:'center',field:'TD_TJ_BHKSUB',title:'体检结果表',width:2400,formatter:function(val, row, index){
            	var str = '<PRE>';
            	var TD_TJ_BHKSUB = row.person.TD_TJ_BHKSUBS.TD_TJ_BHKSUB;
            	for(var i in TD_TJ_BHKSUB) {
            		str += (//'主键：'+TD_TJ_BHKSUB[i].RID+'&#9'+
            				'体检项目编码：'+TD_TJ_BHKSUB[i].ITMCOD
        				+'&#9计量单位：'+TD_TJ_BHKSUB[i].MSRUNT
        				+'&#9参考值：'+TD_TJ_BHKSUB[i].ITEM_STDVALUE
        				+'&#9体检结果：'+TD_TJ_BHKSUB[i].RESULT
        				+'&#9合格标记：'+TD_TJ_BHKSUB[i].RGLTAG
        				+'&#9结果偏高偏低标记：'+TD_TJ_BHKSUB[i].RST_DESC
        				+'&#9是否缺项：'+TD_TJ_BHKSUB[i].IF_LACK
        				+'&#9检查日期：'+TD_TJ_BHKSUB[i].CHKDAT
        				+'&#9检查医生：'+TD_TJ_BHKSUB[i].CHKDOCT
        				+'&#9判断模式：'+TD_TJ_BHKSUB[i].JDGPTN
        				+'&#9最小值：'+TD_TJ_BHKSUB[i].MINVAL
        				+'&#9最大值：'+TD_TJ_BHKSUB[i].MAXVAL
        				+'&#9诊断结论：'+TD_TJ_BHKSUB[i].DIAG_REST
        				+'<br>');
            	}
            	str += '</PRE>';
            	return str;
            }},
            {align:'center',field:'TD_TJ_MHKRST',title:'主检判断主检结论',width:180,formatter:function(val, row, index){
            	var str = '';
            	var TD_TJ_MHKRST = row.person.TD_TJ_MHKRSTS.TD_TJ_MHKRST;
            	for(var i in TD_TJ_MHKRST) {
            		str += (//TD_TJ_MHKRST[i].RID+'-'+
            				TD_TJ_MHKRST[i].BHKRST_CODE+'<br>');
            	}
            	return str;
            }},
            {align:'center',field:'TD_TJ_EMHISTORY',title:'职业史信息',width:800,formatter:function(val, row, index){
            	var str = '<PRE>';
            	var TD_TJ_EMHISTORY = row.person.TD_TJ_EMHISTORYS.TD_TJ_EMHISTORY;
            	for(var i in TD_TJ_EMHISTORY) {
            		str += (//'主键：'+TD_TJ_EMHISTORY[i].RID+'&#9'+
        				'类型编码：'+TD_TJ_EMHISTORY[i].HIS_TYPE
        				+'&#9排序号：'+TD_TJ_EMHISTORY[i].NUM
        				+'&#9起止日期：'+TD_TJ_EMHISTORY[i].STASTP_DATE
        				+'&#9工作单位名称：'+TD_TJ_EMHISTORY[i].UNIT_NAME
        				+'&#9部门车间：'+TD_TJ_EMHISTORY[i].DEPARTMENT
        				+'&#9工种：'+TD_TJ_EMHISTORY[i].WORK_TYPE
        				+'&#9接触有害因素：'+TD_TJ_EMHISTORY[i].PRFRAYSRT
        				+'&#9每日工作时数或工作量：'+TD_TJ_MHKRST[i].PRFWRKLOD
        				+'&#9职业史累积受照剂量：'+TD_TJ_EMHISTORY[i].PRFSHNVLU
        				+'&#9职业史过量照射史：'+TD_TJ_EMHISTORY[i].PRFEXCSHN
        				+'&#9职业照射种类：'+TD_TJ_EMHISTORY[i].PRFRAYSRT2
        				+'&#9职业照射种类代码：'+TD_TJ_EMHISTORY[i].PRFRAYSRTCODS
        				+'&#9放射线种类：'+TD_TJ_EMHISTORY[i].FSSZL
        				+'&#9防护措施：'+TD_TJ_EMHISTORY[i].DEFEND_STEP
        				+'&#9检查日期：'+TD_TJ_EMHISTORY[i].CHKDAT
        				+'&#9检查医生：'+TD_TJ_EMHISTORY[i].CHKDOCT
        				+'<br>');
                	}
            	str += '</PRE>';
            	return str;
            }},
            {align:'center',field:'TD_TJ_SYMPTOM',title:'症状信息',width:200,formatter:function(val, row, index){
            	var str = '<PRE>';
            	var TD_TJ_SYMPTOM = row.person.TD_TJ_SYMPTOMS.TD_TJ_SYMPTOM;
            	for(var i in TD_TJ_SYMPTOM) {
            		str += (//'主键：'+TD_TJ_SYMPTOM[i].RID+'&#9'+
        				'症状编码：'+TD_TJ_SYMPTOM[i].SYMPTOM_CODE
        				+'&#9其他症状：'+TD_TJ_SYMPTOM[i].OTHSYM
        				+'&#9检查日期：'+TD_TJ_SYMPTOM[i].CHKDAT
        				+'&#9检查医生：'+TD_TJ_SYMPTOM[i].CHKDOCT
        				+'<br>');
            	}
            	str += '</PRE>';
            	return str;
            }},
            {align:'center',field:'TD_BHK_ANAMNESIS',title:'既往病史',width:200,formatter:function(val, row, index){
            	var str = '<PRE>';
            	var TD_BHK_ANAMNESIS = row.person.TD_BHK_ANAMNESISES.TD_BHK_ANAMNESIS;
            	for(var i in TD_BHK_ANAMNESIS) {
            		str += (//'主键：'+TD_BHK_ANAMNESIS[i].RID+'&#9'+
        				'疾病名称：'+TD_BHK_ANAMNESIS[i].HSTNAM
        				+'&#9诊断日期：'+TD_BHK_ANAMNESIS[i].HSTDAT
        				+'&#9诊断单位：'+TD_BHK_ANAMNESIS[i].HSTUNT
        				+'&#9治疗经过：'+TD_BHK_ANAMNESIS[i].HSTCRUPRC
        				+'&#9转归：'+TD_BHK_ANAMNESIS[i].HSTLPS
        				+'<br>');
            	}
            	str += '</PRE>';
            	return str;
            }},
            {align:'center',field:'TD_TJ_SUPOCCDISELIST',title:'疑似职业病',width:180,formatter:function(val, row, index){
            	var str = '';
            	var TD_TJ_SUPOCCDISELIST = row.person.TD_TJ_SUPOCCDISELISTS.TD_TJ_SUPOCCDISELIST;
            	for(var i in TD_TJ_SUPOCCDISELIST) {
            		str += (//TD_TJ_SUPOCCDISELIST[i].RID+'-'+
            				TD_TJ_SUPOCCDISELIST[i].BADRSN+'-'+TD_TJ_SUPOCCDISELIST[i].OCCDISE_CODE+'<br>');
            	}
            	return str;
            }},
            {align:'center',field:'TD_TJ_CONTRAINDLIST',title:'职业禁忌证',width:180,formatter:function(val, row, index){
            	var str = '';
            	var TD_TJ_CONTRAINDLIST = row.person.TD_TJ_CONTRAINDLISTS.TD_TJ_CONTRAINDLIST;
            	for(var i in TD_TJ_CONTRAINDLIST) {
            		str += (//TD_TJ_CONTRAINDLIST[i].RID+'-'+
            				TD_TJ_CONTRAINDLIST[i].BADRSN+'-'+TD_TJ_CONTRAINDLIST[i].CONTRAIND_CODE+'<br>');
            	}
            	return str;
            }},
            
            {align:'center',field:'separation',title:'',width:10},
//            {align:'center',field:'CRPT_RID',title:'企业主键',width:80,formatter:function(val, row, index){return row.person.company.RID;}},
            {align:'center',field:'CRPT_NAME',title:'企业名称',width:80,formatter:function(val, row, index){return row.person.company.CRPT_NAME;}},
            {align:'center',field:'CRPT_INSTITUTION_CODE',title:'社会信用代码',width:180,formatter:function(val, row, index){return row.person.company.INSTITUTION_CODE;}},
            {align:'center',field:'ZONE_CODE',title:'所属地区',width:100,formatter:function(val, row, index){return row.person.company.ZONE_CODE;}},
            {align:'center',field:'INDUS_TYPE_CODE',title:'行业类别编码',width:80,formatter:function(val, row, index){return row.person.company.INDUS_TYPE_CODE;}},
            {align:'center',field:'ECONOMY_CODE',title:'经济类型编码',width:80,formatter:function(val, row, index){return row.person.company.ECONOMY_CODE;}},
            {align:'center',field:'CRPT_SIZE_CODE',title:'企业规模编码',width:80,formatter:function(val, row, index){return row.person.company.CRPT_SIZE_CODE;}},
            {align:'center',field:'WORK_FORCE',title:'职工人数',width:80,formatter:function(val, row, index){return row.person.company.WORK_FORCE;}},
            {align:'center',field:'HOLD_CARD_MAN',title:'接触职业病危害因素人数',width:80,formatter:function(val, row, index){return row.person.company.HOLD_CARD_MAN;}},
            {align:'center',field:'ADDRESS',title:'单位注册地址',width:80,formatter:function(val, row, index){return row.person.company.ADDRESS;}},
            {align:'center',field:'PHONE',title:'法人联系电话',width:80,formatter:function(val, row, index){return row.person.company.PHONE;}},
            {align:'center',field:'CORPORATE_DEPUTY',title:'法人代表',width:80,formatter:function(val, row, index){return row.person.company.CORPORATE_DEPUTY;}},
            {align:'center',field:'WORKMAN_NUM',title:'生产工人数',width:80,formatter:function(val, row, index){return row.person.company.WORKMAN_NUM;}},
            {align:'center',field:'WORKMISTRESS_NUM',title:'接触职业病危害因素女工人数',width:80,formatter:function(val, row, index){return row.person.company.WORKMISTRESS_NUM;}},
            {align:'center',field:'POSTALCODE',title:'单位注册邮编',width:80,formatter:function(val, row, index){return row.person.company.POSTALCODE;}},
            {align:'center',field:'WORK_AREA',title:'经营面积',width:80,formatter:function(val, row, index){return row.person.company.WORK_AREA;}},
            {align:'center',field:'REGISTER_FUND',title:'注册资金',width:80,formatter:function(val, row, index){return row.person.company.REGISTER_FUND;}},
            {align:'center',field:'SAFETY_PRINCIPAL',title:'职业卫生安全负责人',width:80,formatter:function(val, row, index){return row.person.company.SAFETY_PRINCIPAL;}},
            {align:'center',field:'FILING_DATE',title:'建档日期',width:80,formatter:function(val, row, index){return row.person.company.FILING_DATE;}},
            {align:'center',field:'BUILD_DATE',title:'建厂日期',width:80,formatter:function(val, row, index){return row.person.company.BUILD_DATE;}},
            {align:'center',field:'LINKMAN1',title:'检测联系人',width:80,formatter:function(val, row, index){return row.person.company.LINKMAN1;}},
            {align:'center',field:'POSITION1',title:'检测联系人职务',width:80,formatter:function(val, row, index){return row.person.company.POSITION1;}},
            {align:'center',field:'LINKPHONE1',title:'检测联系人电话',width:80,formatter:function(val, row, index){return row.person.company.LINKPHONE1;}},
            {align:'center',field:'LINKMAN2',title:'体检联系人',width:80,formatter:function(val, row, index){return row.person.company.LINKMAN2;}},
            {align:'center',field:'POSITION2',title:'体检联系人职务',width:80,formatter:function(val, row, index){return row.person.company.POSITION2;}},
            {align:'center',field:'LINKPHONE2',title:'体检联系人电话',width:80,formatter:function(val, row, index){return row.person.company.LINKPHONE2;}},
            {align:'center',field:'SAFEPOSITION',title:'安全联系人职务',width:80,formatter:function(val, row, index){return row.person.company.SAFEPOSITION;}},
            {align:'center',field:'SAFEPHONE',title:'职业卫生安全联系人电话',width:80,formatter:function(val, row, index){return row.person.company.SAFEPHONE;}},
            {align:'center',field:'SUBJE_CONN',title:'隶属关系',width:80,formatter:function(val, row, index){return row.person.company.SUBJE_CONN;}},
            {align:'center',field:'ENROL_ADDRESS',title:'作业场所地址',width:80,formatter:function(val, row, index){return row.person.company.ENROL_ADDRESS;}},
            {align:'center',field:'ENROL_POSTALCODE',title:'作业场所邮政编码',width:80,formatter:function(val, row, index){return row.person.company.ENROL_POSTALCODE;}},
            {align:'center',field:'OCC_MANA_OFFICE',title:'职业卫生管理机构',width:80,formatter:function(val, row, index){return row.person.company.OCC_MANA_OFFICE;}},
            
//            {align:'center',field:'separation',title:'',width:10,sortable:true},
//		    {align:'center',field:'exam_types',title:'体检类型',width:70},	
//		 	{align:'center',field:'join_date',title:'体检日期',width:100,sortable:true},	 	
//		 	{align:'center',field:'huishou',title:'回收',width:40},
//		 	{align:'center',field:'freezename',title:'冻结',width:40},
//		 	{align:'center',field:'status',title:'体检状态',width:70,sortable:true},
//		 	{align:'center',field:'swuxuzongjian',title:'需要总检',width:80},
//		 	{align:'center',field:'chargeType',title:'付费类型',width:80},
//		 	{align:'center',field:'company',title:'单位',width:200},
//		 	{align:'center',field:'group_name',title:'分组',width:200},
//		 	{align:'center',field:'dep_name',title:'部门',width:150},
//		 	{align:'center',field:'introducer',title:'介绍人',width:80},
//		 	{align:'center',field:'chi_name',title:'创建者',width:80},
//		 	{align:'center',field:'join_operatorName',title:'报到人',width:80},
//		 	{align:'center',field:'final_doctor',title:'总检医生',width:80},
//		 	{align:'center',field:'final_date',title:'总检时间',width:100,sortable:true},
//		 	{align:'center',field:'check_doctor',title:'审核医生',width:80},
//		 	{align:'center',field:'check_time',title:'审核时间',width:140},
//		 	{align:'center',field:'exam_times',title:'完成时间',width:140},
//		 	{align:'center',field:'set_name',title:'套餐',width:200},	
		 	]],	    	
	    	onLoadSuccess:function(value){ 
//	    		 $("#groupusershow").datagrid("hideColumn", "set_name"); // 设置隐藏列    
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
//	    	onDblClickRow : function(index, row) {	    		
	    		//examnumenterDou(row.exam_num);
//			},
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