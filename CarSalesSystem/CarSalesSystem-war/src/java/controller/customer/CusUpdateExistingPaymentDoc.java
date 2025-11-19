/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import static controller.customer.Home.validateCustomerSession;
import facade.InstalmentPaymentFacade;
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
import java.time.LocalDateTime;
import model.InstalmentPayment;
import model.Users;
import util.Validation;

/**
 *
 * @author Chew Jin Ni
 */
@MultipartConfig(fileSizeThreshold = 1024* 1024 * 2,
                 maxFileSize = 1024 * 1024 * 10,
                 maxRequestSize = 1024 * 1024 * 50)
public class CusUpdateExistingPaymentDoc extends HttpServlet {
    
    @EJB
    private InstalmentPaymentFacade InstalmentPaymentFacade;
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
        String payment_id = request.getParameter("payment_id");
        Part payment_doc = request.getPart("doc");
        try (PrintWriter out = response.getWriter()) {
            String instalment_doc_path = "";
            String instalment_doc_directory = "/customerFiles/instalment_payment_doc/";
            String errorMsg = "";

            if(payment_doc.getSize() > 0 && payment_doc.getSubmittedFileName() != null){
                String ori_filename = payment_doc.getSubmittedFileName();
                InputStream file_content = payment_doc.getInputStream();
                String upload_path = "D:/NetbeanProject/CarSalesSystem/CarSalesSystem-war/web" + instalment_doc_directory; 
                Path deposit_filename = Validation.setFilepath("instalment_payment_doc", upload_path, ori_filename);
                instalment_doc_path = deposit_filename.toString();
                Validation.saveFile(file_content, deposit_filename);
            
                InstalmentPayment payment = InstalmentPaymentFacade.find(payment_id);
                payment.setReceipt(instalment_doc_path);
                payment.setCreated_at(LocalDateTime.now());
                InstalmentPaymentFacade.edit(payment);
            }else{
                errorMsg += "Please upload valid instalment payment document.";
            }
            
            if(errorMsg.isEmpty()){
                response.sendRedirect(request.getContextPath() + "/LoadOwnedCars");
            }else{
                if(session.getAttribute("customer_errorMsg") != null){
                    session.removeAttribute("customer_errorMsg");
                    session.setAttribute("customer_errorMsg", errorMsg);
                }else{
                    session.setAttribute("customer_errorMsg", errorMsg);
                }
                response.sendRedirect(request.getContextPath() + "/customer/owned_cars.jsp");
            }
        }catch(Exception e){
            System.out.println("Error in updating existing payment document: " + e);
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
