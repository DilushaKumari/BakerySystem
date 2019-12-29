package lk.ijse.dep.project.dao.custom;

import lk.ijse.dep.project.dao.CrudDAO;
import lk.ijse.dep.project.entity.Employee;
import lk.ijse.dep.project.entity.Task;

import java.util.Date;
import java.util.List;

public interface TaskDAO extends CrudDAO<Task,String> {
   boolean existsByProductId(String productId) throws Exception;
   String getLastTaskId() throws Exception;
   List<Task> getTaskBySearch(String text,String taskCategory) throws Exception;
   boolean startTask(String taskId) throws Exception;
   boolean finishedTask(String taskId, double proQty, Date finishTime) throws  Exception;
   List<Task> getAllTaskBySearch(String text) throws Exception;

}

