/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Chew Jin Ni
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "users")
@NamedQueries({
    @NamedQuery(
        name = "Users.findIc",
        query = "SELECT u FROM Users u WHERE u.ic = :ic"
    )
})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(unique = true, nullable = false)
    protected String user_id;
    @Column(nullable = false)
    protected String password;
    @Column(nullable = false)
    protected String email;
    
    protected String name;
    protected String phone;
    
    @Column(nullable=false)
    protected String ic;
    
    @Enumerated(EnumType.STRING)    
    @Column(nullable = false)
    protected Role role;
    
    @Column(nullable = false, updatable = false)
    protected LocalDateTime created_at;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Status approval;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected State state;  
    
    public enum Role {
        Managing_Staff, Salesman, Customer
    }
    
    public enum Status {
        Approved, Pending, Rejected
    } 
    
    public enum State {
        Active, Inactive
    }
   
    public Users() {
        this.state = State.Active;
        this.created_at = LocalDateTime.now();
        this.approval = Status.Pending;
    }

    public Users(String user_id, String password, String email, String name, String phone, String ic, Role role) {
        this.state = State.Active;
        this.created_at = LocalDateTime.now();
        this.approval = Status.Pending;
        this.user_id = user_id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.ic = ic;
        this.role = role;
    }
    
    public Users(String user_id, Status approval){
        this.state = State.Active;
        this.created_at = LocalDateTime.now();
        this.approval = Status.Pending;
        this.user_id = user_id;
        this.approval = approval;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public Status getApproval() {
        return approval;
    }

    public void setApproval(Status approval) {
        this.approval = approval;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.user_id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Users other = (Users) obj;
        return Objects.equals(this.user_id, other.user_id);
    }

    @Override
    public String toString() {
        return "Users{" + "user_id=" + user_id + ", password=" + password + ", email=" + email + ", name=" + name + ", phone=" + phone + ", ic=" + ic + ", role=" + role + ", created_at=" + created_at + ", approval=" + approval + ", state=" + state + '}';
    }
    
}
