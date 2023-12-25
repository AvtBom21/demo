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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String pass;
    private String image;
    private String name;
    private String phone;
    private String address;
    private int admin;

    public Account(String email, String pass, String image, String name, String phone, String address, int admin) {
        this.email = email;
        this.pass = pass;
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.admin = admin;
    }
}
