/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.salesman;

import static controller.salesman.LoadTickets.validateSalesmanSession;
import facade.DepositFacade;
import facade.SalesFacade;
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
import model.Deposit;
import model.Deposit.Status;
import model.Sales;
import model.Sales.SalesStatus;
import util.Validation;

/**
 *
 * @author Chew Jin Ni
 */
@MultipartConfig(fileSizeThreshold = 1024* 1024 * 2,
                 maxFileSize = 1024 * 1024 * 10,
                 maxRequestSize = 1024 * 1024 * 50)
public class UpdateDepositDoc extends HttpServlet {

    @EJB
    private DepositFacade DepositFacade;
    @EJB
    private SalesFacade SalesFacade;
    
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
        
        String sales_id = request.getParameter("sales_id");
        String deposit_id = request.getParameter("deposit_id");
        Part deposit_doc = request.getPart("deposit_doc");
        
        String deposit_doc_path = "";
        String deposit_doc_directory = "/customerFiles/deposit_doc/";
        
        try (PrintWriter out = response.getWriter()) {
            String errorMsg = "";
            if(deposit_doc.getSize() > 0 && deposit_doc.getSubmittedFileName() != null){
                String ori_filename = deposit_doc.getSubmittedFileName();
                InputStream file_content = deposit_doc.getInputStream();
                String upload_path = "D:/NetbeanProject/CarSalesSystem/CarSalesSystem-war/web" + deposit_doc_directory; 
                Path deposit_filename = Validation.setFilepath("deposit_doc", upload_path, ori_filename);
                deposit_doc_path = deposit_filename.toString();
                Validation.saveFile(file_content, deposit_filename);
  
                Deposit dep = DepositFacade.find(deposit_id);
                dep.setDeposit_doc(deposit_doc_path);
                dep.setStatus(Status.Pending_Approval);
                DepositFacade.edit(dep);
             
                Sales sales = SalesFacade.find(sales_id);
                sales.setSalesStatus(SalesStatus.Pending_Payment_Approval);
                SalesFacade.edit(sales);

            }else{
                errorMsg += "Please upload valid deposit document.";
                if(session.getAttribute("salesman_errorMsg") != null){
                    session.removeAttribute("salesman_errorMsg");
                    session.setAttribute("salesman_errorMsg", errorMsg);
                }else{
                    session.setAttribute("salesman_errorMsg", errorMsg);
                }
            }
            
            response.sendRedirect(request.getContextPath() + "/LoadBookedSales");
 
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
