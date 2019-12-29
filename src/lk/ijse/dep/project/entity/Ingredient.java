package lk.ijse.dep.project.entity;

public class Ingredient implements SuperEntity {
    private String ingId;
    private String ingName;
    private Double ingQty;

    public Ingredient() {
    }

    public Ingredient(String ingId, String ingName, Double ingQty) {
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

    public Double getIngQty() {
        return ingQty;
    }

    public void setIngQty(Double ingQty) {
        this.ingQty = ingQty;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingId='" + ingId + '\'' +
                ", ingName='" + ingName + '\'' +
                ", ingQty=" + ingQty +
                '}';
    }
}
