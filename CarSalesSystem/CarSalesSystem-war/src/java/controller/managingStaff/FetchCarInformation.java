/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.managingStaff;

import static controller.managingStaff.FetchCustomerAccount.validateManagerSession;
import facade.CarTypeFacade;
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
import model.CarStock.StockStatus;
import util.CarDetails;

/**
 *
 * @author Chew Jin Ni
 */
public class FetchCarInformation extends HttpServlet {
    
    @EJB
    private CarTypeFacade CarTypeFacade;
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
        try (PrintWriter out = response.getWriter()) {
            String search = (String) session.getAttribute("filter_cars");
            
            if(search == null || search.isEmpty() || "".equals(search)){
                List<Object[]> cars = CarTypeFacade.getAllCarTypes(StockStatus.Available, StockStatus.Interested, StockStatus.Booked, StockStatus.Paid);
                List<CarDetails> car_details = new ArrayList<>();

                for(Object[] car : cars){
                    CarDetails cardetail = new CarDetails();
                    cardetail.setModel_id((String) car[0]);
                    cardetail.setModel_name((String) car[1]);
                    cardetail.setBrand((String) car[2]);
                    cardetail.setFuel_type((String) car[3]);
                    cardetail.setHorsepower((int) car[4]);
                    cardetail.setSeats((int) car[5]);
                    cardetail.setTransmission_type((String) car[6]);
                    cardetail.setCar_id((String) car[7]);
                    cardetail.setColour((String) car[8]);
                    cardetail.setYear_of_manufacture((int) car[9]);
                    cardetail.setPrice((double) car[10]);
                    cardetail.setAvailableCount((long) car[11]);
                    cardetail.setBookedCount((long) car[12]);
                    cardetail.setPaidCount((long) car[13]);
                    car_details.add(cardetail);
                }
                if(session.getAttribute("all_cars") != null){
                    session.removeAttribute("all_cars");
                    session.setAttribute("all_cars", car_details);
                }else{
                    session.setAttribute("all_cars", car_details);
                }
            }else{
                List<Object[]> cars = CarTypeFacade.getFilteredCarTypes(StockStatus.Available, StockStatus.Interested, StockStatus.Booked, StockStatus.Paid, search);
                List<CarDetails> car_details = new ArrayList<>();

                for(Object[] car : cars){
                    CarDetails cardetail = new CarDetails();
                    cardetail.setModel_id((String) car[0]);
                    cardetail.setModel_name((String) car[1]);
                    cardetail.setBrand((String) car[2]);
                    cardetail.setFuel_type((String) car[3]);
                    cardetail.setHorsepower((int) car[4]);
                    cardetail.setSeats((int) car[5]);
                    cardetail.setTransmission_type((String) car[6]);
                    cardetail.setCar_id((String) car[7]);
                    cardetail.setColour((String) car[8]);
                    cardetail.setYear_of_manufacture((int) car[9]);
                    cardetail.setPrice((double) car[10]);
                    cardetail.setAvailableCount((long) car[11]);
                    cardetail.setBookedCount((long) car[12]);
                    cardetail.setPaidCount((long) car[13]);
                    car_details.add(cardetail);
                }
                if(session.getAttribute("all_cars") != null){
                    session.removeAttribute("all_cars");
                    session.setAttribute("all_cars", car_details);
                }else{
                    session.setAttribute("all_cars", car_details);
                }
            }

            response.sendRedirect(request.getContextPath() + "/managingStaff/car_stocks.jsp");
        }catch(Exception e){
            System.out.println("Error in fetching cars' information: " + e);
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
