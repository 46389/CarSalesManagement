/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.managingStaff;

import static controller.managingStaff.FetchCustomerAccount.validateManagerSession;
import facade.UsersFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Users;
import static model.Users.Role.Managing_Staff;
import static model.Users.Role.Salesman;
import util.Validation;

/**
 *
 * @author Chew Jin Ni
 */
public class EditStaffAccount extends HttpServlet {

    @EJB
    private UsersFacade UsersFacade;
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
            String user_id = request.getParameter("user_id");
            String name = request.getParameter("name");
            String ic = request.getParameter("ic");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String errorMsg = "";
            boolean indicator = false;
            
            if(name == null || name.isEmpty()){
                errorMsg += "Please fill in staff's full name.<br>";
                indicator = true;
            }
            if(ic == null || ic.isEmpty()){
                errorMsg += "Please fill in staff's ic number.<br>";
                indicator = true;
            }
            if(phone == null || phone.isEmpty()){
                errorMsg += "Please fill in staff's phone number.<br>";
                indicator = true;
            }
            if(email == null || email.isEmpty()){
                errorMsg += "Please fill in staff's email address.<br>";
                indicator = true;
            }
            
            if(!indicator){
                if(!Validation.validateName(name)){
                    errorMsg += "Invalid name format.<br>";
                }

                if(!Validation.validateIC(ic)){
                    errorMsg += "Invalid IC format.<br>";
                }

                if(!Validation.validateEmail(email)){
                    errorMsg += "Invalid email format.<br>";
                }

                if(!Validation.validateContact(phone)){
                    errorMsg += "Invalid phone format.<br>";
                }
                
                Users user_ic = UsersFacade.findIc(ic);
                if(user_ic != null && !user_ic.getUser_id().equals(user_id)){
                   errorMsg += "IC existed. Please check again.<br>";
                }
            }
            
            if(errorMsg.isEmpty()){
                Users user = UsersFacade.find(user_id);
                user.setName(name);
                user.setIc(ic);
                user.setPhone(phone);
                user.setEmail(email);
                UsersFacade.edit(user);
                switch(user.getRole()) {
                    case Managing_Staff -> response.sendRedirect(request.getContextPath() + "/FetchManagingStaffAccount");
                    case Salesman -> response.sendRedirect(request.getContextPath() + "/FetchSalesmanAccount");
                }
            }else{
                Users user = UsersFacade.find(user_id);
                if(session.getAttribute("manager_errorMsg") != null){
                    session.removeAttribute("manager_errorMsg");
                    session.setAttribute("manager_errorMsg", errorMsg);
                }else{
                    session.setAttribute("manager_errorMsg", errorMsg);
                }
                switch(user.getRole()) {
                    case Managing_Staff -> {
                        response.sendRedirect(request.getContextPath() + "/managingStaff/managing_staff_account.jsp");   
                    }
                    case Salesman -> {
                        response.sendRedirect(request.getContextPath() + "/managingStaff/salesman_account.jsp");
                    }
                }
            } 
        }catch(Exception e){
            System.out.println("Error in editing staff account: " + e);
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
