package lk.ijse.dep.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.dep.project.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppRunning extends Application {


    public static void main(String[] args)  {
        launch(args);

        try {
            DBConnection.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Logger rootLogger = Logger.getLogger("");
        FileHandler fileHandler = new FileHandler("error.log", true);
        fileHandler.setFormatter(new SimpleFormatter());
        fileHandler.setLevel(Level.INFO);
        rootLogger.addHandler(fileHandler);

        DBConnection.getInstance().getConnection();
     //   URL resource = this.getClass().getResource("/lk/ijse/dep/project/view/DashBoardForm.fxml");
        URL resource = this.getClass().getResource("/lk/ijse/dep/project/view/LoginForm.fxml");
        Parent root = FXMLLoader.load(resource);


        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Bakery Management System");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();



    }
}
