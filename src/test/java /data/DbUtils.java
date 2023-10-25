package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtils {

    private static String url = System.getProperty("db.url");
    private static String userDB = System.getProperty("app.userDB");
    private static String password = System.getProperty("app.password");

    

    @SneakyThrows
    public static Connection getConnection() {
    return DriverManager.getConnection(url, userDB, password);


    }

    @SneakyThrows
    public static void cleanDb() {
        var runner = new QueryRunner();
        var cleanCredit = "DELETE FROM credit_request_entity";
        var cleanDebit = "DELETE FROM payment_entity";
        var cleanOrder = "DELETE FROM order_entity";

        var connection = getConnection();
        runner.update(connection, cleanCredit);
        runner.update(connection, cleanDebit);
        runner.update(connection, cleanOrder);
    }

   @SneakyThrows
   public static String getCreditStatus() {
     var runner = new QueryRunner();
     var request = "select status from credit_request_entity where created = (select max(created) from credit_request_entity)";
     var connection = getConnection();
     var status = runner.query(connection, request, new ScalarHandler<String>());
     return status;
    }

   @SneakyThrows
   public static String getDebitStatus() {
     var runner = new QueryRunner();
     var request = "select status from payment_entity where created = (select max(created) from payment_entity)";
     var connection = getConnection();
     var status = runner.query(connection, request, new ScalarHandler<String>());

     return status;
}

    }


