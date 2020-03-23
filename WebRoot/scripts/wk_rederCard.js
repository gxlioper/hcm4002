var physical_num;//物理卡号
var key = 'FFFFFFFFFFFF';//写入读写器的12字节密码
var sec = 1;//须装载密码的扇区号(0～15) 
var ret = 0;//密码类型  0 — KEY A, 4 — KEY B

function read_card_vip(){//读取会员卡
	if($("#card_reader_code").val() == 's001' || $("#card_reader_code").val() == 's002'){
		var card_num = read_card_wk();//明华读卡器
		return card_num;
	}else if($("#card_reader_code").val() == 's003'){
		var card_num = read_card_dk();//德卡读卡器
		return card_num;
	}else if($("#card_reader_code").val() == 's004'){
		var card_num = read_card_jp();//交大附二金牌-读卡器
		return card_num;
	}else{
		alert('未设置读卡器型号!');
	}
}

function get_phsical_num(){//获取物理卡号
	if($("#card_reader_code").val() == 's001' || $("#card_reader_code").val() == 's002'){
		var phsical_num = get_phsical_num_wk();//明华读卡器
		return phsical_num;
	}else if($("#card_reader_code").val() == 's003'){
		var phsical_num = get_phsical_num_dk();//德卡读卡器
		return phsical_num;
	}else if($("#card_reader_code").val() == 's004'){
		var card_num = 'ok-'+Math.random();//交大附二金牌-读卡器
		return card_num;
	}else{
		alert('未设置读卡器型号!');
	}
}

function make_card_vip(card_info){//制卡 写卡数据
	if($("#card_reader_code").val() == 's001' || $("#card_reader_code").val() == 's002'){
		var msg = make_card_wk(card_info);//明华读卡器
		return msg;
	}else if($("#card_reader_code").val() == 's003'){
		var msg = make_card_dk(card_info);//德卡读卡器
		return msg;
	}else if($("#card_reader_code").val() == 's004'){
		var msg = make_card_jp(card_info);//交大附二金牌-读卡器
		return msg;
	}else{
		alert('未设置读卡器型号!');
	}
}
/************************************共用函数方法*******************************************/
/**
 * 验证物理卡号是否存在
 * @param physical_num
 */
function isPhysical_num(physical_num){
	var boolean = true;
	$.ajax({//验证物理卡号是否存在
		url:'getCardInfoByPhyNum.action?physical_num='+physical_num,
		type:'post',
		async:false, //同步请求 
		success:function(data){
			if(data == 'no'){
				boolean = false;
			}
		}
	});
	return boolean;
}

/**
 * 验证卡号是否存在
 * @param card_num
 */
function isCard_num(card_num){
	var boolean = true;
	$.ajax({ //验证卡号是否存在
		url:'checkCardNum.action?card_num='+card_num,
		type:'post',
		async:false,//同步请求 
		success:function(data){
			if(data !='ok'){
				boolean = false;
			}
		}
	});
	return boolean;
}

/**
 * 通过物理卡号获取加密后卡密
 * @param physical_num
 */
function getCardKey(physical_num){
	var card_key = null;
	$.ajax({//获取秘钥
		url:'getCardKey.action?cardId='+physical_num,
		type:'post',
		async:false,//同步请求 
		success:function(data){
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				card_key = obj[1];
			}
		}
	});
	return card_key;
}

/**
 * 根据卡号获取第一扇区内容
 * @param card_num
 */
function getCardBlock(card_num){
	var block_one = null;
	$.ajax({//获取第一扇区第一块内容
		url:'getCardNOHex.action?cardNO='+card_num,
		type:'post',
		async:false,//同步请求
		success:function(data){
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				block_one = obj[1];
			}
		}
	});
	return block_one;
}

/**
 * 根据第一扇区内容获取卡号
 * @param block_one
 */
