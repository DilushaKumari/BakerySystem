package lk.ijse.dep.project.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lk.ijse.dep.project.business.BOFactory;
import lk.ijse.dep.project.business.BOTypes;
import lk.ijse.dep.project.business.custom.IngredientBO;
import lk.ijse.dep.project.business.custom.RecipeBO;
import lk.ijse.dep.project.business.exception.AllReadyExisitsRcpIdInProductException;
import lk.ijse.dep.project.db.DBConnection;
import lk.ijse.dep.project.dto.IngredientDTO;
import lk.ijse.dep.project.dto.RecipeDTO;
import lk.ijse.dep.project.dto.RecipeDTO2;
import lk.ijse.dep.project.dto.RecipeIngDTO;
import lk.ijse.dep.project.util.RecipeTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeFormController implements Initializable {
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnPrint;
    public JFXTextField txtRecipeDes;
    public JFXTextField txtDate;
    public TableView<RecipeTM> tblRecipe;
    public AnchorPane recipeForm;
    public JFXComboBox<String> cmbIngredient;
    public JFXTextField txtIngDes;
    public JFXButton btnAdd;
    public JFXComboBox<String> cmbRecipe;
    public JFXTextField txtQty;


    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    RecipeBO recipeBO = BOFactory.getInstance().getBO(BOTypes.RECIPE);
    IngredientBO ingredientBO = BOFactory.getInstance().getBO(BOTypes.INGREDIENT);
    boolean flag = false; // avoid null pointer exception when adding a new recipe

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Date curDate = new Date();
        txtDate.setText(dateFormatter.format(curDate));
        cmbIngredient.setDisable(true);
        txtIngDes.setDisable(true);
        txtQty.setDisable(true);
        btnAdd.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnPrint.setDisable(true);


        tblRecipe.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ingId"));
        tblRecipe.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("ingDes"));
        tblRecipe.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ingQty"));
        tblRecipe.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

        loadRecipeId();

        cmbRecipe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {

                    String selectedRcpId = cmbRecipe.getSelectionModel().getSelectedItem();

                    if (selectedRcpId == null)
                        return;

                    loadIngredientData(selectedRcpId);
                    tblRecipe.setDisable(false);

                    txtDate.setDisable(true);
                    txtRecipeDes.setDisable(false);


                    if (flag == false) {
                        btnSave.setText("Update");
                        btnSave.setDisable(false);
                        RecipeDTO2 recipe = recipeBO.findRecipe(selectedRcpId);
                        txtRecipeDes.setText(recipe.getRcpDes());
                        txtDate.setText(dateFormatter.format(recipe.getRcpDate()));
                        btnDelete.setDisable(false);
                        btnPrint.setDisable(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cmbIngredient.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    String selectedIngId = cmbIngredient.getSelectionModel().getSelectedItem();
                    if (selectedIngId == null) {
                        txtIngDes.setText("");
                        return;
                    }
                    IngredientDTO ingrediet = ingredientBO.findIngrediet(selectedIngId);

                    txtIngDes.setText(ingrediet.getIngName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                checkButtonAdd();

            }
        });

        tblRecipe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RecipeTM>() {
            @Override
            public void changed(ObservableValue<? extends RecipeTM> observable, RecipeTM oldValue, RecipeTM newValue) {

                RecipeTM selectedItem = tblRecipe.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    btnAdd.setText("Add");
                    return;
                }

                cmbIngredient.setValue(selectedItem.getIngId());
                txtQty.setText(selectedItem.getIngQty() + "");
                btnAdd.setText("Update");

            }
        });
    }

    private void checkButtonAdd() {
        if (cmbIngredient.getSelectionModel().getSelectedIndex() >= 0)
            btnAdd.setDisable(false);
    }


    public void btnSave_OnAction(ActionEvent actionEvent) {

        if (!txtRecipeDes.getText().matches("^.+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Recipe Description");
            alert.show();
            txtRecipeDes.requestFocus();
            txtRecipeDes.selectAll();
            return;
        }

        String rcpId = cmbRecipe.getSelectionModel().getSelectedItem();
        boolean status = false;
        if (btnSave.getText().equals("Save")) {
            List<RecipeIngDTO> recipeIngDetails = new ArrayList<>();
            for (RecipeTM ing : tblRecipe.getItems()) {
                recipeIngDetails.add(new RecipeIngDTO(rcpId, ing.getIngId(), ing.getIngQty()));
            }

            RecipeDTO recipe = new RecipeDTO(rcpId, txtRecipeDes.getText(), new java.sql.Date(new Date().getTime()), recipeIngDetails);
            try {
                status = recipeBO.saveRecipe(recipe);

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, "When Save", e);
            }
        } else {
            try {
                status = recipeBO.updateRecipe(new RecipeDTO2(rcpId, txtRecipeDes.getText(), new java.sql.Date(dateFormatter.parse(txtDate.getText()).getTime())));

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, "When Update", e);
            }
        }
        if (status) {
            if (btnSave.getText().equals("Save"))
                new Alert(Alert.AlertType.INFORMATION, "New Recipe added Successfully...").show();
            else
                new Alert(Alert.AlertType.INFORMATION, "Recipe updated Successfully...").show();

            txtRecipeDes.setDisable(true);
            cmbIngredient.setDisable(true);
            txtIngDes.setDisable(true);
            txtQty.setDisable(true);
            btnAdd.setDisable(true);
            btnDelete.setDisable(true);
            btnSave.setDisable(true);
            btnPrint.setDisable(true);
            txtQty.clear();
            txtRecipeDes.clear();
            btnSave.setText("Save");
            cmbIngredient.getSelectionModel().clearSelection();
            cmbRecipe.getSelectionModel().clearSelection();
            tblRecipe.getItems().clear();
            loadRecipeId();
            flag = false;
            cmbRecipe.setDisable(false);
        }
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Recipe?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            String selectedItem = cmbRecipe.getSelectionModel().getSelectedItem();
            try {
                recipeBO.deleteRecipe(selectedItem);
                cmbRecipe.getSelectionModel().clearSelection();
                txtRecipeDes.clear();
                loadRecipeId();
                tblRecipe.getItems().clear();

            } catch (AllReadyExisitsRcpIdInProductException e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
            }

        }
    }

    public void btnPrint_OnAction(ActionEvent actionEvent) throws JRException {
        JasperReport mainJasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/project/Report/recipe.jasper"));
        Map<String, Object> params = new HashMap<>();
        params.put("rcpId", cmbRecipe.getSelectionModel().getSelectedItem());
        JasperPrint jasperPrint = JasperFillManager.fillReport(mainJasperReport, params, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void imgAdd_OnMouseClicked(MouseEvent mouseEvent) {

        flag = true;
        cmbIngredient.getSelectionModel().clearSelection();
        cmbRecipe.getSelectionModel().clearSelection();
        btnSave.setText("Save");
        btnDelete.setDisable(true);
        btnPrint.setDisable(true);
        txtDate.setDisable(false);
        txtRecipeDes.setDisable(false);
        cmbIngredient.setDisable(false);
        txtQty.setDisable(false);
        tblRecipe.setDisable(false);
        txtRecipeDes.clear();
        txtQty.clear();
        txtIngDes.clear();

        int maxId = 0;

        try {
            String lstRcpId = recipeBO.getLastRecipeId();

            if (lstRcpId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lstRcpId.replace("R", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "R00" + maxId;
            } else if (maxId < 100) {
                id = "R0" + maxId;
            } else {
                id = "R" + maxId;
            }

            // Loading Recipe Id
            List<String> allRecipeIds = recipeBO.findAllRecipeIds();
            ObservableList<String> recipeItems = cmbRecipe.getItems();
            recipeItems.clear();

            recipeItems.setAll(allRecipeIds);
            recipeItems.add(id);
            cmbRecipe.setItems(recipeItems);
            cmbRecipe.setValue(id);
            cmbRecipe.setDisable(true);

            // Loading Ingredients Id

            List<String> allIngredientsIds = ingredientBO.getAllIngredientsIds();
            ObservableList ingIds = cmbIngredient.getItems();
            ingIds.clear();
            ingIds.setAll(allIngredientsIds);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
        }

    }

    public void imgHome_OnMouseClicked(MouseEvent mouseEvent) {
        try {
            URL resource = this.getClass().getResource("/lk/ijse/dep/project/view/DashBoardForm.fxml");
            Parent root = null;
            root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) this.recipeForm.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {

        if (!txtQty.getText().matches("(\\d+[.]\\d+|(\\d+))")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid  Ingredient Quantity...", ButtonType.OK);
            alert.show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }
        String ingId = cmbIngredient.getSelectionModel().getSelectedItem();
        String des = txtIngDes.getText();
        double qty = Double.parseDouble(txtQty.getText());
        ObservableList<RecipeTM> items = tblRecipe.getItems();
        btnSave.setDisable(false);
        tblRecipe.setDisable(false);


        boolean flag = false;

        if (btnAdd.getText().equals("Add")) {
            for (RecipeTM item : items) {
                if (item.getIngId().equals(ingId)) {
                    item.setIngQty(item.getIngQty() + qty);
                    tblRecipe.refresh();
                    flag = true;
                    txtQty.clear();
                    cmbIngredient.getSelectionModel().clearSelection();
                }
                if (flag == true)
                    return;
            }

            Button btn = new Button("Delete");
            btn.setId(ingId);
            btn.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
            btn.setOnAction(event ->
            {

                for (int i = 0; i < tblRecipe.getItems().size(); i++) {
                    if (tblRecipe.getItems().get(i).getIngId().equals(btn.getId())) {
                        items.remove(i);
                    }
                }
                if (tblRecipe.getItems().size() > 0)
                    btnSave.setDisable(false);
                else
                    btnSave.setDisable(true);

            });
            items.add(new RecipeTM(ingId, des, qty, btn));
            tblRecipe.refresh();

        } else {
            RecipeTM selectedIng = tblRecipe.getSelectionModel().getSelectedItem();
            selectedIng.setIngQty(qty);
            tblRecipe.refresh();
            btnAdd.setText("Add");
        }

        txtQty.clear();
        txtIngDes.clear();
        cmbIngredient.getSelectionModel().clearSelection();

    }

    void loadRecipeId() {
        try {
            List<String> allRecipeIds = recipeBO.findAllRecipeIds();
            ObservableList<String> recipeIds = cmbRecipe.getItems();
            recipeIds.clear();
            recipeIds.setAll(allRecipeIds);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void txtQty_OnAction(ActionEvent actionEvent) {
        btnAdd_OnAction(actionEvent);
    }

    public void loadIngredientData(String rcpId) {
        try {
            List<IngredientDTO> allIngForRcpId = ingredientBO.getAllIngForRcpId(rcpId);
            if (allIngForRcpId == null) {
                tblRecipe.setDisable(false);
                return;
            }
            ObservableList<RecipeTM> items = tblRecipe.getItems();
            items.clear();
            for (IngredientDTO ingredient : allIngForRcpId) {
                items.add(new RecipeTM(ingredient.getIngId(), ingredient.getIngName(), ingredient.getIngQty(), null));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
