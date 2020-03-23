
		var exam_id ="";
		var sex="";
		var exam_indicator="";
		var exam_num = "";
		function getQueryString(name) {
		    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
		    var r = window.location.search.substr(1).match(reg);
		    if (r != null) {
		        return unescape(r[2]);
		    }
		    return null;
		}
    	$(function(){
    		 exam_id = getQueryString("exam_id");
    		 exam_num = getQueryString("exam_num");
    		 sex = decodeURI(getQueryString("sex"));
    		 exam_indicator=getQueryString("exam_indicator");
    		getgroupuserGrid();	
    		getgroupuserGrid1();
    		gettotalinfo();
			
    	});
    	function getgroupuserGrid() { 
    		$('#medicalPackage').bootstrapTable({  
				  method: 'get',  
				  url: 'exam_tclistshow.action',  
				  dataType: "json",  
				  striped: true,     //使表格带有条纹  
				  pagination: false, //在表格底部显示分页工具栏  
 				  pageSize: 10,  
 				  pageNumber: 1,  
				  pageList: [10, 20, 50],  
				  idField: "exam_id",  //标识哪个字段为id主键  
				  showToggle: false,   //名片格式  
				  cardView: false,//设置为True时显示名片（card）布局  
				  showColumns: true, //显示隐藏列    
				  showRefresh: true,  //显示刷新按钮  
				  singleSelect: true,//复选框只能选择一条记录  
				  search: false,//是否显示右上角的搜索框  
				  queryParams: function queryParams(params){
			            var param = {    
			            		exam_id:exam_id,
			            		exam_num:exam_num
			                };   
			                return param;    
			           }, //参数 
				  clickToSelect: true,//点击行即可选中单选/复选框  
				  sidePagination: "server",//表格分页的位置  
				  queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求  
				  toolbar: "#toolbar", //设置工具栏的Id或者class  
				  columns: [{
	 					field: 'set_num',
						title: '套餐编码',
						switchable: true
					}, {
						field: 'set_name',
						title: '套餐名称',
						switchable: true
					}, {
						field: 'sex',
						title: '适用性别',
						switchable: true
					}, {
						field: 'set_discount',
						title: '套餐折扣率',
						switchable: true,
						sortable: true
					}, {
						field: 'set_amount',
						title: '套餐金额',
						switchable: true
					},{
						field : '',
	                title : '操作',
	                align : 'center',
	                valign : 'middle',
	                formatter: operateFormatter
	                }]
				}); 
    	}
    	function operateFormatter(value, row, index) {
            return ['<button type="button" class="RoleOfedita btn btn-primary" style="margin-right:15px;" onclick = "djtdeletetc(\''+row.set_num+'\');">删除</button>'
                   
            ].join('')
    }
    	function djtdeletetc(userid){
    	    if((userid=='')||(userid.length<=0)){
    	 		bootbox.alert( "选择项目无效");
    	 	}else{	 
    	 		bootbox.confirm("是否确定删除选中套餐？", function(r){
    			 	if(r){
    			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    					 $("body").prepend(str);
    		 $.ajax({
    				url : 'djtcustomersetdelshow.action',
    				data : {
    					exam_id : exam_id,
    					exam_num:exam_num,
    			        ids:userid
    				    },
    						type : "post",//数据发送方式   
    						success : function(text) {
     							$(".loading_div").remove();
     							if (text.split("-")[0] == 'ok') {
     								bootbox.alert( text);
     								window.location.reload();
     							} else {
     								bootbox.alert(text);
     							}
    						},
    						error : function() {
    							$(".loading_div").remove();
    							bootbox.alert( "操作错误");
    						}
    					});
    	   }
    		 });
    	 	}
    	}

    	function getgroupuserGrid1() { 
    		
    		$('#medicalPackage1').bootstrapTable({  
				  method: 'get',  
				  url: 'djtcustchangitemlist.action',  
				  dataType: "json",  
				  striped: true,     //使表格带有条纹  
				  pagination: false, //在表格底部显示分页工具栏  
 				  pageSize: 10,  
 				  pageNumber: 1,  
				  pageList: [10, 20, 50],  
				  idField: "exam_id",  //标识哪个字段为id主键  
				  showToggle: false,   //名片格式  
				  cardView: false,//设置为True时显示名片（card）布局  
				  showColumns: true, //显示隐藏列    
				  showRefresh: true,  //显示刷新按钮  
				  singleSelect: false,//复选框只能选择一条记录  
				  search: false,//是否显示右上角的搜索框  
				  queryParams: function queryParams(params){
			            var param = {    
			            		exam_id:exam_id,
			            		exam_num:exam_num
			                };   
			                return param;    
			           }, //参数 
				  clickToSelect: true,//点击行即可选中单选/复选框  
				  sidePagination: "client",//表格分页的位置  
				  queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求  
				  toolbar: "#toolbar", //设置工具栏的Id或者class  
				  columns: [{
	 					field: "selectItem",
						checkbox:true,
					},{
	 					field: 'item_code',
						title: '项目编码',
						switchable: true
					}, {
						field: 'item_name',
						title: '项目名称',
						switchable: true
					}, {
						field: 'item_amount',
						title: '原金额',
						switchable: true
					}, {
						field: 'discount',
						title: '折扣率',
						switchable: true,
						sortable: true
					}, {
						field: 'is_new_added',
						title: '增加次数',
						switchable: true
					}, {
						field: 'amount',
						title: '应收金额',
						switchable: true
					}, {
						field: 'pay_statuss',
						title: '结算状态',
						switchable: true
						
					},  {align : 'center',field : 'team_pay',title : '单位金额',	width : 15},
					   {align : 'center',field : 'personal_pay',title : '个人金额',	width : 15} 
					,{
						field: 'exam_indicators',
						title: '付费方式',
						switchable: true
					},{
						field: 'exam_statuss',
						title: '检查状态',
						switchable: true
						
					}, {
						field: 'is_applications',
						title: '接口方式',
						switchable: true
					}/*,{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var html = "";
							html += '<a type= "button" class="btn btn-primary btn-sm" onclick = "djtdeletetc();">删除</a>';
							return html;
						}
					}*/]
				}); 
    	}
    	 var newWindow;  
    	 var timer; 
