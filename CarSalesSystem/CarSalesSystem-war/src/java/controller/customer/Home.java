/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import facade.CarFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Car;
import model.Users;

/**
 *
 * @author Chew Jin Ni
 */
public class Home extends HttpServlet {

    @EJB
    private CarFacade CarFacade;
    
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
        HttpSession session = request.getSession();
        if (!validateCustomerSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        String brand = request.getParameter("brand");
        String model_name = request.getParameter("modelName");
        String fuel_type = request.getParameter("fuelType");
        String seats = request.getParameter("seats");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String action = request.getParameter("action");
        
        try (PrintWriter out = response.getWriter()) {
            
            if("search".equals(action)){
                double min_price = 0;
                double max_price = 0;
                if(!minPrice.isEmpty()){
                    min_price = Double.parseDouble(minPrice);
                }
                
                if(!maxPrice.isEmpty()){
                    max_price = Double.parseDouble(maxPrice);
                }
                
                List<Car> available_cars = CarFacade.advancedSearch(brand, model_name, fuel_type, seats, min_price, max_price);
                if(session.getAttribute("cars") != null){
                    session.removeAttribute("cars");
                    session.setAttribute("cars", available_cars);
                }else{
                    session.setAttribute("cars", available_cars);
                }
                
            }else{
                List<Car> available_cars = CarFacade.getCarDetails();
                if(session.getAttribute("cars") != null){
                    session.removeAttribute("cars");
                    session.setAttribute("cars", available_cars);
                }else{
                    session.setAttribute("cars", available_cars);
                }
            }   
            
            response.sendRedirect(request.getContextPath() + "/GetCarSearchOption");
        }catch(Exception e){
            System.out.println("Error in loading cars: " + e);
        }
    }
    
    public static boolean validateCustomerSession(HttpServletRequest request, HttpServletResponse response) {
        // Validate session and role
        if (request.getSession(false) != null && request.getSession().getAttribute("user") != null) {
            Users user = (Users) request.getSession().getAttribute("user");
            return user.getRole() == Users.Role.Customer;
        }
        return false;
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
