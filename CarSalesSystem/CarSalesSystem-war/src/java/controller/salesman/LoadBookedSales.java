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
import java.util.ArrayList;
import java.util.List;
import model.CarStock;
import model.Deposit;
import model.InstalmentPlan;
import model.Sales.SalesStatus;
import model.Users;
import util.Ticket;

/**
 *
 * @author Chew Jin Ni
 */
public class LoadBookedSales extends HttpServlet {

    @EJB
    private SalesFacade SalesFacade;
    @EJB
    private DepositFacade DepositFacade;
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
        try (PrintWriter out = response.getWriter()) {
            
            Users salesman = (Users) session.getAttribute("user");
            String salesman_id = salesman.getUser_id();
            List<Ticket> ticketList = new ArrayList<>();
            List<Object[]> fetchLists = SalesFacade.findBySalesmanAndCarStatus(salesman_id, CarStock.StockStatus.Booked, SalesStatus.Cancelled);
            for(Object[] list : fetchLists){ 
                Ticket ticket = new Ticket();
                ticket.setSales_id((String) list[0]);
                ticket.setCustomer_id((String) list[1]);
                ticket.setCustomer_name((String) list[2]);
                ticket.setCustomer_ic((String) list[3]);
                ticket.setCustomer_phone((String) list[4]);
                ticket.setCustomer_email((String) list[5]);
                ticket.setCar_id((String) list[6]);
                ticket.setModel_name((String) list[7]);
                ticket.setCar_colour((String) list[8]);
                ticket.setCar_price((double) (list[9]));
                ticket.setSales_status((String) list[10].toString());
                ticket.setCar_status((String) list[11].toString());
                ticketList.add(ticket);
            }
            
            if(session.getAttribute("paid_saleslist") != null){
                session.removeAttribute("paid_saleslist");
                session.setAttribute("paid_saleslist", ticketList);
            }else{
                session.setAttribute("paid_saleslist", ticketList);
            }
            
            List<Deposit> dp_list = DepositFacade.findAll();
            
            if(session.getAttribute("dp_list") != null){
                session.removeAttribute("dp_list");
                session.setAttribute("dp_list", dp_list);
            }else{
                session.setAttribute("dp_list", dp_list);
            }
            
            List<InstalmentPlan> ip_list = InstalmentPlanFacade.findAll();
            if(session.getAttribute("ip_list") != null){
                session.removeAttribute("ip_list");
                session.setAttribute("ip_list", ip_list);
            }else{
                session.setAttribute("ip_list", ip_list);
            }
            
            response.sendRedirect("salesman/booked_sales.jsp");
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
