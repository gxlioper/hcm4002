package com.hjw.zyb.service;

import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.ExamSetModel;
import com.hjw.zyb.DTO.SetOccuhazardfactorsDTO;
import com.synjones.framework.exception.ServiceException;

import java.util.List;

public interface SetOccuhazardfactorsService {
    /**
     * 获取套餐和危害关系
     * @param exam_set_id
     * @return
     * @throws ServiceException
     */
    public List<SetOccuhazardfactorsDTO> getSetOccuhazardfactorsDTOList(long exam_set_id) throws ServiceException;

    /**
     * 保存套餐和危害关系
     * @param model
     * @param user
     * @return
     * @throws ServiceException
     */
    public String saveExamSetHazard(ExamSetModel model, UserDTO user) throws ServiceException;
}
