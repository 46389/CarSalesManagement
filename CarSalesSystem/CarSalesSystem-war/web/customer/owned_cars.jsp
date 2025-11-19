<%-- 
    Document   : owned_cars
    Created on : Apr 2, 2025, 9:59:59 AM
    Author     : Chew Jin Ni
--%>

<%@page import="util.Validation"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="model.InstalmentPayment"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.CarType"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Owned Cars</title>
        <link rel="stylesheet" type="text/css" href="../css/interested_cars.css">
        <link rel="stylesheet" type="text/css" href="../css/notification.css">
        <script>
            window.onload = function () {
                let notification = document.getElementById("notification");
                if (notification.textContent.trim() !== "") {
                    notification.style.display = "block";
                    setTimeout(() => {
                        notification.style.display = "none";
                    }, 5000);
                }
            };
        </script>
    </head>
    <body>
        <!-- Upload instalment payment -->
        <!-- View payment record -->
        <!-- View car status -->
        <!-- View instalment ongoing / completed -->
        <div id="notification" class="error_notification">
            <c:if test="${not empty sessionScope.customer_errorMsg}">
                ${sessionScope.customer_errorMsg}
                <% session.removeAttribute("customer_errorMsg");%>
            </c:if>  
        </div>
        <%@ include file="header.jsp" %>
        <h2 class="page-title">Owned Cars</h2>
        <c:if test="${not empty deposit_paid_cars}">
            <div class="car-container">
                <!-- Iterate over the 'cars' list -->
                <c:forEach var="sales" items="${sessionScope.deposit_paid_cars}">
                    <c:set var="type" value="${sales.car_id.car_type}" />
                    <c:if test="${not empty type}">
                        <c:set var="car" value="${type.car}" />
                        <c:if test="${not empty car}">
                            <%
                                // Retrieve the absolutePath from the type object
                                String absolutePath = ((CarType) pageContext.getAttribute("type")).getImage();
                                String basePath = "D:\\NetbeanProject\\CarSalesSystem\\CarSalesSystem-war\\web\\";
                                String relativePath = "../image/logo.png"; // Default image
                                if (absolutePath != null && !absolutePath.isEmpty()) {
                                    if (absolutePath.startsWith(basePath)) {
                                        relativePath = "../" + absolutePath.substring(basePath.length()).replace("\\", "/");
                                    }
                                }
                                // Store the processed relativePath in the request scope
                                pageContext.setAttribute("relativePath", relativePath);
                            %>
                            <div class="car-stock-card">
                                <!-- Display the CarType's image -->
                                <img src="${relativePath}" alt="${type.colour}" class="car-image">
                                <!-- Card Details -->
                                <div class="card-details">
                                    <div class="card-header">
                                        <h3>${car.model_name}</h3>
                                    </div>
                                    <div class="card-body">
                                        <p><strong>Brand:</strong> ${car.brand}</p>
                                        <p><strong>Fuel Type:</strong> ${car.fuel_type}</p>
                                        <p><strong>Horsepower:</strong> ${car.horsepower}</p>
                                        <p><strong>Seats:</strong> ${car.seats}</p>
                                        <p><strong>Transmission Type:</strong> ${car.transmission_type}</p>
                                        <p><strong>Colour:</strong> ${type.colour}</p>
                                        <p><strong>Year of Manufacture:</strong> ${type.year_of_manufacture}</p>
                                        <h4 class="price">RM <strong>${type.price}</strong></h4>
                                    </div>
                                    <hr>
                                    <!-- Footer: Price and Buttons -->
                                    <div class="card-footer">
                                        <strong>Assigned Salesman:</strong>
                                        <span class="badge bg-success">${sales.salesman.user_id} | ${sales.salesman.name}</span>
                                    </div>
                                    <hr>
                                    <div class="card-footer">
                                        <c:choose>
                                            <c:when test="${sales.getSalesStatus() eq 'Delivered'}">
                                                <strong>Sales Status:</strong>
                                                <span class="badge bg-success">${sales.getSalesStatus()}</span>
                                            </c:when>
                                            <c:when test="${sales.getSalesStatus() eq 'Repossessed'}">
                                                <strong>Sales Status:</strong>
                                                <span class="badge bg-danger">${sales.getSalesStatus()}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <strong>Sales Status:</strong>
                                                <span class="badge bg-warning">${sales.getSalesStatus()}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <hr>
                                    <div>   
                                        <c:forEach var="plan" items="${instalment_plan_list}">
                                            <c:if test="${plan.sales_id.sales_id == sales.sales_id}">
                                                <strong>Instalment Plan:</strong>
                                                <p></p>
                                                <p>Total Loan: ${plan.total_amount}</p>
                                                <p>Monthly Payment: ${plan.monthly_payment}</p>
                                                <p>Loan Tenure: ${plan.tenure_months} months</p>
                                                <p>Interest rate: ${plan.interest_rate}</p>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <hr>
                                    <div>
                                        <c:forEach var="plan" items="${instalment_plan_list}">
                                            <c:if test="${plan.sales_id.sales_id == sales.sales_id}">
                                                <c:choose>
                                                    <c:when test="${plan.total_paid == plan.total_amount}">
                                                        <div class="card-footer">   
                                                            <strong>Instalment Payment Status: </strong>
                                                            <span class="badge bg-success">Completed</span>
                                                        </div>       
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="card-footer">
                                                            <strong>Instalment Payment Status:</strong>
                                                            <span class="badge bg-warning">Ongoing</span>
                                                        </div>
                                                        <div class="card-footer">
                                                            <strong>Payment Due Date:</strong>
                                                            <span class="badge bg-warning">${plan.next_due_date}</span>
                                                        </div>
                                                        <div class="card-footer">
                                                            <strong>Next Month Payment Document:</strong>
                                                            <form id="updatePaymentForm" action="${pageContext.request.contextPath}/CusUpdatePaymentDoc" method="POST" enctype="multipart/form-data">
                                                                <input type="hidden" id="sales-id" name="sales_id" value="${sales.sales_id}">
                                                                <input type="hidden" id="type" name="type" value="instalment_doc">
                                                                <input type="file" id="instalment_file" name="doc" accept="image/*" onchange="this.form.submit()">
                                                            </form>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:forEach var="payment" items="${instalment_payment_list}">  
                                                    <c:if test="${payment.instalment_id.instalment_id == plan.instalment_id}">
                                                        <hr>
                                                        <strong>Instalment Payment History:</strong>
                                                        <hr>
                                                        <p></p>
                                                        <div class="card-footer">
                                                            <p><strong>Payment Date:</strong></p>
                                                            <%
                                                                LocalDateTime isoTimestamp = ((InstalmentPayment) pageContext.getAttribute("payment")).getCreated_at();
                                                                String formattedDate = Validation.formatLocalDateTime(isoTimestamp, "dd/MM/yyyy HH:mm:ss");
                                                                pageContext.setAttribute("datetime", formattedDate);
                                                            %>
                                                           ${datetime}
                                                        </div>
                                                        <p><strong>Payment Document: </strong></p>  
                                                        <%
                                                            String payment_base = "D:\\NetbeanProject\\CarSalesSystem\\CarSalesSystem-war\\web\\";
                                                            String existing_payment_absolutePath = ((InstalmentPayment) pageContext.getAttribute("payment")).getReceipt();
                                                            String existing_payment_relativePath = "";
                                                            if (existing_payment_absolutePath != null) {
                                                                if (existing_payment_absolutePath.startsWith(payment_base)) {
                                                                    existing_payment_relativePath = "../" + existing_payment_absolutePath.substring(payment_base.length()).replace("\\", "/");
                                                                }
                                                            }
                                                            pageContext.setAttribute("existing_payment_relativePath", existing_payment_relativePath);
                                                        %>
                                                        <a href="${existing_payment_relativePath}" target="_blank">View Document</a>
                                                        <p></p>
                                                        <c:if test="${payment.status eq 'Pending_Approval'}">
                                                            <p><strong>Update Payment Document: </strong></p>  
                                                            <form id="updateExistingPaymentForm" action="${pageContext.request.contextPath}/CusUpdateExistingPaymentDoc" method="POST" enctype="multipart/form-data">
                                                                <input type="hidden" id="payment-id" name="payment_id" value="${payment.payment_id}">
                                                                <input type="file" id="new_instalment_file" name="doc" accept="image/*" onchange="this.form.submit()">
                                                            </form>
                                                        </c:if>
                                                        <div class="card-footer">
                                                            <p><strong>Payment Status: </strong></p>
                                                            <c:choose>
                                                                <c:when test="${payment.status eq 'Approved'}">
                                                                    <span class="badge bg-success">Approved</span>
                                                                </c:when>
                                                                <c:when test="${payment.status eq 'Rejected'}">
                                                                    <span class="badge bg-danger">Rejected</span>
                                                                </c:when>
                                                                <c:when test="${payment.status eq 'Pending_Approval'}">
                                                                    <span class="badge bg-warning">Pending Approval</span>
                                                                </c:when>
                                                            </c:choose>
                                                        </div>
                                                        <p><strong>Payment Remark: ${payment.remark}</strong></p>
                                                        <hr>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </c:if>
    </body>
</html>