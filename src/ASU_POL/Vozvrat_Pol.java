/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.text.*;
import javax.mail.*;
import javax.mail.internet.*;
/**
 *
 * @author apopovkin
 */
public class Vozvrat_Pol {

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
        
                  "  select ID, ID_ROAD_ASU_TR, TAB_NUM, ID_JOB, BLOCK_USER, PFR,  "+
   " LAST_NAME, FIRST_NAME, MIDDLE_NAME, TEL, IP,E_MAIL, DT_BIG, "+
   " ROAD, SN_JOB, RPPS "+
   " from ASU_POL.MAIN_USER_ALL_BLOCK where road like '%РДЖВ%' and DT_END='2011-12-20' "
                    
               );
               while (rs.next()) {      

String query_SOOTV_USER_STAN_DEL = " DELETE FROM ASU_POL.MAIN_USER_ALL_BLOCK "
                + " where ID=" + rs.getString("ID") + "  ";

        PreparedStatement myStmt_SOOTV_USER_STAN_DEL = conn.prepareStatement(query_SOOTV_USER_STAN_DEL);
        myStmt_SOOTV_USER_STAN_DEL.executeUpdate();
        myStmt_SOOTV_USER_STAN_DEL.close();


            /*      String query = " insert into ASU_POL.MAIN_USER_ALL ( "
            + " ID, ID_ROAD_ASU_TR, TAB_NUM, ID_JOB,BLOCK_USER, PFR, "
            + " LAST_NAME, FIRST_NAME, MIDDLE_NAME,TEL, IP,E_MAIL, DT_BIG, ROAD,  SN_JOB, RPPS "
            + " ) values ( "
            + rs.getInt("ID") + ", "
            + rs.getInt("ID_ROAD_ASU_TR") + ", "
            + rs.getInt("TAB_NUM") + ", "
            + rs.getInt("ID_JOB") + ", "
            + "0, "
            + "'" + rs.getString("PFR") + "', "
            + "'" + rs.getString("LAST_NAME") + "', "
            + "'" + rs.getString("FIRST_NAME") + "', "
            + "'" + rs.getString("MIDDLE_NAME") + "', "
                          + "'" + rs.getString("TEL") + "', "
                          + "'" + rs.getString("IP") + "', "
                         + "'" + rs.getString("E_MAIL") + "', "

            + "'" + rs.getString("DT_BIG") + "', "
            + "'" + rs.getString("ROAD") + "', "
            + "'" + rs.getString("SN_JOB") + "', "
            + "'" + rs.getString("RPPS") + "') ";

               PreparedStatement myStmt = conn.prepareStatement(query);

            myStmt.executeUpdate();
            myStmt.close();*/















                    out.println("0 "+ rs.getString("ID")+"---"+ rs.getString("ROAD"));
                   Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(
        " select ID, ID_USER, ID_SYSTEM, ID_USL, ID_ROLE, DT_BIG_Z,"+ 
    " DT_END_Z, DT_BIG, DT_END, ID_STAN, DEL,NOM_ZAYVKI, NOM_REG, "+
    " ID_ZAYAV, ID_REG, R3_YES_OR_NIOT, DOK_ZAYVKI "+
  " from ASU_POL.SOOTV_USER_SYS_BLOCK  where DT_END='2011-12-20' and ID_USER=" +rs.getString("ID")+""
              ); 
             while (rs1.next()) {         
               out.println( "1 "+rs1.getString("ID")+"---"+rs1.getString("ID_USER")+"---"+ rs1.getString("ID_SYSTEM"));
               

              /* String query_SOOTV_USER_STAN_DEL = " DELETE FROM ASU_POL.SOOTV_USER_SYS_BLOCK "
                + " where ID=" + rs1.getString("ID") + "  ";

        PreparedStatement myStmt_SOOTV_USER_STAN_DEL = conn.prepareStatement(query_SOOTV_USER_STAN_DEL);
        myStmt_SOOTV_USER_STAN_DEL.executeUpdate();
        myStmt_SOOTV_USER_STAN_DEL.close();*/



    /*  String query_SOOTV_USER_SYS = " insert into ASU_POL.SOOTV_USER_SYS "
              + " (ID, ID_USER, ID_SYSTEM, ID_USL, ID_ROLE, DT_BIG_Z,"
                + " DT_END_Z, DT_BIG, ID_STAN, del,R3_YES_OR_NIOT,DOK_ZAYVKI "
                // + " ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'yes',' " + rs_SOOTV_USER_SYS.getString("DOK_ZAYVKI").trim() + "' )";

                + " ) values ( "
                +  rs1.getInt("ID") + ", "
                +  rs1.getInt("ID_USER") + ", "
                +  rs1.getInt("ID_SYSTEM") + ", "
                +  rs1.getInt("ID_USL") + ", "
                +  rs1.getInt("ID_ROLE") + ", "
                + "'" + rs1.getString("DT_BIG_Z") + "', "
                + "'" + rs1.getString("DT_END_Z") + "', "
                + "'" + rs1.getString("DT_BIG") + "', "
                +  rs1.getInt("ID_STAN") + ", "
                + "0, "
                + "'" + rs1.getString("R3_YES_OR_NIOT") + "',"
                + "'" + rs1.getString("DOK_ZAYVKI") + "')";

        out.println(query_SOOTV_USER_SYS);

        PreparedStatement myStmt_SOOTV_USER_SYS_BLOCK = conn.prepareStatement(query_SOOTV_USER_SYS);

        myStmt_SOOTV_USER_SYS_BLOCK.executeUpdate();
        myStmt_SOOTV_USER_SYS_BLOCK.close();*/














                 };

            rs1.close();
            stmt1.close();


            Statement stmt2 = conn.createStatement();
             ResultSet rs2 = stmt2.executeQuery(
        " select ID, ID_USER, ID_STAN, DT_BIG,DT_END "+
  " from ASU_POL.SOOTV_USER_STAN_BLOCK  where DT_END='2011-12-20' and ID_USER=" +rs.getString("ID")+""
              );
             while (rs2.next()) {
               out.println( "2 "+rs2.getString("ID")+"---"+rs2.getString("ID_USER")+"---"+ rs2.getString("ID_STAN"));




           /*     String query_SOOTV_USER_SYS = " insert into ASU_POL.SOOTV_USER_STAN "
              + " (ID, ID_USER, ID_STAN, DT_BIG) values ( "
                +  rs2.getInt("ID") + ", "
                +  rs2.getInt("ID_USER") + ", "
                +  rs2.getInt("ID_STAN") + ", "
                + "'" + rs2.getString("DT_BIG") + "')";

        out.println(query_SOOTV_USER_SYS);

        PreparedStatement myStmt_SOOTV_USER_SYS_BLOCK = conn.prepareStatement(query_SOOTV_USER_SYS);

        myStmt_SOOTV_USER_SYS_BLOCK.executeUpdate();
        myStmt_SOOTV_USER_SYS_BLOCK.close();*/

 /*String query_SOOTV_USER_STAN_DEL = " DELETE FROM ASU_POL.SOOTV_USER_STAN_BLOCK "
                + " where ID=" + rs2.getString("ID") + "  ";

        PreparedStatement myStmt_SOOTV_USER_STAN_DEL = conn.prepareStatement(query_SOOTV_USER_STAN_DEL);
        myStmt_SOOTV_USER_STAN_DEL.executeUpdate();
        myStmt_SOOTV_USER_STAN_DEL.close();*/

                   
                 };

            rs2.close();
            stmt2.close();





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
