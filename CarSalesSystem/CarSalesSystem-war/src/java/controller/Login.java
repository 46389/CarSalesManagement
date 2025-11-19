/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import static model.Users.Role.Customer;
import static model.Users.Role.Managing_Staff;
import static model.Users.Role.Salesman;
import model.Users.State;
import static util.Validation.checkPassword;

/**
 *
 * @author Chew Jin Ni
 */
public class Login extends HttpServlet {

    @EJB
    private UsersFacade UserFacade; 
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
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
            
        try (PrintWriter out = response.getWriter()) {
          
            if(username == null || username.isEmpty()){
                request.setAttribute("errorMsg", "Please fill in your username.");
                request.getRequestDispatcher("login.jsp").include(request, response);
            }else if(password == null || password.isEmpty()){
                request.setAttribute("errorMsg", "Please fill in your password.");
                request.getRequestDispatcher("login.jsp").include(request, response);    
            }else{
                Users user = UserFacade.find(username);
                if(user == null){
                    request.setAttribute("errorMsg", "Username not found.");
                    request.getRequestDispatcher("login.jsp").include(request, response);
                }else{
                    boolean matched = checkPassword(password, user.getPassword());
                    if(!matched){
                        request.setAttribute("errorMsg", "Password does not match your username.");
                        request.getRequestDispatcher("login.jsp").include(request, response);
                    }else{                    
                        if(user.getState()== State.Active){
                            if(null != user.getApproval()){
                                switch (user.getApproval()) {
                                    case Approved -> {
                                        HttpSession session = request.getSession();
                                        session.setAttribute("user", user);

                                        switch (user.getRole()) {
                                            case Managing_Staff -> request.getRequestDispatcher("/FetchCustomerAccount").forward(request, response);
                                            case Salesman -> request.getRequestDispatcher("/LoadTickets").forward(request, response);
                                            case Customer -> request.getRequestDispatcher("/CustomerHome").forward(request, response);
                                            default -> {
                                                request.setAttribute("errorMsg", "Error in user role. Please contact customer service.");
                                                request.getRequestDispatcher("login.jsp").include(request, response);
                                            }
                                        }
                                    }
                                    case Pending -> {
                                        request.setAttribute("errorMsg", "Your account is still pending for approval. Thanks for your patience.");
                                        request.getRequestDispatcher("login.jsp").include(request, response);
                                    }
                                    case Rejected -> {
                                        request.setAttribute("errorMsg", "Your account registration has been rejected. Please contact customer service.");
                                        request.getRequestDispatcher("login.jsp").include(request, response);
                                    }
                                }
                            }
                        }else if(user.getState() == State.Inactive){
                            request.setAttribute("errorMsg", "You have a deleted account. Please contact customer service to restore your account.");
                            request.getRequestDispatcher("login.jsp").include(request, response);
                        }      
                    }
                }
            }                    
        }catch(Exception e){
            System.out.println("Error in Login servlet: " + e);
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
