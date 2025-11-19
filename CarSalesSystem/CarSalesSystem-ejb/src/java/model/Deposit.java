/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Chew Jin Ni
 */
@Entity
@Table(name="deposit")
@NamedQueries({
    @NamedQuery(
        name = "Deposit.getDepositBySales",
        query = "SELECT d FROM Deposit d WHERE d.sales_id.sales_id = :sales_id"
    )
})

public class Deposit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deposit_id;
    @OneToOne
    @JoinColumn(name="sales_id", nullable = false)
    private Sales sales_id;
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;
    @Column(nullable = false)
    private double deposit_amount;
    private String deposit_doc;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String remark;
    
    public enum Status {
        Pending_Approval, Approved, Rejected
    }

    public Deposit() {
        this.created_at = LocalDateTime.now();
    }

    public Deposit(Sales sales_id, double deposit_amount, String deposit_doc, String remark) {
        this.created_at = LocalDateTime.now();
        this.sales_id = sales_id;
        this.deposit_amount = deposit_amount;
        this.deposit_doc = deposit_doc;
        this.remark = remark;
    }

    public Deposit(Sales sales_id, double deposit_amount) {
        this.created_at = LocalDateTime.now();
        this.sales_id = sales_id;
        this.deposit_amount = deposit_amount;
    }

    public String getDeposit_id() {
        return deposit_id;
    }

    public void setDeposit_id(String deposit_id) {
        this.deposit_id = deposit_id;
    }

    public Sales getSales_id() {
        return sales_id;
    }

    public void setSales_id(Sales sales_id) {
        this.sales_id = sales_id;
    }

    public double getDeposit_amount() {
        return deposit_amount;
    }

    public void setDeposit_amount(double deposit_amount) {
        this.deposit_amount = deposit_amount;
    }

    public String getDeposit_doc() {
        return deposit_doc;
    }

    public void setDeposit_doc(String deposit_doc) {
        this.deposit_doc = deposit_doc;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.deposit_id);
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
        final Deposit other = (Deposit) obj;
        return Objects.equals(this.deposit_id, other.deposit_id);
    }

    @Override
    public String toString() {
        return "Deposit{" + "deposit_id=" + deposit_id + ", sales_id=" + sales_id + ", created_at=" + created_at + ", deposit_amount=" + deposit_amount + ", deposit_doc=" + deposit_doc + ", status=" + status + ", remark=" + remark + '}';
    }    
}
