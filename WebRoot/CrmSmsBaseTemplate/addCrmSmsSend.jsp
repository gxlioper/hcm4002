<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
$(function (){
	$('#template_id').combobox({
		valueField:'id',    
		textField:'sms_name',
	   panelHeight:'auto',
	   onSelect:function(r){
			$('#sms_note').val(r.sms_note)
			$('#sms_note').focus();
		}
		
	})
	$.ajax({
		url:'queryCrmSmsBaseTemplate.action?page=1&pageSize=10000',
		type:'post',
		success:function(data){
			var da = eval('('+data+')');
			$('#template_id').combobox('loadData',da.rows);
		}
		
	})
	neirong();
	//=================
	
	$('#batch_ids').combobox({
			url : 'getBatch.action',
			editable : true, //不可编辑状态
			cache : false,
	// 		panelHeight : 'auto',//自动高度适合
			panelHeight : '300',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				$('#batch_ids').combobox('setValue', data[0].id);				
			}
		});
	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name'
	});
	$('#exam_num').textbox('textbox').css('ime-mode','disabled');
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
			for (var i = 0; i < data.length; i++) {
				$('#conn_rylb').combobox('setValue', data[0].id);
			}
		}
	});
	f_getdept();
//	getgroupuserGrid();	
	getgroupuserGrid_ys();	
	console.log($('#bodao_checkebox').val());
	if($('#bodao_checkebox').val()=='Y'){
		$('#isprintdjd').attr("checked", true);
		$('#isprinttm').attr("checked", true);
		$('#isprintdah').attr("checked", true);
	} else {
		$('#isprintdjd,#isprinttm,#isprintdah').attr("checked", false);
	}
})
function f_getdept() {
// 	var disease_name=$('#disease_id').val();
	$('#disease_id').combobox({
		url : 'crmSmsDiseaseList.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		multiple:false,
		textField : 'disease_name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
 				$('#disease_id').combobox('setValue', data[i].id);
 			}
		},
	});
}
function dianji(val){
	if(val=="1"){
		$('#ll').css('display','block');
		$('#22').css('display','block');
		$('#ll2').css('display','block');
		$('#ll3').css('display','block');
		$('#ll4').css('display','block');
		$('#ll5').css('display','block');
	} else {
		$('#ll').css('display','none');
		$('#22').css('display','none');
		$('#ll2').css('display','none');
		$('#ll3').css('display','none');
		$('#ll4').css('display','none');
		$('#ll5').css('display','none');
	}
}

