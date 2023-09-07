/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registration;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import util.DBHelper;

/**
 *
 * @author tan18
 */
public class RegistrationDAO implements Serializable {
    public static int totalCount = 1;
//    public boolean checkLogin(String username, String password)
//            throws SQLException, NamingException {
    public RegistrationDTO checkLogin(String username, String password)
            throws SQLException, NamingException {
        //Đối tượng jdbc phải được khai báo null
        Connection con = null;
        PreparedStatement stm = null;//Chỉ execute code không phải chạy lại hết
        ResultSet rs = null;//muốn sử dụng phải có PrepareStatement
//        boolean result = false;
        RegistrationDTO result = null;
        //Phải đóng lại trong mọi trường hợp
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            //check connect xem đã có chưa
            if (con != null) {
                //2. Create SQL String
                String sql = "select fullName, roleID from tblUsers where userID = ? and password = ?";
                //Viết mà quên enter xuống dòng Sql của lỗi SyntaxFromWhere
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4. Execute Query
                rs = stm.executeQuery();
                // Muốn có result set phải có statement(stm) và executeQuery()
                //5. Process
                //Đừng bao h kiểm tra bằng null
                if (rs.next()) {
//                    return true;
                    String fullname = rs.getString("fullName");
                    String role = rs.getString("roleID");
                    boolean isAd;
                    if (role.equals("AD")) {
                        isAd = true;
                    } else {
                        isAd = false;
                    }
                    result = new RegistrationDTO(username, "", fullname, isAd);
                }//end username and password has existed in DB
            }//end connection
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
//        return result;
    }

    private List<RegistrationDTO> accountList;

    public List<RegistrationDTO> getAccountList() {
        return accountList;
    }

    public void searchLastname(String searchValue) throws SQLException, NamingException {
        //Đối tượng jdbc phải được khai báo null
        Connection con = null;
        PreparedStatement stm = null;//Chỉ execute code không phải chạy lại hết
        ResultSet rs = null;//muốn sử dụng phải có PrepareStatement
        boolean result = false;
        //Phải đóng lại trong mọi trường hợp
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            //check connect xem đã có chưa
            if (con != null) {
                //2. Create SQL String
                String sql = "select * from tblUsers "
                        + "where fullName like ?";
                //Viết mà quên enter xuống dòng Sql của lỗi SyntaxFromWhere
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query
                rs = stm.executeQuery();
                // Muốn có result set phải có statement(stm) và executeQuery()
                //5. Process
                while (rs.next()) {
                    //5.1 map
                    //5.1.1 get data from Result Set
                    String username = rs.getString("userID");
                    String password = rs.getString("password");
                    String fullname = rs.getString("fullName");
                    String role = rs.getString("roleID");
                    boolean roleID;
                    if (role.equals("AD")) {
                        roleID = true;
                    } else {
                        roleID = false;
                    }
                    //5.1.2 set data to properties
                    RegistrationDTO dto = new RegistrationDTO(username, password, fullname, roleID);
                    //5.2 add to lust
                    if (this.accountList == null) {
                        accountList = new ArrayList<>();
                        accountList.add(dto);
                    } else {
                        accountList.add(dto);
                    }
                }
                //Đừng bao h kiểm tra bằng null
                if (rs.next()) {
                }//end username and password has existed in DB
            }//end connection
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public void searchLastnameV2(int pageNumber, int pageSize, String searchValue) throws SQLException, NamingException {
        //Đối tượng jdbc phải được khai báo null
        Connection con = null;
        CallableStatement cs = null;//Chỉ execute code không phải chạy lại hết
        ResultSet rs = null;//muốn sử dụng phải có PrepareStatement
        boolean result = false;
        //Phải đóng lại trong mọi trường hợp
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            //check connect xem đã có chưa
            if (con != null) {
                //2. Create SQL String
                String sql = "{CALL GetUserList(?,?,?,?)}";
                //Viết mà quên enter xuống dòng Sql của lỗi SyntaxFromWhere
                //3. Create Statement Object
                cs = con.prepareCall(sql);
                cs.setInt(1, pageNumber);
                cs.setInt(2, pageSize);
                cs.setString(3, searchValue);
                cs.registerOutParameter(4, Types.INTEGER);
                //4. Execute Query
                rs = cs.executeQuery();
                
                // Muốn có result set phải có statement(stm) và executeQuery()
                //5. Process
                
                while (rs.next()) {
                    //5.1 map
                    //5.1.1 get data from Result Set
                    String username = rs.getString("userID");
                    String password = rs.getString("password");
                    String fullname = rs.getString("fullName");
                    String role = rs.getString("roleID");
                    boolean roleID;
                    if (role.equals("AD")) {
                        roleID = true;
                    } else {
                        roleID = false;
                    }
                    
                    //5.1.2 set data to properties
                    RegistrationDTO dto = new RegistrationDTO(username, password, fullname, roleID);
                    //5.2 add to lust
                    if (this.accountList == null) {
                        accountList = new ArrayList<>();
                        accountList.add(dto);
                    } else {
                        accountList.add(dto);
                    }
                }
                totalCount = cs.getInt(4);
                //Đừng bao h kiểm tra bằng null
//                if (rs.next()) {
//                }//end username and password has existed in DB
            }//end connection
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean deleteAccount(String username) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;//Chỉ execute code không phải chạy lại hết
        boolean result = false;
        //Phải đóng lại trong mọi trường hợp
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            //check connect xem đã có chưa
            if (con != null) {
                //2. Create SQL String
                String sql = "DELETE FROM [dbo].[tblUsers]"
                        + " WHERE userID = ?";
                //Viết mà quên enter xuống dòng Sql của lỗi SyntaxFromWhere
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //4. Execute Query
                int effectRows = stm.executeUpdate();
                // Muốn có result set phải có statement(stm) và executeQuery()
                //5. Process
                //Đừng bao h kiểm tra bằng null
                if (effectRows > 0) {
                    result = true;
                }
            }//end connection
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean updateAccount(String username, String password, String role) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. create SQL String
                String sql = "UPDATE [dbo].[tblUsers] "
                        + " SET [password] = ? ,[roleID] = ? "
                        + " WHERE [userID] = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setString(2, role);
                stm.setString(3, username);
                //4 Execute Query
                int effectRows = stm.executeUpdate();
                if (effectRows > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean createNewAccount(RegistrationDTO account) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO [dbo].[tblUsers]\n"
                        + "           ([userID]\n"
                        + "           ,[fullName]\n"
                        + "           ,[password]\n"
                        + "           ,[roleID]\n"
                        + "           ,[status])\n"
                        + "     VALUES(?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, account.getUsername());
                stm.setString(2, account.getFullName());
                stm.setString(3, account.getPassword());
                stm.setString(4, account.isRole()?"AD":"US");
                stm.setBoolean(5, true);
                int affectRow = stm.executeUpdate();
                if (affectRow > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return result;
    }

    public String checkDupUsername(String username) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String result = "";
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT [userID]\n"
                        + "  FROM [dbo].[tblUsers] WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getString("userID");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return result;
    }
}
