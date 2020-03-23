package com.hjw.wst.action;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.util.Base64;
import com.hjw.util.ValidateTime;
import com.hjw.wst.service.CommService;
import com.hjw.wst.service.CustomerInfoService;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.action
 * @Description:
 * @author: yangm
 * @date: 2016年7月16日 下午8:32:41
 * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class SavePicAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	private CommService commService;
    private String pic64string;
    private long exam_id;    
    private JdbcQueryManager jqm;
    
	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public String getPic64string() {
		return pic64string;
	}

	public void setPic64string(String pic64string) {
		this.pic64string = pic64string;
	}


	public long getExam_id() {
		return exam_id;
	}

	public void setExam_id(long exam_id) {
		this.exam_id = exam_id;
	}

	public void setCommService(CommService commService) {
		this.commService = commService;
	}
	/**
	 * 
	 * @Title: djtcutsavePicture @Description:摄像头剪切保存图片和上传图片
	 *         424 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String savePicture() throws WebException {
		this.message="error-保存图片失败";
		try {
			byte[] bmpfiledata64 = Base64.base64Decode(pic64string);
			String picpath = this.commService.getDatadis("TPLJ").getName();
			String path = picpath + "\\";
			File f = new File(path);
			if (!f.exists() && !f.isDirectory())
				f.mkdir();
			path = path + "\\customer-picture";
			f = new File(path);
			if (!f.exists() && !f.isDirectory())
				f.mkdir();
			String picfilename = ValidateTime.getDayTimeAll();
			String filepath = "customer-picture/" + picfilename + ".jpg";
			path = path + "/" + picfilename + ".jpg";
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(bmpfiledata64);
			System.out.println("写入成功");
			fos.close();
			updatepic(this.exam_id,filepath);
			this.message="ok-ok";
		} catch (Exception ex) {

		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	private void updatepic(long examid,String picpath){
		StringBuffer sb = new StringBuffer("update exam_info set picture_path='"+picpath+"' where id = '" + examid + "'");
		Connection connection = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sb.toString());
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
	}
}