/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.managingStaff;

import static controller.managingStaff.FetchCustomerAccount.validateManagerSession;
import facade.CarFacade;
import facade.CarTypeFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Car;
import model.CarType;

/**
 *
 * @author Chew Jin Ni
 */
public class UpdateCarInfo extends HttpServlet {

    @EJB
    private CarTypeFacade CarTypeFacade;
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
        
        HttpSession session = request.getSession();
        if (!validateManagerSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        
        String car_id = request.getParameter("car_id");
        String model_name = request.getParameter("model_name");
        String brand = request.getParameter("brand");
        String fuel_type = request.getParameter("fuel_type");
        int horsepower = Integer.parseInt(request.getParameter("horsepower"));
        int seats = Integer.parseInt(request.getParameter("seats"));
        String transmission = request.getParameter("transmission");
        String colour = request.getParameter("colour");
        int year = Integer.parseInt(request.getParameter("year"));
        double price = Double.parseDouble(request.getParameter("price"));
        String errorMsg = "";
        boolean indicator = false;
        
        try (PrintWriter out = response.getWriter()) {
            if(model_name == null || model_name.isEmpty()){
                errorMsg += "Please fill in the model name.<br>";
                indicator = true;
            }
            
            if(brand == null || brand.isEmpty()){
                errorMsg += "Please fill in the brand.<br>";
                indicator = true;
            }
            
            if(fuel_type == null || fuel_type.isEmpty()){
                errorMsg += "Please fill in the fuel_type.<br>";
                indicator = true;
            }
            
            if(horsepower <= 0){
                errorMsg += "Please check your horsepower.<br>";
                indicator = true;
            }
            
            if(seats <= 0){
                errorMsg += "Please check your seats number.<br>";
                indicator = true;
            }
            
            if(transmission == null || transmission.isEmpty()){
                errorMsg += "Please fill in the transmission type.<br>";
                indicator = true;
            }
            
            if(colour == null || colour.isEmpty()){
                errorMsg += "Please fill in the colour.<br>";
                indicator = true;
            }
            
            if(year <= 0){
                errorMsg += "Please fill in the year of manufacture.<br>";
                indicator = true;
            }
            
            if(price <= 0){
                errorMsg += "Please check your price.<br>";
                indicator = true;
            }
            
            if(!indicator){
                CarType cartype = CarTypeFacade.find(car_id);
                String selected_car_id = cartype.getCar().getModel_id();
                Car car = CarFacade.find(selected_car_id);

                cartype.setColour(colour);
                cartype.setPrice(price);
                cartype.setYear_of_manufacture(year);
                CarTypeFacade.edit(cartype);
                
                car.setBrand(brand);
                car.setFuel_type(fuel_type);
                car.setHorsepower(horsepower);
                car.setModel_name(model_name);
                car.setSeats(seats);
                car.setTransmission_type(transmission);
                CarFacade.edit(car);
                
                response.sendRedirect(request.getContextPath() + "/FetchCarInformation");
            }else{
                if(session.getAttribute("manager_errorMsg") != null){
                    session.removeAttribute("manager_errorMsg");
                    session.setAttribute("manager_errorMsg", errorMsg);
                }else{
                    session.setAttribute("manager_errorMsg", errorMsg);
                }
                response.sendRedirect(request.getContextPath() + "/managingStaff/car_stocks.jsp");
            }      
        }catch(Exception e){
            System.out.println("Error in updating car information: " + e);
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
