$(document).ready(function () { 
	
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	shangchuanchaxun();
	    	
	     }
	}
	
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

//获取分组信息列表
function f_getGroup(company_id){
	if(Number($('#tjrw').combobox('getValue'))<=0){
		$.messager.alert("操作提示", "体检任务不能为空", "error");
	}else{
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	    $("body").prepend(str);
   	    $("#group_id").combobox('clear');
   	    $("#set_id").combobox('clear');
	   	 $('#group_id').combobox({
	 		url : 'termgrouplistshow.action?batchid='+$('#tjrw').combobox('getValue'),
	 		editable : false, //不可编辑状态
	 		cache : false,
	 		panelHeight : 'auto',//自动高度适合
	 		valueField : 'id',
	 		textField : 'group_name',
	 		onLoadSuccess : function(data) {
	 			$(".loading_div").remove();
	 			for (var i = 0; i < data.length; i++) {
	 				$('#group_id').combobox('setValue', data[0].id);
	 				break;
	 			}
	 		},
	 		onChange:function(n,o){
	 	         getSetFrogroupId(n);
	 	     }
	 	});
     }
}

var groupset_id;
function getSetFrogroupId(groupset_id) {
	$('#set_id').combobox({
		url : 'termSetlistshow.action?group_id=' + groupset_id,
		editable : false, // 不可编辑状态
		cache : false,
		panelHeight : 'auto',// 自动高度适合
		valueField : 'id',
		textField : 'set_name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				$('#set_id').combobox('setValue', data[0].id);
				break;
			}
		}
	});
}

