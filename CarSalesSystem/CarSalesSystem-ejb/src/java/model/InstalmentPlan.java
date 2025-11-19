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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Chew Jin Ni
 */

@Entity
@Table(name="instalment_plan")
@NamedQueries({
    @NamedQuery(
        name = "InstalmentPlan.getInstalmentPlanBySales",
        query = "SELECT ip FROM InstalmentPlan ip WHERE ip.sales_id.sales_id = :sales_id"
    ),
    @NamedQuery(
        name = "InstalmentPlan.getInstalmentStatus",
        query = """
                SELECT 
                    SUM(CASE WHEN p.total_amount = p.total_paid THEN 1 ELSE 0 END) AS completedPlans, 
                    SUM(CASE WHEN p.total_amount > p.total_paid THEN 1 ELSE 0 END) AS ongoingPlans
                FROM InstalmentPlan p 
                WHERE
                p.plan_status = :status AND
                p.sales_id.salesman.user_id IN :salesman_ids
                """
    )
})
public class InstalmentPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String instalment_id;
    
    @OneToOne
    @JoinColumn(name = "sales_id", nullable = false)
    private Sales sales_id;
    
    @Column(nullable = false)
    private int tenure_months;
    @Column(nullable = false)
    private double total_amount;
    @Column(nullable = false)
    private double interest_rate;
    @Column(nullable = false)
    private double monthly_payment;
    private LocalDate next_due_date;
    private double total_paid;
    @Enumerated(EnumType.STRING)
    private PlanStatus plan_status;
    private String remark;
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;
    
    public enum PlanStatus {
        Pending_Approval, Approved, Rejected
    }

    public InstalmentPlan() {
        this.created_at = LocalDateTime.now();
    }
    
   public InstalmentPlan(Sales sales_id, int tenure_months, double total_amount, double interest_rate, double monthly_payment) {
        this.sales_id = sales_id;
        this.tenure_months = tenure_months;
        this.total_amount = total_amount;
        this.interest_rate = interest_rate;
        this.monthly_payment = monthly_payment;
        this.created_at = LocalDateTime.now();
    }

    public String getInstalment_id() {
        return instalment_id;
    }

    public void setInstalment_id(String instalment_id) {
        this.instalment_id = instalment_id;
    }

    public Sales getSales_id() {
        return sales_id;
    }

    public void setSales_id(Sales sales_id) {
        this.sales_id = sales_id;
    }

    public int getTenure_months() {
        return tenure_months;
    }

    public void setTenure_months(int tenure_months) {
        this.tenure_months = tenure_months;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public double getMonthly_payment() {
        return monthly_payment;
    }

    public void setMonthly_payment(double monthly_payment) {
        this.monthly_payment = monthly_payment;
    }

    public LocalDate getNext_due_date() {
        return next_due_date;
    }

    public void setNext_due_date(LocalDate next_due_date) {
        this.next_due_date = next_due_date;
    }

    public double getTotal_paid() {
        return total_paid;
    }

    public void setTotal_paid(double total_paid) {
        this.total_paid = total_paid;
    }

    public PlanStatus getPlan_status() {
        return plan_status;
    }

    public void setPlan_status(PlanStatus plan_status) {
        this.plan_status = plan_status;
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

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.instalment_id);
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
        final InstalmentPlan other = (InstalmentPlan) obj;
        return Objects.equals(this.instalment_id, other.instalment_id);
    }

    @Override
    public String toString() {
        return "InstalmentPlan{" + "instalment_id=" + instalment_id + ", sales_id=" + sales_id + ", tenure_months=" + tenure_months + ", total_amount=" + total_amount + ", interest_rate=" + interest_rate + ", monthly_payment=" + monthly_payment + ", next_due_date=" + next_due_date + ", total_paid=" + total_paid + ", plan_status=" + plan_status + ", remark=" + remark + ", created_at=" + created_at + '}';
    }
    
}
