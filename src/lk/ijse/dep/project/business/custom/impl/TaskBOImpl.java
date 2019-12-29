package lk.ijse.dep.project.business.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.dep.project.business.custom.TaskBO;
import lk.ijse.dep.project.dao.DAOFactory;
import lk.ijse.dep.project.dao.DAOTypes;
import lk.ijse.dep.project.dao.custom.*;
import lk.ijse.dep.project.db.DBConnection;
import lk.ijse.dep.project.dto.IngredientDTO;
import lk.ijse.dep.project.dto.TaskDTO;
import lk.ijse.dep.project.dto.TaskDTO2;
import lk.ijse.dep.project.dto.TaskDTO3;
import lk.ijse.dep.project.entity.Ingredient;
import lk.ijse.dep.project.entity.Product;
import lk.ijse.dep.project.entity.Task;
import lk.ijse.dep.project.entity.TaskIngredient;

import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskBOImpl implements TaskBO {

    TaskDAO taskDAO = DAOFactory.getInstance().getDAO(DAOTypes.TASK);
    TaskIngredientDAO taskIngredientDAO = DAOFactory.getInstance().getDAO(DAOTypes.TASK_INGREDIENT);
    IngredientDAO ingredientDAO = DAOFactory.getInstance().getDAO(DAOTypes.INGREDIENT);
    ProductDAO productDAO = DAOFactory.getInstance().getDAO(DAOTypes.PRODUCT);
    EmployeeAssignDAO employeeAssignDAO = DAOFactory.getInstance().getDAO(DAOTypes.EMPLOYEE_ASSIGN);

    @Override
    public String getLastTaskId() throws Exception {
        return taskDAO.getLastTaskId();
    }

    @Override
    public boolean saveTask(TaskDTO task) throws Exception {
        return taskDAO.save(new Task(task.getTaskId(), task.getTaskDes(), "PE", new java.sql.Date(new java.util.Date().getTime()), null, null, task.getProductId(), task.getExpectQty(), 0));
    }

    @Override
    public List<TaskDTO> findTasksBySearch(String text, String taskCategory) throws Exception {
        List<Task> pendingTaskBySearch = taskDAO.getTaskBySearch(text, taskCategory);
        List<TaskDTO> taskList = new ArrayList<>();
        for (Task task : pendingTaskBySearch) {
            taskList.add(new TaskDTO(task.getTaskId(), task.getTaskDescription(), task.getTaskDate(), task.getProductId(), task.getExpectQty()));
        }
        return taskList;
    }

    @Override
    public List<TaskDTO3> loadAllTasks(String text) throws Exception {
        List<Task> taskBySearch = taskDAO.getAllTaskBySearch(text);
        List<TaskDTO3> taskList = new ArrayList<>();
        for (Task task : taskBySearch) {
            taskList.add(new TaskDTO3(task.getTaskId(), task.getTaskDescription(), task.getProductId(), task.getTaskStatus(),
                    task.getTaskDate(), task.getTaskStartTime(), task.getTaskEndTime()));
        }
        return taskList;
    }

    @Override
    public boolean updateTask(TaskDTO task) throws Exception {
        return taskDAO.update(new Task(task.getTaskId(), task.getTaskDes(), "PE", new java.sql.Date(new java.util.Date().getTime()), null, null, task.getProductId(), task.getExpectQty(), 0));

    }

    @Override
    public boolean updateTaskStarting(String taskId) throws Exception {
        return taskDAO.startTask(taskId);
    }

    @Override
    public boolean updateTaskFinishing(TaskDTO2 task) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            Date finishTime = new Date();

            Time time = new Time(finishTime.getTime());

            String taskId = task.getTaskId();
            boolean result = taskDAO.finishedTask(taskId, task.getQty(), finishTime);

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something happen when finished the task");
            }

            result = employeeAssignDAO.empReleasingWhenProcessFinished(taskId, time);

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something happen when update employee assigning");
            }
            for (IngredientDTO ing : task.getList()) {
                result = taskIngredientDAO.save(new TaskIngredient(taskId, ing.getIngId(),
                        ing.getIngQty()));

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something happen  when save task ingredient table");
                }
                Ingredient ingredient = ingredientDAO.find(ing.getIngId());
                ingredient.setIngQty(ingredient.getIngQty() - ing.getIngQty());
                result = ingredientDAO.update(ingredient);

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something happen when update the ingredient table");
                }
            }

            Product product = productDAO.find(task.getProductId());
            product.setProductQty(product.getProductQty() + task.getQty());
            result = productDAO.update(product);

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something happen  when update product  table");
            }

            connection.commit();
            return true;

        } catch (Throwable e) {

            try {
                connection.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public TaskDTO findTask(String taskId) throws Exception {
        Task task2 = taskDAO.find(taskId);
        return new TaskDTO(task2.getTaskId(), task2.getTaskDescription(), task2.getTaskDate(), task2.getProductId(), task2.getExpectQty());
    }

    @Override
    public boolean deletePendingTask(String taskId) throws Exception {
        return taskDAO.delete(taskId);
    }

    @Override
    public List<String> getPendingTaskIds() throws Exception {
        List<Task> all = taskDAO.getTaskBySearch('%' + "" + '%', "PE");
        List<String> list = new ArrayList<>();

        for (Task a : all) {
            list.add(a.getTaskId());

        }
        return list;
    }

    @Override
    public List<String> getProcessingTaskIds() throws Exception {
        List<Task> all = taskDAO.getTaskBySearch('%' + "" + '%', "PR");
        List<String> list = new ArrayList<>();

        for (Task a : all) {
            list.add(a.getTaskId());

        }
        return list;
    }
}
