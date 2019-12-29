package lk.ijse.dep.project.dto;

public class RecipeIngDTO {
    private String rcpId;
    private String ingId;
    private double ingQty;

    public RecipeIngDTO() {
    }

    public RecipeIngDTO(String rcpId, String ingId, double ingQty) {
        this.setRcpId(rcpId);
        this.setIngId(ingId);
        this.setIngQty(ingQty);
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

    public double getIngQty() {
        return ingQty;
    }

    public void setIngQty(double ingQty) {
        this.ingQty = ingQty;
    }

    @Override
    public String toString() {
        return "RecipeIngDTO{" +
                "rcpId='" + rcpId + '\'' +
                ", ingId='" + ingId + '\'' +
                ", ingQty=" + ingQty +
                '}';
    }
}
