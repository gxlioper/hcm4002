/*$(function(){
    $("#header").load("header.html");
    $("#footer").load("footer.html");
	$('#mainbox').css('min-height',document.documentElement.clientHeight-193+'px');
	if($.browser.msie){
		if($.browser.version == '6.0'){
			$('#mainbox').css('height',document.documentElement.clientHeight-193+'px');
		}
		
	}
	
//查询信息
	$("#showQueryDrop").powerFloat({
		offsets:{x:10,y:-26},
		zIndex:9,
		position:"3-2"
	});

//Tab 切换
	$('#cardBaseInfo').Tabs({
		event:'click'
	});
	$('#newsInfo').Tabs({
		event:'click'
	});		

})*/

function getFlexigridCol(resAry ,colAry){
    var i = 0;
	var cols = new Array();
	
	for(var i1 in colAry){
	    if($.inArray(colAry[i1].name.toLowerCase(), resAry) != -1){
	    	cols[i] = colAry[i1];
	    	i++;
	    }
	}
	
	return cols;
}

function getGridCol(url){
     return eval($.ajax({url:url,async:false,dataType:"json",type:"POST"}).responseText);
}

/*function initDatepicker(ids){
    var d = new Date();
    d.setTime(d.getTime()-(1000*60*60*24));
    var daystr = d.getFullYear()+"-"+((d.getMonth()+1)<10?("0"+(d.getMonth()+1)):(d.getMonth()+1))+"-"+(d.getDate()<10?"0"+d.getDate():d.getDate());
   
	for(var i in ids){
		$("#"+ids[i]).datepicker({ defaultDate:-1,picker: "<img class='picker' align='middle' src='themes/default/ecard/images/dp/s.gif' alt=''/>" });
		$("#"+ids[i]).attr("value",daystr);
	}
}*/

function initDatebox(ids){
    var d = new Date();
    d.setTime(d.getTime()-(1000*60*60*24));
    var daystr = d.getFullYear()+"-"+((d.getMonth()+1)<10?("0"+(d.getMonth()+1)):(d.getMonth()+1))+"-"+(d.getDate()<10?"0"+d.getDate():d.getDate());
	
	for(var i in ids){
		$("#"+ids[i]).datebox("setValue", daystr); 
	}
}

/*对象序列化为字符串*/
String.toSerialize = function (obj) {
    var ransferCharForJavascript = function (s) {
        var newStr = s.replace(
/[\x26\x27\x3C\x3E\x0D\x0A\x22\x2C\x5C\x00]/g,
function (c) {
    ascii = c.charCodeAt(0)
    return '\\u00' + (ascii < 16 ? '0' + ascii.toString(16) : ascii.toString(16))
}
);
        return newStr;
    }
    if (obj == null) {
        return null
    }
    else if (obj.constructor == Array) {
        var builder = [];
        builder.push("[");
        for (var index in obj) {
            if (typeof obj[index] == "function") continue;
            if (index > 0) builder.push(",");
            builder.push(String.toSerialize(obj[index]));
        }
        builder.push("]");
        return builder.join("");
    }
    else if (obj.constructor == Object) {
        var builder = [];
        builder.push("{");
        var index = 0;
        for (var key in obj) {
            if (typeof obj[key] == "function") continue;
            if (index > 0) builder.push(",");
            builder.push("\"" + key + "\":" + String.toSerialize(obj[key]) + "");
            index++;
        }
        builder.push("}");
        return builder.join("");
    }
    else if (obj.constructor == Boolean) {
        return obj.toString();
    }
    else if (obj.constructor == Number) {
        return obj.toString();
    }
    else if (obj.constructor == String) {
        return '"' + ransferCharForJavascript(obj) + '"';
    }
    else if (obj.constructor == Date) {
        return '{"__DataType":"Date","__thisue":' + obj.getTime() - (new Date(1970, 0, 1, 0, 0, 0)).getTime() + '}';
    }
    else if (this.toString != undefined) {
        return String.toSerialize(obj);
    }
}
