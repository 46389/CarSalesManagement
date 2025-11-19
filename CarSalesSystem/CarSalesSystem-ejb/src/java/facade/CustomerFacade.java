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
import model.Customer;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
    public List<Customer> findByCustomerInfo(String searchTerm) {
       TypedQuery<Customer> query = em.createNamedQuery("Customer.findByCustomerInfo", Customer.class);
       query.setParameter("searchTerm", "%" + searchTerm.toLowerCase() + "%");
       return query.getResultList(); 
    }
    
}
