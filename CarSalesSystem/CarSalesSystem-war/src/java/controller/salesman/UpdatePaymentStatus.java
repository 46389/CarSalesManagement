/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.salesman;

import static controller.salesman.LoadTickets.validateSalesmanSession;
import facade.InstalmentPaymentFacade;
import facade.InstalmentPlanFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.InstalmentPayment;
import model.InstalmentPlan;

/**
 *
 * @author Chew Jin Ni
 */
public class UpdatePaymentStatus extends HttpServlet {
    
    @EJB
    private InstalmentPaymentFacade InstalmentPaymentFacade;
    @EJB
    private InstalmentPlanFacade InstalmentPlanFacade;
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
        if (!validateSalesmanSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        
        String payment_id = request.getParameter("payment_id");
        String status = request.getParameter("status");
        
        try (PrintWriter out = response.getWriter()) {
            InstalmentPayment payment = InstalmentPaymentFacade.find(payment_id);
            payment.setStatus(InstalmentPayment.Status.valueOf(status));
            InstalmentPaymentFacade.edit(payment);
            
            if("Approved".equals(status)){
                String instalment_id = payment.getInstalment_id().getInstalment_id();
                InstalmentPlan plan = InstalmentPlanFacade.find(instalment_id);
                if(plan.getNext_due_date() != null){
                    LocalDate existed_due = plan.getNext_due_date();
                    LocalDate next_due = existed_due.plusDays(30);
                    plan.setNext_due_date(next_due);
                }else{
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime add_now = now.plusDays(30);
                    LocalDate next_due = add_now.toLocalDate();
                    plan.setNext_due_date(next_due);
                }
                double total = plan.getTotal_paid() + plan.getMonthly_payment();
                plan.setTotal_paid(total);
                InstalmentPlanFacade.edit(plan);
            }else{
                String instalment_id = payment.getInstalment_id().getInstalment_id();
                InstalmentPlan plan = InstalmentPlanFacade.find(instalment_id);
                if(plan.getNext_due_date() != null){
                    LocalDate existed_due = plan.getNext_due_date();
                    LocalDate next_due = existed_due.minusDays(30);
                    plan.setNext_due_date(next_due);
                }
                double total = plan.getTotal_paid() - plan.getMonthly_payment();
                plan.setTotal_paid(total);
                InstalmentPlanFacade.edit(plan);
            }

            response.sendRedirect(request.getContextPath() + "/LoadInstalmentTracking"); 
        }catch(Exception e){
            System.out.println("Error in updating payment status: " + e);
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
