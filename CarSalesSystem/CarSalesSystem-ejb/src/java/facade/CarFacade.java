/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facade;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Car;
import model.Sales;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class CarFacade extends AbstractFacade<Car> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarFacade() {
        super(Car.class);
    }
    
    public List<Car> getCarDetails() {
        TypedQuery<Car> query = em.createNamedQuery("Car.getCarModels", Car.class);
        try {
           return query.getResultList();
        } catch (NoResultException e) {
            return null; // Return null if no match is found
        }
    }
    
    public List<Car> advancedSearch(String brand, String modelName, String fuelType, String seats, double minPrice, double maxPrice){
        String jpql = "SELECT DISTINCT c FROM Car c LEFT JOIN FETCH c.carTypes ct WHERE 1=1";

        // Parameters for filtering
        Map<String, Object> params = new HashMap<>();

        // Add conditions based on user input
        if (brand != null && !brand.isEmpty()) {
            jpql += " AND c.brand = :brand";
            params.put("brand", brand);
        }

        if (modelName != null && !modelName.isEmpty()) {
            jpql += " AND c.model_name LIKE CONCAT('%', :model_name, '%')";
            params.put("model_name", "%" + modelName + "%");
        }

        if (fuelType != null && !fuelType.isEmpty()) {
            jpql += " AND c.fuel_type = :fuel_type";
            params.put("fuel_type", fuelType);
        }

        if (seats != null && !seats.isEmpty()) {
            jpql += " AND c.seats = :seats";
            params.put("seats", Integer.valueOf(seats));
        }

        if (minPrice > 0) {
            jpql += " AND ct.price >= :minPrice";
            params.put("minPrice", minPrice);
        }

        if (maxPrice > 0) {
            jpql += " AND ct.price <= :maxPrice";
            params.put("maxPrice", maxPrice);
        }

        // Execute the query
        TypedQuery<Car> query = (TypedQuery<Car>) em.createQuery(jpql);
        params.forEach((key, value) -> query.setParameter(key, value));
        return query.getResultList();
    }
    
}
