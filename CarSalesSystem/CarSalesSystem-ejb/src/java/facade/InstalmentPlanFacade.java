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
import java.util.List;
import model.InstalmentPlan;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class InstalmentPlanFacade extends AbstractFacade<InstalmentPlan> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstalmentPlanFacade() {
        super(InstalmentPlan.class);
    }
    
    public InstalmentPlan getInstalmentPlanBySales(String sales_id) {
        try {
            TypedQuery<InstalmentPlan> query = em.createNamedQuery("InstalmentPlan.getInstalmentPlanBySales", InstalmentPlan.class);
            query.setParameter("sales_id", sales_id);
            return query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    
    public List<Object[]> getInstalmentStatus(List<String> salesman_ids, InstalmentPlan.PlanStatus status) {
        TypedQuery<Object[]> query = em.createNamedQuery("InstalmentPlan.getInstalmentStatus", Object[].class);
        query.setParameter("status", status);
        query.setParameter("salesman_ids", salesman_ids);
        return query.getResultList();
    }   
}
