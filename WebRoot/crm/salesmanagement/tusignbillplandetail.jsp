<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.min.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	 $('#detailtustarttime').datebox({    
		 prompt:'请选择开始查询日期'
		}); 
	 $('#detailtuendtime').datebox({    
		 prompt:'请选择结束查询日期' 
		}); 
	 gettuSignBillPlanDetailList();
});
function gettuSignBillPlanDetailList(){
	 drawGraph($('#creater_detail').val());
}
function drawGraph(creater) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('ls_graphdetail'));
	
	$.ajax({
		url : 'tuSignBillPLanList.action?creater=' + creater
				+ '&signstartTime=' + $('#detailtustarttime').datebox('getValue')+'&signendTime=' + $('#detailtuendtime').datebox('getValue'),
		type : 'post',
		success : function(data) {
			if(data==''){
					$('#ls_graphdetail').html('<div style="font-color:red"><h3>无信息</h3></div>')
			}else{
				var dataList = eval("("+data+")");
				
				if (dataList == 'null' || data.length <= 0) {
					return;
				}
							
				var weipaichuzhuangdancount = new Array();
				var kaishigenzongcount = new Array();
				var zhizuofangancount = new Array();
				var shengchenghetongcount = new Array();
				var yibeidancount = new Array();
				var tijianbinghuikuancount = new Array();
				var yipaichuzhuangdancount = new Array();
				var gusuanzongjine = new Array();
				var gusuanzongrenshu = new Array();
				var counts = new Array();
				var create_time=new Array();
				for(var i=dataList.length-1; i >= 0; i--){
					weipaichuzhuangdancount.push(dataList[i].weipaichuzhuangdancount);
					kaishigenzongcount.push(dataList[i].kaishigenzongcount);
					zhizuofangancount.push(dataList[i].zhizuofangancount);
					shengchenghetongcount.push(dataList[i].shengchenghetongcount);
					yibeidancount.push(dataList[i].yibeidancount);
					tijianbinghuikuancount.push(dataList[i].tijianbinghuikuancount);
					yipaichuzhuangdancount.push(dataList[i].yipaichuzhuangdancount);
					gusuanzongjine.push(dataList[i].gusuanzongjine);
					gusuanzongrenshu.push(dataList[i].gusuanzongrenshu);
					counts.push(dataList[i].counts);
					create_time.push(dataList[i].create_time);
				}
				var seriesoption=[];
				var optionweipaichuzhuangdancount={
						name : '未排除撞单',
						type : 'line',
						data : weipaichuzhuangdancount 
				}
				seriesoption.push(optionweipaichuzhuangdancount);
				var optionkaishigenzongcount={
						name : '开始跟踪',
						type : 'line',
						data : kaishigenzongcount 
				}
				seriesoption.push(optionkaishigenzongcount);
				var optionzhizuofangancount={
						name : '制作方案',
						type : 'line',
						data : zhizuofangancount 
				}
				seriesoption.push(optionzhizuofangancount);
				var optionshengchenghetongcount={
						name : '生成合同',
						type : 'line',
						data : shengchenghetongcount 
				}
				seriesoption.push(optionshengchenghetongcount);
				var optionyibeidancount={
						name : '已备单',
						type : 'line',
						data : yibeidancount 
				}
				seriesoption.push(optionyibeidancount);
				var optiontijianbinghuikuancount={
						name : '体检并回款',
						type : 'line',
						data : tijianbinghuikuancount 
				}
				seriesoption.push(optiontijianbinghuikuancount);
				var optionyipaichuzhuangdancount={
						name : '已排除撞单',
						type : 'line',
						data : yipaichuzhuangdancount 
				}
				seriesoption.push(optionyipaichuzhuangdancount);
				/* var optioncounts={
						name : '签单计划个数',
						type : 'line',
						data : counts 
				}
				seriesoption.push(optioncounts)); */
				/*
				var optiongusuanzongjine={
						name : '估算总金额',
						type : 'line',
						data : gusuanzongjine 
				}
				seriesoption.push(optiongusuanzongjine);
				var optiongusuanzongrenshu={
						name : '估算总人数',
						type : 'line',
						data : gusuanzongrenshu 
				}
				 */
				option = {
					tooltip : {
						trigger : 'axis'
					}, 
				    grid : {
				        left: 40,
				        right: 40
				    },
					xAxis : {
						type : 'category',
						boundaryGap : false,
						data : create_time
					},
					yAxis : {
						type : 'value',
						axisLabel : {
							formatter : '{value} '
						}
					},
					series : seriesoption
				};
				
				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);
			}
		}
	});

  }
</script>
<input id="creater_detail" value="<s:property value='creater'/>" hidden="true"/>
<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:80px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="width:50px;">开始日期</dd>
					<dd>
						<input   id="detailtustarttime"    style="height:23px;width:80px;" />
					</dd>
					<dd style="width:50px;">结束日期</dd>
					<dd>
						<input  id="detailtuendtime"    style="height:23px;width:80px;"/>
					</dd>
					
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:gettuSignBillPlanDetailList();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
</div>
 <div  data-options="region:'center'">
<fieldset id="tu" style="margin:5px;padding-right:0;">
<legend><strong>签单计划曲线图</strong></legend>
<div id="ls_graphdetail" style="margin-left: 5px;margin-right: 5px;height:336px"></div>
</fieldset>
</div>
</div>
