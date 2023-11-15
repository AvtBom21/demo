package Model.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class MySqlServer {
    @Value("${db.host}")
    private String host;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String pass;
    @Value("${db.database}")
    private String database;

    public MySqlServer() {
    }

    public MySqlServer(String host, String user, String pass, String database) {
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.database = database;
    }
    public Connection getConnection()throws Exception {
        String url = "jdbc:mysql://"+this.host+"/"+this.database+"?user="+this.user+"&password="+this.pass;
        return DriverManager.getConnection(url);
    }

    @Override
    public String toString() {
        return "MySqlServer{" +
                "host='" + host + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                ", database='" + database + '\'' +
                '}';
    }
}
