$(document).ready(function () {
	f_getchangerurltype();
	reopen();
});

function f_getchangerurltype()
{

 if($("#urltype").val()==1)
 {
   $("#wbdz_id").hide();
   $("#dygn_id").show();
 }
 if($("#urltype").val()==2)
 {
   $("#dygn_id").hide();
   $("#wbdz_id").show();
 }
}

function reopen() {
	f_getGnMenuOne("");
	f_getGnList("");
	$("#tree").tree({
		showcheck : false,
		url : "treeShow.action",
		animate : true,
		onClick : function(item) {
			f_getMenuzbOne(item.id);
		}
	});
	$('div.middle-center').layout({
		center__paneSelector : ".inner-center",
		west__paneSelector : ".inner-west",
		east__paneSelector : "false",
		north__paneSelector : "false",
		south__paneSelector : "false",
		west__size : 194,
		spacing_open : 5,
		spacing_closed : 5,
		west__spacing_closed : 5
	});
	$("#weastCotent").height($('div.middle-center').height() - 35);
} 
 function f_getMenuzbOne(patam){
       if (patam=='0')
       {
          $("#rootid").attr("value","root");
          $("#menuname").attr("value","");
          $("#otherurl").attr("value","");
          $("#indexid").attr("value","");
       }else{
       $.post("getMenuzbOne.action?id="+patam+"", "ok", function (data) {
			var obj;
			obj = eval(data);
			f_getMenuzbOneview(obj);
		}, "json");
		}
   }
 
    function f_getMenuzbOneview(obj)
    {

    	 var menulist = $("#menulist").combobox("getData");
			for(i=0;i<menulist.length;i++){				
				if(menulist[i].id == obj.father_id){
					$("#menulist").combobox("setValue",menulist[i].id);
				}
			}
    	 
			
			var gnlist = $("#gnlist").combobox("getData");
			for(i=0;i<gnlist.length;i++){				
				if(gnlist[i].id == obj.xtgn_id){
					$("#gnlist").combobox("setValue",gnlist[i].id);
				}
			}
			
			 $("#urltype").children("option").each(function(){
		           if($(this).val() == obj.url_type){ 
		              $(this).attr("selected","selected"); 
		           } 
		        });
      
		$("#usertype").attr("value",obj.usertype);

        $("#rootid").attr("value",obj.id);
        $("#menuname").attr("value",obj.name);
        $("#otherurl").attr("value",obj.other_url);
        $("#indexid").attr("value",obj.indexid);
    }
 
    
	function f_getGnMenuOne(param) {
		$('#menulist').combobox({
			url : 'getGnMenuOne.action?language='+$("#language").val(),
			editable : true, //不可编辑状态
			cache : false,
			panelHeight : '230',//自动高度适合
			valueField : 'id',
			multiple:false,
			textField : 'remark',
			onLoadSuccess : function(data) {
				if((data!=undefined)&&(data.length>0)){
					$('#menulist').combobox('setValue', data[0].id);	
				}
			},
			filter: function(q, row){
				var opts = $(this).combobox('options');
				var text = row[opts.textField];//下拉的对应选项的汉字
				var pyjp = pinyinUtil.getFirstLetter(text).toLowerCase();
		 		if(row[opts.textField].indexOf(q) > -1 || pyjp.indexOf(q.toLowerCase()) > -1){
		 			return true;
		 		}	
			}
		});
		
		/*$.post("getGnMenuOne.action?language="+$("#language").val(), "ok", function (data) {
			var obj;
			obj = eval(data);
			f_getGnMenuOneview(obj);
		}, "json");*/
	}
	
//
//	function f_getGnList(param) {
//		$.post("getGnList.action", "ok", function (data) {
//			var obj;
//			obj = eval(data);
//			f_getGnListview(obj);
//		}, "json");
//	}
	
	function f_getGnListview(obj) {
		var getGnListview = $("#gnlist");
		getGnListview.empty();//
		$("<option value='0'>不对应功能</option>").appendTo(getGnListview);
		for (var i = 0; i < obj.length; i++) {
			$("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>").appendTo(getGnListview);
		}
	}
	
	
	$('#menulist').change(function(){ 
	    getmenulist();
	 })
	
function getmenulist()
{
   var checkValue=$("#menulist").val(); 
   $("#rootid").attr("value",checkValue);
   f_getMenuzbOne(checkValue);
}

$('#gnlist').change(function(){ 
	getmenuname();
	})
	
function getmenuname()
{
   var checkText=$("#gnlist").find("option:selected").text(); 
   $("#menuname").attr("value",checkText);
}
function f_getGnList(param) {
	
	$('#gnlist').combobox({
		url : 'getGnList.action?language='+$("#language").val(),
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '230',//自动高度适合
		valueField : 'id',
		multiple:false,
		textField : 'name',
		onLoadSuccess : function(data) {
			if((data!=undefined)&&(data.length>0)){
				$('#gnlist').combobox('setValue', data[0].id);	
			}
		},
		filter: function(q, row){
			var opts = $(this).combobox('options');
			var text = row[opts.textField];//下拉的对应选项的汉字
			var pyjp = pinyinUtil.getFirstLetter(text).toLowerCase();
	 		if(row[opts.textField].indexOf(q) > -1 || pyjp.indexOf(q.toLowerCase()) > -1){
	 			return true;
	 		}	
		}
	});
	
	/*$.post("getGnList.action?language="+$("#language").val(), "ok", function (data) {
		var obj;
		obj = eval(data);
		f_getGnListview(obj);
	}, "json");*/
}

