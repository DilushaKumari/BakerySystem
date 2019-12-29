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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.project.business.BOFactory;
import lk.ijse.dep.project.business.BOTypes;
import lk.ijse.dep.project.business.custom.EmployeeBO;
import lk.ijse.dep.project.business.exception.AllReadyExistsEmpIdInEmployeeRegisterException;
import lk.ijse.dep.project.db.DBConnection;
import lk.ijse.dep.project.dto.EmployeeDTO;
import lk.ijse.dep.project.util.EmployeeTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeFormController implements Initializable {
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnPrint;
    public JFXTextField txtEmpPhoneNo;
    public TableView<EmployeeTM> tblEmployee;
    public JFXTextField txtSearch;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpId;
    public AnchorPane employeeForm;

    private EmployeeBO employeeBO = BOFactory.getInstance().getBO(BOTypes.EMPLOYEE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtEmpId.setDisable(true);
        txtEmpName.setDisable(true);
        txtEmpAddress.setDisable(true);
        txtEmpPhoneNo.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empId"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("empName"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("empContact"));
        tblEmployee.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("availability"));

        loadEmployees();

        tblEmployee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmployeeTM>() {
            @Override
            public void changed(ObservableValue<? extends EmployeeTM> observable, EmployeeTM oldValue, EmployeeTM newValue) {
                EmployeeTM selectedRow = tblEmployee.getSelectionModel().getSelectedItem();
                if (selectedRow == null) {
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    btnSave.setDisable(true);
                    return;
                }

                txtEmpName.setDisable(false);
                txtEmpAddress.setDisable(false);
                txtEmpPhoneNo.setDisable(false);
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                txtEmpId.setText(selectedRow.getEmpId());
                txtEmpName.setText(selectedRow.getEmpName());
                txtEmpAddress.setText(selectedRow.getEmpAddress());
                txtEmpPhoneNo.setText(selectedRow.getEmpContact());
                btnSave.setText("Update");

            }
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadEmployees();
            }
        });

    }


    public void imgHome_OnMouseClicked(MouseEvent mouseEvent) {

        try {
            URL resource = this.getClass().getResource("/lk/ijse/dep/project/view/DashBoardForm.fxml");
            Parent root = null;
            root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) this.employeeForm.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imgAddUser_OnMouseClicked(MouseEvent mouseEvent) {
        txtEmpId.clear();
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpPhoneNo.clear();
        btnSave.setText("Save");
        txtEmpName.setDisable(false);
        txtEmpAddress.setDisable(false);
        txtEmpPhoneNo.setDisable(false);
        btnSave.setDisable(false);

        int maxId = 0;

        try {
            String lastEmpId = employeeBO.getLastEmployeeId();

            if (lastEmpId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastEmpId.replace("E", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "E00" + maxId;
            } else if (maxId < 100) {
                id = "E0" + maxId;
            } else {
                id = "E" + maxId;
            }
            txtEmpId.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
        }


    }

    public void btnSave_OnAction(ActionEvent actionEvent) {

        if (!txtEmpName.getText().matches("^[A-Za-z][A-Za-z. ]+$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Employee Name");
            alert.show();
            txtEmpName.requestFocus();
            txtEmpName.selectAll();
            return;
        } else if (!txtEmpAddress.getText().matches("^\\w[ A-Za-z,.-/0-9\\\\]+$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Address");
            alert.show();
            txtEmpAddress.requestFocus();
            txtEmpAddress.selectAll();
            return;
        } else if (!txtEmpPhoneNo.getText().matches("(^\\d{3}-\\d{7})|(^[+]\\d{4}-\\d{7})")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Contact Number... Need this format (XXX-XXXXXXX OR +XXXX-XXXXXXX");
            alert.show();
            txtEmpPhoneNo.requestFocus();
            txtEmpPhoneNo.selectAll();
            return;

        }


        if (btnSave.getText().equals("Save")) {
            ObservableList<EmployeeTM> employees = tblEmployee.getItems();
            EmployeeDTO employee = new EmployeeDTO(txtEmpId.getText(), txtEmpName.getText(), txtEmpAddress.getText(),
                    txtEmpPhoneNo.getText(), "REG");

            try {
                employeeBO.saveEmployee(employee);
                employees.add(new EmployeeTM(employee.getEmpId(), employee.getEmpName(), employee.getEmpAddress(), employee.getEmpContact(), employee.getEmpAvailability()));

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, "When Save", e);
            }
        } else {
            EmployeeTM selectedRow = tblEmployee.getSelectionModel().getSelectedItem();

            try {
                employeeBO.updateEmployee(new EmployeeDTO(selectedRow.getEmpId(), txtEmpName.getText(), txtEmpAddress.getText(), txtEmpPhoneNo.getText(), selectedRow.getAvailability()));
                loadEmployees();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, "When Update", e);
            }
        }
        tblEmployee.getSelectionModel().clearSelection();
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpPhoneNo.clear();
        txtEmpId.clear();
        btnSave.setDisable(true);

    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Employee?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
            try {
                employeeBO.deleteEmployee(selectedItem.getEmpId());
                tblEmployee.getItems().remove(selectedItem);
                tblEmployee.getSelectionModel().clearSelection();

            } catch (AllReadyExistsEmpIdInEmployeeRegisterException e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
            }

            txtEmpName.clear();
            txtEmpAddress.clear();
            txtEmpPhoneNo.clear();
            txtEmpId.clear();
        }
    }

    public void btnPrint_OnAction(ActionEvent actionEvent) throws JRException {
        JasperReport mainJasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/project/Report/employee.jasper"));
        Map<String, Object> params = new HashMap<>();
        params.put("text", '%' + txtSearch.getText() + '%');
        JasperPrint jasperPrint = JasperFillManager.fillReport(mainJasperReport, params, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);

    }


    public void loadEmployees() {
        try {
            List<EmployeeDTO> allEmployees = employeeBO.findAllEmployeesBySearch("%" + txtSearch.getText() + "%");
            ObservableList<EmployeeTM> employees = tblEmployee.getItems();
            employees.clear();

            for (EmployeeDTO employee : allEmployees) {
                /*if (employee.getEmpId().contains(txtSearch.getText()) || employee.getEmpName().contains(txtSearch.getText()) ||
                        employee.getEmpAddress().contains(txtSearch.getText()) || employee.getEmpContact().contains(txtSearch.getText()) || employee.getEmpAvailability().contains(txtSearch.getText())) {
                    employees.add(new EmployeeTM(employee.getEmpId(), employee.getEmpName(), employee.getEmpAddress(), employee.getEmpContact(), employee.getEmpAvailability()));
                }*/
                employees.add(new EmployeeTM(employee.getEmpId(), employee.getEmpName(), employee.getEmpAddress(), employee.getEmpContact(), employee.getEmpAvailability()));

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong... Please Contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
        }
    }
}
