<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var zzks="";
var ddss='1';
var hazard_list  = new Array() //缓存因素
$(document).ready(function(){
	//危害因素
	tjlbshow()
	getgetOccuhazardfactor()
	yinsu_table()
	yinsu_table_y()
	//套餐
	var ss ="<s:property value='intss'/>"
	if(ss==2){
		if($('#addset_name').val()=="" && $('#ys_name').val()!="" && $('#lb_name').val()!=""){
			var s_name = $('#ys_name').val()+"("+$('#lb_name').val()+")";
			//$('#addset_name').val(s_name)
			pinyin();
		}
	}
	
	var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
	if(is_show_discount==0){
		$("#discount").hide(); // 设置隐藏列
		$("#zk").hide(); // 设置隐藏列
		$("#zhekou").hide(); // 设置隐藏列
		$("#set_amount").hide(); // 设置隐藏列
		$("#zhje").hide(); // 设置隐藏列
		$("#yuan").hide(); // 设置隐藏列
		$("#zhis").hide(); // 设置隐藏列
	}
	
	if($('#webResource').val()=='' || $('#webResource').val()==undefined){
		$('#zhis').text("本操作员:没有打折权限！");
	}else{
		$('#zhis').text("本操作员最大权限可以打："+$('#webResource').val()+"折！");
	}
	
	//套餐类别
	examSetType();
	var fdsaa="<s:property value='set_amount'/>";
	$('#set_amount').val(fdsaa);
	var jfdkj="<s:property value='Price'/>";
	$('#amount').text(jfdkj);
	var fs="<s:property value='set_discount'/>";
	if(fs>0){
		$('#discount').val(fs);
	}
	var sex="<s:property value='sex'/>";
	if(sex=='女'){
		$('#sexQuanbu').attr('checked',false);
		$('#sexNan').attr('checked',false);
		$('#sexNvv').attr('checked',true);
	}
	if(sex=='男'){
		$('#sexQuanbu').attr('checked',false);
		$('#sexNan').attr('checked',true);
		$('#sexNvv').attr('checked',false);
	}
	if(sex=='全部'){
		$('#sexQuanbu').attr('checked',true);
		$('#sexNan').attr('checked',false);
		$('#sexNvv').attr('checked',false);
	}
	$('#addset_name,#set_pinyin,#set_num').validatebox({    
	    required: true 
	});
	/**
	*选择性别，收费项目跟着变化
	*/
	$('input[name="sex"]').click(function(){
		getchargingItem();	
	})
	//项目清单左
	getchargingItem();	
	//项目清单右
	selectchargingItem();	
	//拼音
	$('#addset_name').blur(function(){
		pinyin();		
	})
	$("#discount").click(function(){
		zzks=$("#discount").val();
	})
	$("#discount").on('keyup', function (event) {
		if($('#discount').val()>10){
	    	$('#discount').val('10');
	    }
		/* if($('#discount').val()!=""){
			if($('#discount').val()<0.1){
				$('#discount').val('10');
			}
		} */
	    var $amountInput = $(this);
	    //响应鼠标事件，允许左右方向键移动 
	    event = window.event || event;
	    if (event.keyCode == 37 | event.keyCode == 39) {
	        return;
	    }
	    //先把非数字的都替换掉，除了数字和. 
	    $amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
	        //只允许一个小数点              
	        replace(/^\./g, "").replace(/\.{2,}/g, ".").
	        //只能输入小数点后两位
	        replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3'));
	            });
	
	$("#discount").bind('blur', function () {
			if($('#discount').val()>10){
		    	$('#discount').val('10');
		    }
			/* if($("#discount").val()<1){
				$('#discount').val('10');
			}  */ 
		    var $amountInput = $(this);
		    //最后一位是小数点的话，移除
		    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
		    if(ddss=='1'){
			    if(Number($("#discount").val())<Number($('#webResource').val())){
	    	    	$.messager.alert('提示信息',"本操作员最大权限可大"+$('#webResource').val()+"折！",'error');
	    	    	$("#discount").val(zzks);
	    	    }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
	    	    	$.messager.alert('提示信息',"没有打折权限",'error');
	    	    	$("#discount").val(zzks); 
			    }
		    }
		    ddss='1';
		    zjezk();
	});
	
	//输入金额后自动计算价格和折扣额
	$("#set_amount").bind('blur', function () {
		//计算项目的最大优惠金额
		var rows = $("#selectchargingItem").datagrid('getRows');
		var allAmount = 0; //优惠的后总价格
		var itemAmout = 0; //不优惠总价格
		$.each(rows,function(k,v){
			//项目最小折扣
			if(Number(v.item_discount)>=Number($('#webResource').val())){
				allAmount += decimal(Number(v.amount)*Number(v.item_discount)*Number(v.itemnum)/10,2);
			}else{
				allAmount += decimal(Number(v.amount)*Number($('#webResource').val())*Number(v.itemnum)/10,2);
			}
			itemAmout += decimal(Number(v.amount)*Number(v.itemnum),2);
		}) 
		
		if(Number($("#set_amount").val())<allAmount){
			$.messager.alert('提示信息',"最大优惠金额为"+allAmount+"元",'error');
			$.each(rows,function(k,v){
				$('#selectchargingItem').datagrid('updateRow',{
					index:k,
					row:{
						amounts:decimal(Number(v.amount)*Number(v.itemnum),2),  //折扣后金额
						set_discountss:10   //实际折扣
					}
				})
			}) 
			$("#set_amount").val(decimal(itemAmout,2));
			return;
		}
		
		if(Number($("#set_amount").val())>itemAmout){
			$.messager.alert('提示信息',"金额过大，最大金额"+decimal(itemAmout,2)+"元",'error');
			$.each(rows,function(k,v){
				$('#selectchargingItem').datagrid('updateRow',{
					index:k,
					row:{
						amounts:decimal(Number(v.amount)*Number(v.itemnum),2),  //折扣后金额
						set_discountss:10   //实际折扣
					}
				})
			}) 
			$("#set_amount").val(decimal(itemAmout,2));
			return;
		}
		//计算折扣金额
		var huiAmount = decimal(itemAmout-allAmount,2); //
		$.each(rows,function(k,v){
			var itemAmount = 0; //金额
			var shiDiscount = 0;
			if(Number(v.item_discount)>=Number($('#webResource').val())){
				itemAmount = decimal(Number(v.amount)*Number(v.itemnum)-((10-Number(v.item_discount))/10*Number(v.amount)*Number(v.itemnum)-(10-Number(v.item_discount))/10*Number(v.amount)*Number(v.itemnum)*(((Number($("#set_amount").val())-allAmount)/huiAmount))),2);
			}else{
				itemAmount = decimal(Number(v.amount)*Number(v.itemnum)-((10-Number($('#webResource').val()))/10*Number(v.amount)*Number(v.itemnum)-(10-Number($('#webResource').val()))/10*Number(v.amount)*Number(v.itemnum)*(((Number($("#set_amount").val())-allAmount)/huiAmount))),2);
			}
			if(Number(v.amount)!=0){
				shiDiscount = decimal(itemAmount/(Number(v.amount)*Number(v.itemnum))*10, 4)
			}
			$('#selectchargingItem').datagrid('updateRow',{
				index:k,
				row:{
					amounts:itemAmount,
					set_discountss: shiDiscount //实际折扣
				}
			})
			
		})
		
		var oldamount=$('#set_amount').val();
        var amounts=0;//总金额折后金额
        var num=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
        $.each(num,function(k,v){
            amounts+=v.amounts;
        })
		/*
		$('#amount').text(decimal(amount,2));//原价
        $('#set_amount').val(decimal(amounts,2));//折后金额
		*/
		var ceamt=decimal((oldamount-amounts),2);
        if(ceamt>0){
        	var indexk=-1;
        	var bdamt=100000;
        	 $.each(num,function(k,v){
        		 if(v.amount>1&&v.amount<bdamt){
        			 indexk=k;
        			 bdamt=v.amounts;
					 //alert(bdamt);
        		 }
        	 })
        var fxamt=decimal($("#selectchargingItem").datagrid("getRows")[indexk].amounts,2);
		var newamt=decimal((fxamt+ceamt),2);
        $("#selectchargingItem").datagrid("getRows")[indexk].amounts=newamt;
		$('#selectchargingItem').datagrid('refreshRow',indexk);
        }
		
		if(ceamt<0){
        	var indexk=-1;
        	var bdamt=0;
        	 $.each(num,function(k,v){
        		 if(v.amount>1&&v.amount>bdamt){
        			 indexk=k;
        			 bdamt=v.amounts;
					 //alert(bdamt);
        		 }
        	 })
        var fxamt=decimal($("#selectchargingItem").datagrid("getRows")[indexk].amounts,2);
		var newamt=decimal((fxamt+ceamt),2);
        $("#selectchargingItem").datagrid("getRows")[indexk].amounts=newamt;
		$('#selectchargingItem').datagrid('refreshRow',indexk);
        }
		//折扣率
		$("#discount").val(decimal(Number($("#set_amount").val())/itemAmout*10, 4))
		
	});
	
	//总折扣添加回车事件
	 $("#discount").bind('keypress', function (event) {
            if (event.keyCode == "13") {
            	if($('#discount').val()>10){
        	    	$('#discount').val('10');
        	    }
        		/* if($("#discount").val()<1){
        			$('#discount').val('10');
        		}  */
        	    var $amountInput = $(this);
        	    //最后一位是小数点的话，移除
        	    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
        	    if(ddss=='1'){
        	    if(Number($("#discount").val())<$('#webResource').val()){
        	    	 ddss='2';
        	    	$.messager.alert('提示信息',"本操作员最大权限可大"+$('#webResource').val()+"折！",'error');
        	    	$("#discount").val(zzks);
        	    }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
        	    	 ddss='2';
        	    	$.messager.alert('提示信息',"没有打折权限",'error');
        	    	$("#discount").val(zzks);
        	    }
        	    }
        	    zjezk();
        	 
            }
	})
	
	$('#tclist').bind('click', function() {
		select_com_list(this.value);
	});

	$('#tclist').bind('keyup', function() {
		select_com_list(this.value);
	});

	$('#tclist').bind('blur', function() {
		select_com_list_over();
	});
})

