<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
function getData(){
	//获取性别
	var sextype = '<s:property value="model.ti_sex"/>';
	if(sextype!="" && sextype!=null){
		$("ti_sex").combobox('setValue',sextype);
	}
	$('#ti_sex').combobox({
        required:true
    });
	
	queryReplaceUser();
}

//验证省份证信息
function selectIdNum(){
	var idnum= $("#ti_id_num").val();// 取值
	if(idnum.length==18){
		var ssidnum=idnum.substring(0,17);
		if (!isSZ(ssidnum)) {		
			$.messager.alert("提示信息","身份证不存在","error");
		} else{
			$('#ti_sex').combobox('setValue',IdCard(idnum,2));
			$('#ti_age').val(IdCard(idnum,3));
		}
	} else if(idnum.length==15 ){
		//15位身份证验证
		var idca= idCardNoUtil.checkIdCardNo(idnum)
		if(idca){
				//性别&&身份证
			  	var getIdCardInfo = idCardNoUtil.getIdCardInfo(idnum);
			  	//计算年龄
			    var myDate = new Date();
		        var month = myDate.getMonth() + 1;
		        var day = myDate.getDate();
		        var age = myDate.getFullYear() - Number("19"+idnum.substring(6,8)) - 1;
		        if (idnum.substring(8,10) < month || idnum.substring(8,10) == month && idnum.substring(10,12) >= day) {
		            age++;
		        }
		        $('#ti_age').val(age);//年龄
				$('#ti_age').combobox('setValue',getIdCardInfo.gender);//性别
		} else {
			$.messager.alert("提示信息","身份证不存在","error");
		}
	} else if(idnum!="" && idnum.length!=15 && idnum.length!=18 ){
		$.messager.alert("提示信息","身份证不存在","error");
	}
}

//通过身份证号获取生日、年龄、性别
function IdCard(UUserCard,num){
	   if(num==1){
	       // 获取出生日期
	    birth=UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14);
	    return birth;
	   }
	   if(num==2){
	       // 获取性别
	       if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
	           // 男
	     return "男";
	       } else {
	           // 女
	     return "女";
	       }
	   }
	   if(num==3){
	        // 获取年龄
	        var myDate = new Date();
	        var month = myDate.getMonth() + 1;
	        var day = myDate.getDate();
	        var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
	        if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) {
	            age++;
	        }
	  return age;
	 }
}

//查询 替换人的信息
function queryReplaceUser(){
	var model ={
			exam_num:$('#s_num').val() //体检号
		} 
	 	$.ajax({
			url:'queryReplaceUser.action',
			data:model,
			type:'post',
			success:function(text){
				if(text.split("-")[0]=="ok"){
					//alert(text.split("-")[1]+"===="+text.split("-")[2]+"==="+text.split("-")[3]+"==="+text.split("-")[4]);
					$('#ti_name').val(text.split("-")[1]);
					$('#ti_sex').combobox('select',text.split("-")[2]);
					$('#ti_age').val(text.split("-")[3]);
					$('#ti_id_num').val(text.split("-")[4]);
				}
			},
			error:function(){
				$.messager.alert('提示信息','操作失败','error');
			}
		}) 
	
}

/**
 * 保存领取方式
 */
function saveReplaceUser(){
	
	var tiName=$('#ti_name').val();
	if($('#ti_name').val()==''){
		$('#ti_name').focus();
		return;
	}
	var model ={
		exam_num:$('#s_num').val(), //体检号
		arch_num:$('#s_arch_num').val(), //档案号
		ti_name:$('#ti_name').val(),
		ti_sex:$('#ti_sex').combobox('getValue'),
		ti_age:$('#ti_age').val(),
		ti_id_num:$('#ti_id_num').val()
	} 
 	$.ajax({
		url:'saveReplaceUserData.action',
		data:model,
		type:'post',
		success:function(text){
			alert_autoClose("操作提示", text.split("-")[1],"");
			//window.parent.ztabsselect();
		},
		error:function(){
			$.messager.alert('提示信息','操作失败','error');
		}
	}) 
} 
</script>
<body>
	<input type="hidden"   id="s_id"   value="<s:property value = 'id'/>" />
	<input type="hidden"   id="s_arch_num"   value="<s:property value = 'arch_num'/>" />
	<input type="hidden"   id="s_num"   value="<s:property value = 'exam_num'/>" />
	
	 	<div class="formDiv" style="width: 500px;margin: 50px auto;">
		    <dl>
				<dt>姓名</dt>
				<dd>
					<input type="text"  maxlength="20"  id="ti_name"  class="textinput" style="width:280px;height: 35px;" value="<s:property value="model.ti_name" />" />
				</dd>
			</dl>
			<dl>
				<dt>性别</dt>
				<dd>
					<select class="easyui-combobox" id="ti_sex" name="ti_sex" data-options="height:35,width:280,panelHeight:'auto'">
						<option value="女">女</option>
	                	<option value="男">男</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>年龄</dt>
				<dd>
					<input class="textinput" maxlength="3" type="text" id="ti_age" style="width:280px;height: 35px;" value="<s:property value="model.ti_age" />"/>
				</dd>
			</dl>
			<dl>
				<dt>身份证</dt>
				<dd>
					<input  class='textinput'  maxlength="18"  onblur="selectIdNum()" type="text"   id="ti_id_num" style="width:280px;height: 35px;" value="<s:property value="model.ti_id_num" />"/>
				</dd>
			</dl>
			<dl >
				<dt></dt>
				<dd>
					<input type="button"    onclick="saveReplaceUser();"  style="width:280px;height:35px;background: #EDEDED;"   value="保存"/>
				</dd>
			</dl>
		</div> 
</body>
