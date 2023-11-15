package Model.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    private String Email;
    private String User;
    private String Pass;
    private int Status;
    private int Admin;

    public Account() {
    }

    public Account(String email, String user, String pass, int status, int admin) {
        Email = email;
        User = user;
        Pass = pass;
        Status = status;
        Admin = admin;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getAdmin() {
        return Admin;
    }

    public void setAdmin(int admin) {
        Admin = admin;
    }

    @Override
    public String toString() {
        return "Account{" +
                "Email='" + Email + '\'' +
                ", User='" + User + '\'' +
                ", Pass='" + Pass + '\'' +
                ", Status=" + Status +
                ", Admin=" + Admin +
                '}';
    }
}