//验证套餐名称不能输入分号
function  tcmc(obj){
	var newstr=$(obj).val().replace(/;+/g, "")   
	  newstr=newstr.replace(/；+/g, "")   
	  $(obj).val(newstr);
}
/**
 * easyui 单元格编辑
 */
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

var editIndex = undefined;
function endEditing(){
if (editIndex == undefined){return true}
	if ($('#selectchargingItem').datagrid('validateRow', editIndex)){
		$('#selectchargingItem').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

/*体检套餐收费项目
 * 项目清单左
 */
function getchargingItem(){
	   var sex=$('input[name=sex]:checked').val();
	   var intss = '<s:property value='intss'/>';
	   var app_type="";
	   if(intss==2){
		   if($('#pt_exam').is(':checked')){
			   app_type = 99;
		   }
	   }
	   
	  if($('input[name=sex]:checked').val()=='全部'){
		  sex="";
	  }
	 $("#getchargingItem").datagrid({
		 url:'changitemlist.action',
		 queryParams:{
			 setname:$('#item_name').val(),
			 sex:sex,
			 app_type:app_type
	 	 },
	 	 height:340,
		 rownumbers:false,
	     pageSize:10,
	     pageList:[10,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	            {align:'center',field:'item_code',title:'收费项目编码',width:15},	
	            {align:'center',field:'item_name',title:'收费项目名称',width:20},
	        	{align:'center',field:'item_amount',title:'金额',width:10},
	        	{align:'center',field:'item_seq',title:'顺序码',width:10},
	        	{align:'left',field:'app_type_s',title:'应用类型',width:10},
	         	{align:'center',field:'sss',title:'操作',width:10,"formatter":f_g}
	 	]],	    	
    	singleSelect:false,
	    collapsible:false,
		pagination:true,//分页控件
	    fitColumns:true,//自适应
	    striped:true,
        singleSelect:true,//只允许选择一行
        onDblClickRow:function(index){
        	fcc_add(index);
        }
	});
}
/**
 * 左表添加按钮
 */
function f_g(val,row,index){
    	return '<a href=\"javascript:fcc_add(\''+index+ '\')\"  style="text-decoration:none">添加</a>';
}
/**
 * 左表数据添加到右表方法
 */
function fcc_add(index){
 	var itemselect=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
	var row=$('#getchargingItem').datagrid("getRows")[index];//获取选择行的数据
	var f=0;
	var flag=true;
	//判断不能添加重复的记录到右表
	var itemtypeflag=true;
	var selectitemcode="";
		if (itemselect.length > 0) {
			for (var j = 0; j <= itemselect.length - 1; j++)//已选择
			{
				if (row.item_code == itemselect[j].item_code) {
					flag = false;//相等
				}
				if((row.item_type!='')&&(row.item_type==itemselect[j].item_type)){
					itemtypeflag=false;
				}
				selectitemcode=selectitemcode+",'"+itemselect[j].item_code+"'";
			}//j End             
		}
		if (flag) {	
				if(itemtypeflag){
					$('#selectchargingItem').datagrid("appendRow", {
						item_code:row.item_code,
						item_name:row.item_name,
						d_name:row.dep_name,
						amount:row.item_amount,
						amounts:row.item_amount,
						item_type:row.item_type,
						item_seq:row.item_seq,
						id:row.id,
						set_discountss:'10',
						itemnum:'1',
						ischosen:'必选'
					});
					alert_autoClose("操作提示", "添加成功！","");
					d_item.get_item_2(row.id);
					zhekou();//总折扣 
				}else{
					 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
				     if(r){
				    	 $('#selectchargingItem').datagrid("appendRow", {
								item_code:row.item_code,
								item_name:row.item_name,
								d_name:row.dep_name,
								amount:row.item_amount,
								amounts:row.item_amount,
								item_type:row.item_type,
								id:row.id,
								item_seq:row.item_seq,
								set_discountss:'10',
								itemnum:'1',
                                ischosen:'必选'
							});
							alert_autoClose("操作提示", "添加成功！","");
							d_item.get_item_2(row.id);
							zhekou();//总折扣 
						 }
					 });
				}

		}else{
			alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
		}
}
/*
 * 折扣算法
 */
function zhekou(){
	var amountsnum=0;//总金额折后金额
	var amountnum=0;
	var num=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
	$.each(num,function(k,v){
		amountnum+=v.amount*v.itemnum;
		amountsnum+=v.amounts*v.itemnum;
	})
	$('#amount').text(decimal(amountnum,2));
	$('#set_amount').val(decimal(amountsnum,2));
}
/**
 * 总金额折扣
 */
function zjezk(){
	var zk = $('#discount').val();
	if(zk==''){
		zk=10;
		$('#discount').val('10');
		return;
	} 
	var zo = $("#selectchargingItem").datagrid('getRows');
	var je=0;
	var je2=0;
	var zhehou=0;
	$.each(zo,function(k,v){
		//获得该项目最小理论折扣
		zk = $('#discount').val();
		if(Number(v.item_discount)>Number(zk)){
			zk = v.item_discount;
		}
		var num = v.amount*zk*v.itemnum/10;
		$('#selectchargingItem').datagrid('updateRow',{
			index:k,
			row:{
				amounts:parseFloat(decimal(num,2)),
				set_discountss:zk
			}
		})
	}) 
	var hhe= $("#selectchargingItem").datagrid('getRows');
	$.each(zo,function(k,v){
		je+=v.amounts*v.itemnum;
		je2+=v.amount*v.itemnum;
	}) 
	$('#amount').text(decimal(je2,2));
	$('#set_amount').val(decimal(je,2));
} 
/**
 * 必备
 */
 function f_exam_indicators(val, row) {
			var indsstr = '<select name=\"indicators_input\"  id=\"item_required'+ row.item_code + '\"  >'
			    	 +'<option selected value =\"0\">选检</option>'
			    	 +'<option value =\"1\">必检</option>'
			         +'</select>';
			return indsstr;
}
/**
 * 项目清单右
 */
 var  indx = "";
 var s_indx= "s_indx";
 var ff ="";
 var cf ="";
 var sf="";
