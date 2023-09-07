/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import registration.RegistrationDAO;
import registration.RegistrationDTO;

/**
 *
 * @author tan18
 */
public class SearchLastnameServlet extends HttpServlet {

//    private final String SEARCH_PAGE = "search.html";
    private final String SEARCH_RESULT = "search.jsp";
    private static int PAGE_NUMBER_DEFAULT = 1;
    private static int PAGE_SIZE = 4;

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
        String searchValue = request.getParameter("searchName");
        String pageNum_raw = request.getParameter("pageNum");
        int pageNum = 0;
        String url = SEARCH_RESULT;
        try {
            //1.
            if (!searchValue.trim().isEmpty()) {
                //2.call Model
                //2.1 new DAO model
                RegistrationDAO dao = new RegistrationDAO();
                //2.2 call methods of DAO
                int numPage;
                if (pageNum_raw == null) {
                    dao.searchLastnameV2(PAGE_NUMBER_DEFAULT, PAGE_SIZE, searchValue);
                } else if (pageNum_raw != null) {
                    pageNum = Integer.parseInt(pageNum_raw);
                    dao.searchLastnameV2(pageNum, PAGE_SIZE, searchValue);
                }
                //3. process result
                List<RegistrationDTO> result = dao.getAccountList();
                url = SEARCH_RESULT;
                numPage = (int) Math.ceil((double)dao.totalCount / 4);
                System.out.println(dao.totalCount);
                System.out.println(numPage);
                if(numPage > 1) {
                    request.setAttribute("TOTAL_PAGE", numPage);
                } else {
                    request.setAttribute("TOTAL_PAGE", PAGE_NUMBER_DEFAULT);
                }
                request.setAttribute("SEARCH_RESULT", result);
                //end search value has valid value
            } else {
                String error = "Search Bar Empty !";
                request.setAttribute("ERROR", error);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
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
