<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>危急值页面</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
var winopen = '';
var timer = '';
	$(function(){
		getGrid();
		$('#exam_num').textbox('textbox').css('ime-mode','disabled');
		$('#exam_num').textbox('textbox').focus();
		//getLogGrid();
		
		
		 $('#dep_category').combobox({
			url : 'getDatadis.action?com_Type=WJZLX',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				if (data.length>0) {
					$('#dep_category').combobox('setValue', data[0].id);
				}
			}
		});
	});
	function IfWindowClosed() {
            if (winopen.closed == true)          
            {
               searchFun();
               window.clearInterval(timer);
            }
     }


	function searchFun(){
		getGrid();
	}
	function cleanFun(){
		$('#done_flag_s').combobox('setValue',"");
		$('#startCheckDate').datebox('setValue',"");
		$('#endCheckDate').datebox('setValue',"");
		$("#sm").textbox('setValue',"");
		$("#dep_category").combobox('setValue',"");
		$("#startDone_date").datebox('setValue',"");
		$("#endDone_date").datebox('setValue',"");
//		$("#data_source").combobox('setValue',"");
		$("#exam_result").textbox('setValue',"");
		$("#exam_num").textbox('setValue',"");
		getGrid('reload');
	}
	function getGrid(){
		var model = {"done_flag":document.getElementsByName("done_flag_s")[0].value,
		"startCheckDate": $('#startCheckDate').datebox('getValue'),
		"endCheckDate":$('#endCheckDate').datebox('getValue'),
//		"check_doctor":$("#check_doctor").val(),
//		"data_source":document.getElementsByName("data_source")[0].value,
		"startDone_date":$('#startDone_date').datebox('getValue'),
		"endDone_date":$('#endDone_date').datebox('getValue'),
		"critical_type":document.getElementsByName("dep_category")[0].value,
		"exam_result":$("#exam_result").val(),
		"exam_num":$("#exam_num").val()
		};
		   $("#criticallist").datagrid({
			url: 'getCriticalList.action',
			queryParams: model,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	        toolbar:'#toolbar',
			height:400,
	        columns:[[
					  {align:"center",field:"id","title":"ID","hidden":true},
//	                  {align:'center',field:"arch_num","title":dahname,width:100},
	        		  {align:"center",field:"exam_num","title":tjhname,"width":120,upload:'exam_num'},
	        		  {align:"center",field:"user_name","title":"姓名","width":100,upload:'user_name'},
	        		  {align:"center",field:"sex","title":"性别","width":50,upload:'sex'},
	        		  {align:"center",field:"age","title":"年龄","width":50,upload:'age'},
	        		  {align:"center",field:"phone","title":"电话","width":100,upload:'phone'},
	        		  {align:"center",field:"company","title":"单位","width":200,upload:'company'},
	        		  {align:'center',field:"done_flag_s","title":"状态","width":60,"styler":f_clolor},
	        		  {align:'center',field:"critical_type_s","title":"类型","width":80,upload:'critical_type_s'},
	        		  {align:"center",field:"exam_result","title":"内容","width":200,upload:'exam_result'},
//	        		  {align:"center",field:"check_date","title":"体检日期","width":150,upload:'check_date'}, 
	        		  {align:'center',field:"check_doctor","title":"处理医生","width":100,upload:'check_doctor'},
	        		  {align:'center',field:"done_date","title":"处理日期","width":120,upload:'done_date'},
	        		  {align:"center",field:"note","title":"处理内容","width":300,upload:'note'},
	        		  {align:"center",field:"creater","title":"创建者","width":100,upload:'creater'},
	        		  {align:"center",field:"create_time","title":"创建日期","width":120,upload:'create_time'},
	        		  {align:"center",field:"old_results","title":"历史记录","width":520,upload:'old_results',hidden:'true'},
	        		  {align:"center",field:"del","title":"删除","width":80,"formatter":f_del}
	        		  ]],
		    onLoadSuccess:function(value){
		    	$("#datatotal").val(value.total);
		    	MergeCells('criticallist', 'exam_num,user_name,sex,age,phone');
		    },
		    onDblClickRow:function(index,row){
		    	$("#note").val('');
		    	$("#critical_edit").dialog({
		    		title:'处理危急值信息',
		    	});
		    	$("#critical_edit").dialog("open");
		    	$("#examnum").val(row.exam_num);
		    	getLogGrid();
		    }, 
		   nowrap:false,
			rownumbers:true,
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
			fitColumns:false,
			fit:true,
		    striped:true,
	      	toolbar:toolbar	
		});
	}
function f_clolor(value,row,index){
	if(row.done_flag_s == '未处理'){
		return 'color:red';
	}
}
function f_critical_type(val,row){
	if(val == '0'){
		return '重大阳性';
	}
	if(val == '1'){
		return '危急值';
	}
}

//合并单元格
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


function f_Crisave(flag){
		if($("#note").val() == '' || $("#note").val() == null ){
			$.messager.alert('提示信息', '处理内容不能为空。。', 'info');
			return;
		}
	
		$.ajax({
			url:'saveCritical.action',
			type:"post",
			data:{
				exam_num:$("#examnum").val(),
				note:$("#note").val(),
				flag:flag
			},
			success : function(data) {
				$.messager.alert("操作提示",data);
				$("#critical_edit").dialog("close");
				getGrid();
			},
			error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
			}
		});
		
	}

