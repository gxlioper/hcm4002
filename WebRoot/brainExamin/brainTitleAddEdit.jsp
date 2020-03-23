<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
	.formdiv dl dt{font-size: 15px;}
</style>
<script type="text/javascript">
var supID,level;
$(function(){
	var rowInfo = $("#titlelist").datagrid('getSelected'); //获得选中行
	
	//初始化下拉框
	$.ajax({
		url:'querySelectType.action',
		type:'post',
		//data: {},
		success:function(data){
			var message=eval("("+data+")");
			var select ="";
			for(var i=0;i<message.length;i++){
				//alert(message[i].quest_sub_name);
			    var div = 
					"<option value='"+message[i].quest_sub_code+"'>"+message[i].quest_sub_name+"</option>";
			    select += div;
			}
			$("#select_type").append(select);
			//$.parser.parse($("#select_type")); //渲染
			if(rowInfo!=null){
				//得到下拉框的value
				document.getElementById('select_type').value=rowInfo.quest_sub_code;
			}else{
				//新增
				document.getElementById('select_type').value = $("#title_type").val();
			}
		},
		error:function(){
			$.messager.alert('提示信息','初始化问卷类型失败','error');
		}
	});
	
	
	if($("#msg").val()=='edit' && rowInfo!=null){
		//console.log(rows);
		$("#title_id").val(rowInfo.titleID);
		//所属类别
		var leiBie;
		if(rowInfo.supID==0) leiBie = "顶级父标题";
		if(rowInfo.supID!=0) leiBie = rowInfo.supID;
		$("#supID").val(leiBie);
		//级别
		var jiBie;
		if(rowInfo.level==0) jiBie = "一级标题";
		if(rowInfo.level==1) jiBie = "二级标题";
		if(rowInfo.level==2) jiBie = "三级标题";
		$("#level").val(jiBie);
		//赋值
		if($("#title_ji").val()==0) supID = rowInfo.supID;
		if($("#title_ji").val()!=0) supID = $("#title_lei").val();
		level = rowInfo.level;
		
		$("#titleName").val(rowInfo.titleName);
		$("#seqNo").val(rowInfo.seqNo);
		
		$("input[type='radio'][name='isVis']").eq(rowInfo.isVisable).attr("checked",true);
		
		$("#isTitleColumn").val(rowInfo.titleColumn);
		if(rowInfo.titleColumn=='1') $("input[type='radio'][name='titleColumn']").eq(0).attr("checked",true);
		else $("input[type='radio'][name='titleColumn']").eq(1).attr("checked",true);
	} else{
		var isOk = document.getElementById('isOk');
		isOk.checked = true;
		if($("#title_ji").val()==0) $("#level").val("一级标题");
		if($("#title_ji").val()==1) $("#level").val("二级标题");
		if($("#title_ji").val()==2) $("#level").val("三级标题");
		
		if($("#title_ji").val()==0) supID = 0;
		if($("#title_ji").val()!=0 && $("#title_ji").val()!='' ) supID = $("#title_lei").val();
		
		//单选  多选 赋值
		$("input[type='radio'][name='titleColumn']").eq(0).attr("checked",true);
		
		var contText =  $("#titleText").text();
		if(contText.indexOf(">>")>=0){
			var index = contText .lastIndexOf(">>");  
			//包涵 截取 
			$("#supID").val(contText .substring(index + 4, contText.length));
		}else{
			$("#supID").val(contText);
		}
		
		level = $("#title_ji").val();
		
		$("#isTitleColumn").val(1);
		$("#isTitleColumn").attr("readonly",true);
	} 
});


