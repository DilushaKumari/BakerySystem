package lk.ijse.dep.project.dto;

import java.sql.Date;

public class RecipeDTO2 {
    private String rcpId;
    private String rcpDes;
    private Date rcpDate;

    public RecipeDTO2() {
    }

    public RecipeDTO2(String rcpId, String rcpDes, Date rcpDate) {
        this.setRcpId(rcpId);
        this.setRcpDes(rcpDes);
        this.setRcpDate(rcpDate);
    }

    public String getRcpId() {
        return rcpId;
    }

    public void setRcpId(String rcpId) {
        this.rcpId = rcpId;
    }

    public String getRcpDes() {
        return rcpDes;
    }

    public void setRcpDes(String rcpDes) {
        this.rcpDes = rcpDes;
    }

    public Date getRcpDate() {
        return rcpDate;
    }

    public void setRcpDate(Date rcpDate) {
        this.rcpDate = rcpDate;
    }

    @Override
    public String toString() {
        return "RecipeDTO2{" +
                "rcpId='" + rcpId + '\'' +
                ", rcpDes='" + rcpDes + '\'' +
                ", rcpDate=" + rcpDate +
                '}';
    }
}
