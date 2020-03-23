//载入图表
var chartOutChar=null;
var myChart=null;
var peilegend=null;
var peigradient4=null;
var peigradient=null;
var peibar1=null;
var peimultiplexaxis=null;
var peiannual_personnel_growth=null;
var peisnnual_unit_growth=null;
var peistatistics_personnel_times=null;
var peicount_number_checks=null;
var peiage_group_disease=null;
var peisql_conn_count=null;
var peiflow_sql_conn_count=null;  
var peiexam_gride_statistics=null;
$(function () {
	showtest1();  
	showtest2();
	showtest3();
	showtest4();
	showtest5();
	showtest6();
	showtest7();
	showtest8();
	showtest9();
	showtest10();
	showtest11();
	showtest12();
	showtest13();
	showtest14();
	showtest15();
});

//-------------------------------当日检查情况统计----------------------------------------------------
var option1 = {
		 title: {
		      text: '当日检查情况统计',
		      subtext: '单位:(个)',
		      left: 'left'
		    },
         tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                x : 'center',
                y : 'bottom'
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel']
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series : [
                {
                    name:'收费项目个数',
                    type:'pie',
                    radius : [30, 90],
                    center : ['45%', '50%'],
                    roseType : 'area',
                    data:[
                        {value:0, name:'已检'},
                        {value:0, name:'未检'},
                        {value:0, name:'弃检'},
                        {value:0, name:'延期'}
                    ]
                }
            ]
        };

function showtest1(){
	 chartOutChar = echarts.init(document.getElementById('showChart'));
	 chartOutChar.setOption(option1);
	 searchtotal1();
	 window.setInterval(function() {
		 searchtotal1();
		 },10000)	
}

function searchtotal1(){
	 $.ajax({
			url:'inspection_statistics_today.action',//加载已排班信息
			type: "post", 
			  success: function(data){
				  if(data.length>2){
				 var obj=eval("("+data+")");			
				 option1.series[0].data=obj;
				 chartOutChar.setOption(option1);
				  }
			    } 
			});
}

