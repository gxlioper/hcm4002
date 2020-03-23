$(document).ready(function () {
	getExamItem($("#exam_id").val());
	getoccuhazardfactorsGrid();
	if($("#picture_path").val() != ''){
		document.getElementById("exampic").src="getdjtexamPhoto.action?others="+$("#picture_path").val()+"&"+new Date().getTime();
	}
});

var obj;
//查询检查项目信息
function getExamItem(exam_id){
	$.ajax({
		url:'zybInquisitionList.action?examinfo_id='+exam_id,
		type:'post',
		success:function(data){
			if(data == 'null' || data == '[]'){
				
			}else{
				obj=eval("("+data+")");
				var str='';
				for(i=0;i<obj.length;i++){
					if(obj[i].careerItemList.length == 0){
						str+='<div class="B-ultrasound"><p>'+obj[i].name+'：</p>'
						+'<div class="in_conclusion"><textarea id="result_'+obj[i].id+'" cols="100" rows="5">'+obj[i].temp_content+'</textarea> '+
						' </div></div>';
					}else{
						str+='<div class="B-ultrasound"><p>'+obj[i].name+'：</p><div class="user-query">';
						for(j=0;j<obj[i].careerItemList.length;j++){
							str +='<dl><dt>'+obj[i].careerItemList[j].item_name+'</dt>'
								+'<dd><input type="text" maxlength="20" id="career_'+obj[i].careerItemList[j].item_code+'"'
								if(obj[i].careerItemList[j].result==""){
									str +='  value="-"  class="textinput"/></dd>'; 
								} else {
									str +='  value="'+obj[i].careerItemList[j].result+'"  class="textinput"/></dd>' 
								}
							j++;
							if(j<obj[i].careerItemList.length){
								str +='<dt class="label_area">'+obj[i].careerItemList[j].item_name+'</dt>'
								+'<dd><input type="text" maxlength="20" id="career_'+obj[i].careerItemList[j].item_code+'"'
								if(obj[i].careerItemList[j].result==""){
									str +='value="-"  class="textinput"/></dd>';	
								} else {
									str +='value="'+obj[i].careerItemList[j].result+'" class="textinput"/></dd>';
								}
							}
							j++;
							if(j<obj[i].careerItemList.length){
								str +='<dt class="label_area">'+obj[i].careerItemList[j].item_name+'</dt>'
								+'<dd><input type="text" maxlength="20" id="career_'+obj[i].careerItemList[j].item_code+'" '
								if(obj[i].careerItemList[j].result==""){
									str +='value="-"   class="textinput"/></dd></dl>';	
								} else {
									str +='value="'+obj[i].careerItemList[j].result+'"  class="textinput"/></dd></dl>'
								}
								
							}
						}
						str +='</div></div>';
					}
				}
				$("#yingxiangkeshi_jl").html(str);
			}
		}
	});
}

//保存问诊
function saveInquisitionItem(){
	if(obj == undefined || obj == null){
		$.messager.alert("操作提示",'页面加载有问题,请重新进入!', "error");
	}else{
		var inquisitionResult = new Array();
		for(i=0;i<obj.length;i++){
			if(obj[i].careerItemList.length == 0){
				inquisitionResult.push({
					'examinfo_id':$("#exam_id").val(),
					'item_id':obj[i].id,
					'item_type':'1',
					'result':$("#result_"+obj[i].id).val()
				});
			}else{
				for(j=0;j<obj[i].careerItemList.length;j++){
					if($("#career_"+obj[i].careerItemList[j].item_code).val() != ''){
						inquisitionResult.push({
							'examinfo_id':$("#exam_id").val(),
							'item_id':obj[i].careerItemList[j].item_code,
							'item_type':'2',
							'result':$("#career_"+obj[i].careerItemList[j].item_code).val()
						});
					}
				}
			}
		}
		
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		$.ajax({
			url:'saveZybInquisitionList.action',
			type:'post',
			data:{'examinfo_id':$("#exam_id").val(),'inquisitionResults': JSON.stringify(inquisitionResult)},
			success:function(data){
				$(".loading_div").remove();
				$.messager.alert("操作提示",data, "info",close_page);
			}
		});
	}
}

//FF中需要修改配置window.close方法才能有作用，为了不需要用户去手动修改，所以用一个空白页面显示并且让后退按钮失效
//Opera浏览器旧版本(小于等于12.16版本)内核是Presto，window.close方法有作用，但页面不是关闭只是跳转到空白页面，后退按钮有效，也需要特殊处理
function close_page(){
	var _parentWin =  window.opener;
	var userAgent = navigator.userAgent;
	window.opener = null;
	window.open('', '_self');
	window.close();
	_parentWin.shuaxing();
}

function getoccuhazardfactorsGrid() {
		var model = {
			"exam_num" : $("#tjh").html()
		};
		$("#zywhysset").datagrid({
			url : 'examoccuhazardfactorslist.action',
			dataType : 'json',
			queryParams : model,
			toolbar : '#toolbar',
			rownumbers : false,
			pageSize : 5,// 每页显示的记录条数，默认为10
			pageList : [ 5, 10, 20 ],// 可以设置每页记录条数的列表
			columns : [ [ {
				align : 'center',
				field : 'hazard_name',
				title : '职业危害因素',
				width : 30
			}, {
				align : 'center',
				field : 'occuphyexaclass_name',
				title : '体检类别',
				width : 15
			}, {
				align : 'center',
				field : 'hazard_year',
				title : '危害年限',
				width : 15
			}] ],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
			},
			singleSelect : false,
			collapsible : true,
			pagination : false,
			fitColumns : true,
			striped : true,
			fit:true
		});
	}
//显示职业史
function fn_show_zhiyeshi(){
	//getcusthisGrid();
	var url='getzhiyeshiPage.action?exam_num='+$('#tjh').text();
	newwin = window.open("", "职业史", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newwin.moveTo(0,0);
	newwin.resizeTo(screen.width,screen.height-30);
	newwin.location = url;
	newwin.focus();
}
//////////////////////////////职业病历史处理////////////////////////////////////////
function getcusthisGrid(){	
		 var model={
				 "exam_num":$("#tjh").val()
		 };
	     $("#exam_zhiyeshi").datagrid({
		 url:'zybCustomerHislist.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 5,//每页显示的记录条数，默认为10 
	     pageList:[5,10,20],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'company',title:'工作单位',width:18},	
		    {align:'center',field:'workshop',title:'车间部门',width:20},
		 	{align:'center',field:'worktype',title:'工种',width:20},
		 	{align:'center',field:'measure',title:'防护措施',width:30},
		 	{align:'center',field:'harmname',title:'有害因素名称',width:20},
		 	{align:'center',field:'concentrations',title:'有害因素浓度',width:20},
		 	{align:'center',field:'isradiation',title:'是否放射',width:15},
		 	{align:'center',field:'startdate',title:'开始时间',width:15},		 	
		 	{align:'center',field:'enddate',title:'结束时间',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    striped:true
	});
}

