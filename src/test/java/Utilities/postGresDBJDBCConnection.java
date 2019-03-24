package Utilities;

import java.sql.*;

public class postGresDBJDBCConnection {

    public static  String postGresDBJDBCExample(String query) throws ClassNotFoundException, SQLException {
       // String query  = "select orderID from OrderTable;";

       String dbURL = "jdbc:postgres1://serverIPAddresss:port/dbname";
       String username = "username";
       String password = "password";
       Class.forName("org.postgresql.Driver");
       Connection connection = null;
       String orderID = null;
       connection = DriverManager.getConnection(dbURL,username,password);
       Statement stmt = connection.createStatement();
       ResultSet resultSet = stmt.executeQuery(query);
       int columnCount = resultSet.getMetaData().getColumnCount();
       resultSet.next();
       orderID = resultSet.getString(1);
       connection.close();


        return  orderID;
    }

}
