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
    private String Phone;
    private int ProductID;
    private int Quantity;
    private double UnitPrice;
    private float score;
    private double TotalPrice;
    private String status;
    private String typePayment;
    private String DATETIME;

    public DetailTran(String phone, int productID, int quantity, double unitPrice,
                      float score, double totalPrice, String status, String typePayment, String DATETIME) {
        Phone = phone;
        ProductID = productID;
        Quantity = quantity;
        UnitPrice = unitPrice;
        this.score = score;
        TotalPrice = totalPrice;
        this.status = status;
        this.typePayment = typePayment;
        this.DATETIME = DATETIME;
    }
}
