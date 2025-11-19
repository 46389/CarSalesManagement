<%-- 
    Document   : interested_cars
    Created on : Apr 2, 2025, 9:59:40 AM
    Author     : Chew Jin Ni
--%>

<%@page import="model.CarType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Interested Cars</title>
        <link rel="stylesheet" href="../css/interested_cars.css">
    </head>
    <body>
        <%@ include file="header.jsp"%>
        <h2 class="page-title">Interested Cars</h2>
        <c:if test="${not empty interested_cars}">
            <div class="car-container">
                <!-- Iterate over the 'cars' list -->
                <c:forEach var="sales" items="${sessionScope.interested_cars}">
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
                                    <!-- Footer: Price and Buttons -->
                                    <div class="card-footer">
                                        <strong>Assigned Salesman:</strong><span class="badge bg-success">${sales.salesman.user_id} | ${sales.salesman.name}</span>
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