function selectchargingItem(){
	var chongfu ='1';
	$("#selectchargingItem").datagrid({
		 url:'getsetChargingItem.action',
		 queryParams:{
			 id:$('#id').val()
	 	 },
	 	 sort:"item_seq",
	 	 order:'asc',
	 	 height:330,
	     pageSize:10,
	     pageList:[13,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	            {align:'center',field:'id',title:'操作',width:10,"formatter":del_xg},
	            {align:'center',field:'item_code',title:'项目编码',width:15},	
	            {align:'center',field:'item_seq',title:'顺序码',width:15},
	            {align :'center',field :'item_type',title : '项目类型',width:20},
	            {align:'center',field:'item_name',title:'项目名称',width:25},
	            {align:'center',field:'d_name',title:'科室',width:20},
	            {align:'center',field:'amount',title:'金额',width:15},
	            {align:'center',field:'amounts',title:'折后金额',width:15,editor:{type:'text'}},
	            {align:'center',field:'set_discountss',title:'折扣',width:15,editor:{type:'text'}},
	            {align:'center',field:'itemnum',title:'数量',width:15,editor:{type:'text'}},
	            {align:'center',field:'ischosen',title:'可选/必选',width:20,editor:{type:'combobox',
	            	options:{
	            		data:[{'ischosen':'0','i_name':'可选'},{'ischosen':'1','i_name':'必选'},{'ischosen':'2','i_name':'其他'}],
           			    valueField:'ischosen',    
           			    textField:'i_name',
           			    panelHeight:'auto',
           			    editable:false,
	           			onSelect: function(rec){    
		           	       	var ed = $('#selectchargingItem').datagrid('getEditor', {index:indx,field:'ischosen'});
		    				var txt = $(ed.target).combobox('getText');
		    				$('#selectchargingItem').datagrid('getRows')[indx]['ischosen'] = txt;
	           	           $('#selectchargingItem').datagrid('cancelEdit',indx); 
	           	           sf = indx;
			           	     
	           	        }  
	            	}
	            }}
	 	]],
    	singleSelect:false,
	    collapsible:false,
	    fitColumns:true,//自适应
	    rownumbers:true,
	    striped:true,
        singleSelect:true,//只允许选择一行
        onLoadSuccess : function(value) {
			$("#selectchargingItem").datagrid("hideColumn", "item_type"); // 设置隐藏列
			var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
			if(is_show_discount==0){
				$("#selectchargingItem").datagrid("hideColumn", "amounts"); // 设置隐藏列
				$("#selectchargingItem").datagrid("hideColumn", "set_discountss"); // 设置隐藏列
			}
			
		},
        onDblClickCell:function(index,field,value){
            selectItemTableDBclick(index,field,value,chongfu)
        }
	});
}
var index,field,value,chongfu
function selectItemTableDBclick(index,field,value,chongfu) {
    if(indx=="" && indx ==""){
        //$('#selectchargingItem').datagrid('cancelEdit',index);
    } else if(sf==""){
        $('#selectchargingItem').datagrid('cancelEdit',indx);

    }
    var code = $('#selectchargingItem').datagrid('getRows')[index].item_code;
    cf = code;
    indx = index;
    //----------------------------------------------单项折后价格---------------------------------------------
    if(field=='amounts'){//折后价格
        $('#selectchargingItem').datagrid('selectRow', index)
        $('#selectchargingItem').datagrid("beginEdit",index);//获取输入框
        var ec= $('#selectchargingItem').datagrid('getEditor', {index:index,field:field});//获取输入框目标

        var zhehoujine=$(ec.target).val();

        $(ec.target).focus();//获取输入框焦点
        //获取原价
        var fsj=$('#selectchargingItem').datagrid('getRows')[index].amount;


        /*
      *绑定键盘事件验证输入格式
      */
        $(ec.target).on('keyup', function (event) {
            if($(ec.target).val()>fsj){
                $(ec.target).val(fsj);
            }

            var $amountInput = $(this);
            //响应鼠标事件，允许左右方向键移动
            event = window.event || event;
            if (event.keyCode == 37 | event.keyCode == 39) {
                return;
            }
            //先把非数字的都替换掉，除了数字和.
            $amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
            //只允许一个小数点
            replace(/^\./g, "").replace(/\.{2,}/g, ".").
            //只能输入小数点后两位
            replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));
        });
        //折后价格绑定焦点事件
        $(ec.target).on('blur',function(){
            if($(ec.target).val()>fsj){
                $(ec.target).val(fsj);
            }
            /*  if($(ec.target).val()<0.1){
                 $(ec.target).val(fsj);
             } */
            //关闭销毁editor 编辑框
            $('#selectchargingItem').datagrid('cancelEdit',index);
            //把编辑框的值重新给输入框--折后金额
            $('#selectchargingItem').datagrid('updateRow',{
                index:index,
                row:{
                    amounts:Number($(ec.target).val())
                }
            })
            //获取右表数据
            var fsdata=$('#selectchargingItem').datagrid("getRows");
            var zhzongjia=0;
            var danjia=0;
            $.each(fsdata,function(k,v){
                zhzongjia+=Number(v.amounts);
                danjia+=v.amount;
            })
            //选择行原价
            var   amount =  $('#selectchargingItem').datagrid('getRows')[index].amount;
            //选择行表格折后金额
            var  amounts = $('#selectchargingItem').datagrid('getRows')[index].amounts;
            //选择行表格折扣
            var  set_discountss = $('#selectchargingItem').datagrid('getRows')[index].set_discountss;
            //项目单项数量
            var itemnums = $('#selectchargingItem').datagrid('getRows')[index].itemnum;
            //计算折扣
            var  jisuanzhekou =  amounts/(amount/10);
            //资源
            if($(ec.target).val()!=zhehoujine){
                if(Number(decimal(jisuanzhekou,4))<Number($('#webResource').val())){
                    $.messager.alert("提示信息","本操作员最大权限只能打"+$('#webResource').val()+"折！","error");
                    $('#selectchargingItem').datagrid('updateRow',{
                        index:index,
                        row:{
                            amounts:zhehoujine
                        }
                    })
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                    return;
                }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
                    $.messager.alert("提示信息","没有打折权限！","error");
                    $('#selectchargingItem').datagrid('updateRow',{
                        index:index,
                        row:{
                            amounts:zhehoujine
                        }
                    })
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                    return;
                }
            }

            $('#selectchargingItem').datagrid('updateRow',{
                index:index,
                row:{
                    amounts:Number($(ec.target).val()),
                    set_discountss:Number(decimal(jisuanzhekou, 4))
                }
            })
            $('#selectchargingItem').datagrid('cancelEdit',index);
            //赋值给折后总金额
            $('#set_amount').val(decimal(zhzongjia,2));
            $('#amount').text(decimal(danjia,2));

        });
        //折后价格绑定回车事件
        $(ec.target).bind('keypress', function (event) {
            if (event.keyCode == "13") {
                if($(ec.target).val()>fsj){
                    $(ec.target).val(fsj);
                }
                /* if($(ec.target).val()<0.1){
                    $(ec.target).val(fsj);
                } */
                chongfu=2;
                //关闭销毁editor 编辑框
                $('#selectchargingItem').datagrid('cancelEdit',index);
                //把编辑框的值重新给输入框--折后金额
                $('#selectchargingItem').datagrid('updateRow',{
                    index:index,
                    row:{
                        amounts:Number($(ec.target).val())
                    }
                })
                $('#selectchargingItem').datagrid('cancelEdit',index);
                //选择行原价
                var   amount =  $('#selectchargingItem').datagrid('getRows')[index].amount;
                //选择行表格折后金额
                var  amounts = $('#selectchargingItem').datagrid('getRows')[index].amounts;
                //选择行表格折扣
                var  set_discountss = $('#selectchargingItem').datagrid('getRows')[index].set_discountss;
                //项目单项数量
                var itemnums = $('#selectchargingItem').datagrid('getRows')[index].itemnum;
                //计算折扣
                var  jisuanzhekou = amounts/(amount/10);
                //资源
                if($(ec.target).val()!=zhehoujine){
                    if(Number(decimal(jisuanzhekou,4))<Number($('#webResource').val())){
                        $.messager.alert("提示信息","本操作员最大权限只能打"+$('#webResource').val()+"折！","error");
                        $(ec.target).text(zhehoujine);
                        $('#selectchargingItem').datagrid('updateRow',{
                            index:index,
                            row:{
                                amounts:zhehoujine
                            }
                        })
                        $('#selectchargingItem').datagrid('cancelEdit',index);
                        return;
                    }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
                        $.messager.alert("提示信息","没有打折权限！","error");
                        $(ec.target).text(zhehoujine);
                        $('#selectchargingItem').datagrid('updateRow',{
                            index:index,
                            row:{
                                amounts:zhehoujine
                            }
                        })
                        $('#selectchargingItem').datagrid('cancelEdit',index);
                        return;
                    }
                }

                //把编辑框的值重新给输入框--折后金额
                $('#selectchargingItem').datagrid('updateRow',{
                    index:index,
                    row:{
                        amounts:Number($(ec.target).val()),
                        set_discountss:Number(decimal(jisuanzhekou,4))
                    }
                })
                //获取右表数据
                var fsdata=$('#selectchargingItem').datagrid("getRows");
                var zhzongjia=0;
                var danjias=0;
                $.each(fsdata,function(k,v){
                    zhzongjia+=Number(v.amounts);
                    danjias+=v.amount;
                })

                //赋值给折后总金额
                $('#set_amount').val(decimal(zhzongjia,2));
                $('#amount').text(decimal(danjias,2));
                $('#selectchargingItem').datagrid('cancelEdit',index);
            }
        })
        //-------------------------------------------------单项折扣----------------------------
    }else if(field=='set_discountss'){//折扣
        $('#selectchargingItem').datagrid('selectRow',index)
        $('#selectchargingItem').datagrid("beginEdit",index);//获取输入框
        var ed= $('#selectchargingItem').datagrid('getEditor', {index:index,field:field});//获取输入框目标
        var ziyuan = $(ed.target).val();

        $(ed.target).focus();//获取输入框焦点
        /*
        *绑定键盘事件验证输入格式
        */
        $(ed.target).on('keyup', function (event) {
            if($(ed.target).val()>10){
                $(ed.target).val('10');
            }

            var $amountInput = $(this);
            //响应鼠标事件，允许左右方向键移动
            event = window.event || event;
            if (event.keyCode == 37 | event.keyCode == 39) {
                return;
            }
            //先把非数字的都替换掉，除了数字和.
            $amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
            //只允许一个小数点
            replace(/^\./g, "").replace(/\.{2,}/g, ".").
            //只能输入小数点后两位
            replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
        });

        /**
         *失去焦点
         */
        $(ed.target).bind('blur', function () {
            if($(ed.target).val()>10){
                $(ed.target).val('10');
            }
            /* if($(ed.target).val()<0.1){
                    $(ed.target).val("10");
             } */
            var $amountInput = $(this);
            //最后一位是小数点的话，移除
            $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
            var va = ed.target.val();
            //获取右表点击行记录
            //资源
            if(chongfu=='1'){
                var row = $('#selectchargingItem').datagrid('getSelected');  //双击获取选中行数据
                if(Number($(ed.target).val())<row.item_discount){
                    $.messager.alert("提示信息","本项目最大权限只能打"+row.item_discount+"折！","error");
                    $('#selectchargingItem').datagrid('updateRow',{
                        index:index,
                        row:{
                            set_discountss:ziyuan
                        }
                    })
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                    return;
                }
                if(Number($(ed.target).val())<Number($('#webResource').val()) && Number($(ed.target).val())!=ziyuan){
                    $.messager.alert("提示信息","本操作人最大权限只能打"+$('#webResource').val()+"折！","error");
                    $('#selectchargingItem').datagrid('updateRow',{
                        index:index,
                        row:{
                            set_discountss:ziyuan
                        }
                    })
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                    return;
                }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
                    $.messager.alert("提示信息","没有打折权限！","error");
                    $('#selectchargingItem').datagrid('updateRow',{
                        index:index,
                        row:{
                            set_discountss:ziyuan
                        }
                    })
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                    return;
                }else{
                    var amount = $('#selectchargingItem').datagrid('getRows')[index].amount;
                    //项目单项数量
                    var itemnums = $('#selectchargingItem').datagrid('getRows')[index].itemnum;
                    var num = va*(amount/10)*itemnums;
                    var fsdop=decimal(num,2);
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                    $('#selectchargingItem').datagrid('updateRow',{
                        index:index,
                        row:{
                            amounts:fsdop,
                            set_discountss:va
                        }
                    })
                    var amount=0;//总金额
                    var amounts=0;//总金额折后金额
                    var num=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
                    $.each(num,function(k,v){
                        amount+=v.amount;
                        amounts+=v.amounts;
                    })
                    $('#amount').text(decimal(amount,2));//原价
                    $('#set_amount').val(decimal(amounts,2));//折后金额
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                }
            }
            chongfu='1';

        });
        /**
         *添加回车事件
         */
        $(ed.target).bind('keypress', function (event) {
            if (event.keyCode == "13") {
                if($(ed.target).val()>10){
                    $(ed.target).val('10');
                }
                /* if($(ed.target).val()<0.1){
                    $(ed.target).val("10");
                }  */
                var $amountInput = $(this);
                //最后一位是小数点的话，移除
                $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
                //资源
                if(chongfu=='1'){

                    if(Number($(ed.target).val())!=ziyuan){
                        if(Number($(ed.target).val())<Number($('#webResource').val())){
                            chongfu='2';
                            $.messager.alert("提示信息","本操作员最大权限只能打"+$('#webResource').val()+"折！","error");
                            $('#selectchargingItem').datagrid('updateRow',{
                                index:index,
                                row:{
                                    set_discountss:ziyuan
                                }
                            })
                            $('#selectchargingItem').datagrid('cancelEdit',index);
                            return;
                        }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
                            chongfu='2';
                            $.messager.alert("提示信息","没有打折权限！","error");
                            $('#selectchargingItem').datagrid('updateRow',{
                                index:index,
                                row:{
                                    set_discountss:ziyuan
                                }
                            })
                            $('#selectchargingItem').datagrid('cancelEdit',index);
                            return;
                        }
                    }
                    var va = ed.target.val();
                    //获取右表点击行记录
                    var amount = $('#selectchargingItem').datagrid('getRows')[index].amount;
                    //项目单项数量
                    var itemnums = $('#selectchargingItem').datagrid('getRows')[index].itemnum;
                    var num = va*(amount/10)*itemnums;
                    var fsdop=decimal(num,2);
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                    $('#selectchargingItem').datagrid('updateRow',{
                        index:index,
                        row:{
                            amounts:fsdop,
                            set_discountss:va
                        }
                    })
                    var amount=0;//总金额
                    var amounts=0;//总金额折后金额
                    var num=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
                    $.each(num,function(k,v){
                        amount+=v.amount*v.itemnum;
                        amounts+=v.amounts*v.itemnum;
                    })
                    $('#amount').text(decimal(amount,2));//原价
                    $('#set_amount').val(decimal(amounts,2));//折后金额
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                }
            }
        });
        //------------------------------------------------单项数量-------------------------------------------
    } else if(field == 'itemnum'){
        $('#selectchargingItem').datagrid('selectRow',index)
        $('#selectchargingItem').datagrid("beginEdit",index);//获取输入框
        var ed= $('#selectchargingItem').datagrid('getEditor', {index:index,field:field});//获取输入框目标
        $(ed.target).focus();//获取输入框焦点beginEdit
        //----------------------数量键盘事件---------
        $(ed.target).on('keyup', function (event) {
            var c=$(this);
            if(/[^\d]/.test(c.val())){//替换非数字字符
                var temp_amount=c.val().replace(/[^\d]/g,'');
                $(this).val(temp_amount);
            }else if($(c).val()>9999){
                $(this).val("1");
            }
        });
        //----------------------数量焦点------------
        $(ed.target).bind('blur', function () {
            if($(this).val()<1){
                $(this).val('1');
            }

            //选择行原价
            var   amount =  $('#selectchargingItem').datagrid('getRows')[index].amount;
            //选择行表格折扣
            var  set_discountss = $('#selectchargingItem').datagrid('getRows')[index].set_discountss;
            //项目单项数量
            var itemnums = $('#selectchargingItem').datagrid('getRows')[index].itemnum;

            var  amounts = $('#selectchargingItem').datagrid('getRows')[index].amounts;

            var amounts_s = (amount/10)*set_discountss*$(ed.target).val();
            $('#selectchargingItem').datagrid('updateRow',{
                index:index,
                row:{
                    itemnum:$(ed.target).val(),
                    amounts:amounts_s
                }
            })
            var amount=0;//总金额
            var amounts=0;//总金额折后金额
            $('#selectchargingItem').datagrid('cancelEdit',index);
            var num=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据

            $.each(num,function(k,v){
                amount+=v.amount;
                amounts+=v.amounts;
            })
            $('#amount').text(decimal(amount,2));//原价
            $('#set_amount').val(decimal(amounts,2));//折后金额
        })
        //----------------------数量回车-------------
        $(ed.target).on('keypress', function (event) {
            if (event.keyCode == "13") {
                var  amounts = $('#selectchargingItem').datagrid('getRows')[index].amounts;
                //选择行原价
                var   amount =  $('#selectchargingItem').datagrid('getRows')[index].amount;
                //选择行表格折扣
                var  set_discountss = $('#selectchargingItem').datagrid('getRows')[index].set_discountss;
                //项目单项数量
                var itemnums = $('#selectchargingItem').datagrid('getRows')[index].itemnum;

                var  amounts = $('#selectchargingItem').datagrid('getRows')[index].amounts;

                var amounts_s = (amount/10)*set_discountss*$(ed.target).val();

                $('#selectchargingItem').datagrid('updateRow',{
                    index:index,
                    row:{
                        itemnum:$(ed.target).val(),
                        amounts:amounts_s
                    }
                })
                var amount=0;//总金额
                var amounts=0;//总金额折后金额
                $('#selectchargingItem').datagrid('cancelEdit',index);



                var num=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
                $.each(num,function(k,v){
                    amount+=v.amount;
                    amounts+=v.amounts;
                })
                $('#amount').text(decimal(amount,2));//原价
                $('#set_amount').val(decimal(amounts,2));//折后金额
            }
        })
    } else if(field == 'ischosen'){
        //onClickRow(index);
        $('#selectchargingItem').datagrid('selectRow',index)
        $('#selectchargingItem').datagrid("beginEdit",index); //获取输入框
        //$('#selectchargingItem').datagrid('cancelEdit',index);
    }
}
/**
 * 右表删除按钮
 */
