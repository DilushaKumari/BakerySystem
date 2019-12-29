package lk.ijse.dep.project.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.project.business.BOFactory;
import lk.ijse.dep.project.business.BOTypes;
import lk.ijse.dep.project.business.custom.EmployeeBO;
import lk.ijse.dep.project.business.custom.TaskBO;
import lk.ijse.dep.project.db.DBConnection;
import lk.ijse.dep.project.dto.TaskDTO3;
import lk.ijse.dep.project.util.ViewTaskTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class ViewTaskFormController implements Initializable {
    public AnchorPane viewTaskForm;
    public JFXTextField txtSearch;
    public TableView<ViewTaskTM> tblViewTask;
    public JFXButton btnPrint;
    public JFXTextField txtTaskId;

    private TaskBO taskBO = BOFactory.getInstance().getBO(BOTypes.TASK);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblViewTask.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("taskId"));
        tblViewTask.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("des"));
        tblViewTask.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblViewTask.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
        tblViewTask.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblViewTask.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tblViewTask.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("endTime"));

        txtTaskId.setDisable(true);

        loadTask();

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTask();
                txtTaskId.clear();
            }
        });

        tblViewTask.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ViewTaskTM>() {
            @Override
            public void changed(ObservableValue<? extends ViewTaskTM> observable, ViewTaskTM oldValue, ViewTaskTM newValue) {
                ViewTaskTM selectedItem = tblViewTask.getSelectionModel().getSelectedItem();
                if (selectedItem == null)
                    return;
                txtTaskId.setText(selectedItem.getTaskId());

            }
        });
    }

    public void loadTask() {
        try {
            List<TaskDTO3> tasks = taskBO.loadAllTasks('%' + txtSearch.getText() + '%');
            ObservableList items = tblViewTask.getItems();
            items.clear();
            for (TaskDTO3 task : tasks) {
                items.add(new ViewTaskTM(task.getTaskId(), task.getDes(), task.getProductId(), task.getTaskStatus(), task.getDate(), task.getStartTime(), task.getEndTime()));
            }


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

            Stage primaryStage = (Stage) this.viewTaskForm.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnPrint_OnAction(ActionEvent actionEvent) throws JRException {

        JasperReport mainJasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/project/Report/task_main.jasper"));
        Map<String, Object> params = new HashMap<>();
        params.put("taskId", txtTaskId.getText());
        JasperPrint jasperPrint = JasperFillManager.fillReport(mainJasperReport, params, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);

        txtTaskId.setText("");
    }

    public void imgSearch_OnMouseClicked(MouseEvent mouseEvent) {
    }


}
