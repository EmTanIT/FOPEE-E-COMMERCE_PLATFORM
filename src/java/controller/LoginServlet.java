/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import registration.RegistrationDAO;
import registration.RegistrationDTO;

/**
 *
 * @author tan18
 */
public class LoginServlet extends HttpServlet {

    private final String SEARCH_PAGE_ADMIN = "search.jsp";
    private final String SEARCH_PAGE_USER = "home.jsp";
    private final String INVALID_PAGE = "login.jsp";

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
        String username = request.getParameter("name");
        String password = request.getParameter("pass");
        String url = INVALID_PAGE;
        try {
            //1. Call model - DAO
                //1.1 newDao
                RegistrationDAO dao = new RegistrationDAO();
                //1.2 Call method of DAO
                RegistrationDTO result = dao.checkLogin(username, password);
                //2. process result
                if (result != null) {
                    if(result.isRole()){
                        url = SEARCH_PAGE_ADMIN;
                    } else {
                        url = SEARCH_PAGE_USER;
                    } 
                    //Chỗ này là chỗ ghi cookie
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", result);
//                    Cookie cookie = new Cookie(username, password);
//                    cookie.setMaxAge(60*3);
//                    response.addCookie(cookie);
                } else {
                    String msg = "Username or Password is invalid !";
                    request.setAttribute("ERROR_LOGIN_PAGE", msg);
                }
                //end user has existed
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
//            response.sendRedirect(url);
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
