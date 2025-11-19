/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chew Jin Ni
 */
@Entity
@Table(name= "salesman")
@DiscriminatorColumn(name="Salesman")

@NamedQueries({
    @NamedQuery(
        name = "Salesman.findBySalesmanInfo",
        query = "SELECT s FROM Salesman s WHERE LOWER(s.user_id) LIKE :searchTerm OR LOWER(s.ic) LIKE :searchTerm OR LOWER(s.name) LIKE :searchTerm"
    ),
    @NamedQuery(
        name = "Salesman.findApprovedSalesman",
        query = "SELECT s FROM Salesman s WHERE s.approval = :status AND s.state = :state"
    ),
    @NamedQuery(
        name = "Salesman.findSubdordinateSalesman",
        query = "SELECT s FROM Salesman s WHERE s.managingStaff.user_id = :manager_id"
    )
})

public class Salesman extends Users implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne(optional=true)
    @JoinColumn(name="manager_id")
    private ManagingStaff managingStaff;
    
    @OneToMany(mappedBy = "salesman", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sales> sales = new ArrayList<>();
    
    public Salesman() {
    }

    public Salesman(String user_id, String password, String email, String name, String phone, String ic, Role role, ManagingStaff managingStaff) {
        super(user_id, password, email, name, phone, ic, Role.Salesman);
        this.managingStaff = managingStaff;
    }

    public Salesman(String user_id, String password, String email, String name, String phone, String ic) {
        super(user_id, password, email, name, phone, ic, Role.Salesman);
    }

    public ManagingStaff getManagingStaff() {
        return managingStaff;
    }

    public void setManagingStaff(ManagingStaff managingStaff) {
        this.managingStaff = managingStaff;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }
     
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public String toString() {
        return "Salesman{" +
                "user_id='" + getUser_id() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", name='" + getName() + '\'' +
                ", managingStaff=" + managingStaff.getUser_id() +
                '}';
    }
    
}
