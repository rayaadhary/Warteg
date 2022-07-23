package restoran.koneksi;

/**
 *
 * @author adhary
 */

import java.sql.*;
import javax.swing.JOptionPane;

public class Koneksi {
    public static Connection con;
    public Koneksi(){
    }
    public static Connection getConnection(){
         try{
             
    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db10120182restoran","root","");
    }
    catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Gagal Koneksi");
    }
return con;
  }
}
