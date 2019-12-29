package lk.ijse.dep.project.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.project.business.BOFactory;
import lk.ijse.dep.project.business.BOTypes;
import lk.ijse.dep.project.business.custom.ProductBO;
import lk.ijse.dep.project.business.custom.TaskBO;
import lk.ijse.dep.project.business.exception.AllReadyExisitsRcpIdInProductException;
import lk.ijse.dep.project.dto.ProductDTO;
import lk.ijse.dep.project.dto.TaskDTO;
import lk.ijse.dep.project.util.RecipeTM;
import lk.ijse.dep.project.util.TaskAddTM;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskFormController implements Initializable {
    public AnchorPane taskForm;
    public JFXTextField txtTaskDes;
    public JFXTextField txtDate;
    public JFXButton btnDelete;
    public JFXComboBox<String> cmbProductId;
    public JFXTextField txtProductDes;
    public JFXButton btnAdd;
    public JFXTextField txtExpectedQty;
    public TableView<TaskAddTM> tblTask;
    public JFXTextField txtTaskId;
    public JFXTextField txtSearch;


    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    ProductBO productBO = BOFactory.getInstance().getBO(BOTypes.PRODUCT);
    TaskBO taskBO = BOFactory.getInstance().getBO(BOTypes.TASK);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Date curDate = new Date();
        txtDate.setText(dateFormatter.format(curDate));
        txtDate.setDisable(true);
        btnDelete.setDisable(true);
        btnAdd.setDisable(true);

        loadProductId();
        loadTable();
        txtSearch.requestFocus();

        tblTask.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("taskId"));
        tblTask.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("taskDes"));
        tblTask.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblTask.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("expectedQty"));



        cmbProductId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String selectedProdcutId = cmbProductId.getSelectionModel().getSelectedItem();
                if (selectedProdcutId == null)
                    return;
                try {
                    ProductDTO productDetails = productBO.getProductDetails(selectedProdcutId);
                    txtProductDes.setText(productDetails.getProductName());
                    btnAdd.setDisable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });

        tblTask.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TaskAddTM>() {
            @Override
            public void changed(ObservableValue<? extends TaskAddTM> observable, TaskAddTM oldValue, TaskAddTM newValue) {
                btnAdd.setText("Update");
                TaskAddTM selectedTask = tblTask.getSelectionModel().getSelectedItem();

                if (selectedTask == null) {
                    btnAdd.setText("Add");
                    return;
                }

                txtTaskId.setText(selectedTask.getTaskId());
                txtTaskId.setDisable(true);
                btnDelete.setDisable(false);
                txtTaskDes.setText(selectedTask.getTaskDes());
                txtExpectedQty.setText(selectedTask.getExpectedQty()+"");
                cmbProductId.setValue(selectedTask.getProductId());

            }
        });

    }

    public void loadProductId() {
        try {
            List<String> productIds = productBO.getProductId();
            ObservableList items = cmbProductId.getItems();
            items.clear();

            for (String productId : productIds) {
                items.add(productId);
            }
            cmbProductId.setItems(items);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadTable(){
        ObservableList tasks = tblTask.getItems();
        tasks.clear();
        try {
            List<TaskDTO> pendingTasksBySearch = taskBO.findTasksBySearch('%' + txtSearch.getText() + '%',"PE");
            for (TaskDTO task: pendingTasksBySearch) {
                tasks.add(new TaskAddTM(task.getTaskId(),task.getTaskDes(),task.getProductId(),task.getExpectQty()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, "When Load pending task Table", e);

        }
    }
    public void imgHome_OnMouseClicked(MouseEvent mouseEvent) {
        try {
            URL resource = this.getClass().getResource("/lk/ijse/dep/project/view/DashBoardForm.fxml");
            Parent root = null;
            root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) this.taskForm.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this task?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            String selectedItem = txtTaskId.getText();
            try {
                boolean b = taskBO.deletePendingTask(selectedItem);
                if(b){
                    new Alert(Alert.AlertType.INFORMATION,"Task is deleted Successfully...");
                }
                txtTaskId.clear();
                txtTaskDes.clear();
                txtExpectedQty.clear();
                cmbProductId.getSelectionModel().clearSelection();
                txtProductDes.clear();
                btnAdd.setDisable(true);
                btnDelete.setDisable(true);
                loadTable();

            } catch (AllReadyExisitsRcpIdInProductException e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
            }

        }
    }

    public void imgTask_OnMouseClicked(MouseEvent mouseEvent) {
        txtProductDes.clear();
        txtExpectedQty.clear();
        cmbProductId.getSelectionModel().clearSelection();
        txtTaskDes.clear();
        btnAdd.setDisable(true);

        int maxId = 0;

        try {
            String lastTaskId = taskBO.getLastTaskId();

            if (lastTaskId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastTaskId.replace("T", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "T00" + maxId;
            } else if (maxId < 100) {
                id = "T0" + maxId;
            } else {
                id = "T" + maxId;
            }
            txtTaskId.setText(id);
            txtTaskId.setDisable(true);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        if (!txtTaskDes.getText().matches("^.+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Task Description");
            alert.show();
            txtTaskDes.requestFocus();
            txtTaskDes.selectAll();
            return;
        }else if  (!txtExpectedQty.getText().matches("(\\d+[.]\\d+|(\\d+))")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid  Expected Quantity...", ButtonType.OK);
            alert.show();
            txtExpectedQty.requestFocus();
            txtExpectedQty.selectAll();
            return;
        }

        boolean flag =false;
        if (btnAdd.getText().equals("Add")){
            try {
                flag = taskBO.saveTask(new TaskDTO(txtTaskId.getText(), txtTaskDes.getText(), new java.sql.Date(new Date().getTime()), cmbProductId.getSelectionModel().getSelectedItem(), Double.parseDouble(txtExpectedQty.getText())));

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, "When Add", e);
            }

        }else {
            try {
               flag= taskBO.updateTask(new TaskDTO(txtTaskId.getText(), txtTaskDes.getText(), new java.sql.Date(new Date().getTime()), cmbProductId.getSelectionModel().getSelectedItem(), Double.parseDouble(txtExpectedQty.getText())));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, "When Update", e);
            }

        }

        String productId =cmbProductId.getSelectionModel().getSelectedItem();

        if(flag){
            if(btnAdd.getText().equals("Add")) {
                new Alert(Alert.AlertType.INFORMATION, "Successfully Added a task...").show();
                ObservableList items = tblTask.getItems();
                items.add(new TaskAddTM(txtTaskId.getText(),txtTaskDes.getText(),productId,Double.parseDouble(txtExpectedQty.getText())));
                tblTask.setItems(items);
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Successfully Updated a pending task...").show();
                TaskAddTM selectedTask = tblTask.getSelectionModel().getSelectedItem();
                selectedTask.setTaskDes(txtTaskDes.getText());
                selectedTask.setProductId(cmbProductId.getSelectionModel().getSelectedItem());
                selectedTask.setExpectedQty(Double.parseDouble(txtExpectedQty.getText()));
                tblTask.refresh();
            }

            tblTask.refresh();
            txtTaskDes.clear();
            txtTaskId.clear();
            txtProductDes.clear();
            txtExpectedQty.clear();
            cmbProductId.getSelectionModel().clearSelection();
            tblTask.getSelectionModel().clearSelection();
            btnAdd.setDisable(true);
            btnDelete.setDisable(true);
            txtTaskId.setDisable(false);
            txtSearch.requestFocus();
        }
    }


    public void txtExpectedQty_OnAction(ActionEvent actionEvent) {
        btnAdd_OnAction(actionEvent);
    }
}
