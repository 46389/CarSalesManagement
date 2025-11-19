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
import model.CarStock;
import model.CarType;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class CarTypeFacade extends AbstractFacade<CarType> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarTypeFacade() {
        super(CarType.class);
    }
    
    public List<Object[]> getAllCarTypes(CarStock.StockStatus status_1, CarStock.StockStatus status_2, CarStock.StockStatus status_3, CarStock.StockStatus status_4) {
        TypedQuery<Object[]> query = em.createNamedQuery("CarType.getAllCarTypes", Object[].class);
        query.setParameter("status_1", status_1);
        query.setParameter("status_2", status_2);
        query.setParameter("status_3", status_3);
        query.setParameter("status_4", status_4);
        return query.getResultList();    
    }
    
    public List<Object[]> getFilteredCarTypes(CarStock.StockStatus status_1, CarStock.StockStatus status_2, CarStock.StockStatus status_3, CarStock.StockStatus status_4, String searchTerm) {
        TypedQuery<Object[]> query = em.createNamedQuery("CarType.getFilteredCarTypes", Object[].class);
        query.setParameter("status_1", status_1);
        query.setParameter("status_2", status_2);
        query.setParameter("status_3", status_3);
        query.setParameter("status_4", status_4);
        query.setParameter("searchTerm", "%" + searchTerm.toLowerCase() + "%");
        return query.getResultList();    
    }
}
