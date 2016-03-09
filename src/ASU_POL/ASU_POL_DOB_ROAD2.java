
package ASU_POL;

import java.sql.*;

/**
 *
 * @author  apopovkin
 */
public class ASU_POL_DOB_ROAD2 {

    /** Creates a new instance of ASU_POL_STAN */
    public ASU_POL_DOB_ROAD2() {
    }

    
    
    
    
    
       
             
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.print("Begin:");

        
           class RunCode extends Thread {
        Object lock = new Object();
            int id1;
            int kol;
            int ID_ROAD_ASU_TR;
            //Connection myconnection1;
            public RunCode(int id1,int ID_ROAD_ASU_TR, int kol ) {

                this.id1 = id1;
                this.kol = kol;
                this.ID_ROAD_ASU_TR = ID_ROAD_ASU_TR;
                //this.myconnection1 = myconnection1;
            }

            public void run() {

      synchronized(lock)
{
        
    
    try {
     String UserID = "db2admin";
            String Password = "11111111";
            Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");
            Connection myconnection1 = DriverManager.getConnection("jdbc:db2:ASU_POL4", UserID, Password);

                int id = id1;
                System.out.println("id " + id);

               
                //if(id>12746){
                    
                    
                    
               // System.out.println("kol  " + kol);
                boolean t_dor = true;
                int Id_str = ID_ROAD_ASU_TR;
                String Road_user = "";
                String System_user = "";
//System.out.println("ID_ROAD_ASU_TR  " + Id_str);

                while (t_dor) {

                    Statement stmt_2_0_1 = myconnection1.createStatement();
                    ResultSet rs_2_0_1 = stmt_2_0_1.executeQuery(" select ID, CODE_ROAD_ASU_TR, ID_ROAD_ASU_TR, SN_ROAD, FN_ROAD, "
                            + " MY_ID_ROAD_ASU_TR, MY_ID_ROAD, DT_BIG, DT_END, STATUS, TYPE "
                            + " from ASU_POL.POLYGON_ROAD  "
                            + " where ID_ROAD_ASU_TR=" + Id_str + " "
                            + " and "
                           // + "DT_VVOD='2011-12-12'"
                            + " DT_VVOD=(select max(DT_VVOD) from ASU_POL.POLYGON_ROAD where r3 ='yes')"
                            );
                    if (rs_2_0_1.next()) {
                        //System.out.println("Id_str11 " + Id_str);
                       // if (rs_2_0_1.getFetchSize() != 0) {
                            Road_user = rs_2_0_1.getString("FN_ROAD").trim() + ", " + Road_user;
                         //   System.out.println("Road_user1 " + Road_user);
                            Id_str = rs_2_0_1.getInt("MY_ID_ROAD_ASU_TR");
                      //  }
                    } else {
                        Road_user = "Не определено" + Road_user;
                        t_dor = false;
                      //  System.out.println("Road_user2 " + Road_user);
                    }
                    if (t_dor) {
                        t_dor = Id_str != 0;
                     //   System.out.println("Road_user3 " + Road_user);
                    }
                   // System.out.println("Road_user4 " + Road_user);
                    stmt_2_0_1.close();
                    rs_2_0_1.close();
                }

               // System.out.println("Road_user " + Road_user);
                String query = " UPDATE ASU_POL.POLYGON_ROAD SET  "
                        + " ALL_FN_ROAD = '" + Road_user + "'  "
                        + " where  "
                        + " id=" + id + " ";
                PreparedStatement myStmt = myconnection1.prepareStatement(query);

                myStmt.executeUpdate();
                myStmt.close();
     myconnection1.close();
    } catch (Exception e) {
            System.out.print("DB error" + e + "\n");
        }
         
        
        
        
    
    
}}
           } 
        
        
        try {

            String UserID = "db2admin";
            String Password = "11111111";
            Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");
            Connection myconnection1 = DriverManager.getConnection("jdbc:db2:ASU_POL4", UserID, Password);

            Statement stmt = myconnection1.createStatement();
            ResultSet rs = stmt.executeQuery(
                    " select * from ASU_POL.POLYGON_ROAD  "
                    + " where  "
                   //+ " DT_VVOD='2011-12-12' " 
                    + " DT_VVOD=(select max(DT_VVOD) from ASU_POL.POLYGON_ROAD where r3 ='yes') order by 1 "
            );

            int kol = 0;
            int p=0;
            
            while (rs.next()) {
                
                kol++; 
               if(kol>27573){
               System.out.println("kol  " + kol);
               
                Thread thread = new RunCode(rs.getInt("ID"),rs.getInt("ID_ROAD_ASU_TR"),kol);
            thread.start();
               if(p<100){
                   p++;
               }else{
                Thread.sleep(40000);
               p=0;
               }
               }
             // public RunCode(int id1,int ID_ROAD_ASU_TR, int kol ) {
            }//}
            rs.close();
            stmt.close();
          //  System.out.println("kol " + kol);
          //  System.out.println("end:");
            myconnection1.close();

        } catch (Exception e) {
            System.out.print("DB error" + e + "\n");
        }

    }
}
