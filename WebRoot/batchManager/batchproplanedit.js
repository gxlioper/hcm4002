$(document).ready(function() {
	getbatchprolistGrid();	
	});

var lastIndex; 
function getbatchprolistGrid(){
	var model={"batch_id":$("#batch_id").val()};
    $("#batchproplanlist").datagrid({
	 url:'getbatchproList.action',
	 dataType: 'json',
	 queryParams:model,
	 rownumbers:false,
    pageSize: 15,//每页显示的记录条数，默认为10 
    pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	 columns:[[
	    {align:'center',field:'plandate',title:'计划日期',width : 100},
	 	{align:'center',field:'per_num',title:'计划人数',width : 100}
	 	]],	    	
   	onLoadSuccess:function(value){
   		$("#datatotal").val(value.total);
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


function paiqistart(){
	if($("#startdate").datebox('getValue')==''){
		alert("开始日期为空！");
	}else if($("#enddate").datebox('getValue')==''){
		alert("结束日期为空！");
	}else if($("#daynum").val()==''){
		alert("日体检人数为空！");
	}else if (!(isSZ($("#daynum").val()))) {
		alert('日体检人数格式错误！');
	}else {
		var datess = DateDiff($("#startdate").datebox('getValue'),$("#enddate").datebox('getValue'));
		if(Number(datess)*Number($("#daynum").val())<Number($("#exam_number").val())){
			alert("体检日期内容纳不了这些人！");
		}else{
			var daynums=Number($("#daynum").val());
			var countnums=Number($("#exam_number").val());
			$('#batchproplanlist').datagrid('loadData', { total: 0, rows: [] });//清空下方DateGrid 
			var ab = $("#startdate").datebox('getValue').split("-");
			var ae = $("#enddate").datebox('getValue').split("-");
			var db = new Date();
			db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
			var de = new Date();
			de.setUTCFullYear(ae[0], ae[1]-1, ae[2]);
			var unixDb=db.getTime();
			var unixDe=de.getTime();
			var confflag=0;
			for(var k=unixDb;k<=unixDe; (k+unixDb+24*60*60*1000)){	
			    var plandate=(new Date(parseInt(k))).format();	
			    var per_num=daynums;
			    if(countnums<daynums)
			    {
			    	per_num= countnums;
			    }			    
			    insertpro(plandate,per_num);
			    countnums=countnums-daynums;
			    if(countnums<0){
			    	break;
			    }
			    k=k+24*60*60*1000;
			}
			
		}
	}
}

/**
 * 获取两个日期之间的所有天
 */
Date.prototype.format=function (){
	var s='';
	s+=this.getFullYear()+'-';// 获取年份。
	s+=(this.getMonth()+1)+"-";         // 获取月份。
	s+= this.getDate();                 // 获取日。
	return(s);                          // 返回日期。
	};
	
/**
 * 增加分组项目
 */
var oplandate,oper_num
function insertpro(oplandate,oper_num) {
	    if(Number(oper_num)>0){
		  $('#batchproplanlist').datagrid("appendRow", {
			 plandate : oplandate,
			 per_num : oper_num
		  });
	    }
}

/**
 * 计算两个日期之间的天数
 * @param sDate1
 * @param sDate2
 * @returns
 */
function  DateDiff(sDate1,  sDate2){   //sDate1和sDate2是2006-12-18格式 
	var sDate = new Date(sDate1);
	var eDate = new Date(sDate2);
	var fen = ((eDate.getTime()-sDate.getTime())/1000)/60;
	var distance = parseInt(fen/(24*60))+1;
    return  distance;
}   

	/**
	 * 保存修改
	 */
	function paiqiadd() {	
			
			var itemrows = $('#batchproplanlist').datagrid('getRows');  
		    var itementities = "";
		    
		    if (itemrows.length<=0) {
		    	alert("请先排期再保存数据！");
		    }else{
		    	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		   	    $("body").prepend(str);
		    for(i = 0;i < itemrows.length;i++)  
		    {		  
		    	itementities = itementities  + JSON.stringify(itemrows[i]);    
		    } 
			$.ajax({
				url : 'batchproplaneditdo.action',
				data : {
							batch_id : $("#batch_id").val(),							
							itementities: itementities
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								$.messager.alert("操作提示", text);
								$('#dlg-editpro').dialog('close');
								getbatchproGrid();
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

