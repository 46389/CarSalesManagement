/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.dashboard;

import static controller.managingStaff.FetchCustomerAccount.validateManagerSession;
import facade.InstalmentPlanFacade;
import facade.SalesFacade;
import facade.SalesmanFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.InstalmentPlan.PlanStatus;
import model.Sales.SalesStatus;
import model.Salesman;
import model.Users;
import util.RatingTemp;
import util.Validation;

/**
 *
 * @author Chew Jin Ni
 */
public class PaymentFeedbackAnalysis extends HttpServlet {
    
    @EJB
    private SalesFacade SalesFacade;
    @EJB
    private SalesmanFacade SalesmanFacade;
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
        if (!validateManagerSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Users currentUser = (Users) session.getAttribute("user");
            String managerId = currentUser.getUser_id();
            List<Salesman> salesmen = SalesmanFacade.findSubdordinateSalesman(managerId);
            List<String> salesmanIds = new ArrayList<>();

            for (Salesman salesman : salesmen) {
                salesmanIds.add(salesman.getUser_id());
            }
            
            // Feedbacks Table and Rating Chart
            List<Object[]> feedbacks = SalesFacade.getRatingBySalesman(salesmanIds);
            List<RatingTemp> feedback_list = new ArrayList<>();
            if(feedbacks != null){
                for(Object[] feedback : feedbacks){
                    RatingTemp rating = new RatingTemp();
                    rating.setSalesman_id((String) feedback[0]);
                    rating.setCustomer_id((String) feedback[1]);
                    rating.setSales_id((String) feedback[2]);
                    rating.setRating((int) feedback[3]);
                    rating.setComment((String) feedback[4]);
                    feedback_list.add(rating);
                }
            }
            
            session.setAttribute("feedback_list", feedback_list);
            
            List<Object[]> stars = SalesFacade.getRatingValueBySalesman(salesmanIds);
            session.setAttribute("stars", stars);
            
            // Instalment Status
            List<Object[]> instalment_status = InstalmentPlanFacade.getInstalmentStatus(salesmanIds, PlanStatus.Approved);
            session.setAttribute("instalment_status", instalment_status);
            
            // Sales Status Chart
            List<Object[]> sales_status = SalesFacade.getSalesStatusBySalesman(salesmanIds, SalesStatus.Cancelled, SalesStatus.Pending_Payment, SalesStatus.Pending_Payment_Approval, SalesStatus.Manufacturing, SalesStatus.Delivering, SalesStatus.Delivered, SalesStatus.Repossessed);
            Map<String, Integer> statusCounts = new HashMap<>();
            statusCounts.put("Cancelled", 0);
            statusCounts.put("Pending_Payment", 0);
            statusCounts.put("Pending_Payment_Approval", 0);
            statusCounts.put("Manufacturing", 0);
            statusCounts.put("Delivering", 0);
            statusCounts.put("Delivered", 0);
            statusCounts.put("Repossessed", 0);

            // Populate statusCounts only if sales_status is not null
            if (sales_status != null) {
                Object[] salesStatusArray = (Object[]) sales_status.get(0);

                // Ensure the array has exactly 7 elements
                if (salesStatusArray.length == 7) {
                    statusCounts.put("Cancelled", Validation.getSafeLongValue(salesStatusArray[0]));
                    statusCounts.put("Pending_Payment", Validation.getSafeLongValue(salesStatusArray[1]));
                    statusCounts.put("Pending_Payment_Approval", Validation.getSafeLongValue(salesStatusArray[2]));
                    statusCounts.put("Manufacturing", Validation.getSafeLongValue(salesStatusArray[3]));
                    statusCounts.put("Delivering", Validation.getSafeLongValue(salesStatusArray[4]));
                    statusCounts.put("Delivered", Validation.getSafeLongValue(salesStatusArray[5]));
                    statusCounts.put("Repossessed", Validation.getSafeLongValue(salesStatusArray[6]));
                } else {
                    throw new IllegalArgumentException("Unexpected number of elements in sales_status: " + salesStatusArray.length);
                }
            } else {
                throw new IllegalArgumentException("sales_status is null or not an Object[]");
            }
            // Store the statusCounts map in the session
            session.setAttribute("sales_status", statusCounts);

                       
            response.sendRedirect(request.getContextPath() + "/managingStaff/payment_feedback_analysis.jsp");

        }catch(Exception e){
            System.out.println("Error in fetching payment and feedback analysis: " + e);
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
