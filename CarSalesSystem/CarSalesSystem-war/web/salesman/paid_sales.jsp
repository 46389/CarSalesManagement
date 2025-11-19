<%-- 
    Document   : paid_sales
    Created on : Apr 6, 2025, 11:04:29 PM
    Author     : Chew Jin Ni
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sales Tickets</title>
    <link rel="stylesheet" type="text/css" href="../css/sales_table.css">
    <link rel="stylesheet" type="text/css" href="../css/notification.css">
    <style>
        .table td:nth-child(15){
            min-width: 200px;
            white-space: nowrap;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            window.changeSalesStatus = function (sales_id, car_id, status) {
                const isConfirmed = confirm("Confirm to change this sales' status?");
                if (isConfirmed) {            
                    document.getElementById('sales_status_sales_id').value = sales_id;
                    document.getElementById('sales_status_car_id').value = car_id;
                    document.getElementById('sales_status').value = status;
                    document.getElementById('changeSalesStatusForm').submit();
                }
            };
            
            window.alterCarStatus = function (sales_id, status) {
                const isConfirmed = confirm("Confirm to change this sales' car status?");
                if (isConfirmed) {
                    document.getElementById('car_status_1').value = status;
                    document.getElementById('alterCarStatusForm_'+sales_id).submit();
                }
            };
            
            window.openInstalmentForm = function (sales_id, price, deposit, tenure, interest) {
                document.getElementById('instalment_sales_id').value = sales_id;
                if(deposit === null){
                    document.getElementById('deposit_amount').value = 30000;
                    document.getElementById('loan_amount').value = price - 30000;
                }else{
                    document.getElementById('deposit_amount').value = deposit;
                    document.getElementById('loan_amount').value = price - deposit;
                }
                
                if(tenure === null){
                    document.getElementById('loan_period').value = 12;
                }else{
                    document.getElementById('loan_period').value = tenure;
                }
                
                if(interest === null){
                    document.getElementById('interest_rate').value = 2.5;
                }else{
                    document.getElementById('interest_rate').value = interest;
                }

                // Show the pop-up
                document.getElementById('popupOverlay').style.display = 'block';
                document.getElementById('popupContainer').style.display = 'block';
            };

            // Function to close the pop-up
            window.closeInstalmentForm = function () {
                document.getElementById('popupOverlay').style.display = 'none';
                document.getElementById('popupContainer').style.display = 'none';
            };
            
            window.sendEmail = function (cus_name, email, model_name, payment, due_date) {
                const isConfirmed = confirm("Confirm to send reminder email to this customer?");
                if (isConfirmed) {
                    document.getElementById('customer-name').value = cus_name;
                    document.getElementById('customer-email').value = email;
                    document.getElementById('model-name').value = model_name;
                    document.getElementById('monthly-payment').value = payment;
                    document.getElementById('payment-due-date').value = due_date;
                    
                    document.getElementById('sendEmailForm').submit();
                }
            };
            
            window.onload = function () {
                let notification = document.getElementById("notification");
                if (notification.textContent.trim() !== "") {
                    notification.style.display = "block";
                    setTimeout(() => {
                        notification.style.display = "none";
                    }, 5000);
                }
            };
        });
    </script>