function del_xg(val,row,index){
	return '<a href=\"javascript:dell(\''+index+ '\')\" style="text-decoration:none">删除</a>';
}
/**
 * 右表删除方法
 */
function dell(index){
	 var selections  =$('#selectchargingItem').datagrid('getSelections');
     var selectRows = [];
     for ( var i= 0; i< selections.length; i++) {
       selectRows.push(selections[i]);
     }
     for(var j =0;j<selectRows.length;j++){
       var index = $('#selectchargingItem').datagrid('getRowIndex',selectRows[j]);
       $('#selectchargingItem').datagrid('deleteRow',index);
     }
	
 	var amountsnum=0;//总金额折后金额
	var amountnum=0;
	var num=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
	$.each(num,function(k,v){
		amountnum+=Number(v.amount)
		amountsnum+=Number(v.amounts)
	})
	$('#amount').text(decimal(amountnum,2));
	$('#set_amount').val(decimal(amountsnum,2)); 
}
/**
 * 拼音
 */
function pinyin(){
	$.ajax({
		url:'pinying.action',
		type:'post',
		data:{pinying:$('#addset_name').val()},
		success:function(data){
			$('#set_pinyin').val(data);
		}
	})
}
//---------------------------------------体检套餐新增------------------------------------
function addExamSet(){
	if($('#addset_name').val()==""){
		$('#addset_name').focus();
		return;
	}
	var  ss=$('#addset_name').val();
	if(ss.indexOf("；") >=0 ){
		$.messager.alert("警告信息","套餐名称不能有分号","error");
		return;
	} 
	
	if(ss.indexOf(";") >=0 ){
		$.messager.alert("警告信息","套餐名称不能有分号","error");
		return;
	} 
	
	if($('#set_pinyin').val()==""){
		$('#set_pinyin').focus();
		return;
	}
	if($("#set_num").val()==""){
		$('#set_num').focus();
		return;
	}
	//表格数据
	var rows = $('#selectchargingItem').datagrid('getRows');
	if(rows==""){
		$.messager.alert("警告","请选择项目",'error');
		return;
	}
	var li ="";
	for(var i=0;i<rows.length;i++){
		li = li + JSON.stringify(rows[i]);   
	}
	var yinsu_table_data = $("#yinsu_table_y").datagrid("getRows")
	if($('#id').val()>0){
		$.ajax({
			url:'updateEaxmSet.action',
			type:'post',
			data:{
				hazard_list:JSON.stringify(yinsu_table_data),//因素
				li:li,
				id:$('#id').val(),
				set_name:$('#addset_name').val(),//套餐名称
				set_pinyin:$('#set_pinyin').val(),//套餐拼音
				set_seq:$('#set_seq').val(), //套餐顺序码
				sex:$('input[name="sex"]:checked').val(),//性别
				set_num:$('#set_num').val(),//编码
				set_amount:$('#set_amount').val(),//折后总金额
				set_discount:$('#discount').val(),//总折扣
				price:$('#amount').text(),
				exam_set_type:$('#exam_set_type').combobox('getValue'),
				app_type:$('#intss').val(),
				hazardfactorsid:$('#hazardfactorsid').val(),//职业危害因素编码
				occuphyexaclassid:$('#occuphyexaclassid').val()//职业体检类别编码
			},
			success : function(data) {
				//$.messager.alert('提示信息',data);
				alert(data)
				var _parentWin =  window.opener ;
				_parentWin.getgroupuserGrid();
				window.close();
			},
			error : function() {
				$.messager.alert('提示信息', '操作失败！', 'error');
			}
		})
	}else{
		$.ajax({
			url:'addExamSetFunc.action',
			type:'post',
			data:{
				hazard_list:JSON.stringify(yinsu_table_data),//因素
				li:li,
				set_name:$('#addset_name').val(),//套餐名称
				set_pinyin:$('#set_pinyin').val(),//套餐拼音
				set_seq:$('#set_seq').val(),//套餐顺序码
				sex:$('input[name="sex"]:checked').val(),//性别
				set_num:$('#set_num').val(),//编码
				set_amount:$('#set_amount').val(),//折后总金额
				set_discount:$('#discount').val(),//总折扣
				price:$('#amount').text(),
				exam_set_type:$('#exam_set_type').combobox('getValue'),
				app_type:$('#intss').val(),
				hazardfactorsid:$('#hazardfactorsid').val(),//职业危害因素编码
				occuphyexaclassid:$('#occuphyexaclassid').val()//职业体检类别编码
			},
			success : function(data) {
				//$.messager.alert('提示信息',data);
				alert(data)
				var _parentWin =  window.opener ;
				_parentWin.getgroupuserGrid();
				window.close();
			},
			error : function() {
				$.messager.alert('提示信息', '操作失败！', 'error');
			}
		})
	}
}
//-------------------------------显示体检套餐列表------------------------------------
/**
 * 模糊查询公司信息
 */