//设置自定义模式
$("input:radio[name='titleColumn']").on('click', function(event){
    if($(this).val()!='1'){
 	    $("#isTitleColumn").attr("readonly",false);
    }else{
 	    $("#isTitleColumn").val(1);
    	$("#isTitleColumn").attr("readonly",true);
    }
}); 

	//提交数据
	function title_AddSave(){
		
		if($("#titleName").val()==''){
			//alert('角色名称不能为空！');
			$("#titleName").focus();
			return;
		}
		
		if($("#seqNo").val() === "" || $("#seqNo").val() ==null){
	        $.messager.alert('提示',' 顺序号不能为空 ');
	        return;
	    }
	    if(isNaN($("#seqNo").val())){
	    	$.messager.alert('提示',' 顺序号必须为数字 ');
	        return;
	    }
	    
		$.messager.confirm('确认','您确定要保存吗？',function(r){
		    if (r){
		    	//进入ajax提交
		    	title_submit();
		    }
		});
	}

	function title_submit(){
		 var selectValue = document.getElementById("select_type");
	     var laValue = selectValue.value;
	     
		 var isVisable;
	     if($('#isOk').is(":checked"))isVisable = 1;
	     else isVisable = 0;
	     //定义url
	     var titleUrl; 
	     if($("#msg").val()=='edit') titleUrl = "brainTitleAdd.action?titleID="+$("#title_id").val();
	     if($("#msg").val()=='add') titleUrl = "brainTitleAdd.action";
		 $.ajax({
			url:titleUrl,  
	        data:{
	        	titleName:$("#titleName").val().trim(),
	        	seqNo:$("#seqNo").val().trim(),
	        	isVisable:isVisable,
	        	supID:supID,
	        	Level:level,
	        	quest_sub_code:laValue,
	        	titleColumn:$("#isTitleColumn").val()
	        	},          
	          type: "post",//数据发送方式   
	          success: function(data){  
	             	var obj=eval("("+data+")");
	          		$.messager.alert('提示信息', obj,function(){});
	          	 	$("#dlg-addEdit").dialog("close");
	          	 	//加载表格信息
	          	 	if($("#title_ji").val()==0){
	          	 		showTitleList($("#title_type").val(),$("#titleText").text());
	          	 	}else{
	          	 		addBrainTitleTwo($("#title_lei").val(),$("#titleText").text(),"refresh",$("#title_ji").val());
	          	 	}
	            },
	            error:function(){
	            	 $("#dlg-addEdit").dialog("close");
	            	//alert("用户操作失败！");
	            	$.messager.alert('提示信息', "保存失败！",function(){});
	            }
	                 
		});
	}

</script>

	<br/>
	<div class="formdiv">
		<div style="display: none;">
			<input id="title_id" value=""/>
			<input id="msg" value="<s:property value="titleName"/>"/>
		</div>
		
		<dl>
			<dt>所属问卷类型：</dt>
			<dd>
				<select id="select_type" class="combobox" name="select_type" data-options="editable:false" 
					style="width:250px; height:28px;" >
				</select>
			</dd>
		</dl>
		<dl>
			<dt>标题名称：</dt>
			<dd><input type="text" class="textinput" name="titleName" id="titleName" value="" style="width:244px; "/> <strong class="red">*</strong></dd>
		</dl>
		<dl>
			<dt>所属类别：</dt>
			<dd><input type="text" class="textinput" name="supID" id="supID" style="width:244px;" value=""  readonly="readonly"/></dd>
		</dl>
		<dl>
			<dt>级别：</dt>
			<dd><input type="text" class="textinput" name="level" id="level" style="width:244px;" value=""  readonly="readonly"/></dd>
		</dl>
		<dl>
			<dt>顺序号：</dt>
			<dd><input id="seqNo"  type="text" class="textinput" style="width:243px; height: 20px;"/> <strong class="red">*</strong></dd>
		</dl>
		<dl>
			<dt >显示列数：</dt>
			<dd>
				<label for="isDefault">默认</label>
				<input id="isDefault" type="radio" name="titleColumn" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="isCustom">自定义</label>
				<input id="isCustom" type="radio" name="titleColumn" value=""/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input  id="isTitleColumn" class="textinput" style="width:75px; height: 20px;" type="text" />&nbsp;&nbsp;列
			</dd>
		</dl>
		
		<dl>
			<dt>是否显示：</dt>
			<dd style="margin-top: 6px;">
				<label for="isNo">否</label>
				<input id="isNo" type="radio" name="isVis" value="0"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="isOk">是</label>
				<input id="isOk" type="radio" name="isVis" value="1"/>
				
			</dd>
		</dl>
			
	</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javaScript:title_AddSave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-addEdit').dialog('close')">关闭</a>
	</div>
	
</div>