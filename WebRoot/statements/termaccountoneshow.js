$(document).ready(function () {
	gettermaccountlistGrid();
	getteamtotalinfo();
});

	 /**
	  * 
	  */
function gettermaccountlistGrid() {
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
			+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);

	var model = {
		"acc_num" : $("#acc_num").val()
	};
	$("#teamaccountwaylist").datagrid({
		url : 'teamAccountExamwayListShow.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : true,
		showFooter:true,
		pageSize : 100000,// 每页显示的记录条数，默认为10
		pageList : [ 10, 20, 45, 60, 75 ],// 可以设置每页记录条数的列表
		columns : [ [ 
		              {align : 'center',field : 'acc_num',title : '结算单号',	width : 20}, 
		              {align : 'center',field : 'paywayname',title : '支付方式',width : 25}, 
		              {align : 'center',field : 'charges',title : '支付金额',width : 15}, 
		              {align : 'center',field : 'in_date',title : '生成日期',width : 25}
		              ] ],
		onLoadSuccess : function(value) {
			//$("#datatotal").val(value.total);
			
			$(".loading_div").remove();
		},
		singleSelect : false,
		collapsible : true,
		pagination : false,
		fitColumns : true,
		striped : true
	});
}
  
 function windowclosesss(){
		window.close();
 }
 
  function getteamtotalinfo() {
		$.post(
			"teamItemCount.action",
			{
				"acc_num" : $("#acc_num").val()
			},
			function(jsonPost) {
			    var customertotal = eval(jsonPost);
			    //document.getElementById("zrs").firstChild.nodeValue = customertotal.totalcustume;
				document.getElementById("zrc").firstChild.nodeValue = customertotal.totalexam;
			    document.getElementById("ysje").firstChild.nodeValue = customertotal.totalAmt;
				document.getElementById("ssje").firstChild.nodeValue = customertotal.termAmt;
				document.getElementById("yhje").firstChild.nodeValue = customertotal.personAmt;
			}, "json");
	}
