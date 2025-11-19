/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import static controller.customer.Home.validateCustomerSession;
import facade.InstalmentPaymentFacade;
import facade.InstalmentPlanFacade;
import facade.SalesFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.CarStock;
import model.InstalmentPayment;
import model.InstalmentPlan;
import model.Sales;
import model.Users;

/**
 *
 * @author Chew Jin Ni
 */
public class LoadOwnedCars extends HttpServlet {

    @EJB
    private SalesFacade SalesFacade;
    @EJB
    private InstalmentPlanFacade InstalmentPlanFacade;
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

        if (!validateCustomerSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Users user = (Users) session.getAttribute("user");
            String customer_id = user.getUser_id();
            
            List<Sales> sales = SalesFacade.findByCustomerAndCarStatus(customer_id, CarStock.StockStatus.Paid);
            
            if(session.getAttribute("deposit_paid_cars") != null){
                session.removeAttribute("deposit_paid_cars");
                session.setAttribute("deposit_paid_cars", sales);
            }else{
                session.setAttribute("deposit_paid_cars", sales); 
            }
           
            List<InstalmentPlan> ip_list = InstalmentPlanFacade.findAll();
            if(session.getAttribute("instalment_plan_list") != null){
                session.removeAttribute("instalment_plan_list");
                session.setAttribute("instalment_plan_list", ip_list);
            }else{
                session.setAttribute("instalment_plan_list", ip_list); 
            }
            
             List<InstalmentPayment> payment_list = InstalmentPaymentFacade.findAll();
            if(session.getAttribute("instalment_payment_list") != null){
                session.removeAttribute("instalment_payment_list");
                session.setAttribute("instalment_payment_list", payment_list);
            }else{
                session.setAttribute("instalment_payment_list", payment_list); 
            }
            response.sendRedirect(request.getContextPath() + "/customer/owned_cars.jsp");
        }catch(Exception e){
            System.out.println("Error in loading owned cars: " + e);
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
