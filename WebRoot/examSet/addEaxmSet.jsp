<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
var zzks="";
var ddss='1';
$(document).ready(function(){
	var ss ="<s:property value='intss'/>"
	if(ss==2){
		if($('#addset_name').val()=="" && $('#ys_name').val()!="" && $('#lb_name').val()!=""){
			var s_name = $('#ys_name').val()+"("+$('#lb_name').val()+")";
			$('#addset_name').val(s_name)
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
			var rows = $("#selectchargingItem").datagrid('getRows');
		        var total = 0;
		        for (var i = 0; i < rows.length; i++) {
// 		            total += rows[i]['item_discount'];
		            if(rows[i]['item_discount']>total){
		            	total=rows[i]['item_discount'];
		            }
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
		    if(Number($("#discount").val())<total){
		    	$.messager.confirm('提示信息','存在项目折扣小于折扣的项目，是否继续？',function(r){
					if(r){
						 zjezk();
					  }
				})
		    }else{
		    	 zjezk();
		    }
		   
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
			}else if(Number($("#discount").val())<total){
				allAmount += decimal(Number(v.amount)*Number(total)*Number(v.itemnum)/10,2);
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
		//折扣率
		$("#discount").val(decimal(Number($("#set_amount").val())/itemAmout*10, 4))
		
		var oldamount=$('#set_amount').val();
		var amount=0;//总金额
        var amounts=0;//总金额折后金额
        var num=$('#selectchargingItem').datagrid("getRows");//获取已添加的数据
        $.each(num,function(k,v){
            amount+=v.amount;
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
	 	 height:365,
		 rownumbers:false,
	     pageSize:10,
	     pageList:[10,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
	            {align:'center',field:'item_code',title:'收费项目编码',width:15},	
	            {align:'center',field:'item_name',title:'收费项目名称',width:20},
	        	{align:'center',field:'item_amount',title:'金额',width:10},
	        	{align:'center',field:'item_seq',title:'顺序码',width:10},
	        	{align:'left',field:'app_type_s',title:'应用类型',width:10},
	        	{align:'left',field:'item_discount',title:'最大折扣',width:10,hidden:'true'},
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
						item_discount:row.item_discount,
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
								item_discount:row.item_discount,
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
	            		data:[{'ischosen':'1','i_name':'必选'},{'ischosen':'0','i_name':'可选'},{'ischosen':'2','i_name':'其他'}],
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
			
		} ,
        onDblClickCell:function(index,field,value) {
//         	 var d_name = $('#selectchargingItem').datagrid('getRows')[index].d_name;
//         	 if(d_name=="耗材科"){
        		 selectDBclick(index,field,value,chongfu);
//         	 }
           
        } 
	});
}
var index,field,value
function selectDBclick(index,field,value,chongfu) {
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
                if(Number(decimal(jisuanzhekou,2))<Number($('#webResource').val())){
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
                    if(Number(decimal(jisuanzhekou, 4))<Number($('#webResource').val())){
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
                        set_discountss:Number(decimal(jisuanzhekou, 4))
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
            debugger
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
	if($('#id').val()>0){
		$.ajax({
			url:'updateEaxmSet.action',
			type:'post',
			data:{
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
				$.messager.alert('提示信息',data);
				$('#dlg-custedit').dialog('close')
				$('#groupusershow').datagrid('reload');
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
				$.messager.alert('提示信息',data);
				$('#dlg-custedit').dialog('close');
				$('#groupusershow').datagrid('reload')
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
		           	        //----------不好改--------------
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
                selectDBclick(index,field,value)
	        }
		});
	
	$("#com_name_list_div").css("display", "none");
}
function cc2() {
    var c_item_code = $('#selectchargingItem').datagrid('selectRow', index).item_code;
    var  f_item_code = $('#f'+c_item_code).val();
    alert(c_item_code);
    if($('#f'+c_item_code).is(":checked")){
        alert("复选框选中");
    }
    $('#selectchargingItem').datagrid("beginEdit",field);//获取输入框
    if (endEditing()){
        $('#selectchargingItem').datagrid('selectRow', index)
            .datagrid('editCell',{index:index,field:field});
        editIndex=index;
    }
    //----------------------------------------------单项折后价格-----------------------------------------
    if(field=='amounts'){//折后价格

        $('#selectchargingItem').datagrid('selectRow', index)
        $('#selectchargingItem').datagrid("beginEdit",index);//获取输入框
        var ec= $('#selectchargingItem').datagrid('getEditor', {index:index,field:field});//获取输入框目标
        var zhehoujine = $(ec.target).val();
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
            //回车事件
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
            if($(ec.target).val()<0.01){
                $(ec.target).val(fsj);
            }
            if($(ec.target).val()>fsj){
                $(ec.target).val(fsj);
            }
            //关闭销毁editor 编辑框
            $('#selectchargingItem').datagrid('cancelEdit',index);
            //把编辑框的值重新给输入框--折后金额
            $('#selectchargingItem').datagrid('updateRow',{
                index:index,
                row:{
                    amounts:Number($(ec.target).val())
                }
            })

            //选择行原价
            var   amount =  $('#selectchargingItem').datagrid('getRows')[index].amount;
            //选择行表格折后金额
            var  amounts = $('#selectchargingItem').datagrid('getRows')[index].amounts;
            //选择行表格折扣
            var  set_discountss = $('#selectchargingItem').datagrid('getRows')[index].set_discountss;
            //计算折扣
            var  jisuanzhekou = amounts/(amount/10);

            //获取右表数据
            var fsdata=$('#selectchargingItem').datagrid("getRows");
            var zhzongjia=0;
            var danjia=0;
            $.each(fsdata,function(k,v){
                zhzongjia+=Number(v.amounts);
                danjia+=v.amount;
            })



            //资源
            if(Number(decimal(jisuanzhekou, 4))<Number($('#webResource').val()) && $(ec.target).val()!=zhehoujine){
                $.messager.alert("提示信息","本操作员最大权限只能打"+$('#webResource').val()+"折！","error");
                $('#selectchargingItem').datagrid('updateRow',{
                    index:index,
                    row:{
                        amounts:zhehoujine,
                    }
                })
                $('#selectchargingItem').datagrid('cancelEdit','index');
                return;
            }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
                $('#selectchargingItem').datagrid('updateRow',{
                    index:index,
                    row:{
                        amounts:zhehoujine,
                    }
                })
                $('#selectchargingItem').datagrid('cancelEdit','index');
                $.messager.alert("提示信息","没有打折权限！","error");
                return;
            }else{
                $('#selectchargingItem').datagrid('updateRow',{
                    index:index,
                    row:{
                        amounts:Number($(ec.target).val()),
                        set_discountss:Number(decimal(jisuanzhekou, 4))
                    }
                })
                //赋值给折后总金额
                $('#set_amount').val(decimal(zhzongjia,2));
                $('#amount').text(decimal(danjia,2));
            }


        });
        //折后价格绑定回车事件
        $(ec.target).bind('keypress', function (event) {
            if (event.keyCode == "13") {
                /*  if($(ec.target).val()<0.01){
                 $(ec.target).val(fsj);
             } */
                if($(ec.target).val()>fsj){
                    $(ec.target).val(fsj);
                }
                $('#selectchargingItem').datagrid('cancelEdit',index);
                //把编辑框的值重新给输入框--折后金额
                $('#selectchargingItem').datagrid('updateRow',{
                    index:index,
                    row:{
                        amounts:Number($(ec.target).val())
                    }
                })

                //选择行原价
                var   amount =  $('#selectchargingItem').datagrid('getRows')[index].amount;
                //选择行表格折后金额
                var  amounts = $('#selectchargingItem').datagrid('getRows')[index].amounts;
                //选择行表格折扣
                var  set_discountss = $('#selectchargingItem').datagrid('getRows')[index].set_discountss;
                //计算折扣
                var  jisuanzhekou = amounts/(amount/10);

                //资源
                if($(ec.target).val()!=zhehoujine){
                    if(Number(decimal(jisuanzhekou, 4))<Number($('#webResource').val())){
                        $.messager.alert("提示信息","本操作人员最大权限只能打"+$('#webResource').val()+"折！","error");
                        $('#selectchargingItem').datagrid('updateRow',{
                            index:index,
                            row:{
                                amounts:Number(zhehoujine)
                            }
                        })
                        $('#selectchargingItem').datagrid('cancelEdit','index');
                    }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
                        $.messager.alert("提示信息","没有打折权限！","error");
                        $('#selectchargingItem').datagrid('updateRow',{
                            index:index,
                            row:{
                                amounts:Number(zhehoujine)
                            }
                        })
                        $('#selectchargingItem').datagrid('cancelEdit','index');
                    }else{
                        $('#selectchargingItem').datagrid('updateRow',{
                            index:index,
                            row:{
                                amounts:Number($(ec.target).val()),
                                set_discountss:Number(decimal(jisuanzhekou, 4))
                            }
                        })


                        //获取右表数据
                        var fsdata=$('#selectchargingItem').datagrid("getRows");
                        var zhzongjia=0;
                        var danjia2=0;
                        $.each(fsdata,function(k,v){
                            zhzongjia+=Number(v.amounts)*v.itemnum;
                            danjia2+=v.amount*v.itemnum;
                        })


                        //赋值给折后总金额
                        $('#set_amount').val(decimal(zhzongjia,2));
                        $('#amount').text(decimal(danjia2,2));
                    }
                }

            }
        })
        //---------------------------------------单项数量-------------------------------------------------
    } else if(field=='itemnum'){
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
            if($(ed.target).val()<1){
                $(ed.target).val("1")
            }
            var item = $(ed.target).val();
            $('#selectchargingItem').datagrid('updateRow',{
                index:index,
                row:{
                    itemnum:item
                }
            })
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
                if($(ed.target).val()<1){
                    $(ed.target).val("1")
                }
                var item = $(ed.target).val();
                $('#selectchargingItem').datagrid('updateRow',{
                    index:index,
                    row:{
                        itemnum:item
                    }
                })
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
        //-------------------------单项折扣-------------------------------------
    }else if(field == 'set_discountss'){

        $('#selectchargingItem').datagrid("beginEdit",field);//获取输入框
        $('#selectchargingItem').datagrid('selectRow', index);
        var ed= $('#selectchargingItem').datagrid('getEditor', {index:index,field:field});//获取输入框目标
        var zyzhekou=$(ed.target).val();
        $(ed.target).focus();//获取输入框焦点
        /*
        *绑定键盘事件验证输入格式
        */
        $(ed.target).on('keyup', function (event) {
            if($(ed.target).val()>10){
                $(ed.target).val('10');
            }
            /* if($(ed.target).val()<0.1){
                   $(ed.target).val("10");
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
            replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
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
                    $(ed.target).val('10');
                }   */


                var $amountInput = $(this);
                //最后一位是小数点的话，移除
                $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
                var va = ed.target.val();
                if(chongfu=='1'){
                    if(Number($(ed.target).val())<Number($('#webResource').val()) && ($(ed.target).val())!=zyzhekou){
                        chongfu='2';
                        $.messager.alert("提示信息","本操作员最大权限只能打"+$('#webResource').val()+"折！","error");
                        $('#selectchargingItem').datagrid('updateRow',{
                            index:index,
                            row:{
                                set_discountss:zyzhekou
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
                                set_discountss:zyzhekou
                            }
                        })
                        $('#selectchargingItem').datagrid('cancelEdit',index);
                        return;
                    }else{
                        //获取右表点击行记录
                        var amount = $('#selectchargingItem').datagrid('getRows')[index].amount;
                        var va = ed.target.val();
                        //获取右表点击行记录
                        var amount = $('#selectchargingItem').datagrid('getRows')[index].amount;
                        //获取右表点击行记录
                        var amount = $('#selectchargingItem').datagrid('getRows')[index].amount;
                        //项目单项数量
                        var itemnums = $('#selectchargingItem').datagrid('getRows')[index].itemnum;
                        var num = va*(amount/10)*itemnums;

                        var fsdop=parseFloat(decimal(num,2));
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
                    }
                }
            }

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
            }    */
            var $amountInput = $(this);
            //最后一位是小数点的话，移除
            $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
            if(chongfu=='1'){

                if(Number($(ed.target).val())<Number($('#webResource').val())&&Number($(ed.target).val())!=zyzhekou){
                    $.messager.alert("提示信息","本操作员最大权限只能打"+$('#webResource').val()+"折！","error");
                    $(ed.target).val(zyzhekou);
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                    return;
                }else if($('#webResource').val()=="" || $('#webResource').val()==undefined){
                    $.messager.alert("提示信息","没有打折权限！","error");
                    $(ed.target).val(zyzhekou);
                    $('#selectchargingItem').datagrid('cancelEdit',index);
                    return;
                }else{
                    var va = ed.target.val();
                    //获取右表点击行记录
                    var amount = $('#selectchargingItem').datagrid('getRows')[index].amount;
                    //获取右表点击行记录
                    var amount = $('#selectchargingItem').datagrid('getRows')[index].amount;
                    //项目单项数量
                    var itemnums = $('#selectchargingItem').datagrid('getRows')[index].itemnum;
                    var num = va*(amount/10)*itemnums;


                    //var itemnums = $('#selectchargingItem').datagrid('getRows')[index].item_required;

                    var fsdop=parseFloat(decimal(num,2));
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
                }
            }
            chongfu='1';

        });
    } else if(field == 'ischosen'){
        //--fc不好改
        //onClickRow(index);
        $('#selectchargingItem').datagrid('selectRow',index)
        $('#selectchargingItem').datagrid("beginEdit",index); //获取输入框
        //$('#selectchargingItem').datagrid('cancelEdit',index);
    }
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


</script>
<!--资源  -->
<input type="hidden"  id='webResource'  value = "<s:property value='webResource'/>"/>
<input type="hidden"  id='intss'  value = "<s:property value='intss'/>"/>
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">

<!-- 职业体检类别编码 -->
<input type="hidden"  id='occuphyexaclassid'  value = "<s:property value='occuphyexaclassid '/>"/>

<!-- 职业危害因素编码 -->
<input type="hidden"  id='hazardfactorsid'  value = "<s:property value='hazardfactorsid'/>"/>

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
				<input type="hidden" class="textinput" id="set_num"   disabled="disabled" value="<s:property value='set_num'/>"  style="height: 26px; width: 244px;"   /> 
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
			
			
		<!-- 	<input
				class="easyui-textbox" type="text" id="tclist" value="" />
			<div id="com_name_list_div" style="display: none"
				onmouseover="select_com_list_mover()"
				onmouseout="select_com_list_amover()"></div> -->
			
			
		</dl>
	</div>
</fieldset>
	<div id="common" style="margin-bottom: 10px;margin-right: 0px">
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

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
		<div>
			<span   id="zhis" ></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <a href="javascript:addExamSet();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>