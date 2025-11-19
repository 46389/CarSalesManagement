/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.managingStaff;

import static controller.managingStaff.FetchCustomerAccount.validateManagerSession;
import facade.InstalmentPaymentFacade;
import facade.ManagingStaffFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.InstalmentPayment;
import model.ManagingStaff;
import model.Salesman;
import model.Users;

/**
 *
 * @author Chew Jin Ni
 */
public class FetchPaymentInformation extends HttpServlet {
    
    @EJB
    private ManagingStaffFacade ManagingStaffFacade;
    @EJB
    private InstalmentPaymentFacade InstalmentPaymentFacade;
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
            Users user = (Users) session.getAttribute("user");
            String manager_id = user.getUser_id();
            ManagingStaff manager = ManagingStaffFacade.find(manager_id);
            List<Salesman> salesmen = manager.getSalesmen();
            List<String> salesman_ids = new ArrayList<>(); 
            for(Salesman salesman : salesmen){
                String id = salesman.getUser_id();
                salesman_ids.add(id);
            }
            
            List<InstalmentPayment> payment_list = InstalmentPaymentFacade.getPaymentBySalesmen(salesman_ids);
            
            
            if(session.getAttribute("payment_list") != null){
                session.removeAttribute("payment_list");
                session.setAttribute("payment_list", payment_list);
            }else{
                session.setAttribute("payment_list", payment_list);
            }

            response.sendRedirect("managingStaff/instalment_payment_tracking.jsp");   
            
        }catch(Exception e){
            System.out.println("Error in fetching payment information: " + e);
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
