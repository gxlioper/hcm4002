$(document).ready(function () {
	dep_shu();
	showtab();
	b_dep_shu();
});
 //-------------------------------显示科室列表列表------------------------------------
/**
 * 科室树
 */
function dep_shu(zhi){
	$("#some_tree").tree({
		 url:'getDepartmentDepList.action',
		 queryParams:{
			 web_Resource:1
		 },
		 type:'post',
		 dataType:'json',
		 loadFilter :function(data,parent){
			 if(zhi!=""&&zhi!=undefined){
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].id,text:obj[i].dep_name});
				 }
				return newData;
			 }else{
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].id,text:obj[i].dep_name});
				 }
				 var newDate2 = [{id:'',text:'所有科室',children:newData}];
				 return newDate2;
			 }
		 },
		 onLoadSuccess:function(node,data){  
	           $("#some_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
	           var n = $("#some_tree").tree("getSelected");  
	           if(n!=null){  
	                $("#some_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
	           }  
        },
        onSelect:function(node){
        	var obj =  $(this).tree('getChildren',node.target);
        	//getgroupuserGrid(node.id);
        	//console.log(document.getElementById("getDepItemCharging"));
        	 $("#getDepItemDetail").tabs("select","收费项目管理");
        	document.getElementById("getDepItemCharging").contentWindow.getgroupuserGrid(node.id);
        }
	});
}
/**
 *多体检中心科室树
 */
function b_dep_shu(zhi){
	$("#b_some_tree").tree({
		 url:'getCenterDepartmentList.action',
		 type:'post',
		 dataType:'json',
		 loadFilter :function(data,parent){
			 if(zhi!=""&&zhi!=undefined){
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].id,text:obj[i].dep_name});
				 }
				return newData;
			 }else{
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].id,text:obj[i].dep_name});
				 }
				 var newDate2 = [{id:'',text:'所有科室',children:newData}];
				 return newDate2;
			 }
		 },
		 onLoadSuccess:function(node,data){  
	           $("#b_some_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
	           var n = $("#b_some_tree").tree("getSelected");  
	           if(n!=null){  
	                $("#b_some_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
	           }  
        },
        onSelect:function(node){
        	var obj =  $(this).tree('getChildren',node.target);
        	//getgroupuserGrid(node.id);
        	//console.log(document.getElementById("getDepItemCharging"));
        	$("#b_getDepItemDetail").tabs("select","收费项目管理");
        	document.getElementById("b_getDepItemCharging").contentWindow.b_getgroupuserGrid(node.id);
        }
	});
}

var bat_string1="收费项目";
var bat_string2="项目细项";
var count=0;
var count1=0;
var count2=0;
function showtab(){
	$('#getDepItemDetail,#b_getDepItemDetail').tabs({
		width:1300,    
	    height:600
	});
	//收费项目库
	if (!$('#getDepItemDetail').tabs('exists', bat_string1)) {
        $('#getDepItemDetail').tabs('add', {
            title: bat_string1,
            content: '<iframe scrolling="auto" width="100%" align="center" id="getDepItemCharging" name="getDepItemCharging" onload="Javascript:SetWinHeightinn(this)" frameborder="0" src="centerDepItemCharging.action" ></iframe>',
            closable: false
        });
    }
    if (!$('#getDepItemDetail').tabs('exists', bat_string2)) {
        $('#getDepItemDetail').tabs('add', {
            title: bat_string2,
            content: '<iframe scrolling="auto" width="100%" align="center" id="getDepItemChargingDetail" name="getDepItemChargingDetail" onload="Javascript:SetWinHeighIframe2(this)" frameborder="0" src="centerDepItemChargingDetail.action" ></iframe>',
            closable: false
        });
    }   	   
    $("#getDepItemDetail").tabs("select",bat_string1);
    //多体检中心
    if (!$('#b_getDepItemDetail').tabs('exists', bat_string1)) {
        $('#b_getDepItemDetail').tabs('add', {
            title: bat_string1,
            content: '<iframe scrolling="auto" width="100%" align="center" id="b_getDepItemCharging" name="b_getDepItemCharging" onload="Javascript:SetWinHeightinn(this)" frameborder="0" src="bCenterCharging.action" ></iframe>',
            closable: false
        });
    }
    if (!$('#b_getDepItemDetail').tabs('exists', bat_string2)) {
        $('#b_getDepItemDetail').tabs('add', {
            title: bat_string2,
            content: '<iframe scrolling="auto" width="100%" align="center" id="b_getDepItemChargingDetail" name="b_getDepItemChargingDetail" onload="Javascript:SetWinHeighIframe2(this)" frameborder="0" src="centerDepItemChargingDetail.action" ></iframe>',
            closable: false
        });
    }   	   
    $("#b_getDepItemDetail").tabs("select",bat_string1);
}

//选择tab2
function selectTab2(row){
	console.log(row);
	$("#getDepItemDetail").tabs("select",bat_string2);
	document.getElementById("getDepItemChargingDetail").contentWindow.leixing(row);
} 
//选择tab2
function bselectTab2(row){
	$("#b_getDepItemDetail").tabs("select",bat_string2);
	document.getElementById("b_getDepItemChargingDetail").contentWindow.leixing(row);
} 

 /**     
  * 刷新tab 
  * @cfg  
  *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
  *如果tabTitle为空，则默认刷新当前选中的tab 
  *如果url为空，则默认以原来的url进行reload 
  */  
 function refreshTab(cfg){  
     var refresh_tab = cfg.tabTitle?$('#getDepItemDetail').tabs('getTab',cfg.tabTitle):$('#getDepItemDetail').tabs('getSelected');  
     if(refresh_tab && refresh_tab.find('iframe').length > 0){  
     var _refresh_ifram = refresh_tab.find('iframe')[0];  
     var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
     //_refresh_ifram.src = refresh_url;  
     _refresh_ifram.contentWindow.location.href=refresh_url;  
     }  
 }  
 
 
function SetWinHeightinn(obj) {
	 var height = window.screen.height-170; 
	 obj.height = height;
}

function SetWinHeighIframe2(obj) {
	 var height = window.screen.height-170;  
	 obj.height = height;
}
 
