/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Chew Jin Ni
 */
package util;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import facade.SalesFacade;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import model.Sales;

@Named("SalesmanPerformanceBean")
@SessionScoped
public class SalesmanPerformanceBean implements Serializable {

    @EJB
    private SalesFacade salesFacade;

    // Properties for year, month, and performance data
    private int currentYear;
    private int currentMonthNumber;
    private List<Object[]> salesmenPerformanceList;

    // Map for month names (retrieved from session scope)
    private Map<Integer, String> months;

    // Constructor or PostConstruct to initialize default values
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        // Retrieve default values from session scope
        currentYear = (int) session.getAttribute("currentYear");
        currentMonthNumber = (int) session.getAttribute("currentMonthNumber");
        months = (Map<Integer, String>) session.getAttribute("months");

        // Load initial performance data
        filterData();
    }

    // Method to filter data based on selected year and month
    public void filterData() {
        try {
            // Use the selected year and month if provided; otherwise, use defaults
            int filterYear = (currentYear == 0) ? LocalDate.now().getYear() : currentYear;
            int filterMonth = (currentMonthNumber == 0) ? LocalDate.now().getMonthValue() : currentMonthNumber;

            // Fetch filtered data from the database using EJB
            List<String> salesmanIds = (List<String>) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("salesmanIds");

            List<Object[]> filteredData = salesFacade.getSalesmanPerformance(
                salesmanIds,
                Sales.SalesStatus.Cancelled,
                filterMonth,
                filterYear
            );
            salesmenPerformanceList = filteredData; // Update the list
        } catch (Exception e) {
            System.out.println("Error in filtering data: " + e);
        }
    }

    // Getters and Setters
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

    public List<Object[]> getSalesmenPerformanceList() {
        return salesmenPerformanceList;
    }

    public void setSalesmenPerformanceList(List<Object[]> salesmenPerformanceList) {
        this.salesmenPerformanceList = salesmenPerformanceList;
    }

    public Map<Integer, String> getMonths() {
        return months;
    }
}

