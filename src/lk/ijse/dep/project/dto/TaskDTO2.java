package lk.ijse.dep.project.dto;

import java.util.List;

public class TaskDTO2 {
  private   String taskId;
   private double qty;
   private String productId;
  private   List<IngredientDTO> list;

    public TaskDTO2() {
    }

    public TaskDTO2(String taskId, double qty, String productId, List<IngredientDTO> list) {
        this.setTaskId(taskId);
        this.setQty(qty);
        this.setProductId(productId);
        this.setList(list);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<IngredientDTO> getList() {
        return list;
    }

    public void setList(List<IngredientDTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "TaskDTO2{" +
                "taskId='" + taskId + '\'' +
                ", qty=" + qty +
                ", productId='" + productId + '\'' +
                ", list=" + list +
                '}';
    }
}
