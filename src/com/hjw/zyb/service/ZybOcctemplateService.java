package com.hjw.zyb.service;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybOcctemplateDTO;
import com.hjw.zyb.domain.ZybOcctemplate;

public interface ZybOcctemplateService {

	/*
	 * 职业病团报模板分页查询
	 */
	public PageReturnDTO queryByOccuphyexaclass(String exam_type_name, String occuphyexaclass_name, int rows,
			int page);

	public ZybOcctemplate queryByid(String id);

	public ZybOcctemplate updateOcctemplate(ZybOcctemplate c);

	public ZybOcctemplate addOcctemplate(ZybOcctemplate zybOcctemplate);

	public void deleteZybOcctemplate(ZybOcctemplate c);

	public ZybOcctemplateDTO queryByID(String id);

	public ZybOcctemplate queryByExam_typeAndOccuphyexaclassid(int exam_type, String occuphyexaclassid, String id);
}
