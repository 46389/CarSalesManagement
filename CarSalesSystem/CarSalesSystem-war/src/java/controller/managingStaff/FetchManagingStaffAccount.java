/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.managingStaff;

import static controller.managingStaff.FetchCustomerAccount.validateManagerSession;
import facade.ManagingStaffFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.ManagingStaff;

/**
 *
 * @author Chew Jin Ni
 */
public class FetchManagingStaffAccount extends HttpServlet {

    @EJB
    private ManagingStaffFacade ManagingStaffFacade;
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
            String search = (String) session.getAttribute("filter_users");
            
            if(search == null || search.isEmpty() || "".equals(search)){   
                List<ManagingStaff> salesman = ManagingStaffFacade.findAll();

                if(session.getAttribute("managing_staff_list") != null){
                    session.removeAttribute("managing_staff_list");
                    session.setAttribute("managing_staff_list", salesman);
                }else{
                    session.setAttribute("managing_staff_list", salesman);
                }
            }else{
                List<ManagingStaff> managing_staff = ManagingStaffFacade.findByManagingStaffInfo(search);

                if(session.getAttribute("managing_staff_list") != null){
                    session.removeAttribute("managing_staff_list");
                    session.setAttribute("managing_staff_list", managing_staff);
                }else{
                    session.setAttribute("managing_staff_list", managing_staff);
                }
            }
            
            response.sendRedirect(request.getContextPath() + "/managingStaff/managing_staff_account.jsp");
        }catch(Exception e){
            System.out.println("Error in fetching managing staff account: " + e);
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
