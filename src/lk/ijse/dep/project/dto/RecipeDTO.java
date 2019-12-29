package lk.ijse.dep.project.dto;

import java.sql.Date;
import java.util.List;

public class RecipeDTO {
    private String rcpId;
    private String rcpDes;
    private Date  rcpDate;
    private List<RecipeIngDTO> rcpIngDetails;

    public RecipeDTO() {
    }

    public RecipeDTO(String rcpId, String rcpDes, Date rcpDate, List<RecipeIngDTO> rcpIngDetails) {
        this.setRcpId(rcpId);
        this.setRcpDes(rcpDes);
        this.setRcpDate(rcpDate);
        this.setRcpIngDetails(rcpIngDetails);
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

    public List<RecipeIngDTO> getRcpIngDetails() {
        return rcpIngDetails;
    }

    public void setRcpIngDetails(List<RecipeIngDTO> rcpIngDetails) {
        this.rcpIngDetails = rcpIngDetails;
    }

    @Override
    public String toString() {
        return "RecipeDTO{" +
                "rcpId='" + rcpId + '\'' +
                ", rcpDes='" + rcpDes + '\'' +
                ", rcpDate=" + rcpDate +
                ", rcpIngDetails=" + rcpIngDetails +
                '}';
    }
}
