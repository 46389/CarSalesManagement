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
import model.InstalmentPayment;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class InstalmentPaymentFacade extends AbstractFacade<InstalmentPayment> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstalmentPaymentFacade() {
        super(InstalmentPayment.class);
    }
    
    public List<InstalmentPayment> getPaymentByPlan(String instalment_id) {
        TypedQuery<InstalmentPayment> query = em.createNamedQuery("InstalmentPayment.getPaymentByPlan", InstalmentPayment.class);
        query.setParameter("instalment_id", instalment_id);
        return query.getResultList();
    } 
    
    public List<InstalmentPayment> getPaymentBySalesman(String salesman_id) {
        TypedQuery<InstalmentPayment> query = em.createNamedQuery("InstalmentPayment.getPaymentBySalesman", InstalmentPayment.class);
        query.setParameter("salesman_id", salesman_id);
        return query.getResultList();
    } 
    
    public List<InstalmentPayment> getPaymentBySalesmen(List<String> salesman_ids) {
        TypedQuery<InstalmentPayment> query = em.createNamedQuery("InstalmentPayment.getPaymentBySalesmen", InstalmentPayment.class);
        query.setParameter("salesman_ids", salesman_ids);
        return query.getResultList();
    }
}
