/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



package ASU_POL;
import java.sql.*;
/**
 *
 * @author apopovkin
 */
public class NotNull {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         System.out.print("Begin:");
    try {

            String UserID = "db2admin";
            String Password = "11111111";
            Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");
            Connection myconnection1 = DriverManager.getConnection("jdbc:db2:ASU_POL4", UserID, Password);





                Statement stmt_2_0_1 = myconnection1.createStatement();
                    ResultSet rs_2_0_1 = stmt_2_0_1.executeQuery(
                    " select * from ASU_POL.MAIN_USER_ALL  "
                    + " where  TEL is null  "
                          );
                    int kol=0;
                    while (rs_2_0_1.next()) {
                        kol++;
 System.out.println("kol="+kol);
                       String query = " UPDATE ASU_POL.MAIN_USER_ALL SET  "
                        + " TEL = '-',  "
                        + " IP = '-',  "
                        + " E_MAIL = '-'  "
                        + " where  "
                        + " id=" + rs_2_0_1.getInt("ID") + " ";
                PreparedStatement myStmt = myconnection1.prepareStatement(query);

                myStmt.executeUpdate();
                myStmt.close();



                    }
                    stmt_2_0_1.close();
                    rs_2_0_1.close();


            System.out.println("end:");
            myconnection1.close();

        } catch (Exception e) {
            System.out.print("DB error" + e + "\n");
        }

    }
}
