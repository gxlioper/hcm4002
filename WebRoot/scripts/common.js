$(function(){
	$("#north").load("header.html");
	$("#west").load("menu.html");
	$("#content").css("min-height",$(window).height()-$("#header").height()-$("#footer").height());
	$(".custom-item dl").hover(function(){
		$(this).parent().find(".item-a").addClass("item-a-hover");
		$(this).parent().find(".ico-act").show();
	},function(){
		$(this).parent().find(".item-a").removeClass("item-a-hover");
		$(this).parent().find(".ico-act").hide();
	})
	$(".custom-item dl").click(function(){
		window.location=$(this).attr('url');
	})
	//重写datagrid键盘↑↓选择enter选中
	$.extend($.fn.datagrid.methods, {
	    keyCtr : function (jq) {
	        return jq.each(function () {
	            var grid = $(this);
	            grid.datagrid('getPanel').panel('panel').attr('tabindex', 1).bind('keydown', function (e) {
	                switch (e.keyCode) {
	                case 38: // up
	                    var selected = grid.datagrid('getSelected');
	                    if (selected) {
	                        var index = grid.datagrid('getRowIndex', selected);
	                        grid.datagrid('selectRow', index - 1);
	                    } else {
	                        var rows = grid.datagrid('getRows');
	                        grid.datagrid('selectRow', rows.length - 1);
	                    }
	                    break;
	                case 40: // down
	                    var selected = grid.datagrid('getSelected');
                        if (selected) {
                        	 var index = grid.datagrid('getRowIndex', selected);
 	                         grid.datagrid('selectRow', index+1);
 	                        console.log("row--"+(index+1));
                        } else {
                            grid.datagrid('selectRow', 0);
                        }
	                    break;
	                case 13://enter
	                	var row = grid.datagrid('getSelected');
	                	row.set_num = '0';
						if (!isFloat($("#discount").val())) {
							alert('折扣率格式错误！');
							document.getElementById("discount").focus();
						} else if (Number($("#discount").val()) > 10) {
							alert('折扣率不能大于10！');
							document.getElementById("discount").focus();
						} else if (($("#discount").val() == '10')
								|| ($("#discount").val() == '10.0')
								|| ($("#discount").val() == '10.00')) {
							row.discount = '10';
							row.itemnum=1;
							row.amount = decimal(row.item_amount
									* $("#discount").val() / 10, 2);							
							deltiemflags=1;
							insertitem(row);
						} else {
							row.discount = $("#discount").val();
							row.itemnum=1;
							row.amount = decimal(row.item_amount
									* $("#discount").val() / 10, 2);
							deltiemflags=1;
							insertitem(row);
						}
						$('#itemname').focus(); 
						$("#itemname").select();
	                	break;
	                }
	            });
	        });
	    },
	});
	//validate验证
	$.extend($.fn.validatebox.defaults.rules,{
		//验证密码是否一致
			equals:{
				validator:function(value,param){
					return value==$(param[0]).val();
				},
				message:'两次输入的密码不一致'
			},
			//验证银行卡是否一致
			equals_bank:{
				validator:function(value,param){
					return value==$(param[0]).val();
				},
				message:'两次输入的银行卡号不一致'
			},
			//不能输入中文
			CHS:{
				validator:function(value,param){
				
					return !(/[\u0391-\uFFE5]/g.test(value));
					
				},
				message:'不能输入中文'
				
			},
			//只能输入数字
			IsNumber:{
				validator:function(value,param){
				
					return (/^[0-9]{1,20}$/.test(value));
					
				},
				message:'只能输入数字'
				
			},
			//金额判断
			IsNumber_Amt:{
				validator:function(value,param){
				
					var reg=/^\d+(\.\d\d)?$/;
					 var reg1=/^\d+(\.\d)?$/;
					if( reg.test( value ) )
					{ 
					  return true;
					}else{
					if (reg1.test(value))
					  {
					   return true;
					  }else
					  {
					   return false;
					  }
					}
					
				},
				message:'输入金额格式错误'
				
			},
			minLength: {   
		        validator: function(value, param){   
		            return value.length >= param[0];   
		        },   
		        message: '请输入至少 {0} 个字符.'  
		    } ,
		    maxLength: {   
		        validator: function(value, param){   
		            return value.length <= param[0];   
		        },   
		        message: '至多输入 {0} 个字符.'  
		    } ,
			Length: {   
		        validator: function(value, param){   
		            return value.length == param[0];   
		        },   
		        message: '只能输入 {0} 个字符或数字.'  
		    }, 
		    //输入参数必须为字符或数字
		    Lengthszzm: {   
		        validator: function(value, param){ 
		           var reg = /^[0-9a-zA-Z]+$/;
					if(reg.test( value )&&value.length == param[0] )
					{ 
					  return true;
					}else{
					   return false;
					
					}  
		        },   
		        message: '只能输入 {0} 个字符或数字.'  
		    },
			//ip格式判断
			isValidIP:{
				validator:function(value,param){
				 var reg =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/ ;    
    				return reg.test(value);  
					
				},
				message:'输入的ip格式不正确'
				
			}     

		});
})

