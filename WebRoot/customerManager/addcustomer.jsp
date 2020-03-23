<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/sfzCard.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(document).ready(function() {
	f_getDatadic();
	
	$('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list();
    }); 
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list();
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    });	
	
	
	 $("#birthday").datebox({
	        onSelect: function(date){  
	        	selectbirthday();  
	        }  
	    });
	 
	 $('#group_id').next().children(':first').click(function () {
		  getgroups();      
	    });
	 
});

var hc_batchcom_list,batchcom_Namess;
function select_com_list(){
	var url='getbatchcompanyshow.action';
	var data={"company_id":$("#company_id").val()};
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
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].id == $("#company_id").val())){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:setvalue('"+data[index].id+"','"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvalue('"+data[index].id+"','"+data[index].text+"')",
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

//输入生日计算年龄
function selectbirthday(){
	var birthday =$("#birthday").datebox('getValue');
	if(birthday.length==10){
			 var myDate = new Date();
		     var month = myDate.getMonth() + 1;
		     var day = myDate.getDate();
		     var age = myDate.getFullYear() - birthday.substring(0, 4) - 1;
		     if (birthday.substring(5, 7) < month || birthday.substring(5, 7) == month && birthday.substring(8, 10) <= day) {
		        age++;
		     }
		     $('#age').textbox('setValue',age);
	}
}

//获取分组信息列表
function getgroups(){
	if(Number($("#company_id").val())<=0){
		$.messager.alert("操作提示", "单位不能为空", "error");
	}else if(Number($("#addbatch_id").val())<=0){
		$.messager.alert("操作提示", "体检任务不能为空", "error");
	}else{

	 $.ajax({
			url : 'getdjtTgroupInfoDo.action',
			data : {	
						age :document.getElementById("age").value,
						sex : document.getElementsByName("sex")[0].value,
						is_marriage : document.getElementsByName("is_Marriage")[0].value,
						others:'',
						company_id:$("#company_id").val(),
						batch_id:$("#addbatch_id").val(),
						position:$("#addposition").val()
					},
					type : "post",//数据发送方式   
					success : function(text) {
						 $("#group_id").combobox('clear');   
						 var data = $.parseJSON(text);
						 $("#group_id").combobox("loadData", data);
					},
					error : function() {
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
     }
}


//输入年龄计算生日
function selectage(){
	var age =$("#age").textbox('getValue');
	if(age.length>0){
			var myDate = new Date();
			var age1 = parseInt(myDate.getFullYear()) - parseInt(age);
			var birthday = age1 + "-" + (myDate.getMonth() + 1) + "-" + myDate.getDate();
			$("#birthday").datebox('setValue',birthday);
	}
}

//自动填充 生日、年龄、性别
function selectidnum(){
	var idnum= $("#addid_num").textbox('getValue');//取值 
	if(idnum.length==18){
		var ssidnum=idnum.substring(0,17);
		if (!isSZ(ssidnum)) {
			alert('身份证号格式错误！');
		}else{
		  $('#birthday').datebox('setValue',IdCard(idnum,1));
		  $('#age').textbox('setValue',IdCard(idnum,3));
		  $('#sex').combobox('setValue',IdCard(idnum,2));
		}
	}
}

function isSZ(str){	
	return (/^[0-9]{1,20}$/.test(str));	
}

//通过身份证号获取生日、年龄、性别
function IdCard(UUserCard,num){
	   if(num==1){
	       //获取出生日期
	       birth=UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14);
	    return birth;
	   }
	   if(num==2){
	       //获取性别
	       if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
	           //男
	     return "男";
	       } else {
	           //女
	     return "女";
	       }
	   }
	   if(num==3){
	        //获取年龄
	        var myDate = new Date();
	        var month = myDate.getMonth() + 1;
	        var day = myDate.getDate();
	        var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
	        if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) {
	            age++;
	        }
	  return age;
	 }
	}

	//获取菜单
	function f_getDatadic() {
		var sextype = '<s:property value="model.sex"/>';
		$('#sex').combobox({
			url : 'getDatadis.action?com_Type=XBLX2',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == sextype) {
						$('#sex').combobox('setValue', data[i].id);
						break;
					} else {
						$('#sex').combobox('setValue', data[0].id);
					}
				}
			}
		});
		
		    var leveltype = '<s:property value="model._level"/>';
			$('#addlevel').combobox({				
				url : 'getCompanForDept.action?company_id='+$("#company_id").val(),
				editable : false, //不可编辑状态
				cache : false,
				panelHeight : 'auto',//自动高度适合
				valueField : 'id',
				textField : 'dep_Name',
				onLoadSuccess : function(data) {
					for (var i = 0; i < data.length; i++) {
						if (data[i].id == leveltype) {
							$('#addlevel').combobox('setValue', data[i].id);
							break;
						} else {
							$('#addlevel').combobox('setValue', data[0].id);
						}
					}			
				}
			});

		var nationtype = '<s:property value="model.nation"/>';
		$('#minzhu').combobox({
			url :'getDatadis.action?com_Type=MZLX2',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == nationtype) {
						$('#minzhu').combobox('setValue', data[i].id);
						break;
					} else {
						$('#minzhu').combobox('setValue', data[0].id);
					}
				}	
			}
		});
		
		var customer_typetype = '<s:property value="model.customer_type"/>';
		$('#tjlx').combobox({
			url : 'getDatadis.action?com_Type=TJLX',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == customer_typetype) {
						$('#tjlx').combobox('setValue', data[i].id);
						break;
					} else {
						$('#tjlx').combobox('setValue', data[0].id);
					}
				}
			}
		});
		
		//费用类别
		var chargingTypetype = '<s:property value="model.chargingType"/>';
		$('#sftype').combobox({
			url : 'getDatadis.action?com_Type=SFTYPE',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == chargingTypetype) {
						$('#sftype').combobox('setValue', data[i].id);
						break;
					} else {
						$('#sftype').combobox('setValue', data[0].id);
					}
				}
			}
		});

		var customer_type_idtype = '<s:property value="model.customer_type_id"/>';
		$('#rylb').combobox({
			url : 'getDatadis.action?com_Type=RYLB',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == customer_type_idtype) {
						$('#rylb').combobox('setValue', data[i].id);
						break;
					} else {
						$('#rylb').combobox('setValue', data[0].id);
					}
				}
			}
		});

		var is_Marriagetype = '<s:property value="model.is_Marriage"/>';
		$('#is_Marriage').combobox({
			url : 'getDatadis.action?com_Type=HFLX',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == is_Marriagetype) {
						$('#is_Marriage').combobox('setValue', data[i].id);
						break;
					} else {
						$('#is_Marriage').combobox('setValue', data[0].id);
					}
				}
			}
		});
		
		$('#degreeOfedu').combobox({
			url : 'getDatadis.action?com_Type=WHCD',
			editable : false, // 不可编辑状态
			cache : false,
			panelHeight : 'auto',// 自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				$('#degreeOfedu').combobox('setValue', data[0].id);
			}
		});
		
		$('#ZZMM').combobox({
			url : 'getDatadis.action?com_Type=ZZMM',
			editable : false, // 不可编辑状态
			cache : false,
			panelHeight : 'auto',// 自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				$('#ZZMM').combobox('setValue', data[0].id);
			}
		});
	}

	function checkinput() {	
		if (document.getElementById("addcustname").value == '') {
			alert('姓名不能为空！');
			return false;
		}else if($('#idnum_notnull').val() == "Y" && document.getElementById("addid_num").value==""){
			alert('身份证号不能为空！');
			return false;
		}else if ((document.getElementById("addid_num").value!= '')&&(document.getElementById("addid_num").value.length!= 18)) {
			alert('身份证号位数错误！');
			return false;
		}else if ((document.getElementById("addid_num").value!= '')&&(!isSZ(document.getElementById("addid_num").value.substring(0,17)))) {
			alert('身份证号格式错误！');
			return false;
		}else if (document.getElementById("age").value == '') {
			alert('年龄不能为空！');
			return false;
		}if (!(isSZ(document.getElementById("age").value))) {
			alert('年龄格式错误！');
			return false;
		}  else if (Number(document.getElementById("age").value)>120) {
			alert('年龄不能超出120岁！');
			return false;
		} else if (Number(document.getElementById("age").value)<=0) {
			alert('年龄不能小于1岁！');
			return false;
		}else if (document.getElementsByName("sex")[0].value=='') {
			alert('性别不能为空！');
			return false;
		}else if ($("#birthday").datebox('getValue')=='') {
			alert('出生日期不能为空！');
			return false;
		}
		return true;
	}

	/**
	 * 保存修改
	 */
	function addcustomerdo() {
		if (checkinput()) {
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
			$.ajax({
				url : 'addcustomerdo.action',
				data : {
					        company_id:$("#company_id").val(),
							batch_id : $("#addbatch_id").val(),
							customer_id: $("#customer_id").val(),
							custname :document.getElementById("addcustname").value,
							id_num : document.getElementById("addid_num").value,
							exam_id : document.getElementById("exam_id").value,
							exam_num : document.getElementById("exam_num").value,
							arch_num : document.getElementById("arch_num").value,							
							age : $("#age").val(),
							sex : document.getElementsByName("sex")[0].value,
							is_marriage : document.getElementsByName("is_Marriage")[0].value,
							birthday:$("#birthday").datebox('getValue'),	
							customer_type:document.getElementsByName("tjlx")[0].value,
							nation:document.getElementsByName("minzhu")[0].value,
							customer_type_id:document.getElementsByName("rylb")[0].value,
							chargingType:document.getElementsByName("sftype")[0].value,
							tel : $("#addtel").val(),
							email : $("#email").val(),
							employeeID:$("#employeeID").val(),
							_level : document.getElementsByName("addlevel")[0].value,
							position : $("#addposition").val(),
							group_index:$("#group_index").val(),
							employeeID:$("#addemployeeID").val(),
							remark:$("#remark").val(),
							address:$("#address").val(),
							group_id:$('#group_id').combobox('getValue'),
							degreeOfedu:$("#degreeOfedu").combobox("getValue"),
							political_status:$("#ZZMM").combobox("getValue"),
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								$.messager.alert("操作提示", "操作完成");
								var _parentWin =  window.opener ; 
								_parentWin.getgroupuserGrid();
								window.close();
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

	$(function(){
		$('input').attr("maxlength","20");
	})
	
	//----------------------身份证号读取---------------------------------
	//读取身份证
	var conreadcard=0;
	function djtreadcard_sfz() {
	    if(conreadcard==0){
	    	var sfz_div_code = $("#sfz_div_code").val();
	    	var data=readCardSFZ(sfz_div_code);    	
	        if(data.error_flag=="0"){
	        	var certno=data.certno;
	        	if(certno.length==18){
	        		if($("#exam_id").val()<=0)
	        		{	        		
	        		setexaminfoall(data,certno);
	        		selectidnum();
	        		conreadcard=0;
	        	    }else{
	        		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			   	    $("body").prepend(str);
				    $.ajax({
						url : 'djtexamInfoforIdNum.action',
						data : {
							    exam_id:$("#exam_id").val(),
							    id_num:certno,
				                customer_id:$("#customer_id").val()
								},
								type : "post",//数据发送方式   
								success : function(text) {
									$(".loading_div").remove();
									conreadcard=0;
									if (text == '1') {
										//可以正常覆盖
										if(data.sex!=document.getElementsByName("sex")[0].value){
											$.messager.confirm('提示信息','身份证和当前录入人员性别不一致，强制修改可能引起缴费项目混乱，你确定要用此身份证信息覆盖吗？',function(r){
											 	if(r){
													setexaminfoall(data,certno);
													selectidnum();
											 	}
										 });
										}else{
											setexaminfoall(data,certno);
											selectidnum();
										}
									  //可以正常覆盖---------------------------结束--------------------------------------										
									}else if (text == '2'){
										//提示是否需要覆盖
										 $.messager.confirm('提示信息','系统里存在相同身份证号，你确定要用此身份证信息覆盖吗？',function(r){
											 	if(r){
											 		if(data.sex!=document.getElementsByName("sex")[0].value){
														$.messager.confirm('提示信息','身份证和当前录入人员性别不一致，强制修改可能引起缴费项目混乱，你确定要用此身份证信息覆盖吗？',function(r){
														 	if(r){
																setexaminfoall(data,certno);
																selectidnum();
														 	}
													 });
													}else{
														setexaminfoall(data,certno);
														selectidnum();
													}
											 	}
										 });
									}								
								},
								error : function() {
									conreadcard=0;
									$(".loading_div").remove();
									$.messager.alert("操作提示", "操作错误", "error");					
								}
							});
	        	}	
	        	}else{
	        		$.messager.alert("操作提示", "读取身份证号码错误", "error");	
	        	}
	        }else{
	        	//$.messager.alert("操作提示", "读取身份证失败", "error");	
	        	$.messager.alert("操作提示", data.error_msg, "error");
	        }
	        
	    }
}

	var data,certno;
	function setexaminfoall(data,certno){
		$('#addid_num').textbox('setValue',certno);
		$("#addcustname").textbox('setValue',data.name);
		$("#sex").textbox('setValue',data.sex);
		$("#address").textbox('setValue',data.address);
		var object_minzhu = $('#minzhu').combobox('getData');
        for(var i=0;i<object_minzhu.length;i++) {
        	if (object_minzhu[i].name.trim() == data.nation) {
        		$('#minzhu').combobox('setValue', object_minzhu[i].id);
        		break;
        	}
        }
		var bords = data.birth;
		if(bords.length==8){
			var birthday= bords.substring(0,4)+"-"+bords.substring(4,6)+"-"+bords.substring(6,8);
			$('#birthday').datebox('setValue',birthday);						
				 var myDate = new Date();
			     var month = myDate.getMonth() + 1;
			     var day = myDate.getDate();
			     var age = myDate.getFullYear() - birthday.substring(0, 4) - 1;
			     if (birthday.substring(5, 7) < month || birthday.substring(5, 7) == month && birthday.substring(8, 10) <= day) {
			        age++;
			     }
			     $('#age').textbox('setValue',age);
		}
	}
</script>
<!-- 定义身份证设备 -->
<OBJECT ID='GT2ICROCX' width='0' height='0' <s:property value="sfz_div_ocx"/>></OBJECT>
<input type="hidden" id="sfz_div_code" value="<s:property value="sfz_div_code"/>">
<!-- 定义身份证设备结束 -->
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="addbatch_id" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="customer_id" value="<s:property value="model.customer_id"/>">
<input type="hidden" id="exam_id" value="<s:property value="model.id"/>"> 
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="arch_num" value="<s:property value="model.arch_num"/>">
<input type="hidden" id="idnum_notnull" value="<s:property value="model.idnum_notnull"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检信息增加</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dd style="height: 20px; width: 120px;">体检任务名称：</dd>
			<dd style="height: 20px; width: 140px;">
				<s:property value="model.batch_name" />
			</dd>
			<dd style="height: 20px; width: 100px;">单位部门：</dd>
			<dd style="height:20px;">
				<input class="easyui-textbox" type="text" id="com_Name" value="" style="height:26px;width:140px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:305px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
             </dd>
              <dd style="height:20px;"><a  href="javascript:void(0)" onClick="djtreadcard_sfz()"><img style="height:25px;width:25px;" title="身份证获取人员信息" src="<%=request.getContextPath()%>/themes/default/images/shengfencod.png" /></a></dd>      
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员基本信息</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>
				姓名<strong class="quest-color">*</strong>
			</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="addcustname"
					style="height: 26px; width: 140px;" value="<s:property value="model.custname" />"/>
			</dd>
			<dt>
				身份证号 <strong class="quest-color">*</strong>
			</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="addid_num" 
					style="height: 26px; width: 140px;" value="<s:property value="model.id_num" />" data-options="events:{blur:selectidnum}"/>
			</dd>
			
			<dt>工号</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="addemployeeID"
					style="height: 26px; width: 140px;" value="<s:property value="model.employeeID" />"/>
			</dd>
		</dl>
		<dl>
			<dt>性别<strong class="quest-color">*</strong></dt>
			<dd>
				<select class="easyui-combobox" id="sex" name="sex"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
			<dt>婚否</dt>
			<dd>
				<select class="easyui-combobox" id="is_Marriage" name="is_Marriage"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
			<dt>年龄<strong class="quest-color">*</strong></dt>
			<dd>
				<input class="easyui-textbox" type="text" data-options="events:{blur:selectage}" id="age"
					style="height: 26px; width: 50px;" value="<s:property value="model.age" />"/>
			</dd>
			
			
		</dl>
		<dl>
		    <dt>体检类别</dt>
			<dd><select class="easyui-combobox" id="tjlx" name="tjlx"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
			<dt>民族</dt>
			<dd><select class="easyui-combobox" id="minzhu" name="minzhu"	panelMaxHeight="300px" 
							data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
			
			<dt>出生日期<strong class="quest-color">*</strong></dt>
			<dd><input class="easyui-datebox" value="<s:property value="model.birthday"/>" data-options="validType:'Length[10]',events:{blur:selectbirthday}" type=text id="birthday" style="width:100px;height:26px;"></input>
			</dd>
		</dl>
		<dl>
		<dt>费别</dt>
			<dd>
				<select class="easyui-combobox" id="sftype" name="sftype"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
			<dt>联系电话</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="addtel" 
					style="height: 26px; width: 140px;" value="<s:property value="model.tel" />"/>
			</dd>
			<dt>人员类别</dt>
			<dd><select class="easyui-combobox" id="rylb" name="rylb"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
		</dl>
		<dl>
		    <dt>部门</dt>
			<dd><select class="easyui-combobox" id="addlevel" name="addlevel"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
			<dt>职务</dt>
			<dd>
				<input class="easyui-textbox" type="text"  id="addposition" 
					style="height: 26px; width: 140px;" value="<s:property value="model.position" />"/>
			</dd>
			<dt>邮箱</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="email"
					style="height: 26px; width: 140px;" value="<s:property value="model.email" />"/>
			</dd>			
		</dl>
		<dl>		 
		     <dt>选择分组</dt>
			<dd><select class="easyui-combobox" id="group_id" name="group_id"
					data-options="valueField:'id', textField:'group_name',height:26,width:140,panelHeight:'auto'"></select>
			</dd>   
			<dt>通讯住址</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="address"
					style="height: 26px; width: 390px;"  value="<s:property value="model.address" />"/>
			</dd>			
		</dl>
		<dl>	
			<dt>备注</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="remark"
					style="height: 26px; width: 390px;"  value="<s:property value="model.remarke" />"/>
			</dd>
			<dt>分组序列</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="group_index"
					style="height: 26px; width: 140px;" value="<s:property value="model.group_index" />"/>
			</dd>			
		</dl>
		<dl>
            <dt>学历</dt>
			<dd>
				<select class="easyui-combobox" id="degreeOfedu" name="degreeOfedu"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
		    <dt>政治面貌</dt>
			<dd>
				<select class="easyui-combobox" id="ZZMM" name="ZZMM"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
		</dl>
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addcustomerdo();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close();">关闭</a>
	</div>
</div>