function butt(row){
	var neirong = $('#sms_note').val();
	var data = neirong+row.remark;
	$('#sms_note').val(data)
	$('#sms_note').focus();
}
function neirong(){
	$('#neirong').datagrid({
		url:'getDatadis.action?com_Type=DXMBNR',
		columns:[[
			    {align:'center',field:'name',title:'内容',width:10},	
			 	]],
		 	  fitColumns:true,
		    striped:true,
		    fit:false,
		    onDblClickRow:function(index, row){
		    	butt(row);
	        }
	})
}
//=============================================
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




 

 //---------------------------------------显示人员------------------------------------------------------
 /**
  * 
  */
 function getgroupuserGrid(){
	   var custname = "";
		if($('#custname_cke').is(":checked")){
			custname = $('#custname').val();
	    }
	    var phone = "";
		if($('#phone_cke').is(":checked")){
			phone = $('#phone').val()
	    }
	    var exam_num = "";
	    if($('#exam_num_cke').is(":checked")){
	    	exam_num = $('#exam_num').val();
	    }
	    var time1 = "";
	    var time2 = "";
	    if($('#time_cke').is(":checked")){
	    	time1 = $('#start_date').datebox('getValue');
	    	time2 = $('#end_date').datebox('getValue');
	    }
	    var time3 = "";
	    var time4 = "";
	    if($('#time_zj').is(":checked")){
	    	time3 = $('#time3').datebox('getValue');
	    	time4 = $('#time4').datebox('getValue');
	    }
	    var time5 = "";
	    if($('#time_birthday').is(":checked")){
	    	time5 = $('#time5').datebox('getValue');
	    }
	    
	    var min_age =  $('#min_age').val();
	    var max_age =  $('#max_age').val();
	    
		 var company_id = "";
		    if($('#company_id_cke').is(":checked")){
		    	company_id = $('#company_id').val();
		    }
		    var batch_id = new Array();
		    if($('#batch_idss').is(":checked")){
		    	var batch_ids = $("#batch_ids").combobox('getValues');
		    	for(i=0;i<batch_ids.length;i++){
		    		batch_id.push(""+batch_ids[i]+"");
				  }
		    }
		    var sms_phone = "";
		    if($('#sms_phones').is(":checked")){
		    	sms_phone = $('#sms_phone').val();
		    }
		    var sex_type="";
		    if($('#sex_types').is(":checked")){
		    	sex_type = $('#sex_type').val();
		    }
		    var is_print="";
		    if($('#is_prints').is(":checked")){
		    	is_print = $('#is_print').val();
		    }
		    
		    
		    var sms_status = "";
		    if($('#sms_statuss').is(":checked")){
		    	sms_status = $('#sms_status').val();
		    }
		   
		    var statuss = new Array();
		    if($('#statuss').is(":checked")){
		    	 
				  var sta = $("#statusss").combobox('getValues');
				  for(i=0;i<sta.length;i++){
					  statuss.push("'"+sta[i]+"'");
				  }
		    }
// 		    var disease_id="";
		    var disease_id = new Array();
		    if($('#disease').is(":checked")){
		    	var sta = $("#disease_id").combobox('getValues');
				  for(i=0;i<sta.length;i++){
					  disease_id.push("'"+sta[i]+"'");
				  }
// 		    	disease_id = $('#disease_id').val()
		    }
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":company_id,
				 "batch_id" :batch_id.toString(),
				 "sms_phone":sms_phone,
				 "sms_status":sms_status,
				 "time3":time3,	
				 "time4":time4,
				 "time5":time5,
				 "sex_type":sex_type,
				 "is_print":is_print,
				 "min_age":min_age,
				 "max_age":max_age,
				 "exam_num":exam_num,
				 "time1":time1,	
				 "time2":time2,	 
				 "custname":custname,
				 "employeeID":'',
				 "djdstatuss":'',
				 "phone":phone,
				 "status":statuss.toString(),
				 "disease_id":disease_id.toString(),
				 "user_type":$('#user_type').val()
		 };
	     $("#groupusershow").datagrid({
		 url:'getUser.action',
		 dataType: 'json',
		 queryParams:model,
		 pageNumber:1,
// 	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100,300,500,1000],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'exam_num',title:tjhname,width:20,sortable:true},
		    /* {align:'center',field:'exam_types',title:'体检类型',width:18},	 */
		 	{align:'center',field:'user_name',title:'姓名',width:25,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'birthday',title:'出生日期',width:25,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:25,sortable:true},
		 	{align:'center',field:'status',title:'体检状态',width:20,"formatter":f_showstatus,sortable:true},
		 	{align:'center',field:'company',title:'单位',width:60},
		 	{align:'center',field:'join_date',title:'体检日期',width:20,sortable:true} 	
		 	]],	    	
	    	onLoadSuccess:function(value){ 	    		
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	onDblClickRow : function(index, row) {
	    		addrow(row);
			},
			height: window.screen.height-400,
			nowrap:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
		    toolbar:toolbar
	});
	}
 function shanchu(val,row){	
		return '<a href=\"javascript:sc(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
 function sc(index){
	 //-------------返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
	 //selectchargingItem是datagrid的id
	 var selections  =$('#groupusershow_ys').datagrid('getSelections');
	 var selectRows = [];
	 for ( var i= 0; i< selections.length; i++) {
	   selectRows.push(selections[i]);
	 }
	 for(var j =0;j<selectRows.length;j++){
	   var index = $('#groupusershow_ys').datagrid('getRowIndex',selectRows[j]);
	   //执行删除行
	   //selectchargingItem是datagrid的id,index要删除的行
	   $('#groupusershow_ys').datagrid('deleteRow',index);
	 }

 }
 function f_showstatus(val,row){
	  if(row.status=='Y'){
		  return '<font color="red">'+row.statuss+'</font>';
	  }else if(row.status=='D'){
		  return '<font color="blue">已报到</font>';
	  }else if(row.status=='J'){
		  return '<font color="green">'+row.statuss+'</font>';
	  }else{
		  return row.statuss;
	  }
}
 function addrow(row){
	 var data = $('#groupusershow_ys').datagrid('getRows');
	 var fal = "";
	 for(var i = 0 ; i < data.length ; i++){
		 //体检人员冲突
		 if(row.exam_num!="" && data[i].exam_num!=""){
			 if(data[i].id==row.id && data[i].exam_num==row.exam_num){
				 fal = "1";
				 break;
			 }
		 //工作人员冲突
		 } else {
			 if(data[i].id==row.id && data[i].exam_num==row.id){
				 fal = "1";
				 break;
			 }
		 }
	 }
	 if(fal=="1"){
		 $.messager.alert("警告信息","人员冲突","error");
		 return;
	 }
	 $('#groupusershow_ys').datagrid('appendRow',row);
 }
 var toolbar = [ {
		text : '批量添加人员',
		iconCls : 'icon-add',
		width : 120,
		handler : function() {
			var row = $('#groupusershow').datagrid('getChecked');
			console.log(row);
			var name = "";
			if(row.length<=0){
				$.messager.alert("警告信息","<font color='#FF4500'>人员无效</font>","error");
				return;
			}
			
			for(var i = 0 ; i < row.length ; i++){
				console.log(row[i].phone);
				if(row[i].phone.length!=11){
					name += row[i].user_name+"--"+row[i].phone+"</br>";
				}
			}
			if(name!=""){
				$.messager.alert("警告信息",name+"&nbsp;<font color='#FF4500'>手机号无效</font>","error");
				return;
			} 
			
			 var data = $('#groupusershow_ys').datagrid('getRows');
			 var chongtu = "";
			 for(var j = 0 ; j < row.length ; j++){
				 for(var i = 0 ; i < data.length ; i++){
					 //体检人员冲突
					 if(row[j].exam_num!="" && data[i].exam_num!=""){
						 if(data[i].id==row[j].id && data[i].exam_num==row[j].exam_num){
							 chongtu+=data[i].user_name+"--"+data[i].phone+"</br>";
						 }
					 //工作人员冲突
					 } else {
						 if(data[i].id==row[j].id){
							 chongtu+=data[i].user_name+"--"+data[i].phone+"</br>";
						 }
					 }
				 }
			 }
			
			
			 if(chongtu!=""){
				 $.messager.alert("警告信息",chongtu+"&nbsp;<font color='#FF4500'>人员冲突</font>","error");
				 return;
			 }
			
			 if(name==""&& chongtu==""){
				 for(var i = 0 ; i < row.length ; i++){
					 $('#groupusershow_ys').datagrid('appendRow',row[i]);
				}
			 }
		}
	}];
 	
 function getgroupuserGrid_ys(){
	     $("#groupusershow_ys").datagrid({
		 columns:[[
            {align:'center',field:'ss',title:'删除',width:10,'formatter':shanchu},
		    {align:'center',field:'id',title:'id',width:20,sortable:true},
		    {align:'center',field:'exam_num',title:tjhname,width:20,sortable:true},
		 	/* {align:'center',field:'id_num',title:'身份证号',width:40,sortable:true}, */
		    /* {align:'center',field:'exam_types',title:'体检类型',width:18},	 */
		 	{align:'center',field:'user_name',title:'姓名',width:25,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:25,sortable:true},
		 	{align:'center',field:'company',title:'单位',width:60},
		 	{align:'center',field:'join_date',title:'体检日期',width:20,sortable:true} 	
		 	]],	    	
			height: window.screen.height-400,
			nowrap:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    striped:true,
		    onLoadSuccess:function(value){ 	    		
	    		$('#groupusershow_ys').datagrid('hideColumn','id');
	    	}
	});
	}
//==========================================
function baocun(){
	if($('#sms_note').val()==""){
		$.messager.alert("提示信息","短信内容为必填项！","error")
		return;
	}
	if($('#sms_type').combobox('getValue')==""){
		$('#sms_type').textbox('textbox').focus();
		return;
	}
	
	//此处 修改为 延迟发送 必须选择  延迟时间 代码如下
	/* if($('#sms_type').combobox('getValue')=="0" && $('#sms_status').datebox('getValue')==""){
		
		$('#sms_status').textbox('textbox').focus();
		return;
	} */
	if($('#sms_type').combobox('getValue')=="0" && $('#sms_time').datebox('getValue')==""){
		
		$.messager.alert("提示信息","延迟发送必须选择延迟时间！","error")
		return;
	}
	var row = $('#groupusershow_ys').datagrid('getRows');
	var examinfo_id = new Array();
	var li = "{";
	var examinfo_id = new Array();
	
	//此处修改为  短信保存所需的必要 字段  不必把所有字段传到后台
	//因为model  传到后台用的 是examinfoDTO  后台代码中把json串转为 examinfo 的映射类 
	//如果 model中加字段属性 映射类中没有所加字段 会导致程序会报错
	//所有 不必把examinfo_id中所有 属性 传到后台  修改代码如下
	
	/* for(var i = 0 ; i < row.length ; i++){
		examinfo_id.push(JSON.stringify(row[i]));
	} */
	
	for(var i = 0 ; i < row.length ; i++){
		examinfo_id.push(JSON.stringify({
			id:row[i].id,
			exam_num:row[i].exam_num,
			phone:row[i].phone
		}));
	}
	li = li.substring(0,li.length-1)+"}";
	if(examinfo_id.length<=""){
		$.messager.alert('警告信息','请选择人员',"error");
		return;
	}
	var model = {
			li:'['+examinfo_id.toString()+']',
			id:$('#template_id').combobox('getValue'),
			sms_note:$('#sms_note').val(),
			sms_type:$('#sms_type').combobox('getValue'),
			sms_time:$('#sms_time').datebox('getValue')
	}
	  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	$.ajax({
		url:'saveCrmSmsSend.action',
		data:model,
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			if(data.indexOf("ok")>0){
				window.alert(data.replace('ok-',""));
				var _parentWin = window.opener;
				_parentWin.getgroupuserGrid();
				window.close();
			} else {
				window.alert(data.replace('error-',""));
			}
		},
		error:function(){
			$(".loading_div").remove();
			$.messager.alert('警告信息',"操作失败","error");
		}
	})
}

