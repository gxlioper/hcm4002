<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
#main {	width: auto;height: auto;}
#left {	width: 55%;	height: auto;}
#right {width: 40%;	height: auto;margin-left: 10px;}
#left, #right {float: left;}
</style>
<link type="text/css" rel="stylesheet"	href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet"	href="<%=request.getContextPath()%>/themes/default/dtreeck.css" />
<link type="text/css" rel="stylesheet"	href="<%=request.getContextPath()%>/themes/default/dtree.css" />
 
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/sfzCard.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		f_getDatadic();
		f_findcustomerone($("#exam_num").val(),0);
		getcusthisGrid();
		getFscusthisGrid();
		getJWScusthisGrid();
		getoccuhazardfactorsGrid();
		zybsetselectListGrid();
		zybcustChangItemListGrid();
		gettotalinfo();
		$('#com_Name').textbox('textbox').bind('click', function() {
			select_com_list();
		});

		$('#com_Name').textbox('textbox').bind('keyup', function() {
			select_com_list();
		});

		$('#com_Name').textbox('textbox').bind('blur', function() {
			select_com_list_over();
		});

		$('#cyhy_Name').textbox('textbox').bind('click', function() {
			select_cyhy_list(this.value);
		});

		$('#cyhy_Name').textbox('textbox').bind('keyup', function() {
			select_cyhy_list(this.value);
		});

		$('#cyhy_Name').textbox('textbox').bind('blur', function() {
			select_cyhy_list_over();
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

		$("#birthday").datebox({
			onSelect : function(date) {
				selectbirthday();
			}
		});
	});

	var hc_batchcom_list, batchcom_Namess;
	function select_com_list() {
		var url = 'getbatchcompanyshow.action';
		var data = {
			"company_id" : $("#company_id").val()
		};
		load_post(url, data, select_com_list_sess);
	}
	/**
	 * 显示树形结构
	 * @param data
	 * @returns
	 */
	function select_com_list_sess(data) {
		mydtree = new dTree('mydtree', '../../images/img/', 'no', 'no');
		mydtree.add(0, -1, "单位", "javascript:void(0)", "根目录", "_self", false);
		for (var index = 0, l = data.length; index < l; index++) {
			if ((data[index].attributes == null)
					|| (data[index].attributes == '')
					|| (data[index].id == $("#company_id").val())) {
				mydtree.add(data[index].id, 0, data[index].text,
						"javascript:setvalue('" + data[index].id + "','"
								+ data[index].text + "')", data[index].text,
						"_self", false);
			} else {
				mydtree.add(data[index].id, data[index].attributes,
						data[index].text, "javascript:setvalue('"
								+ data[index].id + "','" + data[index].text
								+ "')", data[index].text, "_self", false);
			}
		}
		$("#com_name_list_div").html(mydtree.toString());
		$("#com_name_list_div").css("display", "block");

	}

	/**
	 * 点击树设置内容
	 * @param id
	 * @param name
	 * @returns
	 */
	function setvalue(id, name) {
		$("#company_id").val(id);
		$("#com_Name").textbox('setValue', name);
		$("#com_name_list_div").css("display", "none");
	}

	//单位失去焦点
	var hc_mous_select1 = false;
	function select_com_list_over() {
		if (!hc_mous_select1) {
			$("#com_name_list_div").css("display", "none");
		}
	}

	function select_com_list_mover() {
		hc_mous_select1 = true;
	}
	function select_com_list_amover() {
		hc_mous_select1 = false;
	}

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
		mydtree.add(0, -1, "单位", "javascript:void(0)", "根目录", "_self", false);
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
		mydtree.add(0, -1, "单位", "javascript:void(0)", "根目录", "_self", false);
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

	//输入生日计算年龄
	function selectbirthday() {
		var birthday = $("#birthday").datebox('getValue');
		if (birthday.length == 10) {
			var myDate = new Date();
			var month = myDate.getMonth() + 1;
			var day = myDate.getDate();
			var age = myDate.getFullYear() - birthday.substring(0, 4) - 1;
			if (birthday.substring(5, 7) < month
					|| birthday.substring(5, 7) == month
					&& birthday.substring(8, 10) <= day) {
				age++;
			}
			$('#age').textbox('setValue', age);
		}
	}

	//输入年龄计算生日
	function selectage() {
		var age = $("#age").textbox('getValue');
		if (age.length > 0) {
			var myDate = new Date();
			var age1 = parseInt(myDate.getFullYear()) - parseInt(age);
			var birthday = age1 + "-" + (myDate.getMonth() + 1) + "-"
					+ myDate.getDate();
			$("#birthday").datebox('setValue', birthday);
		}
	}

	//自动填充 生日、年龄、性别
	function selectidnum() {
		var idnum = $("#addid_num").textbox('getValue');//取值 
		if (idnum.length == 18) {
			var ssidnum = idnum.substring(0, 17);
			if (!isSZ(ssidnum)) {
				alert('身份证号格式错误！');
			} else {
				$('#birthday').datebox('setValue', IdCard(idnum, 1));
				$('#age').textbox('setValue', IdCard(idnum, 3));
				$('#sex').combobox('setValue', IdCard(idnum, 2));
			}
		}
	}

	function isSZ(str) {
		return (/^[0-9]{1,20}$/.test(str));
	}

	//通过身份证号获取生日、年龄、性别
	function IdCard(UUserCard, num) {
		if (num == 1) {
			//获取出生日期
			birth = UUserCard.substring(6, 10) + "-"
					+ UUserCard.substring(10, 12) + "-"
					+ UUserCard.substring(12, 14);
			return birth;
		}
		if (num == 2) {
			//获取性别
			if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
				//男
				return "男";
			} else {
				//女
				return "女";
			}
		}
		if (num == 3) {
			//获取年龄
			var myDate = new Date();
			var month = myDate.getMonth() + 1;
			var day = myDate.getDate();
			var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
			if (UUserCard.substring(10, 12) < month
					|| UUserCard.substring(10, 12) == month
					&& UUserCard.substring(12, 14) <= day) {
				age++;
			}
			return age;
		}
	}

	//获取菜单
	function f_getDatadic() {
		$('#sex').combobox({
			url : 'getDatadis.action?com_Type=XBLX2',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				
			}
		});

		var leveltype = '<s:property value="model._level"/>';
		$('#addlevel')
				.combobox(
						{
							url : 'getCompanForDept.action?company_id='
									+ $("#company_id").val(),
							editable : false, //不可编辑状态
							cache : false,
							panelHeight : 'auto',//自动高度适合
							valueField : 'id',
							textField : 'dep_Name',
							onLoadSuccess : function(data) {
								for (var i = 0; i < data.length; i++) {
									if (data[i].id == leveltype) {
										$('#addlevel').combobox('setValue',
												data[i].id);
										break;
									} else {
										$('#addlevel').combobox('setValue',
												data[0].id);
									}
								}
							}
						});

		$('#minzhu').combobox({
			url : 'getDatadis.action?com_Type=MZLX2',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				
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
				 var cm_type = $('#tjlx').combobox('getData');
				 $('#tjlx').combobox('setValue',cm_type[0].id);
				 var te = $('#tjlx').combobox('getText');
					/* if(cm_type[0].name=='放射性体检'){
						$("#yc_zy").css('display','none'); 
					} else {
						$("#yc_fs").css('display','none'); 
					} */
					
			},
			onSelect:function(record){
				if(record.name=='放射性体检'){
					$("#yc_fs").css('display','block'); 
					$("#yc_zy").css('display','block'); 
				}else {
					$("#yc_fs").css('display','none'); 
					$("#yc_zy").css('display','block'); 
					
				}
			}
		});

		//费用类别
		$('#sftype').combobox({
			url : 'getDatadis.action?com_Type=SFTYPE',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				
			}
		});

		$('#rylb').combobox({
			url : 'getDatadis.action?com_Type=RYLB',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				
			}
		});

		$('#is_Marriage').combobox({
			url : 'getDatadis.action?com_Type=HFLX',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				
			}
		});
		
		$('#zyb_set_source').combobox({
			data:[{
		 		id:'0',value:'按职业危害因素'
		 	},{
		 		id:'1',value:'自选套餐'
		 	}],
			valueField : 'id',
			textField : 'value',
		    onLoadSuccess : function(data) {
				$('#zyb_set_source').combobox('select', data[0].id);
			}
		});
	}

	function checkinput() {
		if (document.getElementById("addcustname").value == '') {
			alert('姓名不能为空！');
			return false;
		} else if ((document.getElementById("addid_num").value != '')
				&& (document.getElementById("addid_num").value.length != 18)) {
			alert('身份证号位数错误！');
			return false;
		} else if ((document.getElementById("addid_num").value != '')
				&& (!isSZ(document.getElementById("addid_num").value.substring(
						0, 17)))) {
			alert('身份证号格式错误！');
			return false;
		} else if (document.getElementById("age").value == '') {
			alert('年龄不能为空！');
			return false;
		}
		if (!(isSZ(document.getElementById("age").value))) {
			alert('年龄格式错误！');
			return false;
		} else if (Number(document.getElementById("age").value) > 120) {
			alert('年龄不能超出120岁！');
			return false;
		} else if (Number(document.getElementById("age").value) <= 0) {
			alert('年龄不能小于1岁！');
			return false;
		} else if (document.getElementsByName("sex")[0].value == '') {
			alert('性别不能为空！');
			return false;
		} else if ($("#birthday").datebox('getValue') == '') {
			alert('出生日期不能为空！');
			return false;
		}else if (!(isSZ(document.getElementById("employeeage").value))) {
			alert('工龄格式错误！');
			return false;
		}else if (Number(document.getElementById("employeeage").value) > 65) {
			alert('工龄不能超出65年！');
			return false;
		} else if (Number(document.getElementById("employeeage").value) <= 0) {
			alert('工龄不能小于1年！');
			return false;
		}else if (!(isSZ(document.getElementById("damage").value))) {
			alert('接害工龄格式错误！');
			return false;
		}else if (Number(document.getElementById("damage").value) > 65) {
			alert('接害工龄不能超出65年！');
			return false;
		} else if (Number(document.getElementById("damage").value) <= 0) {
			alert('接害工龄不能小于1年！');
			return false;
		} else if ($("#joinDatetime").datebox('getValue') == '') {
			alert('进厂日期不能为空！');
			return false;
		}
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
			$
					.ajax({
						url : 'zybaddcustomerdo.action',
						data : {
							company_id : $("#company_id").val(),
							batch_id : $("#addbatch_id").val(),
							customer_id : $("#customer_id").val(),
							custname : document.getElementById("addcustname").value,
							id_num : document.getElementById("addid_num").value,
							exam_id : document.getElementById("exam_id").value,
							exam_num : document.getElementById("exam_num").value,
							arch_num : document.getElementById("arch_num").value,
							age : $("#age").val(),
							sex : document.getElementsByName("sex")[0].value,
							is_marriage : document.getElementsByName("is_Marriage")[0].value,
							birthday : $("#birthday").datebox('getValue'),
							customer_type : document.getElementsByName("tjlx")[0].value,
							nation : document.getElementsByName("minzhu")[0].value,
							customer_type_id : document.getElementsByName("rylb")[0].value,
							chargingType : document.getElementsByName("sftype")[0].value,
							tel : $("#addtel").val(),
							email : $("#email").val(),
							_level : document.getElementsByName("addlevel")[0].value,
							position : $("#addposition").val(),
							employeeID : $("#addemployeeID").val(),
							remark : $("#remark").val(),
							address : $("#address").val(),
							occusectorid:$("#occusectorid").val(),
							occutypeofworkid:$("#occutypeofworkid").val(),
							joinDatetime:$("#joinDatetime").datebox('getValue'),
							occusector:$("#occusector").val(),
							occutypeofwork:$("#occutypeofwork").val(),
							employeeage:$("#employeeage").val(),
							damage:$("#damage").val(),
							zyb_set_source:$('#zyb_set_source').combobox('getValue')
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								alert_autoClose("操作提示", "操作成功！","");
								f_findcustomerone(text.split("-")[2],0);
								
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
		$("#customer_id").val(cumtomersd.customer_id);	
		$("#arch_num").val(cumtomersd.arch_num);	
		$("#exam_num").val(cumtomersd.exam_num);
		$('#addid_num').textbox('setValue', cumtomersd.id_num);
		$('#addcustname').textbox('setValue', cumtomersd.user_name);
		$('#age').textbox('setValue', cumtomersd.age);
		$('#email').textbox('setValue', cumtomersd.email);
		$('#others').textbox('setValue', cumtomersd.others);
		//$('#statuss').textbox('setValue', cumtomersd.statuss);	
		//$("#examstatus").val(cumtomersd.status)
		$('#addtel').textbox('setValue', cumtomersd.phone);	

		//document.getElementById("examcount").innerHTML="您是第"+cumtomersd.examcount+"次体检";
		
		$("#employeeID").val(cumtomersd.employeeID);		
		$("#visit_date").val(cumtomersd.visit_date);
		$("#visit_no").val(cumtomersd.visit_no);
		$("#clinic_no").val(cumtomersd.clinic_no);
		$("#patient_id").val(cumtomersd.patient_id);
		$("#mc_no").val(cumtomersd.mc_no);
		
		$("#register_dates").val(cumtomersd.register_date);
		$("#register_times").val(cumtomersd.exam_times);
		$("#join_dates").val(cumtomersd.join_date);
		$('#address').textbox('setValue', cumtomersd.address);
		$('#remark').textbox('setValue', cumtomersd.remarke);
	    $("#birthday").datebox('setValue',cumtomersd.birthday),
		$('#com_Name').textbox('setValue', cumtomersd.company);
		//$('#past_medical_history').textbox('setValue', cumtomersd.past_medical_history);
		//$("#picture_Path").val(cumtomersd.picture_Path);
		//document.getElementById("exampic").src="getdjtexamPhoto.action?others="+cumtomersd.picture_Path+"&"+new Date().getTime();
		$('#is_after_pay').val(cumtomersd.is_after_pay);
		$('#occusectorid').val(cumtomersd.occusectorid);
		$("#cyhy_Name").textbox('setValue', cumtomersd.cyhyname);
		
		$('#occutypeofworkid').val(cumtomersd.occutypeofworkid);
		$("#cygz_Name").textbox('setValue', cumtomersd.cygzname);
		
		$('#occutypeofwork').textbox('setValue',cumtomersd.occutypeofwork);
		$('#occusector').textbox('setValue',cumtomersd.occusector);
		
		$('#damage').textbox('setValue',cumtomersd.damage);
		$('#employeeage').textbox('setValue',cumtomersd.employeeage);
		 $("#joinDatetime").datebox('setValue',cumtomersd.joinDatetime);
		 
		$('#zyb_set_source').combobox('select', cumtomersd.zyb_set_source);
		
		var objectsex = $('#sex').combobox('getData');
		 for(var i=0;i<objectsex.length;i++) {
	        	if (objectsex[i].id == cumtomersd.sex) {
					$('#sex').combobox('setValue', objectsex[i].id);
					break;
				}
	        }  

		var objectminzhu = $('#minzhu').combobox('getData');
        for(var i=0;i<objectminzhu.length;i++) {
        	if (objectminzhu[i].id == cumtomersd.nationtype) {
				$('#minzhu').combobox('setValue', objectminzhu[i].id);
				break;
			}
        } 

        var objectcustomer_type = $('#tjlx').combobox('getData');
        for(var i=0;i<objectcustomer_type.length;i++) {
        	if (objectcustomer_type[i].id == cumtomersd.customer_type) {
				$('#tjlx').combobox('setValue', objectcustomer_type[i].id);
				/* alert(objectcustomer_type[i].name);
				
				if(objectcustomer_type[i].name=='放射性体检'){
					$("#yc_zy").css('display','none'); 
				} else {
					$("#yc_fs").css('display','none'); 
				} */
				break;
			} 
        } 
    	var name = $('#tjlx').combobox('getText');
		if(name=='放射性体检'){
			//$("#yc_zy").css('display','none'); 
		} else {
			$("#yc_fs").css('display','none'); 
		} 
        var objectchargingType = $('#sftype').combobox('getData');
        for(var i=0;i<objectchargingType.length;i++) {
        	if (objectchargingType[i].id == cumtomersd.chargingType) {
				$('#sftype').combobox('setValue', objectchargingType[i].id);
				break;
			}
        }         
        
        //体检类型
        //var objectcustomerType = $('#customerType').combobox('getData');
        //for(var i=0;i<objectcustomerType.length;i++) {
        //	if (objectcustomerType[i].id == cumtomersd.customerType) {
		//		$('#customerType').combobox('setValue', objectcustomerType[i].id);
		//		break;
		//	}
       // } 
		
        var objectcustomer_type_id = $('#rylb').combobox('getData');
        for(var i=0;i<objectcustomer_type_id.length;i++) {
        	if (objectcustomer_type_id[i].id == cumtomersd.customer_type_id) {
				$('#rylb').combobox('setValue', objectcustomer_type_id[i].id);
				break;
			}
        } 

        var objectis_Marriage = $('#is_Marriage').combobox('getData');
        for(var i=0;i<objectis_Marriage.length;i++) {
        	if (objectis_Marriage[i].id == cumtomersd.is_marriage) {
				$('#is_Marriage').combobox('setValue', objectis_Marriage[i].id);
				break;
			}
        }
        


	}

	//----------------------身份证号读取---------------------------------
	//读取身份证
	var conreadcard = 0;
	function djtreadcard_sfz() {
		if (conreadcard == 0) {
			var sfz_div_code = $("#sfz_div_code").val();
			var data = readCardSFZ(sfz_div_code);
			if (data.error_flag == "0") {
				var certno = data.certno;
				if (certno.length == 18) {
					if ($("#exam_id").val() <= 0) {
						setexaminfoall(data, certno);
						conreadcard = 0;
					} else {
						var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
								+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
						$("body").prepend(str);
						$
								.ajax({
									url : 'djtexamInfoforIdNum.action',
									data : {
										exam_id : $("#exam_id").val(),
										id_num : certno,
										customer_id : $("#customer_id").val()
									},
									type : "post",//数据发送方式   
									success : function(text) {
										$(".loading_div").remove();
										conreadcard = 0;
										if (text == '1') {
											//可以正常覆盖
											if (data.sex != document
													.getElementsByName("sex")[0].value) {
												$.messager
														.confirm(
																'提示信息',
																'身份证和当前录入人员性别不一致，强制修改可能引起缴费项目混乱，你确定要用此身份证信息覆盖吗？',
																function(r) {
																	if (r) {
																		setexaminfoall(
																				data,
																				certno);
																	}
																});
											} else {
												setexaminfoall(data, certno);
											}
											//可以正常覆盖---------------------------结束--------------------------------------										
										} else if (text == '2') {
											//提示是否需要覆盖
											$.messager
													.confirm(
															'提示信息',
															'系统里存在相同身份证号，你确定要用此身份证信息覆盖吗？',
															function(r) {
																if (r) {
																	if (data.sex != document
																			.getElementsByName("sex")[0].value) {
																		$.messager
																				.confirm(
																						'提示信息',
																						'身份证和当前录入人员性别不一致，强制修改可能引起缴费项目混乱，你确定要用此身份证信息覆盖吗？',
																						function(
																								r) {
																							if (r) {
																								setexaminfoall(
																										data,
																										certno);
																							}
																						});
																	} else {
																		setexaminfoall(
																				data,
																				certno);
																	}
																}
															});
										}
									},
									error : function() {
										conreadcard = 0;
										$(".loading_div").remove();
										$.messager.alert("操作提示", "操作错误",
												"error");
									}
								});
					}
				} else {
					$.messager.alert("操作提示", "读取身份证号码错误", "error");
				}
			} else {
//				$.messager.alert("操作提示", "读取身份证失败", "error");
				$.messager.alert("操作提示", data.error_msg, "error");
			}

		}
	}

	var data, certno;
	function setexaminfoall(data, certno) {
		$('#addid_num').textbox('setValue', certno);
		$("#addcustname").textbox('setValue', data.name);
		$("#sex").textbox('setValue', data.sex);
		$("#address").textbox('setValue', data.address);
		var object_minzhu = $('#minzhu').combobox('getData');
        for(var i=0;i<object_minzhu.length;i++) {
        	if (object_minzhu[i].name.trim() == data.nation) {
        		$('#minzhu').combobox('setValue', object_minzhu[i].id);
        		break;
        	}
        }
		var bords = data.birth;
		if (bords.length == 8) {
			var birthday = bords.substring(0, 4) + "-" + bords.substring(4, 6)
					+ "-" + bords.substring(6, 8);
			$('#birthday').datebox('setValue', birthday);
			var myDate = new Date();
			var month = myDate.getMonth() + 1;
			var day = myDate.getDate();
			var age = myDate.getFullYear() - birthday.substring(0, 4) - 1;
			if (birthday.substring(5, 7) < month
					|| birthday.substring(5, 7) == month
					&& birthday.substring(8, 10) <= day) {
				age++;
			}
			$('#age').textbox('setValue', age);
		}
	}
	
//////////////////////////////职业病历史处理////////////////////////////////////////
function getcusthisGrid(){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 if($("#exam_num").val()==""){
			 num="不做查询";
		 } else {
			 num = $("#exam_num").val();
		 }
		 var model={
				 "exam_num":num,
				 "isradiation":'0'
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
		/*  	{align:'center',field:'isradiation',title:'是否放射',width:10}, */
		 	{align:'center',field:'startdate',title:'开始时间',width:15},		 	
		 	{align:'center',field:'enddate',title:'结束时间',width:15},
		 	{align:'center',field:'ss',title:'修改',"formatter":f_xg_ZYS}
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
	    	if((document.getElementById("addid_num").value=='')&&(document.getElementById("exam_num").value=='')&&(document.getElementById("arch_num").value==''))
	    		{
	    		   $.messager.alert("操作提示", "体检人员无效，请先添加人员", "error");
	    		}else{
	    	       $("#dlg-zybocchisedit").dialog({
		 		       title:'职业史新增',
		 		       href:'zybocchisadd.action?exam_num='+document.getElementById("exam_num").value+'&id_num='+document.getElementById("addid_num").value+'&arch_num='+document.getElementById("arch_num").value+'&batch_id='+$("#addbatch_id").val()
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
	}];
 
 /**
  * 批量删除
  */
  var ids;
 function delhisrow(ids){
	 if(($("#addbatch_id").val()=='')||(Number($("#addbatch_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择体检任务","error");
 	}else if(ids==''){
 		$.messager.alert("操作提示", "请选择要删除的职业史","error");
 	}else{
	 $.messager.confirm('提示信息','是否确定删除选中职业史？',function(r){
		 	if(r){
		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	 $.ajax({
			url : 'zybocchisdeldo.action',
			data : {
				    batch_id:$("#addbatch_id").val(),				    
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
 
//////////////////////////////职业危害因素与套餐维护////////////////////////////////////////
 function getoccuhazardfactorsGrid(){	
 	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
 		 $("body").prepend(str);
 		 var model={
 				 "exam_num":$("#exam_num").val()
 		 };
 	     $("#zywhysset").datagrid({
 		 url:'examoccuhazardfactorslist.action',
 		 dataType: 'json',
 		 queryParams:model,
 		 toolbar:'#toolbar',
 		 rownumbers:false,
 	     pageSize: 5,//每页显示的记录条数，默认为10 
 	     pageList:[5,10,20],//可以设置每页记录条数的列表 
 		 columns:[[
 		    {align:'center',field:'hazard_name',title:'职业危害因素',width:30},	
 		    {align:'center',field:'occuphyexaclass_name',title:'体检类别',width:30},
 		 	{align:'center',field:'hazard_year',title:'危害年限',width:10},
 		 	{align:'center',field:'occdel',title:'删除',width:10,"formatter":f_delzywhys}
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
 	        toolbar:occtoolbar	       
 	});
 }
  
  /**
   * 定义工具栏
   */
  var occtoolbar=[{
 		text:'新增职业危害',
 		iconCls:'icon-add',
 		width:120,
 	    handler:function(){
 	    	if((document.getElementById("addid_num").value=='')&&(document.getElementById("exam_num").value=='')&&(document.getElementById("arch_num").value==''))
 	    		{
 	    		   $.messager.alert("操作提示", "体检人员无效，请先添加人员", "error");
 	    		}else{
 	    			var scustsex = encodeURI(encodeURI(document.getElementsByName("sex")[0].value));
 	    			var url='zyboccwhystcadd.action?exam_num='+document.getElementById("exam_num").value+'&id_num='+document.getElementById("addid_num").value+'&arch_num='+document.getElementById("arch_num").value+'&batch_id='+$("#addbatch_id").val()+'&sex='+scustsex+'';
 	 	 	        $("#dlg-zybocchisedit").dialog({
 			 		title:'危害因素',
 			 		href:url
 			 	    });
 			 	   $("#dlg-zybocchisedit").dialog('open'); 
 	    		}
 	    }
 	}];
  
  /**
   * 显示一条
   * @param val
   * @param row
   * @returns {String}
   */
   function f_delzywhys(val,row){	
 	  return '<a href=\"javascript:f_delzywhysdo(\''+row.id+'\')\">删除</a>';
   }
   var delzywhlbid;
   function f_delzywhysdo(delzywhlbid){
	   $.messager.confirm('提示信息','是否确定删除选中的职业危害因素？',function(r){
		 	if(r){
		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	 $.ajax({
			url : 'zywhyslbdeldo.action',
			data : {
				    batch_id:$("#addbatch_id").val(),
				    exam_num:document.getElementById("exam_num").value,
				    id_num:document.getElementById("addid_num").value,
				    arch_num:document.getElementById("arch_num").value,
		            ids:delzywhlbid,
		            exam_id:document.getElementById("exam_id").value,
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getoccuhazardfactorsGrid();
							zybsetselectListGrid();
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
   
   
   //////////////////////////////体检项目和套餐////////////////////////////////////////////////////////////////////////
   /**
	 * 显示分组套餐信息
	 */
	function zybsetselectListGrid() {
		var model = {
			"exam_num" :document.getElementById("exam_num").value
		};
		$("#zybGselectsetlist").datagrid({
			url : 'zybexamtclistshow.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ {align : "center",field : "fxfz",title : "删除",width : 15,	"formatter" : f_djtdellset}, 
			             {align : 'center',field : 'set_num',title : '套餐编码',width : 20}, 
			             {align : 'center',field : 'set_name',title : '套餐名称',	width : 45}, 
			             {align : 'center',field : 'sex',title : '适用性别',width : 20}, 
			             {align : 'center',field : 'set_discount',title : '套餐折扣率',width : 30	}, 
			             {align : 'center',field : 'set_amount',title : '套餐金额',width : 20	},
			             {align : 'center',field : 'app_typename',title : '类型',	width : 15}
			             ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
				var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#zybGselectsetlist").datagrid("hideColumn", "set_discount"); // 设置隐藏列
					$("#zybGselectsetlist").datagrid("hideColumn", "set_amount"); // 设置隐藏列
				}
			},
			singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000
		});
	}

	/**
	 * 删除套餐信息
	 * @param val
	 * @param row
	 * @returns {String}
	 */
	function f_djtdellset(val, row) {
		    return '<a href=\"javascript:djtdeletetc(\''
				+ row.set_num
				+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
	}

	/**
	 * 
	 */
	function djtdeletetc(userid){
	    if($('#exam_num').val()==''){
	 		$.messager.alert("操作提示", "人员无效","error");
	 	}else if((userid=='')||(userid.length<=0)){
	 		$.messager.alert("操作提示", "选择项目无效","error");
	 	}else{	 
		 $.messager.confirm('提示信息','是否确定删除选中套餐？',function(r){
			 	if(r){
			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
		 $.ajax({
				url : 'zybcustomersetdelshow.action',
				data : {
					exam_num:$('#exam_num').val(),
			        ids:userid
				    },
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								zybsetselectListGrid();
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
	 * 显示体检项目套餐信息
	 */
	var djtexam_id; 
	function zybcustChangItemListGrid() {
		var model = {"exam_num" :document.getElementById("exam_num").value};
		$("#zybGselectItemlist").datagrid({
			url : 'zybAllcustchangitemlist.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			toolbar:'#toolbar',
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ {field:'ck',checkbox:true }, 
                         {align : 'left',field : 'item_code',title : '项目编码',	width : 15},
			             {align : 'left',field : 'item_name',title : '项目名称',	width : 35},
			             {align : 'center',field : 'item_amount',title : '原金额',	width : 10},
			             {align : 'center',field : 'discount',title : '折扣率',	width : 10},
			             {align : 'center',field : 'is_new_added',title : '增加次数',	width : 1},
			             {align : 'center',field : 'itemnum',title : '个数',	width : 10},
			             {align : 'center',field : 'amount',title : '应收额',	width : 10},
			             {align : 'center',field : 'pay_statuss',title : '结算状态',	width : 15}, 
			             {align : 'center',field : 'exam_indicators',title : '付费方式',	width : 15}, 
			             {align : 'center',field : 'app_typename',title : '类型',	width : 10}
			          ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
				$("#zybGselectItemlist").datagrid("hideColumn", "is_new_added"); // 设置隐藏列   
				$("#zybGselectItemlist").datagrid("hideColumn", "item_code"); // 设置隐藏列  
				var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#zybGselectItemlist").datagrid("hideColumn", "discount"); // 设置隐藏列
					$("#zybGselectItemlist").datagrid("hideColumn", "amount"); // 设置隐藏列
				}
			},
			rowStyler:function(index,row){    
		        if (row.is_new_added>0){    
		            return 'font-weight:bold;';    
		        }    
		    },
		    singleSelect : false,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000,
			toolbar:toolbaritem
		});
	}
	
	/**
	  * 定义工具栏
	  */
	 var toolbaritem=[{
			text:'职业体检',
			width:120,
			height:20,
			iconCls:'icon-add',
		    handler:function(){
		    	zybaddcusItem();
		    }
		},{
			text:'个人普通',
			width:120,
			height:20,
			iconCls:'icon-add',
		    handler:function(){
		    	djtTGaddcusItem();
		    }
		},{
			text:'团体普通',
			width:120,
			height:20,
			iconCls:'icon-add',
		    handler:function(){
		    	zybTTaddcusItem();
		    }
		},{
			text:'减项',
			width:60,
			height:20,
			iconCls:'icon-cancel',
		    handler:function(){
		    	djtdelcusItem();
		    }
		}];
	
	 var newWindow;  
	 var timer; 
	function zybaddcusItem(){
		var scustsex = encodeURI(encodeURI(document.getElementsByName("sex")[0].value));
		if($('#exam_num').val()!=''){
 	    	var url='zybcustomeritemaddshow.action?exam_num='+$("#exam_num").val()+'&sex='+scustsex+'';
 	    	newWindow = window.open(url, "职业病加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
 	    	newWindow.focus();
 	    	//timer = setInterval("djtupdateAfterClose()", 1000);
 	   	}else{
 	   	  $.messager.alert('提示信息',"请先确定体检人员","error");
 	   	}
	}
	
	//人员个人加项
	function djtTGaddcusItem(){
		var scustsex = encodeURI(encodeURI(document.getElementsByName("sex")[0].value));
		if($('#exam_num').val()==''){
		 	$.messager.alert('提示信息',"请先确定体检人员！","error");
		}else if($("#exam_indicator").val()==''){
		 	$.messager.alert('提示信息',"无效付费状态！","error");
		}/*else if($("#exam_indicator").val()!='G'){
		 	$.messager.alert('提示信息',"请选择团体加项！","error");
		}*/else{
 	    	var url='zybTGcustomeritemaddshow.action?exam_num='+$("#exam_num").val()+'&sex='+scustsex+'&exam_indicator='+$("#exam_indicator").val()+'';
 	    	newWindow = window.open(url, "职业病个人加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
 	    	newWindow.focus();
 	    	//timer = setInterval("djtupdateAfterClose()", 1000);
 	   	}
	}

	 //人员团体加项
	function zybTTaddcusItem(){
		var scustsex = encodeURI(encodeURI(document.getElementsByName("sex")[0].value));
		if($('#exam_num').val()==''){
		 	$.messager.alert('提示信息',"请先确定体检人员！","error");
		}else if($("#exam_indicator").val()==''){
		 	$.messager.alert('提示信息',"无效付费状态！","error");
		}/*else if($("#exam_indicator").val()!='T'){
		 	$.messager.alert('提示信息',"请选择“个人加项”按钮！","error");
		}*/else{
	    	var url='zybTTcustomeritemaddshow.action?exam_num='+$("#exam_num").val()+'&sex='+scustsex+'&exam_indicator='+$("#exam_indicator").val()+'';
	    	newWindow = window.open(url, "人员团体加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    	newWindow.focus();
	    	//timer = setInterval("djtupdateAfterClose()", 1000);
	   	}
	}
	
	//登记台减项
	function djtdelcusItem(){
		var checkedItems = $('#zybGselectItemlist').datagrid('getChecked');
		 var delids=new Array();
		 var ids =  new Array();	
		 var delflags=0;
		    var delflagstext="";
		    var delids=new Array();
		    var ids =  new Array();	
		    $.each(checkedItems, function(index, item){       
		        if((item.pay_status=='Y')&&(item.personal_pay>0)){
		        	delflags=1;
		        	delflagstext=item.item_code+"项目已经付费，不能删除！"
		        }else if((item.exam_status=='Y')||(item.exam_status=='C')){
		        	delflags=1;
		        	delflagstext=item.item_code+"项目已检或已登记，不能删除！"
		        }else if(item.is_application=='Y'){
		        	delflags=1;
		        	delflagstext=item.item_code+"项目已发申请，不能删除！"
		        }else{
		        	ids.push(item.item_code);
		        	delids.push(item.item_code);
		        }
		    });
		    if(delflags==1){
		    	$.messager.alert("操作提示", delflagstext,"error");	
		    }else{
	    
        
	    if($('#exam_num').val()==''){
	 		$.messager.alert("操作提示", "人员无效","error");
	 	}else if((ids=='')||(ids.length<=0)){
	 		$.messager.alert("操作提示", "选择项目无效","error");
	 	}else{	 
		 $.messager.confirm('提示信息','是否确定删除选中项目？',function(r){
			 	if(r){
			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
		 $.ajax({
				url : 'zybcustomeritemdelshow.action',
				data : {
					exam_num:$('#exam_num').val(),
					batch_id:$("#batch_id").val(),
					ids:ids.toString(),
			        others:delids.toString()
				    },
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								zybcustChangItemListGrid();
								gettotalinfo();
								alert_autoClose("操作提示", text,"");
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
	}
	
	/**
	  * 设置每隔2秒钟刷新父节点的表格
	  */
	 function djtupdateAfterClose() {  
	     //父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口  
	     if(newWindow.closed == true) {
	     clearInterval(timer);  
	     zybsetselectListGrid();
	     gettotalinfo();
	     zybcustChangItemListGrid();
	     return;  
	     }  
	}  
	 function gettotalinfo(){
			$.post("zybGItemCount.action", 
					{
						"exam_id":$("#exam_id").val(),
						"exam_type":'T'
					}, function(jsonPost) {
						var customertotal = eval(jsonPost);
						document.getElementById("countss").firstChild.nodeValue=customertotal.counts;
						document.getElementById("tyjje").firstChild.nodeValue=customertotal.termAmt;
						document.getElementById("gyjje").firstChild.nodeValue=customertotal.personAmt;
						document.getElementById("gsjje").firstChild.nodeValue=customertotal.personYfAmt;
						document.getElementById("gwjje").firstChild.nodeValue=customertotal.qfAmt;
					}, "json");

		}
	/**
	*--------------------------------------放射史----------------------------------
	*/
	//--------------------------放射性职业史---------------------
	 /**
 * 定义工具栏
 */
var toolss=[{
		text:'新增',
		iconCls:'icon-add',
		width:58,
	    handler:function(){
	    	if((document.getElementById("addid_num").value=='')&&(document.getElementById("exam_num").value=='')&&(document.getElementById("arch_num").value==''))
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
		 var num = "";
		 if($("#exam_num").val()==""){
			 num="不做查询";
		 } else {
			 num = $("#exam_num").val();
		 }
		 var model={
				 "exam_num":num,
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
	//-------------------、、、、、、、、、、、、、、、、、既往史、、、、、、、、、、、、、、、、、、、、、
		function getJWScusthisGrid(c){	
		     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 var model={
					 "exam_num":$("#exam_num").val()
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
			    	if((document.getElementById("addid_num").value=='')&&(document.getElementById("exam_num").value=='')&&(document.getElementById("arch_num").value==''))
			    		{
			    		   $.messager.alert("操作提示", "体检人员无效，请先添加人员", "error");
			    		}else{
			    	       $("#dlg-jiwangshi").dialog({
				 		       title:'职业史新增',
				 		       href:'zybDiseaseHistory.action?exam_num='+document.getElementById("exam_num").value+'&id_num='+document.getElementById("addid_num").value+'&arch_num='+document.getElementById("arch_num").value
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
function guanbi(){
	var _parentWin = window.opener;
	_parentWin.getgroupuserGrid();
	javascript:window.close();
}
</script>
<!-- 定义身份证设备 -->
<OBJECT ID='GT2ICROCX' width='0' height='0'	<s:property value="sfz_div_ocx"/>></OBJECT>
<input type="hidden" id="sfz_div_code"	value="<s:property value="sfz_div_code"/>">
<!-- 定义身份证设备结束 -->
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="addbatch_id" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="customer_id">
<input type="hidden" id="register_date" >
<input type="hidden" id="register_dates" >
<input type="hidden" id="register_times">
<input type="hidden" id="join_dates">

<input type="hidden" id="visit_date">
<input type="hidden" id="visit_no">
<input type="hidden" id="clinic_no">
<input type="hidden" id="patient_id">
<input type="hidden" id="mc_no">
<input type="hidden" id="is_after_pay">
<input type="hidden" id="exam_id" value="<s:property value="model.id"/>">
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="arch_num">
<input type="hidden" id="occusectorid"	value="<s:property value="model.occusectorid"/>">
<input type="hidden" id="occutypeofworkid"	value="<s:property value="model.occutypeofworkid"/>">
<input type="hidden" id="is_show_discount"	value="<s:property value="model.is_show_discount"/>">

<div id="main">
	<div id="left">

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
					<dd style="height: 20px;">
						<input class="easyui-textbox" type="text" id="com_Name" value=""
							style="height: 26px; width: 140px;" />
						<div id="com_name_list_div"
							style="display: none; margin-left: 305px;"
							onmouseover="select_com_list_mover()"
							onmouseout="select_com_list_amover()"></div>
							
					</dd>
					<dd style="height: 20px;">
						<a href="javascript:void(0)" onClick="djtreadcard_sfz()"><img
							style="height: 25px; width: 25px;" title="身份证获取人员信息"
							src="<%=request.getContextPath()%>/themes/default/images/shengfencod.png" /></a>
							</dd>
					<dd style="height: 20px;">		
							<a href="javascript:addcustomerdo();" class="easyui-linkbutton c6"
			style="width: 100px;">保存</a> <a href="javascript:void(0)"
			class="easyui-linkbutton" style="width: 80px;"
			onclick="guanbi()">关闭</a>
					</dd>
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
							style="height: 26px; width: 140px;"
							value="<s:property value="model.custname" />" />
					</dd>
					<dt>
						身份证号 <strong class="quest-color">*</strong>
					</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="addid_num"
							style="height: 26px; width: 140px;"
							value="<s:property value="model.id_num" />"
							data-options="events:{blur:selectidnum}" />
					</dd>

					<dt>工号</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="addemployeeID"
							style="height: 26px; width: 140px;"
							value="<s:property value="model.employeeID" />" />
					</dd>
				</dl>
				<dl>
					<dt>
						性别<strong class="quest-color">*</strong>
					</dt>
					<dd>
						<select class="easyui-combobox" id="sex" name="sex"
							data-options="height:26,width:140,panelHeight:'auto'"></select>
					</dd>
					<dt>婚否</dt>
					<dd>
						<select class="easyui-combobox" id="is_Marriage"
							name="is_Marriage"
							data-options="height:26,width:140,panelHeight:'auto'"></select>
					</dd>
					<dt>
						年龄<strong class="quest-color">*</strong>
					</dt>
					<dd>
						<input class="easyui-textbox" type="text"
							data-options="events:{blur:selectage}" id="age"
							style="height: 26px; width: 50px;"
							value="<s:property value="model.age" />" />
					</dd>
				</dl>
				<dl>
					<dt>体检类别</dt>
					<dd>
						<select class="easyui-combobox" id="tjlx" name="tjlx"
							data-options="height:26,width:140,panelHeight:'auto'"></select>
					</dd>
					<dt>民族</dt>
					<dd>
						<select class="easyui-combobox" id="minzhu" name="minzhu" panelMaxHeight="300px" 
							data-options="height:26,width:140,panelHeight:'auto'"></select>
					</dd>

					<dt>
						出生日期<strong class="quest-color">*</strong>
					</dt>
					<dd>
						<input class="easyui-datebox"
							value="<s:property value="model.birthday"/>"
							data-options="validType:'Length[10]',events:{blur:selectbirthday}"
							type=text id="birthday" style="width: 100px; height: 26px;"></input>
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
							style="height: 26px; width: 140px;"
							value="<s:property value="model.tel" />" />
					</dd>
					<dt>人员类别</dt>
					<dd>
						<select class="easyui-combobox" id="rylb" name="rylb"
							data-options="height:26,width:140,panelHeight:'auto'"></select>
					</dd>
				</dl>
				<dl>
					<dt>部门</dt>
					<dd>
						<select class="easyui-combobox" id="addlevel" name="addlevel"
							data-options="height:26,width:140,panelHeight:'auto'"></select>
					</dd>
					<dt>职务</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="addposition"
							style="height: 26px; width: 140px;"
							value="<s:property value="model.position" />" />
					</dd>
					<dt>邮箱</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="email"
							style="height: 26px; width: 140px;"
							value="<s:property value="model.email" />" />
					</dd>
				</dl>
				<dl>
					<dt>通讯住址</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="address"
							style="height: 26px; width: 640px;"
							value="<s:property value="model.address" />" />
					</dd>
				</dl>
				<dl>
					<dt>备注</dt>
					<dd>
						<input class="easyui-textbox" type="text" id="remark"
							style="height: 26px; width: 390px;"
							value="<s:property value="model.remarke" />" />
					</dd>
					<dt>套餐来源</dt>
					<dd>
						<input class="easyui-combobox" id="zyb_set_source" 
						 data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 140px;" />
					 </dd>
				</dl>
			</div>
		</fieldset>
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
					<dt>工龄(年)<strong class="quest-color">*</strong></dt>
					<dd>
						<input class="easyui-textbox" type="text" id="employeeage"
							style="height: 26px; width: 200px;"
							value="<s:property value="model.employeeage" />" />
					</dd>
					<dt>接害工龄(年)<strong class="quest-color">*</strong></dt>
					<dd>
						<input class="easyui-textbox" type="text" id="damage"
							style="height: 26px; width: 200px;"
							value="<s:property value="model.damage" />" />
					</dd>
				</dl>
				<dl>
					<dt>进厂日期<strong class="quest-color">*</strong></dt>
					<dd>
						<input class="easyui-datebox"  value="<s:property value="model.joinDatetime"/>"
							data-options="validType:'Length[10]'" type=text id="joinDatetime"
							style="width: 100px; height: 26px;"></input>
					</dd>
				</dl>
			</div>
		</fieldset>
		<fieldset style="margin: 5px; padding-right: 0;"  id = "yc_zy">
			<legend>
				<strong>非放射职业史</strong>
			</legend>
			<div class="user-query">
			<table id="zybocchislist">
				</table>
			</div>
		</fieldset>
		<fieldset style="margin: 5px; padding-right: 0;"  id = "yc_fs">
			<legend>
				<strong>放射职业史</strong>
			</legend>
			<div class="user-query">
			<table id="zybFsocchislist">
				</table>
			</div>
		</fieldset>
		<fieldset style="margin: 5px; padding-right: 0;">
			<legend>
				<strong>既往史</strong>
			</legend>
			<div class="user-query">
			<table id="zybJWSocchislist">
				</table>
			</div>
		</fieldset>
	</div>

	<div id="right">
		<fieldset style="margin: 5px; padding-right: 0;">
			<legend>
				<strong>职业危害因素维护</strong>
			</legend>
			<div class="user-query">
			   <table id="zywhysset"></table>
			</div>
		</fieldset>
		 <fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检套餐</strong>
	</legend>	
	      <div id="zybGselectsetlist"></div>
	</fieldset>
	  <fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检项目</strong>
	</legend>
			&nbsp;合计：<font id="countss" style="font-weight:bold;font-style:italic;">0</font>项
			&nbsp;团体应缴额：<font id="tyjje" style="font-weight:bold;font-style:italic;">0</font>元
			&nbsp;个人应缴额：<font id="gyjje" style="font-weight:bold;font-style:italic;">0</font>元
			&nbsp;个人实缴额：<font id="gsjje" style="color:blue;font-weight:bold;font-style:italic;">0</font>元
			&nbsp;个人未缴额：<font id="gwjje" style="color:red;font-weight:bold;font-style:italic;">0</font>元
			<div id="zybGselectItemlist"></div>
	</fieldset>
	</div>
</div>
<div id="dlg-fangsheshi" class="easyui-dialog"  data-options="width: 850,height: 420,closed: true,cache: false,modal: true,top:100"></div>
<div id="dlg-jiwangshi" class="easyui-dialog"  data-options="width: 850,height: 420,closed: true,cache: false,modal: true,top:100"></div>
<div id="dlg-zybocchisedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>