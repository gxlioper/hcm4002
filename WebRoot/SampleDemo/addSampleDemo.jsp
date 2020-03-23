<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	$('#demo_type2').combobox({
		url:"getDatadis.action?com_Type="+"YBFL",
		valueField : 'id',
		textField : 'name',
		editable:false,
		onLoadSuccess : function() {//下拉框默认选择
			var combo_data = $(this).combobox('getData');
            var demo_type = "<s:property value='demo_type' />"
            if(demo_type!='-1'){
                $(this).combobox('select',demo_type)
            } else {
                $(this).combobox('select',combo_data[0].id)
            }

		}
   })
	
	var id="<s:property value="id" />";
	var demo_num="<s:property value='demo_num'/>";
	getSampleDemoType();
	$('#demo_num').blur(function(){
		if(demo_num!=$('#demo_num').val()){
			num();
		}else{
			$('#ssnum').text("")
		}
	})
	$('#demo_num').validatebox({
		required : true,
		validType:'CHS'
	})
	$('#print_copy,#print_seq').validatebox({
			validType:'IsNumber',
	})
	$('#demo_num,#adddemo_name,#print_copy').validatebox({
		required : true,
	})
	var  isPrint_BarCode="<s:property value='isPrint_BarCode'/>";
	if(isPrint_BarCode=='0'){
		$('#isPrint_BarCodeA').attr("checked",false);
		$('#isPrint_BarCodeB').attr("checked",true);
	}
	var barCode="<s:property value='barCode'/>";
	if(barCode=='1'){
		$('#barCodeBB').attr('selected',true);
		$('#barCodeA').attr('selectd',false);
		$('#barCodeC').attr('selectd',false);
	}else if(barCode=='2'){
		$('#barCodeC').attr('selected',true);
		$('#barCodeA').attr('selected',false);
		$('#barCodeBB').attr('selectd',false);
	}
	$(".basic").spectrum({
	    color: "#ffffff",
	    change: function(color) {
	        $("#basic-log").text("change called: " + color.toHexString());
	    }
	});
	suoshukeshi();
	$("#demo_color").spectrum({
		 color:"#ffffff",
	    showInput: true,
	    className: "full-spectrum",
	    showInitial: true,
	    showPalette: true,
	    cancelText: "关闭",//取消按钮,按钮文字
	    chooseText: "确定",//选择按钮,按钮文字
	    showSelectionPalette: true,
	    maxSelectionSize: 10,
	    preferredFormat: "hex",
	    localStorageKey:true,
	    move: function (color) {
	        
	    },
	    show: function () {
	    
	    },
	    beforeShow: function () {
	    
	    },
	    hide: function () {
	    
	    },
	    change: function() {
	        
	    },
	    palette: [
	        ["rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)",
	        "rgb(204, 204, 204)", "rgb(217, 217, 217)","rgb(255, 255, 255)"],
	        ["rgb(152, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 153, 0)", "rgb(255, 255, 0)", "rgb(0, 255, 0)",
	        "rgb(0, 255, 255)", "rgb(74, 134, 232)", "rgb(0, 0, 255)", "rgb(153, 0, 255)", "rgb(255, 0, 255)"], 
	        ["rgb(230, 184, 175)", "rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)", "rgb(217, 234, 211)", 
	        "rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)", "rgb(217, 210, 233)", "rgb(234, 209, 220)", 
	        "rgb(221, 126, 107)", "rgb(234, 153, 153)", "rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)", 
	        "rgb(162, 196, 201)", "rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)", "rgb(213, 166, 189)", 
	        "rgb(204, 65, 37)", "rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)", "rgb(147, 196, 125)", 
	        "rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)", "rgb(142, 124, 195)", "rgb(194, 123, 160)",
	        "rgb(166, 28, 0)", "rgb(204, 0, 0)", "rgb(230, 145, 56)", "rgb(241, 194, 50)", "rgb(106, 168, 79)",
	        "rgb(69, 129, 142)", "rgb(60, 120, 216)", "rgb(61, 133, 198)", "rgb(103, 78, 167)", "rgb(166, 77, 121)",
	        "rgb(91, 15, 0)", "rgb(102, 0, 0)", "rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)", 
	        "rgb(12, 52, 61)", "rgb(28, 69, 135)", "rgb(7, 55, 99)", "rgb(32, 18, 77)", "rgb(76, 17, 48)"]
	    ]
	});
	var iss="<s:property  value='demo_color'/>";
	if(id>0){
		if(iss!=""){
			var s = iss.substring(1,iss.length);
			$("#demo_color").spectrum("set","#"+s); 
		}
	}
})
/**
  * 获取样本类型
  */
 function getSampleDemoType(){
	$('#demo_category').combobox({
		url:"getDatadis.action?com_Type="+"YBLX",
	    valueField:'id',
	    textField:'name',
	    editable:false,
	    onLoadSuccess : function(){//下拉框默认选择
	       var val = $(this).combobox('getData');
    	   for (var item in val[0]) {  
               if (item == 'id') {
            	   if($('#hdemo_category').val()!=''){
            		   var a=$('#hdemo_category').val();
            		   $(this).combobox('select',a);//设置你找选中的
            	   }else{
            		  $(this).combobox('select', val[0][item]);//默认选择第一条
            	   }
               }  
           }  
	    }
	}); 
}
/**
 * 验证报告编号是否可用
 */
