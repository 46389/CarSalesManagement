/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import facade.ManagingStaffFacade;
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
import model.ManagingStaff;
import model.Salesman;
import model.Users;
import model.Users.Role;
import util.Validation;
import static util.Validation.hashPassword;

/**
 *
 * @author Chew Jin Ni
 */
public class RegisterStaff extends HttpServlet {
    
    @EJB
    private UsersFacade UserFacade;
    @EJB
    private SalesmanFacade SalesmanFacade;
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        Role login_role = null;
        if(session.getAttribute("user") != null){
            Users user = (Users) session.getAttribute("user");
            login_role = user.getRole();
        }
        String name = request.getParameter("name");
        String ic = request.getParameter("ic");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_password");
        String errorMsg = "";
        boolean indicator = false;

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
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
            if(role == null || role.isEmpty()){
                errorMsg += "Please select your role.<br>";
                indicator = true;
            }
            if(password == null || password.isEmpty()){
                errorMsg += "Please fill in your password.<br>";
                indicator = true;
            }
            if(confirm_password == null || confirm_password.isEmpty()){
                errorMsg += "Please enter your password again in confirm password box.<br>";
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
                
                if(!password.equals(confirm_password)){
                    errorMsg += "Password do not match.<br>";
                }
                
                if(!Validation.validatePassword(password)){
                    errorMsg += "Please meet the password requirements <br>";
                }
                
                Users staff = UserFacade.find(username);
                Users find_ic = UserFacade.findIc(ic);
                if(staff != null){
                    errorMsg += "Username existed. Please change to another username.<br>";
                }
                if(find_ic != null){
                    errorMsg += "IC existed. Please login with registered account.<br>";
                }
            }
       
            if(!errorMsg.isEmpty() || !"".equals(errorMsg)) {
                request.setAttribute("errorMsg", errorMsg);
                request.setAttribute("name", name);
                request.setAttribute("ic", ic);
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("role", role);
                request.setAttribute("username", username);
                request.getRequestDispatcher("register_staff.jsp").forward(request, response);
            } else {
                out.println("okk");
                String hashed_password = hashPassword(password);
                if("Salesman".equals(role)){
                    SalesmanFacade.create(new Salesman(username, hashed_password, email, name, phone, ic));
                    if(login_role == Role.Managing_Staff){
                        response.sendRedirect(request.getContextPath() + "/FetchSalesmanAccount");
                    }
                }else if("Managing_Staff".equals(role)){
                    ManagingStaffFacade.create(new ManagingStaff(username, hashed_password, email, name, phone, ic));
                    if(login_role == Role.Managing_Staff){
                        response.sendRedirect(request.getContextPath() + "/FetchManagingStaffAccount");
                    }
                }
                response.sendRedirect("login.jsp");
            }
        }catch(Exception e){
            System.out.println("Error in registering staff: " + e);
        }
    }   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
