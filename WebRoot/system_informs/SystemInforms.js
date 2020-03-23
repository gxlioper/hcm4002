/**
 * 站内通知
 */

//启（停）修改
	function f_lockuser(userid,html){
		$.messager.confirm('提示信息','是否确认'+html+'该通知？',function(r){
			if(r){
			$.ajax({
	     	url:'up_off_on.action?id='+userid,
	     	type:'post',
	     	success:function(data){
	     		var obj=eval("("+data+")");
	     		if(obj=='success'){
	     			$.messager.alert('提示信息',html+"该通知成功！");
	     			$('#informationlist').datagrid('reload');
	     		}else if(obj=='error'){
	     			$.messager.alert('提示信息',html+"该通知失败！",'error');
	     		}else{
	     			$.messager.alert('提示信息',obj);
	     		}
	     	},
	     	error:function(){
	     		$.messager.alert('提示信息','操作失败！','error');
	     			}
				});
			}
		})
	}