function getLogGrid(){
	 	var model={"exam_num":$("#examnum").val()};
		   $("#criticalLogList").datagrid({
			url: 'getCriticalHandleListByExamNum.action',
			queryParams: model,
	        pageSize: 5,//每页显示的记录条数，默认为10 
	        pageList:[5,10,15],//可以设置每页记录条数的列表 
	        toolbar:'',
			height:400,
	        columns:[[
	                  {align:'center',field:"doctorname","title":'操作者',width:80},
	        		  {align:"center",field:"visit_date","title":'处理时间',"width":150},
	        		  {align:"left",field:"customer_feedback","title":'处理内容',"width":400}
	        		  ]],
		    nowrap:false,
			rownumbers:true,
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
			fitColumns:false,
			fit:true,
		    striped:true
		    
		});
	}

var  toolbar=[{
		text:'数据导出',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var criticallist = 'criticallist';
	    	getGrid();
	    	datagridExportExcel(criticallist,true,'wjz','sheetname');
			}
	}];
function f_del(val,row,index){
	return '<a href=\"javascript:f_del_s(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"添加\" /></a>';
}

function f_del_s(id){
	$.messager.confirm('提示信息','确定删除吗？',function(r){
		if(r){
			$.ajax({
				url:'delCriticalById.action',
				type:"post",
				data:{
					id:id
				},
				success : function(da) {
					if(da.split("-")[0] == 'ok'){
						$.messager.alert('提示信息', da.split("-")[1], '');
					}else{
						$.messager.alert('提示信息', da.split("-")[1], 'error');
					}
					getGrid();
				},
				error : function() {
				$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	});
	
}

</script>
</head>
	<body>
	<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:100px;">
	<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
	<legend><strong>信息检索</strong></legend>
	<div id="tb" style="padding-right:20px;padding-left:30px;">
		&nbsp;&nbsp;&nbsp;处理标识:<select id="done_flag_s" name = 'done_flag_s'class="easyui-combobox" style="height:24px;width:154px;">
			                		<option value="0" >未处理</option>
			                		<option value="1">已处理</option>
			                		<option value="">所有</option>
                				</select>
		&nbsp;&nbsp;&nbsp; 类型:<select id="dep_category" name = 'dep_category' class="easyui-combobox" style="height:24px;width:120px;">
								</select>	
		&nbsp;&nbsp;&nbsp;检查时间:<input id="startCheckDate" name="startCheckDate" class="easyui-datebox"  data-options="prompt:'起始时间'" />
		&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;<input id="endCheckDate" name="endCheckDate" class="easyui-datebox"  data-options="prompt:'时间'" />
		
	</div>
	<div style="padding-right:20px;padding-left:30px;padding-top:5px;">
		&nbsp;&nbsp;&nbsp; 体检号:  <input id = "exam_num" name = "exam_num" class="easyui-textbox" style="height:24px;width:160px;"/>
		&nbsp;&nbsp;&nbsp; 内容:  <input id = "exam_result" name = "exam_result" class="easyui-textbox" style="height:24px;width:120px;"/>
		&nbsp;&nbsp;&nbsp;处理时间:<input id="startDone_date" name="startDone_date" class="easyui-datebox"  data-options="prompt:'起始时间'" />
		&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;<input id="endDone_date" name="endDone_date" class="easyui-datebox"  data-options="prompt:'时间'" />						
		
		&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
		&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
	</div>
	 </fieldset>
	 </div>
	 <div  data-options="region:'center'">
		<fieldset style="margin:5px;padding-right:20px;height:95%;">
		<legend><strong>信息列表</strong></legend>
		  <div id="criticallist"></div>
		</fieldset>
	 </div>
	 </div>
	 <div id="critical_edit" class="easyui-dialog" data-options="width: 730,height: 550,closed: true,cache: false,modal: true,top:50" >
	 	<fieldset style="margin:10px;padding-right:20px;height:52%;">
			<legend><strong>处理信息列表</strong></legend>
			  <div id="criticalLogList"></div>
		</fieldset>
	 	
		<fieldset style="margin:10px;padding-top:1px;height:25%;">
					<legend><strong>处理危机内容</strong></legend>	
				
		   <div class="formdiv">
				<dl>
			    	<dt><input type="hidden" id="examnum"  value=""/>处理内容</dt>
			    	<dd><textarea style="width:90%;resize:none;" cols="55" rows="4" name="note" id="note" ></textarea>
					</dd>
			   </dl>
			</div>
			
		</fieldset>
	 	<div class="dialog-button-box">
				<div class="inner-button-box">
				    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="f_Crisave(1)">处理完成</a>&nbsp;&nbsp;&nbsp;
		   			 <a href="javascript:void(0)" class="easyui-linkbutton c4" style="width:80px;" onclick="f_Crisave(0)">保存</a>&nbsp;&nbsp;&nbsp;
				    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#critical_edit').dialog('close')">关闭</a>
				</div>
		</div>
	 </div>
	</body>
	</html>