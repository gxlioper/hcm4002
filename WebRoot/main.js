/**
 * 
 */


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
				  
				   td.eq(i-1).css("color","#ff0000");
				   td.eq(i-1).css("font-size","16px");
				   td.eq(i-1).css("font-weight","bold");
				   td.eq(i-1).css("background-color","#ffff00");
				   }
			   td.eq(i-1).css("height","26px");
			   td.eq(i-1).css("color","#000000");
			   td.eq(i-1).css("font-size","14px");
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
	
	$.ajax({
		url:'paibanList.action?m_id='+m+'&y_id='+y,//加载已排班信息
		type: "post", 
		  success: function(data){
			 var obj=eval("("+data+")");
			// alert(obj[0].working_date);
			 var td=$("#paiban_table_list tr td");
			 for( i=0;i<td.length;i++){
					for(n=0;n<obj.length;n++){//写排班
						if(td.eq(i).html().split("<td>")[0]==(parseInt(obj[n].working_date.split("-")[2]))){
							//td.eq(i).children("span").html('<b style="color:#86C127">上班</b>');
							td.eq(i).css("color","#ff0000");//red
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
		if((dt.getFullYear()%4==0 && dt.getFullYear()%100!=0)|| dt.getFullYear()%400==0) //判断是否为闰年
			isrun=true; 
		switch(dt.getMonth()+1) 
		{ 
			case 2: 
			if(isrun) 
			{return 29;} 
			else 
			{return 28;} 
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
	
	//添加备忘
	function newNote(){
		$("#edit_dlg").dialog({
			title:'新建备忘',
			href:'newNotes.action',
		    modal: true,
		});
		$("#edit_dlg").dialog("open");
	}
	
	//保存备忘
	function saveNotesData(){
		 $("#notes_content").validatebox({
				required:true,
			});
			
		$.ajax({
			url:'saveNotes.action',
			type:"post",
			data:{
				notes_content:$("#notes_content").val(),
				notes_date:$("#notes_date").datebox('getValue'),
			},
			success : function(data) {
				$.messager.alert("操作提示",data);
				$("#edit_dlg").dialog("close");
				 yizhou_beiwang();//备忘
			},
			error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
			}
		})
		
	}
	
	//计算当前日期是第几周
	var mydate=new Date();
	
	function getYearWeek(mydate){ 
		
	    var date2=new Date(mydate.getFullYear(), 0, 1); 
	    var day1=mydate.getDay(); 
	    if(day1==0) day1=1; 
	    var day2=date2.getDay(); 
	    if(day2==0) day2=1; 
	    d = Math.round((mydate.getTime() - date2.getTime()+(day2-day1)*(24*60*60*1000)) / 86400000);   
	    return Math.ceil(d /7);  
	} 
	
	//加载备忘信息
	
	var numweek=getYearWeek(mydate);
	
	function yizhou_beiwang(a){
		if(a==-1){
			numweek=numweek-1;
		}
		if(a==1){
			numweek=numweek+1;
		}
		$.ajax({
			url:'yizhoubeiwang.action?wk_id='+numweek,
			type: "post", 
			  success: function(data){
				 var obj=eval("("+data+")");
				 //var t=new Date(obj[0].notes_date.split("-")[0],obj[0].notes_date.split("-")[1]-1,obj[0].notes_date.split("-")[2]);
				 //alert(t);
				 var str = '';
				 if(obj.length<=0){
					 str+='<tr><td style="width:130px;color:#FF0000;padding-left:100px;padding-top:30px; ">本周暂无备忘信息</td></tr>'
					 $("#notesdata").html(str);
				 }else{
					 for(i=0;i<obj.length;i++){
						 var dd=obj[i].notes_date;
							str += '<dl>'
			    	   		+'<dt style=";height:40px;line-height:40px;">星期&nbsp;&nbsp;</dt><dd style="width:20px;height:40px;line-height:40px;">'; 
							if(new Date(obj[i].notes_date).getDay()=='0'){
								str+= "天";
							}else if(new Date(obj[i].notes_date).getDay()=='1'){
								str+= "一";
							}else if(new Date(obj[i].notes_date).getDay()=='2'){
								str+= "二";
							}else if(new Date(obj[i].notes_date).getDay()=='3'){
								str+= "三";
							}else if(new Date(obj[i].notes_date).getDay()=='4'){
								str+= "四";
							}else if(new Date(obj[i].notes_date).getDay()=='5'){
								str+= "五";
							}else{
								str+= "六";
							}
						str +='</dd>'
			    	   		+'<dt style="width:60px;text-align:right;height:40px;line-height:40px;">时间</dt>'
			    	   		+'<dd style="width:140px;text-align:center;height:40px;line-height:40px;">'+'<a  onclick="bianji(this);">'+obj[i].notes_date+'</a>' +'</dd>'
			    	    	+'<dt style="width:90px;text-align:left;height:40px;line-height:40px;">备忘内容</dt>'
			    	       +'<dd>'
			    	       	+'<textarea style="resize: vertical;" cols="38" rows="1" readonly="readonly" id="tt" >'+obj[i].notes_content+'</textarea>'
			    	       +'</dd>'
			    	    +'</dl>';
						};
						$("#notesdata").html(str); 
				 }
			    } 
			});
	}
	
	function bianji(x_date){
		$("#edit_dlg").dialog({
			title:'编辑备忘',
			href:'updaterNotes.action?notes_date='+$(x_date).text(),
		    modal: true,
		});
		$("#edit_dlg").dialog("open");
		
	}
	
	