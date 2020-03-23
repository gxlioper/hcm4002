<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/style/style1/scripts/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
	$(function() {
		getseq_code();
		//alert('<s:property value="model.dep_id"/>');
		//样本分类
		queryDemoType();

		if ($('#h_on_off_schedule').val() == 'Y') {
			$('#on_schedule').attr("checked", true);
		}
		//收费项目不能为空
		$('#additem_name,#additem_pinyin,#amount').validatebox({
			required : 'true'
		})
		//金额格式验证
		$('#amount').blur(function() {
			$('#amount').validatebox({
				required : true,
				validType : 'IsNumber_Amt',
				invalidMessage : '请输入数字或者小数'
			})
		})
		//核算金额输入格式验证
		$('#addcalculation_amount').blur(function() {
			$('#addcalculation_amount').validatebox({
				validType : 'IsNumber_Amt',
				invalidMessage : '请输入数字或者小数'
			})
		})
		//顺序吗格式验证
		$('#additem_seq').blur(function() {
			$('#additem_seq').validatebox({
				validType : 'IsNumber'
			})
		})
		//项目折扣率  默认为10 不打折
		var itemDiscount = "<s:property value='item_discount'/>";
		//alert(itemDiscount);//收费项目拼音是否为空
		if(itemDiscount==0 && $('#additem_pinyin').val()==""){
			document.getElementById('item_discount').value = 10; 
		}else{
			document.getElementById('item_discount').value = itemDiscount; 
		}
		
		update();//根据修改内容切换类型
		addguide_category();//导引单下拉框--添加页面
		sex();//性别切换
		addDepartment_dep();//科室下拉框--添加页面
		adddep_category();//所属统计科室--添加页面
		interface_flag();//接口标示
		var io = $('#gisOnlyApplyOrReport').val();
		if (io == "N") {
			document.getElementById("isOnlyApplyOrReport").checked = false;
		}
		//getItemSampleDemo();//获取所有检验样本--添加页面
		getItemSampleReportDemo();//获取所有报告样本--添加页面
		
		/* $("#addSampleDemo").focus(function(){ //鼠标聚焦事件
			getItemSampleDemo();//获取所有检验样本--添加页面
		}); */

		additem_type();//收费项目类别---添加页面
		his_dep();//his执行科室
		getExaminationItem();//检查项目列表
		seletExaminationItem();//已选中的检查项目清单
		//点击事件类型切换视图
		$($("input[name='item_category']")).click(function() {
			leixing();
		})
		isOnlyApplyOrReport();//独立报告失去焦点
		$('#additem_name').blur(function() {
			itemNameCheck();
			pinyin();
		});
		//回车查询
		$("#item_num").bind('keypress', function(event) {
			if (event.keyCode == "13") {
				getExaminationItem();
			}
		})
		caiwuleibie();//财务类别
	})
	function caiwuleibie() {
		$('#finance_class').combobox({
			panelHeight : 'auto',
			url : "getDatadis.action?com_Type=" + "CWLB",
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function() {//下拉框默认选择
				var val = $(this).combobox('getData');
				for (var i = 0; i < val.length; i++) {
					if (val[i].id == $('#h_finance_class').val()) {
						$('#finance_class').combobox('setValue', val[i].id);
					}
				}
			}
		})
	}
	var hisStatus = "<s:property value='hisStatus'/>";//his系统状态
	var lisStatus = "<s:property value='lisStatus'/>";//lis系统状态
	var pacsStatus = "<s:property value='pacsStatus'/>";//pacs系统状态
	/**
	 * 验证his系统编码唯一
	 */
	function his_numSole() {
		/* if(hisStatus=='Y'&&$('#addhis_num').val()!=''){
			   $('#addhis_num').combobox('select',2);
		} */
		var id = "<s:property value='id'/>";
		if (id > 0) {
			var his = '<s:property value="his_num"/>';
			if (his == $('#addhis_num').val()) {
				return;
			}
		}
		var his = 0;
		$.ajax({
			url : 'getBMYZ.action',
			async : false,
			type : 'post',
			data : {
				bianma : 'his_num',
				zhi : $('#addhis_num').val(),
			},
			success : function(data) {
				if (data == 'ok') {
					his = 1;
				} else if (data == 'no') {
					$.messager.alert('警告信息', 'his系统编码已存在！', 'error');
				}
			}
		})
		return his;
	}
	/**
	 * 检验系统编码验证唯一
	 */
	function exam_numSole() {
		var id = "<s:property value='id'/>";
		if (id > 0) {
			var exam = '<s:property value="exam_num"/>';
			if (exam == $('#addexam_num').val()) {
				return;
			}
		}
		var his = 0;
		$.ajax({
			url : 'getBMYZ.action',
			async : false,
			type : 'post',
			data : {
				bianma : 'exam_num',
				zhi : $('#addexam_num').val(),
			},
			success : function(data) {
				if (data == 'ok') {
					his = 1;
				} else if (data == 'no') {
					$.messager.alert('警告信息', '检验系统编码已存在！', 'error');
				}
			}
		})
		return his;
	}
	/**
	 * 影像系统编码验证唯一
	 */
	function view_numSole() {
		/* if(hisStatus=='Y'&&$('#addhis_num').val()!=''){
			   $('#addinterface_flag').combobox('select',2);
		 } */
		var id = "<s:property value='id'/>";
		if (id > 0) {
			var view = '<s:property value="view_num"/>';
			if (view == $('#addview_num').val()) {
				return;
			}
		}
		var his = 0;
		$.ajax({
			url : 'getBMYZ.action',
			async : false,
			type : 'post',
			data : {
				bianma : 'view_num',
				zhi : $('#addview_num').val(),
			},
			success : function(data) {
				if (data == 'ok') {
					his = 1;
				} else if (data == 'no') {
					$.messager.alert('警告信息', '影像系统编码已存在！', 'error');
				}
			}
		})
		return his;
	}
	/**
	 * 拼音
	 */
	function pinyin() {
		$.ajax({
			url : 'pinying.action',
			type : 'post',
			data : {
				pinying : $('#additem_name').val()
			},
			success : function(data) {
				$('#additem_pinyin').val(data);
			}
		})
	}
	/**
	 * 产生独立报告焦点事件
	 */
	function isOnlyApplyOrReport() {
		$('#isOnlyApplyOrReport').blur(function() {
			var a = $('#isOnlyApplyOrReport').attr('checked');
			if (!a) {
				$('#isOnlyApplyOrReport').val("N");
			} else {
				$('#isOnlyApplyOrReport').val("Y");
			}
		});
	}
	/**
	 * 获取修改数据自动切换类型
	 */
	function update() {
		var item = '<s:property value="model.item_category"/>';
		if (item == '耗材类型') {
			$('#item_categoryH').attr("checked", true);
			$('#item_categoryP').attr("checked", false);
			$('#common').hide();
		}
	}
	/**
	 * 性别
	 */
	function sex() {
		var sex = '<s:property value="model.sex"/>';
		if (sex == '男') {
			$('#sexN').attr("checked", true);
			$('#sexY').attr("checked", false);
			$('#sexQ').attr("checked", false);
		}
		if (sex == '女') {
			$('#sexN').attr("checked", false);
			$('#sexY').attr("checked", true);
			$('#sexQ').attr("checked", false);
		}
		if (sex == '全部') {
			$('#sexN').attr("checked", false);
			$('#sexY').attr("checked", false);
			$('#sexQ').attr("checked", true);
		}
	}
	/**
	 * 失去焦点类型切换
	 */
	function leixing() {
		var a = $("input[name='item_category']:checked").val();
		if (a == "普通类型") {
			$('#common').show();
			getExaminationItem();//检查项目列表
			//getExaminationItem();//检查项目列表
			//$('#examinationItem').datagrid('reload');//刷新检查项目列表
			//seletExaminationItem();//已选中的检查项目清单
			$('#selectedExaminationItem').datagrid({
				url : 'getChargingItemExamItem.action',
				queryParams : {
					id : $('#chargitem_id').val(),
					item_name : $('#item_num').val()
				},
				singleSelect : true,
				height : 380,
				striped : true,
				rownumbers : true,
				fitColumns : true,
				columns : [ [ {
					align : 'center',
					field : 'aa',
					title : '操作',
					"formatter" : del_Item,
					width : 10
				}, {
					align : 'center',
					field : 'item_num',
					title : '检查项目编码',
					width : 10
				}, {
					align : 'left',
					field : 'item_name',
					title : '检查项目名称',
					width : 10
				}, {
					align : 'center',
					field : 'exam_item_id',
					title : '检查项目名称',
					width : 10
				} ] ]
			})
			//seletExaminationItem();//已选中的检查项目清单
			//$('#selectedExaminationItem').datagrid('reload');
		} else if (a == "耗材类型") {
			$('#common').hide();
		}
	}
	/**
	 *接口标示
	 */
	function interface_flag() {
		var interfacec = "<s:property value='interface_flag'/>";
		var idsfs = "<s:property value='id'/>";
		$('#addinterface_flag').combobox('setValue', Number(interfacec));
	}
	/**
	 * His执行科室下拉框
	 */
	function his_dep() {
		var f = "<s:property value='perform_dept'/>";
		$('#addperform_dept').combobox({
			panelWidth : 210,
			url : 'getHisDictDept.action',
			valueField : 'dept_code',
			textField : 'dept_name',
			onLoadSuccess : function() {//下拉框默认选择
				var val = $("#addperform_dept").combobox('getData');
				for ( var item in val[0]) {
					if (item == 'dept_code') {
						if (f != '') {
							$(this).combobox('select', f);//设置你找选中的
						} else {
							$(this).combobox('select', val[0][item]);//默认选择第一条
						}
					}
				}
			}
		});
	}
	/**
	 * 科室下拉框
	 */
	function addDepartment_dep() {
		$('#adddep_id').combobox(
				{
					url : 'getDepartment_dep.action',
					valueField : 'id',
					textField : 'dep_name',
					onLoadSuccess : function() {//下拉框默认选择
						var val = $(this).combobox('getData');
						for ( var item in val[0]) {
							if (item == 'id') {
								if ($('#gdep_id').val() != '' && $('#gdep_id').val() != '0') {
									$(this).combobox('select',$('#gdep_id').val());//设置你找选中的
								} else {
									$(this).combobox('select', val[0][item]);//默认选择第一条
								}
							}
						}
					},onChange:function(newVal, oldVal){
						if($("#deptIdSelect").val()==0 || $("#deptIdSelect").val()==""){
							$("#deptIdSelect").val(newVal);
						}
						if($("#deptIdSelect").val()!=newVal){
							$("#deptIdSelect").val(newVal);
						}
						$.ajax({
							url : 'lianDong.action',
							data : {id :newVal},
							success : function(text) {
								var val = $('#adddep_category').combobox('getData');
								for ( var item in val[0]) {
									if (item == 'id') {
										if (parseInt(text.split("-")[0]) > 0) {
											$('#adddep_category').combobox('select', parseInt(text.split("-")[0]));//科室和统计科室联动
											//ldcode();//系统编码
										}
									}
								}
								//alert(text.split("-")[1]+"===="+parseInt(text.split("-")[0]));
								if(parseInt(text.split("-")[0])==17){
									$('#addSampleDemo').combobox('select', '');
									if(oldVal==""){
										if(Number($('#gSampleDemo').val())==0){
											$('#addSampleDemo').combobox('select', '');
										}else{
											$('#addSampleDemo').combobox('select', Number($('#gSampleDemo').val()));
										}
									}
								}else{
									getItemSampleDemo(0);
								}
								//所有的一般检查
								getExaminationItem();
							}
						}) 

					}
				});
	}
	/**
	 * 统计 科室和系统编码联动，是不必添,pacs,his,lis系统状态
	 */
	/* function ldcode(){
	 $('#addinterface_flag').combobox('select',0);
	 if($('#adddep_category').combobox('getValue')==21&&pacsStatus=='Y'){//影像科
	 $('#addinterface_flag').combobox('setValue',2);
	 }else if($('#adddep_category').combobox('getValue')==131&&lisStatus=='Y'){//检验科
	 $('#addinterface_flag').combobox('select',2);
	 }else if(hisStatus=='Y'&&$('#addhis_num').val()!=''){//HIS系统
	 if(hisStatus=='Y'){
	 $('#addinterface_flag').combobox('select',2);
	 }
	 }else if(hisStatus=='Y'&&$('#addhis_num').val()!=''){
	 $('#addinterface_flag').combobox('select',2);
	 }
	 } */
	/**
	 * 所属统计科室
	 */
	function adddep_category() {
		$('#adddep_category').combobox(
				{
					//panelHeight:80,
					url : "getDatadis.action?com_Type=" + "KSLX",
					valueField : 'id',
					textField : 'name',
					onLoadSuccess : function() {//下拉框默认选择
						var val = $(this).combobox('getData');
						for ( var item in val[0]) {
							if (item == 'id') {
								//alert($('#gdep_category').val()+"----"+val[0][item]);
								if ($('#gdep_category').val() != '') {
									$(this).combobox('select',
											$('#gdep_category').val());//设置你找选中的
								} else {
									$(this).combobox('select', val[0][item]);//默认选择第一条
								}
							}
						}
					}
				})
	}
	 /**
	 * 样本分类
	 */
	 function queryDemoType(){
		 $('#addDemo_type').combobox({
			//panelHeight:80,
			url:"getDatadis.action?com_Type="+"YBFL",
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function() {//下拉框默认选择
			    var demo_type_combobox = $('#addDemo_type').combobox('getData')
                var demo_type = $('#demo_type').val()
                $('#addDemo_type').combobox('select',demo_type_combobox[0].id)
			},
			onSelect:function(node){
			    //alert(node.id)
	        	getItemSampleDemo(node.id);
	        },
	        loadFilter:function(data){
	            data.unshift({id:0,name:'请选择'});
	            return data;
	        }
		})
	 }
    /**
     * 获取所有检验样本
     */
    function getItemSampleDemo(demo_type_id) {
        $('#addSampleDemo').combobox(
            {
                url : 'getItemSampleDemo.action?demo_type='+demo_type_id,
                panelWidth : '250',
                panelHeight : '400',
                valueField : 'id',
                textField : 'demo_name',
                onLoadSuccess : function() {//下拉框默认选择
                    var sampleDemo_combobox = $(this).combobox('getData');
                    var sam_demo_id = "<s:property value='sam_demo_id'/>"
                    $('#addSampleDemo').combobox('setValue',sampleDemo_combobox[0].id)
                    if(sam_demo_id!=null && sam_demo_id!=''){
                        $('#addSampleDemo').combobox('select',sam_demo_id)
                    } else {
                        $('#addSampleDemo').combobox('select',sampleDemo_combobox[0].id)
                    }
                }
            })
    }
	/**
	 * 收费项目类型
	 */
	function additem_type() {
		$('#additem_type').combobox({
			panelHeight : 80,
			url : "getDatadisKongGe.action?com_Type=" + "SFXMLB",
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function() {//下拉框默认选择
				var val = $(this).combobox('getData');
				for ( var item in val[0]) {
					if (item == 'id') {
						if ($('#gitem_type').val() != '') {
							$(this).combobox('select', $('#gitem_type').val());//设置你找选中的
						} else {
							$(this).combobox('select', val[0][item]);//默认选择第一条
						}
					}
				}
			}
		})
	}
	/**
	 * 获取所有报告样本
	 */
	function getItemSampleReportDemo() {
		$('#addSampleReportDemo').combobox(
				{
					url : 'getItemSampleReportDemo.action',
					panelHeight : '400',
					panelWidth : '250',
					valueField : 'id',
					textField : 'demo_name',
					onLoadSuccess : function() {//下拉框默认选择
						var val = $(this).combobox('getData');
						for ( var item in val[0]) {
							if (item == 'id') {
								if ($('#gSampleReportDemo').val() != '') {
									$(this).combobox('select',
											$('#gSampleReportDemo').val());//设置你找选中的
								} else {
									$(this).combobox('select', val[0][item]);//默认选择第一条
								}
							}
						}
					}
				})
	}
	/**
	 * 获取检查项目列表
	 */
	function getExaminationItem() {
		$("#examinationItem").datagrid({
			height : 380,
			url : "queryExamItemByOwnDept.action",
			queryParams : {
				item_name : $('#item_num').val(),
				dept_id:$('#deptIdSelect').val()
			},
			pageSize : 10,
			pagination : true,
			fitColumns : true,
			pageList : [ 10, 20, 30 ],
			singleSelect : true,
			rownumbers : true,
			striped : true,
			columns : [ [ {
				align : 'center',
				title : '检查项目编码',
				field : 'item_num',
				width : 100
			}, {
				align : 'left',
				title : '检查项目名称',
				field : 'item_name',
				width : 100
			}, {
				align : 'center',
				title : '顺数码',
				field : 'seq_code',
				width : 50
			}, {
				align : 'center',
				field : 'ss',
				title : '操作',
				"formatter" : f_add,
				width : 100
			} ] ],
			onDblClickRow:function(index,row){
				fc_add(index)
			}
		})
	}
	//数据添加到右边表格
	function f_add(value, row, index) {
		return '<a href=\"javascript:fc_add(\'' + index + '\')\">添加</a>';
	}
	function fc_add(index) {
		var Item = $('#selectedExaminationItem').datagrid("getRows");//获取已添加的数据
		var row = $('#examinationItem').datagrid("getRows")[index];//获取选择行的数据
		var s = row.item_num;
		var f = 0;
		$.each(Item, function(k, v) {
			if (v.item_num == s) {
				f = 1;
				$.messager.alert('提示', "检查项目已经在清单中", 'error');
			}
		})
		if (f == '0') {
			$('#selectedExaminationItem').datagrid('appendRow', {
				item_name : row.item_name,
				item_num : row.item_num,
				exam_item_id : row.id,
				seq_code : row.seq_code,
				item_result_type : row.item_result_type
			});
		}
	}
	var editRow = ""; 
	function seletExaminationItem() {
		$('#selectedExaminationItem').datagrid({
			url : 'getChargingItemExamItem.action',
			queryParams : {
				id : $('#chargitem_id').val(),
				item_name : $('#item_num').val()
			},
			singleSelect : true,
			height : 380,
			//striped:true,
			//fit:true,
			rownumbers : true,
			fitColumns : true,
			columns : [ [ {
				align : 'center',
				field : 'aa',
				title : '操作',
				"formatter" : del_Item,
				width : 10
			}, {
				align : 'center',
				title : '检查项目编码',
				field : 'item_num',
				width : 10
			}, {
				align : 'left',
				title : '检查项目名称',
				field : 'item_name',
				width : 15
			}, {
				align : 'center',
				title : 'id',
				field : 'id',
				'hidden' : true
			}, {
				align : 'center',
				title : '顺序码',
				field : 'seq_code',
				width : 10,
				editor : 'text'
			} ] ],
			striped : true,
			onDblClickRow :onClickRow
		})

	}
	//删除已选择检查项目
	function del_Item(value, row, index) {
		return '<a href=\"javascript:del_dTem(\'' + index + '\')\">删除</a>';

	}
	function del_dTem(index) {
		//-------------返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
		//selectchargingItem是datagrid的id
		var selections = $('#selectedExaminationItem')
				.datagrid('getSelections');
		var selectRows = [];
		for (var i = 0; i < selections.length; i++) {
			selectRows.push(selections[i]);
		}
		for (var j = 0; j < selectRows.length; j++) {
			var index = $('#selectedExaminationItem').datagrid('getRowIndex',
					selectRows[j]);
			//执行删除行
			//selectchargingItem是datagrid的id,index要删除的行
			$('#selectedExaminationItem').datagrid('deleteRow', index);
		}
		//$('#selectedExaminationItem').datagrid("deleteRow",index);
	}
	//-----------------------------------新增收费项目新增&收费项目修改----------------------
	function addchargingItemdo() {
		accept();
		if (his_numSole() == 0 || exam_numSole() == 0 || view_numSole() == 0) {
			return;
		}
		if ($('#additem_name').val() == '') {
			$('#additem_name').focus();
			return;
		}
		if ($('#additem_pinyin').val() == '') {
			$('#additem_pinyin').focus();
			return;
		}
		if (!isJE((document.getElementById('amount').value))
				|| document.getElementById('amount').value == '') {
			$('#amount').focus();
			return;
		}
		if ($('#item_discount').val() == '') {
			$('#item_discount').focus();
			return;
		}
		if(check_falg){
			alert_autoClose('提示','项目名称重复请重新录入。','error');
			$('#additem_name').focus();
			return;
		}
		var j = $('#selectedExaminationItem').datagrid("getRows");//获取已添加的数据
		var icategory = $('input[name="item_category"]:checked').val();
        var dep_ll = $('#adddep_category').combobox('getValue');//所属统计科室 */
		if (icategory == "普通类型" && dep_ll != '21') {
			if (j == '') {
				$.messager.alert('警告', '请选择检查项目','error');
				return;
			}
			
			//获取已选择的检查项目id
			// var fl = 0;
            //
			// $.each(j, function(k, v) {
			// 	if (v.item_result_type == 1) {
			// 		fl ++;
			// 	}
			// });
			// if (dep_ll == '21') {
			// 	if(fl != 1){
			// 		$.messager.alert('警告', '影像科室必须且只能包含一个结果类型为结论描述型的检查项目', 'error');
			// 		return;
			// 	}
			// }
		}
		/* 	if($("input[name='on_off_schedule']:checked").val()=='Y' && $('#schedule_start_time').datebox('getValue')==""){
				$.messager.alert("提示信息","项目排期开始日期为必选项","error");
				return;
			}
			if($("input[name='on_off_schedule']:checked").val()=='Y' && $('#schedule_end_time').datebox('getValue')==""){
				$.messager.alert("提示信息","项目排期截止日期为必选项","error");
				return;
			}
			if($("input[name='on_off_schedule']:checked").val()=='Y' && $('#schedule_number').val()<=0){
				$.messager.alert("提示信息","排期数量为必填项","error");
				return;
			} */
		if ($('#chargitem_id').val() > 0) {
			var ar = new Array();
			for (var i = 0; i < j.length; i++) {
				var x_j = new Object();
				x_j.exam_item_id = j[i].exam_item_id;
				x_j.seq_code = j[i].seq_code;
				x_j.item_num = j[i].item_num;
				ar.push(JSON.stringify(x_j));
			}
			$.ajax({
				url : 'addChargingItem.action',
				type : 'post',
				data : {
					id : $('#chargitem_id').val(),
					jid : '[' + ar.toString() + ']', //已选择检查项目
					dep_id : $('#adddep_id').combobox("getValue"),//所属科室
					sam_demo_id : $('#addSampleDemo').combobox("getValue"),//所属检验样本
					sam_report_demo_id : $('#addSampleReportDemo').combobox(
							"getValue"),//所属报告样本
					item_code : $('#additem_code').val(),//收费项目编号
					item_name : $('#additem_name').val(),//收费名称
					item_pinyin : $('#additem_pinyin').val(),//收费项目拼音
					item_category : $('input[name="item_category"]:checked')
							.val(),//项目类型
					sex : $('input[name="sex"]:checked').val(),//性别
					amount : $('#amount').val(),//金额
					dep_category : $('#adddep_category').combobox('getValue'),//所属统计科室
					isOnlyApplyOrReport : $('#isOnlyApplyOrReport').val(),//产生独立报告
					item_seq : $('#additem_seq').val(),//顺序吗
					guide_category : $('#addguide_category').combobox(
							'getValue'),//导引单分类
					his_num : $('#addhis_num').val(),//his系统编码
					exam_num : $('#addexam_num').val(),//检验系统编码
					view_num : $('#addview_num').val(),//影像系统编码
					calculation_amount : $('#addcalculation_amount').val(),//核算金额
					interface_flag : $('#addinterface_flag').combobox(
							'getValue'),//接口标识
					item_type : $('#additem_type').combobox('getValue'),//项目类别
					charge_inter_num : $('#addcharge_inter_num').val(),//系统外编码
					item_abbreviation : $('#additem_abbreviation').val(),//收费项目简称
					perform_dept : $('#addperform_dept').combobox('getValue'),//his执行科室编码
					item_note : $('#item_note').val(),//收费项目描述
					item_class : $('#item_class').val(),//HIS诊疗项目
					limit_count : $('#limit_count').val(),//项目上限
					remark : $('#remark').val(),
					finance_class : $('#finance_class').combobox('getValue'),
					charging_item_number:$('#charging_item_number').val(),
					calculation_rate:$('#calculation_rate').val(),
					item_discount:$('#item_discount').val()  //项目折扣率
				},
				success : function(text) {
					if (text.split("-")[0] == 'ok') {
					var _parentWin = window.opener;
					_parentWin.brushpagecharging();
					window.alert( text.split("-")[1]);
					window.close();
					 }else{
							$.messager.alert("操作提示", text.split("-")[1], "error");
						}
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			})
		} else {
			//添加方法
			$.ajax({
				url : 'addChargingItem.action',
				type : 'post',
				data : {
					jid : JSON.stringify(j), //已选择检查项目
					dep_id : $('#adddep_id').combobox("getValue"),//所属科室
					sam_demo_id : $('#addSampleDemo').combobox("getValue"),//所属检验样本
					sam_report_demo_id : $('#addSampleReportDemo').combobox(
							"getValue"),//所属报告样本
					item_code : $('#additem_code').val(),//收费项目编号
					item_name : $('#additem_name').val(),//收费名称
					item_pinyin : $('#additem_pinyin').val(),//收费项目拼音
					item_category : $('input[name="item_category"]:checked')
							.val(),//项目类型
					sex : $('input[name="sex"]:checked').val(),//性别
					amount : $('#amount').val(),//金额
					dep_category : $('#adddep_category').combobox('getValue'),//所属统计科室
					isOnlyApplyOrReport : $('#isOnlyApplyOrReport').val(),//产生独立报告
					item_seq : $('#additem_seq').val(),//顺序吗
					guide_category : $('#addguide_category').combobox(
							'getValue'),//导引单分类
					his_num : $('#addhis_num').val(),//his系统编码
					exam_num : $('#addexam_num').val(),//检验系统编码
					view_num : $('#addview_num').val(),//影像系统编码
					calculation_amount : $('#addcalculation_amount').val(),//核算金额
					interface_flag : $('#addinterface_flag').combobox(
							'getValue'),//接口标识
					item_type : $('#additem_type').combobox('getValue'),//项目类别
					charge_inter_num : $('#addcharge_inter_num').val(),//系统外编码
					item_abbreviation : $('#additem_abbreviation').val(),//收费项目简称
					perform_dept : $('#addperform_dept').combobox('getValue'),//his执行科室编码
					item_note : $('#item_note').val(),//收费项目描述
					item_class : $('#item_class').val(),//HIS诊疗项目
					limit_count : $('#limit_count').val(),//项目上限
					remark : $('#remark').val(),
					finance_class : $('#finance_class').combobox('getValue'),
					charging_item_number:$('#charging_item_number').val(),
					calculation_rate:$('#calculation_rate').val(),
					item_discount:$('#item_discount').val(),  //项目折扣率
                    center_num: $('#center').combobox('getValues').toString()
				},
				success : function(text) {
					if (text.split("-")[0] == 'ok') {
					var _parentWin = window.opener;
					_parentWin.brushpagecharging();
					window.alert( text.split("-")[1]);
					window.close();
					 }else{
							$.messager.alert("操作提示", text.split("-")[1], "error");
						}
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			})
		}
	}

	/**
	 * 导引单类型
	 */
	function addguide_category() {
		$('#addguide_category').combobox(
				{
					panelHeight : 70,
					url : "getDatadis.action?com_Type=" + "DYDLX",
					valueField : 'id',
					textField : 'name',
					onLoadSuccess : function() {//下拉框默认选择
						var val = $(this).combobox('getData');
						for ( var item in val[0]) {
							if (item == 'id') {
								if ($('#yguide_category').val() != '') {
									$(this).combobox('select',
											$('#yguide_category').val());//设置你找选中的
								} else {
									$(this).combobox('select', val[0][item]);//默认选择第一条
								}
							}
						}
					}
				})
	}
	/**
	 * his价表
	 */
	function getHis_price() {
		$('#his_price').dialog(
				{
					title : 'His系统诊疗项目',
					href : 'getHisClinicItemVPricelistPage.action?addhisnum='
							+ $('#addhis_num').val(),
				});
		$('#his_price').dialog('open');
	}
	/**
	 * lis检验项
	 */
	function getlis_dialog() {
		$('#lis_open').dialog({
			title : 'LIS组合项目',
			href : 'getThridLis.action',
			center : 'center',
		});
		$('#lis_open').dialog('open');
	}
	//---------------easyui  编辑框------------------
	//重写编辑框
	function getseq_code() {
			$.extend($.fn.datagrid.defaults.editors,{
						text : {
							init : function(container, options) {
								var input = $('<input type="text"       class="datagrid-editable-input">').appendTo(container);
								return input;
							},
							getValue : function(target) {
								return $(target).val();
							},
							setValue : function(target, value) {
								$(target).val(value);
							},
							resize : function(target, width) {
								var input = $(target);
								if ($.boxModel == true) {
									input.width(width
											- (input.outerWidth() - input
													.width()));
								} else {
									input.width(width);
								}
							}
						}
				});
	}
	//开启编辑框
		var editIndex = undefined;
		function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#selectedExaminationItem').datagrid('validateRow', editIndex)){
				var ed = $('#selectedExaminationItem').datagrid('getEditor', {index:editIndex,field:'seq_code'});
				if(ed) {
					var seq_code = $(ed.target).val();
					$('#selectedExaminationItem').datagrid('getRows')[editIndex]['seq_code'] = seq_code;
				}
				$('#selectedExaminationItem').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		
		function onClickRow(index){
				if (editIndex != index){
						if (endEditing()){
							$('#selectedExaminationItem').datagrid('selectRow', index).datagrid('beginEdit', index);
						editIndex = index;
						} else {
							$('#selectedExaminationItem').datagrid('selectRow', editIndex);
						}
				}
		}
		function accept(){
				if (endEditing()){
					$('#selectedExaminationItem').datagrid('acceptChanges');
				}
		}
		
		//新增检查项目
		function addJianChaItem(){
			$("#dlg-custedit").dialog({
				title : '新增检查项目',
				align : 'center',
				width : 1000,
				height: 515,
				href : 'addExaminationItemPage.action'
			});
			$("#dlg-custedit").dialog('open');
		}
		
		function pinying(){
			$.ajax({
				url:'pinying.action',
				type:'post',
				data:{pinying:$('#item_name').val()},
				success : function(data) {
					$('#item_pinyin').val(data);
				},
			})
		}
		var check_falg = false;
		//验证收费项目是否存在
		function itemNameCheck(){
			var item_name = $("#additem_name").val().trim();
			if(item_name == '' || item_name == null){
				return;
			}else{
					$.ajax({
					url : 'getItemNameCheck.action',
					async : false,
					type : 'post',
					data : {
						item_name : item_name
					},
					success : function(data) {
						var da =  eval('('+data+')')
						if (da.id > 0) {
							if($("#chargitem_id").val() > 0){ //修改操作
								if($("#additem_code").val() != da.item_code){
									$("#additem_name_ch").html('重复');
									check_falg = true;
								}else{
									$("#additem_name_ch").html('*');
									check_falg = false;
								}
							}else{//
								$("#additem_name_ch").html('重复');
								check_falg = true;
							}
						}else{
							$("#additem_name_ch").html('*');
							check_falg = false;
						}
					},
					error : function(){
						$.messager.alert('提示信息', '操作失败！', 'error');
					}
				});
			}
			
			
		}

</script>
<fieldset style="margin: 10px; border-color: #3d85c6">
	<legend>
		<strong>收费项目编辑</strong>
	</legend>
	<div class="formDiv"
		style="margin-top: 10px; float: left; width: 78%; padding-right: 0px;">
		<input type="hidden" id="deptIdSelect" value="<s:property value='model.dep_id'/>" />
		<dl style="height: 24px;">
			<dt style="width: 110px">收费项目编码</dt>
			<dd>
				<input type="hidden" id="chargitem_id" value="<s:property value='id'/>" /> <input
					type="text" id="additem_code" disabled="true"
					value="<s:property  value='item_code'/>" maxlength="45"
					style="height: 26px; width: 160px;" class="textinput" /> <font
					color="ff0000"><span>*</span></font>
			</dd>
			<dt style="width: 125px">收费项目简称</dt>
			<dd>
				<input type="text" class="textinput" id="additem_abbreviation"
					value="<s:property value='item_abbreviation'/>"
					style="height: 26px; width: 160px;" />
			</dd>
			<dt style="width: 125px">所属科室名称</dt>
			<dd>
				<input type="hidden" id="gdep_id"
					value="<s:property value='dep_id'/>" /> <input type="text"
					class="textinput" id="adddep_id"
					style="height: 26px; width: 160px;" /> <font color="ff0000">*</font>
			</dd>
			<%-- <dt style="width:113px">目描述</dt>
			<dd>
				<input type="text" class="textinput" id="item_note"
				value="<s:property value='item_note'/>"  style="height: 26px; width:160px;"/> 
		  	 </dd> --%>
		</dl>
		<dl style="height: 24px">
			<dt style="width: 110px">收费项目名称</dt>
			<dd>
				<input type="text" id="additem_name" onkeydown="itemNameKeyDown()" maxlength="45"
					value="<s:property  value='item_name'/>"
					style="height: 26px; width: 160px;" class="textinput" /> <font  id ='additem_name_ch'
					color="ff0000"><span>*</span></font>
			</dd>
			<dt style="width: 125px">HIS系统关联码</dt>
			<dd>
				<input type="text" class="textinput" id="addhis_num"
					onclick="getHis_price();" value="<s:property value='his_num'/>"
					style="height: 26px; width: 160px;" />
			</dd>
			<dt style="width: 125px">所属统计科室</dt>
			<dd>
				<input type="hidden" id="gdep_category"
					value="<s:property value='dep_category'/>" /> <input type="text"
					id="adddep_category" class="easyui-validatebox"
					data-options="required:true" style="height: 26px; width: 160px;" disabled="disabled" />
				<font color="ff0000">*</font>
			</dd>
		</dl>
		<dl style="height: 24px">
			<dt style="width: 110px">收费项目拼音:</dt>
			<dd>
				<input type="text" id="additem_pinyin" maxlength="45"
					value="<s:property  value='item_pinyin'/>"
					style="height: 26px; width: 160px;" class="textinput" /> <font
					color="ff0000">*</font>&nbsp;&nbsp;
			</dd>
			<dt style="width: 117px">检验系统关联码</dt>
			<dd>
				<input type="text" class="textinput" onblur="exam_numSole()"
					id="addexam_num" ondblclick="getlis_dialog();"
					value="<s:property value='exam_num'/>"
					style="height: 26px; width: 160px;" />
			</dd>
			<dt style="width: 125px">样本分类</dt>
			<dd>
				<input type="hidden" id="demo_type"value="<s:property value='demo_type'/>" />
				<input type="text"class="textinput" id="addDemo_type" style="height: 26px; width: 160px;" />
			</dd>
		</dl>
		<dl style="height: 24px">
			<dt style="width: 110px">项目顺序码:</dt>
			<dd>
				<input type="text" class="textinput" id="additem_seq"
					value="<s:property value='item_seq'/>"
					style="height: 26px; width: 160px;" />
			</dd>
			<dt style="width: 135px">影像系统关联码</dt>
			<dd>
				<input type="text" id="addview_num" class='textinput'
					onblur="view_numSole();" value="<s:property value='view_num'/>"
					style="height: 26px; width: 160px;" />
			</dd>
			<dt style="width: 125px">所属样本</dt>
			<dd>
				<input type="hidden" id="gSampleDemo"
					value="<s:property value='sam_demo_id'/>" /> <input type="text"
					class="textinput" id="addSampleDemo"
					style="height: 26px; width: 160px;" />
			</dd>
			
		</dl>
		<dl style="height: 24px">
			<dt style="width: 110px">项目适应性别</dt>
			<dd style="width: 160px">
				<input type="radio" name="sex" id="sexQ" checked="checked"
					value="全部" class="textinput" /> 全部&nbsp;&nbsp; <input type="radio"
					name="sex" id="sexN" value="男" class="textinput" /> 男&nbsp;&nbsp;
				<input type="radio" name="sex" id="sexY" value="女" class="textinput" />
				女&nbsp;&nbsp;
			</dd>
			<dt style="width: 135px">his执行科室</dt>
			<dd>
				<input type="hidden" id="ggperform_dept"
					value="<s:property value='perform_dept'/>" /> <input type="text"
					class="textinput" id="addperform_dept"
					style="height: 26px; width: 160px;" />
			</dd>
			<dt style="width: 125px">所属报告样本</dt>
			<dd>
				<input type="hidden" id="gSampleReportDemo"
					value="<s:property value='sam_report_demo_id'/>" /> <input
					type="text" class="textinput" id="addSampleReportDemo"
					style="height: 26px; width: 160px;" />

			</dd>
		</dl>
		<dl style="height: 24px">
			<dt style="width: 110px">收费项目类型</dt>
			<dd style="width: 160px; height: 26px">
				<input type="radio" name="item_category" id="item_categoryP"
					checked="checked" value="普通类型" /> <font
					style="text-align: center;">普通类型</font> <input type="radio"
					name="item_category" id="item_categoryH" value="耗材类型" /> 耗材类型
			</dd>
			<dt style="width: 135px">金额</dt>
			<dd>
				<input type="text" class="textinput" id="amount"
					class="easyui-validatebox" value="<s:property value='amount'/>"
					style="height: 26px; width: 160px;" class="textinput" />
			</dd>
			<%-- 	<dt style="width:40px">核算</dt>
			<dd>
				<input type="text" class="textinput" id="addcalculation_amount"
				 value="<s:property value='calculation_amount'/>"
				  style="height: 26px; width:50px;" />
			</dd> --%>
			<dt style="width: 125px;">每日体检人数</dt>
			<dd>
				<input type="text" id="limit_count"
					onkeyup="this.value=this.value.replace(/\D/g,'')"
					onafterpaste="this.value=this.value.replace(/\D/g,'')"
					maxlength="4" class="textinput" style="height: 26px; width: 160px;"
					value="<s:property value='limit_count_s'/>" />
			</dd>
			
		</dl>
		<dl style="height: 24px">
			<dd>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="hidden"
					id="gisOnlyApplyOrReport"
					value="<s:property value='isOnlyApplyOrReport'/>" /> <input
					type="checkbox" class="textinput" 
					id="isOnlyApplyOrReport" value="Y" />
			</dd>
			<dt style="width: 151px">产生独立申请单</dt>
			<dt style="width: 140px">核算金额</dt>
			<dd>
				<input type="text" class="textinput" id="addcalculation_amount"
					value="<s:property value='calculation_amount'/>"
					style="height: 26px; width: 160px;" />
			</dd>
			<dt style="width: 125px">项目类别</dt>
			<dd>
				<input type="hidden" id="gitem_type"
					value="<s:property value='item_type'/>" /> <input type="text"
					class="textinput" id="additem_type"
					style="height: 26px; width: 160px;" />
			</dd>
			
		</dl>
		<dl style="height: 24px">
			<dt style="width: 110px">系统外编码</dt>
			<dd>
				<input type="text" class="textinput" id="addcharge_inter_num"
					value="<s:property value='charge_inter_num'/>"
					style="height: 26px; width: 160px;" />
			</dd>
			<dt style="width: 135px">利润率(%)</dt>
			<dd><input type="text" class="textinput" id="calculation_rate"
					value="<s:property value='calculation_rate'/>"
					style="height: 26px; width: 160px;" />
			</dd>
			<dt style="width: 125px">导引单分类:</dt>
			<dd>
				<input type="hidden" id="yguide_category"
					value="<s:property value='guide_category'/>" /> <input type="text"
					id="addguide_category" class="textinput"
					style="height: 26px; width: 160px;" class="textinput" />
			</dd>
			
			
			<!-- <dt style="width:0px"></dt>
			<dd>
				<div style="width:220px;float: right;margin-top:8px">
					<a href="javascript:addExaminationItem();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
						&nbsp;
   					 <a href="javascript:void(0)" class="easyui-linkbutton" style="width:70px;" onclick="javascript:window.close();">关闭</a>
			   </div>
			</dd> -->
		</dl>
		<dl style="height: 24px">
				<dt style="width: 108px">财务类别</dt>
				<dd>
					<input type="hidden" id="h_finance_class"
						value="<s:property value='finance_class'/>" /> <input
						type="text" class="textinput" id="finance_class"
						style="height: 26px; width: 160px;" />
				<dd>
				<dt style="width:135px">限制次数</dt>
				<dd>
					<input type="text"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  class="textinput" id="charging_item_number"  maxlength="4"
						value="<s:property value='charging_item_number'/>"  style="height: 26px; width: 160px;" /> 
				<dd>
				<dt style="width: 125px">HIS项目类别</dt>
				<dd>
					<input type="hidden" id="item_class"
						value="<s:property value='item_class_c'/>" /> <input type="text"
						class="textinput" id="item_classs"
						value="<s:property value='item_class_cs'/>"
						style="height: 26px; width: 160px;" />
				</dd>
					<%-- 	<dt style="width:135px">项目排期</dt>
				<dd>
					<input type="hidden" id = "h_on_off_schedule"   value="<s:property value='on_off_schedule'/>"  />
					<input type="radio" value='Y' id="on_schedule"  name = "on_off_schedule" />是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio"  name="on_off_schedule"  checked="checked" id="off_schedule" value='N'/>否</dd>
				<dt style="width:55px"></dt>
				<dd >
				开始日期
				<input class="easyui-datebox" name="schedule_start_time"    id="schedule_start_time"  
        			data-options="editable:false" value="<s:property value='schedule_start_time'/>" style="width:95px;height: 26px;">  
        		
				截止日期
				<input class="easyui-datebox"  name="schedule_end_time"  id="schedule_end_time"   
        			data-options="editable:false" value="<s:property value='schedule_end_time'/>" style="width:95px;height: 26px;"> 
        		</dd> --%>
			</dl>
			<%-- 	<dl>
				<dt style="width: 95px">排期数量</dt>
				<dd>
						<input type="text"  maxlength="4" class="textinput" id="schedule_number"     onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
						value="<s:property value='schedule_number'/>"
						style="height: 26px; width: 160px;" />
					
				</dd>
			</dl> --%>
			<dl>
				 <dt style="width: 108px">接口标识</dt>
				 <dd><select class="easyui-combobox" data-options="panelHeight:80"
					id="addinterface_flag" style="height: 26px; width: 160px;">
					<option value="0" id="jb">不传值</option>
					<option value="1" id="jn">系统内</option>
					<option value="2" id='jw'>系统外</option>
					<option value="3" id='type'>类型</option>
				 </select>
				 </dd>
				 <dt style="width: 135px">项目折扣率</dt>
				 <dd>
					<input type="text" class="textinput" id="item_discount" value="<s:property value='item_discount'/>"
						style="height: 26px; width: 160px;" />
				 </dd>
                    <s:if test='id == 0' >
                         <dt style="width: 125px">体检中心</dt>
                         <dd>
                            <input id="center" class="easyui-combobox" data-options="
                              panelHeight : 'auto',
                                url : 'queryAllExam.action',
                                valueField : 'center_num',
                                textField : 'center_name',
                                editable:false,
                                width:160,
                                height:26,
                                multiple:true,
                                onLoadSuccess:function(){
                                    var val = $(this).combobox('getData');
                                    for (var item in val[0]){
                                        if (item == 'center_num'){
                                             $(this).combobox('select', val[0][item]);
                                        }
                                    }
                                }
                              " />
                         </dd>
                    </s:if>
			</dl>
	</div>
	<div style="float: right; width: 22%; padding-left: 0px;"
		class="formDiv">
		<dl style="height: 100px; padding-left: 0px;">
			<dt style="width: 60px;">项目描述</dt>
			<dd>
				<%-- <input type="text" class="textinput" id="item_note"
					value="<s:property value='item_note'/>"  style="height: 26px; width:160px;"/>  --%>
				<textarea style="height: 100px;" id="item_note"><s:property
						value='item_note' /></textarea>
			</dd>
		</dl>
		<dl style="height: 100px; padding-left: 0px;">
			<dt style="width: 60px;">备注</dt>
			<dd>
				<textarea style="height: 95px;" id="remark"><s:property
						value='remark' /></textarea>
			</dd>
		</dl>
		<dl>
			<dt style="width: 60px;"></dt>
			<dd>

				<div style="width: 220px; float: right; margin-top: 8px">
					<a href="javascript:addchargingItemdo();"
						class="easyui-linkbutton c6" style="width: 80px;">保存</a> &nbsp; 
						<a href="javascript:void(0)" class="easyui-linkbutton" style="width: 70px;" onclick="javascript:window.close();">关闭</a>
				</div>
			</dd>
		</dl>
	</div>
</fieldset>
<!-- 普通类型显示 -->
<div id="common" style="margin: 0 auto; width: 98.5%;">
	<fieldset style="margin-top: 2px; border-color: #3d85c6; height: 55%;">
		<legend>
			<strong>检查项目清单</strong>
		</legend>
		<div style="float: left; width: 49.5%;">
			<span style="font-size: 16px;">检查项目名称</span> <input type="text"
				class="textinput" id="item_num" style="width: 150px; height: 26px" />
			<a href="javascript:getExaminationItem();"
				class="easyui-linkbutton c6" style="width: 100px;">查询</a>
			&nbsp;&nbsp;
			<a href="javascript:addJianChaItem();"
				class="easyui-linkbutton c6" style="width: 150px;">新增检查项目</a>
					
			<table id="examinationItem"></table>
		</div>
		<div style="float: right; width: 49.5%; margin-top: 2px">
			<div style="height: 29px">
				<span style="font-size: 16px;">以下是已选择检查项目清单</span>
			</div>
			<table id="selectedExaminationItem" class="easyui-datagrid"></table>
		</div>
	</fieldset>
</div>

<div id="his_price" class="easyui-dialog"
	data-options="width: 1200,height: 590,closed: true,cache: false,modal: true"></div>
<div id="lis_open" class="easyui-dialog"
	data-options="width: 1200,height: 590,closed: true,cache: false,modal: true"></div>
<div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 

