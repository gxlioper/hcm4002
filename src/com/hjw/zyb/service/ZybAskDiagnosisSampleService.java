package com.hjw.zyb.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.hjw.zyb.DTO.ZybAskDiagnosisSampleDTO;
import com.hjw.zyb.domain.ZybAskDiagnosisSample;
import com.hjw.zyb.model.ZybAskDiagnosisSampleModel;

public interface ZybAskDiagnosisSampleService {

	/**
	 * 查询问诊项目列表
	     * @Title: getZybAskDiagnosisSampleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybAskDiagnosisSampleDTO>      
	     * @throws
	 */
	public List<ZybAskDiagnosisSampleDTO> getZybAskDiagnosisSampleList(ZybAskDiagnosisSampleModel model) throws ServiceException;
	
	/**
	 * 通过ID查询问诊项目信息
	     * @Title: getZybAskDiagnosisSampleById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybAskDiagnosisSample      
	     * @throws
	 */
	public ZybAskDiagnosisSample getZybAskDiagnosisSampleById(long id) throws ServiceException;
	
	/**
	 * 新增问诊项目信息
	     * @Title: saveZybAskDiagnosisSample   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ZybAskDiagnosisSample
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybAskDiagnosisSample      
	     * @throws
	 */
	public ZybAskDiagnosisSample saveZybAskDiagnosisSample(ZybAskDiagnosisSample zybAskDiagnosisSample) throws ServiceException;
	
	/**
	 * 修改问诊项目信息
	     * @Title: updateZybAskDiagnosisSample   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zybAskDiagnosisSample
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybAskDiagnosisSample      
	     * @throws
	 */
	public ZybAskDiagnosisSample updateZybAskDiagnosisSample(ZybAskDiagnosisSample zybAskDiagnosisSample) throws ServiceException;
}
