document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');
    var calendar;

    initThemeChooser({
      init: function(themeSystem) {
        calendar = new FullCalendar.Calendar(calendarEl, {
        	plugins : [ 'bootstrap', 'interaction', 'dayGrid' ],
	        themeSystem: themeSystem,
	        header: {
	        	left: 'prevYear,prev,next,nextYear today',
	            center: 'title',
	            right : 'dayGridMonth,dayGridWeek,dayGridDay'
            },
            locale : 'zh-cn',
//          defaultDate: '2019-08-12',
            buttonText : {
            	today : '今天',
            	dayGridMonth : '月',
            	dayGridWeek : '周',
            	dayGridDay : '日'
//  		  	prev: '<',
//  		  	next: '>',
//  		  	prevYear:'<<',
//  		  	nextYear:'>>'
            },
  		  	firstDay : 1,
//        	weekNumbers: true,
  		  	weekMode : 'liquid',
  		  	navLinks: true, // can click day/week names to navigate views
  		  	editable: true,
  		  	eventLimit: true, // allow "more" link when too many events
  		  	selectable: true,
  		  	selectMirror: true,
  		  	select: function(arg) {
	  		  	if($("#schedule_eite").val() != '1'){
	        		return;
	        	}
	  		    $("#details").val(''); //内容 
      		    $("#id").val('');
		  		$.ajax({
		  			type: "POST", //使用post方法访问后台
	                url: "getSchedulePageList.action", //要访问的后台地址
	                data: {'scheduling_time':arg.startStr,'scheduling_end_time':arg.startStr}, //要发送的数据
	                success: function (data) {
	                	var obj=eval(data);
	                	if(obj.length > 0){
	                		$("#details").val(obj[0].title); //内容 
	                		$("#id").val(obj[0].id);
	                		$("#reservebox").dialog({
	                	        autoOpen: false,
	                	        height: 600,
	                	        width: 800,
	                	        title: '编辑' + arg.startStr,
	                	        modal: true,
	                	        position: "center",
	                	        draggable: true,
	                	        timeFormat: 'HH:mm{ - HH:mm}',
	                	        buttons: {
	                	            "关闭": function (){
	                	                $(this).dialog("close");
	                	                $('#title').val("");
	                	                $('#chengdu').val("");
	                	                $("#details").val(''); //内容 
	                	            },
	                	            "保存": function () {
	                	                var confid = $("#title").val(); //标题
	                	                var det = $("#details").val(); //内容 
	                	                var cd = $("#chengdu").val(); //重要程度 
	                	                var schdata = {'id':$("#id").val(),
	                	              		  		  'title': confid,
	                	              		  		  'scheduling_content': det, 
	                	              		  		  'significance': cd,
	                	              		  		  'scheduling_time': arg.startStr,
	                	              		  		  'scheduling_end_time': arg.startStr };
	                	                $.ajax({
	                	                    type: "POST", //使用post方法访问后台
	                	                    url: 'updateSchedulePage.action', //要访问的后台地址
	                	                    data: schdata, //要发送的数据
	                	                    success: function (data) {
	                	                        //对话框里面的数据提交完成,刷新日历
	                	                  	  calendar.refetchEvents();
	                	                      $("#details").val(''); //内容 
	                	              		  $("#id").val('');
	                	                      //刷新日历数据
	                	                      alert("保存成功!"); 
	                	                    }
	                	                });
	                	                $(this).dialog("close");
	                	            },
	                	            "删除": function () {
	                	                var aa = window.confirm("警告：确定要删除记录，删除后无法恢复！");
	                	                if (aa) {
	                	                    var para = { id: $("#id").val()};
	                	                    $.ajax({
	                	                        type: "POST", //使用post方法访问后台
	                	                        url: "deleteSchedulePage.action", //要访问的后台地址
	                	                        data: para, //要发送的数据
	                	                        success: function (data) {
	                	                            //对话框里面的数据提交完成，data为操作结果
	                	                        	calendar.refetchEvents();
	                	                            alert("删除成功");
	                	                            $("#details").val(''); //内容 
	                	                    		$("#id").val('');
	                	                        }
	                	                    });
	                	                    $(this).dialog("close");
	                	                }
	                	            }
	                	        }
	                	    });
	                	    $("#reservebox").dialog("open");
	                	}else{
	                		$("#reservebox").dialog({
	                	        autoOpen: false,
	                	        height: 600,
	                	        width: 800,
	                	        title: '编辑' + arg.startStr,
	                	        modal: true,
	                	        position: "center",
	                	        draggable: true,
	                	        timeFormat: 'HH:mm{ - HH:mm}',
	                	        buttons: {
	                	            "关闭": function (){
	                	                $(this).dialog("close");
	                	                $('#title').val("");
	                	                $('#chengdu').val("");
	                	                $("#details").val(''); //内容 
	                	            },
	                	            "保存": function () {
	                	                var confid = $("#title").val(); //标题
	                	                var det = $("#details").val(); //内容 
	                	                var cd = $("#chengdu").val(); //重要程度 
	                	                var schdata = {'title': confid,
	                	              		  		  'scheduling_content': det, 
	                	              		  		  'significance': cd,
	                	              		  		  'scheduling_time': arg.startStr,
	                	              		  		  'scheduling_end_time': arg.startStr };
	                	                $.ajax({
	                	                    type: "POST", //使用post方法访问后台
	                	                    url: 'saveSchedulePage.action', //要访问的后台地址
	                	                    data: schdata, //要发送的数据
	                	                    success: function (data) {
	                	                        //对话框里面的数据提交完成,刷新日历
	                	                  	  calendar.refetchEvents();
	                	                      $("#details").val(''); //内容 
	                	              		  $("#id").val('');
	                	                      //刷新日历数据
	                	                      alert("保存成功!"); 
	                	                    }
	                	                });
	                	                $(this).dialog("close");
	                	            },
	                	            
	                	        }
	                	    });
	                	    $("#reservebox").dialog("open");
	                	}
	                }
	            });
  		  	},
  		  	events: {
  		  		url: 'getSchedulePageList.action',
  		  		failure: function(view) {
  		  		}
  		  	},
  		  	loading: function(bool) {
  		  		document.getElementById('loading').style.display = bool ? 'block' : 'none';
  		  	},
  		  	editable: false,//判断该日程能否拖动
  		    eventClick: function(arg) {
  		    	if($("#schedule_eite").val() != '1'){
	        		return;
	        	}
  		    	$("#details").val(''); //内容 
        		$("#id").val('');
  		    	var d1 = new Date(arg.event.start);
  		        var startdate=d1.getFullYear() + '-' + (d1.getMonth() + 1) + '-' + d1.getDate();
  		      $.ajax({
		  			type: "POST", //使用post方法访问后台
	                url: "getSchedulePageList.action", //要访问的后台地址
	                data: {'scheduling_time':startdate,'scheduling_end_time':startdate}, //要发送的数据
	                success: function (data) {
	                	var obj=eval(data);
	                	if(obj.length > 0){
	                		$("#details").val(obj[0].title); //内容 
	                		$("#id").val(obj[0].id);
	                		$("#reservebox").dialog({
	                	        autoOpen: false,
	                	        height: 600,
	                	        width: 800,
	                	        title: '编辑' + startdate,
	                	        modal: true,
	                	        position: "center",
	                	        draggable: true,
	                	        timeFormat: 'HH:mm{ - HH:mm}',
	                	        buttons: {
	                	            "关闭": function (){
	                	                $(this).dialog("close");
	                	                $('#title').val("");
	                	                $('#chengdu').val("");
	                	                $("#details").val(''); //内容 
	                	            },
	                	            "保存": function () {
	                	                var confid = $("#title").val(); //标题
	                	                var det = $("#details").val(); //内容 
	                	                var cd = $("#chengdu").val(); //重要程度 
	                	                var schdata = {'id':$("#id").val(),
	                	              		  		  'title': confid,
	                	              		  		  'scheduling_content': det, 
	                	              		  		  'significance': cd,
	                	              		  		  'scheduling_time': startdate,
	                	              		  		  'scheduling_end_time': startdate };
	                	                $.ajax({
	                	                    type: "POST", //使用post方法访问后台
	                	                    url: 'updateSchedulePage.action', //要访问的后台地址
	                	                    data: schdata, //要发送的数据
	                	                    success: function (data) {
	                	                        //对话框里面的数据提交完成,刷新日历
	                	                  	  calendar.refetchEvents();
	                	                      $("#details").val(''); //内容 
	                	              		  $("#id").val('');
	                	                      //刷新日历数据
	                	                      alert("保存成功!"); 
	                	                    }
	                	                });
	                	                $(this).dialog("close");
	                	            },
	                	            "删除": function () {
	                	                var aa = window.confirm("警告：确定要删除记录，删除后无法恢复！");
	                	                if (aa) {
	                	                    var para = { id: $("#id").val()};
	                	                    $.ajax({
	                	                        type: "POST", //使用post方法访问后台
	                	                        url: "deleteSchedulePage.action", //要访问的后台地址
	                	                        data: para, //要发送的数据
	                	                        success: function (data) {
	                	                            //对话框里面的数据提交完成，data为操作结果
	                	                        	calendar.refetchEvents();
	                	                            alert("删除成功");
	                	                            $("#details").val(''); //内容 
	                	                    		$("#id").val('');
	                	                        }
	                	                    });
	                	                    $(this).dialog("close");
	                	                }
	                	            }
	                	        }
	                	    });
	                	    $("#reservebox").dialog("open");
	                	}else{
	                		$("#reservebox").dialog({
	                	        autoOpen: false,
	                	        height: 600,
	                	        width: 800,
	                	        title: '编辑' + startdate,
	                	        modal: true,
	                	        position: "center",
	                	        draggable: true,
	                	        timeFormat: 'HH:mm{ - HH:mm}',
	                	        buttons: {
	                	            "关闭": function (){
	                	                $(this).dialog("close");
	                	                $('#title').val("");
	                	                $('#chengdu').val("");
	                	                $("#details").val(''); //内容 
	                	            },
	                	            "保存": function () {
	                	                var confid = $("#title").val(); //标题
	                	                var det = $("#details").val(); //内容 
	                	                var cd = $("#chengdu").val(); //重要程度 
	                	                var schdata = {'title': confid,
	                	              		  		  'scheduling_content': det, 
	                	              		  		  'significance': cd,
	                	              		  		  'scheduling_time': startdate,
	                	              		  		  'scheduling_end_time': startdate };
	                	                $.ajax({
	                	                    type: "POST", //使用post方法访问后台
	                	                    url: 'saveSchedulePage.action', //要访问的后台地址
	                	                    data: schdata, //要发送的数据
	                	                    success: function (data) {
	                	                        //对话框里面的数据提交完成,刷新日历
	                	                  	  calendar.refetchEvents();
	                	                      $("#details").val(''); //内容 
	                	              		  $("#id").val('');
	                	                      //刷新日历数据
	                	                      alert("保存成功!"); 
	                	                    }
	                	                });
	                	                $(this).dialog("close");
	                	            },
	                	            
	                	        }
	                	    });
	                	    $("#reservebox").dialog("open");
	                	}
	                }
  		      });
  		    }
        });
        calendar.render();
      	},
      	change: function(themeSystem) {
      		calendar.setOption('themeSystem', themeSystem);
      	}
    	});
	});
