/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.dashboard;

import static controller.managingStaff.FetchCustomerAccount.validateManagerSession;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Salesman;
import model.Users;

/**
 *
 * @author Chew Jin Ni
 */
public class FetchSalesmanPerformance extends HttpServlet {

    @EJB
    private SalesmanFacade SalesmanFacade;
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
            Users currentUser = (Users) session.getAttribute("user");

            // Initialize default values
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            int currentMonthNumber = currentDate.getMonthValue();

            session.setAttribute("currentYear", currentYear);
            session.setAttribute("currentMonthNumber", currentMonthNumber);

            // Fetch available years
            List<Integer> availableYears = SalesFacade.getAvailableYears();
            session.setAttribute("availableYears", availableYears);

            // Initialize months map
            Map<Integer, String> monthNames = new HashMap<>();
            monthNames.put(1, "January");
            monthNames.put(2, "February");
            monthNames.put(3, "March");
            monthNames.put(4, "April");
            monthNames.put(5, "May");
            monthNames.put(6, "June");
            monthNames.put(7, "July");
            monthNames.put(8, "August");
            monthNames.put(9, "September");
            monthNames.put(10, "October");
            monthNames.put(11, "November");
            monthNames.put(12, "December");

            session.setAttribute("months", monthNames);

            // Fetch salesman IDs
            String managerId = currentUser.getUser_id();
            List<Salesman> salesmen = SalesmanFacade.findSubdordinateSalesman(managerId);
            List<String> salesmanIds = new ArrayList<>();

            for (Salesman salesman : salesmen) {
                salesmanIds.add(salesman.getUser_id());
            }

            session.setAttribute("salesmanIds", salesmanIds);

            // Redirect to the JSF page
            response.sendRedirect(request.getContextPath() + "/managingStaff/salesmen_performance_report.xhtml");

        }catch(Exception e){
            System.out.println("Error in fetching salesman performance: " + e);
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
