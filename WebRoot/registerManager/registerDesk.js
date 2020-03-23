var teamAmountViewFlag;
$(document).ready(function () {
	teamAmountViewFlag = $("#teamAmountViewFlag").val();
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
				url : 'getdjtTgroupInfoDo.action?batch_id='+record.id.split('-')[1]+'&sex='+sex,
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
	getgroupuserGrid();	
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
/**
 * 体检人数
 */
function numberOfpeople(){
    if($('.join_cheke').attr('checked')=='checked'){
        model = {
            time1: $('#start_date').textbox('getValue'),
            time2: $('#end_date').textbox('getValue')
        }
        $.ajax({
            url:'getNumberOfpeople.action',
            data:model,
            type:'post',
            dataType:'json',
            success:function (text) {
                if(text!=''){
                    $('#join_num').text("体检总人数:"+text.join_num+"人")
                }
            }
        })
    } else {
        $('#join_num').text("体检总人数:0人")
    }
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

//体检号报到
function examnumenter()// enter 键  
{
			if(($("#exam_num").val()!='')&&($("#exam_num").val().length>0)){
				getgroupuserGrid();
				 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
			 $.ajax({
					url : 'djtcheckexamInfoStatus.action',
					data : {
						    exam_num:$("#exam_num").val()
							},
							type : "post",//数据发送方式   
							success : function(text) {
								$(".loading_div").remove();
								if (text.split("-")[0] == 'ok') {//一个体检信息
									   $("#exam_num").textbox('setValue',text.split("-")[1]);
										$.messager.confirm('提示信息','是否报到？',function(r){
										if(r){
											
											$("#exam_num").textbox('textbox').select();
											$('#exam_num').textbox('textbox').focus();
											 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
											 $("body").prepend(str);
											$.ajax({
												url : 'djtexamInfoStatusdo.action',
												data : {
													    exam_num:$("#exam_num").val()
														},
														type : "post",//数据发送方式   
														success : function(text) {
															$(".loading_div").remove();															
															if (text.split("-")[0] == 'ok') {
																$("#exam_num").textbox('textbox').select();
																$('#exam_num').textbox('textbox').focus();
																getgroupuserGrid();
																baodaody($("#exam_num").val());
															} else {
																$("#exam_num").textbox('textbox').select();
																$('#exam_num').textbox('textbox').focus();
																$.messager.alert("操作提示", text.split("-")[1], "error");
															}
															
														},
														error : function() {
															$(".loading_div").remove();
															$.messager.alert("操作提示", "操作错误", "error");					
														}
													});
	
										}
										});
									
								}  else {
									$("#exam_num").textbox('textbox').select();
									$('#exam_num').textbox('textbox').focus();
									$.messager.alert("操作提示", text.split("-")[1], "error");
								}
								
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});
		 	}     
}

//双击报到
var douexamnum;
function examnumenterDou(douexamnum)// enter 键  
{
			if((douexamnum!='')&&(douexamnum.length>0)){
				 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
			 $.ajax({
					url : 'djtcheckexamInfoStatus.action',
					data : {
						    exam_num:douexamnum
							},
							type : "post",//数据发送方式   
							success : function(text) {
								$(".loading_div").remove();
								if (text.split("-")[0] == 'ok') {//一个体检信息
										$.messager.confirm('提示信息','是否报到？',function(r){
										if(r){
											 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
											 $("body").prepend(str);
											$.ajax({
												url : 'djtexamInfoStatusdo.action',
												data : {
													    exam_num:douexamnum
														},
														type : "post",//数据发送方式   
														success : function(text) {
															$(".loading_div").remove();
															if (text.split("-")[0] == 'ok') {
																//报到成功																
																getgroupuserGrid();	
																baodaody(douexamnum);
															} else {
                                                                alert_autoClose("操作提示", text.split("-")[1], "error");
															}
															
														},
														error : function() {
															$(".loading_div").remove();
															$.messager.alert("操作提示", "操作错误", "error");					
														}
													});	
										     }
										});
									
								}  else {
                                    alert_autoClose("操作提示", text.split("-")[1], "error");
								}
								
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});
		 	}     
}

//身份证报到
var conreadcard=0;
function dosfzbd() {  
    if(conreadcard==0){
    	var sfz_div_code = $("#sfz_div_code").val();
    	var data=readCardSFZ(sfz_div_code); 
        if(data.error_flag=="0"){
        	var certno=data.certno;
        	if(certno.length==18){
        		conreadcard=1;
        		$('#id_num').textbox('setValue',certno);
        		$("#id_num").textbox('textbox').select();
				$('#id_num').textbox('textbox').focus();
				getgroupuserGrid();
				 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
				 var bords = data.birth;
				 var sfzage=0,sfzbirthday;
					if(bords.length==8){
						var birthday= bords.substring(0,4)+"-"+bords.substring(4,6)+"-"+bords.substring(6,8);
						sfzbirthday=birthday;						
						var myDate = new Date();
						var month = myDate.getMonth() + 1;
						var day = myDate.getDate();
						age = myDate.getFullYear() - birthday.substring(0, 4) - 1;
						if (birthday.substring(5, 7) < month || birthday.substring(5, 7) == month && birthday.substring(8, 10) <= day) {
						   age++;
						}
						sfzage=age;
					}else if(bords.length==10){
						var birthday= bords;
						sfzbirthday=birthday;					
						var myDate = new Date();
						var month = myDate.getMonth() + 1;
						var day = myDate.getDate();
						var age = myDate.getFullYear() - birthday.substring(0, 4) - 1;
						if (birthday.substring(5, 7) < month || birthday.substring(5, 7) == month && birthday.substring(8, 10) <= day) {
						    age++;
						}
						sfzage=age;
					}
			 $.ajax({
					url : 'djtcheckexamInfoStatus.action',
					data : {
						    id_num:certno,
						    custname:data.name,
						    sex:data.sex,
						    age:sfzage,
						    birthday:sfzbirthday,
						    address:data.address,
						    others:data.bmpFileData
							},
							type : "post",//数据发送方式   
							success : function(text) {
								$(".loading_div").remove();
								var exam_numsfz=text.split("-")[1];
								if (text.split("-")[0] == 'ok') {								
										$.messager.confirm('提示信息','是否报到？',function(r){
										if(r){
											
											$("#exam_num").textbox('textbox').select();
											$('#exam_num').textbox('textbox').focus();
											 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
											 $("body").prepend(str);
											$.ajax({
												url : 'djtexamInfoStatusdo.action',
												data : {
													    exam_num:exam_numsfz
														},
														type : "post",//数据发送方式   
														success : function(text) {
															conreadcard=0;
															$(".loading_div").remove();
															if (text.split("-")[0] == 'ok') {
																getgroupuserGrid();
																baodaody(exam_numsfz);
															} else {
																$.messager.alert("操作提示", text.split("-")[1], "error");
															}
															
														},
														error : function() {
															$(".loading_div").remove();
															$.messager.alert("操作提示", "操作错误", "error");					
														}
													});
	
										}else{
											conreadcard=0;
										}
										});
									
								} else if (text.split("-")[0] == 'okmore') {//多个体检信息
									conreadcard=0;
									$("#exam_num").textbox('textbox').select();
									$('#exam_num').textbox('textbox').focus();
									$("#dlg-custedit").dialog({
								 		title:'体检人员报到',
								 		href:'djtexambaodaoshow.action?id_num='+certno
								 	});
								 	$("#dlg-custedit").dialog('open');									
								} else {
									conreadcard=0;
									$("#exam_num").textbox('textbox').select();
									$('#exam_num').textbox('textbox').focus();
									$.messager.alert("操作提示", text.split("-")[1], "error");
								}
								
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});
        	}else{
        		$.messager.alert("操作提示", "读取身份证号码错误", "error");	
        	}
        }else{
//        	$.messager.alert("操作提示", "读取身份证失败", "error");	
        	$.messager.alert("操作提示", data.error_msg, "error");
        }
        
    }
}  

 //---------------------------------------显示人员------------------------------------------------------
 /**
  * 
  */
 function getgroupuserGrid(){
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
     numberOfpeople()
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
				 "wuxuzongjian":$('#wuxuzongjian').prop("checked")?1:0,
		 };
	     $("#groupusershow").datagrid({
		 url:'getDjtExamInfoUserList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 pageNumber:1,
	     //pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100,300,500,1000],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		 	{align:'center',field:'exam_num',title:tjhname,width:100,"formatter":f_showexam,sortable:true},
		    {align:'center',field:'arch_num',title:dahname,width:100,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:150,sortable:true},		 	
		    {align:'center',field:'exam_types',title:'体检类型',width:70},	
		 	{align:'center',field:'user_name',title:'姓名',width:80,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:40,sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',width:40},
		 	{align:'center',field:'age',title:'年龄',width:40,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:100,sortable:true},		 	
		 	{align:'center',field:'join_date',title:'体检日期',width:100,sortable:true},	 	
		 	{align:'center',field:'huishou',title:'回收',width:40,"formatter":f_huishoutatus},
		 	{align:'center',field:'freezename',title:'冻结',width:40},
		 	{align:'center',field:'status',title:'体检状态',width:70,"formatter":f_showstatus,sortable:true},
		 	{align:'center',field:'swuxuzongjian',title:'需要总检',width:80},
		 	{align:'center',field:'chargeType',title:'付费类型',width:80},
		 	{align:'center',field:'team_pay',title:'团费',width:80,show:teamAmountViewFlag==0},
		 	{align:'center',field:'personal_pay',title:'个费',width:80},
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
		 	{align:'center',field:'employeeID',title:'工号',width:100},
		 	{align:'center',field:'is_upload',title:'是否同步',width:80,"formatter":f_isUpload},
		 	{align:'center',field:'is_need_guide',title:'打印导检单',width:80,hidden:'true'},
		 	{align:'center',field:'is_need_barcode',title:'打印条码',width:80,hidden:'true'},
		 	{align:'center',field:'is_report_upload',title:'是否上传报告',width:15,width:100,"formatter":f_isUploadReport}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		 $("#groupusershow").datagrid("hideColumn", "set_name"); // 设置隐藏列    
	    		 $("#groupusershow").datagrid("hideColumn", "employeeID"); // 设置隐藏列
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		// if($("#ttog_exam").val()=='0'){
				// 	 $("div.datagrid-toolbar [id ='10']").eq(0).hide();
				// }
				// if($("#gtot_exam").val()=='0'){
				// 	 $("div.datagrid-toolbar [id ='9']").eq(0).hide();
				// }
	    	},
			rowStyler:function(index,row){
				/*var freeze_color = $("#freeze_color").val();
		        if (row.freeze==1 && freeze_color){
		            return 'color:'+freeze_color+';';    
		        }*/
		        if (row.freeze==1){    
		            return 'color:#f00;';    
		        }else if(row.is_need_barcode=="Y" || row.is_need_guide=="Y"){
		        	return 'color:#9933FF;';
		        }
		        
		        var customer_type_special = $("#customer_type_special").val();
		        var customer_type_special_color = $("#customer_type_special_color").val();
			  	for (var i in customer_type_special.split(",")) {
			  		var customer_type_id = customer_type_special.split(",")[i];
			  		if(row.customer_type_id==customer_type_id && customer_type_special_color){
			  			return 'color:'+customer_type_special_color+';';
			  		}
			  	}
		    },
	    	onDblClickRow : function(index, row) {	    		
	    		examnumenterDou(row.exam_num);
			},
			height: window.screen.height-window.screen.height*0.44,
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
 
 //是否同步
 function f_isUpload(val,row){
	 return val == 0 ? "否" : "是";
 }
 
//是否上传报告
 function f_isUploadReport(val,row){
	 return val == 0 ? "否" : "是";
 }
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'打印导检',
		width:90,
		iconCls:'icon-print',
	    handler:function(){
	    	var ids = "";
	    	var barids="";
	    	var exam_nums = new Array();
	    	var exam_numss = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	        barids=barids+"$"+(item.exam_num);
    	        exam_nums.push("'"+item.exam_num+"'");
    	        exam_numss.push(item.exam_num);
    	    });
    	    if(ids.length>1) ids=ids.substring(1,ids.length);
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    	var falgs='0';
        	    daoyindan_point(ids.split(","),ids,barids,falgs,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
    			new_print_djd(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if($("#barcode_print_type").val() == '4' || $("#barcode_print_type").val() == '5'){//调用4.0打印程序中间表调用模式
    			new_print_djd4(exam_numss);
    		}else{
    			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    		}
	    }
	},{
		text:'打印条码',
		width:90,
		iconCls:'icon-print',
	    handler:function(){
	    	var barids="";
	    	var exam_nums = new Array();
	    	var exam_numss = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        barids=barids+"$"+(item.exam_num);
    	        exam_nums.push("'"+item.exam_num+"'");
    	        exam_numss.push(item.exam_num);
    	    });
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    	printBar(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
    			new_print_bar(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if($("#barcode_print_type").val() == '4' || $("#barcode_print_type").val() == '5'){//调用4.0打印程序中间表调用模式
    			new_print_bar4(exam_numss,barids);
    		}else{
    			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    		}
    	    
	    }
	},{
		text:'打印档案号',
		width:100,
		iconCls:'icon-print',
	    handler:function(){
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    if(checkedItems.length == 0){
	    		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
	    	}else{
	    		$("#print_nums").numberbox('setValue',1);
	    		$("#dlg-jkz").dialog('open');
	    		$("#dlg-jkz").dialog('center');
	    	}    	    
	    }
	},{
		text:'报告预览',
		width:90,
		iconCls:'icon-print',
	    handler:function(){
	    	var barids="";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        barids=barids+","+(item.exam_num);
    	    });
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if((barids=="")||(barids.split(',').length!=1)){
	    		$.messager.alert("操作提示", "请选择一个体检人员","error");
	    	}else{
	    		printreport(barids);
	    	}    	    
	    }
	},{
		text:'个人登记',
		width:90,
		iconCls:'icon-add',
	    handler:function(){
	  	  window.parent.addPanel_other("体检个人登记","getDjtRegisterGList.action?exam_num=","","1");

	    }
	},{
		text:'团体登记',
		width:90,
		iconCls:'icon-save',
	    handler:function(){
	    	window.parent.addPanel_other('体检团体登记','getDjtRegisterTList.action?exam_num=','"+wx.getIcon_url()+"','1');
	    }
	},{
		text:'预约改期',
		width:90,
		iconCls:'icon-check',
		handler:function(){
			var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    	var yugqflag=1;
    	    $.each(checkedItems, function(index, item){
    	    	if((item.status=='Y')||(item.status=='D')){
    	    		yugqflag=0;
    	    	}
    	    	//alert(item.exam_num);
    	        ids=ids+","+(item.exam_num);
    	    });
    	    if(yugqflag==0){
    	    if(ids.split(',').length<=1){
	    		$.messager.alert("操作提示", "请选择要修改的体检人员","error");
	    	}//else if(ids.split(',').length>2){
	    	//	$.messager.alert("操作提示", "只能同时修改一个体检人员信息","error");
	    	//}
    	    else{    	    	
	    		if(ids.length>1) ids=ids.substring(1,ids.length);
	    		$("#dlg-custedit").dialog({
			 		title:'体检人员预约改期',
			 		href:'editregisterdateshow.action?ids='+ids
			 	});
			 	$("#dlg-custedit").dialog('open');
	    	}
    	    }else{
    	    	$.messager.alert("操作提示", "只有预约\登记状态的人员才能操作","error");
    	    }
	    }
	},{
		text:'取消报到',
		width:90,
		iconCls:'icon-cancel',
		handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    });
    	    if(ids.length>1) ids=ids.substring(1,ids.length);
	    	djtdelregister(ids);
	    }
	},{
		text:'复检登记',
		width:90,
		iconCls:'icon-add',
		handler:function(){
			var exam_num="";
			var examtype="";
			var examstatus="Y";
			
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');	    	
    	    $.each(checkedItems, function(index, item){
    	    	exam_num=exam_num+","+(item.exam_num);
    	        examtype=item.exam_type;
    	        examstatus=item.status;
    	    });
    	    if(exam_num.length>1) exam_num=exam_num.substring(1,exam_num.length);
    	    if((exam_num=="")||(exam_num.split(',').length!=1)){
	    		$.messager.alert("操作提示", "请选择一个体检人员","error");
	    	}else{
	    		if(examstatus=='Y'){
	    			$.messager.alert("操作提示", "预约体检人员，不能复检登记","error");
	    		}else{
                    $.messager.confirm('提示信息','体检号：【'+ exam_num +"】确定复检登记吗？",function(r){
                        if(r){
                            if(examtype=='G'){
                                window.parent.addPanel_other("复查个人登记","regetDjtRegisterGEdit.action?exam_num="+exam_num+"&ren_type=1","","1");
                            }if(examtype=='T'){
                                window.parent.addPanel_other("复查团体登记","regetDjtRegisterTEdit.action?exam_num="+exam_num+"&ren_type=1","","1");
                            }
						}
                    });
	    		}
	    	}
	    }
	},{
		text:'补做登记',
		width:90,
		iconCls:'icon-add',
		handler:function(){
			
			var examNum="";
			var checkedItems = $('#groupusershow').datagrid('getChecked');
			$.each(checkedItems, function(index, item){
    	    	examNum=examNum+","+(item.exam_num);
    	    });
			if(examNum.length>1) examNum=examNum.substring(1,examNum.length);
			if((examNum=="")||(examNum.split(',').length!=1)){
	    		$.messager.alert("操作提示", "请选择一个体检人员","error");
	    		return ;
	    	}
			//查询是否有延期和废弃的项目
			 var model={"exam_num":examNum};
		     $("#bzdjItemShow").datagrid({
				 url:'djdBuZuoGetList.action',
				 dataType: 'json',
				 queryParams:model,
				 //toolbar:toolbar,
				 rownumbers:true,
				 columns:[[
				    {align:'center',field:'ck',checkbox:true},
				    //{align:'center',field:'caozuo',title:'操作',width:12,"formatter":f_cz},
				    {align:'center',field:'item_code',title:'项目编码',width:12},
				 	{align:'center',field:'item_name',title:'项目名称',width:15},
				 	{align:'center',field:'dep_name',title:'科室名称',width:12},
				 	{align:'center',field:'exam_statuss',title:'状态',width:12},
				 	//{align:'center',field:'sample_statuss',title:'采样状态',width:12},
				 	{align:'center',field:'create_time',title:'最晚检查日期',width:15}	
				 	]],	    	
			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    striped:true,
			    fit:true,
			    onLoadSuccess:function(data){
			    	$("#dlg-bzdj").dialog('open');
			    }
		    });
			
	    }
	},{
		text:'数据导出',
		width:90,
		iconCls:'icon-check',
		handler:function(){
			var chk_value ="";    
			$('input[name = chkItem]:checked').each(function(){    
			   chk_value=chk_value+","+($(this).val());    
			}); 
			if(chk_value.length>1){
				chk_value=chk_value.substring(1,chk_value.length);
			}
			
			var filed1 =$(".datagrid-sort-asc").parent().eq(0).attr('field');
			var filed2 = $(".datagrid-sort-desc").parent().eq(0).attr('field');
			var sort = undefined;order = undefined;
			if(filed1 != undefined){
				sort = filed1;
				order = 'asc';
			}
			if(filed2 != undefined){
				sort = filed2;
				order = 'desc';
			}
			
		/*	var statuss = new Array();
			var sta = $("#statusss").combobox('getValues');
			for(i=0;i<sta.length;i++){
				statuss.push("'"+sta[i]+"'");
			}*/
			
			//部门
		    var levels = $("#levelss").combobox('getValues');
		    var levelss = new Array();
		    for(i=0;i<levels.length;i++){
			   if(levels[i] != ''){
				   levelss.push("'"+levels[i]+"'"); 
			   }
		    }
			
			alert_autoClose("操作提示", "正在导出信息，请等待。。。","");
			
			window.location.href='djdUserExportExcel.action?company_id='+$("#company_id").val()+'&exam_num='+$("#exam_num").val()+
				'&time1='+$("#start_date").datebox('getValue')+'&time2='+$("#end_date").datebox('getValue')+
				'&time3='+$("#time3").datebox('getValue')+'&time4='+$("#time4").datebox('getValue')+
				'&yuyue_date1='+$("#yuyue_date1").datebox('getValue')+'&yuyue_date2='+$("#yuyue_date2").datebox('getValue')+
				'&custname='+$("#custname").val()+'&chkItem='+chk_value+'&exam_type='+document.getElementsByName("exam_type")[0].value+
				'&creater='+$("#creater").datebox('getValue')+'&status='+document.getElementsByName("statusss")[0].value+'&arch_num='+$("#arch_num").val()+
				'&id_num='+$("#id_num").val()+'&sex='+$('#sex').combobox('getValue')+'&batch_id='+$('#batch_ids').combobox('getValue')+
				'&levels='+levelss.toString()+'&ren_type='+$('#conn_rylb').combobox('getValue')+'&djdstatuss='+$('#djdstatuss').combobox('getValue')+
				'&data_source='+$('#data_source').combobox('getValue')+'&customer_type='+$('#tjlx').combobox('getValue');
			
	    }
	},{
		text:'批量同步',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    }); 	 
    	    if(ids=="" || ids==null){
    	    	$.messager.alert("操作提示", "请先选择某一行", "error");
    	    }else{
    	    	isSynchro(ids);
    	    }
	    }
	},{
		text:'报告上传',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    }); 	 
    	    if(ids=="" || ids==null){
    	    	$.messager.alert("操作提示", "请先选择某一行", "error");
    	    }else{
    	    	reportUpload(ids);
    	    }
	    }
	},{
		id:9,
		text:'个转团',
		width:80,
		iconCls:'icon-print',
	    handler:function(){
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
			if(checkedItems.length != 1){
				$.messager.alert("操作提示", "请选择一个体检人员","error");
				return;
			}
			var flag = true;
    	    $.each(checkedItems, function(index, item){
				if(item.exam_type != 'G'){
					flag = false;
				}
    	    });
    	    if(!flag){
    	    	$.messager.alert("操作提示", "请选择一个个检人员","error");
				return;
    	    }
    	    $("#dlg-tjzs").dialog('open');	 
			$("#dlg-tjzs").dialog('center');				
	    }
	},{
		id:10,
		text:'团转个',
		width:80,
		iconCls:'icon-print',
	    handler:function(){
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
			if(checkedItems.length != 1){
				$.messager.alert("操作提示", "请选择一个体检人员","error");
				return;
			}
			var exam_id = 0;
			var exam_num = '';
			var batch_id = '';
			var flag = true;
    	    $.each(checkedItems, function(index, item){
				if(item.exam_type != 'T'){
					flag = false;
				}
				exam_id = item.id;
				exam_num = item.exam_num;
				batch_id = item.batch_id;
    	    });
    	    if(!flag){
    	    	$.messager.alert("操作提示", "请选择一个团检人员","error");
				return;
    	    }
    	    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
				url : 'changeExaminfoExamType.action',
				data : {'exam_type':'G','exam_id':exam_id,'exam_num':exam_num,'batch_id':batch_id},
				type : "post",//数据发送方式   
				success : function(text) {
					$(".loading_div").remove();
					if (text.split("-")[0] == 'ok') {
						getgroupuserGrid();
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
	}];

 /**
  * 批量同步
  * @param ids
  * @returns
  */
 function  isSynchro(ids){
		$.messager.confirm('提示信息','是否进行批量同步？',function(r){
			 if(r){
				 $.ajax({
						url : 'isSynchroExamInfo.action',
						data : {ids:ids},
						type : "post",//数据发送方式   
						success : function(text) {
							if (text.split("-")[0] == ' ok') {
								getgroupuserGrid();
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
		})
	}
 
 function djtdelregister(ids){
	 if((ids=='')||(ids.length<=0)){
	 		$.messager.alert("操作提示", "请选择体检人员","error");
	 	}else{
		    $.messager.confirm('提示信息','是否取消报到？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'djtdelregisterdo.action',
				data : {
					    ids:ids
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getgroupuserGrid();
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
		 });
	 	}
} 
 /**
  * 显示一条
  * @param val
  * @param row
  * @returns {String}
  */
  function f_showexam(val,row){
	  return '<a href=\"javascript:f_examoneshow(\''+row.exam_num+'\',\''+row.exam_type+'\')\">'+row.exam_num+'</a>';
  } 
  
  
  function f_huishoutatus(val,row){
	  if(row.is_guide_back=='Y'){
		  return '<font color="green">'+row.is_guide_backs+'</font>';
	  }else{
		  return row.is_guide_backs;
	  }
  }
  
  function f_showstatus(val,row){
	  if(row.status=='Y'){
		  return '<font color="red">'+row.statuss+'</font>';
	  }else if(row.status=='D'){
		  return '<font color="blue">'+row.statuss+'</font>';
	  }else if(row.status=='J'){
		  return '<font color="green">'+row.statuss+'</font>';
	  }else{
		  return row.statuss;
	  }
  }
  /**
   * 显示
   * @param userid
   */
   function f_examoneshow(exam_num,examtype){
	   if(examtype=='G'){
		   window.parent.addPanel_other("体检个人登记","getDjtRegisterGList.action?exam_num="+exam_num+"","","1");
	   }if(examtype=='T'){
		   window.parent.addPanel_other("体检团体登记","getDjtRegisterTList.action?exam_num="+exam_num+"","","1");
	   }
   }
   
   /**
    * 报告上传
    * @param ids
    * @returns
    */
   function  reportUpload(ids){
		$.messager.confirm('提示信息','是否进行批量上传报告？',function(r){
			 if(r){
				 $.ajax({
						url : 'reportUploadExamInfo.action',
						data : {ids:ids},
						type : "post",//数据发送方式   
						success : function(text) {
							if (text.split("-")[0] == ' ok') {
								getgroupuserGrid();
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
		})
	}
   
   function printBar(barids,exam_nums){
		 if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
			 var exeurl =$("#barexeurl").val() +" barcode "+barids+" * *"; 
		 }else{
			 var exeurl =$("#barexeurl").val() +" barcode "+barids+" *"; 
		 }
		 
		 $.ajax({
				url : 'updateSampleExamEetail.action',
				data : {
					ids : barids
				    },
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								$.ajax({
									url : 'updateBarcodePrintStatus.action',
									data : {ids:exam_nums},
									type : "post",//数据发送方式   
									success : function(text) {
										RunExe(exeurl);	
									},
									error : function() {
										$.messager.alert("操作提示", "操作错误", "error");					
									}
								});															
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

   function printreport(barids){
	   /*if($("#barcode_print_type").val() == '1' || $("#barcode_print_type").val() == '2'){//调用旧预览程序
			var exeurl="reportServices://&0&"+barids+"&0";
			RunReportExe(exeurl);
		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
		}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
			var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+exam_num;
		 	location.href=exeurl;
	    }else if($("#barcode_print_type").val() == '5'){//调用5.0打印
	    	var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+exam_num;
		 	location.href=exeurl;
	    }else{
	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
	    }*/
		if($("#report_print_type").val() == '5'){
			//预览普通报告
			var exeurl="reportServices://&0&"+barids+"&Y&0";//0代表体检号Y代表预览，2代表职业病
			location.href=exeurl;
	    }else{
	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-REPORT_PRINT_TYPE","error");
	    }
	 }
   
   
   
 //打印导检单和条码
   function daoyindan_point(idessss,idss,barids,falgs,exam_nums) {
  	 if(idss.length<=1){
  	 		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
  	 	}else{
  	 if($("#hansidjdflag").val()==1){
  		if(idss.split(",").length>10){
  			$.messager.alert("操作提示", "每次打印不能超过10个.","error");
  		}else{
  			$.ajax({
				url : 'updateGuidePrintStatus.action',
				data : {ids:exam_nums},
				type : "post",//数据发送方式   
				success : function(text) {
					doURLPDFPrint_ireport(idss);	
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});		
  		}		 
  	 }else{
  		 printDJD_exe(barids,exam_nums);//打印条码
  	 }
  	 if(falgs=='1'){
  	 printBar(barids,exam_nums);//打印条码
  	 }
  	 	}
   }
   
 //新版本打印导检单
   function printDJD_exe(barids,exam_nums){

  	 if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
  		 var exeurl =$("#djdexeurl").val() + "  guid "+barids+" *"; //打印档案号
  	 }else{
  		 var exeurl =$("#djdexeurl").val() + "  guid "+barids; 
  	 }
  	$.ajax({
		url : 'updateGuidePrintStatus.action',
		data : {ids:exam_nums},
		type : "post",//数据发送方式   
		success : function(text) {
			if (text.split("-")[0] == 'ok') {
				RunExe(exeurl);													
			} else {
				$.messager.alert("操作提示", text, "error");
			}			
			
		},
		error : function() {
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
  	 
   }
   
   
   function doURLPDFPrint_ireport(idss){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
			$.ajax({
				url:'getDjDpdf.action',
				data:{
					ids:idss,
					frandom:Math.random()
					},
				type:"POST",
				complete:function(res,status){
					if(status=="success"){
						var resText=res.responseText;
						if(resText=="timeout"){
							$.messager.alert("操作提示", "连接超时","error");
							}else{

								var filesurl=resText.split("-")[1];

								 var pdf = document.getElementById("createPDF");
								    if (pdf != undefined && pdf != null) {//判断pdf对象是否存在，如果存在就删除该对象
								        var parentNode = pdf.parentNode;
								        parentNode.removeChild(pdf);
								    }
								    var p = document.createElement("object");
								    p.id = "createPDF";
								    p.classid = "CLSID:CA8A9780-280D-11CF-A24D-444553540000";
								    p.width = 1;
								    p._Version=327680;
								    p._ExtentX=19315;
								    p._ExtentY=16034;
								    p._StockProps=0;
								    p.height = 1;
								    var objects=new Object();
								    objects.src=filesurl;
								    p.object=objects;  
								    document.body.appendChild(p);
								    
								    var pdfReader = document.getElementById("createPDF");  
								     pdfReader.setShowToolbar(false);  
								     pdfReader.setShowScrollbars(false);  
								     pdfReader.src=filesurl;  
								     setTimeout(function () { 
								    	 doprintln();
								     }, 5000);	
							}
					}
				}
			})
		}
	 
	 function doprintln(){
		 var pdfReader = document.getElementById("createPDF");   
		 pdfReader.gotoFirstPage();
	     pdfReader.print();
	     $(".loading_div").remove();
	 }
	 
	 
	 function RunReportExe(strPath) {
		location.href=strPath;
	 }

 function RunExe(strPath) {
		 try {
				var ws = new ActiveXObject("WScript.Shell");
				ws.Exec(strPath);
			} catch (e) {
				$.messager.alert("操作提示", '被浏览器拒绝：' + e,"error");
			}
	 }
 //新打印导检
 function new_print_djd(barids,exam_nums){
	 if(barids.length<=1){
	 		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
	 }else{
		 $.ajax({
				url : 'updateGuidePrintStatus.action',
				data : {ids:exam_nums},
				type : "post",//数据发送方式   
				success : function(text) {
					if (text.split("-")[0] == 'ok') {
						var url = 'GuidBarServices://&guid&'+barids+'&&';
						RunReportExe(url);											
					} else {
						$.messager.alert("操作提示", text, "error");
					}			
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
	 }
 }
 // 新打印条码
function new_print_bar(barids, exam_nums) {
	if(barids.length<=1){
 		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
    }else{
		$.ajax({
			url : 'updateSampleExamEetail.action',
			data : {
				ids : barids
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.ajax({
						url : 'updateBarcodePrintStatus.action',
						data : {
							ids : exam_nums
						},
						type : "post",// 数据发送方式
						success : function(text) {
							var exeurl = '';
							if($('[name=isprintdah]:checked').val()=='1'){
						  		 exeurl ='GuidBarServices://&barcode&'+barids+'&&3&1'; //打印档案号
						  	}else{
						  		 exeurl ='GuidBarServices://&barcode&'+barids+'&&1'; 
						  	}
							RunReportExe(exeurl);
						},
						error : function() {
							$.messager.alert("操作提示", "操作错误", "error");
						}
					});
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
// 新打导检单条码
function new_print_barcodeall(barids, exam_nums) {
	if (barids.length <= 1) {
		$.messager.alert("操作提示", "请选择要打印的体检人员", "error");
	} else {
		$.ajax({
			url : 'updateGuidePrintStatus.action',
			data : {
				ids : exam_nums
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$.ajax({
					url : 'updateSampleExamEetail.action',
					data : {
						ids : barids
					},
					type : "post",// 数据发送方式
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							$.ajax({
								url : 'updateBarcodePrintStatus.action',
								data : {
									ids : exam_nums
								},
								type : "post",// 数据发送方式
								success : function(text) {
									var exeurl = '';
									if($('[name=isprintdah]:checked').val()=='1'){
								  		 exeurl ='GuidBarServices://&guidBarcode&'+barids+'&&3&1'; //打印档案号
								  	}else{
								  		 exeurl ='GuidBarServices://&guidBarcode&'+barids+'&&1'; 
								  	}
									RunReportExe(exeurl);
								},
								error : function() {
									$.messager.alert("操作提示", "操作错误", "error");
								}
							});
						} else {
							$.messager.alert("操作提示", text, "error");
						}
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");
					}
				});
			},
			error : function() {
				$.messager.alert("操作提示", "操作错误", "error");
			}
		});
	}
}
//确认打印档案号
function new_print_arch(){
	var regu = /^[1-9]\d*$/;
	if($("#print_nums").val() == ''){
		$.messager.alert("操作提示", "请输入打印个数!", "error");
		return;
	}else if(!regu.test($("#print_nums").val())){
		$.messager.alert("操作提示", "请输入有效的打印个数!", "error");
		return;
	}else{
		var checkedItems = $('#groupusershow').datagrid('getChecked');
	    
		if($("#barcode_print_type").val() == '1'){//调用旧打印程序
			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
			var barids = '';
			$.each(checkedItems, function(index, item){
		        barids=barids+"$"+(item.exam_num);
		    });
		    if(barids.length>1) barids=barids.substring(1,barids.length);
			var url = 'GuidBarServices://&barcode&'+barids+'&&2&'+$("#print_nums").val();
			RunReportExe(url);
			$("#dlg-jkz").dialog('close');
		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
		}else if($("#barcode_print_type").val() == '4' || $("#barcode_print_type").val() == '5'){//调用4.0打印程序中间表调用模式
//			var data = new Array();
//			$.each(checkedItems, function(index, item){
//				data.push({
//					exam_num:item.exam_num,
//	    			print_type:'B',
//	    			bar_calss:2,
//	    			arch_bar_num:$("#print_nums").val()
//				});
//		    });
//			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
//			$("body").prepend(str);
//			$.ajax({
//				url : 'saveExamPrintTmp.action',
//				data : {examPrintTmpLists:JSON.stringify(data)},
//				type : "post",//数据发送方式   
//				success : function(text) {
//					$(".loading_div").remove();
//					if(text.split('-')[0] == 'ok'){
//						var print_num = text.split('-')[1];
//						var url = 'GuidBarServices://&P&1&'+print_num;
//				 		RunServerExe(url);
//				 		$("#dlg-jkz").dialog('close');
//					}else{
//						$.messager.alert("操作提示", "操作错误", "error");
//					}
//				},
//				error : function() {
//					$(".loading_div").remove();
//					$.messager.alert("操作提示", "操作错误", "error");					
//				}
//			});
			var barids = '';
			$.each(checkedItems, function(index, item){
		        barids=barids+"$"+(item.exam_num);
		    });
		    if(barids.length>1) barids=barids.substring(1,barids.length);
			var url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&barcode&'+barids+'&&2&'+$("#print_nums").val();
			RunServerExe(url);
			
		}else{
			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
		}
	}
}
//报到打印
function baodaody(douexamnum){
	if($('[name=isprintdjd]:checked').val() == '1' && $('[name=isprinttm]:checked').val() == '1'){//打导检单条码
		if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    daoyindan_point(douexamnum.split(","),douexamnum,douexamnum,1,'\''+douexamnum+'\'');
		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
			new_print_barcodeall(douexamnum,'\''+douexamnum+'\'');
		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
		}else if($("#barcode_print_type").val() == '4' || $("#barcode_print_type").val() == '5'){//调用4.0打印程序中间表调用模式
			new_printdjdbar4(douexamnum);
		}else{
			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
		}
	}else if($('[name=isprintdjd]:checked').val() == '1'){//打导检单
		if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    daoyindan_point(douexamnum.split(","),douexamnum,douexamnum,0,'\''+douexamnum+'\'');
		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
			new_print_djd(douexamnum,'\''+douexamnum+'\'');
		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
		}else if($("#barcode_print_type").val() == '4' || $("#barcode_print_type").val() == '5'){//调用4.0打印程序中间表调用模式
			var exam_nums = new Array();
			exam_nums.push(douexamnum);
			new_print_djd4(exam_nums);
		}else{
			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
		}
	}else if($('[name=isprinttm]:checked').val() == '1'){//打条码
		if($("#barcode_print_type").val() == '1'){//调用旧打印程序
	    	printBar(douexamnum,'\''+douexamnum+'\'');
		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
			new_print_bar(douexamnum,'\''+douexamnum+'\'');
		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
		}else if($("#barcode_print_type").val() == '4' || $("#barcode_print_type").val() == '5'){//调用4.0打印程序中间表调用模式
			var exam_nums = new Array();
			exam_nums.push(douexamnum);
			new_print_bar4(exam_nums,douexamnum);
		}else{
			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
		}
	}
}


//打导检单条码 4.0
function new_printdjdbar4(exam_num){
	var data = new Array();
	var bar_calss = 1;
	if($('[name=isprintdah]:checked').val()=='1'){
		bar_calss = 3;
	}
	$.ajax({
		url : 'updateSampleExamEetail.action',
		data : {ids : exam_num},
		type : "post",//数据发送方式   
		success : function(text) {
			if (text.split("-")[0] == 'ok') {
				$(".loading_div").remove();
				var url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&guidbar&'+exam_num+'&&'+bar_calss+'&1';
				RunServerExe(url);
			} else {
				$(".loading_div").remove();
				$.messager.alert("操作提示", text, "error");
			}
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}
//新版本打印导检单 4.0
function new_print_djd4(exam_nums){
	if(exam_nums.length >= 1){
		var url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&guid&'+exam_nums[0]+'&';
		RunServerExe(url);
		return;
	}
	var data = new Array();
	for(i=0;i<exam_nums.length;i++){
		data.push({
			exam_num:exam_nums[i],
			print_type:'G',
		});
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url : 'saveExamPrintTmp.action',
		data : {examPrintTmpLists:JSON.stringify(data)},
		type : "post",//数据发送方式   
		success : function(text) {
			$(".loading_div").remove();
			if(text.split('-')[0] == 'ok'){
				var print_num = text.split('-')[1];
				var url = 'GuidBarServices://&P&1&'+print_num;
		 		RunServerExe(url);
			}else{
				$.messager.alert("操作提示", "操作错误", "error");
			}
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}
//新版本打印条码4.0
function new_print_bar4(exam_nums,ids){
	var bar_calss = 1;
	if($('[name=isprintdah]:checked').val()=='1'){
		bar_calss = 3;
	}
	if(exam_nums.length >= 1){
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		$.ajax({
			url : 'updateSampleExamEetail.action',
			data : {ids : ids},
			type : "post",//数据发送方式   
			success : function(text) {
				if (text.split("-")[0] == 'ok') {
					$(".loading_div").remove();
					var url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&barcode&'+exam_nums[0]+'&&'+bar_calss+'&1';
					RunServerExe(url);
					return;
				} else {
					$(".loading_div").remove();
					$.messager.alert("操作提示", text, "error");
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});		
	}else{
		var data = new Array();
		for(i=0;i<exam_nums.length;i++){
			data.push({
				exam_num:exam_nums[i],
				print_type:'B',
				bar_calss:bar_calss,
				arch_bar_num:1
			});
		}
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		$.ajax({
			url : 'saveExamPrintTmp.action',
			data : {examPrintTmpLists:JSON.stringify(data)},
			type : "post",//数据发送方式   
			success : function(text) {
				if(text.split('-')[0] == 'ok'){
					var print_num = text.split('-')[1];
					 $.ajax({
							url : 'updateSampleExamEetail.action',
							data : {ids : ids},
							type : "post",//数据发送方式   
							success : function(text) {
								if (text.split("-")[0] == 'ok') {
									$(".loading_div").remove();
									var url = 'GuidBarServices://&P&1&'+print_num;
									RunServerExe(url);
								} else {
									$(".loading_div").remove();
									$.messager.alert("操作提示", text, "error");
								}
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
					 });		
				}else{
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");	
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
	}
}

function RunServerExe(url){
	location.href=url;
}

//选择补做登记的项目
function addBuZuoItem(){
	var ids = new Array();
	var checkedItems = $('#bzdjItemShow').datagrid('getChecked');
    $.each(checkedItems, function(index, item){
        ids.push(item.charge_item_id);
    });
    if(ids.length == 0){
    	$.messager.alert("提示","请选择需要补做的项目!","error");
    	return;
    }
    var examNum="";
	var checkedItems = $('#groupusershow').datagrid('getChecked');
	$.each(checkedItems, function(index, item){
    	examNum=examNum+","+(item.exam_num);
    });
	if(examNum.length>1) examNum=examNum.substring(1,examNum.length);
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	//alert(ids.toString()+"===="+examNum);
    $.ajax({
		url:'djtExaminfoBuzuo.action',
		data:{'ids':ids.toString(),'exam_num':examNum},
		success:function(text){
			$(".loading_div").remove();
			if (text.split("-")[0] == 'ok') {
				$("#dlg-bzdj").dialog('close');
				$.messager.alert("操作提示","补做成功！体检号为"+text.split("-")[1]);
				//关闭弹框
			} else {
				$.messager.alert("操作提示", text, "error");
			}	
		},
		error:function(){
			$.messager.alert("警告","操作失败","error");
		}
	})
	
}

function save_tjzs(){
	var com_batchid = $("#com_batch_id").val();
	var group_id = $("#group").combobox('getValue');
	if(com_batchid == ''){
		$.messager.alert("操作提示", "请选择单位批次!", "error");
		return;
	}
//	if(group_id == ''){
//		$.messager.alert("操作提示", "请选择分组!", "error");
//		return;
//	}
	var checkedItems = $('#groupusershow').datagrid('getChecked');
	var exam_id = 0;
	var exam_num = '';
    $.each(checkedItems, function(index, item){
		exam_id = item.id;
		exam_num = item.exam_num;
    });
	var comname = $("#comnames").val();
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url : 'changeExaminfoExamType.action',
		data : {'exam_type':'T','exam_id':exam_id,'exam_num':exam_num,'company_id':com_batchid.split('-')[0],'comname':comname,'batch_id':com_batchid.split('-')[1],'group_id':group_id,'gtot_exam':$("#gtot_exam").val()},
		type : "post",//数据发送方式   
		success : function(text) {
			$(".loading_div").remove();
			if (text.split("-")[0] == 'ok') {
				getgroupuserGrid();
				$("#dlg-tjzs").dialog('close');
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