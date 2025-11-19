/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Chew Jin Ni
 */
@Entity
@Table(name="car_type")
@NamedQueries({
    @NamedQuery(
        name = "CarType.getAllCarTypes",
        query = "SELECT c.model_id, c.model_name, c.brand, c.fuel_type, c.horsepower, c.seats, c.transmission_type, ct.car_id, ct.colour, "
                + "ct.year_of_manufacture, ct.price, SUM(CASE WHEN cs.stock_status = :status_1 OR cs.stock_status = :status_2 THEN 1 ELSE 0 END) "
                + "AS availableInterestedCount, SUM(CASE WHEN cs.stock_status = :status_3 THEN 1 ELSE 0 END) AS bookedCount, "
                + "SUM(CASE WHEN cs.stock_status = :status_4 THEN 1 ELSE 0 END) AS paidCount FROM CarType ct JOIN ct.car c LEFT JOIN "
                + "ct.carStocks cs GROUP BY c.model_id, c.model_name, c.brand, c.fuel_type, c.horsepower, c.seats, c.transmission_type, "
                + "ct.car_id, ct.colour, ct.year_of_manufacture, ct.price"
    ),
    @NamedQuery(
        name = "CarType.getFilteredCarTypes",
        query = "SELECT c.model_id, c.model_name, c.brand, c.fuel_type, c.horsepower, c.seats, c.transmission_type, ct.car_id, ct.colour, "
                + "ct.year_of_manufacture, ct.price, SUM(CASE WHEN cs.stock_status = :status_1 OR cs.stock_status = :status_2 THEN 1 ELSE 0 END) "
                + "AS availableInterestedCount, SUM(CASE WHEN cs.stock_status = :status_3 THEN 1 ELSE 0 END) AS bookedCount, "
                + "SUM(CASE WHEN cs.stock_status = :status_4 THEN 1 ELSE 0 END) AS paidCount FROM CarType ct JOIN ct.car c LEFT JOIN "
                + "ct.carStocks cs WHERE LOWER(c.model_name) LIKE :searchTerm OR LOWER(c.brand) LIKE :searchTerm OR LOWER(c.fuel_type) LIKE :searchTerm OR LOWER(ct.colour) LIKE :searchTerm "
                + "GROUP BY c.model_id, c.model_name, c.brand, c.fuel_type, c.horsepower, c.seats, c.transmission_type, "
                + "ct.car_id, ct.colour, ct.year_of_manufacture, ct.price"
    )
})
public class CarType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String car_id;
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Car car;
    @Column(nullable = false)
    private String colour;
    @Column(nullable = false)
    private int year_of_manufacture;
    private String image;
    @Column(nullable = false)
    private double price;
    @OneToMany(mappedBy = "car_type", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private ArrayList<CarStock> carStocks = new ArrayList<>();

    public CarType() {
    }

    public CarType(Car car, String colour, int year_of_manufacture, String image, double price) {
        this.car = car;
        this.colour = colour;
        this.year_of_manufacture = year_of_manufacture;
        this.image = image;
        this.price = price;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getYear_of_manufacture() {
        return year_of_manufacture;
    }

    public void setYear_of_manufacture(int year_of_manufacture) {
        this.year_of_manufacture = year_of_manufacture;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<CarStock> getCarStocks() {
        return carStocks;
    }

    public void setCarStocks(ArrayList<CarStock> carStocks) {
        this.carStocks = carStocks;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (car_id != null ? car_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the car_id fields are not set
        if (!(object instanceof CarType)) {
            return false;
        }
        CarType other = (CarType) object;
        if ((this.car_id == null && other.car_id != null) || (this.car_id != null && !this.car_id.equals(other.car_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CarType{" + "car_id=" + car_id + ", car=" + car + ", colour=" + colour + ", year_of_manufacture=" + year_of_manufacture + ", image=" + image + ", price=" + price + ", carStocks=" + carStocks + '}';
    }

    
}
