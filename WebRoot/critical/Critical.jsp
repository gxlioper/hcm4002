<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%
	application.setAttribute("name","application_James");

%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>危急值页面</title>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
	<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmvisitplan/newcrmvisitplan.js?randomId=<%=Math.random()%>"></script>
	<style type="text/css" >
		.user-query       {padding:14px 0 0;}
		.user-query dl    {height:30px;padding-left:10px;}
		.user-query dt    {width:100px;padding-top:4px;}
		.user-query dd    {padding-right:10px;}
		.user-query dt.width80{width:70px;}
		.user-query dt.autoWidth{width:auto;padding-right:10px;}
		.user-query       {padding:5px 0 0;}
		.user-query dl    {height:20px;padding-left:10px;}
		.user-query dt    {width:100px;padding-top:4px;
			word-break:keep-all;           /* 不换行 */
			white-space:nowrap;          /* 不换行 */
			overflow:hidden;               /* 内容超出宽度时隐藏超出部分的内容 */
			text-overflow:ellipsis;         /* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
		}
		.user-query dd    {padding-right:10px;line-height: 25px;}
		.user-query dt.width80{width:70px;}
		.user-query dt.autoWidth{width:auto;padding-right:10px;}
	</style>
	<script type="text/javascript">
        var dahname ='<s:text name="dahname"/>';
        var tjhname ='<s:text name="tjhname"/>';
        var winopen = '';
        var timer = '';
        $(function(){
            $('#start_date').datebox('setValue',"<s:property value="start_date"/>");
            $('#end_dta').datebox('setValue',"<s:property value="start_date"/>");
            $('#exam_num').textbox('textbox').css('ime-mode','disabled');
            $('#exam_num').textbox('textbox').focus();
            $('#critical_class_level').combobox({
                url:"getDatadis.action?com_Type=" + "WJZDJ",
                valueField:'id',
                textField:'name',
                editable:false,
                panelHeight:'auto',
                onLoadSuccess : function() {//下拉框默认选择
                    var val = $(this).combobox('getData');
                    $(this).combobox('setValue',val[0].id);
                },
                loadFilter:function(data){
                    data.unshift({id:'0',name:'全部等级'});
                    return data;
                }
            });
            getGrid();


            $('#com_Name').textbox('textbox').bind('click', function() {
                select_com_list(this.value);
            });

            $('#com_Name').textbox('textbox').bind('keyup', function() {
                select_com_list(this.value);
            });

            $('#com_Name').textbox('textbox').bind('blur', function() {
                select_com_list_over();
            });

        });

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
            f_getdept(id);
            f_getBatch(id);
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
        //获取菜单batch
        function f_getBatch(id) {
            $('#batch_ids').combobox({
                url : 'getCompanForBatch.action?company_id='+id,
                editable : true, //不可编辑状态
                cache : false,
                panelHeight : 'auto',//自动高度适合
                valueField : 'id',
                textField : 'batch_name',
                onLoadSuccess : function(data) {
                    $('#batch_ids').combobox('setValue', data[0].id);
                }
            });
        }

        //显示部门
        function f_getdept(company_id) {
            $('#levelss').combobox({
                url : 'getCompanForDept2.action?company_id='+company_id,
                editable : true, //不可编辑状态
                cache : false,
                panelHeight : '300',//自动高度适合
                valueField : 'id',
                multiple:true,
                textField : 'dep_Name',
                onLoadSuccess : function(data) {
                    if((data!=undefined)&&(data.length>0)){
                        $('#levelss').combobox('setValue', data[0].id);
                    }
                },
                filter: function(q, row){
                    var opts = $(this).combobox('options');
                    var text = row[opts.textField];//下拉的对应选项的汉字
                    var pyjp = pinyinUtil.getFirstLetter(text).toLowerCase();
                    if(row[opts.textField].indexOf(q) > -1 || pyjp.indexOf(q.toLowerCase()) > -1){
                        return true;
                    }
                }
            });
        }





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
        function getGrid(){
            var comid=0;
            if($("#com_Name").textbox('getValue') != ''){
                comid = $("#company_id").val();
            }
            var model = {
                exam_num:$('#exam_num').textbox('getValue'),
                exam_name:$('#exam_name').textbox('getValue'),
                start_date:$('#start_date').datebox('getValue'),
                end_dta:$('#end_dta').datebox('getValue'),
                done_flag:$('#done_flag_s').combobox('getValue'),
                critical_class_level:$('#critical_class_level').combobox('getValue'),
                startDone_date:$('#startDone_date').datebox('getValue'),
                endDone_date:$('#endDone_date').datebox('getValue'),
                startCheckDate:$('#startCheckDate').datebox('getValue'),
                endCheckDate:$('#endCheckDate').datebox('getValue'),
                start_create_time:$("#start_create_time").datebox('getValue'),
                end_create_time:$("#end_create_time").datebox('getValue'),
                exam_result:$("#exam_result").textbox('getValue'),
                com_id:comid,
                batch_id:$('#batch_ids').combobox('getValue')
            };
            $("#criticallist").datagrid({
                url: 'criticalList.action',
                queryParams: model,
                pageSize: 15,//每页显示的记录条数，默认为10
                pageList:[15,30,45,60,75],//可以设置每页记录条数的列表
                height: window.screen.height-400,
                columns:[[
                    /*{align:"center",field:"exam_info_id","title":"ID"},
                     {align:'center',field:"arch_num","title":dahname,width:100}, */
                    {field:'ck',checkbox:true },
                    {align:"center",field:"exam_num","title":tjhname,"width":120},
                    {align:"center",field:"user_name","title":"姓名","width":100},
                    {align:"center",field:"sex","title":"性别","width":50},
                    {align:"center",field:"age","title":"年龄","width":50},
                    {align:"center",field:"phone","title":"电话","width":150},
                    {align:'center',field:"done_flag_s","title":"状态","width":70,"styler":f_clolor},
                    {align:"center",field:"parent_critical_class_name","title":"大类","width":100},
                    {align:"center",field:"critical_class_name","title":"子类","width":100},
                    {align:"center",field:"exam_result","title":"危急值结果","width":400},
                    {align:"center",field:"check_date","title":"检查日期","width":150},
                    {align:"center",field:"data_name","title":"等级","width":140},
                    {align:"center",field:"company","title":"单位","width":250},
                    {align:"center",field:"dep_name","title":"科室","width":100},
                    {align:"center",field:"item_name","title":"检查项目","width":150},
                    {align:"center",field:"examination_item_name","title":"检查细项","width":150},
                    {align:"center",field:"disease_name","title":"疾病/阳性","width":350},
                    {align:'center',field:"check_doctor","title":"处理医生","width":100},
                    {align:'center',field:"done_date","title":"处理日期","width":200},
                    {align:"center",field:"note","title":"处理内容","width":200},
                    {align:"center",field:"check_doctor","title":"操作人","width":100},
                    {align:"center",field:"create_time","title":"生成日期","width":150}
                ]],
                onLoadSuccess:function(value){
                    $("#datatotal").val(value.total);
                },
                onDblClickRow:function(index,row){
                    $("#critical_edit").dialog({
                        title:'危急值处理',
                        href:'updateCri.action?id='+row.id,
                    });
                    $("#critical_edit").dialog("open");
                },
                nowrap:true,
                rownumbers:true,
                singleSelect:false,
                collapsible:true,
                pagination: true,
                fitColumns:false,
                //fit:true,
                striped:true,
                toolbar:toolbar
            });
        }
        var toolbar=[ {
            text:'数据导出',
            width:90,
            iconCls:'icon-check',
            handler:function(){
                var comid=0;
                if($("#com_Name").textbox('getValue') != ''){
                    comid = $("#company_id").val();
                }

                window.location.href='criticalUserExportExcel.action?exam_num='+$('#exam_num').textbox('getValue')+
                    '&exam_name='+$('#exam_name').textbox('getValue')+'&start_date='+$('#start_date').datebox('getValue')
                    +'&end_dta='+$('#end_dta').datebox('getValue')+'&done_flag='+$('#done_flag_s').combobox('getValue')
                    +'&critical_class_level='+$('#critical_class_level').combobox('getValue')+'&startDone_date='+$('#startDone_date').datebox('getValue')
                    +'&endDone_date='+$('#endDone_date').datebox('getValue')+'&startCheckDate='+$('#startCheckDate').datebox('getValue')
                    +'&endCheckDate='+$('#endCheckDate').datebox('getValue')+'&start_create_time='+$("#start_create_time").datebox('getValue')
                    +'&end_create_time='+$("#end_create_time").datebox('getValue')+'&com_id='+comid
                    +'&page='+1+'&rows='+10000;
            }
        }, /* {
		text:'新增危急值',
		width:120,
		iconCls:'icon-save',
	    handler:function(){
	    winopen = 	window.open('/critical/addCritical.jsp', "新增危机值", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    winopen.focus();
	    timer = window.setInterval("IfWindowClosed()", 500);
	    }
	}, */
            {
                text:'危急值处理',
                width:120,
                iconCls:'icon-check',
                handler:function(){
                    var chekedrow =  $("#criticallist").datagrid('getChecked');
                    if(chekedrow.length==0){
                        $.messager.alert("提示信息","请选择记录","error");
                        return;
                    }
                    var ids = new Array();
                    var exam_num = chekedrow[0].exam_num;
                    var fal = "Y";
                    for(var i = 0 ; i < chekedrow.length ;i++){
                        if(chekedrow[i].exam_num!=exam_num){
                            fal = "N";
                        }
                        ids.push(chekedrow[i].id);
                    }
                    if(fal=='N'){
                        $.messager.alert("提示信息","请选择同一个人的危急值进行处理","error");
                        return;
                    }
                    $("#batch_critical_edit").dialog({
                        title:'批量处理危急值',
                        href:'updateCri.action?ids='+ids.toString(),
                    });
                    $("#batch_critical_edit").dialog("open");
                }
            },{
                text:'检查结果',
                width:120,
                iconCls:'icon-print',
                handler:function(){
                    var item = $('#criticallist').datagrid('getChecked');
                    if(item.length > 1){
                        $.messager.alert("操作提示","请选择单条查看结果的记录",'info');
                    }else if(item.length = 1){
//		    	$("#dlg-edit").dialog({
//		   				title : '检查结果',
//		   				width : 1000,
//		   				height :550,
//		   				href : 'getNewVisitPlanPageResult.action?exam_num='+item[0].exam_num+'&arch_num='+item[0].arch_num
//		   			});
                        $("#dlg-edit").dialog('open');
                        getFinalSummary(item[0].id);
                        getptGrid(item[0].exam_num);
                        getyxGrid(item[0].exam_num);
                        gethyGrid(item[0].exam_num);
                        getExamDisease(item[0].id);

                    }else{
                        $.messager.alert("操作提示","请选择要查看结果的记录",'error');
                    }

                }
            }

            /*,
            {
                text:'数据导出',
                width:90,
                iconCls:'icon-check',
                handler:function(){

                    window.open("../../ReportServer?reportlet=critical.cpt")
                    //var reportlets = new Array();
                     var sa = {
                        "reportlet" : 'vip6.cpt'
                    };
                    reportlets.push(sa);
                    var printurl = "../../ReportServer";
                    var config = {
                        url : printurl,
                        isPopUp : true,
                        data : {
                            reportlets : reportlets
                        }
                    }
                    FR.doURLPDFPrint(config);
                }
            },
            {
                text:'健康回访',
                width:120,
                iconCls:'icon-check',
                handler:function(){
                    window.parent.addPanel_other('健康回访计划','getCrmVisitPlanListPage.action');
                    //addPanel_other("我的健康回访","getNewVisitPlanPage.action","themes/default/images/blank.gif","1")
                }
            }, */
        ];
        function f_clolor(value,row,index){
            if(row.done_flag_s == '未处理'){
                return 'color:red';
            }
        }

        //-----------------------检查结果--------------------------------


        function f_show_picture(val,row){
            if(row.exam_result == 'image_path'){
                return '<a href="javascript:void(0)" onclick = "show_picture('+row.item_id+')">查看图片</a>';
            }else{
                return row.exam_result.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
            }
        }
        function f_color_pt(value,row,index){
            if(row.health_level == 'Y'){
                return 'color:#F00;';
            }else if(row.health_level == 'W'){
                return 'color:#FF9B00;';
            }
            if(row.item_id == '0'){
                return 'background:#FEEAA8;color:#ff5100;';
            }
        }

        function f_result_pt(value,row,index){
            if(row.item_id == '0'){
                return row.exam_result.replace(/\n/g,'</br>');
            }else{
                return row.exam_result;
            }
        }

        function f_color_pt1(value,row,index){
            if(row.health_level == 'Y'){
                return 'color:#F00;';
            }else if(row.health_level == 'W'){
                return 'color:#FF9B00;';
            }
        }
        function f_color_hy(value,row,index){
            if(row.health_level == 1 || row.health_level == 2 || row.health_level == 3){
                return 'color:#F00;';
            }else if(row.health_level == 4){
                return 'color:#FF9B00;';
            }
        }
        function f_color_yx(val,row){
            if(row.item_name == '检查结论'){
                return 'background:#FEEAA8;color:#ff5100;';
            }
        }
        //获取普通科室检查结果
        function getptGrid(exam_num){
            var model={"exam_num":exam_num};
            $("#pt_itemResultList").datagrid({
                url:'getPtDepResultList.action',
                dataType: 'json',
                queryParams:model,
                rownumbers:false,
                columns:[[
                    {align:'center',field:'dep_name',title:'收费项目',width:10},
                    {align:'center',field:"exam_doctor","title":"检查医生","width":10},
                    {align:'center',field:"exam_date","title":"检查时间","width":15},
                    {align:'center',field:'item_name',title:'检查项目',width:15,"styler":f_color_pt},
                    {align:'center',field:'exam_result',title:'检查结果',width:25,"styler":f_color_pt,"formatter":f_result_pt}
                ]],
                onLoadSuccess:function(value){
                    MergeCells('pt_itemResultList', 'dep_name,exam_doctor,exam_date');
                },
                singleSelect:true,
                collapsible:true,
                pagination: false,
                fitColumns:true,
                striped:true,
                fit:true,
                nowrap:false
            });
        }


        function getyxGrid(exam_num){
            var model={"exam_num":exam_num};
            $("#yx_itemResultList").datagrid({
                url:'getYxDepResultList.action',
                dataType: 'json',
                queryParams:model,
                rownumbers:false,
                columns:[[
                    {align:'center',field:'dep_name',title:'收费项目',width:10},
                    {align:'center',field:"exam_doctor","title":"检查医生","width":10},
                    {align:'center',field:"exam_date","title":"检查时间","width":15},
                    {align:'center',field:'item_name',title:'检查项目',width:20,"styler":f_color_yx},
                    {align:'',field:'exam_result',title:'检查结果',width:30,"formatter":f_show_picture,"styler":f_color_yx}
                ]],
                onLoadSuccess:function(value){
                    MergeCells('yx_itemResultList', 'dep_name,exam_doctor,exam_date');
                },
                singleSelect:true,
                collapsible:true,
                pagination: false,
                fitColumns:true,
                fit:true,
                striped:true,
                nowrap:false
            });
        }


        function gethyGrid(exam_num){
            var model={"exam_num":exam_num};
            $("#hy_itemResultList").datagrid({
                url:'getHyDepResultList.action',
                dataType: 'json',
                queryParams:model,
                rownumbers:false,
                columns:[[
                    {align:'center',field:'dep_name',title:'收费项目',width:20},
                    {align:'center',field:"exam_doctor","title":"检查医生","width":20},
                    {align:'center',field:"exam_date","title":"检查时间","width":25},
                    {align:'center',field:'item_name',title:'检查项目',width:20,"styler":f_color_hy},
                    {align:'center',field:'exam_result',title:'检查结果',width:20,"styler":f_color_hy},
                    {align:'center',field:'bs',title:'标识',width:10,"formatter":f_bs,"styler":f_color_hy},
                    {align:'center',field:'item_unit',title:'单位',width:10},
                    {align:'center',field:'ref_value',title:'参考值',width:20},
                ]],
                onLoadSuccess:function(value){
                    MergeCells('hy_itemResultList', 'dep_name,exam_doctor,exam_date');
                },
                singleSelect:true,
                collapsible:true,
                pagination: false,
                fitColumns:true,
                fit:true,
                striped:true,
                nowrap:false
            });
        }
        function f_bs(val,row){
            if(row.health_level == 1){
                return '↑';
            }else if(row.health_level == 2){
                return '↓';
            }else{
                return '';
            }
        }

        //总检结论
        function getFinalSummary(eid){
            $.ajax({
                url:'getFinalSummaryResult.action',
                data:{"examinfo_id":eid},
                type:'post',
                success:function(data){
                    if(data != 'null'){
                        var obj=eval("("+data+")");
                        $("#zongjianjielun").val(obj.final_exam_result);
                    }
                }
            });
        }


        function getExamDisease(eid){
            var model={"examinfo_id":eid};
            $("#exam_disease").datagrid({
                url:'getExamDiseaseResult.action',
                dataType: 'json',
                queryParams:model,
                rownumbers:true,
                pageSize: 15,//每页显示的记录条数，默认为10
                pageList:[15,30,45,60,75],//可以设置每页记录条数的列表
                columns:[[
                    {align:'center',field:'disease_name',title:'阳性/疾病发现',width:10},
                    {align:'center',field:'suggest',title:'阳性/疾病建议',width:20}
                ]],
                onLoadSuccess:function(value){
                    $("#datatotal").val(value.total);
                },
                singleSelect:true,
                collapsible:true,
                pagination: false,
                fitColumns:true,
                fit:true,
                striped:true,
                nowrap:false
            });
        }
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



	</script>
	<style>
	</style>
