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
import model.Deposit;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class DepositFacade extends AbstractFacade<Deposit> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepositFacade() {
        super(Deposit.class);
    }
    
    public Deposit getDepositBySales(String sales_id) {
        TypedQuery<Deposit> query = em.createNamedQuery("Deposit.getDepositBySales", Deposit.class);
        query.setParameter("sales_id", sales_id);
        try{
            return query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    } 
    
}
