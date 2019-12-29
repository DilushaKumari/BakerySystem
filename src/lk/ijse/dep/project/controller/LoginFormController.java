package lk.ijse.dep.project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.deppo.crypto.DEPCrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class LoginFormController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Button btnLogin;

    public void btnLogin_OnAction(ActionEvent actionEvent) {


        try {
            Properties properties = new Properties();
            File file = new File("resources/application.properties");
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            properties.load(fis);
            fis.close();

            String userName = DEPCrypt.decode(properties.getProperty("project.loginUser"), "123");
            String password = DEPCrypt.decode(properties.getProperty("project.loginPassword"), "123");
            if (userName.equals(txtUserName.getText()) && password.equals(txtPassword.getText())) {
                Stage primaryStage = (Stage) txtUserName.getScene().getWindow();
                Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/project/view/DashBoardForm.fxml"));
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.centerOnScreen();
                primaryStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid User Name or Password", ButtonType.OK);
                alert.show();
                txtPassword.clear();
                txtUserName.requestFocus();
                txtUserName.selectAll();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void txtPassword_OnAction(ActionEvent actionEvent) {
        btnLogin_OnAction(actionEvent);
    }
}
