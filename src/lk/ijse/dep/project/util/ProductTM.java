package lk.ijse.dep.project.util;

public class ProductTM {
    private String productId;
    private String productName;
    private double productQty;
    private String rcpId;

    public ProductTM() {
    }

    public ProductTM(String productId, String productName, double productQty, String rcpId) {
        this.setProductId(productId);
        this.setProductName(productName);
        this.setProductQty(productQty);
        this.setRcpId(rcpId);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getRcpId() {
        return rcpId;
    }

    public void setRcpId(String rcpId) {
        this.rcpId = rcpId;
    }

    @Override
    public String toString() {
        return "ProductTM{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productQty=" + productQty +
                ", rcpId='" + rcpId + '\'' +
                '}';
    }
}
