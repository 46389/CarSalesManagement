/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.LocalDateTime;

/**
 *
 * @author Chew Jin Ni
 */
public class DepositInstalment {
    private String sales_id;
    private String salesman_id;
    private String customer_id;
    private String car_id;
    private String model_name;
    private double car_price;
    private LocalDateTime createdDate;
    private String deposit_id;
    private double deposit_amount;
    private String deposit_status;
    private String deposit_doc;
    private String deposit_remark;
    private String instalment_id;
    private double interest_rate;
    private double total_amount;
    private double monthly_payment;
    private double tenure_months;
    private String plan_status;
    private String plan_remark;
    private double loan_amount;

    public DepositInstalment() {
    }

    public double getLoan_amount() {
        this.loan_amount = this.car_price - this.deposit_amount;
        return loan_amount;
    }

    public void setLoan_amount(double loan_amount) {
        this.loan_amount = loan_amount;
    }
    
    public String getSales_id() {
        return sales_id;
    }

    public void setSales_id(String sales_id) {
        this.sales_id = sales_id;
    }

    public String getSalesman_id() {
        return salesman_id;
    }

    public void setSalesman_id(String salesman_id) {
        this.salesman_id = salesman_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
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

    public double getCar_price() {
        return car_price;
    }

    public void setCar_price(double car_price) {
        this.car_price = car_price;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeposit_id() {
        return deposit_id;
    }

    public void setDeposit_id(String deposit_id) {
        this.deposit_id = deposit_id;
    }

    public double getDeposit_amount() {
        return deposit_amount;
    }

    public void setDeposit_amount(double deposit_amount) {
        this.deposit_amount = deposit_amount;
    }

    public String getDeposit_status() {
        return deposit_status;
    }

    public void setDeposit_status(String deposit_status) {
        this.deposit_status = deposit_status;
    }

    public String getDeposit_doc() {
        return deposit_doc;
    }

    public void setDeposit_doc(String deposit_doc) {
        this.deposit_doc = deposit_doc;
    }

    public String getInstalment_id() {
        return instalment_id;
    }

    public void setInstalment_id(String instalment_id) {
        this.instalment_id = instalment_id;
    }

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getMonthly_payment() {
        return monthly_payment;
    }

    public void setMonthly_payment(double monthly_payment) {
        this.monthly_payment = monthly_payment;
    }

    public double getTenure_months() {
        return tenure_months;
    }

    public void setTenure_months(double tenure_months) {
        this.tenure_months = tenure_months;
    }

    public String getPlan_status() {
        return plan_status;
    }

    public void setPlan_status(String plan_status) {
        this.plan_status = plan_status;
    }

    public String getDeposit_remark() {
        return deposit_remark;
    }

    public void setDeposit_remark(String deposit_remark) {
        this.deposit_remark = deposit_remark;
    }

    public String getPlan_remark() {
        return plan_remark;
    }

    public void setPlan_remark(String plan_remark) {
        this.plan_remark = plan_remark;
    }

    @Override
    public String toString() {
        return "DepositInstalment{" + "sales_id=" + sales_id + ", salesman_id=" + salesman_id + ", customer_id=" + customer_id + ", car_id=" +
                car_id + ", model_name=" + model_name + ", car_price=" + car_price + ", createdDate=" + createdDate + ", deposit_id=" + 
                deposit_id + ", deposit_amount=" + deposit_amount + ", deposit_status=" + deposit_status + ", deposit_doc=" + deposit_doc + 
                ", deposit_remark=" + deposit_remark + ", instalment_id=" + instalment_id + ", interest_rate=" + interest_rate + 
                ", total_amount=" + total_amount + ", monthly_payment=" + monthly_payment + ", tenure_months=" + tenure_months + 
                ", plan_status=" + plan_status + ", plan_remark=" + plan_remark + ", loan_amount=" + loan_amount + '}';
    }
    
}
