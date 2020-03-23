<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/scripts/plugins/upload/uploadify.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/upload/swfobject.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/upload/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/upload/jquery.uploadify.v2.1.4.min.js"></script>
<script>
var filestr;
var uploadflag=0;
var twoupload=0;
$(document).ready(function () {
	var batch_id =<s:property value="model.batch_id"/>;
	var company_id= <s:property value="model.company_id"/>;
	var userid=<s:property value="model.creater"/>;
    $('#file_upload').uploadify({
    	'uploader': '<%=request.getContextPath()%>/scripts/plugins/upload/uploadify.swf',
        'cancelImg': '<%=request.getContextPath()%>/scripts/plugins/upload/cancel.png',
        'scriptData':{  
            'batch_id':batch_id,
            'company_id':company_id,
            'userid':userid
        },    
       'script':'com/hjw/zyb/FsxzybUpload',
       'auto': false,
       'multi':true,
       'fileExt': '*.xlsx;*.xls', //允许上传的文件后缀
       'queueSizeLimit' : 1, //当允许多文件生成时，设置选择文件的个数，默认值：999 
       'sizeLimit': 20971520,  //上传文件的大小限制，单位为字节 100k
       'onSelect': function(e, queueId, fileObj)
			        {
			        	if(fileObj.size>20971520){
			        		alert("单个文件不能超过20M");
			        	}
			        },
        'buttonImg': '<%=request.getContextPath()%>/scripts/plugins/upload/btn_image.gif',
        'fileDesc': '文本表格文件',
        //'queueID': 'fileQueue', //文件队列的ID，该ID与存放文件队列的div的ID一致。
        'removeCompleted' : false,
        'onComplete': function(event, queueId, fileObj, response, data) {
        	$('#file_upload').uploadifyClearQueue();
        	$(".loading_div").remove();
        	uploadflag=0;
        	if (response.split("-")[0] == 'ok') {
				$.messager.alert("操作提示", response);
				getgroupGrid();
				$('#dlg-show').dialog('close');
			} else {
				$.messager.alert("操作提示", response, "error");
			}
        	
        },
        'onSelect': function (file) {twoupload=1; }, //选择文件时触发事件
        'onSelectError': function (file, errorCode, errorMsg) { }, //选择文件有误触发事件
        'onError': function(event, queueId, fileObj, errorObj) {
        	uploadflag=0;
        	$(".loading_div").remove();
            $.messager.alert("操作提示", errorObj.info, "error");
        }
    });
});

function updatefile(){
	if(twoupload==1){
	if(uploadflag==0){
		uploadflag=1;
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 twoupload=0;
	$('#file_upload').uploadifyUpload(); 
	}
	}else{
		$.messager.alert("操作提示", "请选择上传文件", "error");
	}
}

</script>
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batch_id"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>所选体检任务</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dd style="height: 20px; width: 80px;">单位名称</dd>
			<dd style="height: 20px; width: 280px;">
				<s:property value="model.comname" />
			</dd>
			<dd style="height: 20px; width: 100px;">体检任务名称：</dd>
			<dd style="height: 20px; width: 140px;">
				<s:property value="model.batch_name" />
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择文件</strong>
	</legend>
	<div class="user-query" >
	 <table>
        <tr>
            <td><input type="file" name="file_upload" id="file_upload"/></td>
        </tr>
        <tr>
            <td  colspan="2"><div id="fileQueue"></div></td>
        </tr>
        
   </table>
	</div>
	
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:updatefile();" class="easyui-linkbutton c6" style="width:100px;">上传文件</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-show').dialog('close')">关闭</a>
	</div>
</div>