function getCardNum(block_one){
	var card_num = null;
	$.ajax({//获取卡号
		url:'getCardNOForConn.action?cardconn='+block_one,
		type:'post',
		async:false,//同步请求
		success:function(data){
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				card_num = obj[1];
			}
		}
	});
	return card_num;
}

function save_card_Info(card_info){//数据库保存卡信息
	var boolean = true;
	$.ajax({
		url:'saveCardInfo.action?language='+$("#language").val(),  
        data:{
        	card_id:card_info.card_id,
        	physical_num:card_info.physical_num,
        	card_num:card_info.card_num,
//        	member_id:card_info.member_id,
        	card_pwd:card_info.card_pwd,
        	card_type:card_info.card_type,
        	amount:card_info.amount,
        	limit_card_count:card_info.limit_card_count,
        	card_level:card_info.card_level,
        	deadline:card_info.deadline,
        	card_remark:card_info.card_remark,
        	discount : card_info.discount,
        	company : card_info.company,
        	sale_amount : card_info.sale_amount
        },          
        type: "post",//数据发送方式 
        async:false,//同步请求
        success: function(data){
        	boolean = true;
        },
        error:function(){
        	boolean = false;
        }  
    });
	return boolean;
}
/******************************德卡T10读卡器函数方法************************************/
var lSnr;
function DkInit(){//打开设备
	MWRFATL.dc_exit();
	var st = MWRFATL.dc_init(100, 115200);
	if(st <= 0){
		return false;
	}else{
		return true;
	}
}

function DkExit(){//关闭设备
	var st = MWRFATL.dc_exit();
	if(st != 0){
		return false;
	}else{
		return true;
	}
}

function DevBeepDk(){//蜂鸣
	var st = MWRFATL.dc_beep(5);
	if(st != 0){
		return false;
	}else{
		return true;
	}
}

function DkLoadkey(ret,sec,key){//装载秘钥
	MWRFATL.put_bstrSBuffer_asc = key;
	var st = MWRFATL.dc_load_key(ret,sec);
	if(st != 0){
		return false;
	}else{
		return true;
	}
}

function DkCard(){//寻卡
	MWRFATL.dc_config_card(65);
	var st = MWRFATL.dc_card(0, lSnr);
	if(st != 0){
		return null
	}else{
		return MWRFATL.get_bstrRBuffer_asc;
	}
}

function DkAuthentication(ret,sec){//核对秘钥
	var st = MWRFATL.dc_authentication(ret,sec);
	if(st != 0){
		return false;
	}else{
		return true;
	}
}

//说明： 读数据 
//返回： <>0 错误	  
//   =0 密码正确
function DkRead(address) {
	var st = MWRFATL.dc_read(address);
	if (st == 0)
		return MWRFATL.get_bstrRBuffer_asc;
	else
		return null;
}

// 说明： 写数据
// 返回： <>0 错误
// =0 密码正确
function DkWrite(address, value) {
	MWRFATL.put_bstrSBuffer_asc = value;
	var st = MWRFATL.dc_write(address);
	if (st == 0)
		return true;
	else
		return false;
}

function read_card_dk(){ //德卡 读卡函数
	DkExit(); //关闭设备
	if(DkInit() == false){//打开设备
		return 'error-打开设备失败,请确认读卡器是否开启插好!';
	}
	var physical_num = DkCard();//寻卡
	if(physical_num == null){
		DkExit(); //关闭设备
		return 'error-请放入卡片!';
	}
	var card_key = getCardKey(physical_num);//获取秘钥
	if(card_key == null){
		DkExit(); //关闭设备
		return 'error-获取卡密失败!';
	}
	if(DkLoadkey(ret,sec,card_key) == false){//装载秘钥
		DkExit(); //关闭设备
		return 'error-装载密码失败!';
	}
	if(DkAuthentication(ret,sec) == false){//核对秘钥
		DkExit(); //关闭设备
		return 'error-核对密码失败!';
	}
	var value = DkRead(sec*4); //读取第一扇区内容
	if(value == null){
		DkExit(); //关闭设备
		return 'error-读卡失败!';
	}
	var card_num = getCardNum(value); //获取卡号
	if(card_num == null){
		DkExit(); //关闭设备
		return 'error-获取卡号失败!';
	}
	DevBeepDk();//蜂鸣器蜂鸣
	DkExit(); //关闭设备
	
	return 'ok-'+card_num;
}

