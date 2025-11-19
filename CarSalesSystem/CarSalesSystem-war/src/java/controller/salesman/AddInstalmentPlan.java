/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.salesman;

import static controller.salesman.LoadTickets.validateSalesmanSession;
import facade.DepositFacade;
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
import java.time.LocalDateTime;
import model.Deposit;
import model.InstalmentPlan;
import model.Sales;

/**
 *
 * @author Chew Jin Ni
 */
public class AddInstalmentPlan extends HttpServlet {

    @EJB
    private InstalmentPlanFacade InstalmentPlanFacade;
    @EJB
    private DepositFacade DepositFacade;
    @EJB
    private SalesFacade SalesFacade;
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
        
        double car_price = Double.parseDouble(request.getParameter("car_price"));
        String sales_id = request.getParameter("instalment_sales_id");
        double deposit_amount = Double.parseDouble(request.getParameter("deposit_amount"));
        double loan_amount = Double.parseDouble(request.getParameter("loan_amount"));
        int loan_period = Integer.parseInt(request.getParameter("loan_period"));
        double interest_rate = Double.parseDouble(request.getParameter("interest_rate"));
        String type = request.getParameter("type");

        
        try (PrintWriter out = response.getWriter()) {              
            String errorMsg = "";
            boolean indicator = false;
            
            if(deposit_amount == 0){
                errorMsg += "The deposit amount should not be more than 15000. <br>";
                indicator = true;
            }
            
            if(loan_amount == 0){
                if(deposit_amount != car_price){
                    errorMsg += "The deposit amount should be same as car price if loan amount is 0. <br>";
                    indicator = true;
                }else{
                    if(loan_period != 0){
                        errorMsg += "Please set the loan period to 0 if full deposit is paid. <br>";
                        indicator = true;
                    }
                    if(interest_rate != 0){
                        errorMsg += "Please set the interest rate to 0 if full deposit is paid. <br>";
                        indicator = true;
                    }
                }      
            }
            
            double total = deposit_amount + loan_amount;
            
            if(total > car_price){
                errorMsg += "The sum of loan and deposit amount should not exceed car price. <br>";
                indicator = true;
            }
            
            if(total < car_price){
                errorMsg += "The sum of loan and deposit amount should not less than car price. <br>";
                indicator = true;
            }
            
            if(!indicator){
                double monthly_interest_rate = interest_rate / 12 / 100;
                double numerator = loan_amount * monthly_interest_rate * Math.pow(1 + monthly_interest_rate, loan_period);
                double denominator = Math.pow(1 + monthly_interest_rate, loan_period);
                double monthly_payment = numerator / denominator;
                double total_amount = (monthly_payment * loan_period) + loan_amount;

                // Round up to 2 decimal places
                double roundedMonthlyPayment = Math.ceil(monthly_payment * 100) / 100;
                double roundedTotalAmount = Math.ceil(total_amount * 100) / 100;

                InstalmentPlan ip = InstalmentPlanFacade.getInstalmentPlanBySales(sales_id);
                Deposit dp = DepositFacade.getDepositBySales(sales_id);
                if(ip != null){
                    ip.setCreated_at(LocalDateTime.now());
                    ip.setTenure_months(loan_period);
                    ip.setTotal_amount(roundedTotalAmount);
                    ip.setInterest_rate(interest_rate);
                    ip.setMonthly_payment(roundedMonthlyPayment);
                    InstalmentPlanFacade.edit(ip);

                    dp.setDeposit_amount(deposit_amount);
                    DepositFacade.edit(dp);
                }else{
                    Sales sales = SalesFacade.find(sales_id);   
                    InstalmentPlanFacade.create(new InstalmentPlan(sales, loan_period, roundedTotalAmount, interest_rate, roundedMonthlyPayment));                     
                    DepositFacade.create(new Deposit(sales, deposit_amount));
                }
                
                if("booked_sales".equals(type)){
                    response.sendRedirect(request.getContextPath() + "/LoadBookedSales");

                }else if("sales_tickets".equals(type)){
                    response.sendRedirect(request.getContextPath() + "/LoadTickets");
                }
                
                
            }else{
                if(session.getAttribute("salesman_errorMsg") != null){
                    session.removeAttribute("salesman_errorMsg");
                    session.setAttribute("salesman_errorMsg", errorMsg);
                }else{
                    session.setAttribute("salesman_errorMsg", errorMsg);
                }
                if("booked_sales".equals(type)){
                    response.sendRedirect(request.getContextPath() + "/salesman/booked_sales.jsp");

                }else if("sales_tickets".equals(type)){
                    response.sendRedirect(request.getContextPath() + "/salesman/sales_tickets.jsp");
                }
            }           
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
