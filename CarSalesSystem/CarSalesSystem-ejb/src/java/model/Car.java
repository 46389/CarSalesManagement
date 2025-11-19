/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name="car")
@NamedQueries({
    @NamedQuery(
        name = "Car.getCarModels",
        query = "SELECT DISTINCT c FROM Car c LEFT JOIN FETCH c.carTypes"
    ),
    @NamedQuery(
        name = "Car.searchCars",
        query = "SELECT DISTINCT c FROM Car c WHERE 1=1"
    )
})

public class Car implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(nullable = false)
    private String model_id;
    @Column(nullable = false)
    private String model_name;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String fuel_type;
    @Column(nullable = false)
    private int horsepower;
    @Column(nullable = false)
    private int seats;
    @Column(nullable = false)
    private String transmission_type;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = false)
    private ArrayList<CarType> carTypes = new ArrayList<>();
    
    public Car() {
    }

    public Car(String model_id, String model_name, String brand, String fuel_type, int horsepower, int seats, String transmission_type) {
        this.model_id = model_id;
        this.model_name = model_name;
        this.brand = brand;
        this.fuel_type = fuel_type;
        this.horsepower = horsepower;
        this.seats = seats;
        this.transmission_type = transmission_type;
    }

    public List<CarType> getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(ArrayList<CarType> carTypes) {
        this.carTypes = carTypes;
    }
    
    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getTransmission_type() {
        return transmission_type;
    }

    public void setTransmission_type(String transmission_type) {
        this.transmission_type = transmission_type;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (model_id != null ? model_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the model_id fields are not set
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.model_id == null && other.model_id != null) || (this.model_id != null && !this.model_id.equals(other.model_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Car{" + "model_id=" + model_id + ", model_name=" + model_name + ", brand=" + brand + ", fuel_type=" + fuel_type + ", horsepower=" + horsepower + ", seats=" + seats + ", transmission_type=" + transmission_type + ", carTypes=" + carTypes + '}';
    }
    
}