//获取单位///////////////////////////////////////////////////////////
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
		f_getDatadic(id);
	}
	
	//获取菜单
	function f_getDatadic(company_id) {
		$('#tjrw').combobox({
			url : 'getCompanForBatch.action?company_id='+company_id,
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				$('#tjrw').combobox('setValue', data[0].id);				
			},
			onChange:function(n,o){
				f_getGroup(n);
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
		getflowLoglist();
		flowmmessage();
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
				$("#exam_num").val(obj.exam_num);
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
				getGrid();
				getFinalSummary(obj.exam_num);
				getExamDisease(obj.exam_num);
				gettjbg(obj.exam_num,"");
			}else{
				$("#exam_num").val('');
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
    		var url='/showViewExamImage.action?pacs_req_code='+id+'exam_num='+$("#exam_num").val();
    		newWindow = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
    		newWindow.focus();
    	}
    	
   

    function flowmmessage() {
	$.ajax({
		url : 'flowmmessageshow.action',
		data : {
			exam_num : $("#exam_num_s").val()
		},
		type : 'post',
		success : function(text) {
			$("#flowmmessage").val(text);
			$("#exam_num_s").select();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
    }
    
    function flowmmessageupdate() {
    	if($("#flowmmessage").val()==""){
    		alert_autoClose("操作提示", "无效内容", "error");
    	}else{
    	$.ajax({
    		url : 'flowmmessageUpdate.action',
    		data : {
    			exam_num : $("#exam_num").val(),
    			remark:$("#flowmmessage").val()
    		},
    		type : 'post',
    		success : function(text) {
    			if (text.split("-")[0] == 'ok') {
					alert_autoClose("操作提示", text.split("-")[1],"");
				} else {
					$.messager.alert("错误信息",text,"error");
				}
    		},
    		error : function() {
    			$.messager.alert('提示信息', '操作失败！', 'error');
    		}
    	});
    	}
    }
 // /////////////////////////////////////上传////////////////////////////////////////////////////////////////////
    	
   function shangchuanchaxun(){
	   
	     var company = undefined;
	     if($("#ck_com_Name")[0].checked){
	    	 company =  $("#com_Name").val();
		 }
	     var batch_id = undefined;
	     if($("#ck_tjrw")[0].checked){
	    	 batch_id =  $("#tjrw").combobox('getValue');
	     }
	     var group_id = undefined;
	     if($("#ck_group_id")[0].checked){
	    	 group_id =  $("#group_id").combobox('getValue');
	     }
	     var set_id = undefined;
	     if($("#ck_set_id")[0].checked){
	    	 set_id =  $("#set_id").combobox('getValue');
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
		 var username = undefined;
		 if($("#ck_username")[0].checked){
			 username = $("#username").val();
		 }
		 var sex = undefined;
		 if($("#ck_usersex")[0].checked){
			 sex = $('#usersex').combobox('getValue');
		 }
		 var remark = undefined;
		 if($("#ck_screater")[0].checked){
			 remark = $("#screater").combobox('getValue');
		 }
		 
	   var model={
		 "exam_num":exam_num,
		 "time1":time1,
		 "time2":time2,
		 "ptype":ptype,
		 "remark":remark,
		 "exam_type":exam_type,
		 "username":username,
		 "sex":sex,
		 "company":company,
		 "batch_id":batch_id,
		 "group_id":group_id,
		 "set_id":set_id,
	   };
	     $("#flowexamlist").datagrid({
		 url:'flowexamListm.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 pageNumber:1,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表
	     pagination: true,
	     remoteSort:false,
	     collapsible:true,
	     fitColumns:true,
	     rownumbers:true,
		 columns:[[
            {field:'ck',checkbox:true },
            {align:'center',field:'examreport',title:'报告',"formatter":f_reportshow},
		    {align:'center',field:'exam_num',title:tjhname,sortable:true},
		    {align:'center',field:'arch_num',title:dahname,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',sortable:true},
		    {align:'center',field:'exam_types',title:'体检类型'},	
		 	{align:'center',field:'user_name',title:'姓名',sortable:true},
		 	{align:'center',field:'sex',title:'性别',sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否'},
		 	{align:'center',field:'age',title:'年龄',sortable:true},
		 	{align:'center',field:'phone',title:'电话',sortable:true},
		 	{align:'center',field:'company',title:'单位',width:100},
		 	{align:'center',field:'set_name',title:'套餐'},	
		 	{align:'center',field:'join_date',title:'体检日期',sortable:true},	 	
		  	{align:'center',field:'statuss',title:'体检状态'},
		 	{align:'center',field:'stre0',title:'整单接收状态'},
		 	{align:'center',field:'e0date',title:'整单接收时间',sortable:true},		 	
		 	{align:'center',field:'stre1',title:'整单上传状态'},
		 	{align:'center',field:'e1date',title:'整单上传时间',sortable:true}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	onDblClickRow : function(index, row) {	  
	    		$("#exam_num_s").val(row.exam_num)
	    		getexamallxmGrid();//放弃项目
	    		getcustomerInfo();//用户信息
	    		getflowLoglist();
	    		flowmmessage();
	    		$('#tts').tabs('select','解读室');
			},
		    toolbar:toolbar
	});
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
		}*/];
   
   //批量上传
   function exame1insert(){
	   var listh1= new Array();
	   var checkedItems = $('#flowexamlist').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        if(item.e1==1){
	        	$.messager.alert("操作提示", tjhname+"编号【"+item.exam_num+"】已经上传，操作不能继续","error");
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
   
   //取消上传
   function exame1delete(){
	   var listh1= new Array();
	   var checkedItems = $('#flowexamlist').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        if(item.e1==0){
	        	$.messager.alert("操作提示", "体检编号【"+item.exam_num+"】已经是未上传，操作不能继续","error");
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
   
   /**
    ****搜索添加输入框根据是否为空自动判断chebox选中
    */
    function setchebox(inp){
    	$('#username').textbox({  
    	    onChange: function(value) {
    	    	if(value==""){
    	    		$('.ck_username').attr('checked',false)
    	    	} else {
    	    		$('.ck_username').attr('checked',true)
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
//    			$('#lizi').textbox('setValue',obj.example+obj.examplenote);
    		}
    	})
    }