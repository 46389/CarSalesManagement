/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import facade.BlacklistsFacade;
import facade.CustomerFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Blacklists;
import model.Customer;
import model.Users;
import static model.Users.Role.Managing_Staff;
import static model.Users.Role.Salesman;

/**
 *
 * @author Chew Jin Ni
 */
public class FetchBlacklistCustomers extends HttpServlet {

    @EJB
    private BlacklistsFacade BlacklistsFacade;
    @EJB
    private CustomerFacade CustomerFacade;
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
        
        String errorMsg = "";
        
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            
            try{
                List<Blacklists> blacklists = BlacklistsFacade.findAll();
                List<Customer> customer = CustomerFacade.findAll();

                if(session.getAttribute("blacklist_customers") != null){
                    session.removeAttribute("blacklist_customers");
                    session.setAttribute("blacklist_customers", blacklists);
                }else{
                    session.setAttribute("blacklist_customers", blacklists);
                }

                if(session.getAttribute("customers") != null){
                    session.removeAttribute("customers");
                    session.setAttribute("customers", customer);
                }else{
                    session.setAttribute("customers", customer);
                }
                switch(user.getRole()) {
                    case Managing_Staff -> response.sendRedirect(request.getContextPath() + "/managingStaff/blacklisted_customer.jsp");
                    case Salesman -> response.sendRedirect(request.getContextPath() + "/salesman/blacklist_customers.jsp");
                }
            }catch(Exception e){
                errorMsg += "Error in fetching blacklisted customers: " + e;
                switch(user.getRole()) {
                    case Managing_Staff -> {
                        session.setAttribute("manager_errorMsg", errorMsg);
                        response.sendRedirect(request.getContextPath() + "/managingStaff/blacklisted_customer.jsp");
                    }
                    case Salesman -> {
                        session.setAttribute("salesman_errorMsg", errorMsg);
                        response.sendRedirect(request.getContextPath() + "/salesman/blacklist_customers.jsp");
                    }
                }            
            }
        }catch(Exception e){
            System.out.println("Error in fetching blacklisted customers: " + e);
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
