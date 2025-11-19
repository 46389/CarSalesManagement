/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.managingStaff;

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
import model.Customer;
import model.Users;

/**
 *
 * @author Chew Jin Ni
 */
public class FetchCustomerAccount extends HttpServlet {
    
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
        
        HttpSession session = request.getSession();
        if (!validateManagerSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String search = (String) session.getAttribute("filter_users");
            
            if(search == null || search.isEmpty() || "".equals(search)){                
                List<Customer> customers = CustomerFacade.findAll();

                if(session.getAttribute("customers_list") != null){
                    session.removeAttribute("customers_list");
                    session.setAttribute("customers_list", customers);
                }else{
                    session.setAttribute("customers_list", customers);
                }
            }else{
                List<Customer> customers = CustomerFacade.findByCustomerInfo(search);

                if(session.getAttribute("customers_list") != null){
                    session.removeAttribute("customers_list");
                    session.setAttribute("customers_list", customers);
                }else{
                    session.setAttribute("customers_list", customers);
                }
            }

            response.sendRedirect("managingStaff/customer_account.jsp");
        }catch(Exception e){
            System.out.println("Error in fetching customer account: " + e);
        }
    }
    
    public static boolean validateManagerSession(HttpServletRequest request, HttpServletResponse response) {
        // Validate session and role
        if (request.getSession(false) != null && request.getSession().getAttribute("user") != null) {
            Users user = (Users) request.getSession().getAttribute("user");
            return user.getRole() == Users.Role.Managing_Staff;
        }
        return false;
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
