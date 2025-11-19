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
import model.ManagingStaff;
import model.Sales;
import model.Users;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class ManagingStaffFacade extends AbstractFacade<ManagingStaff> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ManagingStaffFacade() {
        super(ManagingStaff.class);
    }
    
    public List<ManagingStaff> findByManagingStaffInfo(String searchTerm) {
       TypedQuery<ManagingStaff> query = em.createNamedQuery("ManagingStaff.findByManagingStaffInfo", ManagingStaff.class);
       query.setParameter("searchTerm", "%" + searchTerm.toLowerCase() + "%");
       return query.getResultList(); 
    }
    
    public List<ManagingStaff> findApprovedManagingStaff(Users.Status status, Users.State state) {
       TypedQuery<ManagingStaff> query = em.createNamedQuery("ManagingStaff.findApprovedManagingStaff", ManagingStaff.class);
       query.setParameter("status", status);
       query.setParameter("state", state);
       return query.getResultList(); 
    }
    
    public List<Object[]> findRelatedDepositInstalment(String manager_id, CarStock.StockStatus stockstatus, Sales.SalesStatus status_1, Sales.SalesStatus status_2) {
       TypedQuery<Object[]> query = em.createNamedQuery("ManagingStaff.findRelatedDepositInstalment", Object[].class);
       query.setParameter("manager_id", manager_id);
       query.setParameter("stock_status", stockstatus);
       query.setParameter("sales_status_1", status_1);
       query.setParameter("sales_status_2", status_2);
       return query.getResultList(); 
    }
}
