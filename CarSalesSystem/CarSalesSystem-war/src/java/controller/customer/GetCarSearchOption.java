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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import model.Car;

/**
 *
 * @author Chew Jin Ni
 */
public class GetCarSearchOption extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            if (session.getAttribute("user") == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            /* TODO output your page here. You may use following sample code. */
            List<Car> cars = CarFacade.getCarDetails();
            // Extract unique brands, fuel types, and seats using Streams
            Set<String> brands = cars.stream()
                                    .map(Car::getBrand)
                                    .filter(brand -> brand != null && !brand.isEmpty()) // Filter out null or empty values
                                    .collect(Collectors.toSet());

            Set<String> fuelTypes = cars.stream()
                                    .map(Car::getFuel_type)
                                    .filter(fuelType -> fuelType != null && !fuelType.isEmpty()) // Filter out null or empty values
                                    .collect(Collectors.toSet());

            Set<Integer> seats = cars.stream()
                                .map(Car::getSeats)
                                .filter(seat -> seat != null)
                                .sorted()
                                .collect(Collectors.toSet());
            
            List<String> retrievedBrands = new ArrayList<>(brands);
            List<String> retrivedfuelTypes = new ArrayList<>(fuelTypes);
            List<Integer> retrievedSeats = new ArrayList<>(seats);
            
            session.setAttribute("existing_brands", retrievedBrands);
            session.setAttribute("existing_fuelTypes", retrivedfuelTypes);
            session.setAttribute("existing_seats", retrievedSeats);
            
            response.sendRedirect(request.getContextPath() + "/customer/customer_home.jsp");

        }catch(Exception e){
            System.out.println("Error in generating car search bar: " + e);
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
