package lk.ijse.dep.project.dto;

public class IngredientDTO {
    private String ingId;
    private String ingName;
    private double ingQty;

    public IngredientDTO() {
    }

    public IngredientDTO(String ingId, String ingName, double ingQty) {
        this.setIngId(ingId);
        this.setIngName(ingName);
        this.setIngQty(ingQty);
    }

    public String getIngId() {
        return ingId;
    }

    public void setIngId(String ingId) {
        this.ingId = ingId;
    }

    public String getIngName() {
        return ingName;
    }

    public void setIngName(String ingName) {
        this.ingName = ingName;
    }

    public double getIngQty() {
        return ingQty;
    }

    public void setIngQty(double ingQty) {
        this.ingQty = ingQty;
    }

    @Override
    public String toString() {
        return "IngredientDTO{" +
                "ingId='" + ingId + '\'' +
                ", ingName='" + ingName + '\'' +
                ", ingQty=" + ingQty +
                '}';
    }
}
