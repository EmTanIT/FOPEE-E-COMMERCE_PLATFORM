/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.CartObj;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import product.ProductDAO;
import product.ProductDTO;
import registration.RegistrationDTO;
import util.MyApplicationConstants;

/**
 *
 * @author tan18
 */
public class AddItemToCartServlet extends HttpServlet {
//    private final String SHOPPING_PAGE = "shopping.html";

    private final String SHOPPING_PAGE = "addtocart.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = SHOPPING_PAGE;
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        try {
            //1 Cust goest to the cart' place
            HttpSession session = request.getSession();
            RegistrationDTO result;
            result = (RegistrationDTO) session.getAttribute("USER");
            if (result == null) {
                String pleaseLogin = "Please login before buy !";
                request.setAttribute("ERROR_LOGIN_PAGE", pleaseLogin);
                url = siteMaps.getProperty(
                        MyApplicationConstants.DispatchFeatures.LOGIN_PAGE);
            } else {
                //Phai la true, luon luon phai co cho de gio
                //2.Cus take his/her cart
                //Thuc ra no la DAO va DTO
                CartObj cart = (CartObj) session.getAttribute("CART");
                if (cart == null) {
                    cart = new CartObj();
                }
                //3.Cust drop item to cart
//            String sku = request.getParameter("ddlMarket");
                String sku = request.getParameter("chkPro");
                String searchValue = request.getParameter("seachPro");
                //Parameters khong can check null
                ProductDAO dao = new ProductDAO();
                ProductDTO proTemp = dao.findPro(sku);
                if (proTemp != null) {
                    cart.addItemToCart(proTemp);
                }
                //Cap nhap lai Scope
                session.setAttribute("CART", cart);
                //4.Cust Continuely goes to shopping
                url = "DispatchServlet?btnAction=S&searchProductValue=" + searchValue;
                //Quai lai shoping
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        } finally {
//            response.sendRedirect(url);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
