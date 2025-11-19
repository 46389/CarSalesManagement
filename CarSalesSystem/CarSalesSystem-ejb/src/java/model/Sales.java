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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Chew Jin Ni
 */
@Entity
@Table(name="sales")
@NamedQueries({
    @NamedQuery(
        name = "Sales.findBySalesmanAndCarStatus",
        query = "SELECT sl.sales_id, c.user_id, c.name, c.ic, c.phone, c.email, sl.car_id.car_stock_id, sl.car_id.car_type.car.model_name, "
                + "sl.car_id.car_type.colour, sl.car_id.car_type.price, sl.sales_status, sl.car_id.stock_status FROM Sales sl JOIN FETCH "
                + "sl.customer c WHERE sl.salesman.user_id = :id AND sl.car_id.stock_status = :status AND sl.sales_status <> :sales_status "
                + "ORDER BY sl.created_at"
    ),
    @NamedQuery(
        name = "Sales.findBySalesmanAndSalesStatus",
        query = "SELECT sl.sales_id, c.user_id, c.name, c.ic, c.phone, c.email, sl.car_id.car_stock_id, sl.car_id.car_type.car.model_name, "
                + "sl.car_id.car_type.colour, sl.car_id.car_type.price, sl.sales_status FROM Sales sl JOIN FETCH sl.customer c WHERE "
                + "sl.salesman.user_id = :id AND sl.sales_status = :status ORDER BY sl.created_at"
    ),
    @NamedQuery(
        name = "Sales.findByCustomerAndCarStatus",
        query = "SELECT sl FROM Sales sl WHERE sl.customer.user_id = :customer_id AND sl.car_id.stock_status = :status ORDER BY sl.created_at"
    ),
    @NamedQuery(
        name = "Sales.getSalesmanPerformance",
        query = """
            SELECT
                s.salesman.user_id AS salesmanId,
                SUM(CASE WHEN s.sales_status != :status THEN s.total_price ELSE 0 END) AS totalSales,
                SUM(CASE WHEN s.sales_status != :status THEN 1 ELSE 0 END) AS nonCancelledSalesCount,
                SUM(CASE WHEN s.sales_status = :status THEN 1 ELSE 0 END) AS cancelledSalesCount
            FROM Sales s
            WHERE
                FUNCTION('MONTH', s.created_at) = :month
                AND FUNCTION('YEAR', s.created_at) = :year
                AND s.salesman.user_id IN :salesman_ids
            GROUP BY s.salesman.user_id
            ORDER BY s.salesman.user_id
        """
    ),
    @NamedQuery(
        name = "Sales.getAvailableYears",
        query = "SELECT DISTINCT FUNCTION('YEAR', s.created_at) FROM Sales s ORDER BY FUNCTION('YEAR', s.created_at) DESC"
    ),
    @NamedQuery(
        name = "Sales.getSalesPerformanceByMonth",
        query = "SELECT FUNCTION('MONTH', s.created_at), FUNCTION('DAY', s.created_at), SUM(s.total_price) FROM Sales s "
                + "WHERE s.sales_status <> :status_1 AND s.sales_status <> :status_2 GROUP BY FUNCTION('MONTH', s.created_at), "
                + "FUNCTION('DAY', s.created_at) ORDER BY FUNCTION('MONTH', s.created_at), FUNCTION('DAY', s.created_at)"
    ),
    @NamedQuery(
        name = "Sales.getSalesStatusBySalesman",
        query = """
                SELECT 
                    SUM(CASE WHEN s.sales_status = :status_1 THEN 1 ELSE 0 END) AS cancelledCount,
                    SUM(CASE WHEN s.sales_status = :status_2 THEN 1 ELSE 0 END) AS pendingPaymentCount,
                    SUM(CASE WHEN s.sales_status = :status_3 THEN 1 ELSE 0 END) AS pendingPaymentApprovalCount,
                    SUM(CASE WHEN s.sales_status = :status_4 THEN 1 ELSE 0 END) AS manufacturingCount,
                    SUM(CASE WHEN s.sales_status = :status_5 THEN 1 ELSE 0 END) AS deliveringCount,
                    SUM(CASE WHEN s.sales_status = :status_6 THEN 1 ELSE 0 END) AS deliveredCount,
                    SUM(CASE WHEN s.sales_status = :status_7 THEN 1 ELSE 0 END) AS repossessedCount
                FROM Sales s
                WHERE s.salesman.user_id IN :salesman_ids
                """
    ),
    @NamedQuery(
        name = "Sales.getRatingBySalesman",
        query = "SELECT s.salesman.user_id, s.customer.user_id, s.sales_id, r.rating, r.comment FROM Sales s JOIN FETCH s.rating r WHERE s.salesman.user_id IN :salesman_ids"
    ),
    @NamedQuery(
        name = "Sales.getRatingValueBySalesman",
        query = """
                SELECT 
                    SUM(CASE WHEN r.rating = 1 THEN 1 ELSE 0 END) AS rating1Count,
                    SUM(CASE WHEN r.rating = 2 THEN 1 ELSE 0 END) AS rating2Count,
                    SUM(CASE WHEN r.rating = 3 THEN 1 ELSE 0 END) AS rating3Count,
                    SUM(CASE WHEN r.rating = 4 THEN 1 ELSE 0 END) AS rating4Count,
                    SUM(CASE WHEN r.rating = 5 THEN 1 ELSE 0 END) AS rating5Count
                FROM Sales s
                JOIN s.rating r
                WHERE s.salesman.user_id IN :salesman_ids
                GROUP BY s.salesman.user_id
                ORDER BY s.salesman.user_id
                """
    ),
    @NamedQuery(
        name = "Sales.getCarTrends",
        query = """
                SELECT 
                    s.car_id.car_type.car.model_name,
                    COUNT(s.sales_id) AS salesNum
                FROM Sales s
                WHERE
                    s.sales_status <> :status
                    AND FUNCTION('YEAR', s.created_at) = :year
                    AND FUNCTION('MONTH', s.created_at) = :month
                GROUP BY s.car_id.car_type.car.model_name
                ORDER BY COUNT(s.sales_id) DESC
                """              
    )
})
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sales_id;
    @OneToOne
    @JoinColumn(name="car_id", nullable = false)
    private CarStock car_id;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id", nullable = false)
    private Users customer;
    @ManyToOne
    @JoinColumn(name = "salesman_id", referencedColumnName = "user_id", nullable = false)
    private Users salesman;
    @ManyToOne
    @JoinColumn(name="rating_id", nullable = true)
    private Rating rating;
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private double total_price;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SalesStatus sales_status;
    
    public enum SalesStatus {
        Cancelled, Pending_Payment, Pending_Payment_Approval, Manufacturing, Delivering, Delivered, Repossessed
    }

    public Sales() {
        this.quantity = 1;
        this.created_at = LocalDateTime.now();
        this.sales_status = SalesStatus.Pending_Payment;
    }
    
    public Sales(CarStock car_id, Users customer, Users salesman, int quantity, double unit_price) {
        this.quantity = quantity;
        this.created_at = LocalDateTime.now();
        this.sales_status = SalesStatus.Pending_Payment;
        this.car_id = car_id;
        this.customer = customer;
        this.salesman = salesman;
        this.total_price = quantity * unit_price;
    }

    public String getSales_id() {
        return sales_id;
    }

    public void setSales_id(String sales_id) {
        this.sales_id = sales_id;
    }

    public CarStock getCar_id() {
        return car_id;
    }

    public void setCar_id(CarStock car_id) {
        this.car_id = car_id;
    }

    public Users getCustomer() {
        return customer;
    }

    public void setCustomer(Users customer) {
        this.customer = customer;
    }

    public Users getSalesman() {
        return salesman;
    }

    public void setSalesman(Users salesman) {
        this.salesman = salesman;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public SalesStatus getSalesStatus() {
        return sales_status;
    }

    public void setSalesStatus(SalesStatus salesStatus) {
        this.sales_status = salesStatus;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sales_id != null ? sales_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the sales_id fields are not set
        if (!(object instanceof Sales)) {
            return false;
        }
        Sales other = (Sales) object;
        if ((this.sales_id == null && other.sales_id != null) || (this.sales_id != null && !this.sales_id.equals(other.sales_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sales{" + "sales_id=" + sales_id + ", car_id=" + car_id + ", customer=" + customer + ", salesman=" + salesman + ", rating=" + rating + ", created_at=" + created_at + ", quantity=" + quantity + ", total_price=" + total_price + ", sales_status=" + sales_status + '}';
    }
}
