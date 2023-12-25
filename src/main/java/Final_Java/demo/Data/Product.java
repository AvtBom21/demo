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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String image;
    private String name;
    private float price;
    private String category;
    private String creationDate;

    public Product(String image, String name, float price, String category, String creationDate) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.category = category;
        this.creationDate = creationDate;
    }
}