//-------------------------当日实时流量----------------------------------------------------------
var option2 = {
		title: {
		      text: '实时流量统计',
		      subtext: '',
		      left: 'left'
		},
	    tooltip: {
	        formatter: '{a} <br/>{b} : {c}%'
	    },
	    toolbox: {
	        feature: {
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    series: [
	        {
	            name: '业务指标',
	            type: 'gauge',
	            center: ['45%', '60%'],
	            detail: {formatter: '{value}%'},
	            data: [{value: 0, name: '业务量'}]
	        }
	    ]
	};

function showtest2(){	
	myChart = echarts.init(document.getElementById("container"));
	myChart.setOption(option2, true);
	searchtotal2();
	window.setInterval(function() {		
		searchtotal2();
		 },300000)	
	
	if (option2 && typeof option2 === "object") {
	    myChart.setOption(option2, true);
	}
}
function searchtotal2(){
	$.ajax({
		url:'real_time_traffic_today.action',
		type: "post", 
		  success: function(data){
			  if(data.length>2){
			 var obj=eval("("+data+")");			
			 option2.series[0].data[0].value=obj.value;
			 myChart.setOption(option2, true);
			  }
		    } 
		});
}

//---------------------------------当日体检状态统计--------------------------------------------------
var option3 = {
	    title: {
	        text: '当日体检状态统计',
	        subtext: '单位:(人数)',
	        left: 'left'
	    },
	    tooltip: {
	        trigger: 'item',
	        formatter: '{a} <br/>{b} : {c} ({d}%)'
	    },
	    legend: {
	        type: 'scroll',
	        orient: 'vertical',
	        right: 10,
	        top: 20,
	        bottom: 20,
	        data: ['预约','登记','检查','总检'],
	        selected: ['预约','登记','检查','总检']
	    },
	    series: [
	        {
	            name: '名称',
	            type: 'pie',
	            radius: '40%',
	            center: ['45%', '60%'],
	            emphasis: {
	                itemStyle: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            },
	            data:[
	                    {value:0, name:'预约'},
	                    {value:0, name:'登记'},
	                    {value:0, name:'检查'},
	                    {value:0, name:'总检'}
	                ]	        
	        }
	    ]
	};


function showtest3(){
	 peilegend = echarts.init(document.getElementById("showtotla"));
	 searchtotal3();	 
	 window.setInterval(function() {
		 searchtotal3();
		 },1000)	
}

function searchtotal3(){
	 $.ajax({
			url:'examination_status_statistics.action',//
			type: "post", 
			  success: function(data){
				  if(data.length>2){
				 var obj=eval("("+data+")");			
				 option3.series[0].data=obj;
				 var legendData = [];
				 var selected = {};
				 for (var i = 0; i < obj.length; i++) {
					 legendData.push(obj[i].name); 
					 selected[obj[i].name]=true;
				 }
				 
				 option3.legend.data=legendData;
				 option3.legend.selected=selected;
				 peilegend.setOption(option3, true);
				  }
			    } 
			});
}

//---------------------------当日体检人员组成--------------------------------------------------------
var option4= {
		   title: {
		        text: '当日体检人员组成',
				subtext: '单位:(个)',
				left: 'left'
				},
		    color: ['#3398DB'],
		    tooltip: {
		        trigger: 'axis',
		        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
		            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '1%',
		        right: '1%',
		        bottom: '1%',
		        containLabel: true
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: ['体检人员'],
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value'
		        }
		    ],
		    series: [
		        {
		            name: '直接访问',
		            type: 'bar',
		            barWidth: '20%',
		            data: [0]
		        }
		    ]
		};

function showtest4(){		
	peigradient4 = echarts.init(document.getElementById("showgradient4"));
	searchtotal4();
	 window.setInterval(function() {
		 searchtotal4();
		 },10000)	
	}

function searchtotal4(){
	 $.ajax({
			url:'exam_info_Composition_today.action',
			type: "post", 
			  success: function(data){
				  if(data.length>2){
				 var obj=eval("("+data+")");
				 var namelist=[];
				 var valuelist=[];					
				 for (var i = 0; i < obj.length; i++) {
					 namelist.push(obj[i].name); 
					 valuelist.push(obj[i].value); 
				 }
				 option4.xAxis[0].data=namelist;
				 option4.series[0].data=valuelist;
				 peigradient4.setOption(option4,true);
				  }
			    } 
			});
}

//-----------------------------------------------------------------------------------
var option5= {
		title: {
		      text: '当日体检类型组成',
		      subtext: '单位:(个)',
		      left: 'left'
		    },
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '1%',
        right: '1%',
        bottom: '1%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: ['体检类型'],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '直接访问',
            type: 'bar',
            barWidth: '10%',
            data: [0]
        }
    ]
};
function showtest5(){
	peigradient = echarts.init(document.getElementById("showgradient"));
	searchtotal5();
	 window.setInterval(function() {
		 searchtotal5();
		 },10000)	
 }
