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
public class Connection_ASU_POL {




   

    public Connection getConnection(String UserID,String Password,String DB2,String pyt) throws Exception {
        try {
            /* String Driver   ="COM.ibm.db2.jdbc.app.DB2Driver";
            String UserID = "db2admin";
            String Password = "11111111";
            Context ctx = new InitialContext();
            // Получаем с помошью JNDI ссылку на источник данных - пул JDBC соединений
            javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("jdbc:db2:ASU_POL4");
            Connection conn = ds.getConnection(UserID, Password);

             */
            
            String Driver = "COM.ibm.db2.jdbc.app.DB2Driver";
            Class.forName(Driver);
            Connection conn = DriverManager.getConnection(DB2, UserID, Password);



            // Устанавливаем уровень изоляции транзакций
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            // Устанавливаем управление транзакциями вручную
            conn.setAutoCommit(false);
            System.out.println(pyt+" Открыто соединение с бд ");
            return conn;
        } catch (Exception e) {
            System.err.println("Could not locate datasource!  Reason:");
            e.printStackTrace();
            throw e;
        }
    }

    /*
     *  Возвращаем Connection обратно в пул JDBC соединений
     */
    public void closeConnection(Connection conn,String pyt) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println(pyt+" Закрыто соединение с бд ");
            } catch (SQLException e) {
                System.err.println("Could not close DataBase connection! Reason:");
                e.printStackTrace();
            }
        }
    }

    /*
     * Проверяет существует ли в БД такая таблица.
     * Если таблица не существует, возникнет исключительная ситуация
     */
    public boolean isTableExist(Connection conn, String tabl) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery("SELECT * FROM " + tabl + " WHERE 1<>1");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void deleteTable(Connection conn, String tabl) throws SQLException {
    try {
            // Удаляем данные и таблицу
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM " + tabl + " ");
            stmt.executeUpdate("DROP TABLE " + tabl + " ");
            stmt.executeBatch();
            conn.commit();
            stmt.close();
            System.out.println("Таблица " + tabl + " удалена");
        } catch (SQLException e) {
            // Если произошла ошибка, откатываем изменения
            conn.rollback();
            e.printStackTrace();
        }
    }
}
