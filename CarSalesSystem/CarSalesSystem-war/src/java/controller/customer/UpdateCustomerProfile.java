/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import static controller.customer.Home.validateCustomerSession;
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
import jakarta.servlet.http.HttpSession;
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
public class UpdateCustomerProfile extends HttpServlet {

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
        
        HttpSession session = request.getSession();

        if (!validateCustomerSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        
        String user_id = request.getParameter("username");
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String ic = request.getParameter("ic");
        String address = request.getParameter("address");
        String employment = request.getParameter("employment");
        String occupation = request.getParameter("occupation");
        Part salary_part = request.getPart("salary_slip");
        Part license_part = request.getPart("driving_license");
        String old_pd = request.getParameter("oldPassword");
        String new_pd = request.getParameter("newPassword");
        String confirm_pd = request.getParameter("confirmPassword");
        String type = request.getParameter("type");
        String errorMsg = "";
        boolean indicator = false;
        
        String salary_slip_path = "";
        String driving_license_path = "";
        String salary_directory = "/customerFiles/salary_slip/";
        String license_directory = "/customerFiles/license/";
        
        Customer login_cus = (Customer) session.getAttribute("user");
        
        try (PrintWriter out = response.getWriter()) {
            if("edit_profile".equals(type)){
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
                }
                if(license_part.getSize() > 0 && license_part.getSubmittedFileName() != null){
                    String ori_filename = license_part.getSubmittedFileName();
                    InputStream file_content = license_part.getInputStream();
                    String upload_path = "D:/NetbeanProject/CarSalesSystem/CarSalesSystem-war/web" + license_directory; 
                    Path license_filename = Validation.setFilepath("license", upload_path, ori_filename);
                    driving_license_path = license_filename.toString();
                    Validation.saveFile(file_content, license_filename);
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
                
                if(errorMsg.isEmpty()) {
                    Customer cus = CustomerFacade.find(user_id);
                    cus.setName(name);
                    cus.setAge(age);
                    cus.setGender(Customer.Gender.valueOf(gender));
                    cus.setAddress(address);
                    cus.setOccupation(occupation);
                    cus.setIc(ic);
                    cus.setEmail(email);
                    cus.setPhone(phone);
                    cus.setEmployment_status(Customer.EmpStatus.valueOf(employment));
                                        
                    if(!salary_slip_path.isEmpty()){
                        cus.setSalary_slip(salary_slip_path);
                    }
                    if(!driving_license_path.isEmpty()){
                        cus.setDriving_license(driving_license_path);
                    }
                    CustomerFacade.edit(cus);
                    
                    login_cus.setName(name);
                    login_cus.setAge(age);
                    login_cus.setGender(Customer.Gender.valueOf(gender));
                    login_cus.setAddress(address);
                    login_cus.setOccupation(occupation);
                    login_cus.setIc(ic);
                    login_cus.setEmail(email);
                    login_cus.setPhone(phone);
                    login_cus.setEmployment_status(Customer.EmpStatus.valueOf(employment));
                    if(!salary_slip_path.isEmpty()){
                        login_cus.setSalary_slip(salary_slip_path);
                    }
                    if(!driving_license_path.isEmpty()){
                        login_cus.setDriving_license(driving_license_path);
                    }
                    
                    response.sendRedirect(request.getContextPath() + "/customer/profile.jsp");
                } else {
                    if(session.getAttribute("customer_errorMsg") != null){
                        session.removeAttribute("customer_errorMsg");
                        session.setAttribute("customer_errorMsg", errorMsg);
                    }else{
                        session.setAttribute("customer_errorMsg", errorMsg);
                    }
                    response.sendRedirect(request.getContextPath() + "/customer/profile.jsp");
                }
                
            }else if("edit_password".equals(type)){
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
                    Customer cus = CustomerFacade.find(user_id);
                    if(!Validation.checkPassword(old_pd, cus.getPassword())){
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
                    if(session.getAttribute("customer_errorMsg") != null){
                        session.removeAttribute("customer_errorMsg");
                        session.setAttribute("customer_errorMsg", errorMsg);
                    }else{
                        session.setAttribute("customer_errorMsg", errorMsg);
                    }
                    response.sendRedirect(request.getContextPath() + "/customer/profile.jsp");
                } else {
                    Customer customer = CustomerFacade.find(user_id);
                    String hashed_password = hashPassword(confirm_pd);
                    customer.setPassword(hashed_password);
                    CustomerFacade.edit(customer);

                    response.sendRedirect("login.jsp");
                }
            }
        }catch(Exception e){
            System.out.println("Error in updating customer profile: " + e);
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
