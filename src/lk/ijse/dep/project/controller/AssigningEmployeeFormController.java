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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.project.business.BOFactory;
import lk.ijse.dep.project.business.BOTypes;
import lk.ijse.dep.project.business.custom.EmployeeBO;
import lk.ijse.dep.project.business.custom.TaskBO;
import lk.ijse.dep.project.db.DBConnection;
import lk.ijse.dep.project.dto.EmpAssignDTO;
import lk.ijse.dep.project.dto.EmployeeDTO;
import lk.ijse.dep.project.util.EmpAssignTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AssigningEmployeeFormController implements Initializable {
    public JFXButton btnPrint;
    public JFXTimePicker txtStartTime;
    public JFXComboBox<String> cmbEmployeeId;
    public JFXComboBox<String> cmbProcessingTaskId;
    public JFXTimePicker txtStopTime;

    public TableView<EmpAssignTM> tblAssigning;
    public JFXButton btnAssign;
    public JFXTextField txtName;
    public JFXTextField txtDate;
    public JFXButton btnOut;
    public JFXButton btnRemove;
    public JFXButton btnIn;
    public AnchorPane assigningEmployeeForm;
    public JFXTextField txtSearch;

    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    EmployeeBO employeeBO = BOFactory.getInstance().getBO(BOTypes.EMPLOYEE);
    TaskBO taskBO = BOFactory.getInstance().getBO(BOTypes.TASK);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblAssigning.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empId"));
        tblAssigning.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("taskId"));
        tblAssigning.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblAssigning.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tblAssigning.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("endTime"));

        Date curDate = new Date();
        txtDate.setText(dateFormatter.format(curDate));
        txtDate.setDisable(true);

        btnIn.setDisable(true);
        btnOut.setDisable(true);
        btnAssign.setDisable(true);
        btnRemove.setDisable(true);

        loadEmployee();
        loadTaskId();
        loadTable();

        cmbEmployeeId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String selectedItem = cmbEmployeeId.getSelectionModel().getSelectedItem();
                if (selectedItem == null)
                    return;
                try {
                    EmployeeDTO employee = employeeBO.findEmployee(selectedItem);
                    txtName.setText(employee.getEmpName());
                    btnIn.setDisable(false);
                    btnOut.setDisable(false);
                    if (cmbProcessingTaskId.getSelectionModel().getSelectedItem() != null) {
                        btnAssign.setDisable(false);
                        btnRemove.setDisable(false);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        cmbProcessingTaskId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (cmbEmployeeId.getSelectionModel().getSelectedItem() != null) {
                    btnAssign.setDisable(false);
                    btnRemove.setDisable(false);
                }
            }
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadTable();
            }
        });

    }

    public void loadEmployee() {
        try {
            List<String> allEmployeeId = employeeBO.getAllEmployeeId();
            ObservableList empIds = cmbEmployeeId.getItems();
            empIds.clear();
            empIds.addAll(allEmployeeId);


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong when load employee ids, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, "When Load pending task Table", e);

        }

    }

    public void loadTaskId() {
        try {
            List<String> processingTaskId = taskBO.getProcessingTaskIds();
            ObservableList taskIDs = cmbProcessingTaskId.getItems();

            taskIDs.clear();
            taskIDs.addAll(processingTaskId);


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong when load task ids, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, "When Load pending task Table", e);

        }

    }


    public void imgHome_OnMouseClicked(MouseEvent mouseEvent) {
        try {
            URL resource = this.getClass().getResource("/lk/ijse/dep/project/view/DashBoardForm.fxml");
            Parent root = null;
            root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) this.assigningEmployeeForm.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnIn_OnAction(ActionEvent actionEvent) {
        String selectedEmp = cmbEmployeeId.getSelectionModel().getSelectedItem();
        boolean b = false;
        try {
            boolean check = employeeBO.checkEmpIn(selectedEmp);
            if (check) {
                new Alert(Alert.AlertType.INFORMATION, "Please mark your OUT before mark IN...").show();
                // return ;
            } else {
                b = employeeBO.markingEmpAttendanceIN(selectedEmp);
            }
            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Attendance is marked...").show();
                cmbEmployeeId.getSelectionModel().clearSelection();
                txtName.clear();
                btnIn.setDisable(true);
                btnOut.setDisable(true);
                btnRemove.setDisable(true);
                btnAssign.setDisable(true);

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong when marking  employee attendance, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, "When Load pending task Table", e);

        }
    }

    public void btnOut_OnAction(ActionEvent actionEvent) {
        String selectedEmp = cmbEmployeeId.getSelectionModel().getSelectedItem();
        boolean b = false;
        try {
            boolean check1 = employeeBO.checkEmpIn(selectedEmp);
            boolean check2 = employeeBO.checkEmpAssigningToOut(selectedEmp);
            if (!check1)
                new Alert(Alert.AlertType.INFORMATION, "Please mark IN ...").show();

            else if (check2)
                new Alert(Alert.AlertType.INFORMATION, "Please release your assigned work...").show();
            else {
                b = employeeBO.markingEmpAttendanceOUT(selectedEmp);
                if (b) {
                    new Alert(Alert.AlertType.INFORMATION, "Out is marked...").show();

                    cmbEmployeeId.getSelectionModel().clearSelection();
                    txtName.clear();
                    btnIn.setDisable(true);
                    btnOut.setDisable(true);
                    btnRemove.setDisable(true);
                    btnAssign.setDisable(true);
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong when marking  employee attendance, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, "When Load pending task Table", e);

        }
    }

    public void btnAssign_OnAction(ActionEvent actionEvent) {
        boolean b = false;
        String empId = cmbEmployeeId.getSelectionModel().getSelectedItem();
        String taskId = cmbProcessingTaskId.getSelectionModel().getSelectedItem();

        try {
            boolean check1 = employeeBO.checkEmpAvailability(empId);

            boolean check2 = employeeBO.checkEmpAssigningToOut(empId);

            if (!check1) {
                new Alert(Alert.AlertType.INFORMATION, "Please mark your attendance...").show();

            } else if (check2) {
                new Alert(Alert.AlertType.INFORMATION, "This employee is already assigned in a task...").show();
                // return ;
            } else {

                java.sql.Time startTime = Time.valueOf(txtStartTime.getValue());
                b = employeeBO.empAssigning(empId, taskId, startTime);
                new Alert(Alert.AlertType.INFORMATION, "Employee is assigned successfully...").show();
                loadTable();

            }
            cmbEmployeeId.getSelectionModel().clearSelection();
            txtName.clear();
            cmbProcessingTaskId.getSelectionModel().clearSelection();
            btnIn.setDisable(true);
            btnOut.setDisable(true);
            btnRemove.setDisable(true);
            btnAssign.setDisable(true);
            txtStartTime.getEditor().clear();


        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR, "Something went wrong when employee assigning, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, "When assigning employee", e);
        }

    }

    public void btnRemove_OnAction(ActionEvent actionEvent) {
        String empId = cmbEmployeeId.getSelectionModel().getSelectedItem();
        String taskId = cmbProcessingTaskId.getSelectionModel().getSelectedItem();

        try {

            java.sql.Time stopTime = Time.valueOf(txtStopTime.getValue());
            boolean b = employeeBO.empReleasing(empId, taskId, stopTime);
            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Employee is released successfully...").show();
                loadTable();
            }

            cmbEmployeeId.getSelectionModel().clearSelection();
            txtName.clear();
            cmbProcessingTaskId.getSelectionModel().clearSelection();
            btnIn.setDisable(true);
            btnOut.setDisable(true);
            btnRemove.setDisable(true);
            btnAssign.setDisable(true);
            txtStopTime.getEditor().clear();

        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR, "Something went wrong when employee releasing, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, "When releasing employee", e);
        }
    }

    public void loadTable() {
        ObservableList items = tblAssigning.getItems();
        items.clear();
        try {
            List<EmpAssignDTO> allEmpAssignWorks = employeeBO.getAllEmpAssignWorks('%' + txtSearch.getText() + '%');


            for (EmpAssignDTO all : allEmpAssignWorks) {

                items.add(new EmpAssignTM(all.getEmpId(), all.getTaskId(), all.getDate(), all.getStartTime(), all.getEndTime()));
            }
            tblAssigning.setItems(items);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