//    	 加项
    	function djtaddcusItem(){
//    		var sexxb=document.getElementsByName("sex")[0].value
//    		alert(sex);
    		var scustsex = encodeURI(encodeURI(sex));
    		if(Number(exam_id)>0){
     	    	var url='ipadcustomeritemaddshow.action?exam_num='+exam_num+'&sex='+scustsex+'';
     	    	newWindow = window.open(url, "人员加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
     	    	newWindow.focus();
     	    	gettotalinfo();
     	    	//timer = setInterval("djtupdateAfterClose()", 1000);
     	   	}else{
     	   		bootbox.alert( "请先确定体检人员");
     	   	}
    	}
    	//登记台减项
    	function djtdelcusItem(){
    		var checkedItems= $("#medicalPackage1").bootstrapTable('getSelections');
    	    var ids =  new Array();	
    	    var delflags=0;
    	    var delflagstext="";
    	    var delids=new Array();
    	    $.each(checkedItems, function(index, item){	        
    	        if(item.pay_status=='Y'){
    	        	delflags=1;
    	        	delflagstext=item.item_code+"项目已经付费，不能删除！"
    	        }else if((item.exam_status=='Y')||(item.exam_status=='C')){
    	        	delflags=1;
    	        	delflagstext=item.item_code+"项目已检或已登记，不能删除！"
    	        }else if(item.is_application=='Y'){
    	        	delflags=1;
    	        	delflagstext=item.item_code+"项目已发申请，不能删除！"
    	        }else{
    	        	ids.push(item.item_code);
    	        	delids.push(item.item_code);
    	        }
    	    });
    	    if(delflags==1){
    	    	bootbox.alert(delflagstext);
    	    }else{
           
    	    if(exam_num == ""){
    	    	bootbox.alert( "人员无效");
    	 	}else if((ids=='')||(ids.length<=0)){
    	 		bootbox.alert( "选择项目无效");
    	 	}else{	
    	 		bootbox.confirm("是否确定删除选中项目？", function(r){
    			 	if(r){
    			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    					 $("body").prepend(str);
    		 $.ajax({
    				url : 'djtcustomeritemdelshow.action',
    				data : {
    					exam_id : exam_id,
    					exam_num:exam_num,
    					batch_id:0,
    			        ids:ids.toString(),
    			        others:delids.toString()
    				    },
    						type : "post",//数据发送方式   
    						success : function(text) {
    							$(".loading_div").remove();
    							if (text.split("-")[0] == 'ok') {
    								window.location.reload();
    								gettotalinfo();
//    								$('#medicalPackage').bootStrapTable('destroy');
//    								$('#medicalPackage1').bootStrapTable('destroy');
    								
//    								$.messager.alert("操作提示", text);
    							} else {
    								bootbox.alert(text);
//    								$.messager.alert("操作提示", text, "error");
    							}
    						},
    						error : function() {
    							$(".loading_div").remove();
    							$.messager.alert("操作提示", "操作错误", "error");					
    						}
    					});
    	   }
    		 });
    	 	}
    	    }
    	}
    	
    	function gettotalinfo(){
    		$.post("djtGItemCount.action", 
    				{
    					"exam_id":exam_id,
    					"exam_num":exam_num,
    					"exam_type":'T'
    				}, function(jsonPost) {
    					var customertotal = eval(jsonPost);
    					document.getElementById("countss").firstChild.nodeValue=customertotal.counts;
    					document.getElementById("tyjje").firstChild.nodeValue=customertotal.termAmt;
    					document.getElementById("gyjje").firstChild.nodeValue=customertotal.personAmt;
    					document.getElementById("gsjje").firstChild.nodeValue=customertotal.personYfAmt;
    					document.getElementById("gwjje").firstChild.nodeValue=customertotal.qfAmt;
    				}, "json");

    	}
    $(function(){
    	$.ajax({
    		url:'getUserAndUserDep.action',
    		type:'post',
    		success:function(data){
    			var md = '';
    			if(data!="no"){
    				var list=eval("("+data+")");
//     				$("#yonghu").html(list.chi_name);
    				$("#time").html(list.current_date);
    				$("#user_uuid").val(list.user_uuid);
    				verify_user();
    			}
    		},
    		error:function(){
    			
    		}
    	});
    	
            var columns  = document.getElementsByClassName("columns ");//获取到的是一个类数组
            for(var i=0;i<columns .length;i++){
             columns [i].style.display = "none";
          }
      })
      
      function showcolumns (){
         var columns  = document.getElementsByClassName("columns ");//获取到的是一个类数组
         for(var i=0;i<columns .length;i++){
                   columns [i].style.display = "none";        //隐藏
         }
      }
  //验证session的UUID 是否 相同
    function verify_user(){
    	$.ajax({
            url:'verifyUser.action',  
            data:{"user_uuid":$("#user_uuid").val()},          
            type: "post",//数据发送方式   
            success: function(data){
            	if(data == 'ok'){
            		setTimeout(function(){verify_user();},5*1000);
            	}else{
            		window.location.href='index.html';
            	}
            },
            error:function(){
            	//$.messager.alert('提示信息', "操作失败！",function(){});
            	window.location.href='index.html';
            }  
        });
    }
    var newWindow;  
	 var timer; 
	 //人员团体加项
	function djtTTaddcusItem(){
//		var scustsex = encodeURI(encodeURI(document.getElementsByName("sex")[0].value));
		if(exam_num == ""){
		 	$.messager.alert('提示信息',"请先确定体检人员！","error");
		}else if($("#exam_indicator").val()==''){
		 	$.messager.alert('提示信息',"无效付费状态！","error");
//		}else if($("#exam_indicator").val()!='T'){
//		 	$.messager.alert('提示信息',"请选择“个人加项”按钮！","error");
		}else{
	    	var url='ipadTTcustomeritemaddshow.action?exam_num='+exam_num+'&sex='+sex+'&exam_indicator='+exam_indicator;
	    	newWindow = window.open(url, "人员团体加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    	newWindow.focus();
	    	//timer = setInterval("djtupdateAfterClose()", 1000);
	   	}
	}
	
	//人员个人加项
	function djtTGaddcusItem(){
//		var scustsex = encodeURI(encodeURI(document.getElementsByName("sex")[0].value));
		if(exam_num == ''){
		 	$.messager.alert('提示信息',"请先确定体检人员！","error");
		}else if(exam_num == ''){
		 	$.messager.alert('提示信息',"无效付费状态！","error");
		}/*else if($("#exam_indicator").val()!='G'){
		 	$.messager.('提示信息',"请选择团体加项！","error");
		}*/else{	
	    	var url='ipadTGcustomeritemaddshow.action?exam_num='+exam_num+'&sex='+sex+'&exam_indicator='+exam_indicator;
	    	newWindow = window.open(url, "人员个人加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    	newWindow.focus();
//	    	window.location.href='ipadTGcustomeritemaddshow.action?exam_id='+exam_id+'&sex=1&exam_indicator='+$("#exam_indicator").val()+'';
	    	//timer = setInterval("djtupdateAfterClose()", 1000);
	   	}
	}
	