function get_phsical_num_dk(){//获取物理卡号
	DkExit(); //关闭设备
	if(DkInit() == false){//打开设备
		return 'error-打开设备失败,请确认读卡器是否开启插好!';
	}
	var physical_num = DkCard();//寻卡
	if(physical_num == null){
		DkExit(); //关闭设备
		return 'error-请放入卡片!';
	}
	DkExit(); //关闭设备
	return 'ok-'+physical_num;
}

function make_card_dk(card_info){ //五康 写卡函数
	DkExit(); //关闭设备
	if(DkInit() == false){//打开设备
		return 'error-打开设备失败,请确认读卡器是否开启插好!';
	}
	var physical_num = DkCard();//寻卡
	if(physical_num == null){
		DkExit(); //关闭设备
		return 'error-请放入卡片!';
	}
	if(DkLoadkey(ret,sec,key) == false){//装载秘钥 原始
		DkExit(); //关闭设备
		return 'error-装载密码失败!';
	}
	var card_key = getCardKey(physical_num);//获取新秘钥
	if(card_key == null){
		DkExit(); //关闭设备
		return 'error-获取秘钥失败!';
	}
	if(DkAuthentication(ret,sec) == false){//核对秘钥 原始秘钥
		DkExit(); //关闭设备
		//return 'error-核对密码失败!';
		if(DkInit() == false){//打开设备
			return 'error-打开设备失败,请确认读卡器是否开启插好!';
		}
		var physical_num = DkCard();//寻卡
		if(physical_num == null){
			DkExit(); //关闭设备
			return 'error-请放入卡片!';
		}
		if(DkLoadkey(ret,sec,card_key) == false){//装载秘钥 新秘钥
			DkExit(); //关闭设备
			return 'error-装载密码失败!';
		}
		if(DkAuthentication(ret,sec) == false){//核对秘钥 新秘钥
			DkExit(); //关闭设备
			return 'error-核对密码失败!';
		}
	}
	
	var card_block = getCardBlock(card_info.card_num);//获取第一块内容
	if(card_block == null){
		DkExit(); //关闭设备
		return 'error-获取第一块内容!';
	}
	var address = sec*4;
	var address3 = address +3;
	if(DkWrite(address,card_block) == true && DkWrite(address3,card_key+"FF078069"+card_key) == true){
		save_card_Info(card_info);
	}else{
		DkExit(); //关闭设备
		return 'error-制卡失败!';
	}
	DevBeepDk();//蜂鸣器蜂鸣
	DkExit(); //关闭设备
	return 'ok-制卡成功!';
}

/** ********************************明华读卡器函数方法******************************** */
//打开设备
function RfInit(){
    MWRFATL.CloseReader();
    if($("#card_reader_code").val() == 's001'){
    	var bb=MWRFATL.OpenReader(0,115200);
    }else if($("#card_reader_code").val() == 's002'){
    	var bb=MWRFATL.OpenReader("");
    }
    var aa=MWRFATL.LastRet;
    if(aa==0)				
    	return true;
    else
        return false;
}
//关闭设备
function RfExit(){
	MWRFATL.CloseReader();
    var aa=MWRFATL.LastRet;
    if(aa==0)				
    	return true;
    else
        return false;
}
//蜂鸣器蜂鸣
function DevBeep(){
	var bb=MWRFATL.RF_Beep(10);		//传入参数：设备单次蜂鸣时间
	var aa=MWRFATL.LastRet;
	if(aa==0)				
    	return true;
    else
        return false;
}

