package lk.ijse.dep.project.entity;

public class Employee implements SuperEntity {
    private String empId;
    private String empName;
    private String empAddress;
    private String empContact;
    private String empAvailability;

    public Employee() {
    }

    public Employee(String empId, String empName, String empAddress, String empContact, String empAvailability) {
        this.setEmpId(empId);
        this.setEmpName(empName);
        this.setEmpAddress(empAddress);
        this.setEmpContact(empContact);
        this.setEmpAvailability(empAvailability);
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

    public String getEmpAvailability() {
        return empAvailability;
    }

    public void setEmpAvailability(String empAvailability) {
        this.empAvailability = empAvailability;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", empContact='" + empContact + '\'' +
                ", empAvailability='" + empAvailability + '\'' +
                '}';
    }
}