var hc_set_list, set_Namess;
function select_com_list(set_Namess) {
	var intss = '<s:property value='intss'/>';
	var url = '';
	if(intss=='1'){//普通体检
		url='satlistshow.action';
	} else {//职业病
		url='zybsatlistshow.action?app_type=2';
	}
	var data = {
		"setname" : set_Namess,
		"sex": $("#custsex").val()
	};
	load_post(url, data, select_set_list_sess);
}
/**
 * 显示树形结构
 * @param data
 * @returns
 */
function select_set_list_sess(data) {
	console.log(data);
	mydtree = new dTree('mydtree', '../../images/img/', 'no', 'no');
	mydtree.add(0, -1, "套餐列表", "javascript:void(0)", "根目录", "_self", false);
	for (var index = 0, l = data.length; index < l; index++) {
		if ((data[index].attributes == null) || (data[index].attributes == '')
				|| (data[index].attributes == '0')) {
			mydtree.add(data[index].id, 0, data[index].text,
					"javascript:setvalue(" + data[index].id + ",'"
							+ data[index].text + "')", data[index].text,
					"_self", false);
		} else {
			mydtree.add(data[index].id, data[index].attributes,
					data[index].text, "javascript:setvalue(" + data[index].id
							+ ",'" + data[index].text + "')", data[index].text,
					"_self", false);
		}
	}
	$("#com_name_list_div").html(mydtree.toString());
	$("#com_name_list_div").css("display", "block");
}
/**
 * 点击树设置内容--赋值套餐
 * @param id
 * @param name
 * @returns
 */
