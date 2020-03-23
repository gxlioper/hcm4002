<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet"	href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet"	href="<%=request.getContextPath()%>/themes/default/dtreeck.css" />
<link type="text/css" rel="stylesheet"	href="<%=request.getContextPath()%>/themes/default/dtree.css" />

<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		f_findcustomerone($("#zybxz_exam_num").val(),0);

		$('#cyhy_Name').textbox('textbox').bind('click', function() {
			select_cyhy_list(this.value);
		});

		$('#cyhy_Name').textbox('textbox').bind('keyup', function() {
			select_cyhy_list(this.value);
		});

		$('#cyhy_Name').textbox('textbox').bind('blur', function() {
			select_cyhy_list_over();
		});

        $('#cygz_Name').textbox('textbox').bind('change', function(){
            $("#occutypeofworkid").val("");
        });

		$('#cygz_Name').textbox('textbox').bind('click', function() {
			select_cygz_list(this.value);
		});

		$('#cygz_Name').textbox('textbox').bind('keyup', function() {
			select_cygz_list(this.value);
		});

		$('#cygz_Name').textbox('textbox').bind('blur', function() {
			select_cygz_list_over();
		});
		if($('#zyb_tjlx').val()=='放射性体检'){
			//$("#yc_zy").css('display','none'); 
		} else {
			$("#yc_fs").css('display','none'); 
		}



	});

	//////////////////////////////////////////////////从业行业//////////////////////////////////////////////////////////////
	var hc_cyhy_list, cyhy_Namess;
	function select_cyhy_list(cyhy_Namess) {
		var url = 'getzybCyhyList.action';
		var data = {
			"com_Name" : cyhy_Namess
		};
		load_post(url, data, select_cyhy_list_sess);
	}
	/**
	 * 显示树形结构
	 * @param data
	 * @returns
	 */
	function select_cyhy_list_sess(data) {
		mydtree = new dTree('mydtree', '../../images/img/', 'no', 'no');
		mydtree.add(0, -1, "行业", "javascript:void(0)", "根目录", "_self", false);
		for (var index = 0, l = data.length; index < l; index++) {
			if ((data[index].attributes == null)
					|| (data[index].attributes == '')) {
				mydtree.add(data[index].id, 0, data[index].text,
						"javascript:setvaluecyhy('" + data[index].id + "','"
								+ data[index].text + "')", data[index].text,
						"_self", false);
			} else {
				mydtree.add(data[index].id, data[index].attributes,
						data[index].text, "javascript:setvaluecyhy('"
								+ data[index].id + "','" + data[index].text
								+ "')", data[index].text, "_self", false);
			}
		}
		$("#com_name_list_div1").html(mydtree.toString());
		$("#com_name_list_div1").css("display", "block");

	}

	/**
	 * 点击树设置内容
	 * @param id
	 * @param name
	 * @returns
	 */
	function setvaluecyhy(id, name) {
		$("#occusectorid").val(id);
		$("#cyhy_Name").textbox('setValue', name);
		$("#com_name_list_div1").css("display", "none");
	}

	//单位失去焦点
	var cyhy_mous_select1 = false;
	function select_cyhy_list_over() {
		if (!cyhy_mous_select1) {
			$("#com_name_list_div1").css("display", "none");
		}
	}

	function select_cyhy_list_mover() {
		cyhy_mous_select1 = true;
	}
	function select_cyhy_list_amover() {
		cyhy_mous_select1 = false;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////从业工种//////////////////////////////////////////////////////////////	
	var hc_cygz_list, cygz_Namess;
	function select_cygz_list(cygz_Namess) {
		var url = 'getzybCygzList.action';
		var data = {
			"com_Name" : cygz_Namess
		};
		load_post(url, data, select_cygz_list_sess);
	}
	/**
	 * 显示树形结构
	 * @param data
	 * @returns
	 */
	function select_cygz_list_sess(data) {
		mydtree = new dTree('mydtree', '../../images/img/', 'no', 'no');
		mydtree.add(0, -1, "工种", "javascript:void(0)", "根目录", "_self", false);
		for (var index = 0, l = data.length; index < l; index++) {
			if ((data[index].attributes == null)
					|| (data[index].attributes == '')
					|| (data[index].attributes == '0')) {
				mydtree.add(data[index].id, 0, data[index].text,
						"javascript:setvaluecygz('" + data[index].id + "','"
								+ data[index].text + "')", data[index].text,
						"_self", false);
			} else {
				mydtree.add(data[index].id, data[index].attributes,
						data[index].text, "javascript:setvaluecygz('"
								+ data[index].id + "','" + data[index].text
								+ "')", data[index].text, "_self", false);
			}
		}
		$("#com_name_list_div2").html(mydtree.toString());
		$("#com_name_list_div2").css("display", "block");

	}

	/**
	 * 点击树设置内容
	 * @param id
	 * @param name
	 * @returns
	 */
	function setvaluecygz(id, name) {
		$("#occutypeofworkid").val(id);
		$("#typeofwork_name").val(name);
		$("#cygz_Name").textbox('setValue', name);
		$("#com_name_list_div2").css("display", "none");
	}

	//单位失去焦点
	var cygz_mous_select1 = false;
	function select_cygz_list_over() {
		if (!cygz_mous_select1) {
			$("#com_name_list_div2").css("display", "none");
		}
	}

	function select_cygz_list_mover() {
		cygz_mous_select1 = true;
	}
	function select_cygz_list_amover() {
		cygz_mous_select1 = false;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	function isSZ(str) {
		return (/^[0-9]{1,20}$/.test(str));
	}

	

	function checkinput() {
		if (document.getElementById("employeeage").value != '' && !(isSZ(document.getElementById("employeeage").value))) {
			alert('工龄格式错误！');
			return false;
		}else if (Number(document.getElementById("employeeage").value) > 65) {
			alert('工龄不能超出65年！');
			return false;
		} /*else if (document.getElementById("employeeage").value != '' && Number(document.getElementById("employeeage").value) <= 0) {
			alert('工龄不能小于1年！');
			return false;
		}*/else if (!(isSZ(document.getElementById("damage").value))) {
			alert('接害工龄格式错误！');
			return false;
		}else if (Number(document.getElementById("damage").value) > 65) {
			alert('接害工龄不能超出65年！');
			return false;
		} /*else if (Number(document.getElementById("damage").value) <= 0) {
			alert('接害工龄不能小于1年！');
			return false;
		} else if ($("#joinDatetime").datebox('getValue') == '') {
			alert('进厂日期不能为空！');
			return false;
		}*/
		return true;
	}

	/**
	 * 保存修改
	 */
	function addcustomerdo() {
		if (checkinput()) {
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
					+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
						url : 'zybdjtaddcustomerdo.action',
						data : {							
							id_num : document.getElementById("addid_num").value,
							exam_id : document.getElementById("exam_id").value,
							exam_num : document.getElementById("zybxz_exam_num").value,
							arch_num : document.getElementById("arch_num").value,
							occusectorid:$("#occusectorid").val(),
							occutypeofworkid:$("#occutypeofworkid").val(),
							joinDatetime:$("#joinDatetime").datebox('getValue'),
							occusector:$("#occusector").val(),
							occutypeofwork:$("#occutypeofwork").val(),
							employeeage:$("#employeeage").val(),
							damage:$("#damage").val(),
							employeemonth:$("#employeemonth").val(),
							dammonth:$("#dammonth").val(),
                            typeofwork_name:$("#cygz_Name").val()
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								alert_autoClose("操作提示", "操作成功！","");
								f_findcustomerone(text.split("-")[1],0);
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

	$(function() {
		$('input').attr("maxlength", "20");
	})
	
	//显示一条数据
	var selectexam_id;
	var brushtatle=0;
	function f_findcustomerone(selectexam_id,brushtatle){
		$.post("getzybDjtExamOneShow.action", 
		{
			"exam_num" : selectexam_id
		}, function(jsonPost) {
			var customer = eval(jsonPost);	
			setCustomer(customer,brushtatle);
		}, "json");
	}
		
	function setCustomer(cumtomersd,brushtatle)
	{		
		$("#exam_id").val(cumtomersd.id);
		$("#arch_num").val(cumtomersd.arch_num);	
		//$("#zybxz_exam_num").val(cumtomersd.exam_num);
		$('#addid_num').val(cumtomersd.id_num);		
		$('#occusectorid').val(cumtomersd.occusectorid);
		$("#cyhy_Name").textbox('setValue', cumtomersd.cyhyname);		
		$('#occutypeofworkid').val(cumtomersd.occutypeofworkid);
		$("#cygz_Name").textbox('setValue', cumtomersd.cygzname);		
		$('#occutypeofwork').textbox('setValue',cumtomersd.occutypeofwork);
		$('#occusector').textbox('setValue',cumtomersd.occusector);		
		$('#damage').textbox('setValue',cumtomersd.damage);
		$('#employeeage').textbox('setValue',cumtomersd.employeeage);
		$("#joinDatetime").datebox('setValue',cumtomersd.joinDatetime);
		$('#dammonth').textbox('setValue',cumtomersd.dammonth);
		$('#employeemonth').textbox('setValue',cumtomersd.employeemonth);
		getJWScusthisGrid();
		if($('#zyb_tjlx').val()=='放射性体检'){
			getFscusthisGrid();
			getcusthisGrid();
		} else {
			getcusthisGrid();
		}
	}

//////////////////////////////职业病历史处理////////////////////////////////////////
var examnum = "";
function getcusthisGrid(c){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "exam_num":$("#zybxz_exam_num").val(),
				 "isradiation":"0"    //放射史
		 };
	     $("#zybocchislist").datagrid({
		 url:'zybCustomerHislist.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 5,//每页显示的记录条数，默认为10 
	     pageList:[5,10,20],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'company',title:'工作单位',width:18},	
		    {align:'center',field:'workshop',title:'车间部门',width:20},
		 	{align:'center',field:'worktype',title:'工种',width:25},
		 	{align:'center',field:'measure',title:'防护措施',width:30},
		 	{align:'center',field:'harmname',title:'有害因素名称',width:20},
		 	{align:'center',field:'concentrations',title:'有害因素浓度',width:10},
		 	{align:'center',field:'startdate',title:'开始时间',width:15},		 	
		 	{align:'center',field:'enddate',title:'结束时间',width:15},
			{align:'center',field:'ss',width:10,title:'修改',"formatter":f_xg_ZYS}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar	       
	});
}
function f_xg_ZYS(val,row){	
	return '<a href=\"javascript:updateSampleDemoZYS(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
 function updateSampleDemoZYS(id){
		$("#dlg-zybocchisedit").dialog({
		    //left:20,
			//top:0,
			title:'修改职业史',
			//width :1200,
			//height:590,
			href:'zybocchisaupdate.action?zyb_id='+id
		});
		$("#dlg-zybocchisedit").dialog('open');
}
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'新增',
		iconCls:'icon-add',
		width:58,
	    handler:function(){
	    	if((document.getElementById("addid_num").value=='')&&(document.getElementById("zybxz_exam_num").value=='')&&(document.getElementById("arch_num").value==''))
	    		{
	    		   $.messager.alert("操作提示", "体检人员无效，请先添加人员", "error");
	    		}else{
	    	       $("#dlg-zybocchisedit").dialog({
		 		       title:'职业史新增',
		 		       href:'zybocchisadd.action?exam_num='+document.getElementById("zybxz_exam_num").value+'&id_num='+document.getElementById("addid_num").value+'&arch_num='+document.getElementById("arch_num").value
		 	       });
		 	       $("#dlg-zybocchisedit").dialog('open');
	    		}
	    }
	},{
		text:'删除',
		width:58,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#zybocchislist').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    }); 	    	    
    	    delhisrow(ids);
	    }
	},{
		text:'<a href=\"../../zyb/registerDesk/zybtemplate.xls\">下载模板</a>',
		iconCls:'icon-check',
		handler:function(){
	    }
	},{
	    text:'导入职业史',
		width:100,
		iconCls:'icon-add',
	    handler:function(){
		    		$("#dlg-show").dialog({
				 		title:'上传文件',
				 		href:"getOccuhisUploadPage.action?uploadURL='com/hjw/zyb/ZybUpload2'"
				 	});
				 	$("#dlg-show").dialog('open');
	    	
	    }
    }];
 
 /**
  * 批量删除
  */
  var ids;
 function delhisrow(ids){
	/*  if(($("#addbatch_id").val()=='')||(Number($("#addbatch_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择体检任务","error");
 	}else */ 
 	if(ids==''){
 		$.messager.alert("操作提示", "请选择要删除的职业史","error");
 	}else{
	 $.messager.confirm('提示信息','是否确定删除选中职业史？',function(r){
		 	if(r){
		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	 $.ajax({
			url : 'zybocchisdeldo.action',
			data : {
		            ids:ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getcusthisGrid();
							alert_autoClose("操作提示", "操作成功！","");
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
	  * 设置每隔2秒钟刷新父节点的表格
	  */
   function djtupdateAfterClose() {
	     //父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口  
	     if(newWindow.closed == true) {
	     clearInterval(timer); 
	     return;  
	     }  
	}  
	//--------------------------放射性职业史---------------------
	 /**
  * 定义工具栏
  */
 var toolss=[{
		text:'新增',
		iconCls:'icon-add',
		width:58,
	    handler:function(){
	    	if((document.getElementById("addid_num").value=='')&&(document.getElementById("zybxz_exam_num").value=='')&&(document.getElementById("arch_num").value==''))
	    		{
	    		   $.messager.alert("操作提示", "体检人员无效，请先添加人员", "error");
	    		}else{
	    	       $("#dlg-fangsheshi").dialog({
		 		       title:'职业史新增',
		 		       href:'zybFsocchisadd.action?exam_num='+document.getElementById("exam_num").value+'&id_num='+document.getElementById("addid_num").value+'&arch_num='+document.getElementById("arch_num").value
		 	       });
		 	       $("#dlg-fangsheshi").dialog('open');
	    		}
	    }
	},{
		text:'删除',
		width:58,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#zybFsocchislist').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    }); 	    	    
    	    Fsdelhisrow(ids);
	    }
	},{
		text:'<a href=\"../../zyb/registerDesk/fangsheshi.xls\">下载模板</a>',
		iconCls:'icon-check',
		handler:function(){
	    }
	},{
	    text:'导入职业史',
		width:100,
		iconCls:'icon-add',
	    handler:function(){
		    		$("#dlg-show").dialog({
				 		title:'上传文件',
				 		href:"getOccuhisUploadPage.action?uploadURL='com/hjw/zyb/ZybUploadFS'"
				 	});
				 	$("#dlg-show").dialog('open');
	    	
	    }
    }];
 
	function getFscusthisGrid(c){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "exam_num":$("#exam_num").val(),
				 "isradiation":"1"    //放射史
		 };
	     $("#zybFsocchislist").datagrid({
		 url:'zybCustomerHislist.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 5,//每页显示的记录条数，默认为10 
	     pageList:[5,10,20],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'company',title:'工作单位',width:18},	
		    {align:'center',field:'workshop',title:'车间部门',width:20},
		 	{align:'center',field:'worktype',title:'工种',width:25},
		 	{align:'center',field:'radiation',title:'放射线种类',width:30},
		 	{align:'center',field:'man_haur',title:'每日工作量',width:20},
		 	{align:'center',field:'cumulative_exposure',title:'累积受照射剂量',width:20},
		 	{align:'center',field:'history_excessive',title:'过量照射史',width:20},
		 	{align:'center',field:'remark',title:'备注',width:10},
		 	{align:'center',field:'startdate',title:'开始时间',width:15},		 	
		 	{align:'center',field:'enddate',title:'结束时间',width:15},
		 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolss	       
	});
}
	 function f_xg(val,row){	
			return '<a href=\"javascript:updateSampleDemo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
		}
		 function updateSampleDemo(id){
					$("#dlg-fangsheshi").dialog({
				    //left:20,
					//top:0,
					title:'职业史',
					//width :1200,
					//height:590,
					href:'zybFsocchisupdate.action?zyb_id='+id
				});
				$("#dlg-fangsheshi").dialog('open');
		}
	/**
	  * 批量删除
	  */
	  var ids;
	 function Fsdelhisrow(ids){
	 	if(ids==''){
	 		$.messager.alert("操作提示", "请选择要删除的职业史","error");
	 	}else{
		 $.messager.confirm('提示信息','是否确定删除选中职业史？',function(r){
			 	if(r){
			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
		 $.ajax({
				url : 'zybocchisdeldo.action',
				data : {
			            ids:ids
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getFscusthisGrid();
								alert_autoClose("操作提示", "操作成功！","");
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
	    	
 
	//--------------------------既往史--------------------------
	
	function getJWScusthisGrid(c){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "exam_num":$("#zybxz_exam_num").val()
		 };
	     $("#zybJWSocchislist").datagrid({
		 url:'getDiseaseHistoryTable.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 5,//每页显示的记录条数，默认为10 
	     pageList:[5,10,20],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'left',field:'diseases',title:'疾病名称',width:18},	
		    {align:'left',field:'diagnosiscom',title:'诊疗单位',width:20},
		 	{align:'left',field:'diagnosisnotice',title:'治疗经过',width:25},
		 	{align:'left',field:'diseasereturn',title:'转归',width:30},
		 	{align:'center',field:'diagnosisdate',title:'诊断日期',width:20},
		 	
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolJWS	       
	});
}
	
	  var ids;
	  function JWSdelhisrow(ids){
	 	/*  if(($("#addbatch_id").val()=='')||(Number($("#addbatch_id").val())<=0)){
	  		$.messager.alert("操作提示", "请选择体检任务","error");
	  	}else */ 
	  	if(ids==''){
	  		$.messager.alert("操作提示", "请选择要删除的既往史","error");
	  	}else{
	 	 $.messager.confirm('提示信息','是否确定删除选中既往史？',function(r){
	 		 	if(r){
	 		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 				 $("body").prepend(str);
	 	 $.ajax({
	 			url : 'deletezybDiseaseHistory.action',
	 			data : {
	 		            idss:ids
	 					},
	 					type : "post",//数据发送方式   
	 					success : function(text) {
	 						$(".loading_div").remove();
	 						getJWScusthisGrid();
	 						$.messager.alert("操作提示", text, "info");
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
	  * 定义工具栏
	  */
	 var toolJWS=[{
			text:'新增',
			iconCls:'icon-add',
			width:58,
		    handler:function(){
		    	if((document.getElementById("addid_num").value=='')&&(document.getElementById("zybxz_exam_num").value=='')&&(document.getElementById("arch_num").value==''))
		    		{
		    		   $.messager.alert("操作提示", "体检人员无效，请先添加人员", "error");
		    		}else{
		    	       $("#dlg-jiwangshi").dialog({
			 		       title:'职业史新增',
			 		       href:'zybDiseaseHistory.action?exam_num='+document.getElementById("zybxz_exam_num").value+'&id_num='+document.getElementById("addid_num").value+'&arch_num='+document.getElementById("arch_num").value
			 	       });
			 	       $("#dlg-jiwangshi").dialog('open');
		    		}
		    }
		},{
			text:'删除',
			width:58,
			iconCls:'icon-cancel',
		    handler:function(){
		    	var ids = "";
		    	var checkedItems = $('#zybJWSocchislist').datagrid('getChecked');
	    	    $.each(checkedItems, function(index, item){
	    	    	   ids+="'"+item.id+"',";
	    	    }); 	    	    
	    	    var ss = ids.toString().substring(0,ids.length-1); 
	    	    JWSdelhisrow(ss);
		    }
		},{
			text:'<a href=\"../../zyb/registerDesk/jiwangshi.xls\">下载模板</a>',
			iconCls:'icon-check',
			handler:function(){
		    }
		},{
		    text:'导入既往史',
			width:100,
			iconCls:'icon-add',
		    handler:function(){
			    		$("#dlg-show").dialog({
					 		title:'上传文件',
					 		href:"getOccuhisUploadPage.action?uploadURL='com/hjw/zyb/ZybUploadJWS'"
					 	});
					 	$("#dlg-show").dialog('open');
		    	
		    }
	    }];
</script>

<input type="hidden" id="zyb_tjlx" value="<s:property value="data_name"/>">
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">
<input type="hidden" id="zybxz_exam_num" value="<s:property value="zybxz_exam_num"/>" >
<input type="hidden" id="arch_num">
<input type="hidden" id="occusectorid"	value="<s:property value="model.occusectorid"/>">
<input type="hidden" id="occutypeofworkid"	value="<s:property value="model.occutypeofworkid"/>">
<input type="hidden" id="typeofwork_name"	value=""/>
<input type="hidden" id="addid_num">

		<fieldset style="margin: 5px; padding-right: 0;">
			<legend>
				<strong>从业信息</strong>
			</legend>
			<div class="user-query">
				<dl>
					<dt>行业</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="cyhy_Name" value=""
							style="height: 26px; width: 200px;" />
						<div id="com_name_list_div1"
							style="display: none; height: 220px; width: 300px;; overflow-y: scroll; margin-left: 45px;"
							onmouseover="select_cyhy_list_mover()"
							onmouseout="select_cyhy_list_amover()"></div>
					</dd>
					<dt>从业行业</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="occusector"
							style="height: 26px; width: 280px;"
							value="<s:property value="model.occusector" />" />
					</dd>
				</dl>
				<dl>
					<dt>工种</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="cygz_Name" value=""
							style="height: 26px; width: 200px;" />
						<div id="com_name_list_div2"
							style="display: none; height: 220px; width: 300px;; overflow-y: scroll; margin-left: 45px;"
							onmouseover="select_cygz_list_mover()"
							onmouseout="select_cygz_list_amover()"></div>
					</dd>
					<dt>从业工种</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="occutypeofwork"
							style="height: 26px; width: 280px;"
							value="<s:property value="model.occutypeofwork" />" />
					</dd>
				</dl>
				<dl>
					<dt>工龄(年/月)<!--<strong class="quest-color">*</strong>--></dt>
					<dd>
						<input class="easyui-textbox" type="text" id="employeeage"
							style="height: 26px; width: 85px;"
							value="<s:property value="model.employeeage" />" />年
							<input class="easyui-textbox" type="text" id="employeemonth"
							style="height: 26px; width: 85px;"
							value="<s:property value="model.employeemonth" />" />月
					</dd>
					<dt style="width: auto;">接害工龄(年/月)<!--<strong class="quest-color">*</strong>-->
						
					</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="damage"
							style="height: 26px; width: 85px;"
							value="<s:property value="model.damage" />" />年
						<input class="easyui-textbox" type="text" id="dammonth"
							style="height: 26px; width: 85px;"
							value="<s:property value="model.dammonth" />" />月
					</dd>
				</dl>
				<dl>
					<dt>进厂日期<!--<strong class="quest-color">*</strong>--></dt>
					<dd>
						<input class="easyui-datebox"  value="<s:property value="model.joinDatetime"/>"
							data-options="validType:'Length[10]'" type=text id="joinDatetime"
							style="width: 100px; height: 26px;"></input>
					</dd>					
					<a href="javascript:addcustomerdo();" class="easyui-linkbutton c6"
			style="width: 100px;">保存</a>
				</dl>
			</div>
		</fieldset>
		<fieldset style="margin: 5px; padding-right: 0;"  id="yc_zy" >
			<legend>
				<strong>职业病史</strong>
			</legend>
			<div class="user-query">
			<table id="zybocchislist">
				</table>
			</div>
		</fieldset>
		<fieldset style="margin: 5px; padding-right: 0;"  id = "yc_fs">
			<legend>
				<strong>放射性职业史</strong>
			</legend>
			<div class="user-query">
			<table id="zybFsocchislist">
				</table>
			</div>
		</fieldset>
		<fieldset style="margin: 5px; padding-right: 0;" >
			<legend>
				<strong>既往史</strong>
			</legend>
			<div class="user-query">
			<table id="zybJWSocchislist">
				</table>
			</div>
		</fieldset>

<div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-zybocchisedit" class="easyui-dialog"  data-options="width: 850,height: 420,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-fangsheshi" class="easyui-dialog"  data-options="width: 850,height: 420,closed: true,cache: false,modal: true,top:100"></div>
<div id="dlg-jiwangshi" class="easyui-dialog"  data-options="width: 850,height: 420,closed: true,cache: false,modal: true,top:180"></div>