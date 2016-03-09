/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL;

/**
 *
 * @author apopovkin
 */

import ASU_POL.*;

import java.io.*;
import java.util.Date;
import java.net.*;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.IOException; 

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;
import javax.naming.*;
import java.text.*;
import java.lang.String;
import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
/**
 *
 * @author  apopovkin
 */
public class Exel {
    
    /** Creates a new instance of ASU_POL_STRUK */
    public Exel() {
    }
    
    /**
     * @param args the command line arguments
     */
    
 
    
    
    
    
    public static void main(String[] args) throws Exception, PatternSyntaxException 
    {
       
         try
    {
     String consoleEnc = System.getProperty("console.encoding","Cp866");
     System.setOut(new CodepagePrintStream(System.out,consoleEnc) );
     System.setErr(new CodepagePrintStream(System.err,consoleEnc) );
    }
  catch(UnsupportedEncodingException e)
    {
     System.out.println("Unable to setup console codepage: " + e);
    }
        PrintStream out=System.out;
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        Calendar time1=Calendar.getInstance(); 
        
        out.println("Begin: "+dateFormatter3.format(time1.getTime()).toString()+"\n");
        String date=dateFormatter1.format(time1.getTime()).toString();
        
        String Driver   ="COM.ibm.db2.jdbc.app.DB2Driver";
        String UserID = "db2admin";
       // String Password = "D2_12345678";
        String Password = "11111111";
        
        
        try { 
             Class.forName(Driver);
            Connection myconnection1 = DriverManager.getConnection("jdbc:db2:ASU_POL" , UserID, Password);  
            
 Statement stmt = myconnection1.createStatement(); 
ResultSet rs = stmt.executeQuery("select ID, FIO, FIO1, FIO2, ROADV, EMAILV, TELV, JOBV, SES, "+ 
    " ID_USERV, ID_SYS, VVOD "+
  " from ASU_POL.TIME_LIST_TABLE "
  );
            
//out.print(rs.getRow());
//out.print(rs.getFetchSize());
//out.print(rs.getRow());
while(rs.next()){
String EMAILV=rs.getString("EMAILV");    
String TELV=rs.getString("TELV");  
String FIO=rs.getString("FIO");
String FIO1=rs.getString("FIO1");
String FIO2=rs.getString("FIO2");
out.println("ID"+rs.getInt("ID")); 
   out.println(FIO.trim()+FIO1.trim()+FIO2.trim()); 
    
Statement stmt_1 = myconnection1.createStatement(); 
ResultSet rs_1 = stmt_1.executeQuery(
    
    " select count(LAST_NAME||FIRST_NAME||MIDDLE_NAME) as d, LAST_NAME||FIRST_NAME||MIDDLE_NAME "+
  " from ASU_POL.MAIN_USER_ALL "+
 "  where LAST_NAME='"+FIO+"' and FIRST_NAME='"+FIO1+"' and MIDDLE_NAME='"+FIO2+"' "+
  " group by LAST_NAME,FIRST_NAME,MIDDLE_NAME ");
    out.println("1");
    
  while(rs_1.next()){
    if(rs_1.getInt("d")==1){
     out.print("2");
   Statement stmt_2 = myconnection1.createStatement(); 
ResultSet rs_2 = stmt_2.executeQuery(
     "  select distinct a1.* "+
  " from ASU_POL.MAIN_USER_ALL as a1  "+
  " left join ASU_POL.SOOTV_USER_SYS as a2 on a1.id=a2.id_user and del=0 "+
  " where  (a2.id_system<>211 or a2.id_system is null) "  +
  " and LAST_NAME='"+FIO+"' and FIRST_NAME='"+FIO1+"' and MIDDLE_NAME='"+FIO2+"' "); 
    
 while(rs_2.next()){
int ORGTX_1=rs_2.getInt("ID_ROAD_ASU_TR");
int id=rs_2.getInt("id");
out.println("3");


if(rs_2.getString("TEL")==null){  
   //Обновление данных о пользователе     
   PreparedStatement stmt_up = myconnection1.prepareStatement(
   " UPDATE ASU_POL.MAIN_USER_ALL SET  " +
   " TEL = '"+TELV+"' "+
   " , " +
   " IP = '-' "+
   " , " +
   " E_MAIL= '"+EMAILV+"'" +
   " where ID="+id+" ");
   stmt_up.executeUpdate();
   stmt_up.close();   
   
 } 
   out.println("4");

  Statement stmt_stan = myconnection1.createStatement(); 
ResultSet rs_stan = stmt_stan.executeQuery(" select a1.ID "+
  " from ASU_POL.SOOTV_ROAD_STAN a1 where a1.ID_ROAD="+ORGTX_1+" and   a1.ID_STAN=12  "
  ); 
if(!rs_stan.next()){

      String query_stan = "insert into ASU_POL.SOOTV_ROAD_STAN (  ID_ROAD, ID_STAN,DT_BIG) values ( ?, ?, '"+date+"')";
      PreparedStatement myStmt_stan = myconnection1.prepareStatement(query_stan);
      myStmt_stan.setInt(1,ORGTX_1);
      myStmt_stan.setInt(2,12);
      myStmt_stan.executeUpdate();
      myStmt_stan.close();
 out.print("5");
}  
  rs_stan.close();    
   stmt_stan.close(); 

   
   
   
   
   // обновление соотв польз стан
 
 Statement stmt_user_stan = myconnection1.createStatement(); 
ResultSet rs_user_stan = stmt_user_stan.executeQuery(" select ID, ID_USER, ID_STAN, DT_BIG, DT_END "+
  " from ASU_POL.SOOTV_USER_STAN "+
  " where ID_USER="+id+" " +
  //"and  ID_STAN="+OP_STATION+" "+
  " order by ID "
  );
out.println("6");
 if (!rs_user_stan.next()){   
      String query_user_stan = "insert into ASU_POL.SOOTV_USER_STAN ( id_user, ID_STAN, DT_BIG " +
      ") values ( ?, ?,'"+date+"')";
      PreparedStatement myStmt_user_stan = myconnection1.prepareStatement(query_user_stan);
      myStmt_user_stan.setInt(1,id);
      myStmt_user_stan.setInt(2,12 );
      myStmt_user_stan.executeUpdate();
      myStmt_user_stan.close();  
      
     out.println("7"); 
      // соответствие системе 
      String query_system_user = "insert into ASU_POL.SOOTV_USER_SYS ( id_user, id_system, id_usl, id_role, dt_big_z, dt_end_z,DT_BIG" +
      ", ID_STAN,del,NOM_ZAYVKI" +
      ") values ( ?, ?, ?, ?, '2010-05-28', '2012-05-28','"+date+"'" +
      ",?,0,'211'" +
      ")";
      PreparedStatement myStmt_system_user = myconnection1.prepareStatement(query_system_user);
      myStmt_system_user.setInt(1,id);
      myStmt_system_user.setInt(2,211 );
      myStmt_system_user.setInt(3,52);
      myStmt_system_user.setInt(4,3);
      myStmt_system_user.setInt(5,12);
      myStmt_system_user.executeUpdate();
      myStmt_system_user.close();
      
       out.println("8");
         
  }else{
  
 // соответствие системе 
      String query_system_user = "insert into ASU_POL.SOOTV_USER_SYS ( id_user, id_system, id_usl, id_role, dt_big_z, dt_end_z,DT_BIG" +
      ", ID_STAN,del,NOM_ZAYVKI" +
      ") values ( ?, ?, ?, ?, '2010-05-28', '2012-05-28','"+date+"'" +
      ",?,0,'211'" +
      ")";
      PreparedStatement myStmt_system_user = myconnection1.prepareStatement(query_system_user);
      myStmt_system_user.setInt(1,id);
      myStmt_system_user.setInt(2,211);
      myStmt_system_user.setInt(3,52);
      myStmt_system_user.setInt(4,3);
      myStmt_system_user.setInt(5, rs_user_stan.getInt("ID_STAN") );
      myStmt_system_user.executeUpdate();
      myStmt_system_user.close(); 
   
    out.println("9");
   
   
  }


rs_user_stan.close();
stmt_user_stan.close();  
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   





     
     
     
     
     
 }
    rs_2.close();
stmt_2.close(); 
    
    }else{
  String query_system_user_211 = "insert into ASU_POL.SES ( FIO " +
      ") values ( '"+FIO+FIO1+FIO2+"'"+
      ")";
      PreparedStatement myStmt_system_user_211 = myconnection1.prepareStatement(query_system_user_211);
     
      myStmt_system_user_211.executeUpdate();
      myStmt_system_user_211.close(); 
      
         out.println("10");
        
    } 
    
    
    
    
  
  }
    
 rs_1.close();
stmt_1.close();     





}


rs.close();
stmt.close();  

   
            
            
            
            
            
  
  
           myconnection1.close();     
            out.println("End");
        }
       
        catch(Exception e){
            out.println("DB error"+e+"\n");
         //   Rec_DB(err);
            System.exit(1);
        }     
    out.println("End1");
    
}}  
