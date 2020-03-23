$(document).ready(function () { 
	
//	document.onkeydown = function(e){
//	    var ev = document.all ? window.event : e;
//	    if(ev.keyCode==13) {
//	    	shangchuanchaxun();
//	     }
//	}
	
	$('#com_Name').bind('click', function() {
		select_com_list(this.value);
    }); 
	
	$('#com_Name').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').bind('blur', function() {  
		select_com_list_over();
    });
	
	setchebox();
});

//获取单位///////////////////////////////////////////////////////////
//-------------------------------------------单位信息显示-----------------------------------------------------
/**
* 模糊查询公司信息
*/
var hc_com_list;
function select_com_list(com_Namess){
	var url='companychangshow.action';
	var data={"name":com_Namess};
	load_post(url,data,select_com_list_sess);
}

var companyidss;
/**
 * 显示树形结构
 * @param data
 * @returns
 */
function select_com_list_sess(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].id == companyidss)){
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
		$("#com_Name").val(name);
		$("#com_name_list_div").css("display","none");
		f_getdept(id);
	}
	
	//显示部门
	function f_getdept(company_id) {
		$('#levelss').combobox({
			url : 'getCompanForDept.action?company_id='+company_id,
			editable : true, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'dep_Name',
			onLoadSuccess : function(data) {
				$('#levelss').combobox('setValue', data[0].id);				
			}
		});
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

/**
 * 结束回收
 */
//查询按钮
	function chaxun(){
		getexamallxmGrid();//放弃项目
		getcustomerInfo();//用户信息
		receiveExamInfo();
	}
//查询人员基本信息
function getcustomerInfo(){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#exam_num_s").val(),
		type:'post',
		async:false,
		success:function(data){
			if(data!="null"){
				var obj=eval("("+data+")");
				$("#exam_id").val(obj.exam_num);
				$("#user_name").html(obj.user_name);
				$("#sex").html(obj.sex+'&nbsp;&nbsp;性');
				$("#age").html(obj.age+'&nbsp;&nbsp;岁');
				$("#userphone").html(obj.phone);
				$("#uservipflag").html(obj.vipflag);
				$("#join_date").html(obj.join_date);
				$("#company").html(obj.company);
				$("#exam_type").html(obj.exam_type);
				$("#getReportWay").html(obj.getReportWays);
				$("#reportAddress").html(obj.reportAddress);
				$("#reportAddress").attr("title", obj.reportAddress);
				if(obj.getReportWay == '1') {
					$("#reportAddressLine").show();
				} else {
					$("#reportAddressLine").hide();
				}
				if(obj.wuxuzongjian==1){
					$("#wuxuzongjian").html("无需总检");
					$("#zongjian").html("");	
				}else{
					$("#wuxuzongjian").html("");
					$("#zongjian").html("总检");	
				}
				//$("#is_guide_back").html(obj.is_guide_back);		
				
				if(obj.exam_dep.length > 0){
					$("#teshuxiangmu").show();
					var str = '';
					for(i=0;i<obj.exam_dep.length;i++){
						str +='<dl><dt>'+obj.exam_dep[i].item_name+'：</dt><dt>'+obj.exam_dep[i].exam_result+'</dt></dl>';
					}
					$("#teshu_div").html(str);
				}else{
					$("#teshuxiangmu").hide();
				}
				if($("#isshowxx").val()=='Y'){
					getGrid();
					getFinalSummary(obj.exam_num);
					getExamDisease(obj.exam_num);
				}
				gettjbg(obj.exam_num,"");
			}else{
				$("#exam_id").val("");
				$("#user_name").html("");
				$("#sex").html("");
				$("#age").html("");
				$("#userphone").html("");
				$("#uservipflag").html("");
				$("#join_date").html("");
				$("#company").html("");
				$("#exam_type").html("");
				$("#getReportWay").html("");
				$("#reportAddress").html("");
				$("#reportAddressLine").hide();
				$("#wuxuzongjian").html("");
				$("#zongjian").html("");
				//$("#is_guide_back").html("");
			}
			
			$("#exam_num_s").select();
			$("#exam_num_s").focus();
			
		}
	});
}
function gettjbg(exam_num,app_type){
	var model = {exam_num:exam_num,app_type:app_type};
	document.getElementById("disease_tijianbaogao").innerHTML="";
	$.ajax({
		url:'gettjbgList.action',
		type:'post',
		data:model,
		success:function(data){
			var obj = eval('('+data+')');
			if(obj.DJD_path!='NULL'){
				document.getElementById("disease_tijianbaogao").innerHTML="<img src='picture/"+obj.DJD_path+"' style='width:800px;height:1400px' />";
			}
			//			$('#miaoshu').textbox('setValue',obj.notice);
//			$('#lizi').textbox('setValue',obj.example+obj.examplenote);
		}
	})
}
function getGrid(){//
	var model = {"exam_num":$("#exam_num_s").val()};
	$("#item_result").datagrid({
		url: 'getAcceptanceItemResult.action',
		queryParams: model,
		rownumbers:false,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		columns:[[
		          {align:"center",field:"dep_name","title":"收费项目","width":10},
		          {align:'',field:"item_name","title":"检查项目","width":15,"styler":f_clolor},
		          {align:"",field:"exam_result","title":"检查结果","width":25,"styler":f_clolor,"formatter":f_flowor},
		          {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		          {align:'center',field:"exam_date","title":"检查时间","width":15}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('item_result', 'dep_name,exam_doctor,exam_date');
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		//striped:true,
		toolbar:"#toolbar",
		border:false,
		nowrap:false
	});
}

//总检结论
function getFinalSummary(exam_num){
	$.ajax({
		url:'getFinalExamResult.action?exam_num='+exam_num+'&sug_flag=N',
		type:'post',
		success:function(data){
			if(data != 'null'){
				var obj=eval("("+data+")");
				$("#zongjianjielun").val(obj.final_exam_result);
			}
		}
	});
}

function getExamDisease(exam_num){
	 var model={"exam_num":exam_num};
	$("#exam_disease").datagrid({
			 url:'getExamDiseaseResult.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
//		     pageSize: 15,//每页显示的记录条数，默认为10 
//		     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'',field:'disease_name',title:'阳性/疾病发现',width:10},
			 	{align:'',field:'suggest',title:'阳性/疾病建议',width:20,"formatter":f_font_size}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
//			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false
	});
}

function f_font_size(value,row,index){
	var count = row.suggest.indexOf("：")+1;
	if(count != -1){
		var tou = row.suggest.substring(0,count);
		var wei = row.suggest.substring(count,row.suggest.length-1);
		
		return '<span style="font-weight:bold;">'+tou+'</span>'+wei;
	}else{
		return row.suggest;
	}
}

function getflowLoglist(){//
	var model = {"exam_num":$("#exam_num_s").val()};
	$("#flowloglist").datagrid({
		url: 'flowexamloglist.action',
		queryParams: model,
		          dataType: 'json',
		 		 queryParams:model,
		 		 toolbar:'#toolbar',
		 		remoteSort:false,
		 	    columns:[[
				          {align:"center",field:"senddate","title":"时间","width":28,sortable:'true'},
				          {align:'center',field:"flow_name","title":"事件","width":22},
				          {align:'center',field:"senduname","title":"发送者","width":10,sortable:'true'},
				          //{align:'center',field:"accuname","title":"接收者","width":10},
				          {align:"center",field:"flow_types","title":"状态","width":10}
				          ]],	    	
		 	    	onLoadSuccess:function(value){ 
		 	    		$("#datatotal").val(value.total);
		 	    		$(".loading_div").remove();
		 	    	},
		 	    	singleSelect:false,
		 		    collapsible:true,
		 			pagination: false,
		 			fitColumns:true,
		 			toolbar:"#toolbar",
		 			border:false,
		 			nowrap:false
	});
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
    			if(row.health_level == 1){
    				return 'color:#ff0000;';
    			}else if(row.health_level == 2){
    				return 'color:#0000ff;';
    			}else if(row.health_level == 3){
    				return 'color:#ff5100;';
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
    				return row.exam_result.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
    			}
    		}else if(row.dep_category == '131'){
    			if(row.health_level == 1){
    				return row.exam_result+' ↑';
    			}else if(row.health_level == 2){
    				return row.exam_result+' ↓';
    			}else{
    				return row.exam_result;
    			}
    		}else{
    			if(row.exam_result!=null){
    			    return row.exam_result.replace(/\n/g,'</br>');
    			}
    		}
    	}
    	
    	function show_picture(id){
    		var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_id").val();
    		newWindow = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
    		newWindow.focus();
    	}
 ///////////////////////////////////////上传////////////////////////////////////////////////////////////////////
    	
   var lastIndex;
   function shangchuanchaxun(args){
	   
	     var company = undefined;
	     if($("#ck_com_Name")[0].checked){
	    	 company =  $("#com_Name").val();
		 }
	     var dep_id = undefined;
	     if($("#ck_dep_id")[0].checked){
	    	 dep_id = document.getElementsByName("levelss")[0].value;
	     }
	     var exam_type = undefined;
	     if($("#ck_exam_type")[0].checked){
	    	 exam_type =  $("#exam_type").combobox('getValue');
	     }
	   	
	     var exam_num = undefined;
		 if($("#ck_exam_num_p")[0].checked){
			 exam_num =  $("#exam_num_p").val();
		 }
		 var ptype = undefined;
	     if($("#ck_ptype")[0].checked){
	    	 ptype =  $('#ptype').combobox('getValue');
	     }
		 var time1 = undefined,time2 = undefined;
		 if($("#ck_date")[0].checked){
			 time1 = $("#time1").datebox('getValue');
			 time2 = $("#time2").datebox('getValue');
		 }
		 var report_class = undefined;
		 if($("#ck_report_class")[0].checked){
			 report_class = $("#report_class").val();
		 }
		 var sex = undefined;
		 if($("#ck_usersex")[0].checked){
			 sex = $('#usersex').combobox('getValue');
		 }
		 var remark = undefined;
		 if($("#ck_screater")[0].checked){
			 remark = $("#screater").combobox('getValue');
		 }
		 
		 var receive_date = undefined;
		 if($("#ck_ser_receive_date")[0].checked){
			 receive_date = $("#ser_receive_date").datebox('getValue');
		 }
		 var receive_name = undefined;
		 if($("#ck_ser_receive_name")[0].checked){
			 receive_name = $("#ser_receive_name").val();
		 }
		 var receive_type = undefined;
		 if($("#ck_ser_receive_type")[0].checked){
			 receive_type = $("#ser_receive_type").combobox('getValue');
		 }
		 var tstz = undefined;
		 if($("#ck_tstz")[0].checked){
			 tstz = $("#tstz").combobox('getValue');
		 }
		 var vipflag = undefined;
		 if($('#ck_vipflag').attr('checked')){
			 vipflag = $("#vipflag").combobox('getValue');
		 }
		 var pre_getReportWay = undefined;
		 if($('#ck_pre_getReportWay').attr('checked')){
			 pre_getReportWay = $("#pre_getReportWay").combobox('getValue');
		 }
	   var model={
		 "exam_num":exam_num,
		 "time1":time1,
		 "time2":time2,
		 "ptype":ptype,
		 "remark":remark,
		 "exam_type":exam_type,
		 "report_class":report_class,
		 "sex":sex,
		 "company":company,
		 "dep_id":dep_id,
		 "receive_date":receive_date,
		 "receive_name":receive_name,
		 "receive_type":receive_type,
		 "remark1":tstz,
		 "vipflag" : vipflag,
		 "getReportWay" : pre_getReportWay,
	   };
	   
	   if(args) {
		   model = args;
		 }
		 
	     $("#flowexamlist").datagrid({
		 url:'flowexameListe.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 pageNumber:1,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[60,100,150],//可以设置每页记录条数的列表
	     pagination: true,
	     remoteSort:false,
	     collapsible:true,
	     fitColumns:true,
	     rownumbers:true,
		 columns:[[
            {field:'ck',checkbox:true },
		 	{align:'center',field:'examreport',title:'报告',"formatter":f_reportshow},
            {align:'center',field:'report_class',title:'报告等级',sortable:true},
		    {align:'center',field:'exam_num',title:tjhname,sortable:true},
		    {align:'center',field:'arch_num',title:dahname,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',sortable:true},
		    {align:'center',field:'exam_types',title:'体检类型',sortable:true},
		    {align:'center',field:'vipflag',title:'会员标识',sortable:true},
		 	{align:'center',field:'user_name',title:'姓名',sortable:true},
		 	{align:'center',field:'sex',title:'性别',sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',sortable:true},
		 	{align:'center',field:'age',title:'年龄',sortable:true},
		 	{align:'center',field:'phone',title:'电话',sortable:true},
		 	{align:'center',field:'company',title:'单位',sortable:true},
		 	{align:'center',field:'dep_name',title:'部门',sortable:true},
		 	//{align:'center',field:'set_name',title:'套餐',width:100,sortable:true},
		 	{align:'center',field:'join_date',title:'体检日期',sortable:true},	 	
		  	{align:'center',field:'statuss',title:'体检状态',sortable:true},
		 	{align:'center',field:'stre0',title:'整单接收状态',sortable:true},
		 	{align:'center',field:'e0date',title:'整单接收时间',sortable:true},		 	
		 	{align:'center',field:'stre1',title:'整单上传状态',sortable:true},
		 	{align:'center',field:'e1date',title:'整单上传时间',sortable:true},
		 	{align:'center',field:'ctname',title:'CT',sortable:true},
		 	{align:'center',field:'drname',title:'DR',sortable:true},
		 	{align:'center',field:'mrname',title:'MR',sortable:true},
		 	{align:'center',field:'edesc',title:'录入说明',editor:{type:'text',},sortable:true},
		 	{align:"center",field:"getReportWays",title:"报告准备发送方式",sortable:true},
		 	{align:"center",field:"rty",title:"已邮寄或自取报告","formatter":f_yz,sortable:true},
		 	{align:'center',field:'receive_name',title:'接收人',sortable:true},
		 	{align:'center',field:'receive_date',title:'接收时间',sortable:true},
		 	{align:'center',field:'e1creater',title:'发放人',sortable:true},
		 	{align:'center',field:'e1date',title:'发放时间',sortable:true},
		 	{align:'center',field:'receive_remark',title:'备注',sortable:true},
		 	{align:'center',field:'receive_address',title:'邮寄地址',sortable:true}
		 	]],
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
			rowStyler:function(index,row){    
		        if (row.e1==0){ 
		        	if(row.t == 1){
		        		return 'color:red;font-weight:bold;';
		        	}
		        	return 'color:red;';   
		        }else if(row.t == 1){
		        	return 'font-weight:bold;';
		        }  
		    },
		    onDblClickRow  : function (index,row) {
		    	old_exam_result = row.exam_result;
		    	$(this).datagrid('endEdit', lastIndex);
		    	$(this).datagrid('beginEdit', index);
		    	
				var editors = $(this).datagrid('getEditor',{index:index,field:'edesc'});
				$(editors.target).change(function(){
					$('#flowexamlist').datagrid('endEdit', index);
				});
				lastIndex = index;
		    },
		    onAfterEdit:function(rowIndex, row, changes){
		    	$.ajax({
			   		url:'saveEdesc.action',
			   		data : {
					    exam_num:row.exam_num,
					    edesc:changes.edesc,
					},
			   		type:'post',
			   		success:function(text){
						if (text.split("-")[0] == 'ok') {
							alert_autoClose("操作提示", text.split("-")[1],"");
						} else {
							$.messager.alert("警告信息",text,"error");
						}
			   		},
			   		error:function(){
			   			$.messager.alert('提示信息','操作失败！','error');
			   		}
		   		});
		    },
		    toolbar:toolbar
	});
   }
   function quanliuchengchaxun(args){
	   var company = undefined;
	   if($("#ck_com_Name")[0].checked){
		   company =  $("#com_Name").val();
	   }
	   var dep_id = undefined;
	   if($("#ck_dep_id")[0].checked){
		   dep_id = document.getElementsByName("levelss")[0].value;
	   }
	   var exam_type = undefined;
	   if($("#ck_exam_type")[0].checked){
		   exam_type =  $("#exam_type").combobox('getValue');
	   }
	   
	   var exam_num = undefined;
	   if($("#ck_exam_num_p")[0].checked){
		   exam_num =  $("#exam_num_p").val();
	   }
	   var ptype = undefined;
	   if($("#ck_ptype")[0].checked){
		   ptype =  $('#ptype').combobox('getValue');
	   }
	   var time1 = undefined,time2 = undefined;
	   if($("#ck_date")[0].checked){
		   time1 = $("#time1").datebox('getValue');
		   time2 = $("#time2").datebox('getValue');
	   }
	   var report_class = undefined;
	   if($("#ck_report_class")[0].checked){
		   report_class = $("#report_class").val();
	   }
	   var sex = undefined;
	   if($("#ck_usersex")[0].checked){
		   sex = $('#usersex').combobox('getValue');
	   }
	   var remark = undefined;
	   if($("#ck_screater")[0].checked){
		   remark = $("#screater").combobox('getValue');
	   }
	   
	   var receive_date = undefined;
	   if($("#ck_ser_receive_date")[0].checked){
		   receive_date = $("#ser_receive_date").datebox('getValue');
	   }
	   var receive_name = undefined;
	   if($("#ck_ser_receive_name")[0].checked){
		   receive_name = $("#ser_receive_name").val();
	   }
	   var receive_type = undefined;
	   if($("#ck_ser_receive_type")[0].checked){
		   receive_type = $("#ser_receive_type").combobox('getValue');
	   }
	   var tstz = undefined;
	   if($("#ck_tstz")[0].checked){
		   tstz = $("#tstz").combobox('getValue');
	   }
	   var vipflag = undefined;
	   if($('#ck_vipflag').attr('checked')){
		   vipflag = $("#vipflag").combobox('getValue');
	   }
	   var model={
			   "exam_num":exam_num,
			   "time1":time1,
			   "time2":time2,
			   "ptype":ptype,
			   "remark":remark,
			   "exam_type":exam_type,
			   "report_class":report_class,
			   "sex":sex,
			   "company":company,
			   "dep_id":dep_id,
			   "receive_date":receive_date,
			   "receive_name":receive_name,
			   "receive_type":receive_type,
			   "remark1":tstz,
			   "vipflag" : vipflag,
	   };
	   
	   $("#flowexamlist").datagrid({
		   url:'flowexameListe_allflow.action',
		   dataType: 'json',
		   queryParams:model,
		   toolbar:'#toolbar',
		   pageNumber:1,
		   pageSize: 10,//每页显示的记录条数，默认为10 
		   pageList:[10,15,60,100,150],//可以设置每页记录条数的列表
		   pagination:true,
		   remoteSort:false,
		   collapsible:true,
		   fitColumns:true,
		   columns:[[
               {align:'center',field:'examreport',title:'报告',"formatter":f_reportshow},
			   {align:'center',field:'report_class',title:'报告等级',sortable:true},
			   {align:'center',field:'exam_num',title:tjhname,sortable:true},
			   {align:'center',field:'arch_num',title:dahname,sortable:true},
			   {align:'center',field:'id_num',title:'身份证号',sortable:true},
			   {align:'center',field:'exam_types',title:'体检类型',sortable:true},
			   {align:'center',field:'vipflag',title:'会员标识',sortable:true},
			   {align:'center',field:'user_name',title:'姓名',sortable:true},
			   {align:'center',field:'sex',title:'性别',sortable:true},
			   {align:'center',field:'is_marriage',title:'婚否',sortable:true},
			   {align:'center',field:'age',title:'年龄',sortable:true},
			   {align:'center',field:'phone',title:'电话',sortable:true},
			   {align:'center',field:'company',title:'单位',sortable:true},
			   {align:'center',field:'dep_name',title:'部门',sortable:true},
			   {align:'center',field:'join_date',title:'体检日期',sortable:true},	 	
			   {align:'center',field:'statuss',title:'体检状态',sortable:true},
			   {align:'center',field:'flow_name',title:'最终流程阶段',sortable:true},
			   {align:'center',field:'edesc',title:'录入说明',editor:{type:'text',},sortable:true},
			   {align:"center",field:"rty",title:"邮寄或自取报告","formatter":f_yz,sortable:true},
			   {align:'center',field:'receive_name',title:'接收人',sortable:true},
			   {align:'center',field:'receive_date',title:'接收时间',sortable:true},
			   {align:'center',field:'e1creater',title:'发放人',sortable:true},
			   {align:'center',field:'e1date',title:'发放时间',sortable:true},
			   {align:'center',field:'receive_remark',title:'备注',sortable:true},
			   {align:'center',field:'receive_address',title:'邮寄地址',sortable:true}
			   ]],	    	
			   onLoadSuccess:function(value){ 
				   $("#datatotal").val(value.total);
				   $(".loading_div").remove();
			   },
			   onClickRow: function (rowIndex, rowData) {
                   $(this).datagrid('unselectRow', rowIndex);
               },
			   rowStyler:function(index,row){
				   if (row.e1==0){ 
					   if(row.t == 1){
						   return 'color:red;font-weight:bold;';
					   }
					   return 'color:red;';   
				   }else if(row.t == 1){
					   return 'font-weight:bold;';
				   }  
			   },
	   });
   }
   

function f_yz(val,row){
   	if(row.receive_type1 == '0'){
   		return row.receive_type_y;
   	}else if(row.receive_type1 == '1'){
   		return '<a href=\"javascript:f_showreport_m(\''+row.exam_num+'\')\">'+'邮寄'+'</a>'
   	}else if(row.receive_type1 == '2'){
   		return '<a href=\"javascript:f_showreport_t(\''+row.exam_num+'\')\">'+'自取'+'</a>'
   	}
}

   function f_showreport_m(exam_num){
   	$("#dlg-s").dialog({
   		title:'邮寄报告信息',
   		href:'showReportMail.action?exam_num='+exam_num
   	});
   	$("#dlg-s").dialog("open");
   }

   function f_showreport_t(exam_num){
   	$("#dlg-s").dialog({
   		title:'领取报告信息',
   		href:'showReportThems.action?exam_num='+exam_num
   	});
   	$("#dlg-s").dialog("open");
   }
   
   //取消上传
   
   function getReportMail(exam_num) {
	   $("#dlg-edit").dialog({
   		title:'邮寄报告',
   		href:'getReportMail.action?exam_num='+exam_num.toString()
   	});
   	$("#dlg-edit").dialog("open");
   }
   function getReportThems(exam_num) {
	   $("#dlg-edit-thems").dialog({
   		title:'自己取报告',
   		href:'getReportThems.action?exam_num='+exam_num.toString()
   	});
   	$("#dlg-edit-thems").dialog("open");
   }
   
   /**
	  * 定义工具栏
	  */
	 var toolbar=[/*{
			text:'批量上传',
			width:90,
			iconCls:'icon-save',
		    handler:function(){
		    	exame1insert();
		    }
		},{
			text:'取消上传',
			width:90,
			iconCls:'icon-cancel',
			handler:function(){
				exame1delete();
		    }
		},*/{
		    text:'邮寄报告',
		    iconCls:'icon-add',
		    handler:function(){
		    	var row = $("#flowexamlist").datagrid('getSelections');
		    	if(row.length == 0){
		    		$.messager.alert('提示信息', '请先选中需要邮寄报告的人!','error');
		    		return;
		    	}
		    	var exam_num = new Array();
		    	for(i=0;i<row.length;i++){
			    	if(row[i].receive_type1 == '1'){
			    		$.messager.alert('提示信息', '存在体检人员的报告已邮寄,不能再邮寄!','error');
			    		return;
			    	}else if(row[i].receive_type1 == '2'){
			    		$.messager.alert('提示信息', '存在体检人员的报告已自取,不能再邮寄!','error');
			    		return;
			    	}
			    	exam_num.push(row[i].exam_num);
		    	}
		    	$.ajax({
			   		url:'checkCTMR.action',
			   		data : {
			   			exam_num:exam_num.toString()
					},
			   		type:'post',
			   		success:function(data){
			   			var arr=eval("("+data+")");
			   			
			   			if(arr.length > 1) {
			   				var customer_hasCTMR = new Array();
			   				for(var i in arr) {
			   					customer_hasCTMR.push(arr[i].arch_num);
			   				}
			   				$.messager.alert("提示信息","包含有做CT/MRI的记录，"+dahname+"为："+customer_hasCTMR, "info",function () {
			   					getReportMail(exam_num);
			   				});
			   			} else if(arr.length == 1) {
			   				$.messager.alert("提示信息","此人有做CT/MRI", "info",function () {
			   					getReportMail(exam_num);
			   				});
			   			} else {
			   				getReportMail(exam_num);
			   			}
			   		},
			   		error:function(){
			   			$.messager.alert('提示信息','操作失败！','error');
			   		}
		   		});
		    }
		},{
			text:'自己取报告',
		    iconCls:'icon-add',
		    handler:function(){
		    	var row = $("#flowexamlist").datagrid('getSelections');
		    	if(row.length == 0){
		    		$.messager.alert('提示信息', '请先选中需要自己取报告的人!','error');
		    		return;
		    	}
		    	var exam_num = new Array();
		    	for(i=0;i<row.length;i++){
		    		if(row[i].receive_type1 == '1'){
		        		$.messager.alert('提示信息', '存在体检人员的报告已邮寄,不能再自取!','error');
		        		return;
		        	}else if(row[i].receive_type1 == '2'){
		        		$.messager.alert('提示信息', '存在体检人员的报告已自取,不能再自取!','error');
		        		return;
		        	}
		    		exam_num.push(row[i].exam_num);
		    	}
		    	$.ajax({
			   		url:'checkCTMR.action',
			   		data : {
			   			exam_num:exam_num.toString()
					},
			   		type:'post',
			   		success:function(data){
			   			var arr=eval("("+data+")");
			   			if(arr.length > 1) {
			   				var customer_hasCTMR = new Array();
			   				for(var i in arr) {
			   					customer_hasCTMR.push(arr[i].arch_num);
			   				}
			   				$.messager.alert("提示信息","包含有做CT/MRI的记录，"+dahname+"为："+customer_hasCTMR, "info",function () {
			   					getReportThems(exam_num);
			   				});
			   			} else if(arr.length == 1) {
			   				$.messager.alert("提示信息","此人有做CT/MRI", "info",function () {
			   					getReportThems(exam_num);
			   				});
			   			} else {
			   				getReportThems(exam_num);
			   			}
			   		},
			   		error:function(){
			   			$.messager.alert('提示信息','操作失败！','error');
			   		}
		   		});
		    }
		},{
		    text:'批量取消邮寄/自取',
		    iconCls:'icon-add',
		    handler:function(){
		    	var row = $("#flowexamlist").datagrid('getSelections');
		    	if(row.length == 0){
		    		$.messager.alert('提示信息', '请先选中需要取消邮寄/自取报告的人!','error');
		    		return;
		    	}
		    	var exam_num = new Array();
		    	for(i=0;i<row.length;i++){
		    		exam_num.push("'"+row[i].exam_num+"'");
		    	}
		    	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		    	$("body").prepend(str);
		    	$.ajax({
		    		url:'cancelReportMail.action',
		    		data:{exam_num:exam_num.toString()},
		    		type:'post',
		    		success:function(text){
		    			shangchuanchaxun();
		    			$(".loading_div").remove();
		    			if (text.split("-")[0] == 'ok') {
							alert_autoClose("操作提示", text.split("-")[1],"");
						} else {
							$.messager.alert("错误信息",text,"error");
						}
		    	    	if($("#ser_exam_num").val() != ''){
		    	    		getGrid($("#ser_exam_num").val());
		    	    	}else{
		    	    		getGrid();
		    	    	}
		    		},
		    		error:function(data){
		    			$(".loading_div").remove();
		    			$.messager.alert('提示信息', '操作失败',function(){});
		    		}
		    	});
		    }
		}
//		,{
//			text : '数据导出',
//			width : 90,
//			iconCls : 'icon-check',
//			handler : function() {
//				var filed1 =$(".datagrid-sort-asc").parent().eq(0).attr('field');
//				var filed2 = $(".datagrid-sort-desc").parent().eq(0).attr('field');
//				var sort = undefined;order = undefined;
//				if(filed1 != undefined){
//					sort = filed1;
//					order = 'asc';
//				}
//				if(filed2 != undefined){
//					sort = filed2;
//					order = 'desc';
//				}
//				var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
//				$("body").prepend(str);
//
//				var company = undefined;
//			     if($("#ck_com_Name")[0].checked){
//			    	 company =  $("#com_Name").val();
//				 }
//			     var dep_id = undefined;
//			     if($("#ck_dep_id")[0].checked){
//			    	 dep_id = document.getElementsByName("levelss")[0].value;
//			     }
//			     var exam_type = undefined;
//			     if($("#ck_exam_type")[0].checked){
//			    	 exam_type =  $("#exam_type").combobox('getValue');
//			     }
//			   	
//			     var exam_num = undefined;
//				 if($("#ck_exam_num_p")[0].checked){
//					 exam_num =  $("#exam_num_p").val();
//				 }
//				 var ptype = undefined;
//			     if($("#ck_ptype")[0].checked){
//			    	 ptype =  $('#ptype').combobox('getValue');
//			     }
//				 var time1 = undefined,time2 = undefined;
//				 if($("#ck_date")[0].checked){
//					 time1 = $("#time1").datebox('getValue');
//					 time2 = $("#time2").datebox('getValue');
//				 }
//				 var sex = undefined;
//				 if($("#ck_usersex")[0].checked){
//					 sex = $('#usersex').combobox('getValue');
//				 }
//				 var remark = undefined;
//				 if($("#ck_screater")[0].checked){
//					 remark = $("#screater").combobox('getValue');
//				 }
//				 
//				 var receive_date = undefined;
//				 if($("#ck_ser_receive_date")[0].checked){
//					 receive_date = $("#ser_receive_date").datebox('getValue');
//				 }
//				 var receive_type = undefined;
//				 if($("#ck_ser_receive_type")[0].checked){
//					 receive_type = $("#ser_receive_type").combobox('getValue');
//				 }
//				 var tstz = undefined;
//				 if($("#ck_tstz")[0].checked){
//					 tstz = $("#tstz").combobox('getValue');
//				 }
//			     
//			   var model={
//				 "exam_num":exam_num,
//				 "time1":time1,
//				 "time2":time2,
//				 "ptype":ptype,
//				 "remark":remark,
//				 "exam_type":exam_type,
//				 "username":username,
//				 "sex":sex,
//				 "company":company,
//				 "dep_id":dep_id,
//				 "receive_date":receive_date,
//				 "receive_type":receive_type,
//				 "remark1":tstz,
//				"page":1,
//				"rows":1000,
//				"sort":sort,
//				"order":order
//				};
//				$.ajax({
//					url:'flowexameListe.action',
//					data : model,
//					type : "post",//数据发送方式   
//					success : function(data) {
//						var temp = eval('('+data+')');
//						if(temp.rows.length == 0){
//							$(".loading_div").remove();
//							$.messager.alert("操作提示", "没有需要导出的回访人员信息!","error");
//							return;
//						}
//				    	var filelist = new Array();
//				    	var obj = new Object();
//				    	obj.exam_num = tjhname;
//				    	obj.arch_num = dahname;
//				    	obj.id_num = "身份证号";
//				    	obj.exam_types = "体检类型";
//				    	obj.user_name = "姓名";
//				    	obj.sex = "性别";
//				    	obj.is_marriage = "婚否";
//				    	obj.age = "年龄";
//				    	obj.phone = "手机号";
//				    	obj.company="单位";
//				    	obj.dep_name="部门";
//				    	obj.set_name = "套餐";
//				    	obj.join_date = "体检日期";
//				    	obj.statuss = "体检状态";
//				    	obj.stre0 = "整单接收状态";
//				    	obj.e0date = "整单接收时间";
//				    	obj.stre1 = "整单上传状态";
//				    	obj.e1date = "整单上传时间";
//				    	obj.receive_type_y = "邮寄或自取报告";
//				    	obj.receive_address = "邮寄地址";
//				    	obj.receive_remark = "备注";
//				    	
//				    	filelist.push(obj);
//				    	
//				    	for(i=0;i<temp.rows.length;i++){
//				    		obj = new Object();
//				    		obj.exam_num = temp.rows[i].exam_num;
//					    	obj.arch_num = temp.rows[i].arch_num;
//					    	obj.id_num = temp.rows[i].id_num;
//					    	obj.exam_types = temp.rows[i].exam_types;
//					    	obj.user_name = temp.rows[i].user_name;
//					    	obj.sex = temp.rows[i].sex;
//					    	obj.is_marriage = temp.rows[i].is_marriage;
//					    	obj.age = temp.rows[i].age;
//					    	obj.phone = temp.rows[i].phone;
//					    	obj.company = temp.rows[i].company;
//					    	obj.dep_name = temp.rows[i].dep_name;
//					    	obj.set_name = temp.rows[i].set_name;
//					    	obj.join_date = temp.rows[i].join_date;
//					    	obj.statuss = temp.rows[i].statuss;
//					    	obj.stre0 = temp.rows[i].stre0;
//					    	obj.e0date = temp.rows[i].e0date;
//					    	obj.stre1 = temp.rows[i].stre1;
//					    	obj.e1date = temp.rows[i].e1date;
//					    	obj.receive_type_y = temp.rows[i].receive_type_y;
//					    	obj.receive_address = temp.rows[i].receive_address;
//					    	obj.receive_remark = temp.rows[i].receive_remark;
//					    	obj.e1 = temp.rows[i].e1;
//					    	filelist.push(obj);
//				    	}
//				    	$.ajax({
//							url : 'saveDatagridData.action',
//							data : {filelist:JSON.stringify(filelist)},
//							type : "post",//数据发送方式   
//							success : function(text) {
//								$(".loading_div").remove();
//								if($("#ck_com_Name").is(":checked") && $("#com_Name").val()!=''){
//									window.location.href='datagridExportExcel_e1_red.action?filename='+$("#com_Name").val();
//								}else{
//									var myDate = new Date();
//									var month=myDate.getMonth()+1;
//									var riqi= myDate.getFullYear()+'-'+month+'-'+myDate.getDate();
//									/*var title = $('#bgqrlq').attr('value');*/
//									window.location.href='datagridExportExcel_e1_red.action?filename='+'收发室流程'+riqi;
//								}
//							},
//							error : function() {
//								$.messager.alert("操作提示", "导出excel出错","error");
//							}
//						});
//					},
//					error : function() {
//						$.messager.alert("操作提示", "导出excel出错","error");
//					}
//				});
//		    }
//		}
		];
   
   //批量上传
   function exame1insert(){
	   var listh1= new Array();
	   var checkedItems = $('#flowexamlist').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        if(item.e1==1){
	        	$.messager.alert("操作提示",tjhname+"号【"+item.exam_num+"】已经上传，操作不能继续","error");
	        	return false;
	        }else{
	        	listh1.push(item.exam_num);
	        }
	    });
	  if(listh1.length>0){
		  $.messager.confirm('提示信息','是否确定上传所选数据？',function(r){
				if(r){
			    $.ajax({
		   		url:'flowexame1insert.action',
		   		data : {
				    ids:listh1.toString()
					},
		   		type:'post',
		   		success:function(text){
		   			shangchuanchaxun();
					if (text.split("-")[0] == 'ok') {
						alert_autoClose("操作提示", text.split("-")[1],"");
					} else {
						$.messager.alert("警告信息",text,"error");
					}
		   		},
		   		error:function(){
		   			$.messager.alert('提示信息','操作失败！','error');
		   		}
		   		});
				}
			})  
	  }
   }
   
   //取消上传
   function exame1delete(){
	   var listh1= new Array();
	   var checkedItems = $('#flowexamlist').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        if(item.e1==0){
	        	$.messager.alert("操作提示", tjhname+"编号【"+item.exam_num+"】已经是未上传，操作不能继续","error");
	        	return false;
	        }else{
	        	listh1.push(item.exam_num);
	        }
	    });
	  if(listh1.length>0){
		  $.messager.confirm('提示信息','是否确定上传所选数据？',function(r){
				if(r){
			    $.ajax({
		   		url:'flowexame1delete.action',
		   		data : {
				    ids:listh1.toString()
					},
		   		type:'post',
		   		success:function(text){
		   			shangchuanchaxun();
					if (text.split("-")[0] == 'ok') {
						alert_autoClose("操作提示", text.split("-")[1],"");
					} else {
						$.messager.alert("错误信息",text,"error");
					}
		   		},
		   		error:function(){
		   			$.messager.alert('提示信息','操作失败！','error');
		   		}
		   		});
				}
			})  
	  }
   }
   
   function f_reportshow(val,row){
	   return '<a href=\"javascript:printreport(\''+row.exam_num+'\')\">预览</a>';
   }
   
   function yulanreport(){
	   if($("#exam_num_s").val()!=''){
		   printreport($("#exam_num_s").val());
	   }
   }
   
   function printreport(barids) {
	   if($("#report_print_type").val() == '5'){
			//预览普通报告
			var exeurl="reportServices://&0&"+barids+"&Y&0";//0代表体检号Y代表预览，2代表职业病
			location.href=exeurl;
	    }else{
	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-REPORT_PRINT_TYPE","error");
	    }
}
   
   function getPre_receive(){
	   var model={
		   time1:$("#time1_batch").datebox('getValue'),
		   time2:$("#time2_batch").datebox('getValue'),
	   };
	     $("#pre_receive").datagrid({
		 url:'getPre_receive.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 pageNumber:1,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
	     //pagination: true,
		 columns:[[
            //{field:'ck',checkbox:true },
		 	{align:'center',field:'p1date',title:'报告打印上传时间',sortable:true},
		 	{align:'center',field:'num',title:'人数'},
		    //{align:'center',field:'p0creater',title:'操作者'},	
		 	{align:'center',field:'p1creater',title:'上传者'},
		 	//{align : 'center',field : 'f_xg',title : '修改',"formatter" : f_xg}
		 	]],	    	
	    	onLoadSuccess:function(value){ 

	    	},
	    	onDblClickRow:function(index,row){
	    		getPre_receive_details(row.p1date);
	    	},
			rowStyler:function(index,row){    
		        if (row.e1==0){    
		            return 'color:red;';    
		        }  
		    },
		    //toolbar:toolbar
	     });
   }
     function getPre_receive_details(p1date){
    	 if(!p1date) {
    		 p1date = '1900-01-01 00:00:00';
    	 }
    	 var model={
    			 "time1":p1date,
    	 };
    	 $("#pre_receive_details").datagrid({
    		 url:'getPre_receive_details.action',
    		 dataType: 'json',
    		 queryParams:model,
    		 toolbar:'#toolbar',
    		 pageNumber:1,
    	     pageSize: 15,//每页显示的记录条数，默认为10 
    	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
    	     pagination: true,
    	     collapsible:true,
    	     rownumbers:true,
    	     fitColumns:true,
    		 columns:[[
                {field:'ck',checkbox:true },
    		    {align:'center',field:'exam_num',title:tjhname,sortable:true},
    		    {align:'center',field:'arch_num',title:dahname,sortable:true},
    		 	{align:'center',field:'id_num',title:'身份证号',sortable:true},
    		    {align:'center',field:'exam_types',title:'体检类型'},	
    		 	{align:'center',field:'user_name',title:'姓名',sortable:true},
    		 	{align:'center',field:'getReportWays',title:'报告发送方式',sortable:true},
    		 	{align:'center',field:'sex',title:'性别',sortable:true},
    		 	{align:'center',field:'is_marriage',title:'婚否'},
    		 	{align:'center',field:'age',title:'年龄',sortable:true},
    		 	{align:'center',field:'phone',title:'电话',sortable:true},
    		 	{align:'center',field:'company',title:'单位',width:100},
    		 	{align:'center',field:'join_date',title:'体检日期',sortable:true},	 	
    		 	{align:'center',field:'strp0',title:'接收状态'},
    		 	{align:'center',field:'p0date',title:'接收时间',sortable:true},		 	
    		 	{align:'center',field:'strp1',title:'上传状态'},
    		 	{align:'center',field:'p1date',title:'上传时间',sortable:true}
    		 	]],	    	
    	    	onLoadSuccess:function(value){
    	    		$("#datatotal").val(value.total);
    	    		$(".loading_div").remove();
    	    	},
    			rowStyler:function(index,row){    
    		        if (row.p1==0){    
    		            return 'color:red;';    
    		        }  
    		    },onDblClickRow:function(index,row){
    		    	liuchenbeizhuPage2(row);
	    		},
    		    toolbar:toolbar1
    	});
     }
	 var toolbar1=[{
		text:'批量核收',
		width:90,
		iconCls:'icon-save',
	    handler:function(){
	    	receiveExamInfo_batch();
	    }
	}];
	 /**
	  * 批量回收导引单
	  */
	  function receiveExamInfo_batch(){
		   var listh1= new Array();
		   var postNum = 0;
		   var getNum = 0;
		   var checkedItems = $('#pre_receive_details').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		        if(item.e0==1){
		        	$.messager.alert("操作提示",tjhname+"号【"+item.exam_num+"】已经核收，操作不能继续","error");
		        	return false;
		        }
		        if(item.getReportWay==1) {
		        	postNum++;
		        } else if(item.getReportWay==2) {
		        	getNum++;
		        }
		        listh1.push(item.exam_num);
		    });
		    if(postNum>0) {
		    	$.messager.confirm('提示信息','需要邮寄'+postNum+'份,自取'+getNum+'份',function(r){
					if(r){
						flowe0ExamInfo_batch(listh1);
					}
		    	});
		    } else {
		    	flowe0ExamInfo_batch(listh1);
		    }
	   }
	  
	  function flowe0ExamInfo_batch(listh1) {
		  if(listh1.length>0){
			  $.ajax({
			   		url:'flowe0ExamInfo_batch.action',
			   		data : {
					    ids:listh1.toString()
					},
			   		type:'post',
			   		success:function(text){
		   				getPre_receive();
		   				getPre_receive_details($('#pre_receive_details').datagrid('getRows')[0].p1date);
		   				shangchuanchaxun();
						if (text.split("-")[0] == 'ok') {
							alert("操作提示",text.split("-")[1],"");
						} else {
							$.messager.alert("警告信息",text,"error");
						}
			   		},
			   		error:function(){
			   			$.messager.alert('提示信息','操作失败！','error');
			   		}
			   });
		  }
	  }
	  
	  function liuchenbeizhuPage2(row){
			$.ajax({
				url:'getNumExamInfo.action?exam_num='+row.exam_num+"&dep_id=1",
				type:'post',
				success:function(data){
					if(data==1){
						$("#dlg-beizhu").dialog({
							title : '流程备注',
							width :800,
							height :500,
							href : 'remakPage.action?exam_num='+row.exam_num+"&process=6"
						});
						$("#dlg-beizhu").dialog('open');  
					} else {
						$.messager.alert("提示信息",tjhname+"无效","error");
						return;
					}
				},
				error:function(){
					$.messager.alert("提示信息","操作失败","error");
				}
			})
			
		}
  /**
    ****搜索添加输入框根据是否为空自动判断chebox选中
    */
    function setchebox(inp){
    	$('#report_class').textbox({  
    		onChange: function(value) {
    			if(value==""){
    				$('.ck_report_class').attr('checked',false)
    			} else {
    				$('.ck_report_class').attr('checked',true)
    			}
    		}
    	});
    	$('#ser_receive_name').textbox({  
    		onChange: function(value) {
    			if(value==""){
    				$('.ck_ser_receive_name').attr('checked',false)
    			} else {
    				$('.ck_ser_receive_name').attr('checked',true)
    			}
    		}
    	});
    	$('#exam_num_p').textbox({  
    		onChange: function(value) {
    			if(value==""){
    				$('.ck_exam_num_p').attr('checked',false)
    			} else {
    				$('.ck_exam_num_p').attr('checked',true)
    			}
    		}
    	});
    }
    

 //读取身份证
function djtreadcard_sfz() {
	var sfz_div_code = $("#sfz_div_code").val();
	var data=readCardSFZ(sfz_div_code);
	//var data={error_flag:'0',certno:'350128197102035428'};   //test data	
    if(data.error_flag=="0"){
    	var certno=data.certno;
    	if(certno.length==18){
    		$('#exam_num_p').textbox('setValue',certno);
    		shangchuanchaxun({
    			exam_num:$('#exam_num_p').val(),
	    	});
    	}else{
    		$.messager.alert("操作提示", "读取身份证号码错误", "error");	
    	}
    }else{
    	//$.messager.alert("操作提示", "读取身份证失败", "error");	
    	$.messager.alert("操作提示", data.error_msg, "error");
	}
}

$(function(){
    $("#dlg-edit").dialog({
        onClose: function () {
        shangchuanchaxun();
        }
    });
    $("#dlg-edit-thems").dialog({
        onClose: function () {
        shangchuanchaxun();
        }
    });
    $("#dlg-s").dialog({
        onClose: function () {
        shangchuanchaxun();
        }
    });
})