function searchtotal5(){
	 $.ajax({
			url:'exam_info_type_today.action',
			type: "post", 
			  success: function(data){
				  if(data.length>2){
				 var obj=eval("("+data+")");
				 var namelist=[];
				 var valuelist=[];					
				 for (var i = 0; i < obj.length; i++) {
					 namelist.push(obj[i].name); 
					 valuelist.push(obj[i].value); 
				 }
				 option5.xAxis[0].data=namelist;
				 option5.series[0].data=valuelist;
				 peigradient.setOption(option5,true);
				  }
			    } 
			});
}
//-------------------------------月度营业额----------------------------------------------------
var option6 = {
	    title: {
	        text: '月度营业额',
	        subtext: '单位:(元)'
	    },
tooltip: {
    trigger: 'axis'
},
legend: {
    data: ['个人', '团体']
},
toolbox: {
    show: true,
    feature: {
        dataView: {show: true, readOnly: false},
        magicType: {show: true, type: ['line', 'bar']},
        restore: {show: true},
        saveAsImage: {show: true}
    }
},
calculable: true,
xAxis: [
    {
        type: 'category',
        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    }
],
yAxis: [
    {
        type: 'value'
    }
],
series: [
    {
        name: '个人',
        type: 'bar',
        data: [0,0,0,0,0,0,0,0,0,0,0,0],
        markPoint: {
            data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
            ]
        },
        markLine: {
            data: [
                {type: 'average', name: '平均值'}
            ]
        }
    },
    {
        name: '团体',
        type: 'bar',
        data: [0,0,0,0,0,0,0,0,0,0,0,0],
        markPoint: {
            data: [
                //{name: '年最高', value: 182.2, xAxis: 7, yAxis: 183},
                //{name: '年最低', value: 2.3, xAxis: 11, yAxis: 3}
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
            ]
        },
        markLine: {
            data: [
                {type: 'average', name: '平均值'}
            ]
        }
    }
]
};
function showtest6(){
	peibar1 = echarts.init(document.getElementById("showbar1"));
	searchtotal6();
	 window.setInterval(function() {
		 searchtotal6();
		 },60000)	
}
function searchtotal6(){
	 $.ajax({
			url:'monthly_business_statistics.action',
			type: "post", 
			  success: function(data){
				  if(data.length>2){
				 var obj=eval("("+data+")");
				 var namelist=[];
				 var gvaluelist=[];	
				 var tvaluelist=[];
				 for (var i = 0; i < obj.list.length; i++) {
					 namelist.push(obj.list[i].name); 
					 gvaluelist.push(obj.list[i].value); 
					 tvaluelist.push(obj.list[i].value1); 
				 }
				 option6.xAxis[0].data=namelist;
				 option6.series[0].data=gvaluelist;
				 option6.series[1].data=tvaluelist;
				 peibar1.setOption(option6,true);
				  }
			    } 
			});
}


//----------------------------------流量趋势-------------------------------------------------
var colors7 = ['#5793f3', '#d14a61', '#675bba'];
var option7 = {
	    title: {
	        text: '操作业务量趋势',
	        subtext: '单位:(%)'
	    },
	    color: colors7,
	    tooltip: {
	        trigger: 'none',
	        axisPointer: {
	            type: 'cross'
	        }
	    },
	    legend: {
	        data:['当日业务量趋势']
	    },
	    grid: {
	        top: 70,
	        bottom: 30
	    },
	    xAxis: [
	        {
	            type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            axisLine: {
	                onZero: false,
	                lineStyle: {
	                    color: colors7[0]
	                }
	            },
	            axisPointer: {
	                label: {
	                    formatter: function (params) {
	                        return '峰值  ' + params.value
	                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
	                    }
	                }
	            },
	            data: ['7:00', '7:10', '7:20', '7:30', '7:40', '7:50']
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value'
	        }
	    ],
	    series: [
	        {
	            name: '当日趋势',
	            type: 'line',
	            smooth: true,
	            data: [0, 0, 0, 0, 0, 0]
	        }
	    ]
	};
function showtest7(){
	peimultiplexaxis = echarts.init(document.getElementById("flow_trend_chart"));
    peimultiplexaxis.setOption(option7, true);
    searchtotal7();
    window.setInterval(function() {
    	searchtotal7();
	},60000)	

}

function searchtotal7(){
	 $.ajax({
			url:'flow_trend_chart.action',
			type: "post", 
			  success: function(data){
				 if(data.length>2){
				    var obj=eval("("+data+")");
				    var namelist=[];
				    var gvaluelist=[];	
				    var tvaluelist=[];
				 for (var i = 0; i < obj.length; i++) {
					 namelist.push(obj[i].name); 
					 gvaluelist.push(obj[i].value); 
					 tvaluelist.push(obj[i].value1); 
				 }
				 option7.xAxis[0].data=namelist;
				 option7.series[0].data=tvaluelist;
				 peimultiplexaxis.setOption(option7,true);
				  }
			    } 
			});
}

