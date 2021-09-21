import java.sql.*;
import java.text.SimpleDateFormat;

public class Main {

    public Connection getConn(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String Url = "jdbc:mysql://localhost:3306/onlineshop?useUnicode=true&characterEncoding=UTF-8";
            String User = "root";
            String Password = "root";
            conn = DriverManager.getConnection(Url, User, Password);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConn(Connection conn){
        try{
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Main main = new Main();
        Connection conn = main.getConn();

        String sql = "insert into orders(orderid, ordertime) values(?,?)";
        try{
            PreparedStatement prep = conn.prepareStatement(sql);
            //将连接的自动提交关闭
            conn.setAutoCommit(false);

            java.util.Date starttime = new java.util.Date();
            System.out.println("开始时间：" + starttime);

            java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
            for (int i=1; i<=100; i++){
                for (int j=1; j<=10000; j++){
                    prep.setInt(1,i*j);
                    timestamp = new Timestamp(System.currentTimeMillis());
                    prep.setTimestamp(2,timestamp);
                    // 将预处理添加到批中
                    prep.addBatch();
                }
                // 预处理批量执行
                prep.executeBatch();
                prep.clearBatch();
                conn.commit();
                System.out.println("已提交第 " + i + " 批次");
            }

            java.util.Date endtime = new java.util.Date();
            System.out.println("结束时间：" + endtime);
            System.out.println("花费时间：" + (endtime.getTime()-starttime.getTime()) + "毫秒");

        }catch (SQLException e){
            e.printStackTrace();
        }

        main.closeConn(conn);
    }



}
