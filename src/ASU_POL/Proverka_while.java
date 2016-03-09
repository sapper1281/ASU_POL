/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL;

/**
 *
 * @author apopovkin
 */

import java.io.*;
import java.sql.*;
import java.util.regex.PatternSyntaxException;
/**
 *
 * @author apopovkin
 */
public class Proverka_while {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception, PatternSyntaxException{
        System.out.print("Begin:");
        String DB2 = "jdbc:db2:ASU_POL4";
        String UserID = "db2admin";
        String Password = "11111111";
          Connection conn = null;
PrintStream out = System.out;
        try {
            conn = new Connection_ASU_POL().getConnection(UserID, Password, DB2);

            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
           " select * from ASU_POL.POLYGON_ROAD  "
                    + " where  "
                   //+ " DT_VVOD='2011-12-12' " 
                    + " DT_VVOD=(select max(DT_VVOD) from ASU_POL.POLYGON_ROAD where r3 ='yes')"
               );
            int kol=1;
                if (rs.next()) {      
 out.println("Результат 2: " + rs.getInt("id"));
kol++;

 while (rs.next()) {      
out.println("Результат 3: " + rs.getInt("id"));

kol++;


 }
out.println("Результат 4: " + kol);








               };    
            rs.close();
            stmt.close();          
               conn.commit();
         } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Обязательно закрываем соединение с БД!
            new Connection_ASU_POL().closeConnection(conn);
        }
    }
}
