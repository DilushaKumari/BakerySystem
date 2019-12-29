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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.project.business.BOFactory;
import lk.ijse.dep.project.business.BOTypes;
import lk.ijse.dep.project.business.custom.IngredientBO;
import lk.ijse.dep.project.business.exception.AllReadyExistsIngIdInRecipeException;
import lk.ijse.dep.project.db.DBConnection;
import lk.ijse.dep.project.dto.IngredientDTO;
import lk.ijse.dep.project.util.IngredientTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class IngredientFormController implements Initializable {


    public AnchorPane ingredientForm;
    public JFXTextField txtDescription;
    public JFXTextField txtQty;
    public JFXTextField txtIngredient;
    public JFXTextField txtSearch;
    public TableView<IngredientTM> tblIngredient;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnPrint;
    public ImageView imgAdd;
    public Tooltip ttQty;
    IngredientBO ingredientBO = BOFactory.getInstance().getBO(BOTypes.INGREDIENT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtIngredient.setDisable(true);
        txtDescription.setDisable(true);
        txtQty.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        ttQty.setText("Enter Qty kg or litre");
        txtQty.setTooltip(ttQty);

        tblIngredient.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ingId"));
        tblIngredient.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("ingName"));
        tblIngredient.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ingQty"));

        loadIngredients();

        tblIngredient.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IngredientTM>() {
            @Override
            public void changed(ObservableValue<? extends IngredientTM> observable, IngredientTM oldValue, IngredientTM newValue) {
                IngredientTM selectedRow = tblIngredient.getSelectionModel().getSelectedItem();

                if (selectedRow == null) {
                    btnSave.setText("Save");
                    btnSave.setDisable(true);
                    btnDelete.setDisable(true);
                    return;
                }

                txtDescription.setDisable(false);
                txtQty.setDisable(false);
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                txtIngredient.setText(selectedRow.getIngId());
                txtDescription.setText(selectedRow.getIngName());
                txtQty.setText(selectedRow.getIngQty() + "");

                btnSave.setText("Update");
            }
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadIngredients();
            }
        });
    }

    public void imgHome_OnMouseClicked(MouseEvent mouseEvent) {
        try {
            URL resource = this.getClass().getResource("/lk/ijse/dep/project/view/DashBoardForm.fxml");
            Parent root = null;
            root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) this.ingredientForm.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        if (!txtDescription.getText().matches("^\\w.+$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Description");
            alert.show();
            txtDescription.requestFocus();
            txtDescription.selectAll();
            return;
        } else if (!txtQty.getText().matches("(\\d+[.]\\d+|(\\d+))")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Ingredient Quantity...", ButtonType.OK);
            alert.show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        if (btnSave.getText().equals("Save")) {
            ObservableList<IngredientTM> ingredients = tblIngredient.getItems();
            IngredientDTO newIngredient = new IngredientDTO(txtIngredient.getText(), txtDescription.getText(), Double.parseDouble(txtQty.getText()));

            try {
                ingredientBO.saveIngredient(newIngredient);
                ingredients.add(new IngredientTM(newIngredient.getIngId(), newIngredient.getIngName(), newIngredient.getIngQty()));
                tblIngredient.refresh();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, "When Save", e);
            }
        } else {

            IngredientTM selectedRow = tblIngredient.getSelectionModel().getSelectedItem();

            try {
                ingredientBO.updateIngredient(new IngredientDTO(selectedRow.getIngId(), txtDescription.getText(), Double.parseDouble(txtQty.getText())));
                loadIngredients();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, "When Update", e);
            }
        }
        tblIngredient.refresh();
        tblIngredient.getSelectionModel().clearSelection();
        txtQty.clear();
        txtDescription.clear();
        txtIngredient.clear();
        btnSave.setDisable(true);
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Ingredient?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            IngredientTM selectedItem = tblIngredient.getSelectionModel().getSelectedItem();
            try {
                ingredientBO.deleteIngredient(selectedItem.getIngId());
                tblIngredient.getItems().remove(selectedItem);
                tblIngredient.getSelectionModel().clearSelection();

            } catch (AllReadyExistsIngIdInRecipeException e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
            }

            txtIngredient.clear();
            txtQty.clear();
            txtDescription.clear();
        }
    }

    public void btnPrint_OnAction(ActionEvent actionEvent) throws JRException {
        JasperReport mainJasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/project/Report/ingredient.jasper"));
        Map<String, Object> params = new HashMap<>();
        params.put("text", '%' + txtSearch.getText() + '%');
        JasperPrint jasperPrint = JasperFillManager.fillReport(mainJasperReport, params, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void imgAdd_OnMouseClicked(MouseEvent mouseEvent) {
        txtIngredient.clear();
        txtDescription.clear();
        txtQty.clear();
        btnSave.setText("Save");
        txtDescription.setDisable(false);
        txtQty.setDisable(false);
        btnSave.setDisable(false);


        int maxId = 0;

        try {
            String lstIngId = ingredientBO.getLastIngredientId();

            if (lstIngId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lstIngId.replace("I", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            txtIngredient.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
        }

    }


    public void loadIngredients() {
        try {
            List<IngredientDTO> allIngredients = ingredientBO.findAllIngredientsBySearch("%" + txtSearch.getText() + "%");
            ObservableList<IngredientTM> ingredients = tblIngredient.getItems();
            ingredients.clear();

            for (IngredientDTO ingredient : allIngredients) {
                ingredients.add(new IngredientTM(ingredient.getIngId(), ingredient.getIngName(), ingredient.getIngQty()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong... Please Contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
        }
    }

}