</head>
<body style="height: 100%">
<fieldset style="margin:5px;">
	<legend><strong>信息检索</strong></legend>
	<div class="user-query">
		<%-- <div id="tb" style="padding-right:20px;padding-left:30px;">
            &nbsp;&nbsp;&nbsp;处理标识:<select id="done_flag_s" name = 'done_flag_s'class="easyui-combobox" style="height:24px;width:154px;">
                                        <option value="0" >未处理</option>
                                        <option value="1">已处理</option>
                                        <option value="">所有</option>
                                    </select>
            &nbsp;&nbsp;&nbsp; 科室:<select id="dep_category" name = 'dep_category' class="easyui-combobox" style="height:24px;width:114px;">
                                          <option value="17" >普通科室</option>
                                          <option value="131">化验检查</option>
                                          <option value="21">影像检查</option>
                                          <option value="" selected="selected">所有</option>
                                    </select>
            &nbsp;&nbsp;&nbsp;检查时间:<input id="startCheckDate" name="startCheckDate" class="easyui-datebox"  data-options="prompt:'起始时间'" />
            &nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;<input id="endCheckDate" name="endCheckDate" class="easyui-datebox"  data-options="prompt:'时间'" />
            &nbsp;&nbsp;&nbsp; 体检号:  <input id = "exam_num" name = "exam_num" class="easyui-textbox" style="height:24px;width:137px;"/>
        </div>
        <div style="padding-right:20px;padding-left:30px;padding-top:5px;">
            &nbsp;&nbsp;&nbsp;危急值生成标识:<select id="data_source" name = 'data_source'class="easyui-combobox" style="height:24px;width:114px;">
                                          <option value="1" >手动添加</option>
                                          <option value="0">系统生成</option>
                                          <option value="" selected="selected">所有</option>
                                    </select>
            &nbsp;&nbsp;&nbsp; 处理医生:  <input id = "check_doctor" name = "check_doctor" class="easyui-textbox" style="height:24px;width:82px;"/>
            &nbsp;&nbsp;&nbsp;处理时间:<input id="startDone_date" name="startDone_date" class="easyui-datebox"  data-options="prompt:'起始时间'" />
            &nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;<input id="endDone_date" name="endDone_date" class="easyui-datebox"  data-options="prompt:'时间'" />
            &nbsp;&nbsp;&nbsp; 检查结果:  <input id = "exam_result" name = "exam_result" class="easyui-textbox" style="height:24px;width:120px;"/>
            &nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>
            &nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
        </div> --%>
		<dl>
			<dt style="width: 60px">体检号</dt>
			<dd><input id = "exam_num" name = "exam_num" class="easyui-textbox" style="height:25px;width:100px"/></dd>
			<dt  style="width:40px">姓名</dt>
			<dd><input id = "exam_name" name ="exam_name" class="easyui-textbox" style="height:25px;width:100px"/></dd>
			<dt  style="width:70px">体检日期</dt>
			<dd><input id = "start_date" name = "start_date"  class="easyui-datebox" style="height:25px;width:100px"/></dd>
			<dt   style="width:30px">至</dt>
			<dd><input id = "end_dta" name = "end_dta"  class="easyui-datebox" style="height:25px;width:100px"/></dd>
			<dt style="width:60px">处理状态</dt>
			<dd>
				<select id="done_flag_s" name = 'done_flag_s'class="easyui-combobox" data-options="panelHeight:'auto'" style="height:25px;width:100px;">
					<option value=3>全部</option>
					<option value=0>未通知</option>
					<option value=1>已通知</option>
					<option value=2>未联系上</option>
				</select>
			</dd>
			<dt style="width:30px">等级</dt>
			<dd><input id = "critical_class_level" name="critical_class_level"  style="height:25px;width:100px;"  /></dd>

			<dt style="height:26px;width:70px;">选择单位:</dt>
			<dd><input type="hidden" id="company_id" /><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:205px;"/>
				<div id="com_name_list_div" style="display:none;margin-left:940px;"
					 onmouseover="select_com_list_mover()"
					 onmouseout="select_com_list_amover()">
				</div>
			</dd>
		</dl>
		</br>
		<dl>
			<!--  <dt style="width:70px">处理医生</dt>
             <dd><input id = "check_doctor" name = "check_doctor" class="easyui-textbox" style="height:24px;width:128px;"/></dd> -->
			<dt style="width:60px">处理时间</dt>
			<dd><input id="startDone_date" name="startDone_date" class="easyui-datebox" style="width:100px;height: 25px"  data-options="prompt:'起始时间'" /></dd>
			<dt style="width:40px">至</dt>
			<dd>
				<input id="endDone_date" name="endDone_date" class="easyui-datebox"  style="width:100px;height: 25px"  data-options="prompt:'时间'" />
			</dd>
			<dt style="width:70px">
				检查时间
			</dt>
			<dd>
				<input id="startCheckDate" type="text" name="startCheckDate" class="easyui-datebox"  style="width:100px;height: 25px" />
			</dd>
			<dt style="width:30px">至</dt>
			<dd>
				<input id="endCheckDate" type="text" name="endCheckDate"  class="easyui-datebox"  style="width:100px;height: 25px"  />
			</dd>
			<dt style="width:60px">
				生成时间
			</dt>
			<dd>
				<input id="start_create_time" type="text" name="startCheckDate" class="easyui-datebox"  style="width:100px;height: 25px" />
			</dd>
			<dt style="width:30px">至</dt>
			<dd>
				<input id="end_create_time" type="text" name="endCheckDate"  class="easyui-datebox"  style="width:100px;height: 25px"  />
			</dd>
			<dt style="height:26px;width:70px;">任务</dt>
			<dd>
				<select class="easyui-combobox" id="batch_ids" name="batch_ids"	data-options="height:26,width:210,panelHeight:'auto'"></select>
			</dd>
		</dl>
		<br/>
		<dl>
			<dt style="width:60px">
				检查结果
			</dt>
			<dd>
				<input id = "exam_result" name = "exam_result" class="easyui-textbox" style="height:25px;width:100px;"/>
			</dd>
			<dd><a href="javascript:getGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin:5px;height:85%;">
	<legend><strong>信息列表</strong></legend>
	<div id="criticallist"></div>
</fieldset>
<div id="critical_edit" class="easyui-dialog" data-options="width:1200,height:550,closed: true,cache: false,modal: true,top:50" ></div>
<div id="batch_critical_edit" class="easyui-dialog" data-options="width:700,height:550,closed: true,cache: false,modal: true,top:50" ></div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 1000,height: 600,closed: true,cache: false,modal: true,top:50">

	<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
		<div title="总检结论与建议" style="padding:5px;">
			<div class="easyui-layout" style="height:500px;">
				<div data-options="region:'west'" style="width:223px;">
					<div class="easyui-panel" title="总检结论" data-options="">
						<textarea readonly="readonly" style="width: 215px;resize:none;border: 0px;height: 461px;font-size:14px;" id="zongjianjielun"></textarea>
					</div>
				</div>
				<div data-options="region:'center'">
					<table id="exam_disease">
					</table>
				</div>
			</div>
		</div>
		<div title="普通科" style="padding:5px;" >
			<table id="pt_itemResultList"></table>
		</div>
		<div title="影像科" style="padding:5px;" >
			<table id="yx_itemResultList"></table>
		</div>
		<div title="检验科" style="padding:5px;" >
			<table id="hy_itemResultList"></table>
		</div>

	</div>


</div>
</body>
</html>