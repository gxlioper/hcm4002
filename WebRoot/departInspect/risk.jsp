<%@ page contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
$(function(){
	var riskstr= new Array();
	$("#dt li").each(function(k,v){
		riskstr.push({"exam_item":$(this).children('label').eq(0).text(),
				      "id":$(this).find(".jd").val(),
				      "risk":$(this).find(".result").text()});
			
	});
		$('#riskShow').datagrid({    
		    fitColumns:true,
		    data:riskstr,
		    columns:[[    
                {field:'KK',checkbox:true },
		        {field:'exam_item',title:'检查项目名称','width':200,align:'center'},    
		      /*   {field:'exam_item',title:'编码',width:100},    */ 
		        {field:'risk',title:'检查结果','width':200,align:'center'}    
		    ]]
		}); 
})

function addrisk(){
	var Checked = $('#riskShow').datagrid('getChecked');
	var ids=new Array();
	$.each(Checked,function(k,v){//要设置危机的id
			$("#dt li").each(function(){//遍历id进行比较
				if(v.id==$(this).find(".jd").val()){
					$(this).find(".result").css('color','#ff0000');
					$(this).find(".result").attr('data',1)
				}
			})
    });
	$('#weijizhil').dialog('close');
	jiel();
}
</script>
 <!-- <div style="height: 100%"> -->
   <fieldset style="margin:10px;padding:5px;height:87%;">
   		<legend><strong>项目列表</strong></legend>
   				<table id="riskShow"  style="height:96%" ></table>
   </fieldset>
	<div id="search-buttons"  style="width: " class="dialog-button-box" >
	<div class="inner-button-box">
	    <a href="javascript:addrisk()" class="easyui-linkbutton c6" style="width:100px;">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#weijizhil').dialog('close')">关闭</a>
	</div>
 <!--   </div> -->
</div>
