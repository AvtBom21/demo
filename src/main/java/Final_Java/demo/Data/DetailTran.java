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
public class DetailTran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TransactionDetailID;
    private int TransactionID;
    private int ProductID;
    private int Quantity;
    private double UnitPrice;
    private float score;
    private double TotalPrice;

    public DetailTran(int transactionID, int productID, int quantity, double unitPrice, double totalPrice) {
        TransactionID = transactionID;
        ProductID = productID;
        Quantity = quantity;
        UnitPrice = unitPrice;
        TotalPrice = totalPrice;
    }
}