//---------------------------------------------保单验证基本方法----------------------------------------------------------------------------
//验证ip地址的正确性
function isValidIP(ip)     
{     
    var reg =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;   
    return reg.test(ip);     
} 
//验证时间010000
function isTime(str){
        var a = /^([01][0-9]|2[0-3])[0-5][0-9][0-5][0-9]$/;
       return a.test(str);
 }


function isEMail(str) {
  var reMail = /^(?:[a-z\d]+[_\-\+\.]?)*[a-z\d]+@(?:([a-z\d]+\-?)*[a-z\d]+\.)+([a-z]{2,})+$/i;
  return reMail.test(str)
}

//验证数字字母
function isSzzm(str){
        var a = /^[0-9a-zA-Z]+$/;
       return a.test(str);
}

//验证float
function isFloat(str){
   var a = /^[0-9]+.?[0-9]*$/;
   return a.test(str);
}

//num表示要四舍五入的数,v表示要保留的小数位数。  
function decimal(num,v)  
{  
    var vv = Math.pow(10,v);  
    return Math.round(num*vv)/vv;  
}   


//验证判断
function isJE(str){
	var reg=/^\d+(\.\d\d)?$/;
	 var reg1=/^\d+(\.\d)?$/;
	if(reg.test(str))
	{
	  return true;
	}else{
	  if (reg1.test(str))
	  {
	     return true;
	  }else
	  {
	    return false;
	  }
	}	
}

// 验证是否是数字
function isSZ(str){	
	return (/^[0-9]{1,20}$/.test(str));	
}

//验证是否是数字
function isSZZoo(str){	
	return (/^[1-9]{1,20}$/.test(str));	
}

//判断是否是字母
function isZwzm(str){	
	return (/^[\u0391-\uFFE5]+$/.test(str));	
}