//-----------------------------------------------------------------------------
//----------------------------------年度单位增长趋势-------------------------------------------------
var colors8 = ['#5793f3', '#d14a61', '#675bba'];
var option8 = {
	    title: {
	        text: '单位增长趋势',
	        subtext: '单位:(个数)'
	    },
	    color: colors8,
	    tooltip: {
	        trigger: 'none',
	        axisPointer: {
	            type: 'cross'
	        }
	    },
	    legend: {
	        data:['单位年度增长趋势']
	    },
	    grid: {
	        top: 70,
	        bottom: 50
	    },
	    xAxis: [
	        {
	            type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            axisLine: {
	                onZero: false,
	                lineStyle: {
	                    color: colors8[0]
	                }
	            },
	            axisPointer: {
	                label: {
	                    formatter: function (params) {
	                        return '峰值  ' + params.value
	                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
	                    }
	                }
	            },
	            data: ['2015']
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value'
	        }
	    ],
	    series: [
	        {
	            name: '单位年度增长趋势',
	            type: 'line',
	            smooth: true,
	            data: [0]
	        }
	    ]
	};
function showtest8(){
	peiAnnual_unit_growth = echarts.init(document.getElementById("annual_unit_growth"));
	peiAnnual_unit_growth.setOption(option8, true);
    searchtotal8();
    }

function searchtotal8(){
	 $.ajax({
			url:'annual_unit_growth.action',
			type: "post", 
			  success: function(data){
				 if(data.length>2){
				    var obj=eval("("+data+")");
				    var namelist=[];
				    var gvaluelist=[];	
				    var tvaluelist=[];
				 for (var i = 0; i < obj.length; i++) {
					 namelist.push(obj[i].name); 
					 gvaluelist.push(obj[i].value); 
				 }
				 option8.xAxis[0].data=namelist;
				 option8.series[0].data=gvaluelist;
				 peiAnnual_unit_growth.setOption(option8,true);
				  }
			    } 
			});
}

//----------------------------------年度人员增长趋势-------------------------------------------------
var colors9 = ['#5793f3', '#d14a61', '#675bba'];
var option9 = {
	    title: {
	        text: '人员增长趋势',
	        subtext: '单位:(人数)'
	    },
	    color: colors9,
	    tooltip: {
	        trigger: 'none',
	        axisPointer: {
	            type: 'cross'
	        }
	    },
	    legend: {
	        data:['人员年度增长趋势']
	    },
	    grid: {
	        top: 70,
	        bottom: 50
	    },
	    xAxis: [
	        {
	            type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            axisLine: {
	                onZero: false,
	                lineStyle: {
	                    color: colors9[0]
	                }
	            },
	            axisPointer: {
	                label: {
	                    formatter: function (params) {
	                        return '峰值  ' + params.value
	                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
	                    }
	                }
	            },
	            data: ['2015']
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value'
	        }
	    ],
	    series: [
	        {
	            name: '人员年度增长趋势',
	            type: 'line',
	            smooth: true,
	            data: [0]
	        }
	    ]
	};
function showtest9(){
	peiannual_personnel_growth = echarts.init(document.getElementById("annual_personnel_growth"));
	peiannual_personnel_growth.setOption(option9, true);
    searchtotal9();
    }

function searchtotal9(){
	 $.ajax({
			url:'annual_personnel_growth.action',
			type: "post", 
			  success: function(data){
				 if(data.length>2){
				    var obj=eval("("+data+")");
				    var namelist=[];
				    var gvaluelist=[];	
				    var tvaluelist=[];
				 for (var i = 0; i < obj.length; i++) {
					 namelist.push(obj[i].name); 
					 gvaluelist.push(obj[i].value); 
				 }
				 option9.xAxis[0].data=namelist;
				 option9.series[0].data=gvaluelist;
				 peiannual_personnel_growth.setOption(option9,true);
				  }
			    } 
			});
}


