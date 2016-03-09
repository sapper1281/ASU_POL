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
public class ASU_POL_DOB_ROAD_MIAN_USER {

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

            Statement stmt = myconnection1.createStatement();
            ResultSet rs = stmt.executeQuery(
                     " select * from ASU_POL.POLYGON_ROAD  "
                    + " where  "
                    + " DT_VVOD=(select max(DT_VVOD) from ASU_POL.POLYGON_ROAD where r3 ='yes') order by 1"

            );

            int kol = 0;
            while (rs.next()) {
                kol++;
                int id = rs.getInt("ID");
                System.out.println("kol="+kol+"; id=" + id);


                Statement stmt_2_0_1 = myconnection1.createStatement();
                    ResultSet rs_2_0_1 = stmt_2_0_1.executeQuery(
                    " select * from ASU_POL.MAIN_USER_ALL  "
                    + " where  ID_ROAD_ASU_TR="+rs.getString("ID_ROAD_ASU_TR")+"  "
                          );
                    while (rs_2_0_1.next()) {

                       String query = " UPDATE ASU_POL.MAIN_USER_ALL SET  "
                        + " ROAD = '" + rs.getString("ALL_FN_ROAD") + "'  "
                        + " where  "
                        + " id=" + rs_2_0_1.getInt("ID") + " ";
                PreparedStatement myStmt = myconnection1.prepareStatement(query);

                myStmt.executeUpdate();
                myStmt.close();



                    }
                    stmt_2_0_1.close();
                    rs_2_0_1.close();

            }
            rs.close();
            stmt.close();
            System.out.println("end:");
            myconnection1.close();

        } catch (Exception e) {
            System.out.print("DB error" + e + "\n");
        }

    }
}