</script>
<input type="hidden"  id="id"  value="<s:property value='id'/>"/>
 <fieldset style=" margin: 10px;">
	<legend><strong>短信编辑</strong></legend> 
	<div class="formDiv" style=" margin-top: 15px;width:300px;float: left;">
		<dl>
			<dt style="width: 100px;">
				短信模板:
			</dt>
			<dd>
				<input type="text" class="easyui-combobox" id="template_id"   style="width:150px;height:30px;"  />
			</dd>
		</dl>
		<dl style="margin-top: 10px">
			<dt style="width: 100px;">短信发送类型:
			</dt>
			<dd >
				<select id="sms_type" class="easyui-combobox" data-options="panelHeight:'auto',required:true" name="dept" style="height:30px;width:150px;">   
						    <option value='1'>立即发送</option>   
						    <option value='0'>延期发送</option>   
				</select> 
			</dd>
		</dl>
		<dl  style="margin-top: 10px">
			<dt style="width: 100px;">
				延期时间
			</dt>
			<dd>
				<input class="easyui-datetimebox"  id="sms_time"  data-options="required:true" style="width:150px;height:30px;" />
			</dd>
		</dl>
	</div>
	<div class="formDiv" style=" margin-top: 15px;">
		<dl >
			<dt style="width: 100px;">短信内容:
			</dt>
			<dd >
				<textarea rows="" cols=""  id="sms_note"  style="width:400px;height:150px;resize: none"><s:property value='m.sms_note'/></textarea>
			</dd>
			<dd>
				<table id = "neirong"  style="width: 100px;height:150px;" >
				</table>
			</dd>
			<dd>
				<a href="javascript:baocun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">发送</a>
				<a href="javascript:window.close();"  class="easyui-linkbutton l-btn l-btn-small" style="height:26px;width:100px;">关闭</a>
			</dd>
		</dl>
	
	</div>