function setvalue(id, name) {
    var chongfu='1';
		$('#tclist').val(name);
		$("#selectchargingItem").datagrid({
			 url:'getsetChargingItem.action',
			 queryParams:{
				 id:id
		 	 },
		 	 height:330,
			 rownumbers:false,
		     pageSize:10,
		     pageList:[13,20,30,40,50],//可以设置每页记录条数的列表 
			 columns:[[
		            {align:'center',field:'id',title:'操作',width:10,"formatter":del_xg},
		            {align:'center',field:'item_code',title:'项目编码',width:15},	
		            {align:'center',field:'item_name',title:'项目名称',width:25},
		            {align:'center',field:'d_name',title:'科室',width:20},
		            {align:'center',field:'amount',title:'金额',width:15},
		            {align:'center',field:'amounts',title:'折后金额',width:15,editor:{type:'text'}},
		            {align:'center',field:'set_discountss',title:'折扣',width:15,editor:{type:'text'}},
		            {align:'center',field:'itemnum',title:'数量',width:15,editor:{type:'text'}},
		            {align:'center',field:'ischosen',title:'可选/必选',width:20,editor:{type:'combobox',
		            	options:{
		            		data:[{'ischosen':'0','i_name':'可选'},{'ischosen':'1','i_name':'必选'}],
	           			    valueField:'ischosen',    
	           			    textField:'i_name',
	           			    panelHeight:'auto',
	           			    editable:false,
		           			onSelect: function(rec){    
			           	       	var ed = $('#selectchargingItem').datagrid('getEditor', {index:indx,field:'ischosen'});
			    				var txt = $(ed.target).combobox('getText');
			    				$('#selectchargingItem').datagrid('getRows')[indx]['ischosen'] = txt;
		           	           $('#selectchargingItem').datagrid('cancelEdit',indx); 
		           	        }  
		            	}
		            }}
		 	]],	    	
	    	singleSelect:false,
		    collapsible:false,
		    fitColumns:true,//自适应
		    striped:true,
		    onLoadSuccess:function(){
		    	var zo = $("#selectchargingItem").datagrid('getRows');
				var zje=0;
				var zh=0;
				$.each(zo,function(k,v){
					zje+=v.amount*v.itemnum;
					zh+=v.amounts*v.itemnum;
				})
				$('#amount').text(decimal(zje,2));
				$('#set_amount').val(decimal(zh,2));
		    },
	        singleSelect:true,//只允许选择一行
            onDblClickCell:function(index,field, value){
                selectItemTableDBclick(index,field, value,chongfu)
            }
		});
	$("#com_name_list_div").css("display", "none");
}
//------------------------数量

function itemnumshuliang(index){
		$('#selectchargingItem').datagrid('selectRow',index)
    	$('#selectchargingItem').datagrid("beginEdit",index);//获取输入框
		var ed= $('#selectchargingItem').datagrid('getEditor', {index:index,field:field});//获取输入框目标
		$(ed.target).focus();//获取输入框焦点beginEdit
		//----------------------数量键盘事件---------
		$(ed.target).on('keyup', function (event) {
			 var c=$(this);  
	            if(/[^\d]/.test(c.val())){//替换非数字字符  
	              var temp_amount=c.val().replace(/[^\d]/g,'');  
	              $(this).val(temp_amount);  
    			}else if($(c).val()>9999){
    					 $(this).val("1"); 
    			}
		}); 
		//----------------------数量焦点------------
		$(ed.target).bind('blur', function () {
			 $('#selectchargingItem').datagrid('updateRow',{
    				index:index,
					row:{
    					itemnum:$(ed.target).val()
					}
    			})  
    			var amount=0;//总金额
				var amounts=0;//总金额折后金额
        			$('#selectchargingItem').datagrid('cancelEdit',index);
	    		var num=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
						$.each(num,function(k,v){
								amount+=v.amount;
								amounts+=v.amounts;
						})
				$('#amount').text(decimal(amount,2));//原价
				$('#set_amount').val(decimal(amounts,2));//折后金额
		})
		//----------------------数量回车-------------
		$(ed.target).on('keypress', function (event) {
	            if (event.keyCode == "13") {
	   	             $('#selectchargingItem').datagrid('updateRow',{
	    				index:index,
						row:{
	    					itemnum:$(ed.target).val()
						}
	    			}) 
	    			var amount=0;//总金额
    				var amounts=0;//总金额折后金额
	           			$('#selectchargingItem').datagrid('cancelEdit',index);
		    		var num=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
							$.each(num,function(k,v){
									amount+=v.amount;
									amounts+=v.amounts;
							})
					$('#amount').text(decimal(amount,2));//原价
    				$('#set_amount').val(decimal(amounts,2));//折后金额
	            }
		}) 
}
//单位失去焦点
var hc_mous_select1 = false;
function select_com_list_over() {
	if (!hc_mous_select1) {
		$("#com_name_list_div").css("display", "none");
	}
}

function select_com_list_mover() {
	hc_mous_select1 = true;
}
function select_com_list_amover() {
	hc_mous_select1 = false;
}
//---------------------------------------------套餐类别---------------------------------------
function examSetType(){
	var type="<s:property value='exam_set_type'/>";
	$('#exam_set_type').combobox({
		panelHeight:200,
		url:"getExamSet_typeList.action",
		valueField:'id',
		textField:'set_type_name',
		onLoadSuccess : function(){//下拉框默认选择
	 	       var val = $(this).combobox('getData');
			   for(var i = 0 ; i < val.length ; i++){
				   if(val[i].id==type){
					   $(this).combobox('select',val[i].id);//设置你找选中的 */
				   } else {
					   $(this).combobox('select',val[0].id);//默认选择第一条
				   }
			   }
	 	    }
	})
}
//带项目
/***
 * 项目带出项目
 * @returns {String}
 */
var d_item = {
		get_item:function(id){//项目带出其他项目数据获取
			var rowsLength = "";
			$.ajax({
				url:'getItemSampleDemoDai.action?id='+id,
				type:'post',
			    async: false,
			    success:function(data){
					rowsLength = eval('('+data+')');
					console.log(rowsLength);
				},error:function(){
					$.messager.alert("提示信息","操作失败","error");
				}
		   })
		   return rowsLength;
		},
	   get_item_2:function(id){//执行添加项目到已选择项目列表流程
		   var row = d_item.get_item(id);
		   for(var i = 0 ; i < row.length ; i ++){
			   row[i].item_amount = row[i].amount;
			   row[i].amount = decimal(row[i].item_amount* $("#discount").val() / 10, 2);
			   d_item.get_item_3(row[i]);
		   }
		
	   },
	   get_item_3:function(row){//添加到已选择项目列表
		 	var itemselect=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
			var f=0;
			var flag=true;
			//判断不能添加重复的记录到右表
			var itemtypeflag=true;
			var selectitemcode="";
				if (itemselect.length > 0) {
					for (var j = 0; j <= itemselect.length - 1; j++)//已选择
					{
						if (row.item_code == itemselect[j].item_code) {
							flag = false;//相等
						}
						if((row.item_type!='')&&(row.item_type==itemselect[j].item_type)){
							itemtypeflag=false;
						}
						selectitemcode=selectitemcode+",'"+itemselect[j].item_code+"'";
					}//j End             
				}
				if (flag) {	
						if(itemtypeflag){
							$('#selectchargingItem').datagrid("appendRow", {
								item_code:row.item_code,
								item_name:row.item_name,
								d_name:row.dep_name,
								amount:row.amount,
								amounts:row.item_amount,
								item_type:row.item_type,
								item_seq:row.item_seq,
								id:row.id,
								set_discountss:$('#discount').val(),
								itemnum:'1'
							});
							/* alert_autoClose("操作提示", "添加成功！","");
							d_item.get_2(row.id);
							zhekou();//总折扣  */
						}else{
							 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
						     if(r){
						    	 $('#selectchargingItem').datagrid("appendRow", {
										item_code:row.item_code,
										item_name:row.item_name,
										d_name:row.dep_name,
										amount:row.amount,
										amounts:row.item_amount,
										item_type:row.item_type,
										id:row.id,
										set_discountss:$('#discount').val(),
										itemnum:'1'
									});
									/* alert_autoClose("操作提示", "添加成功！","");
									d_item.get_2(row.id);
									zhekou();//总折扣  */
								 }
							 });
						}

				}else{
					alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
				}
	   }	
}
//复选框
 function f_exam_indicators(val, row){
		//return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><input type="checkbox"  />';
		return '<input type="checkbox"  class=\'fc'+row.item_code+'\'    name = \'f'+row.item_code+'\'    value = "" id =\'f'+row.item_code+'\'  />';
 }
 function f_exam_indicators1(val, row) {
	    var  deltiemflags = 1 ; 
		if (deltiemflags == 0) {
			return row.exam_indicators;
		} else {
			
			var indsstr = '<select name=\"f'+ row.item_code+ '\"  id=\"f'+ row.item_code	+ '\"  onchange=\"f_exam_indicators_input(\''
			+ row.item_code + '\');\">';
			    	indsstr+='<option selected=selected value =\"0\">选检</option>';
			    	indsstr+='<option  value =\"1\">必检</option>';
			    
			        indsstr+='</select>';
			return indsstr;
		}
}
 function f_exam_indicators_input(row_item_code) {
 	var ll = $('#exam_indicatorss'+ row_item_code).val();
 	//alert(ll);
    $('#exam_indicatorss'+ row_item_code).find("option[value='"+ll+"']").attr("selected",true);
}
//------------------因素
function tj_type(){
	var res = [];
	var data = new Array()
	var obj = new Object()
	$.ajax({
		url:'getZYB_OccuphyexaList.action',
		data:'',
		type:'post',
		async:false,
		dataType:'json',
		success:function (text) {
			res = text.rows
		}
	})
	for (var i = 0; i < res.length; i++) {
		obj = new Object()
		obj.title = res[i].occuphyexaclass_name
		obj.id = res[i].occuphyexaclassID
		obj.occuphyexaclassID = res[i].occuphyexaclassID
		obj.occuphyexaclass_name = res[i].occuphyexaclass_name
		data.push(obj)
	}
	return data
}
function tjlbshow() {
	var tj = tj_type();
	$('#tjlb_type').append('<lable>体检类别</lable>&nbsp;&nbsp;&nbsp;&nbsp;'+'<br/>')
	for(var i=0; i< tj.length; i++){
		if(i == 0){
			var html = '<input type="radio" onclick="zywhys_html()" checked name="tj_lb"  value="'+tj[i].occuphyexaclassID+'" data-text="'+tj[i].title+'">'+tj[i].title
					+'&nbsp;&nbsp;&nbsp;&nbsp;'
		} else {
			var html = '<input type="radio" onclick="zywhys_html()" name="tj_lb"  value="'+tj[i].occuphyexaclassID+'" data-text="'+tj[i].title+'">'+tj[i].title
					+'&nbsp;&nbsp;&nbsp;&nbsp;'
		}
		$('#tjlb_type').append(html+'<br/>')
	}
}