//-------------------------------人员体检次数组成分析----------------------------------------------------
var option10 = {
	    title: {
	        text: '体检次数统计',
	        subtext: '单位:(次)'
	    },
tooltip: {
    trigger: 'axis'
},
legend: {
    data: ['次数']
},
toolbox: {
    show: true,
    feature: {
        dataView: {show: true, readOnly: false},
        magicType: {show: true, type: ['line', 'bar']},
        restore: {show: true},
        saveAsImage: {show: true}
    }
},
calculable: true,
xAxis: [
    {
        type: 'category',
        data: ['1次', '2次', '3次']
    }
],
yAxis: [
    {
        type: 'value'
    }
],
series: [
    {
        name: '次数',
        type: 'bar',
        data: [0,0,0],
        markPoint: {
            data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
            ]
        },
        markLine: {
            data: [
                {type: 'average', name: '平均值'}
            ]
        }
    }
]
};
function showtest10(){
	peistatistics_personnel_times = echarts.init(document.getElementById("statistics_personnel_times"));
	searchtotal10();
}
function searchtotal10(){
	 $.ajax({
			url:'statistics_personnel_times.action',
			type: "post", 
			  success: function(data){
				  if(data.length>2){
				 var obj=eval("("+data+")");
				 var namelist=[];
				 var gvaluelist=[];	
				 var tvaluelist=[];
				 for (var i = 0; i < obj.length; i++) {
					 namelist.push(obj[i].name); 
					 gvaluelist.push(obj[i].value); 
				 }
				 option10.xAxis[0].data=namelist;
				 option10.series[0].data=gvaluelist;
				 peistatistics_personnel_times.setOption(option10,true);
				  }
			    } 
			});
}


//-------------------------------月度营业额----------------------------------------------------
var option11 = {
	    title: {
	        text: '月度总检疾病条数统计',
	        subtext: '单位:(条)'
	    },
tooltip: {
    trigger: 'axis'
},
legend: {
    data: ['月份']
},
toolbox: {
    show: true,
    feature: {
        dataView: {show: true, readOnly: false},
        magicType: {show: true, type: ['line', 'bar']},
        restore: {show: true},
        saveAsImage: {show: true}
    }
},
calculable: true,
xAxis: [
    {
        type: 'category',
        data: ['1月', '2月', '3月']
    }
],
yAxis: [
    {
        type: 'value'
    }
],
series: [
    {
        name: '次数',
        type: 'bar',
        data: [0,0,0],
        markPoint: {
            data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
            ]
        },
        markLine: {
            data: [
                {type: 'average', name: '平均值'}
            ]
        }
    }
]
};
function showtest11(){
	peicount_number_checks = echarts.init(document.getElementById("count_number_checks"));
	searchtotal11();
}
function searchtotal11(){
	 $.ajax({
			url:'count_number_checks.action',
			type: "post", 
			  success: function(data){
				  if(data.length>2){
				 var obj=eval("("+data+")");
				 var namelist=[];
				 var gvaluelist=[];	
				 var tvaluelist=[];
				 for (var i = 0; i < obj.length; i++) {
					 namelist.push(obj[i].name); 
					 gvaluelist.push(obj[i].value); 
				 }
				 option11.xAxis[0].data=namelist;
				 option11.series[0].data=gvaluelist;
				 peicount_number_checks.setOption(option11,true);
				  }
			    } 
			});
}

