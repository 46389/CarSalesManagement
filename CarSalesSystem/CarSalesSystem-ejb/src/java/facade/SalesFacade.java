/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facade;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.CarStock;
import model.Sales;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class SalesFacade extends AbstractFacade<Sales> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SalesFacade() {
        super(Sales.class);
    }
    
    public String findingLeastSalesSalesman(Map<String, Integer> salesCountMap) {
        // Use streams to find the entry with the minimum sales count
        Optional<Map.Entry<String, Integer>> minEntry = salesCountMap.entrySet().stream()
            .min(Map.Entry.comparingByValue());

        // Return the salesman ID if a result is found, otherwise return null
        return minEntry.map(Map.Entry::getKey).orElse(null);
    }
    
    public List<Object[]> findBySalesmanAndCarStatus(String salesman_id, CarStock.StockStatus status, Sales.SalesStatus sales_status){
        TypedQuery<Object[]> query = em.createNamedQuery("Sales.findBySalesmanAndCarStatus", Object[].class);
        query.setParameter("id", salesman_id);
        query.setParameter("status", status);
        query.setParameter("sales_status", sales_status);
        return query.getResultList();
    }
    
    public List<Object[]> findBySalesmanAndSalesStatus(String salesman_id, Sales.SalesStatus status){
        TypedQuery<Object[]> query = em.createNamedQuery("Sales.findBySalesmanAndSalesStatus", Object[].class);
        query.setParameter("id", salesman_id);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
    public List<Sales> findByCustomerAndCarStatus(String customer_id, CarStock.StockStatus status){
        TypedQuery<Sales> query = em.createNamedQuery("Sales.findByCustomerAndCarStatus", Sales.class);
        query.setParameter("customer_id", customer_id);
        query.setParameter("status", status);
        return query.getResultList();
    } 
    
    public List<Object[]> getSalesmanPerformance(List<String> salesman_ids, Sales.SalesStatus status, int month, int year){
        TypedQuery<Object[]> query = em.createNamedQuery("Sales.getSalesmanPerformance", Object[].class);
        query.setParameter("salesman_ids", salesman_ids);
        query.setParameter("month", month);
        query.setParameter("year", year);
        query.setParameter("status", status);
        return query.getResultList();
    } 
    
    public List<Integer> getAvailableYears(){
        TypedQuery<Integer> query = em.createNamedQuery("Sales.getAvailableYears", Integer.class);
        return query.getResultList();
    }
    
    public List<Object[]> getSalesPerformanceByMonth(Sales.SalesStatus status_1, Sales.SalesStatus status_2){
        TypedQuery<Object[]> query = em.createNamedQuery("Sales.getSalesPerformanceByMonth", Object[].class);
        query.setParameter("status_1", status_1);
        query.setParameter("status_2", status_2);
        return query.getResultList();
    }
    
    public List<Object[]> getSalesStatusBySalesman(List<String> salesman_ids, Sales.SalesStatus status_1, Sales.SalesStatus status_2, Sales.SalesStatus status_3, Sales.SalesStatus status_4, Sales.SalesStatus status_5, Sales.SalesStatus status_6, Sales.SalesStatus status_7){
        TypedQuery<Object[]> query = em.createNamedQuery("Sales.getSalesStatusBySalesman", Object[].class);
        query.setParameter("status_1", status_1);
        query.setParameter("status_2", status_2);
        query.setParameter("status_3", status_3);
        query.setParameter("status_4", status_4);
        query.setParameter("status_5", status_5);
        query.setParameter("status_6", status_6);
        query.setParameter("status_7", status_7);
        query.setParameter("salesman_ids", salesman_ids);
        return query.getResultList();
    }
    
    public List<Object[]> getRatingBySalesman(List<String> salesman_ids){
        TypedQuery<Object[]> query = em.createNamedQuery("Sales.getRatingBySalesman", Object[].class);
        query.setParameter("salesman_ids", salesman_ids);
        return query.getResultList();
    }
    
    public List<Object[]> getRatingValueBySalesman(List<String> salesman_ids){
        TypedQuery<Object[]> query = em.createNamedQuery("Sales.getRatingValueBySalesman", Object[].class);
        query.setParameter("salesman_ids", salesman_ids);
        return query.getResultList();
    }
    
    public List<Object[]> getCarTrends(Sales.SalesStatus status, int month, int year){
        TypedQuery<Object[]> query = em.createNamedQuery("Sales.getCarTrends", Object[].class);
        query.setParameter("month", month);
        query.setParameter("year", year);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
}