/**
 * 危害因素
 */
function getgetOccuhazardfactor() {
	$('#yinsu').combobox({
		panelHeight:200,
		url:"getgetOccuhazardfactor.action",
		valueField:'hazard_code',
		textField:'hazard_name',
		multiple:true,
		onLoadSuccess : function(){//下拉框默认选择
			var val = $(this).combobox('getData');
			for(var i = 0 ; i < val.length ; i++){
				if(val[i].id==type){
					$(this).combobox('select',val[i].id);//设置你找选中的 */
				} else {
					$(this).combobox('select',val[0].id);//默认选择第一条
				}
			}
		},
		onSelect:function (record) {
			var yinsu = $('#yinsu').combobox('getValues')
			var tj_lb = $('input[name="tj_lb"]:checked').val();
			console.log(yinsu)
			if(checkHazard(yinsu,tj_lb)!=1){
				obj = new Object()
				obj.hazard_code = record.code   //职业危害因素编码
				obj.hazard_name = record.hazard_name   //职业危害因素名称
				obj.occuphyexaclassID = tj_lb //体检类别
				obj.occuphyexaclass_name = $('input[name="tj_lb"]:checked').data('text')
				obj.hazard_year = record.hazard_year
				hazard_list.push(obj)
				hazardExamSet(tj_lb,record.hazardfactorsID)
			}

		},
		onUnselect:function (record) {
			for(var  i = 0 ; i < hazard_list.length ; i++){
				if(hazard_list[i].hazard_code == hazard_code && hazard_list[i].occuphyexaclassid == $('input[name="tj_lb"]:checked').val()){
					hazard_list.splice(i,1)
					break
				}
			}
		}
	})
}
/**
 * 因素带出项目
 * occuphyexaclassid  体检类别id
 * hazardfactorsid  因素id
 */
var occuphyexaclassid,hazardfactorsid
function hazardExamSet(occuphyexaclassid,hazardfactorsid) {
	console.log(occuphyexaclassid)
	console.log(hazardfactorsid)
	//因素获取套餐
	$.ajax({
		url:'getZybExamSetList.action',
		type:'post',
		data:{
			rows:9999,
			occuphyexaclassid:occuphyexaclassid,  //体检类别
			hazardfactorsid:'\''+hazardfactorsid+'\''     //因素id
		},
		dataType:'json',
		success:function(res){
			//循环套餐
			if(res.rows!=''){
				var row = res.rows
				for(var i = 0 ; i < row.length;i++){
					//根据因素与类别带出项目
					$.ajax({
						url:'getsetChargingItem.action',
						type:'post',
						data:{
							id:row[i].id   //套餐id
						},
						dataType:'json',
						success:function(data){
							//循环项目  添加到已选table元素
							for(var i = 0 ; i < data.length ;i++)
								hazard_item_add(data[i])
						}
					})
				}
			}
		}
	})
}
/**
 *
 * @param hazard_code 因素编码
 * @param tjlb  体检类别id
 * @returns {number}
 */
function checkHazard(hazard_code,tjlb){
	var fal = 2
	for(var  i = 0 ; i < hazard_list.length ; i++){
		if(hazard_list[i].hazard_code == hazard_code && hazard_list[i].occuphyexaclassid == tjlb){
			fal = 1
			break
		}
	}
	return fal
}
/**
 * 已选项目渲染页面
 */
function hazard_item_add(item){
    debugger
	console.log("因素带出来的项目"+item)
	var itemselect=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
	var row= item//获取选择行的数据
	var f=0;
	var flag=true;
	//判断不能添加重复的记录到右表
	var itemtypeflag=true;
	var selectitemcode="";
	if (itemselect.length > 0) {
		for (var j = 0; j <= itemselect.length - 1; j++)//已选择
		{
			if (row.item_code == itemselect[j].item_code) {
				flag = false;//相等
                debugger
                if(row.ischosen=='必选'){
                    $('#selectchargingItem').datagrid('updateRow',{
                        index:j,
                        row: {
                            item_code:row.item_code,
                            item_name:row.item_name,
                            d_name:row.d_name,
                            amount:row.amount,
                            amounts:row.amount,
                            item_type:row.item_type,
                            item_seq:row.item_seq,
                            id:row.id,
                            set_discountss:'10',
                            itemnum:'1',
                            ischosen:row.ischosen
                        }
                    });
                    d_item.get_item_2(row.id);
                    zhekou();//总折扣
                }
			}

            if((row.item_type!='')&&(row.item_type==itemselect[j].item_type)){
				itemtypeflag=false;
			}
			selectitemcode=selectitemcode+",'"+itemselect[j].item_code+"'";
		}//j End
	}
	if (flag) {
		if(itemtypeflag){
			$('#selectchargingItem').datagrid("appendRow", {
				item_code:row.item_code,
				item_name:row.item_name,
				d_name:row.d_name,
				amount:row.amount,
				amounts:row.amount,
				item_type:row.item_type,
				item_seq:row.item_seq,
				id:row.id,
				set_discountss:'10',
				itemnum:'1',
				ischosen:row.ischosen
			});
			//alert_autoClose("操作提示", "添加成功！","");
			d_item.get_item_2(row.id);
			zhekou();//总折扣
		}
	}
}

/**
 * 因素左列表
 */
