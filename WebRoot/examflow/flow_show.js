$(document).ready(function () { 
	$('#com_Name').bind('click', function() {
		select_com_list(this.value);
    }); 
	
	$('#com_Name').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').bind('blur', function() {  
		select_com_list_over();
    });	

});

//获取分组信息列表
function f_getGroup(company_id){
	if(Number($('#tjrw').combobox('getValue'))<=0){
		$.messager.alert("操作提示", "体检任务不能为空", "error");
	}else{
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	    $("body").prepend(str);
   	    $("#group_id").combobox('clear');
   	    $("#set_id").combobox('clear');
	   	 $('#group_id').combobox({
	 		url : 'termgrouplistshow.action?batchid='+$('#tjrw').combobox('getValue'),
	 		editable : false, //不可编辑状态
	 		cache : false,
	 		panelHeight : 'auto',//自动高度适合
	 		valueField : 'id',
	 		textField : 'group_name',
	 		onLoadSuccess : function(data) {
	 			$(".loading_div").remove();
	 			for (var i = 0; i < data.length; i++) {
	 				$('#group_id').combobox('setValue', data[0].id);
	 				break;
	 			}
	 		},
	 		onChange:function(n,o){
	 	         getSetFrogroupId(n);
	 	     }
	 	});
     }
}

var groupset_id;
function getSetFrogroupId(groupset_id) {
	$('#set_id').combobox({
		url : 'termSetlistshow.action?group_id=' + groupset_id,
		editable : false, // 不可编辑状态
		cache : false,
		panelHeight : 'auto',// 自动高度适合
		valueField : 'id',
		textField : 'set_name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				$('#set_id').combobox('setValue', data[0].id);
				break;
			}
		}
	});
}

//获取单位///////////////////////////////////////////////////////////
//-------------------------------------------单位信息显示-----------------------------------------------------
/**
* 模糊查询公司信息
*/
var hc_com_list,com_Namess;
function select_com_list(com_Namess){
	var url='companychangshow.action';
	var data={"name":com_Namess};
	load_post(url,data,select_com_list_sess);
	}

/**
 * 显示树形结构
 * @param data
 * @returns
 */
function select_com_list_sess(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].id == companyidss)){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#com_name_list_div").html(mydtree.toString());
			$("#com_name_list_div").css("display","block");
			
		}

/**
 * 点击树设置内容
 * @param id
 * @param name
 * @returns
 */
	function setvalue(id,name){
		$("#com_Name").val(name);
		$("#com_name_list_div").css("display","none");
		f_getDatadic(id);
	}
	
	//获取菜单
	function f_getDatadic(company_id) {
		$('#tjrw').combobox({
			url : 'getCompanForBatch.action?company_id='+company_id,
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				$('#tjrw').combobox('setValue', data[0].id);				
			},
			onChange:function(n,o){
				f_getGroup(n);
	 	     }
		});
	}

//单位失去焦点
var hc_mous_select1=false;
function select_com_list_over(){
	if(!hc_mous_select1){
	$("#com_name_list_div").css("display","none");
	}
	}

function select_com_list_mover(){
	hc_mous_select1=true;
	}
function select_com_list_amover(){
	hc_mous_select1=false;
	}

