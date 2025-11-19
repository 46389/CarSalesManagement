<%-- 
    Document   : cancelled_sales
    Created on : Apr 6, 2025, 11:04:46 PM
    Author     : Chew Jin Ni
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cancelled Sales</title>
        <link rel="stylesheet" type="text/css" href="../css/sales_table.css">
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <!-- Tickets Table -->
        <div class="table-container">
            <h2 class="text-center my-4">Cancelled Sales</h2>

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
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ticket" items="${sessionScope.cancelled_list}">
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
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