function yinsu_table() {
	$('#yinsu_table').datagrid({
		queryParams:{
			pageSize:9999,
			hazard_name:$('#c_hazard_name').val()
		},
		url:'getOccuHazardFactorsList.action',
		columns:[[
			/*{field:'hazardfactorsID',title:'ID',width:100,align:'left'},*/
			{field:'hazard_code',title:'编码因素名称',width:100,align:'left'},
			{field:'hazard_name',title:'因素名称',width:100,align:'left'},
			{field:'hazard_year',title:'年限',width:100,align:'center'}
		]],
		height:200,
		fitColumns:true,//自适应
		onDblClickRow:function(index, row){
			var yinsu = row.hazard_code
			var tj_lb = $('input[name="tj_lb"]:checked').val();
			appTableTr(index, row)
			// if(checkHazard(yinsu,tj_lb)!=1){
			// 	obj = new Object()
			// 	obj.hazard_code = row.hazard_code   //职业危害因素编码
			// 	obj.hazard_name = row.hazard_name   //职业危害因素名称
			// 	obj.occuphyexaclassID = tj_lb //体检类别
			// 	obj.occuphyexaclass_name = $('input[name="tj_lb"]:checked').data('text')
			// 	obj.hazard_year = row.hazard_year
			// 	hazard_list.push(obj)
			//
			// }
		}
	})
}
function appTableTr(index, row) {
	var y_row = $('#yinsu_table_y').datagrid('getRows')
	var fal = 1;
	for(var i = 0 ; i < y_row.length; i++){
		if(y_row[i].hazard_code == row.hazard_code && y_row[i].occuphyexaclassID == $('input[name="tj_lb"]:checked').val()){
			fal = 2;
			break
		}
	}
	if(fal!=2){
		$('#yinsu_table_y').datagrid("appendRow", {
			hazard_code:row.hazard_code,
			hazard_name:row.hazard_name,
			hazard_year:row.hazard_year,
			occuphyexaclass_name:$('input[name="tj_lb"]:checked').data('text'),
			occuphyexaclassID:$('input[name="tj_lb"]:checked').val()
			//tjlb:row.tjlb
		});
		hazardExamSet($('input[name="tj_lb"]:checked').val(),row.hazardfactorsID)
	}
}
function yinsu_table_y() {
	$('#yinsu_table_y').datagrid({
		queryParams:{
			pageSize:9999,
			set_id:$('#id').val()
		},
		url:'getExamSetHazardList.action',
		height:200,
		fitColumns:true,//自适应
		columns:[[
			{align:'center',field:'id',title:'操作',width:30,"formatter":yinsu_del_xg},
			{field:'hazard_code',title:'编码',width:100,align:'left'},
			{field:'hazard_name',title:'因素名称',width:100,align:'left'},
			{field:'hazard_year',title:'年限',width:100,align:'center'},
			{field:'occuphyexaclass_name',title:'体检类别',width:100,align:'center'}
		]]
	})
}
function yinsu_del_xg(val,row,index){
	return '<a href=\"javascript:yinsu_dell(\''+index+ '\')\" style="text-decoration:none">删除</a>';
}
/**
 * 右表删除方法
 */
function yinsu_dell(index){
	var selections  =$('#yinsu_table_y').datagrid('getSelections');
	var selectRows = [];
	for ( var i= 0; i< selections.length; i++) {
		selectRows.push(selections[i]);
	}
	for(var j =0;j<selectRows.length;j++){
		var index = $('#yinsu_table_y').datagrid('getRowIndex',selectRows[j]);
		$('#yinsu_table_y').datagrid('deleteRow',index);
	}
}


</script>
<!--资源  -->
<input type="hidden"  id='webResource'  value = "<s:property value='webResource'/>"/>
<input type="hidden"  id='intss'  value = "<s:property value='intss'/>"/>
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">

<!-- 职业体检类别编码 -->
<input type="hidden"  id='occuphyexaclassid'  value = "<s:property value='occuphyexaclassid '/>"/>

<!-- 职业危害因素编码 -->
<input type="hidden"  id='hazardfactorsid'  value = "<s:property value='hazardfactorsid'/>"/>

<fieldset style=" margin:10px;margin-bottom:0px;margin-top:5px;">
	<legend><strong>因素</strong></legend>

	<div class="formDiv" id="tjlb_type1">

	</div>
	<div>
		<label>危害因素</label>
		<%--<input id="yinsu" value="" style="width:200px" name="yinsu">--%>
		<input id="c_hazard_name" value="" class="textinput" style="width:150px;height: 26px" name="c_yinsu">
		<a href="javascript:yinsu_table();" class="easyui-linkbutton c6" style="width:100px;">查询</a>
	</div>
	<fieldset style="width:40%;float: left">
		<legend><strong>因素列表&nbsp;&nbsp;&nbsp;&nbsp;双击选择因素</strong></legend>
		<div style="width:100%;">
			<table id="yinsu_table" style="float: left;width:100%"></table>
		</div>
	</fieldset>
	<div id="tjlb_type" style="float: left;margin-left: 20px">

	</div>
	<fieldset style="width:49%;float: left">
		<legend><strong>已选因素</strong></legend>
		<div style="width:100%;">
			<table id="yinsu_table_y" style="width:100%;float: right"></table>
		</div>
	</fieldset>
</fieldset>
 <fieldset style=" margin:10px;margin-bottom:0px;margin-top:5px; height:90px">
	<legend><strong>体检套餐编辑</strong></legend> 
	<div class="formDiv">
		<dl>
			<dt style="width:100px">套餐名称</dt>
			<dd>
				<input type="hidden" id="id" value="<s:property value='id'/>" />
				<input type="text" maxlength="40" value="<s:property value='set_name'/>" class="textinput" id="addset_name"   onkeyup="tcmc(this);"   style="height: 26px; width: 244px;"/>
			</dd>
			<dt style="width:60px">拼音</dt>
			<dd>
				<input type="text" maxlength="100" value="<s:property value='set_pinyin'/>" class="textinput" id="set_pinyin" style="height: 26px; width: 100px;"/>
			</dd>
			<dt style="width:60px">顺序码</dt>
			<dd>
				<input type="text" maxlength="100" value="<s:property value='set_seq'/>" class="textinput" id="set_seq" style="height: 26px; width: 100px;"/>
			</dd>
			<dt style="width:100px"><!-- 套餐编码 -->套餐类别</dt>
			<dd>
				<input type="hidden" class="textinput" id="set_num"    value="<s:property value='set_num'/>"    />
				<input type="text" class="textinput" id="exam_set_type"   value="<s:property value='exam_set_type'/>"  style="height: 26px; width: 244px;"   /> 
			</dd>
		</dl>
		<dl>
			<dt style="width:100px">项目查询</dt>
			<dd>
			<input type="text" class="textinput" id="item_name"  onkeyup="getchargingItem();" maxlength="45" style="height: 26px; width: 244px;" />
			</dd>
			<dt style="width:120px">
				适应人群
			</dt>
			<dd style="width: 244px">
				&nbsp;&nbsp;&nbsp;
				<input type="radio" name="sex" id="sexQuanbu" value="全部" checked="checked"/>全部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="sex" id="sexNan" value="男"/>男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="sex" id="sexNvv" value="女"/>女
			</dd>
			<dt style="width:80px">复制套餐</dt>
			<dd>
				<input type="hidden" id="yincang"/>
				<input type="text" class="textinput" id="tclist"  style="height: 26px; width: 244px;"/>
				<div id="com_name_list_div" style="display: none;margin-left: 770px"
					onmouseover="select_com_list_mover()"
					onmouseout="select_com_list_amover()">
				</div>
			</dd>
		</dl>
	</div>
</fieldset>
<div id="common" style="margin-right: 0px">
   <fieldset style="margin-bottom:5px;margin-right:0px;margin-left:10px;height:380px;width:43.25%;float:left">
   &nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;
		<s:if test='intss=="2" '>
			<input type="checkbox"  id = "pt_exam" onclick="getchargingItem();"  value='' />普通体检项目
		</s:if>
		<legend><strong>项目清单</strong></legend>
		<div >
				<table id="getchargingItem" ></table>
		</div>
  </fieldset>
  <fieldset style="margin-left:0px;margin-right:10px;margin-bottom:0px;height:380px;width:51.1%;float:right">
	<legend><strong>已选择项目</strong></legend>
		<div >
			<table id="selectchargingItem"></table>
			<p style="font-size:16px;line-height: 40px">
					套餐金额:
					<span id="amount"></span>元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span id = "zk">折扣:</span>
					<!-- zjezk() -->
					<input  id="discount"  maxlength="3" value="10"   class="textinput" style="width: 40px; height: 30px" /><span id = "zhekou">折</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span id="zhje">折后金额:</span>
					<%-- <span id="set_amount" ></span>元 --%>
					<input  id="set_amount"  class="textinput" style="width: 80px; height: 30px" /><span id="yuan">元</span>
			</P>
		</div>
	</fieldset>
</div>
<div style="margin-top: 20px;margin-bottom: 20px;width:500px;float: right;text-align: right">
	<div style="width:300px;float: left">	<span   id="zhis"  ></span></div>
	<div>
		<a href="javascript:addExamSet()"  class="easyui-linkbutton c6" style="width:100px;">保存</a>
		<a href="javascript:window.close()" class="easyui-linkbutton" style="width:80px;" >关闭</a>
	</div>

</div>
