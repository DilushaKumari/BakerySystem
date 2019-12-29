package lk.ijse.dep.project.util;

public class EmployeeTM {
    private String empId;
    private String empName;
    private String empAddress;
    private String empContact;
    private String availability;

    public EmployeeTM() {
    }

    public EmployeeTM(String empId, String empName, String empAddress, String empContact, String availability) {
        this.setEmpId(empId);
        this.setEmpName(empName);
        this.setEmpAddress(empAddress);
        this.setEmpContact(empContact);
        this.setAvailability(availability);
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpContact() {
        return empContact;
    }

    public void setEmpContact(String empContact) {
        this.empContact = empContact;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "EmployeeTM{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", empContact='" + empContact + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }
}
