<%-- 
    Document   : customer_home
    Created on : Mar 21, 2025, 4:32:25 PM
    Author     : Chew Jin Ni
--%>

<%@page import="model.Users"%>
<%@page import="java.util.List"%>
<%@page import="model.Car"%>
<%@page import="model.CarType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Home Page</title>
    <link rel="stylesheet" href="../css/car_catalog.css">
    <link rel="stylesheet" href="../css/popup_container.css">
    <link rel="stylesheet" type="text/css" href="../css/notification.css">

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            // Function to open the pop-up and set the selected car stock details
            window.openInstalmentCalculator = function (model, price) {
                document.getElementById('model_name').value = model;
                document.getElementById('loan_amount').value = price; // Set loan amount
                document.getElementById('loan_period').value = 1; // Default loan period
                document.getElementById('interest_rate').value = 2.5; // Default interest rate

                // Show the pop-up
                document.getElementById('popupOverlay').style.display = 'block';
                document.getElementById('popupContainer').style.display = 'block';
            };

            // Function to close the pop-up
            window.closeInstalmentCalculator = function () {
                document.getElementById('popupOverlay').style.display = 'none';
                document.getElementById('popupContainer').style.display = 'none';
            };

            // Function to close the results pop-up
            window.closeResultsPopup = function () {
                document.getElementById('resultsPopupOverlay').style.display = 'none';
                document.getElementById('resultsPopupContainer').style.display = 'none';
            };
            
            window.submitForm = function (car_id, car_price) {
                const isConfirmed = confirm("Confirm to inquire about this product?");
                if (isConfirmed) {            
                    document.getElementById('car_type_id').value = car_id;
                    document.getElementById('car_price').value = car_price;
                    document.getElementById(`inquiryForm`).submit();
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
        <c:if test="${not empty sessionScope.customer_errorMsg}">
            ${sessionScope.customer_errorMsg}
            <% session.removeAttribute("customer_errorMsg");%>
        </c:if>  
    </div>
    <%@ include file="header.jsp"%>
    <h1 class="page-title">Car Catalog</h1>
    <%@ include file="car_search_bar.jsp"%>

    <!-- Check if the 'cars' list is not empty -->
    <c:if test="${not empty cars}">
        <div class="car-container">
            <!-- Iterate over the 'cars' list -->
            <c:forEach var="car" items="${cars}">
                <!-- Iterate over each CarType -->
                <c:forEach var="type" items="${car.carTypes}">
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
                            <!-- Footer: Price and Buttons -->
                            <div class="card-footer">
                                <div class="buttons"> 
                                    <button onclick="openInstalmentCalculator('${car.model_name}', ${type.price})" class="button">Instalment Calculator</button>
                                    <form id="inquiryForm" action="${pageContext.request.contextPath}/Inquiry" method="POST" style="display: none;">
                                        <input type="hidden" name="car_type_id" id="car_type_id">
                                        <input type="hidden" name="car_price" id="car_price">
                                    </form>
                                    <button onclick="submitForm('${type.car_id}', ${type.price})" class="button">Inquire Details</button>     
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:forEach>
        </div>
    </c:if>
    <!-- Display a message if no cars are found -->
    <c:if test="${empty cars}">
        <p>No cars available!</p>
    </c:if>
        
    <!-- Pop-up Overlay -->
    <div class="popup-overlay" id="popupOverlay" onclick="closeInstalmentCalculator()"></div>

    <!-- Pop-up Container -->
    <div class="popup-container" id="popupContainer">
        <h2>Instalment Calculator</h2>
        <form action="${pageContext.request.contextPath}/InstalmentCalculator" method="POST" id="installmentForm">
            <input type="hidden" id="model_name" name="model_name" required>
            
            <label for="loan_amount">Loan Amount:</label>
            <input type="number" id="loan_amount" name="loan_amount" min="1" required>

            <label for="loan_period">Loan Period (Months):</label>
            <input type="number" id="loan_period" name="loan_period" min="1" value="1" required>

            <label for="interest_rate">Interest Rate (%):</label>
            <input type="number" id="interest_rate" name="interest_rate" step="0.01"value="2.5" min="0.01" required>
            <small>Default interest rate is 2.5% (subject to changes).</small>

            <button type="submit">Calculate</button>
        </form>
        <button onclick="closeInstalmentCalculator()" style="margin-top: 10px;">Close</button>
    </div>
            
     <!-- Results Pop-up -->
    <%
        // Retrieve session attributes
        String modelName = (String) session.getAttribute("modelName");
        Double loanAmount = (Double) session.getAttribute("loanAmount");
        Integer loanPeriod = (Integer) session.getAttribute("loanPeriod");
        Double interestRate = (Double) session.getAttribute("interestRate");
        Double monthlyPayment = (Double) session.getAttribute("monthlyPayment");
        Double totalAmount = (Double) session.getAttribute("totalAmount");
        
        // Check if session attributes exist
        if (loanAmount != null && loanPeriod != null && interestRate != null && monthlyPayment != null && totalAmount != null) {
    %>
    <!-- Results Pop-up Overlay -->
    <div class="popup-overlay" id="resultsPopupOverlay" onclick="closeResultsPopup()" style="display: block;"></div>

    <!-- Results Pop-up Container -->
    <div class="popup-container" id="resultsPopupContainer" style="display: block;">
        <h3>Calculation Results</h3>
        <br>
        <p><strong>Model Name:</strong> ${modelName}</p>
        <p><strong>Loan Amount:</strong> RM ${loanAmount}</p>
        <p><strong>Loan Period:</strong> ${loanPeriod} months</p>
        <p><strong>Interest Rate:</strong> ${interestRate} %</p>
        <p><strong>Monthly Payment:</strong> RM ${monthlyPayment}</p>
        <p><strong>Total Payment Amount:</strong> RM ${totalAmount}</p>
        <br>
        <button onclick="closeResultsPopup()" style="margin-top: 10px;">Close</button>
    </div>

    <%
            // Clear session attributes after displaying the results
            session.removeAttribute("modelName");
            session.removeAttribute("loanAmount");
            session.removeAttribute("loanPeriod");
            session.removeAttribute("interestRate");
            session.removeAttribute("monthlyPayment");
            session.removeAttribute("totalAmount");
        }
    %>
</body>
</html>