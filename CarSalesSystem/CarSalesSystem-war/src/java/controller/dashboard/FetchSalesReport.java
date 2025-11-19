/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.dashboard;

import static controller.managingStaff.FetchCustomerAccount.validateManagerSession;
import facade.SalesFacade;
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
import model.Sales;

/**
 *
 * @author Chew Jin Ni
 */
public class FetchSalesReport extends HttpServlet {

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
        if (!validateManagerSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            List<Object[]> sales_performance = SalesFacade.getSalesPerformanceByMonth(Sales.SalesStatus.Cancelled, Sales.SalesStatus.Pending_Payment);
            // Process the data into a nested structure
            Map<Integer, List<Map<String, Object>>> monthlySalesData = new HashMap<>();

            for (Object[] row : sales_performance) {
                int monthNumber = (int) row[0]; // Month as an integer (1 for January, etc.)
                int day = (int) row[1];         // Day of the month
                double totalSales = (double) row[2]; // Total sales for the day

                // Convert month number to month name
                String monthName = util.Validation.getMonthName(monthNumber);

                // Create or retrieve the list for this month
                monthlySalesData.putIfAbsent(monthNumber, new ArrayList<>());
                List<Map<String, Object>> daysInMonth = monthlySalesData.get(monthNumber);

                // Add daily sales data
                Map<String, Object> dailyData = new HashMap<>();
                dailyData.put("day", day);
                dailyData.put("totalSales", totalSales);
                daysInMonth.add(dailyData);
            }

            // Set the processed data as a request attribute
            request.setAttribute("monthlySalesData", monthlySalesData);

            // Forward to the JSP page
            request.getRequestDispatcher("managingStaff/sales_performance_report.jsp").forward(request, response);
                        
            
        } catch (Exception e) {
            System.out.println("Error in fetch sales report: " + e);
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
