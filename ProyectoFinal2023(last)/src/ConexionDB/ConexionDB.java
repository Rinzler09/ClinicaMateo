/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDB;

import java.sql.*;

public class ConexionDB {

    Connection c = null;

    public ConexionDB(String db) {

        try {
            //DRIVER
            Class.forName("com.mysql.jdbc.Driver");
            //DEFINIMOS LA BD
            String url = "jdbc:mysql://localhost/" + db;
            String user = "root", pass = "";
            //CONECTAMOS LA BD
            c = DriverManager.getConnection(url, user, pass);
            System.out.print("CONEXION REALIZADA EXITOSAMENTE!");
        } catch (ClassNotFoundException ex) {
            System.out.print("NO SE PUDO REALIZAR LA CONEXION" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.print("NO SE PUDO REALIZAR LA CONEXION" + ex.getMessage());
        }
    }

    public Connection getConexion() {
        return c;
    }

    public static void main(String args[]) {
        new ConexionDB("clinica_mateo");
    }
}
