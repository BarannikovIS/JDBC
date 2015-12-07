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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Иван
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        statement();
        preparedStatement();
        callabeStatement();
    }

    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        Properties property = new Properties();
        FileInputStream in = new FileInputStream("db.properties");
        property.load(in);
        in.close();

        String drivers = property.getProperty("jdbc.drivers");
        Class.forName(drivers);

        String url = property.getProperty("jdbc.url");
        String name = property.getProperty("jdbc.username");
        String password = property.getProperty("jdbc.password");

        return DriverManager.getConnection(url, name, password);
    }

    public static void statement() {
        Connection conn = null;
        try {
            conn = getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from author;");
            System.out.println("Statement");
            while (rs.next()) {
                System.out.println(rs.getString("name") + rs.getString("lastname") + rs.getString("surname") + rs.getString("profession"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void preparedStatement() {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("select * from author where name=? ; ");
            preparedStatement.setString(1, "Rostislav");
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("preparedStatement");
            while (rs.next()) {
                System.out.println(rs.getString("name") + rs.getString("lastname") + rs.getString("surname") + rs.getString("profession"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void callabeStatement() {
        Connection conn = null;
        try {
            conn = getConnection();
            CallableStatement callableStatement = conn.prepareCall(" { call getSurnameProcedure } ");
            ResultSet rs = callableStatement.executeQuery();
            System.out.println("callableStatement");
            while (rs.next()) {
                System.out.println(rs.getString("surname"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