//判断日期
function isDate(str){
 if(str.match(/((^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(10|12|0?[13578])([-\/\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(11|0?[469])([-\/\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(0?2)([-\/\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([3579][26]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][13579][26])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][13579][26])([-\/\._])(0?2)([-\/\._])(29)$))/)==null){
   return false;
 }else{
   return true; 
 }
}

//判断时间
function isTime(str){
    if (str == null || str == '') { 
        return false;
    }
    var a = str.match(/^(\d{1,2})(:)(\d{1,2})$/);
    if (a == null) {
        a = str.match(/^(\d{1,2})(：)(\d{1,2})$/);
    }
    if (a == null) {
        return false;
    }
    if (a[1] >= 24 || a[3] >= 60) {
        return false
    }
    return true;
}

   function f_getFathermenu(param) { 
	    $.post("getMenuzbByid.action?id="+param,"ok",  
	        function (data) {
	            f_showFathermenu(data);  
	         },"json");  
	} 

	function f_showFathermenu(obj) { 
  		var fathermenu = obj;
  		$("#father_name").text(fathermenu.name);
  		$("#father_iconurl").addClass(fathermenu.icon_url);
  		//alert(fathermenu.name);
  		//$("#lmid").val(editfbxx.lmid);
	}  
	
	
//--------------------------------------------------------------------------------------------------------------
	//数据请求
	function load_post_noload(url, datas, callback) {
		$.ajax({
			type : "post",
			url : url,
			dataType : "json",
			data : datas,
			success : function(rest) {
				var listrest=eval(rest);
				if (listrest != null) {
					callback(listrest);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (XMLHttpRequest.status != "200" && XMLHttpRequest.status != "0") {
					var ready = "未初始化";
					if (XMLHttpRequest.readyState == 1)
						ready = '载入数据中';
					if (XMLHttpRequest.readyState == 1)
						ready = '载入已完成';
					if (XMLHttpRequest.readyState == 1)
						ready = '数据交互中';
					if (XMLHttpRequest.readyState == 1)
						ready = '交互完成';

					if (textStatus == "timeout")
						textStatus = "相应超时";
					if (textStatus == "error")
						textStatus = "程序出错";
					if (textStatus == "error")
						textStatus = "缓存未更新";
					if (textStatus == "parsererror")
						textStatus = "返回错误";

					var str = '<b style="font-size:18px; color:red">系统错误,请联系管理员解决!</b><br>错误代码：' + XMLHttpRequest.status + '<br>数据状态：' + ready + '<br>错误信息：' + textStatus;
					$.messager.alert("操作提示", str,"error");
				}
			}
		});
	}

	function load_post(url, datas, callback) {
		var nab = true;
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		if (nab) {
			$("body").prepend(str);
		}
		$.ajax({
			type : "post",
			url : url,
			dataType : "json",
			data : datas,
			success : function(rest) {
				nab = false;
				$(".loading_div").remove();
				var listrest=eval(rest);
				if (listrest != null) {
					callback(listrest);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (XMLHttpRequest.status != "200" && XMLHttpRequest.status != "0") {
					var ready = "未初始化";
					if (XMLHttpRequest.readyState == 1)
						ready = '载入数据中';
					if (XMLHttpRequest.readyState == 1)
						ready = '载入已完成';
					if (XMLHttpRequest.readyState == 1)
						ready = '数据交互中';
					if (XMLHttpRequest.readyState == 1)
						ready = '交互完成';

					if (textStatus == "timeout")
						textStatus = "相应超时";
					if (textStatus == "error")
						textStatus = "程序出错";
					if (textStatus == "error")
						textStatus = "缓存未更新";
					if (textStatus == "parsererror")
						textStatus = "返回错误";

					var str = '<b style="font-size:18px; color:red">系统错误,请联系管理员解决!</b><br>错误代码：' + XMLHttpRequest.status + '<br>数据状态：' + ready + '<br>错误信息：' + textStatus;
					$.messager.alert("操作提示", str,"error");
				} else {
					$(".loading_div").remove();
				}
			}
		});
	}

	//弹出框自动关闭
	function alert_autoClose(title,msg,icon){  
		 var interval;  
		 var time=500;  
		 var x=1;    //设置时间2s
		$.messager.alert(title,msg,icon,function(){});  
		 interval=setInterval(fun,time);  
		        function fun(){  
		      --x;  
		      if(x==0){  
		          clearInterval(interval);  
		  $(".messager-body").window('close');    
		       }  
		}; 
		}
	
	 /**
	* add by cgh
	* 针对panel window dialog三个组件拖动时会超出父级元素的修正
	* 如果父级元素的overflow属性为hidden，则修复上下左右个方向
	* 如果父级元素的overflow属性为非hidden，则只修复上左两个方向
	* @param left
	* @param top
	* @returns
	*/

	var easyuiPanelOnMove = function(left, top) {
	var parentObj = $(this).panel('panel').parent();
	if (left < 0) {
	$(this).window('move', {
	left : 1
	});
	}
	if (top < 0) {
	$(this).window('move', {
	top : 1
	});
	}
	var width = $(this).panel('options').width;
	var height = $(this).panel('options').height;
	var right = left + width;
	var buttom = top + height;
	var parentWidth = parentObj.width();
	var parentHeight = parentObj.height();
	if(parentObj.css("overflow")=="hidden"){
	if(left > parentWidth-width){
	$(this).window('move', {
	"left":parentWidth-width
	});
	}
	if(top > parentHeight-height){
	$(this).window('move', {
	"top":parentHeight-height
	});
	}
	}
	};
//	$.fn.panel.defaults.onMove = easyuiPanelOnMove;
//	$.fn.window.defaults.onMove = easyuiPanelOnMove;
//	$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
	
	var runserurl;
	function RunServerExe(runserurl){
		location.href=runserurl;
	}
	
	function setCookie(name,value)
	{
		var exp  = new Date();
		exp.setTime(exp.getTime() + 24*60*60*1000);
		document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/ ";
		//alert_all("写入")
	}
	function delCookie(name) 
	{ 
	    var exp = new Date(); 
	    exp.setTime(exp.getTime() - 1000000); 
	    var cval=getCookie(name); 
	    if(cval!=null) 
	        document.cookie= name + "="+cval+";expires="+exp.toGMTString()+";path=/ ";
		//alert_all("删除")
	} 
	//读cookie
	 function getCookie(name)
	{

	    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
	    if(arr !=null) return unescape(arr[2]); return null;
		//alert_all("读取")
	}

/**
 * 表格数据导出   （需要导出的列，需增加 upload:'字段名称'属性）
 * @param id  表格datagrid的 id
 * @param isAll  true 表示导出全部、false表示导出当前页
 * @param filename   导出文件名称（目前不支持中文）
 * @param sheetname  
 */
function datagridExportExcel(id,isAll,filename,sheetname){
	if(filename == undefined || filename == ''){
		filename = 'report';
	}
	if(sheetname == undefined || sheetname == ''){
		sheetname = 'work1';
	}
	var options = $("#"+id).datagrid('options');
	var total = $('#'+id).datagrid('getData').total;
	var queryParams = options.queryParams;
	var url = 'http://'+window.location.host+'/'+options.url;
	var sortName = options.sortName;
	var order = options.sortOrder;
	if(sortName != null){
		queryParams.sort = sortName;
	}
	if(order != null){
		queryParams.order = order;
	}
	if(isAll){
		queryParams.page = 1;
		queryParams.rows = total;
	}
	var obj = new Object();
	var fields = $("#"+id).datagrid('getColumnFields');
    for (var i = 0; i < fields.length; i++) {
        var option = $("#"+id).datagrid('getColumnOption', fields[i]);
        if(option.upload != null && option.upload != ''){
        	obj[option.upload] = option.title;
        }
    }
	window.location.href='datagridExportExcelNew.action?excelFileName='+encodeURI(encodeURI(filename))+'&excelSheet='+encodeURI(encodeURI(sheetname))+'&excelurl='+url+'&queryParams='+encodeURI(encodeURI(JSON.stringify(queryParams)))+'&excelTitle='+encodeURI(encodeURI(JSON.stringify(obj)));
}
//文本框 textarea 自适应高度调用函数  调用方式 $(obj).textareaAutoHeight({minHeight:20,maxHeight:300});
$.fn.extend({
    textareaAutoHeight: function(options) {
        this._options = {
            minHeight: 0,
            maxHeight: 1000
        }

        this.init = function() {
            for (var p in options) {
                this._options[p] = options[p];
            }
            if (this._options.minHeight == 0) {
                this._options.minHeight = parseFloat($(this).height());
            }
            for (var p in this._options) {
                if ($(this).attr(p) == null) {
                    $(this).attr(p, this._options[p]);
                }
            }
            $(this).keyup(this.resetHeight).change(this.resetHeight)
                    .focus(this.resetHeight).blur(this.resetHeight);
            $(this).trigger('blur');
        }
        this.resetHeight = function() {
            var _minHeight = parseFloat($(this).attr("minHeight"));
            var _maxHeight = parseFloat($(this).attr("maxHeight"));

            if (!$.browser.msie) {
                $(this).height(0);
            }
            var h = parseFloat(this.scrollHeight);
            h = h < _minHeight ? _minHeight :h > _maxHeight ? _maxHeight : h;
            $(this).height(h).scrollTop(h);
            if (h >= _maxHeight) {
                $(this).css("overflow-y", "scroll");
            }
            else {
                $(this).css("overflow-y", "hidden");
            }
        }
        this.init();
    }
});

//----------------------------------------------------------------------
//<summary>
//限制只能输入数字 支持小数点
//</summary>
//----------------------------------------------------------------------
$.fn.onlyNum = function () {
 $(this).keypress(function (event) {
	 var keynum;
     var keychar;
     var eventObj = event || e;
     var keynum = eventObj.keyCode || eventObj.which;
     if(keynum != 0){
    	 if(keynum == 8){ //删除按钮
    		 return true;
    	 }else if(keynum == 46){//输入小数点
    		 if($(this).val().indexOf('.') <= -1 && $(this).val().length != 0){
    			 return true;
    		 }
    		 return false;
    	 }else if(keynum == 45){//输入负数
    		 if($(this).val().indexOf('.') <= -1 && $(this).val().length == 0){
    			 return true;
    		 }
    		 return false;
    	 }else if(keynum >=48 && keynum <= 57){//是数字
    		 return true;
    	 }else{
    		 return false; 
    	 }
     }else{
     	return false;
     }
 }).focus(function () {
 //禁用输入法
     this.style.imeMode = 'disabled';
 }).bind("paste", function () {
 //获取剪切板的内容
     var clipboard = window.clipboardData.getData("Text");
     if (/^\d+$/.test(clipboard))
         return true;
     else
         return false;
 });
};


//弹出框自动关闭
function alert_autoClosep(title, msg, icon,fn) {
	var interval;
	var time = 1000;
	var x = 1; //设置时间2s
	$.messager.alert(title, msg, icon);
	interval = setInterval(fun, time);
	function fun() {
		--x;
		if (x == 0) {
			clearInterval(interval);
			$(".messager-body").window('close');
			if(fn != null || fn != undefined){
				fn();
			}
		}
	}
}

/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param fldList 要合并table的id
4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
5         */
function MergeCells3(tableID, fldList,zhuIndex) {
	var Arr = fldList.split(",");
	var dg = $('#' + tableID);
	var fldName;
	var RowCount = dg.datagrid("getRows").length;
	var span;
	var PerValue = "";
	var CurValue = "";
	var length = Arr.length - 1;
	for (i = length; i >= 1; i--) {
		z_fldName = Arr[zhuIndex];
		fldName = Arr[i];
		PerValue = "";
		span = 1;
		for (row = 0; row <= RowCount; row++) {
			if (row == RowCount) {
				CurValue = "";
			}else {
				CurValue = dg.datagrid("getRows")[row][z_fldName];
			}
			if (PerValue == CurValue) {
				span += 1;
			}else {
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

