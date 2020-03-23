package com.hjw.wst.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.config.Logincheck;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.MultiSignCollector;
import com.hjw.wst.DTO.MultiSignSearch;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.service.CommService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONObject; 

/**
 * 
 * @Title: 火箭蛙体检管理系统 第三方结果回传
 * @Package com.hjw.wst.action
 * @Description:
 * @author: yangm
 * @date: 2016年7月16日 下午8:32:41
 * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ThirdPRRAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private MultiSignCollector model = new MultiSignCollector();
    private CommService commService;    
	private String _id="";
	private String data="";	
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
	public void setCommService(CommService commService) {
		this.commService = commService;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(MultiSignCollector model) {
		this.model = model;
	}

	/**
	 * 多体征采集仪查询信息接口
	     * @Title: getTExamInfoForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
    public String getTExamInfoForNum()  throws WebException{
    	UserDTO user = (UserDTO) session.get("username");
    	MultiSignSearch ss = new MultiSignSearch();
    	if(StringUtil.isEmpty(this.get_id())){
    		ss.setError("体检编号不能为空");
    		ss.setStatus("ERROR");
    	}else{
    		ExamInfoUserDTO ei= new ExamInfoUserDTO();
    		ei=this.commService.getCustExamInfoForNum(this.get_id(),user);
    		if((ei==null)||(ei.getId()<=0)){
    			ss.setError("无此信息");
        		ss.setStatus("ERROR");
    		}else{
    			ss.setError("");
        		ss.setStatus("SUCCESS");
        		ss.setMsg(ei.getUser_name()+",$住院号:"+ei.getExam_num());
    		}
    	}
    	String res="{error:\""+ss.getError()+"\",status:\""+ss.getStatus()+"\",msg:\""+ss.getMsg()+"\"}"; 
    	this.outJsonStrResult(res);
    	return NONE;
    }
    
    /**
	 *   多体征采集仪信息入库接口
	     * @Title: getTExamInfoForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
    public String setMultiSignIn()  throws WebException{
    	UserDTO user = (UserDTO) session.get("username");
    	TranLogTxt.liswriteEror_to_txt("dtzcjyLog","res:"+data);				
    	MultiSignCollector ms = new MultiSignCollector();
    	if(StringUtil.isEmpty(data)){
    		this.message="{error:\"消息体为空!\",status:\"ERROR\",msg:\"\"}";
    	}else{
    		data = data.replaceAll("\\\\", "");
    		JSONObject jsonObject=JSONObject.fromObject(data);
    		ms=(MultiSignCollector) JSONObject.toBean(jsonObject,MultiSignCollector.class);  
    	    this.message = this.commService.thirdPRRSave(ms,user.getCenter_num());
    	}
    	TranLogTxt.liswriteEror_to_txt("dtzcjyLog","res:"+this.message);
    	this.outJsonStrResult(this.message);
    	return NONE;
    }
}