</head>
<body>
    <div id="notification" class="error_notification">
        <c:if test="${not empty sessionScope.salesman_errorMsg}">
            ${sessionScope.salesman_errorMsg}
            <% session.removeAttribute("salesman_errorMsg");%>
        </c:if>  
    </div>
    
    <%@ include file="header.jsp" %>

    <!-- Tickets Table -->
    <div class="table-container">
        <h2 class="text-center my-4">Paid Sales</h2>

        <!-- Responsive Table -->
        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">Sales ID</th>
                        <th scope="col">Customer ID</th>
                        <th scope="col">Customer Name</th>
                        <th scope="col">Customer IC</th>
                        <th scope="col">Customer Phone</th>
                        <th scope="col">Customer Email</th>
                        <th scope="col">Car ID</th>
                        <th scope="col">Model Name</th>
                        <th scope="col">Colour</th>
                        <th scope="col">Price</th>
                        <th scope="col">Instalment Plan</th>
                        <th scope="col">Total Paid</th>
                        <th scope="col">Instalment Status</th>
                        <th scope="col">Next Payment Due Date</th>
                        <th scope="col">Sales Status</th>
                        <th scope="col">Reminder Email</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ticket" items="${sessionScope.paid_saleslist}">
                        <tr>
                            <td>${ticket.sales_id}</td>
                            <td>${ticket.customer_id}</td>
                            <td>${ticket.customer_name}</td>
                            <td>${ticket.customer_ic}</td>
                            <td>${ticket.customer_phone}</td>
                            <td>${ticket.customer_email}</td>
                            <td>${ticket.car_id}</td>
                            <td>${ticket.model_name}</td>
                            <td>${ticket.car_colour}</td>
                            <td>${ticket.car_price}</td>
                            
                            <!-- Instalment Plan -->
                            <td>                           
                                <c:forEach var="plan" items="${sessionScope.ip_list}">
                                    <c:if test="${plan.sales_id.sales_id == ticket.sales_id}">
                                        <p>Total Loan: ${plan.total_amount}</p>
                                        <p>Monthly Payment: ${plan.monthly_payment}</p>
                                        <p>Loan Tenure: ${plan.tenure_months} months</p>
                                        <p>Interest rate: ${plan.interest_rate}</p>
                                    </c:if>
                                </c:forEach>
                            </td>
                            
                            <!-- Total Paid -->
                            <td>                           
                                <c:forEach var="plan" items="${sessionScope.ip_list}">
                                    <c:if test="${plan.sales_id.sales_id == ticket.sales_id}">
                                        ${plan.total_paid}
                                    </c:if>
                                </c:forEach>
                            </td>
                            
                            <!-- Payment Status -->
                            <td>
                                <c:forEach var="ip" items="${sessionScope.ip_list}">
                                    <c:if test="${ip.sales_id.sales_id == ticket.sales_id}">
                                        <c:choose>
                                            <c:when test="${ip.total_paid == ip.total_amount}">
                                                <span class="badge bg-success">Completed</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning">Ongoing</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </c:forEach>           
                            </td>
                            
                            <!-- Payment Status -->
                            <td>
                                <c:forEach var="ip" items="${sessionScope.ip_list}">
                                    <c:if test="${ip.sales_id.sales_id == ticket.sales_id}"> 
                                        ${ip.next_due_date}
                                    </c:if>
                                </c:forEach>           
                            </td>
                            
                            <!-- Sales Status Dropdown -->
                            <td>
                                <form id="changeSalesStatusForm" action="${pageContext.request.contextPath}/UpdateSalesStatus" method="POST" style="display: none;">
                                    <input type="hidden" name="sales_id" id="sales_status_sales_id">
                                    <input type="hidden" name="car_id" id="sales_status_car_id">
                                    <input type="hidden" name="sales_status" id="sales_status">
                                </form>
                                 <select class="dropdown-status" 
                                        style="${ticket.sales_status eq 'Manufacturing' ? 'background-color: #57d8ff;' : 
                                            ticket.sales_status eq 'Delivering' ? 'background-color: #ffc745;' : 
                                            ticket.sales_status eq 'Delivered' ? 'background-color: #4fff7e;':
                                            ticket.sales_status eq 'Repossessed' ? 'background-color: #ff4545;' : ''}"
                                        onchange="changeSalesStatus('${ticket.sales_id}', '${ticket.car_id}', this.value)">
                                    <option style="background-color: white" value="Manufacturing" ${ticket.sales_status eq 'Manufacturing' ? 'selected' : ''}>Manufacturing</option>
                                    <option style="background-color: white" value="Delivering" ${ticket.sales_status eq 'Delivering' ? 'selected' : ''}>Delivering</option>
                                    <option style="background-color: white" value="Delivered" ${ticket.sales_status eq 'Delivered' ? 'selected' : ''}>Delivered</option>
                                    <option style="background-color: white" value="Repossessed" ${ticket.sales_status eq 'Repossessed' ? 'selected' : ''}>Repossessed</option>
                                </select>
                            </td> 
                            <td>
                                <c:forEach var="ip" items="${sessionScope.ip_list}">
                                    <c:if test="${ip.sales_id.sales_id == ticket.sales_id}">
                                        <form id="sendEmailForm" action="${pageContext.request.contextPath}/SendEmail" method="POST" style="display: none;">
                                            <input type="hidden" name="name" id="customer-name">
                                            <input type="hidden" name="email" id="customer-email">
                                            <input type="hidden" name="model_name" id="model-name">
                                            <input type="hidden" name="monthly_payment" id="monthly-payment">
                                            <input type="hidden" name="due_date" id="payment-due-date">
                                        </form>
                                        <button class="btn btn-primary email-button" onclick="sendEmail('${ticket.customer_name}','${ticket.customer_email}','${ticket.model_name}', '${ip.monthly_payment}', '${ip.next_due_date}')">Send Email</button>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
