package Model.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {
    private String Anh;
    private String Hoten;
    private String Sdt;
    private String Email;
    private String Diachi;

    public Employee() {
    }

    public Employee(String anh, String hoten, String sdt, String email, String diachi) {
        Anh = anh;
        Hoten = hoten;
        Sdt = sdt;
        Email = email;
        Diachi = diachi;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Anh='" + Anh + '\'' +
                ", Hoten='" + Hoten + '\'' +
                ", Sdt='" + Sdt + '\'' +
                ", Email='" + Email + '\'' +
                ", Diachi='" + Diachi + '\'' +
                '}';
    }
}
