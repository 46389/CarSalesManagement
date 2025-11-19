/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 *
 * @author Chew Jin Ni
 */

public class InstalmentCalculator extends HttpServlet {

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
        
        String model_name = request.getParameter("model_name");
        double loan_amount = Double.parseDouble(request.getParameter("loan_amount"));
        int loan_period = Integer.parseInt(request.getParameter("loan_period"));
        double interest_rate = Double.parseDouble(request.getParameter("interest_rate"));
        
        try (PrintWriter out = response.getWriter()) {
            double monthly_interest_rate = interest_rate / 12 / 100;
            double numerator = loan_amount * monthly_interest_rate * Math.pow(1 + monthly_interest_rate, loan_period);
            double denominator = Math.pow(1 + monthly_interest_rate, loan_period);
            double monthly_payment = numerator / denominator;
            double total_amount = monthly_payment * loan_period + loan_amount;
            
            // Round up to 2 decimal places
            double roundedMonthlyPayment = Math.ceil(monthly_payment * 100) / 100;
            double roundedTotalAmount = Math.ceil(total_amount * 100) / 100;
            
             // Store user input and results in the session
            HttpSession session = request.getSession();
            session.setAttribute("modelName", model_name);
            session.setAttribute("loanAmount", loan_amount);
            session.setAttribute("loanPeriod", loan_period);
            session.setAttribute("interestRate", interest_rate);
            session.setAttribute("monthlyPayment", roundedMonthlyPayment);
            session.setAttribute("totalAmount", roundedTotalAmount);

            // Redirect back to the JSP page
            response.sendRedirect(request.getContextPath() + "/customer/customer_home.jsp");
        }catch(Exception e){
            System.out.println("Error in calculating instalment: " + e);
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
