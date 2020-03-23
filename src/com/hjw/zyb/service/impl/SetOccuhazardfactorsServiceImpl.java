package com.hjw.zyb.service.impl;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.StringUtil;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.ExamSetModel;
import com.hjw.zyb.DTO.SetOccuhazardfactorsDTO;
import com.hjw.zyb.domain.SetOccuhazardfactors;
import com.hjw.zyb.service.SetOccuhazardfactorsService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import net.sf.json.JSONArray;
import org.springframework.util.StringUtils;

import java.util.List;

public class SetOccuhazardfactorsServiceImpl implements SetOccuhazardfactorsService {

    private QueryManager qm;
    private JdbcQueryManager jqm;
    private JdbcPersistenceManager jpm;
    private PersistenceManager pm;

    public void setQueryManager(QueryManager qm) {
        this.qm = qm;
    }

    public void setJdbcQueryManager(JdbcQueryManager jqm) {
        this.jqm = jqm;
    }

    public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
        this.jpm = jpm;
    }

    public void setPersistenceManager(PersistenceManager pm) {
        this.pm = pm;
    }

    @Override
    public List<SetOccuhazardfactorsDTO> getSetOccuhazardfactorsDTOList(long exam_set_id) throws ServiceException {
        String sql = "SELECT olcass.occuphyexaclass_name,oh.hazard_name,s.id,s.set_id,s.hazard_code,s.occuphyexaclassID,s.hazard_year,s.creater,s.create_time " +
                "   FROM set_occuhazardfactors s,zyb_occuhazardfactors oh,zyb_occuphyexaclass olcass    " +
                "   WHERE  s.hazard_code = oh.hazard_code AND s.occuphyexaclassID = olcass.occuphyexaclassID   " +
                "   AND s.set_id = "+exam_set_id+" order by  s.id ";
        List<SetOccuhazardfactorsDTO> li = this.jqm.getList(sql,SetOccuhazardfactorsDTO.class);
        return li;
    }

    @Override
    public String saveExamSetHazard(ExamSetModel model, UserDTO user) throws ServiceException {
        String msg = "";
        JSONArray jsonArray = JSONArray.fromObject(model.getHazard_list());
        List<SetOccuhazardfactors> list = JSONArray.toList(jsonArray, SetOccuhazardfactors.class);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getHazard_code() == list.get(i + 1).getHazard_code() &&
                    list.get(i).getOccuphyexaclassID() == list.get(i + 1).getOccuphyexaclassID()) {
                msg = "error-有相同的记录存在";
                break;
            }
        }
        if (StringUtils.isEmpty(msg)) {
            if (model.getSet_id() > 0) {
                String hql = "DELETE FROM set_occuhazardfactors WHERE set_id=" + model.getSet_id();
                this.jpm.executeSql(hql);
//            List<SetOccuhazardfactors> li = this.qm.find(hql);
//            for(SetOccuhazardfactors occ : li){
//                pm.remove(occ);
//            }
            }

            SetOccuhazardfactors s = new SetOccuhazardfactors();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setCreate_time(DateTimeUtil.parse());
                list.get(i).setCreater(user.getUserid());
                list.get(i).setSet_id(model.getSet_id());
                this.pm.save(list.get(i));
            }
            msg = "ok-保存成功";
        }
        return msg;
    }
}
