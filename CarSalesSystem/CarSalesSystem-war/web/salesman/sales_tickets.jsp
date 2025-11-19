<%-- 
    Document   : salesman_home.jsp
    Created on : Mar 22, 2025, 1:19:31 AM
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
    <link rel="stylesheet" type="text/css" href="../css/popup_container.css">
    <link rel="stylesheet" type="text/css" href="../css/notification.css">
    <style>
        .table td:nth-child(12){
            min-width: 240px;
            white-space: nowrap;
        }

        .table td:nth-child(13){
            min-width: 120px;
            white-space: nowrap;  
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            window.changeSalesStatus = function (sales_id, car_id, status) {
                const isConfirmed = confirm("Confirm to change this sales' status?");
                if (isConfirmed) {            
                    document.getElementById('sales_status_id').value = sales_id;
                    document.getElementById('car_id').value = car_id;
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
        <h2 class="text-center my-4">Inquiries Tickets</h2>

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
                        <th scope="col">Sales Status</th>
                        <th scope="col">Car Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ticket" items="${sessionScope.tickets_list}">
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
                            
                            
                            <!--Instalment Plan-->
                            <td>  
                                <c:choose>
                                    <c:when test="${not empty sessionScope.ip_list}">
                                        <c:set var="hasRecord" value="false"></c:set>
                                        <c:forEach var="plan" items="${sessionScope.ip_list}">   
                                            <c:if test="${plan.sales_id.sales_id == ticket.sales_id}">  
                                                <c:forEach var="deposit" items="${sessionScope.dp_list}"> 
                                                    <c:if test="${deposit.sales_id.sales_id == ticket.sales_id}">
                                                        <c:set var="hasRecord" value="true"></c:set>
                                                        <p>Total Loan: ${plan.total_amount}</p>
                                                        <p>Monthly Payment: ${plan.monthly_payment}</p>
                                                        <p>Loan Tenure: ${plan.tenure_months} months</p>
                                                        <p>Interest rate: ${plan.interest_rate}</p>
                                                        <button onclick="openInstalmentForm('${ticket.sales_id}', ${ticket.car_price}, ${deposit.deposit_amount}, ${plan.tenure_months}, ${plan.interest_rate})">Change Plan</button>
                                                    </c:if>
                                                </c:forEach> 
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${!hasRecord}">
                                            <button onclick="openInstalmentForm('${ticket.sales_id}', ${ticket.car_price}, null, null, null, null)">Add Plan</button>  
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <button onclick="openInstalmentForm('${ticket.sales_id}', ${ticket.car_price}, null, null, null, null)">Add Plan</button>  
                                    </c:otherwise>   
                                </c:choose>
                            </td>
                            
                            <!-- Pop-up Overlay -->
                            <div class="popup-overlay" id="popupOverlay" onclick="closeInstalmentForm()"></div>

                            <!-- Pop-up Container -->
                            <div class="popup-container" id="popupContainer">
                                <h2>Instalment Plan</h2>
                                <form action="${pageContext.request.contextPath}/AddInstalmentPlan" method="POST" id="installmentForm">
                                    <input type="hidden" id="instalment_sales_id" name="instalment_sales_id" value="${ticket.sales_id}">
                                    <input type="hidden" id="plan_car_price" name="car_price" value="${ticket.car_price}">
                                    <input type="hidden" id="type" name="type" value="sales_tickets">

                                    
                                    <label for="deposit_amount">Deposit Amount:</label>
                                    <input type="number" id="deposit_amount" name="deposit_amount" min="15000" required>

                                    <label for="loan_amount">Loan Amount:</label>
                                    <input type="number" id="loan_amount" name="loan_amount" min="0" required>

                                    <label for="loan_period">Loan Period (Months):</label>
                                    <input type="number" id="loan_period" name="loan_period" min="0" value="0" required>

                                    <label for="interest_rate">Interest Rate (%):</label>
                                    <input type="number" id="interest_rate" name="interest_rate" step="0.01"value="2.5" min="0.00" required>
                                    <small>Default interest rate is 2.5% (subject to changes).</small>

                                    <button type="submit">Submit</button>
                                </form>
                                <button onclick="closeInstalmentForm()" style="margin-top: 10px;">Close</button>
                            </div>
                            
                            <!-- Sales Status Dropdown -->
                            <td>
                                <form id="changeSalesStatusForm" action="${pageContext.request.contextPath}/UpdateSalesStatus" method="POST" style="display: none;">
                                    <input type="hidden" name="sales_id" id="sales_status_id">
                                    <input type="hidden" name="sales_status" id="sales_status">
                                    <input type="hidden" name="car_id" id="car_id">
                                </form>
                                <select class="dropdown-status" 
                                        style="${ticket.sales_status eq 'Cancelled' ? 'background-color: #ff5d52;' : 
                                            ticket.sales_status eq 'Pending_Payment' ? 'background-color: #ffb947;' : ''}"
                                        onchange="changeSalesStatus('${ticket.sales_id}', '${ticket.car_id}', this.value)">
                                    <option style="background-color: white" value="Cancelled" ${ticket.sales_status eq 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                                    <option style="background-color: white" value="Pending_Payment" ${ticket.sales_status eq 'Pending_Payment' ? 'selected' : ''}>Pending Payment</option>
                                </select>
                            </td>
                            
                            <!-- Car Status Dropdown -->
                            <td>
                                <form id="alterCarStatusForm_${ticket.sales_id}" 
                                    action="${pageContext.request.contextPath}/UpdateCarStatus" 
                                    method="POST" 
                                    style="display: none">
                                    <input type="hidden" name="sales_id" value="${ticket.sales_id}">
                                    <input type="hidden" name="car_status" id="car_status_1" value="Booked">
                                    <input type="hidden" name="car_id" value="${ticket.car_id}">
                                </form>
                                <select class="dropdown-status" 
                                        style="${ticket.car_status eq 'Interested' ? 'background-color: #7ad1ff;' : 
                                            ticket.car_status eq 'Booked' ? 'background-color: #ffe07a;' : ''}"
                                        onchange="alterCarStatus('${ticket.sales_id}', this.value)">
                                    <option style="background-color: white" value="Interested" ${ticket.car_status eq 'Interested' ? 'selected' : ''}>Interested</option>
                                    <option style="background-color: white" value="Booked" ${ticket.car_status eq 'Booked' ? 'selected' : ''}>Booked</option>
                                </select>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>