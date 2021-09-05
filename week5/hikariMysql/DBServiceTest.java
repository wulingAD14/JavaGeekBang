package week5.hikariMysql;

import week5.hikariMysql.DBService;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBServiceTest {

    public static void main(String[] args) throws IOException, SQLException {
        DBSservice.getInstance().start();

        // statement用来执行SQL语句
        Statement statement = DBService.getInstance().getConnection().createStatement();

        // 要执行的SQL语句id和content是表review中的项。
        String sql = "select * from a where id='111'";

        // 得到结果
        ResultSet rs = statement.executeQuery(sql);

        if(rs.next()){
            System.out.println("Logon");

        }else{
            System.out.println("Login Faild");
        }
        rs.close();
    }

}
