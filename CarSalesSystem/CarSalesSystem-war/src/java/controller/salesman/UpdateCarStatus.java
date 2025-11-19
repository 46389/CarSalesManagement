/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.salesman;

import static controller.salesman.LoadTickets.validateSalesmanSession;
import facade.CarStockFacade;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.CarStock;
import model.CarStock.StockStatus;
import static model.CarStock.StockStatus.Booked;
import static model.CarStock.StockStatus.Interested;
import static model.CarStock.StockStatus.Paid;
import model.Deposit;
import model.InstalmentPlan;
import model.Sales;

/**
 *
 * @author Chew Jin Ni
 */
public class UpdateCarStatus extends HttpServlet {

    @EJB
    private InstalmentPlanFacade InstalmentPlanFacade;
    @EJB
    private CarStockFacade CarStockFacade;
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
        
        String sales_id = request.getParameter("sales_id");
        String car_id = request.getParameter("car_id");
        String car_status = request.getParameter("car_status");
        String errorMsg = "";
        
        try (PrintWriter out = response.getWriter()) { 
            InstalmentPlan ip = InstalmentPlanFacade.getInstalmentPlanBySales(sales_id);
            Deposit dp = DepositFacade.getDepositBySales(sales_id);
            
            if(ip == null){
                errorMsg += "Please add the instalment plan before car booking.<br>";
            }else{
                CarStock car = CarStockFacade.find(car_id);
                
                if(null != car_status){
                    switch (car_status) {
                        case "Paid" -> {
                            if(ip.getPlan_status() != InstalmentPlan.PlanStatus.Approved || dp.getStatus() != Deposit.Status.Approved){
                                errorMsg += "The deposit and instalment plan needs to be approved before changing car status to PAID.<br>";
                            }else{
                                car.setStock_status(StockStatus.valueOf(car_status));
                                CarStockFacade.edit(car);
                                Sales sales = SalesFacade.find(sales_id);
                                sales.setSalesStatus(Sales.SalesStatus.Manufacturing);
                                SalesFacade.edit(sales);
                                LocalDateTime now = LocalDateTime.now();
                                LocalDateTime add_now = now.plusDays(30);
                                LocalDate next_due = add_now.toLocalDate();
                                ip.setNext_due_date(next_due);
                                InstalmentPlanFacade.edit(ip);
                            }
                        }
                        case "Booked" -> {
                            car.setStock_status(StockStatus.valueOf(car_status));
                            CarStockFacade.edit(car);
                            ip.setPlan_status(InstalmentPlan.PlanStatus.Pending_Approval);
                            InstalmentPlanFacade.edit(ip);
                        }
                        default -> {
                            car.setStock_status(StockStatus.valueOf(car_status));
                            CarStockFacade.edit(car);
                        }
                    }
                    
                }else{
                    throw new IOException("Cannot fetch car stock status");
                }  
            }
            
            if(!errorMsg.isEmpty()){                
                if(session.getAttribute("salesman_errorMsg") != null){
                    session.removeAttribute("salesman_errorMsg");
                    session.setAttribute("salesman_errorMsg", errorMsg);
                }else{
                    session.setAttribute("salesman_errorMsg", errorMsg);
                }
                CarStock car = CarStockFacade.find(car_id);
                
                switch (car.getStock_status()) {
                    case Interested -> response.sendRedirect(request.getContextPath() + "/salesman/sales_tickets.jsp");
                    case Booked -> response.sendRedirect(request.getContextPath() + "/salesman/booked_sales.jsp");
                    case Paid -> response.sendRedirect(request.getContextPath() + "/salesman/paid_sales.jsp");
                }
            }else{
                CarStock car = CarStockFacade.find(car_id);
                switch (car.getStock_status()) {
                    case Interested -> response.sendRedirect(request.getContextPath() + "/LoadTickets");
                    case Booked -> response.sendRedirect(request.getContextPath() + "/LoadBookedSales");
                    case Paid -> response.sendRedirect(request.getContextPath() + "/LoadPaidSales");
                }
            }           
        }catch(Exception e){
            System.out.println("Error in updating car status: " + e);
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
