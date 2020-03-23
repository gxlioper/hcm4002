package com.hjw.wst.action;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.http.protocol.HTTP;
import org.apache.struts2.ServletActionContext;

import com.fr.general.DesUtils;
import com.hjw.crm.DTO.ResultDTO;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.ExamInfoModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.YxCardPdfService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONObject;

public class YxCardPdfAction  extends BaseAction implements ModelDriven{
	
	private YxCardPdfService yxCardPdfService;
	private ExamInfoModel model = new ExamInfoModel();
	private String pdf_name;
	private CustomerInfoService customerInfoService;
	
	public String getPdf_name() {
		return pdf_name;
	}
	public void setPdf_name(String pdf_name) {
		this.pdf_name = pdf_name;
	}
	public void setYxCardPdfService(YxCardPdfService yxCardPdfService) {
		this.yxCardPdfService = yxCardPdfService;
	}
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 调用接口报告评测问卷
	     * @Title: getYxCardPdf   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getYxCardPdf() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		String config_value = "";
		//获取配置
		CenterConfigurationDTO cen = this.customerInfoService.getCenterconfigByKey("IS_EXAM_ZHEER_GETBAOGAO", user.getCenter_num());
		if(cen!=null && !"".equals(cen)){
			config_value = cen.getConfig_value().trim();
		}
		if("".equals(config_value)){
			this.outJsonStrResult( this.message="{\"result\":\"接口未开启\",\"status\":\"error\"}");
		} else {
			String patientId = "";
			if(model.getExam_num()!=null && !"".equals(model.getExam_num())){
				patientId = yxCardPdfService.getExamInfoPatient(model);
			}
			if(patientId!=null && !"".equals(patientId)){
				try {
					//加密
					String exam_num = DesUtils.getEncString(model.getExam_num(), "huojianw");
					String str = "";
					HttpClient client = new HttpClient();
					 client.getHttpConnectionManager().getParams().setConnectionTimeout(9000);
					  client.getHttpConnectionManager().getParams().setSoTimeout(9000);
					  //String url = config_value+"?exam_num='"+exam_num+"'";
					 
					//GetMethod getMethod = new GetMethod(url);
					  GetMethod getMethod = new GetMethod(config_value);
					org.apache.commons.httpclient.NameValuePair[] n = new org.apache.commons.httpclient.NameValuePair[1];
					n[0] = new org.apache.commons.httpclient.NameValuePair("exam_num",exam_num);
					 TranLogTxt.liswriteEror_to_txt("jiankangbaogao", "CardPDF:" + config_value+"exam_num="+exam_num);
					 getMethod.setQueryString(EncodingUtil.formUrlEncode(n, "UTF-8"));
					 getMethod.addRequestHeader(HTTP.CONTENT_TYPE, "application/json");
					int statu = client.executeMethod(getMethod);
						if(statu==200){
							String res = getMethod.getResponseBodyAsString();
							if(res!=""){
								int cnnt = 0;
								 try {
									JSONObject jsonoStr = JSONObject.fromObject(res);
									 ResultDTO re= (ResultDTO)JSONObject.toBean(jsonoStr,ResultDTO.class);
									 str = re.getResult().substring(re.getResult().indexOf("-")+1,re.getResult().length());
									 this.message="{\"result\":\""+str+"\",\"status\":\"error\"}";
								} catch (Exception e) {
									cnnt = 1;
								}
									if(cnnt==1){
										byte[] bytes = getMethod.getResponseBody();
						    			int n1 = 1024;
					    		        // 创建文件输出流对象
					    			   String path=ServletActionContext.getServletContext().getRealPath(File.separator);
					    		        File file = new File(path+"/jiankangpingce.pdf");
					    		        if(file.exists()){
					    		        	file.delete();
					    		        }
					    		        FileOutputStream os= new FileOutputStream(file);
					    		        int length = bytes.length;
					    		        int start = 0;
					    		        while(length>start+n1){
					    		        	os.write(bytes, start, n1);
					    		            start= start+n1;
					    		        }
					    		        if(length != start+n1){
					    		            n1 = length-start;
					    		            os.write(bytes, start, n1);
					    		        }
					    		        os.flush();
					    		        os.close();
					    		        this.message="{\"result\":\"SUCCESS\",\"status\":\"SUCCESS\"}";
									}
							} else {
								this.message="{\"result\":\"无返回\",\"status\":\"error\"}";
							}
					} else {
						 this.message="{\"result\":\""+statu+"\",\"status\":\"error\"}";
					}
				} catch (Exception e) {
					this.message="{\"result\":\"连接超时\",\"status\":\"error\"}";
					e.printStackTrace();
				}
				this.outJsonStrResult(this.message);
			} else {
				this.outJsonStrResult( this.message="{\"result\":\"未答问卷\",\"status\":\"error\"}");
			}
		}
		return NONE;
	}
	/**
	 * 问卷报告评测显示页面
	     * @Title: getYxCardPdfPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getYxCardPdfPage() throws  WebException{
		return SUCCESS;
	}
	/**
	 * 显示问卷测评报告按钮
	     * @Title: getDepShwoPinCeBaoGaoButtion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDepShwoPinCeBaoGaoButtion()throws  WebException{
		String patientId = yxCardPdfService.getExamInfoPatient(model);
		this.outJsonStrResult(patientId);
		return NONE;
	}
}
