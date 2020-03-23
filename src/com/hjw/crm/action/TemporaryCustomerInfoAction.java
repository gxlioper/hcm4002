package com.hjw.crm.action;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hsqldb.lib.StringUtil;

import com.hjw.crm.DTO.TemporaryCustomerInfoDTO;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.domain.TemporaryCustomerInfo;
import com.hjw.crm.model.TemporaryCustomerInfoModel;
import com.hjw.crm.service.TemporaryCustomerInfoService;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class TemporaryCustomerInfoAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	private TemporaryCustomerInfoModel model=new TemporaryCustomerInfoModel();
	private TemporaryCustomerInfoService temporaryCustomerInfoService;
	private int page=1;
	private int rows=15;
    private SyslogService syslogService;
    


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

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public TemporaryCustomerInfoService getTemporaryCustomerInfoService() {
		return temporaryCustomerInfoService;
	}

	public void setTemporaryCustomerInfoService(TemporaryCustomerInfoService temporaryCustomerInfoService) {
		this.temporaryCustomerInfoService = temporaryCustomerInfoService;
	}

	public void setModel(TemporaryCustomerInfoModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		return model;
	}
	
	/**
	 * crm147获取会员临时表
	     * @Title: getTemporaryCustomerInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getTemporaryCustomerInfoList()throws  WebException,SQLException{
		PageReturnDTO  dto =  this.temporaryCustomerInfoService.getTemporaryCustomerInfoList(model, page, rows);
		this.outJsonResult( dto );
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm147");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	//删除
	/**
	 * crm148删除临时表信息
	     * @Title: deleteTemporaryCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
		public String deleteTemporaryCustomerInfo() throws  WebException,SQLException{
				String yi=this.temporaryCustomerInfoService.deleteTemporaryCustomerInfo(model.getIds());
				if(yi=="1"){
					this.outJsonStrResult(this.message="删除成功");	
				}else{
					this.outJsonStrResult(this.message="删除失败");
				}
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("crm148");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
				return NONE;
			}
		
		//更新
		/**
		 * crm137获取更新会员临时表页面
		     * @Title: getUpdateTemporaryCustomerInfoPage   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException      
		     * @return: String      
		     * @throws
		 */
		public String getUpdateTemporaryCustomerInfoPage() throws  WebException,SQLException{
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			TemporaryCustomerInfo temporaryCustomerInfo =this.temporaryCustomerInfoService.getTemporaryCustomerInfo(model.getId());
			model.setId(temporaryCustomerInfo.getId());
			model.setUser_name(temporaryCustomerInfo.getUser_name());
			model.setId_num(temporaryCustomerInfo.getId_num());
			model.setSex(temporaryCustomerInfo.getSex());
			model.setBirthday(temporaryCustomerInfo.getBirthday());
			model.setNation(temporaryCustomerInfo.getNation());
			model.setPhone(temporaryCustomerInfo.getPhone());
			model.setAddress(temporaryCustomerInfo.getAddress());
			model.setEmail(temporaryCustomerInfo.getEmail());
			model.setLevel(temporaryCustomerInfo.getLevel());
			model.setIntegral(temporaryCustomerInfo.getIntegral());
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm137");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return SUCCESS;
		}

		/**
		 * crm149更新临时表信息
		     * @Title: updateTemporaryCustomerInfo   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException
		     * @param: @throws ParseException      
		     * @return: String      
		     * @throws
		 */
		public String updateTemporaryCustomerInfo() throws  WebException,SQLException, ParseException{
			Date date=new Date();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			UserDTO user = (UserDTO) session.get("username");
			TemporaryCustomerInfo c = new TemporaryCustomerInfo();
			c=this.temporaryCustomerInfoService.getTemporaryCustomerInfo(model.getId());
						c.setUser_name(model.getUser_name());
						c.setId_num(model.getId_num());
						c.setSex(model.getSex());
						c.setBirthday(model.getBirthday());
						String nation=this.temporaryCustomerInfoService.getNationByName(model.getNation_name());
						c.setNation(nation);
						c.setPhone(model.getPhone());
						c.setAddress(model.getAddress());
						c.setEmail(model.getEmail());
						String regex = "(^\\d{18}$)|(^\\d{15}$)";
						Pattern pattern = Pattern.compile(regex);
						if(!StringUtil.isEmpty(this.model.getId_num())){
							Matcher matcher = pattern.matcher(this.model.getId_num());
							if(matcher.matches()){
								String flag1="0";
								String notices1="";
				    			List<TemporaryCustomerInfo> list=temporaryCustomerInfoService.getTemporaryCustomerInfoByIdBum(c.getId_num());
				    			if(list!=null&&list.size()>0){
				    				flag1="1";
				    				notices1="该身份证号已存在";
				    			}
				    			List<CustomerInfo> list2=temporaryCustomerInfoService.getCustomerInfoByIdBum(c.getId_num());
				    			if(list2!=null&&list2.size()>0){
				    				flag1="2";
				    				notices1="该会员有过体检，但之前不是会员";
				    				List<CustomerMemberInfo> list1=temporaryCustomerInfoService.getCustomerMemberInfoByIdBum(list2.get(0).getArch_num());
					    			if(list1!=null&&list1.size()>0){
					    				flag1="1";
					    				notices1="会员表中该的身份证号已经注册";
					    			}
				    			}
				    			c.setFlag(Long.valueOf(flag1));
				    			c.setNotices(notices1);
				    			String bird = model.getId_num().trim().substring(6, 14);
								Date da = Timeutils.strToDateLong2(bird);
								bird = DateTimeUtil.shortFmt3(da);
								c.setBirthday(bird);
								if (Integer.valueOf(model.getId_num().trim().substring(16, 17)) % 2 == 1) {
							           //男
									c.setSex("男");
							       }else{
							           //女
							    	 c.setSex("女");
							       }
							}else{
								c.setFlag(1);
						    	c.setNotices("身份证号格式错误");
							}	
						}else{
							c.setFlag(1);
					    	c.setNotices("身份证号为空");
						}
						this.temporaryCustomerInfoService.updateTemporaryCustomerInfo(c);
						this.message = "编辑成功";
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("crm149");//子功能id 必须填写
						sl.setExplain("");//操作说明
						syslogService.saveSysLog(sl);
			this.outJsonStrResult( this.message );
			return NONE;
		}
		
		/**
		 * crm138获取导入文件页面
		     * @Title: impMemberFile   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException      
		     * @return: String      
		     * @throws
		 */
		public String impMemberFile()throws WebException, SQLException {
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("4");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm138");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return SUCCESS;
		}
		
		/**
		 * crm150导入部分临时表信息
		     * @Title: impuserToCustomerinfodo   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException
		     * @param: @throws ParseException      
		     * @return: String      
		     * @throws
		 */
		public String impuserToCustomerinfodo() throws WebException, SQLException, ParseException {
			UserDTO user = (UserDTO) session.get("username");
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String[] strs=new String[]{};
			strs=model.getIds().split(",");
			int index=0;
			for(String id:strs){
				TemporaryCustomerInfo temporaryCustomerInfo=new TemporaryCustomerInfo();
				temporaryCustomerInfo=this.temporaryCustomerInfoService.getTemporaryCustomerInfo(id);
				String user_name=temporaryCustomerInfo.getUser_name();
				String sex=temporaryCustomerInfo.getSex();
				String email=temporaryCustomerInfo.getEmail();
				String address=temporaryCustomerInfo.getAddress();
				String birthday=temporaryCustomerInfo.getBirthday();
				String id_num=temporaryCustomerInfo.getId_num();
				long level=temporaryCustomerInfo.getLevel();
				long integral=temporaryCustomerInfo.getIntegral();
				String nation=temporaryCustomerInfo.getNation();
				String phone=temporaryCustomerInfo.getPhone();
				String arch_num=GetNumContral.getInstance().getParamNum("vipno", user.getCenter_num());
				long flag=temporaryCustomerInfo.getFlag();
				List<CustomerInfo> list=this.temporaryCustomerInfoService.getCustomerInfoByIdBum(id_num);
				if(list!=null&&list.size()>0){
					index=index+1;
				}else{
					if(flag==2){//身份证在customerinfo已经存在，但是会员还没注册
						 CustomerInfo customerInfo=new CustomerInfo();
						 customerInfo=this.temporaryCustomerInfoService.getCustomerInfoTemporary(id_num);
						 if(user_name!=null&&user_name.length()>0){customerInfo.setUser_name(user_name);}
						 if(sex!=null&&sex.length()>0){customerInfo.setSex(sex);}
						 if(email!=null&&email.length()>0){customerInfo.setEmail(email);}
						 if(address!=null&&address.length()>0){customerInfo.setAddress(address);}
						 if(birthday!=null&&birthday.length()>0){customerInfo.setBirthday(df.parse(birthday));}
						 if(nation!=null&&nation.length()>0){customerInfo.setNation(nation);}
						 if(phone!=null&&phone.length()>0){customerInfo.setPhone(phone);}
						 this.temporaryCustomerInfoService.updateCustomerInfo(customerInfo);
						 CustomerMemberInfo customerMemberInfo=new CustomerMemberInfo();
						 customerMemberInfo.setArch_num(customerInfo.getArch_num());
						 customerMemberInfo.setLevel(level);
						 customerMemberInfo.setIntegral(integral);
						 this.temporaryCustomerInfoService.saveCustomerMemberInfo(customerMemberInfo);
						 String ids="";
						 ids="'"+id+"'";
						 this.temporaryCustomerInfoService.deleteTemporaryCustomerInfo(ids);
					}else if(flag==1){//各种东西有问题不能入库
						index=index+1;
					}else{
						 CustomerInfo customerInfo=new CustomerInfo();
						 if(user_name!=null&&user_name.length()>0){customerInfo.setUser_name(user_name);}
						 if(sex!=null&&sex.length()>0){customerInfo.setSex(sex);}
						 if(email!=null&&email.length()>0){customerInfo.setEmail(email);}
						 if(address!=null&&address.length()>0){customerInfo.setAddress(address);}
						 if(birthday!=null&&birthday.length()>0){customerInfo.setBirthday(df.parse(birthday));}
						 if(nation!=null&&nation.length()>0){customerInfo.setNation(nation);}
						 if(phone!=null&&phone.length()>0){customerInfo.setPhone(phone);}
						 if(id_num!=null&&id_num.length()>0){customerInfo.setId_num(id_num);}
						 customerInfo.setArch_num(arch_num);
						 customerInfo.setFlag(String.valueOf(0));
						 this.temporaryCustomerInfoService.saveCustomerInfo(customerInfo);
						 CustomerMemberInfo customerMemberInfo=new CustomerMemberInfo();
						 customerMemberInfo.setArch_num(customerInfo.getArch_num());
						 customerMemberInfo.setLevel(level);
						 customerMemberInfo.setIntegral(integral);
						 this.temporaryCustomerInfoService.saveCustomerMemberInfo(customerMemberInfo);
						 String ids="";
						 ids="'"+id+"'";
						 this.temporaryCustomerInfoService.deleteTemporaryCustomerInfo(ids);
					}
				}
				if(index==0){
					this.message="导入成功";
				}else{
					this.message=index+"条导入失败,请查询";
				}
				}
				
			this.outJsonStrResult(this.message);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("4");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm150");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
		}
		
		/**
		 * crm151导入全部临时表信息
		     * @Title: alluserToCustomerinfodo   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException
		     * @param: @throws ParseException      
		     * @return: String      
		     * @throws
		 */
		public String alluserToCustomerinfodo() throws WebException, SQLException, ParseException {
			UserDTO user = (UserDTO) session.get("username");
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			List<TemporaryCustomerInfo> list=this.temporaryCustomerInfoService.getTemporaryCustomerInfoList();
			int index=0;
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					TemporaryCustomerInfo temporaryCustomerInfo=list.get(i);
					String id=temporaryCustomerInfo.getId();
					String user_name=temporaryCustomerInfo.getUser_name();
					String sex=temporaryCustomerInfo.getSex();
					String email=temporaryCustomerInfo.getEmail();
					String address=temporaryCustomerInfo.getAddress();
					String birthday=temporaryCustomerInfo.getBirthday();
					String id_num=temporaryCustomerInfo.getId_num();
					long level=temporaryCustomerInfo.getLevel();
					long integral=temporaryCustomerInfo.getIntegral();
					String nation=temporaryCustomerInfo.getNation();
					String phone=temporaryCustomerInfo.getPhone();
					long flag=temporaryCustomerInfo.getFlag();
					String arch_num=GetNumContral.getInstance().getParamNum("vipno", user.getCenter_num());
					if(flag==2){//身份证在customerinfo已经存在，但是会员还没注册
						CustomerInfo customerInfo =this.temporaryCustomerInfoService.getCustomerInfoTemporary(id_num);
						 customerInfo=this.temporaryCustomerInfoService.getCustomerInfoTemporary(id_num);
						 if(user_name!=null&&user_name.length()>0){customerInfo.setUser_name(user_name);}
						 if(sex!=null&&sex.length()>0){customerInfo.setSex(sex);}
						 if(email!=null&&email.length()>0){customerInfo.setEmail(email);}
						 if(address!=null&&address.length()>0){customerInfo.setAddress(address);}
						 if(birthday!=null&&birthday.length()>0){customerInfo.setBirthday(df.parse(birthday));}
						 if(nation!=null&&nation.length()>0){customerInfo.setNation(nation);}
						 if(phone!=null&&phone.length()>0){customerInfo.setPhone(phone);}
						 this.temporaryCustomerInfoService.updateCustomerInfo(customerInfo);
						 CustomerMemberInfo customerMemberInfo=new CustomerMemberInfo();
						 customerMemberInfo.setArch_num(customerInfo.getArch_num());
						 customerMemberInfo.setLevel(level);
						 customerMemberInfo.setIntegral(integral);
						 this.temporaryCustomerInfoService.saveCustomerMemberInfo(customerMemberInfo);
						 String ids="";
						 ids="'"+id+"'";
						 this.temporaryCustomerInfoService.deleteTemporaryCustomerInfo(ids);
					}else if(flag==1){//各种东西有问题不能入库
						index=index+1;
					}else{
						 CustomerInfo customerInfo=new CustomerInfo();
						 if(user_name!=null&&user_name.length()>0){customerInfo.setUser_name(user_name);}
						 if(sex!=null&&sex.length()>0){customerInfo.setSex(sex);}
						 if(email!=null&&email.length()>0){customerInfo.setEmail(email);}
						 if(address!=null&&address.length()>0){customerInfo.setAddress(address);}
						 if(birthday!=null&&birthday.length()>0){customerInfo.setBirthday(df.parse(birthday));}
						 if(nation!=null&&nation.length()>0){customerInfo.setNation(nation);}
						 if(phone!=null&&phone.length()>0){customerInfo.setPhone(phone);}
						 customerInfo.setArch_num(arch_num);
						 this.temporaryCustomerInfoService.saveCustomerInfo(customerInfo);
						 CustomerMemberInfo customerMemberInfo=new CustomerMemberInfo();
						 customerMemberInfo.setArch_num(customerInfo.getArch_num());
						 customerMemberInfo.setLevel(level);
						 customerMemberInfo.setIntegral(integral);
						 this.temporaryCustomerInfoService.saveCustomerMemberInfo(customerMemberInfo);
						 String ids="";
						 ids="'"+id+"'";
						 this.temporaryCustomerInfoService.deleteTemporaryCustomerInfo(ids);
					}
				}
				System.out.println(index);
				if(index==0){
					this.message="全部导入成功";
				}else{
					this.message=index+"条导入失败,请查询";
				}
				
			}else{
				this.message="临时表信息为空";
			}
			this.outJsonStrResult(this.message);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("4");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm151");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
		}
		/**
		 * crm158获取民族编码和名称
		     * @Title: getNation   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException      
		     * @return: String      
		     * @throws
		 */
		public String getNation()throws  WebException,SQLException{
			List<TemporaryCustomerInfoDTO> dto= this.temporaryCustomerInfoService.getNation();
			this.outJsonResult( dto );
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm158");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
		}
		
		/**
		 * crm159获取等级编码和名称
		     * @Title: getLevelName   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException      
		     * @return: String      
		     * @throws
		 */
		public String getLevelName()throws  WebException,SQLException{
			List<TemporaryCustomerInfoDTO> dto= this.temporaryCustomerInfoService.getLevelName();
			this.outJsonResult( dto );
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm159");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
		}
		
}
