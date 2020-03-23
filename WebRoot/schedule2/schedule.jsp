<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%  
  application.setAttribute("name","application_James");  
   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<link href="<%=request.getContextPath()%>/schedule2/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/schedule2/mainstructure.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/schedule2/maincontent.css" rel="stylesheet" type="text/css" />
<link rel='stylesheet' type='text/css' href='<%=request.getContextPath()%>/schedule2/theme.css' />
<link rel='stylesheet' type='text/css' href='<%=request.getContextPath()%>/schedule2/fullcalendar.css' />
<link rel='stylesheet' type='text/css' href='<%=request.getContextPath()%>/schedule2/fullcalendar.print.css'
    media='print' />


<script src="<%=request.getContextPath()%>/schedule2/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/schedule2/jquery-ui.min1.9.1.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/schedule2/jquery-ui-timepicker-addon.js"></script>
<script src="<%=request.getContextPath()%>/schedule2/jquery-ui-sliderAccess.js"></script>
<script src="<%=request.getContextPath()%>/schedule2/datepicker-zh.js?randomId=<%=Math.random()%>" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/schedule2/jquery-ui-timepicker-addon.css"   rel="stylesheet" />
<script src="<%=request.getContextPath()%>/schedule2/fullcalendar.js?randomId=<%=Math.random()%>" type="text/javascript"></script>
<script type='text/javascript'>
	var zhi_i = 1;
    $(document).ready(function () {
    /* 	var date = new Date();
    	var shijian = "";
    	if(date.getDay()=='0'){
    		shijian  = "周日"
    	}
    	if(date.getDay()=='1'){
    		shijian  = "周一"
    	}
    	if(date.getDay()=='2'){
    		shijian  = "周二"
    	}
    	if(date.getDay()=='3'){
    		shijian  = "周三"
    	}
    	if(date.getDay()=='4'){
    		shijian  = "周四"
    	}
    	if(date.getDay()=='5'){
    		shijian  = "周五"
    	}
    	if(date.getDay()=='6'){
    		shijian  = "周六"
    	} */
   
    	var gaodu =window.screen.availHeight; 
    	gaodu = gaodu-220;
    	/* $('#title').blur(function(){
    		var changdu = $('#title').val();
    		if(changdu.length==0){
    			$('#biaoti').text("&bnsp;&nbsp;必填项");
    		}
    	}) */
        //  $("#hid").timepicker();
      $('#title').blur(function(){
    	  
    	  if($('#title').val().length==0){
    		  $('#biaoti').html("&nbsp;必填项")
    	  } else {
    		  $('#biaoti').html("&nbsp;*") 
    	  }
      })
        $('#start').blur(function(){
    	  
    	  if($('#start').val().length==0){
    		  $('#kaishi').html("&nbsp;必填项")
    	  } else {
    		  $('#kaishi').html("&nbsp;*") 
    	  }
      })
        $('#end').blur(function(){
    	  
    	  if($('#end').val().length==0){
    		  $('#jieshu').html("&nbsp;必填项")
    	  } else {
    		  $('#jieshu').html("&nbsp;*") 
    	  }
      })
     
   	  $("#start").timepicker({   closeText: '完成',currentText: '现在',dateFormat: 'yy-mm-dd', timeFormat: 'hh:mm', hourMin: 0, hourMax: 24, hourGrid: 3, minuteGrid: 15, timeText: '时间', hourText: '时', minuteText: '分', timeOnlyTitle: '选择时间', onClose: function (dateText, inst) {
            if ($('#start').val() != '') {
                var testStartDate = $('#start').datetimepicker('getDate');
                var testEndDate = $('#end').datetimepicker('getDate');
                if (testStartDate > testEndDate)
                    $('#end').datetimepicker('setDate', testStartDate);
            } else {
                $('#end').val(dateText);
            }
        },
            onSelect: function (selectedDateTime) {
                $('#end').datetimepicker('option', 'minDate', $('#end').datetimepicker('getDate'));
            }
        }); 
       /*  $('#start').datebox({    
            required:true   
        });  
        $('#end').datebox({    
            required:true   
        });   */
         $("#end").datetimepicker({  closeText: '完成',currentText: '现在',dateFormat: 'yy-mm-dd', hourMin: 5, hourMax: 23, hourGrid: 3, minuteGrid: 15, timeText: '时间', hourText: '时', minuteText: '分', onClose: function (dateText, inst) {
            if ($('#start').val() != '') {
                var testStartDate = $('#start').datetimepicker('getDate');
                var testEndDate = $("#end").datetimepicker('getDate');
                if (testStartDate > testEndDate)
                    $('#start').datetimepicker('setDate', testEndDate);
            } else {
                $('#start').val(dateText);
            }
        },
            onSelect: function (selectedDateTime) {
               // $('#start').datetimepicker('option', 'maxDate', $("#end").datetimepicker('getDate'));
            }
        }); 
        $("#addhelper").hide();

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();
		var j = new Date();
        var json = "["
			+"{"
				+"id: 999,"
				+"title: '重复项目',"
				+"start:'"
				+"allDay: false"
				+"}"
				+"]";
        var jon = '['
                   +'{'
                   +' "id": "1222",'
                 +' "title": "会议已经结束",'
                   +'  "start": "2017-07-06T14:30:00",'
                   +'   "end": "2017-07-06T19:30:00",'
                  +'    "confname":"呵呵呵",'//重要程度
                  //+'    "url": "http://baidu.com",'
                  +'    "description": "内容",'
                   +'    "allDay": "false",'
                  // +'    "chengdu":"chengdu",'
                  // +'    "className":"111"' 
                  +'"fullname":"会议已经结束1"'
                   +'  }'
                   +']';
        	var huidiao ="";
        var v_start="";
        var v_end = "";
        $('#calendar').fullCalendar(huidiao={
            theme: true,
            contentHeight:560,
            header: {
                left: 'prev,next today',
                center: 'title',
             	right: 'month,agendaWeek,agendaDay' 
            },
         
            monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            today: ["今天"],
            firstDay: 1,
            buttonText: {
                today: '本月',
              /*   month: '月',
               	week: '周',
                day: '日',  */
                prev: '上一月',
                next: '下一月'
            },
            viewDisplay: function (view) {//动态把数据查出，按照月份动态查询
            	$('.fc-header-right').html("");
                var viewStart = $.fullCalendar.formatDate(view.start, "yyyy-MM-dd HH:mm:ss");
                var viewEnd = $.fullCalendar.formatDate(view.end, "yyyy-MM-dd HH:mm:ss");
                v_start = viewStart;
                v_end = viewEnd;
                $("#calendar").fullCalendar('removeEvents');
                $.post("getSchedulePageList.action", { scheduling_time: viewStart, scheduling_end_time: viewEnd }, function (data) {
                   var resultCollection = jQuery.parseJSON(data);
                   var f_name = "";
                   $.each(resultCollection, function (index, term) {
                	   f_name = term.fullname.replace(/\n/g,'</br>');
                	   term.fullname = f_name.replace(/\s/g,'&nbsp;');
                        $("#calendar").fullCalendar('renderEvent',term, true);
                   });

                }); //把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示
            },
            editable: false,//判断该日程能否拖动
            //height:550,
           // width:1920,
            
            height:gaodu,
            handleWindowResize:true,
           // aspectRatio:6,
            dayClick: function (date, allDay, jsEvent, view) {//日期点击后弹出的jq ui的框，添加日程记录
            /* 	alert(jiedian);
            	console.log(jsEvent.originalEvent);
            	console.log(jsEvent.originalEvent.target);
            	console.log($(shijian).val());
            	alert($(shijian).val()); */
            	if($("#schedule_eite").val() != '1'){
            		return;
            	}
                var selectdate = $.fullCalendar.formatDate(date, "yyyy-MM-dd");//选择当前日期的时间转换
               	$("#end").datetimepicker('setDate', selectdate);//给时间空间赋值
                $("#start").datetimepicker('setDate', selectdate);//给开始时间赋值
                $("#shijian").text(selectdate);
                $("#reservebox").dialog({
                    autoOpen: false,
                    height: 400,
                    width: 500,
                    title: '编辑' + selectdate,
                    modal: true,
                    position: "center",
                    draggable: false,
                    beforeClose: function (event, ui) {
                        //$.validationEngine.closePrompt("#meeting");
                        //$.validationEngine.closePrompt("#start");
                        //$.validationEngine.closePrompt("#end");
                    },
                    timeFormat: 'HH:mm{ - HH:mm}',

                    buttons: {
                        "关闭": function () {
                            $(this).dialog("close");
                            
                            $('#title').val("");
                            $('#chengdu').val("");
                            $('#title').val("");
                            document.getElementById("details").value=""; 
	                       	$('#biaoti').html("&nbsp;*") 
	                       	$('#kaishi').html("&nbsp;*") 
	                       	$('#jieshu').html("&nbsp;*") 
	                       	
	                         

                        },
                        "保存": function () {

                            var startdatestr = $("#start").val(); //开始时间
                            var enddatestr = $("#end").val(); //结束时间
                            var confid = $("#title").val(); //标题
                            var det = $("#details").val(); //内容 
                            var cd = $("#chengdu").val(); //重要程度 
                            var id2;
                            var startdate = $.fullCalendar.parseDate(selectdate + "T" + startdatestr);//时间和日期拼接
                            var enddate = $.fullCalendar.parseDate(enddatestr);
                            //var schdata = { title: confid, fullname: confid, description: det, confname: cd, confshortname: 'M1', start: selectdate + ' ' + startdatestr, end: enddatestr };
                            var schdata = { title: confid,scheduling_content: det, significance: cd,scheduling_time: selectdate,scheduling_end_time: selectdate };
                            $.ajax({
                                type: "POST", //使用post方法访问后台

                                url: "saveSchedulePage.action", //要访问的后台地址
                                data: schdata, //要发送的数据
                                success: function (data) {
                                    //对话框里面的数据提交完成，data为操作结果
                                    id2 = data;
                                    var schdata2 = { title:det, fullname:det, description: det, confname: cd, confshortname: 'M1', start: selectdate + ' ' + startdatestr, end: enddatestr, id: id2 };
                                    $('#calendar').fullCalendar('renderEvent', schdata2, true);
                                  /*   $("#start").val(''); //开始时间
                                    $("#end").val(''); //结束时间
                                    $("#title").val(''); //标题 */
                                    $("#details").val(''); //内容 
                                  /*   $("#chengdu").val(''); //重要程度  */
                                    
                                 	$('#biaoti').html("&nbsp;*") 
        	                       	$('#kaishi').html("&nbsp;*") 
        	                       	$('#jieshu').html("&nbsp;*") 
        	                       	
                                   	//刷新日历数据
                                    $("#calendar").fullCalendar('removeEvents');
                                    $.post("getSchedulePageList.action", { scheduling_time: v_start, scheduling_end_time: v_end }, function (data) {
                                       var resultCollection = jQuery.parseJSON(data);
                                       $.each(resultCollection, function (index, term) {
                                    	   term.fullname = term.fullname.replace(/\n/g,'</br>');
                                            $("#calendar").fullCalendar('renderEvent',term, true);
                                       });
                                    }); //把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示
        	                       	alert("操作成功"); 
                                
                                }
                            });
                            $(this).dialog("close");
                        }

                    }
                });
                $("#reservebox").dialog("open");
                return false;
            },
            titleFormat: 'yyyy'+'年'+' MMMM ',
            loading: function (bool) {
                if (bool) $('#loading').show();
                else $('#loading').hide();
            },
            //eventBackgroundColor:"background:#000000",
            eventAfterRender: function (event, element, view) {//数据绑定上去后添加相应信息在页面上
            	$(element).css("background:#000000");
                var fstart = $.fullCalendar.formatDate(event.start, "HH:mm");
                var fend = $.fullCalendar.formatDate(event.end, "HH:mm");
                //element.html('<a href=#><div>Time: ' + fstart + "-" +  fend + '</div><div>Room:' + event.confname + '</div><div style=color:#E5E5E5>Host:' +  event.fullname + "</div></a>");


                var confbg = '';
                if (event.confid == 1) {
                    confbg = confbg + '<span class="fc-event-bg"></span>';
                } else if (event.confid == 2) {
                    confbg = confbg + '<span class="fc-event-bg"></span>';
                } else if (event.confid == 3) {
                    confbg = confbg + '<span class="fc-event-bg"></span>';
                } else if (event.confid == 4) {
                    confbg = confbg + '<span class="fc-event-bg"></span>';
                } else if (event.confid == 5) {
                    confbg = confbg + '<span class="fc-event-bg"></span>';
                } else if (event.confid == 6) {
                    confbg = confbg + '<span class="fc-event-bg"></span>';
                } else {
                    confbg = confbg + '<span class="fc-event-bg"></span>';
                }

              //  var titlebg = '<span class="fc-event-conf" style="background:' + event.confcolor + '">' + event.confshortname + '</span>';

//                if (event.repweeks > 0) {
//                    titlebg = titlebg + '<span class="fc-event-conf" style="background:#fff;top:0;right:15;color:#3974BC;font-weight:bold">R</span>';
//                }

                if (view.name == "month") {//按月份
                    var evtcontent = '<div class="fc-event-vert"><a>';
                    evtcontent = evtcontent + confbg;
                    evtcontent = evtcontent + '<span class="fc-event-titlebg">' + event.fullname + '</span>';
//                    evtcontent = evtcontent + '<span>Room: ' + event.confname + '</span>';
//                    evtcontent = evtcontent + '<span>Host: ' + event.fullname + '</span>';
    //  evtcontent = evtcontent + '</a><div class="ui-resizable-handle ui-resizable-e"></div></div>';
                    element.html(evtcontent);
                } else if (view.name == "agendaWeek") {//按周
                    var evtcontent = '<a>';
                    evtcontent = evtcontent + confbg;
                    evtcontent = evtcontent + '<span class="fc-event-time">' + fstart + "-" + fend  + '</span>';
                    evtcontent = evtcontent + '<span>'+ event.fullname + '</span>';
                    //evtcontent = evtcontent + '<span>' +  event.fullname + '</span>';
         //  evtcontent = evtcontent + '</a><span class="ui-icon ui-icon-arrowthick-2-n-s"><div class="ui-resizable-handle ui-resizable-s"></div></span>';
                    element.html(evtcontent);
                } else if (view.name == "agendaDay") {//按日
                    var evtcontent = '<a>';
                    evtcontent = evtcontent + confbg;
                    evtcontent = evtcontent + '<span class="fc-event-time">' + fstart + " - " + fend +  '</span>';
    //              evtcontent = evtcontent + '<span>Room: ' + event.confname + '</span>';
  //                evtcontent = evtcontent + '<span>Host: ' + event.fullname + '</span>';
//                    evtcontent = evtcontent + '<span>Topic: ' + event.topic + '</span>';
                 // evtcontent = evtcontent + '</a><span class="ui-icon ui-icon-arrow-2-n-s"><div class="ui-resizable-handle ui-resizable-s"></div></span>';
                    element.html(evtcontent);
                }
            },
            eventMouseover: function (calEvent, jsEvent, view) {
            	var titl = calEvent.title;
            	
            	if(zhi_i=="2"){
            		titl = calEvent.fullname.replace(/\n/g,'</br>');
            		titl  = titl.replace(/\s/g,'&nbsp;');
            	}  
            	zhi_i=2;
            	//var titl = calEvent.title.replace(/\n/g,'</br>');
                var fstart = $.fullCalendar.formatDate(calEvent.start, "yyyy/MM/dd");
                var fend = $.fullCalendar.formatDate(calEvent.end, "yyyy/MM/dd HH:mm");
                $(this).attr('title', "" + "" + "" + titl);
                $(this).css('font-weight', 'normal');
                $(this).tooltip({
                    effect: 'toggle',
                    cancelDefault: true
                });
            },

            eventClick: function (event) {
            	console.log(event);
            	if($("#schedule_eite").val() != '1'){
            		return;
            	}
                var fstart = $.fullCalendar.formatDate(event.start, "HH:mm");
                //var s_fstart = $.fullCalendar.formatDate(event.start, "HH:mm");
                var fend = $.fullCalendar.formatDate(event.end, "HH:mm");
                //  var schdata = { sid: event.sid, deleted: 1, uid: event.uid };
                var selectdate = $.fullCalendar.formatDate(event.start, "yyyy-MM-dd");
                $("#start").datetimepicker('setDate',selectdate);
                if(event.end==null || event.end==""){
                	 $("#end").datetimepicker('setDate',event.start);
                } else {
	                $("#end").datetimepicker('setDate',event.end);
                }
                $("#title").val(event.title); //标题
                $("#details").val(event.title); //内容 
                $("#chengdu").val(event.confname); //重要程度 
				$('#shijian').text(selectdate);

				var selectdate = $.fullCalendar.formatDate(event.start, "yyyy-MM-dd");//选择当前日期的时间转换
                $("#reservebox").dialog({
                    autoOpen: false,
                    height: 400,
                    width: 500,
                    title: '编辑'+selectdate,
                    modal: true,
                    position: "center",
                    draggable: false,
                    beforeClose: function (event, ui) {
                        //$.validationEngine.closePrompt("#meeting");
                        //$.validationEngine.closePrompt("#start");
                        //$.validationEngine.closePrompt("#end");
                        $("#start").val(''); //开始时间
                        $("#end").val(''); //结束时间
                        $("#title").val(''); //标题
                        $("#details").val(''); //内容 
                        $("#chengdu").val(''); //重要程度 
                    },
                    timeFormat: 'HH:mm{ - HH:mm}',

                    buttons: {
                    	"关闭": function () {
                            $(this).dialog("close");
                            
                            $('#title').val("");
                            $('#chengdu').val("");
                            $('#title').val("");
                            //$('#details').text("");
                            document.getElementById("details").value=""; 
	                       	  $('#biaoti').html("&nbsp;*") 
	                       	  $('#kaishi').html("&nbsp;*") 
	                       	  $('#jieshu').html("&nbsp;*") 
	                       	  
	                       	  
	                       	  
	                       	  
                        },
                        "删除": function () {
                            var aa = window.confirm("警告：确定要删除记录，删除后无法恢复！");
                            if (aa) {
                                var para = { id: event.id };
                                $.ajax({
                                    type: "POST", //使用post方法访问后台
                                    url: "deleteSchedulePage.action", //要访问的后台地址
                                    data: para, //要发送的数据
                                    success: function (data) {
                                        //对话框里面的数据提交完成，data为操作结果
                                        $('#calendar').fullCalendar('removeEvents', event.id);
                                        
                                       	//刷新日历数据
                                        $("#calendar").fullCalendar('removeEvents');
                                        $.post("getSchedulePageList.action", { scheduling_time: v_start, scheduling_end_time: v_end }, function (data) {
                                           var resultCollection = jQuery.parseJSON(data);
                                           $.each(resultCollection, function (index, term) {
                                        	   	term.fullname = term.fullname.replace(/\n/g,'</br>');
                                                $("#calendar").fullCalendar('renderEvent',term, true);
                                           });
                                        }); //把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示
                                        alert("删除成功");
                                        
                                         
                                        
                                        $('#title').val("");
                                        $('#chengdu').val("");
                                        $('#title').val("");
                                        $('#details').text("");
            	                       	  $('#biaoti').html("&nbsp;*") 
            	                       	  $('#kaishi').html("&nbsp;*") 
            	                       	  $('#jieshu').html("&nbsp;*") 
            	                       	  
                                        $('#calendar').fullCalendar('rerenderEvents');
            	                       	  
                                    }

                                });

                            }
                            $(this).dialog("close");
                        },
                        "修改": function () {

                            var startdatestr = $("#start").val(); //开始时间
                            var enddatestr = $("#end").val(); //结束时间
                            var confid = $("#details").val(); //标题
                            var det =$("#details").val(); //内容 
                            var cd = $("#chengdu").val(); //重要程度 
                            var startdate = $.fullCalendar.parseDate(selectdate + "T" + startdatestr);
                            var enddate = $.fullCalendar.parseDate(enddatestr);

                            event.fullname = det;
                            event.confname = det;
                          /*   event.start = startdate;
                            event.end = enddate; */
                            event.description = det;
                            event.scheduling_content=det;
                            event.title=det;
                            var id2;

                            //var schdata = { title: confid, fullname: confid, description: det, confname: cd, confshortname: 'M1', start: selectdate + ' ' + startdatestr, end: enddatestr, id: event.id };
                            var schdata = {scheduling_content: det, significance: cd,scheduling_time: selectdate,scheduling_end_time: enddatestr,id: event.id};
                            
                            
                            $.ajax({
                                type: "POST", //使用post方法访问后台

                                url: "updateSchedulePage.action", //要访问的后台地址
                                data: schdata, //要发送的数据
                                success: function (data) {
                                    //对话框里面的数据提交完成，data为操作结果
									
                                    var schdata2 = { title: det, fullname: det, description: det, confname: det, confshortname: 'M1',id: event.id,scheduling_time: selectdate,scheduling_end_time: selectdate };
                                    $('#calendar').fullCalendar('updateEvent', schdata2);
                                    
                                    	//刷新日历数据
                                        $("#calendar").fullCalendar('removeEvents');
                                        $.post("getSchedulePageList.action", { scheduling_time: v_start, scheduling_end_time: v_end }, function (data) {
                                           var resultCollection = jQuery.parseJSON(data);
                                           $.each(resultCollection, function (index, term) {
                                        	   term.fullname = term.fullname.replace(/\n/g,'</br>');
                                                $("#calendar").fullCalendar('renderEvent',term, true);
                                           });
                                        }); //把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示
                                    
                                    alert("修改成功");
                                     
                                }
                            });
                            $(this).dialog("close");
                        }

                    }
                });
                $("#reservebox").dialog("open");
                return false; 
            },
            //            events: "../../sr/AccessDate.ashx"
            //events: []
            
        });



        //goto date function
        if ($.browser.msie) {
            $("#calendar .fc-header-right table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" style="border-right:0px;padding:1px 3px 2px;" ><input type="text" id="selecteddate" size="10" style="padding:0px;"></div></td><td><div class="ui-state-default ui-corner-left ui-corner-right"><a><span id="selectdate" class="ui-icon ui-icon-search">goto</span></a></div></td><td><span class="fc-header-space"></span></td>');
        } else {
            $("#calendar .fc-header-right table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" style="border-right:0px;padding:3px 2px 4px;" ><input type="text" id="selecteddate" size="10" style="padding:0px;"></div></td><td><div class="ui-state-default ui-corner-left ui-corner-right"><a><span id="selectdate" class="ui-icon ui-icon-search">goto</span></a></div></td><td><span class="fc-header-space"></span></td>');
        }

        $("#selecteddate").datepicker({
            dateFormat: 'yy-mm-dd',
            beforeShow: function (input, instant) {
                setTimeout(
							function () {
							    $('#ui-datepicker-div').css("z-index", 15);
							}, 100
						);
            }
        });



        $("#selectdate").click(function () {
            var selectdstr = $("#selecteddate").val();
            var selectdate = $.fullCalendar.parseDate(selectdstr, "yyyy-mm-dd");
            $('#calendar').fullCalendar('gotoDate', selectdate.getFullYear(), selectdate.getMonth(), selectdate.getDate());
        });


        // conference function
        $("#calendar .fc-header-left table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" id="selectmeeting"><a><span id="selectdate" class="ui-icon ui-icon-search" style="float: left;padding-left: 5px; padding-top:1px"></span>meeting room</a></div></td><td><span class="fc-header-space"></span></td>');



        //        $(".fc-button-prev").click(function () {
        //            alert("fasdf");
        //        });

    });

</script>
<style type='text/css'>
    .ui-datepicker
    {
        width: 23em;
        padding: .2em .2em 0;
        font-size: 70%;
        display: none;
    }
    
    #calendar
    {
        width:100%;
        margin: 0 auto;
    }
    #loading
    {
        top: 0px;
        right: 0px;
    }
    
    .tooltip
    {
        padding-bottom: 25px;
        padding-left: 25px;
        width: 100px !important;
        padding-right: 25px;
        display: none;
        background: #999;
        height: 70px;
        color: red;
        font-size: 12px;
        padding-top: 25px;
        z-order: 10;
    }
    .ui-timepicker-div .ui-widget-header
    {
        margin-bottom: 8px;
    }
    .ui-timepicker-div dl
    {
        text-align: left;
    }
    .ui-timepicker-div dl dt
    {
        height: 25px;
        margin-bottom: -25px;
    }
    .ui-timepicker-div dl dd
    {
        margin: 0 10px 10px 65px;
    }
    .ui-timepicker-div td
    {
        font-size: 90%;
    }
    .ui-tpicker-grid-label
    {
        background: none;
        border: none;
        margin: 0;
        padding: 0;
    }
    .ui-timepicker-rtl
    {
        direction: rtl;
    }
    .ui-timepicker-rtl dl
    {
        text-align: right;
    }
    .ui-timepicker-rtl dl dd
    {
        margin: 0 65px 10px 10px;
    }
   
</style>
<!--   		<style type="text/css"> 
			body,img,p,h1,h2,h3,h4,h5,h6,form,table,td,ul,ol,li,dl,dt,dd,pre,blockquote,fieldset,label{
				margin:0;
				padding:0;
				border:0;
			}
			body{ background-color: #777; border-top: solid 10px #7b94b2; font: 90% Arial, Helvetica, sans-serif; padding: 20px; }
			h1,h2,h3{ margin: 10px 0; }
			h1{}
			h2{ color: #f66; }
			h3{ color: #6b84a2; }
			p{ margin: 10px 0; }
			a{ color: #7b94b2; }
			ul,ol{ margin: 10px 0 10px 40px; }
			li{ margin: 4px 0; }
			dl.defs{ margin: 10px 0 10px 40px; }
			dl.defs dt{ font-weight: bold; line-height: 20px; margin: 10px 0 0 0; }
			dl.defs dd{ margin: -20px 0 10px 160px; padding-bottom: 10px; border-bottom: solid 1px #eee;}
			pre{ font-size: 12px; line-height: 16px; padding: 5px 5px 5px 10px; margin: 10px 0; background-color: #e4f4d4; border-left: solid 5px #9EC45F; overflow: auto; }

			.wrapper{ background-color: #ffffff; width: 800px; border: solid 1px #eeeeee; padding: 20px; margin: 0 auto; }
			#tabs{ margin: 20px -20px; border: none; }
			#tabs, #ui-datepicker-div, .ui-datepicker{ font-size: 85%; }
			.clear{ clear: both; }
			
			.example-container{ background-color: #f4f4f4; border-bottom: solid 2px #777777; margin: 0 0 20px 40px; padding: 20px; }
			.example-container input{ border: solid 1px #aaa; padding: 4px; width: 175px; }
		</style> -->
<body>
<input id = "schedule_eite" type="hidden" value="<s:property value='significance'/>"/>
    <div id="wrap"  style="width: 98%">
        <div id='calendar'  >
        </div>
        <div id="reserveinfo" title="Details">
            <div id="revtitle">
            </div>
            <div id="revdesc">
            </div>
        </div>
        <div style="display: none" id="reservebox" title="Reserve meeting room">
            <form id="reserveformID" method="post">
            <div class="sysdesc">
                &nbsp;</div>
            <div class="rowElem"  style="display:none">
                <label>
                    标题:</label>
                <input id="title"  type="hidden"   maxlength="50" name="start"><span    id="biaoti"  style="color:#FF0000"    >&nbsp;*</span>
            </div>
            <div class="rowElem"  style="display: none;">
                <label>
                    重要程度:</label>
                <input id="chengdu" name="start">
            </div>
            <div class="rowElem"  style="display:none">
                <label>
                    开始时间:</label>
                <span  type="hidden"  id="shijian"></span><input   type="hidden"  readonly="readonly"  style="width:145px;" id="start" name="start"><span   id="kaishi"  style="color:#FF0000"    >&nbsp;*</span>
            </div>
            <div class="rowElem"  style="display:none">
                <label>
                    结束时间:</label>
               <input id="end"   type="hidden"  readonly="readonly" name="end"><span    type="hidden" id="jieshu"  style="color:#FF0000"    >&nbsp;*</span>
            </div>
            <div class="rowElem">
                <label>
                    内容:</label>
                <textarea id="details" rows="15" cols="50" name="details"></textarea>
            </div>
            <div class="rowElem">
            </div>
            <div class="rowElem">
            </div>
            <div id="addhelper" class="ui-widget">
                <div style="padding-bottom: 5px; padding-left: 5px; padding-right: 5px; padding-top: 5px"
                    class="ui-state-error ui-corner-all">
                    <div id="addresult">
                    </div>
                </div>
            </div>
            </form>
        </div>
       
    </div>
</body>
</html>