function saveschedul(startdate){
	$("#reservebox").dialog({
        autoOpen: false,
        height: 400,
        width: 500,
        title: '编辑' + startdate,
        modal: true,
        position: "center",
        draggable: true,
        timeFormat: 'HH:mm{ - HH:mm}',
        buttons: {
            "关闭": function (){
                $(this).dialog("close");
                $('#title').val("");
                $('#chengdu').val("");
                $("#details").val(''); //内容 
            },
            "保存": function () {
                var confid = $("#title").val(); //标题
                var det = $("#details").val(); //内容 
                var cd = $("#chengdu").val(); //重要程度 
                var schdata = {'title': confid,
              		  		  'scheduling_content': det, 
              		  		  'significance': cd,
              		  		  'scheduling_time': startdate,
              		  		  'scheduling_end_time': startdate };
                $.ajax({
                    type: "POST", //使用post方法访问后台
                    url: 'saveSchedulePage.action', //要访问的后台地址
                    data: schdata, //要发送的数据
                    success: function (data) {
                        //对话框里面的数据提交完成,刷新日历
                  	  calendar.refetchEvents();
                      $("#details").val(''); //内容 
              		  $("#id").val('');
                      //刷新日历数据
                      alert("保存成功!"); 
                    }
                });
                $(this).dialog("close");
            },
            
        }
    });
    $("#reservebox").dialog("open");
}
function updateschedul(startdate){
	$("#reservebox").dialog({
        autoOpen: false,
        height: 400,
        width: 500,
        title: '编辑' + startdate,
        modal: true,
        position: "center",
        draggable: true,
        timeFormat: 'HH:mm{ - HH:mm}',
        buttons: {
            "关闭": function (){
                $(this).dialog("close");
                $('#title').val("");
                $('#chengdu').val("");
                $("#details").val(''); //内容 
            },
            "保存": function () {
                var confid = $("#title").val(); //标题
                var det = $("#details").val(); //内容 
                var cd = $("#chengdu").val(); //重要程度 
                var schdata = {'id':$("#id").val(),
              		  		  'title': confid,
              		  		  'scheduling_content': det, 
              		  		  'significance': cd,
              		  		  'scheduling_time': startdate,
              		  		  'scheduling_end_time': startdate };
                $.ajax({
                    type: "POST", //使用post方法访问后台
                    url: 'updateSchedulePage.action', //要访问的后台地址
                    data: schdata, //要发送的数据
                    success: function (data) {
                        //对话框里面的数据提交完成,刷新日历
                  	  calendar.refetchEvents();
                      $("#details").val(''); //内容 
              		  $("#id").val('');
                      //刷新日历数据
                      alert("保存成功!"); 
                    }
                });
                $(this).dialog("close");
            },
            "删除": function () {
                var aa = window.confirm("警告：确定要删除记录，删除后无法恢复！");
                if (aa) {
                    var para = { id: $("#id").val()};
                    $.ajax({
                        type: "POST", //使用post方法访问后台
                        url: "deleteSchedulePage.action", //要访问的后台地址
                        data: para, //要发送的数据
                        success: function (data) {
                            //对话框里面的数据提交完成，data为操作结果
                        	calendar.refetchEvents();
                            alert("删除成功");
                            $("#details").val(''); //内容 
                    		$("#id").val('');
                        }
                    });
                    $(this).dialog("close");
                }
            }
        }
    });
    $("#reservebox").dialog("open");
}

