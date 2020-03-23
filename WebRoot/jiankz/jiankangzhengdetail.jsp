<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	if($('#piclist').val()!=''){
		$('#temppicshow').css('display','none');
	}else{
		$('#temppicshows').css('display','none');
	}
});
function chuanshuresult(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	$.ajax({
		url : 'jianKangZhuangChuanShu.action',
		type : 'post',
		//dataType: "json", 
		data : {
			pic:$('#piclist').val(),
			dwbm:$('#dwbm').val(),
			bh:$('#bh').text(),
			dw:$('#dw').text(),
			xm:$('#xm').text(),
			dh:$('#dh').text(),
			nl:$('#nl').text(),
			hjgcode:$('#hjgcode').val(),
			sfzh:$('#sfzh').text(),
			xb:$('#xb').text(),
			rq:$('#rq').text(),
			fzrq:$('#fzrq').text(),
			hycode:$('#hycode').val(),
			gzcode:$('#gzcode').val(),
			jg_xincode:$('#jg_xincode').val(),
			jg_gancode:$('#jg_gancode').val(),
			jg_picode:$('#jg_picode').val(),
			jg_tppacode:$('#jg_tppacode').val(),
			jg_pfcode:$('#jg_pfcode').val(),
			jg_qtcode:$('#jg_qtcode').val(),
			jg_ljcode:$('#jg_ljcode').val(),
			jg_shcode:$('#jg_shcode').val(),
			jg_xtcode:$('#jg_xtcode').val(),
			jg_altcode:$('#jg_altcode').val(),
			jg_hbsagcode:$('#jg_hbsagcode').val(),
			jg_hbeagcode:$('#jg_hbeagcode').val(),
			jg_rprcode:$('#jg_rprcode').val(),
			jg_lqjcode:$('#jg_lqjcode').val(),
			jg_havcode:$('#jg_havcode').val(),
			jg_hivcode:$('#jg_hivcode').val(),
			yxq:$('#yxq').text(),
			djczy:$('#djczy').text(),
			dyrq:$('#dyrq').text(),
			dwdz:$('#dwdz').text(),
			sfhgcode:$('#sfhgcode').val(),
			sffzcode:$('#sffzcode').val()
		},
		success : function(data) {
			$(".loading_div").remove();
			 $.messager.confirm('提示信息',data+',是否关闭本页面？',function(r){
				 	if(r){
				 		$("#dlg-edit").dialog('close');
				 		getJKZList();
				 	}
				 })
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}
</script>
<fieldset style="margin:5px 5px 30px 5px;">
    <legend><strong>检查结果信息</strong></legend>
    	<table style="font-size: 12px;">
    		<tr>
    			<td style="width:110px">
    			<input id="piclist" value='<s:property value="pic"/>' hidden="true"/>
    				 <div id="img_upload" style="float:right; overflow:hidden; width:100px; height:150px; margin:0 60px 10px; border:1px solid #0b90da;">
    				 <img id="temppicshow" src="<%=request.getContextPath()%>/themes/default/images/user.png" width="100px" height="150px" />
    				  <img id="temppicshows" src="<%=request.getContextPath()%>/jkzImage?pic=<s:property value="pic"/>" width="120px" height="150px"  /> 
    				 </div>
    			</td>
    			<td>
    			 <div class="user-query" style="padding-top:0px;">
	<dl>
		<dt style="width:90px;">单位名称：</dt>
		<dt id="dwbmname" style="width:170px;"><s:property value="dwbmname"/></dt>
		<input id="dwbm" value='<s:property value="dwbm"/>' hidden="true"/>
		<dt style="width:90px;">体检证编号：</dt>
		<dt id="bh" style="width:100px;"><s:property value="bh"/></dt>
		</dl><dl>
		<dt style="width:90px;">工作单位：</dt>
		<dt id="dw" style="width:170px;"><s:property value="dw"/></dt>
		<dt style="width:90px;">姓名：</dt>
		<dt id="xm" style="width:100px;"><s:property value="xm"/></dt>
		</dl>
	<dl>
		<dt style="width:90px;">联系电话：</dt>
		<dt id="dh" style="width:170px;"><s:property value="dh"/></dt>
		<dt style="width:90px;">年龄：</dt>
		<dt  id="nl" style="width:100px;"><s:property value="nl"/></dt>
		</dl>
	<dl>
		<dt style="width:90px;">身份证号：</dt>
		<dt id="sfzh" style="width:170px;"><s:property value="sfzh"/></dt>
		<dt style="width:90px;">性别：</dt>
		<dt id="xb" style="width:100px;"><s:property value="xb"/></dt>
		</dl>
	<dl>
	<dt style="width:90px;">体检日期：</dt>
		<dt id="rq" style="width:170px;"><s:property value="rq"/></dt>
		<dt style="width:90px;">发证日期：</dt>
		<dt id="fzrq" style="width:150px;"><s:property value="fzrq"/></dt>
	</dl>
	<dl>
	<dt style="width:90px;">行业：</dt>
		<dt id="rq" style="width:170px;"><s:property value="hy"/></dt>
		<input id="hycode" value='<s:property value="hycode"/>' hidden="true"/>
		<dt style="width:90px;">工种：</dt>
		<dt id="fzrq" style="width:150px;"><s:property value="gz"/></dt>
		<input id="gzcode" value='<s:property value="gzcode"/>' hidden="true"/>
	</dl>
	</div>
    </td>
   </tr>
    		<tr>
    		<td colspan="2">
    			 <div class="user-query" style="padding-top:0px;">
	<dl>
		<dt style="width:60px;">有效期：</dt>
		<dt id="yxq" style="width:165px;"><s:property value="yxq"/></dt>
		<dt style="width:90px;">登记操作员：</dt>
		<dt id="djczy" style="width:170px;"><s:property value="djczy"/></dt>
		<dt style="width:90px;">打印证日期：</dt>
		<dt id="dyrq" style="width:150px;"><s:property value="dyrq"/></dt>
		</dl>
	<dl>
		<dt style="width:80px;">培训结果：</dt>
		<dt id="hjg" style="width:145px;"><s:property value="hjg"/></dt>
		<input id="hjgcode" value='<s:property value="hjgcode"/>' hidden="true"/>
		<dt style="width:90px;">是否合格：</dt>
		<dt  id="sfhg" style="width:170px;"><s:property value="sfhg"/></dt>
		<input id="sfhgcode" value='<s:property value="sfhgcode"/>' hidden="true"/>
		<dt style="width:90px;">单位地址：</dt>
		<dt id="dwdz" style="width:100px;"><s:property value="dwdz"/></dt>
		</dl>
	<dl>
		<dt style="width:80px;">心检查结果：</dt>
		<dt id="jg_xin" style="width:145px;"><s:property value="jg_xin"/></dt>
		<input id="jg_xincode" value='<s:property value="jg_xincode"/>' hidden="true"/>
		<dt style="width:90px;">肝检查结果：</dt>
		<dt id="jg_gan" style="width:170px;"><s:property value="jg_gan"/></dt>
		<input id="jg_gancode" value='<s:property value="jg_gancode"/>' hidden="true"/>
		<dt style="width:90px;">肝检查结果：</dt>
		<dt id="jg_pi" style="width:100px;"><s:property value="jg_pi"/></dt>
			<input id="jg_picode" value='<s:property value="jg_picode"/>' hidden="true"/>
		</dl>
		<dl>
		<dt style="width:80px;">TPPA：</dt>
		<dt id="jg_tppa" style="width:145px;"><s:property value="jg_tppa"/></dt>
		<input id="jg_tppacode" value='<s:property value="jg_tppacode"/>' hidden="true"/>
		<dt style="width:90px;">皮肤检查结果：</dt>
		<dt id="jg_pf" style="width:170px;"><s:property value="jg_pf"/></dt>
		<input id="jg_pfcode" value='<s:property value="jg_pfcode"/>' hidden="true"/>
		<dt style="width:90px;">其他检查结果：</dt>
		<dt id="jg_qt" style="width:100px;"><s:property value="jg_qt"/></dt>
		<input id="jg_qtcode" value='<s:property value="jg_qtcode"/>' hidden="true"/>
		</dl>
		<dl>
		<dt style="width:80px;">痢疾：</dt>
		<dt id="jg_lj" style="width:145px;"><s:property value="jg_lj"/></dt>
		<input id="jg_ljcode" value='<s:property value="jg_ljcode"/>' hidden="true"/>
		<dt style="width:90px;">伤寒：</dt>
		<dt id="jg_sh" style="width:170px;"><s:property value="jg_sh"/></dt>
		<input id="jg_shcode" value='<s:property value="jg_shcode"/>' hidden="true"/>
		<dt style="width:90px;">胸部透视：</dt>
		<dt id="jg_xt" style="width:100px;"><s:property value="jg_xt"/></dt>
		<input id="jg_xtcode" value='<s:property value="jg_xtcode"/>' hidden="true"/>
		</dl>
		<dl>
		<dt style="width:80px;">ALT：</dt>
		<dt id="jg_alt" style="width:145px;"><s:property value="jg_alt"/></dt>
		<input id="jg_altcode" value='<s:property value="jg_altcode"/>' hidden="true"/>
		<dt style="width:90px;">乙肝Hbsag：</dt>
		<dt id="jg_hbsag" style="width:170px;"><s:property value="jg_hbsag"/></dt>
		<input id="jg_hbsagcode" value='<s:property value="jg_hbsagcode"/>' hidden="true"/>
		<dt style="width:90px;">乙肝hbeag：</dt>
		<dt id="jg_hbeag" style="width:100px;"><s:property value="jg_hbeag"/></dt>
		<input id="jg_hbeagcode" value='<s:property value="jg_hbeagcode"/>' hidden="true"/>
		</dl>
		<dl>
		<dt style="width:80px;">RPR：</dt>
		<dt id="jg_rpr" style="width:145px;"><s:property value="jg_rpr"/></dt>
		<input id="jg_rprcode" value='<s:property value="jg_rprcode"/>' hidden="true"/>
		<dt style="width:90px;">淋球菌：</dt>
		<dt id="jg_lqj" style="width:170px;"><s:property value="jg_lqj"/></dt>
			<input id="jg_lqjcode" value='<s:property value="jg_lqjcode"/>' hidden="true"/>
		<dt style="width:90px;">甲肝igm：</dt>
		<dt id="jg_hav" style="width:100px;"><s:property value="jg_hav"/></dt>
		<input id="jg_havcode" value='<s:property value="jg_havcode"/>' hidden="true"/>
		</dl>
		<dl>
		<dt style="width:80px;">艾滋病：</dt>
		<dt id="jg_hiv" style="width:145px;"><s:property value="jg_hiv"/></dt>
		<input id="jg_hivcode" value='<s:property value="jg_hivcode"/>' hidden="true"/>
		<dt style="width:90px;">是否发证：</dt>
		<dt id="sffz" style="width:180px;"><s:property value="sffz"/></dt>
		<input id="sffzcode" value='<s:property value="sffzcode"/>' hidden="true"/>
		</dl>
	</div>
    		</td>
    		</tr>
    	</table>
</fieldset>

<div class="dialog-button-box">
	<div class="inner-button-box">
	 <a href="javascript:chuanshuresult();" class="easyui-linkbutton c6" style="width:80px;">上传</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
