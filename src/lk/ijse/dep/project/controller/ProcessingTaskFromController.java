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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lk.ijse.dep.project.business.BOFactory;
import lk.ijse.dep.project.business.BOTypes;
import lk.ijse.dep.project.business.custom.IngredientBO;
import lk.ijse.dep.project.business.custom.TaskBO;
import lk.ijse.dep.project.dto.IngredientDTO;
import lk.ijse.dep.project.dto.TaskDTO;
import lk.ijse.dep.project.dto.TaskDTO2;
import lk.ijse.dep.project.util.IngredientTM;
import lk.ijse.dep.project.util.RecipeTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessingTaskFromController implements Initializable {
    public AnchorPane processingTaskForm;
    public JFXButton btnPrint;
    public ImageView imgAddUser;
    public JFXButton btnProcess;
    public JFXTimePicker txtStartTime;
    public JFXComboBox<String> cmbPendingTask;
    public JFXComboBox<String> cmbProcessingTaskId;
    public JFXTimePicker txtStopTime;
    public JFXButton btnFinish;
    public JFXTextField txtProducedQty;
    public TableView<IngredientTM> tblIngredients;
    public JFXTextField txtProductId;
    public JFXTextField txtIngredientDes;
    public JFXTextField txtQty;
    public JFXButton btnAdd;
    public JFXComboBox<String> cmbIngredientId;
    public JFXTextField txtHandQty;

    private List<IngredientDTO> tempIngs = new ArrayList<>();

    TaskBO taskBO = BOFactory.getInstance().getBO(BOTypes.TASK);
    IngredientBO ingredientBO = BOFactory.getInstance().getBO(BOTypes.INGREDIENT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        tblIngredients.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ingId"));
        tblIngredients.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("ingName"));
        tblIngredients.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ingQty"));


        btnAdd.setDisable(true);
        btnFinish.setDisable(true);

        loadPendingTaskIds();
        loadProcessingTaskIds();
        try {
            tempIngs = ingredientBO.getAllIngredients();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cmbProcessingTaskId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String taskId = cmbProcessingTaskId.getSelectionModel().getSelectedItem();
                if(taskId==null)
                    return;
                try {
                    TaskDTO taskDetail = taskBO.findTask(taskId);
                    txtProductId.setText(taskDetail.getProductId());
                    List<String> productIngIds = ingredientBO.getProductIngIds(taskDetail.getProductId());

                    ObservableList<String> ingredients = cmbIngredientId.getItems();
                    ingredients.addAll(productIngIds);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cmbIngredientId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String ingId = cmbIngredientId.getSelectionModel().getSelectedItem();
                btnAdd.setDisable(false);
                try {
                    IngredientDTO ing = ingredientBO.findIngrediet(ingId);
                    txtIngredientDes.setText(ing.getIngName());
                } catch (Exception e) {


                }


            }
        });

        tblIngredients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IngredientTM>() {
            @Override
            public void changed(ObservableValue<? extends IngredientTM> observable, IngredientTM oldValue, IngredientTM newValue) {
                IngredientTM selectedItem = tblIngredients.getSelectionModel().getSelectedItem();
                if(selectedItem==null)
                    return;
                txtQty.setText(selectedItem.getIngQty()+"");
                txtIngredientDes.setText(selectedItem.getIngName());
                cmbIngredientId.setValue(selectedItem.getIngId());
                btnAdd.setText("Update");
            }
        });


    }

    public void loadPendingTaskIds() {
        try {
            List<String> pendingTskIds = taskBO.getPendingTaskIds();
            ObservableList items = cmbPendingTask.getItems();
            items.clear();
            for (String pendingTskId : pendingTskIds) {
                items.add(pendingTskId);
            }
            cmbPendingTask.setItems(items);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadProcessingTaskIds() {
        try {
            List<String> pendingTskIds = taskBO.getProcessingTaskIds();
            ObservableList items = cmbProcessingTaskId.getItems();
            items.clear();
            for (String pendingTskId : pendingTskIds) {
                items.add(pendingTskId);
            }
            cmbProcessingTaskId.setItems(items);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void imgHome_OnMouseClicked(MouseEvent mouseEvent) {
        try {
            URL resource = this.getClass().getResource("/lk/ijse/dep/project/view/DashBoardForm.fxml");
            Parent root = null;
            root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) this.processingTaskForm.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, "Task is not started", e);

        }
    }

    public void btnPrint_OnAction(ActionEvent actionEvent) {
    }

    public void imgAddUser_OnMouseClicked(MouseEvent mouseEvent) {
    }

    public void btnProcess_OnAction(ActionEvent actionEvent) {
        try {
            String selectedItem = cmbPendingTask.getSelectionModel().getSelectedItem();
            boolean b = taskBO.updateTaskStarting(selectedItem);
            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Task is Started...").show();
                cmbProcessingTaskId.getItems().add(selectedItem);
                cmbPendingTask.getItems().remove(selectedItem);
                cmbPendingTask.getSelectionModel().clearSelection();

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, "Task is not finished", e);

        }

    }

    public void btnFinish_OnAction(ActionEvent actionEvent) {
        if  (!txtProducedQty.getText().matches("(\\d+[.]\\d+|(\\d+))")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid  Produced Quantity...", ButtonType.OK);
            alert.show();
            txtProducedQty.requestFocus();
            txtProducedQty.selectAll();
            return;
        }
        String selectedTask = cmbProcessingTaskId.getSelectionModel().getSelectedItem();

        try {
            ObservableList<IngredientTM> items = tblIngredients.getItems();
            List<IngredientDTO> list =new ArrayList<>();

            for (IngredientTM item : items) {
                list.add(new IngredientDTO(item.getIngId(),item.getIngName(),item.getIngQty()));
            }
            boolean b = taskBO.updateTaskFinishing(new TaskDTO2(selectedTask, Double.parseDouble(txtProducedQty.getText()),txtProductId.getText(),list));
            if(b){
                new Alert(Alert.AlertType.INFORMATION, "Task is finished successfully").show();
                cmbIngredientId.getSelectionModel().clearSelection();
                cmbProcessingTaskId.getItems().remove(selectedTask);
                txtQty.clear();
                txtProductId.clear();
                txtProducedQty.clear();
                txtIngredientDes.clear();
                btnAdd.setDisable(true);
                btnFinish.setDisable(true);
                tblIngredients.getItems().clear();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        btnFinish.setDisable(false);

        if  (!txtQty.getText().matches("(\\d+[.]\\d+|(\\d+))")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid  used Ingredient Quantity...", ButtonType.OK);
            alert.show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }
        String ingId = cmbIngredientId.getSelectionModel().getSelectedItem();
        String des = txtIngredientDes.getText();
        double qty = Double.parseDouble(txtQty.getText());
        ObservableList<IngredientTM> items = tblIngredients.getItems();

        if (btnAdd.getText().equals("Add")) {
            items.add(new IngredientTM(ingId, des, qty));
            tblIngredients.refresh();
            cmbIngredientId.getItems().remove(ingId);

        } else {
            IngredientTM selectedIng = tblIngredients.getSelectionModel().getSelectedItem();
            selectedIng.setIngQty(qty);
            tblIngredients.refresh();
            btnAdd.setText("Add");
        }

        txtQty.clear();
        txtIngredientDes.clear();
        cmbIngredientId.getSelectionModel().clearSelection();
        btnAdd.setDisable(true);
    }


    public void txtQty_OnAction(ActionEvent actionEvent) {
        btnAdd_OnAction(actionEvent);
    }
}
