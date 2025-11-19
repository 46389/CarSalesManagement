/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import facade.CustomerFacade;
import facade.UsersFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Path;
import model.Customer;
import model.Users;
import util.Validation;
import static util.Validation.hashPassword;

/**
 *
 * @author Chew Jin Ni
 */
@MultipartConfig(fileSizeThreshold = 1024* 1024 * 2,
                 maxFileSize = 1024 * 1024 * 10,
                 maxRequestSize = 1024 * 1024 * 50)

public class RegisterCustomer extends HttpServlet {

    @EJB
    private CustomerFacade CustomerFacade;
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
        response.setContentType("text/html;charset=UTF-8");
        
        String salary_slip_path = "";
        String driving_license_path = "";
        String salary_directory = "/customerFiles/salary_slip/";
        String license_directory = "/customerFiles/license/";

        
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String ic = request.getParameter("ic");
        String address = request.getParameter("address");
        String employment = request.getParameter("employment_status");
        String occupation = request.getParameter("occupation");
        Part salary_part = request.getPart("salary_slip");
        Part license_part = request.getPart("driving_license");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_password");
       
        String errorMsg = "";
        boolean indicator = false;
        
        try (PrintWriter out = response.getWriter()) {
            try{
                if(name == null || name.isEmpty()){
                    errorMsg += "Please fill in your full name.<br>";
                    indicator = true;
                }
                if(age <= 0){
                    errorMsg += "Please fill in your age.<br>";
                    indicator = true;
                }
                if(gender == null || gender.isEmpty()){
                    errorMsg += "Please fill in your gender.<br>";
                    indicator = true;
                }
                if(salary_part.getSize() > 0 && salary_part.getSubmittedFileName() != null){
                    String ori_filename = salary_part.getSubmittedFileName();
                    InputStream file_content = salary_part.getInputStream();
                    String upload_path = "D:/NetbeanProject/CarSalesSystem/CarSalesSystem-war/web" + salary_directory; 
                    Path salary_filename = Validation.setFilepath("salary_slip", upload_path, ori_filename);
                    salary_slip_path = salary_filename.toString();
                    Validation.saveFile(file_content, salary_filename);
                }else{
                    errorMsg += "Please upload your salary slip.<br>";
                    indicator = true;
                }
                if(license_part.getSize() > 0 && license_part.getSubmittedFileName() != null){
                    String ori_filename = license_part.getSubmittedFileName();
                    InputStream file_content = license_part.getInputStream();
                    String upload_path = "D:/NetbeanProject/CarSalesSystem/CarSalesSystem-war/web" + license_directory; 
                    Path license_filename = Validation.setFilepath("license", upload_path, ori_filename);
                    driving_license_path = license_filename.toString();
                    Validation.saveFile(file_content, license_filename);
                }else{
                    errorMsg += "Please upload your driving license.<br>";
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
                if(address == null || address.isEmpty()){
                    errorMsg += "Please fill in your address.<br>";
                    indicator = true;
                }
                if(employment == null || employment.isEmpty()){
                    errorMsg += "Please fill in your employment status.<br>";
                    indicator = true;
                }
                if(occupation == null || occupation.isEmpty()){
                    errorMsg += "Please fill in your occupation.<br>";
                    indicator = true;
                }
                if(username == null || username.isEmpty()){
                    errorMsg += "Please fill in your username.<br>";
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

                    Customer customer = CustomerFacade.find(username);
                    Users user_ic = UsersFacade.findIc(ic);
                    if(customer != null){
                        errorMsg += "Username existed. Please change to another username.<br>";
                    }
                    if(user_ic != null){
                        errorMsg += "IC existed. Please login with registered account.<br>";
                    }
                }
              
                if(errorMsg.isEmpty()) {
                    String hashed_password = hashPassword(password);
                    Customer cus = new Customer(age, Customer.Gender.valueOf(gender), address, Customer.EmpStatus.valueOf(employment), occupation, salary_slip_path, driving_license_path, username, hashed_password, email, name, phone, ic);
                    CustomerFacade.create(cus);
                    response.sendRedirect("login.jsp");
                } else {
                    request.setAttribute("errorMsg", errorMsg);
                    // Pass form data back to the JSP
                    request.setAttribute("name", name);
                    request.setAttribute("age", age);
                    request.setAttribute("gender", gender);
                    request.setAttribute("email", email);
                    request.setAttribute("phone", phone);
                    request.setAttribute("ic", ic);
                    request.setAttribute("address", address);
                    request.setAttribute("employment_status", employment);
                    request.setAttribute("occupation", occupation);
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("register_customer.jsp").include(request, response);
                }
            }catch(ServletException | IOException e){
                System.out.println("Error in registering customer:" + e);
            }
        }catch(Exception e){
            System.out.println("Error in registering customer: " + e );
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
