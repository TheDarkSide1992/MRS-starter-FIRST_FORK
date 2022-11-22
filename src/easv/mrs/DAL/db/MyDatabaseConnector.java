package easv.mrs.DAL.db;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import easv.mrs.BE.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MyDatabaseConnector {


    //Class will easv.mrs.be included when we start working on DATABASES

    private SQLServerDataSource dataSource;

    public MyDatabaseConnector()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        //dataSource.setDatabaseName("SMSJ_MRS_2023"); //teacher DB
        //dataSource.setUser("CSe22A_13"); //Teacher User
        //dataSource.setPassword("CSe22A_13"); //Teacher pasword
        dataSource.setDatabaseName("JENPED01_MRS_2022"); //My DB
        dataSource.setUser("CSe22A_13"); //my User
        dataSource.setPassword("CSe22A_13"); //My pasword
        dataSource.setTrustServerCertificate(true);
        //dataSource.setPortNumber(1433);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException {

        MyDatabaseConnector databaseConnector = new MyDatabaseConnector();

        try (Connection connection = databaseConnector.getConnection()) {

            System.out.println("Is it open? " + !connection.isClosed());

        } //Connection gets closed here
    }


}
