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
import model.Users;
import model.Users.Role;

/**
 *
 * @author Chew Jin Ni
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "CarSalesSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public Users findIc(String ic) {
        TypedQuery<Users> query = em.createNamedQuery("Users.findIc", Users.class);
        query.setParameter("ic", ic);
        try {
            return query.getSingleResult(); 
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
