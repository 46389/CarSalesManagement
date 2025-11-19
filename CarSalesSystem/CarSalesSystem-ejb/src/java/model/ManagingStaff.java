/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Chew Jin Ni
 */
@Entity
@DiscriminatorColumn(name="ManagingStaff")
@Table(name= "managing_staff")
@NamedQueries({
    @NamedQuery(
        name = "ManagingStaff.findByManagingStaffInfo",
        query = "SELECT ms FROM ManagingStaff ms WHERE LOWER(ms.user_id) LIKE :searchTerm OR LOWER(ms.ic) LIKE :searchTerm OR LOWER(ms.name) LIKE :searchTerm"
    ),
    @NamedQuery(
        name = "ManagingStaff.findApprovedManagingStaff",
        query = "SELECT ms FROM ManagingStaff ms WHERE ms.approval = :status AND ms.state = :state"
    ),
    @NamedQuery(
        name = "ManagingStaff.findRelatedDepositInstalment",
        query = "SELECT sl.sales_id, sm.user_id, sl.customer.user_id, sl.car_id.car_stock_id, sl.car_id.car_type.car.model_name, sl.car_id.car_type.price FROM ManagingStaff ms JOIN FETCH ms.salesmen sm JOIN FETCH sm.sales sl WHERE ms.user_id = :manager_id AND sl.car_id.stock_status <> :stock_status AND sl.sales_status <> :sales_status_1 AND sl.sales_status <> :sales_status_2"
    )
})
public class ManagingStaff extends Users implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @OneToMany(mappedBy = "managingStaff", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private ArrayList<Salesman> salesmen = new ArrayList<>();
       
    public ManagingStaff(String user_id, String password, String email, String name, String phone, String ic) {
        super(user_id, password, email, name, phone, ic, Role.Managing_Staff);
    }
    
    public ManagingStaff() {
    }
    
     public void setSalesmen(List<Salesman> salesmen) {
        this.salesmen = (ArrayList<Salesman>) salesmen;
    }

    public void addSalesman(Salesman salesman) {
        salesmen.add(salesman);
        salesman.setManagingStaff(this); // Ensure bidirectional relationship
    }

    public void removeSalesman(Salesman salesman) {
        salesmen.remove(salesman);
        salesman.setManagingStaff(null);
    }

    public ArrayList<Salesman> getSalesmen() {
        return salesmen;
    }

    @Override
    public int hashCode(){
        return Objects.hash(getUser_id());
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof ManagingStaff)) return false;
        ManagingStaff other = (ManagingStaff) obj;
        return Objects.equals(this.getUser_id(), other.getUser_id());
    }
    
    @Override
    public String toString(){
        return super.toString() + "ManagingStaff{" +
                "salesmen=" + salesmen.size() +
                "}";
    }
}