/**
2         * EasyUI DataGrid根据字段动态合并单元格
3         * @param fldList 要合并table的id
4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
5         */
      function MergeCells(tableID, fldList) {
            var Arr = fldList.split(",");
             var dg = $('#' + tableID);
            var fldName;
             var RowCount = dg.datagrid("getRows").length;
            var span;
            var PerValue = "";
            var CurValue = "";
             var length = Arr.length - 1;
             for (i = length; i >= 0; i--) {
               fldName = Arr[i];
                PerValue = "";
                span = 1;
                for (row = 0; row <= RowCount; row++) {
                    if (row == RowCount) {
                        CurValue = "";
                    }
                    else {
                        CurValue = dg.datagrid("getRows")[row][fldName];
                    }
                     if (PerValue == CurValue) {
                        span += 1;
                    }
                     else {
                        var index = row - span;
                         dg.datagrid('mergeCells', {
                            index: index,
                             field: fldName,
                             rowspan: span,
                             colspan: null
                         });
                         span = 1;
                         PerValue = CurValue;
                     }
                 }
             }
         }
      
      
      function f_clolor(value,row,index){
    		if(row.dep_category == '17'){
    			if(row.health_level == 'Y'){
    				return 'color:#F00;';
    			}else if(row.health_level == 'W'){
    				return 'color:#FF9B00;';
    			}
    			if(row.item_id == '0'){
    				return 'background:#FEEAA8;color:#ff5100;';
    			}
    		}else if(row.dep_category == '131'){
    			if(row.health_level == 1){
    				return 'color:#ff0000;';
    			}else if(row.health_level == 2){
    				return 'color:#0000ff;';
    			}else if(row.health_level == 3){
    				return 'color:#ff5100;';
    			}else if(row.health_level == 4){
    				return 'color:#FF9B00;';
    			}
    		}
    	}

    	function f_flowor(val,row){
    		if(row.dep_category == '21'){
    			if(row.exam_result == 'image_path'){
    				return '<a href="javascript:void(0)" onclick = "show_picture('+row.item_id+')">查看图片</a>';
    			}else{
    				return row.exam_result.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
    			}
    		}else if(row.dep_category == '131'){
    			if(row.health_level == 1){
    				return row.exam_result+' ↑';
    			}else if(row.health_level == 2){
    				return row.exam_result+' ↓';
    			}else{
    				return row.exam_result;
    			}
    		}else{
    			if(row.exam_result!=null){
    			    return row.exam_result.replace(/\n/g,'</br>');
    			}
    		}
    	}
    	
    	function show_picture(id){
    		var url='/showViewExamImage.action?view_imange_id='+id;
    		newWindow = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
    		newWindow.focus();
    	}
 ///////////////////////////////////////上传////////////////////////////////////////////////////////////////////
    	
   function shangchuanchaxun(){
	   
	     var company = undefined;
	     if($("#ck_com_Name")[0].checked){
	    	 company =  $("#com_Name").val();
		 }
	     var batch_id = undefined;
	     if($("#ck_tjrw")[0].checked){
	    	 batch_id =  $("#tjrw").combobox('getValue');
	     }
	     var group_id = undefined;
	     if($("#ck_group_id")[0].checked){
	    	 group_id =  $("#group_id").combobox('getValue');
	     }
	     var set_id = undefined;
	     if($("#ck_set_id")[0].checked){
	    	 set_id =  $("#set_id").combobox('getValue');
	     }
	     var exam_type = undefined;
	     if($("#ck_exam_type")[0].checked){
	    	 exam_type =  $("#exam_type").combobox('getValue');
	     }
	   	
	     var exam_num = undefined;
		 if($("#ck_exam_num_p")[0].checked){
			 exam_num =  $("#exam_num_p").val();
		 }
		 var username = undefined;
		 if($("#ck_username")[0].checked){
			 username = $("#username").val();
		 }
		 var sex = undefined;
		 if($("#ck_usersex")[0].checked){
			 sex = $('#usersex').combobox('getValue');
		 }
		 
	   var model={
			 "exam_num":exam_num,
			 "exam_type":exam_type,
			 "username":username,
			 "sex":sex,
			 "company":company,
			 "batch_id":batch_id,
			 "group_id":group_id,
			 "set_id":set_id,
	     };
	     $("#flowloglist").datagrid({
		 url:'flowloglist.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 remoteSort:false,
		 pageNumber:1,
	     pageSize: 100,//每页显示的记录条数，默认为10 
	     pageList:[100,300,500],//可以设置每页记录条数的列表 
	     pagination: true,
	     collapsible:true,
	     fitColumns:true,
		 columns:[[
//          {field:'ck',checkbox:true},
		    {align:'center',field:'exam_num',title:tjhname,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',sortable:true},
		    {align:'center',field:'remarke',title:'备注'},	
		    {align:'center',field:'exam_types',title:'体检类型'},	
		 	{align:'center',field:'user_name',title:'姓名',sortable:true},
//		 	{align:'center',field:'sex',title:'性别',sortable:true},
//		 	{align:'center',field:'is_marriage',title:'婚否'},
//		 	{align:'center',field:'age',title:'年龄',sortable:true},
		 	{align:'center',field:'phone',title:'电话',sortable:true},
		 	{align:'center',field:'company',title:'单位',width:100},
//		 	{align:'center',field:'set_name',title:'套餐',width:200},	
//		 	{align:'center',field:'join_date',title:'体检日期',sortable:true},	 	
//		  	{align:'center',field:'statuss',title:'体检状态'},
		 	{align:"center",field:"senddate","title":"时间",sortable:'true'},
	        {align:'center',field:"flow_name","title":"事件"},
	        {align:'center',field:"senduname","title":"发送者",sortable:'true'},
	        //{align:'center',field:"accuname","title":"接收者"},
	        {align:"center",field:"flow_types","title":"状态"}
	 	]],	    	
    	onLoadSuccess:function(value){ 
    		$("#datatotal").val(value.total);
    		$(".loading_div").remove();
    		MergeCells('flowloglist', 'exam_num,id_num,remarke,exam_types,user_name,phone,company');
    	},
    	onDblClickRow : function(index, row) {	  
    		//$("#exam_num_s").val(row.exam_num)
		}
	});
}