function initThemeChooser(settings) {
	var isInitialized = false;
	var currentThemeSystem; // don't set this directly. use setThemeSystem
	var currentStylesheetEl;
	var loadingEl = document.getElementById('loading');
	var systemSelectEl = document
			.querySelector('#theme-system-selector select');
	var themeSelectWrapEls = Array.prototype.slice.call( // convert to real
															// array
	document.querySelectorAll('.selector[data-theme-system]'));

	systemSelectEl.addEventListener('change', function() {
		setThemeSystem(this.value);
	});

	setThemeSystem(systemSelectEl.value);

	themeSelectWrapEls.forEach(function(themeSelectWrapEl) {
		var themeSelectEl = themeSelectWrapEl.querySelector('select');

		themeSelectWrapEl.addEventListener('change', function() {
			setTheme(currentThemeSystem,
					themeSelectEl.options[themeSelectEl.selectedIndex].value);
		});
	});

	function setThemeSystem(themeSystem) {
		var selectedTheme;

		currentThemeSystem = themeSystem;

		themeSelectWrapEls
				.forEach(function(themeSelectWrapEl) {
					var themeSelectEl = themeSelectWrapEl
							.querySelector('select');

					if (themeSelectWrapEl.getAttribute('data-theme-system') === themeSystem) {
						selectedTheme = themeSelectEl.options[themeSelectEl.selectedIndex].value;
						themeSelectWrapEl.style.display = 'inline-block';
					} else {
						themeSelectWrapEl.style.display = 'none';
					}
				});

		setTheme(themeSystem, selectedTheme);
	}

	function setTheme(themeSystem, themeName) {
		var stylesheetUrl = generateStylesheetUrl(themeSystem, themeName);
		var stylesheetEl;

		function done() {
			if (!isInitialized) {
				isInitialized = true;
				settings.init(themeSystem);
			} else {
				settings.change(themeSystem);
			}

			showCredits(themeSystem, themeName);
		}

		if (stylesheetUrl) {
			stylesheetEl = document.createElement('link');
			stylesheetEl.setAttribute('rel', 'stylesheet');
			stylesheetEl.setAttribute('href', stylesheetUrl);
			document.querySelector('head').appendChild(stylesheetEl);

			loadingEl.style.display = 'inline';

			whenStylesheetLoaded(stylesheetEl, function() {
				if (currentStylesheetEl) {
					currentStylesheetEl.parentNode
							.removeChild(currentStylesheetEl);
				}
				currentStylesheetEl = stylesheetEl;
				loadingEl.style.display = 'none';
				done();
			});
		} else {
			if (currentStylesheetEl) {
				currentStylesheetEl.parentNode.removeChild(currentStylesheetEl);
				currentStylesheetEl = null
			}
			done();
		}
	}

	function generateStylesheetUrl(themeSystem, themeName) {
		if (themeSystem === 'bootstrap') {
			if (themeName) {
				return 'https://bootswatch.com/4/' + themeName
						+ '/bootstrap.min.css';
			} else { // the default bootstrap theme
				return 'https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css';
			}
		}
	}

	function showCredits(themeSystem, themeName) {
		var creditId;

		if (themeSystem.match('bootstrap')) {
			if (themeName) {
				creditId = 'bootstrap-custom';
			} else {
				creditId = 'bootstrap-standard';
			}
		}

		Array.prototype.slice.call( // convert to real array
		document.querySelectorAll('.credits')).forEach(function(creditEl) {
			if (creditEl.getAttribute('data-credit-id') === creditId) {
				creditEl.style.display = 'block';
			} else {
				creditEl.style.display = 'none';
			}
		})
	}

	function whenStylesheetLoaded(linkNode, callback) {
		var isReady = false;

		function ready() {
			if (!isReady) { // avoid double-call
				isReady = true;
				callback();
			}
		}

		linkNode.onload = ready; // does not work cross-browser
		setTimeout(ready, 2000); // max wait. also handles browsers that
									// don't support onload
	}
}