$(document).ready(function () { 
	var height = window.screen.height-260;  
	stView_layout = $('#commanagerbar').layout({  
	height: height 
	});
	reopen();
	showtab();
});

var bat_string1="体检单位管理";
var bat_string2="单位部门信息维护";
var bat_string3="体检单位层级变更";
var tabselid=0;
var count=0;
function showtab() {
	$('#compantmanager').tabs({
		height : "auto",
		onSelect : function(title) {
			if (title == bat_string1 && count != 0) {
				var reqUrl = "zybcompanyedit.action?id=" + tabselid + "";
				refreshTab({
					tabTitle : title,
					url : reqUrl
				});
			} else if (title == bat_string2) {
				count = 1;
				var reqUrl = "companydwmanager.action?id=" + tabselid + "";
				refreshTab({
					tabTitle : title,
					url : reqUrl
				});
			} else if (title == bat_string3) {
				count = 1;
				var reqUrl = "companychange.action?id=" + tabselid + "";
				refreshTab({
					tabTitle : title,
					url : reqUrl
				});
			}
		}
	});

	// var tab = $('#compantmanager').tabs('getSelected');
	if (!$('#compantmanager').tabs('exists', bat_string1)) {
		$('#compantmanager')
				.tabs(
						'add',
						{
							title : bat_string1,
							content : '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit"  onload="Javascript:SetWinHeightinn(this)"  frameborder="0"  src="zybcompanyedit.action?id='
									+ tabselid + '"></iframe>',
							closable : false
						});
	}

	if (!$('#compantmanager').tabs('exists', bat_string2)) {
		$('#compantmanager')
				.tabs(
						'add',
						{
							title : bat_string2,
							content : '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit"   onload="Javascript:SetWinHeightinn(this)"  frameborder="0"  src="companydwmanager.action?id='
									+ tabselid + '"></iframe>',
							closable : false
						});
	}

	if (!$('#compantmanager').tabs('exists', bat_string3)) {
		$('#compantmanager')
				.tabs(
						'add',
						{
							title : bat_string3,
							content : '<iframe scrolling="auto" width="100%" align="center" id="icompanyedit" name="icompanyedit"  onload="Javascript:SetWinHeightinn(this)"  frameborder="0"  src="companychange.action?id='
									+ tabselid + '"></iframe>',
							closable : false

						});
	}
	$("#compantmanager").tabs("select", bat_string1);
}



// 加载单位信息
function reopen(){
	var url = 'companychangshow.action';
	var data = {"name":$("#com_name").val()};
	load_post(url,data,showcomtree);
}

/**
 * 显示树形结构
 * @param data
 * @returns
 */
function showcomtree(data) {
	mydtree = new dTree('mydtree', '../../images/img/', 'no', 'no');
	mydtree.add(0, -1, "单位", "javascript:void(0)", "根目录", "_self", false);
	var l = data.length;
	for (var index = 0; index < l; index++) {
		if ((data[index].attributes == null) || (data[index].attributes == '')
				|| (data[index].attributes == '0')) {
			mydtree.add(data[index].id, 0, data[index].text,
					"javascript:f_getMenuzbOne(" + data[index].id + ")",
					data[index].text, "_self", false);
		} else {
			mydtree.add(data[index].id, data[index].attributes,
					data[index].text, "javascript:f_getMenuzbOne("
							+ data[index].id + ")", data[index].text, "_self",
					false);
		}
	}
	$("#depttree").html(mydtree.toString());

}


 var patamid;
 function f_getMenuzbOne(patamid) {
	tabselid = patamid;
	var reqUrl = "zybcompanyedit.action?id=" + patamid + "";
	refreshTab({
		tabTitle : bat_string1,
		url : reqUrl
	});
	$("#compantmanager").tabs("select", bat_string1);
}
 
 
 /**     
  * 刷新tab 
  * @cfg  
  *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
  *如果tabTitle为空，则默认刷新当前选中的tab 
  *如果url为空，则默认以原来的url进行reload 
  */  

 function refreshTab(cfg) {
	var refresh_tab = cfg.tabTitle ? $('#compantmanager').tabs('getTab',
			cfg.tabTitle) : $('#compantmanager').tabs('getSelected');
	if (refresh_tab && refresh_tab.find('iframe').length > 0) {
		var _refresh_ifram = refresh_tab.find('iframe')[0];
		var refresh_url = cfg.url ? cfg.url : _refresh_ifram.src;
		// _refresh_ifram.src = refresh_url;
		_refresh_ifram.contentWindow.location.href = refresh_url;
	}
}  
 
 function SetWinHeightinn(obj) {
	 var height = window.screen.height-300;  
	 obj.height = height;
	} 
 