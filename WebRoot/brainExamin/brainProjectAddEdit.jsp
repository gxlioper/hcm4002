<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
	.formdiv dl dt{font-size: 15px;}
</style>
<script type="text/javascript">
var itemlevel,supItemId,titleID;
$(function(){
	
	var rowInfo = $("#questionlist").datagrid('getSelected'); //获得选中行
	//console.log(rowInfo);
	if($("#msg").val()=='edit' && rowInfo !=null){
		$("#item_id").val(rowInfo.itemId);
		$("#itemName").val(rowInfo.itemName);
		if(rowInfo.itemlevel==0) $("#itemlevel").val("顶级父项目");
		if(rowInfo.itemlevel==1) $("#itemlevel").val("父项目");
		if(rowInfo.itemlevel==2) $("#itemlevel").val("子项目");
		
		itemlevel = rowInfo.itemlevel;
		if(rowInfo.supItemId==0){supItemId = rowInfo.supItemId;}
		else{supItemId = $("#sup_item_id").val();}
		titleID = rowInfo.titleID;
		//级别为答案  显示默认值
		if(itemlevel==1){
			document.getElementById('is_default').style.display='block';
			//判断是否有默认值
			if(rowInfo.isDefault==1 && rowInfo.isMulSel==2){
				$("#defaultResult").val(rowInfo.defaultResult);
				document.getElementById('default_result').style.display='block';
			}
		}
		
		$("#linkNo").val(rowInfo.linkNo);
		
		var sex_all = document.getElementById('sex_all');
		var man = document.getElementById('man');
		var woman = document.getElementById('woman');
		
		if(rowInfo.sex=='全部') sex_all.checked = true;
		if(rowInfo.sex=='男') man.checked = true;
		if(rowInfo.sex=='女') woman.checked = true;
		
		//是否显示  赋值
		$("input[type='radio'][name='isVisa']").eq(parseInt(rowInfo.isVisable)).attr("checked",true);
		//单选  多选 赋值
		$("input[type='radio'][name='isXuan']").eq(parseInt(rowInfo.isMulSel)).attr("checked",true);
		//设置默认值
		$("input[type='radio'][name='isDefault']").eq(rowInfo.isDefault).attr("checked",true);
		$("#seqNoPro").val(rowInfo.seqNo);
		
		$("#itemCode").val(rowInfo.itemCode);
		//文本单位
		if(rowInfo.textUnit!="" && rowInfo.textUnit!=null){
			$("#textUnit").val(rowInfo.textUnit);
			document.getElementById('unit_display').style.display='block';
		}
		
	}else{
		if($("#title_ji").val()-2==0)  $("#itemlevel").val("顶级父项目");
		if($("#title_ji").val()-2==1)  $("#itemlevel").val("父项目");
		if($("#title_ji").val()-2==2)  $("#itemlevel").val("子项目");
		itemlevel = $("#title_ji").val()-2;
		//级别为答案  显示默认值
		if(itemlevel==1){
			document.getElementById('is_default').style.display='block';
		}
		
		var sex_all = document.getElementById('sex_all');
		sex_all.checked = true;
		//是否显示  赋值
		$("input[type='radio'][name='isVisa']").eq(1).attr("checked",true);
		//单选  多选 赋值
		$("input[type='radio'][name='isXuan']").eq(0).attr("checked",true);
		//默认显示
		$("input[type='radio'][name='isDefault']").eq(0).attr("checked",true);
		
		if($("#title_ji").val()-2 == 0){
			supItemId = 0 ;
		}else{
			supItemId = $("#sup_item_id").val();
		}
		titleID = $("#add_title_id").val();
	}
})
//是否选择
$("input:radio[name='isXuan']").on('click', function(event){
       if($(this).val()=='2'){
    	   document.getElementById('unit_display').style.display='block';
       }else{
    	   document.getElementById('unit_display').style.display='none';
       }
});

var isDefault ="";
//是否开启默认
$("input:radio[name='isDefault']").on('click', function(event){
	isDefault = $(this).val();
    if($(this).val()=='1' && $('#isWen').is(":checked")){
 	   document.getElementById('default_result').style.display='block';
    }else{
 	   document.getElementById('default_result').style.display='none';
    }
});

function question_AddSave(){
	
	if($("#itemName").val()==''){
		$("#itemName").focus();
		return;
	}
	
    // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
    if($("#seqNoPro").val() === "" || $("#seqNoPro").val() ==null){
        $.messager.alert('提示',' 顺序号不能为空 ');
        return;
    }
    if(isNaN($("#seqNoPro").val())){
    	$.messager.alert('提示',' 顺序号必须为数字 ');
        return;
    }
	
	$.messager.confirm('确认','您确定要保存吗？',function(r){
	    if (r){
	    	//进入ajax提交
	    	question_submit();
	    }
	});
}

