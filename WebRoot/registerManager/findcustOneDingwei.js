$(document).ready(function() {

	f_getDatadicsex();
	setonefzchareitemListGrid();	
	$('#onecom_Name').bind('click', function() {  
		select_onecom_list(this.value);
    }); 
	$('#onecom_Name').bind('keyup', function() {
		select_onecom_list(this.value);
    });
	$('#onecom_Name').bind('blur', function() {  
		select_onecom_list_over();
    });	
	$('#oneexam_num').textbox('textbox').css('ime-mode','disabled');
	$('#oneexam_num').textbox('textbox').focus();
	
});
	

var onecom_Namess;
function select_onecom_list(onecom_Namess){
	var url='companychangshow.action';
 	var data={"name":onecom_Namess};
	load_post(url,data,select_onecom_list_sess);
	}

/**
 * 显示树形结构
 * @param data
 * @returns
 */
function select_onecom_list_sess(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:setvalueone('"+data[index].id+"','"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvalueone('"+data[index].id+"','"+data[index].text+"')",
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
	function setvalueone(id,name){
		$("#onecompany_id").val(id);
		$("#onecom_Name").val(name);
		$("#com_name_list_div").css("display","none");		
	}

//单位失去焦点
var hc_onemous_select1=false;
function select_onecom_list_over(){
	if(!hc_onemous_select1){
	$("#com_name_list_div").css("display","none");
	}
	}

function select_onecom_list_mover(){
	hc_onemous_select1=true;
	}
function select_onecom_list_amover(){
	hc_onemous_select1=false;
	}

	
	
function f_getDatadicsex() {
	var sextype = '<s:property value="model.sex"/>';
	$('#onesex').combobox({
		url : 'getDatadis.action?com_Type=XBLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == sextype) {
					$('#onesex').combobox('setValue', data[i].id);
					break;
				} else {
					$('#onesex').combobox('setValue', data[0].id);
				}
			}
		}
	});
}

	function setonefzchareitemListGrid() {
		if($("#onecom_Name").val().length<=0){
			$("#onecompany_id").val(0);
		}
		var model = {
				"exam_num": $("#oneexam_num").val(),
				"custname" : $("#onecustname").val(),
				"id_num" :  $("#oneid_num").val(),
				"arch_num": $("#onearch_num").val(),
				"sex":document.getElementsByName("onesex")[0].value,
				"tel" : $("#onetel").val(),
				"company_id":$("#onecompany_id").val()
		};
		$("#coustomerDingweilist").datagrid({
			url : 'findcustOneDingweishow.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			pageSize: 10,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [ [ {align : 'center',field : 'arch_num',title : dahname,width : 20,},
			              {align : 'center',field : 'exam_num',title : tjhname,width : 23,},	
			              {align : 'center',field : 'join_date',title : '体检日期',width : 20},	
			              {align : 'center',field : 'statuss',title : '状态',width : 15},
			              {align : 'center',field : 'id_num',title : '证件号码',width : 40},
			              {align : 'center',field : 'phone',title : '联系电话',width : 20},
			              {align : 'center',field : 'user_name',title : '姓名',width : 25}, 
			              {align : 'center',field : 'sex',title : '性别',width : 25}, 
			              {align : 'center',field : 'age',title : '年龄',width : 25}, 
			              {align : 'center',field : 'com_name',title : '单位',width : 40},
			              {align : "center",field : "fxfzddd",title : "选择",	width : 15,	"formatter" : f_selectat},
			            ] ],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
			},
			singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
			
		});
	}
	
	/**
	  * 显示一条方案
	  * @param val
	  * @param row
	  * @returns {String}
	  */
	  function f_selectat(val,row){	
		  return '<a href=\"javascript:f_savedo(\''+row.exam_num+'\')\">选择</a>';
	  }

	 var examid
	 function f_savedo(exam_num){
//		 f_findcustomerone_select(examid);
		 f_findcustomerone(exam_num);
		 $('#dlg-show').dialog('close');
	 }