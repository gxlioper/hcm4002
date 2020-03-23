<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(document).ready(function () {
//	$('#addarch_num').val($("#arch_num").html());
//	$("#addexam_num").val($("#exam_num_x").html());
//	$("#addusername").val($("#user_name").html());
//	
	$('#addimportant').combobox({
		url : 'getDatadis.action?com_Type=JHZYJB',
		editable : false, //不可编辑状态
		cache : false,
		height:26,
		width:210,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#addimportant').combobox('setValue', data[0].id);
		}
	});
	//-----------------------------------------------------------------
	$('#tactics_type').combobox({
		url : 'getCrmVisitPlanTacticsType.action',
		editable : true, //不可编辑状态
		height:26,
		width:210,
		panelHeight : '120',//自动高度适合
		valueField : 'tactics_type',
		textField : 'tactics_type_s',
		onLoadSuccess : function(data) {
			$('#tactics_type').combobox('setText',data[0].tactics_type_s);
			$('#tactics_type').combobox('setValue',data[0].tactics_type);
		}
	 });
	 
	$("#tactics_type").combobox({
		onChange: function (n,o) {
		getTacticsNotices(n);
		}
	});
	
	$("#tactics_notices").combobox({
		onChange: function (n,o) {
		getPlantacTicsDetailListBynum(n);
		}
	});
	
	
	
});

