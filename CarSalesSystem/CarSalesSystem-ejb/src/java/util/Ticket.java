/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Chew Jin Ni
 */
public class Ticket {
    private String sales_id;
    private String customer_id;
    private String customer_name;
    private String customer_ic;
    private String customer_phone;
    private String customer_email;
    private String car_id;
    private String model_name;
    private String car_colour;
    private double car_price;
    private String deposit_status;
    private String sales_status;
    private String car_status;

    public Ticket() {
    }

    public Ticket(String sales_id, String customer_id, String customer_name, String customer_ic, String customer_phone, String customer_email, String car_id, String model_name, String car_colour, double car_price, String deposit_status, String sales_status, String car_status) {
        this.sales_id = sales_id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_ic = customer_ic;
        this.customer_phone = customer_phone;
        this.customer_email = customer_email;
        this.car_id = car_id;
        this.model_name = model_name;
        this.car_colour = car_colour;
        this.car_price = car_price;
        this.deposit_status = deposit_status;
        this.sales_status = sales_status;
        this.car_status = car_status;
    }

    public String getSales_id() {
        return sales_id;
    }

    public void setSales_id(String sales_id) {
        this.sales_id = sales_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_ic() {
        return customer_ic;
    }

    public void setCustomer_ic(String customer_ic) {
        this.customer_ic = customer_ic;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getCar_colour() {
        return car_colour;
    }

    public void setCar_colour(String car_colour) {
        this.car_colour = car_colour;
    }

    public double getCar_price() {
        return car_price;
    }

    public void setCar_price(double car_price) {
        this.car_price = car_price;
    }

    public String getDeposit_status() {
        return deposit_status;
    }

    public void setDeposit_status(String deposit_status) {
        this.deposit_status = deposit_status;
    }

    public String getSales_status() {
        return sales_status;
    }

    public void setSales_status(String sales_status) {
        this.sales_status = sales_status;
    }

    public String getCar_status() {
        return car_status;
    }

    public void setCar_status(String car_status) {
        this.car_status = car_status;
    }

    @Override
    public String toString() {
        return "Ticket{" + "sales_id=" + sales_id + ", customer_id=" + customer_id + ", customer_name=" + customer_name + ", customer_ic=" + customer_ic + ", customer_phone=" + customer_phone + ", customer_email=" + customer_email + ", car_id=" + car_id + ", model_name=" + model_name + ", car_colour=" + car_colour + ", car_price=" + car_price + ", deposit_status=" + deposit_status + ", sales_status=" + sales_status + ", car_status=" + car_status + '}';
    }
    
}
