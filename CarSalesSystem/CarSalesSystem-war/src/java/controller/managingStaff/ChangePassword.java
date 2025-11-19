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
import model.ManagingStaff;
import model.Users;
import util.Validation;
import static util.Validation.hashPassword;

/**
 *
 * @author Chew Jin Ni
 */
public class ChangePassword extends HttpServlet {

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
        
        String old_pd = request.getParameter("oldPassword");
        String new_pd = request.getParameter("newPassword");
        String confirm_pd = request.getParameter("confirmPassword");
        Users user = (Users) session.getAttribute("user");
        String user_id = user.getUser_id();

        String errorMsg = "";
        boolean indicator = false;

        try (PrintWriter out = response.getWriter()) {
            try{
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
                }

                if(!errorMsg.isEmpty() || !"".equals(errorMsg)) {
                    if(session.getAttribute("manager_errorMsg") != null){
                        session.removeAttribute("manager_errorMsg");
                        session.setAttribute("manager_errorMsg", errorMsg);
                    }else{
                        session.setAttribute("manager_errorMsg", errorMsg);
                    }
                    response.sendRedirect(request.getContextPath() + "/managingStaff/change_password.jsp");
                } else {
                    ManagingStaff manager = ManagingStaffFacade.find(user_id);
                    String hashed_password = hashPassword(confirm_pd);
                    manager.setPassword(hashed_password);
                    ManagingStaffFacade.edit(manager);

                    response.sendRedirect("login.jsp");
                }
            }catch(Exception e){
                errorMsg += "Error in changing password: " + e;
                session.setAttribute("manager_errorMsg", errorMsg);
                response.sendRedirect(request.getContextPath() + "/managingStaff/change_password.jsp");
            }
        }catch(Exception e){
            System.out.println("Error in changing password: " + e);
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
