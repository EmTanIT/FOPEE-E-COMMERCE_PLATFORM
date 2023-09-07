package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBHelper {
    public static Connection makeConnection() throws /*ClassNotFoundException thang nay h ko sai nua */ SQLException, NamingException {
          //1. Get current context
          Context currentContext = new InitialContext();
          //2. Get Web App context
          Context tomcatContext = (Context)currentContext.lookup("java:comp/env");
          //3. Look Up DataSource from web app context
          DataSource ds = (DataSource)tomcatContext.lookup("DSSE1721");
          //4. Get Connection
          Connection con = ds.getConnection();
          
          return con;
          
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            //com xong nhấn 4 dấu chấm liên tục là sổ ra
//            //Nếu không nhấn 4 chấm đc thì lỗi -> remove đi add lại
//            //try catch: xử lý nội bộ
//            //throw: Đưa ra bên ngoài rồi xử lý
//            // -> 1 trang web nên đưa ra bên ngoài(throw), nếu ở bên trong nó gọi nhiều
//        //2. Create Connection String
//        String url = "jdbc:sqlserver://"
//                + "localhost:1433;"
//                + "databaseName=UserManagement;"
//                + "instanceName=BEN10";
//        //3. Open Connection
//        Connection con = DriverManager.getConnection(url, "sa", "12345678");
//        //4. Return Connection to caller
//        return con;
//        //Xong cái này là đang đứng ở Database
    
    }
}
