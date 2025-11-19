/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Chew Jin Ni
 */

import facade.SalesFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Sales;
import model.Users;

@Named("CarTrendsBean")
@SessionScoped
public class CarTrendsBean implements Serializable {
    @EJB
    private SalesFacade salesFacade;
    

    private int currentYear;
    private int currentMonthNumber;
    private List<Object[]> carTrends;
    private Map<Integer, String> months;
    private List<Integer> availableYears;


    // Initialize default values
    @PostConstruct
    public void init() { 
        // Initialize current year and month from session or defaults
        currentYear = LocalDate.now().getYear();
        currentMonthNumber = LocalDate.now().getMonthValue();

        // Initialize months map from session or defaults
        months = getDefaultMonths();
        
        availableYears = salesFacade.getAvailableYears();

        // Load initial data
        filterData();
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            try {
                context.getExternalContext().redirect("../login.jsp");
                return;
            } catch (IOException ex) {
                System.out.println("Error in redirecting to login jsp");
            }
        }

        Users user = (Users) session.getAttribute("user");

        if (user == null || user.getRole() == null || user.getRole() != Users.Role.Customer) {
            try {
                context.getExternalContext().redirect("../login.jsp");
            } catch (IOException ex) {
                System.out.println("Error in redirecting to login jsp");
            }
        }
        
    }

    public void filterData() {
        try {
            // Use the selected year and month if provided; otherwise, use defaults
            int filterYear = (currentYear == 0) ? LocalDate.now().getYear() : currentYear;
            int filterMonth = (currentMonthNumber == 0) ? LocalDate.now().getMonthValue() : currentMonthNumber;

            List<Object[]> filteredData = salesFacade.getCarTrends(
                Sales.SalesStatus.Cancelled,
                filterMonth,
                filterYear
            );
            carTrends = filteredData; // Update the list
        } catch (Exception e) {
            System.out.println("Error in filtering data: " + e.getMessage());
        }
    }

    private Map<Integer, String> getDefaultMonths() {
        Map<Integer, String> defaultMonths = new HashMap<>();
        defaultMonths.put(1, "January");
        defaultMonths.put(2, "February");
        defaultMonths.put(3, "March");
        defaultMonths.put(4, "April");
        defaultMonths.put(5, "May");
        defaultMonths.put(6, "June");
        defaultMonths.put(7, "July");
        defaultMonths.put(8, "August");
        defaultMonths.put(9, "September");
        defaultMonths.put(10, "October");
        defaultMonths.put(11, "November");
        defaultMonths.put(12, "December");
        return defaultMonths;
    }

    // Getters and setters
    public SalesFacade getSalesFacade() {
        return salesFacade;
    }

    public void setSalesFacade(SalesFacade salesFacade) {
        this.salesFacade = salesFacade;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getCurrentMonthNumber() {
        return currentMonthNumber;
    }

    public void setCurrentMonthNumber(int currentMonthNumber) {
        this.currentMonthNumber = currentMonthNumber;
    }

    public List<Object[]> getCarTrends() {
        return carTrends;
    }

    public void setCarTrends(List<Object[]> carTrends) {
        this.carTrends = carTrends;
    }

    public Map<Integer, String> getMonths() {
        return months;
    }

    public void setMonths(Map<Integer, String> months) {
        this.months = months;
    }
    
    public List<Integer> getAvailableYears() {
        return availableYears;
    }

    public void setAvailableYears(List<Integer> availableYears) {
        this.availableYears = availableYears;
    }
    
    
}