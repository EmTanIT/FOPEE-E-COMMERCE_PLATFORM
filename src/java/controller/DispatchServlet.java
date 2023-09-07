/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import registration.RegistrationDTO;
import util.MyApplicationConstants;

/**
 *
 * @author tan18
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

//    private final String LOGIN_PAGE = "";//Default Page
//    private final String SHOW_CART_PAGE = "viewcart.jsp";
//    private final String LOGIN_CONTROLLER = "loginController";
//    private final String SEARCH_LASTNAME_CONTROLLER = "searchLastNameController";
//    private final String DELETE_LASTNAME_CONTROLLER = "DeleteAccountServlet";
//    private final String UPDATE_PASSWORD_CONTROLLER = "UpdateAccountServlet";
//    private final String START_UP_CONTROLLER = "StartUpServlet";
//    private final String ADD_ITEM_TO_CART_CONTROLLER = "AddItemToCartServlet";
//    private final String SHOW_ITEM_CONTROLLER = "ShowItemServlet";
//    private final String REMOVE_ITEMS_CONTROLLER = "RemoveItemServlet";
//    private final String LOGOUT_CONTROLLER = "LogoutServlet";
//    private final String ORDER_CONTROLLER = "OrderServlet";
//    private final String CREATE_ACCOUNT_CONTROLLER = "CreateAccountServlet";
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

        //Which Button dis User Click?
        String button = request.getParameter("btnAction");
        //1. Get context scope
        ServletContext context = this.getServletContext();
        //2. Get SiteMaps
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        //3. Use SiteMaps to get value of key
        String url = siteMaps.getProperty(
                MyApplicationConstants.DispatchFeatures.LOGIN_PAGE);
//        String url = LOGIN_PAGE;
        try {
            if (button == null) {//trigger welcome file
                //do nothing
                url = siteMaps.getProperty(
                        MyApplicationConstants.DispatchFeatures.START_UP_CONTROLLER);
            } else if (button.equals("LOGIN")) {
//                url = LOGIN_CONTROLLER;
                url = siteMaps.getProperty(
                        MyApplicationConstants.DispatchFeatures.LOGIN_CONTROLLER);
            } else if (button.equals("Logout Account")) {
                url = siteMaps.getProperty(
                        MyApplicationConstants.LoginFeatures.LOGOUT_CONTROLLER);
            } else if (button.equals("Search")) {
//                url = SEARCH_LASTNAME_CONTROLLER;
                url = siteMaps.getProperty(
                        MyApplicationConstants.DispatchFeatures.SEARCH_LASTNAME_CONTROLLER);
            } else if (button.equals("delete")) {
                url = siteMaps.getProperty(
                        MyApplicationConstants.LoginFeatures.DELETE_LASTNAME_CONTROLLER);
            } else if (button.equals("Update")) {
                url = siteMaps.getProperty(
                        MyApplicationConstants.LoginFeatures.UPDATE_PASSWORD_CONTROLLER);
            } else if (button.equals("S")) {
                url = siteMaps.getProperty(
                        MyApplicationConstants.CartFeatures.SHOW_ITEM_CONTROLLER);
            } else if (button.equals("Add Product to Your Cart")) {
                url = siteMaps.getProperty(
                        MyApplicationConstants.CartFeatures.ADD_ITEM_TO_CART_CONTROLLER);
            } else if (button.equals("Show Your Cart")) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    RegistrationDTO result;
                    result = (RegistrationDTO) session.getAttribute("USER");
                    if (result != null) {
                        url = siteMaps.getProperty(
                            MyApplicationConstants.CartFeatures.SHOW_CART_PAGE);
                    }
                } else {
                    url = siteMaps.getProperty(
                            MyApplicationConstants.DispatchFeatures.LOGIN_CONTROLLER);
                }
            } else if (button.equals("Remove Selected Items")) {
                url = siteMaps.getProperty(
                        MyApplicationConstants.CartFeatures.REMOVE_ITEMS_CONTROLLER);
            } else if (button.equals("Show Product List Of Cate")) {
                url = siteMaps.getProperty(
                        MyApplicationConstants.CartFeatures.PROLIST_CATE_CONTROLLER);
            }else if (button.equals("Check Out")) {
                url = siteMaps.getProperty(MyApplicationConstants.OrderFeatures.ORDER_CONTROLLER);
            } else if (button.equals("Sign Up")) {
                url = siteMaps.getProperty(MyApplicationConstants.LoginFeatures.CREATE_ACCOUNT_CONTROLLER);
            } else if(button.equals("Action with Quan")){
                url = siteMaps.getProperty(MyApplicationConstants.CartFeatures.ACTION_QUAN_CONTROLLER);
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
