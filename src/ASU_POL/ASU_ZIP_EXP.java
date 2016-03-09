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

public class ASU_ZIP_EXP {

    public static void main(String[] args) throws Exception {

        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar DT_NOW_1 = Calendar.getInstance();
        String date = dateFormatter1.format(DT_NOW_1.getTime()).toString();
        Vector report_sys = new Vector();
        Vector report_asuzir = new Vector();
        Vector report_user_main = new Vector();

        PrintStream out = System.out;

        System.out.print("Begin:");
        String DB2 = "jdbc:db2:ASU_POL4";
        String UserID = "db2admin";
        String Password = "11111111";

        Connection conn = null;

        try {
            conn = new Connection_ASU_POL().getConnection(UserID, Password, DB2);


            Calendar DT_NOW = Calendar.getInstance();
            String date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
            System.out.println("Начала Создание таблицы " + date_out);

            String query = "CREATE TABLE ASU_POL.Asu_zir_5 "
                    + "(ID_ZAYAV  INTEGER         NOT NULL, "
                    + "ID_REG    CHARACTER(200)   NOT NULL, "
                    + "ID_USER   INTEGER         NOT NULL, "
                    + "START_DATE DATE NOT NULL, "
                    + "END_DATE  DATE NOT NULL, "
                    + "OP_STATION INTEGER NOT NULL, "
                    + "LN_USER   CHARACTER(200)  NOT NULL, "
                    + "FN_USER   CHARACTER(200)  NOT NULL, "
                    + "SN_USER   CHARACTER(200)  NOT NULL, "
                    + "TEL_USER  CHARACTER(200), "
                    + "MAIL_USER CHARACTER(200), "
                    + "T_NO      INTEGER NOT NULL, "
                    + "ORGTX     INTEGER, "
                    + "ID_RES    INTEGER         NOT NULL, "
                    + "DT_IMPORT DATE, "
                    + "pernr       INTEGER NOT NULL, "
                    + "nachn       CHARACTER(200), "
                    + "vorna       CHARACTER(200), "
                    + "midnm       CHARACTER(200), "
                    + "massn       INTEGER, "
                    + "begda       DATE, "
                    + "orgtx_1     INTEGER NOT NULL, "
                    + "podr        CHARACTER(200), "
                    + "DIV_id      INTEGER, "
                    + "EN_id   INTEGER "
                    + ") "
                    + "DATA CAPTURE NONE "
                    + "IN USERSPACE1; ";
            PreparedStatement myStmt = conn.prepareStatement(query);
            myStmt.executeUpdate();
            myStmt.close();

            query = "ALTER TABLE ASU_POL.Asu_zir_5 "
                    + " LOCKSIZE ROW "
                    + "APPEND OFF "
                    + "NOT VOLATILE; ";
            myStmt = conn.prepareStatement(query);
            myStmt.executeUpdate();
            myStmt.close();

            query = "COMMENT ON ASU_POL.Asu_zir_5 "
                    + "(ID_ZAYAV IS 'id_ID_ZAYAV', "
                    + "ID_REG IS 'id REG', "
                    + "ID_USER IS 'id_user', "
                    + "DT_IMPORT IS 'дата выгрузки', "
                    + "pernr IS 'таб номер 2 из асутр', "
                    + "nachn IS 'ф', "
                    + "vorna IS 'и', "
                    + "midnm IS 'о', "
                    + "massn IS 'признак асутр', "
                    + "begda IS 'дата зап асутр', "
                    + "orgtx_1 IS 'орган асутр асу зир', "
                    + "podr IS 'подр' "
                    + ");";
            myStmt = conn.prepareStatement(query);
            myStmt.executeUpdate();
            myStmt.close();

            query = "ALTER TABLE ASU_POL.Asu_zir_5 "
                    + " ADD  PRIMARY KEY "
                    + "(ID_USER, "
                    + "ID_REG, "
                    + "LN_USER, "
                    + "FN_USER, "
                    + "SN_USER, "
                    + "ID_ZAYAV, "
                    + "ID_RES, "
                    + "START_DATE, "
                    + "END_DATE, "
                    + "OP_STATION, "
                    + "T_NO, "
                    + "PERNR, "
                    + "ORGTX_1 "
                    + "); ";
            myStmt = conn.prepareStatement(query);
            myStmt.executeUpdate();
            myStmt.close();

            DT_NOW = Calendar.getInstance();
            date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
            System.out.println("Завершение Создание таблицы " + date_out);


            //   заполнение    ASU_ZIR_5
            //   асу зир
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@serw-sql-02.serw.oao.rzd:1521:asuzir";
            String user = "vcm";
            String passwd = "vcmvcm";
            Connection connection = DriverManager.getConnection(url, user, passwd);

            DT_NOW = Calendar.getInstance();
            date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
            System.out.println("Начало подклюсение в БД АСУ ЗИР" + date_out);

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    " select "
                    + " a1.id_zayav, a1.id_reg, a1.id_svt, a1.id_user, a1.id_oform,  "
                    + " a1.date_oform, a1.start_date, a1.end_date, a1.availability, a1.op_station,  "
                    + " a1.work_time, a1.div_id, a1.en_id, a1.sv_id, a1.road,    "
                    + " a1_2.ln_user, a1_2.fn_user, a1_2.sn_user, a1_2.tel_user,   "
                    + " a1_2.mail_user, a1_2.place_user, a1_2.id_post, a1_2.us_id,  "
                    + " a1_2.t_no, a1_2.orgtx, a1_2.id_stext,   "
                    + " a1_3.id_res, a1_3.id_nsi_seq, a1_3.type_access, a1_3.note,a1_3.status , "
                    + " a1_4.pernr,a1_4.nachn,a1_4.vorna,a1_4.midnm, a1_4.massn, a1_4.data_rec,a1_4.orgtx as orgtx_1,a1_4.podr "
                    + " from nis.zmain a1   "
                    + " inner join nis.zinf_user a1_2  on a1.id_user=a1_2.id_user and a1.en_id=a1_2.en_id   "
                    + " inner join nis.zires  a1_3  on a1_3.id_zayav=a1.id_zayav and a1_3.status=1  "
                    + " left join  "
                    + " (select * from "
                    + " nis.import_asutr a1_4 where a1_4.id_massn=( "
                    + " select max(id_massn)  "
                    + " from nis.import_asutr a1_4_1 where a1_4.pernr=a1_4_1.pernr)) a1_4 on a1_4.pernr=a1_2.t_no "
                    + " where a1.date_oform=(select max(date_oform)  "
                    + " from nis.zmain a2   "
                    + " inner join nis.zinf_user a2_2  on a2.id_user=a2_2.id_user and a2.en_id=a2_2.en_id  "
                    + " inner join nis.zires  a2_3  on a2_3.id_zayav=a2.id_zayav and a2_3.status=1  "
                    + " where a2.id_user=a1.id_user and "
                    + // "a2_2.t_no=a1_2.t_no and " +
                    //**********************************************************
                    " a2_3.id_res=a1_3.id_res)  "
                    + //  " and  (a1_3.id_res=23 or a1_3.id_res=24 or a1_3.id_res=218 " +
                    //  " or a1_3.id_res=215 or a1_3.id_res=216 or a1_3.id_res=221 or a1_3.id_res=635)" +
                    // " or a1_3.id_res=215 or a1_3.id_res=216  or a1_3.id_res=218 ) "+
                    " order by a1_2.ln_user ");



            DT_NOW = Calendar.getInstance();
            date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
            System.out.println("Оканчание обработки запроса подклюсение в БД АСУ ЗИР" + date_out);




            int iw = 0;
            while (rs.next()) {
                ASU_POL_MAIN_USER_CLASS line_asuzir = new ASU_POL_MAIN_USER_CLASS();
                int id_z = rs.getInt("id_zayav");
                //System.out.println("n-- " + id_z);
                line_asuzir.setID_ZAYAV(id_z);
                line_asuzir.setID_REG(rs.getString("id_reg"));
                line_asuzir.setID_USER(rs.getInt("id_user"));

                String beg_dt = rs.getString("START_DATE");
                if (beg_dt != null) {
                    String dday_p = beg_dt.substring(0, 2);
                    int dday1_p = Integer.parseInt(dday_p);
                    String mmonth_p = beg_dt.substring(3, 5);
                    int mmonth1_p = Integer.parseInt(mmonth_p);
                    String yyear_p = beg_dt.substring(6, 10);
                    int yyear1_p = Integer.parseInt(yyear_p);
                    line_asuzir.setSTART_DATE(yyear1_p + "-" + mmonth1_p + "-" + dday1_p);
                } else {
                    line_asuzir.setSTART_DATE("1000-01-01");
                }


                beg_dt = rs.getString("END_DATE");
                if (beg_dt != null) {
                    String dday_p = beg_dt.substring(0, 2);
                    int dday1_p = Integer.parseInt(dday_p);
                    String mmonth_p = beg_dt.substring(3, 5);
                    int mmonth1_p = Integer.parseInt(mmonth_p);
                    String yyear_p = beg_dt.substring(6, 10);
                    int yyear1_p = Integer.parseInt(yyear_p);
                    line_asuzir.setEND_DATE(yyear1_p + "-" + mmonth1_p + "-" + dday1_p);
                } else {
                    line_asuzir.setEND_DATE("1000-01-01");
                }

                line_asuzir.setop_station(rs.getInt("op_station"));
                line_asuzir.setDIV_ID(rs.getInt("div_id"));
                line_asuzir.setEN_ID(rs.getInt("en_id"));
                line_asuzir.setLN_USER(rs.getString("LN_USER"));
                line_asuzir.setFN_USER(rs.getString("FN_USER"));
                line_asuzir.setSN_USER(rs.getString("SN_USER"));
                line_asuzir.setTEL_USER(rs.getString("TEL_USER"));
                line_asuzir.setMAIL_USER(rs.getString("MAIL_USER"));
                line_asuzir.setT_NO(rs.getInt("T_NO"));
                line_asuzir.setORGTX(rs.getInt("ORGTX"));
                line_asuzir.setID_RES(rs.getInt("ID_RES"));
                // " a1_4.pernr,a1_4.nachn,a1_4.vorna,a1_4.midnm, a1_4.massn, a1_4.begda,a1_4.orgtx,a1_4.podr "+
                //   System.out.println("1");

                line_asuzir.setpernr(rs.getInt("pernr"));
                //   System.out.println("2");
                line_asuzir.setnachn(rs.getString("nachn"));
                //  System.out.println("3");
                line_asuzir.setvorna(rs.getString("vorna"));
                //   System.out.println("4");
                line_asuzir.setmidnm(rs.getString("midnm"));
                //   System.out.println("5");
                line_asuzir.setmassn(rs.getInt("massn"));
                //   System.out.println("6");
                beg_dt = rs.getString("data_rec");
                //   System.out.println(beg_dt);
                //   System.out.println("6_1");
                if (beg_dt != null) {
                    //       System.out.println("6_2");
                    String dday_p = beg_dt.substring(8, 10);
                    //  System.out.println(dday_p);
                    int dday1_p = Integer.parseInt(dday_p);
                    String mmonth_p = beg_dt.substring(5, 7);
                    int mmonth1_p = Integer.parseInt(mmonth_p);
                    String yyear_p = beg_dt.substring(0, 4);
                    int yyear1_p = Integer.parseInt(yyear_p);
                    // System.out.println("begda "+yyear1_p+"-"+mmonth1_p+"-"+dday1_p);
                    //  System.out.println("6_3");
                    line_asuzir.setbegda(yyear1_p + "-" + mmonth1_p + "-" + dday1_p);
                    //  System.out.println("6_4");

                } else {// System.out.println("6_3");
                    line_asuzir.setbegda("1000-01-01");
                }
                // System.out.println("7");
                line_asuzir.setorgtx_1(rs.getInt("orgtx_1"));
                // System.out.println("8");
                line_asuzir.setpodr(rs.getString("podr"));


                report_asuzir.add(line_asuzir);
                // System.out.println("11112 "+iw);
                iw++;
            }
            rs.close();
            stmt.close();
            connection.close();


            DT_NOW = Calendar.getInstance();
            date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
            System.out.println("Оканчание  подклюсения к БД АСУ ЗИР" + date_out);

           System.out.println("Количество строк= "+report_asuzir.size());


            for (int i2 = 0; i2 < report_asuzir.size(); i2++) {
                ASU_POL_MAIN_USER_CLASS line_asuzir = (ASU_POL_MAIN_USER_CLASS) report_asuzir.get(i2);

                /*  System.out.println("n1238--  '" + line_asuzir.getID_REG().trim() + " ', ?");
                System.out.println("'" + line_asuzir.getSTART_DATE() + "','" + line_asuzir.getEND_DATE() + "'");
                System.out.println("," + line_asuzir.getop_station() + ",");
                System.out.println(" '" + line_asuzir.getLN_USER().trim() + " ', '" + line_asuzir.getFN_USER().trim() + " ', ");
                System.out.println(" '" + line_asuzir.getSN_USER().trim() + " ', '" + line_asuzir.getTEL_USER().trim() + " ', ");
                System.out.println(" '" + line_asuzir.getMAIL_USER().trim() + " ', ?, ?, ?");
                System.out.println(",'" + date + "'");

                 */

                System.out.println(i2);

                String query_asuzir = " insert into ASU_POL.ASU_ZIR_5 ( ID_ZAYAV"
                        + ", "
                        + " ID_REG, ID_USER, "
                        + "START_DATE, END_DATE,"
                        + "OP_STATION, "
                        + " LN_USER, FN_USER, SN_USER, TEL_USER, MAIL_USER, T_NO, ORGTX, ID_RES,DT_IMPORT, "
                        + " pernr,nachn,vorna,midnm, massn,"
                        + " begda,"
                        + "orgtx_1,podr"
                        + ",div_id, en_id "
                        + ") values (?"
                        + ","
                        + " '" + line_asuzir.getID_REG().trim().replace('\'', ' ') + " ', ?"
                        + //  " ?, ?" +
                        ","
                        + "'" + line_asuzir.getSTART_DATE().replace('\'', ' ') + "','" + line_asuzir.getEND_DATE().replace('\'', ' ') + "'"
                        + "," + line_asuzir.getop_station() + ","
                        + " '" + line_asuzir.getLN_USER().trim().replace('\'', ' ') + " ', '" + line_asuzir.getFN_USER().trim().replace('\'', ' ') + " ', "
                        + " '" + line_asuzir.getSN_USER().trim().replace('\'', ' ') + " ', '" + line_asuzir.getTEL_USER().trim().replace('\'', ' ') + " ', "
                        + " '" + line_asuzir.getMAIL_USER().trim().replace('\'', ' ') + " ', ?, ?, ?"
                        + ",'" + date + "'"
                        + ",?,";

                if (line_asuzir.getnachn() != null) {



                    query_asuzir = query_asuzir + " '" + line_asuzir.getnachn().trim() + " ','" + line_asuzir.getvorna().trim() + " ', "
                            + " '" + line_asuzir.getmidnm().trim() + " ',"
                            + " ?"
                            + ", '" + line_asuzir.getbegda() + "'  "
                            + ",?, '" + line_asuzir.getpodr().trim() + " ',?,?  "
                            + " )";
                } else {

                    query_asuzir = query_asuzir + " 'not','not', "
                            + " 'not',"
                            + " ?"
                            + ", '" + line_asuzir.getbegda() + "'  "
                            + ",?, 'not',?,?  "
                            + " )";


                }


                PreparedStatement myStmt_asuzir = conn.prepareStatement(query_asuzir);
                //System.out.println("n123-- " + line_asuzir.getID_ZAYAV());


                myStmt_asuzir.setInt(1, line_asuzir.getID_ZAYAV());



                myStmt_asuzir.setInt(2, line_asuzir.getID_USER());
                myStmt_asuzir.setInt(3, line_asuzir.getT_NO());

                myStmt_asuzir.setInt(4, line_asuzir.getORGTX());

                myStmt_asuzir.setInt(5, line_asuzir.getID_RES());

                myStmt_asuzir.setInt(6, line_asuzir.getpernr());
                myStmt_asuzir.setInt(7, line_asuzir.getmassn());
                myStmt_asuzir.setInt(8, line_asuzir.getorgtx_1());
                myStmt_asuzir.setInt(9, line_asuzir.getDIV_ID());
                myStmt_asuzir.setInt(10, line_asuzir.getEN_ID());

                myStmt_asuzir.executeUpdate();
                myStmt_asuzir.close();
                //report_user_main.clear();
            }
            report_asuzir.clear();

            DT_NOW = Calendar.getInstance();
            date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
            System.out.println("Оканчание записи в БД из АСУ ЗИР в АСУ Пол " + date_out);

            //**********************************************
            //  ввод в таблицу ASU_POL.ASU_ZIR_ALL_ZAYAV
            conn.commit();

            String query_out_new_rol =
                    " select "
                    + " a1.ID_ZAYAV, a1.ID_REG, a1.ID_USER, a1.START_DATE, a1.END_DATE, a1.OP_STATION, a1.LN_USER, "
                    + " a1.FN_USER, a1.SN_USER, a1.TEL_USER, a1.MAIL_USER, a1.T_NO, a1.ORGTX, a1.ID_RES, "
                    + " a1.DT_IMPORT,a1.pernr,a1.nachn,a1.vorna,a1.midnm, a1.massn, a1.begda,a1.orgtx_1,a1.podr,a1.div_id, a1.en_id "
                    + " from ASU_POL.ASU_ZIR_5 a1 "
                    + " left join ASU_POL.ASU_ZIR_ALL_ZAYAV as a1_0 on "
                    + " a1_0.ID_ZAYAV=a1.ID_ZAYAV and  a1_0.ID_REG=a1.ID_REG  and  a1_0.ID_USER=a1.ID_USER and "
                    + " a1_0.LN_USER=a1.LN_USER and  a1_0.FN_USER=a1.FN_USER and  a1_0.SN_USER=a1.SN_USER and "
                    + " a1_0.T_NO=a1.T_NO and a1_0.ID_RES=a1.ID_RES "
                    + " where   a1_0.ID_ZAYAV is null  ";

            Statement stmt_out_new_rol = conn.createStatement();
            ResultSet rs_out_new_rol = stmt_out_new_rol.executeQuery(query_out_new_rol);

            DT_NOW = Calendar.getInstance();
            date_out = dateFormatter3.format(DT_NOW.getTime()).toString();

            System.out.println("rs_out_new_rol Перевод новых записей " + date_out);



            iw = 0;
            while (rs_out_new_rol.next()) {


                // System.out.println("rs_out_new_rol=  '" + rs_out_new_rol.getInt("id_zayav") + "'");



                String query_asuzir = " insert into ASU_POL.ASU_ZIR_ALL_ZAYAV ( ID_ZAYAV"
                        + ", ID_REG, ID_USER, "
                        + "START_DATE, END_DATE,"
                        + "OP_STATION, "
                        + " LN_USER, FN_USER, SN_USER, TEL_USER, MAIL_USER, T_NO, ORGTX, ID_RES,DT_IMPORT, "
                        + " pernr,nachn,vorna,midnm, massn,"
                        + " begda, orgtx_1,podr ,div_id, en_id "
                        + ") values (?"
                        + ","
                        + " '" + rs_out_new_rol.getString("ID_REG").trim().replace('\'', ' ') + " ', ?,"
                        + "'" + rs_out_new_rol.getString("START_DATE").trim().replace('\'', ' ') + "','"
                        + rs_out_new_rol.getString("END_DATE").trim().replace('\'', ' ') + "'"
                        + "," + rs_out_new_rol.getInt("OP_STATION") + ","
                        + " '" + rs_out_new_rol.getString("LN_USER").trim().replace('\'', ' ') + " ', '"
                        + rs_out_new_rol.getString("FN_USER").trim().replace('\'', ' ') + " ', "
                        + " '" + rs_out_new_rol.getString("SN_USER").trim().replace('\'', ' ') + " ', '"
                        + rs_out_new_rol.getString("TEL_USER").trim().replace('\'', ' ') + " ', "
                        + " '" + rs_out_new_rol.getString("MAIL_USER").trim().replace('\'', ' ') + " ', ?, ?, ?"
                        + ",'" + rs_out_new_rol.getString("DT_IMPORT") + "'"
                        + ",?,";

                if (rs_out_new_rol.getString("nachn") != null) {
                    query_asuzir = query_asuzir + " '" + rs_out_new_rol.getString("nachn").trim() + " ','"
                            + rs_out_new_rol.getString("vorna").trim() + " ', "
                            + " '" + rs_out_new_rol.getString("midnm").trim() + " ',"
                            + " ?"
                            + ", '" + rs_out_new_rol.getString("begda") + "'  "
                            + ",?, '" + rs_out_new_rol.getString("podr").trim() + " ',?,?  "
                            + " )";
                } else {
                    query_asuzir = query_asuzir + " 'not','not', "
                            + " 'not',"
                            + " ?"
                            + ", '" + rs_out_new_rol.getString("begda") + "'  "
                            + ",?, 'not',?,?  "
                            + " )";
                }

                PreparedStatement myStmt_asuzir = conn.prepareStatement(query_asuzir);
                // System.out.println("rs_out_new_rol-- " + rs_out_new_rol.getInt("ID_ZAYAV"));

                myStmt_asuzir.setInt(1, rs_out_new_rol.getInt("ID_ZAYAV"));
                myStmt_asuzir.setInt(2, rs_out_new_rol.getInt("ID_USER"));
                myStmt_asuzir.setInt(3, rs_out_new_rol.getInt("T_NO"));
                myStmt_asuzir.setInt(4, rs_out_new_rol.getInt("ORGTX"));
                myStmt_asuzir.setInt(5, rs_out_new_rol.getInt("ID_RES"));
                myStmt_asuzir.setInt(6, rs_out_new_rol.getInt("pernr"));
                myStmt_asuzir.setInt(7, rs_out_new_rol.getInt("massn"));
                myStmt_asuzir.setInt(8, rs_out_new_rol.getInt("orgtx_1"));
                myStmt_asuzir.setInt(9, rs_out_new_rol.getInt("div_id"));
                myStmt_asuzir.setInt(10, rs_out_new_rol.getInt("en_id"));

                myStmt_asuzir.executeUpdate();
                myStmt_asuzir.close();

            }


            rs_out_new_rol.close();
            stmt_out_new_rol.close();




            conn.commit();

//******************************************
            //Поиск не введенных заявок


            String query_input_rol = " select year(a1.START_DATE)  as START_year, year(a1.END_DATE) as END_year, "
                    + " a1.ID_ZAYAV, a1.ID_REG, a1.ID_USER, a1.START_DATE, a1.END_DATE, a1.OP_STATION, a1.LN_USER, "
                    + " a1.FN_USER, a1.SN_USER, a1.TEL_USER, a1.MAIL_USER, a1.T_NO, a1.ORGTX, a1.ID_RES, "
                    + " a1.DT_IMPORT,a1.pernr,a1.nachn,a1.vorna,a1.midnm, a1.massn, "
                    + " a1.begda,a1.orgtx_1,a1.podr,a1.div_id, a1.en_id "
                    + " , a2.id ,a4.ID_USL "
                    + " from  (select * from ASU_POL.ASU_ZIR_ALL_ZAYAV where DT_IMPORT='" + date + "') as a1 "
                    + " inner join ASU_POL.MAIN_USER_ALL a2 "
                    + " on a2.ID_ROAD_ASU_TR=a1.ORGTX_1 and a2.tab_num=a1.T_NO and a2.LAST_NAME=a1.LN_USER "
                    + " and a2.FIRST_NAME=a1.FN_USER and a2.MIDDLE_NAME=a1.SN_USER "
                    + " left join ASU_POL.SOOTV_USER_SYS  a3 on a2.id=a3.id_user and a3.ID_SYSTEM=a1.ID_RES  and  a3.del=0 "
                    + " left join  ASU_POL.SOOTV_SYS_USL a4 on a4.id_system=a1.ID_RES "
                    + " where a3.id is null and a4.ID_USL is not null "
                    + " and "
                    + " (a1.massn=0 or a1.massn=3 or a1.massn=5 or a1.massn=6 or a1.massn=7 or "
                    + " a1.massn=8 or a1.massn=10 or a1.massn=11 or a1.massn=12 or a1.massn=13) "
                    + " and a1.START_DATE<='" + date + "' and a1.END_DATE>='" + date + "' ";



            Statement stmt_query_input_rol = conn.createStatement();
            ResultSet rs_query_input_rol = stmt_query_input_rol.executeQuery(query_input_rol);

            DT_NOW = Calendar.getInstance();
            date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
            System.out.println("rs_query_input_rol Поиск не введенных заявок " + date_out);



            while (rs_query_input_rol.next()) {



                int ORGTX_1 = rs_query_input_rol.getInt("orgtx_1");
                int OP_STATION = rs_query_input_rol.getInt("OP_STATION");
                String TEL_USER = rs_query_input_rol.getString("TEL_USER").trim();
                String MAIL_USER = rs_query_input_rol.getString("MAIL_USER").trim();
                int id = rs_query_input_rol.getInt("id");
                String START_DATE = rs_query_input_rol.getString("START_DATE").trim();
                String //END_DATE = rs_query_input_rol.getString("END_DATE").trim();
                        END_DATE = String.valueOf(Integer.parseInt(START_DATE.substring(0, 4)) + 1)
                        + START_DATE.substring(4, START_DATE.length());


                int ID_RES = rs_query_input_rol.getInt("ID_RES");
                int ID_USL = rs_query_input_rol.getInt("ID_USL");
                int ROLE = 3;



                // ввод соответствия станции организациям
                Statement stmt_stan = conn.createStatement();
                ResultSet rs_stan = stmt_stan.executeQuery(" select a1.ID "
                        + " from ASU_POL.SOOTV_ROAD_STAN a1 where a1.ID_ROAD=" + ORGTX_1 + " and   "
                        + "a1.ID_STAN=" + OP_STATION + "  ");
                if (!rs_stan.next()) {
                    String query_stan = "insert into ASU_POL.SOOTV_ROAD_STAN(ID_ROAD, ID_STAN,DT_BIG) "
                            + "values (" + ORGTX_1 + "," + OP_STATION + ",'" + date + "')";
                    PreparedStatement myStmt_stan = conn.prepareStatement(query_stan);
                    myStmt_stan.executeUpdate();
                    myStmt_stan.close();
                }
                rs_stan.close();
                stmt_stan.close();

                DT_NOW = Calendar.getInstance();
                date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
                System.out.println("Окончена ввод соответствия станции организациям " + date_out);





                // обновление соотв польз стан
                Statement stmt_user_stan = conn.createStatement();
                ResultSet rs_user_stan = stmt_user_stan.executeQuery(" select ID, ID_USER, ID_STAN, DT_BIG, DT_END "
                        + " from ASU_POL.SOOTV_USER_STAN "
                        + " where ID_USER=" + id + " "
                        + " order by ID ");
                if (!rs_user_stan.next()) {
                    String query_user_stan = "insert into ASU_POL.SOOTV_USER_STAN ( id_user, ID_STAN, DT_BIG "
                            + ") values ( ?, ?,'" + date + "')";
                    PreparedStatement myStmt_user_stan = conn.prepareStatement(query_user_stan);
                    myStmt_user_stan.setInt(1, id);
                    myStmt_user_stan.setInt(2, OP_STATION);
                    myStmt_user_stan.executeUpdate();
                    myStmt_user_stan.close();

                } else {

                    PreparedStatement stmt_up_user_stan = conn.prepareStatement(
                            " UPDATE ASU_POL.SOOTV_USER_STAN SET  "
                            + " ID_STAN = " + OP_STATION + " "
                            + " , "
                            + " DT_BIG= '" + date + "'"
                            + " where ID_USER=" + id + " ");
                    stmt_up_user_stan.executeUpdate();
                    stmt_up_user_stan.close();

                    PreparedStatement stmt_up_user_stan_USER_SYS = conn.prepareStatement(
                            " UPDATE ASU_POL.SOOTV_USER_SYS SET  "
                            + " ID_STAN = " + OP_STATION + " "
                            + " where ID_USER=" + id + " ");
                    stmt_up_user_stan_USER_SYS.executeUpdate();
                    stmt_up_user_stan_USER_SYS.close();
                }
                rs_user_stan.close();
                stmt_user_stan.close();

                DT_NOW = Calendar.getInstance();
                date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
                System.out.println("Окончена обновление соотв польз стан " + date_out);








                // соответствие системе

                Statement stmt5 = conn.createStatement();
                ResultSet rs5 = stmt5.executeQuery(" select a1.ID "
                        + " from ASU_POL.SOOTV_USER_SYS a1 where a1.id_user=" + id + " and a1.id_system=" + ID_RES + " "
                        + " and a1.id_usl=" + ID_USL + " "
                        + " and R3_YES_OR_NIOT='yes' " // + "and "
                        // + " a1.dt_end is null   "
                        );

                if (!rs5.next()) {

                    DT_NOW = Calendar.getInstance();
                    date_out = dateFormatter3.format(DT_NOW.getTime()).toString();

                    query = "insert into ASU_POL.SOOTV_USER_SYS ( id_user, id_system, id_usl, "
                            + " id_role, dt_big_z, dt_end_z,DT_BIG"
                            + " , ID_STAN,del,ID_ZAYAV, ID_REG, R3_YES_OR_NIOT, DOK_ZAYVKI"
                            + " ) values ( " + id + ", " + ID_RES + ", " + ID_USL + ", " + ROLE + ", "
                            + " '" + START_DATE + "', '" + END_DATE + "','" + date + "'"
                            + " ," + OP_STATION + ",0,  " + rs_query_input_rol.getInt("ID_ZAYAV") + ","
                            + " '" + rs_query_input_rol.getString("ID_REG").trim() + "','yes','-' "
                            + " )";
                    PreparedStatement myStmt_1 = conn.prepareStatement(query);
                    myStmt_1.executeUpdate();
                    myStmt_1.close();

                }
                rs5.close();
                stmt5.close();


                DT_NOW = Calendar.getInstance();
                date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
                System.out.println("Окончена соответствие системе  " + date_out);

























                out.println("Обнавление ASU_ZIR_ALL_ZAYAV: " + rs_query_input_rol.getInt("ID_ZAYAV"));
                PreparedStatement stmt_up_query_input_rol = conn.prepareStatement(
                        " UPDATE ASU_POL.ASU_ZIR_ALL_ZAYAV SET  "
                        + " DOB = 1, "
                        + " VVED = 1 "
                        + " where ID_ZAYAV=" + rs_query_input_rol.getInt("ID_ZAYAV") + " "
                        + " and ID_REG='" + rs_query_input_rol.getString("ID_REG") + "' "
                        + " and ID_USER=" + rs_query_input_rol.getInt("ID_USER") + " "
                        + " and LN_USER='" + rs_query_input_rol.getString("LN_USER") + "' "
                        + " and FN_USER='" + rs_query_input_rol.getString("FN_USER") + "' "
                        + " and SN_USER='" + rs_query_input_rol.getString("SN_USER") + "' "
                        + " and T_NO=" + rs_query_input_rol.getInt("T_NO") + " "
                        + " and ID_RES=" + rs_query_input_rol.getInt("ID_RES") + " ");
                stmt_up_query_input_rol.executeUpdate();
                stmt_up_query_input_rol.close();
                System.out.println("Обнавлена " + date_out);


            }
            rs_query_input_rol.close();
            stmt_query_input_rol.close();

            DT_NOW = Calendar.getInstance();
            date_out = dateFormatter3.format(DT_NOW.getTime()).toString();
            System.out.println("Окончена Добавление не введенных в БД из АСУ ЗИР " + date_out);





            conn.commit();


            //  все (и введенные из асу зир и в асу пол)


            String query_input_vse = " select a1.ID_ZAYAV, a1.ID_REG, a1.ID_USER, a1.START_DATE, a1.END_DATE, a1.OP_STATION, a1.LN_USER, "
                    + " a1.FN_USER, a1.SN_USER, a1.TEL_USER, a1.MAIL_USER, a1.T_NO, a1.ORGTX, a1.ID_RES,  "
                    + " a1.DT_IMPORT,a1.pernr,a1.nachn,a1.vorna,a1.midnm, a1.massn,  "
                    + " a1.begda,a1.orgtx_1,a1.podr,a1.div_id, a1.en_id  "
                    + " , a2.id "
                    + " from  (select * from ASU_POL.ASU_ZIR_ALL_ZAYAV where DT_IMPORT='" + date + "') as a1 "
                    + " inner join ASU_POL.MAIN_USER_ALL a2  "
                    + " on a2.ID_ROAD_ASU_TR=a1.ORGTX_1 and a2.tab_num=a1.T_NO and a2.LAST_NAME=a1.LN_USER  "
                    + "  and a2.FIRST_NAME=a1.FN_USER and a2.MIDDLE_NAME=a1.SN_USER  "
                    + " where  "
                    + " (a1.massn=0 or a1.massn=3 or a1.massn=5 or a1.massn=6 or a1.massn=7 or  "
                    + " a1.massn=8 or a1.massn=10 or a1.massn=11 or a1.massn=12 or a1.massn=13)  "
                    + "   and a1.START_DATE<='" + date + "' and a1.END_DATE>='" + date + "' ";

            Statement stmt_query_input_vse = conn.createStatement();
            ResultSet rs_query_input_vse = stmt_query_input_vse.executeQuery(query_input_vse);

            while (rs_query_input_vse.next()) {

                PreparedStatement stmt_up_query_input_vse = conn.prepareStatement(
                        " UPDATE ASU_POL.ASU_ZIR_ALL_ZAYAV SET  "
                        // + " DOB = 1, "
                        + " VVED = 1 "
                        + " where ID_ZAYAV=" + rs_query_input_vse.getInt("ID_ZAYAV") + " "
                        + " and ID_REG='" + rs_query_input_vse.getString("ID_REG") + "' "
                        + " and ID_USER=" + rs_query_input_vse.getInt("ID_USER") + " "
                        + " and LN_USER='" + rs_query_input_vse.getString("LN_USER") + "' "
                        + " and FN_USER='" + rs_query_input_vse.getString("FN_USER") + "' "
                        + " and SN_USER='" + rs_query_input_vse.getString("SN_USER") + "' "
                        + " and T_NO=" + rs_query_input_vse.getInt("T_NO") + " "
                        + " and ID_RES=" + rs_query_input_vse.getInt("ID_RES") + " ");
                stmt_up_query_input_vse.executeUpdate();
                stmt_up_query_input_vse.close();

            }
            rs_query_input_vse.close();
            stmt_query_input_vse.close();


            //не переданные

            PreparedStatement stmt_up_query_input_nep = conn.prepareStatement(
                    " UPDATE ASU_POL.ASU_ZIR_ALL_ZAYAV SET  "
                    // + " DOB = 1, "
                    + " NEP = 1 "
                    + " where DOB is null and VVED is null ");
            stmt_up_query_input_nep.executeUpdate();
            stmt_up_query_input_nep.close();



            conn.commit();

            String male_out = "   select  "
                    + "  a4.last_name,a4.first_name, a4.middle_name,a4.e_mail,a1.FN_SYSTEM  "
                    + "  from  "
                    + "  (select    a1.ID_USER, a1.ID_SYSTEM, a3.FN_SYSTEM "
                    + "  from  "
                    + "  ASU_POL.SOOTV_USER_SYS as a1   "
                    + "  left join ASU_POL.POLYGON_SYSTEM as a3 on a1.id_system=a3.id_system  "
                    + "  inner join ASU_POL.ASU_ZIR_ALL_ZAYAV as a2 on a1.ID_SYSTEM=a2.ID_RES and a2.DT_IMPORT='" + date + "' "
                    + "  where a1.ID_ROLE=2 and a1.DT_END is null  "
                    + "	 group by a1.ID_USER, a1.ID_SYSTEM, a3.FN_SYSTEM "
                    + "  ) as a1  "
                    + "  left join ASU_POL.MAIN_USER_ALL as a4 on a4.id=a1.id_user and a4.block_user<>1 "
                    + "  group by   a4.last_name,a4.first_name, a4.middle_name,a4.e_mail,a1.FN_SYSTEM  "
                    + "  order by a4.e_mail,a1.FN_SYSTEM ";



            Statement stmt_male_out = conn.createStatement();
            ResultSet rs_male_out = stmt_male_out.executeQuery(male_out);






            //почта





            Calendar DT_NOW_0_13 = Calendar.getInstance();
            String date_0_13 = dateFormatter3.format(DT_NOW_0_13.getTime()).toString();
            System.out.println("male--------------------------------------" + date_0_13);


            Vector report_male_out = new Vector();
            while (rs_male_out.next()) {

                System.out.println("male1--------------------------------------" + date_0_13);


                ASU_POL_MAIN_USER_CLASS line_male_out = new ASU_POL_MAIN_USER_CLASS();
                line_male_out.setSN_SYSTEM(rs_male_out.getString("FN_SYSTEM"));
                line_male_out.setLAST_NAME_adm(rs_male_out.getString("LAST_NAME"));
                line_male_out.setFIRST_NAME_adm(rs_male_out.getString("FIRST_NAME"));
                line_male_out.setMIDDLE_NAME_adm(rs_male_out.getString("MIDDLE_NAME"));
                line_male_out.setE_MAIL(rs_male_out.getString("e_mail"));
                report_male_out.add(line_male_out);

            }
            rs_male_out.close();
            stmt_male_out.close();

            String to = " ";
            int k = 0;
            String us = "";
            for (int i = 0; i < report_male_out.size(); i++) {
                ASU_POL_MAIN_USER_CLASS line0_report_male_out = (ASU_POL_MAIN_USER_CLASS) report_male_out.get(i);
                if (!to.equals(line0_report_male_out.getE_MAIL())) {
                    System.out.println("male2--------------------------------------" + (!to.equals(line0_report_male_out.getE_MAIL())));

                    k = 1;


                    System.out.println("1" + us);
                    if ((!to.equals(" ")) || (report_male_out.size() == 1)) {

                        System.out.println("male22--------------------------------------" + (!to.equals(line0_report_male_out.getE_MAIL())));

                        if (report_male_out.size() == 1) {
                            to = line0_report_male_out.getE_MAIL();
                        }
                        System.out.println("male222--------------------------------------" + (!to.equals(line0_report_male_out.getE_MAIL())));
                        System.out.println("1----");
                        String from = "ASU_POL@serw.rzd";
                        String host = "10.58.0.47";
                        Properties properties = System.getProperties();
                        properties.setProperty("mail.smtp.host", host);
                        Session session = Session.getDefaultInstance(properties);
                        System.out.println("male2222--------------------------------------" + (!to.equals(line0_report_male_out.getE_MAIL())));

                        try {
                            System.out.println("male2--------------------------------------" + (!to.equals(line0_report_male_out.getE_MAIL())));

                            MimeMessage message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(from));
                            message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress("apopovkin@serw.rzd"/*to*/));
                            message.setSubject("Выгрузка из АСУ ЗИР");
                            message.setContent(us, "text/html; charset=UTF-8");
                            System.out.println("male22222--------------------------------------" + (!to.equals(line0_report_male_out.getE_MAIL())));




                            //       message.setText("данные отправлены");

                            // Send message
                            Transport.send(message);
                            System.out.println("Sent message successfully....");
                        } catch (MessagingException mex) {
                            mex.printStackTrace();
                        }




                    }
                    // to="apopovkin@serw.rzd";
                    to = line0_report_male_out.getE_MAIL();
                    us = "<h3>АСУ \"Пользователь\" </h3>"
                            + "<h3>Добрый день: " + line0_report_male_out.getLAST_NAME_adm() + " " + line0_report_male_out.getFIRST_NAME_adm() + " " + line0_report_male_out.getMIDDLE_NAME_adm() + " </h3>"
                            + "<h3>Произведена выгрузка из АСУ ЗИР:  </h3>"
                            + "<h3>Системы:</h3>"
                            + "<h4>" + k + ". " + line0_report_male_out.getSN_SYSTEM() + "</h4>";
                } else {
                    k++;
                    us = us + "<h4>" + k + ". " + line0_report_male_out.getSN_SYSTEM() + "</h4>";
                    System.out.println("2" + us);

                }
            }




            System.out.println("3----");
            System.out.println("2" + us);


            if (!to.equals(" ")) {
                String from = "ASU_POL@serw.rzd";
                String host = "10.58.0.47";
                Properties properties = System.getProperties();
                properties.setProperty("mail.smtp.host", host);
                Session session = Session.getDefaultInstance(properties);

                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress("apopovkin@serw.rzd"/*to*/));
                    message.setSubject("Выгрузка из АСУ ЗИР");
                    message.setContent(us, "text/html; charset=UTF-8");




                    //       message.setText("данные отправлены");

                    // Send message
                    Transport.send(message);
                    System.out.println("Sent message successfully....");
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }

            }

            report_male_out.clear();

            System.out.println("finish1");

            query = " DROP TABLE ASU_POL.ASU_ZIR_5; "
                    + " #SYNC 10; ";

            myStmt = conn.prepareStatement(query);
            myStmt.executeUpdate();
            myStmt.close();


            System.out.println("finish2");


            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Обязательно закрываем соединение с БД!
            new Connection_ASU_POL().closeConnection(conn);
        }
    }
}
