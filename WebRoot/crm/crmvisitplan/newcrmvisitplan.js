$(document).ready(function () {
	rili();
	counta();
});

function counta(){
	var time=$('#plan_visit_datedata').val();
	if($('#jinrirenwucount').html()!='0'){
		var jinricount=$('#jinrirenwucount').html();
		$('#jinrirenwucount').html('<a href=\"javascript:f_jinrirenwu(\''+time+'\')\">'+jinricount+'</a>');
	}
	if($('#putongrenwucount').html()!='0'){
		var putongcount=$('#putongrenwucount').html();
		$('#putongrenwucount').html('<a href=\"javascript:f_putongrenwu(\''+time+'\')\">'+putongcount+'</a>');
	}
	if($('#yibanrenwucount').html()!='0'){
		var yibancount=$('#yibanrenwucount').html();
		$('#yibanrenwucount').html('<a href=\"javascript:f_yibanrenwu(\''+time+'\')\">'+yibancount+'</a>');
	}
	if($('#zhongyaorenwucount').html()!='0'){
		var zhongyaocount=$('#zhongyaorenwucount').html();
		$('#zhongyaorenwucount').html('<a href=\"javascript:f_zhongyaorenwu(\''+time+'\')\">'+zhongyaocount+'</a>');
	}
	if($('#huifanggenzong').html()!='0'){
		var huifanggenzongcount=$('#huifanggenzong').html();
		$('#huifanggenzong').html('<a href=\"javascript:f_huifanggenzong(\''+time+'\')\">'+huifanggenzongcount+'</a>');
	}
	if($('#fujianflag').html()!='0'){
		var fujianflagcount=$('#fujianflag').html();
		$('#fujianflag').html('<a href=\"javascript:f_fujianflagcount(\''+time+'\')\">'+fujianflagcount+'</a>');
	}
	if($('#shifangjilu').html()!='0'){
		var shifangjilucount=$('#shifangjilu').html();
		$('#shifangjilu').html('<a href=\"javascript:f_shifangjilucount(\''+time+'\')\">'+shifangjilucount+'</a>');
	}
	if($('#dafuzixun').html()!='0'){
		var dafuzixuncount=$('#dafuzixun').html();
		$('#dafuzixun').html('<a href=\"javascript:f_dafuzixuncount()\">'+dafuzixuncount+'</a>');
	}
	if($('#kehucount').html()!='0'){
		var kehucountcount=$('#kehucount').html();
		$('#kehucount').html('<a href=\"javascript:f_kehucountcount(\''+time+'\')\">'+kehucountcount+'</a>');
	}
	if($('#yidafukehucount').html()!='0'){
		var yidafukehucountcount=$('#yidafukehucount').html();
		$('#yidafukehucount').html('<a href=\"javascript:f_yidafukehucountcount(\''+time+'\')\">'+yidafukehucountcount+'</a>');
	}
	if($('#weidafukehucount').html()!='0'){
		var weidafukehucountcount=$('#weidafukehucount').html();
		$('#weidafukehucount').html('<a href=\"javascript:f_weidafukehucountcount(\''+time+'\')\">'+weidafukehucountcount+'</a>');
	}
	if($('#shengrikehu').html()!='0'){
		var shengrikehu=$('#shengrikehu').html();
		$('#shengrikehu').html('<a href=\"javascript:f_shengrikehu(\''+time+'\')\">'+shengrikehu+'</a>');
	}
	
}
function rili(){
	$('#genzongrili1').calendar({
		onSelect: function(date){
			var month=date.getMonth()+1;
			month=month+'';
			var day=date.getDate();
			day=day+"";
			if(month.length==1){
				month="0"+month;
			}
			if(day.length==1){
				day="0"+day;
			}
			$.ajax({
				url : 'getNewCrmCount.action',
				type : 'post',
				data : {
					"plan_visit_date":date.getFullYear()+"-"+month+"-"+day
				},
				success : function(data) {
					$('#plan_visit_datedata').val(date.getFullYear()+"-"+month+"-"+day);
					var obj=eval("("+data+")");
					$('#jinrirenwucount').html(obj.jinrirenwucount);
					$('#putongrenwucount').html(obj.putongrenwucount);
					$('#yibanrenwucount').html(obj.yibanrenwucount);
					$('#zhongyaorenwucount').html(obj.zhongyaorenwucount);
					$('#dafuzixun').html(obj.dafuzixun);
					$('#huifanggenzong').html(obj.huifanggenzong);
					$('#kehucount').html(obj.kehucount);
					$('#yidafukehucount').html(obj.yidafukehucount);
					$('#weidafukehucount').html(obj.weidafukehucount);
					$('#rewuzongshu').html(obj.rewuzongshu);
					$('#kehuzongshu').html(obj.kehuzongshu);
					$('#putongrenwuzongshu').html(obj.putongrenwuzongshu);
					$('#yibanrenwuzongshu').html(obj.yibanrenwuzongshu);
					$('#zhongyaorenwuzongshu').html(obj.zhongyaorenwuzongshu);
					$('#yidafukehuzongshu').html(obj.yidafukehuzongshu);
					$('#weidafukehuzongshu').html(obj.weidafukehuzongshu);
					$('#shifangjilu').html(obj.shifangjilu);
					$('#fujianflag').html(obj.fujianflag);
					$('#shengrikehushu').html(obj.shengrikehushu);
					$('#shengrikehu').html(obj.shengrikehushu);
					counta();
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
			
		}
	})
}
function f_weidafukehucountcount(time){
	window.parent.addPanel_other('今日未计划客户','getCrmDoctorMemberPage.action?flag=0','"+wx.getIcon_url()+"','1');
}
function f_yidafukehucountcount(time){
	window.parent.addPanel_other('今日已计划客户','getCrmDoctorMemberPage.action?flag=1','"+wx.getIcon_url()+"','1');
}
function f_kehucountcount(time){
	window.parent.addPanel_other('今日客户','getCrmDoctorMemberPage.action?allot_date='+time,'"+wx.getIcon_url()+"','1');
}
function f_dafuzixuncount(){
	window.parent.addPanel_other('咨询答复','getCrmVisitPlanListPage.action?visit_status=2','"+wx.getIcon_url()+"','1');
}
function f_shifangjilucount(time){
	window.parent.addPanel_other('失访记录','getCrmVisitLostListPage.action?create_time='+time,'"+wx.getIcon_url()+"','1');
}
function f_fujianflagcount(time){
	window.parent.addPanel_other('今日复检记录','getCrmVisitPlanListPage.action?plan_visit_date='+time+'&fujianflag=1','"+wx.getIcon_url()+"','1');
}
function f_huifanggenzong(time){
	window.parent.addPanel_other('今日健康回访跟踪','getNewCrmVisitRecordListPage.action?visit_date='+time,'"+wx.getIcon_url()+"','1');
}
function f_zhongyaorenwu(time){
	window.parent.addPanel_other('重要回访计划','getCrmVisitPlanListPage.action?plan_visit_date='+time+'&visit_important=3','"+wx.getIcon_url()+"','1');
}
function f_yibanrenwu(time){
	window.parent.addPanel_other('一般回访计划','getCrmVisitPlanListPage.action?plan_visit_date='+time+'&visit_important=2','"+wx.getIcon_url()+"','1');
}
function f_putongrenwu(time){
	window.parent.addPanel_other('普通回访计划','getCrmVisitPlanListPage.action?plan_visit_date='+time+'&visit_important=1','"+wx.getIcon_url()+"','1');
}
function f_jinrirenwu(time){
	window.parent.addPanel_other('今日健康回访计划','getCrmVisitPlanListPage.action?plan_visit_date='+time,'"+wx.getIcon_url()+"','1');	
}
function f_historyrenwu(){
	window.parent.addPanel_other('健康回访计划','getCrmVisitPlanListPage.action?','"+wx.getIcon_url()+"','1');	
}
function f_historygenzong(){
	window.parent.addPanel_other('健康回访跟踪','getNewCrmVisitRecordListPage.action?plan_visit_date='+$('#plan_visit_datedata').val(),'"+wx.getIcon_url()+"','1');
}
function f_historykehu(){
	window.parent.addPanel_other('我的客户','getCrmDoctorMemberPage.action?plan_visit_date='+$('#plan_visit_datedata').val(),'"+wx.getIcon_url()+"','1');
}
function f_historyshifang(){
	window.parent.addPanel_other('失访记录','getCrmVisitLostListPage.action','"+wx.getIcon_url()+"','1');
}
function f_historyfujian(){
	window.parent.addPanel_other('复检记录','getCrmVisitPlanListPage.action?fujianflag=1&plan_visit_date='+$('#plan_visit_datedata').val(),'"+wx.getIcon_url()+"','1');
}
function f_historyzixun(){
	window.parent.addPanel_other('咨询答复','getCrmVisitPlanListPage.action?visit_status=2&plan_visit_date='+$('#plan_visit_datedata').val(),'"+wx.getIcon_url()+"','1');
}
function f_shengrikehu(){
	window.parent.addPanel_other('生日客户','getShengrikehuPage.action?plan_visit_date='+$('#plan_visit_datedata').val(),'"+wx.getIcon_url()+"','1');
}