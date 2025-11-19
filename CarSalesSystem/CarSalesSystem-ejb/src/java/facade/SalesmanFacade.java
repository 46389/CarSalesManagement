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
import model.Salesman;
import model.Users;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class SalesmanFacade extends AbstractFacade<Salesman> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SalesmanFacade() {
        super(Salesman.class);
    }
   
    public List<Salesman> findBySalesmanInfo(String searchTerm) {
       TypedQuery<Salesman> query = em.createNamedQuery("Salesman.findBySalesmanInfo", Salesman.class);
       query.setParameter("searchTerm", "%" + searchTerm.toLowerCase() + "%");
       return query.getResultList(); 
    }
    
    public List<Salesman> findApprovedSalesman(Users.Status status, Users.State state) {
       TypedQuery<Salesman> query = em.createNamedQuery("Salesman.findApprovedSalesman", Salesman.class);
       query.setParameter("status", status);
       query.setParameter("state", state);
       return query.getResultList(); 
    }
    
    public List<Salesman> findSubdordinateSalesman(String manager_id) {
       TypedQuery<Salesman> query = em.createNamedQuery("Salesman.findSubdordinateSalesman", Salesman.class);
       query.setParameter("manager_id", manager_id);
       return query.getResultList(); 
    }
}
