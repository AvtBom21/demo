package Model;

import Model.data.Account;
import Model.data.MySqlServer;
import org.hibernate.Session;

import java.sql.Connection;

public class AccountDAO implements Function<Account> {
    private static MySqlServer mySqlServer = new MySqlServer();
    private Connection connection;

    {
        try {
            connection = mySqlServer.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AccountDAO() {
    }
    @Override
    public void getAll() {

    }

    @Override
    public void getOne(String Email) {

    }

    @Override
    public void add(Account object) {

    }

    @Override
    public void delete_onep(String Email) {

    }

    @Override
    public void delete(Account object) {

    }

    @Override
    public void update(Account object) {

    }
    public static void main(String args[]){
        System.out.println(mySqlServer.toString());
    }
}
