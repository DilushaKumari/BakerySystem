package lk.ijse.dep.project.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.project.db.DBConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Singhabahu-PC on 8/19/2019.
 */

public class DashBoardFormController implements Initializable {

    public Label lblMainText;
    public Label lblSubText;
    public JFXButton btnBackup;
    public JFXButton btnRestore;
    public JFXButton btnLogout;
    @FXML
    private AnchorPane mainWindow;

    @FXML
    private ImageView imgAssign;

    @FXML
    private ImageView imgRecipe;

    @FXML
    private ImageView imgEmployee;

    @FXML
    private ImageView imgIngredient;

    @FXML
    private ImageView imgTask;

    @FXML
    private ImageView imgProcessTask;

    @FXML
    private ImageView imgViewTask;

    @FXML
    private ImageView imgProduct;
    public ProgressIndicator pgb;


    public void initialize(URL url, ResourceBundle rb) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), mainWindow);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        pgb.setVisible(false);
    }


    public void playExitAction(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblSubText.setText("");
            lblMainText.setText("");
        }
    }

    public void playEnterAction(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgEmployee":
                    lblMainText.setText("MANAGE EMPLOYEE");
                    lblSubText.setText("Able to add, edit, delete, search Employee");
                    break;
                case "imgIngredient":
                    lblMainText.setText("MANAGE INGREDIENTS");
                    lblSubText.setText("Able to add, edit, delete, search ingredients");
                    break;
                case "imgRecipe":
                    lblMainText.setText("MANAGE RECIPE");
                    lblSubText.setText("Able to add,delete, search recipe");
                    break;
                case "imgAssign":
                    lblMainText.setText("ASSIGN EMPLOYEE");
                    lblSubText.setText("Able to assign employee for a task and marking employee attendance");
                    break;
                case "imgTask":
                    lblMainText.setText("MANAGE TASK");
                    lblSubText.setText("Able to add a task and view pending task ");
                    break;
                case "imgViewTask":
                    lblMainText.setText("VIEW TASK");
                    lblSubText.setText("Able to view all the tasks ");
                    break;
                case "imgProduct":
                    lblMainText.setText("MANAGE PRODUCT");
                    lblSubText.setText("Able to add,delete, search Product");
                    break;
                case "imgProcessTask":
                    lblMainText.setText("PROCESS TASK");
                    lblSubText.setText("Able to process pending task and finish the task");
                    break;
            }


            if (mouseEvent.getSource() instanceof ImageView) {
                ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
                scaleT.setToX(1.2);
                scaleT.setToY(1.2);
                scaleT.play();


                DropShadow glow = new DropShadow();
                glow.setColor(Color.CORNFLOWERBLUE);
                glow.setWidth(15);
                glow.setHeight(15);
                glow.setRadius(15);
                icon.setEffect(glow);
            }
        }
    }

    public void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;

            switch (icon.getId()) {

                case "imgEmployee":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/project/view/EmployeeForm.fxml"));
                    break;
                case "imgIngredient":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/project/view/IngredientForm.fxml"));
                    break;
                case "imgRecipe":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/project/view/RecipeForm.fxml"));
                    break;
                case "imgAssign":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/project/view/AssigningEmployeeForm.fxml"));
                    break;
                case "imgTask":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/project/view/TaskForm.fxml"));
                    break;
                case "imgViewTask":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/project/view/ViewTaskForm.fxml"));
                    break;
                case "imgProduct":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/project/view/ProductForm.fxml"));
                    break;
                case "imgProcessTask":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/project/view/ProcessingTaskForm.fxml"));
                    break;
                case "imgExit":
                    root = FXMLLoader.load(this.getClass().getResource("/View/LoginForm.fxml"));
                    break;
            }
            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.mainWindow.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }


    }

    public void btnBackup_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the DB Backup");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        File file = fileChooser.showSaveDialog(this.mainWindow.getScene().getWindow());

        if (file != null) {
            Task task =  new Task<Void>(){

                @Override
                protected Void call() throws Exception {
                    Process process = Runtime.getRuntime().exec("mysqldump -h" + DBConnection.host +" --port "+DBConnection.port+ " -u" + DBConnection.username + " -p" + DBConnection.password + " " + DBConnection.database + " --result-file " + file.getAbsolutePath() + ((file.getAbsolutePath().endsWith(".sql")) ? "" : ".sql"));
                    int exitCode = process.waitFor();

                    if(exitCode!=0){
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        br.lines().forEach(System.out::println);
                        br.close();
                        throw new RuntimeException("Backup is not work");
                    }else{
                        return null;
                    }



                }
            };
            task.setOnSucceeded(event -> {
                new Alert(Alert.AlertType.INFORMATION,"Backup Process has been success");
                this.mainWindow.setCursor(Cursor.DEFAULT);
                pgb.setVisible(false);

            });
            task.setOnFailed(event -> {
                pgb.setVisible(false);
                this.mainWindow.setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.ERROR,"Failed to Backup the backup");
            });

            new Thread(task).start();

        }
    }


    public void btnRestore_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Let's restore the backup");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        File file = fileChooser.showOpenDialog(this.mainWindow.getScene().getWindow());
        if (file != null) {
            String[] commands;
            if(DBConnection.password.length()>0) {
                commands = new String[]{"mysql", "-h", DBConnection.host,"--port", DBConnection.port,"-u", DBConnection.username, "-p" + DBConnection.password, DBConnection.database,
                        "-e", "source " + file.getAbsolutePath()}; // need a space after "source "}
            }else{
                commands = new String[]{"mysql", "-h", DBConnection.host,"--port", DBConnection.port, "-u", DBConnection.username,  DBConnection.database,
                        "-e", "source " + file.getAbsolutePath()}; // need a space after "source "}
            } this.mainWindow.getScene().setCursor(Cursor.WAIT);
            pgb.setVisible(true);

            Task task =  new Task<Void>(){

                @Override
                protected Void call() throws Exception {
                    Process process = Runtime.getRuntime().exec(commands);
                    int exitCode = process.waitFor();

                    if(exitCode!=0){
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                        br.lines().forEach(System.out::println);
                        br.close();
                        throw new RuntimeException("Wade Kachal");
                    }else{
                        return null;
                    }



                }
            };

            task.setOnSucceeded(event -> {
                this.mainWindow.setCursor(Cursor.DEFAULT);
                pgb.setVisible(false);
                new Alert(Alert.AlertType.INFORMATION,"Restore Process has been success");
            });
            task.setOnFailed(event -> {
                pgb.setVisible(false);
                this.mainWindow.setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.ERROR,"Failed to restore the backup");
            });

            new Thread(task).start();
        }
    }

    public void btnLogout_OnAction(ActionEvent actionEvent) {
        try {
            Parent   root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/project/view/LoginForm.fxml"));
            Scene subScene = new Scene(root);
            Stage primaryStage = (Stage) this.mainWindow.getScene().getWindow();
            primaryStage.setScene(subScene);
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}