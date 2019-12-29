package lk.ijse.dep.project.util;

import javafx.scene.control.Button;

public class RecipeTM {
    private String ingId;
    private String ingDes;
    private double ingQty;
    private Button deleteButton;

    public RecipeTM() {
    }

    public RecipeTM(String ingId, String ingDes, double ingQty, Button deleteButton) {
        this.setIngId(ingId);
        this.setIngDes(ingDes);
        this.setIngQty(ingQty);
        this.setDeleteButton(deleteButton);
    }

    public String getIngId() {
        return ingId;
    }

    public void setIngId(String ingId) {
        this.ingId = ingId;
    }

    public String getIngDes() {
        return ingDes;
    }

    public void setIngDes(String ingDes) {
        this.ingDes = ingDes;
    }

    public double getIngQty() {
        return ingQty;
    }

    public void setIngQty(double ingQty) {
        this.ingQty = ingQty;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    @Override
    public String toString() {
        return "RecipeTM{" +
                "ingId='" + ingId + '\'' +
                ", ingDes='" + ingDes + '\'' +
                ", ingQty=" + ingQty +
                ", deleteButton=" + deleteButton +
                '}';
    }
}
