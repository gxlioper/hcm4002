/**
 * 
 */
	//查看
	function lookachieve(val,row){
		 var str = '<a href=\"javascript:scheduledetails(\''+row.user_id+'\')\" class="easyui-linkbutton" style="width:80px;">查看</a>'
		 return str;
	}

	//查看排班信息
	var schedule_id;
	function scheduledetails(user_id){
		schedule_id=user_id;	
			$("#looks").dialog({
				title:'排班详情',
				href:'lookscheduledetails.action?id='+user_id,
				modal: true,
			}); 
		    $("#looks").dialog('open');
	}

	//日期格式转换
function convert_date(new_date){
	var abc="0"+((new_date.getMonth())+1);
	abc=abc.substring(abc.length-2,abc.length);
	var cba="0"+new_date.getDate();
	cba=cba.substring(cba.length-2,cba.length);
	var date_now=new_date.getFullYear()+"-"+abc+"-"+cba;
	return date_now;
	}

//获取当月排班信息
function load_paiban_this(a){
	var dt=new Date();
    var tyday=dt.getDate();//天      // dt.getDay();//返回的星期  0-星期天  1-6 对应周一到周六
	var tyyear=dt.getFullYear();//年
	var tymou=dt.getMonth();//月+1   返回值是数组  0到11  所以实际月份+1
	
	var theyear_m=true;
	var m;
	var y;
	
	if(a==1){
		var yer=$("#paiban_title").html().split("年")[0];
	    var mou="0"+$("#paiban_title").html().split("年")[1].split("月")[0];
	    mou=mou.substring(mou.length-2,mou.length);
		dt=new Date(yer,mou,01);//计算处理月第一天是星期几
		dt.setMonth(dt.getMonth());//下月
		m=dt.getMonth();
		y=dt.getFullYear();
		if(dt.getFullYear()!=tyyear||dt.getMonth()!=tymou){
			
			theyear_m=false;
		}
	}
	if(a==-1){
		var yer=$("#paiban_title").html().split("年")[0];
	    var mou="0"+$("#paiban_title").html().split("年")[1].split("月")[0];
	    mou=mou.substring(mou.length-2,mou.length);
		dt=new Date(yer,mou,01);
		dt.setMonth(dt.getMonth()-2);//上月
		m=dt.getMonth();
		y=dt.getFullYear();
		if(dt.getFullYear()!=tyyear||dt.getMonth()!=tymou){
			
			theyear_m=false;
		}  
		}
	dt.setDate(1);
	var sweet=dt.getDay();
	$("#paiban_title").html(dt.getFullYear()+"年"+(dt.getMonth()+1)+"月");//当前日期
	m=dt.getMonth()+1;
	y=dt.getFullYear();
	var str=' <tr><th  class="weekend">日</th><th class="weekend">一</th><th class="weekend">二</th><th  class="weekend">三</th><th  class="weekend">四</th><th  class="weekend">五</th><th  class="weekend">六</th></tr>';
	var sum_day=sum_month_func(dt);//返回当月天数
	var absd=sum_day+sweet;
	var tr=0;
	if(absd%7==0) tr=absd/7;//计算日历表格的行数
	else tr=parseInt(absd/7)+1;
	
	for(n=0;n<tr;n++){
		str+='<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>';
		}
	$("#paiban_table_list").html(str);//日历表格写到id为paiban_table_list的div中
    var td=$("#paiban_table_list tr td");
	for(i=1;i<=td.length;i++){
           if(i>sweet&&(i-sweet)<=sum_day){
			   if((i-sweet)==tyday&&theyear_m){
				   td.eq(i-1).css("background","#00ffff");
				   td.eq(i-1).css("color","#ff9900");
				   td.eq(i-1).css("font-size","16px");
				   td.eq(i-1).css("font-weight","bold");
				   }
			   td.eq(i-1).css("font-size","16px");
			   td.eq(i-1).html(i-sweet/*+'<span>休</span>'*/);
			   }
		}
	
	
	var yer=$("#paiban_title").html().split("年")[0];
	var mou="0"+$("#paiban_title").html().split("年")[1].split("月")[0];
	mou=mou.substring(mou.length-2,mou.length);
	var date_now=yer+"-"+mou+"-01";
	//if(a=1){data={"date":date_now};}
	//if(a=-1){data={"date":date_now};}
	//load_post(url,data,load_paiban_this_sess);//可用异步代替
	
	//var r=dg.datagrid('getSelections');//返回被选中的行，如果没有则返回null
	$.ajax({
		url:'scheduledetailsList.action?m_id='+m+"&user_id="+schedule_id+"&y_id="+y,//加载已排班信息
		type: "post", 
		  success: function(data){
			 var obj=eval("("+data+")");
			// alert(obj[0].working_date);
			 var td=$("#paiban_table_list tr td");
			 for( i=0;i<td.length;i++){
					for(n=0;n<obj.length;n++){//写排班
						if(td.eq(i).html().split("<td>")[0]==(parseInt(obj[n].working_date.split("-")[2]))){
							//td.eq(i).children("span").html('<b style="color:#86C127">上班</b>');
							td.eq(i).css("color","#ff0000");
							//td.eq(i).children("span").remove();
							}
						}
					}
		    } 
		});
}


//返回当月天数
function sum_month_func(dt){//dt系统当前时间  	//&&且 ||或者
	var isrun=false; 
	if((dt.getFullYear()%4==0 && dt.getFullYear()%100!=0)|| dt.getFullYear()%400==0) isrun=true; //判断是否为闰年
	switch(dt.getMonth()+1) { 
		case 2: 
			if(isrun) {return 29;} 
			else {return 28;} 
		case 1: 
		case 3: 
		case 5: 
		case 7: 
		case 8: 
		case 10: 
		case 12: 
		return 31; 
		default: 
		return 30; 
	} 
} 



	
		