</fieldset>
<fieldset style=" margin: 10px;">
	<legend><strong>查询条件</strong></legend> 
	<div style="width:100%">
<%-- 		 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend> --%>
	<div class="user-query">
				<dl>
					<dt style="height:26px;width:80px;"><!-- <input id="user_type_cke" checked="checked"  type="checkbox" /> -->人员类型</dt>
			         <dd>
						<select id="user_type"  onchange="dianji(this.value)" style="height:26px;width:117px;">   
						    <option value='1'   >体检用户</option>   
						    <option value='0'>工作人员</option>   
						</select> 
					</dd>
					 
					<dt style="height:26px;width:60px;"><input id="custname_cke"  type="checkbox" value="custname"/>姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:117px;"/></dd>
					<dt style="height:26px;width:60px;"><input id="phone_cke"  type="checkbox" />手机号</dt>
			         <dd><input class="easyui-textbox" type=text id="phone"  style="width:100px;height:26px;"></input></dd>
				 <dt  id="ll2"  style="height:26px;width:80px;"><input id="statuss" name="statuss" type="checkbox" />体检状态</dt>	
                    <dd id="ll3"><select class="easyui-combobox" id="statusss" name="statusss"
						data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
						 <dt id="ll5" style="height:26px;width:98px;"><input id="disease" name="disease" type="checkbox"/>疾病名称</dt>	
                    <dd id="ll4"><select class="easyui-combobox" id="disease_id" name="disease_id"
						data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
					
					<dt style="height:26px;width:80px;"><input id="time_birthday"   type="checkbox" value="over_date"/>出生日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time5" value="<s:property value="model.time5"/>" style="width:100px;height:26px;"></input>
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
				</dl>
				<dl  id="22">
					 <dt  style="height:26px;width:80px;"><input id="batch_idss" name="batch_idss" type="checkbox"/>任务批次</dt>	
	                 <dd><select class="easyui-combobox" id="batch_ids" name="batch_ids" data-options="height:26,width:115,panelHeight:'auto'"></select></dd>
					 <dt style="height:26px;width:80px;"><input id="sms_phones" name="sms_phones" type="checkbox"/>电话号码</dt>
			         <dd>
						<select id="sms_phone"  name ="sms_phone"  style="height:26px;width:99px;">   
						    <option value='1'>有</option>   
						    <option value='0'>无</option>   
						</select> 
					 </dd>
					 
					 <dt style="height:26px;width:80px;"><input id="time_zj"   type="checkbox"  checked="checked" value="over_date"/>终检日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time3" value="<s:property value="model.time3"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:20px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="time4" value="<s:property value="model.time4"/>" style="width:100px;height:26px;"></input></dd> 
			         
					 <dt style="height:26px;width:120px;"><input id="sms_statuss" name="sms_statuss" type="checkbox"/>短信发送状态</dt>	</dt>
			         <dd>
						<select id="sms_status"  name ="sms_status"  style="height:26px;width:117px;">   
						    <option value=''>全部</option>   
						    <option value='0'>未发送</option>   
						    <option value='1'>发送成功</option>   
						    <option value='2'>发送失败</option>   
						</select> 
					 </dd>
					 
			         <dt style="height:26px;width:80px;"><input id="sex_types" name="sex_types" type="checkbox"/>性别</dt>
			         <dd>
						<select id="sex_type"  name="sex_type" style="height:26px;width:100px;">   
						    <option value='1'>男性</option>   
						    <option value='0'>女性</option>   
						</select> 
					</dd>
					<dt>最小年龄(≥)</dt>
					<dd>
						<input maxlength="20" class="easyui-textbox" type="text" id="min_age"
							value="<s:property value="model.min_age"/>"
							style="height: 26px; width: 40px;" />
					</dd>
				</dl>
             
                <dl  id="ll">
                	<dt style="height:26px;width:80px;"><input id="company_id_cke"  type="checkbox" />选择单位</dt>					
					<dd>
					<input type="hidden"  id="company_id"  value=""/>
					<input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:305px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:1px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
					
			        
					 <dt style="height:26px;width:80px;"><input id="time_cke"  type="checkbox"  checked="checked"  value=""/>体检日期</dt>
			         <dd><input class="easyui-datebox" type=text id="start_date" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:20px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>
					 <dt style="height:26px;width:120px;"><input id="exam_num_cke"  type="checkbox"  value="exam_num"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value=""  style="height:26px;width:117px;ime-mode: disabled;"/></dd>
					<dt style="height:26px;width:80px;"><input id="is_prints" name="is_prints" type="checkbox"/>打印状态</dt>
			         <dd>
						<select id="is_print"  name="is_print" style="height:26px;width:100px;">   
						    <option value='Y'>已打印</option>   
						    <option value='N'>未打印</option>   
						</select> 
					</dd>
					
					
					
					<dt>最大年龄(&lt;)</dt>
					<dd>
						<input class="easyui-textbox" maxlength="20" type="text" id="max_age"
							value="<s:property value="model.max_age"/>"
							style="height: 26px; width: 40px;" />
					</dd>
                </dl>
			</div>		
 <!-- </fieldset> -->
 </fieldset>
 <fieldset style="padding-right:0;width:48.5%;float: left;margin-left:5px;margin-right:5px;">
 
<legend><strong>人员列表</strong></legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;width:48.5%">
<legend><strong>已选人员列表</strong></legend>
      <table id="groupusershow_ys">
      </table>	
 </fieldset>

 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  
</div>

<!-- <div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:baocunmoban();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div> -->