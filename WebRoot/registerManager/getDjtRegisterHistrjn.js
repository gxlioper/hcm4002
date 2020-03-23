$(document).ready(function() {
	if( document.getElementById('exam_id').value>0){
		getcustomer_info();
	}
});
/**
 *档案表
 */
function getcustomer_info(){
	 $.ajax({
		 url:'queryEaxmInfo.action',
		 type:'post',
		 data:{id:$('#exam_id').val()},
		 success:function(obj){
			 var obj = JSON.parse(obj);
			 if(obj.length>0){
				for(var i=0; i<obj.length; i++){
					$('#user_name_'+i).text(obj[i].user_name);
					$('#sex_'+i).text(obj[i].sex);
					$('#age_'+i).text(obj[i].age);
					$('#tj_time_'+i).text(obj[i].tj_time);
					$('#tj_exam_set_'+i).text(obj[i].tj_exam_set);
					$('#cishu_'+i).text(obj[i].cishu);
					$('#yxzb_'+i).text(obj[i].yxzb);
					$('#amount_'+i).text(obj[i].amount_s);
				}
			}
			if(obj.length=='0'){
				$('#shou_3').html('');
				$('#shou_2').html('');
				$('#shou_1').html('');
			}else if(obj.length=='1'){
				$('#shou_3').html('');
				$('#shou_2').html('');
			}else if(obj.length=='2'){
				$('#shou_3').html('');
			}
		 },
		 error:function(){
			 $.messager.alert("提示信息","操作失败","error");
		 }
	})
}
