package lk.ijse.dep.project.entity;

public class RecipeIngredientPK {
    private String rcpId;
    private String ingId;

    public RecipeIngredientPK() {
    }

    public RecipeIngredientPK(String rcpId, String ingId) {
        this.setRcpId(rcpId);
        this.setIngId(ingId);
    }

    public String getRcpId() {
        return rcpId;
    }

    public void setRcpId(String rcpId) {
        this.rcpId = rcpId;
    }

    public String getIngId() {
        return ingId;
    }

    public void setIngId(String ingId) {
        this.ingId = ingId;
    }

    @Override
    public String toString() {
        return "RecipeIngredientPK{" +
                "rcpId='" + rcpId + '\'' +
                ", ingId='" + ingId + '\'' +
                '}';
    }
}
