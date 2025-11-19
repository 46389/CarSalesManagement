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
import model.CarStock.StockStatus;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class CarStockFacade extends AbstractFacade<CarStock> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarStockFacade() {
        super(CarStock.class);
    }
    
    public List<CarStock> getCarStockByCarType(String car_id, StockStatus status) {
        TypedQuery<CarStock> query = em.createNamedQuery("CarStock.getCarStockByCarType", CarStock.class);
        query.setParameter("car_id", car_id);
        query.setParameter("status", status);
        return query.getResultList();
    }    
}