function question_submit(){
	var isVisable,isMulSel,isSex;
    if($('#isOk').is(":checked"))isVisable = 1;
    else isVisable = 0;
    
    isMulSel = $("input[name='isXuan']:checked").val();
    
    if($('#sex_all').is(":checked")) isSex = "全部";
    if($('#man').is(":checked")) isSex = "男";
    if($('#woman').is(":checked")) isSex = "女";
    
    if($("#msg").val()=='edit') questionUrl = "brainQuestionAdd.action?ItemId="+$("#item_id").val();
    if($("#msg").val()=='add') questionUrl = "brainQuestionAdd.action";
    
    $.ajax({
		url:questionUrl,  
        data:{
        	itemName:$("#itemName").val().trim(),
        	seqNo:$("#seqNoPro").val().trim(),
        	isVisable:isVisable,
        	itemlevel:itemlevel,
        	linkNo:"无",
        	isMulSel:isMulSel,
        	supItemId:supItemId,
        	titleID:titleID,
        	itemCode:$("#itemCode").val(),
        	sex:isSex,
        	textUnit:$('#textUnit').val(),//文本单位
        	isDefault:isDefault,//此答案是否默认
        	defaultResult:$('#defaultResult').val().trim()//input输入默认结果
        	},          
          type: "post",//数据发送方式   
          success: function(data){  
             	var obj=eval("("+data+")");
          		$.messager.alert('提示信息', obj,function(){});
          	 	$("#pro-addEdit").dialog("close");
          	 	//加载表格信息
          	 	if($("#title_ji").val() < 3){
          	 		addInProjectTwo($("#add_title_id").val(),$("#titleText").text(),"",$("#titleText").text(),"refresh",$("#title_ji").val());
          	 	}else{
          	 		addInProjectTwo($("#add_title_id").val(),$("#titleText").text(),$("#sup_item_id").val(),$("#titleText").text(),"refresh",$("#title_ji").val());
          	 	}
            },
            error:function(){
            	 $("#pro-addEdit").dialog("close");
            	//alert("用户操作失败！");
            	$.messager.alert('提示信息', "保存失败！",function(){});
            }
                 
	});
}
</script>

	<br/>
	<div class="formdiv">
		<div style="display: none;">
			<input id="item_id" value=""/>
			<input id="msg" value="<s:property value="titleName"/>"/>
		</div>
		<dl>
			<dt>问卷项目编码：</dt>
			<dd>
				<input type="text" class="textinput" name="itemCode" id="itemCode" size="10" value="例如：（A1）"  
		            onfocus='if(this.value=="例如：（A1）"){this.value="";this.style.color="#000";}; '   style="width:244px; color:#999;"
		            onblur='if(this.value==""){this.value="例如：（A1）"; this.style.color="#999";};'>  
			</dd>
		</dl>
		<dl>
			<dt>问卷项目名称：</dt>
			<dd><input type="text" class="textinput" name="itemName" id="itemName" value="" style="width:244px; "/> <strong class="red">*</strong></dd>
		</dl>
		<!-- <dl>
			<dt>级别：</dt>
			<dd><input type="text" class="textinput" name="itemlevel" id="itemlevel" style="width:244px;" value=""  readonly="readonly"/></dd>
		</dl> -->
		<dl>
			<dt>顺序号：</dt>
			<dd><input id="seqNoPro" name="seqNoPro"  type="text" class="textinput" style="width:243px; height: 20px;"/> <strong class="red">*</strong></dd>
		</dl>
		<dl style="display: none;">
			<dt>关联项目代码：</dt>
			<dd>
				<input type="text" class="textinput" name="linkNo" id="linkNo" size="10" value="例如：（无）/（A1）"  
		            onfocus='if(this.value=="例如：（无）/（A1）"){this.value="";this.style.color="#000";}; '   style="width:244px; color:#999;"
		            onblur='if(this.value==""){this.value="例如：（无）/（A1）"; this.style.color="#999";};'> <strong class="red">*</strong>
			</dd>
		</dl>
		
		<dl>
			<dt>所属性别：</dt>
			<dd style="margin-top: 6px;">
				<label for="sex_all">全部</label>
				<input id="sex_all" type="radio" name="sex" value="全部"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="man">男</label>
				<input id="man" type="radio" name="sex" value="男"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="woman">女</label>
				<input id="woman" type="radio" name="sex" value="女"/>
			</dd>
		</dl>
		<dl>
			<dt>项目类别：</dt>
			<dd style="margin-top: 6px;">
				<label for="isDan">单选</label>
				<input id="isDan" type="radio" name="isXuan" value="0"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="isDuo">多选</label>
				<input id="isDuo" type="radio" name="isXuan" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="isWen">文本单位</label>
				<input id="isWen" type="radio" name="isXuan" value="2"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="isWenDan">文本输入框</label>
				<input id="isWenDan" type="radio" name="isXuan" value="3"/>  
			</dd>
		</dl>
		
		<dl id="unit_display" style="display: none;">
			<dt>文本单位：</dt>
			<dd><input name="textUnit" id="textUnit" type="text" class="textinput" style="width:248px;" /></dd>
		</dl>
		
		<dl>
			<dt>是否显示：</dt>
			<dd style="margin-top: 6px;">
				<label for="isNo">否</label>
				<input id="isNo" type="radio" name="isVisa" value="0"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="isOk">是</label>
				<input id="isOk" type="radio" name="isVisa" value="1"/>
			</dd>
		</dl>
		
		<dl id="is_default" style="display: none;">
			<dt>是否设置为答案：</dt>
			<dd style="margin-top: 6px;">
				<label for="isDefaultNo">否</label>
				<input id="isDefaultNo" type="radio" name="isDefault" value="0"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="isDefaultOk">是</label>
				<input id="isDefaultOk" type="radio" name="isDefault" value="1"/>
			</dd>
		</dl>
		
		<dl id="default_result" style="display: none;">
			<dt>默认值：</dt>
			<dd><input name="defaultResult" id="defaultResult" type="text" class="textinput" value="" style="width:248px;" /></dd>
		</dl>
			
	</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javaScript:question_AddSave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#pro-addEdit').dialog('close')">关闭</a>
	</div>
	
</div>