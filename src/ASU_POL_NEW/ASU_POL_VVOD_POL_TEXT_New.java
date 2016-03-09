/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL_NEW;

import ASU_POL.Connection_ASU_POL;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Дорабтан
 * @author apopovkin от 9.12.2014 Добавление пользователей из текстового файла
 */
public class ASU_POL_VVOD_POL_TEXT_New {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        
 

        
        
        
        
        
        
        
        
        
        
        
        
        

        class RunCode implements Runnable {

            Object lock = new Object();
            String PytIn;
            String UserID;
            String Password;
            String DB2;
            Thread thread;
            
            
            
            
            
            
            
        private void updateTable(Connection conn, String[] param, String beg_dt,String PytIn) {

        String[] fioPOL = param[10].split(" ");
       
        
        
        
        
        try{
        String f="name " + fioPOL[0]+" "+fioPOL[1]+" "+fioPOL[2]+" ";
        }catch(ArrayIndexOutOfBoundsException e)
        {
         System.out.println("name "+param[2]+" " + fioPOL[0]+" "+PytIn);
         String[] fioPOL1 = new String[3];
         fioPOL1[0] = fioPOL[0];
         fioPOL1[2] = "";
         
         if(fioPOL.length>1)   
         fioPOL1[1] = fioPOL[1]; 
         else fioPOL1[2]="";
         fioPOL=fioPOL1;
        // System.out.println("name2 "+param[2]+" "  + fioPOL[0]+" "+fioPOL[1]+" "+fioPOL[2]+" ");
        }
        
         
        
        
       
       
        try {






            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into ASU_POL.NEW_USER_ASU_TR (PODR, TAB_NUM, SN_SHTAT_JOB, FN_SHTAT_JOB, KAT,"
                    + " LAST_NAME, FIRST_NAME, MIDDLE_NAME, ID_JOB, ID_ROAD_ASU_TR, RPPS, "
                    + " CODE_ROAD_ASU_TR, RAZD_PER, PFR, PER ) values "
                    + "( "
                    //+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                    + "'" + param[1] + "', "
                    + Integer.parseInt(param[2]) + ", "
                    + "'" + param[3] + "', "
                    + "'" + param[4] + "', "
                    + "'" + param[5] + "', "
                    + "'" + fioPOL[0] + "', "
                    + "'" + fioPOL[1] + "', "
                    + "'" + fioPOL[2] + "', "
                    + Integer.parseInt(param[13]) + ", "
                    + Integer.parseInt(param[14]) + ", "
                    + "'" + param[25] + "', "
                    + "'" + param[27] + "', "
                    + "'" + param[31] + "', "
                    + "'" + param[32] + "', "
                    // +"'"+ param[14]+"' "
                    + "'" + beg_dt + "'"
                    + ") ");




          
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (SQLException e) {
            // Если произошла ошибка, откатываем изменения
            try{
                conn.rollback();
                System.out.println("Ошибюка записи "+PytIn);
                System.out.println("Строка :{ " +param[2]+" "+ param[10] + " }");
                e.printStackTrace();
            } catch (SQLException e1) {
               System.out.println("Ошибюка rollback");
               e1.printStackTrace(); 
            }
           
        }
    
        }      
            
            
            
            
            
            
            

            public RunCode(String PytIn, String UserID, String Password, String DB2) {

                this.PytIn = PytIn;
                this.UserID = UserID;
                this.Password = Password;
                this.DB2 = DB2;
                
                
                thread= new Thread(this);
                thread.start();
                
               
            }

            public void run() {
                
                Connection conn = null;
                  try {

                 conn = new Connection_ASU_POL().getConnection(UserID,Password,DB2,PytIn);
            
                BufferedReader br = null;
                try {
                    br = new BufferedReader(
                            new InputStreamReader(
                            new FileInputStream(PytIn), "windows-1251"));
                    br.mark(0);
                    String line = "";
                    String beg_dt = "";
                    if (((line = br.readLine()) != null)) {
                        System.out.println(beg_dt = line.substring(6, 10) + "-" + line.substring(3, 5) + "-" + line.substring(0, 2));
                        
                    }

                    int countProb = 0;

                    while (countProb < 3) {
                        line = br.readLine();
                        if (line.substring(0, 5).equals("-----")) {
                            countProb++;
                           
                        }
                    }


                    while (((line = br.readLine()) != null)) {
                        
                        String[] linePOL = line.split("\\|");
                        if (linePOL.length > 30) {
                          
                              //  System.out.println("Строка :{ Обработана " + kline + " " + linePOL[10] + " }");
                                updateTable(conn, linePOL, beg_dt,PytIn);
                            
                        } else {
                           // System.out.println("Пропуск строки  " + kline);
                        }
                    }





                } catch (FileNotFoundException e) {
                    System.out.println("Файл не найден");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Ошибка  I/O");
                    e.printStackTrace();
                } finally {
                    try {
                        if (br != null) {
                            br.close();
                        }
                    } catch (IOException e) {
                        System.out.println("Ошибка  закрытия ф-ла");
                        e.printStackTrace();
                    }
                }
            conn.commit();
            
            
            } catch (Exception e) {
            e.printStackTrace();
            } finally {
            // Обязательно закрываем соединение с БД!
            new Connection_ASU_POL().closeConnection(conn,PytIn);
            }
        }
        }
        
        
        String userID = "db2admin";
        String password = "11111111";
        String db2="jdbc:db2:ASU_POL4";
        
        
        String[] PytInM = new String[6];
        PytInM[0] = "D:\\ASU_POL_NSI\\HFK_POL_20160125.TXT";
        PytInM[1] = "D:\\ASU_POL_NSI\\H58_POL_20160125.TXT";
     //   | группы
        //Ошибюка записи D:\ASU_POL_NSI\H58_POL.TXT
//Строка :{ 58204643 Дудков Александр Александрович         }- две одинаковые строки одну удалить 
       // Ошибюка записи D:\ASU_POL_NSI\H58_POL.TXT
//Строка :{ 58204826 Краснов Александр Николаевич           }
        //Ошибюка записи D:\ASU_POL_NSI\H58_POL.TXT
//Строка :{ 58207249 Бабаков Александр Николаевич           }
        PytInM[2] = "D:\\ASU_POL_NSI\\PHR_POL_20160125.TXT";
        PytInM[3] = "D:\\ASU_POL_NSI\\HRK(200)_POL_20160125.TXT";
        PytInM[4] = "D:\\ASU_POL_NSI\\HRK(300)_POL_20160125.TXT";//убрать | группы
        PytInM[5] = "D:\\ASU_POL_NSI\\HRK(400)_POL_20160125.TXT";//убрать| категории

        for (String pyt : PytInM) {
           
          new RunCode(pyt,userID,password,db2);
        }
        
  
    }
}
