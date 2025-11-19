/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import static controller.customer.Home.validateCustomerSession;
import facade.CarStockFacade;
import facade.CustomerFacade;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.CarStock;
import model.CarStock.StockStatus;
import model.Customer;
import model.Sales;
import model.Salesman;
import model.Users;

/**
 *
 * @author Chew Jin Ni
 */
public class Inquiry extends HttpServlet {

    @EJB
    private SalesFacade SalesFacade;
    @EJB
    private SalesmanFacade SalesmanFacade;
    @EJB
    private CustomerFacade CustomerFacade;
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

        if (!validateCustomerSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            Users user = (Users) session.getAttribute("user");
            String user_id = user.getUser_id();
            String car_id = request.getParameter("car_type_id");
            double price = Double.parseDouble(request.getParameter("car_price"));
            
            
            List<Salesman> salesmen = SalesmanFacade.findApprovedSalesman(Users.Status.Approved, Users.State.Active);
            List<Sales> sales = SalesFacade.findAll();
            
            Map<String, Integer> salesCountMap = new HashMap<>();
            for (Users salesman : salesmen) {
                salesCountMap.put(salesman.getUser_id(), 0);
            }
            
            for (Sales sale : sales) {
                String salesmanId = sale.getSalesman().getUser_id();
                if (salesCountMap.containsKey(salesmanId)) {
                    salesCountMap.put(salesmanId, salesCountMap.get(salesmanId) + 1);
                }
            }
            
            String free_slm = SalesFacade.findingLeastSalesSalesman(salesCountMap);
            
            Salesman free_salesman = SalesmanFacade.find(free_slm);
            Customer cus = CustomerFacade.find(user_id);
            
            List<CarStock> carstock = CarStockFacade.getCarStockByCarType(car_id, StockStatus.Available);
            
            if(carstock != null && !carstock.isEmpty()){
                CarStock available_carstock = carstock.get(0);
                SalesFacade.create(new Sales(available_carstock, cus, free_salesman, 1, price));
                available_carstock.setStock_status(StockStatus.Interested);
                CarStockFacade.edit(available_carstock);
                response.sendRedirect(request.getContextPath() + "/LoadInterestedCars");
            }else{
                String msg = "Sorry, this car is out of stock.";
                session.setAttribute("customer_errorMsg", msg);
                response.sendRedirect("customer/customer_home.jsp");
            }
     
        }catch(Exception e){
            System.out.println("Error in submitting inquiries: " + e);
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
