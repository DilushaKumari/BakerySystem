package lk.ijse.dep.project.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.project.business.BOFactory;
import lk.ijse.dep.project.business.BOTypes;
import lk.ijse.dep.project.business.custom.ProductBO;
import lk.ijse.dep.project.business.custom.RecipeBO;
import lk.ijse.dep.project.business.exception.AllReadyExistsProductIdInTaskException;
import lk.ijse.dep.project.db.DBConnection;
import lk.ijse.dep.project.dto.ProductDTO;
import lk.ijse.dep.project.dto.RecipeDTO2;
import lk.ijse.dep.project.util.ProductTM;
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

public class ProductFormController implements Initializable {

    public JFXTextField txtName;
    public JFXComboBox<String> cmbRecipeId;
    public JFXTextField txtRcpDes;
    public TableView<ProductTM> tblProduct;

    ProductBO productBO = BOFactory.getInstance().getBO(BOTypes.PRODUCT);
    RecipeBO recipeBO = BOFactory.getInstance().getBO(BOTypes.RECIPE);

    @FXML
    private AnchorPane productForm;


    @FXML
    private ImageView imgHome;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtProductId;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private ImageView imgAdd;

    @FXML
    private JFXTextField txtRecipeId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtProductId.setDisable(true);
        txtName.setDisable(true);
        txtQty.setDisable(true);
        txtRcpDes.setDisable(true);
        cmbRecipeId.getSelectionModel().clearSelection();
        cmbRecipeId.setDisable(true);
        btnSave.setText("Save");
        btnSave.setDisable(true);
        btnDelete.setDisable(true);



        tblProduct.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblProduct.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("productName"));
        tblProduct.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("productQty"));
        tblProduct.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("rcpId"));

        loadAllProducts();


        tblProduct.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductTM>() {
            @Override
            public void changed(ObservableValue<? extends ProductTM> observable, ProductTM oldValue, ProductTM newValue) {
                ProductTM selectedRow = tblProduct.getSelectionModel().getSelectedItem();

                if (selectedRow == null) {

                    return;
                }

                txtName.setDisable(false);
                txtQty.setDisable(false);
                btnDelete.setDisable(false);
                btnSave.setDisable(false);
                btnSave.setText("Update");
                txtProductId.setText(selectedRow.getProductId());
                txtName.setText(selectedRow.getProductName());
                txtQty.setText(selectedRow.getProductQty() + "");
                loadAllRecipeIDs();
                cmbRecipeId.setDisable(false);
                cmbRecipeId.setValue(selectedRow.getRcpId());
            }
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadAllProducts();
            }
        });

        cmbRecipeId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedRecipe = cmbRecipeId.getSelectionModel().getSelectedItem();
                if (selectedRecipe == null) {
                    return;
                }

                try {
                    RecipeDTO2 recipe = recipeBO.findRecipe(selectedRecipe);
                    txtRcpDes.setText(recipe.getRcpDes());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                    Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, null, e);
                }
            }
        });


    }

    private void loadAllProducts() {
        try {
            List<ProductDTO> allProductsFromDB = productBO.findAllProductsBySearch("%" + txtSearch.getText() + "%");
            ObservableList<ProductTM> productsFromTM = tblProduct.getItems();
            productsFromTM.clear();

            for (ProductDTO product : allProductsFromDB) {
                productsFromTM.add(new ProductTM(product.getProductId(), product.getProductName(), product.getProductQty(), product.getRcpId()));
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong... Please Contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Product?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            ProductTM selectedRow = tblProduct.getSelectionModel().getSelectedItem();
            try {
                productBO.deleteProduct(selectedRow.getProductId());
                tblProduct.getItems().remove(selectedRow);
                tblProduct.getSelectionModel().clearSelection();

            } catch (AllReadyExistsProductIdInTaskException e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
            }

            txtProductId.clear();
            txtName.clear();
            txtQty.clear();
            cmbRecipeId.getSelectionModel().clearSelection();
        }

    }

    @FXML
    void btnPrint_OnAction(ActionEvent event) throws JRException {
        JasperReport mainJasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/project/Report/product.jasper"));
        Map<String, Object> params = new HashMap<>();
        params.put("text",'%'+ txtSearch.getText()+'%');
        JasperPrint jasperPrint = JasperFillManager.fillReport(mainJasperReport, params, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);

    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {

        if (!txtName.getText().matches("^[A-Za-z][A-Za-z. ]+$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Product Name");
            alert.show();
            txtName.requestFocus();
            txtName.selectAll();
            return;
        } else if  (!txtQty.getText().matches("(\\d+[.]\\d+|(\\d+))")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid  Product Quantity...", ButtonType.OK);
            alert.show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }
        if (btnSave.getText().equals("Save")) {
            ObservableList<ProductTM> products = tblProduct.getItems();
            ProductDTO newProduct = new ProductDTO(txtProductId.getText(), cmbRecipeId.getSelectionModel().getSelectedItem(), txtName.getText(),Double.parseDouble(txtQty.getText()));

            try {
                productBO.saveProduct(newProduct);
                products.add(new ProductTM(newProduct.getProductId(), newProduct.getProductName(), newProduct.getProductQty(), newProduct.getRcpId()));

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, "When Save", e);
            }
        } else {
            ProductTM selectedRow = tblProduct.getSelectionModel().getSelectedItem();

            try {
                productBO.updateProduct(new ProductDTO(txtProductId.getText(), cmbRecipeId.getSelectionModel().getSelectedItem(), txtName.getText(),Double.parseDouble(txtQty.getText())));

                loadAllProducts();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
                Logger.getLogger("lk.ijse.dep.pos.controller").log(Level.SEVERE, "When Update", e);
            }
        }
        tblProduct.getSelectionModel().clearSelection();
        txtQty.clear();
        txtName.clear();
        txtProductId.clear();
        cmbRecipeId.getSelectionModel().clearSelection();
        btnSave.setDisable(true);

    }

    @FXML
    void imgAdd_OnMouseClicked(MouseEvent event) {
        txtProductId.clear();
        txtName.clear();
        txtQty.clear();
        txtRcpDes.clear();
        Platform.runLater(() ->
        {
            tblProduct.getSelectionModel().clearSelection();


        });
        tblProduct.refresh();


        txtName.setDisable(false);
        txtQty.setDisable(false);
        btnSave.setDisable(false);
        btnSave.setText("Save");
        cmbRecipeId.setDisable(false);

        loadAllRecipeIDs();

        int maxId = 0;

        try {
            String lstProId = productBO.getLastProductId();

            if (lstProId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lstProId.replace("P", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "P00" + maxId;
            } else if (maxId < 100) {
                id = "P0" + maxId;
            } else {
                id = "P" + maxId;
            }
            txtProductId.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact DEPPO").show();
            Logger.getLogger("lk.ijse.dep.project.controller").log(Level.SEVERE, null, e);
        }


    }

    private void loadAllRecipeIDs() {
        try {
            List<String> allRecipesFromBO = recipeBO.findAllRecipeIds();
            ObservableList<String> rcpIDs = cmbRecipeId.getItems();
            rcpIDs.clear();
            for (String rcpID : allRecipesFromBO) {
                rcpIDs.add(rcpID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void imgHome_OnMouseClicked(MouseEvent event) {

        try {
            URL resource = this.getClass().getResource("/lk/ijse/dep/project/view/DashBoardForm.fxml");
            Parent root = null;
            root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) this.productForm.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