//说明： 装载密钥 	
//参数：mode: 密码类型
//          0 — KEY A
//          4 — KEY B
//    secnr: 须装载密码的扇区号(0～15) 
//    key:  写入读写器的12字节新密码	  
//返回： <0 错误		  
//      =0 密码正确
function RfLoadkey(ret,sec,key){
	MWRFATL.RF_LoadKey(ret,sec,key);
	var aa=MWRFATL.LastRet;
	if(aa==0)				
    	return true;
    else
        return false;
}

//说明： 寻卡
//返回： <>0 错误	  
//     =0 密码正确
function RfCard(){      
    MWRFATL.MF_Reset(5);
	var bb=MWRFATL.OpenCard(1);
	var aa=MWRFATL.LastRet;
	if(aa==0)
     	return bb;
    else
     	return null;
}

//说明： 核对密码
//返回： <>0 错误	  
//     =0 密码正确
function RfAuthentication(ret,sec){
	MWRFATL.RF_Authentication(ret,sec);
	var aa=MWRFATL.LastRet;
	if(aa==0)				
    	return true;
    else
        return false;
}

//说明： 读数据 
//返回： <>0 错误	  
//     =0 密码正确
function RfRead(address){
	var block = MWRFATL.MF_Read(address); 
	var aa=MWRFATL.LastRet;
    if(aa==0)
    	return block.slice(0,32);
    else
    	return null;
}

//说明： 写数据
//返回： <>0 错误		  
//     =0 密码正确
function RfWrite(address,value){
	MWRFATL.MF_Write(address,value);
    var aa=MWRFATL.LastRet;
    if(aa==0)				
    	return true;
    else
        return false;
}

//说明：中止卡片
//返回： <>0 错误	  
//     =0 密码正确
function RfHalt(){
    MWRFATL.CloseCard();
    var aa=MWRFATL.LastRet;
    if(aa==0)				
    	return true;
    else
        return false;
}

function read_card_wk(){ //五康 读卡函数
	RfExit(); //关闭设备
	if(RfInit() == false){//打开设备
		return 'error-打开设备失败,请确认读卡器是否开启插好!';
	}
	var physical_num = RfCard();//寻卡
	if(physical_num == null){
		RfExit(); //关闭设备
		return 'error-请放入卡片!';
	}
	var card_key = getCardKey(physical_num);//获取秘钥
	if(card_key == null){
		RfExit(); //关闭设备
		return 'error-获取卡密失败!';
	}
	if(RfLoadkey(ret,sec,card_key) == false){//装载秘钥
		RfExit(); //关闭设备
		return 'error-装载密码失败!';
	}
	if(RfAuthentication(ret,sec) == false){//核对秘钥
		RfExit(); //关闭设备
		return 'error-核对密码失败!';
	}
	var value = RfRead(sec*4); //读取第一扇区内容
	if(value == null){
		RfExit(); //关闭设备
		return 'error-读卡失败!';
	}
	var card_num = getCardNum(value); //获取卡号
	if(card_num == null){
		RfExit(); //关闭设备
		return 'error-获取卡号失败!';
	}
	DevBeep();//蜂鸣器蜂鸣
	RfHalt(); //终止卡片
	RfExit(); //关闭设备
	if($("#card_reader_code").val() == 's001'){
		return 'ok-'+card_num.substring(4,10);
	}else if($("#card_reader_code").val() == 's002'){
		return 'ok-'+card_num.substring(2,10);
	}else{
		return 'ok-'+card_num;
	}
	
}

function get_phsical_num_wk(){//获取物理卡号
	RfExit(); //关闭设备
	if(RfInit() == false){//打开设备
		return 'error-打开设备失败,请确认读卡器是否开启插好!';
	}
	var physical_num = RfCard();//寻卡
	if(physical_num == null){
		RfExit(); //关闭设备
		return 'error-请放入卡片!';
	}
	RfHalt(); //终止卡片
	RfExit(); //关闭设备
	return 'ok-'+physical_num;
}

