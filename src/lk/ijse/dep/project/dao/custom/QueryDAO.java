package lk.ijse.dep.project.dao.custom;

import lk.ijse.dep.project.dao.CrudDAO;
import lk.ijse.dep.project.dao.SuperDAO;
import lk.ijse.dep.project.dto.EmpAssignDTO;
import lk.ijse.dep.project.entity.CustomEntity;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<CustomEntity> gettingRcpIngList(String rcpId) throws Exception;
    List<String> gettingProductIngIdsList(String productId) throws Exception;
    List<CustomEntity> gettingAssignWork(String text) throws Exception;

}