function getTacticsNotices(tactics_type){
	$('#tactics_notices').combobox({
		url : 'getTacticsNoticesList.action?tactics_type='+tactics_type,
		editable : true, //不可编辑状态
		height:26,
		width:210,
		panelHeight : '220',//自动高度适合
		valueField : 'tactics_num',
		textField : 'notices',
		onLoadSuccess : function(data) {
			$('#tactics_notices').combobox('setText',data[0].notices);
			$('#tactics_notices').combobox('setValue',data[0].tactics_num);
		}
	 });
}
var y ;
function getPlantacTicsDetailListBynum(tactics_num){
	$.ajax({
			url : 'getPlanTacticsByNum.action',
			data : {"tactics_num":tactics_num
					},
			type : "post",//数据发送方式   
			success : function(da) {
				var row = eval('('+da+')');
				$("#tactics_remark").val(row.rmark);
			},
			error : function() {
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
	
	
	$(".load_div").remove();
	$.ajax({
			url : 'getPlantacTicsDetailList.action',
			data : {"tactics_num":tactics_num
					},
			type : "post",//数据发送方式   
			success : function(data) {
				var row = eval('('+data+')');
				var da = row.rows;
				y = 0;
				for(var i = 0; i < da.length; i++){
					 y = i + 1;
					$("#dt").append("<dl class='load_div'><input  type='hidden' class='textinput'  id='tactics_detail_id"+y+"' value = '"+da[i].id+"'/><dl/>");
					$("#dt").append("<dl class='load_div'> <dt style='width:100px;'>回访内容"+y+"</dt><dd><textarea id='tactics_notices"+y+"' rows='2' cols='64'>"+da[i].notices+"</textarea></dd> </dl>");
					$("#dt").append("<dl class='load_div'><dt style='width:100px;'>计划回访时间"+y+"</dt><dd><input type='text' class='textinput'  id='tactics_distancedate"+y+"' style='width:155px;height:18px;'  value='"+da[i].distancedate+"' /> (天以后)</dd><dt style='width:100px;'>计划回访医生"+y+"</dt><dd><input   class='easyui-combobox'  id='tactics_plan_doctor_"+y+"' style='height: 26px; width: 203px;'/></dd></dl>");
					
					$('#tactics_plan_doctor_'+y).combobox({
						editable : true, //不可编辑状态
						cache : false,
						data:[{
						        "id":da[i].plan_doctor_id,
						        "chi_Name":da[i].plan_doctor,
						        "selected":true
						 }],
				            textField: 'chi_Name',
				            valueField: 'id',
							 onSelect : function() {
							}
				        });
				        $('#tactics_plan_doctor_'+y).parent().on('click',function(){
				    	 var obj = $(this);
						f_click(obj.find("input").attr("id"));
					});
				        
				}
			},
			error : function() {
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
}



function f_uu(c){
     if(/[^\d]/.test(c.value)){//替换非数字字符  
      var temp_amount=c.value.replace(/[^\d]/g,'');  
       c.value = temp_amount;
     }  
}
function f_click(t){
	$('#'+t).combobox({
		url : 'getDepuser.action?type='+1,
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'chi_Name',
		onLoadSuccess : function(data) {
//			$('#'+t).combobox('setText',data[0].chi_Name);
//			$('#'+t).combobox('setValue',data[0].id);
		}
	});
}
function  add_tactics_detail(){//增加列明细
     y++;
    $("#dt").append("<dl class='load_div'><input  type='hidden' class='textinput'  id='tactics_detail_id"+y+"' value = ''/><dl/>");
	$("#dt").append("<dl class='load_div'> <dt style='width:100px;'>回访内容"+y+"</dt><dd><textarea id='tactics_notices"+y+"' rows='2' cols='64'></textarea></dd> </dl>");
	$("#dt").append("<dl class='load_div'><dt style='width:100px;'>计划回访时间"+y+"</dt><dd><input type='text' class='textinput'  id='tactics_distancedate"+y+"' style='width:155px;height:18px;'  value='' /> (天以后)</dd><dt style='width:100px;'>计划回访医生"+y+"</dt><dd><input   class='easyui-combobox'  id='tactics_plan_doctor_"+y+"' style='height: 26px; width: 203px;'/></dd></dl>");
	$('#tactics_plan_doctor_'+y).combobox({
		url : 'getDepuser.action?type='+1,
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'chi_Name',
		onLoadSuccess : function(data) {
			$('#tactics_plan_doctor_'+y).combobox('setText',data[0].chi_Name);
			$('#tactics_plan_doctor_'+y).combobox('setValue',data[0].id);
		}
	});
}
function del_tactics_detail(){//删除列明细
		$(".load_div").remove();
		y=0;
}

function addCrmVisitPlanList(){
     if(y <= 0){
     	$.messager.alert('提示信息','请添加回访策略明细。。','info');
		return;
     }
     var tacticsDetailList = new Array();
     for(var i = 1; i <= y ;i++){
     	if(Number($("#tactics_distancedate"+i).val()) >= 0){
     		tacticsDetailList.push({
     			id:$("#tactics_detail_id"+i).val(),
     			notices:$("#tactics_notices"+i).val(),
     			plan_doctor_id:$("#tactics_plan_doctor_"+i).combobox('getValue'),
     			distancedate:$("#tactics_distancedate"+i).val()
     		});
     		
     	}else{
	     	$.messager.alert('提示信息','回访时间天数必须大于0。。','info');
			return;
     	}
     }
     
     $.ajax({
		url : 'addCrmVisitPlanList.action',
		type : 'post',
		data : {
			"arch_num" : $('#addarch_num').val(),
			"exam_num" : $("#addexam_num").val(),
			"tactics_num" : $("#tactics_notices").combobox('getValue'),
			"visit_status" : '1',
			"visit_important":$("#addimportant").combobox('getValue'),
			"tacticsDetailList":JSON.stringify(tacticsDetailList)
		},
		success : function(data) {
			$.messager.alert('提示信息',data);
			$('#dlg-edit').dialog('close');
			window.location.reload(true); 
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
     

}




</script>
<input type="hidden" id="add_flag" value="<s:property value="flag"/>"/>
<fieldset style="margin: 10px; height: 1500px;">
	<legend><strong>健康计划编辑</strong></legend> 
	<form id="add1Form">
<div class="formdiv">
		<div id ="dt" class="formdiv fomr3" style="padding-top:20px;">
		<dl>
			<dt style="width:100px;">
				<s:text name="tjhname"/>
			</dt>
			<dd>
				<input  type="text" class="textinput"  id="addexam_num" disabled="disabled" class="easyui-validatebox" style="height: 18px; width: 205px;" value="<s:property value="model.exam_num"/>"/>
			</dd>
			<dt style="width:100px;">
				姓名 
			</dt>
			<dd>
				<input  type="text" class="textinput"  id="addusername" disabled="disabled" class="easyui-validatebox" style="height: 18px; width: 205px;" value="<s:property value="model.persionName"/>" />
			</dd>
		</dl>
		<dl>
		    <dt style="width:100px;">重要级别 </dt>
		    <dd>
		       <select class="easyui-combobox" id="addimportant" style="width:210px;height:26px;"></select>
		    </dd>
		    <dt style="width:100px;"><s:text name="dahname"/></dt>
		    <dd>
		       <input  type="text" class="textinput"  id="addarch_num" disabled="disabled" class="easyui-validatebox" style="height: 18px; width: 205px;" value="<s:property value="model.arch_num"/>"/>
		    </dd>
		</dl>
		
		<dl>
			<dt style="width:100px;">
				策略类型：
			</dt>
			<dd><select class="easyui-combobox"  id="tactics_type"  name="tactics_type" style="width:180px;height:26px;">  
				</select>
			</dd>
			<dt style="width:100px;">
				策略描述：
			</dt>
			<dd><select class="easyui-combobox"  id="tactics_notices"  name="tactics_notices" style="width:180px;height:26px;">  
				</select>
			</dd>
		</dl>
		
		<dl>
		    <dt style="width:100px;">策略说明</dt>
		    <dd>
		    	<textarea id='tactics_remark' rows="2" cols="64"></textarea>
		    </dd>
		</dl>
		
	</div>
  </div>
</form>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	 <a href="javascript:add_tactics_detail();" class="easyui-linkbutton" style="width:80px;"><font size="6" color="royalblue">＋</font></a>&nbsp;&nbsp;&nbsp;
	  <a href="javascript:del_tactics_detail();" class="easyui-linkbutton" style="width:80px;"><font size="6" color="red">×</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <a href="javascript:addCrmVisitPlanList();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
