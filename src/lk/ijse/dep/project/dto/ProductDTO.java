package lk.ijse.dep.project.dto;

public class ProductDTO {
    private String productId;
    private String rcpId;
    private String productName;
    private double productQty;

    public ProductDTO() {
    }

    public ProductDTO(String productId, String rcpId, String productName, double productQty) {
        this.setProductId(productId);
        this.setRcpId(rcpId);
        this.setProductName(productName);
        this.setProductQty(productQty);
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRcpId() {
        return rcpId;
    }

    public void setRcpId(String rcpId) {
        this.rcpId = rcpId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductQty() {
        return productQty;
    }

    public void setProductQty(double productQty) {
        this.productQty = productQty;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId='" + productId + '\'' +
                ", rcpId='" + rcpId + '\'' +
                ", productName='" + productName + '\'' +
                ", productQty=" + productQty +
                '}';
    }
}
