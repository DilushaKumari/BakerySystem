package lk.ijse.dep.project.dao.custom.impl;

import lk.ijse.dep.project.dao.CrudUtil;
import lk.ijse.dep.project.dao.custom.QueryDAO;
import lk.ijse.dep.project.entity.CustomEntity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<CustomEntity> gettingRcpIngList(String rcpId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT  ri.ingId,i.ingName,ri.ingQty FROM  recipeingredient ri JOIN  ingredient i ON ri.ingId = i.ingId\n" +
                "WHERE rcpId =?", rcpId);
        List<CustomEntity> ingList = new ArrayList<>();

        while (rst.next()) {
            ingList.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getDouble(3)));
        }
        return ingList;
    }

    @Override
    public List<String> gettingProductIngIdsList(String productId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT ri.ingId FROM recipeingredient ri JOIN recipe ON ri.rcpId = recipe.rcpId JOIN product p on recipe.rcpId = p.rcpId  WHERE productId=?", productId);
        List<String> ingList = new ArrayList<>();

        while (rst.next()) {
            ingList.add(rst.getString(1));

        }return ingList;
    }

    @Override
    public List<CustomEntity> gettingAssignWork(String text) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT ea.empId,ea.taskId,t.taskDate,ea.startTime,ea.finishTime FROM employeeAssign ea JOIN task t on ea.taskId = t.taskId\n" +
                "    WHERE   ea.empId LIKE ? OR ea.taskId LIKE ? OR t.taskDate LIKE ?", text,text,text);
        List<CustomEntity> assignList = new ArrayList<>();

        while (rst.next()) {
            assignList.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getDate(3),rst.getTime(4),rst.getTime(5)));
        }
        return assignList;
    }


}