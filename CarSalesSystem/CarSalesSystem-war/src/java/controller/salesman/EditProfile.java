/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.salesman;

import static controller.salesman.LoadTickets.validateSalesmanSession;
import facade.SalesmanFacade;
import facade.UsersFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Salesman;
import model.Users;
import util.Validation;
import static util.Validation.hashPassword;

/**
 *
 * @author Chew Jin Ni
 */
public class EditProfile extends HttpServlet {

    @EJB
    private UsersFacade UsersFacade;
    @EJB
    private SalesmanFacade SalesmanFacade;
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
        
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String ic = request.getParameter("ic");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String old_pd = request.getParameter("oldPassword");
        String new_pd = request.getParameter("newPassword");
        String confirm_pd = request.getParameter("confirmPassword");
        
        try (PrintWriter out = response.getWriter()) {
           
            Users user = (Users) session.getAttribute("user");
            String user_id = user.getUser_id();
            
            if("edit_profile".equals(type)){
                
                String errorMsg = "";
                boolean indicator = false;
                
                if(name == null || name.isEmpty()){
                    errorMsg += "Please fill in your full name.<br>";
                    indicator = true;
                }
                if(ic == null || ic.isEmpty()){
                    errorMsg += "Please fill in your ic number.<br>";
                    indicator = true;
                }
                if(phone == null || phone.isEmpty()){
                    errorMsg += "Please fill in your phone number.<br>";
                    indicator = true;
                }
                if(email == null || email.isEmpty()){
                    errorMsg += "Please fill in your email address.<br>";
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
                    
                if(!errorMsg.isEmpty() || !"".equals(errorMsg)) {
                    if(session.getAttribute("profile_errorMsg") != null){
                        session.removeAttribute("profile_errorMsg");
                        session.setAttribute("profile_errorMsg", errorMsg);
                    }else{
                        session.setAttribute("profile_errorMsg", errorMsg);
                    }
                    response.sendRedirect(request.getContextPath() + "/salesman/profile.jsp");
                } else {
                    Salesman salesman = SalesmanFacade.find(user_id);
                    salesman.setName(name);
                    salesman.setIc(ic);
                    salesman.setEmail(email);
                    salesman.setPhone(phone); 
                    
                    SalesmanFacade.edit(salesman);
                    
                    user.setName(name);
                    user.setIc(ic); 
                    user.setEmail(email); 
                    user.setPhone(phone); 
                    
                    response.sendRedirect(request.getContextPath() + "/salesman/profile.jsp");
                }
                
            }else if("edit_password".equals(type)){
                String errorMsg = "";
                boolean indicator = false;
                
                if(old_pd == null || old_pd.isEmpty()){
                    errorMsg += "Please fill in your current password.<br>";
                    indicator = true;
                }
                
                if(new_pd == null || new_pd.isEmpty()){
                    errorMsg += "Please fill in your new password.<br>";
                    indicator = true;
                }
                
                if(confirm_pd == null || confirm_pd.isEmpty()){
                    errorMsg += "Please retype your new password.<br>";
                    indicator = true;
                }
                
                if(!indicator){
                    
                    if(!Validation.checkPassword(old_pd, user.getPassword())){
                        errorMsg += "Old password is incorrect.<br>";
                    }
                    
                    if(!confirm_pd.equals(new_pd)){
                        errorMsg += "New Password and Confirm Password do not match.<br>";
                    }

                    if(!Validation.validatePassword(confirm_pd)){
                        errorMsg += "Please meet the password requirements <br>";
                    }
                    
                    if(!errorMsg.isEmpty() || !"".equals(errorMsg)) {
                        if(session.getAttribute("salesman_errorMsg") != null){
                            session.removeAttribute("salesman_errorMsg");
                            session.setAttribute("salesman_errorMsg", errorMsg);
                        }else{
                            session.setAttribute("salesman_errorMsg", errorMsg);
                        }
                        response.sendRedirect(request.getContextPath() + "/salesman/profile.jsp");
                    } else {
                        Salesman salesman = SalesmanFacade.find(user_id);
                        String hashed_password = hashPassword(confirm_pd);
                        salesman.setPassword(hashed_password);
                        SalesmanFacade.edit(salesman);
                        
                        response.sendRedirect("login.jsp");
                    }
                }
            }
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
