/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcracker.jdbcexample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.io.FileInputStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Иван
 */
public class Main {

    private static final Logger Log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        statement();
        preparedStatement();
        callabeStatement();
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties property = new Properties();
        try (FileInputStream in = new FileInputStream("db.properties");) {
            property.load(in);
        } catch (IOException ex) {
            Log.log(Level.ERROR, "exception with message" + ex.getMessage(), ex);
        }
        String drivers = property.getProperty("mysql.drivers");
        Class.forName(drivers);

        String url = property.getProperty("mysql.url");
        String name = property.getProperty("mysql.username");
        String password = property.getProperty("mysql.password");

        return DriverManager.getConnection(url, name, password);
    }

    public static void statement() {
        try (Connection conn = getConnection(); Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery("select * from author;")) {
            Log.info("Statement");
            while (rs.next()) {
                Log.info(rs.getString("name") + rs.getString("lastname") + rs.getString("surname") + rs.getString("profession"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Log.log(Level.ERROR, "exception with message" + ex.getMessage(), ex);
        }
    }

    public static void preparedStatement() {
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("select * from author where name= ?;");) {
            preparedStatement.setString(1, "Rostislav");
            try (ResultSet rs = preparedStatement.executeQuery()) {
                Log.info("preparedStatement");
                while (rs.next()) {
                    Log.info(rs.getString("name") + rs.getString("lastname") + rs.getString("surname") + rs.getString("profession"));
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Log.log(Level.ERROR, "exception with message" + ex.getMessage(), ex);
        }
    }

    public static void callabeStatement() {
        try (Connection conn = getConnection(); CallableStatement callableStatement = conn.prepareCall(" { call getSurnameByIdNew(?,?) } ")) {
            callableStatement.setInt(1, 2);
            callableStatement.registerOutParameter("sname", Types.VARCHAR);
            callableStatement.execute();
            Log.info("callableStatement");
            Log.info(callableStatement.getString("sname"));
        } catch (SQLException | ClassNotFoundException ex) {
            Log.log(Level.ERROR, "exception with message" + ex.getMessage(), ex);
        }
    }
}
