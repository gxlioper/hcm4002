$(document).ready(function () { 
	$('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    }); 
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    });	
	
	getgroupGrid();
});



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
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
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
		$("#company_id").val(id);
		$("#com_Name").textbox('setValue',name);
		$("#com_name_list_div").css("display","none");
		f_getDatadic();		
	}


	//获取菜单
	function f_getDatadic() {
		$('#batch_id').combobox({
			url : 'getCompanForBatch2.action?company_id='+$("#company_id").val(),
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				$('#batch_id').combobox('setValue', data[0].id);				
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

 //---------------------------------------显示方案------------------------------------------------------
 /**
  * 
  */
 function getgroupGrid(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	     lastIndex=undefined;
		 var model={"company_id":$("#company_id").val(),"batch_id":document.getElementsByName("batch_id")[0].value};
	     $("#contractlist").datagrid({
		 url:'contractlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'contract_num',title:'合同编号',width:20},
		 	{align:'center',field:'company_name',title:'甲方单位',width:25},
		 	{align:'center',field:'batch_name',title:'所属体检任务',width:25},
		 	{align:'center',field:'linkman',title:'更新人员',width:15},
		 	{align:'center',field:'tel',title:'联系电话',width:15},
		 	{align:'center',field:'validity_date',title:'有效期',width:15},
		 	{align:'center',field:'stypes',title:'状态',width:15},
		 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_show},
		 	{align:"center",field:"xgddd",title:"审核",width:10,"formatter":f_xg}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
	}
 
/**
 * 修改
 * @param val
 * @param row
 * @returns {String}
 */
 function f_xg(val,row){	
 	return '<a href=\"javascript:f_shht(\''+row.contract_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"审核\" /></a>';
 }
  
 /**
  * 显示一条方案
  * @param val
  * @param row
  * @returns {String}
  */
  function f_show(val,row){	
	  return '<a href=\"javascript:f_contractshow(\''+row.contract_num+'\')\">查看</a>';
  }
  

  /**
   * 设置每隔1秒钟刷新父节点的表格
   */
  function updateAfterClose() {  
      //父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口  
      if(checknewWindow.closed == true) {  
      clearInterval(checktimer);  
      getgroupGrid(); // 主窗口getgroupGrid();刷新  
      return;  
      }  
 } 
  
  var checknewWindow;  
  var checktimer;   
 function f_contractshow(userid){
 	 	var url='/contractonecheckshow.action?contract_num='+userid;
    	if(userid!=''){
    		newWindow = window.open(url, "查看单独合同", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes")
    		newWindow.focus();
    	}else{
    	  $.messager.alert('提示信息',"请先选择合同","error");
    	}
 	 } 
  
 
 function f_shht(userid){
	 $("#dlg-show").dialog({
	 		title:'审核合同',
	 		href:'contractcheckedit.action?contract_num='+userid
	 	});
	 	$("#dlg-show").dialog('open');
 }
 