function make_card_wk(card_info){ //五康 写卡函数
	RfExit(); //关闭设备
	if(RfInit() == false){//打开设备
		return 'error-打开设备失败,请确认读卡器是否开启插好!';
	}
	var physical_num = RfCard();//寻卡
	if(physical_num == null){
		RfExit(); //关闭设备
		return 'error-请放入卡片!';
	}
	if(RfLoadkey(ret,sec,key) == false){//装载秘钥 原始
		RfExit(); //关闭设备
		return 'error-装载密码失败!';
	}
	var card_key = getCardKey(physical_num);//获取新秘钥
	if(card_key == null){
		RfExit(); //关闭设备
		return 'error-获取秘钥失败!';
	}
	if(RfAuthentication(ret,sec) == false){//核对秘钥 原始秘钥
		RfExit(); //关闭设备
		//return 'error-核对密码失败!';
		if(RfInit() == false){//打开设备
			return 'error-打开设备失败,请确认读卡器是否开启插好!';
		}
		var physical_num = RfCard();//寻卡
		if(physical_num == null){
			RfExit(); //关闭设备
			return 'error-请放入卡片!';
		}
		if(RfLoadkey(ret,sec,card_key) == false){//装载秘钥 新秘钥
			RfExit(); //关闭设备
			return 'error-装载密码失败!';
		}
		if(RfAuthentication(ret,sec) == false){//核对秘钥 新秘钥
			RfExit(); //关闭设备
			return 'error-核对密码失败!';
		}
	}
	
	var card_block = getCardBlock(card_info.card_num);//获取第一块内容
	if(card_block == null){
		RfExit(); //关闭设备
		return 'error-获取第一块内容!';
	}
	var address = sec*4;
	var address3 = address +3;
	if(RfWrite(address,card_block) == true && RfWrite(address3,card_key+"FF078069"+card_key) == true){
		save_card_Info(card_info);
	}else{
		RfExit(); //关闭设备
		return 'error-制卡失败!';
	}
	DevBeep();//蜂鸣器蜂鸣
	RfHalt(); //终止卡片
	RfExit(); //关闭设备
	return 'ok-制卡成功!';
}

/**********************交大附二金牌读卡器*************************/
function read_card_jp(){
	var YLE300 = document.getElementById("MWRFATL");
	YLE300.YLE300_Close();
    var iRet = YLE300.YLE300_Open(0);
    if(iRet != 0){
       return "error-打开错误,错误码: "+iRet;
    }
    var TrackNoEdit = 2;
    var ReadDataStr;
    iRet = YLE300.YLE300_Read(TrackNoEdit);
    if(iRet == 0){
    	ReadDataStr = YLE300.GetTrackData();
    }else{
    	 return "error-读卡错误,错误码: "+iRet;
    }
    iRet = YLE300.YLE300_Close();
    if(iRet != 0){
        return "error-关闭错误,错误码: "+iRet;
    }
    return "ok-"+ReadDataStr;
}

function make_card_jp(card_info){
	var YLE300 = document.getElementById("MWRFATL");
	YLE300.YLE300_Close();
    var iRet = YLE300.YLE300_Open(0);
    if(iRet != 0){
       return "error-打开错误,错误码: "+iRet;
    }
    var TrackNoEdit = 2;
    iRet = YLE300.YLE300_Write(TrackNoEdit,card_info.card_num);
    if(iRet == 0){
    	save_card_Info(card_info);
    }else{
    	YLE300.YLE300_Close();
    	return "error-写卡错误,错误码: "+iRet;
    }
    iRet = YLE300.YLE300_Close();
    if(iRet != 0){
        return "error-关闭错误,错误码: "+iRet;
    }
    return "ok-制卡成功!";
}