//--------------------------------年龄段疾病分布----------------------------------------------------
var colors12 = ['#5793f3', '#d14a61', '#675bba'];
var option12 = {
	    title: {
	        text: '年龄段阳性分布',
	        subtext: '单位:(疾病条数)'
	    },
	    color: colors12,
	    tooltip: {
	        trigger: 'none',
	        axisPointer: {
	            type: 'cross'
	        }
	    },
	    legend: {
	        data:['男性年龄段阳性','女性年龄段阳性']
	    },
	    grid: {
	        top: 70,
	        bottom: 30
	    },
	    xAxis: [
	        {
	            type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            axisLine: {
	                onZero: false,
	                lineStyle: {
	                    color: colors12[0]
	                }
	            },
	            axisPointer: {
	                label: {
	                    formatter: function (params) {
	                        return '峰值  ' + params.value
	                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
	                    }
	                }
	            },
	            data: ['20岁以下']
	        },
	        {
	            type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            axisLine: {
	                onZero: false,
	                lineStyle: {
	                    color: colors12[0]
	                }
	            },
	            axisPointer: {
	                label: {
	                    formatter: function (params) {
	                        return '峰值  ' + params.value
	                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
	                    }
	                }
	            },
	            data: ['20岁以下']
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value'
	        }
	    ],
	    series: [
	        {
	            name: '男性年龄段阳性',
	            type: 'line',
	            smooth: true,
	            data: [0, 0, 0, 0, 0, 0]
	        },
	        {
	            name: '女性年龄段阳性',
	            type: 'line',
	            smooth: true,
	            data: [0, 0, 0, 0, 0, 0]
	        }
	    ]
	};
function showtest12(){
	peiage_group_disease = echarts.init(document.getElementById("age_group_disease"));
	peiage_group_disease.setOption(option12, true);
    searchtotal12();
   
}

function searchtotal12(){
	 $.ajax({
			url:'age_group_disease.action',
			type: "post", 
			  success: function(data){
				 if(data.length>2){
				    var obj=eval("("+data+")");
				    var gnamelist=[];
				    var tnamelist=[];
				    var gvaluelist=[];	
				    var tvaluelist=[];	
				 for (var i = 0; i < obj.slist.length; i++) {
					 gnamelist.push(obj.slist[i].name); 
					 gvaluelist.push(obj.slist[i].value); 
				 }
				 option12.xAxis[0].data=gnamelist;
				 option12.series[0].data=gvaluelist;
				 for (var i = 0; i < obj.slist1.length; i++) {
					 tnamelist.push(obj.slist1[i].name); 
					 tvaluelist.push(obj.slist1[i].value); 
				 }
				 option12.xAxis[1].data=tnamelist;
				 option12.series[1].data=tvaluelist;
				 peiage_group_disease.setOption(option12,true);
				  }
			    } 
			});
}

