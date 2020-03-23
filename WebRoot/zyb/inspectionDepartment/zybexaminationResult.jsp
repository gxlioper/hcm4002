<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$.ajax({
		url:'getZybExamSummaryResult.action',
		type:'post',
		async: true,
		data:{exam_num:$("#exam_num").val()},
		success:function(data){
			if(data == 'null'){
				getdiseaslist('');
			}else{
				var obj = eval("("+data+")");
//				resultID = obj.resultID;
				$('#dis_exam_result').val(obj.exam_result);
				$("#result_remark").val(obj.remark);
				getdiseaslist(obj.occidList);
			}
		}
	});
});
//val resultID = "";
function getdiseaslist(occids){
	$.ajax({
		url:'examoccuhazardfactorslist.action',
		type:'post',
		async: true,
		data:{exam_num:$("#exam_num").val()},
		success:function(data){
			var obj = eval("("+data+")");
			
			var occus = new Array();
			for(i=0;i<obj.rows.length;i++){
				occus.push({'hazardfactorsID':obj.rows[i].hazardfactorsID,'occuphyexaclassid':obj.rows[i].occuphyexaclassid});
			}
			var model={"examOccuhazardfactorsLists":JSON.stringify(occus)};
			$("#zyb_disease").datagrid({
				url:'getExamSummaryOccudiseaseList.action',
				dataType: 'json',
				queryParams:model,
				rownumbers:false,
				columns:[[
					     {align:'center',field:'diseaseclass_name',title:'职业病类别',width:10},
					     {align:'center',field:"occudisease_name",title:"职业病名称",width:15}
				]],	    	
				onLoadSuccess:function(value){
				    $("#datatotal").val(value.total);
				    var data = $("#zyb_disease").datagrid('getRows');
				    for(i=0;i<data.length;i++){
				    	for(j=0;j<occids.length;j++){
				    		if(data[i].occudiseaseID == occids[j].occudiseaseIDorcontraindicationID){
				    			$("#zyb_disease").datagrid('selectRow',i);
				    		}
				    	}
				    }
				},
				singleSelect:false,
				collapsible:true,
				pagination: false,
				fitColumns:true,
				striped:true,
				fit:true,
				nowrap:false
			 });

			$("#jjz_disease").datagrid({
				url:'getExamSummaryOccucontraindicationList.action',
				dataType: 'json',
				queryParams:model,
				rownumbers:false,
				columns:[[
					     {align:'center',field:'contraindication_name',title:'禁忌证名称',width:10}
				]],	    	
				onLoadSuccess:function(value){
				    $("#datatotal").val(value.total);
				    var data = $("#jjz_disease").datagrid('getRows');
				    for(i=0;i<data.length;i++){
				    	for(j=0;j<occids.length;j++){
				    		if(data[i].contraindicationID == occids[j].occudiseaseIDorcontraindicationID){
				    			$("#jjz_disease").datagrid('selectRow',i);
				    		}
				    	}
				    }
				}, 
				singleSelect:false,
				collapsible:true,
				pagination: false,
				fitColumns:true,
				striped:true,
				fit:true,
				nowrap:false
			 });
		}
	});
}
function f_save_result(){
	if(status == 'Z' && $('#webResource').val() != '1'){
		$.messager.alert("操作提示",'已总检,不能修改检查结果!', "error");
		return;
	}
//	var resultid = resultID.split(",");
	var result = $('#dis_exam_result').val();
	var ocid = '';
/**	for(j=0;j<resultid.length;j++){
		if(resultid[j] == '10'){
			var data = $("#zyb_disease").datagrid('getSelections');
			if(data.length == 0){
				$.messager.alert("操作提示",'请选择职业病!', "error");
				return;
			}
			var accid = new Array();
			result +='：';
			for(i=0;i<data.length;i++){
				accid.push(data[i].occudiseaseID);
				if(i == data.length-1){
					result += data[i].occudisease_name;
				}else{
					result += data[i].occudisease_name + '，';
				}
			}
			ocid = accid.toString();
		}else if(resultid[j] == '20'){
			var data = $("#zyb_disease").datagrid('getSelections');
			if(data.length == 0){
				$.messager.alert("操作提示",'请选择疑似职业病!', "error");
				return;
			}
			var accid = new Array();
			result +='：';
			for(i=0;i<data.length;i++){
				accid.push(data[i].occudiseaseID);
				if(i == data.length-1){
					result += data[i].occudisease_name;
				}else{
					result += data[i].occudisease_name + '，';
				}
			}
			ocid = accid.toString();
		}else if(resultid[j] == '30'){
			var data = $("#jjz_disease").datagrid('getSelections');
			if(data.length == 0){
				$.messager.alert("操作提示",'请选择禁忌证!', "error");
				return;
			}
			var accid = new Array();
			result +='：';
			for(i=0;i<data.length;i++){
				accid.push(data[i].contraindicationID);
				if(i == data.length-1){
					result += data[i].contraindication_name;
				}else{
					result += data[i].contraindication_name + '，';
				}
			}
			ocid = accid.toString();
		}
	}*/
	var data = $("#zyb_disease").datagrid('getSelections');
	var accid = new Array();
	if(data.length > 0){
		for(i=0;i<data.length;i++){
			accid.push(data[i].occudiseaseID);
		}
		ocid = accid.toString();
	}else{
		data = $("#jjz_disease").datagrid('getSelections');
		for(i=0;i<data.length;i++){
			accid.push(data[i].contraindicationID);
		}
		ocid = accid.toString();
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveZybExamSummaryResult.action',
		type:'post',
		async: false,
		data:{exam_info_id:$("#result_exam_info_id").val(),
			exam_num:$("#exam_num").val(),
			resultID:"",
			exam_result:result,
			remark:$("#result_remark").val(),
			occudiseaseIDorcontraindicationID:ocid},
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert("操作提示",data, "info");
			
		}
	});
}
var pd=1;
//鼠标离开到下拉div
function select_com_list_amover(){
	pd=1;
}
//鼠标移动到下拉div
function select_com_list_mover(){
	pd=2;
}
//失去焦点
function gb(){
	//移动到div不执行焦点事件
	if(pd!=2){
		$(".xscyc").hide();
	}
}
//获取常用词--单击事件
function cyc(ths){
	$.ajax({
		url:'getZybExaminationResultList.action',
		type:'post',
//		dataType: "json",
		data:{'exam_result':''},
		success:function(data){
				var jcxm=eval('('+data+')');
				if(jcxm.length>0){
					var str = '';
					for(var i=0;i<jcxm.length;i++){
						str+="<li data-id=\""+jcxm[i].resultID+"\" onclick='zhi(\""+jcxm[i].resultID+"\")'>"+jcxm[i].result_name+"</li>";
					}
					$(".xscyc ul").html(str);
					$(".xscyc").show();
				}
			}
	});
}
function zhi(id){
    var va = ""
    $(".xscyc ul li").each(function (k,v) {
        if(id==$(this).data('id')){
            va = $(this).text()
        }
        //console.log($(this).text())
    })
	if($("#dis_exam_result").val() == ""){
		$("#dis_exam_result").val(va);
	}else{
		$("#dis_exam_result").val($("#dis_exam_result").val()+'\r\n'+va);
	}
	$(".xscyc").hide();
}
</script>
<input type="hidden" id="result_exam_info_id" value="<s:property value='exam_info_id'/>"/>
<input id="exam_num" type="hidden" value="<s:property value='exam_num'/>"/>
<form id="add1Form">
<div class="formdiv">
		<div class="xscyc" onmouseover="select_com_list_mover()" onmouseout="select_com_list_amover()"><ul></ul></div>
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl style="height:75px;">
				<dt style="width:120px;">检查结果：</dt>
				<dd><textarea id="dis_exam_result" class="textinput" onclick="cyc(this)" onblur="gb()" style="height:70px;width:573px;resize: none;"></textarea></dd>
			</dl>
			<dl style="height:75px;">
				<dt style="width:120px;">医学建议：</dt>
				<dd><textarea id="result_remark" class="textinput" style="height:70px;width:573px;resize: none;"></textarea></dd>
			</dl>
			<dl id="disdl" style="height:255px;">
				<dt style="width:120px;">职业病列表：</dt>
				<dd style="width:250px;height:250px;"><table id="zyb_disease"></table></dd>
				<dt style="width:100px;">禁忌证列表：</dt>
				<dd style="width:210px;height:250px;"><table id="jjz_disease"></table></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="javascript:f_save_result();">保存检查结果</a>
	   <!--  <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a> -->
	</div>
</div>
</form>