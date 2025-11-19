/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.managingStaff;

import static controller.managingStaff.FetchCustomerAccount.validateManagerSession;
import facade.UsersFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Users;
import static model.Users.Role.Customer;
import static model.Users.Role.Managing_Staff;
import static model.Users.Role.Salesman;
import model.Users.Status;

/**
 *
 * @author Chew Jin Ni
 */
public class UpdateStatus extends HttpServlet {

    @EJB
    private UsersFacade UsersFacade;
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
        
        HttpSession session = request.getSession();
        if (!validateManagerSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String status_user_id = request.getParameter("status_user_id");
            String status = request.getParameter("status");
            
            Users user = UsersFacade.find(status_user_id);
            user.setApproval(Status.valueOf(status));
            UsersFacade.edit(user);
            
            switch(user.getRole()) {
                case Customer -> response.sendRedirect(request.getContextPath() + "/FetchCustomerAccount");
                case Managing_Staff -> response.sendRedirect(request.getContextPath() + "/FetchManagingStaffAccount");
                case Salesman -> response.sendRedirect(request.getContextPath() + "/FetchSalesmanAccount");
            }
            
        }catch(Exception e){
            System.out.println("Error in updating user's status: " + e);
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