function num() {
	$.ajax({
		url : 'getSampleDemoBynum.action?demo_num=' + $("#demo_num").val(),
		type : 'post',
		success : function(data) {
			if (data=='no') {
				$('#ssnum').text("编号已存在!")
				return false;
			} else if (data=='ok') {
				$('#ssnum').text("")
				return true;
			}
		}
	});
}
/**
 * 科室下拉框
 */
function suoshukeshi(){
		$('#print_dep').combobox({
					url : 'getDepartmentDepBarCode.action',
					valueField : 'id',
					textField : 'dep_name',
					editable:false,
					onLoadSuccess : function() {//下拉框默认选择
						var co = $('#print_dep').combobox('getData');
					    var dsa = $('#s_print_dep').val();
						for(var i = 0 ; i < co.length ; i++){
							if(co[i].id==dsa){
								$('#print_dep').combobox("select",dsa);
								break;
							} else {
								//$('#print_dep').combobox('select',co[0].id);
							} 
						}
					}
		})
}
</script>
<input type="hidden"  id = "s_print_dep" value="<s:property value='print_dep'/>"/>
 <fieldset style=" margin: 10px; padding-left: 10px ">
	<legend><strong>检验样本编辑</strong></legend> 
	<div class="formDiv"  style="margin-top: 15px;">
		<dl>
			<dt style="width: 100px">
				样本名称:
			</dt>
			<dd>
				<input   type="text" id="adddemo_name"   maxlength="45" 
				value="<s:property  value='demo_name'/>"
				style="height: 26px; width: 244px;"  class="textinput"/>
				<font color="ff0000"><span>*</span></font>
				
			</dd>
			<dt style="width: 92px">所属类别：</dt>
  			<dd>
  				<input type="hidden" id="h_demo_type2" style="width: 244px;height: 26px;" value="<s:property  value='demo_type'/>" />
  				<input type="text" id="demo_type2" style="width: 244px;height: 26px;" />
  			</dd>
  			
		</dl>
		<dl>
			<dt style="width: 100px">样本标志</dt>
			<dd>
				<input   type="text" maxlength="45" id="demo_indicator"
				 value="<s:property  value='demo_indicator'/>"
				 class="textinput"	style="height: 26px; width: 244px;"/>
			</dd>
			<dt style="width: 101px">
				样本编码
			</dt>
			<dd>
				<input type="hidden" id="demo_id" value="<s:property  value='id'/>"  />
				<span id="update"></span>
				<input  type="text" id="demo_num"  value="<s:property  value='demo_num'/>" maxlength="45"  
				style="height: 26px; width: 244px;" class="textinput" 
				class="easyui-validatebox" />
				<font color="ff0000">
					<span id="xing">*</span>
					<span id="ssnum"></span>
				</font>
			</dd> 
		</dl>
		<dl>
			<dt style="width: 100px">标本类型</dt>
			<dd>
				<input type="hidden" id="hdemo_category" value="<s:property value='demo_category'/>" />
				<input   id="demo_category" style="height: 26px; width: 244px;" />
				<font color="ff0000"><span>*</span></font>
			</dd>
			<dt style="width: 92px">打印顺序</dt>
			<dd>
				<input type="text" class="textinput" id="print_seq"  value="<s:property value='print_seq'/>" style="height: 26px; width: 244px;"/>
			</dd>
				
		</dl>
		<dl>
			<dt style="width: 100px">条码类型</dt>
			<dd>
				<select  class="textinput" id="barCode" class="easyui-combobox" style="height: 26px;">
					<option value="0" selected="selected"  id="barCodeA">系统条码</option>
					<option value="1" id="barCodeBB">预印条码</option>
					<option value="2" id="barCodeC">其他条码</option>
				</select>
			</dd>
			<dt style="width: 87px">样本颜色:</dt>
			<dd>
				<input type="text" class="textinput" id="demo_color" 
				maxlength="45" 	style="height: 26px; width: 25px;"/>
				&nbsp;&nbsp;
			</dd>
			<dt style="width: 90px"">打印份数</dt>
			<dd>
				<input type="text" class="textinput" id="print_copy" 
				value="<s:property  value='print_copy'/>"
			    maxlength="4" style="height: 26px;  width: 244px;"/>
				<font color="ff0000"><span>*</span></font>
			</dd>
			
		</dl>
		<dl>
			<dt style="width:99px">打印科室</dt>
			<dd>
					<input type="text" class="textinput" id="print_dep" 
			    maxlength="4" style="height: 26px;  width: 244px;"/>
			</dd>
			<dt style="width: 100px">
				是否打印
			</dt>
			<dd style="width: 150px">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" value="1" name="isPrint_BarCode" id="isPrint_BarCodeA" checked="checked"   />
				是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" value="0" name="isPrint_BarCode" id="isPrint_BarCodeB" />
				否
			</dd>
		</dl>
		<dl>
			<dt style="width:110px">打印申请单</dt>
			<dd>&nbsp;&nbsp;
				<s:if test="model.isPrint_req == 0" >
					<input type="radio" value="1" name="isPrint_req" id="isPrint_req_s" />
					打印&nbsp;&nbsp;&nbsp;
					<input type="radio" value="0" name="isPrint_req" id="isPrint_req_f" checked="true" />
					不打印
				</s:if>
				<s:else>
					<input type="radio" value="1" name="isPrint_req" id="isPrint_req_s" checked="true" />
					打印&nbsp;&nbsp;&nbsp;
					<input type="radio" value="0" name="isPrint_req" id="isPrint_req_f" />
					不打印
				</s:else>
					
				<s:if test="model.req_print_num > 0" >
					&nbsp;&nbsp;&nbsp;打印份数<input type="text" class="textinput" id="req_print_num" 
			   		 maxlength="4" value="<s:property value='req_print_num'/>" style="height: 26px;width:55px;"   onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" />
				</s:if>
				<s:else>
					&nbsp;&nbsp;&nbsp;打印份数<input type="text" class="textinput" id="req_print_num" 
			   		 maxlength="4" value="1" style="height: 26px;width:55px;"   onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" />
				</s:else>
			</dd>
			<dt style="width:60px">备注：</dt>
			<dd>
				<input type="text" class="textinput" id="remark" maxlength="45" 
				value="<s:property  value='remark'/>"
				id="remark" style="height: 26px;  width: 330px;"/>
			</dd>
		</dl>
		<!--<dl>
			<dt style="width:99px">备注：</dt>
			<dd>
				<input type="text" class="textinput" id="remark" maxlength="45" 
				value="<s:property  value='remark'/>"
				id="remark" style="height: 26px;  width: 630px;"/>
			</dd>
		</dl>-->
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addSampleDemo();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>