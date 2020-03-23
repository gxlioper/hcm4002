$(document).ready(function () {
	var check_types=$("#check_types").val();
//	$('#check_typess').combobox('clear');
	com();
	f_getDatadic();
});
function com(){
	 $('#check_typess').combobox({
		 	data:[{
		 		id:'1',value:'医生审核'
		 	},{
		 		id:'2',value:'财务审核'
		 	},{
		 		id:'3',value:'上级部门审核'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    prompt:'请选择审核方式',
		    multiple:true,
		    panelHeight:'auto',
		    onLoadSuccess:function(){
		    var check_types=$("#check_types").val();
		    if(check_types!=''){
		    	 if(check_types.indexOf(',')>0){
			   		 $('#check_typess').combobox('setValues',check_types.split(','));
			   	 }else{
			   		 $('#check_typess').combobox('setValue',check_types);
			   	 }
		    }else{
		    	$('#check_typess').combobox('setValue','2');
		    }
		    }
	 });
	$('#examtypes').combobox({
		 data:[{
		 		id:'1',value:'健康体检'
		 	},{
		 		id:'2',value:'职业病体检'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    prompt:'请选择体检方式',
		   // multiple:true,
		    panelHeight:'auto',
		    required:true,
		    onLoadSuccess:function(){
		    var tijiantype=$("#tijiantypess").val();
		    if(tijiantype!=''){
			   		 $('#examtypes').combobox('setValue',tijiantype);
		    }else{
		    	$('#examtypes').combobox('setValue','1');
		    }
		    }
	})
}


//获取菜单
function f_getDatadic() {
	var comtype='<s:property value="model.pay_way"/>';
	$('#pay_way').combobox({
        url:'getDatadis.action?com_Type=JSLX', 
        editable:false, //不可编辑状态
        cache: false,
        panelHeight: 'auto',//自动高度适合
        valueField:'id',   
        textField:'name',
        onLoadSuccess: function (data){
           for(var i=0;i<data.length;i++)
            {
               if(data[i].id==comtype){
            	$('#pay_way').combobox('setValue',data[i].id);
            	break;
               }else{
            	$('#pay_way').combobox('setValue',data[0].id);
               }
            }
        }
	});
	
	var charge_type='<s:property value="model.charge_type"/>';
	$('#charge_type').combobox({
		    url:'getDatadis.action?com_Type=SFTYPE', 
	        editable:false, //不可编辑状态
	        cache: false,
	        panelHeight: 'auto',//自动高度适合
	        valueField:'id',   
	        textField:'name',
           onLoadSuccess: function (data){
           for(var i=0;i<data.length;i++)
            {
               if(data[i].id==charge_type){
            	$('#charge_type').combobox('setValue',data[i].id);
            	break;
               }else{
            	$('#charge_type').combobox('setValue',data[0].id);
               }
            }
        }
	});	
}

function checkinput(){
	 if (document.getElementsByName("pay_way")[0].value==''){
  		alert('结算方式不能为空！');
		document.getElementsByName("pay_way").focus();
		return false;
	}
	 if (document.getElementsByName("charge_type")[0].value == ''){
  		alert('收费类型不能为空！');
		document.getElementsByName("charge_type")[0].focus();
		return false;
	}
	 if (document.getElementById("batch_name").value == ''){
  		alert('体检任务名称不能为空！');
		document.getElementById("batch_name").focus();
		return false;
	}
	 if (document.getElementById("phone").value != ''){
		if(!(isSZ(document.getElementById("phone").value))){	
		alert('联系人电话格式错误！');
		document.getElementById("phone").focus();
		return false;
		}
	}
	 if (document.getElementById("exam_number").value == ''){
  		alert('计划体检人数不能为空！');
		document.getElementById("exam_number").focus();
		return false;
	}
	 if(!(isSZ(document.getElementById("exam_number").value))){
		alert('计划体检人数格式错误！');
		document.getElementById("exam_number").focus();
		return false;
	}
	 if (document.getElementById("exam_fee").value == ''){
  		alert('计划体检费用不能为空！');
		document.getElementById("exam_fee").focus();
		return false;
	}
	 if(!(isJE(document.getElementById("exam_fee").value))){
		alert('计划体检费用格式错误！');
		document.getElementById("exam_fee").focus();
		return false;
	}
	 if (document.getElementById("remark").value != ''){
	    if (document.getElementById("remark").value.length>200)
	    {
  		alert('备注字数超限！');
		document.getElementById("remark").focus();
		return false;
		}
	}
	 if ($("#check_typess").combobox('getValues').join(',').indexOf('2')<0){
	    alert('财务审核是必须的');
	    $("#check_type").combobox().focus();
	    return false;
	}
	var v= $("#examtypes").combobox('getValue');
	 if($("#examtypes").combobox('getValue')==undefined){
		 alert('体检类型是必须的');
		    $("#examtypes").combobox().focus();
		    return false;
	 }
	return true;
}

/**
 * 保存修改
 */
function fanganadd(){
	if(checkinput())	
	{
		$("#batch_num").val($("#batch_name").val());
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	    $("body").prepend(str);
	 $.ajax({
        url:'crmbatcheditdo.action',  
        data:{
          id:$("#id").val(),
          company_id:$("#company_ids").val(),
          batch_num:$("#batch_num").val(),
          batch_name:$("#batch_name").val(),
          contact_name:$("#contact_name").val(),
          phone:$("#phone").val(),
          pay_way:document.getElementsByName("pay_way")[0].value,
          charge_type:document.getElementsByName("charge_type")[0].value,
          exam_number:$("#exam_number").val(),
          exam_fee:$("#exam_fee").val(),
          sales_name:$("#sales_name").val(),
          introducer_name:$("#introducer_name").val(),
          exam_item:$("#exam_item").val(),
          accommodation:$("#accommodation").val(),          
          dine:$("#dine").val(),
          invoice_title:$("#invoice_title").val(),
          batch_address:$("#batch_address").val(),
          exam_date:$("#exam_date").datebox('getValue'),   
          exam_date_end:$("#exam_date_end").datebox('getValue'), 
          remark:$("#remark").val(),
          sign_num:$("#sign_nums").val(),       
          check_type:$("#check_typess").combobox('getValues').join(','),
          tijiantype:$("#examtypes").combobox('getValue')
          },          
          type: "post",//数据发送方式   
          success: function(text){
        	  $(".loading_div").remove();
        	  if(text.split("-")[0]=='ok'){
        		  $.messager.alert("操作提示", text);
        		   getbatchGrid();
        		   $('#dlg-edit').dialog('close');
        	  }else if(text.split("-")[0]=='error'){
        		  $.messager.alert("操作提示", text,"error");
        	  }
            },
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}    
    }); 
	}
}