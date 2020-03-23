package com.hjw.crm.action;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.DTO.JianKzDTO;
import com.hjw.crm.model.JianKzModel;
import com.hjw.crm.service.JianKzService;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class JianKzAction  extends BaseAction implements ModelDriven{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private JianKzModel model=new JianKzModel();
	private JianKzService jianKzService;
	private SyslogService syslogService;
	private CustomerInfoService customerInfoService;
	private int page=1;
	private int rows=15;
	@Override
	
	public Object getModel() {
		return model;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public JianKzService getJianKzService() {
		return jianKzService;
	}
	public void setJianKzService(JianKzService jianKzService) {
		this.jianKzService = jianKzService;
	}
	public SyslogService getSyslogService() {
		return syslogService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public void setModel(JianKzModel model) {
		this.model = model;
	}
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	/**
	 * 总检室保存健康证信息
	     * @Title: saveExamSummaryJianKangZheng   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryJianKangZheng() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.jianKzService.saveExamSummaryJianKangZheng(this.model, user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 获取体检者本年度的健康证号
	     * @Title: getOldHealthNoOneYear   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOldHealthNoOneYear() throws WebException,SQLException{
		List<JianKzDTO> list = this.jianKzService.getOldHealthNoOneYear(model.getArch_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	public String getJianKangZhengDetailPage()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		JianKzModel newmodel=new JianKzModel();  
		newmodel=this.jianKzService.getResultJianKzModel(model.getExam_num(), user.getCenter_num());
		this.model.setDwbm(newmodel.getDwbm());
		this.model.setDwbmname(newmodel.getDwbmname());
		this.model.setPic(newmodel.getPic());
		this.model.setBh(newmodel.getBh());
		this.model.setXm(newmodel.getXm());
		this.model.setXb(newmodel.getXb());
		this.model.setNl(newmodel.getNl());
		this.model.setDw(newmodel.getDw());
		this.model.setHy(newmodel.getHy());
		this.model.setHycode(newmodel.getHycode());
		this.model.setGz(newmodel.getHy());
		this.model.setGzcode(newmodel.getHycode());
		this.model.setDh(newmodel.getDh());
		this.model.setSfzh(newmodel.getSfzh());
		this.model.setRq(newmodel.getRq());
		this.model.setHjgcode(newmodel.getHjgcode());
		if(newmodel.getHjgcode().equals("0701")){
			this.model.setHjg("合格");
		}
		this.model.setSfhgcode(newmodel.getSfhgcode());
		if(newmodel.getSfhgcode().equals("1")){
			this.model.setSfhg("合格");
		}else if(newmodel.getSfhgcode().equals("0")){
			this.model.setSfhg("不合格");
		}
		this.model.setJg_xin(newmodel.getJg_xin());
		if(model.getJg_xin().contains("不正常")){
			this.model.setJg_xincode("0002");
		}else if(model.getJg_xin().contains("正常")){
			this.model.setJg_xincode("0001");
		}else if(model.getJg_xin().equals("未见异常")){
			this.model.setJg_xincode("0001");
		}else if(model.getJg_xin().equals("未见明显异常")){
			this.model.setJg_xincode("0001");
		}
		this.model.setJg_gan(newmodel.getJg_gan());
		if(model.getJg_gan().contains("不正常")){
			this.model.setJg_gancode("0004");
		}else if(model.getJg_gan().contains("正常")){
			this.model.setJg_gancode("0003");
		}else if(model.getJg_gan().equals("未见异常")){
			this.model.setJg_gancode("0003");
		}else if(model.getJg_gan().equals("未见明显异常")){
			this.model.setJg_gancode("0003");
		}
		this.model.setJg_pi(newmodel.getJg_pi());
		if(model.getJg_pi().contains("不正常")){
			this.model.setJg_picode("0006");
		}else if(model.getJg_pi().contains("正常")){
			this.model.setJg_picode("0005");
		}else if(model.getJg_pi().equals("未见异常")){
			this.model.setJg_picode("0005");
		}else if(model.getJg_pi().equals("未见明显异常")){
			this.model.setJg_picode("0005");
		}
		this.model.setJg_tppa(newmodel.getJg_tppa());
		 if(model.getJg_tppa().contains("阳性")){
			this.model.setJg_tppacode("0402");
		}else if(model.getJg_tppa().contains("阴性")){
			this.model.setJg_tppacode("0401");
		}
		this.model.setJg_pf(newmodel.getJg_pf());
		if(model.getJg_pf().contains("手癣")){
			this.model.setJg_pfcode("00110001");
		}else if(model.getJg_pf().contains("指甲癣")){
			this.model.setJg_pfcode("00110002");
		}else if(model.getJg_pf().contains("手湿疹")){
			this.model.setJg_pfcode("00110003");
		}else if(model.getJg_pf().contains("银屑病")){
			this.model.setJg_pfcode("00110004");
		}else if(model.getJg_pf().contains("渗出性皮肤病")){
			this.model.setJg_pfcode("00110005");
		}else if(model.getJg_pf().contains("化脓性皮肤病")){
			this.model.setJg_pfcode("00110006");
		}else if(model.getJg_pf().contains("正常")){
			this.model.setJg_pfcode("0010");
		}else if(model.getJg_pf().equals("未见异常")){
			this.model.setJg_pfcode("0010");
		}else if(model.getJg_pf().equals("未见明显异常")){
			this.model.setJg_pfcode("0010");
		}
		this.model.setJg_qt(newmodel.getJg_qt());
		if(this.model.getJg_qt().contains("不正常")){
			this.model.setJg_qtcode("0015");
		}else if(this.model.getJg_qt().contains("正常")){
			this.model.setJg_qtcode("0014");
		}else if(this.model.getJg_qt().equals("未见异常")){
			this.model.setJg_qtcode("0014");
		}else if(this.model.getJg_qt().equals("未见明显异常")){
			this.model.setJg_qtcode("0014");
		}
		this.model.setJg_lj(newmodel.getJg_lj());
		if(this.model.getJg_lj().contains("鲍氏志贺氏菌")){
			this.model.setJg_ljcode("02020007");
		}else if(this.model.getJg_lj().contains("福氏志贺氏菌")){
			this.model.setJg_ljcode("02020008");
		}else if(this.model.getJg_lj().contains("宋内氏志贺氏菌")){
			this.model.setJg_ljcode("02020009");
		}else if(this.model.getJg_lj().contains("志贺氏菌")){
			this.model.setJg_ljcode("02020011");
		}else if(this.model.getJg_lj().contains("便复查")){
			this.model.setJg_ljcode("0205");
		}else if(this.model.getJg_lj().contains("未检出")){
			this.model.setJg_ljcode("0201");
		}
		this.model.setJg_sh(newmodel.getJg_sh());
		if(this.model.getJg_sh().contains("便复查")){
			this.model.setJg_shcode("0206");
		}else if(this.model.getJg_sh().contains("未检出")){
			this.model.setJg_shcode("0203");
		}else if(this.model.getJg_sh().contains("检出")){
			this.model.setJg_shcode("0204");
		}
		this.model.setJg_xt(newmodel.getJg_xt());
		if(this.model.getJg_xt().contains("陈旧性肺结核")){
			this.model.setJg_xtcode("0103");
		}else if(this.model.getJg_xt().contains("左侧胸膜炎影像")){
			this.model.setJg_xtcode("010301");
		}else if(this.model.getJg_xt().contains("右侧胸膜炎影像")){
			this.model.setJg_xtcode("010302");
		}else if(this.model.getJg_xt().contains("两侧胸膜炎影像")){
			this.model.setJg_xtcode("010303");
		}else if(this.model.getJg_xt().contains("左肺炎症")){
			this.model.setJg_xtcode("010304");
		}else if(this.model.getJg_xt().contains("右肺炎症")){
			this.model.setJg_xtcode("010305");
		}else if(this.model.getJg_xt().contains("左右肺炎症")){
			this.model.setJg_xtcode("010306");
		}else if(this.model.getJg_xt().contains("左肺上野斑片影")){
			this.model.setJg_xtcode("01040021");
		}else if(this.model.getJg_xt().contains("左肺上中野斑片影")){
			this.model.setJg_xtcode("01040022");
		}else if(this.model.getJg_xt().contains("左肺中野斑片影")){
			this.model.setJg_xtcode("01040023");
		}else if(this.model.getJg_xt().contains("右肺上野斑片影")){
			this.model.setJg_xtcode("01040024");
		}else if(this.model.getJg_xt().contains("右肺上中野斑片影")){
			this.model.setJg_xtcode("01040025");
		}else if(this.model.getJg_xt().contains("右肺中野斑片影")){
			this.model.setJg_xtcode("01040026");
		}else if(this.model.getJg_xt().contains("两肺上野斑片影")){
			this.model.setJg_xtcode("01040027");
		}else if(this.model.getJg_xt().contains("两肺上中野斑片影")){
			this.model.setJg_xtcode("01040028");
		}else if(this.model.getJg_xt().contains("左肺上野片影")){
			this.model.setJg_xtcode("01040029");
		}else if(this.model.getJg_xt().contains("左肺中野片影")){
			this.model.setJg_xtcode("01040031");
		}else if(this.model.getJg_xt().contains("左肺下野片影")){
			this.model.setJg_xtcode("01040032");
		}else if(this.model.getJg_xt().contains("右肺中野片影")){
			this.model.setJg_xtcode("01040033");
		}else if(this.model.getJg_xt().contains("右肺下野片影")){
			this.model.setJg_xtcode("01040034");
		}else if(this.model.getJg_xt().contains("右肺上野片影")){
			this.model.setJg_xtcode("01040035");
		}else if(this.model.getJg_xt().contains("未见异常")){
			this.model.setJg_xtcode("0101");
		}
		this.model.setJg_alt(newmodel.getJg_alt());
		if(model.getJg_alt()!=null&&model.getJg_alt().length()>0){
			if(Integer.valueOf(this.model.getJg_alt())<40){
				this.model.setJg_altcode("0301");
			}else if(Integer.valueOf(this.model.getJg_alt())>=40){
				this.model.setJg_altcode("0302");
			}	
		}
		this.model.setJg_hbsag(newmodel.getJg_hbsag());
		 if(this.model.getJg_hbsag().contains("HBsAg(+)")){
			this.model.setJg_hbsagcode("0502");
		}else if(this.model.getJg_hbsag().contains("血复查")){
			this.model.setJg_hbsagcode("0507");
		}else if(this.model.getJg_hbsag().contains("HBsAg(-)")){
			this.model.setJg_hbsagcode("0501");
		}
		this.model.setJg_hbeag(newmodel.getJg_hbeag());
		if(this.model.getJg_hbeag().contains("HBeAg(-)")){
			this.model.setJg_hbeagcode("0503");
		}else if(this.model.getJg_hbeag().contains("HBeAg(+)")){
			this.model.setJg_hbeagcode("0504");
		}else if(this.model.getJg_hbeag().contains("HBeAg(+)")){
			this.model.setJg_hbeagcode("0504");
		}else if(this.model.getJg_hbeag().contains("HBeAg血复查")){
			this.model.setJg_hbeagcode("0505");
		}else if(this.model.getJg_hbeag().contains("HBeAg(+)血复查")){
			this.model.setJg_hbeagcode("0506");
		}else if(this.model.getJg_hbeag().contains("缓报")){
			this.model.setJg_hbeagcode("0508");
		}
		this.model.setJg_rpr(newmodel.getJg_rpr());
		if(this.model.getJg_rpr().contains("RPR(+)血复查")){
			this.model.setJg_rprcode("0604");
		}else if(this.model.getJg_rpr().contains("RPR(+)")){
			this.model.setJg_rprcode("0611");
		}else if(this.model.getJg_rpr().contains("RPR(-)")){
			this.model.setJg_rprcode("0603");
		}
		this.model.setJg_lqj(newmodel.getJg_lqj());
		if(this.model.getJg_lqj().contains("检出")){
			this.model.setJg_lqjcode("0608");
		}else if(this.model.getJg_lqj().contains("未检出")){
			this.model.setJg_lqjcode("0607");
		}
		this.model.setJg_hav(newmodel.getJg_hav());
		if(this.model.getJg_hav().contains("阳性")){
			this.model.setJg_havcode("0402");
		}else if(this.model.getJg_hav().contains("阴性")){
			this.model.setJg_havcode("0401");
		}
		this.model.setJg_hiv(newmodel.getJg_hiv());
		if(this.model.getJg_hiv().contains("阳性")){
			this.model.setJg_hivcode("0602");
		}else if(this.model.getJg_hiv().contains("阴性")){
			this.model.setJg_hivcode("0601");
		}
		this.model.setSffzcode(newmodel.getSffzcode());
		if(newmodel.getSffzcode().equals("1")){
			this.model.setSffz("允许");
		}else if(newmodel.getSffzcode().equals("0")){
			this.model.setSffz("不允许");
		}
		this.model.setFzrq(newmodel.getFzrq());
		this.model.setYxq(newmodel.getYxq());
		this.model.setDjczy(newmodel.getDjczy());
		this.model.setDyrq(newmodel.getDyrq());
		this.model.setDwdz(newmodel.getDwdz());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm001");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	public String getJianKangZhengListPage()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setBarcode_print_type(this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm001");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 详细页面健康证上传 
	     * @Title: jianKangZhuangChuanShu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws UnsupportedEncodingException
	     * @param: @throws IllegalArgumentException
	     * @param: @throws IllegalAccessException
	     * @param: @throws FileNotFoundException      
	     * @return: String      
	     * @throws
	 */
	public String jianKangZhuangChuanShu()throws  WebException,SQLException, UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException, FileNotFoundException{
		UserDTO user = (UserDTO) session.get("username");
		String examflag=this.jianKzService.getExamFlag(model.getExam_num());
		String str="";
		if(!examflag.equals("1")){
			 str=this.jianKzService.jianKangZhuangChuanShu(model, user.getCenter_num());
		}else{
		str="已上传";	
		}
		this.outJsonStrResult(str);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm108");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	public String getJKZList() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto =  this.jianKzService.getJKZList(model, page, rows,user);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm154");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	} 
	public String shangChuanjkz()throws  WebException,SQLException, UnsupportedEncodingException, ServiceException, IllegalArgumentException, IllegalAccessException, FileNotFoundException{
		UserDTO user = (UserDTO) session.get("username");
		String str=this.jianKzService.jianKangZhuangBuFenChuanShu(model.getExam_nums(), user.getCenter_num());
		this.outJsonStrResult(str);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm108");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	public String shangChuanAlljkz()throws  WebException,SQLException, UnsupportedEncodingException, ServiceException, IllegalArgumentException, IllegalAccessException, FileNotFoundException{
		UserDTO user = (UserDTO) session.get("username");
		String str=this.jianKzService.jianKangZhuangAllChuanShu(model, user.getCenter_num());
		this.outJsonStrResult(str);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他
		
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm108");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
}