//----------------------------------数据库连接数实时比率------------------------------------------------------
//-------------------------当日实时流量----------------------------------------------------------
var option13 = {
		title: {
		      text: '数据库实时连接数统计',
		      subtext: '实际连接数/最大连接数%',
		      left: 'left'
		},
	    tooltip: {
	        formatter: '{a} <br/>{b} : {c}%'
	    },
	    toolbox: {
	        feature: {
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    series: [
	        {
	            name: '业务指标',
	            type: 'gauge',
	            center: ['45%', '60%'],
	            detail: {formatter: '{value}%'},
	            data: [{value: 0, name: '比率'}]
	        }
	    ]
	};

function showtest13(){	
	peisql_conn_count = echarts.init(document.getElementById("sql_conn_count"));
	peisql_conn_count.setOption(option13, true);
	searchtotal13();
	window.setInterval(function() {		
		searchtotal13();
		 },300000)	
	peisql_conn_count.setOption(option13, true);
}
function searchtotal13(){
	$.ajax({
		url:'sql_conn_count.action',
		type: "post", 
		  success: function(data){
			  if(data.length>2){
			 var obj=eval("("+data+")");			
			 option13.series[0].data[0].value=obj.value;
			 peisql_conn_count.setOption(option13, true);
			  }
		    } 
		});
}


//----------------------------------连接数流量趋势-------------------------------------------------
var colors14 = ['#5793f3', '#d14a61', '#675bba'];
var option14 = {
	    title: {
	        text: '连接数趋势',
	        subtext: '单位:(%)'
	    },
	    color: colors14,
	    tooltip: {
	        trigger: 'none',
	        axisPointer: {
	            type: 'cross'
	        }
	    },
	    legend: {
	        data:['当日流量趋势']
	    },
	    grid: {
	        top: 70,
	        bottom: 30
	    },
	    xAxis: [
	        {
	            type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            axisLine: {
	                onZero: false,
	                lineStyle: {
	                    color: colors14[0]
	                }
	            },
	            axisPointer: {
	                label: {
	                    formatter: function (params) {
	                        return '峰值  ' + params.value
	                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
	                    }
	                }
	            },
	            data: ['7:00', '7:10', '7:20', '7:30', '7:40', '7:50']
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value'
	        }
	    ],
	    series: [
	        {
	            name: '当日趋势',
	            type: 'line',
	            smooth: true,
	            data: [0, 0, 0, 0, 0, 0]
	        }
	    ]
	};
function showtest14(){
	peiflow_sql_conn_count = echarts.init(document.getElementById("flow_sql_conn_count"));
	peiflow_sql_conn_count.setOption(option14, true);
    searchtotal14();
    window.setInterval(function() {
    	searchtotal14();
	},60000)	

}

function searchtotal14(){
	 $.ajax({
			url:'flow_sql_conn_count.action',
			type: "post", 
			  success: function(data){
				 if(data.length>2){
				    var obj=eval("("+data+")");
				    var namelist=[];
				    var gvaluelist=[];	
				    var tvaluelist=[];
				 for (var i = 0; i < obj.length; i++) {
					 namelist.push(obj[i].name); 
					 gvaluelist.push(obj[i].value); 
					 tvaluelist.push(obj[i].value1); 
				 }
				 option14.xAxis[0].data=namelist;
				 option14.series[0].data=tvaluelist;
				 peiflow_sql_conn_count.setOption(option14,true);
				  }
			    } 
			});
}

//-------------------------------------------------------------------------------------
//---------------------------------导检单回收实时统计--------------------------------------------------
var option15 = {
	    title: {
	        text: '导检单回收实时统计',
	        subtext: '单位:(人数)',
	        left: 'left'
	    },
	    tooltip: {
	        trigger: 'item',
	        formatter: '{a} <br/>{b} : {c} ({d}%)'
	    },
	    legend: {
	        type: 'scroll',
	        orient: 'vertical',
	        right: 10,
	        top: 20,
	        bottom: 20,
	        data: ['已回收','未回收'],
	        selected: ['已回收','未回收']
	    },
	    series: [
	        {
	            name: '名称',
	            type: 'pie',
	            radius: '40%',
	            center: ['45%', '60%'],
	            emphasis: {
	                itemStyle: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            },
	            data:[
	                    {value:0, name:'已回收'},
	                    {value:0, name:'未回收'}
	                ]	        
	        }
	    ]
	};


function showtest15(){
	 peiexam_gride_statistics = echarts.init(document.getElementById("exam_gride_statistics"));
	 searchtotal15();	 
	 window.setInterval(function() {
		 searchtotal15();
		 },1000)	
}

function searchtotal15(){
	 $.ajax({
			url:'examination_gride_statistics.action',//
			type: "post", 
			  success: function(data){
				  if(data.length>2){
				 var obj=eval("("+data+")");			
				 option15.series[0].data=obj;
				 var legendData = [];
				 var selected = {};
				 for (var i = 0; i < obj.length; i++) {
					 legendData.push(obj[i].name); 
					 selected[obj[i].name]=true;
				 }				 
				 option15.legend.data=legendData;
				 option15.legend.selected=selected;
				 peiexam_gride_statistics.setOption(option15, true);
				  }
			    } 
			});
}