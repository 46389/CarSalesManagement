/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author Chew Jin Ni
 */
@Entity
@Table(name="customer")
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(
        name = "Customer.findByCustomerInfo",
        query = "SELECT c FROM Customer c WHERE LOWER(c.user_id) LIKE :searchTerm OR LOWER(c.ic) LIKE :searchTerm OR LOWER(c.name) LIKE :searchTerm"
    )
})
public class Customer extends Users implements Serializable {

    @Column(nullable=false)
    private int age;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    
    @Column(nullable = false)
    private String address;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmpStatus employment_status;
    
    @Column(nullable = false)
    private String occupation;

    @Column(nullable = false)
    private String salary_slip;
    
    @Column(nullable = false)
    private String driving_license;
       
    public enum Gender {
        Female, Male
    }
    
    public enum EmpStatus {
        Employed, Unemployed, Freelancer, Self_Employed, Contract
    }

    public Customer() {
    }

    public Customer(int age, Gender gender, String address, EmpStatus employment_status, String occupation, String salary_slip, String driving_license, String user_id, String password, String email, String name, String phone, String ic) {
        super(user_id, password, email, name, phone, ic, Users.Role.Customer);
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.employment_status = employment_status;
        this.occupation = occupation;
        this.salary_slip = salary_slip;
        this.driving_license = driving_license;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EmpStatus getEmployment_status() {
        return employment_status;
    }

    public void setEmployment_status(EmpStatus employment_status) {
        this.employment_status = employment_status;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSalary_slip() {
        return salary_slip;
    }

    public void setSalary_slip(String salary_slip) {
        this.salary_slip = salary_slip;
    }

    public String getDriving_license() {
        return driving_license;
    }

    public void setDriving_license(String driving_license) {
        this.driving_license = driving_license;
    }
       
}
