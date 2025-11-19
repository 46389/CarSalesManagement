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

/**
 *
 * @author Chew Jin Ni
 */
@Entity
@Table(name="car_stock")
@NamedQueries({
    @NamedQuery(
        name = "CarStock.getCarStockByCarType",
        query = "SELECT c From CarStock c where c.car_type.car_id = :car_id AND c.stock_status = :status"
    )
})
public class CarStock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String car_stock_id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockStatus stock_status;
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private CarType car_type;
    
    public enum StockStatus {
        Available, Interested, Booked, Paid
    }

    public CarStock() {
    }

    public CarStock(CarType car_type) {
        this.car_type = car_type;
        this.stock_status = StockStatus.Available;
    }

    public String getCar_stock_id() {
        return car_stock_id;
    }

    public void setCar_stock_id(String car_stock_id) {
        this.car_stock_id = car_stock_id;
    }

    public StockStatus getStock_status() {
        return stock_status;
    }

    public void setStock_status(StockStatus stock_status) {
        this.stock_status = stock_status;
    }

    public CarType getCar_type() {
        return car_type;
    }

    public void setCar_type(CarType car_type) {
        this.car_type = car_type;
    } 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (car_stock_id != null ? car_stock_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the car_stock_id fields are not set
        if (!(object instanceof CarStock)) {
            return false;
        }
        CarStock other = (CarStock) object;
        if ((this.car_stock_id == null && other.car_stock_id != null) || (this.car_stock_id != null && !this.car_stock_id.equals(other.car_stock_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CarStock{" + "car_stock_id=" + car_stock_id + ", stock_status=" + stock_status + ", car_type=" + car_type + '}';
    }
    
    
}
