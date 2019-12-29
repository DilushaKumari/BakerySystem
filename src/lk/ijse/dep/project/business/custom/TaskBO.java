package lk.ijse.dep.project.business.custom;

import lk.ijse.dep.project.business.SuperBO;
import lk.ijse.dep.project.dto.TaskDTO;
import lk.ijse.dep.project.dto.TaskDTO2;
import lk.ijse.dep.project.dto.TaskDTO3;

import java.util.List;

public interface TaskBO extends SuperBO {

    String getLastTaskId() throws Exception;

    boolean saveTask(TaskDTO task) throws Exception;

    List<TaskDTO> findTasksBySearch(String text, String taskCategory) throws Exception;

    List<TaskDTO3> loadAllTasks(String text) throws Exception;

    boolean updateTask(TaskDTO task) throws Exception;

    boolean updateTaskStarting(String taskId) throws Exception;

    boolean updateTaskFinishing(TaskDTO2 task) throws Exception;

    TaskDTO findTask(String taskId) throws Exception;

    boolean deletePendingTask(String taskId) throws Exception;

    List<String> getPendingTaskIds() throws Exception;

    List<String> getProcessingTaskIds() throws Exception;

}
