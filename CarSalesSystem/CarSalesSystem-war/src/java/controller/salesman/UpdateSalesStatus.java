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
import model.CarStock;
import model.CarStock.StockStatus;
import model.Deposit;
import model.InstalmentPlan;
import model.Sales;
import model.Sales.SalesStatus;

/**
 *
 * @author Chew Jin Ni
 */
public class UpdateSalesStatus extends HttpServlet {

    @EJB
    private SalesFacade SalesFacade;
    @EJB
    private DepositFacade DepositFacade;
    @EJB
    private InstalmentPlanFacade InstalmentPlanFacade;
    @EJB
    private CarStockFacade CarStockFacade;
    
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
        String sales_status = request.getParameter("sales_status");
        String car_id = request.getParameter("car_id");
        String errorMsg = "";
                
        try (PrintWriter out = response.getWriter()) {

            Sales sales = SalesFacade.find(sales_id);
            if("Cancelled".equals(sales_status)){
                
                sales.setSalesStatus(SalesStatus.valueOf(sales_status));
                SalesFacade.edit(sales);
                CarStock car = CarStockFacade.find(car_id);
                car.setStock_status(StockStatus.Available);
                CarStockFacade.edit(car);
                InstalmentPlan ip = InstalmentPlanFacade.getInstalmentPlanBySales(sales_id);
                if(ip != null){
                    InstalmentPlanFacade.remove(ip);
                }
                Deposit dp = DepositFacade.getDepositBySales(sales_id);
                if(dp != null){
                    DepositFacade.remove(dp);
                }       
                response.sendRedirect(request.getContextPath() + "/LoadCancelledSales");
            }else{                
                if("Pending_Payment_Approval".equals(sales_status)){
                    Deposit dp = DepositFacade.getDepositBySales(sales_id);
                    if(dp != null && dp.getDeposit_doc() != null){
                        sales.setSalesStatus(SalesStatus.valueOf(sales_status));
                        SalesFacade.edit(sales);
                    }else{
                        errorMsg += "Please upload respective deposit document before proceeding to approval.";
                        if(session.getAttribute("salesman_errorMsg") != null){
                            session.removeAttribute("salesman_errorMsg");
                            session.setAttribute("salesman_errorMsg", errorMsg);
                        }else{
                            session.setAttribute("salesman_errorMsg", errorMsg);
                        }
                        response.sendRedirect(request.getContextPath() + "/salesman/booked_sales.jsp");
                    }
                }else{
                    sales.setSalesStatus(SalesStatus.valueOf(sales_status));
                    SalesFacade.edit(sales);   
                }
                            
                StockStatus status = sales.getCar_id().getStock_status();
                
                if(null != status) switch (status) {
                    case Interested -> response.sendRedirect(request.getContextPath() + "/LoadTickets");
                    case Booked -> response.sendRedirect(request.getContextPath() + "/LoadBookedSales");
                    case Paid -> response.sendRedirect(request.getContextPath() + "/LoadPaidSales");
                }else{
                    errorMsg += "Fail to fetch car status.";
                    session.setAttribute("salesman_errorMsg", errorMsg);
                    response.sendRedirect(request.getContextPath() + "/salesman/booked_sales.jsp");
                }
            } 
        }catch(Exception e){
            System.out.println("Error in updating sales status: " + e);
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
