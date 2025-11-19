/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.salesman;

import static controller.salesman.LoadTickets.validateSalesmanSession;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Properties;

/**
 *
 * @author Chew Jin Ni
 */
public class SendEmail extends HttpServlet {

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
        
        HttpSession httpsession = request.getSession();
        if (!validateSalesmanSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String to_email = request.getParameter("email");
        String customer_name = request.getParameter("name");
        String due_date = request.getParameter("due_date");
        String payment = request.getParameter("monthly_payment");
        String model_name = request.getParameter("model_name");
        String subject = "Reminder for Car Instalment Payment";
        String body = "Dear " + customer_name + ",\n\n\tKindly reminded that your monthly instalment payment for " + model_name + " is due. \n\n\tThe payment due date is on " + due_date + ". \n\n\tThe payment amount is RM " + payment + ".\n\n\tThank you and have a nice day!\n\nYours sincerely,\nCar Elite Team";
        String errorMsg = "";
        try (PrintWriter out = response.getWriter()) {
            Properties props = new Properties();
            props.load(getServletContext().getResourceAsStream("/WEB-INF/email.properties"));
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        props.getProperty("mail.username"),
                        props.getProperty("mail.password")
                    );
                }
            });
            
            try {
                // Create the email message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(props.getProperty("mail.username")));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to_email));
                message.setSubject(subject);
                message.setText(body);
                
                props.put("mail.smtp.ssl.trust", props.getProperty("mail.smtp.host"));

                // Send the email
                Transport.send(message);
                response.sendRedirect(request.getContextPath() + "/LoadPaidSales");
            } catch (MessagingException e) {
                errorMsg += "Fail to send email to customer: " + e;
                if(httpsession.getAttribute("salesman_errorMsg") != null){
                    httpsession.removeAttribute("salesman_errorMsg");
                    httpsession.setAttribute("salesman_errorMsg", errorMsg);
                }else{
                    httpsession.setAttribute("salesman_errorMsg", errorMsg);
                }
                response.sendRedirect(request.getContextPath() + "/paid_sales.jsp");
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
