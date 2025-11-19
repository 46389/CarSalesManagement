/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import facade.BlacklistsFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Blacklists;
import model.Users;
import static model.Users.Role.Managing_Staff;
import static model.Users.Role.Salesman;

/**
 *
 * @author Chew Jin Ni
 */
public class DeleteBlacklistRequest extends HttpServlet {

    @EJB
    private BlacklistsFacade BlacklistsFacade;
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
        
        String blacklist_id = request.getParameter("blacklist_id");
        String errorMsg = "";
        
        try (PrintWriter out = response.getWriter()) {
            try{
                Blacklists blacklist = BlacklistsFacade.find(blacklist_id);
                BlacklistsFacade.remove(blacklist);
                response.sendRedirect(request.getContextPath() + "/FetchBlacklistCustomers");
            }catch (Exception e){
                HttpSession session = request.getSession();
                errorMsg += "Error in deleting blacklist customer request: " + e;
                Users user = (Users) session.getAttribute("user");

                switch(user.getRole()) {
                    case Managing_Staff -> {
                        session.setAttribute("manager_errorMsg", errorMsg);
                        response.sendRedirect(request.getContextPath() + "/managingStaff/blacklisted_customer.jsp");
                    }
                    case Salesman -> {
                        session.setAttribute("salesman_errorMsg", errorMsg);
                        response.sendRedirect(request.getContextPath() + "/salesman/blacklist_customers.jsp");
                    }
                }
            }
        }catch(Exception e){
            System.out.println("Error in deleting blacklist customer request: " + e);
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
