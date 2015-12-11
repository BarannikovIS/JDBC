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
import org.apache.log4j.Logger;

/**
 *
 * @author Иван
 */
public class Main {
    private static final Logger exLog= Logger.getLogger(Main.class);
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
            exLog.error(ex);
        }
        String drivers = property.getProperty("jdbc.drivers");
        Class.forName(drivers);

        String url = property.getProperty("jdbc.url");
        String name = property.getProperty("jdbc.username");
        String password = property.getProperty("jdbc.password");

        return DriverManager.getConnection(url, name, password);
    }

    public static void statement() {
        try (Connection conn = getConnection(); Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery("select * from author;")) {
            System.out.println("Statement");
            while (rs.next()) {
                System.out.println(rs.getString("name") + rs.getString("lastname") + rs.getString("surname") + rs.getString("profession"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            exLog.error(ex);
        }
    }

    public static void preparedStatement() {

        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("select * from author where name=? ; "); ResultSet rs = preparedStatement.executeQuery();) {
            preparedStatement.setString(1, "Rostislav");
            System.out.println("preparedStatement");
            while (rs.next()) {
                System.out.println(rs.getString("name") + rs.getString("lastname") + rs.getString("surname") + rs.getString("profession"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            exLog.error(ex);
        }
    }

    public static void callabeStatement() {
        try(Connection conn= getConnection();CallableStatement callableStatement=conn.prepareCall(" { call getSurnameProcedure } ");ResultSet rs =callableStatement.executeQuery();) {
            System.out.println("callableStatement");
            while (rs.next()) {
                System.out.println(rs.getString("surname"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            exLog.error(ex);
        }
    }
}
