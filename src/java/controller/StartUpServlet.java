/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import category.CategoryDAO;
import category.CategoryDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
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
public class StartUpServlet extends HttpServlet {
    private final String HOME_PAGE = "home.jsp";
    private final String SEARCH_PAGE = "search.jsp";
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
        String url = HOME_PAGE;
        try {
//            //1. Get all Cookies
//            Cookie[] cookies = request.getCookies();
//            //2. Check Cookie
//            if(cookies != null){
//                //3. Get Newest Cookie
//                Cookie newestCookie = cookies[cookies.length - 1];
//                //4. Get user and pass from cookie
//                String username = newestCookie.getName();
//                String password = newestCookie.getValue();
//                //5. call DAO
//                RegistrationDAO dao = new RegistrationDAO();
//                RegistrationDTO result = dao.checkLogin(username, password);
//                if(result != null){
//                    url = SEARCH_PAGE;
//                }
//            }
            CategoryDAO dao = new CategoryDAO();
            boolean checkCate = dao.showCateName();
            if(checkCate){
                HttpSession session = request.getSession();
                List<CategoryDTO> cateList = dao.getCateList();
                session.setAttribute("CATE_LIST", cateList);
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
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

