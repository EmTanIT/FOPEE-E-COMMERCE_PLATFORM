/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import registration.RegistrationDTO;
import util.DBHelper;

/**
 *
 * @author tan18
 */
public class ProductDAO implements Serializable {

    private List<ProductDTO> proList;

    public List<ProductDTO> getProList() {
        return proList;
    }

    public boolean showProduct(String searchValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            //1 Make connnection
            con = DBHelper.makeConnection();
            //2 Create SQL
            if (con != null) {
                String sql = "SELECT [Sku]\n"
                        + "      ,[name]\n"
                        + "      ,[quantity]\n"
                        + "      ,[price]\n"
                        + "      ,[cateID]\n"
                        + "      ,[status]\n"
                        + "  FROM [dbo].[tblProduct] Where name like ? ";
                //3 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4 Execute Query
                rs = stm.executeQuery();
                //5 Process
                while (rs.next()) {
                    String sku = rs.getString("Sku");
                    String name = rs.getNString("name");
                    int quan = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    boolean status = rs.getBoolean("status");
                    String cateID = rs.getNString("cateID");
                    ProductDTO pro = new ProductDTO(sku, name, quan, price, cateID, status);
                    if (proList == null) {
                        proList = new ArrayList<>();
                        proList.add(pro);
                    } else {
                        proList.add(pro);
                    }
                }
                result = true;
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

    public ProductDTO findPro(String proID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ProductDTO result = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT [Sku]\n"
                    + "      ,[name]\n"
                    + "      ,[quantity]\n"
                    + "      ,[price]\n"
                    + "      ,[cateID]\n"
                    + "      ,[status]\n"
                    + "  FROM [dbo].[tblProduct] Where sku = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, proID);
            rs = stm.executeQuery();
            if (rs.next()) {
                String sku = rs.getString("Sku");
                String name = rs.getNString("name");
                int quan = rs.getInt("quantity");
                double price = rs.getDouble("price");
                boolean status = rs.getBoolean("status");
                String cateID = rs.getNString("cateID");
                result = new ProductDTO(sku, name, quan, price, cateID, status);
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
