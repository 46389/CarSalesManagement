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
import jakarta.persistence.ManyToOne;
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
@Table(name="instalment_payment")
@NamedQueries({
    @NamedQuery(
        name = "InstalmentPayment.getPaymentByPlan",
        query = "SELECT ipyt FROM InstalmentPayment ipyt WHERE ipyt.instalment_id.instalment_id = :instalment_id"
    ),
    @NamedQuery(
        name = "InstalmentPayment.getPaymentBySalesman",
        query = "SELECT ipyt FROM InstalmentPayment ipyt WHERE ipyt.instalment_id.sales_id.salesman.user_id = :salesman_id"
    ),
    @NamedQuery(
        name = "InstalmentPayment.getPaymentBySalesmen",
        query = "SELECT ipyt FROM InstalmentPayment ipyt WHERE ipyt.instalment_id.sales_id.salesman.user_id IN :salesman_ids"
    )  
})
public class InstalmentPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String payment_id;
    @ManyToOne
    @JoinColumn(name="instalment_id", nullable = false)
    private InstalmentPlan instalment_id;
    @Column(nullable = false)
    private LocalDateTime created_at;
    private double amount_paid;
    private String receipt;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String remark;
    
    public enum Status {
        Pending_Approval, Approved, Rejected
    }

    public InstalmentPayment() {
        this.created_at = LocalDateTime.now();
        this.status = Status.Pending_Approval;
    }

    public InstalmentPayment(InstalmentPlan instalment_id, double amount_paid, String receipt) {
        this.instalment_id = instalment_id;
        this.created_at = LocalDateTime.now();
        this.amount_paid = amount_paid;
        this.receipt = receipt;
        this.status = Status.Pending_Approval;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public InstalmentPlan getInstalment_id() {
        return instalment_id;
    }

    public void setInstalment_id(InstalmentPlan instalment_id) {
        this.instalment_id = instalment_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
    
    public double getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(double amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.payment_id);
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
        final InstalmentPayment other = (InstalmentPayment) obj;
        return Objects.equals(this.payment_id, other.payment_id);
    }

    @Override
    public String toString() {
        return "InstalmentPayment{" + "payment_id=" + payment_id + ", instalment_id=" + instalment_id + ", created_at=" + created_at + ", amount_paid=" + amount_paid + ", receipt=" + receipt + ", status=" + status + ", remark=" + remark + '}';
    }

    
}
