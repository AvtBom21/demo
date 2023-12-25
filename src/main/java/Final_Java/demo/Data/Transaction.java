package Final_Java.demo.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TransactionID;
    private int EmployeeID;
    private String CustomerPhone;
    private double TotalAmount;
    private String status;
    private String typePayment;
    private String DATETIME;

    public Transaction(int employeeID, String customerPhone, double totalAmount, String DATETIME) {
        EmployeeID = employeeID;
        CustomerPhone = customerPhone;
        TotalAmount = totalAmount;
        this.DATETIME = DATETIME;
    }
}
