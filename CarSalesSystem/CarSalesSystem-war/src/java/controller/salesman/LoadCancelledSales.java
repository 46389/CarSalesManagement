/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.salesman;

import static controller.salesman.LoadTickets.validateSalesmanSession;
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
import model.Sales;
import model.Users;
import util.Ticket;

/**
 *
 * @author Chew Jin Ni
 */
public class LoadCancelledSales extends HttpServlet {
    
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
        try (PrintWriter out = response.getWriter()) {
           
            Users salesman = (Users) session.getAttribute("user");
            String salesman_id = salesman.getUser_id();
            List<Ticket> ticketList = new ArrayList<>();
            List<Object[]> fetchLists = SalesFacade.findBySalesmanAndSalesStatus(salesman_id, Sales.SalesStatus.Cancelled);
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
                ticketList.add(ticket);
            }

            if(session.getAttribute("cancelled_list") != null){
                session.removeAttribute("cancelled_list");
                session.setAttribute("cancelled_list", ticketList);
            }else{
                session.setAttribute("cancelled_list", ticketList);
            }

            response.sendRedirect("salesman/cancelled_sales.jsp");    
                
        }catch(Exception e){
            System.out.println("Error in loading cancelled sales: " + e);
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
